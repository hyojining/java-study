package exception;

import java.io.IOException;

public class MyClass {
	public void danger() throws IOException, MyException { // IOException 던질 수 있다고 메소드에 정의
		System.out.println("some code1.....");
		System.out.println("some code2.....");

		if(3-3 == 0) {
			throw new MyException();
		}
		if(1-1 == 0) {
			throw new IOException();
		}
		
		System.out.println("some code3.....");
		System.out.println("some code4.....");
	}
}
