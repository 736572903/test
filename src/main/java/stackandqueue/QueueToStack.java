package stackandqueue;

import java.util.ArrayDeque;
import java.util.Queue;

/**
 * 两个对列实现一个栈
 * @author RYX
 *
 */
public class QueueToStack {
	
	static Queue<String> queue1 = new ArrayDeque<String>();
	static Queue<String> queue2 = new ArrayDeque<String>();

	public static void main(String[] args) {
		push("1");
		push("2");
		push("3");
		push("4");
		push("5");
		
		System.out.println(pop());
		System.out.println(pop());
		System.out.println(pop());
		System.out.println(pop());
		System.out.println(pop());
		push("6");
		push("7");
		push("8");
		push("9");
		push("10");
		System.out.println("---------------------------");
		System.out.println(pop());
		System.out.println(pop());
		System.out.println(pop());
		System.out.println(pop());
		System.out.println(pop());
	}
	
	public static void push(String name){
		
		if(queue1.size() ==0 && queue2.size() == 0){
			queue1.add(name);
			return;
		}
		if(queue1.size() > 0){
			queue1.add(name);
			return;
		}
		if(queue2.size() > 0){
			queue2.add(name);
			return;
		}
		
	}
	
	public static String pop(){
		
		if(queue1.size() ==0 && queue2.size() == 0){
			return null;
		}
		
		if(queue1.size() > 0){
			while(queue1.size() > 1){
				queue2.add(queue1.poll());
			}
			return queue1.poll();
		}
		
		if(queue2.size() > 0){
			while(queue2.size() > 1){
				queue1.add(queue2.poll());
			}
			return queue2.poll();
		}
		
		return null;
	}

}
