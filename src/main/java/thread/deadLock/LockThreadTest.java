package thread.deadLock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 *使用lock来避免死锁 
 *
 */
public class LockThreadTest {
	
	public static void main(String[] args) {
		
		Lock lock = new ReentrantLock();
		
		
//		synchronized在发生异常时，会自动释放线程占有的锁，因此不会由于异常而导致死锁现象发生；
//		而Lock在发生异常时，如果没有主动通过unLock()去释放锁，则很可能造成死锁现象，因此使用Lock时需要在finally块中释放锁；
		
		LockThread lock1 = new LockThread("first", lock);
		LockThread lock2 = new LockThread("second", lock);
		
		new Thread(lock1).start();
		new Thread(lock2).start();
		
	}
	
	
}
