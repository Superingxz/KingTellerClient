package com.kingteller.client.bean.workorder;

import java.io.Serializable;
import java.util.Date;

public class RTroubleRemarkBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;               // 主键id
	private String idLike;
	private String troubleRemark;    // 故障描述文本
	private String troubleRemarkLike;
	private String remark;           // 备注
	private String remarkLike;
	private String parentId;         // 故障类别ID（一级部件）
	private String parentIdLike;
	private String twoLevelId;       // 故障部件ID（二级部件）
	private String twoLevelIdLike;
	private String threeLevelId;     // 物料id(三级部件)
	private String threeLevelIdLike;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getIdLike() {
		return idLike;
	}
	public void setIdLike(String idLike) {
		this.idLike = idLike;
	}
	public String getTroubleRemark() {
		return troubleRemark;
	}
	public void setTroubleRemark(String troubleRemark) {
		this.troubleRemark = troubleRemark;
	}
	public String getTroubleRemarkLike() {
		return troubleRemarkLike;
	}
	public void setTroubleRemarkLike(String troubleRemarkLike) {
		this.troubleRemarkLike = troubleRemarkLike;
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
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getParentId() {
		return parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	public String getParentIdLike() {
		return parentIdLike;
	}
	public void setParentIdLike(String parentIdLike) {
		this.parentIdLike = parentIdLike;
	}
	public String getTwoLevelId() {
		return twoLevelId;
	}
	public void setTwoLevelId(String twoLevelId) {
		this.twoLevelId = twoLevelId;
	}
	public String getTwoLevelIdLike() {
		return twoLevelIdLike;
	}
	public void setTwoLevelIdLike(String twoLevelIdLike) {
		this.twoLevelIdLike = twoLevelIdLike;
	}
	public String getThreeLevelId() {
		return threeLevelId;
	}
	public void setThreeLevelId(String threeLevelId) {
		this.threeLevelId = threeLevelId;
	}
	public String getThreeLevelIdLike() {
		return threeLevelIdLike;
	}
	public void setThreeLevelIdLike(String threeLevelIdLike) {
		this.threeLevelIdLike = threeLevelIdLike;
	}

	
}
