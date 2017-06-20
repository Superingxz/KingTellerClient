package com.kingteller.client.bean.logisticmonitor;

import java.io.Serializable;
import java.util.List;

public class TydDataBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String tydId;
	private List<HwDataBean> hdbList;
	
	public String getTydId() {
		return tydId;
	}
	public void setTydId(String tydId) {
		this.tydId = tydId;
	}
	public List<HwDataBean> getHdbList() {
		return hdbList;
	}
	public void setHdbList(List<HwDataBean> hdbList) {
		this.hdbList = hdbList;
	}
	
	
}
