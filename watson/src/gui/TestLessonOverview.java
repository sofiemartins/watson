package gui;

import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.BeforeClass;

import io.FileManager;

import java.util.ArrayList;

import util.Lesson;

public class TestLessonOverview {
	
	@BeforeClass
	public void changeTestFilename(){
		FileManager.dryRun();
	}

	@Test
	public void testLoadLessons(){
		new LessonOverview();
		createExampleSaveStatus();
		emptyLoadedLessons();
		
	}
	
	private ArrayList<Lesson> getExampleLessonList(){
		ArrayList<Lesson> lessons = new ArrayList<Lesson>();
		for(int i = 0; i < 50; i++){
			lessons.add(new Lesson());
		}
		return lessons;
	}
	
	private void createExampleSaveStatus(){
	}
	
	private void emptyLoadedLessons(){}
}
