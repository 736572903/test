package thread;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * 试验 Java 的 Future 用法
 * https://www.oschina.net/question/54100_83333
 */
public class TestFuture {

    public static class Task implements Callable<String> {
        public String call() throws Exception {
            String tid = String.valueOf(Thread.currentThread().getId());
            System.out.printf("Thread#%s : in call\n", tid);
            return tid;
        }
    }

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        List<Future<String>> results = new ArrayList<Future<String>>();
        List<Object> list = new ArrayList<Object>();
        ExecutorService es = Executors.newFixedThreadPool(100);
        long time1 = System.currentTimeMillis();
        for(int i=0; i<100;i++){
            results.add(es.submit(new Task()));
        	
//        	list.add(es.submit(new Task()).get());
        }
//        System.out.println(list.get(10)+"");
        System.out.println(System.currentTimeMillis() - time1);
        //调用get方法，当前线程就开始阻塞，这个地方就是main线程开始阻塞，直到获得线程的返回值
        for(Future<String> res : results){
            System.out.println(res.get());
        }
        System.out.println("结束");
    }

}