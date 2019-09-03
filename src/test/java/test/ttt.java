package test;

public class ttt {

	public static void main(String[] args) {
		String content = "http://ww.baidu.com?content=eee";
//		content = content.replaceAll("(h)(.+)/$", "$1");
		content = content.replaceAll("(.)\\1+","$1");
		System.out.println(content);
	}

}
