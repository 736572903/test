package threadlocal.simpleDateFormat;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class SimpleDateFormatError {
	
	static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	//2019-01-09 10:17:03
	//2019-01-09 10:17:03
	public static void main(String[] args) {
		
		new Thread(new Runnable() {
			public void run() {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				Calendar calendar = Calendar.getInstance();
				calendar.set(Calendar.MONTH, 0);
				sdf.setCalendar(calendar);
				Date date = calendar.getTime();
				System.out.println(sdf.format(date));
			}
		}).start();
		
		new Thread(new Runnable() {
			public void run() {
				Calendar calendar = Calendar.getInstance();
				calendar.set(Calendar.MONTH, -10);
				sdf.setCalendar(calendar);
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				calendar = sdf.getCalendar();
				Date date = calendar.getTime();
				System.out.println(sdf.format(date));
			}
		}).start();
		
	}
	
	
	
	

}
