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
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.DefaultListModel;
import javax.swing.ListSelectionModel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
//local
import util.Lesson;

public class LessonOverview {
	
	public LessonOverview(){
		
	}
	
	protected void loadLessons(){}
	
	private void save(){}
	
	private JButton getNewButton(){
		JButton button = new JButton("New"); // TODO: add icon
		button.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				new EditLessonDialog(new Lesson());
			}
		});
		return button;
	}
	
	private JButton getRemoveButton(){
		JButton button = new JButton("Remove");
		button.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				//remove lesson
			}
		});
		return button;
	}
	
	private JButton getEditButton(){
		JButton button = new JButton("Edit");
		button.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				
			}
		});
		return button;
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
