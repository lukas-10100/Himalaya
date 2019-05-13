package de.himalaya.gui.dialog;

import java.awt.Color;
import java.awt.Dialog.ModalExclusionType;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

import de.himalaya.data.Address;
import de.himalaya.gui.components.HButton;
import de.himalaya.gui.components.HTextField;
import net.miginfocom.swing.MigLayout;

public class EditAddressDialog extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2006192307695854530L;
	private JPanel contentPane;
	

	/**
	 * Launch the application.
	 */
	public static void main(Address addre, JTextField comp) {
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (Throwable e) {
			e.printStackTrace();
		}
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					EditAddressDialog frame = new EditAddressDialog(addre, comp);
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
	public EditAddressDialog(Address addr, JTextField com) {
		setType(Type.POPUP);
		setModalExclusionType(ModalExclusionType.APPLICATION_EXCLUDE);
		setTitle("Adresse bearbeiten");
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new MigLayout("", "[][grow]", "[][][][][][][][][]"));
		
		JLabel lblAdresszeile = new JLabel("Adresszeile 1:");
		contentPane.add(lblAdresszeile, "cell 0 0,alignx trailing");
		
		HTextField txtfldLine = new HTextField();
		txtfldLine.setText(addr.getLine1());
		txtfldLine.setOpaque(false);
		contentPane.add(txtfldLine, "cell 1 0,growx");
		
		JLabel lblAdresszeile_1 = new JLabel("Adresszeile 2:");
		contentPane.add(lblAdresszeile_1, "cell 0 1,alignx trailing");
		
		HTextField txtfldLine_1 = new HTextField();
		txtfldLine_1.setText(addr.getLine2());
		txtfldLine_1.setOpaque(false);
		contentPane.add(txtfldLine_1, "cell 1 1,growx");
		
		JLabel lblAdresszeile_2 = new JLabel("Adresszeile 3:");
		contentPane.add(lblAdresszeile_2, "cell 0 2,alignx trailing");
		
		HTextField txtfldLine_2 = new HTextField();
		txtfldLine_2.setText(addr.getLine3());
		txtfldLine_2.setOpaque(false);
		contentPane.add(txtfldLine_2, "cell 1 2,growx");
		
		JLabel lblStadt = new JLabel("Stadt:");
		contentPane.add(lblStadt, "cell 0 3,alignx trailing");
		
		HTextField txtfldCity = new HTextField();
		txtfldCity.setText(addr.getCity());
		txtfldCity.setOpaque(false);
		contentPane.add(txtfldCity, "cell 1 3,growx");
		
		JLabel lblStaat = new JLabel("Staat:");
		contentPane.add(lblStaat, "cell 0 4,alignx trailing");
		
		HTextField txtfldState = new HTextField();
		txtfldState.setText(addr.getState());
		txtfldState.setOpaque(false);
		contentPane.add(txtfldState, "cell 1 4,growx");
		
		JLabel lblPostleitzahl = new JLabel("Postleitzahl:");
		contentPane.add(lblPostleitzahl, "cell 0 5,alignx trailing");
		
		HTextField txtfldPostcode = new HTextField();
		txtfldPostcode.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				int caret = txtfldPostcode.getCaretPosition();
				txtfldPostcode.setText(extract(txtfldPostcode.getText(), "0123456789-"));
				txtfldPostcode.setCaretPosition(caret);
			}
		});
		txtfldPostcode.setText(String.valueOf(addr.getPostalCode()));
		txtfldPostcode.setOpaque(false);
		contentPane.add(txtfldPostcode, "cell 1 5,growx");
		
		JLabel lblGeburtsdatum = new JLabel("Geburtsdatum:");
		contentPane.add(lblGeburtsdatum, "cell 0 6,alignx trailing");
		
		JComboBox<Integer> cbxTag = new JComboBox<Integer>();
		cbxTag.setModel(new DefaultComboBoxModel<Integer>(new Integer[] {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31}));
		cbxTag.setSelectedIndex(addr.getInstant().atZone(ZoneOffset.UTC).getDayOfMonth()-1);
		contentPane.add(cbxTag, "flowx,cell 1 6,alignx left");
		
		JLabel lblTelefonnummer = new JLabel("Telefonnummer:");
		contentPane.add(lblTelefonnummer, "cell 0 7,alignx trailing");
		
		HTextField txtfldPhone = new HTextField();
		txtfldPhone.setText(addr.getPhone());
		txtfldPhone.setOpaque(false);
		contentPane.add(txtfldPhone, "cell 1 7,growx");
		
		JComboBox<String> cbxMonat = new JComboBox<String>();
		cbxMonat.setModel(new DefaultComboBoxModel<String>(new String[] {"Januar", "Februar", "M\u00E4rz", "April", "Mai", "Juni", "Juli", "August", "September", "Oktober", "November", "Dezember"}));
		cbxMonat.setSelectedIndex(addr.getInstant().atZone(ZoneOffset.UTC).getMonthValue()-1);
		contentPane.add(cbxMonat, "cell 1 6");
		
		JComboBox<Integer> cbxJahr = new JComboBox<Integer>();
		cbxJahr.setModel(new DefaultComboBoxModel<Integer>(new Integer[] {2003, 2002, 2001, 2000, 1999, 1998, 1997, 1996, 1995, 1994, 1993, 1992, 1991, 1990, 1989, 1988, 1987, 1986, 1985, 1984, 1983, 1982, 1981, 1980, 1979, 1978, 1977, 1976, 1975, 1974, 1973, 1972, 1971, 1970, 1901, 1900}));
		cbxJahr.setSelectedItem(addr.getInstant().atZone(ZoneOffset.UTC).getYear());
		contentPane.add(cbxJahr, "cell 1 6");
		
		HButton button = new HButton();
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		button.setText("Abbrechen");
		button.shadowColor = new Color(0, 100, 0);
		button.setShadowColor(new Color(139, 0, 0));
		button.setBackground(Color.WHITE);
		contentPane.add(button, "flowx,cell 1 8,alignx right");
		
		HButton button_1 = new HButton();
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addr.setCity(txtfldCity.getText().trim());
				addr.setDateOfBirth(LocalDateTime.of((Integer)cbxJahr.getSelectedItem(), cbxMonat.getSelectedIndex()+1, (Integer)cbxTag.getSelectedItem(), 0, 0).toInstant(ZoneOffset.UTC));
				addr.setLine1(txtfldLine.getText().trim());
				addr.setLine2(txtfldLine_2.getText().trim());
				addr.setLine3(txtfldLine_2.getText().trim());
				addr.setPhone(txtfldPhone.getText().trim());
				addr.setPostalCode(Integer.parseInt(txtfldPostcode.getText().trim()));
				addr.setState(txtfldState.getText().trim());
				com.setText(addr.toString());
				dispose();
			}
		});
		button_1.setText("Speichern");
		button_1.shadowColor = new Color(0, 100, 0);
		button_1.setShadowColor(new Color(0, 100, 0));
		button_1.setBackground(Color.WHITE);
		contentPane.add(button_1, "cell 1 8,alignx right");
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
