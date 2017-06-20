package com.kingteller.client.bean.logisticmonitor;

import java.io.Serializable;
import java.util.List;

public class LogisticOrderBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private LogisticConsignBasicBean basicbean;   //基础信息类
	private LogisticTaskBean taskbean;            //任务单类
	private List<LogisticConsignBean> consignList; //托运单集合
	private String hwsl;
	public LogisticConsignBasicBean getBasicbean() {
		return basicbean;
	}
	public void setBasicbean(LogisticConsignBasicBean basicbean) {
		this.basicbean = basicbean;
	}
	public LogisticTaskBean getTaskbean() {
		return taskbean;
	}
	public void setTaskbean(LogisticTaskBean taskbean) {
		this.taskbean = taskbean;
	}
	public List<LogisticConsignBean> getConsignList() {
		return consignList;
	}
	public void setConsignList(List<LogisticConsignBean> consignList) {
		this.consignList = consignList;
	}
	public String getHwsl() {
		return hwsl;
	}
	public void setHwsl(String hwsl) {
		this.hwsl = hwsl;
	}
	
}
