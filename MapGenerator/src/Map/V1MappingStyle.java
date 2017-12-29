package Map;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;

import GUI.Graph;
import HitObjects.Circle;
import HitObjects.Pair;
import midiDecoder.MidiInfo;
import midiDecoder.Note;

public class V1MappingStyle implements IMap {

	// with 1.4x slider velocity and an absolute value of 100 for the slider length -> the slider last 1/2 beat
	protected String pathMidi;
	protected String pathMp3;
	protected double BPM;
	protected double sliderVelocity;
	protected ArrayList<Note> sortedMidiEvents;
	protected float durationTick;
	protected float resolution;
	
	public V1MappingStyle(String pathMidi,String pathMp3, double BPM) throws Exception {
		this.pathMidi=pathMidi;
		this.pathMp3=pathMp3;
		this.BPM=BPM;
		sliderVelocity=1.4;
		MidiInfo midiInfo = new MidiInfo(pathMidi);
		resolution=(float)midiInfo.getResolution();
		durationTick=midiInfo.getDurationTick();
		sortedMidiEvents=midiInfo.getSortedMidiEvents();
		osuFileGeneration();
	}

	public String hitObjects() {
		String res = new Circle(256,192,sortedMidiEvents.get(0).getStartTick(),true).toString();
		double angle = Math.random()*2*Math.PI;
		double r = 16800/(BPM*sliderVelocity*getRythmNext(0));
		float rythmNext;
		float rythmPrevious;
		int offset;
		boolean newCombo;
		Circle nextCircle = new Circle(256,192,0,true);
		Pair<Pair<Double,Double>,Pair<Double,Double>> pos;
		ArrayList<Pair<Pair<Double,Double>,Double>> listPosAngle= new ArrayList<Pair<Pair<Double,Double>,Double>>();
		
		try {
			if(getRythmNext(0)<=2) {
				Pair<Double,Double> nextPos = polarToCarthesian(new Pair<Double,Double>(56d, 192d), angle, r);
				res += new Circle(nextPos.getLeft(),nextPos.getRight(),sortedMidiEvents.get(0).getStartTick(),false);
				pos = new Pair<Pair<Double,Double>, Pair<Double,Double>>(new Pair<Double,Double>(256d,192d),new Pair<Double,Double>(nextPos.getLeft(),nextPos.getRight()));
			}
			else {
				int n = 0;
				while(n<sortedMidiEvents.size()-1) {
					if(getRythmNext(n)>3) n++;
					else break;
				}
				res += stackCircle(new Circle(256,192,sortedMidiEvents.get(1).getStartTick(),false),1,n);
				Pair<Double,Double> nextPos = polarToCarthesian(new Pair<Double,Double>(56d, 192d), angle, r);
				res += new Circle(nextPos.getLeft(),nextPos.getRight(),sortedMidiEvents.get(n+1).getStartTick(),false);
				pos = new Pair<Pair<Double,Double>, Pair<Double,Double>>(new Pair<Double,Double>(256d,192d), new Pair<Double,Double>(nextPos.getLeft(),nextPos.getRight()));
			}
			System.out.println("(338, 357)\n(373, 377)");
			System.out.println("_________________________");
			pos = new Pair<Pair<Double,Double>, Pair<Double,Double>>(new Pair<Double,Double>(338d,357d), new Pair<Double,Double>(373d,377d));
			double a = (double)(pos.getRight().getRight()-pos.getLeft().getRight())/(double)(pos.getRight().getLeft()-pos.getLeft().getLeft());
			double angleOfNextObject;
			Pair<Double,Double> nextPos;
			if(flowDirection(pos).getLeft())	angleOfNextObject = Math.atan(a);
			else								angleOfNextObject = Math.atan(-a);
			angleOfNextObject=positiveAngle(angleOfNextObject);
			for(int i=2;i<sortedMidiEvents.size()-1;i++) {
				if(getRythmPrevious(i)>4) i++;
				offset= Math.round(sortedMidiEvents.get(i).getStartTick()*durationTick);
				rythmNext=getRythmNext(i);
				rythmPrevious=getRythmPrevious(i);
				newCombo = getRythmPrevious(i)<=1;
				if(/*rythmNext<=2 && rythmPrevious<=2*/true ) {
					r = 16800/(BPM*sliderVelocity*rythmNext);
					a = (double)(pos.getRight().getRight()-pos.getLeft().getRight())/(double)(pos.getRight().getLeft()-pos.getLeft().getLeft());
					double b = pos.getRight().getRight()- a*pos.getRight().getLeft();
					double norm = Math.sqrt(Math.pow(pos.getRight().getLeft()-pos.getLeft().getLeft(), 2) + Math.pow(pos.getRight().getRight()-pos.getLeft().getRight(),2));
					char border = border(flowDirection(pos),a,b);
					double distanceFromEdge = distanceFromEdge(pos.getRight(), border, a, b, norm);
					angleOfNextObject = angleOfNextObject(angleOfNextObject,flowDirection(pos), pos,border,distanceFromEdge,a,b);
					nextPos = polarToCarthesian(pos.getRight(),angleOfNextObject,r);
					nextCircle = new Circle(nextPos.getLeft(),nextPos.getRight(),offset,newCombo);
				}
				/*else {
					just a paste of the if
				}*/
				/*debug*/ System.out.println(i + "/" + sortedMidiEvents.size() + ": (" + nextPos.getLeft().intValue() + ", " + nextPos.getRight().intValue() + ")" );
				Pair<Pair<Double,Double>,Double> posAngle = new Pair<Pair<Double,Double>,Double>(nextPos,angleOfNextObject);
				listPosAngle.add(posAngle);
				assert nextCircle.getXPos()>=0d	&& nextCircle.getXPos()<=512d && nextCircle.getYPos()>=0d && nextCircle.getYPos()<=384d : nextCircle;
				res += nextCircle;
				pos.setLeft(pos.getRight());
				pos.setRight(new Pair<Double,Double>(nextCircle.getXPos(),nextCircle.getYPos()));
			}
			System.out.println(res);
			return res;
		}
		catch(AssertionError e) {
			new Graph(listPosAngle,1.5d);
			throw e;
		}
		
	}

