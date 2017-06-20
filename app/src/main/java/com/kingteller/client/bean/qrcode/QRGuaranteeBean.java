package com.kingteller.client.bean.qrcode;

import java.io.Serializable;

public class QRGuaranteeBean implements Serializable {
	
	private static final long serialVersionUID = 1878718831888545119L;
	private String repairReceiptId;//报修单id
	private String repairReceiptNo;//报修单编号
	private String fillinWrName;//报修仓位
	private String fillinPeopleName;//报修人员
	private String fillinDateStr;//报修时间
	private String scanned;//报修单是否为已扫描
	private String code;
	

	public String getScanned() {
		return scanned;
	}
	public void setScanned(String scanned) {
		this.scanned = scanned;
	}
	public String getRepairReceiptId() {
		return repairReceiptId;
	}
	public void setRepairReceiptId(String repairReceiptId) {
		this.repairReceiptId = repairReceiptId;
	}
	public String getRepairReceiptNo() {
		return repairReceiptNo;
	}
	public void setRepairReceiptNo(String repairReceiptNo) {
		this.repairReceiptNo = repairReceiptNo;
	}
	public String getFillinWrName() {
		return fillinWrName;
	}
	public void setFillinWrName(String fillinWrName) {
		this.fillinWrName = fillinWrName;
	}
	public String getFillinPeopleName() {
		return fillinPeopleName;
	}
	public void setFillinPeopleName(String fillinPeopleName) {
		this.fillinPeopleName = fillinPeopleName;
	}
	public String getFillinDateStr() {
		return fillinDateStr;
	}
	public void setFillinDateStr(String fillinDateStr) {
		this.fillinDateStr = fillinDateStr;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	
}
