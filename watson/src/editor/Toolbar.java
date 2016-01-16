package editor;

import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.border.EmptyBorder;
import java.awt.GridLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;

import static java.awt.Color.*;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import editor.ColorButton;
import editor.PenSizeButton;
import gui.Editor;

public class Toolbar extends JPanel{
	
	public static final long serialVersionUID = 8724543215436543876L;
	
	private ActionListener actionListener;
	private static final String TOOLBAR_BUTTON = "toolbarButton";
	
	public Toolbar(){
		addComponents();
	}
	
	private void addComponents(){
		setLayout(new FlowLayout(FlowLayout.LEFT));
		setBorder(new EmptyBorder(0,0,0,0));
		add(getPenTypePanel());
		add(getPenModePanel());
		add(getColorPanel());
		add(getPenSizePanel());
	}
	
	/**
	 * Checks whether the editor pen variable is in sync with the toolbar.
	 */
	public void update(){
		checkSize();
		checkColor();
		checkMode(getCurrentPen());
		checkType(getCurrentPen());
	}
	
	private Pen getCurrentPen(){
		return Editor.currentPen;
	}
	
	private boolean isFine(){
		return getCurrentPen().getSize()==PenSize.FINE;
	}
	
	private boolean isMedium(){
		return getCurrentPen().getSize()==PenSize.MEDIUM;
	}
	
	private boolean isThick(){
		return getCurrentPen().getSize()==PenSize.THICK;
	}
	
	private void checkSize(){
		resetAllPenSizeButtons();
		if(isFine()){
			fineButton.setSelected(true);
		}else if(isMedium()){
			mediumButton.setSelected(true);
		}else if(isThick()){
			thickButton.setSelected(true);
		}
	}
	
	private boolean hasColor(Color color){
		return getCurrentPen().getColor()==color;
	}
	
	private void checkColor(){
		resetAllColorButtons();
		if(hasColor(black)){
			blackButton.setSelected(true);
		}else if(hasColor(blue)){
			blueButton.setSelected(true);
		}else if(hasColor(red)){
			redButton.setSelected(true);
		}else if(hasColor(green)){
			greenButton.setSelected(true);
		}
	}
	
	private boolean isRuler(){
		return getCurrentPen().getMode()==PenMode.RULER;
	}
	
	private boolean isInRectangleMode(){
		return getCurrentPen().getMode()==PenMode.SQUARE;
	}
	
	private void setAllModeButtonsUnselected(){
		rulerButton.setSelected(false);
		rectangleButton.setSelected(false);
	}
	
	private void checkMode(Pen currentPen){
		setAllModeButtonsUnselected();
		if(isRuler()){
			rulerButton.setSelected(true);
		}else if(isInRectangleMode()){
			rectangleButton.setSelected(true);
		}
	}
	
	private boolean isDefaultPen(){
		return getCurrentPen().getType()==PenType.PEN;
	}
	
	private boolean isEraser(){
		return getCurrentPen().getType()==PenType.ERASER;
	}
	
	private boolean isMarker(){
		return getCurrentPen().getType()==PenType.MARKER;
	}
	
	private void setAllTypeButtonsUnselected(){
		penButton.setSelected(false);
		eraserButton.setSelected(false);
		markerButton.setSelected(false);	
	}
	
	private void checkType(Pen currentPen){
		setAllTypeButtonsUnselected();
		if(isDefaultPen()){
			penButton.setSelected(true);
		}else if(isEraser()){
			eraserButton.setSelected(true);
		}else if(isMarker()){
			eraserButton.setSelected(true);
		}
	}

	private JPanel getPenTypePanel(){
		JPanel container = new JPanel();
		container.setLayout(new BorderLayout());
		container.setBorder(new EmptyBorder(0,0,0,0));
		container.add(getLabel("Pen Type"), BorderLayout.NORTH);
		JPanel subcontainer = new JPanel();
		setupPenTypePanel(subcontainer);
		addButtonsToPenTypePanel(subcontainer);
		container.add(subcontainer, BorderLayout.CENTER);
		return container;
	}
	
