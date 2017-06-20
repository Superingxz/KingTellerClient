package com.kingteller.client.bean.qrcode;

import java.io.Serializable;

public class QRDeliveryBean implements Serializable {

	private static final long serialVersionUID = -4984955254636629714L;
	
	private String recDeliversWsId;//出库单id
	private String fbillNo;//出库单编号
	private String fdcstockName;//发货仓库
	private String fdeptName;//收货仓库
	private String flowStatusName;//流程状态
	private String fbillerName;//制单人名称
	private String fbillCaptionName;//单据类型
	private String wlTotal;//物料总数
	private String barcodeCount;//有条码
	private String nullBarcodeCount;//没条码
	private String createDateStr;//制单时间
	private String code;
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	
	public String getNullBarcodeCount() {
		return nullBarcodeCount;
	}
	public void setNullBarcodeCount(String nullBarcodeCount) {
		this.nullBarcodeCount = nullBarcodeCount;
	}
	public String getWlTotal() {
		return wlTotal;
	}
	public void setWlTotal(String wlTotal) {
		this.wlTotal = wlTotal;
	}
	public String getBarcodeCount() {
		return barcodeCount;
	}
	public void setBarcodeCount(String barcodeCount) {
		this.barcodeCount = barcodeCount;
	}
	public String getRecDeliversWsId() {
		return recDeliversWsId;
	}
	public void setRecDeliversWsId(String recDeliversWsId) {
		this.recDeliversWsId = recDeliversWsId;
	}
	public String getFbillNo() {
		return fbillNo;
	}
	public void setFbillNo(String fbillNo) {
		this.fbillNo = fbillNo;
	}
	public String getFdcstockName() {
		return fdcstockName;
	}
	public void setFdcstockName(String fdcstockName) {
		this.fdcstockName = fdcstockName;
	}
	public String getFdeptName() {
		return fdeptName;
	}
	public void setFdeptName(String fdeptName) {
		this.fdeptName = fdeptName;
	}
	public String getFlowStatusName() {
		return flowStatusName;
	}
	public void setFlowStatusName(String flowStatusName) {
		this.flowStatusName = flowStatusName;
	}
	public String getFbillerName() {
		return fbillerName;
	}
	public void setFbillerName(String fbillerName) {
		this.fbillerName = fbillerName;
	}
	public String getFbillCaptionName() {
		return fbillCaptionName;
	}
	public void setFbillCaptionName(String fbillCaptionName) {
		this.fbillCaptionName = fbillCaptionName;
	}
	public String getCreateDateStr() {
		return createDateStr;
	}
	public void setCreateDateStr(String createDateStr) {
		this.createDateStr = createDateStr;
	}
	
	
	
}
