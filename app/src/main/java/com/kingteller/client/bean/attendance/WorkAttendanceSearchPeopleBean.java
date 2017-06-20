package com.kingteller.client.bean.attendance;

import java.io.Serializable;

public class WorkAttendanceSearchPeopleBean implements Serializable{

	private static final long serialVersionUID = 3255625606364407644L;
	
	private String userId;            // 用户id
	private String userAccount;       // 登录系统账号
	private String userName;          // 姓名
	private String code;
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserAccount() {
		return userAccount;
	}
	public void setUserAccount(String userAccount) {
		this.userAccount = userAccount;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	
	
}
