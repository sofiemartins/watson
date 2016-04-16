package util;

import java.util.Date;
import java.io.Serializable;

public class StatsSet implements Serializable{
	
	public static final long serialVersionUID = 958867584765634354L;
	private int numberOfWrongAnswers = 0; 
	private int numberOfRightAnswers = 0;
	private Date timestamp;
	
	public StatsSet(){
		timestamp = new Date();
	}
	
	public StatsSet(int wrongAnswers, int rightAnswers, Date date){ 
		numberOfWrongAnswers = wrongAnswers;
		numberOfRightAnswers = rightAnswers;
		timestamp = date;
	}
	
	public void answeredRight(){
		numberOfRightAnswers++;
	}
	
	public void answeredWrong(){
		numberOfWrongAnswers++;
	}
	
	public int getNumberOfRightAnswers(){
		return numberOfRightAnswers;
	}
	
	public int getNumberOfWrongAnswers(){
		return numberOfWrongAnswers;
	}
	
	public Date getTimestamp(){
		return timestamp;
	}
}
