package com.kingteller.client.bean.logisticmonitor;

import java.io.Serializable;
import java.util.Date;

public class LogisticTaskBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id; // 主键
	private String idLike;
	private String rwdh; // 任务单号
	private String rwdhLike;
	private String cydw; // 承运单位 1：公司物流 2：合作商物流
	private String cydwLike;
	private String cl; // 车辆
	private String clLike;
	private String sj; // 司机
	private String sjLike;
	private String gcy1; // 跟车员1
	private String gcy1Like;
	private String gcy2; // 跟车员2
	private String gcy2Like;
	private String cysl; // 承运数量
	private String cyslLike;
	private String sf; // 省份
	private String sfLike;
	private String cs; // 城市
	private String csLike;
	private String zysx; // 注意事项
	private String zysxLike;
	private String rwdzt; // 任务单状态
	private String rwdztLike;
	private String qcqlcs; // 起程前里程数
	private String qcqlcsLike;
	private String fhckhlcs; // 返回车库后里程数
	private String fhckhlcsLike;
	private String rwlc; // 任务里程
	private String rwlcLike;
	private String bz; // 备注
	private String bzLike;
	private String shpj; // 审核评价1：优 2：良 3：合格 4：差
	private String shpjLike;
	private String shbz; // 审核备注
	private String shbzLike;
	private String shr; // 审核人
	private String shrLike;
	private String shjg; // 审核结果 1：通过 2：退回
	private String shjgLike;
	private String cjz; // 创建者
	private String cjzLike;
/*	private Date cjsj; // 创建时间
	private Date cjsjRange1;
	private Date cjsjRange2;*/
	private String cjsjStr;
	private String expand1; // 任务类型  数字
	private String expand1Like;//  任务类型   汉字
	private String expand2; // 扩展2
	private String expand2Like;
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
	public String getRwdh() {
		return rwdh;
	}
	public void setRwdh(String rwdh) {
		this.rwdh = rwdh;
	}
	public String getRwdhLike() {
		return rwdhLike;
	}
	public void setRwdhLike(String rwdhLike) {
		this.rwdhLike = rwdhLike;
	}
	public String getCydw() {
		return cydw;
	}
	public void setCydw(String cydw) {
		this.cydw = cydw;
	}
	public String getCydwLike() {
		return cydwLike;
	}
	public void setCydwLike(String cydwLike) {
		this.cydwLike = cydwLike;
	}
	public String getCl() {
		return cl;
	}
	public void setCl(String cl) {
		this.cl = cl;
	}
	public String getClLike() {
		return clLike;
	}
	public void setClLike(String clLike) {
		this.clLike = clLike;
	}
	public String getSj() {
		return sj;
	}
	public void setSj(String sj) {
		this.sj = sj;
	}
	public String getSjLike() {
		return sjLike;
	}
	public void setSjLike(String sjLike) {
		this.sjLike = sjLike;
	}
	public String getGcy1() {
		return gcy1;
	}
	public void setGcy1(String gcy1) {
		this.gcy1 = gcy1;
	}
	public String getGcy1Like() {
		return gcy1Like;
	}
	public void setGcy1Like(String gcy1Like) {
		this.gcy1Like = gcy1Like;
	}
	public String getGcy2() {
		return gcy2;
	}
	public void setGcy2(String gcy2) {
		this.gcy2 = gcy2;
	}
	public String getGcy2Like() {
		return gcy2Like;
	}
	public void setGcy2Like(String gcy2Like) {
		this.gcy2Like = gcy2Like;
	}
	public String getCysl() {
		return cysl;
	}
	public void setCysl(String cysl) {
		this.cysl = cysl;
	}
	public String getCyslLike() {
		return cyslLike;
	}
	public void setCyslLike(String cyslLike) {
		this.cyslLike = cyslLike;
	}
	public String getSf() {
		return sf;
	}
	public void setSf(String sf) {
		this.sf = sf;
	}
	public String getSfLike() {
		return sfLike;
	}
	public void setSfLike(String sfLike) {
		this.sfLike = sfLike;
	}
	public String getCs() {
		return cs;
	}
	public void setCs(String cs) {
		this.cs = cs;
	}
	public String getCsLike() {
		return csLike;
	}
	public void setCsLike(String csLike) {
		this.csLike = csLike;
	}
	public String getZysx() {
		return zysx;
	}
	public void setZysx(String zysx) {
		this.zysx = zysx;
	}
	public String getZysxLike() {
		return zysxLike;
	}
	public void setZysxLike(String zysxLike) {
		this.zysxLike = zysxLike;
	}
	public String getRwdzt() {
		return rwdzt;
	}
	public void setRwdzt(String rwdzt) {
		this.rwdzt = rwdzt;
	}
	public String getRwdztLike() {
		return rwdztLike;
	}
	public void setRwdztLike(String rwdztLike) {
		this.rwdztLike = rwdztLike;
	}
	public String getQcqlcs() {
		return qcqlcs;
	}
	public void setQcqlcs(String qcqlcs) {
		this.qcqlcs = qcqlcs;
	}
	public String getQcqlcsLike() {
		return qcqlcsLike;
	}
	public void setQcqlcsLike(String qcqlcsLike) {
		this.qcqlcsLike = qcqlcsLike;
	}
	public String getFhckhlcs() {
		return fhckhlcs;
	}
	public void setFhckhlcs(String fhckhlcs) {
		this.fhckhlcs = fhckhlcs;
	}
	public String getFhckhlcsLike() {
		return fhckhlcsLike;
	}
	public void setFhckhlcsLike(String fhckhlcsLike) {
		this.fhckhlcsLike = fhckhlcsLike;
	}
	public String getRwlc() {
		return rwlc;
	}
	public void setRwlc(String rwlc) {
		this.rwlc = rwlc;
	}
	public String getRwlcLike() {
		return rwlcLike;
	}
	public void setRwlcLike(String rwlcLike) {
		this.rwlcLike = rwlcLike;
	}
	public String getBz() {
		return bz;
	}
	public void setBz(String bz) {
		this.bz = bz;
	}
	public String getBzLike() {
		return bzLike;
	}
	public void setBzLike(String bzLike) {
		this.bzLike = bzLike;
	}
	public String getShpj() {
		return shpj;
	}
	public void setShpj(String shpj) {
		this.shpj = shpj;
	}
	public String getShpjLike() {
		return shpjLike;
	}
	public void setShpjLike(String shpjLike) {
		this.shpjLike = shpjLike;
	}
	public String getShbz() {
		return shbz;
	}
	public void setShbz(String shbz) {
		this.shbz = shbz;
	}
	public String getShbzLike() {
		return shbzLike;
	}
	public void setShbzLike(String shbzLike) {
		this.shbzLike = shbzLike;
	}
	public String getShr() {
		return shr;
	}
	public void setShr(String shr) {
		this.shr = shr;
	}
	public String getShrLike() {
		return shrLike;
	}
	public void setShrLike(String shrLike) {
		this.shrLike = shrLike;
	}
	public String getShjg() {
		return shjg;
	}
	public void setShjg(String shjg) {
		this.shjg = shjg;
	}
	public String getShjgLike() {
		return shjgLike;
	}
	public void setShjgLike(String shjgLike) {
		this.shjgLike = shjgLike;
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
	
	

}
