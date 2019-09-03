package thread.deadLock;

public class DeadThreadTest1 {

	public static void main(String[] args) {
		
		Object a = new Object();
		
		Object b = new Object();
		
		/**
		 * a向b转账，b向a转账，
		 * 获得a的锁，准备获取b的锁
		 * 获得b的锁，准备获取a的锁
		 */
		
		DeadThread dead1 = new DeadThread("first", a, b);
		
		DeadThread dead2 = new DeadThread("second", b, a);
		
		new Thread(dead1).start();
		
		new Thread(dead2).start();

	}

}
