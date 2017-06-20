package com.kingteller.client.bean.common;

import java.io.Serializable;

/**
 * 树形数据
 * 
 * @author 王定波
 * 
 */
public class TreeBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6029581169675956199L;
	private String id;
	private String pid;
	private String title;
	private boolean isLast;
	

	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public boolean isLast() {
		return isLast;
	}
	public void setLast(boolean isLast) {
		this.isLast = isLast;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	
	public CommonSelectData getCommonSelectData()
	{
		CommonSelectData cdata=new CommonSelectData();
		cdata.setText(getTitle());
		cdata.setValue(getId());
		return cdata;
	}
}
