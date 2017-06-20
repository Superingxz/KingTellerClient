package com.kingteller.client.bean.common;

import java.io.Serializable;

/**
 * 通用的Select选择数据
 * @author 王定波
 *
 */
public class CommonSelectGZBJ implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5183852249343815258L;
	private String value;
	private String text;
	private Object obj;
	private String pathname;
	private String type_id;
	
	public String getType_id() {
		return type_id;
	}
	public void setType_id(String type_id) {
		this.type_id = type_id;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	
	public String getPathname() {
		return pathname;
	}
	public void setPathname(String pathname) {
		this.pathname = pathname;
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		if(obj==null)
		return getText();
		else return obj.toString();
	}
	public Object getObj() {
		return obj;
	}
	public void setObj(Object obj) {
		this.obj = obj;
	}
}
