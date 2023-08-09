package prob02;

import java.util.Scanner;

public class GoodsApp {
	private static final int COUNT_GOODS = 3;

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);

		Goods[] goods = new Goods[COUNT_GOODS];

		// 상품 입력
		for(int i=0;i<goods.length;i++) {
			String line = scanner.nextLine();
			String[] infos = line.split(" ");
			
			String name = infos[0];
		    int price = Integer.parseInt(infos[1]);
		    int count = Integer.parseInt(infos[2]);
			goods[i] = new Goods();
			goods[i].setName(name);
			goods[i].setPrice(price);
			goods[i].setCount(count);
		}
		
		// 상품 출력
		System.out.println();
		for(int i=0;i<goods.length;i++) {
			System.out.println(
					goods[i].getName() + "(가격: " + goods[i].getPrice() + "원)이 " + 
							goods[i].getCount() + "개 입고 되었습니다.");
		}
		// 자원정리
		scanner.close();
	}
}
