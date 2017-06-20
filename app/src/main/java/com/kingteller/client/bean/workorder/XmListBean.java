package com.kingteller.client.bean.workorder;

import java.io.Serializable;
import java.util.List;

public class XmListBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String xmct;
	private List<XmBean> xmlist;
	public String getXmct() {
		return xmct;
	}
	public void setXmct(String xmct) {
		this.xmct = xmct;
	}
	public List<XmBean> getXmlist() {
		return xmlist;
	}
	public void setXmlist(List<XmBean> xmlist) {
		this.xmlist = xmlist;
	}

	
}
