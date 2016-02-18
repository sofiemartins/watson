package toolbar;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import editor.PenSize;
import editor.PenSizeButton;
import editor.Toolbar;
import editor.ToolbarButton;
import gui.Editor;

public class PenSizePanel extends JPanel{
	
	public static final long serialVersionUID = 6334254366548907865L;
	
	private PenSizeButton fineButton = getPenSizeButton(PenSize.FINE);
	private PenSizeButton mediumButton = getPenSizeButton(PenSize.MEDIUM);
	private PenSizeButton thickButton = getPenSizeButton(PenSize.THICK);
	
	private ActionListener actionListener;
	
	public PenSizePanel(){
		setLayout(new BorderLayout());
		add(Toolbar.getLabel("Pen Size"), BorderLayout.NORTH);
		JPanel subcontainer = new JPanel();
		setUpPenSizePanel(subcontainer);
		addButtonsToPenSizePanel(subcontainer);
		add(subcontainer, BorderLayout.CENTER);
		
	}
	
	private boolean isFine(){
		return Editor.currentPen.getSize()==PenSize.FINE;
	}
	
	private boolean isMedium(){
		return Editor.currentPen.getSize()==PenSize.MEDIUM;
	}
	
	private boolean isThick(){
		return Editor.currentPen.getSize()==PenSize.THICK;
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
	
	private PenSizeButton getPenSizeButton(PenSize penSize){
		PenSizeButton button = new PenSizeButton(penSize);
		button.setPreferredSize(new Dimension(60, 60));
		button.setBorder(new EmptyBorder(8, 8, 8, 8));
		button.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				resetAllPenSizeButtons();
				if(actionListener!=null){
					actionListener.actionPerformed(e);
				}
				resetAllPenSizeButtons();
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
	
	public void update(){
		checkSize();
	}

}
