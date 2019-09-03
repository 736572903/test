package single;

import entity.Student;

public class Test {

	public static void main(String[] args) {
		Student a = EasySingleton.INSTANCE.getSingleton();
		Student b = EasySingleton.INSTANCE.getSingleton();
		System.out.println(a == b);
	}

}
