package midiDecoder;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiMessage;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequence;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Track;

import hitObject.Pair;

public class MidiInfo {

	private final int NOTE_ON = 0x90;
	private final int NOTE_OFF = 0x80;
	private String pathMidi;
	private MidiStruct midiStruct;
	private float durationTick;
	private int resolution;
	private ArrayList<Pair<Float,Integer>> listTempo;
	private float BPM;

	public MidiInfo (String pathMidi) throws Exception {
		this.pathMidi = pathMidi;
		midiStruct = new MidiStruct();
		listTempo = new ArrayList<Pair<Float,Integer>>();
		resolution=resolution();
		durationTick=durationTick();
		getMidiEvents();
		BPM=getBPM();
		System.out.println(listTempo);
		//System.out.println(midiStruct);
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

	/*
	 * FIRST : Calculate a BPM out of the most frequent time beetween 2 beats
	 * SECOND : overwrite on listTempo the right beat division to each note 
	 * @return the BPM calculated, always beetween 125 and 250
	 */
	private float getBPM() {
		float mostFrequentTime = listTempo.get(0).getLeft();
		float BPM=60000/mostFrequentTime;
		while(!(BPM>=125f && BPM<=250f)) {
			if(BPM<125f)	BPM*=2;
			if(BPM>250f)	BPM/=2;
		}
		//The bigger beatPrecision is, the most accurate beats divison will be
		//(e.g. a beatPrecision of 2 won't make the difference beetween a beat divison of 0.667 and 0.8, but a beatPrecision of 4 will)
		int beatPrecision = 4;
		for(Pair<Float,Integer> p : listTempo) {
			p.setLeft(60000/p.getLeft()/BPM);
			if(p.getLeft()>=1) {
				p.setLeft((float)(Math.round(beatPrecision*p.getLeft())));
				p.setLeft(beatPrecision/p.getLeft());
			}
			else {
				p.setLeft(beatPrecision/p.getLeft());
				p.setLeft((float)(Math.round(p.getLeft()/beatPrecision)));
			}
		}
		return BPM;
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
							add(listTempo, nextTick.intValue()-tick.intValue());
							midiStruct.add(tick.intValue(),nextTick.intValue()-tick.intValue(),sm.getData1(),sm.getData2()-nextSm.getData2());
						}
					}
					else {
						//TODO : Understand when this case happen
						//System.err.println("C'est bizarre!");
					}
				}
			}
		}
		Collections.sort(listTempo, (left, right) -> right.getRight() - left.getRight());
		midiStruct.sort();
	}

	private void add(ArrayList<Pair<Float,Integer>> listTempo, int tick) {
		for(Pair<Float,Integer> p : listTempo) {
			if(p.getLeft()==tick*durationTick) {
				p.setRight(p.getRight()+1);
				return;
			}
		}
		listTempo.add(new Pair<Float, Integer>(tick*durationTick, 1));
	}
}
