package de.himalaya.gui.rendering;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import javax.swing.border.LineBorder;

import de.himalaya.data.Product;

public class ProductListCellRenderer extends JLabel implements ListCellRenderer<Product>{

	private static final long serialVersionUID = -3710437328753803891L;
	
	public ProductListCellRenderer() {
		this.setOpaque(true);
	}

	@Override
	public Component getListCellRendererComponent(JList<? extends Product> list, Product value, int index, boolean isSelected, boolean cellHasFocus) {

		this.setText(value.getArtikelnummer() + " [" + value.getName() + " (" + value.getArt() + ")]");
			
		Color selectedInStock = new Color(200, 250, 200);
		Color selectedOutOfStock = new Color(250, 200, 200);
		
		this.setForeground(list.getForeground());
		
		if(isSelected || cellHasFocus) {
			this.setBackground(value.isBestand()?darkenG(selectedInStock, 60):darkenR(selectedOutOfStock, 60));
			this.setBorder(new LineBorder(new Color(60, 60, 60)));
		}else {
			this.setBackground(value.isBestand()?selectedInStock:selectedOutOfStock);
			this.setBorder(new LineBorder(value.isBestand()?selectedInStock:selectedOutOfStock));
		}
		
		return this;
	}
	
	public Color darkenR(Color c, int strength) {
		int r = c.getRed()-strength;
		int g = c.getGreen()-strength*2;
		int b = c.getBlue()-strength*2;
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
	public Color darkenG(Color c, int strength) {
		int r = c.getRed()-strength*2;
		int g = c.getGreen()-strength;
		int b = c.getBlue()-strength*2;
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
