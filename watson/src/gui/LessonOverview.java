/**
 *  This shows up after starting the program and is some kind of "main menu".
 *  What users can do here:
 *  <ol>
 *    <li> add lessons
 *    <li> remove lessons
 *    <li> edit lessons
 *    <li> start practicing a certain lesson
 *  </ol>
 */

package gui;

// java lib
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.JOptionPane;
import javax.swing.plaf.synth.SynthLookAndFeel;
import javax.swing.BorderFactory;

import java.awt.event.ActionListener;
import java.io.IOException;
import java.awt.event.ActionEvent;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import static java.awt.BorderLayout.*;
import java.awt.GridLayout;
import java.awt.Window;
import java.awt.event.ComponentListener;
import java.awt.event.ComponentEvent;

//local
import static util.Main.*;
import static util.Colors.*;
import util.Lesson;
import io.FileManager;
import stats.TimeOverview;
import stats.PiChartAnswerOverview;
import stats.AbsoluteAnswerOverview;
import panel.MainControlPanel;
import panel.MainControlEvent;
import static panel.MainControlEventType.*;

public class LessonOverview extends JFrame implements ComponentListener{
	
	//TODO: Make it possible to maximize the frame!
	
	public static final long serialVersionUID = 5543266543547765465L;
	
	private OverviewList overviewList;
	private JPanel overviewPanel;
	private JPanel statsPanel = new JPanel();
	private JPanel overviewListSubcontainer;
	
	public LessonOverview(){
		loadLessons();
		setupFrameLayout();
		initLookAndFeel();
		addComponentListener(this);
	}
	
	private void initLookAndFeel(){
		SynthLookAndFeel lookAndFeel = new SynthLookAndFeel();
		try{
			lookAndFeel.load(ClassLoader.getSystemClassLoader().getResourceAsStream("style.xml"), LessonOverview.class);
			UIManager.setLookAndFeel(lookAndFeel);
			updateDesign();
		}catch(Exception e){
			e.printStackTrace();//TODO: better exception handling
		}
	}
	
	private void updateDesign(){
		for(Window window : JFrame.getWindows()){
			SwingUtilities.updateComponentTreeUI(window);
		}
	}
	
	protected void loadLessons(){
		try{
			Lesson.allLessons = FileManager.getLessons();
		}catch(Exception e){
			e.printStackTrace();
			JOptionPane.showMessageDialog(this, "A severe error occurred. To fix this, please reinstall the program.", 
					"Error", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public void dispose(){
		super.dispose();
		try{
			FileManager.save(Lesson.allLessons);
		}catch(Exception e){
			JOptionPane.showMessageDialog(null, "An error occurred while saving.");
			System.exit(1);
		}
		System.exit(0);
	}
	
	private void setupFrameLayout(){
		setTitle("Lessons - " + applicationTitle);
		setSize(500, 500);
		setMinimumSize(new Dimension(300, 400));
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setLayout(new BorderLayout());
		overviewPanel = getOverview();
		add(overviewPanel, CENTER);
		add(getButtonPanel(), EAST);
		setUpStatsPanel();
		setVisible(true);
	}
	
	public MainControlPanel getButtonPanel(){
		MainControlPanel panel = new MainControlPanel();
		panel.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				MainControlEvent ev = (MainControlEvent)e;
				if(ev.getType()==NEW){
					newLesson();
				}else if(ev.getType()==DELETE){
					delete();
				}else if(ev.getType()==EDIT){
					edit();
				}else if(ev.getType()==START){
					start();
				}
			}
		});
		return panel;
	}
	
	private void newLesson(){
		if(createNewLesson()){
			LessonOverview.super.dispose();
		}
	}

	private void start(){
		if(!overviewList.isSelectionEmpty()){
			startInterrogation();
		}
	}
	
	private void delete(){
		removeSelectedLesson();
	}
	
	private void edit(){
		Lesson lesson = overviewList.getSelectedValue();
		edit(lesson);
		LessonOverview.super.dispose();
	}
	
	private void setUpStatsPanel(){
		statsPanel = new JPanel();
		statsPanel.setLayout(new GridLayout(1, 2));
	}
	
	protected static boolean createNewLesson(){
		String validTitle = getValidTitle();
		if(!validTitle.equals("-1")){//TODO: find a better way for dealing with the cancel button!
			new EditLessonDialog(new Lesson(validTitle));
			return true;
		}
		return false;
	}
	
