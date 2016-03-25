package panel;

import static java.awt.BorderLayout.NORTH;
import static java.awt.BorderLayout.SOUTH;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import static panel.MainControlEventType.*;

public class MainControlPanel extends Panel{
	
	public static final long serialVersionUID = 8594049384736728743L;
	
	private static final String TOOLBAR_BUTTON = "toolbarButton";
	private ActionListener actionListener;
	
	public MainControlPanel(){
		setLayout(new BorderLayout());
		add(getButtonPanelSubPanel(), NORTH);
		add(getStartPractisingButton(), SOUTH);
	}
	
	private JPanel getButtonPanelSubPanel(){
		JPanel subcontainer = new JPanel();
		subcontainer.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1, true));
		subcontainer.setLayout(new GridLayout(3,1));
		subcontainer.add(getNewButton());
		subcontainer.add(getRemoveButton());
		subcontainer.add(getEditButton());
		return subcontainer;
	}
		
	private JPanel getNewButton(){
		JPanel container = new JPanel();
		container.setLayout(new GridLayout(1,1));
		container.setBorder(new EmptyBorder(8, 8, 4, 8));
		JButton button = new JButton("New");
		button.setName(TOOLBAR_BUTTON);
		button.setVerticalTextPosition(SwingConstants.BOTTOM);
		button.setHorizontalTextPosition(SwingConstants.CENTER);
		button.setSize(100, 100);
		setIcon(button, "add.png");
		button.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				if(actionListener!=null){
					actionListener.actionPerformed(new MainControlEvent(NEW));
				}
			}
			
		});
		container.add(button);
		return container;
	}
	
	
	
	private JPanel getStartPractisingButton(){
		JPanel container = new JPanel();
		container.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1, true));
		container.setLayout(new GridLayout(1,1));
		JPanel subcontainer = new JPanel();
		subcontainer.setLayout(new GridLayout(1,1));
		JButton button = new JButton("Start");
		button.setName(TOOLBAR_BUTTON);
		button.setVerticalTextPosition(SwingConstants.BOTTOM);
		button.setHorizontalTextPosition(SwingConstants.CENTER);
		setIcon(button, "add.png");
		button.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				if(actionListener!=null){
					actionListener.actionPerformed(new MainControlEvent(START));
				}
			}
		});
		subcontainer.add(button);
		container.add(subcontainer);
		return container;
	}
	
	private JPanel getRemoveButton(){
		JPanel container = new JPanel();
		container.setLayout(new GridLayout(1,1));
		container.setBorder(new EmptyBorder(4, 8, 4, 8));
		JButton button = new JButton("Delete");
		button.setName(TOOLBAR_BUTTON);
		button.setVerticalTextPosition(SwingConstants.BOTTOM);
		button.setHorizontalTextPosition(SwingConstants.CENTER);
		setIcon(button, "remove.png");
		button.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				if(actionListener!=null){
					actionListener.actionPerformed(new MainControlEvent(DELETE));	
				}
			}
		});
		container.add(button);
		return container;
	}
	
	private JPanel getEditButton(){
		JPanel container = new JPanel();
		container.setLayout(new GridLayout(1,1));
		container.setBorder(new EmptyBorder(4, 8, 8, 8));
		JButton button = new JButton("Edit");
		button.setName(TOOLBAR_BUTTON);
		button.setVerticalTextPosition(SwingConstants.BOTTOM);
		button.setHorizontalTextPosition(SwingConstants.CENTER);
		setIcon(button, "edit.png");
		button.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				if(actionListener!=null){
					actionListener.actionPerformed(new MainControlEvent(EDIT));
				}
			}
		});
		container.add(button);
		return container;
	}
	
	public void addActionListener(ActionListener al){
		actionListener = al;
	}

}
