package com.kingteller.client.bean.qrcode;

import java.io.Serializable;

public class QRWRBean implements Serializable {

	private static final long serialVersionUID = 3531218214141584738L;
	
	private String wrId;//仓库id
	private String wrName;//仓库名称
	
	private String wdId;//网点id
	private String wdName;//网点名称
	
	private String code;//获取状态信息
	
	
	public String getWdId() {
		return wdId;
	}
	public void setWdId(String wdId) {
		this.wdId = wdId;
	}
	public String getWdName() {
		return wdName;
	}
	public void setWdName(String wdName) {
		this.wdName = wdName;
	}
	public String getWrId() {
		return wrId;
	}
	public void setWrId(String wrId) {
		this.wrId = wrId;
	}
	public String getWrName() {
		return wrName;
	}
	public void setWrName(String wrName) {
		this.wrName = wrName;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	

}
