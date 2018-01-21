package test;

import hitObject.Pair;
import pattern.*;

public class TestPattern {

	public static void main(String[] args) throws Exception {
		Pair<Double,Double> pos = new Pair<Double,Double>(200d,200d);
		
		Pattern pattern;
		/*Square*/ //	pattern = new PatternJumpPolygon(4, pos, Math.PI/4, 100d, false, 4);
		/*Burst*/  		pattern = new PatternBurst(3,pos);
		pattern.showGraph();
		
	}

}
