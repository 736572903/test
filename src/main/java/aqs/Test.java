package aqs;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;

public class Test {

    /**
     * 存储是否有小闪贷订单
     */
    private static ThreadLocal<Boolean> threadLocal = ThreadLocal.withInitial(() -> Boolean.FALSE);

    public static void main(String[] args) throws InterruptedException {
        JSONArray jsonArray = JSONArray.parseArray(JSON.toJSONString(null));
        System.out.println(jsonArray);

//        ReentrantLock lock = new ReentrantLock();
//        lock.tryLock();
//        lock.lock();
//
//        ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();
//        readWriteLock.writeLock();
//
//        Semaphore semaphore = new Semaphore(5);
//        semaphore.acquire();
//        semaphore.release();
//
//        CountDownLatch countDownLatch = new CountDownLatch(5);
//        countDownLatch.await();
//        countDownLatch.countDown();
//
//        AbstractQueuedSynchronizer aqs = new AbstractQueuedSynchronizer() {
//            @Override
//            protected boolean tryAcquire(int arg) {
//                return super.tryAcquire(arg);
//            }
//        };

        new Thread(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
            }
            threadLocal.set(true);
        }).start();

        new Thread(() -> {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
            }
            System.out.println(threadLocal.get());
        }).start();
    }
}
