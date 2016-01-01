package util;

import java.io.Serializable;
import java.util.ArrayList;
import java.awt.image.BufferedImage;

public class Lesson implements Serializable{
	
	public static final long serialVersionUID = 996887968500987365L;
	// all Lessons are always accessible, because there are a lot of gui classes that have to be able to change things here.
	public static ArrayList<Lesson> allLessons = new ArrayList<Lesson>();
	
	private String title;
	private ArrayList<Card> cards;
	private Card currentCard;
	
	public Lesson(){
		cards = new ArrayList<Card>();
	}
	
	public Lesson(String lessonTitle){
		title = lessonTitle;
		cards = new ArrayList<Card>();
	}
	
	public String toString(){
		return title;
	}
	
	public Card getCurrentCard(){
		if(cards.isEmpty()){
			Card newCard = new Card();
			cards.add(newCard);
			return newCard;
		}
		if(currentCard==null){
			return cards.get(0);
		}
		return currentCard;
	}	
	
	//TODO: Adding in between features
	//TODO: Mixing cards up features
	
	public Card getNextCard(){
		int currentIndex = cards.indexOf(currentCard);
		if((cards.size()-1)==currentIndex){
			Card newCard = new Card();
			cards.add(newCard);
			return newCard;
		}else{
			return cards.get(currentIndex+1);
		}
	}
	
	public Card getPreviousCard(){
		int currentIndex = cards.indexOf(currentCard);
		if(currentIndex==0){
			return null;
		}else{
			return cards.get(currentIndex-1);
		}
	}

}
