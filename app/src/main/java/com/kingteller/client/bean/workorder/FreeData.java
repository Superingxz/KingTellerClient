package com.kingteller.client.bean.workorder;

/**
 * 费用对应的数据类型
 * @author 王定波
 *
 */
public class FreeData {
	private String id;
	private String feeType;
	private String feeTypeId;
	private String userName;
	private String userId;
	private String feeMode;
	private String feeModeId;
	private String feeMoney;
	
	private String qzdd;
	private String jsdd;
	
	private String busLine;
	private String isGoBack;//是否回程
	
	
	public String getQzdd() {
		return qzdd;
	}
	public void setQzdd(String qzdd) {
		this.qzdd = qzdd;
	}
	public String getJsdd() {
		return jsdd;
	}
	public void setJsdd(String jsdd) {
		this.jsdd = jsdd;
	}
	public String getFeeType() {
		return feeType;
	}
	public void setFeeType(String feeType) {
		this.feeType = feeType;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getFeeMode() {
		return feeMode;
	}
	public void setFeeMode(String feeMode) {
		this.feeMode = feeMode;
	}
	public String getFeeMoney() {
		return feeMoney;
	}
	public void setFeeMoney(String feeMoney) {
		this.feeMoney = feeMoney;
	}
	public String getBusLine() {
		return busLine;
	}
	public void setBusLine(String busLine) {
		this.busLine = busLine;
	}
	public String getFeeTypeId() {
		return feeTypeId;
	}
	public void setFeeTypeId(String feeTypeId) {
		this.feeTypeId = feeTypeId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getFeeModeId() {
		return feeModeId;
	}
	public void setFeeModeId(String feeModeId) {
		this.feeModeId = feeModeId;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getIsGoBack() {
		return isGoBack;
	}
	public void setIsGoBack(String isGoBack) {
		this.isGoBack = isGoBack;
	}
	
}
