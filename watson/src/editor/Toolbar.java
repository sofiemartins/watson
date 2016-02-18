package editor;

import javax.swing.JPanel;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.border.EmptyBorder;
import java.awt.GridLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;

import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import static util.Colors.*;
import editor.ColorButton;
import editor.PenSizeButton;
import gui.Editor;
import toolbar.*;

public class Toolbar extends JPanel{
	
	public static final long serialVersionUID = 8724543215436543876L;
	
	private ActionListener actionListener;
	 
	private JPanel[] panels = { new PenTypePanel(), new PenModePanel(), new PenColorPanel() };
	
	public Toolbar(){
		addComponents();
	}
	
	private void addComponents(){
		setLayout(new FlowLayout(FlowLayout.LEFT));
		setBorder(new EmptyBorder(0,0,0,0));
		for(JPanel panel : panels){
			add(panel);
		}
		add(getPenSizePanel());
		//TODO add actionListeners
	}
	
	/**
	 * Checks whether the editor pen variable is in sync with the toolbar.
	 */
	public void update(){
		checkSize();
		checkColor();
		penModePanel.update();
		penTypePanel.update();
	}
	
	private Pen getCurrentPen(){
		return Editor.currentPen;
	}
	
	
	
	public static JLabel getLabel(String string){
		JLabel label = new JLabel(string);
		label.setBorder(new EmptyBorder(5, 5, 5, 5));
		label.setHorizontalAlignment(JLabel.CENTER);
		label.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		return label;
	}
	
	
	
	public void addActionListener(ActionListener al){
		actionListener = al;
	}
}
