package com.kingteller.client.activity.workorder.checkout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kingteller.R;
import com.kingteller.client.KingTellerApplication;
import com.kingteller.client.activity.workorder.BaseReportActivity;
import com.kingteller.client.bean.workorder.AttachmentBean;
import com.kingteller.client.bean.workorder.CostInfoBean;
import com.kingteller.client.bean.workorder.FreeData;
import com.kingteller.client.bean.workorder.LogisticsReportBean;
import com.kingteller.client.config.KingTellerUrlConfig;
import com.kingteller.client.netauth.AjaxHttpCallBack;
import com.kingteller.client.netauth.KTHttpClient;
import com.kingteller.client.utils.CommonSelcetUtils;
import com.kingteller.client.utils.ConditionUtils;
import com.kingteller.client.utils.KingTellerConfigUtils;
import com.kingteller.client.utils.KingTellerJudgeUtils;
import com.kingteller.client.view.FeeGroupView;
import com.kingteller.client.view.KingTellerEditText;
import com.kingteller.client.view.ListViewObj;
import com.kingteller.client.view.dialog.use.KingTellerProgressUtils;
import com.kingteller.client.view.toast.T;
import com.kingteller.framework.http.AjaxParams;

/**
 * 物流报告
 * 
 * @author 王定波
 * 
 */
public class LogisticsReportCheckoutActivity extends BaseReportActivity{

	private LogisticsReportBean logisticsReport;

	private KingTellerEditText orderNo; // 工单号
	private KingTellerEditText workDateStr; // 工作日期
	private KingTellerEditText areaName; // 区域
	private KingTellerEditText wdName; // 网点
	private KingTellerEditText bankName;
	private KingTellerEditText machineCode; // 机器编号
	private KingTellerEditText assessoryNum;// 配件数量
	private KingTellerEditText recordUserName; // 派单人
	private KingTellerEditText assignDateStr; // 派单时间
	private KingTellerEditText confirmDateStr; // 确认接收
	private KingTellerEditText arrivalDateStr; // 达到现场.
	private KingTellerEditText beginDateStr; // 装车完毕
	private KingTellerEditText endDateStr; // 物流完成
	private KingTellerEditText requireDateStr; // 要求到达时间
	private KingTellerEditText machineNum; // 机器数量
	private KingTellerEditText packageflag; // 包装是否损坏
	private KingTellerEditText packageRemark; // 包装损坏说明
	private KingTellerEditText installAddress; // 装机地址
	private KingTellerEditText custName; // 客户名称
	private KingTellerEditText linkName; // 联系人
	private KingTellerEditText linkPhone; // 联系电话
	private KingTellerEditText workType; // 工作类别(qxz：请选择，backMachine：退机，transferGoods：货运，other：其他方式，sendMachine：发机，moveMachine：移机)
	private KingTellerEditText specialReq; // 特殊要求
	private KingTellerEditText workAddress; // 发货地址
	private KingTellerEditText workToAddress; // 调入地址
	private KingTellerEditText troubleRemark; // 工作内容
	private KingTellerEditText maintainRemark; // 备注
	private KingTellerEditText assignNames; // 维护工程师
	private KingTellerEditText remark; // 工单的备注
	private KingTellerEditText picFlag;

	private KingTellerEditText receiverType;
	private KingTellerEditText receiver;
	private KingTellerEditText receiverPhone;
	private LinearLayout layout_hide;
	private LinearLayout layout_report;
	
	private ListViewObj listviewObj;

	@Override
	public void onCreate(Bundle paramBundle) {
		super.onCreate(paramBundle);
		setContentView(R.layout.layout_logistics_report_checkout);
		KingTellerApplication.addActivity(this);
		
		if (findViewById(R.id.loading_view) != null) {
			setListviewObj(new ListViewObj(this));
		}
		// 加载背景不透明
		getListviewObj().setBackground(false);
		
		initUI();
		initData();
		initChangeUI();
	}

