package HitObjects;

import java.util.ArrayList;

public class Slider implements HitObject {
	
	private ArrayList<Pair<Double,Double>> pos;
	private int offset;
	private int objectInfo;
	private char sliderType;
	private int reverseArrow; //number of reverse arrows + 1
	private float sliderVelocity;
	private float sliderBeatLength; //sliderVelocity*10 -> 1 beat length slider ; sliderVelocity*5 ->1/2 beat length slider
	
	public Slider(ArrayList<Pair<Double,Double>> pos, int offset, boolean newCombo, char sliderType, int reverseArrow, float sliderVelocity, float sliderBeatLength) {
		this.pos=pos;
		this.offset=offset;
		if(newCombo) objectInfo=6;
		else objectInfo=2;
		this.sliderType=sliderType;
		this.reverseArrow=reverseArrow;
		this.sliderVelocity=sliderVelocity;
		this.sliderBeatLength=sliderBeatLength;
	}

	public ArrayList<Pair<Double,Double>> getPos() {
		return pos;
	} 

	public char getSliderType() { 					// P=Passthrough ; L=Linear ; C = Catmull ; B = Bezier
		return sliderType;
	}

	public int getOffset() {
		return offset;
	}

	public int getObjectInfo() {
		return objectInfo;
	}

	public int getReverseArrow() {
		return reverseArrow;
	}

	public float getSliderVelocity() {
		return sliderVelocity;
	}

	public float getSliderBeatLength() {
		return sliderBeatLength;
	}
	
	public String toString() {
		String res= "\n" + pos.get(0).getLeft() + "," + pos.get(0).getRight() + "," + offset + "," + objectInfo + ",0," + sliderType;
		for(int i=1;i<pos.size();i++) {
			res+= "|" + pos.get(i).getLeft() + ":" + pos.get(i).getRight();
		}
		return res + "," + reverseArrow + "," + (int)((sliderVelocity*100f)/sliderBeatLength) + "\r\n";
	}
	
	public boolean isNewCombo() {
		if(objectInfo==6) return true; 
		else return false;
	}
	
	
	
	/*
	 * examples:
	 * 96,128,0,6,0,L|240:128,2,900					//slider tres long (900) 
	 * 96,128,2206,2,0,L|432:128,1,140				//140 : 1 beat long slider because of 1.4 slider velocity
	 * 192,192,0,6,0,L|336:192,1,140
	 * 192,96,827,2,0,L|336:96,163,140				//162 reverse arrows
	 * 32,256,2068,2,0,C|112:304|160:272,1,140		//angle avec arrondi
	 * 288,256,2068,2,0,L|368:304|416:272,1,140		//angle sans arrondi
	 * 288,64,2068,2,0,P|368:112|416:80,1,140		//courbure parfaite
	 * 32,64,3931,2,0,B|464:64|464:64|464:304|464:304|288:304|320:192|128:240|272:304|256:336,1,980 //exemple de B :2x le même point -> L  1x le même point -> P
	 * 
	 * 
	 * 
	 * WARNING:
	 * 96,128,1655,6,0,L|256:128,1,140
	 * 96,192,1655,1,0,0:0:0:0:
	 * 240,192,2068,1,0,0:0:0:0:
	 * cercle au début et à la fin du slider MAIS sliderBeatLength != c2.getOffset() - c1.getOffset()
	 * 140 != 2068 - 1655
	 * Offset fin de slider : offset au debut + 2.95* sliderBeatLength
	 */
	
}