	//TODO: clean up
	private static String getValidTitle(){
		String lessonTitle = JOptionPane.showInputDialog("Please choose a title: ");
		if(lessonTitle==null){
			return "-1";
		}
		while(!titleValid(lessonTitle)){
			lessonTitle = reaskFor(lessonTitle);
			if(lessonTitle==null){
				return "-1";
			}
		}
		return lessonTitle;
	}
	
	private static String reaskFor(String string){//TODO: no parent is not so great.
		JOptionPane.showMessageDialog(null, "The title has to be unique and not blank!");
		string = null;
		return JOptionPane.showInputDialog("Please choose a title: ");
	}
	
	protected static boolean titleValid(String title){
		for(Lesson lesson : Lesson.allLessons){
			if(lesson.toString().equals(title)){
				return false;
			}
		}
		if(title.equals("")){
			return false;
		}
		return true;
	}
	
	private void removeSelectedLesson(){
		Lesson lesson = overviewList.getSelectedValue();
		if(lesson!=null){
			Lesson.allLessons.remove(lesson);
			try{
				FileManager.save(Lesson.allLessons);
			}catch(IOException e){
				e.printStackTrace();//Exception handling
			}
			overviewList.update();
		}
	}
	
	protected static void edit(Lesson lesson){
		new EditLessonDialog(lesson);
	}
	
	private JPanel getOverview(){
		JPanel container = new JPanel();
		container.setLayout(new BorderLayout());
		overviewListSubcontainer = getOverviewListSubcontainer();
		container.add(overviewListSubcontainer);
		return container;
	}
	
	private JPanel getOverviewListSubcontainer(){
		JPanel subcontainer = new JPanel();
		subcontainer.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1, true));
		subcontainer.setLayout(new BorderLayout());
		subcontainer.add(getOverviewList(), CENTER);
		return subcontainer;
	}
	
	
	private JScrollPane getOverviewList(){
		overviewList = new OverviewList();
		overviewList.setCellRenderer(new CellRenderer());
		overviewList.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				startInterrogation();
			}
		});
		overviewList.addListSelectionListener(new ListSelectionListener(){
			@Override
			public void valueChanged(ListSelectionEvent e){
				reloadStatsPanel();
				if(statsPanel.getParent()==null){
					overviewListSubcontainer.add(statsPanel, SOUTH);
				}
				updateDesign();
				LessonOverview.this.validate();
				LessonOverview.this.repaint();
			}
		});
		return new JScrollPane(overviewList);
	}
	
	private void reloadStatsPanel(){
		if(overviewList.isSelectionEmpty()){
			statsPanel.setVisible(false);
		}else{
			statsPanel.removeAll();
			if(getWidth()>600){
				statsPanel.add(getTimeOverview());
			}
			statsPanel.add(getAnswerOverview());
		}
	}
	
	private JPanel getTimeOverview(){
		JPanel container = new JPanel();
		container.setLayout(new GridLayout(1,1));
		JPanel subcontainer = new JPanel();
		subcontainer.setLayout(new GridLayout(1,1));
		subcontainer.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(lightGray, 1, true), "Wrong Answers Over Time"));
		subcontainer.add(new TimeOverview(overviewList.getSelectedValue()));
		container.add(subcontainer);
		return container;
	}
	
	private JPanel getAnswerOverview(){
		JPanel container = new JPanel();
		container.setLayout(new GridLayout(1,1));
		JPanel subcontainer = new JPanel();
		subcontainer.setLayout(new GridLayout(1,1));
		subcontainer.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(lightGray, 1, true), "Answer Composition"));//TODO; Better captions
		subcontainer.add(absolutesPanel());
		container.add(subcontainer);
		return container;
	}
	
	private JPanel absolutesPanel(){
		JPanel container = new JPanel();
		container.setLayout(new GridLayout(1,2));
		if(getWidth()>400){
			container.add(new PiChartAnswerOverview(overviewList.getSelectedValue()));
		}
		container.add(new AbsoluteAnswerOverview(overviewList.getSelectedValue()));
		return container;
	}
	
	private void startInterrogation(){
		Lesson selectedLesson = overviewList.getSelectedValue();
		selectedLesson.resetCurrentCard();
		new Interrogation(selectedLesson);
		LessonOverview.super.dispose();
	}

	@Override
	public void componentHidden(ComponentEvent e) {}

	@Override
	public void componentMoved(ComponentEvent e) {}

	@Override
	public void componentResized(ComponentEvent e) {
		if(statsPanel.isValid()){
			reloadStatsPanel();
			repaint();
			revalidate();
		}
	}

	@Override
	public void componentShown(ComponentEvent e) {}
	
}