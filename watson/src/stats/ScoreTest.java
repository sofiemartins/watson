package stats;

import static org.junit.Assert.*;

import org.junit.Test;

import java.util.Date;

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
	
	@Test
	public void testRightWrongRatio(){
		Score score = new Score();
		Lesson lesson = new Lesson();
		addTenCards(lesson);
		score.lessonCreated(lesson);
		assertEquals(10+11*5, score.getScore());
		lesson.startPractising();
		lesson.answeredWrong();
		lesson.answeredWrong();
		lesson.answeredWrong();
		lesson.answeredRight();
		lesson.answeredRight();
		lesson.answeredRight();
		assertEquals(0.5, lesson.getRightAnswerRatio(), 0.1);
		score.lessonPractised(lesson);
		assertEquals(10+11*5+100+1000*0.5, score.getScore(), 0.1);
	}
	
	@Test
	public void testRightWrongRatio_2(){
		Score score = new Score();
		Lesson lesson = new Lesson();
		addTenCards(lesson);
		score.lessonCreated(lesson);
		assertEquals(10+11*5, score.getScore());
		lesson.startPractising();
		for(int i=0; i<12; i++){
			lesson.answeredWrong();
		}
		assertEquals(0, lesson.getRightAnswerRatio(), 0.1);
		score.lessonPractised(lesson);
		assertEquals(10+11*5+100, score.getScore());
	}
	
	@Test
	public void testRightWrongRatio_3(){
		Score score = new Score();
		Lesson lesson = new Lesson();
		addTenCards(lesson);
		score.lessonCreated(lesson);
		assertEquals(10+11*5, score.getScore());
		lesson.startPractising();
		lesson.answeredRight();
		lesson.answeredRight();
		lesson.answeredRight();
		lesson.answeredRight();
		lesson.answeredRight();
		lesson.answeredRight();//6 times
		lesson.answeredWrong();
		lesson.answeredWrong();
		lesson.answeredWrong();
		assertEquals((2.0/3.0), lesson.getRightAnswerRatio(), 0.1);
		score.lessonPractised(lesson);
		assertEquals((int)(10+11*5+100+1000*(2.0/3.0)), score.getScore());
	}
	
	@Test
	public void testNoExtraForNoBreak(){
		Score score = new Score();
		Lesson lesson = new Lesson();
		addTenCards(lesson);
		score.lessonCreated(lesson);
		oneQuarterRightThreeQuartersWrong(score, lesson);
		assertEquals(10+11*5+100+250, score.getScore());
		oneQuarterRightThreeQuartersWrong(score, lesson);
		assertEquals(10+11*5+2*(100+250), score.getScore());
	}
	
	@Test
	public void testExtraForSixHourBreak(){
		Score score = new Score();
		Lesson lesson = new Lesson();
		addTenCards(lesson);
		score.lessonCreated(lesson);
		oneQuarterRightThreeQuartersWrong(score, lesson);
		Date currentDate = new Date();
		long additionalScore = score.getAdditionalScore(lesson);
		score.getLessonPractisedOnExtra(new Date(currentDate.getTime()+Score.SIX_HOURS_IN_MS+1000), lesson, additionalScore);
		assertEquals(10+11*5+100*(100+250), score.getScore());
	}
	
	private void oneQuarterRightThreeQuartersWrong(Score score, Lesson lesson){
		lesson.startPractising();
		lesson.answeredRight();
		lesson.answeredWrong();
		lesson.answeredWrong();
		lesson.answeredWrong();
		assertEquals(0.25, lesson.getRightAnswerRatio(), 0.001);
		score.lessonPractised(lesson);
	}
	
	private void addTenCards(Lesson lesson){
		int currentNumberOfCards = lesson.getCards().size();
		for(int i=currentNumberOfCards; i<(currentNumberOfCards+10); i++){
			lesson.addCard(new Card());
		}
		assertEquals(currentNumberOfCards+10, lesson.getCards().size());
	}
}
