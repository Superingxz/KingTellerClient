package com.kingteller.client.activity.workorder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.kingteller.R;
import com.kingteller.client.KingTellerApplication;
import com.kingteller.client.activity.base.BaseActivity;
import com.kingteller.client.bean.db.Report;
import com.kingteller.client.bean.workorder.CleanReportBean;
import com.kingteller.client.bean.workorder.MachineInfoBean;
import com.kingteller.client.bean.workorder.PictureInfoBean;
import com.kingteller.client.bean.workorder.ReturnBackStatus;
import com.kingteller.client.bean.workorder.WorkOrderBean;
import com.kingteller.client.config.KingTellerUrlConfig;
import com.kingteller.client.netauth.AjaxHttpCallBack;
import com.kingteller.client.netauth.KTHttpClient;
import com.kingteller.client.utils.CommonSelcetUtils;
import com.kingteller.client.utils.ConditionUtils;
import com.kingteller.client.utils.KingTellerConfigUtils;
import com.kingteller.client.utils.KingTellerDbUtils;
import com.kingteller.client.utils.KingTellerJudgeUtils;
import com.kingteller.client.utils.KingTellerTimeUtils;
import com.kingteller.client.view.KTAlertDialog;
import com.kingteller.client.view.KingTellerEditText;
import com.kingteller.client.view.dialog.use.KingTellerProgressUtils;
import com.kingteller.client.view.toast.T;
import com.kingteller.framework.http.AjaxParams;

