package stats;

import static org.junit.Assert.*;

import org.junit.Test;

import util.Lesson;
import util.Card;

public class ScoreTest {

	@Test
	public void testLessonCreated(){
		Score score = new Score();
		Lesson lesson = new Lesson();
		assertEquals(1, lesson.getCards().size());
		addTenCards(lesson);
		assertEquals(11, lesson.getCards().size());
		assertEquals(0, score.getScore());
		score.lessonCreated(lesson);
		assertEquals(10+11*5, score.getScore());
		
		score = new Score();
		addTenCards(lesson);
		assertEquals(21, lesson.getCards().size());
		assertEquals(0, score.getScore());
		score.lessonCreated(lesson);
		assertEquals(10+21*5, score.getScore());
		
		score.lessonCreated(lesson);
		assertEquals(2*(10+21*5), score.getScore());
	}
	
	private void addTenCards(Lesson lesson){
		int currentNumberOfCards = lesson.getCards().size();
		for(int i=currentNumberOfCards; i<(currentNumberOfCards+10); i++){
			lesson.addCard(new Card());
		}
		assertEquals(currentNumberOfCards+10, lesson.getCards().size());
	}
}
