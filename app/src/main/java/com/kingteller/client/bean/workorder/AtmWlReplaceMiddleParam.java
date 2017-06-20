package com.kingteller.client.bean.workorder;

import java.io.Serializable;

public class AtmWlReplaceMiddleParam implements Serializable {

	private static final long serialVersionUID = 6409415348639480464L;
	
	private String middleId;         // 主键ID
	private String wlBarcode;        // 物料二维码
	private String wlInfoId;         // 物料总类ID
	private String newCode;          // 物料编码
	private String wlName;           // 物料名称
	private Long isNew;              // 新旧物料0：旧，1：新
	private String createUserId;     // 创建人ID
	private String createDate;         // 创建时间
	private String createDateRange1;
	private String createDateRange2;
	private  String createDateStr;
	private String orderId;          // 维护工单ID
	private String jqId;             // 机器ID
	private Long status;             // 状态0：失效，1：正常
	private String expand1;          // 扩展1
	private String expand2;          // 扩展2
	private String wlTypeId;         //物料类别ID
	
	public String getMiddleId() {
		return middleId;
	}
	public void setMiddleId(String middleId) {
		this.middleId = middleId;
	}
	public String getWlBarcode() {
		return wlBarcode;
	}
	public void setWlBarcode(String wlBarcode) {
		this.wlBarcode = wlBarcode;
	}
	public String getWlInfoId() {
		return wlInfoId;
	}
	public void setWlInfoId(String wlInfoId) {
		this.wlInfoId = wlInfoId;
	}
	public String getNewCode() {
		return newCode;
	}
	public void setNewCode(String newCode) {
		this.newCode = newCode;
	}
	public String getWlName() {
		return wlName;
	}
	public void setWlName(String wlName) {
		this.wlName = wlName;
	}
	public Long getIsNew() {
		return isNew;
	}
	public void setIsNew(Long isNew) {
		this.isNew = isNew;
	}
	public String getCreateUserId() {
		return createUserId;
	}
	public void setCreateUserId(String createUserId) {
		this.createUserId = createUserId;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public String getCreateDateRange1() {
		return createDateRange1;
	}
	public void setCreateDateRange1(String createDateRange1) {
		this.createDateRange1 = createDateRange1;
	}
	public String getCreateDateRange2() {
		return createDateRange2;
	}
	public void setCreateDateRange2(String createDateRange2) {
		this.createDateRange2 = createDateRange2;
	}
	public String getCreateDateStr() {
		return createDateStr;
	}
	public void setCreateDateStr(String createDateStr) {
		this.createDateStr = createDateStr;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getJqId() {
		return jqId;
	}
	public void setJqId(String jqId) {
		this.jqId = jqId;
	}
	public Long getStatus() {
		return status;
	}
	public void setStatus(Long status) {
		this.status = status;
	}
	public String getExpand1() {
		return expand1;
	}
	public void setExpand1(String expand1) {
		this.expand1 = expand1;
	}
	public String getExpand2() {
		return expand2;
	}
	public void setExpand2(String expand2) {
		this.expand2 = expand2;
	}
	public String getWlTypeId() {
		return wlTypeId;
	}
	public void setWlTypeId(String wlTypeId) {
		this.wlTypeId = wlTypeId;
	}


}


