package chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;

public class ChatClient {

	public static void main(String[] args) {
		
		Scanner scanner = null;
		Socket socket = null;
		
		try {
			// 1. 키보드 연결
			scanner = new Scanner(System.in);
			
			// 2. socket 생성
			socket = new Socket();
			
			// 3. 연결
			socket.connect(new InetSocketAddress("0.0.0.0", ChatServer.PORT));
			
			// 4. reader/writer 생성
			BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream(), "utf-8"));
			PrintWriter pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), "utf-8"), true);

			// 5. join 프로토콜
			System.out.print("닉네임>>");
			String nickname = scanner.nextLine(); // 첫 입력은 사용자 이름
			pw.println("JOIN:"+nickname); // server로 송신
			String ack = br.readLine(); // server로부터 ack 메시지 읽어들임
			if("JOIN:OK".equals(ack)) {
				System.out.println("입장하였습니다. 즐거운 채팅 되세요");
			}
			
			// 6. ChatClientThread 시작
			new ChatClientThread(socket).start(); // 접속하는 Client마다 소켓 스레드 실행하고 시작
			
			// 7. 키보드 입력 처리
			while(true) {
				if( scanner.hasNextLine() == false ) {
					continue;
				}
				
				System.out.print(">>");
				String input = scanner.nextLine(); // 키보드로부터 데이터 입력받기
				
				if("quit".equals(input) == true) {
					// 8. quit 프로토콜 처리
					pw.println("QUIT");
					break;
				}else { // "quit"이 아니면 메시지 처리
					pw.println("MESSAGE:" + input);
				}
			}
		} catch (ConnectException ex) {
			log("error:" + ex);
		} catch (Exception ex) {
			log("error:" + ex);
		} finally {
			// 10. 자원정리
			try {
				if (socket != null && socket.isClosed() == false) {
					socket.close();
				}
				if (scanner != null) {
					scanner.close();
				}
            } catch (IOException ex) {
                log("error:" + ex);
            }
		}
	}

	public static void log(String message) {
		System.out.println( "[ChatClient] " + message );
	}
}
