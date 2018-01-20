package pattern;

import java.util.List;

import hitObject.Circle;

public interface Pattern {		//Create patterns, regardless of the midi file
	
	public String hitObjects();
	
	public List<Circle> listObjects();
	
}
