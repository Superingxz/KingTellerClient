package com.kingteller.client.bean.attendance;

import java.io.Serializable;

public class WorkAttendanceSearchLeaveFromBean implements Serializable{

	private static final long serialVersionUID = -2209042309544132245L;
	
	private String leaveId;            	// 请假id
	private String leaveNo;      		// 请假单号
	private String leaveTimes;      	// 请假时长
	private String leaveStartTimeStr;   // 请假开始时间
	private String leaveEndTimeStr;		// 请假结束时间
	private String code;
	
	
	public String getLeaveTimes() {
		return leaveTimes;
	}
	public void setLeaveTimes(String leaveTimes) {
		this.leaveTimes = leaveTimes;
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
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	
	
}
