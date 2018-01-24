package test;

import hitObject.Pair;
import pattern.*;

public class TestPattern {

	public static void main(String[] args) throws Exception {
		Pair<Double,Double> pos = new Pair<Double,Double>(100d,300d);
		Pair<Double,Double> pos2 = new Pair<Double,Double>(152d,105d);
		
		Pattern pattern;
		/*Square*/
		//pattern = new PatternJumpPolygon(4, pos, Math.PI/4, 100d, false, 4);
		/*StreamCurve*/
		int nbObjects=12;
		double angleCurve=-3*Math.PI/4;
		pattern = new PatternStreamCurve(nbObjects , pos, 0, angleCurve, 20);
		pattern.showGraph();
		System.out.println(pattern.toString());
		pattern = new PatternStreamCurveAbsolute(nbObjects,pos,pattern.listPosition().get(pattern.listPosition().size()-1),angleCurve);
		pattern.showGraph();
		
	}

}
