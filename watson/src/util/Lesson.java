package util;

import java.io.Serializable;
import java.util.ArrayList;

public class Lesson implements Serializable{
	
	public static final long serialVersionUID = 996887968500987365L;
	// all Lessons are always accessible, because there are a lot of gui classes that have to be able to change things here.
	public static ArrayList<Lesson> allLessons = new ArrayList<Lesson>();
	
	private String title;
	protected ArrayList<Card> cards;
	private Card currentCard;
	
	public Lesson(){
		cards = new ArrayList<Card>();
		cards.add(new Card());
	}
	
	public Lesson(String lessonTitle){
		title = lessonTitle;
		cards = new ArrayList<Card>();
		cards.add(new Card());
	}
	
	public String toString(){
		return title;
	}
	
	public Card getCurrentCard(){
		if(currentCard==null){
			return cards.get(0);
		}
		return currentCard;
	}	
		
	public Card getNextCard(){ 
		int currentIndex = cards.indexOf(getCurrentCard());
		currentCard = cycleThroughElementsForward(currentIndex);
		return currentCard;
	}
	
	public Card getPreviousCard(){
		int currentIndex = cards.indexOf(getCurrentCard());
		currentCard = cycleThroughElementsBackwards(currentIndex);
		return currentCard;
	}
	
	protected Card cycleThroughElementsBackwards(int currentIndex){
		if(currentIndex<=0){
			return cards.get(cards.size()-1);
		}else{
			return cards.get(currentIndex-1);
		}
	}
	
	protected Card cycleThroughElementsForward(int currentIndex){
		if(currentIndex>=cards.size()-1){
			return cards.get(0);
		}else{
			return cards.get(currentIndex+1);
		}
	}
	
	
	public void addCard(Card card){
		int currentIndex = cards.indexOf(getCurrentCard());
		cards.add(currentIndex+1,card);
		currentCard = card;
	}
	
	public void removeCurrentCard(){
		int currentIndex = cards.indexOf(getCurrentCard());
		cards.remove(getCurrentCard());
		addEmptyCardIfThisWasTheLastOne();
		showPreviousCard(currentIndex);
	}
	
	private void addEmptyCardIfThisWasTheLastOne(){ //TODO: better name ...?
		if(cards.size()==0){
			cards.add(new Card());
		}
	}
	
	private void showPreviousCard(int currentIndex){
		if(currentIndex==0){
			currentCard = cards.get(0);
		}else{
			currentCard = cards.get(currentIndex-1);
		}
	}
	
	public ArrayList<Card> getCards(){
		return cards;
	}
}
