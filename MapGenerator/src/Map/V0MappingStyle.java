package Map;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;

import HitObjects.Circle;
import midiDecoder.MidiInfo;
import midiDecoder.Note;

public class V0MappingStyle implements IMap {
	
	protected String pathMidi;
	protected String pathMp3;
	protected ArrayList<Note> sortedMidiEvents;
	protected float durationTick;
	protected float resolution;
	int posX;
	int posY;
	
	
	public V0MappingStyle(String pathMidi,String pathMp3) throws Exception {
		this.pathMidi=pathMidi;
		this.pathMp3=pathMp3;
		MidiInfo midiInfo = new MidiInfo(pathMidi);
		resolution=(float)midiInfo.getResolution();
		durationTick=midiInfo.getDurationTick();
		sortedMidiEvents=midiInfo.getSortedMidiEvents();
		osuFileGeneration();
		posX=0;
		posY=0;
	}
	
	public void osuFileGeneration() {
		try (PrintWriter p = new PrintWriter(new FileOutputStream(pathMp3, true))) {
		    p.print("osu file format v14\r\n\r\n[General]\r\nAudioFilename: audio.mp3\r\nAudioLeadIn: 0\r\nCountdown: 1\r\nSampleSet: Normal\r\nStackLeniency: 0.7\r\nMode: 0\r\nLetterboxInBreaks: 0\r\nWidescreenStoryboard: 1\r\n\r\n[Editor]\r\nDistanceSpacing: 1\r\nBeatDivisor: 4\r\nGridSize: 16\r\nTimelineZoom: 1\r\n\r\n[Metadata]\r\nTitle:\r\nTitleUnicode:\r\nArtist:\r\nArtistUnicode:\r\nCreator:evilmrcraft\r\nVersion:V0MappingStyle\r\nSource:\r\nTags:\r\nBeatmapID:0\r\nBeatmapSetID:-1\r\n\r\n[Difficulty]\r\nHPDrainRate:6\r\nCircleSize:4\r\nOverallDifficulty:8\r\nApproachRate:9\r\nSliderMultiplier:1.4\r\nSliderTickRate:1\r\n\r\n[Events]\r\n//Background and Video events\r\n//Break Periods\r\n//Storyboard Layer 0 (Background)\r\n//Storyboard Layer 1 (Fail)\r\n//Storyboard Layer 2 (Pass)\r\n//Storyboard Layer 3 (Foreground)\r\n//Storyboard Sound Samples\r\n\r\n\r\n[HitObjects]\r\n");
		    p.print(hitObjects());
		} catch (FileNotFoundException e1) {
		    e1.printStackTrace();
		}
	}
	
	public float getRythmNext(int n) {
		assert n+1<sortedMidiEvents.size();
		float startTickDifference=sortedMidiEvents.get(n+1).getStartTick()-sortedMidiEvents.get(n).getStartTick();
		return resolution/startTickDifference;	
	}
	

	public float getRythmPrevious(int n) {
		assert n>0;
		float startTickDifference=sortedMidiEvents.get(n).getStartTick()-sortedMidiEvents.get(n-1).getStartTick();
		return resolution/startTickDifference;	
	}
	
	public void positionNext() {
		if(posY==0 && posX!=7) posX++;
		else if(posY==3 && posX!=0) posX--;
		else if(posY!=0 && posX==0) posY--;
		else if(posY!=3 && posX==7) posY++;
	}
	
	public String hitObjects() {
		String hitObject="";
		int offset;
		boolean newCombo;
		float rythmNext;
		float rythmPrevious;
		for(int i=1;i<sortedMidiEvents.size()-1;i++) {
			if(getRythmPrevious(i)>4) i++;
			offset= Math.round(sortedMidiEvents.get(i).getStartTick()*durationTick);
			rythmNext=getRythmNext(i);
			rythmPrevious=getRythmPrevious(i);
			newCombo = getRythmPrevious(i)<=1;
			if(rythmNext<=1 && rythmPrevious<=1) {
				hitObject+=new Circle(256,192,offset,true);
			}
			else if(rythmNext<=3 && rythmPrevious<=3) {
				hitObject+=new Circle(32+posX*64,96+posY*64,offset,newCombo);
				positionNext();
			}
			else if(rythmNext==4) {
				int n=0;
				boolean b=true;
				while(i+n<sortedMidiEvents.size()-1 && b) {
					if(getRythmNext(i+n)==4) n++;
					else b=false;
				}
				hitObject+=stackCircle(new Circle(32+posX*64,96+posY*64,offset,newCombo),i,n);
				positionNext();
				i+=n;
			}
			else {
				System.out.println(i + " : " + rythmNext);
				while(rythmNext!=1 && rythmNext!=2 && rythmNext!=3 && rythmNext!=4 && i<sortedMidiEvents.size()-1) {
					rythmNext=getRythmNext(i++);
				}
			}
		}
		return hitObject;
	}
	
	
	/**
	 * Function called by hitObjects() to create a specific pattern. In this case, it's a burst of n circles without any spacing
	 * Create a stack of n notes at the same position with the offset of currentNote and the n-1 following circle
	 * @return a String who will be used by hitObjects()
	 */
	public String stackCircle(Circle c,int currentNote, int n) {
		String res="";
		int offset;
		for(int i=0;i<=n;i++) {
			offset= Math.round(sortedMidiEvents.get(i+currentNote).getStartTick()*durationTick);
			res+= new Circle(c.getXPos(),c.getYPos(),offset,false);
		}
		return res;
	}

}
