package com.kingteller.client.bean.map;

import java.io.Serializable;

public class ServiceStationLocationBean implements Serializable{

	private static final long serialVersionUID = -4848445358120908930L;
	
	private String dTotal;//总数量
	private String totalPage;//总页面
	private String orgid; //服务站ID
	private String jqbh; //机器编号
	private String ssdq; //机器状态
	private String ssqy; //机器状态名称
	private double  y; 
	private double  x;
	private String msg;//错误信息
	
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
	public String getOrgid() {
		return orgid;
	}
	public void setOrgid(String orgid) {
		this.orgid = orgid;
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
	public String getSsqy() {
		return ssqy;
	}
	public void setSsqy(String ssqy) {
		this.ssqy = ssqy;
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
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	
}