public class CleanReportActivity extends BaseActivity implements
		OnClickListener {

	private KingTellerEditText orderNo;
	private KingTellerEditText workDateStr;
	private KingTellerEditText flowStatusName;
	private KingTellerEditText ssbscName;
	private KingTellerEditText sszhName;
	private KingTellerEditText workerNames;
	private KingTellerEditText wdsbjc;
	private KingTellerEditText orderRemark;
	private KingTellerEditText defraudTipsFlag;
	private KingTellerEditText otherFlag;
	private KingTellerEditText lampFaultFlag;
	private KingTellerEditText reportReamrk;
	private LinearLayout layout_audit_common;
	protected KingTellerEditText auditRemark;
	protected KingTellerEditText auditContent;
	private TextView auditTitle;
	private ListView listView;
	private WorkOrderBean workBean;

	private Button btn_temp_save;
	private Button btn_submit;
	private Button btn_back;
	private Button btn_sp;
	private CleanReportBean cleanReport;
	private LinearLayout layout_hide;
	private Button hide;
	public final static String OPT_TYPE = "optType";// 类型字段
	protected int optType = 0;//如何等于则是审核类型
	public final static String OPT_STATE_TYPE = "stateType";//未处理和已处理
	private String state_type ="";
	private String isCheckout = "";
	private Long backflag;
	
	private Context mContext;

	@Override
	public void onCreate(Bundle paramBundle) {
		super.onCreate(paramBundle);
		this.setContentView(R.layout.layout_clear_report);
		KingTellerApplication.addActivity(this);
		
		mContext = CleanReportActivity.this;
		initUI();
		initData();
	}

	public void initUI() {
		
		workBean = (WorkOrderBean) this.getIntent().getExtras().getSerializable("reportBean");
		optType = getIntent().getIntExtra(OPT_TYPE, 0);
		state_type = getIntent().getStringExtra(OPT_STATE_TYPE);
		isCheckout = getIntent().getStringExtra("isCheckout");
		backflag = getIntent().getLongExtra("backflag", 0);
		
		title_right.setText("查看");
		btn_temp_save = (Button) findViewById(R.id.btn_temp_save);
		btn_submit = (Button) findViewById(R.id.btn_submit);
		btn_back = (Button) findViewById(R.id.btn_back);
		btn_sp = (Button) findViewById(R.id.btn_sp);
		
		title_left.setOnClickListener(this);
		btn_temp_save.setOnClickListener(this);
		btn_submit.setOnClickListener(this);
		btn_back.setOnClickListener(this);
		btn_sp.setOnClickListener(this);

		orderNo = (KingTellerEditText) findViewById(R.id.orderNo);
		workDateStr = (KingTellerEditText) findViewById(R.id.workDateStr);
		flowStatusName = (KingTellerEditText) findViewById(R.id.flowStatusName);
		ssbscName = (KingTellerEditText) findViewById(R.id.ssbscName);
		sszhName = (KingTellerEditText) findViewById(R.id.sszhName);
		workerNames = (KingTellerEditText) findViewById(R.id.workerNames);
		wdsbjc = (KingTellerEditText) findViewById(R.id.wdsbjc);
		orderRemark = (KingTellerEditText) findViewById(R.id.orderRemark);
		defraudTipsFlag = (KingTellerEditText) findViewById(R.id.defraudTipsFlag);
		otherFlag = (KingTellerEditText) findViewById(R.id.otherFlag);
		lampFaultFlag = (KingTellerEditText) findViewById(R.id.lampFaultFlag);
		reportReamrk = (KingTellerEditText) findViewById(R.id.reportReamrk);
		layout_audit_common = (LinearLayout) findViewById(R.id.layout_audit_common);
		auditRemark = (KingTellerEditText) findViewById(R.id.auditRemark);
		auditContent = (KingTellerEditText) findViewById(R.id.auditContent);
		auditTitle = (TextView) findViewById(R.id.auditTitle);

		listView = (ListView) findViewById(R.id.listView);
		layout_hide = (LinearLayout) findViewById(R.id.layout_hide);
		hide = (Button) findViewById(R.id.hide);
		
		title_right.setOnClickListener(this);
		hide.setOnClickListener(this);
		
		if (optType == BaseReportActivity.OPT_ADUIT) {
			// 设置信息不可写 只可以看
			LinearLayout layout_form = (LinearLayout) findViewById(R.id.layout_form);
			if (layout_form != null) {
				int length = layout_form.getChildCount();
				for (int i = 0; i < length; i++) {
					if (layout_form.getChildAt(i).getClass().getName()
							.equals(KingTellerEditText.class.getName())) {
						((KingTellerEditText) layout_form.getChildAt(i))
								.setFieldEnabled(false);
					}
				}
			}
		}
		switch (optType) {
		case BaseReportActivity.OPT_ADUIT:
			title_title.setText("清洁报告审核");
			if( state_type.equals(BaseReportActivity.OPT_PROCESSED)){
				layout_audit_common.setVisibility(View.VISIBLE);
				btn_submit.setVisibility(View.GONE);
				btn_temp_save.setVisibility(View.GONE);
			}else{
				layout_audit_common.setVisibility(View.VISIBLE);
				btn_submit.setVisibility(View.GONE);
				btn_temp_save.setVisibility(View.GONE);
			}
			break;
		default:
			if(backflag > 0){
				onlyRead();
				layout_audit_common.setVisibility(View.VISIBLE);
			}else{
				layout_audit_common.setVisibility(View.GONE);
			}
			if(KingTellerJudgeUtils.isEmpty(isCheckout)){	
			}else{
				btn_submit.setVisibility(View.GONE);
				btn_temp_save.setVisibility(View.GONE);
			}
			btn_back.setVisibility(View.GONE);
			btn_sp.setVisibility(View.GONE);
			title_title.setText("清洁报告填写");
			layout_audit_common.setVisibility(View.GONE);
			break;
		}
		
		// 加载背景不透明
		getListviewObj().setBackground(false);
		KingTellerConfigUtils.hideInputMethod(this);
	}
	
	public void onlyRead(){
		int length = layout_audit_common.getChildCount();
		for (int i = 0; i < length; i++) {
			if (layout_audit_common.getChildAt(i).getClass().getName()
					.equals(KingTellerEditText.class.getName())) {
				((KingTellerEditText) layout_audit_common.getChildAt(i))
						.setFieldEnabled(false);
			}
		}
	}

	public void initData() {
		defraudTipsFlag.setLists(CommonSelcetUtils.getSelectList(CommonSelcetUtils.radios01));
		otherFlag.setLists(CommonSelcetUtils.getSelectList(CommonSelcetUtils.radios01));
		lampFaultFlag.setLists(CommonSelcetUtils.getSelectList(CommonSelcetUtils.radios01));
		// 从数据库读取数据
		Report data = KingTellerDbUtils.getReportFromDataBase(this, workBean.getOrderId());

		if (data == null || data.getReportData() == null)
			getCleanReports();
		else {
			setDataInfo(KingTellerDbUtils.getReportDataFromString(data.getReportData(), CleanReportBean.class));
		}

	}

	private void getCleanReports() {

		if (!KingTellerJudgeUtils.isNetAvaliable(this)) {
			T.showShort(mContext, "没有网络, 请检查网络是否可用!");
			return;
		}

		KTHttpClient fh = new KTHttpClient(true);
		AjaxParams params = new AjaxParams();

		params.put("orderId", workBean.getOrderId());
		params.put("reportType", workBean.getOrderType());

		fh.post(KingTellerConfigUtils.CreateUrl(this, KingTellerUrlConfig.WebQtbgUrl),
				params, new AjaxHttpCallBack<CleanReportBean>(this, true) {

					@Override
					public void onStart() {
						KingTellerProgressUtils.showProgress(mContext, "数据加载中...");
					}
			
					@Override
					public void onFinish() {
						KingTellerProgressUtils.closeProgress();
					}

					@Override
					public void onDo(CleanReportBean data) {

						setDataInfo(data);
					};
				});
	}

	/**
	 * 设置数据
	 * 
	 * @param data
	 */
	private void setDataInfo(CleanReportBean data) {
		cleanReport = data;
		orderNo.setFieldTextAndValue(data.getOrderNo());
		workDateStr.setFieldTextAndValue(data.getWorkDateStr());
		flowStatusName.setFieldTextAndValue(data.getFlowStatusName());
		ssbscName.setFieldTextAndValue(data.getSsbscName());
		sszhName.setFieldTextAndValue(data.getSszhName());
		workerNames.setFieldTextAndValue(data.getWorkerNames());
		wdsbjc.setFieldTextAndValue(data.getWdsbjc());
		orderRemark.setFieldTextAndValue(data.getOrderRemark());
		defraudTipsFlag.setFieldTextAndValue(data.getDefraudTipsFlag()
				.toString());
		otherFlag.setFieldTextAndValue(data.getOtherFlag().toString());
		lampFaultFlag.setFieldTextAndValue(data.getLampFaultFlag().toString());
		reportReamrk.setFieldTextAndValue(data.getReportReamrk());
		
		auditTitle.setText("清洁报告意见");

		listView.setAdapter(new ListViewAdapter(this, data.getJqList()));

		getListviewObj().setVisibility(View.GONE);
	}

	/**
	 * 获取参数生成json
	 * 
	 * @return
	 */
	public String getFromData() {
		HashMap<String, Object> params = ConditionUtils.getHashMapForm(this,
				(LinearLayout) findViewById(R.id.layout_form), false);
		HashMap<String, Object> paramsHide = ConditionUtils.getHashMapForm(this, 
				(LinearLayout) findViewById(R.id.layout_hide), false);

		List<PictureInfoBean> ss = new ArrayList<PictureInfoBean>();
		ss.add(new PictureInfoBean());
		ss.add(new PictureInfoBean());
		List<MachineInfoBean> ss2 = cleanReport.getJqList();

		params.putAll(paramsHide);
		params.put("picList", ss);
		params.put("jqList", ss2);
		params.put("reportId", cleanReport.getReportId());
		params.put("orderNo", cleanReport.getOrderNo());

		return ConditionUtils.getJsonFromHashMap(params);
	}

	public boolean checkNet(boolean isSubmit) {
		if (!KingTellerJudgeUtils.isNetAvaliable(this)) {
			new KTAlertDialog.Builder(this)
					.setTitle("提醒")
					.setMessage("网络不可用，是否离线保存？")
					.setPositiveButton("确定",
							new KTAlertDialog.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialogInterface, int pos) {
									dialogInterface.dismiss();
								}
							})
					.setNegativeButton("取消",
							new KTAlertDialog.OnClickListener() {
								public void onClick(DialogInterface dialogInterface, int paramAnonymousInt) {
									dialogInterface.dismiss();
								}
							}).create().show();

			return false;
		} /*else if (!CommonUtils.isWifi(this) && !isSubmit) {
			new KTAlertDialog.Builder(this)
					.setTitle("提醒")
					.setMessage("你的网络不是WIFI网络，是否上传附件？")
					.setPositiveButton("确定",
							new KTAlertDialog.OnClickListener() {
								@Override
								public void onClick(
										DialogInterface dialogInterface, int pos) {
									finish();
								}
							})
					.setNegativeButton("取消",
							new KTAlertDialog.OnClickListener() {
								public void onClick(
										DialogInterface dialogInterface,
										int paramAnonymousInt) {
									dialogInterface.dismiss();
								}
							}).create().show();
			return false;
		}*/

		return true;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btn_sp:
			new KTAlertDialog.Builder(this)
					.setTitle("提醒")
					.setMessage("您确定要审批报告吗?")
					.setPositiveButton("确定",
							new KTAlertDialog.OnClickListener() {
								@Override
								public void onClick(
										DialogInterface dialogInterface, int pos) {
									doAuditFlow(0);
									dialogInterface.dismiss();
								}
							})
					.setNegativeButton("取消",
							new KTAlertDialog.OnClickListener() {
								public void onClick(
										DialogInterface dialogInterface,
										int paramAnonymousInt) {
									dialogInterface.dismiss();
								}
							}).create().show();

			break;
		case R.id.btn_back:
			new KTAlertDialog.Builder(this)
					.setTitle("提醒")
					.setMessage("您确定要退回该报告吗?")
					.setPositiveButton("确定",
							new KTAlertDialog.OnClickListener() {
								@Override
								public void onClick(
										DialogInterface dialogInterface, int pos) {
									doAuditFlow(1);
									dialogInterface.dismiss();
								}
							})
					.setNegativeButton("取消",
							new KTAlertDialog.OnClickListener() {
								public void onClick(
										DialogInterface dialogInterface,
										int paramAnonymousInt) {
									dialogInterface.dismiss();
								}
							}).create().show();
			break;
		case R.id.button_left:
			back();
			break;
		case R.id.btn_temp_save:
			if (checkNet(false)) {
				submitReport(1);
			}
			break;
		case R.id.btn_submit:
			if (checkNet(true)) {
				submitReport(2);
			}
			break;
		case R.id.button_right:
			layout_hide.setVisibility(View.VISIBLE);
			break;
		case R.id.hide:
			layout_hide.setVisibility(View.GONE);
			break;
		default:
			break;
		}
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		// super.onBackPressed();
		back();
	}
	
	private void back() {
		if (optType == 0) {
			String data = KingTellerDbUtils.getReportDataFromDataBase(this,
					workBean.getOrderId());
			try {
				final String fromdata = getFromData();

				if (!data.equals(fromdata))
					new KTAlertDialog.Builder(this)
							.setTitle("提醒")
							.setMessage("您的数据尚未提交，是否保存本地？")
							.setPositiveButton("保存",
									new KTAlertDialog.OnClickListener() {
										@Override
										public void onClick(DialogInterface dialogInterface, int pos) {
											KingTellerDbUtils.saveReportToDataBase(
													CleanReportActivity.this, 
													workBean.getOrderId(),
													workBean.getOrderType(),
													fromdata, 
													"",
													KingTellerTimeUtils.getNowTime(),
													0);

											dialogInterface.dismiss();
											finish();

										}
									})
							.setNegativeButton("不保存",
									new KTAlertDialog.OnClickListener() {
										public void onClick(DialogInterface dialogInterface, int paramAnonymousInt) {
											dialogInterface.dismiss();
											finish();
										}
									}).create().show();
				else
					finish();
			} catch (Exception e) {
				// TODO: handle exception
				finish();
			}

		} else
			finish();
	}
	
	/**
	 * 提交报告
	 * 
	 * @param flag
	 *            flag:1：暂存 2：提交　
	 */
	public void submitReport(final int flag) {

		final String assign = getFromData();
		KTHttpClient fh = new KTHttpClient(true);
		AjaxParams ajaxParams = new AjaxParams();
		ajaxParams.put("clean", assign);
		ajaxParams.put("flag", String.valueOf(flag));

		fh.post(KingTellerConfigUtils.CreateUrl(this, KingTellerUrlConfig.otherMatterUrl),
				ajaxParams, new AjaxHttpCallBack<ReturnBackStatus>(this, true) {

					@Override
					public void onStart() {
						KingTellerProgressUtils.showProgress(mContext, "保存数据中...");
					}
			
					@Override
					public void onFinish() {
						KingTellerProgressUtils.closeProgress();
					}
					
					@Override
					public void onError(int errorNo, String strMsg) {
						// TODO Auto-generated method stub
						KingTellerDbUtils.saveReportToDataBase(
								CleanReportActivity.this, 
								workBean.getOrderId(),
								workBean.getOrderType(),
								assign, 
								"",
								KingTellerTimeUtils.getNowTime(),
								0);

					}

					@Override
					public void onDo(ReturnBackStatus data) {
						// TODO Auto-generated method stub
						if (data.getResult().equals("success")) {
							if (flag == 1){
								T.showShort(mContext, "保存成功!");
								KingTellerDbUtils.saveReportToDataBase(
										CleanReportActivity.this, 
										workBean.getOrderId(),
										workBean.getOrderType(),
										assign, 
										"",
										KingTellerTimeUtils.getNowTime(),
										1);

							}
							else if (flag == 2) {
								KingTellerDbUtils.deleteReportFromDataBase(
										CleanReportActivity.this,
										workBean.getOrderId());
								
								T.showShort(mContext, data.getMessage());
								finish();
							}
					
						} else {
							KingTellerDbUtils.saveReportToDataBase(
									CleanReportActivity.this, 
									workBean.getOrderId(),
									workBean.getOrderType(),
									assign,
									"",
									KingTellerTimeUtils.getNowTime(),
									0);

							T.showShort(mContext, data.getMessage());
						}

					}
				});
	}

	
	/**
	 * 审批
	 * 
	 * @param flag
	 *            1退回 0审批
	 */
	private void doAuditFlow(final int flag) {

		String assign = getFromData();
		KTHttpClient fh = new KTHttpClient(true);
		AjaxParams ajaxParams = new AjaxParams();
		ajaxParams.put("clean", assign);
		ajaxParams.put("flag", String.valueOf(flag));

		fh.post(KingTellerConfigUtils.CreateUrl(this, KingTellerUrlConfig.SfthUrl), ajaxParams,
				new AjaxHttpCallBack<ReturnBackStatus>(this, true) {

					@Override
					public void onStart() {
						KingTellerProgressUtils.showProgress(mContext, "正在提交...");
					}
			
					@Override
					public void onFinish() {
						KingTellerProgressUtils.closeProgress();
					}

					@Override
					public void onDo(ReturnBackStatus data) {
						if (data.getResult().equals("success")) {
							T.showShort(mContext, data.getMessage());
							finish();
						} else
							T.showShort(mContext, data.getMessage());
					};
				});
	}

	
	class ListViewAdapter extends BaseAdapter {

		MachineInfoBean machineInfoBean;
		private LayoutInflater inflater;
		private List<MachineInfoBean> machineInfos;
		private Context context;

		public ListViewAdapter(Context context,
				List<MachineInfoBean> machineInfos) {
			this.machineInfos = machineInfos;
			this.context = context;
			inflater = LayoutInflater.from(context);
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return machineInfos.size();
		}

		@Override
		public Object getItem(int arg0) {
			// TODO Auto-generated method stub
			return machineInfos.get(arg0);
		}

		@Override
		public long getItemId(int arg0) {
			// TODO Auto-generated method stub
			return arg0;
		}

		@Override
		public View getView(int arg0, View v, ViewGroup arg2) {
			// TODO Auto-generated method stub
			machineInfoBean = machineInfos.get(arg0);
			ViewHoler viewHoler = null;
			if (v == null) {
				viewHoler = new ViewHoler();
				v = inflater.inflate(R.layout.item_machineinfo, null);
				viewHoler.jqbh = (KingTellerEditText) v.findViewById(R.id.jqbh);
				viewHoler.atmh = (KingTellerEditText) v.findViewById(R.id.atmh);
				v.setTag(viewHoler);
			} else {
				viewHoler = (ViewHoler) v.getTag();
			}
			viewHoler.jqbh.setFieldTextAndValue(machineInfoBean.getJqbh());
			viewHoler.atmh.setFieldTextAndValue(machineInfoBean.getAtmh());
			return v;
		}
	}

	static class ViewHoler {
		KingTellerEditText jqbh;
		KingTellerEditText atmh;
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		KingTellerApplication.removeActivity(this);
	}
}
