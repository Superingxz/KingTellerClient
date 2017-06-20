package com.kingteller.client.bean.offlineupload;

import java.io.Serializable;
import java.util.List;

import com.kingteller.client.bean.qrcode.QRCargoScanBean;

/**
 * 离线设备部件更换列表 bean
 * @author Administrator
 */
public class OfflineDotMachineReplaceBean implements Serializable{

	private static final long serialVersionUID = 2496188548021843501L;
	
	private String cacheDataId;//缓存数据id
	private List<QRCargoScanBean> ewmList; //二维码数据
	private String cacheOtherData;//其他数据   工单id,工单单号
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
	public List<QRCargoScanBean> getEwmList() {
		return ewmList;
	}
	public void setEwmList(List<QRCargoScanBean> ewmList) {
		this.ewmList = ewmList;
	}
	public String getCacheTime() {
		return cacheTime;
	}
	public void setCacheTime(String cacheTime) {
		this.cacheTime = cacheTime;
	}
	
	
	public OfflineDotMachineReplaceBean(String cacheDataId,
			List<QRCargoScanBean> ewmList, String cacheOtherData, String cacheTime) {
		super();
		this.cacheDataId = cacheDataId;
		this.ewmList = ewmList;
		this.cacheOtherData = cacheOtherData;
		this.cacheTime = cacheTime;
	}
}
