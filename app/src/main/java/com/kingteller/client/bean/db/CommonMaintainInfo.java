package com.kingteller.client.bean.db;

import com.kingteller.framework.annotation.sqlite.Id;
import com.kingteller.framework.annotation.sqlite.Table;


@Table(name = "maintaininfov2")
public class CommonMaintainInfo {

	@Id(column = "maintainId")
	private String maintainId; // id
	private String maintainData;// 数据
	private int isSuccess;//服务器保存是否成功 0失败 1成功

	public String getMaintainId() {
		return maintainId;
	}

	public void setMaintainId(String maintainId) {
		this.maintainId = maintainId;
	}

	public String getMaintainData() {
		return maintainData;
	}

	public void setMaintainData(String maintainData) {
		this.maintainData = maintainData;
	}

	public int getIsSuccess() {
		return isSuccess;
	}

	public void setIsSuccess(int isSuccess) {
		this.isSuccess = isSuccess;
	}

}
