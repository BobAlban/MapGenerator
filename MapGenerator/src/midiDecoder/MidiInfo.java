package midiDecoder;

import javax.sound.midi.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;

public class MidiInfo {
	
	protected final int NOTE_ON = 0x90;
	protected final int NOTE_OFF = 0x80;
	protected String pathMidi;
	protected ArrayList<Note> sortedMidiEvents;
	protected float durationTick;
	protected int resolution;
    
    
    public MidiInfo (String pathMidi) throws Exception {
    	this.pathMidi = pathMidi;
    	sortedMidiEvents=sortMidiEvents();
    	durationTick=durationTick();
    	//System.out.println(sortedMidiEvents);
    	resolution=resolution();
    }
    
	public String getpath() {
		return pathMidi;
	}
	
	public ArrayList<Note> getSortedMidiEvents(){
		return sortedMidiEvents;
	}
	
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
	
	private ArrayList<Note> removeStackedNotes(ArrayList<Note> l) throws Exception{
		int highestVelocity=Integer.MAX_VALUE;
		for(int i=0;i<l.size();i++) {
			highestVelocity=l.get(i).getVelocity();
			int currentStartTick=l.get(i).getStartTick(); 
			while(i+1<l.size() && currentStartTick==l.get(i+1).getStartTick()) {
				//System.out.println("l(i):   " + l.get(i) + "\n" + "l(i+1):   " + l.get(i+1));
				if(highestVelocity<l.get(i+1).getVelocity()) {
					highestVelocity=l.get(i+1).getVelocity();
					l.remove(l.get(i));
				}
				else {//highestVelocity=>l.get(i+1).getVelocity
					l.remove(l.get(i+1));
				}
			}
			highestVelocity=Integer.MAX_VALUE;
		}
		return l;
	}
		
	/**
	 * @return a sorted ArrayList of all  NOTE_ON MidiEvents stocked in sortedMidiEvents
	 */
	private ArrayList<Note> sortMidiEvents() throws Exception{
		ArrayList<Note> l = new ArrayList<Note>();
		
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
						 
						 l.add(new Note(tick.intValue(),nextTick.intValue()-tick.intValue(),sm.getData1(),sm.getData2()-nextSm.getData2()));
					 }
					 
				 }
			 }
		}
		Collections.sort(l, (left, right) -> left.getStartTick() - right.getStartTick());
		return removeStackedNotes(l);
	}
}
