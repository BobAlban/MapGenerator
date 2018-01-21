package test;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.Random;

//import midiDecoder.*;


public class TestMidiDecoder {
	
	public static void main(String[] args) throws Exception{
		//new MidiInfo("D:\\programs files\\eclipse\\Workspace\\MapGenerator\\src\\test\\test.mid");
		
		//double r = Math.random()*2*Math.PI;
		//System.out.println("x = " + 100*Math.cos(r) + "\ny = " + 100*Math.sin(r));
		//System.out.println(180*Math.atan(100000)/Math.PI + "\n" + 180*Math.atan(-100000)/Math.PI);
		
		Random r = new Random();
		r.nextGaussian();
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		double width = screenSize.getWidth();
		double height = screenSize.getHeight();
		System.out.println(width);
		
	}
	
}