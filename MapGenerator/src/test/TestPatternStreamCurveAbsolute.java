package test;

import java.util.Random;

import org.junit.Test;

import hitObject.Pair;
import pattern.Pattern;
import pattern.PatternStreamCurve;
import pattern.PatternStreamCurveAbsolute;

public class TestPatternStreamCurveAbsolute {

	public static void main(String[] args) throws Exception {
		test();
	}
	
	static public void test() {
		Random r = new Random();
		int fail = 0;
		int nbTests = 10000;
		for(int i=0;i<nbTests;i++) {
			int nbObjects = 1+r.nextInt(100);
			Pair<Double,Double> posStart = new Pair<Double,Double>(r.nextDouble()*512,r.nextDouble()*384);
			double angleCurve = r.nextDouble()*Math.PI;
			double angleStart = 2*r.nextDouble()*Math.PI;
			double distanceObject = r.nextDouble()*30;
			Pattern original = new PatternStreamCurve(nbObjects , posStart, angleCurve, angleStart, distanceObject);
			Pattern immitation = new PatternStreamCurveAbsolute(nbObjects, posStart, angleCurve, original.getListPosition().get(nbObjects-1));
			double errorDistance = errorDistance(original, immitation);
			if(errorDistance>50d) {
				fail++;
				System.err.println(errorDistance);
			}
			else {
				System.out.println(errorDistance);
			}
		}
		System.out.println("FAIL RATE: " + (fail/(double)nbTests)*100);
	}
	
	private static double errorDistance(Pattern p1, Pattern p2) {
		assert p1.getListPosition().size()==p2.getListPosition().size();
		int size = p1.getListPosition().size();
		Pair<Double,Double> p1Last = p1.getListPosition().get(size-1);
		Pair<Double,Double> p2Last = p2.getListPosition().get(size-1);
		return Math.sqrt(Math.pow(p2Last.getLeft()-p1Last.getLeft(), 2) + Math.pow(p2Last.getRight()-p1Last.getRight(), 2));
	}
	

}
