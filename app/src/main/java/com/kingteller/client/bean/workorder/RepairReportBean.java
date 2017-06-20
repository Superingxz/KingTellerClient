package com.kingteller.client.bean.workorder;

import java.io.Serializable;
import java.util.List;

public class RepairReportBean implements Serializable {

	private static final long serialVersionUID = 8873632888126600124L;
	
	private String orderId; // 工单id(隐藏字段)
	private String orderNo; // 工单号
	private String skillServeNumber; // 技术服务单号
	private String reportId; // 维护报告id(隐藏字段)
	private String flowStatus; // 流程状态(隐藏字段)
	private String machineId; // 机器id(隐藏字段)
	private String backflag; // 退回次数(隐藏字段)
	private String machineStatus; // 机器状态(隐藏字段)
	private String sxrq; // 上线日期(隐藏字段)
	private String expand1; // 机器编号
	private String atmh; // ATM号
	private String jqgsrusername; // 机器归属人
	private String atmTypeCode; // 机型类别
	private String workOrderPrompt; // 派单工作提示
	private String wdlxr; // 网点联系人
	private String expand9; // 网点id(隐藏字段)
	private String atmTypeId; // 机型id(隐藏字段)
	private String wdlxdh; // 网点联系人电话
	private String orgYesOrNot; // 是否有服务站(隐藏字段)
	private String workOrgId; // 服务站id(隐藏字段)
	private String orgName; // 服务站名称
	private String wddz; // 网点地址
	private String wdsbmc; // 网点设备简称
	private String servType;//服务方式(隐藏字段)(SITE_SERV:现场服务)
	private String workTypeName; // 工作类别名称(隐藏字段)
	private String workTypeNewMove; // 是否新机开通(MOVE:移机开通,MOVE_OUT:移走机器,NEW:新机开通)
	private String workFinishFlag; // 机器是否正常服务(0:是,1:否,2:机器带故障运行，3:未上线停机,99:请选择)
	private String isbjyy; // 是否备件原因(1:是,2:否)
	private String arrangeType; // 是否预约(0:否,1:是)
	private String prearrangeDate; // 预约时间
	private String askTim; // 知会日期时间
	private String assignOrderTime; // 派单日期时间
	private String arriveTime; // 到达现场时间
	private String maintainBeginTime; // 维护开始时间
	private String troubleHappenTime; // 故障发生时间
	private String maintainEndTime; // 维护结束时间
	private String arriveOvertimeMin; // 响应超时分钟数
	private String workDate; // 工作时间
	private String arriveOverRemark; // 响应超时说明
	private String maintainOverRemark; // 维护超时说明
	private String atmcVerson; // 应用版本(ATMC)
	private String spVerson; // 机芯SP版本
	private String expand6; // 跨平台硬件驱动版本(KTWSP)
	private String expand7; // BV版本(仅循环机)
	private String flowStatusFlag; // 1 表示已经提交流程， 其他值表示该维护报告还未提交审核流程
	private String auditContent; // 审核意见
	private String auditRemark; // 审核备注
	private String siteRealityTrouble; // 现场实际故障
	private String troubleHandleCourse; // 故障处理详细过程
	private String troubleHandleResult; // 故障处理结果
	private String follow; // 处理情况跟进
	private String maintainpersonname; // 处理情况跟进
	private String involvesSpareParts;//是否涉及备件
	private String changebjflag;//更换备件标识 “yes”可编辑维护信息，“no”不可编辑维护信息
	private String maintainOvertimeMin;//维护超时分钟数
	private String serveAssessResultCode;
	private String porderno;//项目工单号
	private String xmflag;//项目工单标志
	private String ssyhId; // 所属银行id
	private String ssyhName;//所属银行名称
	private String sszhId; // 所属支行id
	private String sszhName;//所属支行名称
	private String yesnotj;			//是否退机
	private String tjdh;			//退机单号
	private String stopReason;
	private String pplb;//品牌类别
	
	private String servcomp; //服务商名称(隐藏字段)
	private String brandname; //品牌名称(隐藏字段)
	
