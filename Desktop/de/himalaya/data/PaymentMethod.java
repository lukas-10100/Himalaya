package de.himalaya.data;

import java.io.Serializable;

public class PaymentMethod implements Serializable{

	private static final long serialVersionUID = -1088834948613251113L;

	private String name;
	
	public PaymentMethod(String name) {
		this.setName(name);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
