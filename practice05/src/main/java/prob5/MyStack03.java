package prob5;

public class MyStack03<T> {
	private T[] buffer;
	private int top;
	
	public MyStack03(int size) {
		buffer = (T[])new Object[size];
		top = 0;
	}
	public void push(T t){
		if(top >= buffer.length) {
			T[] buffer2 = (T[])new Object[2*buffer.length];
			for(int i=0; i<buffer.length;i++) {
				buffer2[i] = buffer[i];
			}
			buffer2[top] = t;
			buffer=buffer2;
			top++;
		}
		else{
			buffer[top] = t;
			top++;
		}
	}
	public T pop() throws MyStackException{
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