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
	private static String filename = "lessons.watson"; 
	
	public static ArrayList<Lesson> getLessons() throws Exception{
		ObjectInputStream stream = new ObjectInputStream(new FileInputStream("lessons.watson"));
		ArrayList<Lesson> lessons = (ArrayList<Lesson>) stream.readObject(); // TODO:Fix this so the warning disappears
		stream.close();
		return lessons;
	}
	
	public static void save(ArrayList<Lesson> lessons) throws IOException{
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
