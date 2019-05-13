package de.himalaya.gui.components;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class HTextField extends JTextField {

	private static final long serialVersionUID = 2501325673064343569L;

	private int pixels = 3;
	
	private boolean focus = false;
	
	public Color glowColor = Color.BLUE;

	public Color getGlowColor() {
		return glowColor;
	}

	public void setGlowColor(Color glowColor) {
		this.glowColor = glowColor;
	}

	public HTextField() {
		this.setOpaque(true);
		this.setBorder(new EmptyBorder(5, 5, 5, 5));
		
		this.addFocusListener(new FocusListener() {
			
			@Override
			public void focusLost(FocusEvent e) {
				focus = false;
				repaint();
			}
			
			@Override
			public void focusGained(FocusEvent e) {
				focus = true;
				repaint();
			}
		});
	}
	
	@Override
	public void paint(Graphics g) {
		Graphics2D g2d = (Graphics2D)g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		int shade = 0;
		int topOpacity = 60;
		for (int i = 0; i < pixels; i++) {
			g2d.setColor(new Color(focus?glowColor.getRed():shade, focus?glowColor.getGreen():shade, focus?glowColor.getBlue():shade, ((topOpacity / pixels) * i)));
			g2d.drawRoundRect(i, i, this.getWidth() - ((i * 2) + 1), this.getHeight() - ((i * 2) + 1), 2, 2);
		}

		g2d.setColor(getBackground());
		g2d.fillRect(pixels, pixels, getWidth()-pixels*2, getHeight()-pixels*2);

		super.paint(g2d);
		
	}
}
