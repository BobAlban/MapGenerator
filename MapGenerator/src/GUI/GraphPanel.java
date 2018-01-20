package GUI;

import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import hitObject.Pair;

public class GraphPanel extends JPanel{

	List<Pair<Double,Double>> l;
	double sizeMultiplier;

	public GraphPanel(List<Pair<Double,Double>> l, double sizeMultiplier) {
		this.l=l;
		this.sizeMultiplier=sizeMultiplier;
	}

	@Override
	public void paintComponent(Graphics g){
		//Draw the graph
		for(int i=1;i<=10;i++) {
			for(int j=0;j<96;j=j+2) {
				g.drawLine((int)(sizeMultiplier*50*i), (int)(sizeMultiplier*4*j), (int)(sizeMultiplier*50*i), (int)(sizeMultiplier*4*(j+1)));	//(x1, y1, x2, y2)
			}
		}
		for(int i=1;i<=7;i++) {
			for(int j=0;j<128;j=j+2) {
				g.drawLine((int)(sizeMultiplier*4*j), (int)(sizeMultiplier*50*i), (int)(sizeMultiplier*4*(j+1)), (int)(sizeMultiplier*50*i));
			}
		}
		g.drawLine(0, (int)(sizeMultiplier*384), (int)(sizeMultiplier*512), (int)(sizeMultiplier*384));
		g.drawLine((int)(sizeMultiplier*512), (int)(sizeMultiplier*384), (int)(sizeMultiplier*512), 0);

		//Draw the circles
		try {
			int hitCircleSize = 37;			//closest int from cs4 radius (if the in-game radius is well interpreted)
			Image img = ImageIO.read(new File("hitcircle.png"));
			for(int i=0;i<l.size();i++) {
				double xPos=l.get(i).getLeft();
				double yPos=l.get(i).getRight();
				g.drawLine((int)(sizeMultiplier*(int)xPos-2), (int)(sizeMultiplier*(int)yPos-2), (int)(sizeMultiplier*(int)xPos+2), (int)(sizeMultiplier*(int)yPos+2));
				g.drawLine((int)(sizeMultiplier*(int)xPos-2), (int)(sizeMultiplier*(int)yPos+2), (int)(sizeMultiplier*(int)xPos+2), (int)(sizeMultiplier*(int)yPos-2));
				g.drawImage(img, (int)(sizeMultiplier*((int)xPos-hitCircleSize/2)), (int)(sizeMultiplier*((int)yPos-hitCircleSize/2)), (int)(sizeMultiplier*hitCircleSize), (int)(sizeMultiplier*hitCircleSize), this);
				g.drawString(i + ": (" + (int)xPos + ", " + (int)yPos + ")", (int)(sizeMultiplier*((int)xPos-hitCircleSize/2)), (int)(sizeMultiplier*((int)yPos-hitCircleSize/2)));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
