package editor;

import javax.swing.BorderFactory;
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
import java.awt.event.ComponentListener;
import java.awt.event.ComponentEvent;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.awt.AlphaComposite;
import java.util.LinkedList;

import static editor.PenMode.*;

public class PaintingArea extends JPanel implements MouseListener, MouseMotionListener, ComponentListener{
	
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
		show(bufferedImage);
		setUpPanel();
		setUpImageGraphics();
		addComponentListener(this);
	}
	
	private void setUpPanel(){
		addListeners();
		setBackground(Color.white);
		setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1, true));
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
		g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
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
		Point pointOnImage = getPointOnImage(e.getPoint());
		makeFollowingStepUndoable();
		updatePen();
		saveLastDrawnPoint(pointOnImage); // interpolation is drawn on the image, so image coordinates
		saveStartingPoint(e.getPoint()); //Preview is drawn on the panel, so panel coordinates
		drawDependingOnPen(pointOnImage);
		repaint();
	}
	
	private void makeFollowingStepUndoable(){
		if(snapshotClipboard.size()>10){
			snapshotClipboard.removeFirst();
		}
		createSnapshot();
		redoClipboard = new LinkedList<BufferedImage>();
	}
	
	private void saveLastDrawnPoint(Point point){
		lastDrawn = point;
	}
	
	private void drawDependingOnPen(Point point){
		if(isRuler() || isInRectangleMode()){
			//Do nothing, because only preview is drawn here TODO: make this better
		}else if(isEraser()){
			erase(point);
		}else if(isMarker()){
			mark(point);
		}else{
			paintInterpolated(point);
		}
	}
	
	private void saveStartingPoint(Point point){
		if(isRuler() || isInRectangleMode()){
			previewStart = point;
		}
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
	
	private boolean isDefaultMode(){
		return getCurrentPen().getMode()==PenMode.NONE;
	}
	
	private boolean isDefaultType(){
		return getCurrentPen().getType()==PenType.PEN;
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
		Point startingPointOnImage = null;//TODO: ?
		if(previewStart!=null){
			startingPointOnImage = getPointOnImage(previewStart);
		}
		Point lastPointOnImage = getPointOnImage(e.getPoint());
		if(isRuler()){
			imageGraphics.drawLine((int)startingPointOnImage.getX(), 
									(int)startingPointOnImage.getY(), 
									(int)lastPointOnImage.getX(), 
									(int)lastPointOnImage.getY());
		}else if(isInRectangleMode()){
			imageGraphics.drawRect((int)startingPointOnImage.getX(), 
									(int)startingPointOnImage.getY(), 
									getWidthDifference(startingPointOnImage, lastPointOnImage), 
									getHeightDifference(startingPointOnImage, lastPointOnImage));
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
		Point pointOnImage = getPointOnImage(e.getPoint());
		if(isDefaultMode()){
			paintDependingOnPenType(pointOnImage);
		}else if(isRuler()){
			preview = new Line2D.Double(previewStart.getX(), previewStart.getY(), e.getX(), e.getY());
		}else if(isInRectangleMode()){
			preview = new Rectangle((int)previewStart.getX(),
									(int) previewStart.getY(), 
									getWidthDifference(previewStart, e.getPoint()), 
									getHeightDifference(previewStart, e.getPoint()));
		}
		repaint();
	}
	
	private int getWidthDifference(Point point1, Point point2){
		return (int)(point1.getX() - point2.getX());
	}
	
	private int getHeightDifference(Point point1, Point point2){
		return (int)(point1.getY() - point2.getY());
	}
	
	@Override
	public void mouseMoved(MouseEvent e) {}
	
	private void paintDependingOnPenType(Point point){
		if(isEraser()){
			erase(point);
		}else if(isMarker()){
			mark(point);
		}else if(isDefaultType()){
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
		resetComposite();
	}
	
	private void mark(Point point){
		imageGraphics.setStroke(getMarkerStroke());
		imageGraphics.setComposite(marking);
		paintInterpolated(point);
		updatePen();
		resetComposite();
	}
	
	private BasicStroke getMarkerStroke(){
		return new BasicStroke(Editor.currentPen.getSizeInPx(), BasicStroke.CAP_SQUARE, BasicStroke.JOIN_BEVEL);
	}
	
	private void updatePen(){
		imageGraphics.setColor(Editor.currentPen.getColor());
		imageGraphics.setStroke(new BasicStroke(Editor.currentPen.getSizeInPx(), BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
	}
	
	public BufferedImage getImage(){
		return image;
	}
	
	public void open(BufferedImage bufferedImage){
		show(bufferedImage);
		emptyClipboards();
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
		snapshotClipboard.add(getDeepCopyOfCurrentImage());//copy the image
	}
	
	private void loadLastSnapshot(){
		if(!snapshotClipboard.isEmpty()){
			redoClipboard.add(getDeepCopyOfCurrentImage());
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
	
	private BufferedImage getDeepCopyOfCurrentImage(){
		//stole this here:
		//http://stackoverflow.com/questions/3514158/how-do-you-clone-a-bufferedimage; 2016-01-14, 7:38 MET
		ColorModel colorModel = image.getColorModel();
		boolean isAlphaPremultiplied = colorModel.isAlphaPremultiplied();
		WritableRaster raster = image.copyData(null);
		return new BufferedImage(colorModel, raster, isAlphaPremultiplied, null);
	}
	
	private Point getPointOnImage(Point pointOnScreen){
		int xOnImage = (int)(pointOnScreen.getX()*((double)image.getWidth()/(double)getWidth()));//Intercept theorem, or at least similar
		int yOnImage = (int)(pointOnScreen.getY()*((double)image.getHeight()/(double)getHeight()));
		return new Point(xOnImage, yOnImage);
 	}
	
	@Override
	public void componentHidden(ComponentEvent e) {}

	@Override
	public void componentMoved(ComponentEvent e) {}

	@Override
	public void componentResized(ComponentEvent e) {
		BufferedImage resizedImage = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
		Graphics2D graphics = resizedImage.createGraphics();
		LinkedList<BufferedImage> newSnapshotClipboard = new LinkedList<BufferedImage>();
		for(BufferedImage img : snapshotClipboard){
			graphics.drawImage(img, 0, 0, getWidth(), getHeight(), null);
			newSnapshotClipboard.add(resizedImage);
			resizedImage = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
			graphics = resizedImage.createGraphics();
		}
		snapshotClipboard = newSnapshotClipboard;
		LinkedList<BufferedImage> newRedoClipboard = new LinkedList<BufferedImage>();
		for(BufferedImage img : redoClipboard){
			graphics.drawImage(img, 0, 0, getWidth(), getHeight(), null);
			newRedoClipboard.add(resizedImage);
			resizedImage = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
			graphics = resizedImage.createGraphics();
		}
		redoClipboard = newRedoClipboard;
		graphics.drawImage(image, 0, 0, getWidth(), getHeight(), null);
		image = resizedImage;
		imageGraphics = image.createGraphics();
		revalidate();
		repaint();
	}
	
	@Override
	public void componentShown(ComponentEvent e) {}
}