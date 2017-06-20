package com.kingteller.client.bean.db;

import com.kingteller.framework.annotation.sqlite.Id;
import com.kingteller.framework.annotation.sqlite.Table;

/**
 * 数据库保存网点机器部件更换_离线
 * @author 王定波
 * @param offlinedotMachineReplaceId 		网点机器部件更换id			值  = 自增id
 * @param offlinedotMachineReplaceType 		数据类型					值  = dotmachinereplace_offline
 * @param offlinedotMachineReplaceData		网点机器部件更换--小组数据		值  = List<QRCargoScanBean>
 * @param offlinedotMachineReplaceOtherData	缓存其他数据				值  =	工单id,工单单号,机器id,机器编号
 * @param offlinedotMachineReplaceTime			缓存时间
 * @param isSuccess			服务器保存是否成功 			值  =	0:失败		1:成功
 */
@Table(name = "offlinedotmachinereplacev2")
public class QROfflineDotMachineReplace {
	@Id(column = "offlinedotMachineReplaceId")
	
	private String offlinedotMachineReplaceId; 			// 网点机器部件更换id    		值  =	自增id
	private String offlinedotMachineReplaceType; 		// 数据类型 				值  = dotmachinereplace_offline 
	private String offlinedotMachineReplaceData;		// 网点机器部件更换--小组数据	值  = List<QRCargoScanBean>
	private String offlinedotMachineReplaceOtherData;	// 缓存其他数据
	private String offlinedotMachineReplaceTime; 		// 缓存时间
	private int isSuccess;//服务器保存是否成功   0失败    1成功
	
	public int getIsSuccess() {
		return isSuccess;
	}
	public void setIsSuccess(int isSuccess) {
		this.isSuccess = isSuccess;
	}
	public String getOfflinedotMachineReplaceId() {
		return offlinedotMachineReplaceId;
	}
	public void setOfflinedotMachineReplaceId(String offlinedotMachineReplaceId) {
		this.offlinedotMachineReplaceId = offlinedotMachineReplaceId;
	}
	public String getOfflinedotMachineReplaceType() {
		return offlinedotMachineReplaceType;
	}
	public void setOfflinedotMachineReplaceType(String offlinedotMachineReplaceType) {
		this.offlinedotMachineReplaceType = offlinedotMachineReplaceType;
	}
	public String getOfflinedotMachineReplaceData() {
		return offlinedotMachineReplaceData;
	}
	public void setOfflinedotMachineReplaceData(String offlinedotMachineReplaceData) {
		this.offlinedotMachineReplaceData = offlinedotMachineReplaceData;
	}
	public String getOfflinedotMachineReplaceOtherData() {
		return offlinedotMachineReplaceOtherData;
	}
	public void setOfflinedotMachineReplaceOtherData(
			String offlinedotMachineReplaceOtherData) {
		this.offlinedotMachineReplaceOtherData = offlinedotMachineReplaceOtherData;
	}
	public String getOfflinedotMachineReplaceTime() {
		return offlinedotMachineReplaceTime;
	}
	public void setOfflinedotMachineReplaceTime(String offlinedotMachineReplaceTime) {
		this.offlinedotMachineReplaceTime = offlinedotMachineReplaceTime;
	}
	
	
}

