package chat.gui;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;

public class ChatClientApp {
	public static final int PORT = 8888;

	public static void main(String[] args) {
		String name = null;
		Scanner scanner = new Scanner(System.in);

		while(true) {
			System.out.println("대화명을 입력하세요.");
			System.out.print("> ");
			name = scanner.nextLine();
			
			if (name.isEmpty() == false) { // 비지 않았으면
				break;
			}
			
			System.out.println("대화명은 한글자 이상 입력해야 합니다.\n");
		}
		
		scanner.close();

		Socket socket = null;
		
		try {
			// 1. create socket
			socket = new Socket();
			
			// 2. connect server
			socket.connect(new InetSocketAddress("0.0.0.0", PORT));
			
			// 3. reader/writer 생성
			BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream(), "utf-8"));
			PrintWriter pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), "utf-8"), true);

			// 4. join protocol 진행
			pw.println("JOIN:"+name); // server로 송신
			String ack = br.readLine(); // server로부터 ack 메시지 읽어들임
			if("JOIN:OK".equals(ack)) {
				new ChatWindow(name, socket).show();
			}
			
		}catch (ConnectException ex) {
			log("error:" + ex);
		} catch (Exception ex) {
			log("error:" + ex);
		} finally {
			// 10. 자원정리
			if (scanner != null) {
				scanner.close();
            }
		}
	}
	
	public static void log(String message) {
		System.out.println( "[ChatClient] " + message );
	}

}
