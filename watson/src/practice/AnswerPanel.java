package practice;

import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.event.KeyEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.GridLayout;

public class AnswerPanel extends JPanel{
	
	public static final long serialVersionUID = 995887873425167890L;
	
	private ActionListener actionListener;
	
	protected boolean answer;
	
	public AnswerPanel(){
		setLayout(new GridLayout(1,2));
		setSize(800,500);
		add(getRightButton());
		add(getWrongButton());
		setVisible(true);
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
				actionListener.actionPerformed(new AnswerEvent(this, ActionEvent.ACTION_PERFORMED, "Question has been answered.", true));
			}
		});
		return button;
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
				actionListener.actionPerformed(new AnswerEvent(this, ActionEvent.ACTION_PERFORMED, "Question has been answered.", false));
			}
		});
		return button;
	}
	
	public void addActionListener(ActionListener al){
		actionListener = al;
	}
}
