/**
 * There's exactly one file where everything is saved.
 * This class deals with reading from the file and writing to the file.
 */

package io;

//java lib
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

import java.awt.image.BufferedImage;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.io.IOException;
import java.io.File;
//local
import util.Lesson;
import util.Card;


public class FileManager {
	
	// default filename that can be saved for testing the program without destroying data.
	protected static String filename = "lessons.watson"; 
	
	public static ArrayList<Lesson> getLessons() throws Exception{
		makeSureFileExists();
		ObjectInputStream stream = new ObjectInputStream(new FileInputStream(filename));
		ArrayList<Lesson> lessons = (ArrayList<Lesson>) stream.readObject(); // TODO:Fix this so the warning disappears
		stream.close();
		for(Lesson lesson : lessons){
			for(Card card : lesson.getCards()){
				int cardIndex = lesson.getCards().indexOf(card);
				BufferedImage sideA = ImageIO.read(new File("img_data/"+lesson.toString()+"-"+cardIndex+"-A"));
				BufferedImage sideB = ImageIO.read(new File("img_data/"+lesson.toString()+"-"+cardIndex+"-B"));
				lesson.getCards().set(cardIndex, new Card(sideA, sideB));
			}
		}
		return lessons;
	}
	
	protected static void makeSureFileExists() throws IOException{
		File file = new File(filename);
		if(!file.exists()){
			file.createNewFile();
		}
	}
	
	public static void save(ArrayList<Lesson> lessons) throws IOException{
		//save all images to img_data
		makeSureFileExists();
		File file = new File(filename);
		file.delete();
		for(Lesson lesson : lessons){
			for(Card card : lesson.getCards()){
				int cardIndex = lesson.getCards().indexOf(card);
				ImageIO.write(card.getFirstSide(), "png", new File("img_data/"+lesson.toString()+"-"+cardIndex+"-A"));
				ImageIO.write(card.getSecondSide(), "png", new File("img_data/"+lesson.toString()+"-"+cardIndex+"-B"));
			}
		}
		ObjectOutputStream stream = new ObjectOutputStream(new FileOutputStream(filename));
		stream.writeObject(lessons);
		stream.close();
	}
	
	/**
	 * Creates the environment for test cases
	 */
	public static void dryRun(){
		filename = "test.watson";
	}
}
