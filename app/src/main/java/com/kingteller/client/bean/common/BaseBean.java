package com.kingteller.client.bean.common;

import java.io.Serializable;

/**
 * @Description BaseBean所有处理对象的基础类
 * @author 王定波
 */
public class BaseBean implements Serializable {

	private static final long serialVersionUID = 6288961714085811766L;
	private String status;//错误代码
	private String msg;//错误信息

	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	

}
