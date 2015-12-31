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
		setSize(800,500);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		add(getTopPanel(), NORTH);
		add(new CardEditor(), CENTER);
		add(getBottomPanel(), SOUTH);
		setVisible(true);
	}
	
	private JPanel getTopPanel(){}
	private JPanel getBottomPanel(){}

}
