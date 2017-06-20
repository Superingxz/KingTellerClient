package com.kingteller.client.bean.attendance;

import java.io.Serializable;
import java.util.List;

public class BusinessTripBean implements Serializable{

	private static final long serialVersionUID = -5625655203863887352L;
	
	private String saveflag;			//操作类型		zc：暂存		submit：提交		audit：审批通过		back：退回
	private String billType;			//单据类型
	
	private String travelId;			//出差单id
	private String travelNo;			//出差单单号
	
	private String createUserId;		//创建人id
	private String createUserName;		//创建人姓名
	private String createTimeStr;		//创建时间
	
	private String flowStatus;			// 流程环节
	private String travelTimes;			// 出差时长
	private String travelDay;			// 出差天数
	private String travelHour;			// 出差小时
	
	private String firstDayHour;		// 第一天时长
	private String endDayHour;			// 最后一天时长
	
	private String startTime;			// 开始时间	2015-11-25 08:30
	private String endTime;				// 结束时间	2015-11-25 08:30
	
	private String travelUserId;				// 出差人id
	private String travelUserName;				// 出差人姓名
	private String travelUserAccount;			// 出差人帐号
	
	private String travelDestination;			// 出差目的地
	private String travelReason;				// 出差原因
	private String exeRemark;					// 审批意见
	private List<HistoryOperation>		historyList;			//流程信息list
	
	private String code;						//获取状态信息
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

	public String getExeRemark() {
		return exeRemark;
	}

	public void setExeRemark(String exeRemark) {
		this.exeRemark = exeRemark;
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

	public String getTravelId() {
		return travelId;
	}

	public void setTravelId(String travelId) {
		this.travelId = travelId;
	}

	public String getTravelNo() {
		return travelNo;
	}

	public void setTravelNo(String travelNo) {
		this.travelNo = travelNo;
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

	public String getTravelTimes() {
		return travelTimes;
	}

	public void setTravelTimes(String travelTimes) {
		this.travelTimes = travelTimes;
	}

	public String getTravelDay() {
		return travelDay;
	}

	public void setTravelDay(String travelDay) {
		this.travelDay = travelDay;
	}

	public String getTravelHour() {
		return travelHour;
	}

	public void setTravelHour(String travelHour) {
		this.travelHour = travelHour;
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

	public String getTravelUserId() {
		return travelUserId;
	}

	public void setTravelUserId(String travelUserId) {
		this.travelUserId = travelUserId;
	}

	public String getTravelUserName() {
		return travelUserName;
	}

	public void setTravelUserName(String travelUserName) {
		this.travelUserName = travelUserName;
	}

	public String getTravelUserAccount() {
		return travelUserAccount;
	}

	public void setTravelUserAccount(String travelUserAccount) {
		this.travelUserAccount = travelUserAccount;
	}

	public String getTravelDestination() {
		return travelDestination;
	}

	public void setTravelDestination(String travelDestination) {
		this.travelDestination = travelDestination;
	}

	public String getTravelReason() {
		return travelReason;
	}

	public void setTravelReason(String travelReason) {
		this.travelReason = travelReason;
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
