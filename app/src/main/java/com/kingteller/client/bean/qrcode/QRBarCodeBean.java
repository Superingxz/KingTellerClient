package com.kingteller.client.bean.qrcode;

import java.io.Serializable;

public class QRBarCodeBean implements Serializable {

	private static final long serialVersionUID = -7989529759738604091L;

	private String wlInfoId;//物料ID
	private String barCode;//二维码
	private String wlName;//物料名称
	private String newCode;//物料编码
	private String modelNo;//规格型号
	private String stockQuantity;//库存数量
	private String barcodeCount;//已录入数量
	private String nullBarcodeCount;//未录入数量
	private String wlDotType;//机器物料种类 
	private String smZt;//扫描状态
	private String code;//信息

	
	
	public String getWlDotType() {
		return wlDotType;
	}
	public void setWlDotType(String wlDotType) {
		this.wlDotType = wlDotType;
	}
	public String getStockQuantity() {
		return stockQuantity;
	}
	public void setStockQuantity(String stockQuantity) {
		this.stockQuantity = stockQuantity;
	}
	public String getBarcodeCount() {
		return barcodeCount;
	}
	public void setBarcodeCount(String barcodeCount) {
		this.barcodeCount = barcodeCount;
	}
	public String getNullBarcodeCount() {
		return nullBarcodeCount;
	}
	public void setNullBarcodeCount(String nullBarcodeCount) {
		this.nullBarcodeCount = nullBarcodeCount;
	}
	public String getBarCode() {
		return barCode;
	}
	public void setBarCode(String barCode) {
		this.barCode = barCode;
	}
	public String getSmZt() {
		return smZt;
	}
	public void setSmZt(String smZt) {
		this.smZt = smZt;
	}
	public String getWlInfoId() {
		return wlInfoId;
	}
	public void setWlInfoId(String wlInfoId) {
		this.wlInfoId = wlInfoId;
	}
	public String getWlName() {
		return wlName;
	}
	public void setWlName(String wlName) {
		this.wlName = wlName;
	}
	public String getNewCode() {
		return newCode;
	}
	public void setNewCode(String newCode) {
		this.newCode = newCode;
	}
	public String getModelNo() {
		return modelNo;
	}
	public void setModelNo(String modelNo) {
		this.modelNo = modelNo;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	
}
