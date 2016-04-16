package stats;

import java.io.Serializable;
import java.util.Date;

import util.Lesson;

public class Score implements Serializable{
	
	public static final long serialVersionUID = 945653465476545444L;
	protected static final long SIX_HOURS_IN_MS = 21600000L;
	protected static final long THIRTY_SIX_HOURS_IN_MS = 129600000L;
	protected static final long A_WEEK_IN_MS = 604800000L;
	protected static final long A_MONTH_IN_MS = 2292000000L;
	
	private long score = 0; //TODO BigInteger ...
	private Date lastPractised = new Date();
	
	public Score(){}
	
	public void lessonPractised(Lesson lesson){
		long additionalScore = getAdditionalScore(lesson);
		additionalScore = getLessonPractisedOnExtra(new Date(), lesson, additionalScore);//TODO: this is bad design
		score +=additionalScore;
		lastPractised = new Date();
	}
	
	protected long getAdditionalScore(Lesson lesson){
		long additionalScore = 100;
		additionalScore += 1000*lesson.getRightAnswerRatio();
		return additionalScore;
	}
	
	protected long getLessonPractisedOnExtra(Date currentDate, Lesson lesson, long additionalScore){
		if(lesson.getStats().getStatsList().size()<(lesson.getCards().size()*20)){
			return procedureForNewLessons(currentDate, additionalScore);
		}else{
			return procedureForOldLessons(currentDate, additionalScore);
		}
	}
	
	private long procedureForNewLessons(Date currentDate, long additionalScore){
		long timeDifference = currentDate.getTime()-lastPractised.getTime();
		if((timeDifference > SIX_HOURS_IN_MS) && (timeDifference < THIRTY_SIX_HOURS_IN_MS)){
			return additionalScore * 100;
		}else if((timeDifference > THIRTY_SIX_HOURS_IN_MS) && (timeDifference < A_WEEK_IN_MS)){
			return additionalScore * 50;
		}else if((timeDifference > A_WEEK_IN_MS) && (timeDifference < A_MONTH_IN_MS)){
			return additionalScore * 10;
		}
		return additionalScore;
	}
	
	private long procedureForOldLessons(Date currentDate, long additionalScore){
		long timeDifference = currentDate.getTime()-lastPractised.getTime();
		if(timeDifference > A_MONTH_IN_MS){
			return additionalScore * 100;
		}
		return additionalScore;
	}
	
	public void lessonCreated(Lesson lesson){
		score+=10;
		score+=lesson.getCards().size()*5;
	}
	
	public long getScore(){
		return score;
	}
}
