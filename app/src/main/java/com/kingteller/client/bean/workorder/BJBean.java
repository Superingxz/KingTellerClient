package com.kingteller.client.bean.workorder;

import java.io.Serializable;

/**
 * 备件模块
 * 
 * @author 王定波
 * 
 */
public class BJBean implements Serializable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id; // 主键id
	private String idLike;
	private String reportId; // 报告id
	private String reportIdLike;
	private String orderId; // 工单id
	private String orderIdLike;
	private String troubleType; // 故障类型
	private String troubleTypeLike;
	private String troubleModule; // 故障部件
	private String troubleModuleLike;
	private Long isChangeModule; // 是否更换
	private String installBjWlId; // 安装上去的多个物料id
	private String installBjWlIdLike;
	private String installBjWlName; // 安装上去的多个物料名称
	private String installBjWlNameLike;
	private String installBjWlCode; // 安装上去的多个物料条码
	private String installBjWlCodeLike;
	private String downBjWlId; // 原来安装的多个物料id
	private String downBjWlIdLike;
	private String downBjWlName; // 原来安装的多个物料名称
	private String downBjWlNameLike;
	private String downBjWlCode; // 原来安装的多个物料条码
	private String downBjWlCodeLike;
	private String changeDate; // 更换时间
	private String changeDateRange1;
	private String changeDateRange2;
	private String changeDateStr;
	private String changeErrorcode; // 故障代码
	private String changeErrorcodeLike;
	private String handleSubId; // 处理过程id
	private String handleSubIdLike;
	private String handleSub; // 处理过程描述文本（如果是工程师手工填写的，则没有id，只有文本）
	private String handleSubLike;
	private String troubleRemarkId; // 故障描述id
	private String troubleRemarkIdLike;
	private String troubleRemark; // 故障描述文本（如果是工程师手工填写的，则没有id，只有文本）
	private String troubleRemarkLike;
	private String remark; // 备注
	private String remarkLike;
	private String expand1; // 扩展1
	private String expand1Like;
	private String expand2; // 扩展2
	private String expand2Like;
	private String expand3; // 扩展3
	private String expand3Like;
	private String expand4; // 扩展4
	private String expand4Like;
	private String expand5; // 扩展5
	private String expand5Like;
	private String isChangeModuleStr;
	private String machineBjId;          // 机器配置信息id
	private String machineBjName;        // 机器配置信息名称
	private String machineAttr;          // 机器字段
	private String machineAttrName;      // 机器字段名称
    private String changeSm;             // 更换情况（0：原配置正确；1：原配置错误且已修改；2：原配置错误未修改）
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getIdLike() {
		return idLike;
	}
	public void setIdLike(String idLike) {
		this.idLike = idLike;
	}
	public String getReportId() {
		return reportId;
	}
	public void setReportId(String reportId) {
		this.reportId = reportId;
	}
	public String getReportIdLike() {
		return reportIdLike;
	}
	public void setReportIdLike(String reportIdLike) {
		this.reportIdLike = reportIdLike;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getOrderIdLike() {
		return orderIdLike;
	}
	public void setOrderIdLike(String orderIdLike) {
		this.orderIdLike = orderIdLike;
	}
	public String getTroubleType() {
		return troubleType;
	}
	public void setTroubleType(String troubleType) {
		this.troubleType = troubleType;
	}
	public String getTroubleTypeLike() {
		return troubleTypeLike;
	}
	public void setTroubleTypeLike(String troubleTypeLike) {
		this.troubleTypeLike = troubleTypeLike;
	}
	public String getTroubleModule() {
		return troubleModule;
	}
	public void setTroubleModule(String troubleModule) {
		this.troubleModule = troubleModule;
	}
	public String getTroubleModuleLike() {
		return troubleModuleLike;
	}
	public void setTroubleModuleLike(String troubleModuleLike) {
		this.troubleModuleLike = troubleModuleLike;
	}
	public Long getIsChangeModule() {
		return isChangeModule;
	}
	public void setIsChangeModule(Long isChangeModule) {
		this.isChangeModule = isChangeModule;
	}
	public String getInstallBjWlId() {
		return installBjWlId;
	}
	public void setInstallBjWlId(String installBjWlId) {
		this.installBjWlId = installBjWlId;
	}
	public String getInstallBjWlIdLike() {
		return installBjWlIdLike;
	}
	public void setInstallBjWlIdLike(String installBjWlIdLike) {
		this.installBjWlIdLike = installBjWlIdLike;
	}
	public String getInstallBjWlName() {
		return installBjWlName;
	}
	public void setInstallBjWlName(String installBjWlName) {
		this.installBjWlName = installBjWlName;
	}
	public String getInstallBjWlNameLike() {
		return installBjWlNameLike;
	}
	public void setInstallBjWlNameLike(String installBjWlNameLike) {
		this.installBjWlNameLike = installBjWlNameLike;
	}
	public String getInstallBjWlCode() {
		return installBjWlCode;
	}
	public void setInstallBjWlCode(String installBjWlCode) {
		this.installBjWlCode = installBjWlCode;
	}
	public String getInstallBjWlCodeLike() {
		return installBjWlCodeLike;
	}
	public void setInstallBjWlCodeLike(String installBjWlCodeLike) {
		this.installBjWlCodeLike = installBjWlCodeLike;
	}
	public String getDownBjWlId() {
		return downBjWlId;
	}
	public void setDownBjWlId(String downBjWlId) {
		this.downBjWlId = downBjWlId;
	}
	public String getDownBjWlIdLike() {
		return downBjWlIdLike;
	}
	public void setDownBjWlIdLike(String downBjWlIdLike) {
		this.downBjWlIdLike = downBjWlIdLike;
	}
	public String getDownBjWlName() {
		return downBjWlName;
	}
	public void setDownBjWlName(String downBjWlName) {
		this.downBjWlName = downBjWlName;
	}
	public String getDownBjWlNameLike() {
		return downBjWlNameLike;
	}
	public void setDownBjWlNameLike(String downBjWlNameLike) {
		this.downBjWlNameLike = downBjWlNameLike;
	}
	public String getDownBjWlCode() {
		return downBjWlCode;
	}
	public void setDownBjWlCode(String downBjWlCode) {
		this.downBjWlCode = downBjWlCode;
	}
	public String getDownBjWlCodeLike() {
		return downBjWlCodeLike;
	}
	public void setDownBjWlCodeLike(String downBjWlCodeLike) {
		this.downBjWlCodeLike = downBjWlCodeLike;
	}
	public String getChangeDate() {
		return changeDate;
	}
	public void setChangeDate(String changeDate) {
		this.changeDate = changeDate;
	}
	public String getChangeDateRange1() {
		return changeDateRange1;
	}
	public void setChangeDateRange1(String changeDateRange1) {
		this.changeDateRange1 = changeDateRange1;
	}
	public String getChangeDateRange2() {
		return changeDateRange2;
	}
	public void setChangeDateRange2(String changeDateRange2) {
		this.changeDateRange2 = changeDateRange2;
	}
	public String getChangeDateStr() {
		return changeDateStr;
	}
	public void setChangeDateStr(String changeDateStr) {
		this.changeDateStr = changeDateStr;
	}
	public String getChangeErrorcode() {
		return changeErrorcode;
	}
	public void setChangeErrorcode(String changeErrorcode) {
		this.changeErrorcode = changeErrorcode;
	}
	public String getChangeErrorcodeLike() {
		return changeErrorcodeLike;
	}
	public void setChangeErrorcodeLike(String changeErrorcodeLike) {
		this.changeErrorcodeLike = changeErrorcodeLike;
	}
	public String getHandleSubId() {
		return handleSubId;
	}
	public void setHandleSubId(String handleSubId) {
		this.handleSubId = handleSubId;
	}
	public String getHandleSubIdLike() {
		return handleSubIdLike;
	}
	public void setHandleSubIdLike(String handleSubIdLike) {
		this.handleSubIdLike = handleSubIdLike;
	}
	public String getHandleSub() {
		return handleSub;
	}
	public void setHandleSub(String handleSub) {
		this.handleSub = handleSub;
	}
	public String getHandleSubLike() {
		return handleSubLike;
	}
	public void setHandleSubLike(String handleSubLike) {
		this.handleSubLike = handleSubLike;
	}
	public String getTroubleRemarkId() {
		return troubleRemarkId;
	}
	public void setTroubleRemarkId(String troubleRemarkId) {
		this.troubleRemarkId = troubleRemarkId;
	}
	public String getTroubleRemarkIdLike() {
		return troubleRemarkIdLike;
	}
	public void setTroubleRemarkIdLike(String troubleRemarkIdLike) {
		this.troubleRemarkIdLike = troubleRemarkIdLike;
	}
	public String getTroubleRemark() {
		return troubleRemark;
	}
	public void setTroubleRemark(String troubleRemark) {
		this.troubleRemark = troubleRemark;
	}
	public String getTroubleRemarkLike() {
		return troubleRemarkLike;
	}
	public void setTroubleRemarkLike(String troubleRemarkLike) {
		this.troubleRemarkLike = troubleRemarkLike;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getRemarkLike() {
		return remarkLike;
	}
	public void setRemarkLike(String remarkLike) {
		this.remarkLike = remarkLike;
	}
	public String getExpand1() {
		return expand1;
	}
	public void setExpand1(String expand1) {
		this.expand1 = expand1;
	}
	public String getExpand1Like() {
		return expand1Like;
	}
	public void setExpand1Like(String expand1Like) {
		this.expand1Like = expand1Like;
	}
	public String getExpand2() {
		return expand2;
	}
	public void setExpand2(String expand2) {
		this.expand2 = expand2;
	}
	public String getExpand2Like() {
		return expand2Like;
	}
	public void setExpand2Like(String expand2Like) {
		this.expand2Like = expand2Like;
	}
	public String getExpand3() {
		return expand3;
	}
	public void setExpand3(String expand3) {
		this.expand3 = expand3;
	}
	public String getExpand3Like() {
		return expand3Like;
	}
	public void setExpand3Like(String expand3Like) {
		this.expand3Like = expand3Like;
	}
	public String getExpand4() {
		return expand4;
	}
	public void setExpand4(String expand4) {
		this.expand4 = expand4;
	}
	public String getExpand4Like() {
		return expand4Like;
	}
	public void setExpand4Like(String expand4Like) {
		this.expand4Like = expand4Like;
	}
	public String getExpand5() {
		return expand5;
	}
	public void setExpand5(String expand5) {
		this.expand5 = expand5;
	}
	public String getExpand5Like() {
		return expand5Like;
	}
	public void setExpand5Like(String expand5Like) {
		this.expand5Like = expand5Like;
	}
	public String getIsChangeModuleStr() {
		return isChangeModuleStr;
	}
	public void setIsChangeModuleStr(String isChangeModuleStr) {
		this.isChangeModuleStr = isChangeModuleStr;
	}
	public String getMachineBjId() {
		return machineBjId;
	}
	public void setMachineBjId(String machineBjId) {
		this.machineBjId = machineBjId;
	}
	public String getMachineBjName() {
		return machineBjName;
	}
	public void setMachineBjName(String machineBjName) {
		this.machineBjName = machineBjName;
	}
	public String getMachineAttr() {
		return machineAttr;
	}
	public void setMachineAttr(String machineAttr) {
		this.machineAttr = machineAttr;
	}
	public String getMachineAttrName() {
		return machineAttrName;
	}
	public void setMachineAttrName(String machineAttrName) {
		this.machineAttrName = machineAttrName;
	}
	public String getChangeSm() {
		return changeSm;
	}
	public void setChangeSm(String changeSm) {
		this.changeSm = changeSm;
	}

}
