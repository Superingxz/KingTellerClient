package com.kingteller.client.bean.logisticmonitor;

import java.io.Serializable;
import java.util.List;

public class LogisticConsignMobileBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
/*	private String tydId;   //托运单Id
	private String tydh;    //托运单号
	private String psxxdz;   //配送地址
	private String psxxlxr;   //配送联系人
	private String psdzlxdh;  //配送人电话
	private String zydw;     //转运单位
	private String zylxr;     //转运联系人
	private String zylxdh;    //转运联系人电话
	private String zydwdz;    //转运单位地址
*/	
	private LogisticConsignBean consign;
	private List<LogisticObjectBean> hwList;   //货物名称和数量list
	

	public LogisticConsignBean getConsign() {
		return consign;
	}
	public void setConsign(LogisticConsignBean consign) {
		this.consign = consign;
	}
	public List<LogisticObjectBean> getHwList() {
		return hwList;
	}
	public void setHwList(List<LogisticObjectBean> hwList) {
		this.hwList = hwList;
	}
	
	
	

}
