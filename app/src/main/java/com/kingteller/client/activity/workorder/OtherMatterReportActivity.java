package com.kingteller.client.activity.workorder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.kingteller.R;
import com.kingteller.client.KingTellerApplication;
import com.kingteller.client.activity.workorder.checkout.OtherMatterReportCheckoutActivity;
import com.kingteller.client.bean.db.Report;
import com.kingteller.client.bean.workorder.AttachmentBean;
import com.kingteller.client.bean.workorder.CostInfoBean;
import com.kingteller.client.bean.workorder.FreeData;
import com.kingteller.client.bean.workorder.OtherMatterReportBean;
import com.kingteller.client.bean.workorder.PickInfoBean;
import com.kingteller.client.config.KingTellerUrlConfig;
import com.kingteller.client.netauth.AjaxHttpCallBack;
import com.kingteller.client.netauth.KTHttpClient;
import com.kingteller.client.utils.CommonSelcetUtils;
import com.kingteller.client.utils.ConditionUtils;
import com.kingteller.client.utils.KingTellerConfigUtils;
import com.kingteller.client.utils.KingTellerDbUtils;
import com.kingteller.client.utils.KingTellerJudgeUtils;
import com.kingteller.client.view.FeeGroupView;
import com.kingteller.client.view.FeeRPGroupView;
import com.kingteller.client.view.KingTellerEditText;
import com.kingteller.client.view.ListViewObj;
import com.kingteller.client.view.dialog.use.KingTellerProgressUtils;
import com.kingteller.client.view.toast.T;
import com.kingteller.framework.http.AjaxParams;

/**
 * 其他事物报告
 * 
 * @author 王定波
 * 
 */
public class OtherMatterReportActivity extends BaseReportActivity {

	private OtherMatterReportBean otherMatterReport;

	private KingTellerEditText orderNo;
	private KingTellerEditText workDateStr;
	private KingTellerEditText areaName;
	private KingTellerEditText machineCode;
	private KingTellerEditText wdName;
	private KingTellerEditText bankName;
	private KingTellerEditText workOrgName;
	private KingTellerEditText recordUserName;
	private KingTellerEditText assignDateStr;
	private KingTellerEditText confirmDateStr;
	private KingTellerEditText beginDateStr;
	private KingTellerEditText endDateStr;
	private KingTellerEditText linkName;
	private KingTellerEditText linkPhone;
	private KingTellerEditText workType;
	private KingTellerEditText workAddress;
	private KingTellerEditText troubleRemark;
	private KingTellerEditText maintainRemark;
	private KingTellerEditText assignNames;
	private KingTellerEditText remark;
	private LinearLayout layout_from;
	
	private ListViewObj listviewObj;

	@Override
	public void onCreate(Bundle paramBundle) {
		super.onCreate(paramBundle);
		setContentView(R.layout.layout_other_report);
		KingTellerApplication.addActivity(this);
		
		if (findViewById(R.id.loading_view) != null) {
			setListviewObj(new ListViewObj(this));
		}
		// 加载背景不透明
		getListviewObj().setBackground(false);
		
		initUI();
		initData();
		
	}

	@Override
	public void initUI() {
		dataKey = "otherMatter";
		
		super.initUI();

		orderNo = (KingTellerEditText) findViewById(R.id.orderNo);
		workDateStr = (KingTellerEditText) findViewById(R.id.workDateStr);
		areaName = (KingTellerEditText) findViewById(R.id.areaName);
		machineCode = (KingTellerEditText) findViewById(R.id.machineCode);
		wdName = (KingTellerEditText) findViewById(R.id.wdName);
		bankName = (KingTellerEditText) findViewById(R.id.bankName);
		workOrgName = (KingTellerEditText) findViewById(R.id.workOrgName);
		recordUserName = (KingTellerEditText) findViewById(R.id.recordUserName);
		assignDateStr = (KingTellerEditText) findViewById(R.id.assignDateStr);
		confirmDateStr = (KingTellerEditText) findViewById(R.id.confirmDateStr);
		beginDateStr = (KingTellerEditText) findViewById(R.id.beginDateStr);
		endDateStr = (KingTellerEditText) findViewById(R.id.endDateStr);
		linkName = (KingTellerEditText) findViewById(R.id.linkName);
		linkPhone = (KingTellerEditText) findViewById(R.id.linkPhone);
		workType = (KingTellerEditText) findViewById(R.id.workType);
		workAddress = (KingTellerEditText) findViewById(R.id.workAddress);
		troubleRemark = (KingTellerEditText) findViewById(R.id.troubleRemark);
		maintainRemark = (KingTellerEditText) findViewById(R.id.maintainRemark);
		assignNames = (KingTellerEditText) findViewById(R.id.assignNames);
		remark = (KingTellerEditText) findViewById(R.id.remark);

		layout_from = (LinearLayout) findViewById(R.id.layout_form);

		
		
		if(KingTellerJudgeUtils.isEmpty(isCheckout)){	
			switch (optType) {
			case OPT_ADUIT:	
				title_text.setText("其他事物报告审核");
				break;
			default:
				title_text.setText("其他事物报告填写");
				break;
			}
		}else{
			title_text.setText("其他事物报告");
		}

	}

