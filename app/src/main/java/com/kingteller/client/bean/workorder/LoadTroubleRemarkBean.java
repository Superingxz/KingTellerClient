package com.kingteller.client.bean.workorder;

import java.io.Serializable;

public class LoadTroubleRemarkBean implements Serializable{

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
	private String createDateRange1;
	private String createDateRange2;
	private String createDateStr;
	private String createUserid;     // 创建人
	private String createUseridLike;
	private Long status;             // 状态 1有效  0 无效
	private String parentId;         // 故障类别ID（一级部件）
	private String parentIdLike;
	private String twoLevelId;       // 故障部件ID（二级部件）
	private String twoLevelIdLike;
	private String threeLevelId;     // 物料id(三级部件)
	private String threeLevelIdLike;
	private Long indexnum;           // 序号
	private Long updateFlag;
	private String pathId;           // 路径id
	private String pathIdLike;
	private String pathName;         // 路径name
	private String pathNameLike;
	
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
	public String getCreateDateRange1() {
		return createDateRange1;
	}
	public void setCreateDateRange1(String createDateRange1) {
		this.createDateRange1 = createDateRange1;
	}
	public String getCreateDateRange2() {
		return createDateRange2;
	}
	public void setCreateDateRange2(String createDateRange2) {
		this.createDateRange2 = createDateRange2;
	}
	public String getCreateDateStr() {
		return createDateStr;
	}
	public void setCreateDateStr(String createDateStr) {
		this.createDateStr = createDateStr;
	}
	public String getCreateUserid() {
		return createUserid;
	}
	public void setCreateUserid(String createUserid) {
		this.createUserid = createUserid;
	}
	public String getCreateUseridLike() {
		return createUseridLike;
	}
	public void setCreateUseridLike(String createUseridLike) {
		this.createUseridLike = createUseridLike;
	}
	public Long getStatus() {
		return status;
	}
	public void setStatus(Long status) {
		this.status = status;
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
	public Long getIndexnum() {
		return indexnum;
	}
	public void setIndexnum(Long indexnum) {
		this.indexnum = indexnum;
	}
	public Long getUpdateFlag() {
		return updateFlag;
	}
	public void setUpdateFlag(Long updateFlag) {
		this.updateFlag = updateFlag;
	}
	public String getPathId() {
		return pathId;
	}
	public void setPathId(String pathId) {
		this.pathId = pathId;
	}
	public String getPathIdLike() {
		return pathIdLike;
	}
	public void setPathIdLike(String pathIdLike) {
		this.pathIdLike = pathIdLike;
	}
	public String getPathName() {
		return pathName;
	}
	public void setPathName(String pathName) {
		this.pathName = pathName;
	}
	public String getPathNameLike() {
		return pathNameLike;
	}
	public void setPathNameLike(String pathNameLike) {
		this.pathNameLike = pathNameLike;
	}
	
	
	
}
