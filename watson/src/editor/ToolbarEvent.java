package editor;

import java.awt.event.ActionEvent;
/**
 * 
 * @author Sofie A. Martins
 *
 *TODO: Maybe make this cleaner: There are a lot of things that aren't needed.
 */

public class ToolbarEvent extends ActionEvent{
	
	public static final long serialVersionUID = 8477366456524556789L;
	
	private Pen pen;

	public ToolbarEvent(Object source, int id, String command, Pen newPen) {
		super(source, id, command);
		pen = newPen;
	}
	
	public Pen getPen(){
		return pen;
	}
}
