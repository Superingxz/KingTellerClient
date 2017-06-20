package com.kingteller.client.bean.logisticmonitor;

import java.io.Serializable;

public class LogisticConsignBean implements Serializable{

	/**
	 * 			
	 */
	private static final long serialVersionUID = 1L;
	private String id; // 主键
	private String idLike;
	private String rwdid; // 任务单ID
	private String rwdidLike;
	private String tydh; // 托运单号
	private String tydhLike;
	private String tydzt; // 托运单状态
	private String tydztLike;
	private String jbxxid; // 基本信息ID
	private String jbxxidLike;
	private String sfd; // 始发地
	private String sfdLike;
	private String jqbm; // 机器编码
	private String jqbmLike;
	private String psxxdz; // 配送详细地址
	private String psxxdzLike;
	private String psdzlxr; // 配送地址联系人
	private String psdzlxrLike;
	private String psdzlxdh; // 配送地址联系电话
	private String psdzlxdhLike;
	private String hwsl; // 货物数量
	private String hwslLike;
	private String dcdz; // 调出地址
	private String dcdzLike;
	private String dcdzlxr; // 调出地址联系人
	private String dcdzlxrLike;
	private String dcdzlxdh; // 调出地址联系电话
	private String dcdzlxdhLike;
	private String drdz; // 调入地址
	private String drdzLike;
	private String drdzlxr; // 调入地址联系人
	private String drdzlxrLike;
	private String drdzlxdh; // 调入地址联系电话
	private String drdzlxdhLike;
	private String sjpsdz; // 实际配送地址
	private String sjpsdzLike;
	private String sqxxz; // 市区/县乡镇
	private String sqxxzLike;
	private String sjpsjq; // 实际配送机器编码
	private String sjpsjqLike;
	private String qsdlsh; // 签收单流水号
	private String qsdlshLike;
	private String zydw; // 转运单位
	private String zydwLike;
	private String zylxr; // 转运联系人
	private String zylxrLike;
	private String zylxdh; // 转运联系电话
	private String zylxdhLike;
	private String zydwdz; // 转运单位地址
	private String zydwdzLike;
	private String zydd; // 转运地点
	private String zyddLike;
	private String cjz; // 创建者
	private String cjzLike;
	/*private Date cjsj; // 创建时间
	private Date cjsjRange1;
	private Date cjsjRange2;*/
	private String cjsjStr;
	private String expand1; // 扩展1、托运单类型 1：公司物流 2：合作商物流 3：二次转运
	private String expand1Like;
	private String expand2; // 扩展2、包装单备注
	private String expand2Like;
	private String pdz; // 派单者
	private String pdzLike;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getIdLike() {
		return idLike;
	}

	public void setIdLike(String idLike) {
		this.idLike = idLike;
	}

	public String getRwdid() {
		return rwdid;
	}

	public void setRwdid(String rwdid) {
		this.rwdid = rwdid;
	}

	public String getRwdidLike() {
		return rwdidLike;
	}

	public void setRwdidLike(String rwdidLike) {
		this.rwdidLike = rwdidLike;
	}

	public String getTydh() {
		return tydh;
	}

	public void setTydh(String tydh) {
		this.tydh = tydh;
	}

	public String getTydhLike() {
		return tydhLike;
	}

	public void setTydhLike(String tydhLike) {
		this.tydhLike = tydhLike;
	}

	public String getJbxxid() {
		return jbxxid;
	}

	public void setJbxxid(String jbxxid) {
		this.jbxxid = jbxxid;
	}

	public String getJbxxidLike() {
		return jbxxidLike;
	}

	public void setJbxxidLike(String jbxxidLike) {
		this.jbxxidLike = jbxxidLike;
	}

	public String getSfd() {
		return sfd;
	}

	public void setSfd(String sfd) {
		this.sfd = sfd;
	}

	public String getSfdLike() {
		return sfdLike;
	}

	public void setSfdLike(String sfdLike) {
		this.sfdLike = sfdLike;
	}

	public String getJqbm() {
		return jqbm;
	}

	public void setJqbm(String jqbm) {
		this.jqbm = jqbm;
	}

	public String getJqbmLike() {
		return jqbmLike;
	}

