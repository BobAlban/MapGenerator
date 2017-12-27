package midiDecoder;

public class Note {
	
	private int startTick;
	private int durationTick;
	private int key;
	private int velocity;
	
	public Note(int startTick, int durationTick, int key, int velocity) {
		this.startTick=startTick;
		this.durationTick=durationTick;
		this.key=key;
		this.velocity=velocity;
	}

	public int getStartTick() {
		return startTick;
	}

	public int getDurationTick() {
		return durationTick;
	}

	public int getKey() {
		return key;
	}

	public int getVelocity() {
		return velocity;
	}
	
	public String toString() {
		return "\n(startTick = " + startTick + ", durationTick = " + durationTick + ", key = " + key + ", velocity = " + velocity + ")";
	}
	
	public int compareTo(Note n) {
		return n.startTick-startTick;
	}
	

}
