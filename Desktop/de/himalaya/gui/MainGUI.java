package de.himalaya.gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
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
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import de.himalaya.api.HimalayaAPI;
import de.himalaya.api.HimalayaAPI.Aktion;
import de.himalaya.api.HimalayaAPI.Modul;
import de.himalaya.data.Account;
import de.himalaya.data.Address;
import de.himalaya.data.Cart;
import de.himalaya.data.CartProduct;
import de.himalaya.data.Converter;
import de.himalaya.data.Country;
import de.himalaya.data.ImportExport;
import de.himalaya.data.PaymentMethod;
import de.himalaya.data.Product;
import de.himalaya.data.Settings;
import de.himalaya.gui.components.HButton;
import de.himalaya.gui.components.HCheckBox;
import de.himalaya.gui.components.HList;
import de.himalaya.gui.components.HTextField;
import de.himalaya.gui.components.JProductView;
import de.himalaya.gui.dialog.AddAccountDialog;
import de.himalaya.gui.dialog.AddPaymentMethodDialog;
import de.himalaya.gui.dialog.AddProductDialog;
import de.himalaya.gui.dialog.EditAddressDialog;
import de.himalaya.gui.dialog.SettingsDialog;
import de.himalaya.gui.rendering.AccountListCellRenderer;
import de.himalaya.gui.rendering.CartListCellRenderer;
import de.himalaya.gui.rendering.CartProductListCellRenderer;
import de.himalaya.gui.rendering.PaymentMethodListCellRenderer;
import de.himalaya.gui.rendering.ProductListCellRenderer;
import net.miginfocom.swing.MigLayout;

public class MainGUI extends JFrame {

	private static final long serialVersionUID = -3746911785128884679L;
	private static JPanel contentPane;

	public static DefaultListModel<Product> productsModel = new DefaultListModel<>();
	public static DefaultListModel<Account> accountsModel = new DefaultListModel<>();
	public static DefaultListModel<Cart> cartsModel = new DefaultListModel<>();

	public static HimalayaAPI api = new HimalayaAPI("<key>");

	public static File settingsFile = new File(System.getProperty("user.home") + File.separator + "himalayaDesktop.settings");
	public static Settings settings = new Settings("gsEuxQas");

	private static HList<Product> list_Produkte;
	private HTextField txtfldId;
	private HTextField txtfldArtnr;
	private HTextField txtfldArt;
	private HTextField txtfldName;
	private HTextField txtfldPreis;
	private HCheckBox chkBestand;
	private HTextField txtfldBild;
	private HTextField txtfldBezeichung;
	private HTextField txtfldGewicht;
	private HButton btnProdukte;
	private HButton btnAccounts;
	private HButton btnWarenkrbe;
	private HButton btnEinstellungen;
	private JLabel lblTitle;
	private JPanel vpCarts;
	private JPanel vpAccounts;
	private JPanel vpProdukte;
	private static HList<Account> list_Accounts;
	private static HList<Cart> list_Carts;
	private HTextField txtfldId_1;
	private HTextField txtfldKundennummer;
	private HTextField txtfldName_1;
	private HTextField txtfldEmail;
	private HTextField txtfldPasswort;
	private HList<PaymentMethod> list_PaymentMethods;
	private HTextField txtfldAdresse;
	private JComboBox<Country> comboBox;
	private HList<CartProduct> list_CartContent;
	private Address newAddress;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {

		if(!settingsFile.exists()) {
			try {
				settingsFile.createNewFile();
				settings.setApiKey("<key>");
				settings.save(settingsFile);
				System.out.println("Einstellungen gespeichert");
			} catch (IOException | InvalidKeyException | NoSuchAlgorithmException | IllegalBlockSizeException | BadPaddingException | NoSuchPaddingException e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(null, "Erstellen der Einstellungsdatei fehlgeschlagen", "Fehler", JOptionPane.ERROR_MESSAGE);
			}
		}else {
			try {
				settings.load(settingsFile);
				api.setKey(settings.getApiKey());
				System.out.println("Einstellungen geladen");
			} catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException | IllegalBlockSizeException
					| BadPaddingException | ClassNotFoundException | IOException e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(null, "Laden der Einstellungsdatei fehlgeschlagen", "Fehler", JOptionPane.ERROR_MESSAGE);
			}
		}

		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (Throwable e) {
			e.printStackTrace();
		}
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainGUI frame = new MainGUI();
					frame.setVisible(true);

