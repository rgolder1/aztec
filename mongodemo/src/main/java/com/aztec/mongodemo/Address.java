package com.aztec.mongodemo;

public class Address {

	private String street;
	private String city;
	private String postCode;
	
	public Address(String street, String city, String postCode) {
		this.street = street;
		this.city = city;
		this.postCode = postCode;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getPostCode() {
		return postCode;
	}

	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}
}
