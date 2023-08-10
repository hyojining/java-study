package tv;

public class TV {
	private int channel; 
	private int volume; 
	private boolean power;
	
	public TV() {
		this(7, 20, false);
	}
	
	public TV(int channel, int volume, boolean power) {
		this.channel = channel;
		this.volume = volume;
		this.power = power;
	}
	
	public int getChannel() {
		return channel;
	}

	public int getVolume() {
		return volume;
	}

	public boolean isPower() {
		return power;
	}

	public void power(boolean on) {
		power = on;
	}
	
	public void channel(boolean up) { // 아날로그 방식
		if(power) {
			if(up) {
				channel = (channel == 255) ? 1 : channel + 1;
			}else {
				channel = (channel == 1) ? 255 : channel - 1;
			}
		}
	}
	
	public void channel(int channel) { // 디지털 방식
		if(power) {
			this.channel = (channel < 1) ? 255 : (channel > 255) ? 1 : channel;
		}
	}
	
	public void volume(boolean up) {
		if (power) {
            if (up) {
                volume = (volume == 100) ? 0 : volume + 1;
            } else {
                volume = (volume == 0) ? 100 : volume - 1;
            }
        }
	}
	
	public void volume(int volume) {
		if (power) {
            this.volume = (volume < 0) ? 100 : (volume > 100) ? 0 : volume;
        }
	}
	
	public void status() {
		System.out.println("TV[power=" + (power ? "on" : "off") + ", channel=" + channel + ", volume=" + volume + "]");
	}
	
}
