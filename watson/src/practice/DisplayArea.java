package practice;

import javax.swing.JPanel;
import java.awt.image.BufferedImage;
import java.awt.Graphics;

public class DisplayArea extends JPanel{
	
	public static final long serialVersionUID = 984456765876123423L;
	
	private BufferedImage imageDisplayed;
	
	public DisplayArea(BufferedImage image){
		imageDisplayed = image;
	}
	
	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		g.drawImage(imageDisplayed, 0, 0, null);
	}
	
	public void show(BufferedImage image){
		imageDisplayed = image;
		repaint();
	}
}
