package list;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

public class TestList {

	public static void main(String[] args) {
		
		//查询快，删除增加涉及到数组移位，初始长度为10，扩容size*1.5,
		List<String> list = new ArrayList<String>();
		
		list.add("b");
		list.add("b");
		list.add("b");
		list.add("b");
		
		//iterator
		Iterator<String> it = list.iterator();
		
		while(it.hasNext()){
			it.next();
			it.remove();
		}
		System.out.println(list.size());
		
//		//增删快，链表
//		LinkedList<String> linkedList = new LinkedList<String>();
//		
//		linkedList.iterator();
//		
//		
//		Vector<String> vector = new Vector<String>();
//		vector.get(0);
	}

}
