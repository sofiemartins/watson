package panel;

import java.awt.event.ActionEvent;

public class EditEvent extends ActionEvent{
	
	public static final long serialVersionUID = 5948825438754943654L;
	
	private EditType type;
	
	public EditEvent(Object source, EditType editType){
		super(source, ActionEvent.ACTION_PERFORMED, "Edit");
		type = editType;
	}
	
	public EditType getType(){
		return type;
	}
}
