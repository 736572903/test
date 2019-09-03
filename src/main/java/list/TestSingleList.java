package list;

import list.SingleList.Node;

public class TestSingleList {
	
	public static void main(String[] args) {
		
		SingleList list = new SingleList();
		
		list.addHeadNode(5);
		list.addHeadNode(4);
		list.addHeadNode(3);
		list.addHeadNode(2);
		list.addHeadNode(1);
		
		System.out.println(String.format("list的个数：%d", list.getPosition()));
		System.out.println(String.format("list的第一个的值：%d", list.getFirst().getValue()));
		
		Node node = list.reserve(list.getFirst());
		System.out.println(String.format("遍历法的第一个的值：%d", node.value));
		
		node = list.reserve2(list.getFirst());
		System.out.println(String.format("递归法的第一个的值：%d", node.value));
	}
	
}
