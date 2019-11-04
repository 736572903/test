package thread.threadExtend;

public class ThreadExtendTest {

    public static void main(String[] args) {

        String name = "张三";
        int age = 18;

        for (int i = 0; i < 50; i++) {

            ThreadExtend threadExtend = new ThreadExtend(name, age);
            threadExtend.start();
//			threadExtend.run();//main主线程执行
        }


    }

}
