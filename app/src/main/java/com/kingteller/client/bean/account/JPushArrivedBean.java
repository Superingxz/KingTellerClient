package com.kingteller.client.bean.account;

public class JPushArrivedBean {
	private long jpushSendNo;
	private boolean isMobileRecevie;
	private String senderUserName;//发送者姓名 
	private String senderDateStr;//发送日期文本
	private String pushTransSmsIdentifyNo;//手机推送和短信之间一致性标识

	public long getJpushSendNo() {
		return jpushSendNo;
	}

	public void setJpushSendNo(long jpushSendNo) {
		this.jpushSendNo = jpushSendNo;
	}

	public boolean isMobileRecevie() {
		return isMobileRecevie;
	}

	public void setMobileRecevie(boolean isMobileRecevie) {
		this.isMobileRecevie = isMobileRecevie;
	}

	public String getSenderUserName() {
		return senderUserName;
	}

	public void setSenderUserName(String senderUserName) {
		this.senderUserName = senderUserName;
	}

	public String getSenderDateStr() {
		return senderDateStr;
	}

	public void setSenderDateStr(String senderDateStr) {
		this.senderDateStr = senderDateStr;
	}

	public String getPushTransSmsIdentifyNo() {
		return pushTransSmsIdentifyNo;
	}

	public void setPushTransSmsIdentifyNo(String pushTransSmsIdentifyNo) {
		this.pushTransSmsIdentifyNo = pushTransSmsIdentifyNo;
	}

}
