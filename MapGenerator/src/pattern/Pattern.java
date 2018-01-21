package pattern;

import java.util.List;
import GUI.Graph;
import hitObject.Pair;

public abstract class Pattern {				//create patterns regardless of the midi file
	
	List<Pair<Double, Double>> listPosition;
	boolean outOfScreen;
	int nbObjects;
	Pair<Double,Double> posFirstObject;
	
	public Pattern(int nbObjects, Pair<Double,Double> posFirstObject) {
		this.nbObjects=nbObjects;
		this.posFirstObject=posFirstObject;
		outOfScreen=false;
	}
	
	public abstract List<Pair<Double,Double>> listPosition();
	
	public boolean isOutOfScreen() {
		return outOfScreen;
	}
	
	public void showGraph() {
		new Graph(listPosition,1.5d);
	}
}
