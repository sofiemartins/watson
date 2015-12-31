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
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.DefaultListModel;
import javax.swing.ListSelectionModel;
import javax.swing.JOptionPane;
import javax.swing.border.EmptyBorder;
import javax.swing.ImageIcon;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.BorderLayout;
import static java.awt.BorderLayout.*;
import java.awt.GridLayout;
import java.awt.Image;
import javax.imageio.ImageIO;
import java.io.IOException;
//local
import util.Lesson;
import io.FileManager;

public class LessonOverview extends JFrame{
	
	public static final long serialVersionUID = 5543266543547765465L;
	
	public LessonOverview(){
		loadLessons();
		setupFrameLayout();
		
	}
	
	protected void loadLessons(){
		try{
			Lesson.allLessons = FileManager.getLessons();
		}catch(Exception e){
			JOptionPane.showMessageDialog(this, "A severe error occurred. To fix this, please reinstall the program.", 
					"Error", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	private void setupFrameLayout(){
		setSize(800,500);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
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
				new EditLessonDialog(new Lesson());
			}
		});
		return button;
	}
	
	
	
	private JButton getRemoveButton(){
		JButton button = new JButton();
		setIcon(button, "remove.png");
		button.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				//remove lesson
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
				
			}
		});
		return button;
	}
	
	private void setIcon(JButton button, String filepath){
		button.setIcon(new ImageIcon(getClass().getResource(filepath)));
	}
	
	
	private JScrollPane getOverviewList(){
		JList<Lesson> list = new JList<Lesson>();
		setBasicProperties(list);
		addElementsTo(list);
		return new JScrollPane(list);
	}
	
	private void setBasicProperties(JList<Lesson> list){
		list.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		list.setLayoutOrientation(JList.HORIZONTAL_WRAP);
		list.setVisibleRowCount(-1);
	}
	
	private void addElementsTo(JList<Lesson> list){
		DefaultListModel<Lesson> listModel = new DefaultListModel<Lesson>();
		for(Lesson lesson : Lesson.allLessons){
			listModel.addElement(lesson);
		}
		list.setModel(listModel);
	}

}
