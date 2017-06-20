package com.kingteller.client.bean.map;

import java.io.Serializable;

public class ATMLocationBean implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 891684427157083991L;
	
	private String dTotal;//总数量
	private String totalPage;//总页面
	private String jqid; //机器ID
	private String jqbh; //机器编号
	private String status; //机器状态
	private String statusName; //机器状态名称
	private String  jqgsrusername; //机器归属人
	private double  y; 
	private double  x;
	private String msg;//错误信息

	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
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
	public String getJqgsrusername() {
		return jqgsrusername;
	}
	public void setJqgsrusername(String jqgsrusername) {
		this.jqgsrusername = jqgsrusername;
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
