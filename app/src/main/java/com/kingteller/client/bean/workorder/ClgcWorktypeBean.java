package com.kingteller.client.bean.workorder;

import java.io.Serializable;

public class ClgcWorktypeBean implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id; // 主键id
	private String idLike;
	private String handleSub; // 工作类别－处理过程
	private String handleSubLike;
	private String remark; // 说明
	private String remarkLike;
	private String parentId; // 工作类别表id
	private String parentIdLike;
	
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
	
	

}
