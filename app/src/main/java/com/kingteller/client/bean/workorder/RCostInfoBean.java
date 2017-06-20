package com.kingteller.client.bean.workorder;

import java.io.Serializable;

/**
 * 费用信息
 * 
 * @author jinyh
 */
public class RCostInfoBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private String id; // Id号
	private String idLike;
	private String reportId; // 维护报告Id
	private String reportIdLike;
	private long reportAccess; // 交通方式
	private String maintainpersonid; // 维护工程师Id
	private String maintainpersonidLike;
	private String maintainpersonname; // 维护工程师名称
	private String maintainpersonnameLike;
	private String trafficefee; // 交通费用
	private String arriveroute; // 乘车路线
	private String arriverouteLike;
	private String expand1; // 扩展字段1,已用费用类型id
	private String expand1Like;
	private String expand2; // 扩展字段2,已用费用方式id
	private String expand2Like;
	private String expand3; // 扩展字段3,已用费用类型名称
	private String expand3Like;
	private String expand4; // 扩展字段4
	private String expand4Like;
	private String expand5; // 扩展字段5
	private String expand5Like;
	private String accessname;
	private String accessnameLike;
	
	private String qzdd;                 // 起止地点:交通费
	private String jsdd;                 // 结束地点:交通费
	
	
	public String getQzdd() {
		return qzdd;
	}
	public void setQzdd(String qzdd) {
		this.qzdd = qzdd;
	}
	public String getJsdd() {
		return jsdd;
	}
	public void setJsdd(String jsdd) {
		this.jsdd = jsdd;
	}
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
	public String getReportId() {
		return reportId;
	}
	public void setReportId(String reportId) {
		this.reportId = reportId;
	}
	public String getReportIdLike() {
		return reportIdLike;
	}
	public void setReportIdLike(String reportIdLike) {
		this.reportIdLike = reportIdLike;
	}
	public long getReportAccess() {
		return reportAccess;
	}
	public void setReportAccess(long reportAccess) {
		this.reportAccess = reportAccess;
	}
	public String getMaintainpersonid() {
		return maintainpersonid;
	}
	public void setMaintainpersonid(String maintainpersonid) {
		this.maintainpersonid = maintainpersonid;
	}
	public String getAccessnameLike() {
		return accessnameLike;
	}
	public void setAccessnameLike(String accessnameLike) {
		this.accessnameLike = accessnameLike;
	}
	public String getTrafficefee() {
		return trafficefee;
	}
	public String getAccessname() {
		return accessname;
	}
	public void setAccessname(String accessname) {
		this.accessname = accessname;
	}
	public String getExpand5Like() {
		return expand5Like;
	}
	public void setExpand5Like(String expand5Like) {
		this.expand5Like = expand5Like;
	}
	public String getMaintainpersonidLike() {
		return maintainpersonidLike;
	}
	public void setMaintainpersonidLike(String maintainpersonidLike) {
		this.maintainpersonidLike = maintainpersonidLike;
	}
	public String getMaintainpersonname() {
		return maintainpersonname;
	}
	public void setMaintainpersonname(String maintainpersonname) {
		this.maintainpersonname = maintainpersonname;
	}
	public String getExpand5() {
		return expand5;
	}
	public void setExpand5(String expand5) {
		this.expand5 = expand5;
	}
	public String getExpand4Like() {
		return expand4Like;
	}
	public void setExpand4Like(String expand4Like) {
		this.expand4Like = expand4Like;
	}
	public String getExpand4() {
		return expand4;
	}
	public void setExpand4(String expand4) {
		this.expand4 = expand4;
	}
	public String getExpand3Like() {
		return expand3Like;
	}
	public void setExpand3Like(String expand3Like) {
		this.expand3Like = expand3Like;
	}
	public String getExpand3() {
		return expand3;
	}
	public void setExpand3(String expand3) {
		this.expand3 = expand3;
	}
	public String getExpand2Like() {
		return expand2Like;
	}
	public void setExpand2Like(String expand2Like) {
		this.expand2Like = expand2Like;
	}
	public String getExpand1Like() {
		return expand1Like;
	}
	public void setExpand1Like(String expand1Like) {
		this.expand1Like = expand1Like;
	}
	public String getMaintainpersonnameLike() {
		return maintainpersonnameLike;
	}
	public void setMaintainpersonnameLike(String maintainpersonnameLike) {
		this.maintainpersonnameLike = maintainpersonnameLike;
	}
	public void setTrafficefee(String trafficefee) {
		this.trafficefee = trafficefee;
	}
	public String getArriveroute() {
		return arriveroute;
	}
	public void setArriveroute(String arriveroute) {
		this.arriveroute = arriveroute;
	}
	public String getArriverouteLike() {
		return arriverouteLike;
	}
	public void setArriverouteLike(String arriverouteLike) {
		this.arriverouteLike = arriverouteLike;
	}
	public String getExpand1() {
		return expand1;
	}
	public void setExpand1(String expand1) {
		this.expand1 = expand1;
	}
	public String getExpand2() {
		return expand2;
	}
	public void setExpand2(String expand2) {
		this.expand2 = expand2;
	}

}
