package toolbar;

import java.awt.event.ActionListener;
import javax.swing.JPanel;

public abstract class ToolbarPanel extends JPanel{
	
	public static final long serialVersionUID = 4776587123543654387L;
	
	private ActionListener actionListener;
	
	public void addActionListener(ActionListener al){
		actionListener = al;
	}
	
	protected ActionListener getActionListener(){
		return actionListener;
	}
	
	public void update(){
		checkProperties();
	}
	
	protected abstract void checkProperties();
	
}
