package pattern;

import hitObject.Pair;

public abstract class PatternStream extends Pattern{
	
	Pair<Double,Double> posFinalObject;
	
	public PatternStream(int nbObjects, Pair<Double, Double> posFirstObject, Pair<Double,Double> posFinalObject) {
		super(nbObjects, posFirstObject);
		this.posFinalObject=posFinalObject;
	}
}
