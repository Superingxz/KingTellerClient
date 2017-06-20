package com.kingteller.client.config;

/**
 * 通用URL配置
 * 
 * @author Administrator
 * 
 */
public final class KingTellerUrlConfig {
    public final static String DefalutIp = "ktcs.kingteller.com.cn";//218.107.9.108  //14.23.165.67  ktcs.kingteller.com.cn
    public final static String DefaultPort = "8081";//8081   8086
    public final static String DefaultPath = "/ktcs";

	// 登陆
	public final static String LoginUrl = "/kt/mobile/mobileLogin_mobileAction.action";
	// 获取代办
	public final static String WaitDoUrl = "/kt/mobile/queryToDoList_mobileAction.action";
	// 获取菜单权限
	public final static String GetRightUrl = "/kt/mobile/queryMenu_mobileAction.action";
	// 通用数据接口
	public final static String GetCommonDataListUrl = "/kt/mobile/queryListData_mobileAction.action";
	// 公告详细
	public final static String CommonDataWebUrl = "/sysmgr/notice/queryNoticeByNoticeId_notice.action?noticeId=%1$s&userId=%2$s";
	// 技能考核
	public final static String WebJnkhUrl = "/custmgr/skillgeade/login_weiHuSkillGradeMobile.action?uid=%1$s&pwd=%2$s";
	// 获取工单列表
	public final static String WebRwdUrl = "/workreportmobile/queryWorkOrder_workordermobile.action";
	// 工程师更改工单状态
	///kt/mobile/mobileLogin_mobileAction.action
	public final static String WebRwdztUrl = "/workreportmobile/updateOrderStatus_workordermobile.action";
	//工程师更改工单状态  结束维护 提示二维码信息
	public final static String WebRwdztJsWhTsUrl = "/workreportmobile/checkScanCode_workordermobile.action";
		
	//填写报告列表
	public final static String WebTxbgUrl = "/sysmgr/workreportmobile/queryWorkReportByList_workReportMobileNew.action";
	public final static String WebQtbgUrl = "/sysmgr/workreportmobile/toEdit_workReportMobileNew.action";
	//获取硬件配置
	public final static String WebYjpzUrl = "/sysmgr/workreportmobile/queryMachineComponentList_workReportMobileNew.action";
	
	//获取响应时间
	public final static String toUpdateMachineUrl = "/sysmgr/workreportmobile/toUpdateMachine_workReportMobileNew.action";
	//设置响应时间
	public final static String doUpdateMachineUrl = "/sysmgr/workreportmobile/doUpdateMachine_workReportMobileNew.action";

		
	public final static String otherMatterUrl = "/sysmgr/workreportmobile/doSaveOther_workReportMobileNew.action";
	
	// 知识库
	public final static String WebZskhUrl = "/sysmgr/knowledge/login_Knowledgemgr.action?uid=%1$s&pwd=%2$s";
	// 审批报告
	public final static String WebSpbgUrl = "/sysmgr/workreportmobile/queryAuditReport_workReportMobileNew.action";
	//单个审批报告
	
	public final static String WebDgspUrl = "/sysmgr/workreportmobile/toEditAuditPage_workReportMobileNew.action";
	// 销售下单
	public final static String WebXsXdUrl = "/saleCenter/saleBill/querySalesBill_salesMobile.action?uid=%1$s&pwd=%2$s";
	// 销售单审核
	public final static String WebShUrl = "/production/market/saleCenter/saleBill/mobile/sales_sp_list.html";
	// 我的销售单
	public final static String WebMysaleUrl = "/production/market/saleCenter/saleBill/mobile/mySaleBill_list.html";
	// 服务查询
	public final static String WebFwzUrl = "/custmgr/maplocation/toPhoneFwz" +
			"_MachineLocation.action?userid=%1$s";
	// 机器查询
	public final static String WebATMCUrl = "/custmgr/maplocation/toPhoneMachine_MachineLocation.action?userid=%1$s";
	// 搜索经纬度ATM信息
	public final static String LocATMListUrl = "/custmgr/maplocation/queryLnglatByJqbh_MachineLocation.action";
	// 获取ATM列表
	public final static String ATMListUrl = "/custmgr/maplocation/queryLnglatByJqbhForPhone_MachineLocation.action";
	// 员工定位搜索
	public final static String StaffListUrl = "/custmgr/maplocation/queryUserlistForPhone_MachineLocation.action";
	//ATM定位搜索
	public final static String StaffATMListUrl = "/custmgr/maplocation/searchMachineInfo_MachineLocation.action";
	//FWZ定位搜索
	public final static String StaffFWZListUrl = "/custmgr/maplocation/searchFWZInfo_MachineLocation.action";
	//获取服务站位置信息
	public final static String getFwzLocationUrl = "/mobile/machineMobileMap/searchOrg_machineMobileMapMgr.action";
	//设置服务站位置信息
	public final static String setFwzLocationUrl = "/mobile/machineMobileMap/saveOrgLocation_machineMobileMapMgr.action";
		
