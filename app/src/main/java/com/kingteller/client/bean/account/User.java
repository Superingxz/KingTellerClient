package com.kingteller.client.bean.account;

import com.kingteller.client.KingTellerApplication;
import com.kingteller.client.config.KingTellerStaticConfig;
import com.kingteller.client.utils.KingTellerJudgeUtils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 * 
 * @author 用户信息
 * 
 */
public class User {
	private String name;//姓名
	private String orgId;//服务站id
	private String orgName;//服务站名
	private String right;//用户权限
	private String roleCode;//用户角色
	private String userId;//用户id
	private String userName;//用户名
	private String password;//密码
	private boolean push_reg;//推送状态
	private String versionName;//版本号
	private String wrId;//仓库id
	private String wrName;//仓库名
	private String dbDate_reg;//数据库 数据更新状态

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

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
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

	public boolean isPushReg() {
		return push_reg;
	}

	public void setPushReg(boolean push_reg) {
		this.push_reg = push_reg;
	}
	
	
	public String getDbDateReg() {
		return dbDate_reg;
	}

	public void setDbDateReg(String dbDate_reg) {
		this.dbDate_reg = dbDate_reg;
	}

	/**
	 * 保存用户数据
	 * 
	 * @param context
	 * @param data
	 * @param username
	 *            用户名可为NUll
	 * @param password
	 *            密码可为NULL
	 */
	public static void SaveInfo(Context context, LoginBean data) {
		Editor sharedata = context.getSharedPreferences(
				KingTellerStaticConfig.SHARED_PREFERENCES.USER, Context.MODE_APPEND)
				.edit();
		if (!KingTellerJudgeUtils.isEmpty(data.getUsername()))
			sharedata.putString("username", data.getUsername());
		if (!KingTellerJudgeUtils.isEmpty(data.getPassword()))
			sharedata.putString("password", data.getPassword());

		sharedata.putString("name", data.getName());
		sharedata.putString("orgId", data.getOrgId());
		sharedata.putString("orgName", data.getOrgName());
		sharedata.putString("right", data.getRight());
		sharedata.putString("roleCode", data.getRoleCode());
		sharedata.putString("userId", data.getUserId());
		sharedata.putString("versionName", data.getVersionName());
		sharedata.putString("wrId", data.getWrId());
		sharedata.putString("wrName", data.getWrName());
		
		sharedata.commit();
	}

	/**
	 * 保存用户数据
	 * 
	 * @param context
	 * @param data
	 */
	public static void SaveInfo(Context context, User data) {
		Editor sharedata = context.getSharedPreferences(
				KingTellerStaticConfig.SHARED_PREFERENCES.USER, Context.MODE_APPEND)
				.edit();
		if (!KingTellerJudgeUtils.isEmpty(data.getUserName()))
			sharedata.putString("username", data.getUserName());
		if (!KingTellerJudgeUtils.isEmpty(data.getPassword()))
			sharedata.putString("password", data.getPassword());

		sharedata.putString("name", data.getName());
		sharedata.putString("orgId", data.getOrgId());
		sharedata.putString("orgName", data.getOrgName());
		sharedata.putString("right", data.getRight());
		sharedata.putString("roleCode", data.getRoleCode());
		sharedata.putString("userId", data.getUserId());
		sharedata.putString("versionName", data.getVersionName());
		sharedata.putString("wrId", data.getWrId());
		sharedata.putString("wrName", data.getWrName());
		sharedata.commit();
	}

	/**
	 * 获取用户信息
	 * @param context
	 * @return
	 */
	public static User getInfo(Context context) {
		User data = new User();
		SharedPreferences sp = context.getSharedPreferences(
				KingTellerStaticConfig.SHARED_PREFERENCES.USER, Context.MODE_APPEND);

		data.setUserName(sp.getString("username", ""));
		data.setPassword(sp.getString("password", ""));
		data.setName(sp.getString("name", ""));
		data.setOrgId(sp.getString("orgId", ""));
		data.setOrgName(sp.getString("orgName", ""));
		data.setRight(sp.getString("right", ""));
		data.setRoleCode(sp.getString("roleCode", ""));
		data.setUserId(sp.getString("userId", ""));
		data.setPushReg(sp.getBoolean("push_reg", false));
		data.setVersionName(sp.getString("versionName", ""));
		data.setWrId(sp.getString("wrId", ""));
		data.setWrName(sp.getString("wrName", ""));
		return data;
	}
	
	//存储 仓库id/仓库名称
	public static void saveWr(Context context, String wrId, String wrName) {
		Editor sharedata = context.getSharedPreferences(
				KingTellerStaticConfig.SHARED_PREFERENCES.USER, Context.MODE_APPEND)
				.edit();

		sharedata.putString("wrId", wrId);
		sharedata.putString("wrName", wrName);
		sharedata.commit();
	}

	//存储 推送状态
	public static void savePushReg(Context context, boolean pushreg) {
		Editor sharedata = context.getSharedPreferences(
				KingTellerStaticConfig.SHARED_PREFERENCES.USER, Context.MODE_APPEND)
				.edit();

		sharedata.putBoolean("push_reg", pushreg);

		sharedata.commit();
	}

	// 得到极光别名
	public String getAlias() {
		return getUserName() + KingTellerApplication.getDeviceToken();
	}

}
