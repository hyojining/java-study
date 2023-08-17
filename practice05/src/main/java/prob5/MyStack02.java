package prob5;

public class MyStack02 {
	private Object[] buffer;
	private int top;
	
	public MyStack02(int size) {
		buffer = new Object[size];
		top = 0;
	}
	public void push(Object string){
		if(top >= buffer.length) {
			Object[] buffer2 = new Object[2*buffer.length];
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
	public Object pop() throws MyStackException{
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