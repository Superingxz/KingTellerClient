package com.kingteller.client.bean.map;

import java.io.Serializable;

public class CheckMacineBean implements Serializable  {

	private static final long serialVersionUID = -2797747574396212769L;
	private String picFlag;
	private String jwdFlag;
	private String jqbh;
	private String wddz;
	
	
	public String getJwdFlag() {
		return jwdFlag;
	}
	public void setJwdFlag(String jwdFlag) {
		this.jwdFlag = jwdFlag;
	}
	public String getWddz() {
		return wddz;
	}
	public void setWddz(String wddz) {
		this.wddz = wddz;
	}
	public String getPicFlag() {
		return picFlag;
	}
	public void setPicFlag(String picFlag) {
		this.picFlag = picFlag;
	}
	public String getJqbh() {
		return jqbh;
	}
	public void setJqbh(String jqbh) {
		this.jqbh = jqbh;
	}

}
