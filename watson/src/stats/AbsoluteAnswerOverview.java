package stats;

import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ComponentListener;
import java.awt.event.ComponentEvent;

import util.Lesson;

public class AbsoluteAnswerOverview extends JPanel implements ComponentListener{
	
	public static final long serialVersionUID = 2665437768845653425L;
	
	private Lesson lesson;
	
	public AbsoluteAnswerOverview(Lesson lessonDisplayed){
		lesson = lessonDisplayed;
		addComponentListener(this);
		addLabels();
	}
	
	private void addLabels(){
		setLayout(new GridLayout(5, 1));
		add(getCaptionLabel());
		add(getTimesPractisedLabel());
		add(getRightAnswersLabel());
		add(getWrongAnswersLabel());
		add(getScoreLabel());
	}
	
	private JLabel getCaptionLabel(){
		String label;
		if(getWidth()<100){
			label = "<html>Absolute<br>Values</html>";
		}else{
			label = "Absolute Value";
		}
		JLabel caption = new JLabel(label);
		caption.setFont(new Font("Times New Roman", Font.PLAIN, 10));
		return caption;
	}
	
	private JLabel getTimesPractisedLabel(){
		String label;
		if(lesson!=null){
			if(getWidth()<100){
				label = "<html>Times<br>Practised<br>" + lesson.getStats().getStatsList().size() + "</html>";
			}else{
				label = "Times Practised " + lesson.getStats().getStatsList().size();
			}
			
		}else{ label = " "; }
		JLabel timesPractised = new JLabel(label);
		timesPractised.setFont(new Font("Times New Roman", Font.PLAIN, 10));
		return timesPractised;
	}
	
	private JLabel getRightAnswersLabel(){
		String label;
		if(getWidth()<100){
			label = "<html>Right<br>Answers:<br>" + lesson.getStats().getTotalNumberOfRightAnswers() + "</html>";
		}else{
			label = "Right Answers: " + lesson.getStats().getTotalNumberOfRightAnswers();
		}
		JLabel rightAnswers = new JLabel(label);
		rightAnswers.setFont(new Font("Times New Roman", Font.PLAIN, 10));
		return rightAnswers;
	}
	
	private JLabel getWrongAnswersLabel(){
		String label;
		if(getWidth()<100){
			label = "<html>Wrong<br>Answers:<br>" + lesson.getStats().getTotalNumberOfWrongAnswers() + "</html>";
		}else{
			label = "Wrong Answers: " + lesson.getStats().getTotalNumberOfWrongAnswers();
		}
		JLabel wrongAnswers = new JLabel(label);
		wrongAnswers.setFont(new Font("Times New Roman", Font.PLAIN, 10));
		return wrongAnswers;
	}
	
	private JLabel getScoreLabel(){
		String label;
		if(getWidth()<100){
			label = "<html>Score:</html>";
		}else{
			label = "Score: ";
		}
		JLabel score = new JLabel(label);
		score.setFont(new Font("Times New Roman", Font.PLAIN, 10));
		return score;
	}

	@Override
	public void componentHidden(ComponentEvent e) {}

	@Override
	public void componentMoved(ComponentEvent e) {}

	@Override
	public void componentResized(ComponentEvent e) {
		removeAll();
		addLabels();
		revalidate();
		repaint();
	}

	@Override
	public void componentShown(ComponentEvent e) {}
	
	
}
