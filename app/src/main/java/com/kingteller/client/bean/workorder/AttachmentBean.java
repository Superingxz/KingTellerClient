package com.kingteller.client.bean.workorder;

import java.io.Serializable;

/**
 * 附件信息
 * @author jinyh
 */

public class AttachmentBean implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private String id; // id
	private String reportId;// 工单id
	private String attachName;// 附件名字
	private String attachDesc;// 附件描述
	private String attachPath;// 附件路径
	private String senderId;// 上传者id
	private String senderName;// 上传者姓名
	private String attachPathName;// 附件别名
	
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
	public String getAttachName() {
		return attachName;
	}
	public void setAttachName(String attachName) {
		this.attachName = attachName;
	}
	public String getAttachDesc() {
		return attachDesc;
	}
	public void setAttachDesc(String attachDesc) {
		this.attachDesc = attachDesc;
	}
	public String getAttachPath() {
		return attachPath;
	}
	public void setAttachPath(String attachPath) {
		this.attachPath = attachPath;
	}
	public String getSenderId() {
		return senderId;
	}
	public void setSenderId(String senderId) {
		this.senderId = senderId;
	}
	public String getSenderName() {
		return senderName;
	}
	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}
	public String getAttachPathName() {
		return attachPathName;
	}
	public void setAttachPathName(String attachPathName) {
		this.attachPathName = attachPathName;
	}

}
