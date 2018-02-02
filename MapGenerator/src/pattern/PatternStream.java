package pattern;

import hitObject.Pair;

public abstract class PatternStream extends Pattern{
	
	double angleCurve;
	
	public PatternStream(int nbObjects, Pair<Double, Double> posFirstObject, double angleCurve) {
		super(nbObjects, posFirstObject);
		this.angleCurve=angleCurve;
	}
	
	public String toString() {
		return super.toString() + "\nAngleCurve: " + angleCurve*180/Math.PI;
	}
}
