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
import java.net.URL;

import javax.swing.Box;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import org.json.JSONObject;

import de.himalaya.api.HimalayaAPI.Aktion;
import de.himalaya.api.HimalayaAPI.Modul;
import de.himalaya.data.ImportExport;
import de.himalaya.data.Product;
import de.himalaya.gui.MainGUI;
import de.himalaya.gui.components.HButton;
import de.himalaya.gui.components.HCheckBox;
import de.himalaya.gui.components.HTextField;
import de.himalaya.gui.components.JWebImage;
import de.himalaya.gui.components.JWebImage.ScaleMode;
import net.miginfocom.swing.MigLayout;

public class AddProductDialog extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 320758406157593337L;
	private JPanel contentPane;

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
					AddProductDialog frame = new AddProductDialog();
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
	public AddProductDialog() {
		setModalExclusionType(ModalExclusionType.APPLICATION_EXCLUDE);
		setType(Type.POPUP);
		setTitle("Neues Produkt");
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 547, 356);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNeuesProdukt = new JLabel("Neues Produkt");
		lblNeuesProdukt.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblNeuesProdukt.setBounds(10, 11, 140, 22);
		contentPane.add(lblNeuesProdukt);
		
		JLabel lblBildvorschau = new JLabel("Bildvorschau:");
		lblBildvorschau.setBounds(10, 44, 140, 14);
		contentPane.add(lblBildvorschau);
		
		JWebImage wbmgImage = new JWebImage((URL) null);
		wbmgImage.setBorder(new LineBorder(new Color(0, 0, 0)));
		wbmgImage.setBounds(10, 60, 140, 146);
		contentPane.add(wbmgImage);
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.WHITE);
		panel.setBounds(160, 11, 375, 265);
		contentPane.add(panel);
		panel.setLayout(new MigLayout("", "[][][grow]", "[][][][][][][][][]"));
		
		JLabel label = new JLabel("ID:");
		panel.add(label, "cell 0 0,alignx right");
		
		Component horizontalStrut = Box.createHorizontalStrut(20);
		panel.add(horizontalStrut, "cell 1 0");
		
		HTextField textField_1 = new HTextField();
		textField_1.setText("<automatisch>");
		textField_1.setOpaque(false);
		textField_1.setEditable(false);
		panel.add(textField_1, "cell 2 0,growx");
		
		JLabel label_1 = new JLabel("Artikelnummer:");
		panel.add(label_1, "cell 0 1,alignx right");
		
		HTextField txtfldArtikelnummer = new HTextField();
		txtfldArtikelnummer.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				int caret = txtfldArtikelnummer.getCaretPosition();
				txtfldArtikelnummer.setText(extract(txtfldArtikelnummer.getText(), "0123456789"));
				txtfldArtikelnummer.setCaretPosition(caret);
			}
		});
		txtfldArtikelnummer.setOpaque(false);
		panel.add(txtfldArtikelnummer, "cell 2 1,growx");
		
		JLabel label_2 = new JLabel("Art:");
		panel.add(label_2, "cell 0 2,alignx right");
		
		HTextField txtfldArt = new HTextField();
		txtfldArt.setOpaque(false);
		panel.add(txtfldArt, "cell 2 2,growx");
		
		JLabel label_3 = new JLabel("Name:");
		panel.add(label_3, "cell 0 3,alignx right");
		
		HTextField txtfldName = new HTextField();
		txtfldName.setOpaque(false);
		panel.add(txtfldName, "cell 2 3,growx");
		
		JLabel label_4 = new JLabel("Preis:");
		panel.add(label_4, "cell 0 4,alignx right");
		
		HTextField txtfldPreis = new HTextField();
		txtfldPreis.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				int caret = txtfldPreis.getCaretPosition();
				txtfldPreis.setText(extract(txtfldPreis.getText(), "0123456789,."));
				txtfldPreis.setCaretPosition(caret);
			}
		});
		txtfldPreis.setOpaque(false);
		panel.add(txtfldPreis, "cell 2 4,growx");
		
		JLabel label_5 = new JLabel("Bestand:");
		panel.add(label_5, "cell 0 5,alignx right");
		
		HCheckBox chbxBestand = new HCheckBox();
		chbxBestand.fillcolor = new Color(0, 128, 0);
		chbxBestand.setFillcolor(new Color(0, 128, 0));
		chbxBestand.setBackground(Color.WHITE);
		panel.add(chbxBestand, "cell 2 5");
		
		JLabel label_6 = new JLabel("Bild:");
		panel.add(label_6, "cell 0 6,alignx right");
		
		HTextField txtfldBild = new HTextField();
		txtfldBild.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				try {
					wbmgImage.setSource(new URL(txtfldBild.getText()));
					wbmgImage.reload(ScaleMode.STRETCH);
				} catch (IOException e1) {}
			}
		});
		txtfldBild.setOpaque(false);
		panel.add(txtfldBild, "cell 2 6,growx");
		
		JLabel label_7 = new JLabel("Bezeichnung:");
		panel.add(label_7, "cell 0 7,alignx right");
		
		HTextField txtfldBezeichnung = new HTextField();
		txtfldBezeichnung.setOpaque(false);
		panel.add(txtfldBezeichnung, "cell 2 7,growx");
		
		JLabel label_8 = new JLabel("Gewicht");
		panel.add(label_8, "cell 0 8,alignx right");
		
		HTextField txtfldGewicht = new HTextField();
		txtfldGewicht.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent arg0) {
				int caret = txtfldGewicht.getCaretPosition();
				txtfldGewicht.setText(extract(txtfldGewicht.getText(), "0123456789"));
				txtfldGewicht.setCaretPosition(caret);
			}
		});
		txtfldGewicht.setOpaque(false);
		panel.add(txtfldGewicht, "cell 2 8,growx");
		
		HButton button = new HButton();
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				String[] params = {txtfldArtikelnummer.getText(), txtfldArt.getText(), txtfldName.getText(), txtfldPreis.getText().replace(',', '.'), chbxBestand.isSelected()?"1":"0", txtfldBild.getText(), txtfldBezeichnung.getText(), txtfldGewicht.getText()};
				
				try {
					String result = MainGUI.api.performRequest(Modul.PRODUKTE, Aktion.CREATE, params);
					JSONObject err = new JSONObject(result);
					if(err.getBoolean("error")) {
						JOptionPane.showMessageDialog(contentPane, err.getInt("code") + " - " + err.getString("description"), "Fehler", JOptionPane.ERROR_MESSAGE);
					}else {
						JOptionPane.showMessageDialog(contentPane, "Das Produkt wurde eingetragen", "Erfolgreich", JOptionPane.INFORMATION_MESSAGE);
						MainGUI.updateProdukte();
						dispose();
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
		button.setBounds(462, 286, 69, 30);
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
		btnAbbrechen.setBounds(383, 286, 79, 30);
		contentPane.add(btnAbbrechen);
		
		HButton button_1 = new HButton();
		button_1.addActionListener(new ActionListener() {
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
							txtfldArtikelnummer.setText(p.getArtikelnummer()+"");
							txtfldArt.setText(p.getArt());
							chbxBestand.setSelected(p.isBestand());
							txtfldBezeichnung.setText(p.getBezeichnung());
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
		button_1.setText("Importieren");
		button_1.setBackground(Color.WHITE);
		button_1.setBounds(10, 286, 78, 30);
		contentPane.add(button_1);
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
