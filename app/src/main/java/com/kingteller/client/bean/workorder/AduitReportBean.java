package com.kingteller.client.bean.workorder;

import java.io.Serializable;

public class AduitReportBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String id;			//报告流程表id
	private String reportId;	//报告id
	private String orderId;		//工单id
	private String orderNo;		//工单号
	private String exeUserId;	//提交人id
	private String exeUserName;	//提交人名称
	private Long taskStatus;    // 任务状态：0 未执行 1 已经执行
	private Long flowStatus;    // 流程环节：10 暂存 20 服务站负责人待审核 30 信息管理部待审核  1 流程完成
	private String reportProperty; //属于维护工单还是其他事务单  OTHERMATTER or order  xm
	private String submitTime;	//提交时间 
	private String ATMCode; //机器编号(御银为机器编的号)
	private String troubleRemark; //任务信息

	//private String jqlb;//及其类别
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}	
	public String getReportId() {
		return reportId;
	}
	public void setReportId(String reportId) {
		this.reportId = reportId;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public String getExeUserId() {
		return exeUserId;
	}
	public void setExeUserId(String exeUserId) {
		this.exeUserId = exeUserId;
	}
	public String getExeUserName() {
		return exeUserName;
	}
	public void setExeUserName(String exeUserName) {
		this.exeUserName = exeUserName;
	}
	public Long getTaskStatus() {
		return taskStatus;
	}
	public void setTaskStatus(Long taskStatus) {
		this.taskStatus = taskStatus;
	}
	public Long getFlowStatus() {
		return flowStatus;
	}
	public void setFlowStatus(Long flowStatus) {
		this.flowStatus = flowStatus;
	}
	public String getReportProperty() {
		return reportProperty;
	}
	public void setReportProperty(String reportProperty) {
		this.reportProperty = reportProperty;
	}
	public String getSubmitTime() {
		return submitTime;
	}
	public void setSubmitTime(String submitTime) {
		this.submitTime = submitTime;
	}
	public String getATMCode() {
		return ATMCode;
	}
	public void setATMCode(String aTMCode) {
		ATMCode = aTMCode;
	}
	public String getTroubleRemark() {
		return troubleRemark;
	}
	public void setTroubleRemark(String troubleRemark) {
		this.troubleRemark = troubleRemark;
	}
	

}
