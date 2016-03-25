package panel;

import java.awt.event.ActionEvent;

public class MainControlEvent extends ActionEvent{
	
	public static final long serialVersionUID = 6549868763651653762L;
	
	private MainControlEventType type;
	
	public MainControlEvent(Object source, MainControlEventType eventType){
		super(source, ActionEvent.ACTION_PERFORMED, "main control");
		type = eventType;
	}
	
	public MainControlEventType getType(){
		return type;
	}
}
