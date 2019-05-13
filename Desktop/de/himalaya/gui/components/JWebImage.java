package de.himalaya.gui.components;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class JWebImage extends JLabel{

	private static final long serialVersionUID = -1280192681287577985L;

	private URL source;
	
	public JWebImage(URL source) {
		this.setSource(source);
	}
	
	public void reload(ScaleMode scaleMode) throws IOException {
		BufferedImage img = ImageIO.read(this.getSource());
		Image newImg = img;
		switch (scaleMode) {
		case CONTAIN:
			
			break;
		case STRETCH:
			newImg = img.getScaledInstance((int)this.getBounds().getWidth(), (int)this.getBounds().getHeight(), 30);
			break;
		case NONE:
			break;
		default:
			break;
		}
		this.setIcon(new ImageIcon(newImg));
	}
	
	public URL getSource() {
		return source;
	}

	public void setSource(URL source) {
		this.source = source;
	}
	
	public static enum ScaleMode{
		STRETCH,
		CONTAIN,
		NONE;
	}
	
}