	public void osuFileGeneration() {
		try (PrintWriter p = new PrintWriter(new FileOutputStream(pathMp3, true))) {
		    p.print("osu file format v14\r\n\r\n[General]\r\nAudioFilename: audio.mp3\r\nAudioLeadIn: 0\r\nCountdown: 1\r\nSampleSet: Normal\r\nStackLeniency: 0.7\r\nMode: 0\r\nLetterboxInBreaks: 0\r\nWidescreenStoryboard: 1\r\n\r\n[Editor]\r\nDistanceSpacing: 1\r\nBeatDivisor: 4\r\nGridSize: 16\r\nTimelineZoom: 1\r\n\r\n[Metadata]\r\nTitle:\r\nTitleUnicode:\r\nArtist:\r\nArtistUnicode:\r\nCreator:evilmrcraft\r\nVersion:V0MappingStyle\r\nSource:\r\nTags:\r\nBeatmapID:0\r\nBeatmapSetID:-1\r\n\r\n[Difficulty]\r\nHPDrainRate:6\r\nCircleSize:4\r\nOverallDifficulty:8\r\nApproachRate:9\r\nSliderMultiplier:1.4\r\nSliderTickRate:1\r\n\r\n[Events]\r\n//Background and Video events\r\n//Break Periods\r\n//Storyboard Layer 0 (Background)\r\n//Storyboard Layer 1 (Fail)\r\n//Storyboard Layer 2 (Pass)\r\n//Storyboard Layer 3 (Foreground)\r\n//Storyboard Sound Samples\r\n\r\n\r\n[HitObjects]\r\n");
		    p.print(hitObjects());
		} catch (FileNotFoundException e1) {
		    e1.printStackTrace();
		}
	}
	
