package gui;

import javax.swing.JPanel;
import javax.imageio.ImageIO;
import java.awt.Rectangle;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;

import static editor.PenType.*;
import editor.PenType;

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
	private int penSize = 2;
	private Color penColor = Color.black;
	private PenType penType = PEN;
	
		
	public CardEditor(){
		addMouseListener(this);
		addMouseMotionListener(this);
		setBackground(Color.white);
		imageGraphics.setColor(Color.blue);
	}
	
	@Override 
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		g.drawImage(image, 0, 0, null);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		imageGraphics.fillRect(e.getX()-1, e.getY()-1, 2, 2);;
		repaint();
	}

	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}

	@Override
	public void mousePressed(MouseEvent e) {
		imageGraphics.fillRect(e.getX()-1, e.getY()-1, 4, 4);;
		repaint();
	}

	@Override
	public void mouseReleased(MouseEvent e) {}

	@Override
	public void mouseDragged(MouseEvent e) {
		imageGraphics.fillRect(e.getX()-1, e.getY()-1, 4, 4);;
		repaint();
	}

	@Override
	public void mouseMoved(MouseEvent e) {}
}
