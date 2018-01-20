package pattern;

import hitObject.Pair;

public abstract class PatternJump implements Pattern{

	int nbObjects;
	Pair<Double,Double> posFirstObject;
	double angle;							//clockwise
	
	public PatternJump(int nbObjects, Pair<Double,Double> posFirstObject, double angle) {
		this.nbObjects=nbObjects;
		this.posFirstObject=posFirstObject;
		this.angle=angle;
	}	
	
}
