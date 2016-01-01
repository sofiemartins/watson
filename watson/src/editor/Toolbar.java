package editor;

import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.ImageIcon;;
import java.awt.GridLayout;
import java.awt.Color;
import java.awt.Rectangle;
import java.awt.Graphics;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Toolbar {
	
	public Toolbar(){}

	private JPanel getPenTypePanel(){}
	
	private JButton getPenButton(){
		JButton button = new JButton();
		button.setIcon(new ImageIcon(getClass().getResource("pen.png")));
		button.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				//TODO implement a pen listener
			}
		});
		return button;
	}
	
	private JButton getEraserButton(){
		JButton button = new JButton();
		button.setIcon(new ImageIcon(getClass().getResource("eraser.png")));
		button.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				//switch to eraser
			}
		});
		return button;
	}
	
	private JButton getMarkerButton(){
		JButton button = new JButton();
		button.setIcon(new ImageIcon(getClass().getResource("marker.png")));
		button.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				//switch to marker
			}
		});
		return button;
	}
	
	private JPanel getPenModePanel(){}
	
	private JButton getRulerButton(){
		JButton button = new JButton();
		button.setIcon(new ImageIcon(getClass().getResource("ruler.png")));
		button.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				//switch to ruler mode
			}
		});
		return button;
	}
	
	private JButton getRectangleButton(){
		JButton button = new JButton(){
			@Override
			public void paintComponent(Graphics g){
				super.paintComponent(g);
				g.setColor(Color.black);
				g.drawRect(5,5,this.getWidth()-10, this.getHeight()-10);
			}
		}
	}
	
	private JPanel getColorPanel(){}
	
	private JButton getColorButton(Color color){}
	
	private JPanel getPenSizePanel(){}
	
	private JButton getPenSizeButton(int penSize){}
}
