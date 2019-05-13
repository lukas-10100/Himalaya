package de.himalaya.gui.components;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JButton;
import javax.swing.border.EmptyBorder;

public class HButton extends JButton {

	private static final long serialVersionUID = -3333778430762431305L;

	private int pixels = 3;

	private boolean pressed = false;
	private boolean entered = false;

	public Color shadowColor = new Color(0, 0, 0);
	
	public Color getShadowColor() {
		return shadowColor;
	}

	public void setShadowColor(Color shadowColor) {
		this.shadowColor = shadowColor;
	}

	public HButton() {
		this.setOpaque(true);
		this.setContentAreaFilled(false);
		this.setBorder(new EmptyBorder(8, 11, 8, 11));
		this.setFocusPainted(false);

		addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {
				pressed = false;
				if(entered) {
					pixels = 4;
				}else {
					pixels = 3;
				}
			}

			@Override
			public void mousePressed(MouseEvent e) {
				pressed = true;
			}

			@Override
			public void mouseExited(MouseEvent e) {
				if(!pressed)
					pixels = 3;
				entered = false;
			}

			@Override
			public void mouseEntered(MouseEvent e) { 
				entered = true;
			}

			@Override
			public void mouseClicked(MouseEvent e) { }
		});

		addMouseMotionListener(new MouseMotionListener() {

			@Override
			public void mouseMoved(MouseEvent e) {
				pixels = 4;
			}

			@Override
			public void mouseDragged(MouseEvent e) { }
		});
	}

	@Override
	public void paint(Graphics g) {
		Graphics2D g2d = (Graphics2D)g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		int topOpacity = 90;
		Color b = getShadowColor();
		if(isSelected()) {
			b=darken(b, 40);
		}
		for (int i = 0; i < pixels; i++) {
			g2d.setColor(new Color(b.getRed(), b.getGreen(), b.getBlue(), ((topOpacity / pixels) * i)));
			g2d.drawRoundRect(i, i, this.getWidth() - ((i * 2) + 1), this.getHeight() - ((i * 2) + 1), 0, 0);
		}

		g2d.setColor(getBackground());
		if(pressed || isSelected()) {
			g2d.setColor(darken(getBackground(), isSelected()?40:20));
		}
		g2d.fillRect(pixels, pixels, getWidth()-pixels*2, getHeight()-pixels*2);

		super.paint(g2d);
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
