package com.kingteller.client.bean.workorder;

import java.io.Serializable;

/**
 * tb_bjgl_atm_parts_config_wl 表
 */
public class LoadBjglAtmpartsWlBean implements Serializable{

	private static final long serialVersionUID = 7551924390931801498L;
	
	private String configFormId;     // 主键id
	private String configId;         // 机器贴码部件标配表id
	private String wlInfoId;         // 物料种类id
	private String newCode;          // 物料编码
	private String wlName;           // 物料名称
	private String remark;           // 备注  
	private String createDateStr;	 // 创建时间
	private String createUserId;     // 创建人id
	private Long status;             // 状态：0禁用，1可用     
	private String updateDateStr;	// 更改日期
	private String updateUserId;     // 更改人ID
	
	public String getConfigFormId() {
		return configFormId;
	}
	public void setConfigFormId(String configFormId) {
		this.configFormId = configFormId;
	}
	public String getConfigId() {
		return configId;
	}
	public void setConfigId(String configId) {
		this.configId = configId;
	}
	public String getWlInfoId() {
		return wlInfoId;
	}
	public void setWlInfoId(String wlInfoId) {
		this.wlInfoId = wlInfoId;
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
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getCreateDateStr() {
		return createDateStr;
	}
	public void setCreateDateStr(String createDateStr) {
		this.createDateStr = createDateStr;
	}
	public String getCreateUserId() {
		return createUserId;
	}
	public void setCreateUserId(String createUserId) {
		this.createUserId = createUserId;
	}
	public Long getStatus() {
		return status;
	}
	public void setStatus(Long status) {
		this.status = status;
	}
	public String getUpdateDateStr() {
		return updateDateStr;
	}
	public void setUpdateDateStr(String updateDateStr) {
		this.updateDateStr = updateDateStr;
	}
	public String getUpdateUserId() {
		return updateUserId;
	}
	public void setUpdateUserId(String updateUserId) {
		this.updateUserId = updateUserId;
	}
	
	

}
