package com.kingteller.client.bean.pxkh;

import java.io.Serializable;

public class QuestionParamBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String code;             // 试题编号
	private String content;          // 试题内容
	private String type;             // 试题类型,1、单选   2、多选
	private String createTimeStr;
	private String createUser;       // 创建者
	private String createUserLike;
	private String modifyTimeStr;
	private String publishType;      // 试题发布类型：1、练习题  2、考试题  3、年审题
	private String status;           // 试题状态
	private String isPublish;        // 试题是否发布:1、是   0、否
	private String aboutData;        // 相关学习资料
	private String aboutDatatype;    // 相关学习资料类型
	private String answerOrient;     // 答案方向
	private String remark;           // 备注
	private String expand1;          // 扩展字段
	private String expand2;          // 扩展字段
	private String analysis;         // 试题解析
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
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
	public String getCreateUserLike() {
		return createUserLike;
	}
	public void setCreateUserLike(String createUserLike) {
		this.createUserLike = createUserLike;
	}
	public String getModifyTimeStr() {
		return modifyTimeStr;
	}
	public void setModifyTimeStr(String modifyTimeStr) {
		this.modifyTimeStr = modifyTimeStr;
	}
	public String getPublishType() {
		return publishType;
	}
	public void setPublishType(String publishType) {
		this.publishType = publishType;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getIsPublish() {
		return isPublish;
	}
	public void setIsPublish(String isPublish) {
		this.isPublish = isPublish;
	}
	public String getAboutData() {
		return aboutData;
	}
	public void setAboutData(String aboutData) {
		this.aboutData = aboutData;
	}
	public String getAboutDatatype() {
		return aboutDatatype;
	}
	public void setAboutDatatype(String aboutDatatype) {
		this.aboutDatatype = aboutDatatype;
	}
	public String getAnswerOrient() {
		return answerOrient;
	}
	public void setAnswerOrient(String answerOrient) {
		this.answerOrient = answerOrient;
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
	public String getAnalysis() {
		return analysis;
	}
	public void setAnalysis(String analysis) {
		this.analysis = analysis;
	}
	
	
}
