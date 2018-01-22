package test;

import hitObject.Pair;
import pattern.*;

public class TestPattern {

	public static void main(String[] args) throws Exception {
		Pair<Double,Double> pos = new Pair<Double,Double>(100d,300d);
		
		Pattern pattern;
		/*Square*/
		//pattern = new PatternJumpPolygon(4, pos, Math.PI/4, 100d, false, 4);
		/*StreamCurve*/
		pattern = new PatternStreamCurve(12 , pos, -2*Math.PI/3, 2*Math.PI/3, 30);
		pattern.showGraph();
		
	}

}
