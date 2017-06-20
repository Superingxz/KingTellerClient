package com.kingteller.client.bean.qrcode;

import java.io.Serializable;

/**
 * 质量信息
 * @author Administrator
 */
public class QRTraceBackToZlBean implements Serializable{

	private static final long serialVersionUID = -913705793381747568L;
	
	private String serialnumber; //序号
	private String maintainDateStr; //维修/自修时间
	private String isOverdue; //是否在保
	
	public String getSerialnumber() {
		return serialnumber;
	}
	public void setSerialnumber(String serialnumber) {
		this.serialnumber = serialnumber;
	}
	public String getMaintainDateStr() {
		return maintainDateStr;
	}
	public void setMaintainDateStr(String maintainDateStr) {
		this.maintainDateStr = maintainDateStr;
	}
	public String getIsOverdue() {
		return isOverdue;
	}
	public void setIsOverdue(String isOverdue) {
		this.isOverdue = isOverdue;
	}
	
	
}