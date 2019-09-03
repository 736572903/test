package threadlocal.basicExample;

public class ExampleError {
	
	static int count = 0;

	public static void main(String[] args) {
		
		//---------------多线程会互相影响-------------------------
		new Thread(new Runnable() {
			public void run() {
				for(int i=0;i<5;i++){
					count++;
					System.out.println(Thread.currentThread().getName()+":"+count);
				}
			}
		}).start();
		
		new Thread(new Runnable() {
			public void run() {
				for(int i=0;i<5;i++){
					count++;
					System.out.println(Thread.currentThread().getName()+":"+count);
				}
			}
		}).start();
		
	}

}
