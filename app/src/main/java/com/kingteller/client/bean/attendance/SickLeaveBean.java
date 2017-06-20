package com.kingteller.client.bean.attendance;

import java.io.Serializable;
import java.util.List;

public class SickLeaveBean implements Serializable {

	private static final long serialVersionUID = -7031825487307090733L;

	private String saveflag;			//操作类型		zc：暂存		submit：提交		audit：审批通过		back：退回
	private String billType;			//单据类型
	
	private String sickId;				//销假单id
	private String sickNo;				//销假单单号
	
	private String createUserId;		//创建人id
	private String createUserName;		//创建人姓名
	private String createTimeStr;		//创建时间
	
	private String flowStatus;			// 流程环节
	private String sickTimes;			// 销假时长
	private String sickDay;				// 销假天数
	private String sickHour;			// 销假小时
	
	private String firstDayHour;		// 第一天时长
	private String endDayHour;			// 最后一天时长
	
	private String startTime;			// 开始时间	2015-11-25 08:30
	private String endTime;				// 结束时间	2015-11-25 08:30
	
	private String sickUserId;			// 销假人id
	private String sickUserName;		// 销假人姓名
	private String sickUserAccount;		// 销假人帐号
	
	private String sickReason;			// 销假原因
	
	private String leaveId;				// 需要销假的请假单id
	private String leaveNo;				// 需要销假的请假单号
	private String leaveStartTimeStr;	// 需要销假的请假单 开始时间
	private String leaveEndTimeStr;		// 需要销假的请假单 结束时间
	private String leaveTimes;			// 需要销假的请假单 请假时长
	
	private String exeRemark;					// 审批意见
	private List<HistoryOperation>		historyList;			//审批信息list
	
	private String code;			//获取状态信息
	private String nextUserAccounts;			//下一环节处理人账号
	private  List<WorkAttendanceSearchPeopleBean> ulist;			//审批人list
	
	
	
	
	public String getFirstDayHour() {
		return firstDayHour;
	}

	public void setFirstDayHour(String firstDayHour) {
		this.firstDayHour = firstDayHour;
	}

	public String getEndDayHour() {
		return endDayHour;
	}

	public void setEndDayHour(String endDayHour) {
		this.endDayHour = endDayHour;
	}

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

	public String getLeaveTimes() {
		return leaveTimes;
	}

	public void setLeaveTimes(String leaveTimes) {
		this.leaveTimes = leaveTimes;
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

	public String getSickId() {
		return sickId;
	}

	public void setSickId(String sickId) {
		this.sickId = sickId;
	}

	public String getSickNo() {
		return sickNo;
	}

	public void setSickNo(String sickNo) {
		this.sickNo = sickNo;
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

	public String getSickTimes() {
		return sickTimes;
	}

	public void setSickTimes(String sickTimes) {
		this.sickTimes = sickTimes;
	}

	public String getSickDay() {
		return sickDay;
	}

	public void setSickDay(String sickDay) {
		this.sickDay = sickDay;
	}

	public String getSickHour() {
		return sickHour;
	}

	public void setSickHour(String sickHour) {
		this.sickHour = sickHour;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getSickUserId() {
		return sickUserId;
	}

	public void setSickUserId(String sickUserId) {
		this.sickUserId = sickUserId;
	}

	public String getSickUserName() {
		return sickUserName;
	}

	public void setSickUserName(String sickUserName) {
		this.sickUserName = sickUserName;
	}

	public String getSickUserAccount() {
		return sickUserAccount;
	}

	public void setSickUserAccount(String sickUserAccount) {
		this.sickUserAccount = sickUserAccount;
	}

	public String getSickReason() {
		return sickReason;
	}

	public void setSickReason(String sickReason) {
		this.sickReason = sickReason;
	}

	public String getLeaveId() {
		return leaveId;
	}

	public void setLeaveId(String leaveId) {
		this.leaveId = leaveId;
	}

	public String getLeaveNo() {
		return leaveNo;
	}

	public void setLeaveNo(String leaveNo) {
		this.leaveNo = leaveNo;
	}

	public String getLeaveStartTimeStr() {
		return leaveStartTimeStr;
	}

	public void setLeaveStartTimeStr(String leaveStartTimeStr) {
		this.leaveStartTimeStr = leaveStartTimeStr;
	}

	public String getLeaveEndTimeStr() {
		return leaveEndTimeStr;
	}

	public void setLeaveEndTimeStr(String leaveEndTimeStr) {
		this.leaveEndTimeStr = leaveEndTimeStr;
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
