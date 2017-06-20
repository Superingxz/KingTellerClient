package com.kingteller.client.bean.workorder;

import java.io.Serializable;

//物料通用关系表信息
public class LoadWlGeneralRelationBean implements Serializable{

	private static final long serialVersionUID = 50765543871208600L;
	private String tempId;            // 主键id
	private String tempIdLike;
	private  String createDateStr;
	private String createUserId;      // 创建人id
	private String createUserIdLike;
	private Long tempStatus;          // 状态：0禁用，1可用
	private String operateDateStr; 	  //创建时间
	private String operateUserId;     // 禁用或启用操作人id
	private String operateUserIdLike;
	private String wlInfoId;          // 物料种类id
	private String wlInfoIdLike;
	private String wlTypeId;          // 类别id
	private String wlTypeIdLike;
	private String remark;            //备注
	private String remarkLike;
	public String getTempId() {
		return tempId;
	}
	public void setTempId(String tempId) {
		this.tempId = tempId;
	}
	public String getTempIdLike() {
		return tempIdLike;
	}
	public void setTempIdLike(String tempIdLike) {
		this.tempIdLike = tempIdLike;
	}
	public String getCreateDateStr() {
		return createDateStr;
	}
	public void setCreateDateStr(String createDateStr) {
		this.createDateStr = createDateStr;
	}
	public String getCreateUserId() {
		return createUserId;
	}
	public void setCreateUserId(String createUserId) {
		this.createUserId = createUserId;
	}
	public String getCreateUserIdLike() {
		return createUserIdLike;
	}
	public void setCreateUserIdLike(String createUserIdLike) {
		this.createUserIdLike = createUserIdLike;
	}
	public Long getTempStatus() {
		return tempStatus;
	}
	public void setTempStatus(Long tempStatus) {
		this.tempStatus = tempStatus;
	}
	public String getOperateDateStr() {
		return operateDateStr;
	}
	public void setOperateDateStr(String operateDateStr) {
		this.operateDateStr = operateDateStr;
	}
	public String getOperateUserId() {
		return operateUserId;
	}
	public void setOperateUserId(String operateUserId) {
		this.operateUserId = operateUserId;
	}
	public String getOperateUserIdLike() {
		return operateUserIdLike;
	}
	public void setOperateUserIdLike(String operateUserIdLike) {
		this.operateUserIdLike = operateUserIdLike;
	}
	public String getWlInfoId() {
		return wlInfoId;
	}
	public void setWlInfoId(String wlInfoId) {
		this.wlInfoId = wlInfoId;
	}
	public String getWlInfoIdLike() {
		return wlInfoIdLike;
	}
	public void setWlInfoIdLike(String wlInfoIdLike) {
		this.wlInfoIdLike = wlInfoIdLike;
	}
	public String getWlTypeId() {
		return wlTypeId;
	}
	public void setWlTypeId(String wlTypeId) {
		this.wlTypeId = wlTypeId;
	}
	public String getWlTypeIdLike() {
		return wlTypeIdLike;
	}
	public void setWlTypeIdLike(String wlTypeIdLike) {
		this.wlTypeIdLike = wlTypeIdLike;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getRemarkLike() {
		return remarkLike;
	}
	public void setRemarkLike(String remarkLike) {
		this.remarkLike = remarkLike;
	}

	

}
