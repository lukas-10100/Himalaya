package de.himalaya.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Account implements Serializable{

	private static final long serialVersionUID = 3584238129895685200L;

	private int id;
	private int kundennummer;
	private String name;
	private String email;
	private String passwort;
	private List<PaymentMethod> zahlungsmethoden;
	private Address adresse;
	private Country land;
	
	public Account(int id, int kundennummer) {
		this.setId(id);
		this.setKundennummer(kundennummer);
		this.setName("");
		this.setEmail("");
		this.setPasswort("");
		this.setZahlungsmethoden(new ArrayList<>());
		this.setAdresse(new Address());
		this.setLand(Country.NONE);
	}
	
	public Account(int id, int kundennummer, String name, String email, String passwort, List<PaymentMethod> zahlungsmethoden, Address adresse, Country land) {
		this.setId(id);
		this.setKundennummer(kundennummer);
		this.setName(name);
		this.setEmail(email);
		this.setPasswort(passwort);
		this.setZahlungsmethoden(zahlungsmethoden);
		this.setAdresse(adresse);
		this.setLand(land);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getKundennummer() {
		return kundennummer;
	}

	public void setKundennummer(int kundennummer) {
		this.kundennummer = kundennummer;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPasswort() {
		return passwort;
	}

	public void setPasswort(String passwort) {
		this.passwort = passwort;
	}

	public List<PaymentMethod> getZahlungsmethoden() {
		return zahlungsmethoden;
	}

	public void setZahlungsmethoden(List<PaymentMethod> zahlungsmethoden) {
		this.zahlungsmethoden = zahlungsmethoden;
	}

	public Address getAdresse() {
		return adresse;
	}

	public void setAdresse(Address adresse) {
		this.adresse = adresse;
	}

	public Country getLand() {
		return land;
	}

	public void setLand(Country land) {
		this.land = land;
	}
	
}