	// 项目管理
	public final static String ProjectUrl = "/projectmanage/projectcollectstatistic/workorderListStatistic.html";
	// 项目工单审批]
	public final static String ProjectAuditUrl = "/projectmanage/auditOrder/mallwaitsubmitorder.html";
	//项目工单销单
	public final static String ProjectDEALORDERUrl = "/projectmanage/dealorder/xdorderlist.html";
	
	// 检索机器
	public final static String CheckMacineUrl = "/mobile/machineMobileMap/checkMacine_machineMobileMapMgr.action";
	// 文件上传files longitude latitude upimage userid viewjson
	public final static String UploadPicUrl = "/mobile/machineMobileMap/saveMachineData_machineMobileMapMgr.action";
	// 检测session是否有效
	public final static String CheckSessionUrl = "/kt/mobile/returnInvalidateSessionByMobile_mobileAction.action";
	// 推送消息点击接收后 回调 jpushSendNo
	public final static String JPushCallBackUrl = "/kt/mobile/jpushCallBack_mobileAction.action";
	// 上传经纬度
	public final static String UploadLocationUrl = "/mobile/machineMobileMap/saveUserLonction_machineMobileMapMgr.action";
	// 注销session
	public final static String LoginoutUrl = "/kt/mobile/exitLogin_mobileAction.action";
	// 极光注册域名是否成功
	public final static String PushUpdateAliasUrl = "/kt/mobile/updateAliasStatus_mobileAction.action";
	// 打开其他模块busid不为空的url
	public final static String OtherBusIdUrl = "/sysmgr/workreport/toViewMyOrderDetailPage_workreportmobile.action?openerflag=1&vo.workOrderId=%s&reportflowstatus=10";
	// 注册极光IMEI
	public final static String RegJPushIMEIUrl = "/kt/mobile/registerMobileUser_mobileAction.action";
	// app升级
	public final static String APPUpdateUrl = "/kt/mobile/update_mobileAction.action";
	// 反馈
	public final static String SubmitFeedBackUrl = "/kt/mobile/saveUserFeedBackInfo_mobileAction.action";

