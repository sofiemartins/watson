package editor;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.border.EmptyBorder;
import java.awt.FlowLayout;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import toolbar.*;

public class Toolbar extends JPanel{
	
	public static final long serialVersionUID = 8724543215436543876L;
	
	private ActionListener actionListener;
	 
	private ToolbarPanel[] panels = { new PenTypePanel(), new PenModePanel(), new PenColorPanel(), new PenSizePanel() };
	
	public Toolbar(){
		setupLayout();
		addComponents();
	}
	
	private void setupLayout(){
		setLayout(new FlowLayout(FlowLayout.LEFT));
		setBorder(new EmptyBorder(0,0,0,0));
	}
	
	private void addComponents(){
		addActionListeners();
		for(JPanel panel : panels){
			add(panel);
		}
	}
	
	private void addActionListeners(){
		for(ToolbarPanel panel : panels){
			panel.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent e){
					actionListener.actionPerformed(e);
				}
				
			});
		}
	}
	
	/**
	 * Checks whether the editor pen variable is in sync with the toolbar.
	 */
	public void update(){
		for(ToolbarPanel panel : panels){
			panel.update();
		}
	}
	
	public static JLabel getLabel(String string){
		JLabel label = new JLabel(string);
		label.setBorder(new EmptyBorder(5, 5, 5, 5));
		label.setHorizontalAlignment(JLabel.CENTER);
		return label;
	}
	
	public void addActionListener(ActionListener al){
		actionListener = al;
	}
}
