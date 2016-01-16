package practice;

import javax.swing.JButton;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;

public class AnswerButton extends JButton implements MouseListener{
	
	public static final long serialVersionUID = 6667453365432432544L;
	
	private ButtonType type;
	private int darkness = 50;
	private String text;
	
	public AnswerButton(ButtonType buttonType){
		type = buttonType;
		addMouseListener(this);
		setBackground(Color.white);
		if(buttonType==ButtonType.RIGHT){
			text = "Right";
		}else{
			text = "Wrong";
		}
	}
	
	@Override
	public void paintComponent(Graphics graphics){
		super.paintComponent(graphics);
		Graphics2D g = (Graphics2D)graphics;
		if(type==ButtonType.RIGHT){
			g.setColor(new Color(200-darkness, 255-darkness, 200-darkness));
		}else{
			g.setColor(new Color(255-darkness, 200-darkness, 200-darkness));
		}
		g.fillOval((getWidth()/2)-100, (getHeight()/2)-100, 200, 200);
		if(type==ButtonType.RIGHT){
			g.setColor(new Color(180-darkness, 200-darkness, 180-darkness));
		}else{
			g.setColor(new Color(200-darkness, 180-darkness, 180-darkness));
		}
		float dash[] = { 10.0f };
		g.setStroke(new BasicStroke(3.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, dash, 0.0f));
		g.drawOval((getWidth()/2)-100, (getHeight()/2)-100, 200, 200);
		g.setColor(Color.white);
		g.setFont(new Font("Times New Roman", Font.BOLD, 30));
		g.drawString(text, (getWidth()/2)-50, (getHeight()/2)+20);
	}
	
	public void activate(){
		darkness = 0;
	}

	@Override
	public void mouseClicked(MouseEvent e) {}

	@Override
	public void mouseEntered(MouseEvent e) {
		darkness=0;
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