	//知识库
	public final static String KnowledgeUrl="/sysmgr/knowledge/queryKnowledgeList_KnowledgeMobileMgr.action";
	//通用文件上传接口
	//upload; 上传文件的类型 ContentType  uploadContentType;  上传文件的名称   uploadFileName; 
	//获取费用方式
	public final static String FeeModeUrl="/sysmgr/workreportmobile/getFeeMode_workReportMobileNew.action";
     //是否退回报告
	public final static String SfthUrl="/sysmgr/workreportmobile/doAuditFlow_workReportMobileNew.action";
	//0批量审核 1：批量退回
	public final static String PlthUrl="/sysmgr/workreportmobile/toAll_workReportMobileNew.action";
	//故障描述
	public final static String GzmsUrl="/sysmgr/workreportmobile/getTroubleRemarkInfo_workReportMobileNew.action";
	//处理工程
	public final static String ClgcUrl="/sysmgr/workreportmobile/getWorkTypeHandleSub_workReportMobileNew.action";
	//故障类别
	public final static String GzlbUrl="/sysmgr/workreportmobile/queryWlByAll_workReportMobileNew.action";
	//故障部件
	public final static String GzbjUrl="/sysmgr/workreportmobile/queryWlByAll_workReportMobileNew.action";
	//派单列表
	public final static String PdlbUrl="/workreportmobile/queryAssignOrderInfo_workordermobile.action";
	//机器查询
	public final static String JqcxUrl="/workreportmobile/queryMachineInfoList_workordermobile.action";
	//查询维护人员
	public final static String WhryUrl="/workreportmobile/queryWorkerName_workordermobile.action";
	//获取系统推荐维护人员
	public final static String XttjwhryUrl="/workreportmobile/queryAvailableWorkerListByJQid_workordermobile.action";
	//提交派单
	public final static String TjpdUrl="/workreportmobile/doSaveAssignWorkOrder_workordermobile.action";
	//查询历史派单
	public final static String CxlspdUrl="/workreportmobile/queryAssignOrderInfo_workordermobile.action";
	//查询服务站人员
	public final static String CxfwzryUrl="/workreportmobile/queryFwzWorker_workordermobile.action";
	//webview连接
	public final static String WebViewUrl="/sysmgr/workreport/toEdit_workreportmobile.action";
	//是否退机
	public final static String SftjUrl="/sysmgr/workreportmobile/queryBackSingle_workReportMobileNew.action";
	//查询用户的培训考核试卷
	public final static String sjlistUrl = "/custmgr/skillgeade/querySjPageList_pxkhmgrMobile.action";
	//数据库更新
	public final static String SjkgxUrl="/sysmgr/workreportmobile/checkBasicData_workReportMobileNew.action";
	//选择题
	public final static String XztUrl="/custmgr/skillgeade/xztJsonDataFind_pxkhmgrMobile.action";
	//简答题
	public final static String JdtUrl="/custmgr/skillgeade/jdtJsonDataFind_pxkhmgrMobile.action";
	//填空题
	public final static String TktUrl="/custmgr/skillgeade/tktJsonDataFind_pxkhmgrMobile.action";
	//提交试卷
	public final static String TjsjUrl="/custmgr/skillgeade/doSavePxkhData_pxkhmgrMobile.action";
	//获取物流监控列表
	public final static String WljklbUrl="/custmgr/logisticmonitor/queryAllConsignByYpd_TaskAndConsignMobile.action";
	//获取物流监控具体信息
	public final static String WljkrwdUrl="/custmgr/logisticmonitor/queryConsignByRwdId_TaskAndConsignMobile.action";
	//任务单事件处理
	public final static String RwdsjUrl="/custmgr/logisticmonitor/updateRwdStatus_TaskAndConsignMobile.action";
	//查询费用类型接口
	public final static String FeeLxUrl="/custmgr/logisticmonitor/queryFare_TaskAndConsignMobile.action";
	//物流监控提交,暂存报告接口
	public final static String TjZcBgUrl="/custmgr/logisticmonitor/saveWhbgFareM_TaskAndConsignMobile.action";
	//更改托运单状态
	public final static String WljktydztUrl="/custmgr/logisticmonitor/updateTydStatus_TaskAndConsignMobile.action";
	
	//物流监控其他事务 start //
	public final static String WljkotUrl="/custmgr/othertask/queryOtherTask_otherTaskMobile.action";
	public final static String WljkcUrl="/custmgr/othertask/queryOtherConsign_otherTaskMobile.action";
	public final static String WljkstuatsUrl="/custmgr/othertask/updateStatus_otherTaskMobile.action";
	public final static String WljkOtSubmitUrl="/custmgr/othertask/submitOtherTask_otherTaskMobile.action";
	//物流监控其他事务 stop  //
	
