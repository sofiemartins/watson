/**
 * There's exactly one file where everything is saved.
 * This class deals with reading from the file and writing to the file.
 */

package io;

//java lib
import java.util.ArrayList;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.io.IOException;
//local
import util.Lesson;


public class FileManager {
	
	// default filename that can be saved for testing the program without destroying data.
	protected static String filename = "lessons.watson"; 
	
	public static ArrayList<Lesson> getLessons() throws IOException{
		//ObjectInputStream stream = new ObjectInputStream(new FileInputStream("lessons.watson"));
		//stream.close();
		return null;
	}
	
	public static void save(ArrayList<Lesson> lessons) throws IOException{
		ObjectOutputStream stream = new ObjectOutputStream(new FileOutputStream(filename));
		stream.writeObject(lessons);
		stream.close();
	}
}
