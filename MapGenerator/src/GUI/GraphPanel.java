package GUI;

import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import HitObjects.Pair;

public class GraphPanel extends JPanel{

	List<Pair<Pair<Double,Double>,Double>> l;

	public GraphPanel(List<Pair<Pair<Double,Double>,Double>> l) {
		this.l=l;
	}
	
	@Override
	public void paintComponent(Graphics g){
		//Draw the graph
		for(int i=1;i<=10;i++) {
			for(int j=0;j<96;j=j+2) {
				g.drawLine(2*50*i, 2*4*j, 2*50*i, 2*4*(j+1));	//(x1, y1, x2, y2)
			}
		}
		for(int i=1;i<=7;i++) {
			for(int j=0;j<128;j=j+2) {
				g.drawLine(2*4*j, 2*50*i, 2*4*(j+1), 2*50*i);
			}
		}
		g.drawLine(0, 768, 1024, 768);
		g.drawLine(1024, 768, 1024, 0);

		//Draw the circles
		try {
			int hitCircleSize = 37;			//closest int from cs4 radius (if the in-game radius is well interpreted)
			int numberCircle=25;			//number of circles to show on the GUI
			int indexCircle=l.size()-numberCircle;
			//int indexCircle=0;
			Image img = ImageIO.read(new File("hitcircle.png"));
			if(indexCircle==0){
				g.drawLine(2*338-2, 2*357-2, 2*338+2, 2*357+2);
				g.drawLine(2*338-2, 2*357+2, 2*338+2, 2*357-2);
				g.drawImage(img, 2*(338-hitCircleSize/2), 2*(357-hitCircleSize/2), 2*hitCircleSize, 2*hitCircleSize, this);
				g.drawString("0: (" + 338 + ", " + 357 + ")", 2*(338-hitCircleSize/2), 2*(357-hitCircleSize/2));
				g.drawLine(2*373-2, 2*377-2, 2*373+2, 2*377+2);
				g.drawLine(2*373-2, 2*377+2, 2*373+2, 2*377-2);
				g.drawImage(img, 2*(373-hitCircleSize/2), 2*(377-hitCircleSize/2), 2*hitCircleSize, 2*hitCircleSize, this);
				g.drawString("1: (" + 373 + ", " + 377 + ")", 2*(373-hitCircleSize/2), 2*(377-hitCircleSize/2));
			}
			for(int i=indexCircle;i<numberCircle+indexCircle;i++) {
				double xPos=l.get(i).getLeft().getLeft();
				double yPos=l.get(i).getLeft().getRight();
				g.drawLine(2*(int)xPos-2, 2*(int)yPos-2, 2*(int)xPos+2, 2*(int)yPos+2);
				g.drawLine(2*(int)xPos-2, 2*(int)yPos+2, 2*(int)xPos+2, 2*(int)yPos-2);
				g.drawImage(img, 2*((int)xPos-hitCircleSize/2), 2*((int)yPos-hitCircleSize/2), 2*hitCircleSize, 2*hitCircleSize, this);
				g.drawString(i+2 + ": (" + (int)xPos + ", " + (int)yPos + ") Angle: " + (int)Math.toDegrees(l.get(i).getRight()), 2*((int)xPos-hitCircleSize/2), 2*((int)yPos-hitCircleSize/2));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
