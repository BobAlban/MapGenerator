package pattern;

import hitObject.Pair;

public class PatternStreamCurveAbsolute extends Pattern{
	/*
	public PatternStreamCurveAbsolute(int nbObjects, Pair<Double, Double> posFirstObject, double angleStart, double angleCurve, double distanceObject) {
		super(nbObjects, posFirstObject, angleStart, angleCurve, distanceObject);
		// TODO Auto-generated constructor stub
	}*/
	
	Pair<Double, Double> posFinalObject;
	double angleCurve;
	
	public PatternStreamCurveAbsolute(int nbObjects, Pair<Double, Double> posFirstObject, Pair<Double, Double> posFinalObject, double angleCurve) {
		super(nbObjects,posFirstObject);
		this.posFinalObject=posFinalObject;
		this.angleCurve=angleCurve;
		Pair<Double,Double> posCenter = getCenter();
		System.out.println("posCenter : " + posCenter + " ;posFirstObject : " + posFirstObject + " ;posFinalObject : " + posFinalObject);
		/*WARNING*/
		double angleStart=Math.atan(-1/(posCenter.getRight()-posFirstObject.getRight())/(posCenter.getLeft()-posFirstObject.getLeft()));
		System.out.println("angleFirstCenter : " + Math.atan(posCenter.getRight()-posFirstObject.getRight())/(posCenter.getLeft()-posFirstObject.getLeft())*180/Math.PI);
		System.out.println("angleFirstFinal : " + Math.atan(posFinalObject.getRight()-posFirstObject.getRight())/(posFinalObject.getLeft()-posFirstObject.getLeft())*180/Math.PI);
		System.out.println("angleFinalCenter : " + Math.atan(posCenter.getRight()-posFinalObject.getRight())/(posCenter.getLeft()-posFinalObject.getLeft())*180/Math.PI);
		System.out.println("angleStart : " + angleStart*180/Math.PI);
		double distanceObject=getDiameter()*Math.sin(angleCurve/(2*nbObjects));
		Pattern p = new PatternStreamCurve(nbObjects, posFirstObject, angleStart, angleCurve, distanceObject);
		listPosition=p.listPosition();
		
	}
	
	private double getDiameter() {
		double distanceFirstFinalObject=Math.sqrt(Math.pow(posFinalObject.getLeft()-posFirstObject.getLeft(), 2) + Math.pow(posFinalObject.getRight()-posFirstObject.getRight(), 2));
		return distanceFirstFinalObject/Math.cos(0.5*(Math.PI-angleCurve));
	}
	
	private Pair<Double,Double> getCenter(){
		Pair<Double,Double> posMiddle = new Pair<Double,Double>((posFirstObject.getLeft()+posFinalObject.getLeft())/2,(posFirstObject.getRight()+posFinalObject.getRight())/2);
		double radius=0.5*getDiameter();
		double distanceFirstFinalObject=Math.sqrt(Math.pow(posFinalObject.getLeft()-posFirstObject.getLeft(), 2) + Math.pow(posFinalObject.getRight()-posFirstObject.getRight(), 2));
		double curve=Math.sqrt(Math.pow(radius/(distanceFirstFinalObject), 2)-0.25);
		return new Pair<Double,Double>(posMiddle.getLeft()+curve*(posFinalObject.getRight()-posFirstObject.getRight()),posMiddle.getRight()-curve*(posFinalObject.getLeft()-posFirstObject.getLeft()));
	}
}
