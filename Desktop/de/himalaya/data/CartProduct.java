package de.himalaya.data;

import java.io.Serializable;

public class CartProduct implements Serializable {

	private static final long serialVersionUID = -5087630369299449917L;

	private int artikelnummer;
	private String name;
	private int menge;
	
	public CartProduct(int artikelnummer, String name, int menge) {
		this.setArtikelnummer(artikelnummer);
		this.setName(name);
		this.setMenge(menge);
	}

	public int getArtikelnummer() {
		return artikelnummer;
	}

	public void setArtikelnummer(int artikelnummer) {
		this.artikelnummer = artikelnummer;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getMenge() {
		return menge;
	}

	public void setMenge(int menge) {
		this.menge = menge;
	}
	
}
