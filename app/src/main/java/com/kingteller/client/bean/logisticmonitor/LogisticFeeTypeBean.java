package com.kingteller.client.bean.logisticmonitor;

import java.io.Serializable;

public class LogisticFeeTypeBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String ename;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getEname() {
		return ename;
	}
	public void setEname(String ename) {
		this.ename = ename;
	}
	
}
