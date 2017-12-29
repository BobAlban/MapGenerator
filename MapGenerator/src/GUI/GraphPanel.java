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
	double sizeMultiplier;

	public GraphPanel(List<Pair<Pair<Double,Double>,Double>> l, double sizeMultiplier) {
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
			int numberCircle=25;			//number of circles to show on the GUI
			int indexCircle=l.size()-numberCircle;
			Image img = ImageIO.read(new File("hitcircle.png"));
			if(indexCircle==0){
				g.drawLine((int)(sizeMultiplier*338-2), (int)(sizeMultiplier*357-2), (int)(sizeMultiplier*338+2), (int)(sizeMultiplier*357+2));
				g.drawLine((int)(sizeMultiplier*338-2), (int)(sizeMultiplier*357+2), (int)(sizeMultiplier*338+2), (int)(sizeMultiplier*357-2));
				g.drawImage(img, (int)(sizeMultiplier*(338-hitCircleSize/2)), (int)(sizeMultiplier*(357-hitCircleSize/2)), (int)(sizeMultiplier*hitCircleSize), (int)(sizeMultiplier*hitCircleSize), this);
				g.drawString("0: (" + 338 + ", " + 357 + ")", (int)(sizeMultiplier*(338-hitCircleSize/2)), (int)(sizeMultiplier*(357-hitCircleSize/2)));
				g.drawLine((int)(sizeMultiplier*373-2), (int)(sizeMultiplier*377-2), (int)(sizeMultiplier*373+2), (int)(sizeMultiplier*377+2));
				g.drawLine((int)(sizeMultiplier*373-2), (int)(sizeMultiplier*377+2), (int)(sizeMultiplier*373+2), (int)(sizeMultiplier*377-2));
				g.drawImage(img, (int)(sizeMultiplier*(373-hitCircleSize/2)), (int)(sizeMultiplier*(377-hitCircleSize/2)), (int)(sizeMultiplier*hitCircleSize), (int)(sizeMultiplier*hitCircleSize), this);
				g.drawString("1: (" + 373 + ", " + 377 + ")", (int)(sizeMultiplier*(373-hitCircleSize/2)), (int)(sizeMultiplier*(377-hitCircleSize/2)));
			}
			for(int i=indexCircle;i<numberCircle+indexCircle;i++) {
				double xPos=l.get(i).getLeft().getLeft();
				double yPos=l.get(i).getLeft().getRight();
				g.drawLine((int)(sizeMultiplier*(int)xPos-2), (int)(sizeMultiplier*(int)yPos-2), (int)(sizeMultiplier*(int)xPos+2), (int)(sizeMultiplier*(int)yPos+2));
				g.drawLine((int)(sizeMultiplier*(int)xPos-2), (int)(sizeMultiplier*(int)yPos+2), (int)(sizeMultiplier*(int)xPos+2), (int)(sizeMultiplier*(int)yPos-2));
				g.drawImage(img, (int)(sizeMultiplier*((int)xPos-hitCircleSize/2)), (int)(sizeMultiplier*((int)yPos-hitCircleSize/2)), (int)(sizeMultiplier*hitCircleSize), (int)(sizeMultiplier*hitCircleSize), this);
				g.drawString(i+2 + ": (" + (int)xPos + ", " + (int)yPos + ") Angle: " + (int)Math.toDegrees(l.get(i).getRight()), (int)(sizeMultiplier*((int)xPos-hitCircleSize/2)), (int)(sizeMultiplier*((int)yPos-hitCircleSize/2)));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
