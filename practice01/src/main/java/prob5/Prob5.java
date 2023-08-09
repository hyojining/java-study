package prob5;

public class Prob5 {

	public static void main(String[] args) {
		for(int i=1;i<100;i++) {
			String numStr=String.valueOf(i);
			int count=0;
			for (int j=0;j<numStr.length();j++) {
				char ch=numStr.charAt(j);
				if(ch=='3'||ch=='6'||ch=='9') {
					count++;
				}
			}
			if(count!=0) {
				System.out.print(numStr+" ");
				for (int k=0;k<count;k++) {
					System.out.print("짝");
				}
				System.out.println();
			}
		}
	}
}
