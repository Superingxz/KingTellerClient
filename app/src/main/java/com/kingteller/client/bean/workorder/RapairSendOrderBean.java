package com.kingteller.client.bean.workorder;

import java.io.Serializable;
import java.util.List;

public class RapairSendOrderBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String jqid;//机器id
	private String jqbh;//机器编号
	private String status;//工单状态
	private String troubleRemark;//故障描述
	private String arrangeType; //工单是否预约  1 表示预约  空或者0 表示正常派单
	private String prearrangeDateStr;// 预约时间
	private String systemProposeFlags ;      //表示该维护工程师 是否用户手工选择的？ 还是系统推荐的
	private String workDateStr;	   //工作日期
	private List<AssignWorkerNameBean> wnList; //维护人员
	
	public String getJqid() {
		return jqid;
	}
	public void setJqid(String jqid) {
		this.jqid = jqid;
	}
	public String getJqbh() {
		return jqbh;
	}
	public void setJqbh(String jqbh) {
		this.jqbh = jqbh;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getTroubleRemark() {
		return troubleRemark;
	}
	public void setTroubleRemark(String troubleRemark) {
		this.troubleRemark = troubleRemark;
	}
	public String getArrangeType() {
		return arrangeType;
	}
	public void setArrangeType(String arrangeType) {
		this.arrangeType = arrangeType;
	}
	public String getPrearrangeDateStr() {
		return prearrangeDateStr;
	}
	public void setPrearrangeDateStr(String prearrangeDateStr) {
		this.prearrangeDateStr = prearrangeDateStr;
	}
	public String getSystemProposeFlags() {
		return systemProposeFlags;
	}
	public void setSystemProposeFlags(String systemProposeFlags) {
		this.systemProposeFlags = systemProposeFlags;
	}
	public String getWorkDateStr() {
		return workDateStr;
	}
	public void setWorkDateStr(String workDateStr) {
		this.workDateStr = workDateStr;
	}
	public List<AssignWorkerNameBean> getWnList() {
		return wnList;
	}
	public void setWnList(List<AssignWorkerNameBean> wnList) {
		this.wnList = wnList;
	}
	
}