	//新增网点
	public final static String XzwddzUrl="/sysmgr/workreportmobile/addWDDZ_workReportMobileNew.action";
	//
	public final static String WljkUploadPicUrl = "/custmgr/logisticmonitor/uploadPhotos_TaskAndConsignMobile.action";
	//获取机器信息URL
	public final static String HqjqxxURL = "/workreportmobile/queryMachineDetailInfoByJQBH_workordermobile.action";
	//视频查询分类
	public final static String SpcxflURL = "/custmgr/km/video/queryGernerList_QueryMoblie.action";
	//视频列表
	public final static String SplbURL = "/custmgr/km/video/queryVideoByGernerId_QueryMoblie.action";
	public final static String SphjURL ="/custmgr/km/video/mobileInsertRecord_QueryMoblie.action";
	//文档阅读
	public final static String WdydURL ="/custmgr/km/wordlibaray/getLibayById_wordlibaray.action";
	//参数为jqId
	public final static String Fhjqxx = "/workreportmobile/fetchXmgdInfo_workordermobile.action";
	
	/**二维码模块**/
	/**二维码录入**/
	//根据服务站id查询所属仓位
	public final static String CxsscwUrl = "/custmgr/barcodemobile/getWrInfoByOrgId_barcodemobile.action";
	//模糊查询服务站信息
	public final static String MhCxsscwUrl = "/custmgr/barcodemobile/queryWrInfo_barcodemobile.action";
	//根据二维码，查询二维码关联的物料信息
	public final static String CxEwmMsg = "/custmgr/barcodemobile/getWlInfoByBarcode_barcodemobile.action";
	//录入二维码到物料明细
	public final static String LREwmMsg = "/custmgr/barcodemobile/saveBarcode_barcodemobile.action";
	/**出库物料**/
	//获取出库单列表
	public final static String CkdLists = "/custmgr/barcodemobile/queryRecDeliversWsPage_barcodemobile.action";
	//获取出库单明细列表
	public final static String CkdMxLists = "/custmgr/barcodemobile/queryRecDeliversWsFormsAll_barcodemobile.action";
	//出库单明细扫描验证二维码
	public final static String CkdMxEwmYz = "/custmgr/barcodemobile/getWlDetailByBarcodeForRecDeliversWs_barcodemobile.action";
	//保存出库单
	public final static String CkdBC = "/custmgr/barcodemobile/saveRecDeliversWs_barcodemobile.action";
	/**收货物料**/
	//获取收货单列表
	public final static String SHdLists = "/custmgr/barcodemobile/queryDeliversWarehousePage_barcodemobile.action";
	//获取收货明细单列表
	public final static String SHdMxLists = "/custmgr/barcodemobile/queryDeliversWlDetailListAll_barcodemobile.action";
	//收货单明细扫描验证二维码
	public final static String SHdMxEwmYz = "/custmgr/barcodemobile/updateDeliversWlDetailBarcodeScaned_barcodemobile.action";
	/**保修物料**/
	//获取保修单列表
	public final static String BXdLists = "/custmgr/barcodemobile/querySelfMaintainList_barcodemobile.action";
	//获取保修单明细列表
	public final static String BXdMxLists = "/custmgr/barcodemobile/queryRepairReceiptWlDetailByRrid_barcodemobile.action";
	//报修明细扫描验证二维码
	public final static String BXdMxEwmYz = "/custmgr/barcodemobile/getWlDetailByBarcodeForRepairReceipt_barcodemobile.action";
	//保存报修单
	public final static String BXdBC = "/custmgr/barcodemobile/updateRepairReceiptWithBarcode_barcodemobile.action";
	/**服务站出入库物料**/
	//获取服务站出入库物料单列表
	public final static String FwzCRKList = "/custmgr/barcodemobile/queryRecDeliversRegPage_barcodemobile.action";
	//获取服务站出入库物料单明细列表
	public final static String FwzCRKMxList = "/custmgr/barcodemobile/queryRecDeliversRegFormAll_barcodemobile.action";
	
	//获取服务站出库物料扫描验证二维码
	public final static String FwzCKMxEwmYz = "/custmgr/barcodemobile/getWlDetailByBarcodeForRecDeliversRegOut_barcodemobile.action";
	//获取服务站出入库物料扫描验证二维码
	public final static String FwzRKMxEwmYz = "/custmgr/barcodemobile/getWlDetailByBarcodeForRecDeliversRegIn_barcodemobile.action";
	
