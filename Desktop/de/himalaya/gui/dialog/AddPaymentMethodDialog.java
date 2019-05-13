package de.himalaya.gui.dialog;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dialog.ModalExclusionType;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

import de.himalaya.data.PaymentMethod;
import de.himalaya.gui.components.HButton;
import de.himalaya.gui.components.HTextField;
import net.miginfocom.swing.MigLayout;

public class AddPaymentMethodDialog extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 320758406157593337L;
	private JPanel contentPane;


	/**
	 * Launch the application.
	 */
	public static void main(DefaultListModel<PaymentMethod> toAdd) {
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (Throwable e) {
			e.printStackTrace();
		}
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AddPaymentMethodDialog frame = new AddPaymentMethodDialog(toAdd);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public AddPaymentMethodDialog(DefaultListModel<PaymentMethod> toAdd) {
		setModalExclusionType(ModalExclusionType.APPLICATION_EXCLUDE);
		setType(Type.POPUP);
		setTitle("Neue Zahlungsmethode");
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 549, 127);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblNeuesProdukt = new JLabel("Neue");
		lblNeuesProdukt.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblNeuesProdukt.setBounds(10, 11, 184, 22);
		contentPane.add(lblNeuesProdukt);

		JPanel panel = new JPanel();
		panel.setBackground(Color.WHITE);
		panel.setBounds(160, 11, 372, 38);
		contentPane.add(panel);
		panel.setLayout(new MigLayout("", "[][][grow]", "[]"));

		JLabel lblKundennummer = new JLabel("Name:");
		panel.add(lblKundennummer, "cell 0 0,alignx right");

		HTextField txtfldName = new HTextField();

		Component horizontalStrut = Box.createHorizontalStrut(20);
		panel.add(horizontalStrut, "cell 1 0");
		txtfldName.setOpaque(false);
		panel.add(txtfldName, "cell 2 0,growx");

		HButton button = new HButton();
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				boolean isEmpty = false;
				isEmpty = txtfldName.getText().isEmpty();
				
				if(!isEmpty) {
					toAdd.addElement(new PaymentMethod(txtfldName.getText()));
					dispose();
				}
			}
		});
		button.setText("Speichern");
		button.shadowColor = new Color(0, 100, 0);
		button.setShadowColor(new Color(0, 100, 0));
		button.setBackground(Color.WHITE);
		button.setBounds(463, 56, 69, 30);
		contentPane.add(button);

		HButton btnAbbrechen = new HButton();
		btnAbbrechen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});
		btnAbbrechen.setText("Abbrechen");
		btnAbbrechen.shadowColor = new Color(0, 100, 0);
		btnAbbrechen.setShadowColor(new Color(139, 0, 0));
		btnAbbrechen.setBackground(Color.WHITE);
		btnAbbrechen.setBounds(384, 56, 79, 30);
		contentPane.add(btnAbbrechen);

		JLabel lblZahlungsmethode = new JLabel("Zahlungsmethode");
		lblZahlungsmethode.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblZahlungsmethode.setBounds(10, 30, 184, 22);
		contentPane.add(lblZahlungsmethode);
	}
	public static String extract(String in, String set) {
		char[] chars = in.toCharArray();
		for(int i = 0; i < chars.length; i++) {
			if(!set.contains(String.valueOf(chars[i]))) {
				chars[i] = ' ';
			}
		}
		String nstr = new String(chars).replace(" ", "");
		return nstr;
	}
}
