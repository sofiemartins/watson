package panel;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

public class Panel extends JPanel{
	
	public static final long serialVersionUID = 4493968685948123531L;
	
	public static void setIcon(JButton button, String filepath){
		BufferedImage image = null;
		try{
			image = ImageIO.read(new File("res/" + filepath));
		}catch(Exception e){
			e.printStackTrace();//TODO: Exception handling
		}
		button.setIcon(new ImageIcon(image.getScaledInstance(25, 25, Image.SCALE_SMOOTH)));
	}
}
