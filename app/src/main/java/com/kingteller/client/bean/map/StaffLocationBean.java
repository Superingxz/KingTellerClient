package com.kingteller.client.bean.map;

import java.io.Serializable;
import java.util.List;

public class StaffLocationBean implements Serializable  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7187147127296940720L;
	private String dTotal;
	private int totalPage;
	private String userId;
	private String userName;
	private List<StaffPointBean> uMaplist;
	
	public String getdTotal() {
		return dTotal;
	}
	public void setdTotal(String dTotal) {
		this.dTotal = dTotal;
	}

	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public List<StaffPointBean> getuMaplist() {
		return uMaplist;
	}
	public void setuMaplist(List<StaffPointBean> uMaplist) {
		this.uMaplist = uMaplist;
	}
	public int getTotalPage() {
		return totalPage;
	}
	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

}
