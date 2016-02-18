package toolbar;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import editor.Pen;
import editor.PenMode;
import editor.Toolbar;
import editor.ToolbarButton;
import editor.ToolbarEvent;
import gui.Editor;

public class PenModePanel extends JPanel{
	
	public static final long serialVersionUID = 445688769987687515L;
	
	private ToolbarButton rulerButton = getRulerButton();
	private ToolbarButton rectangleButton = getRectangleButton();
	
	private ActionListener actionListener;
	
	public PenModePanel(){
		setLayout(new BorderLayout());
		add(Toolbar.getLabel("Pen Mode"), BorderLayout.NORTH);
		JPanel subcontainer = new JPanel();
		setUpPenModePanel(subcontainer);
		addButtonsToPenModePanel(subcontainer);
		add(subcontainer, BorderLayout.CENTER);
	}
	
	private void setUpPenModePanel(JPanel container){
		container.setLayout(new GridLayout(1,2));
		container.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1, true));
	}
	
	private void addButtonsToPenModePanel(JPanel container){
		ToolbarButton buttons[] = { rulerButton, rectangleButton };
		for(ToolbarButton button : buttons){
			JPanel buttonContainer = new JPanel();
			buttonContainer.setLayout(new GridLayout(1,1));
			buttonContainer.add(button);
			container.add(buttonContainer);
		}
	}
	
	private ToolbarButton getRulerButton(){
		ToolbarButton button = new ToolbarButton();
		button.setPreferredSize(new Dimension(60, 60));
		button.setBorder(new EmptyBorder(8, 4, 8, 8));
		button.setIcon(new ImageIcon(getClass().getResource("ruler.png")));
		button.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				if(actionListener!=null){
					rectangleButton.setSelected(false);
					Pen currentPen = Editor.currentPen;
					Pen newPen;
					if(!rulerButton.isSelected()){
						newPen = new Pen(currentPen.getSize(), currentPen.getColor(), PenMode.NONE, currentPen.getType());
						actionListener.actionPerformed(new ToolbarEvent(this, 
								ActionEvent.ACTION_PERFORMED,
								"Disabled ruler", 
								newPen));
					}else{
						newPen = new Pen(currentPen.getSize(), currentPen.getColor(), PenMode.RULER, currentPen.getType());
						actionListener.actionPerformed(new ToolbarEvent(this,
								ActionEvent.ACTION_PERFORMED,
								"changed to ruler",
								newPen));
					}
				}
			}
		});
		return button;
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
		button.setPreferredSize(new Dimension(60, 60));		
		button.setBorder(new EmptyBorder(8, 8, 8, 4));
		button.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				if(actionListener!=null){
					rulerButton.setSelected(false);
					Pen currentPen = Editor.currentPen;
					Pen newPen;
					if(!rectangleButton.isSelected()){
						newPen = new Pen(currentPen.getSize(), currentPen.getColor(), PenMode.NONE, currentPen.getType());
						actionListener.actionPerformed(new ToolbarEvent(this, ActionEvent.ACTION_PERFORMED, 
								"Disabled rectangle mode.",
								newPen));
					}else{
						newPen = new Pen(currentPen.getSize(), currentPen.getColor(), PenMode.SQUARE, currentPen.getType());//TODO: Refactor this to rect
						actionListener.actionPerformed(new ToolbarEvent(this,
								ActionEvent.ACTION_PERFORMED,
								"changed to rectangle mode",
								newPen));
					}
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
		rulerButton.setSelected(false);
		rectangleButton.setSelected(false);
	}
	
	private void checkMode(Pen currentPen){
		setAllModeButtonsUnselected();
		if(isRuler()){
			rulerButton.setSelected(true);
		}else if(isInRectangleMode()){
			rectangleButton.setSelected(true);
		}
	}
	
	public void update(){
		checkMode(Editor.currentPen);
	}
	
	public void addActionListener(ActionListener al){
		actionListener = al;
	}

	
}
