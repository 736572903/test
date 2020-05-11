package synchronizedTest;

public class TestSynchronized implements Runnable {

        static TestSynchronized instance=new TestSynchronized();
        static int i=0;
        @Override
        public void run() {
            //省略其他耗时操作....
            //使用同步代码块对变量i进行同步操作,锁对象为instance
            synchronized(instance){
                System.out.println(Thread.currentThread().getName());
                for(int j=0;j<100;j++){
                    i++;
                }
                System.out.println(Thread.currentThread().getName() + " jieshu ");
            }
        }
        public static void main(String[] args) throws InterruptedException {
            Thread t1=new Thread(instance);
            Thread t2=new Thread(instance);
            t1.start();
            t2.start();
//            t1.join();
//            t2.join();
            System.out.println(i);
        }
    }
