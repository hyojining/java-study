package prob04;

public class Person {
	
	private static int numberOfPerson; // 전체 인구수
	private int age;
	private String name;
	
	/* 코드 작성 */
	public Person() {
		this.age = 12;
		this.name = "";
	}
	
	public Person(String name) {
		this.age = 12;
		this.name = name;
	}
	
	public Person(int age, String name) {
		this.age = age;
		this.name = name;
	}

	public void selfIntroduce() {
		System.out.println("내 이름은 " + this.name + "이며, 나이는 " + this.age + "입니다.");
	}

	public static String getPopulation() {
		return String.valueOf(++numberOfPerson);
	}
}
