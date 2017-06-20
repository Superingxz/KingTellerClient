package com.kingteller.client.bean.attendance;

import java.io.Serializable;

public class HistoryOperation implements Serializable{
	
	private static final long serialVersionUID = 2630577809120132267L;
	
	private String title;				//环节名称
	private String exeuser;				//处理人
	private String suggestion;			//处理意见
	private String exetime;				//处理时间
	private String tt;					//操作
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getExeuser() {
		return exeuser;
	}
	public void setExeuser(String exeuser) {
		this.exeuser = exeuser;
	}
	public String getSuggestion() {
		return suggestion;
	}
	public void setSuggestion(String suggestion) {
		this.suggestion = suggestion;
	}
	public String getExetime() {
		return exetime;
	}
	public void setExetime(String exetime) {
		this.exetime = exetime;
	}
	public String getTt() {
		return tt;
	}
	public void setTt(String tt) {
		this.tt = tt;
	}
	
	

}
