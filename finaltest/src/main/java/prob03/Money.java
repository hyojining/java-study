package prob03;

import java.util.Objects;

public class Money {
	private int amount;

	public Money(int i) {
		this.amount = i;
	}

	public Object add(Money other) {
		return new Money(this.amount + other.amount);
	}

	public Object minus(Money other) {
		return new Money(this.amount - other.amount);
	}

	public Object multiply(Money other) {
		return new Money(this.amount * other.amount);
	}

	public Object devide(Money other) {
		return new Money(this.amount / other.amount);
	}

	@Override
	public int hashCode() {
		return Objects.hash(amount);
	}

	@Override
	public boolean equals(Object obj) {
//		if (this == obj) // 동일성 비교 (주소값 비교)
// 	        return true;
//	    if (obj == null)
//	        return false;
//	    if (getClass() != obj.getClass())
//	        return false;
		if(obj instanceof Money) {
			Money other = (Money) obj;
	    return amount == other.amount;
		}
		return false;
	}
	
	// hashcode, equal override
	/* 코드 작성 */
}
