package panel;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;

import static panel.EditType.*;

public class EditPanel extends Panel{
	
	public static final long serialVersionUID = 5599354632142532246L;
	
	private JButton deleteButton, addButton;
	private ActionListener actionListener;
	
	public EditPanel(){
		setLayout(new GridLayout(1,2));
		initBorder();
		initButtons();
		addButtons();
	}
	
	private void initBorder(){
		setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1, true));
	}
	
	private void initButtons(){
		initDeleteButton();
		initAddButton();
	}
	
	private void addButtons(){
		JButton buttons[] = { deleteButton, addButton };
		for(JButton button : buttons){
			add(addButtonToPanel(button));
		}
	}
	
	private JPanel addButtonToPanel(JButton button){
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridLayout(1,1));
		buttonPanel.add(button);
		return buttonPanel;
	}
	
	private void initDeleteButton(){
		deleteButton = new JButton();
		setAppearanceOfDeleteButton();
		setActionListenerOfDeleteButton();
	}
	
	private void setAppearanceOfDeleteButton(){
		deleteButton.setName("toolbarButton");
		setIcon(deleteButton, "remove.png");
	}
	
	private void setActionListenerOfDeleteButton(){
		deleteButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				actionListener.actionPerformed(new EditEvent(DELETE));
			}
		});
	}
	
	private void initAddButton(){
		addButton = new JButton();
		setAppearanceOfAddButton();
		setActionListenerOfAddButton();
	}
	
	private void setAppearanceOfAddButton(){
		addButton.setName("toolbarButton");
		setIcon(addButton, "add.png");
	}
	
	private void setActionListenerOfAddButton(){
		addButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				if(actionListener!=null){
					actionListener.actionPerformed(new EditEvent(ADD));
				}
			}
		});
	}
	
	public void addActionListener(ActionListener al){
		actionListener = al;
	}
}
