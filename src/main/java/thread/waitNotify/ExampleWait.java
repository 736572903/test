package thread.waitNotify;

public class ExampleWait implements Runnable {

    private String name;
    private Object prev;
    private Object self;

    private ExampleWait(String name, Object prev, Object self) {
        this.name = name;
        this.prev = prev;
        this.self = self;
    }

    public void run() {
        int count = 10;
        while (count > 0) {
            synchronized (prev) {
                synchronized (self) {
                    System.out.print(name);
                    count--;

                    self.notify();
                }
                try {
                    prev.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    public static void main(String[] args) throws Exception {
        Object a = new Object();
        Object b = new Object();
        Object c = new Object();
        ExampleWait pa = new ExampleWait("A", c, a);//c睡眠,a唤醒
        ExampleWait pb = new ExampleWait("B", a, b);//a睡眠,b唤醒
        ExampleWait pc = new ExampleWait("C", b, c);//b睡眠,c唤醒

        new Thread(pa).start();
        Thread.sleep(1000); // 确保按顺序A、B、C执行
        new Thread(pb).start();
        Thread.sleep(1000);
        new Thread(pc).start();
        Thread.sleep(1000);
    }
}
