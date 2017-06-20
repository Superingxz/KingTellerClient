package com.kingteller.client.bean.workorder;

import java.io.Serializable;

public class SendOrderBean implements Serializable{

	/**
	 * author jinyh
	 */
	private static final long serialVersionUID = 1L;
	private String orderId;//工单id
	private String orderNo;//工单号
	private String ATMCode;//机器编号(御银为机器编的号)
	private String ATMBankcode;//ATM号(银行为机器编的号)
	private String wdsbjc;//网点设备简称,维护工单(assignOrder)才会有值
	private String assignTime;//派单时间
	private String assignMan;
	private String troubleRemark;//:问题描述,其他事务和物流工单时为工作内容
	private String workAddress;//目的地
	private String orderType;//工单的类型，包括：维护工单maintenance ;其他事务 otherMatter ; 物流logistics;清洁clean"
	private String status;//工单状态：new-新单(20)，accept-接障(30)，arrive-到达(99)，begin-开始维护(40),finish-维护结束(1)
	private boolean doClean;//如果是维护工单，则true表示同时做清洁，否则不做清洁
	private String assignName;//派单人
	private Long backflag;//退回状态
	private String assignWorkerName;//维护人员
	private String cmFlag;//标志(1或null表示手机端,2表示pc端)
	
	private String createType;//P手动派单,PER邮政派单   XM 项目工单
	
	private String jqId;//添加机器ID
	/**
	 * 工单是否预约  1 表示预约  空或者0 表示正常派单
	 */
	private String arrangeType; 
	/**
	 *  预约时间
	 */
	private String prearrangeDateStr;
	
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
	public String getATMCode() {
		return ATMCode;
	}
	public void setATMCode(String aTMCode) {
		ATMCode = aTMCode;
	}
	public String getATMBankcode() {
		return ATMBankcode;
	}
	public void setATMBankcode(String aTMBankcode) {
		ATMBankcode = aTMBankcode;
	}
	public String getWdsbjc() {
		return wdsbjc;
	}
	public void setWdsbjc(String wdsbjc) {
		this.wdsbjc = wdsbjc;
	}
	public String getAssignTime() {
		return assignTime;
	}
	public void setAssignTime(String assignTime) {
		this.assignTime = assignTime;
	}
	public String getAssignMan() {
		return assignMan;
	}
	public void setAssignMan(String assignMan) {
		this.assignMan = assignMan;
	}
	public String getTroubleRemark() {
		return troubleRemark;
	}
	public void setTroubleRemark(String troubleRemark) {
		this.troubleRemark = troubleRemark;
	}
	public String getWorkAddress() {
		return workAddress;
	}
	public void setWorkAddress(String workAddress) {
		this.workAddress = workAddress;
	}
	public String getOrderType() {
		return orderType;
	}
	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public boolean isDoClean() {
		return doClean;
	}
	public void setDoClean(boolean doClean) {
		this.doClean = doClean;
	}
	public String getAssignName() {
		return assignName;
	}
	public void setAssignName(String assignName) {
		this.assignName = assignName;
	}
	public Long getBackflag() {
		return backflag;
	}
	public void setBackflag(Long backflag) {
		this.backflag = backflag;
	}
	public String getAssignWorkerName() {
		return assignWorkerName;
	}
	public void setAssignWorkerName(String assignWorkerName) {
		this.assignWorkerName = assignWorkerName;
	}
	public String getCmFlag() {
		return cmFlag;
	}
	public void setCmFlag(String cmFlag) {
		this.cmFlag = cmFlag;
	}
	public String getArrangeType() {
		return arrangeType;
	}
	public void setArrangeType(String arrangeType) {
		this.arrangeType = arrangeType;
	}
	public String getPrearrangeDateStr() {
		return prearrangeDateStr;
	}
	public void setPrearrangeDateStr(String prearrangeDateStr) {
		this.prearrangeDateStr = prearrangeDateStr;
	}
	public String getCreateType() {
		return createType;
	}
	public void setCreateType(String createType) {
		this.createType = createType;
	}
	public String getJqId() {
		return jqId;
	}
	public void setJqId(String jqId) {
		this.jqId = jqId;
	}
	
}
