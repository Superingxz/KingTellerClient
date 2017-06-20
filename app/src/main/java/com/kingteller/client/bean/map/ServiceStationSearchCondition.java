package com.kingteller.client.bean.map;

import java.io.Serializable;

public class ServiceStationSearchCondition implements Serializable{

	private static final long serialVersionUID = 5903001580082598791L;

	private String ssdq; //所属大区
	private String ssqy;//所属区域
	private String fwz; //服务站
	private String page;//当前页数	
	
	public String getPage() {
		return page;
	}
	public void setPage(String page) {
		this.page = page;
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
	public String getFwz() {
		return fwz;
	}
	public void setFwz(String fwz) {
		this.fwz = fwz;
	}
	
}
