package thread.threadPool;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ThreadPoolTest {
	
	//固定大小------------队列是无限的，来了就进队列，时间是没用的
	private static ExecutorService fixedThreadPool = Executors.newFixedThreadPool(5);
	
	//缓存----队列不存-----线程是无限的，来了就创建线程
	private static ExecutorService cachaeThreadPool = Executors.newCachedThreadPool();
	
	//单线程-------------队列是无限的，来了就进队列，时间没用
	private static ExecutorService singleThreadExecutor = Executors.newSingleThreadExecutor();
	
	//定时周期性执行--------线程是无限的，来了就创建
	private static ScheduledExecutorService scheduledThreadPool = Executors.newScheduledThreadPool(5);

	public static void main(String[] args) {
		
		
		for(int i=0;i<10;i++){
			fixedThreadPool.execute(new ThreadExample("", null));
		}
		
//		for(int i=0;i<10000;i++){
//			cachaeThreadPool.execute(new ThreadExample("", null));
//		}
		
//		for(int i=0;i<10;i++){
//			singleThreadExecutor.execute(new ThreadExample("", null));
//		}
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		System.out.println(sdf.format(new Date()));
		//延迟1秒后每两秒执行一次
		scheduledThreadPool.scheduleWithFixedDelay(new ThreadExample("", null), 1, 2, TimeUnit.SECONDS);
		
	}

}
