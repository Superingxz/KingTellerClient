package com.kingteller.client.bean.workorder;

import java.io.Serializable;

/**
CREATE TABLE tb_sm_wl_info(
_id varchar2(32)primary key,
new_code varchar2(30),
old_code varchar2(30),
wl_name varchar2(200),
k3_name varchar2(200),
model_no varchar2(200),
type_id varchar2(32),
status integer,
wl_desc varchar2(200),
cp_newcode varchar2(200),
typeflag integer,
base_unit varchar2(50))
*/
public class LoadWlInfoBean implements Serializable{

	/**
	 * 基础物料信息
	 */
	private static final long serialVersionUID = 1L;
	private String wlInfoId;     // 主键
	private String wlInfoIdLike;
	private String newCode;      // 新物料编码
	private String newCodeLike;
	private String oldCode;      // 旧物料编码
	private String oldCodeLike;
	private String wlName;       // 仓库物料名称
	private String wlNameLike;
	private String k3Name;       // K3物料名称
	private String k3NameLike;
	private String modelNo;      // 规格型号
	private String modelNoLike;
	private String typeId;       // 所属备件类别id
	private String typeIdLike;
	private Long status;         // 状态
	private String wlDesc;       // 说明
	private String wlDescLike;
	private String cpNewcode;    // 1为备件数据 2为销售配耗,影响备件单据统计不能够为空
	private String cpNewcodeLike;
	private Long typeflag;       // 对应备件的newcode编码
	private String baseUnit;     // 基本单位名称
	private String baseUnitLike;
	private Long price;          // 物料单价
	//private String modifyDate;         // 修改时间
	private String modifyDateRange1;
	private String modifyDateRange2;
	private String modifyDateStr;
	private String modifyUserid;     // 修改人id
	private String modifyUseridLike;
	
	public String getWlInfoId() {
		return wlInfoId;
	}
	public void setWlInfoId(String wlInfoId) {
		this.wlInfoId = wlInfoId;
	}
	public String getWlInfoIdLike() {
		return wlInfoIdLike;
	}
	public void setWlInfoIdLike(String wlInfoIdLike) {
		this.wlInfoIdLike = wlInfoIdLike;
	}
	public String getNewCode() {
		return newCode;
	}
	public void setNewCode(String newCode) {
		this.newCode = newCode;
	}
	public String getNewCodeLike() {
		return newCodeLike;
	}
	public void setNewCodeLike(String newCodeLike) {
		this.newCodeLike = newCodeLike;
	}
	public String getOldCode() {
		return oldCode;
	}
	public void setOldCode(String oldCode) {
		this.oldCode = oldCode;
	}
	public String getOldCodeLike() {
		return oldCodeLike;
	}
	public void setOldCodeLike(String oldCodeLike) {
		this.oldCodeLike = oldCodeLike;
	}
	public String getWlName() {
		return wlName;
	}
	public void setWlName(String wlName) {
		this.wlName = wlName;
	}
	public String getWlNameLike() {
		return wlNameLike;
	}
	public void setWlNameLike(String wlNameLike) {
		this.wlNameLike = wlNameLike;
	}
	public String getK3Name() {
		return k3Name;
	}
	public void setK3Name(String k3Name) {
		this.k3Name = k3Name;
	}
	public String getK3NameLike() {
		return k3NameLike;
	}
	public void setK3NameLike(String k3NameLike) {
		this.k3NameLike = k3NameLike;
	}
	public String getModelNo() {
		return modelNo;
	}
	public void setModelNo(String modelNo) {
		this.modelNo = modelNo;
	}
	public String getModelNoLike() {
		return modelNoLike;
	}
	public void setModelNoLike(String modelNoLike) {
		this.modelNoLike = modelNoLike;
	}
	public String getTypeId() {
		return typeId;
	}
	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}
	public String getTypeIdLike() {
		return typeIdLike;
	}
	public void setTypeIdLike(String typeIdLike) {
		this.typeIdLike = typeIdLike;
	}
	public Long getStatus() {
		return status;
	}
	public void setStatus(Long status) {
		this.status = status;
	}
	public String getWlDesc() {
		return wlDesc;
	}
	public void setWlDesc(String wlDesc) {
		this.wlDesc = wlDesc;
	}
	public String getWlDescLike() {
		return wlDescLike;
	}
	public void setWlDescLike(String wlDescLike) {
		this.wlDescLike = wlDescLike;
	}
	public String getCpNewcode() {
		return cpNewcode;
	}
	public void setCpNewcode(String cpNewcode) {
		this.cpNewcode = cpNewcode;
	}
	public String getCpNewcodeLike() {
		return cpNewcodeLike;
	}
	public void setCpNewcodeLike(String cpNewcodeLike) {
		this.cpNewcodeLike = cpNewcodeLike;
	}
	public Long getTypeflag() {
		return typeflag;
	}
	public void setTypeflag(Long typeflag) {
		this.typeflag = typeflag;
	}
	public String getBaseUnit() {
		return baseUnit;
	}
	public void setBaseUnit(String baseUnit) {
		this.baseUnit = baseUnit;
	}
	public String getBaseUnitLike() {
		return baseUnitLike;
	}
	public void setBaseUnitLike(String baseUnitLike) {
		this.baseUnitLike = baseUnitLike;
	}
	public Long getPrice() {
		return price;
	}
	public void setPrice(Long price) {
		this.price = price;
	}
/*	public String getModifyDate() {
		return modifyDate;
	}
	public void setModifyDate(String modifyDate) {
		this.modifyDate = modifyDate;
	}*/
	public String getModifyDateRange1() {
		return modifyDateRange1;
	}
	public void setModifyDateRange1(String modifyDateRange1) {
		this.modifyDateRange1 = modifyDateRange1;
	}
	public String getModifyDateRange2() {
		return modifyDateRange2;
	}
	public void setModifyDateRange2(String modifyDateRange2) {
		this.modifyDateRange2 = modifyDateRange2;
	}
	public String getModifyDateStr() {
		return modifyDateStr;
	}
	public void setModifyDateStr(String modifyDateStr) {
		this.modifyDateStr = modifyDateStr;
	}
	public String getModifyUserid() {
		return modifyUserid;
	}
	public void setModifyUserid(String modifyUserid) {
		this.modifyUserid = modifyUserid;
	}
	public String getModifyUseridLike() {
		return modifyUseridLike;
	}
	public void setModifyUseridLike(String modifyUseridLike) {
		this.modifyUseridLike = modifyUseridLike;
	}
}
