package com.kingteller.client.bean.map;

import java.io.Serializable;

public class StaffPointBean  implements Serializable  {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4054236197812936512L;
	private String jqbh;
	private double latItude;
	private double longItude;
	private String monitorDate;
	private String orderId;
	private String orderNo;
	private String orderProperty;
	
	public String getJqbh() {
		return jqbh;
	}
	public void setJqbh(String jqbh) {
		this.jqbh = jqbh;
	}
	public double getLatItude() {
		return latItude;
	}
	public void setLatItude(double latItude) {
		this.latItude = latItude;
	}
	public double getLongItude() {
		return longItude;
	}
	public void setLongItude(double longItude) {
		this.longItude = longItude;
	}
	public String getMonitorDate() {
		return monitorDate;
	}
	public void setMonitorDate(String monitorDate) {
		this.monitorDate = monitorDate;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public String getOrderProperty() {
		return orderProperty;
	}
	public void setOrderProperty(String orderProperty) {
		this.orderProperty = orderProperty;
	}

}
