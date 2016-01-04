package practice;

import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.BorderLayout;
import java.util.ArrayList;

import util.Card;
import util.Lesson;

public class ShowCardFrame extends JFrame{
	
	public static final long serialVersionUID = 998564736654354543L;
	
	private int sidesSeen = 0;
	private DisplayArea displayArea;
	private JButton nextButton;
	private AnswerPanel answerPanel;
	private Card cardDisplayed;
	private Lesson currentLesson;
	public ArrayList<Boolean> answers = new ArrayList<Boolean>();// TODO: Find some collection that has the needed functionality
	
	public ShowCardFrame(Lesson lesson){
		currentLesson = lesson;
		cardDisplayed = lesson.getCurrentCard();
		setUpFrame();
	}
	
	private void setUpFrame(){
		setLayout(new BorderLayout());
		setSize(800,500);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		displayArea = new DisplayArea(cardDisplayed.getFirstSide());
		nextButton = getNextButton();
		answerPanel = getAnswerPanel();
		
		add(displayArea, BorderLayout.CENTER);
		add(getNextButton(), BorderLayout.SOUTH);
		setVisible(true);
	}
	
	private JButton getNextButton(){
		JButton button = new JButton("Next");
		button.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				sidesSeen++;
				if(sidesSeen==1){
					displayArea.show(cardDisplayed.getSecondSide());
				}else{
					cardDisplayed = currentLesson.getNextCard();
					sidesSeen = 0;
					changeToAnswerFrame();
				}
			}
		});
		return button;
	}
	
	private AnswerPanel getAnswerPanel(){
		AnswerPanel panel = new AnswerPanel();
		panel.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				AnswerEvent event = (AnswerEvent)e;
				answers.add(new Boolean(event.getAnswer()));
				changeToShowCardFrame();
			}
		});
		return panel;
	}
	
	private void changeToAnswerFrame(){
		getContentPane().removeAll();
		add(answerPanel, BorderLayout.CENTER);
		revalidate();
		repaint();
	}
	
	private void changeToShowCardFrame(){
		getContentPane().removeAll();
		add(displayArea, BorderLayout.CENTER);
		add(nextButton, BorderLayout.SOUTH);
		revalidate();
		repaint();
		displayArea.show(cardDisplayed.getFirstSide());
	}
}
