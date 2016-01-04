package practice;

import java.awt.event.ActionEvent;

public class AnswerEvent extends ActionEvent{
	
	public static final long serialVersionUID = 8557463887659786721L;
	
	public boolean answerBoolean;
	
	public AnswerEvent(Object source, int id, String command, boolean answer){
		super(source, id, command);
		answerBoolean = answer;
	}
	
	public boolean getAnswer(){
		return answerBoolean;
	}
}
