package com.kingteller.client.bean.onlinelearning;

import java.io.Serializable;

public class WorkListBean implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String wfId; // 文件id
	private String wfSwfLink; // flash链接地址
	private String wfTitle; // 文件标题
	
	public String getWfId() {
		return wfId;
	}

	public void setWfId(String wfId) {
		this.wfId = wfId;
	}

	public String getWfSwfLink() {
		return wfSwfLink;
	}

	public void setWfSwfLink(String wfSwfLink) {
		this.wfSwfLink = wfSwfLink;
	}

	public String getWfTitle() {
		return wfTitle;
	}

	public void setWfTitle(String wfTitle) {
		this.wfTitle = wfTitle;
	}

	
}
