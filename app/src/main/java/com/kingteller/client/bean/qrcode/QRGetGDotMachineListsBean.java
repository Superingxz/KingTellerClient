package com.kingteller.client.bean.qrcode;

import java.io.Serializable;
import java.util.List;

public class QRGetGDotMachineListsBean implements Serializable {

	private static final long serialVersionUID = 7831611497556630205L;
	
	private List<QRDotMachineBean> wlList;
//	private List<QRDotMachineWdJqBean> jqList;
	private String code;
	
	
	public List<QRDotMachineBean> getWlList() {
		return wlList;
	}
	public void setWlList(List<QRDotMachineBean> wlList) {
		this.wlList = wlList;
	}
//	public List<QRDotMachineWdJqBean> getJqList() {
//		return jqList;
//	}
//	public void setJqList(List<QRDotMachineWdJqBean> jqList) {
//		this.jqList = jqList;
//	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	
	
}
