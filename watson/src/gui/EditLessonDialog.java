package gui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import javax.swing.border.EmptyBorder;

import static java.awt.BorderLayout.*;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import util.Lesson;

public class EditLessonDialog extends JFrame{
	
	//TODO: Save button
	
	public static final long serialVersionUID = 4332554321662211089L;
	
	public EditLessonDialog(Lesson lesson){
		basicFrameSetup();
		addComponents();
		setVisible(true);
	}
	
	private void basicFrameSetup(){
		setSize(800,500);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
	}
	
	private void addComponents(){
		add(getTopPanel(), NORTH);
		add(new Editor(), CENTER);
		add(getBottomPanel(), SOUTH);
	}
	
	private JPanel getTopPanel(){
		JPanel container = new JPanel();
		container.setLayout(new GridLayout(1,3));
		container.setBorder(new EmptyBorder(5,50,5,600));
		container.add(backButton());
		container.add(nextButton());
		container.add(otherSideButton());
		container.add(getSaveButton());
		return container;
	}
	
	private JButton nextButton(){
		JButton button = new JButton();
		button.setIcon(new ImageIcon(getClass().getResource("next.png")));
		button.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				//next card
			}
		});
		return button;
	}
	private JButton backButton(){
		JButton button = new JButton();
		button.setIcon(new ImageIcon(getClass().getResource("back.png")));
		button.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				//one card back
			}
		});
		return button;
	}
	private JButton otherSideButton(){
		JButton button = new JButton();
		button.setIcon(new ImageIcon(getClass().getResource("otherside.png")));
		button.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				//show other side
			}
		});
		return button;
	}
	
	private JButton getSaveButton(){
		JButton button = new JButton();
		button.setIcon(new ImageIcon(getClass().getResource("save.png")));
		button.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				//save lesson
			}
		});
		return button;
	}
	
	private JPanel getBottomPanel(){
		JPanel container = new JPanel();
		container.setLayout(new GridLayout(1,2));
		container.setBorder(new EmptyBorder(5,350,5,350));
		container.add(deleteButton());
		container.add(addButton());
		return container;
	}
	
	private JButton deleteButton(){
		JButton button = new JButton();
		button.setIcon(new ImageIcon(getClass().getResource("remove.png")));
		button.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				// delete
			}
		});
		return button;
	}
	private JButton addButton(){
		JButton button = new JButton();
		button.setIcon(new ImageIcon(getClass().getResource("add.png")));
		button.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				//add card
			}
		});
		return button;
	}

}
