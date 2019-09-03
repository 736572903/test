package threadlocal.simpleDateFormat;

import java.text.SimpleDateFormat;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SimpleDateFormatUtils {
	
	static ThreadLocal<SimpleDateFormat> threadLocal = new ThreadLocal<SimpleDateFormat>(){
		protected SimpleDateFormat initialValue() {
			return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		};
	};
	
	public static SimpleDateFormat get(){
		return threadLocal.get();
	}
	
	public static void set(){
		if(threadLocal.get() == null){
			threadLocal.set(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
		}
	}

	public static void main(String[] args) {
		
		
		ExecutorService newFixedThreadPool = Executors.newFixedThreadPool(10);
		
		for(int i=0;i<10;i++){
			
			//这个获取的是主线程的SimpleDateFormatUtils.get()，所以有问题，必须在线程里面进行获取
//			newFixedThreadPool.execute(new SimpleDateThread("", SimpleDateFormatUtils.get(), i));
			
			newFixedThreadPool.execute(new SimpleDateThread(i));
			
//			Calendar calendar = Calendar.getInstance();
//			calendar.set(Calendar.MONTH, i);
//			SimpleDateFormatUtils.get().setCalendar(calendar);
//			Date date = calendar.getTime();
//			System.out.println(Thread.currentThread().getName()+":"+SimpleDateFormatUtils.get().format(date));
			
		}

	}

}
