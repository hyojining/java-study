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
		List<PrintWriter> listWriters = new ArrayList<PrintWriter>();
		
		try {
			serverSocket = new ServerSocket();
		
			// 1-1. FIN-WAIT -> TIME_WAIT 상태에서도 소켓 포트 할당이 가능하도록 하기 위해...
			serverSocket.setReuseAddress(true);
			
			String hostAddress = InetAddress.getLocalHost().getHostAddress();
			serverSocket.bind(new InetSocketAddress(hostAddress, PORT));
			log("연결 기다림 " + hostAddress + ":" + PORT);
			
			
			while(true) {
				Socket socket = serverSocket.accept();
				new ChatServerThread(socket, listWriters).start();
			}
			
		} catch (IOException ex) {
			log("error:" + ex);
		} finally {
			try {
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
