package thread.deadLock;

import java.util.ArrayList;
import java.util.List;

/**
 * 死锁
 * @author wanghj
 *
 */
public class DeadLockTest {
	
	private static List<String> listA = new ArrayList<String>();
	
	private static List<String> listB = new ArrayList<String>();
	
	public static void main(String[] args) {
		
		new Thread(new Runnable() {
			
			public void run() {
				
				synchronized (listA) {
					
					System.out.println("线程名:"+Thread.currentThread().getName()+"获得listA锁");
					
					synchronized (listB) {
						System.out.println("线程名:"+Thread.currentThread().getName()+"获得listB锁");
						try {
							Thread.sleep(3000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
					
				}
				
			}
		}).start();
		
		new Thread(new Runnable() {
			public void run() {
				
				synchronized (listB) {
					
					System.out.println("线程名:"+Thread.currentThread().getName()+"获得listA锁");
					
					/*synchronized (listB) {
						System.out.println("线程名:"+Thread.currentThread().getName()+"获得listB锁");
						try {
							Thread.sleep(3000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}*/
					
				}
				
			}
		}).start();

	}

}
