package com.kingteller.client.bean.knowledge;

import java.io.Serializable;

public class KnowledgeBean implements Serializable{
	private String id;        			// 主键id
	private String errorCode; 			// 错误编码
	private String component;          	//故障部件
	private String troubleRemark;       // 故障说明
	private String maintenanceRemark;   // 维修处理说明
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	public String getComponent() {
		return component;
	}
	public void setComponent(String component) {
		this.component = component;
	}
	public String getTroubleRemark() {
		return troubleRemark;
	}
	public void setTroubleRemark(String troubleRemark) {
		this.troubleRemark = troubleRemark;
	}
	public String getMaintenanceRemark() {
		return maintenanceRemark;
	}
	public void setMaintenanceRemark(String maintenanceRemark) {
		this.maintenanceRemark = maintenanceRemark;
	}
}
