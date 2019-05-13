package de.himalaya.gui.dialog;

import java.awt.Color;
import java.awt.Dialog.ModalExclusionType;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

import de.himalaya.gui.MainGUI;
import de.himalaya.gui.components.HButton;
import de.himalaya.gui.components.HTextField;
import net.miginfocom.swing.MigLayout;

public class SettingsDialog extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4015374158726455667L;
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
					SettingsDialog frame = new SettingsDialog();
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
	public SettingsDialog() {
		setType(Type.POPUP);
		setModalExclusionType(ModalExclusionType.APPLICATION_EXCLUDE);
		setTitle("Einstellungen");
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 481, 161);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 255, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new MigLayout("", "[][grow]", "[][][][][][][][][][][][][]"));
		
		JLabel lblApiKey = new JLabel("API Key:");
		contentPane.add(lblApiKey, "cell 0 0,alignx trailing");
		
		HTextField txtfldApikey = new HTextField();
		txtfldApikey.setText(MainGUI.settings.getApiKey());
		txtfldApikey.setOpaque(false);
		contentPane.add(txtfldApikey, "cell 1 0,growx");
		
		HButton button = new HButton();
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MainGUI.settings.setApiKey(txtfldApikey.getText().trim());
				try {
					MainGUI.settings.save(MainGUI.settingsFile);
					
					try {
						MainGUI.settings.load(MainGUI.settingsFile);
						MainGUI.api.setKey(MainGUI.settings.getApiKey());
						System.out.println("Einstellungen geladen");
					} catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException | IllegalBlockSizeException
							| BadPaddingException | ClassNotFoundException | IOException e3) {
						e3.printStackTrace();
						JOptionPane.showMessageDialog(null, "Laden der Einstellungsdatei fehlgeschlagen", "Fehler", JOptionPane.ERROR_MESSAGE);
					}
					dispose();
				} catch (InvalidKeyException | NoSuchAlgorithmException | IllegalBlockSizeException
						| BadPaddingException | NoSuchPaddingException | IOException e1) {
					e1.printStackTrace();
					JOptionPane.showMessageDialog(contentPane, "Die Einstellungen konnten nicht gespeichert werden\n\n" + e1.getLocalizedMessage(), "Fehler", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		button.setText("Speichern");
		button.shadowColor = new Color(0, 100, 0);
		button.setShadowColor(new Color(0, 100, 0));
		button.setBackground(Color.WHITE);
		contentPane.add(button, "cell 1 12,alignx right");
	}

}
