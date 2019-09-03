package single;

import entity.Student;

public class SingletonLan {
	
	public static volatile SingletonLan instance = null;
	
	private SingletonLan(){}
	
	//不安全
//	public static SingletonLan getInstence(){
//		if(instance == null){
//			instance = new SingletonLan();
//		}
//		return instance;
//		
//	}
	
	//双重校验锁
	/**
	 * 当线程A执行到" instance = new DoubleCheckLock();"
	 * 这一行，而线程B执行到外层"if (instance == null) "时，可能出现instance还未完成构造，
	 * 但是此时不为null,导致线程B获取到一个不完整的instance。
	 * 指令重排
	 * 
	 * 1.分配内存空间
	 * 2.内存空间创建一个实例
	 * 3.引用指向这个内存空间
	 * 
	 */
	public static SingletonLan getInstence(){
		if(instance == null){
			synchronized (SingletonLan.class) {
				if(instance == null){
					instance = new SingletonLan();
				}
			}
		}
		return instance;
		
	}

}
