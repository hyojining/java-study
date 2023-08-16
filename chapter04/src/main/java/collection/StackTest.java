package collection;

import java.util.Stack;

public class StackTest {

	public static void main(String[] args) {
		Stack<String> s = new Stack<>();
		
		s.push("둘리");
		s.push("마이콜");
		s.push("도우너");
		
		while(!s.isEmpty()) {
			String str = s.pop();
			System.out.println(str);
		}
		
		// 비어있는 경우에는 예외 발생
		// s.pop();
		
		s.push("둘리");
		s.push("마이콜");
		s.push("도우너");
		
		System.out.println(s.pop()); 	// 도우너
		System.out.println(s.peek()); 	// 마이콜 (마지막 값)
		System.out.println(s.pop()); 	// 마이콜

	}

}