	@Override
	public void initData() {
		super.initData();
		
		workType.setLists(CommonSelcetUtils.getSelectList(CommonSelcetUtils.workTypeOtherMatter));
		
		// 从数据库读取数据
		Report data = KingTellerDbUtils.getReportFromDataBase(this,workBean.getOrderId());
		if (data == null || optType != 0 || data.getReportData() == null)
			getOtherReports();
		else {
			setDataInfo(KingTellerDbUtils.getReportDataFromString(data.getReportData(), OtherMatterReportBean.class));
		}
	}

	public void getOtherReports() {
		
		if (!KingTellerJudgeUtils.isNetAvaliable(this)) {
			T.showShort(this, "没有网络, 请检查网络是否可用!");
			return;
		}


		KTHttpClient fh = new KTHttpClient(true);
		AjaxParams params = new AjaxParams();
		params.put("orderId", workBean.getOrderId());
		params.put("reportType", workBean.getOrderType());

		fh.post(KingTellerConfigUtils.CreateUrl(this, KingTellerUrlConfig.WebQtbgUrl),
				params, new AjaxHttpCallBack<OtherMatterReportBean>(this, true) {

					@Override
					public void onStart() {
						KingTellerProgressUtils.showProgress(OtherMatterReportActivity.this, "数据加载中...");
					}
					
					@Override
					public void onFinish() {
						KingTellerProgressUtils.closeProgress();
					}

					@Override
					public void onDo(OtherMatterReportBean data) {
						setDataInfo(data);
					};
				});
	}

