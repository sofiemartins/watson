/**
 * There's exactly one file where everything is saved.
 * This class deals with reading from the file and writing to the file.
 */

package io;

import util.Lesson;
import java.util.ArrayList;

public class FileManager {
	
	public static ArrayList<Lesson> getLessons(){
		return new ArrayList<Lesson>();
	}
	
	public static void save(ArrayList<Lesson> lessons){}

}
