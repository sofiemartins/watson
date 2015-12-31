package io;

//junit
import static org.junit.Assert.*;
import org.junit.Test;
//java lib
import java.util.ArrayList;
//local
import util.Card;
import util.Lesson;

public class TestFileManager {

	@Test
	public void readWriteEmptyLessons(){
		ArrayList<Lesson> lessons = new ArrayList<Lesson>();
		lessons.add(new Lesson());
		lessons.add(new Lesson());
		lessons.add(new Lesson());
		assertEquals(lessons.size(), 3);
		FileManager.save(lessons);
		ArrayList<Lesson> loadedLessons = FileManager.getLessons();
		assertEquals(loadedLessons.size(), lessons.size());
	}
}
