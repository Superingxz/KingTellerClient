package com.kingteller.client.bean.workorder;

import java.io.Serializable;
import java.util.List;

public class CleanReportBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String  reportId;		//报告id(隐藏字段)
	private String  orderId;		//工单id(隐藏字段）
	private String  orderNo;		//工单号
	private String  workDateStr;	//清洁日期（workDate）
	private String  flowStatusName;	//流程状态
	private String  ssbscName;		//服务站
	private String  sszhName;		//所属银行
	private String  workerNames;	//执行工程师
	private String  wdsbjc;			//网点地址
	private String  assignOrderTimeStr;	//派单时间
	private String  arriveTimeStr;	//到达现场时间
	private String  maintainEndTimeStr;	//完成清洁时间
	private String  orderRemark;	//派单备注
	private Long  defraudTipsFlag;	//粘贴诈骗提示(0:否,1:是)
	private Long  otherFlag;	//加装不明信息(0:否,1:是)
	private Long  lampFaultFlag;	//灯箱损坏/照明不亮(0:否,1:是)
	private String  reportReamrk;	//清洁备注
	private List<PictureInfoBean> picList; //保存该清洁工单相关的图片volist
	private List<MachineInfoBean> jqList;//机器消息
	
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
	public String getFlowStatusName() {
		return flowStatusName;
	}
	public void setFlowStatusName(String flowStatusName) {
		this.flowStatusName = flowStatusName;
	}
	public String getSsbscName() {
		return ssbscName;
	}
	public void setSsbscName(String ssbscName) {
		this.ssbscName = ssbscName;
	}
	public String getSszhName() {
		return sszhName;
	}
	public void setSszhName(String sszhName) {
		this.sszhName = sszhName;
	}
	public String getWorkerNames() {
		return workerNames;
	}
	public void setWorkerNames(String workerNames) {
		this.workerNames = workerNames;
	}
	public String getWdsbjc() {
		return wdsbjc;
	}
	public void setWdsbjc(String wdsbjc) {
		this.wdsbjc = wdsbjc;
	}
	public String getAssignOrderTimeStr() {
		return assignOrderTimeStr;
	}
	public void setAssignOrderTimeStr(String assignOrderTimeStr) {
		this.assignOrderTimeStr = assignOrderTimeStr;
	}
	public String getArriveTimeStr() {
		return arriveTimeStr;
	}
	public void setArriveTimeStr(String arriveTimeStr) {
		this.arriveTimeStr = arriveTimeStr;
	}
	public String getMaintainEndTimeStr() {
		return maintainEndTimeStr;
	}
	public void setMaintainEndTimeStr(String maintainEndTimeStr) {
		this.maintainEndTimeStr = maintainEndTimeStr;
	}
	public String getOrderRemark() {
		return orderRemark;
	}
	public void setOrderRemark(String orderRemark) {
		this.orderRemark = orderRemark;
	}
	public Long getDefraudTipsFlag() {
		return defraudTipsFlag;
	}
	public void setDefraudTipsFlag(Long defraudTipsFlag) {
		this.defraudTipsFlag = defraudTipsFlag;
	}
	public Long getOtherFlag() {
		return otherFlag;
	}
	public void setOtherFlag(Long otherFlag) {
		this.otherFlag = otherFlag;
	}
	public Long getLampFaultFlag() {
		return lampFaultFlag;
	}
	public void setLampFaultFlag(Long lampFaultFlag) {
		this.lampFaultFlag = lampFaultFlag;
	}
	public String getReportReamrk() {
		return reportReamrk;
	}
	public void setReportReamrk(String reportReamrk) {
		this.reportReamrk = reportReamrk;
	}
	public List<PictureInfoBean> getPicList() {
		return picList;
	}
	public void setPicList(List<PictureInfoBean> picList) {
		this.picList = picList;
	}
	public List<MachineInfoBean> getJqList() {
		return jqList;
	}
	public void setJqList(List<MachineInfoBean> jqList) {
		this.jqList = jqList;
	}

}
