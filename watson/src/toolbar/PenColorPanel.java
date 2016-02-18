package toolbar;

import static util.Colors.*;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import editor.ColorButton;
import editor.Toolbar;
import editor.ToolbarButton;
import gui.Editor;

public class PenColorPanel extends JPanel{
	
	public static final long serialVersionUID = 5587623465197685476L;
	
	private ColorButton colorButton1 = getColorButton(black);
	private ColorButton colorButton2 = getColorButton(green);
	private ColorButton colorButton3 = getColorButton(blue);
	private ColorButton colorButton4 = getColorButton(red);
	
	private ActionListener actionListener;
	
	public PenColorPanel(){
		setLayout(new BorderLayout());
		add(Toolbar.getLabel("Color"), BorderLayout.NORTH);
		JPanel subcontainer = new JPanel();
		setUpColorPanel(subcontainer);
		addButtonsToColorPanel(subcontainer);
	}
	
	private boolean hasColor(Color color){
		return Editor.currentPen.getColor()==color;
	}
	
	private void checkColor(){
		resetAllColorButtons();
		if(hasColor(black)){
			colorButton1.setSelected(true);
		}else if(hasColor(blue)){
			colorButton2.setSelected(true);
		}else if(hasColor(red)){
			colorButton4.setSelected(true);
		}else if(hasColor(green)){
			colorButton3.setSelected(true);
		}
	}
	
	private void setUpColorPanel(JPanel container){
		container.setLayout(new GridLayout(1,4));
		container.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1, true));
	}
	
	private void addButtonsToColorPanel(JPanel container){
		ToolbarButton buttons[] = { colorButton1, colorButton2, colorButton3, colorButton4 };
		for(ToolbarButton button : buttons){
			JPanel buttonPanel = new JPanel();
			buttonPanel.setLayout(new GridLayout(1,1));
			buttonPanel.add(button);
			container.add(buttonPanel);
		}
	}
		
	private ColorButton getColorButton(Color color){
		ColorButton button = new ColorButton(color);
		button.setPreferredSize(new Dimension(60, 60));
		button.setBorder(new EmptyBorder(8, 8, 8, 8));
		button.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				if(actionListener!=null){
					resetAllColorButtons();
					actionListener.actionPerformed(e);
				}
			}
		});
		return button;
	}
	
	protected void resetAllColorButtons(){
		colorButton1.setSelected(false);
		colorButton2.setSelected(false);
		colorButton4.setSelected(false);
		colorButton3.setSelected(false);
	}
	
	public void addActionListener(ActionListener al){
		actionListener = al;
	}
	
	public void update(){
		checkColor();
	}

}
