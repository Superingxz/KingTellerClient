package com.kingteller.client.bean.db;


import com.kingteller.framework.annotation.sqlite.Id;
import com.kingteller.framework.annotation.sqlite.Table;

/**
 * 数据库保存报告
 * @author 王定波
 * @param orderId 		报告id			值  = 工单id
 * @param reportType 	报告类型			值  = 维护工单:maintenance, 其他事务:otherMatter, 物流:logistics, 清洁:clean
 * @param reportData	报告数据			值  = getFromData()
 * @param reportOtherData	其他数据		值  =	""
 * @param reportTime		缓存时间
 * @param isSuccess			服务器保存是否成功 		值  =	0:失败		1:成功
 * 
 */
@Table(name = "reportv2")
public class Report {
	@Id(column = "orderId")
	
	private String orderId; // 报告id				值  = 工单id
	private String reportType;// 报告类型			值  = 维护工单:maintenance, 其他事务:otherMatter, 物流:logistics, 清洁:clean
	private String reportData;// 报告数据			值  = getFromData()
	private String reportOtherData;// 其他数据		值  =	""
	private String reportTime;// 缓存时间
	private int isSuccess;//服务器保存是否成功		值  =	0:失败		1:成功

	public String getReportOtherData() {
		return reportOtherData;
	}

	public void setReportOtherData(String reportOtherData) {
		this.reportOtherData = reportOtherData;
	}

	public String getReportTime() {
		return reportTime;
	}

	public void setReportTime(String reportTime) {
		this.reportTime = reportTime;
	}

	public String getReportData() {
		return reportData;
	}

	public void setReportData(String reportData) {
		this.reportData = reportData;
	}

	public String getReportType() {
		return reportType;
	}

	public void setReportType(String reportType) {
		this.reportType = reportType;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public int getIsSuccess() {
		return isSuccess;
	}

	public void setIsSuccess(int isSuccess) {
		this.isSuccess = isSuccess;
	}


}
