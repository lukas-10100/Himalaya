package de.himalaya.gui.rendering;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

import de.himalaya.data.Account;

public class AccountListCellRenderer extends JLabel implements ListCellRenderer<Account>{

	private static final long serialVersionUID = -3345201971616641123L;

	public AccountListCellRenderer() {
		this.setOpaque(true);
	}
	
	@Override
	public Component getListCellRendererComponent(JList<? extends Account> list, Account value, int index,
			boolean isSelected, boolean cellHasFocus) {
		
		this.setText(value.getKundennummer() + " [" + value.getName() + "; " + value.getEmail() + "]");
		
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
