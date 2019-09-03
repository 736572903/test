package thread.waitNotify;

/**
 * 
 * 三个线程：线程1打印A
 * 		    线程2打印B
 * 		    线程3打印C
 * 要求：打印10次ABC
 *wait notify
 *
 *wait 在获取对象锁后，主动释放对象锁，同时本线程休眠，直到有其他线程调用对象的notify()方法唤醒该线程，才能继续获得对象锁，并继续执行
 *notify 唤醒对象锁，但有点注意的是，notify执行完后，并不是马上释放锁，而是在对应的synchronized{}执行完成后才释放锁
 *
 *sleep和wait的区别：
 *	1.sleep是Thread方法，thread.sleep;
 *	  wait是obj方法，obj.wait();
 *	2.sleep和wait都是释放CPU时间片，区别是，wait释放CPU时间片的同时会释放对象锁
 */
public class WaitNotify {
	

	public static void main(String[] args) throws InterruptedException {
		
		a();
		
//		Object a = new Object();
//		Object b = new Object();
//		Object c = new Object();
//		ThreadTest pa = new ThreadTest("A", c, a);//c睡眠,a唤醒
//		ThreadTest pb = new ThreadTest("B", a, b);//a睡眠,b唤醒
//		ThreadTest pc = new ThreadTest("C", b, c);//b睡眠,c唤醒
//
//		new Thread(pa).start();
//		Thread.sleep(100); // 确保按顺序A、B、C执行
//		new Thread(pb).start();
//		Thread.sleep(100);
//		new Thread(pc).start();
		
		
	}

	private static void a() {
		
		final Object a = new Object();
		final Object b = new Object();
		final Object c = new Object();
		
		
		new Thread(new Runnable() {
			public void run() {
				
				try {
					Thread.sleep(100);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
				
				for (int i = 0; i < 10; i++) {
					
					synchronized (c) {
						synchronized (a) {
								
								System.out.print("A");
								
								a.notify();
								
						}
						try {
							c.wait();//1.c睡眠  2.唤醒a
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						
					} 
				}
				
			}
		}).start();
		
		
		new Thread(new Runnable() {
			
			public void run() {
				try {
					Thread.sleep(200);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
				
				for (int i = 0; i < 10; i++) {
					synchronized (a) {
						synchronized (b) {
								
								System.out.print("B");
								
								b.notify();
								
						}
						try {
							a.wait();//1.a睡眠 2.唤醒b
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						
					} 
				}
				
				
			}
		}).start();
		
		
		new Thread(new Runnable() {
			public void run() {
				
				try {
					Thread.sleep(300);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
				
				
				for (int i = 0; i < 10; i++) {
					synchronized (b) {
						synchronized (c) {
								
								System.out.print("C");
								
								c.notify();
								
						}
						try {
							b.wait();//1.b睡眠 2.唤醒c
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						
					} 
				}
			}
		}).start();
		
	}

}
