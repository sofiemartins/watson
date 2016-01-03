package gui;

import practice.ShowCardFrame;
import util.Lesson;
import util.Card;

public class Interrogation{
	
	public static final long serialVersionUID = 8857453542858887463L;
	//TODO: implement some better training methods, not just asking everything
	
	public Interrogation(Lesson lesson){
		for(Card card : lesson.getCards()){
			new ShowCardFrame(card);
		}
		lesson.incrementNumberOfTries();
	}
	
	
	

}
