package util;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Scanner;

public class NSLookup {

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		
		while(true) {
			System.out.print(">> ");
			String line = scanner.nextLine();
			
			if("exit".equals(line)) {
				break;
			}
			
			InetAddress[] addresses;
			try {
				addresses = InetAddress.getAllByName(line);
				for(InetAddress address : addresses) {
					System.out.println(address.getHostName() + " : " + address.getHostAddress());
				}
			} catch (UnknownHostException e) {
				e.printStackTrace();
			}
			
		}
	}

}
