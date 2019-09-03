package test;

/**
 * 类加载过程
 * 1.当需要使用一个类的时候，先去找到该类的.class文件，将其的详细信息加载到元空间，并分配内存（堆里创建对象）
 * 2.校验被加载类的合法性，满足java虚拟机的规范
 * 3.为类的静态变量赋予默认值
 * 4.虚拟机会把所以类名，变量名，方法名这些符号引用转成直接引用
 * 5.为类的静态赋值，加载静态代码块静态方法，自上而下加载，父类若未初始化，则会先初始化父类
 * 
 * 
 * 加载，就是类加载器将类的.class文件的二进制读入到内存中，并且创建一个java.lang.Class对象。这个class对象提供了访问类中数据的接口。
         验证，确保Class文件的字节流符合当前虚拟机的要求。
         准备，给类变量分配内存，并设置初始值。
         解析，把常量池中的符号引用换为直接引用。符号引用只是符合虚拟机规范的格式，直接引用才是指向目标的指针。
         初始化，调用<client>方法给静态变量赋值，执行静态代码块。在此之前会先执行父类的<client>方法。如果类中没有静态变量或静态代码块，编译器可以不生成<client>方法。

 * 类加载的 双亲委派机制
 * 某个特定的类加载器在接受到加载类的请求时，会优先调用父类加载器进行加载，依次递归，如果父类加载器无法加载，自己才去加载
 * 
 * 这就保证了类的唯一性，因为不同的加载器加载出来的类是不同的。也保证了java的运行机制，因为假如你自己编写了一个java.lang.Object类想要用，
 * 这时java程序出现了两个不同的Object类，程序将会混乱。其实这时无论哪个类加载器都会把这个任务交给启动类加载器去完成，
 * 然后它加载自己路径下的Object类保证了java程序中只有一个Object类。
 * 
 * 虚拟机只有在两个类的类名相同且加载这个类的加载器相同的情况下才判断是一个类
 * 如果没有双亲委派机制，不同的加载器加载同一个类，则会认为是不同的类
 * 
 * 
 */
public class TestClassloader {

	public static void main(String[] args) {
		try {
			
			//系统类加载器sun.misc.Launcher$AppClassLoader
			System.out.println(ClassLoader.getSystemClassLoader());
			
			//拓展类加载器(Extension)sun.misc.Launcher$ExtClassLoader
			System.out.println(ClassLoader.getSystemClassLoader().getParent());
			System.out.println(ClassLoader.getSystemClassLoader().getParent().getParent());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
