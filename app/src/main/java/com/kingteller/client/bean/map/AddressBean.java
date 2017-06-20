package com.kingteller.client.bean.map;

import java.io.Serializable;

public class AddressBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -975589348165845234L;
	private String address = "";
	private String name;
	private double lat;
	private double lng;
	private String city;

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public double getLat() {
		return lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	public double getLng() {
		return lng;
	}

	public void setLng(double lng) {
		this.lng = lng;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

}