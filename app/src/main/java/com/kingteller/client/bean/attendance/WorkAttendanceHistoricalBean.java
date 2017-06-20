package com.kingteller.client.bean.attendance;

import java.io.Serializable;

public class WorkAttendanceHistoricalBean implements Serializable{

	private static final long serialVersionUID = -1892753136286716204L;


	private String billDay;
	private String code;			//获取状态信息
	

	public String getBillDay() {
		return billDay;
	}
	public void setBillDay(String billDay) {
		this.billDay = billDay;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	
}