	public void setJqbmLike(String jqbmLike) {
		this.jqbmLike = jqbmLike;
	}

	public String getPsxxdz() {
		return psxxdz;
	}

	public void setPsxxdz(String psxxdz) {
		this.psxxdz = psxxdz;
	}

	public String getPsxxdzLike() {
		return psxxdzLike;
	}

	public void setPsxxdzLike(String psxxdzLike) {
		this.psxxdzLike = psxxdzLike;
	}

	public String getPsdzlxr() {
		return psdzlxr;
	}

	public void setPsdzlxr(String psdzlxr) {
		this.psdzlxr = psdzlxr;
	}

	public String getPsdzlxrLike() {
		return psdzlxrLike;
	}

	public void setPsdzlxrLike(String psdzlxrLike) {
		this.psdzlxrLike = psdzlxrLike;
	}

	public String getPsdzlxdh() {
		return psdzlxdh;
	}

	public void setPsdzlxdh(String psdzlxdh) {
		this.psdzlxdh = psdzlxdh;
	}

	public String getPsdzlxdhLike() {
		return psdzlxdhLike;
	}

	public void setPsdzlxdhLike(String psdzlxdhLike) {
		this.psdzlxdhLike = psdzlxdhLike;
	}

	public String getHwsl() {
		return hwsl;
	}

	public void setHwsl(String hwsl) {
		this.hwsl = hwsl;
	}

	public String getHwslLike() {
		return hwslLike;
	}

	public void setHwslLike(String hwslLike) {
		this.hwslLike = hwslLike;
	}

	public String getDcdz() {
		return dcdz;
	}

	public void setDcdz(String dcdz) {
		this.dcdz = dcdz;
	}

	public String getDcdzLike() {
		return dcdzLike;
	}

	public void setDcdzLike(String dcdzLike) {
		this.dcdzLike = dcdzLike;
	}

	public String getDcdzlxr() {
		return dcdzlxr;
	}

	public void setDcdzlxr(String dcdzlxr) {
		this.dcdzlxr = dcdzlxr;
	}

	public String getDcdzlxrLike() {
		return dcdzlxrLike;
	}

	public void setDcdzlxrLike(String dcdzlxrLike) {
		this.dcdzlxrLike = dcdzlxrLike;
	}

	public String getDcdzlxdh() {
		return dcdzlxdh;
	}

	public void setDcdzlxdh(String dcdzlxdh) {
		this.dcdzlxdh = dcdzlxdh;
	}

	public String getDcdzlxdhLike() {
		return dcdzlxdhLike;
	}

	public void setDcdzlxdhLike(String dcdzlxdhLike) {
		this.dcdzlxdhLike = dcdzlxdhLike;
	}

	public String getDrdz() {
		return drdz;
	}

	public void setDrdz(String drdz) {
		this.drdz = drdz;
	}

	public String getDrdzLike() {
		return drdzLike;
	}

	public void setDrdzLike(String drdzLike) {
		this.drdzLike = drdzLike;
	}

	public String getDrdzlxr() {
		return drdzlxr;
	}

	public void setDrdzlxr(String drdzlxr) {
		this.drdzlxr = drdzlxr;
	}

	public String getDrdzlxrLike() {
		return drdzlxrLike;
	}

	public void setDrdzlxrLike(String drdzlxrLike) {
		this.drdzlxrLike = drdzlxrLike;
	}

	public String getDrdzlxdh() {
		return drdzlxdh;
	}

	public void setDrdzlxdh(String drdzlxdh) {
		this.drdzlxdh = drdzlxdh;
	}

	public String getDrdzlxdhLike() {
		return drdzlxdhLike;
	}

	public void setDrdzlxdhLike(String drdzlxdhLike) {
		this.drdzlxdhLike = drdzlxdhLike;
	}

	public String getSjpsdz() {
		return sjpsdz;
	}

	public void setSjpsdz(String sjpsdz) {
		this.sjpsdz = sjpsdz;
	}

	public String getSjpsdzLike() {
		return sjpsdzLike;
	}

	public void setSjpsdzLike(String sjpsdzLike) {
		this.sjpsdzLike = sjpsdzLike;
	}

	public String getSqxxz() {
		return sqxxz;
	}