	@Override
	public void initUI() {
		// TODO Auto-generated method stub
		super.initUI();
		
		title_text.setText("物流报告");
		
		dataKey = "logistics";
		orderNo = (KingTellerEditText) findViewById(R.id.orderNo);
		workDateStr = (KingTellerEditText) findViewById(R.id.workDateStr);
		areaName = (KingTellerEditText) findViewById(R.id.areaName);
		machineCode = (KingTellerEditText) findViewById(R.id.machineCode);
		wdName = (KingTellerEditText) findViewById(R.id.wdName);
		bankName = (KingTellerEditText) findViewById(R.id.bankName);
		recordUserName = (KingTellerEditText) findViewById(R.id.recordUserName);
		assignDateStr = (KingTellerEditText) findViewById(R.id.assignDateStr);
		confirmDateStr = (KingTellerEditText) findViewById(R.id.confirmDateStr);
		arrivalDateStr = (KingTellerEditText) findViewById(R.id.arrivalDateStr);
		beginDateStr = (KingTellerEditText) findViewById(R.id.beginDateStr);
		endDateStr = (KingTellerEditText) findViewById(R.id.endDateStr);
		requireDateStr = (KingTellerEditText) findViewById(R.id.requireDateStr);
		machineNum = (KingTellerEditText) findViewById(R.id.machineNum);

		custName = (KingTellerEditText) findViewById(R.id.custName);
		linkName = (KingTellerEditText) findViewById(R.id.linkName);
		linkPhone = (KingTellerEditText) findViewById(R.id.linkPhone);
		workType = (KingTellerEditText) findViewById(R.id.workType);
		specialReq = (KingTellerEditText) findViewById(R.id.specialReq);
		workAddress = (KingTellerEditText) findViewById(R.id.workAddress);
		workToAddress = (KingTellerEditText) findViewById(R.id.workToAddress);
		troubleRemark = (KingTellerEditText) findViewById(R.id.troubleRemark);
		maintainRemark = (KingTellerEditText) findViewById(R.id.maintainRemark);
		assignNames = (KingTellerEditText) findViewById(R.id.assignNames);
		assessoryNum = (KingTellerEditText) findViewById(R.id.assessoryNum);
		picFlag = (KingTellerEditText) findViewById(R.id.picFlag);
		packageflag = (KingTellerEditText) findViewById(R.id.packageflag);
		packageRemark = (KingTellerEditText) findViewById(R.id.packageRemark);
		installAddress = (KingTellerEditText) findViewById(R.id.installAddress);
		remark = (KingTellerEditText) findViewById(R.id.remark);

		receiverType = (KingTellerEditText) findViewById(R.id.receiverType);
		receiver = (KingTellerEditText) findViewById(R.id.receiver);
		receiverPhone = (KingTellerEditText) findViewById(R.id.receiverPhone);

		
		layout_hide = (LinearLayout) findViewById(R.id.layout_hide);
		layout_report = (LinearLayout) findViewById(R.id.layout_report);
		
		//orderNo.setFieldCheckoutEnabled(false);
		int count = layout_hide.getChildCount();
		for (int i = 0; i < count; i++) {
			if (layout_hide.getChildAt(i).getClass().getName()
					.equals(KingTellerEditText.class.getName())) {
				((KingTellerEditText) layout_hide.getChildAt(i))
						.setFieldCheckoutEnabled(false);
			}
		}
		
		int count1 = layout_report.getChildCount();
		for (int i = 0; i < count1; i++) {
			if (layout_report.getChildAt(i).getClass().getName()
					.equals(KingTellerEditText.class.getName())) {
				((KingTellerEditText) layout_report.getChildAt(i))
						.setFieldCheckoutEnabled(false);
			}
		}
	}

	@Override
	public void initData() {
		// TODO Auto-generated method stub
		super.initData();
		workType.setLists(CommonSelcetUtils
				.getSelectList(CommonSelcetUtils.workTypeLogistic));
		picFlag.setLists(CommonSelcetUtils
				.getSelectList(CommonSelcetUtils.radios01));
		packageflag.setLists(CommonSelcetUtils
				.getSelectList(CommonSelcetUtils.radios01));
		receiverType.setLists(CommonSelcetUtils
				.getSelectList(CommonSelcetUtils.receiverType));

		// 从数据库读取数据
		/*Report data = KingTellerUtils.getReportFromDataBase(this,
				workBean.getOrderId());*/

		//if (data == null || optType != 0 || data.getReportData() == null)
			getReportInfo();
		/*else {
			setDataInfo(KingTellerUtils.getReportDataFromString(
					data.getReportData(), LogisticsReportBean.class));
		}*/
	}

