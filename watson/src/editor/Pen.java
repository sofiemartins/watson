package editor;

import static editor.PenMode.NONE;

import java.awt.Color;

public class Pen {
	// TODO: Size slider
	private int size = 2; //px
	private Color color = Color.black;
	private PenMode mode = NONE;
	
	private static final Color markerColor = new Color(255,240,0,100);
	private static final int markerSize = 25; //px
	public static final int BIG = 4;
	public static final int MEDIUM = 3;
	public static final int SMALL = 2;
	
	public static final Pen MARKER = new Pen(markerSize, markerColor, NONE);
	public static final Pen PEN = new Pen(MEDIUM, Color.black, NONE);
	public static final Pen ERASER = new Pen(4, Color.white, NONE);//TODO: Make a real eraser, not just a white pen
			
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
