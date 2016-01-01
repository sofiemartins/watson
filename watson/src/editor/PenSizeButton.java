package editor;

import javax.swing.JButton;
import java.awt.Graphics;
import java.awt.Color;

public class PenSizeButton extends JButton{
	
	public static final long serialVersionUID = 948886739228574838L;
	
	private int penSize;
	
	public PenSizeButton(int size){
		penSize = size;
	}
	
	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		g.setColor(Color.black);
		g.fillOval((int)(getWidth()*0.5), (int)(getHeight()*0.5), penSize, penSize);
	}

}
