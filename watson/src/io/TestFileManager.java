package io;

//junit
import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.junit.After;
import org.junit.Test;
//java lib
import java.util.ArrayList;
import java.io.IOException;
import java.io.File;
//local
import util.Lesson;

public class TestFileManager {
	
	/*
	 * The saving filename has to changed to not destroy saves on running copies.
	 */
	@BeforeClass
	public static void changeFilename(){
		FileManager.dryRun();
	}
	
	/*
	 * After every single test the test file will be removed because otherwise a writing test could succeed
	 * even though it really wasn't successful just because the test before has been successful. For example.
	 */
	@After
	public void removeFile(){
		File file = new File("test.watson");
		file.delete();
	}
	
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
		}catch(Exception e){
			fail("Exception was thrown while saving.");
		}
		try{
			loadedLessons = FileManager.getLessons();
		}catch(Exception e){
			fail("Exception was thrown while loading.");
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
		File file = new File("test.watson");
		assertTrue(file.exists());
	}
	
	@Test 
	public void testDryRun(){
		FileManager.dryRun();
		assertEquals(FileManager.filename,"test.watson");
	}
	
	@Test
	public void testMakeSureFileExists(){
		File file = new File(FileManager.filename);
		assertEquals("test.watson", FileManager.filename);
		assertFalse(file.exists());
		try{
			FileManager.makeSureFileExists();
		}catch(IOException e){
			fail("Unexpected exception was thrown.");
		}
		assertTrue(file.exists());
	}
}