	//保存服务站出入库物料
	public final static String FwzCRKBC = "/custmgr/barcodemobile/saveRegDeliversReg_barcodemobile.action";
	/**网点机器扫描**/
	//搜索网点信息列表
	public final static String CsWdName = "/custmgr/barcodemobile/queryWdxxListByWdName_atmbarcode.action";
	//搜索机器列表
	public final static String CsJqName = "/custmgr/barcodemobile/queryJqbm_atmbarcode.action";
	//获取网点信息和机器信息
	public final static String WdJQXinXi = "/custmgr/barcodemobile/queryAtmBarcodeInfoByWdidAndJqid_atmbarcode.action";
	//获取ATM物料信息
	public final static String AtmWlXinXi= "/custmgr/barcodemobile/queryWlInfoByBarcode_atmbarcode.action";
	//保存网点信息和机器信息
	public final static String WdJQBC = "/custmgr/barcodemobile/doSaveAtmBarcode_atmbarcode.action";
	/**网点机器备件更换扫描**/
	//获取网点机器备件更换物料
	public final static String WdJQBjGHHQ = "/custmgr/barcodemobile/queryAtmWlReplaceMiddleList_atmbarcode.action";
	//保存网点机器备件更换物料
	public final static String WdJQBjGHBC = "/custmgr/barcodemobile/doSaveAtmWlReplaceMiddleParam_atmbarcode.action";
	/**网点发货扫描**/
	//保存网点发货物料
	public final static String WdFHBC = "/custmgr/barcodemobile/saveRecDeliversWsOfWd_barcodemobile.action";
	/**追溯二维码**/
	//搜索追溯二维码信息
	public final static String SsZsEwm = "/custmgr/barcodemobile/getTraceInfoByBarcode_barcodemobile.action";
	//查看追溯二维码信息
	public final static String CkZsEwm = "/custmgr/barcodemobile/queryTraceOperationRecodeList_barcodemobile.action";
	
	
	/**离线任务模块**/
	//查找3天内的所有    工单id，工单单号，机器id，机器编号
	public final static String OfflineCxGddh = "/custmgr/offlinemobile/getWorkOrderInfo_offlinemobile.action";
	//根据整机二维码  查询   机器id，机器编号
	public final static String OfflineCxJqbh = "/custmgr/offlinemobile/getMachineInfo_offlinemobile.action";
	//离线数据总接口
	public final static String OfflineUpData = "/custmgr/offlinemobile/interfacetTansfer_offlinemobile.action";
	
	
	/**考勤模块**/
	//我的考勤 List
	public final static String OAAttendanceList = "/kqmgr/mobile/queryKqinfoMobile_kqmobile.action";
	//考勤审批 List
	public final static String OAAduitAttendanceList = "/kqmgr/mobile/queryKqSpMobile_kqmobile.action";
	//批量考勤审批  
	public final static String OAAduitAttendanceAllApproval = "/kqmgr/mobile/dobatch_kqmobile.action";
	
	//获取 人员信息
	public final static String GetOAPeopleDate = "/kqmgr/mobile/queryUsers_kqmobile.action";
	//获取 请假单信息
	public final static String GetOALeaveFromDate = "/kqmgr/mobile/getLeaveForSick_kqmobile.action";
	//获取 使用时间
	public final static String GetOAUserTimeDate = "/kqmgr/mobile/getDayAndHour_kqmobile.action";
	//获取 字典配置
	public final static String GetOADictList = "/kqmgr/mobile/getDictInfo_kqmobile.action";
	//获取 历史日期 
	public final static String GetOAHistoricalDayList = "/kqmgr/mobile/getKqmonthInfo_kqmobile.action";
		
	//获取各种单据 数据	查看 修改
	public final static String GetOAAllWorkDate = "/kqmgr/mobile/toedit_kqmobile.action";
	//获取各种单据 数据	审批 
	public final static String GetOAAllAduitWorkDate = "/kqmgr/mobile/toaudit_kqmobile.action";
	
	//各种单据 数据  操作 	zc暂存  submit提交   audit同意  back退回
	public final static String OAOAAllAduitWorkFillIn = "/kqmgr/mobile/dosave_kqmobile.action";
	
	
	
}
