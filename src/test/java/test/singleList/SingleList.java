package test.singleList;

/**
 * 倒序一个链表
 *
 */
public class SingleList {
	
	public Node first; // 定义一个头结点
    private int position = 0;// 节点的位置
	
	public SingleList() {
		this.first = null;
	}
	
	public void addHeadNode(int value){
		Node node = new Node(value);
		
		node.next = first;
		first = node;
		position ++;
	}

	public int getPosition() {
		return position;
	}

	public Node getFirst() {
		return first;
	}

	class Node{
		int value;
		Node next;
		
		public Node(int value, Node next) {
			this.value = value;
			this.next = next;
		}

		public Node(int value) {
			this.value = value;
		}

		public int getValue() {
			return value;
		}

		public void setValue(int value) {
			this.value = value;
		}
		
	}
	
	/**
	 * 遍历法 ，前一个节点和当前节点
	 * 1->2->3->4->5
	 * 1 <- 2 <- 3 
	 */
	Node reserve(Node node){
		
		//前一个节点
		Node prev = null;
		
		while(node != null){
			
			//下一个节点
			Node node2 = node.next;
			//将当前节点的next指向前一个
			node.next = prev;
			
			//重新设置前一个节点和当前节点，继续遍历
			prev = node;
			node = node2;
			
		}
		
		return prev;
	}
	
	/**
	 * 递归法，从最后一个开始变换指针
	 * 1->2->3->4->5
	 * 5
	 * 5->4
	 * 5->4->3
	 */
	Node reserve2(Node node){
		
		//递归终止条件
		if(node == null || node.next == null){
			return node;
		}
		
		//下一个节点
		Node node2 = node.next;
		
		Node newHead = reserve2(node2);
		
		//5.next = 4, 4.next = null
		node2.next = node;
		node.next = null;
		
		return newHead;
		
	}
	
	
	
	
}
