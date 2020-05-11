package lock;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

/**
 * 功能描述: 不可重入锁
 * https://blog.csdn.net/rickiyeat/article/details/78314451
 * 可重入锁：可重复可递归调用的锁，在外层使用锁之后，在内层仍然可以使用，并且不发生死锁（前提得是同一个对象或者class），这样的锁就叫做可重入锁。
 * ReentrantLock和synchronized都是可重入锁
 * 不可重入锁：不可递归调用，会出现死锁
 *
 * @author wanghouji
 * @date 2019/11/25 11:52 上午
 */
public class UnreentrantLock {

    private static AtomicReference<Thread> owner = new AtomicReference<Thread>();

    private static AtomicInteger atomicInteger = new AtomicInteger();

    public static void lock() {
        Thread current = Thread.currentThread();
        // 这句是很经典的“自旋”语法，AtomicInteger中也有
        for (; ; ) {
            if (owner.compareAndSet(null, current)) {
                return;
            }
        }
    }

    public static void unlock() {
        Thread current = Thread.currentThread();
        owner.compareAndSet(current, null);
    }


    public static void main(String[] args) {
        lock();
        lock();
    }
}
