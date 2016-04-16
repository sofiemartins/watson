package editor;

import javax.swing.JToggleButton;

public class ToolbarButton extends JToggleButton{
	
	public static final long serialVersionUID = 665388765887765567L;
	
	public ToolbarButton(){
		super();
		setName("editorToolbarButton");
	}
	
	public ToolbarButton(String label){
		super(label);
		setName("editorToolbarButton");
	}

}
