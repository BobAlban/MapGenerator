package GUI;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import hitObject.Pair;
import pattern.PatternSlider;

@SuppressWarnings("serial")
public class GraphPanel extends JPanel{

	List<Pair<Double,Double>> l;
	double sizeMultiplier;
	int hitCircleSize = 37;			//closest int from cs4 radius (if the in-game radius is well interpreted)

	public GraphPanel(List<Pair<Double,Double>> l, double sizeMultiplier) {
		this.l=l;
		this.sizeMultiplier=sizeMultiplier;
	}

	@Override
	public void paintComponent(Graphics g){
		paintGraph(g);
		try {
			Image circle = ImageIO.read(new File("hitcircle.png"));
			for(int i=0;i<l.size();i++) {
				if(l.get(i)==PatternSlider.nullElem) {
					i += paintSlider(g, circle, i);
				}
				paintCircle(g, circle, i);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void paintGraph(Graphics g) {
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
	}

	private void paintCircle(Graphics g, Image circle, int i) {
		double xPos=l.get(i).getLeft();
		double yPos=l.get(i).getRight();
		Graphics2D g2 = (Graphics2D) g;
		drawCross(g2, l.get(i));
		g.drawImage(circle, (int)(sizeMultiplier*((int)xPos-hitCircleSize/2)), (int)(sizeMultiplier*((int)yPos-hitCircleSize/2)), (int)(sizeMultiplier*hitCircleSize), (int)(sizeMultiplier*hitCircleSize), this);
		g.drawString(i + ": (" + (int)xPos + ", " + (int)yPos + ")", (int)(sizeMultiplier*((int)xPos-hitCircleSize/2)), (int)(sizeMultiplier*((int)yPos-hitCircleSize/2)));
	}

	//[(2.147483647E9, 2.147483647E9), (100.0, 100.0), (199.61946980917457, 108.71557427476581), (2.147483647E9, 2.147483647E9)]
	private int paintSlider(Graphics g, Image circle, int i) {
		int res = 1;
		paintCircle(g, circle, i+res);
		Pair<Double,Double> oldPos = l.get(i+res);
		Pair<Double,Double> nextPos = l.get(i+res+1);
		Graphics2D g2 = (Graphics2D) g;
		while(nextPos!=PatternSlider.nullElem) {
			System.out.println("oldPos: " + oldPos);
			System.out.println("nextPos: " + nextPos);
			g2.setStroke(new BasicStroke(3));
			g.setColor(Color.RED);
			g.drawLine((int)(sizeMultiplier*(oldPos.getLeft().intValue())), (int)(sizeMultiplier*(oldPos.getRight().intValue())), (int)(sizeMultiplier*(nextPos.getLeft().intValue())), (int)(sizeMultiplier*(nextPos.getRight().intValue())));
			drawCross(g2, nextPos);
			res++;
			oldPos = l.get(i+res);
			nextPos = l.get(i+res+1);
		}
		g2.setStroke(new BasicStroke(1));
		g.setColor(Color.BLACK);
		paintCircle(g, circle, i+res);
		return res+1;//3
	}
	
	private void drawCross(Graphics g, Pair<Double,Double> pos) {
		Graphics2D g2 = (Graphics2D) g;
		g.setColor(Color.BLACK);
		g2.setStroke(new BasicStroke(3));
		g.drawLine((int)(sizeMultiplier*pos.getLeft().intValue()-4), (int)(sizeMultiplier*pos.getRight().intValue()-4), (int)(sizeMultiplier*pos.getLeft().intValue()+4), (int)(sizeMultiplier*pos.getRight().intValue()+4));
		g.drawLine((int)(sizeMultiplier*pos.getLeft().intValue()-4), (int)(sizeMultiplier*pos.getRight().intValue()+4), (int)(sizeMultiplier*pos.getLeft().intValue()+4), (int)(sizeMultiplier*pos.getRight().intValue()-4));
		g2.setStroke(new BasicStroke(1));
	}
}
