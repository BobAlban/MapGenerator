package hitObject;

import java.util.ArrayList;

public interface HitObject {
	
	public ArrayList<Pair<Double,Double>> getPos();
	
	public int getOffset();
	
	public int getObjectInfo(); // 1=circle ; 2=slider ; 5=new combo circle ; 6=new combo slider ; 12 = slider
	
	public boolean isNewCombo();

}
