package pattern;

import hitObject.Pair;

public class PatternStreamCurveAbsolute extends PatternStreamCurve{
	/*
	public PatternStreamCurveAbsolute(int nbObjects, Pair<Double, Double> posFirstObject, double angleStart, double angleCurve, double distanceObject) {
		super(nbObjects, posFirstObject, angleStart, angleCurve, distanceObject);
		// TODO Auto-generated constructor stub
	}*/
	
	double angleStart;
	
	//angle curve is different of superclass's angle curve
	public PatternStreamCurveAbsolute(int nbObjects, Pair<Double, Double> posFirstObject, Pair<Double, Double> posFinalObject, double streamCurve) {
		angleStart = Math.atan((posFinalObject.getRight()-posFirstObject.getRight())/(posFinalObject.getLeft()-posFirstObject.getLeft()));
		double nbObjectWholeCircle = nbObjects * (2*Math.PI/(streamCurve%(2*Math.PI)));
		
	}
	
}
