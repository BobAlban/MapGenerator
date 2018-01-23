package test;

import hitObject.Pair;

public class Test {
	
	public static void main(String[] args) throws Exception{
		//new MidiInfo("D:\\programs files\\eclipse\\Workspace\\MapGenerator\\src\\test\\test.mid");
		
		//double r = Math.random()*2*Math.PI;
		//System.out.println("x = " + 100*Math.cos(r) + "\ny = " + 100*Math.sin(r));
		//System.out.println(180*Math.atan(100000)/Math.PI + "\n" + 180*Math.atan(-100000)/Math.PI);
		Pair<Double,Double> posFirstObject = new Pair<Double,Double>(100d,300d);
		Pair<Double,Double> posFinalObject = new Pair<Double,Double>(240d,55d);
		double angleCurve = 2*Math.PI/3;
		System.out.println(getCenter(posFirstObject,posFinalObject,angleCurve));
		
	}
	
	public static Pair<Double,Double> getCenter(Pair<Double, Double> posFirstObject, Pair<Double, Double> posFinalObject, double angleCurve){
		double distanceFirstFinalObject=Math.sqrt(Math.pow(posFinalObject.getLeft()-posFirstObject.getLeft(), 2) + Math.pow(posFinalObject.getRight()-posFirstObject.getRight(), 2));
		double angleFinalObject=0.5*(Math.PI-angleCurve);
		double diameter=distanceFirstFinalObject/Math.cos(angleFinalObject);
		double angleDiameter = angleCurve - angleFinalObject;
		System.out.println("distanceFirstFinalObject: " + distanceFirstFinalObject + "\ndiameter: " + diameter + "\nangleFinalObject:" + angleFinalObject*180/Math.PI + "\nangleDiameter:" + angleDiameter*180/Math.PI);
		return new Pair<Double,Double>(posFinalObject.getLeft() + 0.5*diameter*Math.cos(angleDiameter),posFinalObject.getRight() + 0.5*diameter*Math.sin(angleDiameter));
	}
	
}