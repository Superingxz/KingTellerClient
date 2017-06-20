package com.kingteller.client.bean.attendance;

import java.io.Serializable;

public class WorkAttendanceGetUserTimeBean implements Serializable{

	private static final long serialVersionUID = -1427252930172973327L;

	private String totalHour;			//时长
	private String hour;				//小时
	private String day;					//天数
	private String newBeginDateTime;	//开始时间
	private String newEndDateTime;		//结束时间
	private String beginHour;			//第一天时长
	private String endHour;				//最后一天时长
	
	private String code;		

	public String getBeginHour() {
		return beginHour;
	}
	public void setBeginHour(String beginHour) {
		this.beginHour = beginHour;
	}
	public String getEndHour() {
		return endHour;
	}
	public void setEndHour(String endHour) {
		this.endHour = endHour;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getTotalHour() {
		return totalHour;
	}
	public void setTotalHour(String totalHour) {
		this.totalHour = totalHour;
	}
	public String getHour() {
		return hour;
	}
	public void setHour(String hour) {
		this.hour = hour;
	}
	public String getDay() {
		return day;
	}
	public void setDay(String day) {
		this.day = day;
	}
	public String getNewBeginDateTime() {
		return newBeginDateTime;
	}
	public void setNewBeginDateTime(String newBeginDateTime) {
		this.newBeginDateTime = newBeginDateTime;
	}
	public String getNewEndDateTime() {
		return newEndDateTime;
	}
	public void setNewEndDateTime(String newEndDateTime) {
		this.newEndDateTime = newEndDateTime;
	}

	
	
}
