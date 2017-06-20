package com.kingteller.client.bean.map;

import java.io.Serializable;

public class StaffSearchCondition implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 988156360961644565L;
	private String username;
	private String department;
	private String usecode;
	private String starttime;
	private String endtime;
	private String date;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getUsecode() {
		return usecode;
	}

	public void setUsecode(String usecode) {
		this.usecode = usecode;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getStarttime() {
		return starttime;
	}

	public void setStarttime(String starttime) {
		this.starttime = starttime;
	}

	public String getEndtime() {
		return endtime;
	}

	public void setEndtime(String endtime) {
		this.endtime = endtime;
	}

}
