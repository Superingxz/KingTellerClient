package com.kingteller.client.bean.logisticmonitor;

import java.io.Serializable;

public class FareDetailParam implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String fylx;      // 费用类型
	private String fylxLike;
	private String je;        // 金额

	public String getFylx() {
		return fylx;
	}
	public void setFylx(String fylx) {
		this.fylx = fylx;
	}

	public String getFylxLike() {
		return fylxLike;
	}
	public void setFylxLike(String fylxLike) {
		this.fylxLike = fylxLike;
	}
	public String getJe() {
		return je;
	}
	public void setJe(String je) {
		this.je = je;
	}

}