	/**
	 * 设置数据
	 * 
	 * @param data
	 */
	private void setDataInfo(OtherMatterReportBean data) {
		otherMatterReport = data;
		orderNo.setFieldTextAndValue(data.getOrderNo());
		workDateStr.setFieldTextAndValue(data.getWorkDateStr());
		areaName.setFieldTextAndValue(data.getAreaName());
		machineCode.setFieldTextAndValue(data.getMachineCode());
		wdName.setFieldTextAndValue(data.getWdName());
		bankName.setFieldTextAndValue(data.getBankName());
		workOrgName.setFieldTextAndValue(data.getWorkOrgName());
		recordUserName.setFieldTextAndValue(data.getRecordUserName());
		assignDateStr.setFieldTextAndValue(data.getAssignDateStr());
		confirmDateStr.setFieldTextAndValue(data.getConfirmDateStr());
		beginDateStr.setFieldTextAndValue(data.getBeginDateStr());
		endDateStr.setFieldTextAndValue(data.getEndDateStr());
		linkName.setFieldTextAndValue(data.getLinkName());
		linkPhone.setFieldTextAndValue(data.getLinkPhone());
		workType.setFieldTextAndValueFromValue(data.getWorkType());
		workAddress.setFieldTextAndValue(data.getWorkAddress());
		troubleRemark.setFieldTextAndValue(data.getTroubleRemark());
		maintainRemark.setFieldTextAndValue(data.getMaintainRemark());
		assignNames.setFieldTextAndValue(data.getAssignNames());
		remark.setFieldTextAndValue(data.getRemark());

		auditRemark.setFieldTextAndValue(data.getAuditRemark());
		auditContent.setFieldTextAndValue(data.getAuditContent());
		auditTitle.setText("其他事物报告意见");

		//维护费用信息
		fee_group_list.getLayoutList().removeAllViews();
		if (data.getEpList() != null){
			
			if(data.getEpList().size() > 0){
			findViewById(R.id.layout_whfyxx).setVisibility(View.VISIBLE);
				
				for (int i = 0; i < data.getEpList().size(); i++) {
					FeeRPGroupView fview;
					if (i == 0){
						fview = new FeeRPGroupView(OtherMatterReportActivity.this, false);
					}else{
						fview = new FeeRPGroupView(OtherMatterReportActivity.this, true);
					}
					
					FreeData fdata = new FreeData();
					fdata.setUserName(data.getEpList().get(i).getUsername());
					fdata.setUserId(data.getEpList().get(i).getUserId());
					fdata.setFeeMoney(String.valueOf(data.getEpList().get(i).getCostFee()));
					fdata.setBusLine(data.getEpList().get(i).getDescript());
					fdata.setFeeMode(data.getEpList().get(i).getCostdescName());
					fdata.setFeeModeId(data.getEpList().get(i).getCostDesc());
					fdata.setFeeTypeId(String.valueOf(data.getEpList().get(i).getTypeId()));
					fdata.setFeeType(data.getEpList().get(i).getTypeName());
					fdata.setId(data.getEpList().get(i).getId());
					fdata.setQzdd(data.getEpList().get(i).getQzdd());
					fdata.setJsdd(data.getEpList().get(i).getJsdd());
					fview.setData(fdata);
					
					fee_group_list.addItem(fview);
				}
			}else{
				findViewById(R.id.layout_whfyxx).setVisibility(View.GONE);
			}
		}
		
		getListviewObj().setVisibility(View.GONE);
	}
	
	
	/**
	 * 获取参数生成json
	 * 
	 * @return
	 */
	public String getFromData() {
		if(otherMatterReport == null){
			return "";
		}
		
		
		HashMap<String, Object> params = ConditionUtils.getHashMapForm(this,
				(LinearLayout) findViewById(R.id.layout_form), false);
		
		HashMap<String, Object> params_audit = ConditionUtils.getHashMapForm(this,
				(LinearLayout) findViewById(R.id.layout_audit_common), false);
		
		HashMap<String, Object> params_info = ConditionUtils.getHashMapForm(this,
				(LinearLayout) findViewById(R.id.layout_hide), false);
		
		HashMap<String, Object> params_bgxx = ConditionUtils.getHashMapForm(this,
				(LinearLayout) findViewById(R.id.layout_bgxx), false);
		
		params.putAll(params_audit);
		params.putAll(params_info);
		params.putAll(params_bgxx);
		
		
		// 费用类型
		List<CostInfoBean> rcost = new ArrayList<CostInfoBean>();
		List<FreeData> fcost = fee_group_list.getListData();
		for (int i = 0; i < fcost.size(); i++) {
			CostInfoBean data = new CostInfoBean();
			data.setDescript(fcost.get(i).getBusLine());
			data.setCostFee(Double.valueOf(KingTellerJudgeUtils.isEmpty(fcost.get(i).getFeeMoney())?"0":fcost.get(i).getFeeMoney()));
			data.setCostDesc(fcost.get(i).getFeeModeId());
//			data.setId(fcost.get(i).getFeeModeId());
//			data.setModeName(fcost.get(i).getFeeMode());
			data.setCostdescName(fcost.get(i).getFeeMode());
			data.setTypeId(fcost.get(i).getFeeTypeId());
			data.setTypeName(fcost.get(i).getFeeType());
			data.setUserId(fcost.get(i).getUserId());
			data.setUsername(fcost.get(i).getUserName());
			data.setId(fcost.get(i).getId());
			data.setQzdd(fcost.get(i).getQzdd());
			data.setJsdd(fcost.get(i).getJsdd());
			rcost.add(data);
		}
		params.put("epList", rcost);

		// 附件类型
		List<AttachmentBean> ss = new ArrayList<AttachmentBean>();
		ss.add(new AttachmentBean());
		ss.add(new AttachmentBean());
		params.put("apList", ss);

		// 送货信息类型
		List<PickInfoBean> ss2 = new ArrayList<PickInfoBean>();
		ss2.add(new PickInfoBean());
		ss2.add(new PickInfoBean());
		params.put("lppList", ss2);
		
		params.put("reportId", otherMatterReport.getReportId());
		params.put("orderNo", otherMatterReport.getOrderNo());
		params.put("orderId", otherMatterReport.getOrderId());

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
