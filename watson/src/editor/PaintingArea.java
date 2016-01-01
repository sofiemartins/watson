package editor;

import javax.swing.JPanel;

import gui.Editor;

import java.awt.Shape;
import java.awt.Rectangle;
import java.awt.Point;
import java.awt.Color;
import java.awt.geom.Line2D;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.BasicStroke;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;

import static editor.PenMode.*;

public class PaintingArea extends JPanel implements MouseListener, MouseMotionListener{
	
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
		
	/**
	 * Sometimes a shape shows up while drawing that shouldn't be on the image later
	 */
	private Shape preview;
	private Point previewStart; // TODO: find a better name
	
		
	public PaintingArea(){
		addMouseListener(this);
		addMouseMotionListener(this);
		setBackground(Color.white);
		imageGraphics.setColor(Editor.currentPen.getColor());
	}
	
	@Override 
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;
		g2.drawImage(image, 0, 0, null);
		if(preview!=null){
			g2.setColor(Editor.currentPen.getColor());
			g2.setStroke(new BasicStroke(Editor.currentPen.getSize()));
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
		if(Editor.currentPen.getMode()==RULER || Editor.currentPen.getMode()==SQUARE){
			previewStart = new Point(e.getX(), e.getY());
		}else{
			paint(e.getPoint());
		}
		repaint();
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		imageGraphics.setStroke(new BasicStroke(Editor.currentPen.getSize()));
		if(Editor.currentPen.getMode()==RULER){
			imageGraphics.setStroke(new BasicStroke(Editor.currentPen.getSize()));
			imageGraphics.drawLine((int)previewStart.getX(), (int)previewStart.getY(), (int)e.getX(), (int)e.getY());
		}else if(Editor.currentPen.getMode()==SQUARE){
			int dx = (int)(e.getX() - previewStart.getX());
			int dy = (int)(e.getY() - previewStart.getY());
			imageGraphics.drawRect((int)previewStart.getX(), (int)previewStart.getY(), dx, dy);
		}
		preview = null;
		previewStart = null;
		repaint();
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		if(Editor.currentPen.getMode()==NONE){
			paint(e.getPoint());
		}else if(Editor.currentPen.getMode()==RULER){
			preview = new Line2D.Double(previewStart.getX(), previewStart.getY(), e.getX(), e.getY());
		}else if(Editor.currentPen.getMode()==SQUARE){
			int dx = (int)(e.getX() - previewStart.getX());
			int dy = (int)(e.getY() - previewStart.getY());
			preview = new Rectangle((int)previewStart.getX(),(int) previewStart.getY(), dx, dy);
		}
		repaint();
	}
	
	@Override
	public void mouseMoved(MouseEvent e) {}
	
	private void paint(Point point){
		imageGraphics.fillRect((int)(point.getX()-0.5*Editor.currentPen.getSize()),
				(int)(point.getY()-0.5*Editor.currentPen.getSize()),
				Editor.currentPen.getSize(),
				Editor.currentPen.getSize());
	}
	
	public void updateColor(){//TODO: make this better
		imageGraphics.setColor(Editor.currentPen.getColor());
	}
}
