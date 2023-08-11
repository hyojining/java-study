package chapter04;

public class ObjectTest01 {

	public static void main(String[] args) {
		Point point = new Point();
		
		// Class klass = point.getClass();		// reflection
		System.out.println(point.getClass());
		System.out.println(point.hashCode()); 	// address x
												// reference x
												// address 기반의 해싱값 o (10진수)
		System.out.println(point.toString());	// getClass() + "@" + hashCode()(16진수)
		System.out.println(point);
	}

}
