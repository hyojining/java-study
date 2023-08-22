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
	
	private String nickname;
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
				request = br.readLine();
				
				if(request == null) {
					ChatClient.log("클라이언트로부터 연결 끊김");
					doQuit(pw);
					break;
				}
					
				// 4. 프로토콜 분석
				String[] tokens = request.split(":");
				if("JOIN".equals(tokens[0])) {
					doJoin(tokens[1], pw);
				}else if("MESSAGE".equals(tokens[0])) {
					doMessage(tokens[1]);
				}else if("QUIT".equals(tokens[0])) {
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
		broadcast(data);
		
		/* writer pool에 저장 */
		addWriter(pw);
		
		// ack
		pw.println("JOIN:OK");
	}
	
	private void addWriter(PrintWriter pw) {
		synchronized(listWriters) {
			listWriters.add(pw);
		}
	}
	
	private void broadcast(String data) {
		synchronized(listWriters) {
			for(PrintWriter pw : listWriters) {
				pw.println(data);
			}
		}
	}
	
	private void doMessage(String message) {
		String data = nickname + ":" + message;
		broadcast(data);
	}
	
	private void doQuit(PrintWriter pw) {
		removeWriter(pw);
		
		String data = nickname + "님이 퇴장하였습니다.";
		broadcast(data);
	}

	private void removeWriter(PrintWriter pw) {
		synchronized(listWriters) {
			listWriters.remove(pw);
		}
	}

}
