package chat;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ChatServer {
	
	public static final int PORT = 8888;
	
	public static void main(String[] args) {
		
		ServerSocket serverSocket = null;
		List<PrintWriter> listWriters = new ArrayList<PrintWriter>(); // 접속된 모든 Client에게 쓸 수 있는 PrintWriter List 공유 객체 
		
		try {
			// 1. Server Socket 생성
			serverSocket = new ServerSocket();
		
			// 1-1. FIN-WAIT -> TIME_WAIT 상태에서도 소켓 포트 할당이 가능하도록 하기 위해..
			serverSocket.setReuseAddress(true);
			
			// 2. 바인딩(Binding)
			String hostAddress = InetAddress.getLocalHost().getHostAddress(); // 로컬 호스트 주소
			serverSocket.bind(new InetSocketAddress(hostAddress, PORT));
			log("연결 기다림 " + hostAddress + ":" + PORT);
			
			// 3. 요청 대기
			while(true) {
				Socket socket = serverSocket.accept(); // client에서 들어오는 연결 수락
				// 들어오는 연결마다 Client와의 통신을 하기 위한 스레드 생성하고 시작(List 객체를 스레드의 생성자를 통해 전달)
				new ChatServerThread(socket, listWriters).start(); 
			}
			
		} catch (IOException ex) {
			log("error:" + ex);
		} finally {
			try {
				// 자원정리
				if (serverSocket != null && serverSocket.isClosed() == false) {
					serverSocket.close();
				}
			} catch (IOException ex) {
				log("error:" + ex);
			}
		}
	}

	public static void log(String message) {
		System.out.println("[ChatServer] " + message);
	}
}
