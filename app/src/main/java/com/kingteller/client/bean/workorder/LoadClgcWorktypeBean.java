package com.kingteller.client.bean.workorder;

import java.io.Serializable;

public class LoadClgcWorktypeBean implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;           // 主键id
	private String idLike;
	private String handleSub;    // 工作类别－处理过程
	private String handleSubLike;
	private String remark;       // 说明
	private String remarkLike;
	private String parentId;     // 工作类别表id
	private String parentIdLike;
	//private String modifyDate;         // 修改时间
	private String modifyDateRange1;
	private String modifyDateRange2;
	private String modifyDateStr;
	private String modifyUserid;     // 修改人id
	private String modifyUseridLike;
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
	public String getHandleSub() {
		return handleSub;
	}
	public void setHandleSub(String handleSub) {
		this.handleSub = handleSub;
	}
	public String getHandleSubLike() {
		return handleSubLike;
	}
	public void setHandleSubLike(String handleSubLike) {
		this.handleSubLike = handleSubLike;
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
	/*public String getModifyDate() {
		return modifyDate;
	}
	public void setModifyDate(String modifyDate) {
		this.modifyDate = modifyDate;
	}*/
	public String getModifyDateRange1() {
		return modifyDateRange1;
	}
	public void setModifyDateRange1(String modifyDateRange1) {
		this.modifyDateRange1 = modifyDateRange1;
	}
	public String getModifyDateRange2() {
		return modifyDateRange2;
	}
	public void setModifyDateRange2(String modifyDateRange2) {
		this.modifyDateRange2 = modifyDateRange2;
	}
	public String getModifyDateStr() {
		return modifyDateStr;
	}
	public void setModifyDateStr(String modifyDateStr) {
		this.modifyDateStr = modifyDateStr;
	}
	public String getModifyUserid() {
		return modifyUserid;
	}
	public void setModifyUserid(String modifyUserid) {
		this.modifyUserid = modifyUserid;
	}
	public String getModifyUseridLike() {
		return modifyUseridLike;
	}
	public void setModifyUseridLike(String modifyUseridLike) {
		this.modifyUseridLike = modifyUseridLike;
	}
	
	
}
