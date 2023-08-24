package chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class ChatServerThread extends Thread{
	
	private String nickname; // 사용자 이름
	private Socket socket;
	List<PrintWriter> listWriters;
	
	public ChatServerThread(Socket socket, List<PrintWriter> listWriters) {
		this.socket = socket;
		this.listWriters = listWriters;
	}

	@Override
	public void run() {
		BufferedReader br = null;
		PrintWriter pw = null;
		
		try {
			// 1. Remote Host Information
			// client의 ip, port를 server에 알려주기 위함
			InetSocketAddress inetSocketAddress = (InetSocketAddress)socket.getRemoteSocketAddress();
			ChatServer.log( "connected from [" + inetSocketAddress.getAddress().getHostAddress() + ":" + inetSocketAddress.getPort() + "]");			
			
			// 2. 스트림 얻기
			br = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
			pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8), true);
			
			// 3. 요청 처리
			while(true) {
				String request;
				request = br.readLine(); // Client로부터 데이터 읽어들이기
				
				if(request == null) { // Client가 "quit" 보내지 않고 소켓을 닫은 경우
					doQuit(pw);
					ChatServer.log("클라이언트로부터 연결 끊김");
					break;
				}
					
				// 4. 프로토콜 분석
				// token[0]: Client의 요청(닉네임 등록, 메시지 전달, 방 나가기)
				// token[1]: 메시지(닉네임, 메시지 내용)
				String[] tokens = request.split(":"); // 읽어온 데이터를 ":" 기준으로 split
				if("JOIN".equals(tokens[0])) { // 닉네임 등록
					doJoin(tokens[1], pw);
				}else if("MESSAGE".equals(tokens[0])) { // 메시지 전달
					doMessage(tokens[1]);
				}else if("QUIT".equals(tokens[0])) { // 방 나가기
					doQuit(pw);
					break;
				}else {
					ChatServer.log("에러:알 수 없는 요청(" + tokens[0] + ")");
					break;
				}
			}	
		}catch (SocketException ex) {
			doQuit(pw);
			ChatServer.log("error: " + ex);
		}catch (IOException ex) {
			doQuit(pw);
			ChatServer.log("error: " + ex);
		} finally {
			try {
				if (socket != null && socket.isClosed() == false) {
					socket.close();
				}
			}catch (IOException ex) {
				ChatClient.log("error:" + ex);
		    }
		}
				
	}

	private void doJoin(String nickname, PrintWriter pw) {
		this.nickname = nickname;
		
		String data = nickname + "님이 입장하였습니다.";
		broadcast(data); // 사용자가 채팅방에 참여했을 때, 다른 사용자들에게 입장 메시지를 브로드캐스팅
		
		/* writer pool에 저장 */
		addWriter(pw); // List 공유 객체에 추가
		
		// ack(Client로부터 채팅방 참여 요청을 잘 받았다고 Server에서 Client로 ACK 메시지 전달)
		pw.println("JOIN:OK");
	}
	
	private void addWriter(PrintWriter pw) {
		synchronized(listWriters) {
			listWriters.add(pw); // List 공유 객체에 파라미터로 받은 PrintWriter 추가
		}
	}
	
	private void broadcast(String data) { // Server에서 연결된 모든 Client에 메시지를 보내는 메소드
		synchronized(listWriters) { // 스레드간 공유 객체인 listWriters에 접근하기 때문에 동기화 처리
			for(PrintWriter pw : listWriters) {
				pw.println(data); // 연결된 모든 Client에 메시지 전달
			}
		}
	}
	
	private void doMessage(String message) {
		String data = nickname + ":" + message;
		broadcast(data); // 사용자가 채팅을 보낼 때, 다른 사용자들에게 메시지를 브로드캐스팅
	}
	
	private void doQuit(PrintWriter pw) {
		removeWriter(pw); // List 공유 객체에서 제거
		
		String data = nickname + "님이 퇴장하였습니다.";
		broadcast(data); // 사용자가 채팅방을 나갈 때, 다른 사용자들에게 퇴장 메시지를 브로드캐스팅
	}

	private void removeWriter(PrintWriter pw) {
		synchronized(listWriters) {
			listWriters.remove(pw); // List 공유 객체에 파라미터로 받은 PrintWriter 제거
		}
	}

}
