package editor;

import gui.Editor;

import java.awt.Graphics;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import static java.awt.event.ActionEvent.ACTION_PERFORMED;

public class PenSizeButton extends ToolbarButton implements ActionListener{
	
	public static final long serialVersionUID = 948886739228574838L;
	
	private ActionListener actionListener;
	
	private PenSize penSize;
	
	public PenSizeButton(PenSize size){
		penSize = size;
		super.addActionListener(this);
	}
	
	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		drawSymbol(g);
	}
	
	private void drawSymbol(Graphics g){
		g.setColor(Color.black);
		g.fillOval((int)(getWidth()*0.5-getSizeInPx()*0.5), (int)(getHeight()*0.5-getSizeInPx()*0.5),
				getSizeInPx(), getSizeInPx());
	}
	
	private int getSizeInPx(){
		return Pen.getSizeInPx(penSize, getCurrentPen().getType());
	}
	
	private Pen getCurrentPen(){
		return Editor.currentPen;
	}
	
	private Pen getNewPen(){
		return new Pen(penSize, getCurrentPen().getColor(), getCurrentPen().getMode(), getCurrentPen().getType());
	}
	
	public void addActionListener(ActionListener al){
		actionListener = al;
	}
	
	public void actionPerformed(ActionEvent e){
		actionListener.actionPerformed(triggeredEvent());
		setSelected(true);
	}
	
	private ToolbarEvent triggeredEvent(){
		return new ToolbarEvent(this, ACTION_PERFORMED, "size was changed", getNewPen());
	}
}
