package chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.SocketException;

public class ChatClientThread extends Thread{
	private Socket socket;
	
	public ChatClientThread(Socket socket) {
		this.socket = socket;
	}
	
	@Override
	public void run() {
		/* reader를 통해 읽은 데이터 콘솔에 출력하기 (message 처리) */
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream(), "utf-8"));
			
			while(true) {
				String line = br.readLine();
				if(line == null) {
					break;
				}
				
				System.out.println(line);
			}
		} catch(SocketException ex) {
			ChatClient.log("error:"+ex);
		} catch(IOException ex) {
			ChatClient.log("error:"+ex);
		} finally {
			System.exit(0);
		}
	}

}
