package editor;

import static editor.PenMode.NONE;

import java.awt.Color;

public class Pen {
	// TODO: Size slider
	private PenSize size = PenSize.MEDIUM; //px
	private Color color = Color.black;
	private PenMode mode = NONE;
	private PenType type = PenType.PEN;
	
	private static final Color markerColor = new Color(255,240,0,100);
	
	public static final Pen MARKER = new Pen(PenSize.MEDIUM, Color.yellow, NONE, PenType.MARKER);
	public static final Pen PEN = new Pen(PenSize.MEDIUM, Color.black, NONE, PenType.PEN);
	public static final Pen ERASER = new Pen(PenSize.MEDIUM, Color.black, PenMode.ERASER, PenType.ERASER);
			
	public Pen(){}
	
	public Pen(PenSize penSize, Color penColor, PenMode penMode, PenType penType){
		color = penColor;
		mode = penMode;
		size = penSize;
		type = penType;
	}
	
	public int getSizeInPx(){
		return getSizeInPx(size, type);
	}
	
	public static int getSizeInPx(PenSize penSize, PenType type){
		if(penSize == PenSize.THICK){
			return thickInPx(type);
		}else if(penSize == PenSize.MEDIUM){
			return mediumInPx(type);
		}else{
			return fineInPx(type);
		}
	}
	
	private static int thickInPx(PenType type){
		if(type==PenType.MARKER){
			return 50;
		}else if(type==PenType.ERASER){
			return 20;
		}else{
			return 10;
		}
	}
	
	private static int mediumInPx(PenType type){
		if(type==PenType.MARKER){
			return 25;
		}else if(type==PenType.ERASER){
			return 10;
		}else{
			return 5;
		}
	}
	
	private static int fineInPx(PenType type){
		if(type==PenType.MARKER){
			return 12;
		}else if(type==PenType.ERASER){
			return 5;
		}else{
			return 2;
		}
	}
	
	public PenSize getSize(){ 
		return size; 
	}
	
	public void setSize(PenSize newSize){ 
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
	
	public PenType getType(){
		return type;
	}
	
	public void setType(PenType newType){
		type = newType;
	}
}
