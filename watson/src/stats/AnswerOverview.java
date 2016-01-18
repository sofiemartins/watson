package stats;

import javax.swing.JPanel;

import java.awt.Polygon;
import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

import util.Lesson;

public class AnswerOverview extends JPanel{
	
	public static final long serialVersionUID = 4665763454224365432L;
	
	Lesson lesson;
	
	public AnswerOverview(Lesson lessonDisplayed){
		lesson = lessonDisplayed;
	}
	
	@Override
	public void paintComponent(Graphics graphics){
		super.paintComponent(graphics);
		Graphics2D g = (Graphics2D)graphics;
		drawPlot(g);
	}
	
	private void drawPlot(Graphics2D g){
		BufferedImage plot = new BufferedImage(300, 300, BufferedImage.TYPE_INT_ARGB);
		Graphics2D imageGraphics = plot.createGraphics();
		imageGraphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		drawDataIfPresent(imageGraphics);
		if(getHeight()>=getWidth()){
			g.drawImage(plot, 0, 0, getWidth(), getWidth(), null);
		}else{
			g.drawImage(plot, 0, 0, getHeight(), getHeight(), null);
		}
	}
	
	private void drawDataIfPresent(Graphics2D g){
		if(lesson.getStats()==null){
			g.setColor(new Color(180, 180, 180));
			g.setFont(new Font("Times New Roman", Font.PLAIN, 30));
			g.drawString("No data present", 5, 30);
		}else{
			g.setBackground(Color.green);
			g.fillPolygon(wrongAnswers());
			g.setComposite(AlphaComposite.DstIn);
			g.fillOval(5, 5, 290, 290);
		}
	}
	
	private Polygon wrongAnswers(){
		int pointsX[] = { 150, 150,  150 + (int)(150*Math.sin(lesson.getStats().getTotalNumberOfWrongAnswers()*anglePerAnswer())) };
		int pointsY[] = { 150,   0,  																							0 };
		return new Polygon( pointsX, pointsY, 3);
	}
	
	private double anglePerAnswer(){
		return 2*Math.PI/(lesson.getStats().getTotalNumberOfAnswers());
	}
	
}
