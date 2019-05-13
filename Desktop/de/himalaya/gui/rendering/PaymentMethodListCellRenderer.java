package de.himalaya.gui.rendering;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

import de.himalaya.data.PaymentMethod;

public class PaymentMethodListCellRenderer extends JLabel implements ListCellRenderer<PaymentMethod>{

	private static final long serialVersionUID = -3345201971616641123L;

	public PaymentMethodListCellRenderer() {
		this.setOpaque(true);
	}
	
	@Override
	public Component getListCellRendererComponent(JList<? extends PaymentMethod> list, PaymentMethod value, int index,
			boolean isSelected, boolean cellHasFocus) {
		
		this.setText(value.getName() + " [" + "AKTIV" + "]");
		
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
