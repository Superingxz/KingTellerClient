package com.kingteller.client.bean.db;

import java.util.List;

import com.kingteller.framework.annotation.sqlite.Id;
import com.kingteller.framework.annotation.sqlite.Table;

/**
 * 数据库保存网点机器部件更换
 * @author 王定波
 * @param dotMachineReplaceId 		网点机器部件更换id			值  = jqid,gdid   		机器id,工单id
 * @param dotMachineReplaceType 	数据类型					值  = dotmachinereplace
 * @param dotMachineReplaceData		网点机器部件更换--小组数据		值  = List<QRCargoScanBean>
 * @param dotMachineReplaceOtherData	缓存其他数据			值  =	gdid,gddh,jqid,jqbm  工单id,工单单号,机器id,机器编号
 * @param dotMachineReplaceTime			缓存时间
 * @param isSuccess			服务器保存是否成功 					值  =	0:失败		1:成功
 */
@Table(name = "dotmachinereplacev2")
public class QRDotMachineReplace {
	@Id(column = "dotMachineReplaceId")
	
	private String dotMachineReplaceId; // 网点机器部件更换id    值  =  jqid,gdid
	private String dotMachineReplaceType; // 数据类型   值  = dotmachinereplace 
	private String dotMachineReplaceData;// 网点机器部件更换--小组数据
	private String dotMachineReplaceOtherData;// 缓存其他数据
	private String dotMachineReplaceTime; // 缓存时间
	private int isSuccess;//服务器保存是否成功   0失败    1成功
	
	public String getDotMachineReplaceOtherData() {
		return dotMachineReplaceOtherData;
	}
	public void setDotMachineReplaceOtherData(String dotMachineReplaceOtherData) {
		this.dotMachineReplaceOtherData = dotMachineReplaceOtherData;
	}
	public String getDotMachineReplaceType() {
		return dotMachineReplaceType;
	}
	public void setDotMachineReplaceType(String dotMachineReplaceType) {
		this.dotMachineReplaceType = dotMachineReplaceType;
	}
	public String getDotMachineReplaceTime() {
		return dotMachineReplaceTime;
	}
	public void setDotMachineReplaceTime(String dotMachineReplaceTime) {
		this.dotMachineReplaceTime = dotMachineReplaceTime;
	}
	public String getDotMachineReplaceData() {
		return dotMachineReplaceData;
	}
	public void setDotMachineReplaceData(String dotMachineReplaceData) {
		this.dotMachineReplaceData = dotMachineReplaceData;
	}
	public String getDotMachineReplaceId() {
		return dotMachineReplaceId;
	}
	public void setDotMachineReplaceId(String dotMachineReplaceId) {
		this.dotMachineReplaceId = dotMachineReplaceId;
	}
	public int getIsSuccess() {
		return isSuccess;
	}
	public void setIsSuccess(int isSuccess) {
		this.isSuccess = isSuccess;
	}
	
	
}
