package thread.threadPool;

import static java.util.concurrent.TimeUnit.NANOSECONDS;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.ThreadPoolExecutor.AbortPolicy;


/**
 * 线程池的好处
 * 1.重用性，减少频繁的创建线程和销毁线程上所花的时间，降低系统资源的消耗
 * 2.控制并发数，如果不使用线程池，可能会由于大量创建线程而导致系统资源的大量消耗
 * 3.便于对线程进行管理
 * <p>
 * 线程池的问题
 * 1.死锁，只要是多线程都存在的问题，
 * 2.如果线程池线程数量过多，会消耗性能（比如fixedThreadPool,核心线程数即使空闲也不会被回收）
 * 3.线程泄露
 * ①对于工作线程数目固定的线程池，如果工作线程在执行任务时抛出了RunTimeException或者error，并且这些异常和error没有被捕获，那么这个工作线程就会终止，使线程池永久丢失该线程;
 * ②如果工作线程在执行任务时被阻塞（例如一直在等待用户输入，用户迟迟不输入），这个线程一直被阻塞，名存实亡，如果线程池里的线程都阻塞了，则线程池不会再新建线程了
 * 4.任务过载，如果队列中还有大量等待线程，则会消耗太多系统资源
 * <p>
 * <p>
 * 线程池中的执行规则:
 * 1.任务进来，如果当前线程数小于核心线程数大小，则新建核心线程执行任务
 * 2.任务进来，如果当前线程数大于核心线程数大小，则将该任务放入阻塞队列
 * 3.任务进来，如果阻塞队列已满，当前线程数小于最大线程数，则新建非核心线程
 * 4.任务进来，如果阻塞队列已满，而且当前线程数等于最大线程数，则执行策略handler，抛弃该任务或者抛出异常或者由提交该任务的线程进行执行
 */
public class ThreadPool {

    //创建一个定长线程池，可控制现成最大并发数，超出的线程会在队列中等待
    private static ExecutorService fixedThreadPool = Executors.newFixedThreadPool(10);//最小10最大10,阻塞队列的最大值为2的31次方-1
    //new ThreadPoolExecutor(nThreads, nThreads, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>()

    //创建一个可缓存线程池，如果线程池长度超过处理需要，会自动回收线程，若无可回收则新建线程
    private static ExecutorService cachedThreadPool = Executors.newCachedThreadPool();//最小0最大2的31次方-1 60秒
    //new ThreadPoolExecutor(0, Integer.MAX_VALUE, 60L, TimeUnit.SECONDS, new SynchronousQueue<Runnable>()

    //创建一个定长的线程池，支持定时和周期性任务执行
    private static ScheduledExecutorService scheduledThreadPool = Executors.newScheduledThreadPool(5);//最大2的31次方-1 阻塞队列初始值为16，每次扩大50% int newCapacity = oldCapacity + (oldCapacity >> 1); // grow 50%
    //(corePoolSize, Integer.MAX_VALUE, 0, NANOSECONDS, new DelayedWorkQueue())

    public static void main(String[] args) {

        //int corePoolSize 线程池的基本大小（未有任务进来时的线程数）线程池里面保持运行的线程数 ，即使它们处于空闲状态
        //int maximumPoolSize 线程池的最大线程数,超过最大线程数时会采取handler策略
        //long keepAliveTime 当前线程数大于corePoolSize时，非核心线程闲置超过这个时间就会被回收
        //TimeUnit unit 时间单位
        //BlockingQueue<Runnable> workQueue 缓存需要执行的异步任务队列
        new ThreadPoolExecutor(5, 5, 60, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>());
        new ThreadPoolExecutor(5, 5, 60, TimeUnit.SECONDS, new SynchronousQueue<Runnable>());
        //ThreadFactory threadFactory, RejectedExecutionHandler handler
        //ThreadFactory threadFactory 新建线程工厂
        /*RejectedExecutionHandler handler 拒绝策略，当workQueue满了的时候，而且线程池也满了的时候，会采取下面策略
         * 		DiscardPolicy:抛弃当前任务
         * 		DiscardOldestPolicy:抛弃最旧的任务
         * 		CallerRunsPolicy:由向线程池提交任务的线程来执行该任务
         * 		AbortPolicy:抛出RejectedExecutionException异常
         */
        new ThreadPoolExecutor(5, 5, 60, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>(), Executors.defaultThreadFactory(), new AbortPolicy());


    }

}
