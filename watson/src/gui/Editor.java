package gui;

import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.BorderLayout;
import static java.awt.BorderLayout.*;

import editor.PaintingArea;
import editor.Pen;
import editor.Toolbar;

public class Editor extends JPanel{
	
	public static final long serialVersionUID = 995873214543768578L;
	public static Pen currentPen = Pen.PEN;
	
	public Editor(){
		setLayout(new BorderLayout());
		setBorder(new EmptyBorder(5,5,5,5));
		add(new Toolbar(), NORTH);
		add(new PaintingArea(), CENTER);
	}
	
	private Toolbar getToolbar(){}

}
