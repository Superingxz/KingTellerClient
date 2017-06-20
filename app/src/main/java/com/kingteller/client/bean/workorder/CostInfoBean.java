package com.kingteller.client.bean.workorder;

import java.io.Serializable;

/**
 * 费用信息
 * @author jinyh
 */
public class CostInfoBean implements Serializable{

	private static final long serialVersionUID = 1L;

	private String id;  	//id
	private String reportId; //工单id
	private String typeId; //费用类型id
	private Double costFee;	//费用数目
	private String costFeeStr;
	private String costdescName;//
	private String costDesc;//费用方式ID
	private String userId;//费用使用者id
	private String username;//费用使用者姓名
	private String descript;//费用使用描述
	private String typeName;  	//费用类型名称
	private String modeName;//费用方式名称
	
	private String qzdd;                 // 起止地点:交通费
	private String jsdd;                 // 结束地点:交通费
	
	
	public String getQzdd() {
		return qzdd;
	}
	public void setQzdd(String qzdd) {
		this.qzdd = qzdd;
	}
	public String getJsdd() {
		return jsdd;
	}
	public void setJsdd(String jsdd) {
		this.jsdd = jsdd;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getReportId() {
		return reportId;
	}
	public void setReportId(String reportId) {
		this.reportId = reportId;
	}
	public String getTypeId() {
		return typeId;
	}
	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}
	public Double getCostFee() {
		return costFee;
	}
	public void setCostFee(Double costFee) {
		this.costFee = costFee;
	}
	public String getCostFeeStr() {
		return costFeeStr;
	}
	public void setCostFeeStr(String costFeeStr) {
		this.costFeeStr = costFeeStr;
	}
	
	public String getCostdescName() {
		return costdescName;
	}
	public void setCostdescName(String costdescName) {
		this.costdescName = costdescName;
	}
	public String getCostDesc() {
		return costDesc;
	}
	public void setCostDesc(String costDesc) {
		this.costDesc = costDesc;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getDescript() {
		return descript;
	}
	public void setDescript(String descript) {
		this.descript = descript;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public String getModeName() {
		return modeName;
	}
	public void setModeName(String modeName) {
		this.modeName = modeName;
	}
	
	

}
