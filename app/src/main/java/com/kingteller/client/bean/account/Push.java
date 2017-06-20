package com.kingteller.client.bean.account;

import com.kingteller.framework.annotation.sqlite.Id;
import com.kingteller.framework.annotation.sqlite.Table;

@Table(name = "jpush")
public class Push {
	@Id(column = "id")
	private String id;
	private String sender;
	private String msg;
	private String send_time;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getSend_time() {
		return send_time;
	}

	public void setSend_time(String send_time) {
		this.send_time = send_time;
	}



}
