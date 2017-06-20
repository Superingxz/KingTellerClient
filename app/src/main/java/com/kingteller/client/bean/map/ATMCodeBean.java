package com.kingteller.client.bean.map;

import java.io.Serializable;

public class ATMCodeBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1242096151528017511L;
	private String jqbh;
	private double x;
	private double y;
	public String getJqbh() {
		return jqbh;
	}
	public void setJqbh(String jqbh) {
		this.jqbh = jqbh;
	}
	public double getX() {
		return x;
	}
	public void setX(double x) {
		this.x = x;
	}
	public double getY() {
		return y;
	}
	public void setY(double y) {
		this.y = y;
	}

}
