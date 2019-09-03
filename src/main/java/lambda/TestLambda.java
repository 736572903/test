package lambda;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.LongSummaryStatistics;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Test;

import com.alibaba.fastjson.JSONObject;

import javassist.expr.NewArray;

/**
 * Lambda 表达式的基础语法：Java8中引入了一个新的操作符 "->" 该操作符称为箭头操作符或 Lambda 操作符
 * Lambda 是一个匿名函数，我们可以把 Lambda 表达式理解为是一段可以传递的代码（将代码像数据一样进行传递）。
 * 可以写出更简洁、更灵活的代码。作为一种更紧凑的代码风格，使Java的语言表达能力得到了提升。
 */
public class TestLambda {
	
	@Test
	public void test1(){
		//Java内置的四大函数式:
		Consumer<String> consumer = e->System.out.println("ghijhkhi"+e);
//	    consumer.accept("woojopj");
//	    
//	    Supplier<String> supplier = ()->"532323".substring(0, 2);
//        System.out.println(supplier.get());
//        
//        Function<String, String> function = (x)->x.substring(0, 2);
//        System.out.println(function.apply("我是中国人"));
//        
//        Predicate<String> predicate = (x)->x.length()>5;
//        System.out.println(predicate.test("12345678"));
        
      Comparator<Integer> comparator = (x,y)->Integer.compare(x, y);
      int compare = comparator.compare(1, 5);
      System.out.println("compare:"+compare);
//        
//        Function<Date, String> function2 = (x)->{
//        	return new SimpleDateFormat("yyyyMMdd").format(x);
//        };
//        System.out.println(function2.apply(new Date()));
        
        List<String> features = Arrays.asList("Lambdas", "Default Method", "Stream API", "Date and Time API");
        features.forEach(n -> System.out.println(n));
        features.stream().map((cost) -> cost + "aaa").forEach(System.out::println);
        
        
//		List<Double> list2 = null;
//		Double a = Optional.ofNullable(list2)
//					.map(list1 -> list1.get(0)).orElse(0.00);
//		System.out.println(a);
//		
//		//对集合进行加减操作
//		List<Double> list = new ArrayList<>();
//		list.add(0.71);
//		list.add(0.22);
//		list.add(0.44);
//		
//		Function<List<Double>, DoubleSummaryStatistics> function = (item)->item.stream().mapToDouble((data)->data).summaryStatistics();
//		
//		DoubleSummaryStatistics sum = Optional.ofNullable(list).map(item -> function.apply(item)).orElse(new DoubleSummaryStatistics());
//		
//		System.out.println(sum.getSum());
		
		List<Long> list3 = new ArrayList<>();
		list3.add(1L);
		list3.add(2L);
		list3.add(3L);
		list3.add(4L);
		list3.add(5L);
		
		//对数据+1
		Stream<Long> stream = list3.stream().map((item) -> item+1);
		System.out.println(String.format("第一个元素：%d", stream.collect(Collectors.toList()).get(0)));
		
		//过滤
		stream = list3.stream().filter((item) -> item > 3);
		System.out.println(String.format("大于3的个数：%d", stream.collect(Collectors.toList()).size()));
		
		list3.stream().filter((item) -> item != null).map((item) -> item+1).collect(Collectors.toList());
		
		Optional.ofNullable(list3).orElse(new ArrayList<Long>()).stream().filter(item -> item != null && item > 50).collect(Collectors.toList());
		
		Function<List<Long>, LongSummaryStatistics> function = (item)->item.stream().mapToLong((data)->data).summaryStatistics();
		
		LongSummaryStatistics longSum = Optional.ofNullable(list3).map(item -> function.apply(item)).orElse(new LongSummaryStatistics());
		
		System.out.println(String.format("list的总和：%d", longSum.getSum()));
		
		try {
			System.out.println(String.format("list的平均值：%s", longSum.getAverage()+""));
			System.out.println(String.format("list的最大值：%d", longSum.getMax()));
			System.out.println(String.format("list的最小值：%d", longSum.getMin()));
			System.out.println(String.format("list的个数：%d", longSum.getCount()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
//		Runnable runnable = new Runnable() {
//			@Override
//			public void run() {
//				
//			}
//		};
//		runnable.run();
//		
//		Runnable run1 = () -> System.out.println("aaaa");
//		run1.run();
	}

}
