package tv;

public class TV {
	private int channel; 	// 1~255 (256이면 rotation)
	private int volume; 	// 0~100 (넘으면 0으로 rotation)
	private boolean power;
	
	public void power(boolean on) {
		power = on;
	}
	
	public void channel(boolean up) { // 아날로그 방식
		
	}
	
	public void channel(int channel) { // 디지털 방식
		this.channel = channel;
	}
	
	public void volume(boolean up) {
		
	}
	
	public void volume(int volume) {
		this.volume = volume;
	}
	
	public void status() {
		System.out.println("TV[power=" + (power ? "on" : "off") + ", channel=" + channel + ", volume=" + volume);
	}
	
}
