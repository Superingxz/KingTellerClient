package com.kingteller.client.bean.workorder;

public class BjWlBean {
	private String wlInfoId; // 主键
	private String wlInfoIdLike;
	private String newCode; // 新物料编码
	private String newCodeLike;
	private String oldCode; // 旧物料编码
	private String oldCodeLike;
	private String wlName; // 仓库物料名称
	private String wlNameLike;
	private String k3Name; // K3物料名称
	private String k3NameLike;
	private String modelNo; // 规格型号
	private String modelNoLike;
	private String typeId; // 所属备件类别id
	private String typeIdLike;
	private long status; // 状态
	private String wlDesc; // 说明
	private String wlDescLike;
	private String cpNewcode; // 1为备件数据 2为销售配耗
	private String cpNewcodeLike;
	private long typeflag; // 对应备件的newcode编码
	private String baseUnit; // 基本单位名称
	private String baseUnitLike;
	private String parentName;
	private String statusName; // 菜单项状态：1 表示有效，其他数字表示无效
	private String bjTypeName;
	private String onenewCode;
	private String oldWlName;
	// private String newParentId;
	private String atmCode;
	private String atmName;
	// 类型树属性
	private String id; // 类型树数据标识
	private String parentId; // 父级物料id
	private String pathid; // 物料层次路径ID
	private long nodeLevel; // 层次
	private String pathname; // 物料层次路径名
	private long wlIndex; // 顺序号
	private String number = null;// ?
	private String flg = null;// ?
	private String countNumber; // 已去除，不使用
	private long stockQuantity; // 库存数量
	private String cpNewcodeName; // 物料类型名称：备件，耗材
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
	public String getCpNewcodeName() {
		return cpNewcodeName;
	}
	public void setCpNewcodeName(String cpNewcodeName) {
		this.cpNewcodeName = cpNewcodeName;
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
	public long getStockQuantity() {
		return stockQuantity;
	}
	public void setStockQuantity(long stockQuantity) {
		this.stockQuantity = stockQuantity;
	}
	public String getCountNumber() {
		return countNumber;
	}
	public void setCountNumber(String countNumber) {
		this.countNumber = countNumber;
	}
	public String getFlg() {
		return flg;
	}
	public void setFlg(String flg) {
		this.flg = flg;
	}
	public String getAtmCode() {
		return atmCode;
	}
	public void setAtmCode(String atmCode) {
		this.atmCode = atmCode;
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
	public long getStatus() {
		return status;
	}
	public void setStatus(long status) {
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
	public String getAtmName() {
		return atmName;
	}
	public void setAtmName(String atmName) {
		this.atmName = atmName;
	}
	public String getParentId() {
		return parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	public String getPathid() {
		return pathid;
	}
	public void setPathid(String pathid) {
		this.pathid = pathid;
	}
	public String getPathname() {
		return pathname;
	}
	public void setPathname(String pathname) {
		this.pathname = pathname;
	}
	public long getNodeLevel() {
		return nodeLevel;
	}
	public void setNodeLevel(long nodeLevel) {
		this.nodeLevel = nodeLevel;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public long getWlIndex() {
		return wlIndex;
	}
	public void setWlIndex(long wlIndex) {
		this.wlIndex = wlIndex;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getOldWlName() {
		return oldWlName;
	}
	public void setOldWlName(String oldWlName) {
		this.oldWlName = oldWlName;
	}
	public String getOnenewCode() {
		return onenewCode;
	}
	public void setOnenewCode(String onenewCode) {
		this.onenewCode = onenewCode;
	}
	public long getTypeflag() {
		return typeflag;
	}
	public void setTypeflag(long typeflag) {
		this.typeflag = typeflag;
	}
	public String getBaseUnitLike() {
		return baseUnitLike;
	}
	public void setBaseUnitLike(String baseUnitLike) {
		this.baseUnitLike = baseUnitLike;
	}
	public String getBaseUnit() {
		return baseUnit;
	}
	public void setBaseUnit(String baseUnit) {
		this.baseUnit = baseUnit;
	}
	public String getParentName() {
		return parentName;
	}
	public void setParentName(String parentName) {
		this.parentName = parentName;
	}
	public String getStatusName() {
		return statusName;
	}
	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}
	public String getBjTypeName() {
		return bjTypeName;
	}
	public void setBjTypeName(String bjTypeName) {
		this.bjTypeName = bjTypeName;
	}

}
