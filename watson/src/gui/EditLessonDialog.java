package gui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import static java.awt.BorderLayout.*;
import java.awt.BorderLayout;
import java.awt.GridLayout;

import util.Lesson;

public class EditLessonDialog extends JFrame{
	
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
		add(new CardEditor(), CENTER);
		add(getBottomPanel(), SOUTH);
	}
	
	private JPanel getTopPanel(){}
	
	private JButton nextButton(){
	}
	private JButton backButton(){}
	private JButton otherSideButton(){}
	
	private JPanel getBottomPanel(){}
	
	private JButton deleteButton(){}
	private JButton addButton(){}

}
