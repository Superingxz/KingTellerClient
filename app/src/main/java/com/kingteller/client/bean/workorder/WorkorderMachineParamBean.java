package com.kingteller.client.bean.workorder;

import java.io.Serializable;

public class WorkorderMachineParamBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;            // 主键
	private String idLike;
	private String workorderid;   // 项目工单ID
	private String workorderidLike;
	private String machineid;     // 机器id
	private String machineidLike;
	private String jqbh;          // 机器编号
	private String jqbhLike;
	private String content; //项目主题
	private String whorderid;//维护工单对应字段
	private String exestatus;
	private String type; //项目类型字典值
	private String typename;//项目类型字典翻译值
	private String reportstatus;
	private String exeuserid;
	private String exeusername;
	private String exeuseraccount;
	private String orderno;
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
	public String getWorkorderid() {
		return workorderid;
	}
	public void setWorkorderid(String workorderid) {
		this.workorderid = workorderid;
	}
	public String getWorkorderidLike() {
		return workorderidLike;
	}
	public void setWorkorderidLike(String workorderidLike) {
		this.workorderidLike = workorderidLike;
	}
	public String getMachineid() {
		return machineid;
	}
	public void setMachineid(String machineid) {
		this.machineid = machineid;
	}
	public String getMachineidLike() {
		return machineidLike;
	}
	public void setMachineidLike(String machineidLike) {
		this.machineidLike = machineidLike;
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
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getWhorderid() {
		return whorderid;
	}
	public void setWhorderid(String whorderid) {
		this.whorderid = whorderid;
	}
	public String getExestatus() {
		return exestatus;
	}
	public void setExestatus(String exestatus) {
		this.exestatus = exestatus;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getTypename() {
		return typename;
	}
	public void setTypename(String typename) {
		this.typename = typename;
	}
	public String getReportstatus() {
		return reportstatus;
	}
	public void setReportstatus(String reportstatus) {
		this.reportstatus = reportstatus;
	}
	public String getExeuserid() {
		return exeuserid;
	}
	public void setExeuserid(String exeuserid) {
		this.exeuserid = exeuserid;
	}
	public String getExeusername() {
		return exeusername;
	}
	public void setExeusername(String exeusername) {
		this.exeusername = exeusername;
	}
	public String getExeuseraccount() {
		return exeuseraccount;
	}
	public void setExeuseraccount(String exeuseraccount) {
		this.exeuseraccount = exeuseraccount;
	}
	public String getOrderno() {
		return orderno;
	}
	public void setOrderno(String orderno) {
		this.orderno = orderno;
	}
	

}
