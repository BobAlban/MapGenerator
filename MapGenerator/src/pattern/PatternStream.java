package pattern;

import hitObject.Pair;

public abstract class PatternStream extends Pattern{
	
	double angleStart;
	
	public PatternStream(int nbObjects, Pair<Double, Double> posFirstObject, double angleStart) {
		super(nbObjects, posFirstObject);
		this.angleStart=angleStart;
	}
}
