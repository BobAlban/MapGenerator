package test;

import hitObject.Pair;
import pattern.*;

public class TestPattern {

	public static void main(String[] args) throws Exception {
		Pair<Double,Double> pos = new Pair<Double,Double>(100d,100d);
		Pair<Double,Double> pos2 = new Pair<Double,Double>(300d,300d);
		Pattern pattern;
		
		//_______________________________________________________________________________________________//
		//		Square
		//pattern = new PatternJumpPolygon(4, pos, Math.PI/4, 100d, false, 4);
		//_______________________________________________________________________________________________//
		//		StreamCurve
		
		int nbObjects=12;
		double angleCurve=(0.5+Math.random())*Math.PI/2;
		System.out.println("\n___ORIGINAL______________________");
		pattern = new PatternStreamCurve(nbObjects , pos, angleCurve, -Math.random()*Math.PI/2, 30);
		pattern.showGraph("Original");
		pos2 = pattern.getListPosition().get(nbObjects-1);
		System.out.println(pos2 + "\n___IMMITATION____________________");
		pattern = new PatternStreamCurveAbsolute(nbObjects, pos, angleCurve, pos2);
		pattern.showGraph("Immitation");
		System.out.println(pattern.getListPosition().get(nbObjects-1));
		
		//_______________________________________________________________________________________________//
		//		StreamDoubleAbsolute
		/*
		pattern = new PatternStreamDoubleAbsolute(20, pos, 2*Math.PI/6, pos2, true);
		pattern.showGraph("Graph");
		*/
		//_______________________________________________________________________________________________//
		
	}

}
