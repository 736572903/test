package threadlocal.simpleDateFormat;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class SimpleDateThread implements Runnable {
	
	private String name;
	
	private SimpleDateFormat sdf;
	
	private int month;
	
	public SimpleDateThread(String name, SimpleDateFormat sdf, int month){
		this.name = name;
		this.sdf = sdf;
		this.month = month;
	}
	
	public SimpleDateThread(int month){
		this.month = month;
	}
	
//	public SimpleDateThread(String name, int month){
//		this.name = name;
//		this.month = month;
//	}
	
	
//	public void run() {
//		
//		Calendar calendar = Calendar.getInstance();
//		calendar.set(Calendar.MONTH, month);
//		sdf.setCalendar(calendar);
//		Date date = calendar.getTime();
//		System.out.println(Thread.currentThread().getName()+":"+sdf.format(date));
//		
//	}
	
	public void run() {
		
		SimpleDateFormat simpleDateFormat = SimpleDateFormatUtils.get();
		
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.MONTH, month);
		simpleDateFormat.setCalendar(calendar);
		Date date = calendar.getTime();
		System.out.println(Thread.currentThread().getName()+":"+simpleDateFormat.format(date));
		
	}

}
