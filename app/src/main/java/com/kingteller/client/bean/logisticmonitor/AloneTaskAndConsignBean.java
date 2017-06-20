package com.kingteller.client.bean.logisticmonitor;

import java.io.Serializable;
import java.util.List;

public class AloneTaskAndConsignBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private LogisticTaskBean task;//任务单
	private LogisticConsignBasicBean basic;//基本信息
	private List<LogisticConsignMobileBean> tydlist;       //托运单集合
	private List<FareDetailParam> fareDetailList;
	
	public LogisticTaskBean getTask() {
		return task;
	}
	public void setTask(LogisticTaskBean task) {
		this.task = task;
	}
	public LogisticConsignBasicBean getBasic() {
		return basic;
	}
	public void setBasic(LogisticConsignBasicBean basic) {
		this.basic = basic;
	}
	public List<LogisticConsignMobileBean> getTydlist() {
		return tydlist;
	}
	public void setTydlist(List<LogisticConsignMobileBean> tydlist) {
		this.tydlist = tydlist;
	}
	public List<FareDetailParam> getFareDetailList() {
		return fareDetailList;
	}
	public void setFareDetailList(List<FareDetailParam> fareDetailList) {
		this.fareDetailList = fareDetailList;
	}

}
