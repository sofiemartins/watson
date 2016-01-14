package editor;

import javax.swing.JToggleButton;

import gui.Editor;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import static java.awt.event.ActionEvent.ACTION_PERFORMED;

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
		paintSymbol(g);
	}
	
	private void paintSymbol(Graphics g){
		fillRectangle(g);
		drawOutline(g);
	}
	
	private void fillRectangle(Graphics g){
		g.setColor(buttonColor);
		g.fillRect(5, 5, getWidth()-10, getHeight()-10);
	}
	
	private void drawOutline(Graphics g){
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
		actionListener.actionPerformed(triggeredEvent());
		setSelected(true);
	}
	
	private Pen getCurrentPen(){
		return Editor.currentPen;
	}
	
	private Pen getNewPen(){
		return new Pen(getCurrentPen().getSize(), buttonColor, getCurrentPen().getMode(), getCurrentPen().getType());
	}
	
	private ToolbarEvent triggeredEvent(){
		return new ToolbarEvent(this, ACTION_PERFORMED, "Color button was pressed.", getNewPen());
	}
	
}
