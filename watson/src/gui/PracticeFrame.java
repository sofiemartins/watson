package gui;

import practice.ShowCardFrame;
import practice.AnswerFrame;
import util.Lesson;
import util.Card;

public class PracticeFrame{
	
	public static final long serialVersionUID = 8857453542858887463L;
	//TODO: implement some better training methods, not just asking everything
	
	public PracticeFrame(Lesson lesson){
		for(Card card : lesson.getCards()){
			ShowCardFrame.showCard(card);
			boolean wasAnswerRight = AnswerFrame.showAnswerDialog();
		}
	}
	
	
	

}
