package com.kingteller.client.bean.pxkh;

import java.io.Serializable;
import java.util.List;

public class CommonListAndCoreBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String core;
	private List<KjBackDataBean> kbdbList;
	
	public String getCore() {
		return core;
	}
	public void setCore(String core) {
		this.core = core;
	}
	public List<KjBackDataBean> getKbdbList() {
		return kbdbList;
	}
	public void setKbdbList(List<KjBackDataBean> kbdbList) {
		this.kbdbList = kbdbList;
	}
	
}
