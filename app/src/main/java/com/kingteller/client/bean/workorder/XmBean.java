package com.kingteller.client.bean.workorder;

import java.io.Serializable;

public class XmBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String xmid;
	private String oper;
	private String unexe;
	public String getXmid() {
		return xmid;
	}
	public void setXmid(String xmid) {
		this.xmid = xmid;
	}
	public String getOper() {
		return oper;
	}
	public void setOper(String oper) {
		this.oper = oper;
	}
	public String getUnexe() {
		return unexe;
	}
	public void setUnexe(String unexe) {
		this.unexe = unexe;
	}
	

}
