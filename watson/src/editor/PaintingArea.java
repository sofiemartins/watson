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
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.awt.AlphaComposite;
import java.util.LinkedList;

import static editor.PenMode.*;

public class PaintingArea extends JPanel implements MouseListener, MouseMotionListener{
	
	public static final long serialVersionUID = 960493968771821243L;
	public static final String UNDO = "undo";
	public static final String REDO = "redo";
	
	/**
	 * the used images have a fixed size: It's a flashcard, if someone wants to create great artwork
	 * or write multiple pages of information he/she chose the wrong software.
	 * 
	 * TODO: Maybe TYPE_INT_ARGB is overkill and we should use TYPE_4BYTE_AGBR
	 * we definitely need alpha for the usage of yellow markers or similar.
	 */
	private BufferedImage image;
	private Graphics2D imageGraphics;

	private LinkedList<BufferedImage> snapshotClipboard = new LinkedList<BufferedImage>();
	private LinkedList<BufferedImage> redoClipboard = new LinkedList<BufferedImage>();
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
		show(image);
		setUpPanel();
		setUpImageGraphics();
	}
	
	private void setUpPanel(){
		addListeners();
		setBackground(Color.white);
	}
	
	private void setUpImageGraphics(){
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
		drawCardContent(g2);
		drawPreview(g2);
	}
	
	private void drawCardContent(Graphics2D g){
		g.drawImage(image, 0, 0, null);
	}
	
	private void drawPreview(Graphics2D g){
		if(preview!=null){
			g.setColor(Editor.currentPen.getColor());
			g.setStroke(new BasicStroke(Editor.currentPen.getSizeInPx()));
			g.draw(preview);
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
		makeFollowingStepUndoable();
		updatePen();
		saveLastDrawnPoint(e);
		drawDependingOnPen(e);
		repaint();
	}
	
	private void makeFollowingStepUndoable(){
		createSnapshot();
		redoClipboard = new LinkedList<BufferedImage>();
	}
	
	private void saveLastDrawnPoint(MouseEvent e){
		lastDrawn = e.getPoint();
	}
	
	private void drawDependingOnPen(MouseEvent e){
		if(isRuler() || isInRectangleMode()){
			saveStartingPoint(e);
		}else if(isEraser()){
			erase(e.getPoint());
		}else if(isMarker()){
			mark(e.getPoint());
		}else{
			paintInterpolated(e.getPoint());
		}
	}
	
	private void saveStartingPoint(MouseEvent e){
		previewStart = new Point(e.getX(), e.getY());
	}
	
	private boolean isRuler(){
		return getCurrentPen().getMode()==RULER;
	}
	
	private boolean isInRectangleMode(){
		return getCurrentPen().getMode()==SQUARE;
	}
	
	private boolean isEraser(){
		return getCurrentPen().getType()==PenType.ERASER;
	}
	
	private boolean isMarker(){
		return getCurrentPen().getType()==PenType.MARKER;
	}
	
	private Pen getCurrentPen(){
		return Editor.currentPen;
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		drawPreviewOnImage(e);
		deleteSavedPointsAndPreview();
		resetComposite();
		repaint();
	}
	
	private void drawPreviewOnImage(MouseEvent e){
		setCompositeDependingOnPen();
		drawShapeDependingOnMode(e);
	}
	
	private void setCompositeDependingOnPen(){
		if(isMarker()){
			imageGraphics.setComposite(marking);
		}else if(isEraser()){
			imageGraphics.setComposite(erasing);
		}
	}
	
	private void drawShapeDependingOnMode(MouseEvent e){
		if(isRuler()){
			imageGraphics.drawLine((int)previewStart.getX(), (int)previewStart.getY(), (int)e.getX(), (int)e.getY());
		}else if(isInRectangleMode()){
			imageGraphics.drawRect((int)previewStart.getX(), (int)previewStart.getY(), 
					getWidthDifference(e), getHeightDifference(e));
		}
	}
	
	private void deleteSavedPointsAndPreview(){
		preview = null;
		previewStart = null;
		lastDrawn = null;
	}
	
	private void resetComposite(){
		imageGraphics.setComposite(drawing);
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
		imageGraphics.setStroke(new BasicStroke(Editor.currentPen.getSizeInPx(), BasicStroke.CAP_SQUARE, BasicStroke.JOIN_BEVEL));
		imageGraphics.setComposite(marking);
		paintInterpolated(point);
		updatePen();
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
		setUpImageGraphics();
		emptyClipboards();
		updatePen();
		repaint();
	}
	
	private void show(BufferedImage bufferedImage){
		image = bufferedImage;
		imageGraphics = image.createGraphics();
		setUpImageGraphics();
		updatePen();
		repaint();
	}
	
	private void emptyClipboards(){
		snapshotClipboard = new LinkedList<BufferedImage>();
		redoClipboard = new LinkedList<BufferedImage>();
	}
	
	private void createSnapshot(){
		snapshotClipboard.add(getCopyOfCurrentImage());//copy the image
	}
	
	private void loadLastSnapshot(){
		if(!snapshotClipboard.isEmpty()){
			redoClipboard.add(getCopyOfCurrentImage());
			show(snapshotClipboard.getLast());
			snapshotClipboard.removeLast();
		}
		repaint();
	}
	
	private void loadFromRedoClipboard(){
		if(!redoClipboard.isEmpty()){
			createSnapshot();
			show(redoClipboard.getLast());
			redoClipboard.removeLast();
		}
		repaint();
	}
	
	public void undo(){
		loadLastSnapshot();
	}
	
	public void redo(){
		loadFromRedoClipboard();
	}
	
	private BufferedImage getCopyOfCurrentImage(){
		//stole this here:
		//http://stackoverflow.com/questions/3514158/how-do-you-clone-a-bufferedimage; 2016-01-14, 7:38 MET
		ColorModel colorModel = image.getColorModel();
		boolean isAlphaPremultiplied = colorModel.isAlphaPremultiplied();
		WritableRaster raster = image.copyData(null);
		return new BufferedImage(colorModel, raster, isAlphaPremultiplied, null);
	}
}
