package practice;

import javax.swing.JButton;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;

public class AnswerButton extends JButton implements MouseListener{
	
	public static final long serialVersionUID = 6667453365432432544L;
	
	private ButtonType type;
	private int darkness = 50;
	
	public AnswerButton(ButtonType buttonType){
		type = buttonType;
		addMouseListener(this);
	}
	
	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		if(type==ButtonType.RIGHT){
			g.setColor(new Color(0, 200-darkness, 0));
		}else{
			g.setColor(new Color(200-darkness, 0, 0));
		}
		g.fillRect(10,10,getWidth()-20,getHeight()-20);
		if(type==ButtonType.RIGHT){
			g.setColor(new Color(0, 100-darkness, 0));
		}else{
			g.setColor(new Color(100-darkness, 0, 0));
		}
		g.drawRect(10,10,getWidth()-20,getHeight()-20);
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
