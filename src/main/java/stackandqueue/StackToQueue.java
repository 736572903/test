package stackandqueue;

import java.util.Stack;

/**
 * ����ջʵ�ֶ���
 *
 * @author RYX
 */
public class StackToQueue {

    static Stack<String> stack1 = new Stack<String>();

    static Stack<String> stack2 = new Stack<String>();

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
        System.out.println(pop());


    }

    public static void push(String name) {
        stack1.add(name);
    }

    public static String pop() {

        while (stack1.size() > 0) {
            String name = stack1.pop();
            stack2.add(name);
        }
        if (stack2.size() > 0) {
            return stack2.pop();
        }

        return null;

    }

}
