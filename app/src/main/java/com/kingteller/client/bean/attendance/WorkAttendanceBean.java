package com.kingteller.client.bean.attendance;

import java.io.Serializable;

import com.kingteller.R;
public class WorkAttendanceBean implements Serializable {

	private static final long serialVersionUID = -2040720707271887793L;

	
	private String billId;			//单据id
	private String billNo;			//单据单号
	private String billType;		//单据类型 		kqmgrOvertimeFlow：加班	kqmgrLeaveFlow：请假
									//			kqmgrSickleaveFlow：销假	kqmgrAbsenteeismFlow：旷工
									//			kqmgrFILLFlow：补登		kqmgrTravelFlow：出差
	
	private String flowStatus;		//流程环节
	private String flowPosition;	//流程状态		ZC：暂存	SPZ：审批中	TH：退回	YWC：已完成
	private String bodyCuserName;	//单据主体人姓名
	private String dateContent;		//时间描述
	private String allContent;		//综合描述
	private String othContent;		//原因说明
	private String code;
	
	
	
	public String getBillNo() {
		return billNo;
	}
	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}
	public String getBillId() {
		return billId;
	}
	public void setBillId(String billId) {
		this.billId = billId;
	}
	public String getBillType() {
		return billType;
	}
	public void setBillType(String billType) {
		this.billType = billType;
	}
	public String getFlowStatus() {
		return flowStatus;
	}
	public void setFlowStatus(String flowStatus) {
		this.flowStatus = flowStatus;
	}
	public String getFlowPosition() {
		return flowPosition;
	}
	public void setFlowPosition(String flowPosition) {
		this.flowPosition = flowPosition;
	}
	public String getBodyCuserName() {
		return bodyCuserName;
	}
	public void setBodyCuserName(String bodyCuserName) {
		this.bodyCuserName = bodyCuserName;
	}
	public String getDateContent() {
		return dateContent;
	}
	public void setDateContent(String dateContent) {
		this.dateContent = dateContent;
	}
	public String getAllContent() {
		return allContent;
	}
	public void setAllContent(String allContent) {
		this.allContent = allContent;
	}
	public String getOthContent() {
		return othContent;
	}
	public void setOthContent(String othContent) {
		this.othContent = othContent;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	
	
}
