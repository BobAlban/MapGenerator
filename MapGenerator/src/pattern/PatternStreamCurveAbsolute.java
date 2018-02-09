package pattern;

import hitObject.Pair;

public class PatternStreamCurveAbsolute extends PatternStream{

	Pair<Double, Double> posFinalObject;
	
	public PatternStreamCurveAbsolute(int nbObjects, Pair<Double, Double> posFirstObject, double angleCurve, Pair<Double, Double> posFinalObject) {
		super(nbObjects,posFirstObject,angleCurve);
		this.posFinalObject=posFinalObject;
		Pair<Double,Double> posCenter = getCenter();
		System.out.println("PosCenter: " + posCenter);
		System.out.println("Coef: " + -1/((posCenter.getRight()-posFirstObject.getRight())/(posCenter.getLeft()-posFirstObject.getLeft())));
		double angleStart=Math.atan(-1/((posCenter.getRight()-posFirstObject.getRight())/(posCenter.getLeft()-posFirstObject.getLeft())));
		double distanceObject=getDiameter()*Math.sin(angleCurve/(2*nbObjects));
		Pattern p = new PatternStreamCurve(nbObjects, posFirstObject, angleCurve, angleStart, distanceObject);
		System.out.println(toString());
		listPosition=p.listPosition();
		
		
		/*	//DEBUG
		System.out.println("posCenter : " + posCenter + " ;posFirstObject : " + posFirstObject + " ;posFinalObject : " + posFinalObject);
		System.out.println("angleFirstCenter : " + Math.atan(posCenter.getRight()-posFirstObject.getRight())/(posCenter.getLeft()-posFirstObject.getLeft())*180/Math.PI);
		System.out.println("angleFirstFinal : " + Math.atan(posFinalObject.getRight()-posFirstObject.getRight())/(posFinalObject.getLeft()-posFirstObject.getLeft())*180/Math.PI);
		System.out.println("angleFinalCenter : " + Math.atan(posCenter.getRight()-posFinalObject.getRight())/(posCenter.getLeft()-posFinalObject.getLeft())*180/Math.PI);
		System.out.println("angleStart : " + angleStart*180/Math.PI);
		*/
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
		return new Pair<Double,Double>(posMiddle.getLeft()-(angleCurve/Math.abs(angleCurve))*curve*(posFinalObject.getRight()-posFirstObject.getRight()),posMiddle.getRight()+(angleCurve/Math.abs(angleCurve))*curve*(posFinalObject.getLeft()-posFirstObject.getLeft()));
	}
	
	public String toString() {
		return super.toString() + "\nPosFinalObject: " + posFinalObject;
	}
}
