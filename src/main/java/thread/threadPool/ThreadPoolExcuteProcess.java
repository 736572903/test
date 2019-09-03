package thread.threadPool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


/**
 * 理解线程池的执行过程
 * @author RYX
 *
 */
public class ThreadPoolExcuteProcess {
	
	public static ExecutorService fixedThreadPool = Executors.newFixedThreadPool(5);
	
	public static ThreadPoolExecutor excutor = 
			new ThreadPoolExecutor(5, 10, 20, TimeUnit.SECONDS, new LinkedBlockingDeque<Runnable>(2));
	
	
	public static void main(String[] args) throws Exception {
		
		for(int i=0;i<20;i++){
			Thread.sleep(2000);
			excutor.execute(new RunnableTest(excutor,i));
		}
		
		
	}

	
	static class RunnableTest implements Runnable {
		
		private ThreadPoolExecutor excutor = null;
		private int i=0;
		
		public RunnableTest(ThreadPoolExecutor excutor, int i) {
			this.excutor = excutor;
			this.i = i;
		}

		public void run() {
			
			int queueSize = excutor.getQueue().size();
			
			int threadSize = excutor.getActiveCount();
			
			
			System.out.println("i:"+i+",线程名："+Thread.currentThread().getName()+",线程数："+threadSize+",队列数："+queueSize);
			
			try {
				Thread.sleep(80000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}