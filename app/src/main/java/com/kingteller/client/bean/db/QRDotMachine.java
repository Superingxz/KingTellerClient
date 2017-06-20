package com.kingteller.client.bean.db;

import com.kingteller.framework.annotation.sqlite.Id;
import com.kingteller.framework.annotation.sqlite.Table;

/**
 * 数据库保存网点机器部件扫描
 * @author 王定波
 * @param dotMachineId 	网点机器部件录入id			值  = jqId					机器id
 * @param dotMachineType 	数据类型				值  = dotMachine
 * @param dotMachineData	网点机器部件数据			值  = List<QRDotMachineBean>
 * @param dotMachineOtherData	缓存其他数据		值  =	jqid,jqbm 				机器id,机器编码,网点设备简称
 * @param dotMachineTime		缓存时间
 * @param isSuccess			服务器保存是否成功 		值  =	0:失败		1:成功
 */
@Table(name = "dotmachinev2")
public class QRDotMachine {
	@Id(column = "dotMachineId")
	
	private String dotMachineId; // 网点机器部件录入id    	值  = jqId
	private String dotMachineType; // 数据类型    			值  = dotMachine
	private String dotMachineData;// 网点机器部件数据			值  = List<QRDotMachineBean>
	private String dotMachineOtherData;// 缓存其他数据        	 值  =  所属服务站,网点设备简称,机器编码
	private String dotMachineTime;// 缓存时间
	private int isSuccess;//服务器保存是否成功 0失败 1成功
	
	public String getDotMachineOtherData() {
		return dotMachineOtherData;
	}
	public void setDotMachineOtherData(String dotMachineOtherData) {
		this.dotMachineOtherData = dotMachineOtherData;
	}
	public String getDotMachineType() {
		return dotMachineType;
	}
	public void setDotMachineType(String dotMachineType) {
		this.dotMachineType = dotMachineType;
	}
	public String getDotMachineTime() {
		return dotMachineTime;
	}
	public void setDotMachineTime(String dotMachineTime) {
		this.dotMachineTime = dotMachineTime;
	}
	public String getDotMachineId() {
		return dotMachineId;
	}
	public void setDotMachineId(String dotMachineId) {
		this.dotMachineId = dotMachineId;
	}
	public String getDotMachineData() {
		return dotMachineData;
	}
	public void setDotMachineData(String dotMachineData) {
		this.dotMachineData = dotMachineData;
	}
	public int getIsSuccess() {
		return isSuccess;
	}
	public void setIsSuccess(int isSuccess) {
		this.isSuccess = isSuccess;
	}
	
	
}
