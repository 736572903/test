 package test;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.alibaba.fastjson.JSONObject;

public class Test1 {
	 
	private static final Object Double = null;

	private String content;
	
	private String title;
	
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	@Override
	public String toString() {
		return String.format("body=%s;title=%s", content, title);
	}

	public static void main(String[] args) {
		
		Map<String, String> map = new HashMap<String, String>();
		
		System.out.println("111"+map.get("bbb"));
		
//		double a = 3;
//		double b = 3.3;
//		double h = 0.03;
//		System.out.println(a+b+h);
//		
//		double result = 1.0 - 0.9; 
//		System.out.println(result);
//		
		Object a = 500.00;
		
		Double eee = (Double)a;
		
		Double aaa = 0.7;
		Double bbb = 0.2;
		Double ccc = 0.4;
		System.out.println(0.7+0.2+0.4);
		System.out.println(aaa+bbb+ccc);
//		
//		 a = 3;
//		 b = 0.03;
//		double c = 0.03;
//
//		double d =  b + c;
//
//		System.out.println("first d:" + d);
		
		double d = 3333.09999;
		System.out.println(String.format("%.2f", d));
		
//		Integer a = 200;
//		Integer b = 200;
//		System.out.println(a==b);
//		
//		Boolean c = null;
//		
//		if(c){
//			System.out.println("aa");
//		}else{
//			System.out.println("bb");
//		}
		
//		List<String> list = new ArrayList<String>();
//		list.add("bbb");
//		
//		int count = Optional.ofNullable(list).map(bill -> bill.size()).orElse(0);
//		System.out.println(count);
		
//		int i = 89;
//		
//		int day = i/24;//取整数部分
//		int hour = i%24;//取余数
//		System.out.println(String.format("工作时间：%d天%d小时", day, hour));
		
//		Test1 t = new Test1();
//		t.setContent("content");
//		t.setTitle("title");
//		System.out.println(t.toString());
//		System.out.println(JSONObject.toJSONString(t));
		
//		Pattern p = Pattern.compile("ryx\\.(.*?)\\.com");
//		String a  = "ryx.44444.com";
//		
//		Matcher m = p.matcher(a);
//		
//		System.out.println(m.find());
		
		
//		float a = 23.67f;
//		System.out.println(a);
		
	}

}
