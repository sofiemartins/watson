package practice;

import javax.swing.JButton;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.awt.event.MouseEvent;

public class RightAnswerButton extends JButton implements MouseListener{
	
	public static final long serialVersionUID = 558677498776834322L;
	
	private int darkness = 50;
	
	public RightAnswerButton(){
		addMouseListener(this);
	}
	
	@Override
	public void paintComponent(Graphics graphics){
		super.paintComponent(graphics);
		Graphics2D g = (Graphics2D)graphics;
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		printQuestion(g);
		fillCircle(g);
		dashedOutline(g);
		drawLabel(g);
		drawSymbol(g);
	}
	
	private void printQuestion(Graphics2D g){
		g.setColor(new Color(200, 200, 200));
		g.setFont(new Font("Times New Roman", Font.PLAIN, 30));
		g.drawString("Your answer has been ... ?", 20, 50);
	}
	
	private void fillCircle(Graphics2D g){
		g.setColor(new Color(200-darkness, 255-darkness, 200-darkness));
		g.fillOval(getWidth()-220, (getHeight()/2)-100, 200, 200);
	}
	
	private void dashedOutline(Graphics2D g){
		g.setColor(new Color(150-darkness, 205-darkness, 150-darkness));
		float dash[] = { 10.0f };
		g.setStroke(new BasicStroke(3.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, dash, 0.0f));
		g.drawOval(getWidth()-220, (getHeight()/2)-100, 200, 200);
	}
	
	private void drawLabel(Graphics2D g){
		g.setColor(Color.white);
		g.setFont(new Font("Times New Roman", Font.PLAIN, 30));
		g.drawString("Right", getWidth()-160, (getHeight()/2)+40);
	}
	
	private void drawSymbol(Graphics2D g){
		BufferedImage icon = new BufferedImage(50, 50, BufferedImage.TYPE_INT_ARGB);
		Graphics2D imageGraphics = icon.createGraphics();
		imageGraphics.setColor(new Color(150-darkness, 205-darkness, 150-darkness));
		imageGraphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		imageGraphics.setStroke(new BasicStroke(5, BasicStroke.CAP_ROUND, BasicStroke.JOIN_MITER));
		imageGraphics.drawLine(5, 20, 20, 45);
		imageGraphics.drawLine(20, 45, 45, 5);
		g.drawImage(icon, getWidth()-145, (getHeight()/2)-70, null);
	}
	
	public void activate(){
		darkness = 0;
	}

	@Override
	public void mouseClicked(MouseEvent e) {}

	@Override
	public void mouseEntered(MouseEvent e) {
		activate();
		repaint();
	}

	@Override
	public void mouseExited(MouseEvent e) {
		darkness=50;
		repaint();
	}

	@Override
	public void mousePressed(MouseEvent e) {}

	@Override
	public void mouseReleased(MouseEvent e) {}

}
