package zookeeper.lock;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessLock;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.utils.CloseableUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class DistributedLockDemo {

    /**
     * Zookeeper锁的节点，分布式锁的相关操作都是在这个节点下面执行
     */
    private final String lockPath = "/distributed-lock";

    /**
     * Zookeeper服务器地址
     */
    private String connectAddress;

    /**
     * Curator重试策略
     */
    private RetryPolicy retryPolicy;

    /**
     * Curator客户端1
     */
    private CuratorFramework client1;

    /**
     * Curator客户端2
     */
    private CuratorFramework client2;

    /**
     */
    final CountDownLatch countDownLatch = new CountDownLatch(2);

    @Before
    public void init() {
        connectAddress = "10.41.32.133:2181";
        // 重试策略,初始休眠时间为1000ms,最大重试次数为3
        retryPolicy = new ExponentialBackoffRetry(1000, 3);
        /**
         * 创建一个客户端，6000ms为session超时时间，15000ms为连接超时时间
         * sessionTimeoutMs 如果在这个时间之内重新建立连接成功，则一切恢复正常，临时节点和watch不会发生丢失
         * 如果在这个时间之后连接上zkSserver,则zkServer会返回Session Expired,此时session已经失效，临时节点和watch丢失
         */
        client1 = CuratorFrameworkFactory.newClient(connectAddress, 6000, 15000, retryPolicy);
        client2 = CuratorFrameworkFactory.newClient(connectAddress, 6000, 15000, retryPolicy);
        // 创建会话
        client1.start();
        client2.start();
    }

    @After
    public void close() {
        CloseableUtils.closeQuietly(client1);
        CloseableUtils.closeQuietly(client2);
    }

    /**
     * 共享可重入锁（InterProcessMutex）
     * 此锁可以重入，但是重入几次需要释放几次
     */
    @Test
    public void shareReentrantLock() throws Exception {
        // 模拟两个
        final InterProcessLock lock1 = new InterProcessMutex(client1, lockPath);
        final InterProcessLock lock2 = new InterProcessMutex(client2, lockPath);

        ThreadPoolExecutor executor =
                new ThreadPoolExecutor(2, 2, 20, TimeUnit.SECONDS, new LinkedBlockingDeque<Runnable>(2));

        executor.execute(new ThreadLock(lock1));
        executor.execute(new ThreadLock(lock2));

        countDownLatch.await();

    }

    class ThreadLock implements Runnable {

        private InterProcessLock lock;

        public ThreadLock(InterProcessLock lock) {
            this.lock = lock;
        }

        @Override
        public void run() {
            try {
                boolean getLock = lock.acquire(3, TimeUnit.SECONDS);
                if (!getLock) {
                    System.out.println(String.format("%s没有获得锁", Thread.currentThread().getName()));
                    return;
                }
                System.out.println(String.format("%s获得锁", Thread.currentThread().getName()));

                // 测试锁重入
                lock.acquire();
                System.out.println(String.format("%s再次获得锁", Thread.currentThread().getName()));

                Thread.sleep(5000);

                lock.release();
                System.out.println(String.format("%s释放锁", Thread.currentThread().getName()));

                lock.release();
                System.out.println(String.format("%s再次释放锁", Thread.currentThread().getName()));

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                countDownLatch.countDown();
            }
        }
    }

}
