package util;

import java.io.Serializable;
import java.util.LinkedList;

public class Stats implements Serializable{
	
	public static final long serialVersionUID = 8857747768575112321L;
	
	private LinkedList<StatsSet> statsList = new LinkedList<StatsSet>();
	
	public void addStats(StatsSet stats){
		statsList.add(stats);
	}
	
	public LinkedList<StatsSet> getStats(){
		return statsList;
	}
}
