package com.kingteller.client.bean.logisticmonitor;

import java.io.Serializable;

public class HwDataBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String hwId;
	private String psdz;
	private String jqbm;
	private String qsdlsh;
	private String sqxz;
	private String jqhwId;
	private String name;
    private String sl;
    private String sjpsrq;
    
	public String getHwId() {
		return hwId;
	}
	public void setHwId(String hwId) {
		this.hwId = hwId;
	}
	public String getPsdz() {
		return psdz;
	}
	public void setPsdz(String psdz) {
		this.psdz = psdz;
	}
	public String getJqbm() {
		return jqbm;
	}
	public void setJqbm(String jqbm) {
		this.jqbm = jqbm;
	}
	public String getQsdlsh() {
		return qsdlsh;
	}
	public void setQsdlsh(String qsdlsh) {
		this.qsdlsh = qsdlsh;
	}
	public String getSqxz() {
		return sqxz;
	}
	public void setSqxz(String sqxz) {
		this.sqxz = sqxz;
	}
	public String getJqhwId() {
		return jqhwId;
	}
	public void setJqhwId(String jqhwId) {
		this.jqhwId = jqhwId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSl() {
		return sl;
	}
	public void setSl(String sl) {
		this.sl = sl;
	}
	public String getSjpsrq() {
		return sjpsrq;
	}
	public void setSjpsrq(String sjpsrq) {
		this.sjpsrq = sjpsrq;
	}
}
