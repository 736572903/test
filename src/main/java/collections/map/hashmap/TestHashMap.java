package collections.map.hashmap;

import java.util.HashMap;
import java.util.Iterator;

public class TestHashMap {
	
	//https://blog.csdn.net/changhangshi/article/details/82114727
	public static void main(String[] args) {
		
		//初始大小16，扩充因子0.75
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		map.put("a", "a");
		map.remove("a");
		
		//jdk1.7:数组+链表，put方法会导致闭环和数据丢失
		//jdk1.8:数组+链表+红黑树，put方法会导致数据丢失
		//如果链表的长度大于等于8，而且map的size大于等于64，则会转化成红黑树    基于长度很长的链表的遍历是一个很漫长的过程，所以使用红黑树
		
		System.out.println(map.size());
		
		map.put("b", "b");
		map.put("c", "c");
		map.put("d", "d");
		map.put("e", "e");
		
		map.entrySet();//里面使用了iterator方法迭代器
		map.keySet();//里面使用了iterator方法迭代器
		
		System.out.println("开始迭代器fast-fail-----------------");
		
		
		//只要实现了iterator就可以在不知道集合内部结构的情况下进行遍历，强一致性
		/**remove方法和next方法
		 * if (modCount != expectedModCount)
                throw new ConcurrentModificationException();
		 */
		Iterator<String> keys = map.keySet().iterator();
        while(keys.hasNext()){
            String i = keys.next();
            if("b".equals(i)){
                keys.remove(); 
//            	map.put("f", "f");
//            	map.remove(i);//如果只使用这个remove，则会抛出异常，fast-fail，如果迭代的对象发生改变，则会抛出异常ConcurrentModificationException 
            }
        }
        
		System.out.println("map.size:"+map.size());
	}

}
