package gui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JOptionPane;
import static java.awt.BorderLayout.*;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.io.IOException;

import util.Lesson;
import util.Card;
import io.FileManager;

import panel.EditPanel;
import panel.EditEvent;
import static panel.EditType.*;
import panel.NavigationPanel;
import panel.NavigationEvent;
import static panel.NavigationEventType.*;

public class EditLessonDialog extends JFrame{
	
	private class Dispatcher implements KeyEventDispatcher{
		@Override
		public boolean dispatchKeyEvent(KeyEvent e){
			if(e.getID()==KeyEvent.KEY_PRESSED){
				if(e.getKeyCode()==KeyEvent.VK_LEFT){
					showPreviousCard();
				}else if(e.getKeyCode()==KeyEvent.VK_RIGHT){
					showNextCard();
				}else if(e.getKeyCode()==KeyEvent.VK_DOWN || e.getKeyCode()==KeyEvent.VK_UP){
					turnAround();
				}else if((e.getKeyCode()==KeyEvent.VK_S) && (e.getModifiers() & KeyEvent.CTRL_MASK) != 0){
					saveAndCloseDialog();
				}else if((e.getKeyCode()==KeyEvent.VK_Z) && (e.getModifiers() & KeyEvent.CTRL_MASK) != 0){
					editor.undo();
				}else if((e.getKeyCode()==KeyEvent.VK_Y) && (e.getModifiers() & KeyEvent.CTRL_MASK) != 0){
					editor.redo();
				}
			}
			return false;
		}
	}
		
	public static final long serialVersionUID = 4332554321662211089L;
	
	private Lesson lesson;
	private int currentSide = 1;
	
	private Editor editor;
	private Dispatcher keyEventDispatcher = new Dispatcher();
	private KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
	
	public EditLessonDialog(Lesson l){
		setUpLesson(l);
		prepareKeyListener();
		setUpFrame();
	}
	
	private void setUpLesson(Lesson l){
		lesson = l;
		lesson.resetCurrentCard();
	}
	
	private void prepareKeyListener(){
		manager.addKeyEventDispatcher(keyEventDispatcher);
	}
	
	private void setUpFrame(){
		basicFrameSetup();
		addComponents();
		setVisible(true);
	}
	
	@Override
	public void dispose(){
		manager.removeKeyEventDispatcher(keyEventDispatcher);
		super.dispose();
	}
	
	private void basicFrameSetup(){
		initSize();
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLayout(new BorderLayout());
	}
	
	private void initSize(){
		setSize(800,500);
		setMinimumSize(new Dimension(400,400));
	}
	
	private void addComponents(){
		add(getBottomPanel(), SOUTH);
		currentSide = 1;
		editor = new Editor(lesson.getCurrentCard().getSideNumber(currentSide));
		add(editor, CENTER);
	}
	
	private JPanel getBottomPanel(){
		JPanel container = new JPanel();
		container.setLayout(new BorderLayout());
		container.add(getNavigationPanel(), BorderLayout.WEST);
		container.add(getEditPanel(), BorderLayout.EAST);
		return container;
	}
	
	private NavigationPanel getNavigationPanel(){
		NavigationPanel panel = new NavigationPanel();
		panel.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				NavigationEvent navigationEvent = (NavigationEvent)e;
				if(navigationEvent.getType()==NEXT){
					showNextCard();
				}else if(navigationEvent.getType()==BACK){
					showPreviousCard();
				}else if(navigationEvent.getType()==TURN){
					turnAround();
				}else if(navigationEvent.getType()==SAVE){
					saveAndCloseDialog();
				}else;
			}
		});
		return panel;
	}
	
	private void showPreviousCard(){
		editor.open(lesson.getPreviousCard().getSideNumber(currentSide));
	}
	
	private void showNextCard(){
		editor.open(lesson.getNextCard().getSideNumber(currentSide));
	}
	
	private void turnAround(){
		currentSide = cycle(currentSide);
		editor.open(lesson.getCurrentCard().getSideNumber(currentSide));
	}
	
	private int cycle(int sideNumber){
		if(sideNumber == 1){
			return 2;
		}else{
			return 1;
		}
	}
	
	private void showLessonOverview(){
		new LessonOverview();
	}
	
	private void saveAndCloseDialog(){
		replaceLesson(lesson);
		try{
			FileManager.save(Lesson.allLessons);
		}catch(IOException ex){
			JOptionPane.showMessageDialog(EditLessonDialog.this, 
					"An error occurred while reading the file. Please check file permissions or reinstall.");
		}
		showLessonOverview();
		EditLessonDialog.this.dispose();
	}
	
	private void replaceLesson(Lesson newLesson){
		boolean hasBeenReplaced = false;
		for(Lesson lesson : Lesson.allLessons){
			if(lesson.toString().equals(newLesson.toString())){
				int index = Lesson.allLessons.indexOf(lesson);
				Lesson.allLessons.set(index, newLesson);
				hasBeenReplaced = true;
			}
		}
		if(!hasBeenReplaced){
			Lesson.allLessons.add(newLesson);
		}
	}
	
	private EditPanel getEditPanel(){
		EditPanel panel = new EditPanel();
		panel.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				EditEvent ev = (EditEvent)e;
				if(ev.getType()==DELETE){
					delete();
				}else if(ev.getType()==ADD){
					add();
				}
			}
		});
		return panel;
	}
	
	private void delete(){
		lesson.removeCurrentCard();
		editor.open(lesson.getCurrentCard().getSideNumber(currentSide));
	}
	
	private void add(){
		lesson.addCard(new Card());
		currentSide = 1;
		editor.open(lesson.getCurrentCard().getSideNumber(currentSide)); //Because the user always wants to write on the first side after clicking on "add"
	}
}
