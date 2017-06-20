package com.kingteller.client.bean.workorder;

import java.io.Serializable;

public class JqzdDataBean implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private String attrCode; // :  字段code
	private String attrCodeId; // :  字段codeid
	private String attrwlinfoid; // :  物料infoid
	private String attrValueId; // :  物料id	
	private String attrName; // :  机器字段		
	private String attrValue; // :  物料名称
	private String wlPathName; //：物料路径
	public String getAttrCode() {
		return attrCode;
	}
	public void setAttrCode(String attrCode) {
		this.attrCode = attrCode;
	}
	public String getAttrCodeId() {
		return attrCodeId;
	}
	public void setAttrCodeId(String attrCodeId) {
		this.attrCodeId = attrCodeId;
	}
	public String getAttrwlinfoid() {
		return attrwlinfoid;
	}
	public void setAttrwlinfoid(String attrwlinfoid) {
		this.attrwlinfoid = attrwlinfoid;
	}
	public String getAttrValueId() {
		return attrValueId;
	}
	public void setAttrValueId(String attrValueId) {
		this.attrValueId = attrValueId;
	}
	public String getAttrName() {
		return attrName;
	}
	public void setAttrName(String attrName) {
		this.attrName = attrName;
	}
	public String getAttrValue() {
		return attrValue;
	}
	public void setAttrValue(String attrValue) {
		this.attrValue = attrValue;
	}
	public String getWlPathName() {
		return wlPathName;
	}
	public void setWlPathName(String wlPathName) {
		this.wlPathName = wlPathName;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
    
}
