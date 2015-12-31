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

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class LessonOverview {
	
	public LessonOverview(){
		
	}
	
	private void load(){}
	
	private void save(){}
	
	public JButton getNewButton(){
		JButton button = new JButton("New"); // TODO: add icon
		button.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				new NewLessonDialog();
			}
		});
		return button;
	}
	
	public JButton getRemoveButton(){
		JButton button = new JButton("Remove");
		button.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				//remove lesson
			}
		});
		return button;
	}
	
	public JButton getEditButton(){
		JButton button = new JButton("Edit");
		button.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				
			}
		})
	}
	
	

}
