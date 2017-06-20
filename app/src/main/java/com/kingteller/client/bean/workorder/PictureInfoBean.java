package com.kingteller.client.bean.workorder;

import java.io.Serializable;

public class PictureInfoBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String id;            // 主键id
	private String idLike;
	private String picName;       // 图片名称
	private String picNameLike;
	private String itemExeId;     // 执行记录主表id
	private String itemExeIdLike;
	private String orderId;       // 工单id
	private String orderIdLike;
	private String reportId;      // 工作报告id
	private String reportIdLike;
	private String wdId;          // 网点id
	private String wdIdLike;
	private Long picType;         // 图片类别：-1 表示清洁前的图片，1 表示清洁后的图片
	private Long picIndex;        // 序号
	private Long auditFlag;       // 审核标志
	private Long status;          // 图片状态 1 表示有效，0 表示无效
	private String remark;        // 备注
	private String remarkLike;
	private String picPathId;     // 图片上传后的路径信息
	private String picPathIdLike;
	
	private String modifyUserId ;
	private String modifyDate;
	private String modifyDateStr;
	private String picTypeName;        // 图片类别：-1 表示清洁前的图片，1 表示清洁后的图片
	private String modifyUserName;
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
	public String getPicName() {
		return picName;
	}
	public void setPicName(String picName) {
		this.picName = picName;
	}
	public String getPicNameLike() {
		return picNameLike;
	}
	public void setPicNameLike(String picNameLike) {
		this.picNameLike = picNameLike;
	}
	public String getItemExeId() {
		return itemExeId;
	}
	public void setItemExeId(String itemExeId) {
		this.itemExeId = itemExeId;
	}
	public String getItemExeIdLike() {
		return itemExeIdLike;
	}
	public void setItemExeIdLike(String itemExeIdLike) {
		this.itemExeIdLike = itemExeIdLike;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getOrderIdLike() {
		return orderIdLike;
	}
	public void setOrderIdLike(String orderIdLike) {
		this.orderIdLike = orderIdLike;
	}
	public String getReportId() {
		return reportId;
	}
	public void setReportId(String reportId) {
		this.reportId = reportId;
	}
	public String getReportIdLike() {
		return reportIdLike;
	}
	public void setReportIdLike(String reportIdLike) {
		this.reportIdLike = reportIdLike;
	}
	public String getWdId() {
		return wdId;
	}
	public void setWdId(String wdId) {
		this.wdId = wdId;
	}
	public String getWdIdLike() {
		return wdIdLike;
	}
	public void setWdIdLike(String wdIdLike) {
		this.wdIdLike = wdIdLike;
	}
	public Long getPicType() {
		return picType;
	}
	public void setPicType(Long picType) {
		this.picType = picType;
	}
	public Long getPicIndex() {
		return picIndex;
	}
	public void setPicIndex(Long picIndex) {
		this.picIndex = picIndex;
	}
	public Long getAuditFlag() {
		return auditFlag;
	}
	public void setAuditFlag(Long auditFlag) {
		this.auditFlag = auditFlag;
	}
	public Long getStatus() {
		return status;
	}
	public void setStatus(Long status) {
		this.status = status;
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
	public String getPicPathId() {
		return picPathId;
	}
	public void setPicPathId(String picPathId) {
		this.picPathId = picPathId;
	}
	public String getPicPathIdLike() {
		return picPathIdLike;
	}
	public void setPicPathIdLike(String picPathIdLike) {
		this.picPathIdLike = picPathIdLike;
	}
	public String getModifyUserId() {
		return modifyUserId;
	}
	public void setModifyUserId(String modifyUserId) {
		this.modifyUserId = modifyUserId;
	}
	public String getModifyDate() {
		return modifyDate;
	}
	public void setModifyDate(String modifyDate) {
		this.modifyDate = modifyDate;
	}
	public String getModifyDateStr() {
		return modifyDateStr;
	}
	public void setModifyDateStr(String modifyDateStr) {
		this.modifyDateStr = modifyDateStr;
	}
	public String getPicTypeName() {
		return picTypeName;
	}
	public void setPicTypeName(String picTypeName) {
		this.picTypeName = picTypeName;
	}
	public String getModifyUserName() {
		return modifyUserName;
	}
	public void setModifyUserName(String modifyUserName) {
		this.modifyUserName = modifyUserName;
	}


}
