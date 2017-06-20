package com.kingteller.client.bean.workorder;

import java.io.Serializable;

public class MachineInfoBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String id;            // 主键id
	private String idLike;
	private String itemExeId;     // 执行记录主表id
	private String itemExeIdLike;
	private String year;          // 年份
	private String yearLike;
	private String month;         // 月度
	private String monthLike;
	private String orderId;       // 工单id
	private String orderIdLike;
	private String reportId;      // 工作报告id
	private String reportIdLike;
	private String jqid;          // 机器id
	private String jqidLike;
	private String jqbh;          // 机器编号
	private String jqbhLike;
	private String atmh;          // ATM号
	private String atmhLike;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getIdLike() {
		return idLike;
	}
	public void setIdLike(String idLike) {
		this.idLike = idLike;
	}
	public String getItemExeId() {
		return itemExeId;
	}
	public void setItemExeId(String itemExeId) {
		this.itemExeId = itemExeId;
	}
	public String getItemExeIdLike() {
		return itemExeIdLike;
	}
	public void setItemExeIdLike(String itemExeIdLike) {
		this.itemExeIdLike = itemExeIdLike;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getYearLike() {
		return yearLike;
	}
	public void setYearLike(String yearLike) {
		this.yearLike = yearLike;
	}
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	public String getMonthLike() {
		return monthLike;
	}
	public void setMonthLike(String monthLike) {
		this.monthLike = monthLike;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getOrderIdLike() {
		return orderIdLike;
	}
	public void setOrderIdLike(String orderIdLike) {
		this.orderIdLike = orderIdLike;
	}
	public String getReportId() {
		return reportId;
	}
	public void setReportId(String reportId) {
		this.reportId = reportId;
	}
	public String getReportIdLike() {
		return reportIdLike;
	}
	public void setReportIdLike(String reportIdLike) {
		this.reportIdLike = reportIdLike;
	}
	public String getJqid() {
		return jqid;
	}
	public void setJqid(String jqid) {
		this.jqid = jqid;
	}
	public String getJqidLike() {
		return jqidLike;
	}
	public void setJqidLike(String jqidLike) {
		this.jqidLike = jqidLike;
	}
	public String getJqbh() {
		return jqbh;
	}
	public void setJqbh(String jqbh) {
		this.jqbh = jqbh;
	}
	public String getJqbhLike() {
		return jqbhLike;
	}
	public void setJqbhLike(String jqbhLike) {
		this.jqbhLike = jqbhLike;
	}
	public String getAtmh() {
		return atmh;
	}
	public void setAtmh(String atmh) {
		this.atmh = atmh;
	}
	public String getAtmhLike() {
		return atmhLike;
	}
	public void setAtmhLike(String atmhLike) {
		this.atmhLike = atmhLike;
	}

}
