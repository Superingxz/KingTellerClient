package com.kingteller.client.bean.qrcode;

import java.io.Serializable;

public class QRDotMachineWdJqBean implements Serializable{

	private static final long serialVersionUID = -4067349724684339530L;
	
	private String jqId;//机器id
	private String jqBm;//机器编码
	private String code;
	
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getJqId() {
		return jqId;
	}
	public void setJqId(String jqId) {
		this.jqId = jqId;
	}
	public String getJqBm() {
		return jqBm;
	}
	public void setJqBm(String jqBm) {
		this.jqBm = jqBm;
	}
	
	

}
