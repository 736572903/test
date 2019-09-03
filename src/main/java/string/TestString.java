package string;

public class TestString {

	public static void main(String[] args) {
		
		//字符串的拼接操作，从JDK8 开始，会自动被编译成StringBuilder
		String st = "aaa";
		String str = st+"bb";
//		String b = str.intern();
		String c = "aaabb";
		System.out.println(str == c);
		
		//使用Synchronized关键字保证线程安全
		StringBuffer buffer = new StringBuffer("aa");
		
		//与StringBuffer用法相同，线程不安全
		StringBuilder builder = new StringBuilder("aa");
		
		
	}

}
