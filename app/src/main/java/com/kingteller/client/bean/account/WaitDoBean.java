package com.kingteller.client.bean.account;

import java.io.Serializable;

import com.kingteller.client.utils.KingTellerJudgeUtils;
import com.kingteller.framework.utils.MD5Utils;

/**
 * 待办的Bean
 * @author 王定波
 *
 */
public class WaitDoBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3749194426487382532L;
	private String actCode; //业务环节编码
	private String assignOrderDate; //维护管理派单时间
	private String beanName; //业务请求Bean名称
	private String busCode; //业务单据编号
	private String busId; //业务ID
	private String content; //内容
	private String curUserCode; //当前用户账号（只适用于故障信息）
	private int currentPage; //业务记录当前第几页
	
	/**
	 * 业务待办分类标记    
	 * notice:公告信息
	 * assignOrder:工单审核
	 * workReportFlow:维护报告
	 */
	private String flag; 

	/**
	 * 业务流程编码    
	 * saleBillFlowNoContract：未签合同订单   
	 * saleBillFlowContracted：已签合同订单
	 * logisticsFlow：物流派单
	 * otherMatter：其他事务派单
	 * assignOrder：维护派单
	 * ReportFlow：报告
	 */
	private String flowCode; 

	private String flowTitle; //工作流标题
	private String nav; //手机端导航名称
	private int pageSize; //业务记录每页条数
	private String sendTime;  //任务创建时间
	private String senderName; //任务创建者姓名
	private String title; //任务创建标题
	private String toDoTaskUrl; //待办处理URL
	private int totalCount; //业务总记录数
	private String backFlag;
	
	public String getActCode() {
		return actCode;
	}
	public void setActCode(String actCode) {
		this.actCode = actCode;
	}
	public String getBeanName() {
		return beanName;
	}
	public void setBeanName(String beanName) {
		this.beanName = beanName;
	}
	public String getAssignOrderDate() {
		return assignOrderDate;
	}
	public void setAssignOrderDate(String assignOrderDate) {
		this.assignOrderDate = assignOrderDate;
	}
	public String getBusCode() {
		return busCode;
	}
	public void setBusCode(String busCode) {
		this.busCode = busCode;
	}
	public String getBusId() {
		return busId;
	}
	public void setBusId(String busId) {
		this.busId = busId;
	}
	public String getToDoTaskUrl() {
		return toDoTaskUrl;
	}
	public void setToDoTaskUrl(String toDoTaskUrl) {
		this.toDoTaskUrl = toDoTaskUrl;
	}
	public String getNav() {
		return nav;
	}
	public void setNav(String nav) {
		this.nav = nav;
	}
	public String getFlowCode() {
		return flowCode;
	}
	public void setFlowCode(String flowCode) {
		this.flowCode = flowCode;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public int getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public String getCurUserCode() {
		return curUserCode;
	}
	public void setCurUserCode(String curUserCode) {
		this.curUserCode = curUserCode;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public int getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}
	public String getFlowTitle() {
		return flowTitle;
	}
	public void setFlowTitle(String flowTitle) {
		this.flowTitle = flowTitle;
	}
	public String getSenderName() {
		return senderName;
	}
	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}
	public String getSendTime() {
		return sendTime;
	}
	public void setSendTime(String sendTime) {
		this.sendTime = sendTime;
	}
	
	public String getBackFlag() {
		return backFlag;
	}
	public void setBackFlag(String backFlag) {
		this.backFlag = backFlag;
	}
	/**
	 * 唯一标识Md5
	 * @return
	 */
	public String getOnlyId()
	{
		StringBuffer sb=new StringBuffer(KingTellerJudgeUtils.isEmptyGetString(getBusId()));
		sb.append(KingTellerJudgeUtils.isEmptyGetString(getSendTime()));
		sb.append(KingTellerJudgeUtils.isEmptyGetString(getSenderName()));
		sb.append(KingTellerJudgeUtils.isEmptyGetString(getTitle()));
		sb.append(KingTellerJudgeUtils.isEmptyGetString(getToDoTaskUrl()));
		sb.append(KingTellerJudgeUtils.isEmptyGetString(getBeanName()));
		return MD5Utils.toMD5(sb.toString());
	}

}
