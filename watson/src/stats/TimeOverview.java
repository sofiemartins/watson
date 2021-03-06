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

import static util.Colors.*;
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
		setBackground(Color.white);
	}
	
	@Override
	public void paintComponent(Graphics graphics){
		super.paintComponent(graphics);
		Graphics2D g = (Graphics2D)graphics;
		antialiasing(g);
		drawContent(g);
	}
	
	private void drawContent(Graphics2D graphics){
		BufferedImage layer = new BufferedImage(getWidth()+10, getHeight()+10, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = layer.createGraphics();
		antialiasing(g);
		g.setColor(new Color(50, 50, 50));
		plotDataIfPresent(g);
		graphics.drawImage(layer, 0, 0, getWidth(), getHeight(), null);
	}
	
	private void antialiasing(Graphics2D g){
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	}
	
	private void drawTimeAxis(Graphics g){
		g.drawLine(2, getHeight()-20, getWidth()-10, getHeight()-20);
		g.drawLine(getWidth()-10, getHeight()-20, getWidth()-12, getHeight()-22);
		g.drawLine(getWidth()-10, getHeight()-20, getWidth()-12, getHeight()-18);
		g.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		g.drawString("time", getWidth()-30, getHeight()-4);
	}
	
	private void drawYAxis(Graphics g){
		g.drawLine(5, 5, 5, getHeight()-15);
		g.drawLine(5, 5, 7, 7);
		g.drawLine(5, 5, 3, 7);
		g.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		g.drawString("number of wrong answers", 10, 20);
	}
	
	private void plotDataIfPresent(Graphics2D graphics){
		BufferedImage plot = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = plot.createGraphics();
		antialiasing(g);
		if(lesson.getStats().getStatsList().isEmpty()){
			g.setColor(new Color(180, 180, 180));
			g.setFont(new Font("Times New Roman", Font.PLAIN, 10));
			g.drawString("No data present", 5, 30);
			graphics.drawImage(plot, 0, 0, getWidth(), getHeight(), null);
		}else{
			g.setColor(black);
			drawTimeAxis(g);
			drawYAxis(g);
			plotWrongAnswers(g);
			graphics.drawImage(plot, 0, 0, getWidth(), getHeight(), null);
		}
	}
	
	private void plotWrongAnswers(Graphics2D graphics){
		BufferedImage plot = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = plot.createGraphics();
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		StatsSet lastSet = null;
		for(StatsSet set : statistics.getStatsList()){
			if(lastSet!=null){
				g.setStroke(new BasicStroke(5, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
				g.setColor(red);
				//TODO: clean this up ...
				g.drawLine((int)(timeAxisValue(lastSet.getTimestamp().getTime())*timestampImageRatio()), 
							getHeight()-(int)(lastSet.getNumberOfWrongAnswers()*wrongAnswerImageRatio()), 
							(int)(timeAxisValue(set.getTimestamp().getTime())*timestampImageRatio()), 
							getHeight()-(int)(set.getNumberOfWrongAnswers()*wrongAnswerImageRatio()));
			}
			lastSet = set;
		}
		graphics.drawImage(plot, 10, 20, getWidth()-40, getHeight()-40, null);
	}
	
	private double timestampImageRatio(){
		return ((getWidth()*1.0d) / timestampDifference());
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
		return ((getHeight()*1.0d)/maxNumberOfWrongAnswers());
	}
	
	private int maxNumberOfWrongAnswers(){
		return lesson.getCards().size();
	}
}
