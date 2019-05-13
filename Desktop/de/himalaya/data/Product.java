package de.himalaya.data;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;

import javax.imageio.ImageIO;

public class Product implements Serializable{

	private static final long serialVersionUID = 3943680749128728593L;

	private int id;
	private int artikelnummer;
	private String art;
	private String name;
	private float preis;
	private boolean bestand;
	private String bild;
	private String bezeichnung;
	private int gewicht;
	
	public Product(int id, int artikelnummer) {
		this.setId(id);
		this.setArtikelnummer(artikelnummer);
		this.setArt("");
		this.setName("");
		this.setPreis(0.0F);
		this.setBestand(false);
		this.setBild("");
		this.setBezeichnung("");
		this.setGewicht(0);
	}
	
	public Product(int id, int artikelnummer, String art, String name, float preis, boolean bestand, String bild, String bezeichnung, int gewicht) {
		this.setId(id);
		this.setArtikelnummer(artikelnummer);
		this.setArt(art);
		this.setName(name);
		this.setPreis(preis);
		this.setBestand(bestand);
		this.setBild(bild);
		this.setBezeichnung(bezeichnung);
		this.setGewicht(gewicht);
	}
	
	public BufferedImage getImage() throws MalformedURLException, IOException {
		return ImageIO.read(new URL(this.getBild()));
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getArtikelnummer() {
		return artikelnummer;
	}

	public void setArtikelnummer(int artikelnummer) {
		this.artikelnummer = artikelnummer;
	}

	public String getArt() {
		return art;
	}

	public void setArt(String art) {
		this.art = art;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public float getPreis() {
		return preis;
	}

	public void setPreis(float preis) {
		this.preis = preis;
	}

	public boolean isBestand() {
		return bestand;
	}

	public void setBestand(boolean bestand) {
		this.bestand = bestand;
	}

	public String getBild() {
		return bild;
	}

	public void setBild(String bild) {
		this.bild = bild;
	}

	public String getBezeichnung() {
		return bezeichnung;
	}

	public void setBezeichnung(String bezeichnung) {
		this.bezeichnung = bezeichnung;
	}

	public int getGewicht() {
		return gewicht;
	}

	public void setGewicht(int gewicht) {
		this.gewicht = gewicht;
	}
	
}
