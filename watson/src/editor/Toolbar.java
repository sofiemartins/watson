package editor;

import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import javax.swing.border.EmptyBorder;
import java.awt.GridLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import editor.ColorButton;
import editor.PenSizeButton;

public class Toolbar extends JPanel{
	
	public static final long serialVersionUID = 8724543215436543876L;
	
	public Toolbar(){
		setLayout(new GridLayout(1,4));
		addComponents();
	}
	
	private void addComponents(){
		add(getPenTypePanel());
		add(getPenModePanel());
		add(getColorPanel());
		add(getPenSizePanel());
	}

	private JPanel getPenTypePanel(){
		JPanel container = new JPanel();
		container.setLayout(new GridLayout(1,3));
		container.setBorder(new EmptyBorder(10,2,10,2));
		container.add(getPenButton());
		container.add(getEraserButton());
		container.add(getMarkerButton());
		return container;
	}
	
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
	
	private JPanel getPenModePanel(){
		JPanel container = new JPanel();
		container.setLayout(new GridLayout(1,2));
		container.setBorder(new EmptyBorder(10,2,10,2));
		container.add(getRulerButton());
		container.add(getRectangleButton());
		return container;
	}
	
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
			public static final long serialVersionUID = 995847386758473869L;
			
			@Override
			public void paintComponent(Graphics g){
				super.paintComponent(g);
				g.setColor(Color.black);
				g.drawRect(5,5,this.getWidth()-10, this.getHeight()-10);
			}
		};
		button.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				//switch to rectangle mode
			}
		});
		return button;
	}
	
	private JPanel getColorPanel(){
		JPanel container = new JPanel();
		container.setLayout(new GridLayout(1,4));
		container.setBorder(new EmptyBorder(10,2,10,2));
		container.add(getColorButton(Color.black));
		container.add(getColorButton(Color.blue));
		container.add(getColorButton(Color.green));
		container.add(getColorButton(Color.red));
		return container;
	}
	
	private ColorButton getColorButton(Color color){
		ColorButton button = new ColorButton(color);
		button.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				//change pen color to buttonColor
			}
		});
		return button;
	}
	
	private JPanel getPenSizePanel(){
		JPanel container = new JPanel();
		container.setLayout(new GridLayout(1,3));
		container.setBorder(new EmptyBorder(10,2,10,2));
		container.add(getPenSizeButton(2));
		container.add(getPenSizeButton(3));
		container.add(getPenSizeButton(4));
		return container;
	}
	
	private PenSizeButton getPenSizeButton(int penSize){
		PenSizeButton button = new PenSizeButton(penSize);
		button.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				//change pen size to pen size
			}
		});
		return button;
	}
	
}
