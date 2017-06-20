package com.kingteller.client.bean.attendance;

import java.io.Serializable;
import java.util.List;

public class LeaveBean implements Serializable{

	private static final long serialVersionUID = 8067255394816904130L;
	
	private String saveflag;			//操作类型		zc：暂存		submit：提交		audit：审批通过		back：退回
	private String billType;			//单据类型
	
	private String leaveId;				//请假单id
	private String leaveNo;				//请假单单号
	
	private String createUserId;		//创建人id
	private String createUserName;		//创建人姓名
	private String createTimeStr;		//创建时间
	
	private String flowStatus;			// 流程环节
	private String leaveTimes;			// 请假时长
	private String leaveDay;			// 请假天数
	private String leaveHour;			// 请假小时
	
	private String firstDayHour;		// 第一天时长
	private String endDayHour;			// 最后一天时长
	
	private String startTime;			// 开始时间	2015-11-25 08:30
	private String endTime;				// 结束时间	2015-11-25 08:30

	private String leaveUserId;			// 请假人id
	private String leaveUserName;		// 请假人姓名
	private String leaveUserAccount;	// 请假人帐号
	
	private String leaveType;			// 请假类型
	private String leaveName;			// 请假类型名称
	
	private String leaveReason;			// 请假原因
	private String exeRemark;			// 审批意见
	
	private List<HistoryOperation>		historyList;			//流程信息list
	private List<LeavePicListBean>		picList;				//图片信息list
	
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

	public List<LeavePicListBean> getPicList() {
		return picList;
	}

	public void setPicList(List<LeavePicListBean> picList) {
		this.picList = picList;
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

	public String getLeaveTimes() {
		return leaveTimes;
	}

	public void setLeaveTimes(String leaveTimes) {
		this.leaveTimes = leaveTimes;
	}

	public String getLeaveDay() {
		return leaveDay;
	}

	public void setLeaveDay(String leaveDay) {
		this.leaveDay = leaveDay;
	}

	public String getLeaveHour() {
		return leaveHour;
	}

	public void setLeaveHour(String leaveHour) {
		this.leaveHour = leaveHour;
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

	public String getLeaveUserId() {
		return leaveUserId;
	}

	public void setLeaveUserId(String leaveUserId) {
		this.leaveUserId = leaveUserId;
	}

	public String getLeaveUserName() {
		return leaveUserName;
	}

	public void setLeaveUserName(String leaveUserName) {
		this.leaveUserName = leaveUserName;
	}

	public String getLeaveUserAccount() {
		return leaveUserAccount;
	}

	public void setLeaveUserAccount(String leaveUserAccount) {
		this.leaveUserAccount = leaveUserAccount;
	}

	public String getLeaveType() {
		return leaveType;
	}

	public void setLeaveType(String leaveType) {
		this.leaveType = leaveType;
	}

	public String getLeaveName() {
		return leaveName;
	}

	public void setLeaveName(String leaveName) {
		this.leaveName = leaveName;
	}

	public String getLeaveReason() {
		return leaveReason;
	}

	public void setLeaveReason(String leaveReason) {
		this.leaveReason = leaveReason;
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
