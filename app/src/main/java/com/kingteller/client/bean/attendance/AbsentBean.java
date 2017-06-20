package com.kingteller.client.bean.attendance;

import java.io.Serializable;
import java.util.List;

public class AbsentBean implements Serializable{

	private static final long serialVersionUID = -4433012837643885103L;
	
	private String saveflag;			//操作类型		zc：暂存		submit：提交		audit：审批通过		back：退回
	private String billType;			//单据类型
	
	private String absenteeismId;		//旷工单id
	private String absenteeismNo;		//旷工单单号
	
	private String createUserId;		//创建人id
	private String createUserName;		//创建人姓名
	private String createTimeStr;		//创建时间
	
	private String flowStatus;			// 流程环节
	private String absenteeismTimes;	// 旷工时长
	private String absenteeismDay;		// 旷工天数
	private String absenteeismHour;		// 旷工小时
	
	private String firstDayHour;		// 第一天时长
	private String endDayHour;			// 最后一天时长
	
	private String startTime;			// 开始时间	2015-11-25 08:30
	private String endTime;				// 结束时间	2015-11-25 08:30
	
	private String absenteeismUserId;				// 旷工人id
	private String absenteeismUserName;				// 旷工人姓名
	private String absenteeismUserAccount;			// 旷工人帐号
	
	private String absenteeismReason;				// 旷工描述
	private String exeRemark;						// 审批意见
	private List<HistoryOperation> historyList;		// 流程list
	
	private String code;							//获取状态信息
	private String nextUserAccounts;				//下一环节处理人账号
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

	public String getAbsenteeismId() {
		return absenteeismId;
	}

	public void setAbsenteeismId(String absenteeismId) {
		this.absenteeismId = absenteeismId;
	}

	public String getAbsenteeismNo() {
		return absenteeismNo;
	}

	public void setAbsenteeismNo(String absenteeismNo) {
		this.absenteeismNo = absenteeismNo;
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

	public String getAbsenteeismTimes() {
		return absenteeismTimes;
	}

	public void setAbsenteeismTimes(String absenteeismTimes) {
		this.absenteeismTimes = absenteeismTimes;
	}

	public String getAbsenteeismDay() {
		return absenteeismDay;
	}

	public void setAbsenteeismDay(String absenteeismDay) {
		this.absenteeismDay = absenteeismDay;
	}

	public String getAbsenteeismHour() {
		return absenteeismHour;
	}

	public void setAbsenteeismHour(String absenteeismHour) {
		this.absenteeismHour = absenteeismHour;
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

	public String getAbsenteeismUserId() {
		return absenteeismUserId;
	}

	public void setAbsenteeismUserId(String absenteeismUserId) {
		this.absenteeismUserId = absenteeismUserId;
	}

	public String getAbsenteeismUserName() {
		return absenteeismUserName;
	}

	public void setAbsenteeismUserName(String absenteeismUserName) {
		this.absenteeismUserName = absenteeismUserName;
	}

	public String getAbsenteeismUserAccount() {
		return absenteeismUserAccount;
	}

	public void setAbsenteeismUserAccount(String absenteeismUserAccount) {
		this.absenteeismUserAccount = absenteeismUserAccount;
	}

	public String getAbsenteeismReason() {
		return absenteeismReason;
	}

	public void setAbsenteeismReason(String absenteeismReason) {
		this.absenteeismReason = absenteeismReason;
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
