package com.kingteller.client.bean.db;

import com.kingteller.framework.annotation.sqlite.Id;
import com.kingteller.framework.annotation.sqlite.Table;

/**
 * 网点发货扫描
 * @author 王定波
 * @param dotDeliveryId 		网点机器部件更换id			值  = 服务站id
 * @param dotDeliveryType 		数据类型					值  = dotdelivery
 * @param dotDeliveryData		网点机器部件更换--小组数据		值  = List<QRCargoScanBean>
 * @param dotDeliveryOtherData	缓存其他数据				值  =	所属服务站
 * @param dotDeliveryTime			缓存时间
 * @param isSuccess			服务器保存是否成功 			值  =	0:失败		1:成功
 */
@Table(name = "dotdeliveryv2")
public class QRDotDelivery {
	@Id(column = "dotDeliveryId")
	
	private String dotDeliveryId; // 网点发货id  		值  =  服务站id
	private String dotDeliveryType;// 数据类型  		 值  = "dotdelivery"
	private String dotDeliveryData;// 网点发货数据
	private String dotDeliveryOtherData;// 其他数据  
	private String dotDeliveryTime;// 缓存时间
	private int isSuccess;//服务器保存是否成功 0失败 1成功
	
	public String getDotDeliveryTime() {
		return dotDeliveryTime;
	}
	public void setDotDeliveryTime(String dotDeliveryTime) {
		this.dotDeliveryTime = dotDeliveryTime;
	}
	public String getDotDeliveryType() {
		return dotDeliveryType;
	}
	public void setDotDeliveryType(String dotDeliveryType) {
		this.dotDeliveryType = dotDeliveryType;
	}
	public String getDotDeliveryOtherData() {
		return dotDeliveryOtherData;
	}
	public void setDotDeliveryOtherData(String dotDeliveryOtherData) {
		this.dotDeliveryOtherData = dotDeliveryOtherData;
	}
	public String getDotDeliveryId() {
		return dotDeliveryId;
	}
	public void setDotDeliveryId(String dotDeliveryId) {
		this.dotDeliveryId = dotDeliveryId;
	}
	public String getDotDeliveryData() {
		return dotDeliveryData;
	}
	public void setDotDeliveryData(String dotDeliveryData) {
		this.dotDeliveryData = dotDeliveryData;
	}
	public int getIsSuccess() {
		return isSuccess;
	}
	public void setIsSuccess(int isSuccess) {
		this.isSuccess = isSuccess;
	}
	
	
}
