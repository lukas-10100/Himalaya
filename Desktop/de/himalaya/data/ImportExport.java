package de.himalaya.data;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ImportExport {

	/**
	 * Exportiert ein Product-Objekt im JSON-Format oder als Textdatei.
	 * Die Formatauswahl erfolgt automatisch.
	 * 
	 * @param p Zu exportierendes Product-Objekt
	 * @param f Zieldatei
	 * 
	 * @throws IOException
	 */
	public static void export(Product p, File f) throws IOException {
		String toWrite = "";

		// Format nach Dateiendung auswaehlen
		if(f.getName().toLowerCase().endsWith(".json")) {
			// JSON-Objekt erzeugen und Daten eintragen
			JSONObject data = new JSONObject();
			data.put("artikelnummer", p.getArtikelnummer());
			data.put("art", p.getArt());
			data.put("name", p.getName());
			data.put("preis", p.getPreis());
			data.put("bestand", p.isBestand());
			data.put("bild", p.getBild());
			data.put("bezeichnung", p.getBezeichnung());
			data.put("gewicht", p.getGewicht());
			// JSON-Objekt in String umwandeln und zwischenspeichern
			toWrite = data.toString();
		}
		else {
			// Manuelles Zusammenstellen des Strings, der die Informationen erhaelt
			toWrite += "[artikelnummer] " + p.getArtikelnummer() + System.lineSeparator();
			toWrite += "[art] " + p.getArt() + System.lineSeparator();
			toWrite += "[name] " + p.getName() + System.lineSeparator();
			toWrite += "[preis] " + p.getPreis() + System.lineSeparator();
			toWrite += "[bestand] " + p.isBestand() + System.lineSeparator();
			toWrite += "[bild] " + p.getBild() + System.lineSeparator();
			toWrite += "[bezeichnung] " + p.getBezeichnung() + System.lineSeparator();
			toWrite += "[gewicht] " + p.getGewicht() + System.lineSeparator();
		}
		
		// Text des generierten Strings mithilfe eines BufferedWriter-Objekts in die Datei schreiben
		BufferedWriter bw = Files.newBufferedWriter(f.toPath(), StandardOpenOption.CREATE);
		bw.write(toWrite);
		bw.newLine();
		bw.flush();
		bw.close();
	}

	/**
	 * Importiert ein Product-Objekt aus einer Datei im JSON-Format oder einer Textdatei.
	 * Die Formatauswahl erfolgt automatisch.
	 * 
	 * @param f Quelldatei
	 * @return Product-Objekt, das aus der Datei geladen wurde
	 * 
	 * @throws IOException
	 */
	public static Product importProduct(File f) throws IOException {
		List<String> lines = new ArrayList<>();
		String raw = "";
		// BUfferedReader zum Einlesen der Datei
		BufferedReader br = Files.newBufferedReader(f.toPath());
		String line;
		while((line=br.readLine())!=null) {
			lines.add(line);
			raw += line;
		}
		
		// Format erkennen
		// Versuch, den Inhalt der Datei als JSON-Objekt zu parsen. Schlaegt fehl => Kein JSON, sondern normaler Text
		try {
			// JSON-Objekt aus Dateiinhalt erzeugen und Product-Objekt erstellen und zurueckgeben
			JSONObject o = new JSONObject(raw);
			Product p = new Product(0, o.getInt("artikelnummer"), o.getString("art"), o.getString("name"), (float)o.getDouble("preis"), o.getBoolean("bestand"), o.getString("bild"), o.getString("bezeichnung"), o.getInt("gewicht"));
			br.close();
			return p;
		}catch(JSONException ex) {
			// Neues, "leeres" Product-Objekt erzeugen
			Product p = new Product(0, 0);
			// Jede Zeile der Datei durchgehen
			for(String l : lines) {
				// Zeile beginnt mit gewissem Text (Beim Export festgelegt) => Rest der Zeile entspricht dem Wert
				if(l.startsWith("[artikelnummer]")) {
					// Wert isolieren (Leerzeichen entfernen, Schlagwort entfernen) und zu entsprechendem Datentyp umwandeln
					p.setArtikelnummer(Integer.parseInt(l.replace("[artikelnummer]", "").trim()));
				}else if(l.startsWith("[art]")) {
					p.setArt(l.replace("[art]", "").trim());
				}else if(l.startsWith("[name]")) {
					p.setName(l.replace("[name]", "").trim());
				}else if(l.startsWith("[preis]")) {
					p.setPreis(Float.parseFloat(l.replace("[preis]", "").trim()));
				}else if(l.startsWith("[bestand]")) {
					// Besonders: boolean aus String, indem geprueft wurd, ob der gelesene String dem String "true" entspricht
					p.setBestand(l.replace("[bestand]", "").trim().toLowerCase().equals("true"));
				}else if(l.startsWith("[bild]")) {
					p.setBild(l.replace("[bild]", "").trim());
				}else if(l.startsWith("[bezeichnung]")) {
					p.setBezeichnung(l.replace("[bezeichnung]", "").trim());
				}else if(l.startsWith("[gewicht]")) {
					p.setGewicht(Integer.parseInt(l.replace("[gewicht]", "").trim()));
				}
			}
			return p;
		}
	}
	
	/**
	 * Exportiert ein Account-Objekt im JSON-Format oder als Textdatei.
	 * Die Formatauswahl erfolgt automatisch.
	 * 
	 * @param a Zu exportierendes Account-Objekt
	 * @param f Zieldatei
	 * 
	 * @throws IOException
	 */
	public static void export(Account a, File f) throws IOException {
		String toWrite = "";

		if(f.getName().toLowerCase().endsWith(".json")) {
			JSONObject data = new JSONObject();
			JSONArray paymentMethods = new JSONArray();
			for(PaymentMethod m : a.getZahlungsmethoden()) {
				paymentMethods.put(Converter.toJsonOriginal(m));
			}
			data.put("kundennummer", a.getKundennummer());
			data.put("name", a.getName());
			data.put("email", a.getEmail());
			data.put("passwort", a.getPasswort());
			data.put("zahlungsmethoden", paymentMethods);
			data.put("adresse", Converter.toJsonOriginal(a.getAdresse()));
			data.put("land", a.getLand().name().toUpperCase());
			toWrite = data.toString();
		}
		else {
			JSONArray paymentMethods = new JSONArray();
			for(PaymentMethod m : a.getZahlungsmethoden()) {
				paymentMethods.put(Converter.toJsonOriginal(m));
			}
			toWrite += "[kundennummer] " + a.getKundennummer() + System.lineSeparator();
			toWrite += "[name] " + a.getName() + System.lineSeparator();
			toWrite += "[email] " + a.getEmail() + System.lineSeparator();
			toWrite += "[passwort] " + a.getPasswort() + System.lineSeparator();
			toWrite += "[zahlungsmethoden] " + paymentMethods.toString() + System.lineSeparator();
			toWrite += "[adresse] " + a.getAdresse().toString() + System.lineSeparator();
			toWrite += "[land] " + a.getLand().name().toUpperCase() + System.lineSeparator();
		}
		BufferedWriter bw = Files.newBufferedWriter(f.toPath(), StandardOpenOption.CREATE);
		bw.write(toWrite);
		bw.newLine();
		bw.flush();
		bw.close();
	}
	
	/**
	 * Importiert ein Account-Objekt aus einer Datei im JSON-Format oder einer Textdatei.
	 * Die Formatauswahl erfolgt automatisch.
	 * 
	 * @param f Quelldatei
	 * @return Account-Objekt, das aus der Datei geladen wurde
	 * 
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	public static Account importAccount(File f) throws IOException {
		List<String> lines = new ArrayList<>();
		String raw = "";
		BufferedReader br = Files.newBufferedReader(f.toPath());
		String line;
		while((line=br.readLine())!=null) {
			lines.add(line);
			raw += line;
		}
		try {
			JSONObject o = new JSONObject(raw);
			List<PaymentMethod> zahlungsmethoden = new ArrayList<>();
			JSONArray zm = o.getJSONArray("zahlungsmethoden");
			for(Object obj : zm.toList()) {
				JSONObject pm = new JSONObject((HashMap<String, Object>)obj);
				PaymentMethod pmm = new PaymentMethod("");
				pmm.setName(pm.getString("name"));
				zahlungsmethoden.add(pmm);
			}
			JSONObject addrObj = o.getJSONObject("adresse");
			Address adresse = new Address();
			adresse.setLine1(addrObj.getString("line1"));
			adresse.setLine2(addrObj.getString("line2"));
			adresse.setLine3(addrObj.getString("line3"));
			adresse.setCity(addrObj.getString("city"));
			adresse.setState(addrObj.getString("state"));
			adresse.setDateOfBirth(addrObj.getLong("dateOfBirth"));
			adresse.setPostalCode(addrObj.getInt("postalCode"));
			adresse.setPhone(addrObj.getString("phone"));
			Account a = new Account(0, o.getInt("kundennummer"), o.getString("name"), o.getString("email"), o.getString("passwort"), zahlungsmethoden, adresse, Country.valueOf(o.getString("land")));
			br.close();
			return a;
		}catch(JSONException ex) {
			Account a = new Account(0, 0);
			for(String l : lines) {
				if(l.startsWith("[kundennummer]")) {
					a.setKundennummer(Integer.parseInt(l.replace("[kundennummer]", "").trim()));
				}else if(l.startsWith("[name]")) {
					a.setName(l.replace("[name]", "").trim());
				}else if(l.startsWith("[email]")) {
					a.setEmail(l.replace("[email]", "").trim());
				}else if(l.startsWith("[passwort]")) {
					a.setPasswort(l.replace("[passwort]", "").trim());
				}else if(l.startsWith("[zahlungsmethoden]")) {
					List<PaymentMethod> zahlungsmethoden = new ArrayList<>();
					JSONArray zm = new JSONArray(l.replace("[zahlungsmethoden]", "").trim());
					for(Object obj : zm.toList()) {
						JSONObject pm = new JSONObject((HashMap<String, Object>)obj);
						PaymentMethod pmm = new PaymentMethod("");
						pmm.setName(pm.getString("name"));
						zahlungsmethoden.add(pmm);
					}
					a.setZahlungsmethoden(zahlungsmethoden);
				}else if(l.startsWith("[adresse]")) {
					String[] contents = l.replace("[adresse]", "").trim().split("\\|");
					Address addr = new Address(contents[0].trim(), contents[1].trim(), contents[2].trim(), contents[3].trim(), contents[4].trim(), Integer.parseInt(contents[5].trim()), LocalDateTime.of(Integer.parseInt(contents[6].trim().split("\\.")[2]), Integer.parseInt(contents[6].trim().split("\\.")[1]), Integer.parseInt(contents[6].trim().split("\\.")[0]), 0, 0).atZone(ZoneOffset.UTC).toInstant(), contents[7].trim());
					a.setAdresse(addr);
				}else if(l.startsWith("[land]")) {
					a.setLand(Country.valueOf(l.replace("[land]", "").trim().toUpperCase()));
				}
			}
			return a;
		}
	}

	/**
	 * Exportiert ein Cart-Objekt im JSON-Format oder als Textdatei.
	 * Die Formatauswahl erfolgt automatisch.
	 * 
	 * Cart-Objekte koennen nur exportiert werden.
	 * 
	 * @param c Zu exportierendes Cart-Objekt
	 * @param f Zieldatei
	 * 
	 * @throws IOException
	 */
	public static void export(Cart c, File f) throws IOException {
		String toWrite = "";

		if(f.getName().toLowerCase().endsWith(".json")) {
			JSONArray produkte = new JSONArray();
			for(CartProduct p : c.getProdukte()) {
				produkte.put(Converter.toJsonOriginal(p));
			}
			JSONObject data = new JSONObject();
			data.put("kundennummer", c.getKundennummer());
			data.put("produkte", produkte);
			toWrite = data.toString();
		}
		else {
			String produkte = "";
			for(CartProduct p : c.getProdukte()) {
				produkte += p.getArtikelnummer() + "[" + p.getName() + "]x" + p.getMenge() + ", ";
			}
			produkte = produkte.substring(0, produkte.length()-3);
			toWrite += "[kundennummer] " + c.getKundennummer() + System.lineSeparator();
			toWrite += "[produkte] " + produkte + System.lineSeparator();
		}
		BufferedWriter bw = Files.newBufferedWriter(f.toPath(), StandardOpenOption.CREATE);
		bw.write(toWrite);
		bw.newLine();
		bw.flush();
		bw.close();
	}
	
}
