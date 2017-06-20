package com.kingteller.client.bean.logisticmonitor;

import java.io.Serializable;

public class LogisticConsignBasicBean implements Serializable{

	/**
	 			
	 */
	private static final long serialVersionUID = 1L;
	private String id; // 主键
	private String idLike;
	private String tzdh; // 通知单号
	private String tzdhLike;
	private String tylx; // 托运类型
	private String tylxLike;
	private String ctdw; // 承托单位 1：公司
	private String ctdwLike;
	/*private Date thrq; // 提货日期
	private Date thrqRange1;
	private Date thrqRange2;*/
	private String thrqStr;
	/*private Date psddrq; // 配送到达日期
	private Date psddrqRange1;
	private Date psddrqRange2;*/
	private String psddrqStr;
	private String htfs; // 合同方式 1：销售 2：合作 3：租赁 4：其他
	private String htfsLike;
	private String yhmc; // 银行名称
	private String yhmcLike;
	private String tsyq; // 特殊要求
	private String tsyqLike;
	private String sfeczy; // 是否二次转运 1：是 0：否
	private String sfeczyLike;
	private String cjz; // 创建者
	private String cjzLike;
/*	private Date cjsj; // 创建时间
	private Date cjsjRange1;
	private Date cjsjRange2;*/
	private String cjsjStr;
	private String expand1; // 扩展1
	private String expand1Like;
	private String expand2; // 扩展2
	private String expand2Like;
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
	public String getTzdh() {
		return tzdh;
	}
	public void setTzdh(String tzdh) {
		this.tzdh = tzdh;
	}
	public String getTzdhLike() {
		return tzdhLike;
	}
	public void setTzdhLike(String tzdhLike) {
		this.tzdhLike = tzdhLike;
	}
	public String getTylx() {
		return tylx;
	}
	public void setTylx(String tylx) {
		this.tylx = tylx;
	}
	public String getTylxLike() {
		return tylxLike;
	}
	public void setTylxLike(String tylxLike) {
		this.tylxLike = tylxLike;
	}
	public String getCtdw() {
		return ctdw;
	}
	public void setCtdw(String ctdw) {
		this.ctdw = ctdw;
	}
	public String getCtdwLike() {
		return ctdwLike;
	}
	public void setCtdwLike(String ctdwLike) {
		this.ctdwLike = ctdwLike;
	}
	public String getThrqStr() {
		return thrqStr;
	}
	public void setThrqStr(String thrqStr) {
		this.thrqStr = thrqStr;
	}
	public String getPsddrqStr() {
		return psddrqStr;
	}
	public void setPsddrqStr(String psddrqStr) {
		this.psddrqStr = psddrqStr;
	}
	public String getHtfs() {
		return htfs;
	}
	public void setHtfs(String htfs) {
		this.htfs = htfs;
	}
	public String getHtfsLike() {
		return htfsLike;
	}
	public void setHtfsLike(String htfsLike) {
		this.htfsLike = htfsLike;
	}
	public String getYhmc() {
		return yhmc;
	}
	public void setYhmc(String yhmc) {
		this.yhmc = yhmc;
	}
	public String getYhmcLike() {
		return yhmcLike;
	}
	public void setYhmcLike(String yhmcLike) {
		this.yhmcLike = yhmcLike;
	}
	public String getTsyq() {
		return tsyq;
	}
	public void setTsyq(String tsyq) {
		this.tsyq = tsyq;
	}
	public String getTsyqLike() {
		return tsyqLike;
	}
	public void setTsyqLike(String tsyqLike) {
		this.tsyqLike = tsyqLike;
	}
	public String getSfeczy() {
		return sfeczy;
	}
	public void setSfeczy(String sfeczy) {
		this.sfeczy = sfeczy;
	}
	public String getSfeczyLike() {
		return sfeczyLike;
	}
	public void setSfeczyLike(String sfeczyLike) {
		this.sfeczyLike = sfeczyLike;
	}
	public String getCjz() {
		return cjz;
	}
	public void setCjz(String cjz) {
		this.cjz = cjz;
	}
	public String getCjzLike() {
		return cjzLike;
	}
	public void setCjzLike(String cjzLike) {
		this.cjzLike = cjzLike;
	}
	public String getCjsjStr() {
		return cjsjStr;
	}
	public void setCjsjStr(String cjsjStr) {
		this.cjsjStr = cjsjStr;
	}
	public String getExpand1() {
		return expand1;
	}
	public void setExpand1(String expand1) {
		this.expand1 = expand1;
	}
	public String getExpand1Like() {
		return expand1Like;
	}
	public void setExpand1Like(String expand1Like) {
		this.expand1Like = expand1Like;
	}
	public String getExpand2() {
		return expand2;
	}
	public void setExpand2(String expand2) {
		this.expand2 = expand2;
	}
	public String getExpand2Like() {
		return expand2Like;
	}
	public void setExpand2Like(String expand2Like) {
		this.expand2Like = expand2Like;
	}
	
	

}
