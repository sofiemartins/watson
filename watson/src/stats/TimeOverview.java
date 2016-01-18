package stats;

import javax.swing.JPanel;
import java.awt.Graphics;

import util.Stats;

public class TimeOverview extends JPanel{
	
	public static final long serialVersionUID = 958847567644323454L;
	private Stats statistics;
	
	public TimeOverview(Stats stats){
		statistics = stats;
	}
	
	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		drawTimeAxis(g);
		drawYAxis(g);
		plotWrongAnswers(g);
	}
	
	private void drawTimeAxis(Graphics g){
		g.drawLine(5, getHeight()-5, getWidth()-5, getHeight()-5);
		g.drawLine(getWidth()-5, getHeight()-5, getWidth()-7, getHeight()-7);
		g.drawLine(getWidth()-5, getHeight()-5, getWidth()-3, getHeight()-3);
		g.drawString("time", getWidth()-10, getHeight()-2);
	}
	
	private void drawYAxis(Graphics g){
		g.drawLine(5, 5, 5, getHeight()-5);
		g.drawLine(5, 5, 7, 7);
		g.drawLine(5, 5, 3, 7);
		g.drawString("number of wrong answers", 7, 5);
	}
	
	private void plotWrongAnswers(Graphics g){
		
	}
	

}
