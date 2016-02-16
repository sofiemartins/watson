package stats;

import javax.swing.JPanel;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.util.LinkedList;

import util.Stats;
import util.StatsSet;
import util.Lesson;
import editor.Toolbar;

public class TimeOverview extends JPanel{
	
	public static final long serialVersionUID = 958847567644323454L;
	private Stats statistics;
	private Lesson lesson;
	
	public TimeOverview(Lesson displayedLesson){
		lesson = displayedLesson;
		statistics = displayedLesson.getStats();
		setPreferredSize(new Dimension(200, 200));
		setBackground(Color.white);
	}
	
	@Override
	public void paintComponent(Graphics graphics){
		super.paintComponent(graphics);
		Graphics2D g = (Graphics2D)graphics;
		antialiasing(g);
		drawCaption(g);
		plotDataIfPresent(g);
	}
	
	private void antialiasing(Graphics2D g){
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	}
	
	private void drawCaption(Graphics g){
		g.setFont(new Font("Times New Roman", Font.BOLD, 10));
		g.drawString("Number of wrong answers in the last tries", 200, 15);
	}
	
	private void drawTimeAxis(Graphics g){
		g.drawLine(2, getHeight()-20, getWidth()-10, getHeight()-20);
		g.drawLine(getWidth()-10, getHeight()-20, getWidth()-12, getHeight()-22);
		g.drawLine(getWidth()-10, getHeight()-20, getWidth()-12, getHeight()-18);
		g.setFont(new Font("Times New Roman", Font.PLAIN, 10));
		g.drawString("time", getWidth()-30, getHeight()-2);
	}
	
	private void drawYAxis(Graphics g){
		g.drawLine(5, 5, 5, getHeight()-15);
		g.drawLine(5, 5, 7, 7);
		g.drawLine(5, 5, 3, 7);
		g.setFont(new Font("Times New Roman", Font.PLAIN, 10));
		g.drawString("number of wrong answers", 10, 15);
	}
	
	private void plotDataIfPresent(Graphics2D g){
		if(lesson.getStats().getStatsList().isEmpty()){
			g.setColor(new Color(180, 180, 180));
			g.setFont(new Font("Times New Roman", Font.PLAIN, 20));
			g.drawString("No data present", 5, 30);
		}else{
			g.setColor(Toolbar.black);
			drawTimeAxis(g);
			drawYAxis(g);
			plotWrongAnswers(g);
		}
	}
	
	private int imageResolution = 2000;
	
	private void plotWrongAnswers(Graphics2D graphics){
		BufferedImage plot = new BufferedImage(imageResolution, imageResolution, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = plot.createGraphics();
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		StatsSet lastSet = null;
		for(StatsSet set : statistics.getStatsList()){
			if(lastSet!=null){
				g.setStroke(new BasicStroke(5, BasicStroke.CAP_ROUND, BasicStroke.JOIN_MITER));
				g.setColor(Toolbar.red);
				//TODO: clean this up ...
				g.drawLine((int)(timeAxisValue(lastSet.getTimestamp().getTime())*timestampImageRatio()), 
							imageResolution-(int)(lastSet.getNumberOfWrongAnswers()*wrongAnswerImageRatio()), 
							(int)(timeAxisValue(set.getTimestamp().getTime())*timestampImageRatio()), 
							imageResolution-(int)(set.getNumberOfWrongAnswers()*wrongAnswerImageRatio()));
			}
			lastSet = set;
		}
		graphics.drawImage(plot, 10, 20, getWidth()-20, getHeight()-40, null);
	}
	
	private double timestampImageRatio(){
		return imageResolution / timestampDifference();
	}

	private long timeAxisValue(long timestamp){
		LinkedList<StatsSet> stats = statistics.getStatsList();
		long firstTryTimestamp = stats.getFirst().getTimestamp().getTime();
		return timestamp - firstTryTimestamp;
	}
	
	private double timestampDifference(){
		LinkedList<StatsSet> stats = statistics.getStatsList();
		long firstTryTimestamp = stats.getFirst().getTimestamp().getTime();
		long lastTryTimestamp = stats.getLast().getTimestamp().getTime();
		return lastTryTimestamp - firstTryTimestamp;
	}
	
	private double wrongAnswerImageRatio(){
		if(maxNumberOfWrongAnswers()==0){
			return 1;
		}else{
			return imageResolution / maxNumberOfWrongAnswers();
		}
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
