package com.kingteller.client.bean.logisticmonitor;

import java.io.Serializable;

public class OtherTaskBean implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String swdh;
	private String swname;
	private String cjz;
	private String yqthsj;
	private String rwdzt;
	private String statusName;
    private String gcy1Name;
    private String gcy2Name;
    private String clName;
    private String cjStr;
    private String cjsjStr;
    private String yqthsjStr;
    private String shjg;
    private String cl;//车牌号
    private String rwlc;       // 任务里程
	private String qcqlcs;     // 起程前里程数
	private String fhckhlcs;   // 返回车库后里程数
	private String tel;	       //调度员tel
    
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getSwdh() {
		return swdh;
	}
	public void setSwdh(String swdh) {
		this.swdh = swdh;
	}
	public String getSwname() {
		return swname;
	}
	public void setSwname(String swname) {
		this.swname = swname;
	}
	public String getCjz() {
		return cjz;
	}
	public void setCjz(String cjz) {
		this.cjz = cjz;
	}
	public String getYqthsj() {
		return yqthsj;
	}
	public void setYqthsj(String yqthsj) {
		this.yqthsj = yqthsj;
	}
	public String getRwdzt() {
		return rwdzt;
	}
	public void setRwdzt(String rwdzt) {
		this.rwdzt = rwdzt;
	}
	public String getStatusName() {
		return statusName;
	}
	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}
	public String getGcy1Name() {
		return gcy1Name;
	}
	public void setGcy1Name(String gcy1Name) {
		this.gcy1Name = gcy1Name;
	}
	public String getGcy2Name() {
		return gcy2Name;
	}
	public void setGcy2Name(String gcy2Name) {
		this.gcy2Name = gcy2Name;
	}
	public String getClName() {
		return clName;
	}
	public void setClName(String clName) {
		this.clName = clName;
	}
	public String getCjStr() {
		return cjStr;
	}
	public void setCjStr(String cjStr) {
		this.cjStr = cjStr;
	}
	public String getCjsjStr() {
		return cjsjStr;
	}
	public void setCjsjStr(String cjsjStr) {
		this.cjsjStr = cjsjStr;
	}
	public String getYqthsjStr() {
		return yqthsjStr;
	}
	public void setYqthsjStr(String yqthsjStr) {
		this.yqthsjStr = yqthsjStr;
	}
	public String getShjg() {
		return shjg;
	}
	public void setShjg(String shjg) {
		this.shjg = shjg;
	}
	public String getCl() {
		return cl;
	}
	public void setCl(String cl) {
		this.cl = cl;
	}
	public String getRwlc() {
		return rwlc;
	}
	public void setRwlc(String rwlc) {
		this.rwlc = rwlc;
	}
	public String getQcqlcs() {
		return qcqlcs;
	}
	public void setQcqlcs(String qcqlcs) {
		this.qcqlcs = qcqlcs;
	}
	public String getFhckhlcs() {
		return fhckhlcs;
	}
	public void setFhckhlcs(String fhckhlcs) {
		this.fhckhlcs = fhckhlcs;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
    
	

}
