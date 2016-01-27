package stats;

import java.io.Serializable;
import java.util.Date;

import util.Lesson;

public class Score implements Serializable{
	
	public static final long serialVersionUID = 945653465476545444L;
	
	private int score = 0; //TODO BigInteger ...
	private Date lastPractised = new Date();
	
	public Score(){}
	
	public void lessonPractised(Date date, int numberOfWrongAnswers){}
	
	public void lessonCreated(Lesson lesson){
		score+=10;
		score+=lesson.getCards().size()*5;
	}
	
	public int getScore(){
		return score;
	}
}
