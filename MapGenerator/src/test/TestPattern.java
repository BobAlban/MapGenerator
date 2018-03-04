package test;

import hitObject.Pair;
import pattern.*;

public class TestPattern {

	public static void main(String[] args) throws Exception {
		Pair<Double,Double> pos = new Pair<Double,Double>(100d,100d);
		Pair<Double,Double> pos2 = new Pair<Double,Double>(300d,300d);
		Pair<Double,Double> pos3 = new Pair<Double,Double>(200d,200d);
		Pattern pattern;
		
		//______Square___________________________________________________________________________________//
		
		//pattern = new PatternJumpPolygon(4, pos, Math.PI/4, 100d, false, 4);
		
		//______StreamCurve______________________________________________________________________________//
		
		int nbObjects=13;
		double angleCurve=Math.PI/(2);
		System.out.println("\n___ORIGINAL______________________");
		pattern = new PatternStreamCurve(nbObjects , pos, angleCurve, 0, 30);
		pattern.showGraph("Original");
		pos2 = pattern.getListPosition().get(nbObjects-1);
		System.out.println(pos2 + "\n___IMMITATION____________________");
		pattern = new PatternStreamCurveAbsolute(nbObjects, pos, angleCurve, pos2);
		pattern.showGraph("Immitation");
		System.out.println(pattern.getListPosition().get(nbObjects-1));
		
		//______StreamDoubleAbsolute______________________________________________________________________//
		/*
		int nbObjects = 20;
		double angleCurve = Math.PI/3;
		pattern = new PatternStreamDoubleAbsolute(nbObjects, pos, angleCurve, pos2, true);
		pattern.showGraph("Graph1");
		pattern = new PatternStreamDoubleAbsolute(nbObjects, pos, angleCurve, pos2, false);
		pattern.showGraph("Graph2");
		*/
		//_______________________________________________________________________________________________//
		
	}

}
