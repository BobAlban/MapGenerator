package pattern;

import java.util.LinkedList;

import hitObject.Pair;

public class PatternStreamDoubleAbsolute extends PatternStream{
	
	Pair<Double, Double> posFinalObject;
	
	public PatternStreamDoubleAbsolute(int nbObjects, Pair<Double, Double> posFirstObject, double angleCurve, Pair<Double, Double> posFinalObject, boolean clockwise){
		super(nbObjects, posFirstObject,angleCurve);
		this.posFinalObject=posFinalObject;
		listPosition = new LinkedList<Pair<Double,Double>>();
		Pattern p1;
		Pattern p2;
		int nbObjectP1 = nbObjects/2;
		Pair<Double,Double> posMiddleObject = new Pair<Double,Double>(0.5*(posFirstObject.getLeft()+posFinalObject.getLeft()),0.5*(posFirstObject.getRight()+posFinalObject.getRight()));
		if(clockwise) {
			p1 = new PatternStreamCurveAbsolute(nbObjectP1, posFirstObject, Math.abs(angleCurve), posMiddleObject);
			p2 = new PatternStreamCurveAbsolute(nbObjects-nbObjectP1, posMiddleObject, -Math.abs(angleCurve), posFinalObject);
		}
		else {
			p1 = new PatternStreamCurveAbsolute(nbObjectP1, posFirstObject, -Math.abs(angleCurve), posMiddleObject);
			p2 = new PatternStreamCurveAbsolute(nbObjects-nbObjectP1, posMiddleObject, Math.abs(angleCurve), posFinalObject);
		}
		listPosition.addAll(p1.getListPosition());
		listPosition.addAll(p2.getListPosition());
	}

}
