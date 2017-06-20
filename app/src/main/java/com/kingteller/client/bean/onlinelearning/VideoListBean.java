package com.kingteller.client.bean.onlinelearning;

import java.io.Serializable;

public class VideoListBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String vtId; // 视频id
	private String vtSwfLink; // flash链接地址

	private String vtTitle; // 视频标题
	private String vtDuration; // 视频时长

	public String getVtId() {
		return vtId;
	}

	public void setVtId(String vtId) {
		this.vtId = vtId;
	}

	public String getVtSwfLink() {
		return vtSwfLink;
	}

	public void setVtSwfLink(String vtSwfLink) {
		this.vtSwfLink = vtSwfLink;
	}

	public String getVtTitle() {
		return vtTitle;
	}

	public void setVtTitle(String vtTitle) {
		this.vtTitle = vtTitle;
	}

	public String getVtDuration() {
		return vtDuration;
	}

	public void setVtDuration(String vtDuration) {
		this.vtDuration = vtDuration;
	}

}
