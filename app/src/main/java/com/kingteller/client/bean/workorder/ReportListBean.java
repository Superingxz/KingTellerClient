package com.kingteller.client.bean.workorder;

import java.io.Serializable;

public class ReportListBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String orderId; // 维护工单id
	private String reportId; // 工单id
	private String orderNo;	 //工单号
	private String machineInfo;	//机器信息（ATM号+机器编号）
	private String bankAddress;	//银行地址
	private String workTypeStr;	//工作类别
	private String assignUserName;//分派人
	private String assignOrderTimeStr; // 派单日期时间
	private String reportType;	//报告类型
	private String reportTypeStr;	//报告类型名称
	
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getReportId() {
		return reportId;
	}
	public void setReportId(String reportId) {
		this.reportId = reportId;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public String getMachineInfo() {
		return machineInfo;
	}
	public void setMachineInfo(String machineInfo) {
		this.machineInfo = machineInfo;
	}
	public String getBankAddress() {
		return bankAddress;
	}
	public void setBankAddress(String bankAddress) {
		this.bankAddress = bankAddress;
	}
	public String getWorkTypeStr() {
		return workTypeStr;
	}
	public void setWorkTypeStr(String workTypeStr) {
		this.workTypeStr = workTypeStr;
	}
	public String getAssignUserName() {
		return assignUserName;
	}
	public void setAssignUserName(String assignUserName) {
		this.assignUserName = assignUserName;
	}

	public String getAssignOrderTimeStr() {
		return assignOrderTimeStr;
	}
	public void setAssignOrderTimeStr(String assignOrderTimeStr) {
		this.assignOrderTimeStr = assignOrderTimeStr;
	}

	public String getReportType() {
		return reportType;
	}
	public void setReportType(String reportType) {
		this.reportType = reportType;
	}
	public String getReportTypeStr() {
		return reportTypeStr;
	}
	public void setReportTypeStr(String reportTypeStr) {
		this.reportTypeStr = reportTypeStr;
	}

	
}
