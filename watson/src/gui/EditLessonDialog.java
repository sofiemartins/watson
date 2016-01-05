package gui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import javax.swing.border.EmptyBorder;
import javax.swing.JOptionPane;
import static java.awt.BorderLayout.*;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.io.IOException;

import util.Lesson;
import util.Card;
import io.FileManager;

public class EditLessonDialog extends JFrame implements KeyListener{
	
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
		lesson = l;
		lesson.resetCurrentCard();
		addKeyListener(this);
		basicFrameSetup();
		addComponents();
		manager.addKeyEventDispatcher(keyEventDispatcher);
		setVisible(true);
	}
	
	public void dispose(){
		manager.removeKeyEventDispatcher(keyEventDispatcher);
		super.dispose();
	}
	
	private void basicFrameSetup(){
		setSize(800,500);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
	}
	
	private void addComponents(){
		add(getTopPanel(), NORTH);
		editor = new Editor(lesson.getCurrentCard().getSideNumber(currentSide));
		currentSide = 1;
		add(editor, CENTER);
		add(getBottomPanel(), SOUTH);
	}
	
	private JPanel getTopPanel(){
		JPanel container = new JPanel();
		container.setLayout(new GridLayout(1,3));
		container.setBorder(new EmptyBorder(5,50,5,550));
		container.add(previousButton());
		container.add(nextButton());
		container.add(turnAroundButton());
		container.add(getSaveButton());
		return container;
	}
	
	private JButton nextButton(){
		JButton button = new JButton();
		button.setIcon(new ImageIcon(getClass().getResource("next.png")));
		button.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				showNextCard();
			}
		});
		return button;
	}
	
	private void showNextCard(){
		editor.open(lesson.getNextCard().getSideNumber(currentSide));
	}
	
	private JButton previousButton(){
		JButton button = new JButton();
		button.setIcon(new ImageIcon(getClass().getResource("back.png")));
		button.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				showPreviousCard();
			}
		});
		return button;
	}
	
	private void showPreviousCard(){
		editor.open(lesson.getPreviousCard().getSideNumber(currentSide));
	}
	
	private JButton turnAroundButton(){
		JButton button = new JButton();
		button.setIcon(new ImageIcon(getClass().getResource("otherside.png")));
		button.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				turnAround();
			}
		});
		button.grabFocus();
		return button;
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
	
	private JButton getSaveButton(){
		JButton button = new JButton();
		button.setIcon(new ImageIcon(getClass().getResource("save.png")));
		button.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				saveAndCloseDialog();
			}
		});
		return button;
	}
	
	private void saveAndCloseDialog(){
		replaceLesson(lesson);
		try{
			FileManager.save(Lesson.allLessons);
		}catch(IOException ex){
			JOptionPane.showMessageDialog(EditLessonDialog.this, 
					"An error occurred while reading the file. Please check file permissions or reinstall.");
		}
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
				lesson.removeCurrentCard();
				editor.open(lesson.getCurrentCard().getSideNumber(currentSide));
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
				lesson.addCard(new Card());
				editor.open(lesson.getCurrentCard().getSideNumber(currentSide));
			}
		});
		return button;
	}

	@Override
	public void keyPressed(KeyEvent e) {}

	@Override
	public void keyReleased(KeyEvent e) {}

	@Override
	public void keyTyped(KeyEvent e) {
		System.out.println("Hello World");
	}
}
