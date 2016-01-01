package gui;

import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.BorderLayout;
import static java.awt.BorderLayout.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import editor.PaintingArea;
import editor.Pen;
import editor.Toolbar;
import editor.ToolbarEvent;

public class Editor extends JPanel{
	
	public static final long serialVersionUID = 995873214543768578L;
	public static Pen currentPen = Pen.PEN;
	
	public Editor(){
		setLayout(new BorderLayout());
		setBorder(new EmptyBorder(5,5,5,5));
		add(getToolbar(), NORTH);
		add(new PaintingArea(), CENTER);
	}
	
	private Toolbar getToolbar(){
		Toolbar toolbar = new Toolbar();
		toolbar.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				ToolbarEvent toolbarEvent = (ToolbarEvent)e;
				currentPen = toolbarEvent.getPen(); 
			}
		});
		return toolbar;
	}

}
