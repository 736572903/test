package thread.threadImp;

public class ThreadImp implements Runnable {

    private String name;

    private int age;

    public ThreadImp() {

    }

    public ThreadImp(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public void run() {
        Thread thread = Thread.currentThread();
        System.out.println("threadName:" + thread.getName() + ",name:" + name + ",age:" + age);

    }

}