	/**
	 * return the angle of the next Object depending of the position of the current one.
	 * @param Pair<Pair<Integer,Integer>,Pair<Integer,Integer>> pos : a Pair with the position of the previous object in the left element and the position of the current 
	 * object in the right element
	 * @param double distanceFromEdge : the distance between lastPos and the edge of the map normalized by norm
	 * @param double a : coefficient of the equation y=ax+b
	 * @param double b : coefficient of the equation y=ax+b
	 * @return the angle of the next object
	 */
	public double angleOfNextObject(double currentAngle, Pair<Boolean,Boolean> flowDirection, Pair<Pair<Double,Double>,Pair<Double,Double>> pos,char border, double distanceFromEdge, double a, double b) {
		int sign = 0;
		/*debug*/ //System.out.print("pos : " + pos);
		/*debug*/ System.out.println();
		if		(((border=='U' || border=='u') && flowDirection.getLeft()) ||
				((border=='R' || border=='r') && !flowDirection.getRight()) ||
				((border=='D' || border=='d') && !flowDirection.getLeft()) ||
				((border=='L' || border=='l') && flowDirection.getRight())) {
			sign = 1;
		}
		else if(((border=='U' || border=='u') && !flowDirection.getLeft()) ||
				((border=='R' || border=='r') && flowDirection.getRight()) ||
				((border=='D' || border=='d') && flowDirection.getLeft()) ||
				((border=='L' || border=='l') && !flowDirection.getRight())) {
			sign = -1;
		}
		else {
			assert false : "intersectonWithBorder Exception";
		}
		if(Character.isLowerCase(border))			sign=4*(-sign);
		/*debug*/ if(flowDirection.getLeft())		System.out.print("1");
		/*debug*/ else								System.out.print("2");
		/*debug*/ System.out.println(") " + border + "; a: " + a + " ; " + flowDirection + " ; distanceFromEdge:" + distanceFromEdge);
		/*debug*/ System.out.println(" -> angle à ajouter: " + (sign*Math.abs(Math.atan(2/(3*distanceFromEdge)))*180/Math.PI) + 
				" ; angle originel: " + currentAngle*180/Math.PI + " ; angle final: " + 
				positiveAngle(currentAngle - sign*Math.abs(Math.atan(2/(3*distanceFromEdge))))*180/Math.PI);
		return positiveAngle(currentAngle - sign*Math.abs(Math.atan(2/(3*distanceFromEdge))));		//we substract instead of add because of the y axis
	}
	
	/**
	 * check if the angle given in parameter is between 0 and 2*pi, then add or substract 2*pi to obtain the coresponding angle between 0 and 2*pi 
	 * @param angle	: the angle to convert
	 * @return the converted angle between 0 and 2*pi
	 */
	public double positiveAngle(double angle) {
		/*debug*/ //System.out.println(angle*180/Math.PI);
		while(angle<0)							angle+=2*Math.PI;
		while(angle>=2*Math.PI)					angle-=2*Math.PI;
		if(angle<0 || angle>=2*Math.PI)			assert false : "angle must be beetween 0 and 360 degrees : " + angle*180/Math.PI;
		/*debug*/ //System.out.println(angle*180/Math.PI);
		return angle;
	}
	
	public Pair<Boolean,Boolean> flowDirection(Pair<Pair<Double,Double>,Pair<Double,Double>> pos){
		if(pos.getRight().getLeft()>=pos.getLeft().getLeft()) {			//x augmente
			if(pos.getRight().getRight()>=pos.getLeft().getRight())		return new Pair<Boolean,Boolean>(true,true);
			else														return new Pair<Boolean,Boolean>(true,false);
		}
		else {															//x diminue
			if(pos.getRight().getRight()>pos.getLeft().getRight())		return new Pair<Boolean,Boolean>(false,true);
			else														return new Pair<Boolean,Boolean>(false,false);
		}
	}
	
