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
		
		displayArea.show(cardDisplayed.getFirstSide());
		setVisible(true);
	}
	
	private JButton getNextButton(){
		JButton button = new JButton("Next");
		button.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				if(sidesSeen==0){
					displayArea.show(cardDisplayed.getSecondSide());
					sidesSeen++;
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
				if(interrogationEnd()){
					ShowCardFrame.this.dispose();
				}else if(isLastCardOnStack()){
					currentLesson = getSubLessonFromWrongAnswers();
					answers = new ArrayList<Boolean>();
				}
			}
		});
		return panel;
	}
	
	private boolean interrogationEnd(){// TODO: find better name
		if(isLastCardOnStack()){
			if(numberOfWrongAnswers()==0){
				return true;
			}else{
				return false;
			}
		}else{
			return false;
		}
	}
	
	private boolean isLastCardOnStack(){
		return currentLesson.isLastCard(currentLesson.getCurrentCard());
	}
	
	private Lesson getSubLessonFromWrongAnswers(){
		ArrayList<Card> cards = new ArrayList<Card>();
		for(int index = 0; index<answers.size(); index++){
			System.out.println("The answer of card number "+ index + " was " + answers.get(index).booleanValue());
			if(!answers.get(index).booleanValue()){
				cards.add(currentLesson.getCards().get(index));
			}
		}
		return new Lesson(cards);
	}
	
	private int numberOfWrongAnswers(){
		int wrongAnswers = 0;
		for(Boolean answer : answers){
			if(!answer.booleanValue()){
				wrongAnswers++;
			}
		}
		return wrongAnswers;
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
