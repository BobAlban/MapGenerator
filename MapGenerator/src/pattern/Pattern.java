package pattern;

import java.util.List;
import hitObject.Pair;

public interface Pattern {				//create patterns regardless of the midi file
	
	public List<Pair<Double,Double>> listPosition();
	
	public boolean isOutOfScreen();
	
	public void showGraph();
}
