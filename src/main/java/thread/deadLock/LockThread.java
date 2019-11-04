package thread.deadLock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;

public class LockThread implements Runnable {

    private String name;

    private Lock lock;

    private Lock to;


    public LockThread(String name, Lock lock) {
        this.lock = lock;
        this.name = name;
    }

    public void run() {

//		lock.lock();//尝试获得锁，如果锁在被使用，则一直阻塞直到获得锁

        //lock.tryLock()尝试获得锁，如果能立刻获得，则返回true，否则返回false
//		if(lock.tryLock()){
        try {
            if (lock.tryLock(1, TimeUnit.SECONDS)) {
                System.out.println(Thread.currentThread().getName() + ":" + name + "获得锁");
                try {
                    Thread.sleep(2000);
//				System.out.println(Thread.currentThread().getName()+":"+name+"获得锁");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                    System.out.println(Thread.currentThread().getName() + ":" + name + "释放锁");
                }

            } else {
                System.out.println(Thread.currentThread().getName() + ":" + name + "没有获得锁");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }

}
