package de.himalaya.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Cart implements Serializable{

	private static final long serialVersionUID = -4111892537575031091L;
	
	private int id;
	private int kundennummer;
	private List<CartProduct> produkte;
	
	public Cart(int id) {
		this.setId(id);
		this.setKundennummer(0);
		this.setProdukte(new ArrayList<>());
	}
	
	public Cart(int id, int kundennummer, List<CartProduct> produkte) {
		this.setId(id);
		this.setKundennummer(kundennummer);
		this.setProdukte(produkte);
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

	public List<CartProduct> getProdukte() {
		return produkte;
	}

	public void setProdukte(List<CartProduct> produkte) {
		this.produkte = produkte;
	}
	
}
