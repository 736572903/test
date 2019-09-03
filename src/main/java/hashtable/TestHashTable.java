package hashtable;

import java.util.Hashtable;

public class TestHashTable {

	public static void main(String[] args) {
		
		
		Hashtable<String, Object> hashTable = new Hashtable<String, Object>();
		
		//方法synchronized关键字修饰，和hashMap区别，一个线程安全一个非安全，且key，value均不能为null
		hashTable.put("key", "value");
		
		//方法synchronized关键字修饰
		hashTable.get("key");

	}

}
