package clone;

public class TestClone {

	public static void main(String[] args) throws CloneNotSupportedException {
		
		Person p = new Person("小明",11,123);
//		Person p1 = p;
		/**
		 * 两者打印的结果一样，说明两个的引用均为同一个对象，并没有创建出一个新的对象，这个称为引用的拷贝复制
		 */
//		System.out.println(p);//clone.Person@6d06d69c
//		System.out.println(p1);//clone.Person@6d06d69c
		
		/**
		 * 加入toString之后，p修改了，p1也受到影响，说明两者引用的同一对象
		 */
//		System.out.println(p);//Person [name=小明, age=11, code=123]
//		p.setCode(888);
//		System.out.println(p1);//Person [name=小明, age=11, code=888]
		
		/**
		 * 实现Cloneable接口，实现对象拷贝的类，必须实现Cloneable接口，并覆写clone()方法。
		 */
		
		Person p2 = (Person) p.clone();
		p2.setName("小明克隆人");
		p2.setAge(12);
		p2.setCode(12345);
		/**
		 * Person [name=小明, age=11, code=123]
		   Person [name=小明克隆人, age=12, code=12345]
		 */
//		System.out.println(p);
//		System.out.println(p2);
		
		/**
		 * 该对象增加对象引用
		 */
		Address address = new Address("北京");
		p.setAddress(address);
		
		Person p3 = (Person) p.clone();
		p3.setName("小明克隆人");
		p3.setAge(12);
		p3.setCode(12345);
		p3.getAddress().setName("北京克隆人");
		
		/**
		 * 针对基本类型及其封装类都是将对应的基本类型值拷贝，对于其余对象，则仅是拷贝其引用
		 * Address [name=北京克隆人]
		 * Address [name=北京克隆人]
		 * ---------------------------------------------
		 * 重新clone方法后-------------深拷贝
		 * Address [name=北京]
		 * Address [name=北京克隆人]
		 */
		System.out.println(p.getAddress());
		System.out.println(p3.getAddress());
		
		
		/**	
		 * 	深拷贝---------------
		 * 	//写入对象
			ByteArrayOutoutStream bo=new ByteArrayOutputStream();
			ObjectOutputStream oo=new ObjectOutputStream(bo);
			oo.writeObject(this);
			//读取对象
			ByteArrayInputStream bi=new ByteArrayInputStream(bo.toByteArray());
			ObjectInputStream oi=new ObjectInputStream(bi);
			return(oi.readObject());
		 */
		
	}

}
