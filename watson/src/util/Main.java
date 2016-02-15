package util;

import javax.swing.SwingUtilities;

import gui.LessonOverview;

public class Main {
	
	public static void main(String[] args){
		SwingUtilities.invokeLater(new Runnable(){
			@Override
			public void run(){
				new LessonOverview();
			}
		});
	}
}
