package com.kingteller.client.bean.attendance;

import java.io.Serializable;
import java.util.List;

public class BoardBean implements Serializable{

	private static final long serialVersionUID = -7999300742948580712L;

	private String saveflag;			//操作类型		zc：暂存		submit：提交		audit：审批通过		back：退回
	private String billType;			//单据类型
	
	private String fillId;				//补登单id
	private String fillNo;				//补登单单号
	
	private String createUserId;		//创建人id
	private String createUserName;		//创建人姓名
	private String createTimeStr;		//创建时间
	
	private String flowStatus;			// 流程环节
	private String fillTime;			// 补登时间	2015-11-25 08:30

	private String fillUserId;			// 补登人id
	private String fillUserName;		// 补登人姓名
	private String fillUserAccount;		// 补登人帐号
	
	private String fillType;			// 补登类型
	private String fillTypeName;		// 补登类型名称
	private String fillTypeOth;			// 类型为 其他 时 有值
	
	private String fillReason;			// 补登原因
	private String exeRemark;			// 审批意见
	private List<HistoryOperation> historyList;		//审批信息list
	
	private String code;							//获取状态信息
	private String nextUserAccounts;				//下一环节处理人账号
	private  List<WorkAttendanceSearchPeopleBean> ulist;	//审批人list
	
	
	public String getNextUserAccounts() {
		return nextUserAccounts;
	}

	public void setNextUserAccounts(String nextUserAccounts) {
		this.nextUserAccounts = nextUserAccounts;
	}

	public List<WorkAttendanceSearchPeopleBean> getUlist() {
		return ulist;
	}

	public void setUlist(List<WorkAttendanceSearchPeopleBean> ulist) {
		this.ulist = ulist;
	}

	public String getSaveflag() {
		return saveflag;
	}

	public void setSaveflag(String saveflag) {
		this.saveflag = saveflag;
	}

	public String getBillType() {
		return billType;
	}

	public void setBillType(String billType) {
		this.billType = billType;
	}

	public String getFillId() {
		return fillId;
	}

	public void setFillId(String fillId) {
		this.fillId = fillId;
	}

	public String getFillNo() {
		return fillNo;
	}

	public void setFillNo(String fillNo) {
		this.fillNo = fillNo;
	}

	public String getCreateUserId() {
		return createUserId;
	}

	public void setCreateUserId(String createUserId) {
		this.createUserId = createUserId;
	}

	public String getCreateUserName() {
		return createUserName;
	}

	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}

	public String getCreateTimeStr() {
		return createTimeStr;
	}

	public void setCreateTimeStr(String createTimeStr) {
		this.createTimeStr = createTimeStr;
	}

	public String getFlowStatus() {
		return flowStatus;
	}

	public void setFlowStatus(String flowStatus) {
		this.flowStatus = flowStatus;
	}

	public String getFillTime() {
		return fillTime;
	}

	public void setFillTime(String fillTime) {
		this.fillTime = fillTime;
	}

	public String getFillUserId() {
		return fillUserId;
	}

	public void setFillUserId(String fillUserId) {
		this.fillUserId = fillUserId;
	}

	public String getFillUserName() {
		return fillUserName;
	}

	public void setFillUserName(String fillUserName) {
		this.fillUserName = fillUserName;
	}

	public String getFillUserAccount() {
		return fillUserAccount;
	}

	public void setFillUserAccount(String fillUserAccount) {
		this.fillUserAccount = fillUserAccount;
	}

	public String getFillType() {
		return fillType;
	}

	public void setFillType(String fillType) {
		this.fillType = fillType;
	}

	public String getFillTypeName() {
		return fillTypeName;
	}

	public void setFillTypeName(String fillTypeName) {
		this.fillTypeName = fillTypeName;
	}

	public String getFillTypeOth() {
		return fillTypeOth;
	}

	public void setFillTypeOth(String fillTypeOth) {
		this.fillTypeOth = fillTypeOth;
	}

	public String getFillReason() {
		return fillReason;
	}

	public void setFillReason(String fillReason) {
		this.fillReason = fillReason;
	}

	public String getExeRemark() {
		return exeRemark;
	}

	public void setExeRemark(String exeRemark) {
		this.exeRemark = exeRemark;
	}

	public List<HistoryOperation> getHistoryList() {
		return historyList;
	}

	public void setHistoryList(List<HistoryOperation> historyList) {
		this.historyList = historyList;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	
}
