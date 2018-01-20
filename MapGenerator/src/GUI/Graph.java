package GUI;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;

import hitObject.Pair;

public class Graph {

	List<Pair<Double,Double>> l;
	double sizeMultiplier;
	
	public Graph(List<Pair<Double,Double>> l, double sizeMultiplier) {
		this.l=l;
		this.sizeMultiplier=sizeMultiplier;
		JPanel panel = new GraphPanel(l,sizeMultiplier);
		panel.setPreferredSize(new Dimension((int)(sizeMultiplier*512),(int)(sizeMultiplier*384)));
		JFrame frame = new JFrame("Graph");
		frame.setContentPane(panel);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setLocation((int)(screenSize.getWidth()-(sizeMultiplier*512))/2,(int)(screenSize.getHeight()-(sizeMultiplier*384))/2);
		frame.pack();
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
