package thread.threadImp;

public class ThreadImpTest {

	public static void main(String[] args) {
		
		String name = "账单";
		
		int age = 19;
		
		for(int i=0;i<50;i++){
			ThreadImp threadImp = new ThreadImp(name, age);
			new Thread(threadImp).start();
		}

	}

}
