package com.kingteller.client.bean.map;

import java.io.Serializable;
import java.util.List;

public class AtmGroupBean implements Serializable {

    private String id;//缓存id
    private String type;//缓存type  atmgroupuppic
	private String jqbh;//机器编号
	private List<String> piclist;//图片list
    private String str;//图片水印
	private int status;//检索状态

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getJqbh() {
		return jqbh;
	}

	public void setJqbh(String jqbh) {
		this.jqbh = jqbh;
	}

	public List<String> getPiclist() {
		return piclist;
	}

	public void setPiclist(List<String> piclist) {
		this.piclist = piclist;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStr() {
        return str;
    }

    public void setStr(String str) {
        this.str = str;
    }

    public AtmGroupBean(){}

    public AtmGroupBean( String id, String type, String jqbh, List<String> piclist, String str) {
        super();
        this.id = id;
        this.type = type;
        this.jqbh = jqbh;
        this.piclist = piclist;
        this.str = str;
    }
}