	private List<BJBean> mfList;//备件类型
	private List<WorkTypeBean> rwList;//工作类别
	private List<WorkTypeBean> prwList;//项目工单工作类别
	private List<RCostInfoBean> epList;// 费用类型
	private List<AttachmentBean> apList;// 附件类型
	private List<AtmWlReplaceMiddleParam> nList;//二维码新物料的信息
	private List<AtmWlReplaceMiddleParam> pList;//二维码旧物料的信息
	
    private String result;
    private String message;
    
	public String getServcomp() {
		return servcomp;
	}
	public void setServcomp(String servcomp) {
		this.servcomp = servcomp;
	}
	public String getBrandname() {
		return brandname;
	}
	public void setBrandname(String brandname) {
		this.brandname = brandname;
	}
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
	public List<AtmWlReplaceMiddleParam> getnList() {
		return nList;
	}
	public void setnList(List<AtmWlReplaceMiddleParam> nList) {
		this.nList = nList;
	}
	public List<AtmWlReplaceMiddleParam> getpList() {
		return pList;
	}
	public void setpList(List<AtmWlReplaceMiddleParam> pList) {
		this.pList = pList;
	}
	public String getServType() {
		return servType;
	}
	public void setServType(String servType) {
		this.servType = servType;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public List<AttachmentBean> getApList() {
		return apList;
	}
	public void setApList(List<AttachmentBean> apList) {
		this.apList = apList;
	}

	public String getFollow() {
		return follow;
	}
	public void setFollow(String follow) {
		this.follow = follow;
	}
	public String getSkillServeNumber() {
		return skillServeNumber;
	}
	public void setSkillServeNumber(String skillServeNumber) {
		this.skillServeNumber = skillServeNumber;
	}
	public String getReportId() {
		return reportId;
	}
	public void setReportId(String reportId) {
		this.reportId = reportId;
	}
	public String getFlowStatus() {
		return flowStatus;
	}
	public void setFlowStatus(String flowStatus) {
		this.flowStatus = flowStatus;
	}
	public String getMachineId() {
		return machineId;
	}
	public void setMachineId(String machineId) {
		this.machineId = machineId;
	}
	public String getBackflag() {
		return backflag;
	}
	public void setBackflag(String backflag) {
		this.backflag = backflag;
	}
	public String getJqgsrusername() {
		return jqgsrusername;
	}
	public void setJqgsrusername(String jqgsrusername) {
		this.jqgsrusername = jqgsrusername;
	}
	public String getWorkOrderPrompt() {
		return workOrderPrompt;
	}
	public void setWorkOrderPrompt(String workOrderPrompt) {
		this.workOrderPrompt = workOrderPrompt;
	}
	public String getTroubleHandleResult() {
		return troubleHandleResult;
	}
	public void setTroubleHandleResult(String troubleHandleResult) {
		this.troubleHandleResult = troubleHandleResult;
	}
	public String getTroubleHandleCourse() {
		return troubleHandleCourse;
	}
	public void setTroubleHandleCourse(String troubleHandleCourse) {
		this.troubleHandleCourse = troubleHandleCourse;
	}
	public String getSiteRealityTrouble() {
		return siteRealityTrouble;
	}
	public void setSiteRealityTrouble(String siteRealityTrouble) {
		this.siteRealityTrouble = siteRealityTrouble;
	}
	public String getAuditRemark() {
		return auditRemark;
	}
	public void setAuditRemark(String auditRemark) {
		this.auditRemark = auditRemark;
	}
	public String getExpand6() {
		return expand6;
	}
	public void setExpand6(String expand6) {
		this.expand6 = expand6;
	}
	public String getArriveOverRemark() {
		return arriveOverRemark;
	}
	public void setArriveOverRemark(String arriveOverRemark) {
		this.arriveOverRemark = arriveOverRemark;
	}
	public String getWorkDate() {
		return workDate;
	}
	public void setWorkDate(String workDate) {
		this.workDate = workDate;
	}
	public String getTroubleHappenTime() {
		return troubleHappenTime;
	}
	public void setTroubleHappenTime(String troubleHappenTime) {
		this.troubleHappenTime = troubleHappenTime;
	}
	public String getAssignOrderTime() {
		return assignOrderTime;
	}
	public void setAssignOrderTime(String assignOrderTime) {
		this.assignOrderTime = assignOrderTime;
	}
	public String getOrgYesOrNot() {
		return orgYesOrNot;
	}
	public void setOrgYesOrNot(String orgYesOrNot) {
		this.orgYesOrNot = orgYesOrNot;
	}
	public String getAtmTypeCode() {
		return atmTypeCode;
	}
	public void setAtmTypeCode(String atmTypeCode) {
		this.atmTypeCode = atmTypeCode;
	}
	public String getMachineStatus() {
		return machineStatus;
	}
	public void setMachineStatus(String machineStatus) {
		this.machineStatus = machineStatus;
	}
	public String getSxrq() {
		return sxrq;
	}
	public void setSxrq(String sxrq) {
		this.sxrq = sxrq;
	}
	public String getAtmh() {
		return atmh;
	}
	public void setAtmh(String atmh) {
		this.atmh = atmh;
	}
	public String getAuditContent() {
		return auditContent;
	}
	public void setAuditContent(String auditContent) {
		this.auditContent = auditContent;
	}
	public String getFlowStatusFlag() {
		return flowStatusFlag;
	}
	public void setFlowStatusFlag(String flowStatusFlag) {
		this.flowStatusFlag = flowStatusFlag;
	}
	public String getExpand7() {
		return expand7;
	}
	public void setExpand7(String expand7) {
		this.expand7 = expand7;
	}
	public String getAtmcVerson() {
		return atmcVerson;
	}
	public void setAtmcVerson(String atmcVerson) {
		this.atmcVerson = atmcVerson;
	}
	public String getMaintainOverRemark() {
		return maintainOverRemark;
	}
	public void setMaintainOverRemark(String maintainOverRemark) {
		this.maintainOverRemark = maintainOverRemark;
	}
	public String getArriveOvertimeMin() {
		return arriveOvertimeMin;
	}
	public void setArriveOvertimeMin(String arriveOvertimeMin) {
		this.arriveOvertimeMin = arriveOvertimeMin;
	}
	public String getMaintainEndTime() {
		return maintainEndTime;
	}
	public void setMaintainEndTime(String maintainEndTime) {
		this.maintainEndTime = maintainEndTime;
	}
	public String getMaintainBeginTime() {
		return maintainBeginTime;
	}
	public void setMaintainBeginTime(String maintainBeginTime) {
		this.maintainBeginTime = maintainBeginTime;
	}
	public String getSpVerson() {
		return spVerson;
	}
	public void setSpVerson(String spVerson) {
		this.spVerson = spVerson;
	}
	public String getArriveTime() {
		return arriveTime;
	}
	public void setArriveTime(String arriveTime) {
		this.arriveTime = arriveTime;
	}
	public String getAskTim() {
		return askTim;
	}
	public void setAskTim(String askTim) {
		this.askTim = askTim;
	}
	public String getPrearrangeDate() {
		return prearrangeDate;
	}
	public void setPrearrangeDate(String prearrangeDate) {
		this.prearrangeDate = prearrangeDate;
	}
	public String getExpand1() {
		return expand1;
	}
	public void setExpand1(String expand1) {
		this.expand1 = expand1;
	}
	public String getWdlxr() {
		return wdlxr;
	}
	public void setWdlxr(String wdlxr) {
		this.wdlxr = wdlxr;
	}
	public String getExpand9() {
		return expand9;
	}
	public void setExpand9(String expand9) {
		this.expand9 = expand9;
	}
	public String getAtmTypeId() {
		return atmTypeId;
	}
	public void setAtmTypeId(String atmTypeId) {
		this.atmTypeId = atmTypeId;
	}
	public String getWdlxdh() {
		return wdlxdh;
	}
	public void setWdlxdh(String wdlxdh) {
		this.wdlxdh = wdlxdh;
	}
	public String getWorkOrgId() {
		return workOrgId;
	}
	public void setWorkOrgId(String workOrgId) {
		this.workOrgId = workOrgId;
	}
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	public String getWddz() {
		return wddz;
	}
	public void setWddz(String wddz) {
		this.wddz = wddz;
	}
	public String getWdsbmc() {
		return wdsbmc;
	}
	public void setWdsbmc(String wdsbmc) {
		this.wdsbmc = wdsbmc;
	}

	public String getWorkTypeName() {
		return workTypeName;
	}
	public void setWorkTypeName(String workTypeName) {
		this.workTypeName = workTypeName;
	}
	public String getWorkTypeNewMove() {
		return workTypeNewMove;
	}
	public void setWorkTypeNewMove(String workTypeNewMove) {
		this.workTypeNewMove = workTypeNewMove;
	}
	public String getWorkFinishFlag() {
		return workFinishFlag;
	}
	public void setWorkFinishFlag(String workFinishFlag) {
		this.workFinishFlag = workFinishFlag;
	}
	public String getIsbjyy() {
		return isbjyy;
	}
	public void setIsbjyy(String isbjyy) {
		this.isbjyy = isbjyy;
	}
	public String getArrangeType() {
		return arrangeType;
	}
	public void setArrangeType(String arrangeType) {
		this.arrangeType = arrangeType;
	}
	public String getMaintainpersonname() {
		return maintainpersonname;
	}
	public void setMaintainpersonname(String maintainpersonname) {
		this.maintainpersonname = maintainpersonname;
	}
	public List<RCostInfoBean> getEpList() {
		return epList;
	}
	public void setEpList(List<RCostInfoBean> epList) {
		this.epList = epList;
	}

	public String getInvolvesSpareParts() {
		return involvesSpareParts;
	}
	public void setInvolvesSpareParts(String involvesSpareParts) {
		this.involvesSpareParts = involvesSpareParts;
	}
	public String getMaintainOvertimeMin() {
		return maintainOvertimeMin;
	}
	public void setMaintainOvertimeMin(String maintainOvertimeMin) {
		this.maintainOvertimeMin = maintainOvertimeMin;
	}
	public String getServeAssessResultCode() {
		return serveAssessResultCode;
	}
	public void setServeAssessResultCode(String serveAssessResultCode) {
		this.serveAssessResultCode = serveAssessResultCode;
	}
	public List<WorkTypeBean> getRwList() {
		return rwList;
	}
	public void setRwList(List<WorkTypeBean> rwList) {
		this.rwList = rwList;
	}
	public List<WorkTypeBean> getPrwList() {
		return prwList;
	}
	public void setPrwList(List<WorkTypeBean> prwList) {
		this.prwList = prwList;
	}
	public String getPorderno() {
		return porderno;
	}
	public void setPorderno(String porderno) {
		this.porderno = porderno;
	}
	public String getXmflag() {
		return xmflag;
	}
	public void setXmflag(String xmflag) {
		this.xmflag = xmflag;
	}
	public List<BJBean> getMfList() {
		return mfList;
	}
	public void setMfList(List<BJBean> mfList) {
		this.mfList = mfList;
	}
	public String getSsyhId() {
		return ssyhId;
	}
	public void setSsyhId(String ssyhId) {
		this.ssyhId = ssyhId;
	}
	public String getSsyhName() {
		return ssyhName;
	}
	public void setSsyhName(String ssyhName) {
		this.ssyhName = ssyhName;
	}
	public String getSszhId() {
		return sszhId;
	}
	public void setSszhId(String sszhId) {
		this.sszhId = sszhId;
	}
	public String getSszhName() {
		return sszhName;
	}
	public void setSszhName(String sszhName) {
		this.sszhName = sszhName;
	}
	public String getYesnotj() {
		return yesnotj;
	}
	public void setYesnotj(String yesnotj) {
		this.yesnotj = yesnotj;
	}
	public String getTjdh() {
		return tjdh;
	}
	public void setTjdh(String tjdh) {
		this.tjdh = tjdh;
	}
	public String getStopReason() {
		return stopReason;
	}
	public void setStopReason(String stopReason) {
		this.stopReason = stopReason;
	}
	public String getPplb() {
		return pplb;
	}
	public void setPplb(String pplb) {
		this.pplb = pplb;
	}
	public String getChangebjflag() {
		return changebjflag;
	}
	public void setChangebjflag(String changebjflag) {
		this.changebjflag = changebjflag;
	}
	
}
