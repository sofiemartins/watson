package editor;

import javax.swing.JToggleButton;

import gui.Editor;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ColorButton extends JToggleButton implements ActionListener{
	
	public static final long serialVersionUID = 366547611232432597L;
	private ActionListener actionListener;
	
	private Color buttonColor;
	
	public ColorButton(Color color){
		buttonColor = color;
		super.addActionListener(this);
	}
	
	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		g.setColor(buttonColor);
		g.fillRect(5, 5, getWidth()-10, getHeight()-10);
		g.setColor(Color.black);
		g.drawRect(5, 5, getWidth()-10, getHeight()-10);
	}
	
	public Color getButtonColor(){
		return buttonColor;
	}
	
	public void addActionListener(ActionListener al){
		actionListener = al;
	}
	
	public void actionPerformed(ActionEvent e){
		Pen currentPen = Editor.currentPen;
		Pen newPen = new Pen(currentPen.getSize(), buttonColor, currentPen.getMode(), currentPen.getType());
		actionListener.actionPerformed(new ToolbarEvent(this,
				ActionEvent.ACTION_PERFORMED,
				"color button was pressed",
				newPen));
		setSelected(true);
	}
}
