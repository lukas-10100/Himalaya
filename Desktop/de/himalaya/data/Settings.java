package de.himalaya.data;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

public class Settings implements Serializable {

	private static final long serialVersionUID = -2506390362749036608L;

	private String fileKey;
	
	private String apiKey;
	
	public Settings(String fileKey) {
		this.fileKey = fileKey;
		
		this.setApiKey("");
	}

	/**
	 * Laed Einstellungswerte aus einer verschluesselten Datei und uebernimmt diese in der eigenen Instanz
	 * 
	 * @param f Quelldatei
	 * 
	 * @throws IOException
	 * @throws InvalidKeyException
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchPaddingException
	 * @throws IllegalBlockSizeException
	 * @throws BadPaddingException
	 * @throws ClassNotFoundException
	 */
	public void load(File f) throws IOException, InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, ClassNotFoundException {
		// Key fuer Verschluesselung erzeugen
		SecretKeySpec key = this.getKey();
		// Rohe Byte-Daten der Datei einlesen (mithilfe eines FileInputStream)
		FileInputStream fin = new FileInputStream(f);
		byte[] buff = new byte[fin.available()]; // byte-Array mit Laenge der Dateigroeße
		// Solange noch Bytes zum Lesen uebrig sind...
		while(fin.available()>0) {
			// ... je ein Byte nach dem anderen der richtigen Stelle im byte-Array zuweisen
			buff[buff.length-fin.available()] = (byte) fin.read();
		}
		// byte-Array entschluesseln und in Objekt umwandeln
		Object o = decrypt(buff, key);
		if(o instanceof Settings) {
			// Werte uebernehmen
			Settings s = (Settings)o;
			this.setApiKey(s.getApiKey());
		}
		fin.close();
	}
	
	/** 
	 * Speichert die Eintellungswerte der eigenen Instanz verschluesselt in einer Datei
	 * 
	 * @param f Zieldatei
	 * 
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeyException
	 * @throws IllegalBlockSizeException
	 * @throws BadPaddingException
	 * @throws NoSuchPaddingException
	 * @throws IOException
	 */
	public void save(File f) throws NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, NoSuchPaddingException, IOException {
		SecretKeySpec key = this.getKey();
		FileOutputStream fos = new FileOutputStream(f);
		// Verschluesselte byte-Array in die Datei schreiben
		fos.write(encrypt(this, key));
		fos.flush();
		fos.close();
	}
	
	private SecretKeySpec getKey() throws UnsupportedEncodingException, NoSuchAlgorithmException {
		// String des Keys in Klartext zu byte-Array umwandeln
		byte[] key = (this.getFileKey()).getBytes("UTF-8");
		// Key als SecretKeySpec erzeugen und zurueckgeben
		MessageDigest sha = MessageDigest.getInstance("SHA-256");
		key = sha.digest(key);
		key = Arrays.copyOf(key, 16); // Nur die ersten 16 Stellen verwenden
		SecretKeySpec secretKeySpec = new SecretKeySpec(key, "AES");
		return secretKeySpec;
	}
	
	private byte[] encrypt(Object in, SecretKeySpec key) throws IOException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException {
		Object out = in;
		ByteArrayOutputStream baos = new ByteArrayOutputStream(); // Stream, in den das Objekt geschrieben werden soll
		ObjectOutputStream oos = new ObjectOutputStream(baos); // Stream, der das Objekt als byte-Array in den ByteArrayOutputStream schreibt
		oos.writeObject(out);
		oos.flush();
		// byte-Array aus ByteStream kopieren
		byte[] buffer = baos.toByteArray();
		oos.close();
		baos.close();
		// byte-Array entschluesseln und zurueckgeben
		Cipher cipher = Cipher.getInstance("AES");
		cipher.init(Cipher.ENCRYPT_MODE, key);
		byte[] encrypted = cipher.doFinal(buffer);
		return encrypted;
	}
	
	private Object decrypt(byte[] in, SecretKeySpec key) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, IOException, ClassNotFoundException {
		// byte-Array entschluesseln
		Cipher cipher2 = Cipher.getInstance("AES");
		cipher2.init(Cipher.DECRYPT_MODE, key);
		byte[] out = cipher2.doFinal(in);
		
		// Wie bei encrypt, nur dass diesmal InputStreams verwendet werden, um das byte-Array als Grundlage zu nehmen und das Objekt zu "lesen"
		ByteArrayInputStream bais = new ByteArrayInputStream(out);
		ObjectInputStream ois = new ObjectInputStream(bais);
		Object ret = ois.readObject();
		ois.close();
		bais.close();
		return ret;
	}
	
	public String getApiKey() {
		return apiKey;
	}

	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}

	public String getFileKey() {
		return fileKey;
	}
	
}
