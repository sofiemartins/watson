package util;

import java.io.Serializable;
import java.awt.image.BufferedImage;

public class Card implements Serializable{
	
	public static final long serialVersionUID = 3658394727116385564L; 
	private BufferedImage front, back;
	
	public Card(){}
	
	public Card(BufferedImage side1, BufferedImage side2){
		front = side1;
		back = side2;
	}
	
	public BufferedImage getFirstSide(){
		return front;
	}
	
	public void setFirstSide(BufferedImage img){
		front = img;
	}
	
	public BufferedImage getSecondSide(){
		return back;
	}
	
	public void setSecondSide(BufferedImage img){
		back = img;
	}

}
