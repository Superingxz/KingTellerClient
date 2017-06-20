package com.kingteller.client.bean.qrcode;

import java.io.Serializable;
import java.util.List;

/**
 * 追溯数据类
 * @author Administrator
 */

public class QRTraceBackToBean implements Serializable{

	private static final long serialVersionUID = -7978960113181413123L;
	
	
	private String baseInfoId;//追溯基础信息ID
	private String barcode;//二维码
	private String newCode;//物料编码
	private String wlName;//物料名称
	private String modelNo;//规格型号
	
	private String isWholeMachineName;//是否整机
	private String statusName;//状态
	private String locationName;//所在地点
	private String wdAddress;//网点地址
	private String machineCode;//机器编码
	
	private String maintenanceName;//维保商
	private String produceNum;//生产/采购订单号
	private String stockDateStr;//入库日期
	private String version;//版本
	private String supplierName;//供应商名称
	private String supplierCode;//供应商代码
	private String supplierBarcode;//厂家编码号
	private String serialNumber;//厂家流水码
	private String deliveryDateStr;//发货日期
	private String orgName;//机构
	private String areaName;//区域
	private String bigAreaName;//大区
	private String maintenancePeriod;//维保期限
	private String serviceLifePeriod;//使用年限
	private String faultTime;//故障次数
	
	private List<QRTraceBackToSjBean> recordList;
	
	private List<QRTraceBackToZlBean> maintainList;
	
	private String code;//获取状态信息
	

	public String getStockDateStr() {
		return stockDateStr;
	}

	public void setStockDateStr(String stockDateStr) {
		this.stockDateStr = stockDateStr;
	}

	public String getFaultTime() {
		return faultTime;
	}

	public void setFaultTime(String faultTime) {
		this.faultTime = faultTime;
	}

	public List<QRTraceBackToZlBean> getMaintainList() {
		return maintainList;
	}

	public void setMaintainList(List<QRTraceBackToZlBean> maintainList) {
		this.maintainList = maintainList;
	}

	public String getSupplierCode() {
		return supplierCode;
	}

	public void setSupplierCode(String supplierCode) {
		this.supplierCode = supplierCode;
	}

	public String getMaintenancePeriod() {
		return maintenancePeriod;
	}

	public void setMaintenancePeriod(String maintenancePeriod) {
		this.maintenancePeriod = maintenancePeriod;
	}

	public String getServiceLifePeriod() {
		return serviceLifePeriod;
	}

	public void setServiceLifePeriod(String serviceLifePeriod) {
		this.serviceLifePeriod = serviceLifePeriod;
	}

	public List<QRTraceBackToSjBean> getRecordList() {
		return recordList;
	}

	public void setRecordList(List<QRTraceBackToSjBean> recordList) {
		this.recordList = recordList;
	}

	public String getBaseInfoId() {
		return baseInfoId;
	}

	public void setBaseInfoId(String baseInfoId) {
		this.baseInfoId = baseInfoId;
	}

	public String getNewCode() {
		return newCode;
	}

	public void setNewCode(String newCode) {
		this.newCode = newCode;
	}

	public String getWlName() {
		return wlName;
	}

	public void setWlName(String wlName) {
		this.wlName = wlName;
	}

	public String getModelNo() {
		return modelNo;
	}

	public void setModelNo(String modelNo) {
		this.modelNo = modelNo;
	}

	public String getIsWholeMachineName() {
		return isWholeMachineName;
	}

	public void setIsWholeMachineName(String isWholeMachineName) {
		this.isWholeMachineName = isWholeMachineName;
	}

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	public String getLocationName() {
		return locationName;
	}

	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}

	public String getWdAddress() {
		return wdAddress;
	}

	public void setWdAddress(String wdAddress) {
		this.wdAddress = wdAddress;
	}

	public String getMachineCode() {
		return machineCode;
	}

	public void setMachineCode(String machineCode) {
		this.machineCode = machineCode;
	}

	public String getMaintenanceName() {
		return maintenanceName;
	}

	public void setMaintenanceName(String maintenanceName) {
		this.maintenanceName = maintenanceName;
	}

	public String getProduceNum() {
		return produceNum;
	}

	public void setProduceNum(String produceNum) {
		this.produceNum = produceNum;
	}


	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public String getSupplierBarcode() {
		return supplierBarcode;
	}

	public void setSupplierBarcode(String supplierBarcode) {
		this.supplierBarcode = supplierBarcode;
	}

	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public String getDeliveryDateStr() {
		return deliveryDateStr;
	}

	public void setDeliveryDateStr(String deliveryDateStr) {
		this.deliveryDateStr = deliveryDateStr;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public String getBigAreaName() {
		return bigAreaName;
	}

	public void setBigAreaName(String bigAreaName) {
		this.bigAreaName = bigAreaName;
	}

	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}


	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	
}
