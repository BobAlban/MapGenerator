package pattern;

import java.util.LinkedList;
import java.util.List;

import hitObject.Pair;

public class PatternStreamCurve extends PatternStream{
	
	double angleCurve;
	double distanceObject;
	double angleStart;
	
	public PatternStreamCurve(int nbObjects, Pair<Double, Double> posFirstObject, double angleStart, double angleCurve, double distanceObject) {
		super(nbObjects, posFirstObject);
		this.angleStart=angleStart;
		this.angleCurve=angleCurve;
		this.distanceObject=distanceObject;
		listPosition=listPosition();
		System.out.println(toString());
	}

	public List<Pair<Double, Double>> listPosition() {
		listPosition = new LinkedList<Pair<Double,Double>>();
		double angleModification = angleCurve/nbObjects;
		listPosition.add(posFirstObject);
		Pair<Double,Double> posLastObject;
		Pair<Double,Double> posNextObject;
		for(int i=1;i<nbObjects;i++) {
			posLastObject=listPosition.get(listPosition.size()-1);
			posNextObject=new Pair<Double, Double>(posLastObject.getLeft() + distanceObject*Math.cos(angleStart + angleModification*i), posLastObject.getRight() + distanceObject*Math.sin(angleStart + angleModification*i));
			if(posNextObject.getLeft()<0 || posNextObject.getLeft()>512 || posNextObject.getRight()<0 || posNextObject.getRight()>384) {
				outOfScreen=true;
			}
			listPosition.add(posNextObject);
		}
		return listPosition;
	}
	
	public String toString() {
		return super.toString() +  "\nangleStart: " + angleStart*180/Math.PI + "\nangleCurve: " + angleCurve*180/Math.PI + "\ndistanceObject: " + distanceObject;
	}
}
