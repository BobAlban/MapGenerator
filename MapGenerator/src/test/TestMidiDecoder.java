package test;

//import midiDecoder.*;


public class TestMidiDecoder {
	
	public static void main(String[] args) throws Exception{
		//new MidiInfo("D:\\programs files\\eclipse\\Workspace\\MapGenerator\\src\\test\\test.mid");
		
		//double r = Math.random()*2*Math.PI;
		//System.out.println("x = " + 100*Math.cos(r) + "\ny = " + 100*Math.sin(r));
		//System.out.println(180*Math.atan(100000)/Math.PI + "\n" + 180*Math.atan(-100000)/Math.PI);
		
		double angle = -Math.PI;
		if(angle<0) {
			angle+=Math.PI*2;
		}
		System.out.println("angle: " + angle/Math.PI);
		
	}
	
}