package com.kingteller.client.bean.pxkh;

import java.io.Serializable;
import java.util.List;

public class JdtParamBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String timeLong;
	private String core;
	private List<QuestionParamBean> questions;
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
	public List<QuestionParamBean> getQuestions() {
		return questions;
	}
	public void setQuest(List<QuestionParamBean> questions) {
		this.questions = questions;
	}

	
	
}
