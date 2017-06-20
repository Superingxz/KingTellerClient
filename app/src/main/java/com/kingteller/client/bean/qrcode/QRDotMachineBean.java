package com.kingteller.client.bean.qrcode;

import java.io.Serializable;

import com.kingteller.client.bean.onlinelearning.TreeNodeId;
import com.kingteller.client.bean.onlinelearning.TreeNodeLabel;
import com.kingteller.client.bean.onlinelearning.TreeNodePid;

public class QRDotMachineBean implements Serializable {

	private static final long serialVersionUID = 2649764193294069056L;
	
	@TreeNodeId
	private String genreId;//自Id
	@TreeNodePid
	private String genrePid;//父Id
	@TreeNodeLabel
	private String genreName;//显示字符串
	
	private String jqId;//机器id
	private String jqBm;//机器编码
	private String nodeLevel;//物料级别
	private String wlName;//物料名称
	private String newCode;//物料编号
	private String pidNewCode;//父  物料编号
	private String wlBarCode;//自二维码  物料条码编号

	private String isCurrent; //是否当前扫物料
	private String isNewadd; //是否新增 
	


	public String getIsNewadd() {
		return isNewadd;
	}

	public void setIsNewadd(String isNewadd) {
		this.isNewadd = isNewadd;
	}

	public String getIsCurrent() {
		return isCurrent;
	}

	public void setIsCurrent(String isCurrent) {
		this.isCurrent = isCurrent;
	}

	public String getPidNewCode() {
		return pidNewCode;
	}

	public void setPidNewCode(String pidNewCode) {
		this.pidNewCode = pidNewCode;
	}

	public String getJqId() {
		return jqId;
	}

	public void setJqId(String jqId) {
		this.jqId = jqId;
	}

	public String getJqBm() {
		return jqBm;
	}

	public void setJqBm(String jqBm) {
		this.jqBm = jqBm;
	}

	public String getNodeLevel() {
		return nodeLevel;
	}

	public void setNodeLevel(String nodeLevel) {
		this.nodeLevel = nodeLevel;
	}

	public String getWlName() {
		return wlName;
	}

	public void setWlName(String wlName) {
		this.wlName = wlName;
	}

	public String getNewCode() {
		return newCode;
	}

	public void setNewCode(String newCode) {
		this.newCode = newCode;
	}

	public String getWlBarCode() {
		return wlBarCode;
	}

	public void setWlBarCode(String wlBarCode) {
		this.wlBarCode = wlBarCode;
	}

	public String getGenreId() {
		return genreId;
	}

	public void setGenreId(String genreId) {
		this.genreId = genreId;
	}

	public String getGenrePid() {
		return genrePid;
	}

	public void setGenrePid(String genrePid) {
		this.genrePid = genrePid;
	}

	public String getGenreName() {
		return genreName;
	}

	public void setGenreName(String genreName) {
		this.genreName = genreName;
	}
	
	public QRDotMachineBean() {
	}
	
	public QRDotMachineBean(String wlBarCode) {
		super();
		this.wlBarCode = wlBarCode;
	}
	
	public QRDotMachineBean(String jqId, String jqBm) {
		super();
		this.jqId = jqId;
		this.jqBm = jqBm;
	}
	
	public QRDotMachineBean(String genreId, String genrePid, String genreName) {
		super();
		this.genreId = genreId;
		this.genrePid = genrePid;
		this.genreName = genreName;//"物料名称，物料编码，物料条码"

	}
	
	public QRDotMachineBean(String jqId, String jqBm, String genreId, String genrePid, 
			String nodeLevel, String wlName, String newCode, String pidNewCode, String wlBarCode, String isNewadd) {
		super();
		this.jqId = jqId;
		this.jqBm = jqBm;
		this.genreId = genreId;
		this.genrePid = genrePid;
		this.nodeLevel = nodeLevel;
		this.wlName = wlName;
		this.newCode = newCode;
		this.pidNewCode = pidNewCode;
		this.wlBarCode = wlBarCode;
		this.isNewadd = isNewadd;
	}
	
}