					updateProdukte();

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MainGUI() {
		setResizable(false);
		setTitle("Himalaya Desktop");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1141, 677);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(0, 0, 0, 0));
		setContentPane(contentPane);
		contentPane.setLayout(new MigLayout("", "[100px][grow]", "[][][][][][][][grow]"));

		JLabel lblKategorie = new JLabel("Kategorie");
		lblKategorie.setFont(new Font("Tahoma", Font.BOLD, 16));
		contentPane.add(lblKategorie, "cell 0 0,alignx center");

		lblTitle = new JLabel("Produkte");
		lblTitle.setFont(new Font("Tahoma", Font.BOLD, 16));
		contentPane.add(lblTitle, "cell 1 0,alignx center");

		JSeparator separator = new JSeparator();
		contentPane.add(separator, "cell 0 1");

		btnProdukte = new HButton();
		btnProdukte.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				btnProdukte.setSelected(true);
				btnAccounts.setSelected(false);
				btnWarenkrbe.setSelected(false);
				vpProdukte.setVisible(true);
				vpAccounts.setVisible(false);
				vpCarts.setVisible(false);
				lblTitle.setText("Produkte");
				updateProdukte();
			}
		});
		btnProdukte.setSelected(true);
		btnProdukte.setShadowColor(new Color(0, 139, 139));
		btnProdukte.setBackground(new Color(255, 255, 255));
		btnProdukte.setText("Produkte");
		contentPane.add(btnProdukte, "cell 0 2,growx");

		btnAccounts = new HButton();
		btnAccounts.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnProdukte.setSelected(false);
				btnAccounts.setSelected(true);
				btnWarenkrbe.setSelected(false);
				vpProdukte.setVisible(false);
				vpAccounts.setVisible(true);
				vpCarts.setVisible(false);
				lblTitle.setText("Accounts");
				updateAccounts();
			}
		});

		JPanel panel = new JPanel();
		contentPane.add(panel, "cell 1 2 1 6,grow");
		panel.setLayout(null);

		vpAccounts = new JPanel();
		vpAccounts.setVisible(false);
		vpAccounts.setBounds(0, 0, 1017, 604);
		panel.add(vpAccounts);
		vpAccounts.setBackground(Color.WHITE);
		vpAccounts.setLayout(null);

		JPanel panel_2 = new JPanel();
		panel_2.setBackground(Color.WHITE);
		panel_2.setBounds(231, 11, 543, 573);
		vpAccounts.add(panel_2);
		panel_2.setLayout(new MigLayout("", "[][][grow]", "[][][][][][][][][][][]"));

		JLabel lblId_1 = new JLabel("ID:");
		panel_2.add(lblId_1, "cell 0 0,alignx right");

		Component horizontalStrut_1 = Box.createHorizontalStrut(20);
		panel_2.add(horizontalStrut_1, "cell 1 0");

		txtfldId_1 = new HTextField();
		txtfldId_1.setText("0");
		txtfldId_1.setOpaque(false);
		txtfldId_1.setEditable(false);
		panel_2.add(txtfldId_1, "cell 2 0,growx");

		JLabel lblKundennummer = new JLabel("Kundennummer:");
		panel_2.add(lblKundennummer, "cell 0 1,alignx right");

		txtfldKundennummer = new HTextField();
		txtfldKundennummer.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				int caret = txtfldKundennummer.getCaretPosition();
				txtfldKundennummer.setText(extract(txtfldKundennummer.getText(), "0123456789"));
				txtfldKundennummer.setCaretPosition(caret);
			}
		});
		txtfldKundennummer.setOpaque(false);
		panel_2.add(txtfldKundennummer, "cell 2 1,growx");

		JLabel lblName_1 = new JLabel("Name:");
		panel_2.add(lblName_1, "cell 0 2,alignx right");

		txtfldName_1 = new HTextField();
		txtfldName_1.setOpaque(false);
		panel_2.add(txtfldName_1, "cell 2 2,growx");

		JLabel lblEmail = new JLabel("E-Mail:");
		panel_2.add(lblEmail, "cell 0 3,alignx right");

		txtfldEmail = new HTextField();
		txtfldEmail.setOpaque(false);
		panel_2.add(txtfldEmail, "cell 2 3,growx");

		JLabel lblPasswort = new JLabel("Passwort:");
		panel_2.add(lblPasswort, "cell 0 4,alignx right");

		txtfldPasswort = new HTextField();
		txtfldPasswort.setOpaque(false);
		panel_2.add(txtfldPasswort, "cell 2 4,growx");

		JLabel lblZahlungsmethoden = new JLabel("Zahlungsmethoden:");
		panel_2.add(lblZahlungsmethoden, "cell 0 5,alignx right");

		JPanel panel_3 = new JPanel();
		panel_3.setBackground(Color.WHITE);
		panel_2.add(panel_3, "cell 2 5 1 2,grow");
		panel_3.setLayout(new MigLayout("", "[grow][]", "[][]"));

		JScrollPane scrollPane_3 = new JScrollPane();
		panel_3.add(scrollPane_3, "cell 0 0 1 2,grow");

		list_PaymentMethods = new HList<PaymentMethod>();
		list_PaymentMethods.setCellRenderer(new PaymentMethodListCellRenderer());
		list_PaymentMethods.setModel(new DefaultListModel<PaymentMethod>());
		scrollPane_3.setViewportView(list_PaymentMethods);


		HButton button_4 = new HButton();
		button_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AddPaymentMethodDialog.main((DefaultListModel<PaymentMethod>) list_PaymentMethods.getModel());
			}
		});
		button_4.setBackground(Color.WHITE);
		panel_3.add(button_4, "cell 1 0,growx");
		button_4.setText("+");

		HButton button_5 = new HButton();
		button_5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int ind = list_PaymentMethods.getSelectedIndex()-1;
				if(!list_PaymentMethods.isSelectionEmpty())
					((DefaultListModel<PaymentMethod>)list_PaymentMethods.getModel()).removeElementAt(list_PaymentMethods.getSelectedIndex());
				if(ind >= 0)
					list_PaymentMethods.setSelectedIndex(ind);
			}
		});
		button_5.setBackground(Color.WHITE);
		panel_3.add(button_5, "cell 1 1,growx,aligny top");
		button_5.setText("-");

		JLabel lblAdressen = new JLabel("Adresse:");
		panel_2.add(lblAdressen, "cell 0 7,alignx right");

		txtfldAdresse = new HTextField();
		txtfldAdresse.setEditable(false);
		txtfldAdresse.setOpaque(false);
		panel_2.add(txtfldAdresse, "flowx,cell 2 7,growx");

		JLabel lblLand = new JLabel("Land:");
		panel_2.add(lblLand, "cell 0 8,alignx right");

		comboBox = new JComboBox<>();
		comboBox.setModel(new DefaultComboBoxModel<Country>(Country.values()));
		panel_2.add(comboBox, "cell 2 8,growx");

		HButton button_6 = new HButton();
		button_6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!list_Accounts.isSelectionEmpty()) {
					EditAddressDialog.main(newAddress, txtfldAdresse);
				}
			}
		});
		button_6.setText("...");
		button_6.setBorder(new EmptyBorder(5, 11, 5, 11));
		button_6.setBackground(Color.WHITE);
		panel_2.add(button_6, "cell 2 7");

		HButton button_2 = new HButton();
		button_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e1) {
				if(!list_Accounts.isSelectionEmpty()) {
					int oldCustomerID = list_Accounts.getSelectedValue().getKundennummer();
					JSONArray paymentMethods = new JSONArray();
					for(int i = 0; i < list_PaymentMethods.getModel().getSize(); i++) {
						paymentMethods.put(Converter.toJson(list_PaymentMethods.getModel().getElementAt(i)));
					}
					String param1 = "ID>" + txtfldId_1.getText().trim() + "|"
							+ "KUNDENNUMMER>" + txtfldKundennummer.getText().trim() + "|"
							+ "NAME>'" + txtfldName_1.getText().trim() + "'|"
							+ "EMAIL>'" + txtfldEmail.getText().trim() + "'|"
							+ "PASSWORT>'" + txtfldPasswort.getText().trim() + "'|"
							+ "ZAHLUNGSMETHODEN>'" + paymentMethods.toString() + "'|"
							+ "ADRESSE>'" + Converter.toJson(newAddress).toString() + "'|"
							+ "LAND>'" + ((Country)comboBox.getSelectedItem()).name().toUpperCase() + "'";
					String param2 = "WHERE `kundennummer`=" + oldCustomerID;
					try {
						String result = api.performRequest(Modul.NUTZER, Aktion.SET, param1, param2);
						JSONObject err = new JSONObject(result);
						if(err.getBoolean("error")) {
							JOptionPane.showMessageDialog(contentPane, err.getInt("code") + " - " + err.getString("description"), "Fehler", JOptionPane.ERROR_MESSAGE);
						}else {
							updateAccounts();
							String param3 = "KUNDENNUMMER>" + txtfldKundennummer.getText().trim();
							String result2 = api.performRequest(Modul.CART, Aktion.SET, param3, param2);
							JSONObject err2 = new JSONObject(result2);
							if(err2.getBoolean("error")) {
								JOptionPane.showMessageDialog(contentPane, err2.getInt("code") + " - " + err2.getString("description"), "Fehler", JOptionPane.ERROR_MESSAGE);
							}else {
								JOptionPane.showMessageDialog(contentPane, "Die Änderungen wurden gespeichert", "Erfolgreich", JOptionPane.INFORMATION_MESSAGE);
								updateCarts();
							}
						}
					} catch (IOException e) {
						e.printStackTrace();
						JOptionPane.showMessageDialog(contentPane, "Die Änderungen konnten nicht gespeichert werden", "Fehler", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});

		HButton button_8 = new HButton();
		button_8.addActionListener(new ActionListener() {
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
							newAddress = ac.getAdresse();
							txtfldEmail.setText(ac.getEmail());
							txtfldKundennummer.setText(ac.getKundennummer()+"");
							comboBox.setSelectedItem(ac.getLand());
							txtfldName_1.setText(ac.getName());
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
		button_8.setText("Importieren");
		button_8.setBackground(Color.WHITE);
		panel_2.add(button_8, "flowx,cell 2 10,alignx right");

		HButton button_7 = new HButton();
		button_7.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Account a = new Account(0, 0);
				a.setAdresse(newAddress);
				a.setEmail(txtfldEmail.getText().trim());
				a.setId(0);
				a.setKundennummer(Integer.parseInt(txtfldKundennummer.getText().trim()));
				a.setLand((Country)comboBox.getSelectedItem());
				a.setName(txtfldName_1.getText().trim());
				a.setPasswort(txtfldPasswort.getText().trim());
				List<PaymentMethod> zm = new ArrayList<>();
				for(int i = 0; i < ((DefaultListModel<PaymentMethod>)list_PaymentMethods.getModel()).size(); i++) {
					zm.add(((DefaultListModel<PaymentMethod>)list_PaymentMethods.getModel()).getElementAt(i));
				}
				a.setZahlungsmethoden(zm);
				JFileChooser chooser = new JFileChooser();
				chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
				chooser.setMultiSelectionEnabled(false);
				chooser.setSelectedFile(new File(a.getName()+"_"+txtfldId_1.getText()+"-"+a.getKundennummer()+".json"));
				int a1 = chooser.showSaveDialog(contentPane);
				if(a1==JFileChooser.APPROVE_OPTION) {
					File f;
					if((f=chooser.getSelectedFile())!=null) {
						try {
							ImportExport.export(a, f);
							JOptionPane.showMessageDialog(contentPane, "Datensatz exportiert", "Erfolgreich", JOptionPane.INFORMATION_MESSAGE);
						} catch (IOException e1) {
							JOptionPane.showMessageDialog(contentPane, "Speichern fehlgeschlagen\n\n" + e1.getLocalizedMessage(), "Fehler", JOptionPane.ERROR_MESSAGE);
							e1.printStackTrace();
						}
					}else {
						JOptionPane.showMessageDialog(contentPane, "Ungültige Datei", "Fehler", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});
		button_7.setText("Exportieren");
		button_7.setBackground(Color.WHITE);
		panel_2.add(button_7, "cell 2 10,alignx right");

		Component horizontalStrut_3 = Box.createHorizontalStrut(20);
		panel_2.add(horizontalStrut_3, "cell 2 10,alignx right");
		button_2.setText("Speichern");
		button_2.shadowColor = new Color(0, 100, 0);
		button_2.setShadowColor(new Color(0, 100, 0));
		button_2.setBackground(Color.WHITE);
		panel_2.add(button_2, "cell 2 10,alignx right");
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(10, 0, 199, 564);
		vpAccounts.add(scrollPane_1);

		list_Accounts = new HList<Account>();
		list_Accounts.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				if(!list_Accounts.isSelectionEmpty()) {
					Account acc = list_Accounts.getSelectedValue();
					txtfldId_1.setText(acc.getId()+"");
					txtfldKundennummer.setText(acc.getKundennummer()+"");
					txtfldName_1.setText(acc.getName());
					txtfldEmail.setText(acc.getEmail());
					txtfldPasswort.setText(acc.getPasswort());
					newAddress=acc.getAdresse();
					txtfldAdresse.setText(newAddress.toString());
					comboBox.setSelectedItem(acc.getLand());
					((DefaultListModel<PaymentMethod>)list_PaymentMethods.getModel()).clear();
					for(PaymentMethod m : acc.getZahlungsmethoden()) {
						((DefaultListModel<PaymentMethod>)list_PaymentMethods.getModel()).addElement(m);
					}
				}
			}
		});
		list_Accounts.setSelectedIndex(0);
		list_Accounts.setCellRenderer(new AccountListCellRenderer());
		scrollPane_1.setViewportView(list_Accounts);

		HButton button = new HButton();
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AddAccountDialog.main(null);
			}
		});
		button.setText("Hinzuf\u00FCgen");
		button.shadowColor = new Color(0, 0, 139);
		button.setShadowColor(new Color(0, 0, 139));
		button.setForeground(Color.BLACK);
		button.setBackground(Color.WHITE);
		button.setBounds(10, 566, 101, 27);
		vpAccounts.add(button);

		HButton button_1 = new HButton();
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!list_Accounts.isSelectionEmpty()) {
					int customerID = list_Accounts.getSelectedValue().getKundennummer();
					try {
						String result = api.performRequest(Modul.NUTZER, Aktion.DELETE, "`kundennummer`=" + customerID);
						JSONObject err = new JSONObject(result);
						if(err.getBoolean("error")) {
							JOptionPane.showMessageDialog(contentPane, err.getInt("code") + " - " + err.getString("description"), "Fehler", JOptionPane.ERROR_MESSAGE);
						}else {
							updateAccounts();
							String result2 = api.performRequest(Modul.CART, Aktion.DELETE, "`kundennummer`=" + customerID);
							JSONObject err2 = new JSONObject(result2);
							if(err2.getBoolean("error")) {
								JOptionPane.showMessageDialog(contentPane, err2.getInt("code") + " - " + err2.getString("description"), "Fehler", JOptionPane.ERROR_MESSAGE);
							}else {
								JOptionPane.showMessageDialog(contentPane, "Der Account wurde gelöscht", "Erfolgreich", JOptionPane.INFORMATION_MESSAGE);
								updateCarts();
							}
						}
					} catch (IOException e1) {
						e1.printStackTrace();
						JOptionPane.showMessageDialog(contentPane, "Der Account konnte nicht gelöscht werden", "Fehler", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});
		button_1.setText("L\u00F6schen");
		button_1.shadowColor = new Color(165, 42, 42);
		button_1.setShadowColor(new Color(165, 42, 42));
		button_1.setBackground(Color.WHITE);
		button_1.setBounds(108, 566, 101, 27);
		vpAccounts.add(button_1);

		vpProdukte = new JPanel();
		vpProdukte.setBounds(0, 0, 1017, 604);
		panel.add(vpProdukte);
		vpProdukte.setBackground(Color.WHITE);
		vpProdukte.setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 0, 199, 564);
		vpProdukte.add(scrollPane);


		JProductView productView = new JProductView(new Product(0, 0));

		list_Produkte = new HList<>();
		list_Produkte.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				if(!list_Produkte.isSelectionEmpty()) {
					Product p = list_Produkte.getSelectedValue();
					productView.setProduct(p);
					txtfldId.setText(p.getId()+"");
					txtfldArtnr.setText(p.getArtikelnummer()+"");
					txtfldArt.setText(p.getArt());
					txtfldName.setText(p.getName());
					txtfldPreis.setText((p.getPreis() + "").replace(".", ","));
					txtfldBild.setText(p.getBild());
					txtfldBezeichung.setText(p.getBezeichnung());
					txtfldGewicht.setText(p.getGewicht() + "");
					chkBestand.setSelected(p.isBestand());
				}
			}
		});
		list_Produkte.setCellRenderer(new ProductListCellRenderer());
		list_Produkte.setSelectedIndex(0);
		scrollPane.setViewportView(list_Produkte);


		JSeparator separator_1 = new JSeparator();
		separator_1.setOrientation(SwingConstants.VERTICAL);
		separator_1.setBounds(0, 0, 2, 592);
		vpProdukte.add(separator_1);

		//productView.setProduct(list.getSelectedValue());
		productView.setBounds(807, 10, 200, 50);
		vpProdukte.add(productView);

		JPanel panel_1 = new JPanel();
		panel_1.setBackground(new Color(255, 255, 255));
		panel_1.setBounds(232, 10, 543, 573);
		vpProdukte.add(panel_1);
		panel_1.setLayout(new MigLayout("", "[][][grow]", "[][][][][][][][][][][]"));

		JLabel lblId = new JLabel("ID:");
		panel_1.add(lblId, "cell 0 0,alignx right");

		txtfldId = new HTextField();
		txtfldId.setEditable(false);
		txtfldId.setOpaque(false);
		panel_1.add(txtfldId, "cell 2 0,growx");

		JLabel lblArtikelnummer = new JLabel("Artikelnummer:");
		panel_1.add(lblArtikelnummer, "cell 0 1,alignx right");

		txtfldArtnr = new HTextField();
		txtfldArtnr.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				int caret = txtfldArtnr.getCaretPosition();
				txtfldArtnr.setText(extract(txtfldArtnr.getText(), "0123456789"));
				txtfldArtnr.setCaretPosition(caret);
			}
		});
		txtfldArtnr.setOpaque(false);
		panel_1.add(txtfldArtnr, "cell 2 1,growx");

		JLabel lblArt = new JLabel("Art:");
		panel_1.add(lblArt, "cell 0 2,alignx right");

		txtfldArt = new HTextField();
		txtfldArt.setOpaque(false);
		panel_1.add(txtfldArt, "cell 2 2,growx");

		JLabel lblName = new JLabel("Name:");
		panel_1.add(lblName, "cell 0 3,alignx right");

		txtfldName = new HTextField();
		txtfldName.setOpaque(false);
		panel_1.add(txtfldName, "cell 2 3,growx");

		JLabel lblPreis = new JLabel("Preis:");
		panel_1.add(lblPreis, "cell 0 4,alignx right");

		txtfldPreis = new HTextField();
		txtfldPreis.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				int caret = txtfldPreis.getCaretPosition();
				txtfldPreis.setText(extract(txtfldPreis.getText(), "0123456789,."));
				txtfldPreis.setCaretPosition(caret);
			}
		});
		txtfldPreis.setOpaque(false);
		panel_1.add(txtfldPreis, "cell 2 4,growx");

		JLabel lblBestand = new JLabel("Bestand:");
		panel_1.add(lblBestand, "cell 0 5,alignx right");

		chkBestand = new HCheckBox();
		chkBestand.setFillcolor(new Color(0, 128, 0));
		chkBestand.setBackground(Color.WHITE);
		panel_1.add(chkBestand, "cell 2 5");

		JLabel lblBild = new JLabel("Bild:");
		panel_1.add(lblBild, "cell 0 6,alignx right");

		Component horizontalStrut = Box.createHorizontalStrut(20);
		panel_1.add(horizontalStrut, "cell 1 6");

		txtfldBild = new HTextField();
		txtfldBild.setOpaque(false);
		panel_1.add(txtfldBild, "cell 2 6,growx");

		JLabel lblBezeichnung = new JLabel("Bezeichnung:");
		panel_1.add(lblBezeichnung, "cell 0 7,alignx right");

		txtfldBezeichung = new HTextField();
		txtfldBezeichung.setOpaque(false);
		panel_1.add(txtfldBezeichung, "cell 2 7,growx");

		JLabel lblGewicht = new JLabel("Gewicht");
		panel_1.add(lblGewicht, "cell 0 8,alignx right");

		txtfldGewicht = new HTextField();
		txtfldGewicht.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				int caret = txtfldGewicht.getCaretPosition();
				txtfldGewicht.setText(extract(txtfldGewicht.getText(), "0123456789"));
				txtfldGewicht.setCaretPosition(caret);
			}
		});
		txtfldGewicht.setOpaque(false);
		panel_1.add(txtfldGewicht, "cell 2 8,growx");

		HButton btnSpeichern = new HButton();
		btnSpeichern.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(!list_Produkte.isSelectionEmpty()) {
					String param1 = "ID>" + txtfldId.getText().trim() + "|"
							+ "ARTIKELNUMMER>" + txtfldArtnr.getText().trim() + "|"
							+ "ART>'" + txtfldArt.getText().trim() + "'|"
							+ "NAME>'" + txtfldName.getText().trim() + "'|"
							+ "PREIS>" + txtfldPreis.getText().trim().replace(",", ".") + "|"
							+ "BESTAND>" + (chkBestand.isSelected()?"1":"0") + "|"
							+ "BEZEICHNUNG>'" + txtfldBezeichung.getText().trim() + "'|"
							+ "BILD>'" + txtfldBild.getText().trim() + "'|"
							+ "GEWICHT>" + txtfldGewicht.getText().trim();
					String param2 = "WHERE `artikelnummer`=" + list_Produkte.getSelectedValue().getArtikelnummer();
					try {
						String result = api.performRequest(Modul.PRODUKTE, Aktion.SET, param1, param2);
						JSONObject err = new JSONObject(result);
						if(err.getBoolean("error")) {
							JOptionPane.showMessageDialog(contentPane, err.getInt("code") + " - " + err.getString("description"), "Fehler", JOptionPane.ERROR_MESSAGE);
						}else {
							JOptionPane.showMessageDialog(contentPane, "Die Änderungen wurden gespeichert", "Erfolgreich", JOptionPane.INFORMATION_MESSAGE);
							updateProdukte();
						}
					} catch (IOException e) {
						e.printStackTrace();
						JOptionPane.showMessageDialog(contentPane, "Die Änderungen konnten nicht gespeichert werden", "Fehler", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});

		HButton btnImportieren = new HButton();
		btnImportieren.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser = new JFileChooser();
				chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
				chooser.setMultiSelectionEnabled(false);
				int a = chooser.showOpenDialog(contentPane);
				if(a==JFileChooser.APPROVE_OPTION) {
					File f;
					if((f=chooser.getSelectedFile())!=null) {
						try {
							Product p = ImportExport.importProduct(f);
							txtfldArtnr.setText(p.getArtikelnummer()+"");
							txtfldArt.setText(p.getArt());
							chkBestand.setSelected(p.isBestand());
							txtfldBezeichung.setText(p.getBezeichnung());
							txtfldBild.setText(p.getBild());
							txtfldGewicht.setText(p.getGewicht()+"");
							txtfldName.setText(p.getName());
							txtfldPreis.setText(p.getPreis()+"");
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
		btnImportieren.setBackground(new Color(255, 255, 255));
		btnImportieren.setText("Importieren");
		panel_1.add(btnImportieren, "flowx,cell 2 10,alignx right");

		HButton btnExportieren = new HButton();
		btnExportieren.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Product p = new Product(0, 0);
				p.setArtikelnummer(Integer.parseInt(txtfldArtnr.getText().trim()));
				p.setArt(txtfldArt.getText().trim());
				p.setBestand(chkBestand.isSelected());
				p.setBezeichnung(txtfldBezeichung.getText().trim());
				p.setBild(txtfldBild.getText().trim());
				p.setGewicht(Integer.parseInt(txtfldGewicht.getText().trim()));
				p.setId(0);
				p.setName(txtfldName.getText().trim());
				p.setPreis(Float.parseFloat(txtfldPreis.getText().trim().replace(',', '.')));
				JFileChooser chooser = new JFileChooser();
				chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
				chooser.setMultiSelectionEnabled(false);
				chooser.setSelectedFile(new File(p.getName()+"_"+txtfldId.getText()+"-"+p.getArtikelnummer()+".json"));
				int a = chooser.showSaveDialog(contentPane);
				if(a==JFileChooser.APPROVE_OPTION) {
					File f;
					if((f=chooser.getSelectedFile())!=null) {
						try {
							ImportExport.export(p, f);
							JOptionPane.showMessageDialog(contentPane, "Datensatz exportiert", "Erfolgreich", JOptionPane.INFORMATION_MESSAGE);
						} catch (IOException e1) {
							JOptionPane.showMessageDialog(contentPane, "Speichern fehlgeschlagen\n\n" + e1.getLocalizedMessage(), "Fehler", JOptionPane.ERROR_MESSAGE);
							e1.printStackTrace();
						}
					}else {
						JOptionPane.showMessageDialog(contentPane, "Ungültige Datei", "Fehler", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});
		btnExportieren.setBackground(new Color(255, 255, 255));
		btnExportieren.setText("Exportieren");
		panel_1.add(btnExportieren, "cell 2 10,alignx right");

		Component horizontalStrut_2 = Box.createHorizontalStrut(20);
		panel_1.add(horizontalStrut_2, "cell 2 10");
		btnSpeichern.setShadowColor(new Color(0, 100, 0));
		btnSpeichern.setBackground(new Color(255, 255, 255));
		btnSpeichern.setText("Speichern");
		panel_1.add(btnSpeichern, "cell 2 10,alignx right");

		HButton btnHinzufgen = new HButton();
		btnHinzufgen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				AddProductDialog.main(null);
			}
		});
		btnHinzufgen.setShadowColor(new Color(0, 0, 139));
		btnHinzufgen.setBackground(new Color(255, 255, 255));
		btnHinzufgen.setForeground(new Color(0, 0, 0));
		btnHinzufgen.setText("Hinzuf\u00FCgen");
		btnHinzufgen.setBounds(10, 567, 101, 27);
		vpProdukte.add(btnHinzufgen);

		HButton btnLschen = new HButton();
		btnLschen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!list_Produkte.isSelectionEmpty()) {
					try {
						String result = api.performRequest(Modul.PRODUKTE, Aktion.DELETE, "`artikelnummer`=" + list_Produkte.getSelectedValue().getArtikelnummer());
						JSONObject err = new JSONObject(result);
						if(err.getBoolean("error")) {
							JOptionPane.showMessageDialog(contentPane, err.getInt("code") + " - " + err.getString("description"), "Fehler", JOptionPane.ERROR_MESSAGE);
						}else {
							JOptionPane.showMessageDialog(contentPane, "Das Produkt wurde gelöscht", "Erfolgreich", JOptionPane.INFORMATION_MESSAGE);
							updateProdukte();
						}
					} catch (IOException e1) {
						e1.printStackTrace();
						JOptionPane.showMessageDialog(contentPane, "Das Produkt konnte nicht gelöscht werden", "Fehler", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});
		btnLschen.setBackground(new Color(255, 255, 255));
		btnLschen.setShadowColor(new Color(165, 42, 42));
		btnLschen.setText("L\u00F6schen");
		btnLschen.setBounds(108, 567, 101, 27);
		vpProdukte.add(btnLschen);

		vpCarts = new JPanel();
		vpCarts.setVisible(false);
		vpCarts.setBounds(0, 0, 1017, 604);
		panel.add(vpCarts);
		vpCarts.setBackground(Color.WHITE);
		vpCarts.setLayout(null);

		JScrollPane scrollPane_2 = new JScrollPane();
		scrollPane_2.setBounds(10, 0, 199, 604);
		vpCarts.add(scrollPane_2);

		list_Carts = new HList<Cart>();
		list_Carts.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent arg0) {
				if(!list_Carts.isSelectionEmpty()) {
					Cart c = list_Carts.getSelectedValue();
					((DefaultListModel<CartProduct>)list_CartContent.getModel()).clear();
					for(CartProduct cnt : c.getProdukte()) {
						((DefaultListModel<CartProduct>)list_CartContent.getModel()).addElement(cnt);
					}
				}
			}
		});
		list_Carts.setSelectedIndex(0);
		list_Carts.setCellRenderer(new CartListCellRenderer());
		scrollPane_2.setViewportView(list_Carts);

		JLabel lblInhalt = new JLabel("Inhalt:");
		lblInhalt.setBounds(238, 16, 46, 14);
		vpCarts.add(lblInhalt);

		JScrollPane scrollPane_4 = new JScrollPane();
		scrollPane_4.setBounds(238, 31, 384, 533);
		vpCarts.add(scrollPane_4);
		JLabel lblAnzahl = new JLabel("Anzahl: 0");
		JProductView productView_1 = new JProductView(new Product(0, 0));

		list_CartContent = new HList<CartProduct>();
		list_CartContent.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				if(!list_CartContent.isSelectionEmpty()) {
					CartProduct prod = list_CartContent.getSelectedValue();
					lblAnzahl.setText("Anzahl: " + prod.getMenge());
					Product theProd = new Product(0, prod.getArtikelnummer());
					for(int i = 0; i < productsModel.size(); i++) {
						if(productsModel.getElementAt(i).getArtikelnummer()==prod.getArtikelnummer()) {
							theProd = productsModel.getElementAt(i);
							break;
						}
					}
					productView_1.setProduct(theProd);

				}
			}
		});
		list_CartContent.setCellRenderer(new CartProductListCellRenderer());
		list_CartContent.setModel(new DefaultListModel<CartProduct>());
		scrollPane_4.setViewportView(list_CartContent);

		productView_1.setBounds(646, 31, 200, 260);
		vpCarts.add(productView_1);

		lblAnzahl.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblAnzahl.setBounds(646, 293, 200, 14);
		vpCarts.add(lblAnzahl);

		HButton button_3 = new HButton();
		button_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser chooser = new JFileChooser();
				chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
				chooser.setMultiSelectionEnabled(false);
				chooser.setSelectedFile(new File("cart_" + list_Carts.getSelectedValue().getId() + "-" + list_Carts.getSelectedValue().getKundennummer()+".json"));
				int a = chooser.showSaveDialog(contentPane);
				if(a==JFileChooser.APPROVE_OPTION) {
					File f;
					if((f=chooser.getSelectedFile())!=null) {
						try {
							ImportExport.export(list_Carts.getSelectedValue(), f);
							JOptionPane.showMessageDialog(contentPane, "Datensatz exportiert", "Erfolgreich", JOptionPane.INFORMATION_MESSAGE);
						} catch (IOException e1) {
							JOptionPane.showMessageDialog(contentPane, "Speichern fehlgeschlagen\n\n" + e1.getLocalizedMessage(), "Fehler", JOptionPane.ERROR_MESSAGE);
							e1.printStackTrace();
						}
					}else {
						JOptionPane.showMessageDialog(contentPane, "Ungültige Datei", "Fehler", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});
		button_3.setText("Exportieren");
		button_3.setBackground(Color.WHITE);
		button_3.setBounds(632, 534, 78, 30);
		vpCarts.add(button_3);
		btnAccounts.setText("Accounts");
		btnAccounts.shadowColor = new Color(0, 139, 139);
		btnAccounts.setShadowColor(new Color(0, 139, 139));
		btnAccounts.setBackground(Color.WHITE);
		contentPane.add(btnAccounts, "cell 0 3,growx");

		btnWarenkrbe = new HButton();
		btnWarenkrbe.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnProdukte.setSelected(false);
				btnAccounts.setSelected(false);
				btnWarenkrbe.setSelected(true);
				vpProdukte.setVisible(false);
				vpAccounts.setVisible(false);
				vpCarts.setVisible(true);
				lblTitle.setText("Warenkörbe");
				updateCarts();
			}
		});
		btnWarenkrbe.setText("Warenk\u00F6rbe");
		btnWarenkrbe.shadowColor = new Color(0, 139, 139);
		btnWarenkrbe.setShadowColor(new Color(0, 139, 139));
		btnWarenkrbe.setBackground(Color.WHITE);
		contentPane.add(btnWarenkrbe, "cell 0 4,growx");

		Component verticalStrut = Box.createVerticalStrut(20);
		contentPane.add(verticalStrut, "cell 0 5");

		btnEinstellungen = new HButton();
		btnEinstellungen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SettingsDialog.main(null);
			}
		});
		btnEinstellungen.setText("Einstellungen");
		btnEinstellungen.shadowColor = new Color(0, 139, 139);
		btnEinstellungen.setShadowColor(new Color(123, 104, 238));
		btnEinstellungen.setBackground(Color.WHITE);
		contentPane.add(btnEinstellungen, "cell 0 6,growx");

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

	public static void updateProdukte() {
		// Produkte Abfragen
		try {
			String resp = api.performRequest(Modul.PRODUKTE, Aktion.GET, "*");
			productsModel.clear();
			try {
				for(Product p : Converter.convertProducts(new JSONArray(resp))) {
					productsModel.addElement(p);
				}
			}catch (JSONException ex){}
		} catch (IOException e1) {
			e1.printStackTrace();
			JOptionPane.showMessageDialog(contentPane, "Daten konnten nicht abgefragt werden", "Fehler", JOptionPane.ERROR_MESSAGE);
		}
		list_Produkte.setModel(productsModel);
		list_Produkte.setSelectedIndex(0);
	}

	public static void updateAccounts() {
		// Accounts Abfragen
		try {
			String resp = api.performRequest(Modul.NUTZER, Aktion.GET, "*");
			accountsModel.clear();
			try {
				for(Account a : Converter.convertAccounts(new JSONArray(resp))) {
					accountsModel.addElement(a);
				}
			}catch (JSONException ex){}
		} catch (IOException e1) {
			e1.printStackTrace();
			JOptionPane.showMessageDialog(contentPane, "Daten konnten nicht abgefragt werden", "Fehler", JOptionPane.ERROR_MESSAGE);
		}
		list_Accounts.setModel(accountsModel);
		list_Accounts.setSelectedIndex(0);
	}

	public static void updateCarts() {
		// Warenkoerbe Abfragen
		try {
			String resp = api.performRequest(Modul.CART, Aktion.GET, "*");
			cartsModel.clear();
			try {
				for(Cart c : Converter.convertCarts(new JSONArray(resp))) {
					cartsModel.addElement(c);
				}
			}catch (JSONException ex){}
		} catch (IOException e1) {
			e1.printStackTrace();
			JOptionPane.showMessageDialog(contentPane, "Daten konnten nicht abgefragt werden", "Fehler", JOptionPane.ERROR_MESSAGE);
		}
		list_Carts.setModel(cartsModel);
		list_Carts.setSelectedIndex(0);
	}
}
