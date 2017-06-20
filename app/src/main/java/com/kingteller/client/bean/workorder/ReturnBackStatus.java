package com.kingteller.client.bean.workorder;

import java.io.Serializable;

public class ReturnBackStatus implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String result;
	private String message;
	//private String status;
	
/*	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}*/
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
}
