package de.himalaya.gui.components;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.IOException;
import java.net.MalformedURLException;

import javax.swing.JComponent;

import de.himalaya.data.Product;

public class JProductView extends JComponent{

	private static final long serialVersionUID = -1629011013048884042L;

	public int radius = 5;
	private Product product;

	private BufferedImage image = null;

	public int getRadius() {
		return radius;
	}

	public void setRadius(int radius) {
		this.radius = radius;
	}

	public JProductView(Product product1) {
		this.setProduct(product1);
		repaint();
		BufferedImage oldImg = image;
		image = null;
		Thread loader = new Thread(new Runnable() {

			@Override
			public void run() {
				int oldArtnr = product.getArtikelnummer();
				int oldID = product.getId();
				try {
					image = product.getImage();
					if(product.getArtikelnummer()!=oldArtnr && product.getId()!=oldID) {
						image = oldImg;
					}
				} catch (MalformedURLException e) { } catch (IOException e) { }
				repaint();
			}
		});
		loader.start();
	}

	@Override
	public void setBounds(int x, int y, int width, int height) {
		super.setBounds(x, y, 200, 260);
	}

	@Override
	public void paint(Graphics g) {
		Graphics2D g2d = (Graphics2D)g;
		ImageObserver obs = this;
		//		Thread imgDraw = new Thread(new Runnable() {
		//
		//
		//
		//			@Override
		//			public void run() {

		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		g2d.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
		g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

		g2d.setColor(getBackground());
		g2d.fillRoundRect(0, 0, getWidth(), getHeight(), getRadius(), getRadius());
		g2d.setColor(darken(getBackground(), 70));
		g2d.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, getRadius(), getRadius());


		//Bild

		if(image!=null) {
			BufferedImage img = image;
			int imgW = img.getWidth();
			int imgH = img.getHeight();
			int ratio = imgW/imgH;
			int contH = 100;
			int contW = ratio*contH;
			g2d.drawImage(img.getScaledInstance(contW, contH, 50), getWidth()/2-contW/2, 2, contW, contH, obs);
		} else {
			g2d.setColor(Color.RED);
			g2d.drawString("Fehler:", getWidth()/2-g2d.getFontMetrics().stringWidth("Fehler:")/2, g2d.getFontMetrics().getHeight()+10);
			g2d.drawString("Bild nicht verfügbar",getWidth()/2-g2d.getFontMetrics().stringWidth("Bild nicht verfügbar")/2, g2d.getFontMetrics().getHeight()*2+12);
		}

		g2d.setColor(darken(getBackground(), 70));
		g2d.drawLine(8, 104, getWidth()-8, 104);

		int lineH = g2d.getFontMetrics().getHeight();
		g2d.setColor(product.isBestand()?new Color(0, 100, 0):new Color(200, 0, 0));
		g2d.setFont(new Font(getFont().getFontName(), Font.BOLD, 14));
		g2d.drawString(getProduct().getName(), getWidth()/2-g2d.getFontMetrics().stringWidth(getProduct().getName())/2, g2d.getFontMetrics().getHeight()+114);
		lineH = g2d.getFontMetrics().getHeight();
		g2d.setColor(getForeground());
		g2d.setFont(new Font(getFont().getFontName(), Font.ITALIC, 10));
		g2d.drawString(getProduct().getArt(), getWidth()/2-g2d.getFontMetrics().stringWidth(getProduct().getArt())/2, g2d.getFontMetrics().getHeight()+lineH+114);

		g2d.setFont(new Font(getFont().getFontName(), Font.BOLD, 12));
		g2d.drawString("Preis:", 20, g2d.getFontMetrics().getHeight()+150);
		g2d.setFont(new Font(getFont().getFontName(), Font.PLAIN, 12));
		g2d.drawString("€ "+String.format("%1$,.2f", getProduct().getPreis()), 30, g2d.getFontMetrics().getHeight()+165);
		g2d.setFont(new Font(getFont().getFontName(), Font.BOLD, 12));
		g2d.drawString("Bezeichnung:", 20, g2d.getFontMetrics().getHeight()+180);
		g2d.setFont(new Font(getFont().getFontName(), Font.PLAIN, 12));
		g2d.drawString(getProduct().getBezeichnung(), 30, g2d.getFontMetrics().getHeight()+195);
		g2d.setFont(new Font(getFont().getFontName(), Font.BOLD, 12));
		g2d.drawString("Gewicht:", 20, g2d.getFontMetrics().getHeight()+210);
		g2d.setFont(new Font(getFont().getFontName(), Font.PLAIN, 12));
		g2d.drawString(String.format("%1$,.2f", getProduct().getGewicht()*0.001)+" kg", 30, g2d.getFontMetrics().getHeight()+225);

		//
		////				super.paint(g2d);
		//			}
		//		});
		//		imgDraw.start();

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

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product1) {
		this.product = product1;
		repaint();
		BufferedImage oldImg = image;
		image = null;
		Thread loader = new Thread(new Runnable() {

			@Override
			public void run() {
				int oldArtnr = product.getArtikelnummer();
				int oldID = product.getId();
				try {
					image = product.getImage();
					if(product.getArtikelnummer()!=oldArtnr && product.getId()!=oldID) {
						image = oldImg;
					}
				} catch (MalformedURLException e) { } catch (IOException e) { }
				repaint();
			}
		});
		loader.start();
	}
}
