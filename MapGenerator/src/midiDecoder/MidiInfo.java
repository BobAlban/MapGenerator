package midiDecoder;

import java.io.File;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiMessage;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequence;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Track;

public class MidiInfo {

	private final int NOTE_ON = 0x90;
	private final int NOTE_OFF = 0x80;
	private String pathMidi;
	private MidiStruct midiStruct;
	private float durationTick;
	private int resolution;

	public MidiInfo (String pathMidi) throws Exception {
		this.pathMidi = pathMidi;
		midiStruct = new MidiStruct();
		getMidiEvents();
		resolution=resolution();
		durationTick=durationTick();
		System.out.println(midiStruct);
		//System.out.println(midiStruct.toStringMilliseconds(durationTick));
	}

	public String getpath() { return pathMidi; }
	
	public MidiStruct getMidiSturct() {	return midiStruct; }
	
	public float getDurationTick() {
		return durationTick;
	}
	
	public int getResolution() {
		return resolution;
	}
	
	private float durationTick() throws Exception {
		Sequence sequence = MidiSystem.getSequence(new File(pathMidi));
		Long tickLength = sequence.getTickLength();
		return sequence.getMicrosecondLength()/tickLength.floatValue()/1000f;
	}
	
	private int resolution() throws Exception{
		Sequence sequence = MidiSystem.getSequence(new File(pathMidi));
		return sequence.getResolution();
	}
	
	private void getMidiEvents() throws Exception {
		Sequence sequence = MidiSystem.getSequence(new File(pathMidi));
		for (Track track :  sequence.getTracks()) {
			for (int i=0; i < track.size()-1; i++) {
				MidiEvent event = track.get(i);
				MidiEvent nextEvent = track.get(i+1);
				MidiMessage message = event.getMessage();
				MidiMessage nextMessage = nextEvent.getMessage();
				if (message instanceof ShortMessage && nextMessage instanceof ShortMessage) {
					ShortMessage sm = (ShortMessage) message;
					ShortMessage nextSm = (ShortMessage) nextMessage;
					if (sm.getCommand() == NOTE_ON && sm.getData2()!=0 && (nextSm.getCommand() == NOTE_OFF || nextSm.getData2()==0)) {
						Long tick = event.getTick();
						Long nextTick = nextEvent.getTick();
						if(nextTick.intValue()-tick.intValue()!=0) {
							midiStruct.add(tick.intValue(),nextTick.intValue()-tick.intValue(),sm.getData1(),sm.getData2()-nextSm.getData2());
						}
					}
					else {
						//System.err.println("C'est bizarre!");
					}
				}
			}
		}
		midiStruct.sort();
	}
}
