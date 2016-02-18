package toolbar;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import editor.Pen;
import editor.PenType;
import editor.Toolbar;
import editor.ToolbarButton;
import editor.ToolbarEvent;
import gui.Editor;

public class PenTypePanel extends JPanel{
	
	public static final long serialVersionUID = 4466577463325436565L;
	
	private ToolbarButton penButton = getPenButton();
	private ToolbarButton eraserButton = getEraserButton();
	private ToolbarButton markerButton = getMarkerButton();
	
	private ActionListener actionListener;
	
	public PenTypePanel(){
		setLayout(new BorderLayout());
		add(Toolbar.getLabel("Pen Type"), BorderLayout.NORTH);
		JPanel subcontainer = new JPanel();
		setupPenTypePanel(subcontainer);
		addButtonsToPenTypePanel(subcontainer);
		add(subcontainer, BorderLayout.CENTER);
	}
	
	private void setupPenTypePanel(JPanel container){
		container.setLayout(new GridLayout(1,3));
		container.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1, true));
	}
	
	private void addButtonsToPenTypePanel(JPanel container){
		ToolbarButton buttons[] = { penButton, eraserButton, markerButton };
		for(ToolbarButton button : buttons){
			JPanel buttonContainer = new JPanel();
			buttonContainer.setLayout(new GridLayout(1,1));
			buttonContainer.add(button);
			container.add(buttonContainer);
		}
	}
	
	private ToolbarButton getPenButton(){
		ToolbarButton button = new ToolbarButton("Pen");
		button.setVerticalTextPosition(SwingConstants.BOTTOM);
		button.setHorizontalTextPosition(SwingConstants.CENTER);
		button.setPreferredSize(new Dimension(60, 60));
		button.setBorder(new EmptyBorder(8, 4, 8, 8));
		setIcon(button, "res/pen.png");
		button.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				if(actionListener!=null){
					actionListener.actionPerformed(new ToolbarEvent(this, 
							ActionEvent.ACTION_PERFORMED, 
							"change to standard pen",
							Pen.PEN)); // TODO: Let the user define their default pen oder change to last used pen
							eraserButton.setSelected(false);
							markerButton.setSelected(false);	
				}
			}
		});
		return button;
	}
	
	private ToolbarButton getEraserButton(){
		ToolbarButton button = new ToolbarButton("Eraser");
		button.setVerticalTextPosition(SwingConstants.BOTTOM);
		button.setHorizontalTextPosition(SwingConstants.CENTER);
		button.setPreferredSize(new Dimension(60, 60));		
		button.setBorder(new EmptyBorder(8, 4, 8, 4));
		setIcon(button, "res/eraser.png");
		button.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				if(actionListener!=null){
					actionListener.actionPerformed(new ToolbarEvent(this,
							ActionEvent.ACTION_PERFORMED,
							"change to eraser",
							Pen.ERASER));
							penButton.setSelected(false);
							markerButton.setSelected(false);
				}
			}
		});
		return button;
	}
	
	private ToolbarButton getMarkerButton(){
		ToolbarButton button = new ToolbarButton("Marker");
		button.setVerticalTextPosition(SwingConstants.BOTTOM);
		button.setHorizontalTextPosition(SwingConstants.CENTER);
		button.setPreferredSize(new Dimension(60, 60));		
		button.setBorder(new EmptyBorder(8, 8, 8, 4));
		setIcon(button, "res/marker.png");
		button.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				if(actionListener!=null){
					actionListener.actionPerformed(new ToolbarEvent(this,
							ActionEvent.ACTION_PERFORMED,
							"change to marker",
							Pen.MARKER));
							penButton.setSelected(false);
							eraserButton.setSelected(false);
				}
			}
		});
		return button;
	}
	
	private void setIcon(ToolbarButton button, String filepath){
		BufferedImage image = null;
		try{
			image = ImageIO.read(new File(filepath));
		}catch(Exception e){
			e.printStackTrace();//TODO: Exception handling
		}
		button.setIcon(new ImageIcon(image.getScaledInstance(25, 25, Image.SCALE_SMOOTH)));
	}
	
	private boolean isEraser(){
		return Editor.currentPen.getType()==PenType.ERASER;
	}
	
	private boolean isMarker(){
		return Editor.currentPen.getType()==PenType.MARKER;
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
	
	private boolean isDefaultPen(){
		return Editor.currentPen.getType()==PenType.PEN;
	}
	
	public void update(){
		checkType(Editor.currentPen);
	}
	
	public void addActionListener(ActionListener al){
		actionListener = al;
	}
}
