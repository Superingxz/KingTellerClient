package com.kingteller.client.bean.workorder;

import java.io.Serializable;

/**
 * 接送货信息,只有维护报告包含此类
 * @author jinyh
 */

public class PickInfoBean implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String id; //主键
	private String name;//接货人姓名
	private String phone;//接收人电话
	private String reportId;//对应报告物流报告id
	private String pType;//联系人类型
	private String pTypeName;//联系人类型
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getReportId() {
		return reportId;
	}
	public void setReportId(String reportId) {
		this.reportId = reportId;
	}
	public String getpType() {
		return pType;
	}
	public void setpType(String pType) {
		this.pType = pType;
	}
	public String getpTypeName() {
		return pTypeName;
	}
	public void setpTypeName(String pTypeName) {
		this.pTypeName = pTypeName;
	}

}
