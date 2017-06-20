package com.kingteller.client.bean.workorder;

import java.io.Serializable;

public class WddzBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String wdId;
	private String wdCode;
	private String wdAddress;
	private String reAddress;
	private String name;
	private String phone;
	
	public String getWdId() {
		return wdId;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getWdCode() {
		return wdCode;
	}
	public void setWdCode(String wdCode) {
		this.wdCode = wdCode;
	}
	public String getReAddress() {
		return reAddress;
	}
	public void setReAddress(String reAddress) {
		this.reAddress = reAddress;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public void setWdId(String wdId) {
		this.wdId = wdId;
	}
	public String getWdAddress() {
		return wdAddress;
	}
	public void setWdAddress(String wdAddress) {
		this.wdAddress = wdAddress;
	}
}
