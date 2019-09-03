package thread.waitNotify;

public class ThreadTest implements Runnable{

		
		private String name;
		private Object prev;
		private Object self;

		ThreadTest(String name, Object prev, Object self) {
			this.name = name;
			this.prev = prev;
			this.self = self;
		}

		public void run() {
			
			for (int i = 0; i < 10; i++) {
				
				synchronized (prev) {
					synchronized (self) {
							
							System.out.print(name);
							
							self.notify();
							
					}
					try {
						prev.wait();//1.c睡眠  2.唤醒a
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					
				} 
			}
			
//			int count = 10;
//			while (count > 0) {
//				synchronized (prev) {
//					synchronized (self) {
//						System.out.print(name);
//						count--;
//
//						self.notify();
//					}
//					try {
//						prev.wait();
//					} catch (InterruptedException e) {
//						e.printStackTrace();
//					}
//				}
//
//			}
		}


}
