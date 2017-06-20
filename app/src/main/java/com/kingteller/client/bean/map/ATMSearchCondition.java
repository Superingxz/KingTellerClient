package com.kingteller.client.bean.map;

import java.io.Serializable;

public class ATMSearchCondition implements Serializable{

	private static final long serialVersionUID = -3497263499965651318L;

	private String jqbh; //机器编号
	private String atmhlike;//ATM号
	private String jqlb; //机器类别
	private String extfield20;//所属大区
	private String ssqy; //所属区域
	private String ssyhLike;//所属银行status
	private String ssbsc; //所属服务站
	private String jqgsrusername;//机器归属人
	private String wddzLike; //网点地址
	private String sszhLike;//所属支行
	private String wdsbjc;//网点设备简称
	private String statusName;//机器状态名称
	private String page;//当前页数	
	
	public String getStatusName() {
		return statusName;
	}
	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}
	public String getJqbh() {
		return jqbh;
	}
	public void setJqbh(String jqbh) {
		this.jqbh = jqbh;
	}
	public String getAtmhlike() {
		return atmhlike;
	}
	public void setAtmhlike(String atmhlike) {
		this.atmhlike = atmhlike;
	}
	public String getJqlb() {
		return jqlb;
	}
	public void setJqlb(String jqlb) {
		this.jqlb = jqlb;
	}
	public String getExtfield20() {
		return extfield20;
	}
	public void setExtfield20(String extfield20) {
		this.extfield20 = extfield20;
	}
	public String getSsqy() {
		return ssqy;
	}
	public void setSsqy(String ssqy) {
		this.ssqy = ssqy;
	}
	public String getSsyhLike() {
		return ssyhLike;
	}
	public void setSsyhLike(String ssyhLike) {
		this.ssyhLike = ssyhLike;
	}
	public String getSsbsc() {
		return ssbsc;
	}
	public void setSsbsc(String ssbsc) {
		this.ssbsc = ssbsc;
	}
	public String getJqgsrusername() {
		return jqgsrusername;
	}
	public void setJqgsrusername(String jqgsrusername) {
		this.jqgsrusername = jqgsrusername;
	}
	public String getWddzLike() {
		return wddzLike;
	}
	public void setWddzLike(String wddzLike) {
		this.wddzLike = wddzLike;
	}
	public String getSszhLike() {
		return sszhLike;
	}
	public void setSszhLike(String sszhLike) {
		this.sszhLike = sszhLike;
	}
	public String getWdsbjc() {
		return wdsbjc;
	}
	public void setWdsbjc(String wdsbjc) {
		this.wdsbjc = wdsbjc;
	}
	public String getPage() {
		return page;
	}
	public void setPage(String page) {
		this.page = page;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}
