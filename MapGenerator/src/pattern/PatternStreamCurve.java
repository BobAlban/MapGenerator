package pattern;

import java.util.LinkedList;
import java.util.List;

import hitObject.Pair;

public class PatternStreamCurve extends PatternStream{
	
	double angleCurve;
	
	public PatternStreamCurve(int nbObjects, Pair<Double, Double> posFirstObject, Pair<Double, Double> posFinalObject, double angleCurve) {
		super(nbObjects, posFirstObject, posFinalObject);
		this.angleCurve=angleCurve;
		listPosition=listPosition();
	}

	public List<Pair<Double, Double>> listPosition() {
		listPosition = new LinkedList<Pair<Double,Double>>();
		listPosition.add(posFirstObject);
		listPosition.add(posFinalObject);	
		
		return listPosition;
	}
	
	public List<Pair<Double,Double>> listPositionRec(Pair<Double, Double> posFirstObject, Pair<Double, Double> posFinalObject, double angleCurve){
		
		
		
		
		
		return null;
	}
}
