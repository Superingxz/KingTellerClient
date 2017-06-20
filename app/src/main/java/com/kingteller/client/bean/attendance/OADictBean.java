package com.kingteller.client.bean.attendance;

import java.io.Serializable;

public class OADictBean implements Serializable{
	
	private static final long serialVersionUID = -9103218434866386766L;
	
	private String dictType;			//字典id
	private String dictValue;			//字典名称
	
	public String getDictType() {
		return dictType;
	}
	public void setDictType(String dictType) {
		this.dictType = dictType;
	}
	public String getDictValue() {
		return dictValue;
	}
	public void setDictValue(String dictValue) {
		this.dictValue = dictValue;
	}
}
