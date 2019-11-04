package thread.waitNotify;

public class zTest {

    //4线程ABCD
    public static void main(String[] args) throws InterruptedException {

        final Object a = new Object();
        final Object b = new Object();
        final Object c = new Object();
        final Object d = new Object();


        new Thread(new Runnable() {
            public void run() {

                for (int i = 0; i < 10; i++) {
                    synchronized (d) {

                        synchronized (a) {

                            System.out.print("A");

                            a.notify();//1.d睡眠

                        }

                        try {
                            d.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                    }
                }
            }
        }).start();

        Thread.sleep(500);

        new Thread(new Runnable() {
            public void run() {

                for (int i = 0; i < 10; i++) {
                    synchronized (a) {

                        synchronized (b) {

                            System.out.print("B");
                            b.notify();//1.a睡眠

                        }

                        try {
                            a.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                    }
                }


            }
        }).start();

        Thread.sleep(500);

        new Thread(new Runnable() {
            public void run() {

                for (int i = 0; i < 10; i++) {
                    synchronized (b) {

                        synchronized (c) {

                            System.out.print("C");
                            c.notify();//1.b睡眠
                        }

                        try {
                            b.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                    }
                }
            }
        }).start();


        Thread.sleep(500);

        new Thread(new Runnable() {
            public void run() {

                for (int i = 0; i < 10; i++) {
                    synchronized (c) {

                        synchronized (d) {

                            System.out.print("D");
                            d.notify();//1.c睡眠-->唤醒d 循环开始
                        }

                        try {
                            c.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                    }
                }


            }
        }).start();

    }

}