	private void setupPenTypePanel(JPanel container){
		container.setLayout(new GridLayout(1,3));
		container.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1, true));
	}
	
	private void addButtonsToPenTypePanel(JPanel container){
		JToggleButton buttons[] = { penButton, eraserButton, markerButton };
		for(JToggleButton button : buttons){
			JPanel buttonContainer = new JPanel();
			buttonContainer.setLayout(new GridLayout(1,1));
			buttonContainer.add(button);
			container.add(buttonContainer);
		}
	}
	
	private JToggleButton penButton = getPenButton();
	private JToggleButton eraserButton = getEraserButton();
	private JToggleButton markerButton = getMarkerButton();
	
	private JToggleButton getPenButton(){
		JToggleButton button = new JToggleButton();
		button.setPreferredSize(new Dimension(50, 60));
		button.setBorder(new EmptyBorder(8, 4, 8, 8));
		button.setName(TOOLBAR_BUTTON);
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
		button.setPreferredSize(new Dimension(50, 60));
		button.setBorder(new EmptyBorder(8, 4, 8, 4));
		button.setName(TOOLBAR_BUTTON);
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
		button.setPreferredSize(new Dimension(50, 60));
		button.setBorder(new EmptyBorder(8, 8, 8, 4));
		button.setName(TOOLBAR_BUTTON);
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
		container.setLayout(new BorderLayout());
		container.setBorder(new EmptyBorder(0,0,0,0));
		container.add(getLabel("Pen Mode"), BorderLayout.NORTH);
		JPanel subcontainer = new JPanel();
		setUpPenModePanel(subcontainer);
		addButtonsToPenModePanel(subcontainer);
		container.add(subcontainer, BorderLayout.CENTER);
		return container;
	}
	
	private void setUpPenModePanel(JPanel container){
		container.setLayout(new GridLayout(1,2));
		container.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1, true));
	}
	
	private void addButtonsToPenModePanel(JPanel container){
		JToggleButton buttons[] = { rulerButton, rectangleButton };
		for(JToggleButton button : buttons){
			JPanel buttonContainer = new JPanel();
			buttonContainer.setLayout(new GridLayout(1,1));
			buttonContainer.add(button);
			container.add(buttonContainer);
		}
	}
	
	private JToggleButton rulerButton = getRulerButton();
	private JToggleButton rectangleButton = getRectangleButton();
	
	private JToggleButton getRulerButton(){
		JToggleButton button = new JToggleButton();
		button.setPreferredSize(new Dimension(50, 60));
		button.setBorder(new EmptyBorder(8, 4, 8, 8));
		button.setName(TOOLBAR_BUTTON);
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
		button.setPreferredSize(new Dimension(50, 60));
		button.setBorder(new EmptyBorder(8, 8, 8, 4));
		button.setName(TOOLBAR_BUTTON);
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
		container.setLayout(new BorderLayout());
		container.setBorder(new EmptyBorder(0,0,0,0));
		container.add(getLabel("Color"), BorderLayout.NORTH);
		JPanel subcontainer = new JPanel();
		setUpColorPanel(subcontainer);
		addButtonsToColorPanel(subcontainer);
		container.add(subcontainer, BorderLayout.CENTER);
		return container;
	}
	
	private void setUpColorPanel(JPanel container){
		container.setLayout(new GridLayout(1,4));
		container.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1, true));
	}
	
	private void addButtonsToColorPanel(JPanel container){
		JToggleButton buttons[] = { blackButton, blueButton, greenButton, redButton };
		for(JToggleButton button : buttons){
			JPanel buttonPanel = new JPanel();
			buttonPanel.setLayout(new GridLayout(1,1));
			buttonPanel.add(button);
			container.add(buttonPanel);
		}
	}
	
	private ColorButton blackButton = getColorButton(Color.black);
	private ColorButton blueButton = getColorButton(Color.blue);
	private ColorButton greenButton = getColorButton(Color.green);
	private ColorButton redButton = getColorButton(Color.red);
		
	private ColorButton getColorButton(Color color){
		ColorButton button = new ColorButton(color);
		button.setPreferredSize(new Dimension(50, 60));
		button.setBorder(new EmptyBorder(8, 8, 8, 8));
		button.setName(TOOLBAR_BUTTON);
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
		container.setLayout(new BorderLayout());
		container.setBorder(new EmptyBorder(0,0,0,0));
		JPanel subcontainer = new JPanel();
		setUpPenSizePanel(subcontainer);
		container.add(getLabel("Pen Size"), BorderLayout.NORTH);
		addButtonsToPenSizePanel(subcontainer);
		container.add(subcontainer, BorderLayout.CENTER);
		return container;
	}
	
	private void setUpPenSizePanel(JPanel container){
		container.setLayout(new GridLayout(1,3));
		container.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1, true));
	}
	
	private void addButtonsToPenSizePanel(JPanel container){
		JToggleButton buttons[] = { fineButton, mediumButton, thickButton };
		for(JToggleButton button : buttons){
			JPanel buttonPanel = new JPanel();
			buttonPanel.setLayout(new GridLayout(1,1));
			buttonPanel.add(button);
			container.add(buttonPanel, BorderLayout.CENTER);
		}
	}
	
	private JLabel getLabel(String string){
		JLabel label = new JLabel(string);
		label.setBorder(new EmptyBorder(5, 5, 5, 5));
		label.setHorizontalAlignment(JLabel.CENTER);
		label.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		return label;
	}
	
	private PenSizeButton fineButton = getPenSizeButton(PenSize.FINE);
	private PenSizeButton mediumButton = getPenSizeButton(PenSize.MEDIUM);
	private PenSizeButton thickButton = getPenSizeButton(PenSize.THICK);
	
	private PenSizeButton getPenSizeButton(PenSize penSize){
		PenSizeButton button = new PenSizeButton(penSize);
		button.setPreferredSize(new Dimension(50, 60));
		button.setBorder(new EmptyBorder(8, 8, 8, 8));
		button.setName(TOOLBAR_BUTTON);
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
