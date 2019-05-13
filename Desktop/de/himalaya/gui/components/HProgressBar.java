package de.himalaya.gui.components;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JComponent;

public class HProgressBar extends JComponent {

	private static final long serialVersionUID = 5057336607653771264L;

	private int pixels = 3;

	public HProgressBar() {
		this.setSize(20, 100);
	}

	@Override
	public void paint(Graphics g) {
		Graphics2D g2d = (Graphics2D)g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		int topOpacity = 90;
		int shade = 0;
		for (int i = 0; i < pixels; i++) {
			g2d.setColor(new Color(shade, shade, shade, ((topOpacity / pixels) * i)));
			g2d.drawRoundRect(i, i, this.getWidth() - ((i * 2) + 1), this.getHeight() - ((i * 2) + 1), 0, 0);
		}

		g2d.setColor(getBackground());
		g2d.fillRect(pixels, pixels, getWidth()-pixels*2, getHeight()-pixels*2);
		GradientPaint gpGray = new GradientPaint(0, 0, Color.BLACK, getWidth(), 0, getBackground());
		g2d.setPaint(gpGray);
		g2d.fillRect(getWidth()/2-pixels-2, pixels+1, 4, getHeight()-2-pixels*2);
		GradientPaint gp = new GradientPaint(0, 0, getForeground(), getWidth()/2, getHeight(), darken(getForeground(), -20));
		g2d.setPaint(gp);
		g2d.fillRect(pixels+1, pixels+1, getWidth()/2-pixels*2-2, getHeight()-2-pixels*2);

	}
	
	public Color darken(Color c, int strength) {
		int r = c.getRed()-strength;
		int g = c.getGreen()-strength;
		int b = c.getBlue()-strength;
		if(r<0) {
			r=0;
		}
		if(r>255) {
			r=255;
		}

		if(g<0) {
			g=0;
		}
		if(g>255) {
			g=255;
		}

		if(b<0) {
			b=0;
		}
		if(b>255) {
			b=255;
		}
		return new Color(r,g,b,c.getAlpha());
	}

}
