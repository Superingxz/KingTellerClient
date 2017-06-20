package com.kingteller.client.bean.attendance;

import java.io.Serializable;

public class OverTimePeopleBean implements Serializable{

	private static final long serialVersionUID = 5409929118984859222L;
	
	private String userNameId;			//人员 id
	private String userName;			//人员 姓名
	private String userAccount;			//人员 工号
	private String userWard;			//补贴类型
	private String userTimeHour;		//加班时间
	private String userTimeCheckout;	//打卡记录

	
	
	public String getUserNameId() {
		return userNameId;
	}

	public void setUserNameId(String userNameId) {
		this.userNameId = userNameId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserAccount() {
		return userAccount;
	}

	public void setUserAccount(String userAccount) {
		this.userAccount = userAccount;
	}

	public String getUserWard() {
		return userWard;
	}

	public void setUserWard(String userWard) {
		this.userWard = userWard;
	}

	public String getUserTimeHour() {
		return userTimeHour;
	}

	public void setUserTimeHour(String userTimeHour) {
		this.userTimeHour = userTimeHour;
	}

	public String getUserTimeCheckout() {
		return userTimeCheckout;
	}

	public void setUserTimeCheckout(String userTimeCheckout) {
		this.userTimeCheckout = userTimeCheckout;
	}

	public OverTimePeopleBean(String userNameId, String userName, String userAccount, String userWard, String userTimeHour, String userTimeCheckout) {
		this.userNameId = userNameId;
		this.userName = userName;
		this.userAccount = userAccount;
		this.userWard = userWard;
		this.userTimeHour = userTimeHour;
		this.userTimeCheckout = userTimeCheckout;
	}

	public OverTimePeopleBean() {
		
	}
}
