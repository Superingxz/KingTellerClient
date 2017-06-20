package com.kingteller.client.bean.offlineupload;

import java.io.Serializable;
import java.util.List;

import com.kingteller.client.bean.qrcode.QRDotMachineBean;

/**
 * 离线设备录入列表 bean
 * @author Administrator
 */
public class OfflineDotMachineBean implements Serializable{

	private static final long serialVersionUID = -7453703817994695190L;
	
	private String cacheDataId;//缓存数据id
	private List<QRDotMachineBean> ewmList; //二维码数据
	private String cacheOtherData;//其他数据   	机器id,机器编号
	private String cacheTime;//缓存时间

	
	public String getCacheOtherData() {
		return cacheOtherData;
	}

	public void setCacheOtherData(String cacheOtherData) {
		this.cacheOtherData = cacheOtherData;
	}

	public String getCacheDataId() {
		return cacheDataId;
	}

	public void setCacheDataId(String cacheDataId) {
		this.cacheDataId = cacheDataId;
	}

	public List<QRDotMachineBean> getEwmList() {
		return ewmList;
	}

	public void setEwmList(List<QRDotMachineBean> ewmList) {
		this.ewmList = ewmList;
	}

	public String getCacheTime() {
		return cacheTime;
	}

	public void setCacheTime(String cacheTime) {
		this.cacheTime = cacheTime;
	}
	
	public OfflineDotMachineBean( String cacheDataId, List<QRDotMachineBean> ewmList, String cacheOtherData, String cacheTime) {
		super();
		this.cacheDataId = cacheDataId;
		this.ewmList = ewmList;
		this.cacheOtherData = cacheOtherData;
		this.cacheTime = cacheTime;
	}
}
