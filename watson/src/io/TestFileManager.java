package io;

//junit
import static org.junit.Assert.*;
import org.junit.Test;
//java lib
import java.util.ArrayList;
import java.io.IOException;
import java.io.File;
//local
import util.Card;
import util.Lesson;

public class TestFileManager {
	
	public ArrayList<Lesson> getExampleLessons(){
		ArrayList<Lesson> lessons = new ArrayList<Lesson>();
		lessons.add(new Lesson());
		lessons.add(new Lesson());
		lessons.add(new Lesson());
		assertEquals(lessons.size(), 3);
		return lessons;
	}

	@Test
	public void readWriteEmptyLessons(){
		ArrayList<Lesson> lessons = getExampleLessons(); 
		ArrayList<Lesson> loadedLessons = new ArrayList<Lesson>();
		try{
			FileManager.save(lessons);
			loadedLessons = FileManager.getLessons();
		}catch(IOException e){
			fail("Unexpected Exception");
		}
		assertEquals(loadedLessons.size(), lessons.size());
	}
	
	@Test
	public void AfterWriteAFileExists(){
		try{
			FileManager.save(getExampleLessons());
		}catch(IOException e){
			fail("Unexpected Exception");
		}
		File file = new File("lessons.watson");
		assertTrue(file.exists());
	}
}
