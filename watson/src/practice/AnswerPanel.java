package practice;

import javax.swing.JPanel;
import java.awt.KeyEventDispatcher;
import java.awt.event.KeyEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.GridLayout;
import java.awt.KeyboardFocusManager;

import util.Lesson;

public class AnswerPanel extends JPanel{
	
	private class Dispatcher implements KeyEventDispatcher{
		@Override
		public boolean dispatchKeyEvent(KeyEvent e){
			if(active){
				if(e.getID()==KeyEvent.KEY_PRESSED){
					if(e.getKeyCode()==KeyEvent.VK_LEFT){
						rightButton.activate();
					}else if(e.getKeyCode()==KeyEvent.VK_RIGHT){
						wrongButton.activate();
					}
				}else if(e.getID()==KeyEvent.KEY_RELEASED){
					if(e.getKeyCode()==KeyEvent.VK_LEFT){
						answerRight();
					}else if(e.getKeyCode()==KeyEvent.VK_RIGHT){
						answerWrong();
					}
				}
			}
			return false;
		}
	}
	
	public static final long serialVersionUID = 995887873425167890L;
	
	private ActionListener actionListener;
	
	protected boolean active;
	
	public RightAnswerButton rightButton = getRightButton();
	private WrongAnswerButton wrongButton = getWrongButton();
	
	private Dispatcher keyEventDispatcher = new Dispatcher();
	private KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
	
	private Lesson lesson;
	
	public AnswerPanel(Lesson currentLesson){
		setLayout(new GridLayout(1,2));
		setSize(800,500);
		add(rightButton);
		add(wrongButton);
		manager.addKeyEventDispatcher(keyEventDispatcher);
		lesson = currentLesson;
		setVisible(true);
	}
	
	public void dispose(){
		manager.removeKeyEventDispatcher(keyEventDispatcher);
	}
	
	private RightAnswerButton getRightButton(){
		RightAnswerButton button = new RightAnswerButton();
		button.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				answerRight();
			}
		});
		return button;
	}
	
	private void answerRight(){
		lesson.answeredRight();
		actionListener.actionPerformed(new AnswerEvent(this, ActionEvent.ACTION_PERFORMED, "Question has been answered.", true));

	}
	
	private WrongAnswerButton getWrongButton(){
		WrongAnswerButton button = new WrongAnswerButton();
		button.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				answerWrong();
			}
		});
		return button;
	}
	
	private void answerWrong(){
		lesson.answeredWrong();
		actionListener.actionPerformed(new AnswerEvent(this, ActionEvent.ACTION_PERFORMED, "Question has been answered.", false));
	}
	
	public void addActionListener(ActionListener al){
		actionListener = al;
	}
	
	public void activate(){
		active = true;
	}
			
	public void disable(){
		active = false;
	}
}
