package editor;

import javax.swing.JButton;

import gui.Editor;

import java.awt.Graphics;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class PenSizeButton extends JButton implements ActionListener{
	
	public static final long serialVersionUID = 948886739228574838L;
	
	private ActionListener actionListener;
	
	private int penSize;
	
	public PenSizeButton(int size){
		penSize = size;
		super.addActionListener(this);
	}
	
	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		g.setColor(Color.black);
		g.fillOval((int)(getWidth()*0.5), (int)(getHeight()*0.5), penSize, penSize);
	}
	
	public void addActionListener(ActionListener al){
		actionListener = al;
	}
	
	public void actionPerformed(ActionEvent e){
		Pen currentPen = Editor.currentPen;
		Pen newPen = new Pen(penSize, currentPen.getColor(), currentPen.getMode());
		actionListener.actionPerformed(new ToolbarEvent(this,
				ActionEvent.ACTION_PERFORMED,
				"size was changed",
				newPen));
	}
}
