package pattern;

import java.util.List;

import hitObject.Pair;

public class PatternStreamCurveAbsolute extends Pattern{
	/*
	public PatternStreamCurveAbsolute(int nbObjects, Pair<Double, Double> posFirstObject, double angleStart, double angleCurve, double distanceObject) {
		super(nbObjects, posFirstObject, angleStart, angleCurve, distanceObject);
		// TODO Auto-generated constructor stub
	}*/
	
	Pair<Double, Double> posFinalObject;
	double angleCurve;
	
	
	//angle curve is different of superclass's angle curve
	public PatternStreamCurveAbsolute(int nbObjects, Pair<Double, Double> posFirstObject, Pair<Double, Double> posFinalObject, double angleCurve) {
		super(nbObjects,posFirstObject);
		this.posFinalObject=posFinalObject;
		this.angleCurve=angleCurve;
		Pair<Double,Double> posCenter = getCenter();
		double angleStart=Math.PI-Math.atan((posCenter.getRight()-posFirstObject.getRight())/(posCenter.getLeft()-posFirstObject.getLeft()));
		double distanceObject=0.5*getDiameter()*angleCurve/nbObjects;
		Pattern p = new PatternStreamCurve(nbObjects, posFirstObject, angleStart, angleCurve, distanceObject);
		listPosition=p.listPosition();
		
	}
	
	private double getDiameter() {
		double distanceFirstFinalObject=Math.sqrt(Math.pow(posFinalObject.getLeft()-posFirstObject.getLeft(), 2) + Math.pow(posFinalObject.getRight()-posFirstObject.getRight(), 2));
		return distanceFirstFinalObject/Math.cos(0.5*(Math.PI-angleCurve));
	}
	
	private Pair<Double,Double> getCenter(){
		double distanceFirstFinalObject=Math.sqrt(Math.pow(posFinalObject.getLeft()-posFirstObject.getLeft(), 2) + Math.pow(posFinalObject.getRight()-posFirstObject.getRight(), 2));
		double angleFinalObject=0.5*(Math.PI-angleCurve);
		double diameter=distanceFirstFinalObject/Math.cos(angleFinalObject);
		double angleDiameter = angleCurve - angleFinalObject;
		System.out.println("distanceFirstFinalObject: " + distanceFirstFinalObject + "\ndiameter: " + diameter + "\nangleFinalObject:" + angleFinalObject*180/Math.PI + "\nangleDiameter:" + angleDiameter*180/Math.PI);
		return new Pair<Double,Double>(posFinalObject.getLeft() + 0.5*diameter*Math.cos(angleDiameter),posFinalObject.getRight() + 0.5*diameter*Math.sin(angleDiameter));
	}

	public List<Pair<Double, Double>> listPosition() {
		return listPosition;
	}	
}
