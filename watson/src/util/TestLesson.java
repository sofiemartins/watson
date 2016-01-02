package util;

import static org.junit.Assert.*;

import org.junit.Test;

public class TestLesson {

	@Test
	public void testConstructor(){
		Lesson lesson = new Lesson();
		assertFalse(lesson.cards==null);
		assertFalse(lesson.cards.isEmpty());
		Lesson lesson2 = new Lesson("this is a lesson title");
		assertFalse(lesson2.cards==null);
		assertFalse(lesson2.cards.isEmpty());
		assertEquals(lesson2.toString(), "this is a lesson title");
	}
	
	@Test
	public void testGetCurrentCard(){
		Lesson lesson = new Lesson();
		Card card = lesson.getCurrentCard();
		assertEquals(card, lesson.cards.get(0));
	}
	
	@Test
	public void testGetNextCard(){
		Lesson lesson = new Lesson();
		assertEquals(1,lesson.cards.size());
		Card card = new Card();
		lesson.addCard(card);
		assertEquals(lesson.getCurrentCard(), card);
		assertEquals(2,lesson.cards.size());
		assertNotEquals(card, null);
		assertEquals(card, lesson.cards.get(1));
	}
	
	@Test
	public void testGetPreviousCard(){
		Lesson lesson = new Lesson();
		lesson.getNextCard();
		Card card = lesson.getPreviousCard();
		assertEquals(lesson.cards.get(0), card);
		
		Lesson lesson2 = new Lesson();
		assertEquals(lesson2.getCurrentCard(), lesson2.cards.get(0));
		lesson2.getNextCard();
		assertEquals(lesson2.getCurrentCard(), lesson2.cards.get(1));
		lesson2.getPreviousCard();
		assertEquals(lesson2.getCurrentCard(), lesson2.cards.get(0));
		Card card2 = lesson2.getPreviousCard();
		assertEquals(lesson2.getCurrentCard(), lesson2.cards.get(1));
		assertEquals(lesson2.cards.get(1), card2);
	}
	
	@Test
	public void testAddCard(){
		Lesson lesson = new Lesson();
		lesson.addCard(new Card());
		lesson.addCard(new Card());
		lesson.addCard(new Card());
		lesson.addCard(new Card());
		assertEquals(lesson.getCurrentCard(), lesson.cards.get(4));
	}
	
	@Test
	public void testRemoveCard(){
		Lesson lesson = new Lesson();
		lesson.addCard(new Card());
		lesson.addCard(new Card());
		lesson.addCard(new Card());
		lesson.addCard(new Card());
		lesson.getPreviousCard();
		lesson.removeCurrentCard();
		//test that size is reduced
		assertEquals(4, lesson.cards.size());
		//test that the current card is the one that was before the removed card
		assertEquals(2, lesson.cards.indexOf(lesson.getCurrentCard()));
		//test that if the first card is removed, the index of the current card will stay at 0.
		lesson.getPreviousCard();
		lesson.getPreviousCard();
		lesson.removeCurrentCard();
		assertEquals(3, lesson.cards.size());
		assertEquals(0, lesson.cards.indexOf(lesson.getCurrentCard()));
		lesson.removeCurrentCard();
		assertEquals(2, lesson.cards.size());
		assertEquals(0, lesson.cards.indexOf(lesson.getCurrentCard()));
		//when the last one is removed, the lesson will always stay with one empty card
		lesson.removeCurrentCard();
		lesson.removeCurrentCard();
		assertEquals(1, lesson.cards.size());
		lesson.removeCurrentCard();
		assertEquals(1, lesson.cards.size());
	}

}
