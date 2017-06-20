package com.kingteller.client.bean.qrcode;

import java.io.Serializable;

public class QRCargoScanBean implements Serializable {
	private static final long serialVersionUID = -1741134094284138424L;
	
	private String recDeliversWsFormsId;//出库物料ID
	private String recDeliversWsFormdtId;//收货物料ID
	private String wlId;//保修物料ID
	private String regFormDtId;//服务站登记出入库物料ID
	private String newCode;//物料编码
	private String wlName;//物料名称
	private String wlBarCode;//物料条码
	private String quantity;//物料数量
	private String barcodeCount;//有条码数
	private String nullBarcodeCount;//没条码数
	private String unscanBarcodeCount;//未扫描条码数
	private String fsourceBillNo;//源单号
	private String regFormsType;//服务站登记出入库物料类型
	private String replaceType;//网点机器部件更换物料类型  0：旧物料 ,1：新物料
	private String isNewadd;//是否新增物料
	private String isNewaddSl;//是否新增数量
	private String isNewaddTms;//是否新增条码数
	private String isReplace;//是否被替换
	private String isScaned;//判断是否收货验证
	private String code;
	
	
	public String getFsourceBillNo() {
		return fsourceBillNo;
	}
	public void setFsourceBillNo(String fsourceBillNo) {
		this.fsourceBillNo = fsourceBillNo;
	}
	public String getReplaceType() {
		return replaceType;
	}
	public void setReplaceType(String replaceType) {
		this.replaceType = replaceType;
	}
	public String getIsReplace() {
		return isReplace;
	}
	public void setIsReplace(String isReplace) {
		this.isReplace = isReplace;
	}
	public String getUnscanBarcodeCount() {
		return unscanBarcodeCount;
	}
	public void setUnscanBarcodeCount(String unscanBarcodeCount) {
		this.unscanBarcodeCount = unscanBarcodeCount;
	}
	public String getNullBarcodeCount() {
		return nullBarcodeCount;
	}
	public void setNullBarcodeCount(String nullBarcodeCount) {
		this.nullBarcodeCount = nullBarcodeCount;
	}
	public String getRegFormDtId() {
		return regFormDtId;
	}
	public void setRegFormDtId(String regFormDtId) {
		this.regFormDtId = regFormDtId;
	}
	public String getRegFormsType() {
		return regFormsType;
	}
	public void setRegFormsType(String regFormsType) {
		this.regFormsType = regFormsType;
	}
	public String getWlId() {
		return wlId;
	}
	public void setWlId(String wlId) {
		this.wlId = wlId;
	}
	public String getRecDeliversWsFormdtId() {
		return recDeliversWsFormdtId;
	}
	public void setRecDeliversWsFormdtId(String recDeliversWsFormdtId) {
		this.recDeliversWsFormdtId = recDeliversWsFormdtId;
	}
	public String getWlBarCode() {
		return wlBarCode;
	}
	public void setWlBarCode(String wlBarCode) {
		this.wlBarCode = wlBarCode;
	}
	public String getIsScaned() {
		return isScaned;
	}
	public void setIsScaned(String isScaned) {
		this.isScaned = isScaned;
	}
	public String getIsNewaddSl() {
		return isNewaddSl;
	}
	public void setIsNewaddSl(String isNewaddSl) {
		this.isNewaddSl = isNewaddSl;
	}
	public String getIsNewaddTms() {
		return isNewaddTms;
	}
	public void setIsNewaddTms(String isNewaddTms) {
		this.isNewaddTms = isNewaddTms;
	}
	public String getIsNewadd() {
		return isNewadd;
	}
	public void setIsNewadd(String isNewadd) {
		this.isNewadd = isNewadd;
	}
	public String getRecDeliversWsFormsId() {
		return recDeliversWsFormsId;
	}
	public void setRecDeliversWsFormsId(String recDeliversWsFormsId) {
		this.recDeliversWsFormsId = recDeliversWsFormsId;
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
	public String getQuantity() {
		return quantity;
	}
	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}
	public String getBarcodeCount() {
		return barcodeCount;
	}
	public void setBarcodeCount(String barcodeCount) {
		this.barcodeCount = barcodeCount;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	
}
