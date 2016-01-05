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
import gui.Editor;

public class Toolbar extends JPanel{
	
	public static final long serialVersionUID = 8724543215436543876L;
	
	private ActionListener actionListener;
	
	public Toolbar(){
		addComponents();
	}
	
	private void addComponents(){
		setLayout(new GridLayout(1,4));
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
				Toolbar.this.actionListener.actionPerformed(new ToolbarEvent(this, 
						ActionEvent.ACTION_PERFORMED, 
						"change to standard pen",
						Pen.PEN)); // TODO: Let the user define their default pen oder change to last used pen
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
				Toolbar.this.actionListener.actionPerformed(new ToolbarEvent(this,
						ActionEvent.ACTION_PERFORMED,
						"change to eraser",
						Pen.ERASER));
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
				Toolbar.this.actionListener.actionPerformed(new ToolbarEvent(this,
						ActionEvent.ACTION_PERFORMED,
						"change to marker",
						Pen.MARKER));
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
				Pen currentPen = Editor.currentPen;
				Pen newPen = new Pen(currentPen.getSize(), currentPen.getColor(), PenMode.RULER, currentPen.getType());
				Toolbar.this.actionListener.actionPerformed(new ToolbarEvent(this,
						ActionEvent.ACTION_PERFORMED,
						"changed to ruler",
						newPen)); // TODO: If pen mode is ruler, this button should look different
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
				Pen currentPen = Editor.currentPen;
				Pen newPen = new Pen(currentPen.getSize(), currentPen.getColor(), PenMode.SQUARE, currentPen.getType());//TODO: Refactor this to rect
				Toolbar.this.actionListener.actionPerformed(new ToolbarEvent(this,
						ActionEvent.ACTION_PERFORMED,
						"changed to rectangle mode",
						newPen));
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
				Toolbar.this.actionListener.actionPerformed(e);
			}
		});
		return button;
	}
	
	private JPanel getPenSizePanel(){
		JPanel container = new JPanel();
		container.setLayout(new GridLayout(1,3));
		container.setBorder(new EmptyBorder(10,2,10,2));
		container.add(getPenSizeButton(PenSize.FINE));
		container.add(getPenSizeButton(PenSize.MEDIUM));
		container.add(getPenSizeButton(PenSize.THICK));
		return container;
	}
	
	private PenSizeButton getPenSizeButton(PenSize penSize){
		PenSizeButton button = new PenSizeButton(penSize);
		button.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				Toolbar.this.actionListener.actionPerformed(e);
			}
		});
		return button;
	}
	
	public void addActionListener(ActionListener al){
		actionListener = al;
	}
	
}
