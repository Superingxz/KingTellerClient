package com.kingteller.client.bean.account;

import java.io.Serializable;

/**
 * 登录接口返回信息
 * @author 王定波
 *
 */
public class LoginBean implements Serializable {

	private static final long serialVersionUID = 6288961714085811766L;
	
	private int loginError;
	
	private String loginState;
	
	private String name;
	private String orgId;
	private String orgName;
	private String right;
	private String roleCode;
	private String userId;
	
	private String username;
	private String password;
	
	private String versionName;
	
	private String wrId;
	private String wrName;

	public String getLoginState() {
		return loginState;
	}
	public void setLoginState(String loginState) {
		this.loginState = loginState;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getOrgId() {
		return orgId;
	}
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	public String getRight() {
		return right;
	}
	public void setRight(String right) {
		this.right = right;
	}
	public String getRoleCode() {
		return roleCode;
	}
	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public int getLoginError() {
		return loginError;
	}
	public void setLoginError(int loginError) {
		this.loginError = loginError;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getVersionName() {
		return versionName;
	}
	public void setVersionName(String versionName) {
		this.versionName = versionName;
	}
	public String getWrId() {
		return wrId;
	}
	public void setWrId(String wrId) {
		this.wrId = wrId;
	}
	public String getWrName() {
		return wrName;
	}
	public void setWrName(String wrName) {
		this.wrName = wrName;
	}
	
}
