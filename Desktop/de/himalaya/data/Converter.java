package de.himalaya.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

public class Converter {

	/**
	 * Wandelt ein JSON-Array mit Produktinformationen in eine List um,
	 * die Product-Objekte zur weiteren Verarbeitung enthaelt, welche aus den Inhalten des
	 * Arrays generiert wurden.
	 * 
	 * @param in Das JSON-Array der zu konvertierenden Produkte
	 * @return List mit Product-Objekten, welche das JSON-Array repraesentieren
	 */
	@SuppressWarnings("unchecked")
	public static List<Product> convertProducts(JSONArray in){
		List<Product> products = new ArrayList<>();

		for(Object o : in.toList()) {
			JSONObject prod = new JSONObject((HashMap<String, String>)o);
			Product p = new Product(0, 0);
			p.setId(Integer.parseInt(prod.getString("id")));
			p.setArtikelnummer(Integer.parseInt(prod.getString("artikelnummer")));
			p.setArt(prod.getString("art"));
			p.setName(prod.getString("name"));
			p.setPreis(Float.parseFloat(prod.getString("preis")));
			p.setBestand(prod.getString("bestand").equals("1"));
			p.setBild(prod.getString("bild"));
			p.setBezeichnung(prod.getString("bezeichnung"));
			p.setGewicht(Integer.parseInt(prod.getString("gewicht")));
			products.add(p);
		}


		return products;
	}

	/**
	 * Wandelt ein JSON-Array mit Accountinformationen in eine List um,
	 * die Account-Objekte zur weiteren Verarbeitung enthaelt, welche aus den Inhalten des
	 * Arrays generiert wurden.
	 * 
	 * @param in Das JSON-Array der zu konvertierenden Accounts
	 * @return List mit Account-Objekten, welche das JSON-Array repraesentieren
	 */
	@SuppressWarnings("unchecked")
	public static List<Account> convertAccounts(JSONArray in){
		List<Account> accounts = new ArrayList<>();

		for(Object o : in.toList()) {
			JSONObject acc = new JSONObject((HashMap<String, String>)o);
			Account a = new Account(0, 0);
			JSONObject addr = new JSONObject(acc.getString("adresse"));
			JSONArray paymMethods = new JSONArray(acc.getString("zahlungsmethoden"));
			List<PaymentMethod> methods = new ArrayList<>();
			for(Object pm : paymMethods.toList()) {
				JSONObject pmObj = new JSONObject((HashMap<String, String>)pm);
				methods.add(new PaymentMethod(pmObj.getString("name")));
				//TODO PaymentMethods
			}
			a.setAdresse(new Address(addr.getString("line1"), addr.getString("line2"), addr.getString("line3"), addr.getString("city"), addr.getString("state"), Integer.parseInt(addr.getString("postalCode")), Long.parseLong(addr.getString("dateOfBirth")), addr.getString("phone")));
			a.setEmail(acc.getString("email"));
			a.setId(Integer.parseInt(acc.getString("id")));
			a.setKundennummer(Integer.parseInt(acc.getString("kundennummer")));
			a.setLand(Country.valueOf(acc.getString("land").toUpperCase()));
			a.setName(acc.getString("name"));
			a.setPasswort(acc.getString("passwort"));
			a.setZahlungsmethoden(methods);
			accounts.add(a);
		}


		return accounts;
	}

	/**
	 * Wandelt ein JSON-Array mit Warenkorbinformationen in eine List um,
	 * die Cart-Objekte zur weiteren Verarbeitung enthaelt, welche aus den Inhalten des
	 * Arrays generiert wurden.
	 * 
	 * @param in Das JSON-Array der zu konvertierenden Warenkoerbe
	 * @return List mit Cart-Objekten, welche das JSON-Array repraesentieren
	 */
	@SuppressWarnings("unchecked")
	public static List<Cart> convertCarts(JSONArray in){
		List<Cart> carts = new ArrayList<>();

		for(Object o : in.toList()) {
			JSONObject crt = new JSONObject((HashMap<String, String>)o);
			Cart c = new Cart(0);
			
			List<CartProduct> content = new ArrayList<>();
			JSONArray cnt = new JSONArray(crt.getString("inhalt"));
			for(Object cp : cnt.toList()) {
				JSONObject cpObj = new JSONObject((HashMap<String, String>)cp);
				content.add(new CartProduct(cpObj.getInt("artikelnummer"), cpObj.getString("name"), cpObj.getInt("menge")));
			}
			c.setId(Integer.parseInt(crt.getString("id")));
			c.setKundennummer(Integer.parseInt(crt.getString("kundennummer")));
			c.setProdukte(content);
			carts.add(c);
		}


		return carts;
	}
	