	private void initChangeUI() {

	}

	public void getReportInfo() {
		if (!KingTellerJudgeUtils.isNetAvaliable(this)) {
			T.showShort(this, "没有网络, 请检查网络是否可用!");
			return;
		}

		KTHttpClient fh = new KTHttpClient(true);
		AjaxParams params = new AjaxParams();

		params.put("orderId", workBean.getOrderId());
		params.put("reportType", workBean.getOrderType());

		fh.post(KingTellerConfigUtils.CreateUrl(this, KingTellerUrlConfig.WebQtbgUrl),
				params, new AjaxHttpCallBack<LogisticsReportBean>(this, true) {

					@Override
					public void onStart() {
						KingTellerProgressUtils.showProgress(LogisticsReportCheckoutActivity.this, "数据加载中...");
					}
			
					@Override
					public void onFinish() {
						KingTellerProgressUtils.closeProgress();
					}

					@Override
					public void onDo(LogisticsReportBean data) {
						setDataInfo(data);
					};
				});
	}

	/**
	 * 设置数据
	 * 
	 * @param data
	 */
	private void setDataInfo(LogisticsReportBean data) {
		logisticsReport = data;
		orderNo.setFieldTextAndValue(data.getOrderNo());
		workDateStr.setFieldTextAndValue(data.getWorkDateStr());
		areaName.setFieldTextAndValue(data.getAreaName());
		wdName.setFieldTextAndValue(data.getWdName());
		bankName.setFieldTextAndValue(data.getBankName());
		machineCode.setFieldTextAndValue(data.getMachineCode());
		assessoryNum.setFieldTextAndValue(data.getAssessoryNum().toString());
		recordUserName.setFieldTextAndValue(data.getRecordUserName());
		assignDateStr.setFieldTextAndValue(data.getAssignDateStr());

		confirmDateStr.setFieldTextAndValue(data.getConfirmDateStr());
		arrivalDateStr.setFieldTextAndValue(data.getArrivalDateStr());
		beginDateStr.setFieldTextAndValue(data.getBeginDateStr());
		endDateStr.setFieldTextAndValue(data.getEndDateStr());
		requireDateStr.setFieldTextAndValue(data.getRequireDateStr());
		machineNum.setFieldTextAndValue(data.getMachineNum().toString());
		packageflag.setFieldTextAndValueFromValue(data.getPackageflag()
				.toString());
		packageRemark.setFieldTextAndValue(data.getPackageRemark());
		installAddress.setFieldTextAndValue(data.getInstallAddress());
		custName.setFieldTextAndValue(data.getCustName());
		linkName.setFieldTextAndValue(data.getLinkName());
		linkPhone.setFieldTextAndValue(data.getLinkPhone());
		workType.setFieldTextAndValueFromValue(data.getWorkType());
		specialReq.setFieldTextAndValue(data.getSpecialReq());
		workAddress.setFieldTextAndValue(data.getWorkAddress());
		workToAddress.setFieldTextAndValue(data.getWorkToAddress());
		troubleRemark.setFieldTextAndValue(data.getTroubleRemark());
		maintainRemark.setFieldTextAndValue(data.getMaintainRemark());
		assignNames.setFieldTextAndValue(data.getAssignNames());
		remark.setFieldTextAndValue(data.getRemark());
		picFlag.setFieldTextAndValueFromValue(data.getPicFlag().toString());

		receiverType.setFieldTextAndValueFromValue(data.getReceiverType());
		receiver.setFieldTextAndValue(data.getReceiver());
		receiverPhone.setFieldTextAndValue(data.getReceiverPhone());
		
		auditRemark.setFieldTextAndValue(data.getAuditRemark());
		auditContent.setFieldTextAndValue(data.getAuditContent());
		auditTitle.setText("物流报告意见");

		fee_group_list.getLayoutList().removeAllViews();

		for (int i = 0; i < data.getEpList().size(); i++) {
			FeeGroupView fview;
			if (i == 0)
				fview = new FeeGroupView(LogisticsReportCheckoutActivity.this, false);
			else
				fview = new FeeGroupView(LogisticsReportCheckoutActivity.this, true);
			FreeData fdata = new FreeData();
			fdata.setUserName(data.getEpList().get(i).getUsername());
			fdata.setUserId(data.getEpList().get(i).getUserId());
			fdata.setFeeMoney(String.valueOf(data.getEpList().get(i)
					.getCostFee()));
			fdata.setBusLine(data.getEpList().get(i).getDescript());
			fdata.setFeeMode(data.getEpList().get(i).getCostdescName());
			fdata.setFeeModeId(data.getEpList().get(i).getCostDesc());
			fdata.setFeeTypeId(String.valueOf(data.getEpList().get(i)
					.getTypeId()));
			fdata.setFeeType(data.getEpList().get(i).getTypeName());
			fdata.setId(data.getEpList().get(i).getId());

			((KingTellerEditText) fview.findViewById(R.id.maintainpersonname)).setFieldCheckoutEnabled(false);
			((KingTellerEditText) fview.findViewById(R.id.expand1)).setFieldCheckoutEnabled(false);
			((KingTellerEditText) fview.findViewById(R.id.reportAccess)).setFieldCheckoutEnabled(false);
			((KingTellerEditText) fview.findViewById(R.id.trafficefee)).setFieldCheckoutEnabled(false);
			((KingTellerEditText) fview.findViewById(R.id.arriveroute)).setFieldCheckoutEnabled(false);
			fview.setData(fdata);
			fee_group_list.addItem(fview);
		}

		getListviewObj().setVisibility(View.GONE);
	}

