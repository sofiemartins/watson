package panel;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;

import static panel.NavigationEventType.*;

public class NavigationPanel extends Panel{
	
	public static final long serialVersionUID = 6998365766487650987L;
	
	private ActionListener actionListener;
	private JButton nextButton, previousButton, turnAroundButton, saveButton;
	
	public NavigationPanel(){
		initBorder();
		initButtons();
		addButtons();
	}
	
	private void initBorder(){
		setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1, true));
	}
	
	private void initButtons(){
		initNextButton();
		initPreviousButton();
		initTurnAroundButton();
		initSaveButton();
	}
	
	private void addButtons(){
		JButton buttons[] = { previousButton, nextButton, turnAroundButton, saveButton };
		for(JButton button : buttons){
			add(putButtonToPanel(button));
		}
	}
	
	private JPanel putButtonToPanel(JButton button){
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridLayout(1,1));
		buttonPanel.add(button);
		return buttonPanel;
	}
	
	private void initNextButton(){
		nextButton = new JButton();
		setAppearanceOfNextButton();
		setActionListenerOfNextButton();
	}
	
	private void setAppearanceOfNextButton(){
		nextButton.setName("toolbarButton");
		setIcon(nextButton, "next.png");
	}
	
	private void setActionListenerOfNextButton(){
		nextButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				actionListener.actionPerformed(new NavigationEvent(NEXT));
			}
		});
	}
	
	private void initPreviousButton(){
		previousButton = new JButton();
		setAppearanceOfPreviousButton();
		setActionListenerOfPreviousButton();
	}
	
	private void setAppearanceOfPreviousButton(){
		previousButton.setName("toolbarButton");
		setIcon(previousButton, "back.png");
	}
	
	private void setActionListenerOfPreviousButton(){
		previousButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				actionListener.actionPerformed(new NavigationEvent(BACK));
			}
		});
	}
	
	private void initTurnAroundButton(){
		turnAroundButton = new JButton();
		setAppearanceOfTurnAroundButton();
		setActionListenerOfTurnAroundButton();
		
	}
	
	private void setAppearanceOfTurnAroundButton(){
		turnAroundButton.setName("toolbarButton");
		setIcon(turnAroundButton, "otherside.png");
	}
	
	private void setActionListenerOfTurnAroundButton(){
		turnAroundButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				actionListener.actionPerformed(new NavigationEvent(TURN));
			}
		});
	}

	private void initSaveButton(){
		saveButton = new JButton();
		setAppearanceOfSaveButton();
		setActionListener();
	}
	
	private void setAppearanceOfSaveButton(){
		saveButton.setName("toolbarButton");
		setIcon(saveButton, "save.png");
	}
	
	private void setActionListener(){
		saveButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				actionListener.actionPerformed(new NavigationEvent(SAVE));
			}
		});
	}
	
	public void addActionListener(ActionListener al){
		actionListener = al;
	}

}
