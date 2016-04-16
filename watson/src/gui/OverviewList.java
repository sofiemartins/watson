package gui;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.ListSelectionModel;

import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;


import util.Lesson;

public class OverviewList extends JList<Lesson> implements MouseListener, KeyListener{
	
	public static final long serialVersionUID = 5467874654352647647L;
	
	private ActionListener actionListener;
	
	public OverviewList(){
		addMouseListener(this);
		addKeyListener(this);
		setBasicProperties();
		addElements();
	}
	
	private void setBasicProperties(){
		setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		setLayoutOrientation(JList.VERTICAL);
		setVisibleRowCount(-1);
	}
	
	private void addElements(){
		DefaultListModel<Lesson> listModel = new DefaultListModel<Lesson>();
		for(Lesson lesson : Lesson.allLessons){
			listModel.addElement(lesson);
		}
		setModel(listModel);
	}
	
	public void update(){
		addElements();
		revalidate();
	}
	
	public void addActionListener(ActionListener al){
		actionListener = al;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if(actionListener!=null){
			if(e.getClickCount()==2){
				actionListener.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "Lesson was clicked."));
			}
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}

	@Override
	public void mousePressed(MouseEvent e) {}

	@Override
	public void mouseReleased(MouseEvent e) {}

	@Override
	public void keyPressed(KeyEvent e) {}

	@Override
	public void keyReleased(KeyEvent e) {}

	@Override
	public void keyTyped(KeyEvent e) {
		if(e.getKeyChar()=='+'){
			LessonOverview.createNewLesson();
		}else if(e.getKeyChar()==' ' || e.getKeyChar()=='\n'){
			startInterrogation();
		}else if(e.getKeyChar()=='e'){
			editSelectedLesson();
		}
	}
	
	private void editSelectedLesson(){
		if(getSelectedValue()!=null){
			new EditLessonDialog(getSelectedValue());
		}
	}
	
	private void startInterrogation(){
		if(getSelectedValue()!=null){
			new Interrogation(getSelectedValue());
		}
	}

}
