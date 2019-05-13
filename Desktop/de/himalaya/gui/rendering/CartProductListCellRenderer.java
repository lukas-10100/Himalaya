package de.himalaya.gui.rendering;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

import de.himalaya.data.CartProduct;

public class CartProductListCellRenderer extends JLabel implements ListCellRenderer<CartProduct>{

	private static final long serialVersionUID = -3710437328753803891L;

	public CartProductListCellRenderer() {
		this.setOpaque(true);
	}

	@Override
	public Component getListCellRendererComponent(JList<? extends CartProduct> list, CartProduct value, int index, boolean isSelected, boolean cellHasFocus) {

		this.setText(value.getArtikelnummer() + " [" + value.getName() + " x" + value.getMenge() + "]");

		Color selected = new Color(153, 255, 230);

		this.setForeground(list.getForeground());

		if(isSelected || cellHasFocus) {
			this.setBackground(selected);
		}else {
			this.setBackground(list.getBackground());
		}

		return this;
	}
}