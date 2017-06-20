package com.kingteller.client.bean.workorder;

import java.io.Serializable;

public class MachineinfoSimpleBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String jqid;	//机器id
	private String jqbh;	//机器编号
	private String atmh;	//ATM号
	private String wdsbjc;	//网点设备简称
	private String jqgsrusername;	//机器归属人
	private String jqgsruserid;	//机器归属人 jqgsruserid
    private String jqgsrphone;  //机器归属人 电话   
	private String ssdq  ; //   所属大区域  
	private String ssdqid ; //    大区域id      
    
	private String ssqy  ; //   所属区域  
	private String ssqyid ; //    区域id      
	private String ssyh    ; //    所属银行
	private String ssyhid   ; //    银行id    
	private String sszh      ; //  所属支行     
	private String sszhid     ; //  支行id    
	private String ssbsc    ; //   所属办事处     
	private String ssbscid  ; //    办事处id  
	private String wdid;     //网点id
	private String wdlxdh;   //网点联系电话
	private String wdlxr;    //网点联系人
	private String wddz;     //网点地址
  	private Long status;	//状态
  	private String statusName ;// 状态名称
  	private String wbStatus;	//维保状态
  	private String wbStatusName;	//维保状态中文
  	private String province; //省份
  	private String city; //地市
  	private String county ;//区县
  	private String jxName;//机型
  	private String azdz;//安装地址
  	
  	private String brandName;//品牌
  	private String servCompName;//服务商
  	private String fsName;//方式
  	
  	private String brandId;//品牌id
  	private String servCompId;//服务商id
  	
  	private String clearItemExeStatus ;// 清洁项目执行的情况： 为空 表示当月未清洁；
  				//  10 表示清洁中；1表示清洁完成 （审核也通过），-1  表示清洁工作不合格
  	
  	private String clearItemExeStatusName ;  //清洁项目执行的情况的状态对应的名称
  	private String onoff;//在离行
  	
  	private String jqlb; //机器类别

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

	public String getSsdq() {
		return ssdq;
	}

	public void setSsdq(String ssdq) {
		this.ssdq = ssdq;
	}

	public String getSsdqid() {
		return ssdqid;
	}

	public void setSsdqid(String ssdqid) {
		this.ssdqid = ssdqid;
	}

	public String getSsqy() {
		return ssqy;
	}

	public void setSsqy(String ssqy) {
		this.ssqy = ssqy;
	}

	public String getSsqyid() {
		return ssqyid;
	}

	public void setSsqyid(String ssqyid) {
		this.ssqyid = ssqyid;
	}

	public String getSsyh() {
		return ssyh;
	}

	public void setSsyh(String ssyh) {
		this.ssyh = ssyh;
	}

	public String getSsyhid() {
		return ssyhid;
	}

	public void setSsyhid(String ssyhid) {
		this.ssyhid = ssyhid;
	}

	public String getSszh() {
		return sszh;
	}

	public void setSszh(String sszh) {
		this.sszh = sszh;
	}

	public String getSszhid() {
		return sszhid;
	}

	public void setSszhid(String sszhid) {
		this.sszhid = sszhid;
	}

	public String getSsbsc() {
		return ssbsc;
	}

	public void setSsbsc(String ssbsc) {
		this.ssbsc = ssbsc;
	}

	public String getSsbscid() {
		return ssbscid;
	}

	public void setSsbscid(String ssbscid) {
		this.ssbscid = ssbscid;
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

	public Long getStatus() {
		return status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	public String getWbStatus() {
		return wbStatus;
	}

	public void setWbStatus(String wbStatus) {
		this.wbStatus = wbStatus;
	}

	public String getWbStatusName() {
		return wbStatusName;
	}

	public void setWbStatusName(String wbStatusName) {
		this.wbStatusName = wbStatusName;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCounty() {
		return county;
	}

	public void setCounty(String county) {
		this.county = county;
	}

	public String getJxName() {
		return jxName;
	}

	public void setJxName(String jxName) {
		this.jxName = jxName;
	}

	public String getAzdz() {
		return azdz;
	}

	public void setAzdz(String azdz) {
		this.azdz = azdz;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public String getServCompName() {
		return servCompName;
	}

	public void setServCompName(String servCompName) {
		this.servCompName = servCompName;
	}

	public String getFsName() {
		return fsName;
	}

	public void setFsName(String fsName) {
		this.fsName = fsName;
	}

	public String getBrandId() {
		return brandId;
	}

	public void setBrandId(String brandId) {
		this.brandId = brandId;
	}

	public String getServCompId() {
		return servCompId;
	}

	public void setServCompId(String servCompId) {
		this.servCompId = servCompId;
	}

	public String getClearItemExeStatus() {
		return clearItemExeStatus;
	}

	public void setClearItemExeStatus(String clearItemExeStatus) {
		this.clearItemExeStatus = clearItemExeStatus;
	}

	public String getClearItemExeStatusName() {
		return clearItemExeStatusName;
	}

	public void setClearItemExeStatusName(String clearItemExeStatusName) {
		this.clearItemExeStatusName = clearItemExeStatusName;
	}

	public String getOnoff() {
		return onoff;
	}

	public void setOnoff(String onoff) {
		this.onoff = onoff;
	}

	public String getJqlb() {
		return jqlb;
	}

	public void setJqlb(String jqlb) {
		this.jqlb = jqlb;
	}
  	
  	
}
