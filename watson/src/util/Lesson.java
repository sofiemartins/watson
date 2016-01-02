package util;

import java.io.Serializable;
import java.util.ArrayList;
import java.io.IOException;
import javax.swing.JOptionPane;

import io.FileManager;

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
		if(cards.size()==currentIndex+1){
			Card newCard = new Card();
			cards.add(newCard);
			currentCard = newCard;
			return newCard;
		}else{
			Card newCurrent = cards.get(currentIndex+1);
			currentCard = newCurrent;
			return currentCard;
		}
	}
	
	public Card getPreviousCard(){
		int currentIndex = cards.indexOf(getCurrentCard());
		Card newCurrent = cycleThroughElements(currentIndex);
		currentCard = newCurrent;
		return newCurrent;
	}
	
	private Card cycleThroughElements(int currentIndex){
		if(currentIndex<=0){
			return cards.get(cards.size()-1);
		}else{
			return cards.get(currentIndex-1);
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
}
