package editor;

import javax.swing.JButton;
import java.awt.Color;
import java.awt.Graphics;

public class ColorButton extends JButton{
	
	public static final long serialVersionUID = 366547611232432597L;
	
	private Color buttonColor;
	
	public ColorButton(Color color){
		buttonColor = color;
	}
	
	@Override
	public void paintComponent(Graphics g){
		g.setColor(buttonColor);
		g.fillRect(5, 5, getWidth()-10, getHeight()-10);
		g.setColor(Color.black);
		g.drawRect(5, 5, getWidth()-10, getHeight()-10);
	}
}
