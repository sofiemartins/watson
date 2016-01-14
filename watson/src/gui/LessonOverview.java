/**
 *  This shows up after starting the program and is some kind of "main menu".
 *  What users can do here:
 *  <ol>
 *    <li> add lessons
 *    <li> remove lessons
 *    <li> edit lessons
 *    <li> start practicing a certain lesson
 *  </ol>
 */

package gui;

// java lib
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JOptionPane;
import javax.swing.border.EmptyBorder;
import javax.swing.ImageIcon;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.BorderLayout;
import static java.awt.BorderLayout.*;
import java.awt.GridLayout;
//local
import util.Lesson;
import io.FileManager;

public class LessonOverview extends JFrame{
	
	//TODO: Make it possible to maximize the frame!
	
	public static final long serialVersionUID = 5543266543547765465L;
	
	private OverviewList overviewList;
	
	public LessonOverview(){
		loadLessons();
		setupFrameLayout();
	}
	
	protected void loadLessons(){
		try{
			Lesson.allLessons = FileManager.getLessons();
		}catch(Exception e){
			e.printStackTrace();
			JOptionPane.showMessageDialog(this, "A severe error occurred. To fix this, please reinstall the program.", 
					"Error", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public void dispose(){
		super.dispose();
		try{
			FileManager.save(Lesson.allLessons);
		}catch(Exception e){
			JOptionPane.showMessageDialog(null, "An error occurred while saving.");
			System.exit(1);
		}
		System.exit(0);
	}
	
	private void setupFrameLayout(){
		setSize(800,500);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setLayout(new BorderLayout());
		add(getOverviewList(), CENTER);
		add(getButtonPanel(), SOUTH);
		setVisible(true);
	}
	
	private JPanel getButtonPanel(){
		JPanel container = new JPanel();
		container.setLayout(new GridLayout(1,3));
		container.setBorder(new EmptyBorder(5,320,5,320));
		container.add(getNewButton());
		container.add(getRemoveButton());
		container.add(getEditButton());
		return container;
	}
		
	private JButton getNewButton(){
		JButton button = new JButton(); 
		setIcon(button, "add.png");
		button.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				createNewLesson();
				LessonOverview.super.dispose();
			}
		});
		return button;
	}
	
	protected static void createNewLesson(){
		String validTitle = getValidTitle();
		if(!validTitle.equals("-1")){//TODO: find a better way for dealing with the cancel button!
			new EditLessonDialog(new Lesson(validTitle));// TODO: dispose lesson overview?
		}
	}
	
	//TODO: clean up
	private static String getValidTitle(){
		String lessonTitle = JOptionPane.showInputDialog("Please choose a title: ");
		if(lessonTitle==null){
			return "-1";
		}
		while(!titleValid(lessonTitle)){
			lessonTitle = reaskFor(lessonTitle);
			if(lessonTitle==null){
				return "-1";
			}
		}
		return lessonTitle;
	}
	
	private static String reaskFor(String string){//TODO: no parent is not so great.
		JOptionPane.showMessageDialog(null, "The title has to be unique and not blank!");
		string = null;
		return JOptionPane.showInputDialog("Please choose a title: ");
	}
	
	protected static boolean titleValid(String title){
		for(Lesson lesson : Lesson.allLessons){
			if(lesson.toString().equals(title)){
				return false;
			}
		}
		if(title.equals("")){
			return false;
		}
		return true;
	}
	
	private JButton getRemoveButton(){
		JButton button = new JButton();
		setIcon(button, "remove.png");
		button.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				//TODO: remove
			}
		});
		return button;
	}
	
	private JButton getEditButton(){
		JButton button = new JButton();
		setIcon(button, "edit.png");
		button.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				Lesson lesson = overviewList.getSelectedValue();
				edit(lesson);
				LessonOverview.super.dispose();
			}
		});
		return button;
	}
	
	protected static void edit(Lesson lesson){
		new EditLessonDialog(lesson);
	}
	
	private void setIcon(JButton button, String filepath){
		button.setIcon(new ImageIcon(getClass().getResource(filepath)));
	}
	
	
	private JScrollPane getOverviewList(){
		overviewList = new OverviewList();
		overviewList.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				startInterrogation();
			}
		});
		return new JScrollPane(overviewList);
	}
	
	private void startInterrogation(){
		Lesson selectedLesson = overviewList.getSelectedValue();
		new Interrogation(selectedLesson);
	}
}
