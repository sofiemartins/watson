package editor;

import static editor.PenMode.NONE;

import java.awt.Color;

public class Pen {
	
	private int size = 2; //px
	private Color color = Color.black;
	private PenMode mode = NONE;
	
	private static final Color markerColor = new Color(255,240,0,100);
	private static final int markerSize = 6; //px
	
	public static final Pen MARKER = new Pen(6, markerColor, NONE);
	public static final Pen PEN = new Pen(2, Color.black, NONE);
	public static final Pen ERASER = new Pen(4, new Color(0,0,0,0), NONE);
			
	public Pen(){}
	
	public Pen(int penSize, Color penColor, PenMode penMode){
		size = penSize;
		color = penColor;
		mode = penMode;
	}
	
	public int getSize(){ 
		return size; 
	}
	
	public void setSize(int newSize){ 
		size = newSize; 
	}
	
	public Color getColor(){ 
		return color; 
	}
	
	public void setColor(Color newColor){ 
		color = newColor; 
	}
	
	public PenMode getMode(){ 
		return mode; 
	}
	
	public void setMode(PenMode newMode){ 
		mode = newMode; 
	}
}
