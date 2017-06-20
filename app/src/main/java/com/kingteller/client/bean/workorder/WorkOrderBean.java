package com.kingteller.client.bean.workorder;

import java.io.Serializable;

/**
 * 任务单
 * @author jinyh
 * */

public class WorkOrderBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String orderId;//工单id
	private String orderNo;//工单号
	
	private String ATMCode;//机器编号(御银为机器编的号)
	private String ATMBankcode;//ATM号(银行为机器编的号)
	
	
	private String assignTime;//派单时间
	private String assignName;//派单人
	private String assignMan;
	
	private String orderType;//工单的类型(维护工单:maintenance, 其他事务:otherMatter, 物流:logistics, 清洁:clean)
	private String status;//工单状态(新单(20)：new, 接障(30):accept, 到达(99):arrive, 开始维护(40):begin, 维护结束(1):finish)
	private String troubleRemark;//:问题描述,其他事务和物流工单时为工作内容
	
	private boolean doClean;//是否做清洁(清洁:true, 不清洁:false)
	
	private Long backflag;//退回状态
	private String cmFlag;//标志(1:手机端, null:手机端  , "":手机端, 2:电脑端)
	
	private String reportFlowStatus;//审核人类型(信息管理员审核:20, 服务站负责人审核:30)
	private String createType;//派单类别(手动派单:P, 邮政派单:PER, 项目工单:XM)
	private String autoorderBanktype;//自动派单的银行类别(山东邮政:SDYZ, 广东邮政:GDYZ,"")
	
	private String workAddress;//目的地
	private String jqId;//机器id
	private String wdid;//网点id
	private String wdsbjc;//网点设备简称
	private String workOrgId;//服务站id
	private String workOrgName;//服务站名


	private String arrangeType; //工单是否预约(预约:1, "":正常派单 , 0:正常派单)
	private String prearrangeDateStr;//预约时间
	
	
	public String getWdid() {
		return wdid;
	}
	public void setWdid(String wdid) {
		this.wdid = wdid;
	}
	public String getWorkOrgId() {
		return workOrgId;
	}
	public void setWorkOrgId(String workOrgId) {
		this.workOrgId = workOrgId;
	}
	public String getWorkOrgName() {
		return workOrgName;
	}
	public void setWorkOrgName(String workOrgName) {
		this.workOrgName = workOrgName;
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
	public String getReportFlowStatus() {
		return reportFlowStatus;
	}
	public void setReportFlowStatus(String reportFlowStatus) {
		this.reportFlowStatus = reportFlowStatus;
	}
	public String getCreateType() {
		return createType;
	}
	public void setCreateType(String createType) {
		this.createType = createType;
	}
	public String getAutoorderBanktype() {
		return autoorderBanktype;
	}
	public void setAutoorderBanktype(String autoorderBanktype) {
		this.autoorderBanktype = autoorderBanktype;
	}
	public String getJqId() {
		return jqId;
	}
	public void setJqId(String jqId) {
		this.jqId = jqId;
	}
	
	
}
