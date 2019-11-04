package collections.map.hashmap;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/**
 * @Auther: wenbochang
 * @Date: 2018/8/5 11
 */
public class HashMapNoSafe {
    /**
     * NUMBER = 10，表示十个线程执行put方法执行了十次
     * 也就是map中总共有10 * 10 = 100 条数据
     */
    public static final int NUMBER = 10;

    public static void main(String[] args) throws InterruptedException {

        //hashMap的put方法会导致数据丢失
        Map<String, String> map = new ConcurrentHashMap<String, String>(2);

        for (int i = 0; i < NUMBER; i++) {
            new Thread(new A(map)).start();
        }
        TimeUnit.SECONDS.sleep(5);
        int cnt = 0;
        for (Map.Entry<String, String> mp : map.entrySet()) {
            cnt++;
            System.out.println(mp.getKey() + "  " + mp.getValue());
        }
        // 观察cnt的值是否等于插入的数量，就可以判断数据是否丢失了
        System.out.println("cnt = " + cnt);
    }
}

class A implements Runnable {

    Map<String, String> map;

    public A(Map<String, String> map) {
        this.map = map;
    }

    public void run() {
        for (int i = 0; i < HashMapNoSafe.NUMBER; i++) {
            map.put(i + "  " + Thread.currentThread().getName(), "test");
        }
    }
}