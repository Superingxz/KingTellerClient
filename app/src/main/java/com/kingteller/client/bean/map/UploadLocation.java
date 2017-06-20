package com.kingteller.client.bean.map;

public class UploadLocation {
	private String latidu;
	private String longtidu;
	private String userAccount;
	private String imeiCode;
	

	public String getUserAccount() {
		return userAccount;
	}
	public void setUserAccount(String userAccount) {
		this.userAccount = userAccount;
	}
	public String getImeiCode() {
		return imeiCode;
	}
	public void setImeiCode(String imeiCode) {
		this.imeiCode = imeiCode;
	}
	public String getLatidu() {
		return latidu;
	}
	public void setLatidu(String latidu) {
		this.latidu = latidu;
	}
	public String getLongtidu() {
		return longtidu;
	}
	public void setLongtidu(String longtidu) {
		this.longtidu = longtidu;
	}

}
