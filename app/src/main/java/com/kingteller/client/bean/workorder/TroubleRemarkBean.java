package com.kingteller.client.bean.workorder;

/**
 * 故障描述
 * 
 * @author 王定波
 * 
 */
public class TroubleRemarkBean {
	private String id;
	private String troubleRemark;
	private String remark;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTroubleRemark() {
		return troubleRemark;
	}
	public void setTroubleRemark(String troubleRemark) {
		this.troubleRemark = troubleRemark;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}
