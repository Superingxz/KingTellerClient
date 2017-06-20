package com.kingteller.client.bean.offlineupload;

import java.io.Serializable;
import java.util.List;

import com.kingteller.client.bean.qrcode.QRDotMachineBean;

public class OfflineSelectDataBean implements Serializable{
	
	private static final long serialVersionUID = 5373509635966295038L;
	
	private String orderId;//工单id
	private String orderNo;//工单单号
	private String jqid;//机器id
	private String jqbh;//机器编号
	private String code;
	
	
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
	public String getJqid() {
		return jqid;
	}
	public void setJqid(String jqid) {
		this.jqid = jqid;
	}
	public String getJqbh() {
		return jqbh;
	}
	public void setJqbh(String jqbh) {
		this.jqbh = jqbh;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	
	
}
