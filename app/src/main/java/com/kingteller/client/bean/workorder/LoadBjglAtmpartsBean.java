package com.kingteller.client.bean.workorder;

import java.io.Serializable;

/**
 * tb_bjgl_atm_parts_config 表
 */
public class LoadBjglAtmpartsBean implements Serializable {

	private static final long serialVersionUID = -7321589879645944700L;
	private String configId;         // 主键id
	private String configName;       // 名称
	private Long nodeLevel;          // 树形级别标志：0 顶级目录（整机），后面模块大小按照顺序依次类推；
	private String parentId;         // 父级id（0为最顶级）
	private Long status;             // 状态：0 禁用，1 可用；
	private Long indexNum;           // 顺序号
	private Long isScan;             // 是否必扫描项：0 否，1 是；
	private String remark;           // 备注 
	private String createDateStr;	 // 创建时间
	private String createUserId;     // 创建人id
	private String configPath;       // 层级路径
	private Long isJixin;            // 是否机芯(0否，1是主机芯，2是子机芯)
	private Long isMany;             // 是否有多个（0：否  1：是）      
	private String updateDateStr; 	 // 修改日期
	private String updateUserId;     // 修改人
	
	public String getConfigId() {
		return configId;
	}
	public void setConfigId(String configId) {
		this.configId = configId;
	}
	public String getConfigName() {
		return configName;
	}
	public void setConfigName(String configName) {
		this.configName = configName;
	}
	public Long getNodeLevel() {
		return nodeLevel;
	}
	public void setNodeLevel(Long nodeLevel) {
		this.nodeLevel = nodeLevel;
	}
	public String getParentId() {
		return parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	public Long getStatus() {
		return status;
	}
	public void setStatus(Long status) {
		this.status = status;
	}
	public Long getIndexNum() {
		return indexNum;
	}
	public void setIndexNum(Long indexNum) {
		this.indexNum = indexNum;
	}
	public Long getIsScan() {
		return isScan;
	}
	public void setIsScan(Long isScan) {
		this.isScan = isScan;
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
	public String getConfigPath() {
		return configPath;
	}
	public void setConfigPath(String configPath) {
		this.configPath = configPath;
	}
	public Long getIsJixin() {
		return isJixin;
	}
	public void setIsJixin(Long isJixin) {
		this.isJixin = isJixin;
	}
	public Long getIsMany() {
		return isMany;
	}
	public void setIsMany(Long isMany) {
		this.isMany = isMany;
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
