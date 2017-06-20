package com.kingteller.client.bean.pxkh;

import java.io.Serializable;
import java.util.List;

public class XzTkParamBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private QuestionParamBean quest;
	private List<AnswerParamBean> answer;
	public QuestionParamBean getQuest() {
		return quest;
	}
	public void setQuest(QuestionParamBean quest) {
		this.quest = quest;
	}
	public List<AnswerParamBean> getAnswer() {
		return answer;
	}
	public void setAnswerList(List<AnswerParamBean> answer) {
		this.answer = answer;
	}
	
	
}
