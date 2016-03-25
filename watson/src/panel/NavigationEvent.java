package panel;

import java.awt.event.ActionEvent;

public class NavigationEvent extends ActionEvent{
	
	public static final long serialVersionUID = 3776984567436154328L;
	private NavigationEventType navigationEventType;
	
	public NavigationEvent(Object source, NavigationEventType type){
		super(source, ActionEvent.ACTION_PERFORMED, "Navigation Event");
		navigationEventType = type;
	}
	
	public NavigationEventType getType(){
		return navigationEventType;
	}

}
