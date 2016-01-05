package practice;

import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.KeyEventDispatcher;
import java.awt.event.KeyEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.KeyboardFocusManager;

public class AnswerPanel extends JPanel{
	
	private class Dispatcher implements KeyEventDispatcher{
		@Override
		public boolean dispatchKeyEvent(KeyEvent e){
			if(active){
				if(e.getID()==KeyEvent.KEY_PRESSED){
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
	
	public JButton rightButton = getRightButton();
	
	private Dispatcher keyEventDispatcher = new Dispatcher();
	private KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
	
	public AnswerPanel(){
		setLayout(new GridLayout(1,2));
		setSize(800,500);
		add(rightButton);
		add(getWrongButton());
		manager.addKeyEventDispatcher(keyEventDispatcher);
		setVisible(true);
	}
	
	public void dispose(){
		manager.removeKeyEventDispatcher(keyEventDispatcher);
	}
	
	private JButton getRightButton(){
		JButton button = new JButton(){
			public static final long serialVersionUID = 3347685748876236548L;
			@Override
			public void paintComponent(Graphics g){
				super.paintComponent(g);
				g.setColor(new Color(0,200,0));
				g.fillRect(10,10,getWidth()-20,getHeight()-20);
				g.setColor(new Color(0,100,0));
				g.drawRect(10,10,getWidth()-20,getHeight()-20);
			}
		};
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
	
	private JButton getWrongButton(){
		JButton button = new JButton(){
			public static final long serialVersionUID = 4876587927564657776L;
			@Override
			public void paintComponent(Graphics g){
				super.paintComponent(g);
				g.setColor(new Color(200,0,0));
				g.fillRect(10,10,getWidth()-20,getHeight()-20);
				g.setColor(new Color(100,0,0));
				g.drawRect(10,10,getWidth()-20,getHeight()-20);
			}
		};
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
