package editor;

import static editor.PenMode.NONE;
import static editor.PenType.PEN;

import java.awt.Color;

public class Pen {
	
	private int penSize = 2; //px
	private Color penColor = Color.black;
	private PenType penType = PEN;
	private PenMode penMode = NONE;
	
	private static final Color markerColor = new Color(255,240,0,100);
	private static final int markerSize = 6; //px
}
