package com.kingteller.client.bean.pxkh;

import java.io.Serializable;
import java.util.List;

public class XzParamBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String timeLong;
	private String core;
	private List<XzTkParamBean> questions;
	public String getTimeLong() {
		return timeLong;
	}
	public void setTimeLong(String timeLong) {
		this.timeLong = timeLong;
	}
	public String getCore() {
		return core;
	}
	public void setCore(String core) {
		this.core = core;
	}
	public List<XzTkParamBean> getQuestions() {
		return questions;
	}
	public void setQuestions(List<XzTkParamBean> questions) {
		this.questions = questions;
	}
	
	
	
	
}
