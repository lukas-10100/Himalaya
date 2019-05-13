package de.himalaya.gui.components;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JList;

public class HList<E> extends JList<E> {

	private static final long serialVersionUID = -5844374409907149069L;

	private int pixels = 3;
	
	public HList() {
		this.setOpaque(true);
	}
	
	@Override
	public void paint(Graphics g) {
		Graphics2D g2d = (Graphics2D)g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		g2d.setColor(getParent().getBackground());
		
		g2d.fillRect(0, 0, getWidth(), getHeight());
		
		int topOpacity = 90;
		int shade = 0;
		for (int i = 0; i < pixels; i++) {
			g2d.setColor(new Color(shade, shade, shade, ((topOpacity / pixels) * i)));
			g2d.drawRoundRect(i, i, this.getWidth() - ((i * 2) + 1), this.getHeight() - ((i * 2) + 1), 0, 0);
		}

		g2d.setColor(getBackground());
		
		g2d.fillRect(pixels, pixels, getWidth()-pixels*2, getHeight()-pixels*2);
//		g2d.translate(pixels, pixels);
//		double wScale = pixels/this.WIDTH;
//		double hScale = pixels/this.HEIGHT;
//		g2d.scale(hScale, wScale);
		super.paint(g2d);
	}
	
}
