package com.kingteller.client.bean.qrcode;

import java.io.Serializable;

public class QRReceiptBean  implements Serializable {
	
	private static final long serialVersionUID = 4664219767840651773L;
	
	private String deliversWarehouseId;//收货单id
	private String billNo;//收货编号
	private String fdcstockName;//发货仓库
	private String fdeptName;//收货仓库
	private String fsmanagerName;//发货人
	private String fdateStr;//发货时间
	private String wlTotal;//物料总数
	private String barcodeCount;//条码数总
	private String unscanBarcodeCount;//未验证条码数
	private String code;
	
	
	public String getBarcodeCount() {
		return barcodeCount;
	}
	public void setBarcodeCount(String barcodeCount) {
		this.barcodeCount = barcodeCount;
	}
	public String getWlTotal() {
		return wlTotal;
	}
	public void setWlTotal(String wlTotal) {
		this.wlTotal = wlTotal;
	}
	public String getUnscanBarcodeCount() {
		return unscanBarcodeCount;
	}
	public void setUnscanBarcodeCount(String unscanBarcodeCount) {
		this.unscanBarcodeCount = unscanBarcodeCount;
	}
	public String getFdateStr() {
		return fdateStr;
	}
	public void setFdateStr(String fdateStr) {
		this.fdateStr = fdateStr;
	}
	public String getDeliversWarehouseId() {
		return deliversWarehouseId;
	}
	public void setDeliversWarehouseId(String deliversWarehouseId) {
		this.deliversWarehouseId = deliversWarehouseId;
	}
	public String getBillNo() {
		return billNo;
	}
	public void setBillNo(String billNo) {
		this.billNo = billNo;
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
	public String getFsmanagerName() {
		return fsmanagerName;
	}
	public void setFsmanagerName(String fsmanagerName) {
		this.fsmanagerName = fsmanagerName;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	
	
	
}
