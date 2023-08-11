package exception;

public class MyException extends Exception {
	private static final long serialVersionUID = 1L; // warning 거슬리면

	public MyException(String message) {
		super(message);
	}
	
	public MyException() {
		super("MyException Occurs");
	}
}
