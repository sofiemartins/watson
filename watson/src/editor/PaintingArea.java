package editor;

import javax.swing.JPanel;

import gui.Editor;

import java.awt.Shape;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Point;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.geom.Line2D;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.BasicStroke;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.awt.AlphaComposite;

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
	private BufferedImage image;
	private Graphics2D imageGraphics;
		
	/**
	 * Sometimes a shape shows up while drawing that shouldn't be on the image later
	 */
	private Shape preview;
	private Point previewStart; // TODO: find a better name
	private Point lastDrawn; //Important for interpolating drawing gaps due to performance issues.
	
	/**
	 * @param We need a composite for simple drawing and erasing things
	 */
	private static final AlphaComposite drawing = AlphaComposite.SrcOver;
	private static final AlphaComposite erasing = AlphaComposite.DstOut;
	private static final AlphaComposite marking = AlphaComposite.DstOver;
	
	public PaintingArea(BufferedImage bufferedImage){
		image = bufferedImage;
		imageGraphics = image.createGraphics();
		addListeners();
		setBackground(Color.white);
		imageGraphics.setColor(Editor.currentPen.getColor());
		imageGraphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	}
	
	private void addListeners(){
		addMouseListener(this);
		addMouseMotionListener(this);
	}
	
	@Override 
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;
		g2.drawImage(image, 0, 0, null);
		if(preview!=null){
			g2.setColor(Editor.currentPen.getColor());
			g2.setStroke(new BasicStroke(Editor.currentPen.getSizeInPx()));
			g2.draw(preview);
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {}

	@Override
	public void mouseEntered(MouseEvent e) {
		this.setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
	}

	@Override
	public void mouseExited(MouseEvent e) {
		this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
	}

	@Override
	public void mousePressed(MouseEvent e) {
		updatePen();
		lastDrawn = e.getPoint();
		if(Editor.currentPen.getMode()==RULER || Editor.currentPen.getMode()==SQUARE){
			previewStart = new Point(e.getX(), e.getY());
		}else if(Editor.currentPen.getType()==PenType.ERASER){
			erase(e.getPoint());
		}else if(Editor.currentPen.getType()==PenType.MARKER){
			mark(e.getPoint());
		}else{
			paintInterpolated(e.getPoint());
		}
		repaint();
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if(Editor.currentPen.getMode()==RULER){
			imageGraphics.drawLine((int)previewStart.getX(), (int)previewStart.getY(), (int)e.getX(), (int)e.getY());
		}else if(Editor.currentPen.getMode()==SQUARE){
			imageGraphics.drawRect((int)previewStart.getX(), (int)previewStart.getY(), 
					getWidthDifference(e), getHeightDifference(e));
		}
		preview = null;
		previewStart = null;
		lastDrawn = null;
		repaint();
	}

	@Override
	public void mouseDragged(MouseEvent e) { 
		if(Editor.currentPen.getMode()==NONE){
			paint(e.getPoint());
		}else if(Editor.currentPen.getMode()==RULER){
			preview = new Line2D.Double(previewStart.getX(), previewStart.getY(), e.getX(), e.getY());
		}else if(Editor.currentPen.getMode()==SQUARE){
			preview = new Rectangle((int)previewStart.getX(),(int) previewStart.getY(), 
					getWidthDifference(e), getHeightDifference(e));
		}
		repaint();
	}
	
	private int getWidthDifference(MouseEvent e){
		return (int)(e.getX() - previewStart.getX());
	}
	
	private int getHeightDifference(MouseEvent e){
		return (int)(e.getY() - previewStart.getY());
	}
	
	@Override
	public void mouseMoved(MouseEvent e) {}
	
	private void paint(Point point){
		if(Editor.currentPen.getType() == PenType.ERASER){
			erase(point);
		}else if(Editor.currentPen.getType()==PenType.MARKER){
			mark(point);
		}else if(Editor.currentPen.getType()==PenType.PEN){
			paintInterpolated(point);
		}
	}
	
	private void paintInterpolated(Point point){
		if(lastDrawn==null){
			imageGraphics.drawLine((int)point.getX(), (int)point.getY(), (int)point.getX(), (int)point.getY());
		}else{
			imageGraphics.drawLine((int)lastDrawn.getX(), (int)lastDrawn.getY(), (int)point.getX(), (int)point.getY());
		}
		lastDrawn = point;
	}
	
	private void erase(Point point){
		imageGraphics.setComposite(erasing);
		paintInterpolated(point);
		imageGraphics.setComposite(drawing);
	}
	
	private void mark(Point point){
		imageGraphics.setComposite(marking);
		paintInterpolated(point);
		imageGraphics.setComposite(drawing);
	}
	
	private void updatePen(){
		imageGraphics.setColor(Editor.currentPen.getColor());
		imageGraphics.setStroke(new BasicStroke(Editor.currentPen.getSizeInPx(), BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
	}
	
	public BufferedImage getImage(){
		return image;
	}
	
	public void open(BufferedImage bufferedImage){
		image = bufferedImage;
		imageGraphics = image.createGraphics();
		updatePen();
		repaint();
	}
}
