package com.kingteller.client.bean.attendance;

import java.io.Serializable;
import java.util.List;

public class OADictListBean implements Serializable{

	private static final long serialVersionUID = 1199842898361586440L;

	private List<OADictBean> dictList;
	private String code;
	
	
	public List<OADictBean> getDictList() {
		return dictList;
	}
	public void setDictList(List<OADictBean> dictList) {
		this.dictList = dictList;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}

}
