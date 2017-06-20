package com.kingteller.client.bean.pxkh;

import java.io.Serializable;

public class AnswerParamBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String questionId;    // 所属试题ID
	private String code;          // 答案编号
	private String sort;          // 答案顺序
	private String content;       // 答案内容
	private String status;        // 答案状态
	private String createTimeStr;
	private String createUser;    // 创建者ID
	private String modifyTimeStr;
	private String isRight;       // 是否标准答案
	private String detail;        // 答案解析
	private String remark;        // 备注
	private String expand1;       // 扩展字段
	private String expand2;       // 扩展字段
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getQuestionId() {
		return questionId;
	}
	public void setQuestionId(String questionId) {
		this.questionId = questionId;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getSort() {
		return sort;
	}
	public void setSort(String sort) {
		this.sort = sort;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getCreateTimeStr() {
		return createTimeStr;
	}
	public void setCreateTimeStr(String createTimeStr) {
		this.createTimeStr = createTimeStr;
	}
	public String getCreateUser() {
		return createUser;
	}
	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}
	public String getModifyTimeStr() {
		return modifyTimeStr;
	}
	public void setModifyTimeStr(String modifyTimeStr) {
		this.modifyTimeStr = modifyTimeStr;
	}
	public String getIsRight() {
		return isRight;
	}
	public void setIsRight(String isRight) {
		this.isRight = isRight;
	}
	public String getDetail() {
		return detail;
	}
	public void setDetail(String detail) {
		this.detail = detail;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getExpand1() {
		return expand1;
	}
	public void setExpand1(String expand1) {
		this.expand1 = expand1;
	}
	public String getExpand2() {
		return expand2;
	}
	public void setExpand2(String expand2) {
		this.expand2 = expand2;
	}
	
	
}
