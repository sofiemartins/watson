package util;

import java.io.Serializable;
import java.util.LinkedList;

public class Stats implements Serializable{
	
	public static final long serialVersionUID = 8857747768575112321L;
	
	private LinkedList<StatsSet> statsList = new LinkedList<StatsSet>();
	
	public Stats(){}
	
	public void addStats(StatsSet stats){
		statsList.add(stats);
	}
	
	public StatsSet getCurrentStatsSet(){
		return statsList.getLast();
	} 
	
	public LinkedList<StatsSet> getStatsList(){//TODO: better encapsulation
		return statsList;
	}
	
	public int getTotalNumberOfWrongAnswers(){
		int total = 0;
		for(StatsSet set : statsList){
			total += set.getNumberOfWrongAnswers();
		}
		return total;
	}
	
	public int getTotalNumberOfRightAnswers(){
		int total = 0;
		for(StatsSet set : statsList){
			total += set.getNumberOfRightAnswers();
		}
		return total;
	}
	
	public int getTotalNumberOfAnswers(){ //TODO: use something bigger than int
		return getTotalNumberOfWrongAnswers() + getTotalNumberOfRightAnswers();
	}
	
	public void startPractising(){
		statsList.add(new StatsSet());
	}
}
