package stats;

import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Font;

import util.Lesson;

public class AbsoluteAnswerOverview extends JPanel{
	
	public static final long serialVersionUID = 2665437768845653425L;
	
	private Lesson lesson;
	
	public AbsoluteAnswerOverview(Lesson lessonDisplayed){
		lesson = lessonDisplayed;
	}
	
	@Override
	public void paintComponent(Graphics graphics){
		super.paintComponent(graphics);
		Graphics2D g = (Graphics2D)graphics;
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.setFont(new Font("Times New Roman", Font.BOLD, 15));
		g.drawString("Absolute values:", 10, 15);
		g.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		g.drawString("Times practised: " + lesson.getStats().getStatsList().size(), 10, 35);
		g.drawString("Number of Right Answers: " + lesson.getStats().getTotalNumberOfRightAnswers(), 10, 55);
		g.drawString("Number of Wrong Answers: " + lesson.getStats().getTotalNumberOfWrongAnswers(), 10, 75);
		g.drawString("Score: ", 10, 95);
	}

}
