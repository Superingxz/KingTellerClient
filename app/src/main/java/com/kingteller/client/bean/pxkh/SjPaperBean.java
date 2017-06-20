package com.kingteller.client.bean.pxkh;

import java.io.Serializable;

public class SjPaperBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String paperId;
	private String paperCode;
	private String content;
	private String timeLong;
	private String answerDateLike;
	
	public String getPaperId() {
		return paperId;
	}
	public void setPaperId(String paperId) {
		this.paperId = paperId;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getTimeLong() {
		return timeLong;
	}
	public void setTimeLong(String timeLong) {
		this.timeLong = timeLong;
	}
	public String getPaperCode() {
		return paperCode;
	}
	public void setPaperCode(String paperCode) {
		this.paperCode = paperCode;
	}
	public String getAnswerDateLike() {
		return answerDateLike;
	}
	public void setAnswerDateLike(String answerDateLike) {
		this.answerDateLike = answerDateLike;
	}
	
	
}
