package pattern;

import java.util.ArrayList;
import java.util.List;

import hitObject.Pair;

public class PatternBurst extends Pattern {

	public PatternBurst(int nbObjects, Pair<Double,Double> posFirstObject) {
		super(nbObjects,posFirstObject);
		listPosition=listPosition();
	}

	public List<Pair<Double, Double>> listPosition() {
		listPosition = new ArrayList<Pair<Double,Double>>();
		for(int i=0;i<nbObjects;i++) {
			listPosition.add(new Pair<Double, Double>(posFirstObject.getLeft() + i, posFirstObject.getRight() + i));
		}
		if(listPosition.get(listPosition.size()-1).getLeft()<0 || listPosition.get(listPosition.size()-1).getLeft()>512 || listPosition.get(listPosition.size()-1).getRight()<0 || listPosition.get(listPosition.size()-1).getRight()>384) {
			outOfScreen=true;
		}
		return listPosition;
	}
}
