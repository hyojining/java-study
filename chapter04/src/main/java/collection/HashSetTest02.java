package collection;

import java.util.HashSet;
import java.util.Set;

public class HashSetTest02 {

	public static void main(String[] args) {
		Set<Gugudan> set = new HashSet<>();
		
		set.add(new Gugudan(2, 3));
		set.add(new Gugudan(9, 9));
		set.add(new Gugudan(3, 2)); // (2, 3)과 같기 때문에 들어가면 안됨
		
		for(Gugudan : set) {
			System.out.println(set);
		}

	}

}
