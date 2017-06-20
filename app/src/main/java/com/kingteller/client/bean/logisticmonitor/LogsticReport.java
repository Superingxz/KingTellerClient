package com.kingteller.client.bean.logisticmonitor;

import com.kingteller.framework.annotation.sqlite.Id;
import com.kingteller.framework.annotation.sqlite.Table;

@Table(name = "logsticreportv2")
public class LogsticReport {

	@Id(column = "rwdId")
	private String rwdId;//任务单ID
	private String atacb;
	private int isSuccess;//服务器保存是否成功 0失败 1成功
	public String getRwdId() {
		return rwdId;
	}
	public void setRwdId(String rwdId) {
		this.rwdId = rwdId;
	}
	public String getAtacb() {
		return atacb;
	}
	public void setAtacb(String atacb) {
		this.atacb = atacb;
	}
	public int getIsSuccess() {
		return isSuccess;
	}
	public void setIsSuccess(int isSuccess) {
		this.isSuccess = isSuccess;
	}
	
	
}
