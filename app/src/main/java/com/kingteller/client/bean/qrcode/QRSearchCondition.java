package com.kingteller.client.bean.qrcode;

import java.io.Serializable;

public class QRSearchCondition implements Serializable{

	private static final long serialVersionUID = -2784528066900162784L;
	private String fbillNo; //出库单编号
	public String getFbillNo() {
		return fbillNo;
	}
	public void setFbillNo(String fbillNo) {
		this.fbillNo = fbillNo;
	}
	
}
