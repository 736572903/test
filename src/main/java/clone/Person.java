package clone;

import org.apache.commons.lang.StringUtils;

public class Person implements Cloneable{
	
	//姓名
    private String name;
    // 年龄
    private int age;
    // 编号
    private Integer code;
    
    private Address address;

    public Person(){

    }
		
    public Person(String name, int age, Integer code) {
        this.name = name;
        this.age = age;
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
    
	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	@Override
	public String toString() {
		return "Person [name=" + name + ", age=" + age + ", code=" + code + "]";
	}
	
	@Override
	protected Object clone() {
		
//		return super.clone();
		
		try {
			Person person = (Person) super.clone();
			if(address != null){
				person.address = (Address) address.clone();
			}
			return person;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
    
}
