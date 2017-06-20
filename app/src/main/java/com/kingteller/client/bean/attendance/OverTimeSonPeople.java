package com.kingteller.client.bean.attendance;

import java.io.Serializable;

public class OverTimeSonPeople implements Serializable{
	private static final long serialVersionUID = 5551582198053594261L;
	
	private String sonId;				//加班从表id
	private String sonUserId;			//加班人id
	private String sonUserName;			//加班人姓名
	private String sonUserAccount;		//加班人工号
	private String sonHour;				//加班时长
	private String rewardType;			//报酬类型		0补休		1计薪 	
	private String cardTime;           // 打卡记录（多个用逗号隔开）

	
	public OverTimeSonPeople(String sonId, String sonUserId, String sonUserName,
			String sonUserAccount, String sonHour, String rewardType, String cardTime) {
		this.sonId = sonId;
		this.sonUserId = sonUserId;
		this.sonUserName = sonUserName;
		this.sonUserAccount = sonUserAccount;
		this.sonHour = sonHour;
		this.rewardType = rewardType;
		this.cardTime = cardTime;
	}
	
	
	public String getCardTime() {
		return cardTime;
	}
	public void setCardTime(String cardTime) {
		this.cardTime = cardTime;
	}
	public String getSonId() {
		return sonId;
	}
	public void setSonId(String sonId) {
		this.sonId = sonId;
	}
	public String getSonUserId() {
		return sonUserId;
	}
	public void setSonUserId(String sonUserId) {
		this.sonUserId = sonUserId;
	}
	public String getSonUserName() {
		return sonUserName;
	}
	public void setSonUserName(String sonUserName) {
		this.sonUserName = sonUserName;
	}
	public String getSonUserAccount() {
		return sonUserAccount;
	}
	public void setSonUserAccount(String sonUserAccount) {
		this.sonUserAccount = sonUserAccount;
	}
	public String getSonHour() {
		return sonHour;
	}
	public void setSonHour(String sonHour) {
		this.sonHour = sonHour;
	}
	public String getRewardType() {
		return rewardType;
	}
	public void setRewardType(String rewardType) {
		this.rewardType = rewardType;
	}
	
	
}