	/**
	 * CARE Y AXIS : BORDER DOWN IS THE BORDER UP FOR YOU IF THE Y AXIS GO UP FOR YOU 
	 * @param flowDirection
	 * @param a
	 * @param b
	 * @return
	 */
	public char border(Pair<Boolean,Boolean> flowDirection, double a, double b) {		
		/*debug*/ System.out.println(flowDirection + " ; a:" + a + " ; b:" + b);
		double c;
		double distanceFromCornerNotAllowed = 0;		//must be in [0;50[, percentage of the border near a corner not considered as the border (ex : 5 => 10% not allowed)
		double lengthXAxisCorner = 512*distanceFromCornerNotAllowed/100;
		double lengthYAxisCorner = 384*distanceFromCornerNotAllowed/100;
		//Search border with the corner constraint
		if(flowDirection.getLeft()) {
			c=512*a + b;
			if(c>=0+lengthYAxisCorner && c<=384-lengthYAxisCorner)		return 'R';
			else if(flowDirection.getRight()) {	//x et y augmentent
				c=(384-b)/a;
				if(c>=0+lengthXAxisCorner && c<=512-lengthXAxisCorner)	return 'U';
			}
			else {
				c=(0-b)/a;
				if(c>=0+lengthXAxisCorner && c<=512-lengthXAxisCorner)	return 'D';
			}
		}
		else {
			c=0*a + b;							//test left border
			if(c>=0+lengthYAxisCorner && c<=384-lengthYAxisCorner)		return 'L';
			else if(flowDirection.getRight()) {	//x diminue et y augmente
				c=(384-b)/a;
				if(c>=0+lengthXAxisCorner && c<=512-lengthXAxisCorner)	return 'U';
			}
			else {
				c=(0-b)/a;
				if(c>=0+lengthXAxisCorner && c<=512-lengthXAxisCorner)	return 'D';
			}
		}
		//Corner detected, search border without the corner constraint
		if(flowDirection.getLeft()) {
			c=512*a + b;
			if(c>=0 && c<=384)		return 'r';
			else if(flowDirection.getRight()) {	//x et y augmentent
				c=(384-b)/a;
				if(c>=0 && c<=512)	return 'u';
			}
			else {
				c=(0-b)/a;
				if(c>=0 && c<=512)	return 'd';
			}
		}
		else {
			c=0*a + b;							//test left border
			if(c>=0 && c<=384)		return 'l';
			else if(flowDirection.getRight()) {	//x diminue et y augmente
				c=(384-b)/a;
				if(c>=0 && c<=512)	return 'u';
			}
			else {
				c=(0-b)/a;
				if(c>=0 && c<=512)	return 'd';
			}
		}
		//Can't normally happen
		assert false : "intersectionWithBorder Exception ; flowDirection: " + flowDirection + " ; (a,b): " + a + b;
		return '0';
	}
	
	/**
	 * This function will find the point where the equation y=ax+b cross the edge of the map. Then, it will return the distance beetween the edge of the map and
	 * lastPos, normalized by norm.
	 * @param Pair<Integer,Integer> lastPos : the position of the last circle 
	 * @param double a : coefficient of the equation y=ax+b
	 * @param double b : coefficient of the equation y=ax+b
	 * @param double norm : the distance unit
	 * @return the distance between lastPos and the edge of the map normalized by norm
	 */
	public double distanceFromEdge(Pair<Double,Double> lastPos, char border, double a, double b, double norm){
		Pair<Double,Double> intersectonWithBorder = null;
		if(border=='U' || border=='u')			intersectonWithBorder = new Pair<Double,Double>((384d-b)/a,384d);
		else if(border=='R' || border=='r')		intersectonWithBorder = new Pair<Double,Double>(512d,512d*a + b);
		else if(border=='D' || border=='d')		intersectonWithBorder = new Pair<Double,Double>((0-b)/a,0d);
		else if(border=='L' || border=='l')		intersectonWithBorder = new Pair<Double,Double>(0d,0d*a + b);
		else					assert false : "intersectonWithBorder Exception";
		/*debug*/ //System.out.println("intersectonWithBorder : " + intersectonWithBorder );
		/*debug*/ //System.out.println("distanceFromEdge : " + Math.sqrt(Math.pow(intersectonWithBorder.getLeft()-lastPos.getLeft(), 2) + Math.pow(intersectonWithBorder.getRight()-lastPos.getRight(),2))/norm);
		return Math.sqrt(Math.pow(intersectonWithBorder.getLeft()-lastPos.getLeft(), 2) + Math.pow(intersectonWithBorder.getRight()-lastPos.getRight(),2))/norm;
	}
	
	public Pair<Double,Double> polarToCarthesian(Pair<Double,Double> pos, double angle, double r) {
		return new Pair<Double, Double>(pos.getLeft()+r*Math.cos(angle), pos.getRight()+r*Math.sin(angle));	
	}

	public float getRythmNext(int n) {
		assert n+1<sortedMidiEvents.size() : n + "/" + sortedMidiEvents.size();
		float startTickDifference=sortedMidiEvents.get(n+1).getStartTick()-sortedMidiEvents.get(n).getStartTick();
		return resolution/startTickDifference;
	}

	public float getRythmPrevious(int n) {
		assert n>0;
		float startTickDifference=sortedMidiEvents.get(n).getStartTick()-sortedMidiEvents.get(n-1).getStartTick();
		return resolution/startTickDifference;
	}

	public String stackCircle(Circle c, int currentNote, int n) {
		String res="";
		int offset;
		for(int i=0;i<=n;i++) {
			offset= Math.round(sortedMidiEvents.get(i+currentNote).getStartTick()*durationTick);
			res+= new Circle(c.getXPos(),c.getYPos(),offset,false);
		}
		return res;
	}
}
