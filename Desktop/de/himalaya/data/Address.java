package de.himalaya.data;

import java.io.Serializable;
import java.time.Instant;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

public class Address implements Serializable{

	private static final long serialVersionUID = 3398778458721227885L;

	private String line1;
	private String line2;
	private String line3;
	private String city;
	private String state;
	private int postalCode;
	private long dateOfBirth;
	private String phone;
	
	public Address() {
		this.setLine1("");
		this.setLine2("");
		this.setLine3("");
		this.setCity("");
		this.setState("");
		this.setPostalCode(0);
		this.setDateOfBirth(0L);
		this.setPhone("");
	}
	
	public Address(String line1, String line2, String line3, String city, String state, int postalCode, long dateOfBirth, String phone) {
		this.setLine1(line1);
		this.setLine2(line2);
		this.setLine3(line3);
		this.setCity(city);
		this.setState(state);
		this.setPostalCode(postalCode);
		this.setDateOfBirth(dateOfBirth);
		this.setPhone(phone);
	}
	
	public Address(String line1, String line2, String line3, String city, String state, int postalCode, Instant dateOfBirth, String phone) {
		this.setLine1(line1);
		this.setLine2(line2);
		this.setLine3(line3);
		this.setCity(city);
		this.setState(state);
		this.setPostalCode(postalCode);
		this.setDateOfBirth(dateOfBirth.toEpochMilli());
		this.setPhone(phone);
	}
	
	@Override
	public String toString() {
		return this.getLine1() + " | " + this.getLine2() + " | " + this.getLine3() + " | " + this.getCity() + " | " + this.getState() + " | " + this.getPostalCode() + " | "
				+ this.getInstant().atZone(ZoneOffset.UTC).format(DateTimeFormatter.ofPattern("dd.MM.yyyy")) + " | " + this.getPhone();
	}
	
	public Instant getInstant() {
		return Instant.ofEpochMilli(getDateOfBirth());
	}

	public String getLine1() {
		return line1;
	}

	public void setLine1(String line1) {
		this.line1 = line1;
	}

	public String getLine2() {
		return line2;
	}

	public void setLine2(String line2) {
		this.line2 = line2;
	}

	public String getLine3() {
		return line3;
	}

	public void setLine3(String line3) {
		this.line3 = line3;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public int getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(int postalCode) {
		this.postalCode = postalCode;
	}

	public long getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(long dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public void setDateOfBirth(Instant dateOfBirth) {
		this.dateOfBirth = dateOfBirth.toEpochMilli();
	}
	
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	
}
