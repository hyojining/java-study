package chat.gui;
import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Frame;
import java.awt.Panel;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;

import chat.ChatClient;
import chat.ChatClientThread;

public class ChatWindow {

	private Frame frame;
	private Panel pannel;
	private Button buttonSend;
	private TextField textField;
	private TextArea textArea;
	private Socket socket;
	private BufferedReader br;
	private PrintWriter pw;
	private String name;
	

	public ChatWindow(String name, Socket socket) {
		frame = new Frame(name);
		pannel = new Panel();
		buttonSend = new Button("Send");
		textField = new TextField();
		textArea = new TextArea(30, 80);
		this.socket = socket;
	}

	public void show() { // 위젯을 윈도우에 붙힘
		// Button
		buttonSend.setBackground(Color.GRAY);
		buttonSend.setForeground(Color.WHITE);
		buttonSend.addActionListener( new ActionListener() { // 이름없는 클래스
			@Override
			public void actionPerformed( ActionEvent actionEvent ) {
				sendMessage();
			}
		});
		
//		buttonSend.addActionListener((ActionEvent actionEvent) -> { 
//				sendMessage();
//		});

		// Textfield
		textField.setColumns(80);
		textField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				char keyCode = e.getKeyChar();
				if(keyCode == KeyEvent.VK_ENTER) { // enter 입력
					sendMessage();
				}
			}
		});

		// Pannel
		pannel.setBackground(Color.LIGHT_GRAY);
		pannel.add(textField);
		pannel.add(buttonSend);
		frame.add(BorderLayout.SOUTH, pannel);

		// TextArea
		textArea.setEditable(false);
		frame.add(BorderLayout.CENTER, textArea);

		// Frame
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				finish();
			}
		});
		frame.setVisible(true);
		frame.pack(); // 윈도우 띄우기
		
		
		try {
			// IOStream 받아오기
			br = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
			pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8), true);
			
			new ChatClientThread().start();
			
		} catch (IOException ex) {
			ChatClientApp.log("error:" + ex);
		}
		
		
	}
	
	private void sendMessage() {
		String message = textField.getText();
		System.out.println("메세지를 보내는 프로토콜 구현:" + message);
		
		textField.setText("");
		textField.requestFocus();
		
		// ChatClientThread에서 서버로부터 받은 메세지가 있다고 치고~~
		updateTextArea(name + ":" + message); // 여기서 불러오는 거 아님
		pw.println("MESSAGE:" + message);

	}
	
	private void updateTextArea(String message) {
		textArea.append(message);
		textArea.append("\n");
	}
	
	private void finish() {
		// quit 프로토콜
		pw.println("QUIT");

		// exit java(JVM)
		System.exit(0);
		
	}
	
	private class ChatClientThread extends Thread{
		
		@Override
		public void run() {
			/* reader를 통해 읽은 데이터 콘솔에 출력하기 (message 처리) */
			try {
				while(true) {
					String line = br.readLine(); // server로부터 데이터 읽기
					if(line == null) {
						break;
					}
					
					updateTextArea(line); // server로부터 읽은 데이터를 콘솔에 출력
				}
			} catch(SocketException ex) {
				ChatClient.log("error:"+ ex);
			} catch(IOException ex) {
				ChatClient.log("error:"+ex);
			} finally {
				System.exit(0);
			}
		}
		
	}
}
