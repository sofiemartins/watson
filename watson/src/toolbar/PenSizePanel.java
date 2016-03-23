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

public class PenSizePanel extends ToolbarPanel{
	
	public static final long serialVersionUID = 6334254366548907865L;
	
	private PenSizeButton buttons[] = { getPenSizeButton(PenSize.FINE), 
										getPenSizeButton(PenSize.MEDIUM),
										getPenSizeButton(PenSize.THICK),
										getPenSizeButton(PenSize.FINE)};
	
	private ActionListener actionListener;
	
	public PenSizePanel(){
		setUpPanelLayout();
		add(getSubcontainer(), BorderLayout.CENTER);
	}
	
	private void setUpPanelLayout(){
		setLayout(new BorderLayout());
		add(Toolbar.getLabel("Pen Size"), BorderLayout.NORTH);
	}
	
	private JPanel getSubcontainer(){
		JPanel subcontainer = new JPanel();
		setUpPenSizePanel(subcontainer);
		addButtonsToPenSizePanel(subcontainer);
		return subcontainer;
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
	
	private void setUpPenSizePanel(JPanel container){
		container.setLayout(new GridLayout(1,3));
		container.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1, true));
	}
	
	private void addButtonsToPenSizePanel(JPanel container){
		for(ToolbarButton button : buttons){
			JPanel buttonPanel = new JPanel();
			buttonPanel.setLayout(new GridLayout(1,1));
			buttonPanel.add(button);
			container.add(buttonPanel, BorderLayout.CENTER);
		}
	}
	
	private PenSizeButton getPenSizeButton(PenSize penSize){
		PenSizeButton button = new PenSizeButton(penSize);
		setupButtonLayout(button);
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
	
	private void setupButtonLayout(ToolbarButton button){
		button.setPreferredSize(new Dimension(60, 60));
		button.setBorder(new EmptyBorder(8, 8, 8, 8));
	}
	
	private void resetAllPenSizeButtons(){
		for(ToolbarButton button : buttons){
			button.setSelected(false);
		}
	}
	
	public void addActionListener(ActionListener al){
		actionListener = al;
	}
	
	@Override
	protected void checkProperties() {
		resetAllPenSizeButtons();
		if(isFine()){
			buttons[0].setSelected(true);
		}else if(isMedium()){
			buttons[1].setSelected(true);
		}else if(isThick()){
			buttons[2].setSelected(true);
		}
	}

}
