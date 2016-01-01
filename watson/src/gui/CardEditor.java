package gui;

import javax.swing.JPanel;
import javax.imageio.ImageIO;
import java.awt.Shape;
import java.awt.Rectangle;
import java.awt.Point;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;

import static editor.PenType.*;
import static editor.PenMode.*;
import editor.PenType;
import editor.PenMode;

public class CardEditor extends JPanel implements MouseListener, MouseMotionListener{
	
	public static final long serialVersionUID = 960493968771821243L;
	
	/**
	 * the used images have a fixed size: It's a flashcard, if someone wants to create great artwork
	 * or write multiple pages of information he/she chose the wrong software.
	 * 
	 * TODO: Maybe TYPE_INT_ARGB is overkill and we should use TYPE_4BYTE_AGBR
	 * we definitely need alpha for the usage of yellow markers or similar.
	 */
	private BufferedImage image = new BufferedImage(1600,1000,BufferedImage.TYPE_INT_ARGB);
	private Graphics2D imageGraphics = image.createGraphics();
	
	//pen properties
	private int penSize = 2; //px
	private Color penColor = Color.black;
	private PenType penType = PEN;
	private PenMode penMode = NONE;
	
	private static final Color markerColor = new Color(255,240,0,100);
	private static final int markerSize = 6; //px
	
	/**
	 * Sometimes a shape shows up while drawing that shouldn't be on the image later
	 */
	private Shape preview;
	private Point previewStart; // TODO: find a better name
	
		
	public CardEditor(){
		addMouseListener(this);
		addMouseMotionListener(this);
		setBackground(Color.white);
		imageGraphics.setColor(Color.blue);
	}
	
	@Override 
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;
		g2.drawImage(image, 0, 0, null);
		if(preview!=null){
			g2.draw(preview);
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		imageGraphics.fillRect(e.getX()-1, e.getY()-1, 2, 2);;
		repaint();
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		
	}

	@Override
	public void mouseExited(MouseEvent e) {}

	@Override
	public void mousePressed(MouseEvent e) {
		paint(e.getPoint());
		if(penMode==RULER){
			previewStart = new Point(e.getX(), e.getY());
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {}

	@Override
	public void mouseDragged(MouseEvent e) {
		if(penMode==NONE){
			paint(e.getPoint());
		}else if(penMode==RULER){
			int dx = (int)(e.getX() - previewStart.getX());
			int dy = (int)(e.getY() - previewStart.getY());
			preview = new Rectangle((int)previewStart.getX(),(int) previewStart.getY(), dx, dy);
		}
	}
	
	@Override
	public void mouseMoved(MouseEvent e) {}
	
	private void paint(Point point){
		imageGraphics.fillRect((int)(point.getX()-0.5*penSize),
				(int)(point.getY()-0.5*penSize),
				penSize,
				penSize);
		repaint();
	}
}
