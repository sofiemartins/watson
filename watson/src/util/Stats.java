package util;

import java.io.Serializable;
import java.util.LinkedList;

public class Stats implements Serializable{
	
	public static final long serialVersionUID = 8857747768575112321L;
	
	private LinkedList<StatsSet> statsList = new LinkedList<StatsSet>();
	
	public void addStats(StatsSet stats){
		statsList.add(stats);
	}
	
	public StatsSet getCurrentStatsSet(){
		return statsList.getLast();
	} 
	
	public LinkedList<StatsSet> getStatsList(){//TODO: better encapsulation
		return statsList;
	}
}
