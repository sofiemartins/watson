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
import gui.Editor;

public class PenColorPanel extends ToolbarPanel{
	
	public static final long serialVersionUID = 5587623465197685476L;
	
	private ColorButton[] buttons = { getColorButton(black), 
										getColorButton(green), 
										getColorButton(blue), 
										getColorButton(red) };
	
	private ActionListener actionListener;
	
	public PenColorPanel(){
		setLayout(new BorderLayout());
		add(Toolbar.getLabel("Color"), BorderLayout.NORTH);
		JPanel subcontainer = new JPanel();
		setUpColorPanel(subcontainer);
		addButtonsToColorPanel(subcontainer);
	}
	
	private boolean buttonSelected(ColorButton button){
		return Editor.currentPen.getColor()==button.getButtonColor();
	}
	
	private void setUpColorPanel(JPanel container){
		container.setLayout(new GridLayout(1,4));
		container.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1, true));
	}
	
	private void addButtonsToColorPanel(JPanel container){
		for(ColorButton button : buttons){
			container.add(getButtonPanel(button));
		}
	}
	
	private JPanel getButtonPanel(ColorButton button){
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridLayout(1,1));
		buttonPanel.add(button);
		return buttonPanel;
	}
		
	private ColorButton getColorButton(Color color){
		ColorButton button = new ColorButton(color);
		setUpButtonLayout(button);
		button.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				resetAllColorButtons();
				if(actionListener!=null){
					actionListener.actionPerformed(e);
				}
			}
		});
		return button;
	}
	
	private void setUpButtonLayout(ColorButton button){
		button.setPreferredSize(new Dimension(60, 60));
		button.setBorder(new EmptyBorder(8, 8, 8, 8));
	}
	
	protected void resetAllColorButtons(){
		for(ColorButton button : buttons){
			button.setSelected(false);
		}
	}
	
	public void addActionListener(ActionListener al){
		actionListener = al;
	}
	
	@Override
	protected void checkProperties() {
		resetAllColorButtons();
		for(ColorButton button : buttons){
			if(buttonSelected(button)){
				button.setSelected(true);
			}
		}
	}

}
