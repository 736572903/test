package thread.threadExtend;

public class ThreadExtend extends Thread {

    private String name;

    private int age;

    public ThreadExtend() {
        super();
    }

    public ThreadExtend(String name, int age) {

        this.name = name;
        this.age = age;

    }

    @Override
    public void run() {
        Thread thread = Thread.currentThread();
        System.out.println("thread:" + thread.getName() + ",name:" + name + ",age:" + age);
    }

}
