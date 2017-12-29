package GUI;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JPanel;
import HitObjects.Pair;

public class Graph {

	List<Pair<Pair<Double,Double>,Double>> l;
	
	public Graph(List<Pair<Pair<Double,Double>,Double>> l) {
		this.l=l;
		JPanel panel = new GraphPanel(l);
		panel.setPreferredSize(new Dimension(1024,768));
		JFrame frame = new JFrame("Graph");
		frame.setContentPane(panel);
		frame.setLocation(171,0);
		frame.pack();
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
