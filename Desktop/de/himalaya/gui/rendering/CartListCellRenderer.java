package de.himalaya.gui.rendering;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

import de.himalaya.data.Cart;

public class CartListCellRenderer extends JLabel implements ListCellRenderer<Cart>{

	private static final long serialVersionUID = -3345201971616641123L;

	public CartListCellRenderer() {
		this.setOpaque(true);
	}
	
	@Override
	public Component getListCellRendererComponent(JList<? extends Cart> list, Cart value, int index,
			boolean isSelected, boolean cellHasFocus) {
		
		this.setText(value.getKundennummer() + " [" + value.getProdukte().size() + "]");
		
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
