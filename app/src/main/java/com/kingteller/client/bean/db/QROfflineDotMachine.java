package com.kingteller.client.bean.db;

import com.kingteller.framework.annotation.sqlite.Id;
import com.kingteller.framework.annotation.sqlite.Table;

/**
 * 数据库保存网点机器部件扫描_离线
 * @author 王定波
 * @param offlinedotMachineId 		网点机器部件录入id			值  = 自增id
 * @param offlinedotMachineType 	数据类型					值  = dotmachine_offline
 * @param offlinedotMachineData	网点机器部件数据					值  = List<QRDotMachineBean>
 * @param offlinedotMachineOtherData	缓存其他数据			值  =	机器id,机器编码
 * @param offlinedotMachineTime		缓存时间
 * @param isSuccess			服务器保存是否成功 			值  =	0:失败		1:成功
 */
@Table(name = "offlinedotmachinev2")
public class QROfflineDotMachine {
	@Id(column = "offlinedotMachineId")
	
	private String offlinedotMachineId; // 网点机器部件录入id    		值  = 自增id
	private String offlinedotMachineType; // 数据类型    				值  = dotmachine_offline
	private String offlinedotMachineData;// 网点机器部件数据			值  = List<QRDotMachineBean>
	private String offlinedotMachineOtherData;// 缓存其他数据        		值  = 机器id,机器编码
	private String offlinedotMachineTime;// 缓存时间
	private int isSuccess;//服务器保存是否成功 0失败 1成功
	
	public String getOfflinedotMachineId() {
		return offlinedotMachineId;
	}
	public void setOfflinedotMachineId(String offlinedotMachineId) {
		this.offlinedotMachineId = offlinedotMachineId;
	}
	public String getOfflinedotMachineType() {
		return offlinedotMachineType;
	}
	public void setOfflinedotMachineType(String offlinedotMachineType) {
		this.offlinedotMachineType = offlinedotMachineType;
	}
	public String getOfflinedotMachineData() {
		return offlinedotMachineData;
	}
	public void setOfflinedotMachineData(String offlinedotMachineData) {
		this.offlinedotMachineData = offlinedotMachineData;
	}
	public String getOfflinedotMachineOtherData() {
		return offlinedotMachineOtherData;
	}
	public void setOfflinedotMachineOtherData(String offlinedotMachineOtherData) {
		this.offlinedotMachineOtherData = offlinedotMachineOtherData;
	}
	public String getOfflinedotMachineTime() {
		return offlinedotMachineTime;
	}
	public void setOfflinedotMachineTime(String offlinedotMachineTime) {
		this.offlinedotMachineTime = offlinedotMachineTime;
	}
	public int getIsSuccess() {
		return isSuccess;
	}
	public void setIsSuccess(int isSuccess) {
		this.isSuccess = isSuccess;
	}
	
	
}
