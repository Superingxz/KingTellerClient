package com.kingteller.client.bean.workorder;

import java.io.Serializable;
import java.util.Date;

public class LoadHandleSubBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;              // 主键id
	private String handleSub;       // 故障描述文本
	private String handleSubLike;
	private String remark;          // 备注
	private String remarkLike;
	private String createDateRange1;
	private String createDateRange2;
	private String createDateStr;
	private String createUserid;    // 创建人
	private String createUseridLike;
	private Long status;            // 状态 1有效  0 无效
	private String reId;            // 故障描述ID
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
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
	public String getReId() {
		return reId;
	}
	public void setReId(String reId) {
		this.reId = reId;
	}

	
}