	public void setSqxxz(String sqxxz) {
		this.sqxxz = sqxxz;
	}

	public String getSqxxzLike() {
		return sqxxzLike;
	}

	public void setSqxxzLike(String sqxxzLike) {
		this.sqxxzLike = sqxxzLike;
	}

	public String getSjpsjq() {
		return sjpsjq;
	}

	public void setSjpsjq(String sjpsjq) {
		this.sjpsjq = sjpsjq;
	}

	public String getSjpsjqLike() {
		return sjpsjqLike;
	}

	public void setSjpsjqLike(String sjpsjqLike) {
		this.sjpsjqLike = sjpsjqLike;
	}

	public String getQsdlsh() {
		return qsdlsh;
	}

	public void setQsdlsh(String qsdlsh) {
		this.qsdlsh = qsdlsh;
	}

	public String getQsdlshLike() {
		return qsdlshLike;
	}

	public void setQsdlshLike(String qsdlshLike) {
		this.qsdlshLike = qsdlshLike;
	}

	public String getZydw() {
		return zydw;
	}

	public void setZydw(String zydw) {
		this.zydw = zydw;
	}

	public String getZydwLike() {
		return zydwLike;
	}

	public void setZydwLike(String zydwLike) {
		this.zydwLike = zydwLike;
	}

	public String getZylxr() {
		return zylxr;
	}

	public void setZylxr(String zylxr) {
		this.zylxr = zylxr;
	}

	public String getZylxrLike() {
		return zylxrLike;
	}

	public void setZylxrLike(String zylxrLike) {
		this.zylxrLike = zylxrLike;
	}

	public String getZylxdh() {
		return zylxdh;
	}

	public void setZylxdh(String zylxdh) {
		this.zylxdh = zylxdh;
	}

	public String getZylxdhLike() {
		return zylxdhLike;
	}

	public void setZylxdhLike(String zylxdhLike) {
		this.zylxdhLike = zylxdhLike;
	}

	public String getZydwdz() {
		return zydwdz;
	}

	public void setZydwdz(String zydwdz) {
		this.zydwdz = zydwdz;
	}

	public String getZydwdzLike() {
		return zydwdzLike;
	}

	public void setZydwdzLike(String zydwdzLike) {
		this.zydwdzLike = zydwdzLike;
	}

	public String getZydd() {
		return zydd;
	}

	public void setZydd(String zydd) {
		this.zydd = zydd;
	}

	public String getZyddLike() {
		return zyddLike;
	}

	public void setZyddLike(String zyddLike) {
		this.zyddLike = zyddLike;
	}

	public String getTydzt() {
		return tydzt;
	}

	public void setTydzt(String tydzt) {
		this.tydzt = tydzt;
	}

	public String getTydztLike() {
		return tydztLike;
	}

	public void setTydztLike(String tydztLike) {
		this.tydztLike = tydztLike;
	}

	public String getCjz() {
		return cjz;
	}

	public void setCjz(String cjz) {
		this.cjz = cjz;
	}

	public String getCjzLike() {
		return cjzLike;
	}

	public void setCjzLike(String cjzLike) {
		this.cjzLike = cjzLike;
	}

	public String getCjsjStr() {
		return cjsjStr;
	}

	public void setCjsjStr(String cjsjStr) {
		this.cjsjStr = cjsjStr;
	}

	public String getExpand1() {
		return expand1;
	}

	public void setExpand1(String expand1) {
		this.expand1 = expand1;
	}

	public String getExpand1Like() {
		return expand1Like;
	}

	public void setExpand1Like(String expand1Like) {
		this.expand1Like = expand1Like;
	}

	public String getExpand2() {
		return expand2;
	}

	public void setExpand2(String expand2) {
		this.expand2 = expand2;
	}

	public String getExpand2Like() {
		return expand2Like;
	}

	public void setExpand2Like(String expand2Like) {
		this.expand2Like = expand2Like;
	}

	public String getPdz() {
		return pdz;
	}

	public void setPdz(String pdz) {
		this.pdz = pdz;
	}

	public String getPdzLike() {
		return pdzLike;
	}

	public void setPdzLike(String pdzLike) {
		this.pdzLike = pdzLike;
	}

}
