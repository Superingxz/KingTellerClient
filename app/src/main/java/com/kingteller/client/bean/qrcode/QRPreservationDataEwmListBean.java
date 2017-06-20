package com.kingteller.client.bean.qrcode;

import java.io.Serializable;

public class QRPreservationDataEwmListBean implements Serializable{

	private static final long serialVersionUID = -2170952165817452235L;
	
	private String recDeliversWsFormsId;//出库单物料id
	private String regFormsType;//服务站出入库物料登记类型
	private String newCode;
	private String barcode;
	private String isNewadd;
	
	
	public String getIsNewadd() {
		return isNewadd;
	}
	public void setIsNewadd(String isNewadd) {
		this.isNewadd = isNewadd;
	}
	public String getRegFormsType() {
		return regFormsType;
	}
	public void setRegFormsType(String regFormsType) {
		this.regFormsType = regFormsType;
	}
	public String getBarcode() {
		return barcode;
	}
	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	public String getRecDeliversWsFormsId() {
		return recDeliversWsFormsId;
	}
	public void setRecDeliversWsFormsId(String recDeliversWsFormsId) {
		this.recDeliversWsFormsId = recDeliversWsFormsId;
	}
	public String getNewCode() {
		return newCode;
	}
	public void setNewCode(String newCode) {
		this.newCode = newCode;
	}
	
	
}
