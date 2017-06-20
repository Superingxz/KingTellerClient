package com.kingteller.client.bean.common.selectdata;

import java.io.Serializable;

public class WorkServiceStateBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3980308902889851815L;
	private String orgName;
	private String orgId;
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	public String getOrgId() {
		return orgId;
	}
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
}
