package com.kingteller.client.bean.map;

import java.io.Serializable;

public class ATMInfoWindowBean implements Serializable {

	private static final long serialVersionUID = 3713639203406128041L;
	//机器编号 ，所属大区，所属银行，机器状态，所属区域，所属支行，ATM号，所属服务站，机器归属人，网点设备简称，网点地址，定位地址
	private String jqid; //机器ID
	private String jqbh; //机器编号
	private String ssdq;//所属大区
	private String ssyh;//所属银行
	private String status; //机器状态
	private String statusName;//机器状态名称
	private String ssqy;//所属区域
	private String sszh;//所属支行
	private String atmh;//ATM号
	private String fwz;//所属服务站
	private String jqgsrusername; //机器归属人
	private String wdsbjc;//网点设备简称
	private String wddz;//网点地址
	private String dwdz;//定位地址
	
	private String fileid;
	private String dTotal;//总数量
	private String totalPage;//总页面
	
	private String msg;//错误信息
	private double  y; 
	private double  x;
	
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
	public String getSsdq() {
		return ssdq;
	}
	public void setSsdq(String ssdq) {
		this.ssdq = ssdq;
	}
	public String getSsyh() {
		return ssyh;
	}
	public void setSsyh(String ssyh) {
		this.ssyh = ssyh;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getStatusName() {
		return statusName;
	}
	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}
	public String getSsqy() {
		return ssqy;
	}
	public void setSsqy(String ssqy) {
		this.ssqy = ssqy;
	}
	public String getSszh() {
		return sszh;
	}
	public void setSszh(String sszh) {
		this.sszh = sszh;
	}
	public String getAtmh() {
		return atmh;
	}
	public void setAtmh(String atmh) {
		this.atmh = atmh;
	}
	public String getFwz() {
		return fwz;
	}
	public void setFwz(String fwz) {
		this.fwz = fwz;
	}
	public String getJqgsrusername() {
		return jqgsrusername;
	}
	public void setJqgsrusername(String jqgsrusername) {
		this.jqgsrusername = jqgsrusername;
	}
	public String getWdsbjc() {
		return wdsbjc;
	}
	public void setWdsbjc(String wdsbjc) {
		this.wdsbjc = wdsbjc;
	}
	public String getWddz() {
		return wddz;
	}
	public void setWddz(String wddz) {
		this.wddz = wddz;
	}
	public String getDwdz() {
		return dwdz;
	}
	public void setDwdz(String dwdz) {
		this.dwdz = dwdz;
	}
	public String getFileid() {
		return fileid;
	}
	public void setFileid(String fileid) {
		this.fileid = fileid;
	}
	public String getdTotal() {
		return dTotal;
	}
	public void setdTotal(String dTotal) {
		this.dTotal = dTotal;
	}
	public String getTotalPage() {
		return totalPage;
	}
	public void setTotalPage(String totalPage) {
		this.totalPage = totalPage;
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
