package list;

import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

public class TestLinkedList {

    public static void main(String[] args) {

        List<Integer> list = new LinkedList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        list.add(5);

        //low方法实现,add(index, value);
        list = descListByLinked(list);
        System.out.println(String.format("使用新建链表实现翻转%s", list.toString()));

        list = descByStack(list);
        System.out.println(String.format("使用栈实现翻转%s", list.toString()));

        list = descByNode(list);

    }

    private static List<Integer> descByNode(List<Integer> list) {
        return null;
    }

    private static List<Integer> descByStack(List<Integer> list) {
        Stack<Integer> stack = new Stack<Integer>();
        List<Integer> newList = new LinkedList<>();

        for (int i = 0; i < list.size(); i++) {
            stack.add(list.get(i));
        }

        for (int i = 0; i < list.size(); i++) {
            newList.add(stack.pop());
        }

        return newList;
    }

    private static List<Integer> descListByLinked(List<Integer> list) {
        List<Integer> newList = new LinkedList<>();
        for (int i = 0; i < list.size(); i++) {
            newList.add(0, list.get(i));
        }
        return newList;
    }

}
