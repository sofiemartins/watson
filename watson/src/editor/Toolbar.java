package editor;

import javax.swing.JPanel;
import javax.swing.JToggleButton;
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
		container.add(penButton);
		container.add(eraserButton);
		container.add(markerButton);
		return container;
	}
	
	private JToggleButton penButton = getPenButton();
	private JToggleButton eraserButton = getEraserButton();
	private JToggleButton markerButton = getMarkerButton();
	
	private JToggleButton getPenButton(){
		JToggleButton button = new JToggleButton();
		button.setIcon(new ImageIcon(getClass().getResource("pen.png")));
		button.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				Toolbar.this.actionListener.actionPerformed(new ToolbarEvent(this, 
						ActionEvent.ACTION_PERFORMED, 
						"change to standard pen",
						Pen.PEN)); // TODO: Let the user define their default pen oder change to last used pen
						eraserButton.setSelected(false);
						markerButton.setSelected(false);
			}
		});
		return button;
	}
	
	private JToggleButton getEraserButton(){
		JToggleButton button = new JToggleButton();
		button.setIcon(new ImageIcon(getClass().getResource("eraser.png")));
		button.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				Toolbar.this.actionListener.actionPerformed(new ToolbarEvent(this,
						ActionEvent.ACTION_PERFORMED,
						"change to eraser",
						Pen.ERASER));
						penButton.setSelected(false);
						markerButton.setSelected(false);
			}
		});
		return button;
	}
	
	private JToggleButton getMarkerButton(){
		JToggleButton button = new JToggleButton();
		button.setIcon(new ImageIcon(getClass().getResource("marker.png")));
		button.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				Toolbar.this.actionListener.actionPerformed(new ToolbarEvent(this,
						ActionEvent.ACTION_PERFORMED,
						"change to marker",
						Pen.MARKER));
						penButton.setSelected(false);
						eraserButton.setSelected(false);
			}
		});
		return button;
	}
	
	private JPanel getPenModePanel(){
		JPanel container = new JPanel();
		container.setLayout(new GridLayout(1,2));
		container.setBorder(new EmptyBorder(10,2,10,2));
		container.add(rulerButton);
		container.add(rectangleButton);
		return container;
	}
	
	private JToggleButton rulerButton = getRulerButton();
	private JToggleButton rectangleButton = getRectangleButton();
	
	private JToggleButton getRulerButton(){
		JToggleButton button = new JToggleButton();
		button.setIcon(new ImageIcon(getClass().getResource("ruler.png")));
		button.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				rectangleButton.setSelected(false);
				Pen currentPen = Editor.currentPen;
				Pen newPen;
				if(!rulerButton.isSelected()){
					newPen = new Pen(currentPen.getSize(), currentPen.getColor(), PenMode.NONE, currentPen.getType());
					Toolbar.this.actionListener.actionPerformed(new ToolbarEvent(this, 
							ActionEvent.ACTION_PERFORMED,
							"Disabled ruler", 
							newPen));
				}else{
					newPen = new Pen(currentPen.getSize(), currentPen.getColor(), PenMode.RULER, currentPen.getType());
					Toolbar.this.actionListener.actionPerformed(new ToolbarEvent(this,
							ActionEvent.ACTION_PERFORMED,
							"changed to ruler",
							newPen));
				}
			}
		});
		return button;
	}
	
	private JToggleButton getRectangleButton(){
		JToggleButton button = new JToggleButton(){
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
				rulerButton.setSelected(false);
				Pen currentPen = Editor.currentPen;
				Pen newPen;
				if(!rectangleButton.isSelected()){
					newPen = new Pen(currentPen.getSize(), currentPen.getColor(), PenMode.NONE, currentPen.getType());
					Toolbar.this.actionListener.actionPerformed(new ToolbarEvent(this, ActionEvent.ACTION_PERFORMED, 
							"Disabled rectangle mode.",
							newPen));
				}else{
					newPen = new Pen(currentPen.getSize(), currentPen.getColor(), PenMode.SQUARE, currentPen.getType());//TODO: Refactor this to rect
					Toolbar.this.actionListener.actionPerformed(new ToolbarEvent(this,
							ActionEvent.ACTION_PERFORMED,
							"changed to rectangle mode",
							newPen));
				}
			}
		});
		return button;
	}
	
	private JPanel getColorPanel(){
		JPanel container = new JPanel();
		container.setLayout(new GridLayout(1,4));
		container.setBorder(new EmptyBorder(10,2,10,2));
		container.add(blackButton);
		container.add(blueButton);
		container.add(greenButton);
		container.add(redButton);
		return container;
	}
	
	private ColorButton blackButton = getColorButton(Color.black);
	private ColorButton blueButton = getColorButton(Color.blue);
	private ColorButton greenButton = getColorButton(Color.green);
	private ColorButton redButton = getColorButton(Color.red);
		
	private ColorButton getColorButton(Color color){
		ColorButton button = new ColorButton(color);
		button.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				resetAllColorButtons();
				Toolbar.this.actionListener.actionPerformed(e);
			}
		});
		return button;
	}
	
	protected void resetAllColorButtons(){
		blackButton.setSelected(false);
		blueButton.setSelected(false);
		redButton.setSelected(false);
		greenButton.setSelected(false);
	}
	
	private JPanel getPenSizePanel(){
		JPanel container = new JPanel();
		container.setLayout(new GridLayout(1,3));
		container.setBorder(new EmptyBorder(10,2,10,2));
		container.add(fineButton);
		container.add(mediumButton);
		container.add(thickButton);
		return container;
	}
	
	private PenSizeButton fineButton = getPenSizeButton(PenSize.FINE);
	private PenSizeButton mediumButton = getPenSizeButton(PenSize.MEDIUM);
	private PenSizeButton thickButton = getPenSizeButton(PenSize.THICK);
	
	private PenSizeButton getPenSizeButton(PenSize penSize){
		PenSizeButton button = new PenSizeButton(penSize);
		button.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				resetAllPenSizeButtons();
				Toolbar.this.actionListener.actionPerformed(e);
			}
		});
		return button;
	}
	
	private void resetAllPenSizeButtons(){
		fineButton.setSelected(false);
		mediumButton.setSelected(false);
		thickButton.setSelected(false);
	}
	
	public void addActionListener(ActionListener al){
		actionListener = al;
	}
}
