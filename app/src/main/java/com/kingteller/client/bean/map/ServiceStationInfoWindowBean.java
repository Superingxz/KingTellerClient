package com.kingteller.client.bean.map;

import java.io.Serializable;

public class ServiceStationInfoWindowBean implements Serializable {

	private static final long serialVersionUID = 1909473268563198323L;
	//所属大区，所属区域，服务站名，机器数量，服务站负责人数，维护工程师人数，定位地址
	private String ssdq; //所属大区
	private String ssqy; //所属区域
	private String fwz;//服务站名
	private String machinecount;//机器数量
	private String fwzfzrcount; //服务站负责人数
	private String whgcscount ;//维护工程师人数
	private String dwdz;//定位地址
	
	private String msg;//错误信息
	private double  y; 
	private double  x;
	
	public String getSsdq() {
		return ssdq;
	}
	public void setSsdq(String ssdq) {
		this.ssdq = ssdq;
	}
	public String getSsqy() {
		return ssqy;
	}
	public void setSsqy(String ssqy) {
		this.ssqy = ssqy;
	}
	public String getFwz() {
		return fwz;
	}
	public void setFwz(String fwz) {
		this.fwz = fwz;
	}
	public String getMachinecount() {
		return machinecount;
	}
	public void setMachinecount(String machinecount) {
		this.machinecount = machinecount;
	}
	public String getFwzfzrcount() {
		return fwzfzrcount;
	}
	public void setFwzfzrcount(String fwzfzrcount) {
		this.fwzfzrcount = fwzfzrcount;
	}
	public String getWhgcscount() {
		return whgcscount;
	}
	public void setWhgcscount(String whgcscount) {
		this.whgcscount = whgcscount;
	}
	public String getDwdz() {
		return dwdz;
	}
	public void setDwdz(String dwdz) {
		this.dwdz = dwdz;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public double getY() {
		return y;
	}
	public void setY(double y) {
		this.y = y;
	}
	public double getX() {
		return x;
	}
	public void setX(double x) {
		this.x = x;
	}
	

}
