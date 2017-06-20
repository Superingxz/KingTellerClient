package com.kingteller.client.bean.more;

import java.io.Serializable;

public class AppUpdateBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5416039173865889042L;
	private String busId;
	private String content; //更新内容
	private boolean successFul; //是否有更新
	private String title; //更新的版本
	private String url; //更新包地址
	
	public String getBusId() {
		return busId;
	}
	public void setBusId(String busId) {
		this.busId = busId;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public boolean isSuccessFul() {
		return successFul;
	}
	public void setSuccessFul(boolean successFul) {
		this.successFul = successFul;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	

}
