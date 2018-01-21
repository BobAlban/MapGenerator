package pattern;

import hitObject.Pair;

public abstract class PatternJump extends Pattern{

	double angle;							//clockwise
	
	public PatternJump(int nbObjects, Pair<Double,Double> posFirstObject, double angle) {
		super(nbObjects,posFirstObject);
		this.angle=angle;
	}	
	
}
