package editor;

import javax.swing.JPanel;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.border.EmptyBorder;
import java.awt.GridLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;

import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import static util.Colors.*;
import editor.ColorButton;
import editor.PenSizeButton;
import gui.Editor;
import toolbar.*;

public class Toolbar extends JPanel{
	
	public static final long serialVersionUID = 8724543215436543876L;
	
	private ActionListener actionListener;
	 
	private PenTypePanel penTypePanel = new PenTypePanel();
	private PenModePanel penModePanel = new PenModePanel();
	
	public Toolbar(){
		addComponents();
	}
	
	private void addComponents(){
		setLayout(new FlowLayout(FlowLayout.LEFT));
		setBorder(new EmptyBorder(0,0,0,0));
		add(penTypePanel);
		add(penModePanel);
		add(getColorPanel());
		add(getPenSizePanel());
	}
	
	/**
	 * Checks whether the editor pen variable is in sync with the toolbar.
	 */
	public void update(){
		checkSize();
		checkColor();
		penModePanel.update();
		penTypePanel.update();
	}
	
	private Pen getCurrentPen(){
		return Editor.currentPen;
	}
	
	private boolean isFine(){
		return getCurrentPen().getSize()==PenSize.FINE;
	}
	
	private boolean isMedium(){
		return getCurrentPen().getSize()==PenSize.MEDIUM;
	}
	
	private boolean isThick(){
		return getCurrentPen().getSize()==PenSize.THICK;
	}
	
	private void checkSize(){
		resetAllPenSizeButtons();
		if(isFine()){
			fineButton.setSelected(true);
		}else if(isMedium()){
			mediumButton.setSelected(true);
		}else if(isThick()){
			thickButton.setSelected(true);
		}
	}
	
	private boolean hasColor(Color color){
		return getCurrentPen().getColor()==color;
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
	
	private JPanel getColorPanel(){
		JPanel container = new JPanel();
		container.setLayout(new BorderLayout());
		container.setBorder(new EmptyBorder(0,0,0,0));
		container.add(getLabel("Color"), BorderLayout.NORTH);
		JPanel subcontainer = new JPanel();
		setUpColorPanel(subcontainer);
		addButtonsToColorPanel(subcontainer);
		container.add(subcontainer, BorderLayout.CENTER);
		return container;
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
	
	private ColorButton colorButton1 = getColorButton(black);
	private ColorButton colorButton2 = getColorButton(green);
	private ColorButton colorButton3 = getColorButton(blue);
	private ColorButton colorButton4 = getColorButton(red);
		
	private ColorButton getColorButton(Color color){
		ColorButton button = new ColorButton(color);
		button.setPreferredSize(new Dimension(60, 60));
		button.setBorder(new EmptyBorder(8, 8, 8, 8));
		button.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				resetAllColorButtons();
				Toolbar.this.actionListener.actionPerformed(e);
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
	
	private JPanel getPenSizePanel(){
		JPanel container = new JPanel();
		container.setLayout(new BorderLayout());
		container.setBorder(new EmptyBorder(0,0,0,0));
		JPanel subcontainer = new JPanel();
		setUpPenSizePanel(subcontainer);
		container.add(getLabel("Pen Size"), BorderLayout.NORTH);
		addButtonsToPenSizePanel(subcontainer);
		container.add(subcontainer, BorderLayout.CENTER);
		return container;
	}
	
	private void setUpPenSizePanel(JPanel container){
		container.setLayout(new GridLayout(1,3));
		container.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1, true));
	}
	
	private void addButtonsToPenSizePanel(JPanel container){
		ToolbarButton buttons[] = { fineButton, mediumButton, thickButton };
		for(ToolbarButton button : buttons){
			JPanel buttonPanel = new JPanel();
			buttonPanel.setLayout(new GridLayout(1,1));
			buttonPanel.add(button);
			container.add(buttonPanel, BorderLayout.CENTER);
		}
	}
	
	public static JLabel getLabel(String string){
		JLabel label = new JLabel(string);
		label.setBorder(new EmptyBorder(5, 5, 5, 5));
		label.setHorizontalAlignment(JLabel.CENTER);
		label.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		return label;
	}
	
	private PenSizeButton fineButton = getPenSizeButton(PenSize.FINE);
	private PenSizeButton mediumButton = getPenSizeButton(PenSize.MEDIUM);
	private PenSizeButton thickButton = getPenSizeButton(PenSize.THICK);
	
	private PenSizeButton getPenSizeButton(PenSize penSize){
		PenSizeButton button = new PenSizeButton(penSize);
		button.setPreferredSize(new Dimension(60, 60));
		button.setBorder(new EmptyBorder(8, 8, 8, 8));
		button.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				resetAllPenSizeButtons();
				Toolbar.this.actionListener.actionPerformed(e);
			}
		});
		return button;
	}
	
	private void resetAllPenSizeButtons(){
		fineButton.setSelected(false);
		mediumButton.setSelected(false);
		thickButton.setSelected(false);
	}
	
	public void addActionListener(ActionListener al){
		actionListener = al;
	}
}
