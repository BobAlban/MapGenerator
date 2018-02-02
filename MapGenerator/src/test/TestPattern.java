package test;

import hitObject.Pair;
import pattern.*;

public class TestPattern {

	public static void main(String[] args) throws Exception {
		Pair<Double,Double> pos = new Pair<Double,Double>(100d,100d);
		Pair<Double,Double> pos2 = new Pair<Double,Double>(300d,300d);
		
		
		Pattern pattern;
		/*Square*/
		//pattern = new PatternJumpPolygon(4, pos, Math.PI/4, 100d, false, 4);
		/*StreamCurve*/
		/*
		int nbObjects=12;
		double angleCurve=2*Math.PI/3;
		pattern = new PatternStreamCurve(nbObjects , pos, 0, angleCurve, 30);
		pattern.showGraph();
		Pair<Double,Double> pos2 = pattern.getListPosition().get(nbObjects-1);
		System.out.println(pos2 + "\n______________");
		pattern = new PatternStreamCurveAbsolute(nbObjects,pos,pos2,angleCurve);
		pattern.showGraph();
		*/
		pattern = new PatternStreamDoubleAbsolute(20, pos, pos2, 2*Math.PI/3, true);
		pattern.showGraph();
	}

}
