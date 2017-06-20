package com.kingteller.client.bean.account;

import java.io.Serializable;

public class FunctionBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2865816839879172443L;
	private String busId;
	private String content;
	private boolean successFul;
	private String title;
	private String url;
	private String view;
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
	public String getView() {
		return view;
	}
	public void setView(String view) {
		this.view = view;
	}

}
