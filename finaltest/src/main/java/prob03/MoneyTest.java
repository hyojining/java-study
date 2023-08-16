package prob03;

public class MoneyTest {

	public static void main(String[] args) { // 수정 금지
		Money five = new Money(5);
		Money two = new Money(2);
		Money three = new Money(3);
		Money ten = new Money(10);
		
		// add, minus ...는 money를 리턴해줘야 함
		if (five.equals(two.add(three))
				&& three.equals(five.minus(two))
				&& ten.equals(five.multiply(two))
				&& two.equals(ten.devide(five))) {
			System.out.println("Money Class 구현을 완료 하였습니다.");
		}
	}
}
