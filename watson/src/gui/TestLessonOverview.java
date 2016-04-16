package gui;

import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.BeforeClass;
import org.junit.After;

import java.util.ArrayList;
import java.io.IOException;
import java.io.File;

import io.FileManager;
import util.Lesson;

public class TestLessonOverview {
	
	@BeforeClass
	public static void changeTestFilename(){
		FileManager.dryRun();
	}
	
	@After
	public void removeTestFile(){
		File file = new File("test.watson");
		file.delete();
	}

	@Test
	public void testLoadLessons(){
		LessonOverview lessonOverview = new LessonOverview();
		createExampleSaveStatus();
		unloadLessons();
		assertTrue(Lesson.allLessons.isEmpty());
		lessonOverview.loadLessons();
		assertFalse(Lesson.allLessons.isEmpty());
	}
	
	private ArrayList<Lesson> getExampleLessonList(){
		ArrayList<Lesson> lessons = new ArrayList<Lesson>();
		for(int i = 0; i < 50; i++){
			lessons.add(new Lesson());
		}
		return lessons;
	}
	
	private void createExampleSaveStatus(){
		try{
			FileManager.save(getExampleLessonList());
		}catch(IOException e){
			fail("Unexpected Exception");
		}
	}
	
	private void unloadLessons(){
		Lesson.allLessons = new ArrayList<Lesson>();
		assertTrue(Lesson.allLessons.isEmpty());
	}
	
}
