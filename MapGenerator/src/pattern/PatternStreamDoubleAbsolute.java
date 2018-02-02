package pattern;

import java.util.LinkedList;

import hitObject.Pair;

public class PatternStreamDoubleAbsolute extends PatternStream{
	
	Pair<Double, Double> posFinalObject;
	double angleCurve;
	
	public PatternStreamDoubleAbsolute(int nbObjects, Pair<Double, Double> posFirstObject, Pair<Double, Double> posFinalObject, double angleCurve, boolean clockwise){
		super(nbObjects, posFirstObject);
		this.posFinalObject=posFinalObject;
		this.angleCurve=angleCurve;
		listPosition = new LinkedList<Pair<Double,Double>>();
		Pattern p1;
		Pattern p2;
		int nbObjectP1 = nbObjects/2;
		Pair<Double,Double> posMiddleObject = new Pair<Double,Double>(0.5*(posFirstObject.getLeft()+posFinalObject.getLeft()),0.5*(posFirstObject.getRight()+posFinalObject.getRight()));
		if(clockwise) {
			p1 = new PatternStreamCurveAbsolute(nbObjectP1, posFirstObject, posMiddleObject, Math.abs(angleCurve));
			p2 = new PatternStreamCurveAbsolute(nbObjects-nbObjectP1, posMiddleObject, posFinalObject, -Math.abs(angleCurve));
		}
		else {
			p1 = new PatternStreamCurveAbsolute(nbObjectP1, posFirstObject, posMiddleObject, -Math.abs(angleCurve));
			p2 = new PatternStreamCurveAbsolute(nbObjects-nbObjectP1, posMiddleObject, posFinalObject, Math.abs(angleCurve));
		}
		listPosition.addAll(p1.getListPosition());
		listPosition.addAll(p2.getListPosition());
	}

}
