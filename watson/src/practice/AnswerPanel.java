package practice;

import javax.swing.JPanel;
import java.awt.KeyEventDispatcher;
import java.awt.event.KeyEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.GridLayout;
import java.awt.KeyboardFocusManager;

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
	
	public AnswerButton rightButton = getRightButton();
	private AnswerButton wrongButton = getWrongButton();
	
	private Dispatcher keyEventDispatcher = new Dispatcher();
	private KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
	
	public AnswerPanel(){
		setLayout(new GridLayout(1,2));
		setSize(800,500);
		add(rightButton);
		add(wrongButton);
		manager.addKeyEventDispatcher(keyEventDispatcher);
		setVisible(true);
	}
	
	public void dispose(){
		manager.removeKeyEventDispatcher(keyEventDispatcher);
	}
	
	private AnswerButton getRightButton(){
		AnswerButton button = new AnswerButton(ButtonType.RIGHT);
		button.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				answerRight();
			}
		});
		return button;
	}
	
	private void answerRight(){
		actionListener.actionPerformed(new AnswerEvent(this, ActionEvent.ACTION_PERFORMED, "Question has been answered.", true));

	}
	
	private AnswerButton getWrongButton(){
		AnswerButton button = new AnswerButton(ButtonType.WRONG);
		button.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				answerWrong();
			}
		});
		return button;
	}
	
	private void answerWrong(){
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
