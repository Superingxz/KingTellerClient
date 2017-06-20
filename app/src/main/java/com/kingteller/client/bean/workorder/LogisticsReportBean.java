package com.kingteller.client.bean.workorder;

import java.util.List;
import java.io.Serializable;

public class LogisticsReportBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String reportId;	//其他事务报告id(隐藏字段)
	private String orderId;		//对应工单id(隐藏字段)
	private String orderNo;		//工单号
	private String workDateStr;	//工作日期
	private String areaName;	//区域
	private String wdName;		//网点
	private String bankName;	//银行
	private Long assessoryNum;//配件数量
	private String packageflag;//包装是否损坏
	private String packageRemark;//包装损坏说明
	private String installAddress;//装机地址
	private Long picFlag;//是否已上传图片到亿美通，是1，否0
	private String workTypeName;//工单类型名称（隐藏字段）
	private String machineCode;//机器编号
	private String workOrgName;//所属服务站名称
	private String recordUserName;	//派单人
	private String assignDateStr;	//派单时间
	private String confirmDateStr;	//确认接收
	private String arrivalDateStr;	//达到现场
	private String beginDateStr;	//装车完毕
	private String endDateStr;		//物流完成
	private String requireDateStr;	//要求到达时间
	private Long machineNum;		//机器数量
	private String custName;		//客户名称
	private String linkName;		//联系人
	private String linkPhone;		//联系电话
	private String workType;		//工作类别(qxz：请选择，backMachine：退机，transferGoods：货运，other：其他方式，sendMachine：发机，moveMachine：移机)
	private String specialReq;		//特殊要求
	private String workAddress;		//发货地址
	private String workToAddress;	//调入地址
	private String troubleRemark;	//工作内容
	private String maintainRemark;	//备注
	private String assignNames;		//维护工程师
	private String remark;			//工单的备注
	private String receiver;		//接机人
	private String	receiverPhone;	//接机电话
	private String	receiverType;	//接机类型
	private List<CostInfoBean> epList; //费用类型
	private List<AttachmentBean> attachList;//附件类型
	private String auditContent; // 审核意见
	private String auditRemark; // 审核备注
	
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
	public String getWorkDateStr() {
		return workDateStr;
	}
	public void setWorkDateStr(String workDateStr) {
		this.workDateStr = workDateStr;
	}
	public String getAreaName() {
		return areaName;
	}
	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}
	public String getWdName() {
		return wdName;
	}
	public void setWdName(String wdName) {
		this.wdName = wdName;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	
	public Long getAssessoryNum() {
		return assessoryNum;
	}
	public void setAssessoryNum(Long assessoryNum) {
		this.assessoryNum = assessoryNum;
	}
	public String getPackageRemark() {
		return packageRemark;
	}
	public void setPackageRemark(String packageRemark) {
		this.packageRemark = packageRemark;
	}
	public String getInstallAddress() {
		return installAddress;
	}
	public void setInstallAddress(String installAddress) {
		this.installAddress = installAddress;
	}
	public Long getPicFlag() {
		return picFlag;
	}
	public void setPicFlag(Long picFlag) {
		this.picFlag = picFlag;
	}
	public String getWorkTypeName() {
		return workTypeName;
	}
	public void setWorkTypeName(String workTypeName) {
		this.workTypeName = workTypeName;
	}
	public String getMachineCode() {
		return machineCode;
	}
	public void setMachineCode(String machineCode) {
		this.machineCode = machineCode;
	}
	public String getWorkOrgName() {
		return workOrgName;
	}
	public void setWorkOrgName(String workOrgName) {
		this.workOrgName = workOrgName;
	}
	public String getRecordUserName() {
		return recordUserName;
	}
	public void setRecordUserName(String recordUserName) {
		this.recordUserName = recordUserName;
	}
	public String getPackageflag() {
		return packageflag;
	}
	public void setPackageflag(String packageflag) {
		this.packageflag = packageflag;
	}
	public String getAssignDateStr() {
		return assignDateStr;
	}
	public void setAssignDateStr(String assignDateStr) {
		this.assignDateStr = assignDateStr;
	}
	public String getConfirmDateStr() {
		return confirmDateStr;
	}
	public void setConfirmDateStr(String confirmDateStr) {
		this.confirmDateStr = confirmDateStr;
	}
	public String getArrivalDateStr() {
		return arrivalDateStr;
	}
	public void setArrivalDateStr(String arrivalDateStr) {
		this.arrivalDateStr = arrivalDateStr;
	}
	public String getBeginDateStr() {
		return beginDateStr;
	}
	public void setBeginDateStr(String beginDateStr) {
		this.beginDateStr = beginDateStr;
	}
	public String getEndDateStr() {
		return endDateStr;
	}
	public void setEndDateStr(String endDateStr) {
		this.endDateStr = endDateStr;
	}
	public String getRequireDateStr() {
		return requireDateStr;
	}
	public void setRequireDateStr(String requireDateStr) {
		this.requireDateStr = requireDateStr;
	}
	public Long getMachineNum() {
		return machineNum;
	}
	public void setMachineNum(Long machineNum) {
		this.machineNum = machineNum;
	}
	public String getCustName() {
		return custName;
	}
	public void setCustName(String custName) {
		this.custName = custName;
	}
	public String getLinkName() {
		return linkName;
	}
	public void setLinkName(String linkName) {
		this.linkName = linkName;
	}
	public String getLinkPhone() {
		return linkPhone;
	}
	public void setLinkPhone(String linkPhone) {
		this.linkPhone = linkPhone;
	}
	public String getWorkType() {
		return workType;
	}
	public void setWorkType(String workType) {
		this.workType = workType;
	}
	public String getSpecialReq() {
		return specialReq;
	}
	public void setSpecialReq(String specialReq) {
		this.specialReq = specialReq;
	}
	public String getWorkAddress() {
		return workAddress;
	}
	public void setWorkAddress(String workAddress) {
		this.workAddress = workAddress;
	}
	public String getWorkToAddress() {
		return workToAddress;
	}
	public void setWorkToAddress(String workToAddress) {
		this.workToAddress = workToAddress;
	}
	public String getTroubleRemark() {
		return troubleRemark;
	}
	public void setTroubleRemark(String troubleRemark) {
		this.troubleRemark = troubleRemark;
	}
	public String getMaintainRemark() {
		return maintainRemark;
	}
	public void setMaintainRemark(String maintainRemark) {
		this.maintainRemark = maintainRemark;
	}
	public String getAssignNames() {
		return assignNames;
	}
	public void setAssignNames(String assignNames) {
		this.assignNames = assignNames;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getReceiver() {
		return receiver;
	}
	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}
	public String getReceiverPhone() {
		return receiverPhone;
	}
	public void setReceiverPhone(String receiverPhone) {
		this.receiverPhone = receiverPhone;
	}
	public String getReceiverType() {
		return receiverType;
	}
	public void setReceiverType(String receiverType) {
		this.receiverType = receiverType;
	}

	public List<AttachmentBean> getAttachList() {
		return attachList;
	}
	public void setAttachList(List<AttachmentBean> attachList) {
		this.attachList = attachList;
	}
	public List<CostInfoBean> getEpList() {
		return epList;
	}
	public void setEpList(List<CostInfoBean> epList) {
		this.epList = epList;
	}
	public String getAuditContent() {
		return auditContent;
	}
	public void setAuditContent(String auditContent) {
		this.auditContent = auditContent;
	}
	public String getAuditRemark() {
		return auditRemark;
	}
	public void setAuditRemark(String auditRemark) {
		this.auditRemark = auditRemark;
	}
	
	
}
