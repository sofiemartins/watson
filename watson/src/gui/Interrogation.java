package gui;

import practice.ShowCardFrame;
import util.Lesson;

public class Interrogation{
	
	public static final long serialVersionUID = 8857453542858887463L;
	//TODO: implement some better training methods, not just asking everything
	
	public Interrogation(Lesson lesson){
		new ShowCardFrame(lesson);
		lesson.incrementNumberOfTries();
	}
}
