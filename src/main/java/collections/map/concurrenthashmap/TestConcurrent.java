package collections.map.concurrenthashmap;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 
 * @author RYX
 * @date 2019年5月21日
 */
public class TestConcurrent {

	public static void main(String[] args) {
		
		testconcurrent();
		
		
		//https://sky-xin.iteye.com/blog/2431255
		// ConcurrentHashMap在jdk1.8和1.7中的区别

		ConcurrentHashMap<String, Object> concurrentHashMap = new ConcurrentHashMap<String, Object>(8);
		
		//jdk1.7
		//1.put方法作了两次hash，减少冲突碰撞;
		//2.使用 Segment分段锁，Segment实现了ReentrantLock,tryLock()获得锁
		//3.数组+链表+segment锁
		//JDK1.7版本锁的粒度是基于Segment的，包含多个HashEntry
		
		//jdk1.8
		//1.put方法作了两次hash（相比较hashmap）;
		//2.使用了synchronized关键字以及CAS，synchronized锁的是链表的头结点或者是红黑树的头结点
		//3.数组+链表+红黑树+synchronized锁
		//JDK1.8锁的粒度就是HashEntry（首节点）
		
		concurrentHashMap.put("a", "a");
		
		//https://www.cnblogs.com/keeya/p/9632958.html
		//为什么ConcurrentHashMap的读操作不需要加锁？
		
		/**
		 * 为了提高处理速度，处理器不会和主内存直接进行交互，而是先将主内存读取到内部缓存，处理器对内部缓存进行操作，但是内部缓存什么时候更新到主内存则不知道
		 * 因此，普通的共享变量不能保证可见性
		 * 
		 * validate
		 * 如果使用validate修饰共享变量，当处理器在对该变量进行写操作，写入内部缓存后会及时更新到主内存，
		 * 但是，就算更新到了主内存，其他处理器缓存的值还是旧的，使用的数据还是旧的数据
		 * 
		 * 为了保证各个处理器的缓存是一致的，都实现缓存一致性协议，当一个线程在对共享变量执行写操作时，会告知其他CPU该共享变量的缓存是无效的，
		 * 这时如果其他CPU使用该变量，则会先从主内存读取最新的数据到内部缓存，再进行操作
		 * 
		 * 总结下来：
		         第一：使用volatile关键字会强制将修改的值立即写入主存；
                                 第二：使用volatile关键字的话，当线程2进行修改时，会导致线程1的工作内存中缓存变量的缓存行无效（反映到硬件层的话，就是CPU的L1或者L2缓存中对应的缓存行无效）；
                                 第三：由于线程1的工作内存中缓存变量的缓存行无效，所以线程1再次读取变量的值时会去主存读取。
		 * 
		 */
		
		//volatile V val;
        //volatile Node<K,V> next;
		concurrentHashMap.get("a");
		
		concurrentHashMap.entrySet().iterator();
		
	}
	
	//concurrent的迭代器是弱一致性
	/**
	 * 
	 */
	private static void testconcurrent() {
		ConcurrentHashMap<String, String> map = new ConcurrentHashMap<String, String>(16);
        map.put("B", "B");
        map.put("C", "C");
        map.put("3", "3");
        Iterator<Map.Entry<String, String>> iterator = map.entrySet().iterator();
        while (iterator.hasNext()) {
            //不会打印出AA
        	map.put("A", "A");
            map.remove("B", "B");
            //此时迭代器已经取到B，再remove会对map生效，但是迭代器不生效
            Map.Entry<String, String> next = iterator.next();
            System.out.printf("key:%s, value:%s\n", next.getKey(), next.getValue());
        }
        System.out.println("map取到："+map.get("A"));
	}

}
