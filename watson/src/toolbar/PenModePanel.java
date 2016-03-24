package toolbar;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import editor.Pen;
import editor.PenMode;
import editor.Toolbar;
import editor.ToolbarButton;
import editor.ToolbarEvent;
import gui.Editor;

public class PenModePanel extends ToolbarPanel{
	
	public static final long serialVersionUID = 445688769987687515L;
	
	private ToolbarButton[] buttons = { getRulerButton(), getRectangleButton() };
	
	private ActionListener actionListener;
	
	public PenModePanel(){
		setupPanelLayout();
		add(getSubcontainer(), BorderLayout.CENTER);
	}
	
	private void setupPanelLayout(){
		setLayout(new BorderLayout());
		add(Toolbar.getLabel("Pen Mode"), BorderLayout.NORTH);
	}
	
	private JPanel getSubcontainer(){
		JPanel subcontainer = new JPanel();
		setUpPenModePanel(subcontainer);
		addButtonsToPenModePanel(subcontainer);
		return subcontainer;
	}
	
	private void setUpPenModePanel(JPanel container){
		container.setLayout(new GridLayout(1,2));
		container.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1, true));
	}
	
	private void addButtonsToPenModePanel(JPanel container){
		for(ToolbarButton button : buttons){
			container.add(getButtonContainerFor(button));
		}
	}
	
	private JPanel getButtonContainerFor(ToolbarButton button){
		JPanel buttonContainer = new JPanel();
		buttonContainer.setLayout(new GridLayout(1,1));
		buttonContainer.add(button);
		return buttonContainer;
	}
	
	private ToolbarButton getRulerButton(){
		ToolbarButton button = new ToolbarButton();
		setUpButtonLayout(button);
		setIcon(button, "ruler.png");
		button.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				if(actionListener!=null){
					adjustModeOfButton(0);
				}
			}
		});
		return button;
	}
	
	private void setUpButtonLayout(ToolbarButton button){
		button.setPreferredSize(new Dimension(60, 60));
		button.setBorder(new EmptyBorder(8, 4, 8, 8));
	}
	
	private void adjustModeOfButton(int no){
		setOtherButtonsUnselected(buttons[no]);
		if(!buttons[no].isSelected()){
			performDisablingActionOfButton(no);
		}else{
			performEnablingActionOfButton(no);
		}
	}
	
	private void setOtherButtonsUnselected(ToolbarButton buttonSelected){
		for(ToolbarButton button : buttons){
			if(!buttonSelected.equals(button)){
				button.setSelected(false);
			}
		}
	}
	
	private void performDisablingActionOfButton(int no){
		Pen newPen;
		if(no==0){
			newPen = disableRuler();
		}else{
			newPen = disableRectangle();
		}
		actionListener.actionPerformed(new ToolbarEvent(this, 
				ActionEvent.ACTION_PERFORMED,
				"Disabled ruler", 
				newPen));
	}
	
	private Pen disableRuler(){
		return new Pen(Editor.currentPen.getSize(), Editor.currentPen.getColor(), 
				PenMode.NONE, Editor.currentPen.getType());
	}
	
	private Pen disableRectangle(){
		return new Pen(Editor.currentPen.getSize(), Editor.currentPen.getColor(), PenMode.NONE, Editor.currentPen.getType());
	}
	
	private void performEnablingActionOfButton(int no){
		Pen newPen;
		if(no==0){
			newPen = enableRuler();
		}else{
			newPen = enableRectangle();
		}
		actionListener.actionPerformed(new ToolbarEvent(this,
				ActionEvent.ACTION_PERFORMED,
				"changed to ruler",
				newPen));
	}
	
	private Pen enableRuler(){
		return new Pen(Editor.currentPen.getSize(), Editor.currentPen.getColor(), 
				PenMode.RULER, Editor.currentPen.getType());
	}
	
	private Pen enableRectangle(){
		return new Pen(Editor.currentPen.getSize(), Editor.currentPen.getColor(), 
				PenMode.SQUARE, Editor.currentPen.getType());

	}
	
	private ToolbarButton getRectangleButton(){
		ToolbarButton button = new ToolbarButton(){
			public static final long serialVersionUID = 995847386758473869L;
			
			@Override
			public void paintComponent(Graphics g){
				super.paintComponent(g);
				g.setColor(Color.black);
				g.drawRect(5,5,this.getWidth()-10, this.getHeight()-10);
			}
		};
		setUpButtonLayout(button);
		button.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				if(actionListener!=null){
					adjustModeOfButton(1);
				}
			}
		});
		return button;
	}
	
	private boolean isRuler(){
		return Editor.currentPen.getMode()==PenMode.RULER;
	}
	
	private boolean isInRectangleMode(){
		return Editor.currentPen.getMode()==PenMode.SQUARE;
	}
	
	private void setAllModeButtonsUnselected(){
		for(ToolbarButton button : buttons){
			button.setSelected(false);
		}
	}
	
	public void addActionListener(ActionListener al){
		actionListener = al;
	}

	@Override
	protected void checkProperties() {
		setAllModeButtonsUnselected();
		if(isRuler()){
			buttons[0].setSelected(true);
		}else if(isInRectangleMode()){
			buttons[1].setSelected(true);
		}
	}
	
	private void setIcon(ToolbarButton button, String filepath){
		BufferedImage icon = null;
		try{
			icon = ImageIO.read(new File("res/" + filepath));
			button.setIcon(new ImageIcon(icon.getScaledInstance(25, 25, Image.SCALE_SMOOTH)));
		}catch(IOException e){
			e.printStackTrace(); //TODO: Exception handling
		}
	}

}