	/**
	 * Wandelt ein Address-Objekt in ein JSON-Objekt um.
	 * 
	 * Alle Datentypen werden als String uebernommen
	 * 
	 * @param arg0 Umzuwandelnde Address-Object
	 * @return JSON-Objekt, welches das Objekt repraesentiert
	 */
	public static JSONObject toJson(Address arg0) {
		JSONObject obj = new JSONObject();
		obj.put("line1", String.valueOf(arg0.getLine1()));
		obj.put("line2", String.valueOf(arg0.getLine2()));
		obj.put("line3", String.valueOf(arg0.getLine3()));
		obj.put("city", String.valueOf(arg0.getCity()));
		obj.put("state", String.valueOf(arg0.getState()));
		obj.put("postalCode", String.valueOf(arg0.getPostalCode()));
		obj.put("dateOfBirth", String.valueOf(arg0.getDateOfBirth()));
		obj.put("phone", String.valueOf(arg0.getPhone()));
		return obj;
	}
	/**
	 * Wandelt ein Address-Objekt in ein JSON-Objekt um.
	 * 
	 * Alle Datentypen werden unveraendert uebernommen.
	 * 
	 * @param arg0 Umzuwandelnde Address-Object
	 * @return JSON-Objekt, welches das Objekt repraesentiert
	 */
	public static JSONObject toJsonOriginal(Address arg0) {
		JSONObject obj = new JSONObject();
		obj.put("line1", arg0.getLine1());
		obj.put("line2", arg0.getLine2());
		obj.put("line3", arg0.getLine3());
		obj.put("city", arg0.getCity());
		obj.put("state", arg0.getState());
		obj.put("postalCode", arg0.getPostalCode());
		obj.put("dateOfBirth", arg0.getDateOfBirth());
		obj.put("phone", arg0.getPhone());
		return obj;
	}
	/**
	 * Wandelt ein CartProduct-Objekt in ein JSON-Objekt um.
	 * 
	 * Alle Datentypen werden als String uebernommen
	 * 
	 * @param arg0 Umzuwandelndes CartProduct-Objekt
	 * @return JSON-Objekt, welches das Objekt repraesentiert
	 */
	public static JSONObject toJson(CartProduct arg0) {
		JSONObject obj = new JSONObject();
		obj.put("artikelnummer", String.valueOf(arg0.getArtikelnummer()));
		obj.put("name", String.valueOf(arg0.getName()));
		obj.put("menge", String.valueOf(arg0.getMenge()));
		return obj;
	}
	/**
	 * Wandelt ein CartProduct-Objekt in ein JSON-Objekt um.
	 * 
	 * Alle Datentypen werden unveraendert uebernommen
	 * 
	 * @param arg0 Umzuwandelndes CartProduct-Objekt
	 * @return JSON-Objekt, welches das Objekt repraesentiert
	 */
	public static JSONObject toJsonOriginal(CartProduct arg0) {
		JSONObject obj = new JSONObject();
		obj.put("artikelnummer", arg0.getArtikelnummer());
		obj.put("name", arg0.getName());
		obj.put("menge", arg0.getMenge());
		return obj;
	}
	/**
	 * Wandelt ein PaymentMethod-Objekt in ein JSON-Objekt um.
	 * 
	 * Alle Datentypen werden als String uebernommen
	 * 
	 * @param arg0 Umzuwandelndes PaymentMethod-Objekt
	 * @return JSON-Objekt, welches das Objekt repraesentiert
	 */
	public static JSONObject toJson(PaymentMethod arg0) {
		JSONObject obj = new JSONObject();
		obj.put("name", String.valueOf(arg0.getName()));
		return obj;
	}
	/**
	 * Wandelt ein PaymentMethod-Objekt in ein JSON-Objekt um.
	 * 
	 * Alle Datentypen werden unveraendert uebernommen
	 * 
	 * @param arg0 Umzuwandelndes PaymentMethod-Objekt
	 * @return JSON-Objekt, welches das Objekt repraesentiert
	 */
	public static JSONObject toJsonOriginal(PaymentMethod arg0) {
		JSONObject obj = new JSONObject();
		obj.put("name", arg0.getName());
		return obj;
	}
}
