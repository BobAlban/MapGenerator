package Map;

import HitObjects.Circle;

public interface IMap {
	
	public String hitObjects();
	
	public void osuFileGeneration();
	
	public float getRythmNext(int n);
	
	public float getRythmPrevious(int n);
	
	public String stackCircle(Circle c,int currentNote, int n);

}
