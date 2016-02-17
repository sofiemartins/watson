package stats;

import javax.swing.JPanel;

import editor.Toolbar;

import java.awt.Polygon;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

import static util.Colors.*;
import util.Lesson;

public class PiChartAnswerOverview extends JPanel{
	
	public static final long serialVersionUID = 4665763454224365432L;
	
	Lesson lesson;
	
	public PiChartAnswerOverview(Lesson lessonDisplayed){
		lesson = lessonDisplayed;
	}
	
	@Override
	public void paintComponent(Graphics graphics){
		super.paintComponent(graphics);
		Graphics2D g = (Graphics2D)graphics;
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		paintContent(g);
	}
	
	private void paintContent(Graphics2D graphics){
		BufferedImage image = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = image.createGraphics();
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		drawDataIfPresent(g);
		graphics.drawImage(image, 0, 0, getWidth(), getHeight(), null);
	}
	
	private void setUpPen(Graphics g){
		g.setColor(new Color(50, 50, 50));
		g.setFont(new Font("Times New Roman", Font.BOLD, 10));
	}
	
	private void drawStringScaling(Graphics g){
		if(getWidth()<100){
			g.drawString("Percentage", 10, 10);
			g.drawString("of", 10, 20);
			g.drawString("Wrong", 10, 30);
			g.drawString("Answers", 10, 40);
		}else if(getWidth()>=100 && getWidth()<200){
			g.drawString("Percentage of", 10, 10);
			g.drawString("Wrong Answers", 10, 20);
		}else if(getWidth()>200){
			g.drawString("Percentage of Wrong Answers", 10, 10);
		}
	}
	
	private void drawDataIfPresent(Graphics2D g){
		if(lesson.getStats().getStatsList().isEmpty()){
			g.setColor(new Color(180, 180, 180));
			g.setFont(new Font("Times New Roman", Font.PLAIN, 10));
			g.drawString("No data present", 5, 30);
		}else{
			drawPlot(g);
		}
	}
	
	private void fillCircle(double angleOfColorChange, Graphics2D g){
		g.setStroke(new BasicStroke(2, BasicStroke.CAP_BUTT, BasicStroke.JOIN_ROUND));
		g.setColor(red);
		for(double phi = 0; phi < angleOfColorChange; phi+=0.05*Math.PI){
			fillCircleSegment(phi, 0.051*Math.PI, g);
		}
		g.setColor(green);
		for(double phi = angleOfColorChange; phi < 2*Math.PI; phi+=0.05*Math.PI){
			fillCircleSegment(phi, 0.051*Math.PI, g);
		}
	}
	
	private int imageResolution = 800;
	
	private void drawPlot(Graphics2D g){
		BufferedImage plot = new BufferedImage(imageResolution, imageResolution, BufferedImage.TYPE_INT_ARGB);
		Graphics2D imageGraphics = plot.createGraphics();
		imageGraphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		fillCircle(lesson.getStats().getTotalNumberOfWrongAnswers()*anglePerAnswer(), imageGraphics);
		if(getHeight()>=getWidth()){
			g.drawImage(plot, 20, ((getHeight()-getWidth())/2)+20, getWidth()-30, getWidth()-30, null);
		}else{
			g.drawImage(plot, (getWidth()-getHeight())/2, 20, getHeight()-30, getHeight()-30, null);
		}
	}
	
	private void fillCircleSegment(double phi, double dphi, Graphics2D g){
		int pointsX[] = { (imageResolution/2), 
				(imageResolution/2) + (int)(((imageResolution/2)-10)*Math.cos(phi)), 
				(imageResolution/2) + (int)(((imageResolution/2)-10)*Math.cos(phi+dphi))};
		int pointsY[] = { (imageResolution/2), 
				(imageResolution/2) + (int)(((imageResolution/2)-10)*Math.sin(phi)), 
				(imageResolution/2) + (int)(((imageResolution/2)-10)*Math.sin(phi+dphi))};
		g.fillPolygon(new Polygon(pointsX, pointsY, 3));
	}	
	
	private double anglePerAnswer(){
		return 2*Math.PI/(lesson.getStats().getTotalNumberOfAnswers());
	}
	
}
