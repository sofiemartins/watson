package stats;

import javax.swing.JPanel;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.util.LinkedList;

import util.Stats;
import util.StatsSet;
import util.Lesson;

public class TimeOverview extends JPanel{
	
	public static final long serialVersionUID = 958847567644323454L;
	private Stats statistics;
	private Lesson lesson;
	
	public TimeOverview(Lesson displayedLesson){
		lesson = displayedLesson;
		statistics = displayedLesson.getStats();
		setPreferredSize(new Dimension(200, 200));
	}
	
	@Override
	public void paintComponent(Graphics graphics){
		super.paintComponent(graphics);
		Graphics2D g = (Graphics2D)graphics;
		antialiasing(g);
		drawTimeAxis(g);
		drawYAxis(g);
		//plotWrongAnswers((Graphics2D)g);
	}
	
	private void antialiasing(Graphics2D g){
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	}
	
	private void drawTimeAxis(Graphics g){
		g.drawLine(2, getHeight()-20, getWidth()-10, getHeight()-20);
		g.drawLine(getWidth()-10, getHeight()-20, getWidth()-12, getHeight()-22);
		g.drawLine(getWidth()-10, getHeight()-20, getWidth()-12, getHeight()-18);
		g.drawString("time", getWidth()-30, getHeight()-2);
	}
	
	private void drawYAxis(Graphics g){
		g.drawLine(5, 5, 5, getHeight()-15);
		g.drawLine(5, 5, 7, 7);
		g.drawLine(5, 5, 3, 7);
		g.drawString("number of wrong answers", 10, 15);
	}
	
	private void plotWrongAnswers(Graphics2D graphics){
		BufferedImage plot = new BufferedImage(500, 500, BufferedImage.TYPE_INT_ARGB);
		Graphics g = plot.createGraphics();
		StatsSet lastSet = null;
		for(StatsSet set : statistics.getStatsList()){
			if(lastSet!=null){
				g.drawLine((int)(lastSet.getTimestamp().getTime()*timestampImageRatio()), 
							getHeight()-(int)(lastSet.getNumberOfWrongAnswers()*wrongAnswerImageRatio()), 
							(int)(set.getTimestamp().getTime()*timestampImageRatio()), 
							getHeight()-(int)(set.getNumberOfWrongAnswers()*wrongAnswerImageRatio()));
			}
			lastSet = set;
		}
		graphics.drawImage(plot, 5, 5, null);
	}
	
	private long timestampImageRatio(){
		return 500 / timestampDifference();
	}
	
	private long timestampDifference(){
		LinkedList<StatsSet> stats = statistics.getStatsList();
		long firstTryTimestamp = stats.getFirst().getTimestamp().getTime();
		long lastTryTimestamp = stats.getLast().getTimestamp().getTime();
		return lastTryTimestamp - firstTryTimestamp;
	}
	
	private long wrongAnswerImageRatio(){
		return 500 / maxNumberOfWrongAnswers();
	}
	
	private int maxNumberOfWrongAnswers(){
		int currentMax = 0;
		for(StatsSet set : statistics.getStatsList()){
			if(set.getNumberOfWrongAnswers()>currentMax){
				currentMax = set.getNumberOfWrongAnswers();
			}
		}
		return currentMax;
	}
}
