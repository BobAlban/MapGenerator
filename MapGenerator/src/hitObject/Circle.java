package HitObjects;

import java.util.ArrayList;

public class Circle implements HitObject {
	
	private Pair<Double,Double> pos;
	private int offset;
	private int objectInfo;
	
	public Circle(double xPos, double yPos, int offset, boolean newCombo) {
	pos = new Pair<Double,Double>(xPos,yPos);
		this.offset=offset;
		if(newCombo){
			objectInfo=5;
		}
		else {
			objectInfo=1;
		}
		
	}
	
	public ArrayList<Pair<Double,Double>> getPos(){
		ArrayList<Pair<Double,Double>> l = new ArrayList<Pair<Double,Double>>();
		l.add(pos);
		return l;
	}

	public double getXPos() {
		return pos.getLeft();
	}

	public double getYPos() {
		return pos.getRight();
	}

	public int getOffset() {
		return offset;
	}

	public int getObjectInfo() {
		return objectInfo;
	}
	
	public String toString() {
		String res = "\n" + getXPos() + "," + getYPos() + "," + offset + "," + objectInfo + ",0,0:0:0:0:\r\n";
		return res;
	}

	public boolean isNewCombo() {
		if(objectInfo==5) {
			return true;
		}
		else return false;
	}
	
	

}
