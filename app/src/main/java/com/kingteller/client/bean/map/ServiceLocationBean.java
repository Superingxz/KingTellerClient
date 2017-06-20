package com.kingteller.client.bean.map;

import java.io.Serializable;

public class ServiceLocationBean implements Serializable{

	private static final long serialVersionUID = -3220208503303312190L;
	
	private String orgId;
	private String orgName;
	private String locationFlag;
	private String orgLocation;
	private String locationName;
	private String flag;
	
	
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
	public String getLocationFlag() {
		return locationFlag;
	}
	public void setLocationFlag(String locationFlag) {
		this.locationFlag = locationFlag;
	}
	public String getOrgLocation() {
		return orgLocation;
	}
	public void setOrgLocation(String orgLocation) {
		this.orgLocation = orgLocation;
	}
	public String getLocationName() {
		return locationName;
	}
	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}

	
}
