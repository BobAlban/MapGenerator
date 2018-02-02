package pattern;

import java.util.List;
import GUI.Graph;
import hitObject.Pair;

public abstract class Pattern {				//create patterns regardless of the midi file
	
	protected List<Pair<Double, Double>> listPosition;
	boolean outOfScreen;
	int nbObjects;
	Pair<Double,Double> posFirstObject;
	
	public Pattern(int nbObjects, Pair<Double,Double> posFirstObject) {
		this.nbObjects=nbObjects;
		this.posFirstObject=posFirstObject;
		outOfScreen=false;
	}
	
	public final boolean isOutOfScreen() {
		return outOfScreen;
	}
	
	public final void showGraph(String nameGraph) {
		new Graph(listPosition,1.2d,nameGraph);
	}
	
	public final List<Pair<Double, Double>> getListPosition() {
		return listPosition;
	}
	
	protected List<Pair<Double, Double>> listPosition() {
		return listPosition;
	}
	
	public String toString() {
		return "___PARAMETERS____________________\nNbObjects: " + nbObjects + "\nPosFirstObject: " + posFirstObject;
	}
}
