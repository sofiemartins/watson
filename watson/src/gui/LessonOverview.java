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
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.JOptionPane;
import javax.swing.plaf.synth.SynthLookAndFeel;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.awt.event.ActionEvent;
import java.awt.BorderLayout;
import java.awt.Color;

import static java.awt.BorderLayout.*;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Window;

//local
import util.Lesson;
import io.FileManager;
import stats.TimeOverview;
import stats.AnswerOverview;

public class LessonOverview extends JFrame{
	
	//TODO: Make it possible to maximize the frame!
	
	public static final long serialVersionUID = 5543266543547765465L;
	
	private OverviewList overviewList;
	private JPanel overviewPanel;
	private JPanel statsPanel = new JPanel();
	private JPanel overviewListSubcontainer;
	private static final String TOOLBAR_BUTTON = "toolbarButton";
	
	public LessonOverview(){
		loadLessons();
		setupFrameLayout();
		initLookAndFeel();
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
			System.out.println("Lessons were loaded. The number of currently loaded lessons is: " + Lesson.allLessons.size());
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
		setSize(800,500);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setLayout(new BorderLayout());
		overviewPanel = getOverview();
		add(overviewPanel, CENTER);
		add(getButtonPanel(), EAST);
		setUpStatsPanel();
		setVisible(true);
	}
	
	private void setUpStatsPanel(){
		statsPanel = new JPanel();
		statsPanel.setLayout(new GridLayout(1, 2));
	}
	
	private JPanel getButtonPanel(){
		JPanel container = new JPanel();
		container.setLayout(new BorderLayout());
		container.add(getButtonPanelSubPanel(), NORTH);
		container.add(getStartPractisingButton(), SOUTH);
		return container;
	}
	
	private JPanel getButtonPanelSubPanel(){
		JPanel subcontainer = new JPanel();
		subcontainer.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1, true));
		subcontainer.setLayout(new GridLayout(3,1));
		subcontainer.add(getNewButton());
		subcontainer.add(getRemoveButton());
		subcontainer.add(getEditButton());
		return subcontainer;
	}
		
	private JPanel getNewButton(){
		JPanel container = new JPanel();
		container.setLayout(new GridLayout(1,1));
		container.setBorder(new EmptyBorder(8, 8, 4, 8));
		JButton button = new JButton("New");
		button.setName(TOOLBAR_BUTTON);
		button.setVerticalTextPosition(SwingConstants.BOTTOM);
		button.setHorizontalTextPosition(SwingConstants.CENTER);
		setIcon(button, "add.png");
		button.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				createNewLesson();
				LessonOverview.super.dispose();
			}
		});
		container.add(button);
		return container;
	}
	
	private JPanel getStartPractisingButton(){
		JPanel container = new JPanel();
		container.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1, true));
		container.setLayout(new GridLayout(1,1));
		JPanel subcontainer = new JPanel();
		subcontainer.setLayout(new GridLayout(1,1));
		JButton button = new JButton("Start");
		button.setName(TOOLBAR_BUTTON);
		button.setVerticalTextPosition(SwingConstants.BOTTOM);
		button.setHorizontalTextPosition(SwingConstants.CENTER);
		setIcon(button, "add.png");
		button.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				startInterrogation();
			}
		});
		subcontainer.add(button);
		container.add(subcontainer);
		return container;
	}
	
	protected static void createNewLesson(){
		String validTitle = getValidTitle();
		if(!validTitle.equals("-1")){//TODO: find a better way for dealing with the cancel button!
			new EditLessonDialog(new Lesson(validTitle));
		}
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
	
	private JPanel getRemoveButton(){
		JPanel container = new JPanel();
		container.setLayout(new GridLayout(1,1));
		container.setBorder(new EmptyBorder(4, 8, 4, 8));
		JButton button = new JButton("Delete");
		button.setName(TOOLBAR_BUTTON);
		button.setVerticalTextPosition(SwingConstants.BOTTOM);
		button.setHorizontalTextPosition(SwingConstants.CENTER);
		setIcon(button, "remove.png");
		button.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				//TODO: remove
			}
		});
		container.add(button);
		return container;
	}
	
	private JPanel getEditButton(){
		JPanel container = new JPanel();
		container.setLayout(new GridLayout(1,1));
		container.setBorder(new EmptyBorder(4, 8, 8, 8));
		JButton button = new JButton("Edit");
		button.setName(TOOLBAR_BUTTON);
		button.setVerticalTextPosition(SwingConstants.BOTTOM);
		button.setHorizontalTextPosition(SwingConstants.CENTER);
		setIcon(button, "edit.png");
		button.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				Lesson lesson = overviewList.getSelectedValue();
				edit(lesson);
				LessonOverview.super.dispose();
			}
		});
		container.add(button);
		return container;
	}
	
	protected static void edit(Lesson lesson){
		new EditLessonDialog(lesson);
	}
	
	private void setIcon(JButton button, String filepath){
		BufferedImage icon = null;
		try{
			icon = ImageIO.read(new File("res/" + filepath));
			button.setIcon(new ImageIcon(icon.getScaledInstance(25, 25, Image.SCALE_SMOOTH)));
		}catch(IOException e){
			e.printStackTrace(); //TODO: Exception handling
		}
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
		statsPanel.removeAll();
		statsPanel.add(getTimeOverview());
		statsPanel.add(getAnswerOverview());
	}
	
	private JPanel getTimeOverview(){
		JPanel container = new JPanel();
		container.setLayout(new GridLayout(1,1));
		JPanel subcontainer = new JPanel();
		subcontainer.setLayout(new GridLayout(1,1));
		subcontainer.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1, true));
		subcontainer.add(new TimeOverview(overviewList.getSelectedValue()));
		container.add(subcontainer);
		return container;
	}
	
	private JPanel getAnswerOverview(){
		JPanel container = new JPanel();
		container.setLayout(new GridLayout(1,1));
		JPanel subcontainer = new JPanel();
		subcontainer.setLayout(new GridLayout(1,1));
		subcontainer.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1, true));
		subcontainer.add(new AnswerOverview(overviewList.getSelectedValue()));
		container.add(subcontainer);
		return container;
	}
	
	private void startInterrogation(){
		Lesson selectedLesson = overviewList.getSelectedValue();
		new Interrogation(selectedLesson);
	}
}