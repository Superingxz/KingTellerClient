package com.kingteller.client.bean.qrcode;

import java.io.Serializable;

public class QRServiceCheckBean implements Serializable {

	private static final long serialVersionUID = 3423380700494072682L;
	private String regId;//服务站物料出入库登记单id
	private String regFbillNo;//服务站物料出入库登记单编号
	private String auditFlowStatus;//流程值
	private String auditFlowStatusName;//流程名称
	private String registerPropertyName;//出入库性质：出库/入库
	private String totalOutQuantity;//已经登记出库物料数
	private String totalOutBarcodeQuantity;//已经登记出库物料已扫描条码数
	private String totalOutNullBarcodeQuantity;//已经登记出库物料无条码数
	
	private String totalInQuantity;//已经登记入库库物料数
	private String totalInBarcodeQuantity;//已经登记入库物料已扫描条码数
	private String totalInNullBarcodeQuantity;//已经登记入库物料无条码数
	
	private String wrName;//登记仓位
	private String fbillerName;//登记人员
	private String regDate;//登记时间
	private String typeName;//类型名称
	private String code;
	
	

	public String getRegisterPropertyName() {
		return registerPropertyName;
	}
	public void setRegisterPropertyName(String registerPropertyName) {
		this.registerPropertyName = registerPropertyName;
	}
	public String getTotalOutNullBarcodeQuantity() {
		return totalOutNullBarcodeQuantity;
	}
	public void setTotalOutNullBarcodeQuantity(String totalOutNullBarcodeQuantity) {
		this.totalOutNullBarcodeQuantity = totalOutNullBarcodeQuantity;
	}
	public String getTotalInNullBarcodeQuantity() {
		return totalInNullBarcodeQuantity;
	}
	public void setTotalInNullBarcodeQuantity(String totalInNullBarcodeQuantity) {
		this.totalInNullBarcodeQuantity = totalInNullBarcodeQuantity;
	}
	public String getRegId() {
		return regId;
	}
	public void setRegId(String regId) {
		this.regId = regId;
	}
	public String getRegFbillNo() {
		return regFbillNo;
	}
	public void setRegFbillNo(String regFbillNo) {
		this.regFbillNo = regFbillNo;
	}
	public String getAuditFlowStatus() {
		return auditFlowStatus;
	}
	public void setAuditFlowStatus(String auditFlowStatus) {
		this.auditFlowStatus = auditFlowStatus;
	}
	public String getAuditFlowStatusName() {
		return auditFlowStatusName;
	}
	public void setAuditFlowStatusName(String auditFlowStatusName) {
		this.auditFlowStatusName = auditFlowStatusName;
	}
	public String getTotalOutQuantity() {
		return totalOutQuantity;
	}
	public void setTotalOutQuantity(String totalOutQuantity) {
		this.totalOutQuantity = totalOutQuantity;
	}
	public String getTotalOutBarcodeQuantity() {
		return totalOutBarcodeQuantity;
	}
	public void setTotalOutBarcodeQuantity(String totalOutBarcodeQuantity) {
		this.totalOutBarcodeQuantity = totalOutBarcodeQuantity;
	}
	public String getTotalInQuantity() {
		return totalInQuantity;
	}
	public void setTotalInQuantity(String totalInQuantity) {
		this.totalInQuantity = totalInQuantity;
	}
	public String getTotalInBarcodeQuantity() {
		return totalInBarcodeQuantity;
	}
	public void setTotalInBarcodeQuantity(String totalInBarcodeQuantity) {
		this.totalInBarcodeQuantity = totalInBarcodeQuantity;
	}
	public String getWrName() {
		return wrName;
	}
	public void setWrName(String wrName) {
		this.wrName = wrName;
	}
	public String getFbillerName() {
		return fbillerName;
	}
	public void setFbillerName(String fbillerName) {
		this.fbillerName = fbillerName;
	}
	public String getRegDate() {
		return regDate;
	}
	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	
	
	
}
