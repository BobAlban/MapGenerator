package pattern;

import java.util.ArrayList;
import java.util.List;
import hitObject.Pair;

public class PatternJumpPolygon extends PatternJump{
	
	double distanceObject;
	boolean clockwise;
	int nbPolygonSummit;
	
	public PatternJumpPolygon(int nbObjects, Pair<Double, Double> posFirstObject, double angle, double distanceObject, boolean clockwise, int nbPolygonSummit) {
		super(nbObjects, posFirstObject, angle);
		this.distanceObject=distanceObject;
		this.clockwise=clockwise;
		this.nbPolygonSummit=nbPolygonSummit;
		listPosition=listPosition();
	}
	
	public List<Pair<Double, Double>> listPosition() {
		List<Pair<Double, Double>> l = new ArrayList<Pair<Double,Double>>();
		l.add(posFirstObject);
		Pair<Double, Double> posLastObject;
		Pair<Double, Double> posNextObject;
		for(int i=0;i<nbObjects-1;i++) {
			posLastObject=l.get(l.size()-1);
			if(clockwise) {
				posNextObject = new Pair<Double, Double>(posLastObject.getLeft() + distanceObject*Math.cos(angle+(i*2*Math.PI/nbPolygonSummit)), posLastObject.getRight() + distanceObject*Math.sin(angle+(i*2*Math.PI/nbPolygonSummit)));
			}
			else {
				posNextObject = new Pair<Double, Double>(posLastObject.getLeft() + distanceObject*Math.cos(angle-(i*2*Math.PI/nbPolygonSummit)), posLastObject.getRight() + distanceObject*Math.sin(angle-(i*2*Math.PI/nbPolygonSummit)));
			}
			if(posNextObject.getLeft()<0 || posNextObject.getLeft()>512 || posNextObject.getRight()<0 || posNextObject.getRight()>384) {
				outOfScreen=true;
			}
			System.out.println(posNextObject);
			l.add(posNextObject);
		}
		return l;
	}	
}