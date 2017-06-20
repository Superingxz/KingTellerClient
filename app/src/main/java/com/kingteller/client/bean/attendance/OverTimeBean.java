package com.kingteller.client.bean.attendance;

import java.io.Serializable;
import java.util.List;

/**
 * 加班综合
 * @author Administrator
 */

public class OverTimeBean implements Serializable{
	
	private static final long serialVersionUID = 6123186400674727830L;

	private String saveflag;			//操作类型		zc：暂存		submit：提交		audit：审批通过		back：退回
	private String billType;			//单据类型
	
	private String overtimeId;			//加班单id
	private String overtimeNo;			//加班申请单号
	
	private String createUserId;		//创建人id
	private String createUserName;		//创建人姓名
	private String createTimeStr;		//创建时间
	
	private String flowStatus;			//流程环节
	private String overtimeType;		//加班类型			1：单人    2：多人
	private String overtimeDayExt;		//加班日期
	private String startTime;			//加班开始时间
	private String endTime;				//加班结束时间
	private String overtimeHourOne;		//加班时长（默认）
	private String overtimeHour;		//加班时长（合计）
	private String overtimeReason;		//加班原因
	private String exeRemark;			//审批意见
	private List<OverTimeSonPeople> 	sonList;				//加班人员list
	private List<HistoryOperation>		historyList;			//流程信息list
	
	private String code;				//获取状态信息
	private String nextUserAccounts;	//下一环节处理人账号
	private List<WorkAttendanceSearchPeopleBean> ulist;			//审批人list
	
	
	
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
	public String getBillType() {
		return billType;
	}
	public void setBillType(String billType) {
		this.billType = billType;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getSaveflag() {
		return saveflag;
	}
	public void setSaveflag(String saveflag) {
		this.saveflag = saveflag;
	}
	public String getOvertimeId() {
		return overtimeId;
	}
	public void setOvertimeId(String overtimeId) {
		this.overtimeId = overtimeId;
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
	public String getOvertimeNo() {
		return overtimeNo;
	}
	public void setOvertimeNo(String overtimeNo) {
		this.overtimeNo = overtimeNo;
	}
	public String getOvertimeType() {
		return overtimeType;
	}
	public void setOvertimeType(String overtimeType) {
		this.overtimeType = overtimeType;
	}
	public String getOvertimeDayExt() {
		return overtimeDayExt;
	}
	public void setOvertimeDayExt(String overtimeDayExt) {
		this.overtimeDayExt = overtimeDayExt;
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
	public String getOvertimeHourOne() {
		return overtimeHourOne;
	}
	public void setOvertimeHourOne(String overtimeHourOne) {
		this.overtimeHourOne = overtimeHourOne;
	}
	public String getOvertimeHour() {
		return overtimeHour;
	}
	public void setOvertimeHour(String overtimeHour) {
		this.overtimeHour = overtimeHour;
	}
	public String getOvertimeReason() {
		return overtimeReason;
	}
	public void setOvertimeReason(String overtimeReason) {
		this.overtimeReason = overtimeReason;
	}
	public String getExeRemark() {
		return exeRemark;
	}
	public void setExeRemark(String exeRemark) {
		this.exeRemark = exeRemark;
	}
	public List<OverTimeSonPeople> getSonList() {
		return sonList;
	}
	public void setSonList(List<OverTimeSonPeople> sonList) {
		this.sonList = sonList;
	}
	public List<HistoryOperation> getHistoryList() {
		return historyList;
	}
	public void setHistoryList(List<HistoryOperation> historyList) {
		this.historyList = historyList;
	}
	
	
	
}