	/**
	 * 获取参数生成json
	 * 
	 * @return
	 */
	@Override
	public String getFromData() {
		HashMap<String, Object> params = ConditionUtils.getHashMapForm(this,
				(LinearLayout) findViewById(R.id.layout_form), false);

		HashMap<String, Object> paramsaudit = ConditionUtils.getHashMapForm(
				this, (LinearLayout) findViewById(R.id.layout_audit_common),
				false);
		HashMap<String, Object> paramsinfo = ConditionUtils.getHashMapForm(this,
				(LinearLayout) findViewById(R.id.layout_hide), false);

		params.putAll(paramsaudit);
		params.putAll(paramsinfo);
		
		List<AttachmentBean> ss = new ArrayList<AttachmentBean>();
		ss.add(new AttachmentBean());
		ss.add(new AttachmentBean());

		params.put("reportId", logisticsReport.getReportId());
		params.put("orderId", logisticsReport.getOrderId());

		List<CostInfoBean> rcost = new ArrayList<CostInfoBean>();
		List<FreeData> fcost = fee_group_list.getListData();
		for (int i = 0; i < fcost.size(); i++) {
			CostInfoBean data = new CostInfoBean();
			data.setDescript(fcost.get(i).getBusLine());
			data.setCostFee(Double.valueOf(KingTellerJudgeUtils.isEmpty(fcost.get(i)
					.getFeeMoney()) ? "0" : fcost.get(i).getFeeMoney()));
			data.setCostDesc(fcost.get(i).getFeeModeId());
			data.setCostdescName(fcost.get(i).getFeeMode());
			data.setTypeId(fcost.get(i).getFeeTypeId());
			data.setTypeName(fcost.get(i).getFeeType());
			data.setUserId(fcost.get(i).getUserId());
			data.setUsername(fcost.get(i).getUserName());
			data.setId(fcost.get(i).getId());
			rcost.add(data);
		}

		// 初始化其他字段
		params.put("apList", ss);
		params.put("epList", rcost);

		return ConditionUtils.getJsonFromHashMap(params);
	}

	public ListViewObj getListviewObj() {
		return listviewObj;
	}

	public void setListviewObj(ListViewObj listviewObj) {
		this.listviewObj = listviewObj;
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		KingTellerApplication.removeActivity(this);
	}
}
