package de.himalaya.gui.components;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JCheckBox;

public class HCheckBox extends JCheckBox {

	private static final long serialVersionUID = 1270634958198954267L;

	private int pixels = 3;

	private boolean pressed = false;
	private boolean entered = false;
	
	public Color fillcolor = Color.black;
	
	public HCheckBox() {
		this.setOpaque(true);
		this.setContentAreaFilled(false);
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

		int shade = 0;
		int topOpacity = 60;
		for (int i = 0; i < pixels; i++) {
			g2d.setColor(new Color(shade, shade, shade, ((topOpacity / pixels) * i)));
			g2d.drawRoundRect(i+2, i+2, this.getHeight()-4 - ((i * 2) + 1), this.getHeight()-4 - ((i * 2) + 1), 2, 2);
		}

		g2d.setColor(getBackground());
		g2d.fillRect(pixels+2, pixels+2, getHeight()-pixels*2-4, getHeight()-pixels*2-4);
		g2d.setColor(fillcolor);
		if(isSelected())
		g2d.fillRoundRect(pixels+4, pixels+4, getHeight()-pixels*2-8, getHeight()-pixels*2-8, 4, 4);
		g2d.drawString(getText(), getHeight()+2, (int) getHeight()-g2d.getFontMetrics(g2d.getFont()).getHeight()/2);
	}

	public Color getFillcolor() {
		return fillcolor;
	}

	public void setFillcolor(Color fillcolor) {
		this.fillcolor = fillcolor;
	}
}
