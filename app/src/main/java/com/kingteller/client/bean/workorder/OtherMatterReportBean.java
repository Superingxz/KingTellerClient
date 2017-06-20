package com.kingteller.client.bean.workorder;

import java.io.Serializable;
import java.util.List;

/**
 *    "apList": [
            
        ],
        "areaName": "广佛区域",
        "assignDateStr": "2013-10-30  19:10:41",
        "bankName": "",
        "beginDateStr": "2013-10-30  19:10:48",
        "confirmDateStr": "2013-10-30  19:10:44",
        "endDateStr": "2013-10-30  19:10:52",
        "epList": [
            {
                "costDesc": "1",
                "costFee": 45,
                "costFeeStr": "",
                "descript": "4545",
                "id": "402881d84208f3930142090fbbc80141",
                "modeName": "",
                "reportId": "402881d84208f3930142090f12ae0138",
                "typeId": "402881d041ed95100141ed9d296c0009",
                "typeName": "",
                "userId": "000000003e829271013e82a9d67c0567",
                "username": "测试工程师(广州服务站)"
            },
        ],
        "linkName": "廖敏  ",
        "linkPhone": "023-62808412  ",
        "lppList": [
            
        ],
        "machineCode": "S/N61101350",
        "maintainRemark": "",
        "orderNo": "WH/20131030/85095",
        "recordUserName": "超级管理员",
        "troubleRemark": "重庆市南岸区昌隆花园",
        "wdName": "",
        "workAddress": "重庆市南岸区昌隆花园",
        "workDateStr": "2013-10-31",
        "workOrgName": "",
        "workType": "SEND_DATA"
 * */
public class OtherMatterReportBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<PickInfoBean> pickInfoList;
	private String areaName;
	private String assignDateStr;
	private String bankName;
	private String beginDateStr;
	private String confirmDateStr;
	private String endDateStr;
	private List<CostInfoBean> epList; 
	private String linkName;
	private String linkPhone;
	private List<AttachmentBean> attachList;
	private String machineCode;
	private String maintainRemark;
	private String orderNo;
	private String reportId;
	private String orderId;
	private String assignNames;
	private String remark;
	private String recordUserName;
	private String troubleRemark;
	private String wdName;
	private String workAddress;
	private String workDateStr;
	private String workOrgName;
	private String workType;
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
	public List<PickInfoBean> getPickInfoList() {
		return pickInfoList;
	}
	public void setPickInfoList(List<PickInfoBean> pickInfoList) {
		this.pickInfoList = pickInfoList;
	}
	
	
	public List<CostInfoBean> getEpList() {
		return epList;
	}
	public void setEpList(List<CostInfoBean> epList) {
		this.epList = epList;
	}

	/*
	 * public List<CostInfoBean> getCostInfoList() { return costInfoList; }
	 * public void setCostInfoList(List<CostInfoBean> costInfoList) {
	 * this.costInfoList = costInfoList; }
	 */
	public List<AttachmentBean> getAttachList() {
		return attachList;
	}
	public void setAttachList(List<AttachmentBean> attachList) {
		this.attachList = attachList;
	}
	
	public String getAreaName() {
		return areaName;
	}
	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}
	public String getAssignDateStr() {
		return assignDateStr;
	}
	public void setAssignDateStr(String assignDateStr) {
		this.assignDateStr = assignDateStr;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public String getBeginDateStr() {
		return beginDateStr;
	}
	public void setBeginDateStr(String beginDateStr) {
		this.beginDateStr = beginDateStr;
	}
	public String getConfirmDateStr() {
		return confirmDateStr;
	}
	public void setConfirmDateStr(String confirmDateStr) {
		this.confirmDateStr = confirmDateStr;
	}
	public String getEndDateStr() {
		return endDateStr;
	}
	public void setEndDateStr(String endDateStr) {
		this.endDateStr = endDateStr;
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
	public String getMachineCode() {
		return machineCode;
	}
	public void setMachineCode(String machineCode) {
		this.machineCode = machineCode;
	}
	public String getMaintainRemark() {
		return maintainRemark;
	}
	public void setMaintainRemark(String maintainRemark) {
		this.maintainRemark = maintainRemark;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public String getRecordUserName() {
		return recordUserName;
	}
	public void setRecordUserName(String recordUserName) {
		this.recordUserName = recordUserName;
	}
	public String getTroubleRemark() {
		return troubleRemark;
	}
	public void setTroubleRemark(String troubleRemark) {
		this.troubleRemark = troubleRemark;
	}
	public String getWdName() {
		return wdName;
	}
	public void setWdName(String wdName) {
		this.wdName = wdName;
	}
	public String getWorkAddress() {
		return workAddress;
	}
	public void setWorkAddress(String workAddress) {
		this.workAddress = workAddress;
	}
	public String getWorkDateStr() {
		return workDateStr;
	}
	public void setWorkDateStr(String workDateStr) {
		this.workDateStr = workDateStr;
	}
	public String getWorkOrgName() {
		return workOrgName;
	}
	public void setWorkOrgName(String workOrgName) {
		this.workOrgName = workOrgName;
	}
	public String getWorkType() {
		return workType;
	}
	public void setWorkType(String workType) {
		this.workType = workType;
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
