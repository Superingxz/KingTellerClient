package com.kingteller.client.bean.workorder;

import java.io.Serializable;

public class JqxxDataBean implements Serializable{

	private static final long serialVersionUID = 1L;
	private String jqid;	//机器id
	private String jqbh;	//机器编号
	private String atmh;	//ATM号
	private String wdsbjc;	//网点设备简称
	private String jqgsrusername;	//机器归属人
	private String jqgsruserid;	//机器归属人 jqgsruserid
    private String jqgsrphone;  //机器归属人 电话 
    private String wdid;     //网点id
	private String wdlxdh;   //网点联系电话
	private String wdlxr;    //网点联系人
	private String wddz;     //网点地址
    private String extfield28; //经纬度(纬度,经度)
    
    private String reportId;//维护报告id
    private String bzwhsj; //标准维护时间(小时数);
    private String bzxysj; //标准响应时间(小时数);
    
    private String result;
    private String message;
    
    

	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getReportId() {
		return reportId;
	}
	public void setReportId(String reportId) {
		this.reportId = reportId;
	}
	public String getBzwhsj() {
		return bzwhsj;
	}
	public void setBzwhsj(String bzwhsj) {
		this.bzwhsj = bzwhsj;
	}
	public String getBzxysj() {
		return bzxysj;
	}
	public void setBzxysj(String bzxysj) {
		this.bzxysj = bzxysj;
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
	public String getAtmh() {
		return atmh;
	}
	public void setAtmh(String atmh) {
		this.atmh = atmh;
	}
	public String getWdsbjc() {
		return wdsbjc;
	}
	public void setWdsbjc(String wdsbjc) {
		this.wdsbjc = wdsbjc;
	}
	public String getJqgsrusername() {
		return jqgsrusername;
	}
	public void setJqgsrusername(String jqgsrusername) {
		this.jqgsrusername = jqgsrusername;
	}
	public String getJqgsruserid() {
		return jqgsruserid;
	}
	public void setJqgsruserid(String jqgsruserid) {
		this.jqgsruserid = jqgsruserid;
	}
	public String getJqgsrphone() {
		return jqgsrphone;
	}
	public void setJqgsrphone(String jqgsrphone) {
		this.jqgsrphone = jqgsrphone;
	}
	public String getWdid() {
		return wdid;
	}
	public void setWdid(String wdid) {
		this.wdid = wdid;
	}
	public String getWdlxdh() {
		return wdlxdh;
	}
	public void setWdlxdh(String wdlxdh) {
		this.wdlxdh = wdlxdh;
	}
	public String getWdlxr() {
		return wdlxr;
	}
	public void setWdlxr(String wdlxr) {
		this.wdlxr = wdlxr;
	}
	public String getWddz() {
		return wddz;
	}
	public void setWddz(String wddz) {
		this.wddz = wddz;
	}
	public String getExtfield28() {
		return extfield28;
	}
	public void setExtfield28(String extfield28) {
		this.extfield28 = extfield28;
	}
}
