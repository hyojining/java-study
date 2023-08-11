package prob5;

public class MyStack {
	private String[] buffer;
	private int top;
	
	public MyStack(int size) {
		buffer = new String[size];
		top = 0;
	}
	public void push(String string){
		if(top >= buffer.length) {
			String[] buffer2 = new String[2*buffer.length];
			for(int i=0; i<buffer.length;i++) {
				buffer2[i] = buffer[i];
			}
			buffer2[top] = string;
			buffer=buffer2;
			top++;
		}
		else{
			buffer[top] = string;
			top++;
		}
	}
	public String pop() throws MyStackException{
		if(top <= 0) {
			throw new MyStackException("stack is empty");
		}else {
	
			return buffer[--top];
		}
	}
	public boolean isEmpty() {
		return top == 0;
	}
	
}