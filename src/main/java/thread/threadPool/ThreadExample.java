package thread.threadPool;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ThreadExample implements Runnable {

    private String name;

    private Object object;

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public ThreadExample(String name, Object object) {
        this.name = name;
        this.object = object;
    }

    public void run() {

        for (int i = 0; i < 5; i++) {
//			try {
//				Thread.sleep(100);
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
            System.out.println("name:" + Thread.currentThread().getName() + ",i:" + i + ",time:" + sdf.format(new Date()));
        }

    }

}
