package threadlocal.basicExample;

public class ExampleErrorRepair {
	
	static ThreadLocal<Integer> threadLocal = new ThreadLocal<Integer>(){
		protected Integer initialValue() {
			return 0;
		};
	};
	
	private static Integer get(){
		return threadLocal.get();
	}
	
	public static void main(String[] args) {
		new Thread(new Runnable() {
			public void run() {
				int count = ExampleErrorRepair.get().intValue();
				for(int i=0;i<5;i++){
					count ++;
					System.out.println(Thread.currentThread().getName()+":"+count);
				}
			}
		}).start();
		
		new Thread(new Runnable() {
			public void run() {
				int count = ExampleErrorRepair.get().intValue();
				for(int i=0;i<5;i++){
					count ++;
					System.out.println(Thread.currentThread().getName()+":"+count);
				}
			}
		}).start();
		
	}
	
	
	
}
