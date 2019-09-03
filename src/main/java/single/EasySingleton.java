package single;

import entity.Student;

/**
 * 枚举单例
 *
 */
public enum EasySingleton {
	INSTANCE;
	
	private Student student = null;
	
    //私有化枚举的构造函数
    private EasySingleton(){
    	System.out.println("私有化枚举的构造函数");
    	student = new Student();
    }
	
	public Student getSingleton(){
		return student;
    }
}
