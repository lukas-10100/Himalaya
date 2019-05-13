package de.himalaya.gui.dialog;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dialog.ModalExclusionType;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;

import javax.swing.Box;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

import org.json.JSONArray;
import org.json.JSONObject;

import de.himalaya.api.HimalayaAPI.Aktion;
import de.himalaya.api.HimalayaAPI.Modul;
import de.himalaya.data.Account;
import de.himalaya.data.Address;
import de.himalaya.data.Converter;
import de.himalaya.data.Country;
import de.himalaya.data.ImportExport;
import de.himalaya.data.PaymentMethod;
import de.himalaya.gui.MainGUI;
import de.himalaya.gui.components.HButton;
import de.himalaya.gui.components.HList;
import de.himalaya.gui.components.HTextField;
import de.himalaya.gui.rendering.PaymentMethodListCellRenderer;
import net.miginfocom.swing.MigLayout;

public class AddAccountDialog extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 320758406157593337L;
	private JPanel contentPane;
	private Address address;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (Throwable e) {
			e.printStackTrace();
		}
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AddAccountDialog frame = new AddAccountDialog();
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
	public AddAccountDialog() {
		address = new Address();
		setModalExclusionType(ModalExclusionType.APPLICATION_EXCLUDE);
		setType(Type.POPUP);
		setTitle("Neuer Account");
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 635, 446);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNeuesProdukt = new JLabel("Neuer Account");
		lblNeuesProdukt.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblNeuesProdukt.setBounds(10, 11, 140, 22);
		contentPane.add(lblNeuesProdukt);
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.WHITE);
		panel.setBounds(160, 11, 459, 354);
		contentPane.add(panel);
		panel.setLayout(new MigLayout("", "[][][grow]", "[][][][][][][][]"));
		
		JLabel label = new JLabel("ID:");
		panel.add(label, "cell 0 0,alignx right");
		
		Component horizontalStrut = Box.createHorizontalStrut(20);
		panel.add(horizontalStrut, "cell 1 0");
		
		HTextField textField_1 = new HTextField();
		textField_1.setText("<automatisch>");
		textField_1.setOpaque(false);
		textField_1.setEditable(false);
		panel.add(textField_1, "cell 2 0,growx");
		
		JLabel lblKundennummer = new JLabel("Kundennummer:");
		panel.add(lblKundennummer, "cell 0 1,alignx right");
		
		HTextField txtfldKundennummer = new HTextField();
		txtfldKundennummer.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				int caret = txtfldKundennummer.getCaretPosition();
				txtfldKundennummer.setText(extract(txtfldKundennummer.getText(), "0123456789"));
				txtfldKundennummer.setCaretPosition(caret);
			}
		});
		txtfldKundennummer.setOpaque(false);
		panel.add(txtfldKundennummer, "cell 2 1,growx");
		
		JLabel lblName = new JLabel("Name:");
		panel.add(lblName, "cell 0 2,alignx right");
		
		HTextField txtfldName = new HTextField();
		txtfldName.setOpaque(false);
		panel.add(txtfldName, "cell 2 2,growx");
		
		JLabel lblEmail = new JLabel("E-Mail:");
		panel.add(lblEmail, "cell 0 3,alignx right");
		
		HTextField txtfldEMail = new HTextField();
		txtfldEMail.setOpaque(false);
		panel.add(txtfldEMail, "cell 2 3,growx");
		
		JLabel lblPasswort = new JLabel("Passwort:");
		panel.add(lblPasswort, "cell 0 4,alignx right");
		
		HTextField txtfldPasswort = new HTextField();
		txtfldPasswort.setOpaque(false);
		panel.add(txtfldPasswort, "cell 2 4,growx");
		
		JLabel lblZahlungsmethoden = new JLabel("Zahlungsmethoden:");
		panel.add(lblZahlungsmethoden, "cell 0 5,alignx right,aligny top");
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(Color.WHITE);
		panel.add(panel_1, "cell 2 5,grow");
		panel_1.setLayout(new MigLayout("", "[grow][]", "[][]"));
		
		JScrollPane scrollPane = new JScrollPane();
		panel_1.add(scrollPane, "cell 0 0 1 2,grow");
		
		HList<PaymentMethod> list_PaymentMethods = new HList<PaymentMethod>();
		list_PaymentMethods.setCellRenderer(new PaymentMethodListCellRenderer());
		list_PaymentMethods.setModel(new DefaultListModel<PaymentMethod>());
		scrollPane.setViewportView(list_PaymentMethods);
		
		HButton button_1 = new HButton();
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AddPaymentMethodDialog.main((DefaultListModel<PaymentMethod>) list_PaymentMethods.getModel());
			}
		});
		button_1.setText("+");
		button_1.setBackground(Color.WHITE);
		panel_1.add(button_1, "cell 1 0,growx");
		
		HButton button_2 = new HButton();
		button_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int ind = list_PaymentMethods.getSelectedIndex()-1;
				if(!list_PaymentMethods.isSelectionEmpty())
					((DefaultListModel<PaymentMethod>)list_PaymentMethods.getModel()).removeElementAt(list_PaymentMethods.getSelectedIndex());
				if(ind >= 0)
					list_PaymentMethods.setSelectedIndex(ind);
			}
		});
		button_2.setText("-");
		button_2.setBackground(Color.WHITE);
		panel_1.add(button_2, "cell 1 1,growx,aligny top");
		
		JLabel lblAdresse = new JLabel("Adresse:");
		panel.add(lblAdresse, "cell 0 6,alignx right");
		
		HTextField txtfldAdresse = new HTextField();
		txtfldAdresse.setEditable(false);
		
		txtfldAdresse.setOpaque(false);
		panel.add(txtfldAdresse, "flowx,cell 2 6,growx");
		
		JLabel lblLand = new JLabel("Land:");
		panel.add(lblLand, "cell 0 7,alignx right");
		
		JComboBox<Country> comboBox = new JComboBox<Country>();
		comboBox.setModel(new DefaultComboBoxModel<Country>(Country.values()));
		panel.add(comboBox, "cell 2 7,growx");
		
		HButton button_3 = new HButton();
		button_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				EditAddressDialog.main(address, txtfldAdresse);
			}
		});
		button_3.setBackground(Color.WHITE);
		button_3.setBorder(new EmptyBorder(5, 11, 5, 11));
		button_3.setText("...");
		panel.add(button_3, "cell 2 6");
		
		HButton button = new HButton();
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				JSONArray pm = new JSONArray();
				DefaultListModel<PaymentMethod> list = ((DefaultListModel<PaymentMethod>)list_PaymentMethods.getModel());
				for(int i = 0; i < list.getSize(); i++) {
					PaymentMethod pms = list.getElementAt(i);
					pm.put(Converter.toJson(pms));
				}
				
				
				
				String[] params = {txtfldKundennummer.getText(), txtfldName.getText(), txtfldEMail.getText(), txtfldPasswort.getText(), pm.toString(), Converter.toJson(address).toString(), ((Country)comboBox.getSelectedItem()).name()};
				
				try {
					String result = MainGUI.api.performRequest(Modul.NUTZER, Aktion.CREATE, params);
					JSONObject err = new JSONObject(result);
					if(err.getBoolean("error")) {
						JOptionPane.showMessageDialog(contentPane, err.getInt("code") + " - " + err.getString("description"), "Fehler", JOptionPane.ERROR_MESSAGE);
					}else {
						MainGUI.updateAccounts();
						String[] params2 = {txtfldKundennummer.getText(), new JSONArray().toString()};
						String result2 = MainGUI.api.performRequest(Modul.CART, Aktion.CREATE, params2);
						JSONObject err2 = new JSONObject(result2);
						if(err2.getBoolean("error")) {
							JOptionPane.showMessageDialog(contentPane, err2.getInt("code") + " - " + err2.getString("description"), "Fehler", JOptionPane.ERROR_MESSAGE);
						}else {
							JOptionPane.showMessageDialog(contentPane, "Der Account wurde eingetragen", "Erfolgreich", JOptionPane.INFORMATION_MESSAGE);
							MainGUI.updateCarts();
							dispose();
						}
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
				
			}
		});
		button.setText("Speichern");
		button.shadowColor = new Color(0, 100, 0);
		button.setShadowColor(new Color(0, 100, 0));
		button.setBackground(Color.WHITE);
		button.setBounds(550, 375, 69, 30);
		contentPane.add(button);
		
		HButton btnAbbrechen = new HButton();
		btnAbbrechen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnAbbrechen.setText("Abbrechen");
		btnAbbrechen.shadowColor = new Color(0, 100, 0);
		btnAbbrechen.setShadowColor(new Color(139, 0, 0));
		btnAbbrechen.setBackground(Color.WHITE);
		btnAbbrechen.setBounds(471, 375, 79, 30);
		contentPane.add(btnAbbrechen);
		
		HButton button_4 = new HButton();
		button_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser = new JFileChooser();
				chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
				chooser.setMultiSelectionEnabled(false);
				int a = chooser.showOpenDialog(contentPane);
				if(a==JFileChooser.APPROVE_OPTION) {
					File f;
					if((f=chooser.getSelectedFile())!=null) {
						try {
							Account ac = ImportExport.importAccount(f);
							txtfldAdresse.setText(ac.getAdresse().toString());
							address = ac.getAdresse();
							txtfldEMail.setText(ac.getEmail());
							txtfldKundennummer.setText(ac.getKundennummer()+"");
							comboBox.setSelectedItem(ac.getLand());
							txtfldName.setText(ac.getName());
							txtfldPasswort.setText(ac.getPasswort());
							((DefaultListModel<PaymentMethod>)list_PaymentMethods.getModel()).clear();
							for(PaymentMethod pm : ac.getZahlungsmethoden()) {
								((DefaultListModel<PaymentMethod>)list_PaymentMethods.getModel()).addElement(pm);
							}
							JOptionPane.showMessageDialog(contentPane, "Datensatz importiert", "Erfolgreich", JOptionPane.INFORMATION_MESSAGE);
						} catch (IOException e1) {
							e1.printStackTrace();
							JOptionPane.showMessageDialog(contentPane, "Laden fehlgeschlagen\n\n" + e1.getLocalizedMessage(), "Fehler", JOptionPane.ERROR_MESSAGE);
						}
					}else {
						JOptionPane.showMessageDialog(contentPane, "Ungültige Datei", "Fehler", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});
		button_4.setText("Importieren");
		button_4.setBackground(Color.WHITE);
		button_4.setBounds(10, 375, 78, 30);
		contentPane.add(button_4);
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
