package gui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.JButton;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.border.EmptyBorder;
import javax.swing.JOptionPane;
import static java.awt.BorderLayout.*;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.awt.event.KeyEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.io.File;
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
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLayout(new BorderLayout());
		setExtendedState(JFrame.MAXIMIZED_BOTH);
	}
	
	private void addComponents(){
		add(getBottomPanel(), SOUTH);
		editor = new Editor(lesson.getCurrentCard().getSideNumber(currentSide));
		currentSide = 1;
		add(editor, CENTER);
	}
	
	private JPanel getBottomPanel(){
		JPanel container = new JPanel();
		container.setLayout(new BorderLayout());
		container.add(getNavigationPanel(), BorderLayout.WEST);
		container.add(getEditPanel(), BorderLayout.EAST);
		return container;
	}
	
	private JPanel getNavigationPanel(){
		JPanel container = new JPanel();
		container.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1, true));
		container.setLayout(new GridLayout(1,3));
		JButton buttons[] = { previousButton(), nextButton(), turnAroundButton(), getSaveButton() };
		for(JButton button : buttons){
			JPanel buttonPanel = new JPanel();
			buttonPanel.setLayout(new GridLayout(1,1));
			buttonPanel.add(button);
			container.add(buttonPanel);
		}
		return container;
	}
	
	private JButton nextButton(){
		JButton button = new JButton();
		button.setName("toolbarButton");
		setIcon(button, "next.png");
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
		button.setName("toolbarButton");
		setIcon(button, "back.png");
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
		button.setName("toolbarButton");
		setIcon(button, "otherside.png");
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
		button.setName("toolbarButton");
		setIcon(button, "save.png");
		button.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				saveAndCloseDialog();
			}
		});
		return button;
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
				System.out.println("replacing lesson " + lesson.toString() + " with lesson " + newLesson.toString());//TODO:
				hasBeenReplaced = true;
			}
		}
		if(!hasBeenReplaced){
			Lesson.allLessons.add(newLesson);
		}
	}
	
	private JPanel getEditPanel(){
		JPanel container = new JPanel();
		container.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1, true));
		container.setLayout(new GridLayout(1,2));
		JButton buttons[] = { deleteButton(), addButton() };
		for(JButton button : buttons){
			JPanel buttonPanel = new JPanel();
			buttonPanel.setLayout(new GridLayout(1,1));
			buttonPanel.add(button);
			container.add(buttonPanel);
		}
		return container;
	}
	
	private JButton deleteButton(){
		JButton button = new JButton();
		button.setName("toolbarButton");
		setIcon(button, "remove.png");
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
		button.setName("toolbarButton");
		setIcon(button, "add.png");
		button.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				lesson.addCard(new Card());
				currentSide = 1;
				editor.open(lesson.getCurrentCard().getSideNumber(currentSide)); //Because the user always wants to write on the first side after clicking on "add"
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
	
	private void setIcon(JButton button, String filepath){
		BufferedImage image = null;
		try{
			image = ImageIO.read(new File("res/" + filepath));
		}catch(Exception e){
			e.printStackTrace();//TODO: Exception handling
		}
		button.setIcon(new ImageIcon(image.getScaledInstance(25, 25, Image.SCALE_SMOOTH)));
	}
}
