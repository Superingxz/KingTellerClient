package com.kingteller.client.bean.offlineupload;

import java.io.Serializable;
/**
 * 缓存数据列表Bean
 */
public class OfflineUploadBean implements Serializable{
	private static final long serialVersionUID = -5740413585780236145L;
	
	private String cacheDataId;//缓存数据id
	private String cacheType;//缓存类型
	private String cacheData;//缓存数据
	private String cacheOtherData;//缓存其他数据
	private String cacheTime;//缓存时间
	private int cacheIsSuccess;//缓存数据是否保存在服务器   0失败 1成功
	
	private String code;//缓存时间
	//单号
	//机器信息
	//任务信息
	//网点设备简称
	//缓存数据
	//缓存时间
	
	
	//1.我的工单  提交数据格式

	
	

	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
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
	public int getCacheIsSuccess() {
		return cacheIsSuccess;
	}
	public void setCacheIsSuccess(int cacheIsSuccess) {
		this.cacheIsSuccess = cacheIsSuccess;
	}
	public String getCacheData() {
		return cacheData;
	}
	public void setCacheData(String cacheData) {
		this.cacheData = cacheData;
	}
	public String getCacheTime() {
		return cacheTime;
	}
	public void setCacheTime(String cacheTime) {
		this.cacheTime = cacheTime;
	}
	public void setCacheType(String cacheType) {
		this.cacheType = cacheType;
	}
	public String getCacheType() {
		return cacheType;
	}
	public String getCacheTableID() {
		return cacheDataId;
	}
	public void setCacheTableID(String cacheDataId) {
		this.cacheDataId = cacheDataId;
	}
	
	public OfflineUploadBean( String cacheDataId, String cacheType, String cacheData, String cacheOtherData, 
			String cacheTime, int cacheIsSuccess) {
		super();
		this.cacheDataId = cacheDataId;
		this.cacheType = cacheType;
		this.cacheData = cacheData;
		this.cacheOtherData = cacheOtherData;
		this.cacheTime = cacheTime;
		this.cacheIsSuccess = cacheIsSuccess;
	}

}
