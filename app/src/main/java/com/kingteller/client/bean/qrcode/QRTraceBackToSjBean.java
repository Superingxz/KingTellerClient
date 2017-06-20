package com.kingteller.client.bean.qrcode;

import java.io.Serializable;
import java.util.List;

/**
 * 追溯事件
 * @author Administrator
 */
public class QRTraceBackToSjBean implements Serializable{
	
	private static final long serialVersionUID = -9015075694726050473L;

	private String stockName; //发货点
	private String senderName; //发送人
	private String sendDateStr; //发送时间
	
	private String deptName; //收货点
	private String recipientName; //接收人
	private String receiptDateStr; //接收时间
	
	private String billNo; //单号
	private String billTypeName; //单据类型
	private String description; //业务描述
	
	private String remark; //备注
	private String createDateStr; //创建时间
	


	public String getStockName() {
		return stockName;
	}

	public void setStockName(String stockName) {
		this.stockName = stockName;
	}

	public String getSenderName() {
		return senderName;
	}

	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}

	public String getSendDateStr() {
		return sendDateStr;
	}

	public void setSendDateStr(String sendDateStr) {
		this.sendDateStr = sendDateStr;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getRecipientName() {
		return recipientName;
	}

	public void setRecipientName(String recipientName) {
		this.recipientName = recipientName;
	}

	public String getReceiptDateStr() {
		return receiptDateStr;
	}

	public void setReceiptDateStr(String receiptDateStr) {
		this.receiptDateStr = receiptDateStr;
	}

	public String getBillNo() {
		return billNo;
	}

	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}

	public String getBillTypeName() {
		return billTypeName;
	}

	public void setBillTypeName(String billTypeName) {
		this.billTypeName = billTypeName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getCreateDateStr() {
		return createDateStr;
	}

	public void setCreateDateStr(String createDateStr) {
		this.createDateStr = createDateStr;
	}

	
}
