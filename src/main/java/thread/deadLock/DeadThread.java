package thread.deadLock;

public class DeadThread implements Runnable {

    private String name;

    private Object from;

    private Object to;

    public DeadThread(String name, Object from, Object to) {
        this.name = name;
        this.from = from;
        this.to = to;
    }

    public void run() {

        int inFrom = from.hashCode();

        int inTo = to.hashCode();

        //通过hash值比较，使锁的顺序在一个有序方向
        if (inFrom > inTo) {

            synchronized (from) {

                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                synchronized (to) {

                    System.out.println(Thread.currentThread().getName() + ":" + name);

                }

            }

        } else {

            synchronized (to) {

                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                synchronized (from) {

                    System.out.println(Thread.currentThread().getName() + ":" + name);

                }

            }


        }


//		下面容易发生死锁
//		synchronized (from) {
//			
//			try {
//				Thread.sleep(2000);
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
//			
//			synchronized (to) {
//				
//				System.out.println(Thread.currentThread().getName()+":"+name);
//				
//			}
//			
//		}


    }


}
