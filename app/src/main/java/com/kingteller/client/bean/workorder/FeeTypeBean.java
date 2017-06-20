package com.kingteller.client.bean.workorder;

public class FeeTypeBean {
	private String id; // 主键ID
	private String eName; // 费用名称
	private String eNameLike;
	private Long status; // 费用状态0不可用，1可用
	private String pid; // 当前类别的父ID
	private Long eLevel; // 种类的级别
	private String descript; // 种类描述
	private String isdetail; // 是否有明细 1、是 0、否
	private String code; // 编码
	private Long indexs; // 序列
	private String totalMoney; // 该费用类型的钱数总计
	private String expend1; // 扩展列1
	private String expend2; // 扩展列1
	private String pName;
	private String operation;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getOperation() {
		return operation;
	}
	public void setOperation(String operation) {
		this.operation = operation;
	}
	public String getpName() {
		return pName;
	}
	public void setpName(String pName) {
		this.pName = pName;
	}
	public String getExpend2() {
		return expend2;
	}
	public void setExpend2(String expend2) {
		this.expend2 = expend2;
	}
	public String getExpend1() {
		return expend1;
	}
	public void setExpend1(String expend1) {
		this.expend1 = expend1;
	}
	public String getTotalMoney() {
		return totalMoney;
	}
	public void setTotalMoney(String totalMoney) {
		this.totalMoney = totalMoney;
	}
	public Long getIndexs() {
		return indexs;
	}
	public void setIndexs(Long indexs) {
		this.indexs = indexs;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getIsdetail() {
		return isdetail;
	}
	public void setIsdetail(String isdetail) {
		this.isdetail = isdetail;
	}
	public String getDescript() {
		return descript;
	}
	public void setDescript(String descript) {
		this.descript = descript;
	}
	public Long geteLevel() {
		return eLevel;
	}
	public void seteLevel(Long eLevel) {
		this.eLevel = eLevel;
	}
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	public Long getStatus() {
		return status;
	}
	public void setStatus(Long status) {
		this.status = status;
	}
	public String geteNameLike() {
		return eNameLike;
	}
	public void seteNameLike(String eNameLike) {
		this.eNameLike = eNameLike;
	}
	public String geteName() {
		return eName;
	}
	public void seteName(String eName) {
		this.eName = eName;
	}

}
