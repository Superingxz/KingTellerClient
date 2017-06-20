package com.kingteller.client.activity.workorder.checkout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ExpandableListView.OnGroupCollapseListener;
import android.widget.ExpandableListView.OnGroupExpandListener;

import com.kingteller.R;
import com.kingteller.client.KingTellerApplication;
import com.kingteller.client.activity.common.CommonSelectDataActivity;
import com.kingteller.client.activity.workorder.BaseReportActivity;
import com.kingteller.client.activity.workorder.RepairReportActivity;
import com.kingteller.client.adapter.QRDotMachineReplaceExpandableAdapter;
import com.kingteller.client.adapter.TopTabAdapter;
import com.kingteller.client.bean.common.CommonSelectData;
import com.kingteller.client.bean.common.CommonSelectGZBJ;
import com.kingteller.client.bean.common.TreeBean;
import com.kingteller.client.bean.common.selectdata.ATMCodeBean;
import com.kingteller.client.bean.db.Report;
import com.kingteller.client.bean.qrcode.QRCargoScanBean;
import com.kingteller.client.bean.workorder.AtmWlReplaceMiddleParam;
import com.kingteller.client.bean.workorder.AttachmentBean;
import com.kingteller.client.bean.workorder.BJBean;
import com.kingteller.client.bean.workorder.FreeData;
import com.kingteller.client.bean.workorder.RCostInfoBean;
import com.kingteller.client.bean.workorder.RepairReportBean;
import com.kingteller.client.bean.workorder.WddzBean;
import com.kingteller.client.bean.workorder.WorkOrderBean;
import com.kingteller.client.bean.workorder.WorkTypeBean;
import com.kingteller.client.config.KingTellerStaticConfig;
import com.kingteller.client.config.KingTellerUrlConfig;
import com.kingteller.client.netauth.AjaxHttpCallBack;
import com.kingteller.client.netauth.KTHttpClient;
import com.kingteller.client.utils.CommonSelcetUtils;
import com.kingteller.client.utils.ConditionUtils;
import com.kingteller.client.utils.KingTellerConfigUtils;
import com.kingteller.client.utils.KingTellerJudgeUtils;
import com.kingteller.client.view.BjGroupView;
import com.kingteller.client.view.FeeRPGroupView;
import com.kingteller.client.view.GroupListView;
import com.kingteller.client.view.ListViewObj;
import com.kingteller.client.view.XmWorkTypeGroupView;
import com.kingteller.client.view.GroupListView.AddViewCallBack;
import com.kingteller.client.view.KingTellerEditText;
import com.kingteller.client.view.KingTellerEditText.OnChangeListener;
import com.kingteller.client.view.TabView;
import com.kingteller.client.view.TabView.OnItemTabListener;
import com.kingteller.client.view.dialog.use.KingTellerProgressUtils;
import com.kingteller.client.view.toast.T;
import com.kingteller.client.view.WorkTypeGroupView;
import com.kingteller.framework.http.AjaxParams;
import com.kingteller.framework.utils.StringUtil;

public class RepairReportCheckoutActivity extends BaseReportActivity implements
		com.kingteller.client.view.WorkTypeGroupView.OnChangeListener {

	private KingTellerEditText orderId;
	private KingTellerEditText atmhExt;
	private KingTellerEditText jqgsrusername;
	private KingTellerEditText wdlxr;
	private KingTellerEditText wdId;
	private KingTellerEditText atmTypeCode;
	private KingTellerEditText wdlxdh;
	private KingTellerEditText workOrgId;
	private KingTellerEditText wddz;
	private KingTellerEditText wdsbmc;
	private KingTellerEditText workOrderPrompt;
	private KingTellerEditText workFinishFlag;
	private KingTellerEditText isbjyy;
	private KingTellerEditText arrangeType;
	private KingTellerEditText machId;
	private KingTellerEditText atmh;
	private KingTellerEditText arriveOvertimeMin;
	private KingTellerEditText maintainOvertimeMin;
	private KingTellerEditText ssyhName;
	private KingTellerEditText askTim;
	private KingTellerEditText assignOrderTime;
	private KingTellerEditText arriveTime;
	private KingTellerEditText maintainBeginTime;
	private KingTellerEditText maintainEndTime;
	private KingTellerEditText workDate;
	private KingTellerEditText maintainOverRemark;
	private KingTellerEditText atmcVerson;
	private KingTellerEditText spVerson;
	private KingTellerEditText expand6;
	private KingTellerEditText expand7;
	private KingTellerEditText serveAssessResultCode;

	private KingTellerEditText workTypeNewMove;

	private View workTypeNewMove_line;


	private KingTellerEditText involvesSpareParts;
	private GroupListView bj_group_list;
	private KingTellerEditText skillServeNumber;
	private KingTellerEditText expand1;
	private RepairReportBean reportdata;
	private KingTellerEditText prearrangeDate;
	private KingTellerEditText arriveOverRemark;
	private GroupListView workType_group_list;
	private KingTellerEditText porderno;
	private KingTellerEditText xmflag;
	private GroupListView xm_workType_group_list;
	// private KingTellerEditText troubleHandleResult;

	private KingTellerEditText stopReason;
	private KingTellerEditText yesnotj;
	private KingTellerEditText tjdh;
	private KingTellerEditText pplb;
	
	private LinearLayout layout_info;
	private LinearLayout layout_hide;
	private LinearLayout layout_bgxx;
	private LinearLayout layout_xmbgxx;
	private LinearLayout layout_jqxx;
	private LinearLayout layout_rjbbxx;
	private LinearLayout layout_whfyxx;
	private LinearLayout layout_fjxx;
	
	private LinearLayout layout_bj;
	private LinearLayout layout_bj_info;
	private LinearLayout layout_bj_wlxx;
	
	private ExpandableListView mExpandableListView;
	private QRDotMachineReplaceExpandableAdapter mQRDotMachineReplaceAdapter;
	
	private ListViewObj listviewObj;
	
	private String[] mTwoLevelTitles = new String[]{"基本信息","维护信息"};
	
	private List<TextView> mTwoLevelTitlesTVLists;
	
	private TextView title_twolevel_textone, title_twolevel_texttwo;
	
	private int offset = 0;// 动画图片偏移量
	private int currIndex = 0;// 当前页卡编号
	private int bmpW;// 动画图片宽度
	private ImageView imageView;// 动画图片
	
	private WorkOrderBean workBean;
	
	private Context mContext;
	
	@Override
	public void onCreate(Bundle paramBundle) {
		super.onCreate(paramBundle);
		setContentView(R.layout.layout_repair_report_checkout);
		KingTellerApplication.addActivity(this);

		mContext = RepairReportCheckoutActivity.this;
		
		if (findViewById(R.id.loading_view) != null) {
			setListviewObj(new ListViewObj(this));
		}
		// 加载背景不透明
		getListviewObj().setBackground(false);
		workBean = (WorkOrderBean) this.getIntent().getExtras().getSerializable("reportBean");
		
		InitImageView();
		initUI();
		InitTextView();
		initChangeUI();
		initData();
	}
	
	/**
	 * 初始化头标
	 * 
	 */
	private void InitTextView() {
		mTwoLevelTitlesTVLists  = new ArrayList<TextView>();
		
		title_twolevel_textone = (TextView) findViewById(R.id.title_twolevel_textone);
		title_twolevel_texttwo = (TextView) findViewById(R.id.title_twolevel_texttwo);
		
		mTwoLevelTitlesTVLists.add(title_twolevel_textone);
		mTwoLevelTitlesTVLists.add(title_twolevel_texttwo);
		
		title_twolevel_textone.setText(mTwoLevelTitles[0]);
		title_twolevel_texttwo.setText(mTwoLevelTitles[1]);
		
		TwoLevelTitleOnClickListener mTwoLevelTitleOnClickListener = new TwoLevelTitleOnClickListener();// 底部按钮监听事件
		 
		title_twolevel_textone.setOnClickListener(mTwoLevelTitleOnClickListener);
		title_twolevel_texttwo.setOnClickListener(mTwoLevelTitleOnClickListener);
		
		chosenTwoLevelTVColor(0);
	}

	
	/**
	 * 头标点击监听
	 */
	private class TwoLevelTitleOnClickListener implements OnClickListener {
		public void onClick(View v) {
			int one = offset * 2 + bmpW;// 页卡1 -> 页卡2 偏移量
			Animation animation = null;

			List<WorkTypeBean> list;			
			if("xm".equals(xmflag.getFieldValue())) {
				list = xm_workType_group_list.getListData();
			}else {
				list = workType_group_list.getListData();
			}
			
			List<String> listtype = new ArrayList<String>();
			for (int j = 0; j < list.size(); j++) {
				listtype.add(list.get(j).getWorkType());
			}
			
			if("0".equals(involvesSpareParts.getFieldValue())) {
				if(v.getId() == R.id.title_twolevel_texttwo){
					T.showShort(mContext, "此报告没有 维护信息!");
				}
				bj_group_list.getLayoutList().removeAllViews();
				layout_bj.setVisibility(View.GONE);
			}else{
				switch (v.getId()) {
					case R.id.title_twolevel_textone:
						chosenTwoLevelTVColor(0);
						animation = new TranslateAnimation(one * currIndex, one * 0, 0, 0);// 显然这个比较简洁，只有一行代码。
					
						animation.setFillAfter(true);// True:图片停在动画结束位置
						animation.setDuration(300);
						imageView.startAnimation(animation);
					
						currIndex = 0;
					
						layout_info.setVisibility(View.VISIBLE);
						layout_bj.setVisibility(View.GONE);
						break;
					case R.id.title_twolevel_texttwo:
						chosenTwoLevelTVColor(1);
					
						animation = new TranslateAnimation(one * currIndex, one * 1, 0, 0);// 显然这个比较简洁，只有一行代码。	
						animation.setFillAfter(true);// True:图片停在动画结束位置
						animation.setDuration(300);
						imageView.startAnimation(animation);
					
						currIndex = 1;
					
						layout_info.setVisibility(View.GONE);
						layout_bj.setVisibility(View.VISIBLE);
						break;
				}
				//involvesSpareParts.setFieldTextAndValue("1");
			}

			
		}
	}
	
	
	@Override
	public void initUI() {
		dataKey = "assign";
		
		super.initUI();
		


		bj_group_list = (GroupListView) findViewById(R.id.bj_group_list);  //维护信息Group
		
		workType_group_list = (GroupListView) findViewById(R.id.workType_group_list);//工作类型Group
		((LinearLayout)workType_group_list.findViewById(R.id.add_workType).getParent()).setVisibility(View.GONE);
		
		xm_workType_group_list = (GroupListView) findViewById(R.id.xm_workType_group_list); //项目类型Group
		
		
		
//		// 备件
//		bj_group_list.setAddViewCallBack(new AddViewCallBack() {
//			@Override
//			public void addView(GroupListView view) {
//				view.addItem(new BjGroupView(RepairReportCheckoutActivity.this,true));
//			}
//		});
//		bj_group_list.addItem(new BjGroupView(RepairReportCheckoutActivity.this, false));

		
	
//		workType_group_list.setAddViewCallBack(new AddViewCallBack() {
//			@Override
//			public void addView(GroupListView view) {
//				WorkTypeGroupView wview = new WorkTypeGroupView(RepairReportCheckoutActivity.this, true);
//				wview.setOnChangeListener(RepairReportCheckoutActivity.this);
//				view.addItem(wview);
//			}
//		});
//		
//		
//		WorkTypeGroupView wview = new WorkTypeGroupView(RepairReportCheckoutActivity.this, true);
//		wview.setOnChangeListener(this);
//		workType_group_list.addItem(wview);
	

	

		
		//工单信息
		orderId = (KingTellerEditText) findViewById(R.id.orderId);   //工单号
		pplb = (KingTellerEditText) findViewById(R.id.pplb);//工单号
		skillServeNumber = (KingTellerEditText) findViewById(R.id.skillServeNumber);//技术服务单号
		expand1 = (KingTellerEditText) findViewById(R.id.expand1);//机器编号
		atmhExt = (KingTellerEditText) findViewById(R.id.atmhExt);//ATM号
		jqgsrusername = (KingTellerEditText) findViewById(R.id.jqgsrusername);//机器归属人
		ssyhName = (KingTellerEditText) findViewById(R.id.ssyhName);//所属银行
		wdId = (KingTellerEditText) findViewById(R.id.wdId);//网点ID
		wdlxr = (KingTellerEditText) findViewById(R.id.wdlxr);//网点联系人
		atmTypeCode = (KingTellerEditText) findViewById(R.id.atmTypeCode);//机型类别 
		wdlxdh = (KingTellerEditText) findViewById(R.id.wdlxdh);//网点联系人电话
		workOrgId = (KingTellerEditText) findViewById(R.id.workOrgId);//所属服务站
		wddz = (KingTellerEditText) findViewById(R.id.wddz);//网点地址
		wdsbmc = (KingTellerEditText) findViewById(R.id.wdsbmc);//网点设备简称
		workOrderPrompt = (KingTellerEditText) findViewById(R.id.workOrderPrompt);//派单工作提示
		askTim = (KingTellerEditText) findViewById(R.id.askTim);//知会日期时间
		assignOrderTime = (KingTellerEditText) findViewById(R.id.assignOrderTime);//派单日期时间
		arriveTime = (KingTellerEditText) findViewById(R.id.arriveTime);//到达现场时间
		maintainBeginTime = (KingTellerEditText) findViewById(R.id.maintainBeginTime);//维护开始时间
		maintainEndTime = (KingTellerEditText) findViewById(R.id.maintainEndTime);//维护结束时间
		
		//机器信息
		workTypeNewMove = (KingTellerEditText) findViewById(R.id.workTypeNewMove);//机器类型选项
		workFinishFlag = (KingTellerEditText) findViewById(R.id.workFinishFlag);//机器是否正常服务
		stopReason = (KingTellerEditText) findViewById(R.id.stopReason);//停机原因
		isbjyy = (KingTellerEditText) findViewById(R.id.isbjyy);//是否备件原因
		arrangeType = (KingTellerEditText) findViewById(R.id.arrangeType);//是否预约
		prearrangeDate = (KingTellerEditText) findViewById(R.id.prearrangeDate);//预约时间
		machId = (KingTellerEditText) findViewById(R.id.machId);//机器编号
		atmh = (KingTellerEditText) findViewById(R.id.atmh);//ATM号
		yesnotj = (KingTellerEditText) findViewById(R.id.yesnotj);//是否退机
		tjdh = (KingTellerEditText) findViewById(R.id.tjdh);//退机单号
		tjdh.setFouces(false);
		arriveOvertimeMin = (KingTellerEditText) findViewById(R.id.arriveOvertimeMin);//响应超时分钟数
		maintainOvertimeMin = (KingTellerEditText) findViewById(R.id.maintainOvertimeMin);//维护超时分钟数
		workDate = (KingTellerEditText) findViewById(R.id.workDate);//工作时间
		arriveOverRemark = (KingTellerEditText) findViewById(R.id.arriveOverRemark);//响应超时说明
		maintainOverRemark = (KingTellerEditText) findViewById(R.id.maintainOverRemark);//维护超时说明
		serveAssessResultCode = (KingTellerEditText) findViewById(R.id.serveAssessResultCode);//服务评价	
		
		//软件版本信息
		atmcVerson = (KingTellerEditText) findViewById(R.id.atmcVerson);//应用版本(ATMC)
		spVerson = (KingTellerEditText) findViewById(R.id.spVerson);//机芯SP版本(仅循环机)
		expand6 = (KingTellerEditText) findViewById(R.id.expand6);//跨平台硬件驱动版本(KTWSP)
		expand7 = (KingTellerEditText) findViewById(R.id.expand7);//BV版本(仅循环机)
		
		//项目工单信息
		porderno = (KingTellerEditText) findViewById(R.id.porderno);//项目工单号
		xmflag = (KingTellerEditText) findViewById(R.id.xmflag);//项目工单标志
		
		
		involvesSpareParts = (KingTellerEditText) findViewById(R.id.involvesSpareParts);//是否涉及故障

		mExpandableListView = (ExpandableListView) findViewById(R.id.whbg_whxx_wl_listview);//更换部件
		mQRDotMachineReplaceAdapter = new QRDotMachineReplaceExpandableAdapter(mContext, new ArrayList<CommonSelectData>(), new ArrayList<List<QRCargoScanBean>>(), 1);
		mExpandableListView.setAdapter(mQRDotMachineReplaceAdapter);
		mExpandableListView.setOnGroupCollapseListener(new OnGroupCollapseListener() {//折叠事件
			@Override
			public void onGroupCollapse(int groupPosition) {
				layout_scroll.setExpandableListViewHeight(mExpandableListView);
			}
		});
		mExpandableListView.setOnGroupExpandListener(new OnGroupExpandListener() {//展开事件
			@Override
			public void onGroupExpand(int groupPosition) {
				layout_scroll.setExpandableListViewHeight(mExpandableListView);
			}
		});
		
		layout_info = (LinearLayout) findViewById(R.id.layout_info);
		layout_hide = (LinearLayout) findViewById(R.id.layout_hide);
		layout_bgxx = (LinearLayout) findViewById(R.id.layout_bgxx);
		layout_xmbgxx = (LinearLayout) findViewById(R.id.layout_xmbgxx);
		layout_jqxx = (LinearLayout) findViewById(R.id.layout_jqxx);
		layout_rjbbxx = (LinearLayout) findViewById(R.id.layout_rjbbxx);
		layout_whfyxx = (LinearLayout) findViewById(R.id.layout_whfyxx);
		layout_fjxx = (LinearLayout) findViewById(R.id.layout_fjxx);
		
		
		layout_bj = (LinearLayout) findViewById(R.id.layout_bj);
		layout_bj_info = (LinearLayout) findViewById(R.id.layout_bj_info);
		layout_bj_wlxx = (LinearLayout) findViewById(R.id.layout_bj_wlxx);
		
		

		// 设置信息不可写 只可以看
		int count1 = layout_hide.getChildCount();
		for (int i = 0; i < count1; i++) {
			if (layout_hide.getChildAt(i).getClass().getName().equals(KingTellerEditText.class.getName())) {
				((KingTellerEditText) layout_hide.getChildAt(i)).setFieldEnabled(false);
			}
		}
		
		int count2 = layout_xmbgxx.getChildCount();
		for (int i = 0; i < count2; i++) {
			if (layout_xmbgxx.getChildAt(i).getClass().getName().equals(KingTellerEditText.class.getName())) {
				((KingTellerEditText) layout_xmbgxx.getChildAt(i)).setFieldEnabled(false);
			}
		}
		
		int count3 = layout_jqxx.getChildCount();
		for (int i = 0; i < count3; i++) {
			if (layout_jqxx.getChildAt(i).getClass().getName().equals(KingTellerEditText.class.getName())) {
				((KingTellerEditText) layout_jqxx.getChildAt(i)).setFieldEnabled(false);
			}
		}
		
		int count4 = layout_rjbbxx.getChildCount();
		for (int i = 0; i < count4; i++) {
			if (layout_rjbbxx.getChildAt(i).getClass().getName().equals(KingTellerEditText.class.getName())) {
				((KingTellerEditText) layout_rjbbxx.getChildAt(i)).setFieldEnabled(false);
			}
		}

		int count5 = layout_bj.getChildCount();
		for (int i = 0; i < count5; i++) {
			if (layout_bj.getChildAt(i).getClass().getName().equals(KingTellerEditText.class.getName())) {
				((KingTellerEditText) layout_bj.getChildAt(i)).setFieldEnabled(false);
			}
		}

		title_text.setText("维护报告");
		
		KingTellerConfigUtils.hideInputMethod(this);
	}

	@Override
	public void initData() {
		super.initData();

		workFinishFlag.setLists(CommonSelcetUtils.getSelectList(CommonSelcetUtils.workFinishFlag2));
		isbjyy.setLists(CommonSelcetUtils.getSelectList(CommonSelcetUtils.radios12));
		arrangeType.setLists(CommonSelcetUtils.getSelectList(CommonSelcetUtils.radios01));
		workTypeNewMove.setLists(CommonSelcetUtils.getSelectList(CommonSelcetUtils.workTypeNewMove));
		involvesSpareParts.setLists(CommonSelcetUtils.getSelectList(CommonSelcetUtils.radios01));
		yesnotj.setLists(CommonSelcetUtils.getSelectList(CommonSelcetUtils.radios01));
		stopReason.setLists(CommonSelcetUtils.getSelectList(CommonSelcetUtils.stopReasonType2));
		serveAssessResultCode.setLists(CommonSelcetUtils.getSelectList(CommonSelcetUtils.serveAssessResultCode));

		involvesSpareParts.setFieldTextAndValueFromValue("0");
		yesnotj.setFieldTextAndValueFromValue("0");
		
		RepairReportBean opendata = (RepairReportBean) this.getIntent().getExtras().getSerializable("mRepairReportBean");//审核报告返回
		if(opendata == null){
			getReportInfo();//获取工单中查看
		}else{
			setDataInfo(opendata);
		}

	}
//
//	@Override
//	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//		// TODO Auto-generated method stub
//		super.onActivityResult(requestCode, resultCode, data);
//		switch (requestCode) {
//		case CommonCodeConfig.SELECT_GZMS:
//			if (resultCode == RESULT_OK) {
//				bj_group_list.setItemData((CommonSelectData) data
//						.getSerializableExtra(CommonSelectDataActivity.DATA));
//
//				List<BJBean> bjList = bj_group_list.getListData();
//				for (int i = 0; i < bjList.size(); i++) {
//					if (bjList.get(i).getTroubleRemark().contains("其他")) { //其它 其他
//						((BjGroupView) bj_group_list.getLayoutList()
//								.getChildAt(i)).findViewById(R.id.liearOther)
//								.setVisibility(View.VISIBLE);
//					} else {
//						((BjGroupView) bj_group_list.getLayoutList()
//								.getChildAt(i)).findViewById(R.id.liearOther)
//								.setVisibility(View.GONE);
//					}
//				}
//			}
//			break;
//		case CommonCodeConfig.SELECT_BJMK:
//			if (resultCode == RESULT_OK) {
//				bj_group_list.setItemData((CommonSelectData) data
//						.getSerializableExtra(CommonSelectDataActivity.DATA));
//			}
//			break;
//		case CommonCodeConfig.SELECT_CLGC:
//			if (resultCode == RESULT_OK) {
//				bj_group_list.setItemData((CommonSelectData) data
//						.getSerializableExtra(CommonSelectDataActivity.DATA));
//
//				List<BJBean> bjList = bj_group_list.getListData();
//				for (int i = 0; i < bjList.size(); i++) {
//					if (!bjList.get(i).getTroubleRemark().contains("其他")) { //其它 其他
//						if (bjList.get(i).getHandleSub().contains("其他")) { 
//							((BjGroupView) bj_group_list.getLayoutList()
//									.getChildAt(i)).findViewById(
//									R.id.liearOther)
//									.setVisibility(View.VISIBLE);
//						} else {
//							((BjGroupView) bj_group_list.getLayoutList()
//									.getChildAt(i)).findViewById(
//									R.id.liearOther).setVisibility(View.GONE);
//						}
//					} else {
//						((BjGroupView) bj_group_list.getLayoutList()
//								.getChildAt(i)).findViewById(R.id.liearOther)
//								.setVisibility(View.VISIBLE);
//					}
//				}
//
//			}
//			break;
//		/*
//		 * case CommonCodeConfig.SELECT_BJMK: case CommonCodeConfig.SELECT_CLGC:
//		 * if (resultCode == RESULT_OK) { List<BJBean> bjList =
//		 * bj_group_list.getListData(); for(int i = 0; i < bjList.size(); i ++){
//		 * if(!bjList.get(i).getTroubleRemark().contains("其它")){
//		 * if(bjList.get(i).getHandleSub().contains("其它")){ ((BjGroupView)
//		 * bj_group_list
//		 * .getLayoutList().getChildAt(i)).findViewById(R.id.liearOther
//		 * ).setVisibility(View.VISIBLE); }else{ ((BjGroupView)
//		 * bj_group_list.getLayoutList
//		 * ().getChildAt(i)).findViewById(R.id.liearOther
//		 * ).setVisibility(View.GONE); } }else{ ((BjGroupView)
//		 * bj_group_list.getLayoutList
//		 * ().getChildAt(i)).findViewById(R.id.liearOther
//		 * ).setVisibility(View.VISIBLE); } }
//		 * bj_group_list.setItemData((CommonSelectData) data
//		 * .getSerializableExtra(CommonSelectDataActivity.DATA)); } break;
//		 */
//		case CommonCodeConfig.REQUEST_BJWL:
//			if (resultCode == RESULT_OK) {
//				bj_group_list.setItemDataMul(((TreeBean) data
//						.getSerializableExtra(CommonSelectDataActivity.DATA))
//						.getCommonSelectData());
//
//			}
//			break;
//		case CommonCodeConfig.SELECT_HANDLESUB:
//			if (resultCode == RESULT_OK) {
//				workType_group_list.setItemData((CommonSelectData) data
//						.getSerializableExtra(CommonSelectDataActivity.DATA));
//
//				List<WorkTypeBean> bjList = workType_group_list.getListData();
//				for (int i = 0; i < bjList.size(); i++) {
//					if (bjList.get(i).getHandleSub().contains("其他")) {  //其它 其他
//						((WorkTypeGroupView) workType_group_list
//								.getLayoutList().getChildAt(i)).findViewById(
//								R.id.remark).setVisibility(View.VISIBLE);
////						((WorkTypeGroupView) workType_group_list
////								.getLayoutList().getChildAt(i)).findViewById(
////								R.id.remark_view).setVisibility(View.VISIBLE);
//					} else {
//						((WorkTypeGroupView) workType_group_list
//								.getLayoutList().getChildAt(i)).findViewById(
//								R.id.remark).setVisibility(View.GONE);
////						((WorkTypeGroupView) workType_group_list
////								.getLayoutList().getChildAt(i)).findViewById(
////								R.id.remark_view).setVisibility(View.GONE);
//					}
//				}
//			}
//			break;
//		case CommonCodeConfig.REQUEST_GZBJ:
//			if (resultCode == RESULT_OK) {
//				/*
//				 * bj_group_list.setItemData(((TreeBean) data
//				 * .getSerializableExtra(CommonSelectDataActivity.DATA))
//				 * .getCommonSelectData());
//				 */
//				bj_group_list.setItemData((CommonSelectGZBJ) data
//						.getSerializableExtra(CommonSelectDataActivity.DATA));
//			}
//			break;
//		case CommonCodeConfig.SELECT_SERVICE:
//			if (resultCode == RESULT_OK) {
//				workOrgId.setFieldTextAndValue((CommonSelectData) data
//						.getSerializableExtra(CommonSelectDataActivity.DATA));
//			}
//			break;
//		case CommonCodeConfig.SELECT_WDDZ:
//			if (resultCode == RESULT_OK) {
//				CommonSelectData csa = (CommonSelectData) data
//						.getSerializableExtra(CommonSelectDataActivity.DATA);
//				if (csa.getObj() != null) {
//					wdlxr.setFieldTextAndValue(((WddzBean) csa.getObj())
//							.getName());
//					wdlxdh.setFieldTextAndValue(((WddzBean) csa.getObj())
//							.getPhone());
//					wdId.setFieldTextAndValue(((WddzBean) csa.getObj()).getId());
//				} else if(csa.getObj() == null &&csa.getValue() != null){
//					wdlxr.setFieldTextAndValue("");
//					wdlxdh.setFieldTextAndValue("");
//					wdId.setFieldTextAndValue(csa.getValue());
//				}else{
//					wdlxr.setFieldTextAndValue("");
//					wdlxdh.setFieldTextAndValue("");
//					wdId.setFieldTextAndValue("");
//				}
//				wddz.setFieldTextAndValue(csa);
//			}
//			break;
//		case CommonCodeConfig.SELECT_ATMCODE:
//			if (resultCode == RESULT_OK) {
//				CommonSelectData cdata = (CommonSelectData) data
//						.getSerializableExtra(CommonSelectDataActivity.DATA);
//				machId.setFieldTextAndValue(cdata);
//				ATMCodeBean adata = (ATMCodeBean) cdata.getObj();
//
//				atmh.setFieldTextAndValue(adata.getAtmh());
//				expand1.setFieldTextAndValue(adata.getJqbh());
//				atmhExt.setFieldTextAndValue(adata.getAtmh());
//				wddz.setFieldTextAndValue(adata.getWddz());
//				wdlxr.setFieldTextAndValue(adata.getWdlxr());
//				wdlxdh.setFieldTextAndValue(adata.getWdlxdh());
//				wdsbmc.setFieldTextAndValue(adata.getWdsbjc());
//				atmTypeCode.setFieldTextAndValue(adata.getJxName());
//				jqgsrusername.setFieldTextAndValue(adata.getJqgsrusername());
//
//			}
//			break;
//		default:
//			break;
//		}
//	}

	/**
	 * 设置一些动作
	 */
	private void initChangeUI() {
//		// TODO Auto-generated method stub
//
//		/*
//		 * involvesSpareParts.setOnChangeListener(new OnChangeListener() {
//		 * 
//		 * @Override public void onChanged(CommonSelectData data) { // TODO
//		 * Auto-generated method stub if (data.getValue().equals("0")) {
//		 * layout_bj_info.setVisibility(View.GONE); } else if
//		 * (data.getValue().equals("1")) {
//		 * layout_bj_info.setVisibility(View.VISIBLE); } } });
//		 */
//
//		involvesSpareParts.setOnChangeListener(new OnChangeListener() {
//
//			@Override
//			public void onChanged(CommonSelectData data) {
//				// TODO Auto-generated method stub
//				if (data.getValue().equals("0")) {
//					layout_bj_info.setVisibility(View.GONE);
//					bj_group_list.getLayoutList().removeAllViews();
//				} else if (data.getValue().equals("1")) {
//					layout_bj_info.setVisibility(View.VISIBLE);
//					if (bj_group_list.getLayoutList().getChildCount() == 0) {
//						bj_group_list.addItem(new BjGroupView(
//								RepairReportCheckoutActivity.this, false));
//					}
//				}
//			}
//		});
//
//		workFinishFlag.setOnChangeListener(new OnChangeListener() {
//
//			@Override
//			public void onChanged(CommonSelectData data) {
//				// TODO Auto-generated method stub
//				if (data.getValue().equals("1")) {
//					isbjyy.setVisibility(View.VISIBLE);
//					stopReason.setVisibility(View.VISIBLE);
//				} else {
//					isbjyy.setVisibility(View.GONE);
//					stopReason.setVisibility(View.GONE);
//				}
//			}
//		});
//
//		arrangeType.setOnChangeListener(new OnChangeListener() {
//
//			@Override
//			public void onChanged(CommonSelectData data) {
//				// TODO Auto-generated method stub
//				if (data.getValue().equals("0")) {
//					prearrangeDate.setVisibility(View.GONE);
//				} else if (data.getValue().equals("1")) {
//					prearrangeDate.setVisibility(View.VISIBLE);
//				}
//			}
//		});
	}

	private void getReportInfo() {

		if (!KingTellerJudgeUtils.isNetAvaliable(this)) {
			T.showShort(this, "没有网络, 请检查网络是否可用!");
			return;
		}

		KTHttpClient fh = new KTHttpClient(true);
		AjaxParams params = new AjaxParams();
		params.put("orderId", workBean.getOrderId());
		params.put("reportType", workBean.getOrderType());

		fh.post(KingTellerConfigUtils.CreateUrl(this, KingTellerUrlConfig.WebQtbgUrl), params,
				new AjaxHttpCallBack<RepairReportBean>(this, true) {

					@Override
					public void onStart() {
						KingTellerProgressUtils.showProgress(RepairReportCheckoutActivity.this, "数据加载中...");
					}
			
					@Override
					public void onFinish() {
						KingTellerProgressUtils.closeProgress();
					}

					@Override
					public void onDo(RepairReportBean data) {	
						setDataInfo(data);
					};
				});
	}

	/**
	 * 设置数据
	 * 
	 * @param data
	 */
	private void setDataInfo(RepairReportBean data) {
		reportdata = data;
		
		//展示旧版本    停机原因
		if ("1".equals(data.getFlowStatusFlag())) {
			stopReason.setLists(CommonSelcetUtils.getSelectList(CommonSelcetUtils.stopReasonType));
		}else {
			stopReason.setLists(CommonSelcetUtils.getSelectList(CommonSelcetUtils.stopReasonType2));
		}
		
		orderId.setFieldTextAndValue(data.getOrderNo(), data.getOrderId());
		skillServeNumber.setFieldTextAndValue(data.getSkillServeNumber());
		expand1.setFieldTextAndValue(data.getExpand1());
		atmhExt.setFieldTextAndValue(data.getAtmh());
		jqgsrusername.setFieldTextAndValue(data.getJqgsrusername());
		wdlxr.setFieldTextAndValue(data.getWdlxr());
		atmTypeCode.setFieldTextAndValue(data.getAtmTypeCode());
		wdlxdh.setFieldTextAndValue(data.getWdlxdh());
		workOrgId.setFieldTextAndValue(data.getOrgName(), data.getWorkOrgId());
		wddz.setFieldTextAndValue(data.getWddz(), data.getExpand9());
		wdsbmc.setFieldTextAndValue(data.getWdsbmc());
		workOrderPrompt.setFieldTextAndValue(data.getWorkOrderPrompt());
		if ("1".equals(data.getWorkFinishFlag())||"3".equals(data.getWorkFinishFlag())) {
			workFinishFlag.setFieldTextAndValueFromValue("4");
		}else {
			workFinishFlag.setFieldTextAndValueFromValue(data.getWorkFinishFlag());
		}
	//	isbjyy.setFieldTextAndValueFromValue(data.getIsbjyy());  //(2.2.14去掉是否备件原因)
		
		stopReason.setFieldTextAndValueFromValue(data.getStopReason());
		stopReason.setLists(CommonSelcetUtils.getSelectList(CommonSelcetUtils.stopReasonType2));
		serveAssessResultCode.setFieldTextAndValueFromValue(data.getServeAssessResultCode());
		pplb.setFieldTextAndValue(data.getPplb());
		wdId.setFieldTextAndValue(data.getExpand9());
		
		ssyhName.setFieldTextAndValue(data.getSsyhName());
		askTim.setFieldTextAndValue(data.getAskTim());
		assignOrderTime.setFieldTextAndValue(data.getAssignOrderTime());
		arriveTime.setFieldTextAndValue(data.getArriveTime());
		maintainBeginTime.setFieldTextAndValue(data.getMaintainBeginTime());
		maintainEndTime.setFieldTextAndValue(data.getMaintainEndTime());

		machId.setFieldTextAndValue(data.getExpand1());
		atmh.setFieldTextAndValue(data.getAtmh());
		workDate.setFieldTextAndValue(data.getWorkDate());
		
		atmcVerson.setFieldTextAndValue(data.getAtmcVerson());
		spVerson.setFieldTextAndValue(data.getSpVerson());
		expand6.setFieldTextAndValue(data.getExpand6());
		expand7.setFieldTextAndValue(data.getExpand7());

		involvesSpareParts.setFieldTextAndValueFromValue(data.getInvolvesSpareParts());
		workTypeNewMove.setFieldTextAndValueFromValue(data.getWorkTypeNewMove());
		
		auditRemark.setFieldTextAndValue(data.getAuditRemark());
		auditContent.setFieldTextAndValue(data.getAuditContent());
		auditTitle.setText("维护报告意见");
		
		workTypeNewMove.setFieldTextAndValueFromValue(data.getWorkTypeNewMove());
		/*if(!KingTellerJudgeUtils.isEmpty(data.getWorkTypeNewMove())){
			workTypeNewMove.setVisibility(View.VISIBLE);
		}else{
			workTypeNewMove.setVisibility(View.GONE);
		}*/
		if ("START".equals(data.getWorkTypeNewMove()) ){
			workTypeNewMove.setVisibility(View.VISIBLE);
		}else{
			workTypeNewMove.setVisibility(View.GONE);
		}
		if (!KingTellerJudgeUtils.isEmpty(data.getPorderno())) {
			porderno.setFieldTextAndValue(data.getPorderno());
		}
		if (!KingTellerJudgeUtils.isEmpty(data.getXmflag())) {
			xmflag.setFieldTextAndValue(data.getXmflag());
		}

		
		yesnotj.setFieldTextAndValueFromValue(data.getYesnotj());
		if ("1".equals(data.getYesnotj())) {
			tjdh.setVisibility(View.VISIBLE);
			tjdh.setFieldTextAndValue(data.getTjdh());
		} else {
			tjdh.setVisibility(View.GONE);
			tjdh.setFieldTextAndValue("");
		}
		
		if ("1".equals(arrangeType.getFieldValue())) {
			arrangeType.setFieldTextAndValueFromValue(data.getArrangeType());
			prearrangeDate.setFieldTextAndValue(data.getPrearrangeDate());
			prearrangeDate.setVisibility(View.VISIBLE);
		}else{
			arrangeType.setFieldTextAndValueFromValue("0");
			prearrangeDate.setVisibility(View.GONE);
		}
		

		if (KingTellerJudgeUtils.isEmpty(data.getArriveOvertimeMin()) || "0".equals(data.getArriveOvertimeMin())) {//响应超时分钟
			arriveOvertimeMin.setVisibility(View.GONE);
			arriveOverRemark.setVisibility(View.GONE);
		} else {
			arriveOvertimeMin.setFieldTextAndValue(data.getArriveOvertimeMin());
			arriveOvertimeMin.setFieldEnabled(false);
			arriveOverRemark.setFieldTextAndValue(data.getArriveOverRemark());//响应超时说明
			arriveOverRemark.setFieldEnabled(false);
		}
		
	

		if (KingTellerJudgeUtils.isEmpty(data.getMaintainOvertimeMin()) || "0".equals(data.getMaintainOvertimeMin())) {//维护超时分钟
			maintainOvertimeMin.setVisibility(View.GONE);
			maintainOverRemark.setVisibility(View.GONE);
		} else {
			maintainOvertimeMin.setFieldTextAndValue(data.getMaintainOvertimeMin());
			maintainOvertimeMin.setFieldEnabled(false);
			maintainOverRemark.setFieldTextAndValue(data.getMaintainOverRemark());//维护超时说明
			maintainOverRemark.setFieldEnabled(false);
		}
		
	
		//维护费用信息
		fee_group_list.getLayoutList().removeAllViews();
		if (data.getEpList() != null && data.getEpList().size() > 0){
			findViewById(R.id.layout_whfyxx).setVisibility(View.VISIBLE);

			for (int i = 0; i < data.getEpList().size(); i++) {
				FeeRPGroupView fview = new FeeRPGroupView(RepairReportCheckoutActivity.this, false);

				FreeData fdata = new FreeData();
				fdata.setUserName(data.getEpList().get(i).getMaintainpersonname());
				fdata.setUserId(data.getEpList().get(i).getMaintainpersonid());
				fdata.setFeeMoney(String.valueOf(data.getEpList().get(i).getTrafficefee()));
				fdata.setBusLine(data.getEpList().get(i).getArriveroute());
				fdata.setFeeModeId(data.getEpList().get(i).getExpand2());
				fdata.setFeeTypeId(String.valueOf(data.getEpList().get(i).getExpand1()));
				fdata.setFeeType(data.getEpList().get(i).getExpand3());
				fdata.setIsGoBack(data.getEpList().get(i).getExpand4());
				fdata.setFeeMode(data.getEpList().get(i).getExpand5());
				fdata.setQzdd(data.getEpList().get(i).getQzdd());
				fdata.setJsdd(data.getEpList().get(i).getJsdd());
				fview.setData(fdata);
				
				((KingTellerEditText) fview.findViewById(R.id.maintainpersonname)).setFieldEnabled(false);
				((KingTellerEditText) fview.findViewById(R.id.expand1)).setFieldEnabled(false);
				((KingTellerEditText) fview.findViewById(R.id.reportAccess)).setFieldEnabled(false);
				((KingTellerEditText) fview.findViewById(R.id.trafficefee)).setFieldEnabled(false);
				((KingTellerEditText) fview.findViewById(R.id.arriveroute)).setFieldEnabled(false);
				((KingTellerEditText) fview.findViewById(R.id.qzdd)).setFieldEnabled(false);
				((KingTellerEditText) fview.findViewById(R.id.jsdd)).setFieldEnabled(false);
				((KingTellerEditText) fview.findViewById(R.id.isGoBack)).setFieldEnabled(false);
				
				((Button) fview.findViewById(R.id.btn_reportAccess)).setVisibility(View.GONE);
				((Button) fview.findViewById(R.id.btn_delete)).setVisibility(View.GONE);
				((Button) fview.findViewById(R.id.btn_add)).setVisibility(View.GONE);
				
				fee_group_list.addItem(fview);
			}
		}else{
			findViewById(R.id.layout_whfyxx).setVisibility(View.GONE);
		}
		
		//维护信息
		bj_group_list.getLayoutList().removeAllViews();
		if (data.getMfList() != null && data.getMfList().size() > 0) {
			findViewById(R.id.layout_bj_info).setVisibility(View.VISIBLE);
			
			bj_group_list.findViewById(R.id.btn_add).setVisibility(View.GONE);
			
			for (int i = 0; i < data.getMfList().size(); i++) {
				BjGroupView bview = new BjGroupView(RepairReportCheckoutActivity.this, data.getPplb(), workBean.getJqId());
				bview.setData(data.getMfList().get(i));
				/*
				if (data.getMfList().get(i).getTroubleRemark().contains("其他")  || data.getMfList().get(i).getHandleSub().contains("其他")) {
					bview.findViewById(R.id.liearOther).setVisibility(View.VISIBLE);
				} else {
					bview.findViewById(R.id.liearOther).setVisibility(View.GONE);
				}*/
				if (data.getMfList().get(i).getTroubleRemark() != null) {
					if (data.getMfList().get(i).getExpand4().contains("其他") ) {
						bview.findViewById(R.id.liearOther_troubleRemarkId).setVisibility(View.VISIBLE);
					}else {
						bview.findViewById(R.id.liearOther_troubleRemarkId).setVisibility(View.GONE);
					}
				}
				if (data.getMfList().get(i).getHandleSub() != null) {
					if (data.getMfList().get(i).getHandleSub().contains("其他") ) {
						bview.findViewById(R.id.liearOther).setVisibility(View.VISIBLE);
					}else {
						bview.findViewById(R.id.liearOther).setVisibility(View.GONE);
					}
				}
				((KingTellerEditText) bview.findViewById(R.id.troubleType)).setFieldEnabled(false);
				((KingTellerEditText) bview.findViewById(R.id.troubleModule)).setFieldEnabled(false);
				((KingTellerEditText) bview.findViewById(R.id.troubleRemarkId)).setFieldEnabled(false);
				((KingTellerEditText) bview.findViewById(R.id.handleSubId)).setFieldEnabled(false);
				((KingTellerEditText) bview.findViewById(R.id.otherDescription)).setFieldEnabled(false);
				((KingTellerEditText) bview.findViewById(R.id.isChangeModule)).setFieldEnabled(false);
				((KingTellerEditText) bview.findViewById(R.id.troubleJqZd)).setFieldEnabled(false);
				((KingTellerEditText) bview.findViewById(R.id.troubleYjPz)).setFieldEnabled(false);
				((KingTellerEditText) bview.findViewById(R.id.changeSituation)).setFieldEnabled(false);
				((KingTellerEditText) bview.findViewById(R.id.installBjWlId)).setFieldEnabled(false);
				((KingTellerEditText) bview.findViewById(R.id.downBjWlId)).setFieldEnabled(false);
				
				((Button) bview.findViewById(R.id.btn_downBjWlId)).setVisibility(View.GONE);
				((Button) bview.findViewById(R.id.btn_installBjWlId)).setVisibility(View.GONE);
				
				bj_group_list.addItem(bview);
			}
		}else{
			findViewById(R.id.layout_bj_info).setVisibility(View.GONE);
		}

		
		//项目工单类型
		xm_workType_group_list.getLayoutList().removeAllViews();
		if (data.getPrwList() != null && data.getPrwList().size() > 0) {

			xm_workType_group_list.setVisibility(View.VISIBLE);
			findViewById(R.id.xmworkType_group_list_view).setVisibility(View.VISIBLE);
			
			if ("xm".equals(data.getXmflag())) {
				findViewById(R.id.layout_bgxx).setVisibility(View.GONE);
			}

			xm_workType_group_list.findViewById(R.id.btn_add).setVisibility(View.GONE);
			
			for (int i = 0; i < data.getPrwList().size(); i++) {
				XmWorkTypeGroupView xwtgv = new XmWorkTypeGroupView(RepairReportCheckoutActivity.this);
				xwtgv.setData(data.getPrwList().get(i));
				
				((KingTellerEditText) xwtgv.findViewById(R.id.workType)).setFieldEnabled(false);
				((KingTellerEditText) xwtgv.findViewById(R.id.costMinute)).setFieldEnabled(false);
				((KingTellerEditText) xwtgv.findViewById(R.id.handleSub)).setFieldEnabled(false);
				if(KingTellerJudgeUtils.isEmpty(((KingTellerEditText) xwtgv.findViewById(R.id.remark)).getFieldValue())) {
					((KingTellerEditText) xwtgv.findViewById(R.id.remark)).setVisibility(View.GONE);
				}
				((KingTellerEditText) xwtgv.findViewById(R.id.remark)).setFieldEnabled(false);
				xm_workType_group_list.addItem(xwtgv);
			}
		}else{
			xm_workType_group_list.setVisibility(View.GONE);
			findViewById(R.id.xmworkType_group_list_view).setVisibility(View.GONE);
		}

		//工作工单类型
		workType_group_list.getLayoutList().removeAllViews();
		if (data.getRwList() != null && data.getRwList().size() > 0) {
			
			if ("xm".equals(data.getXmflag())) {
				findViewById(R.id.layout_bgxx).setVisibility(View.GONE);
			} else {
				
				for (int i = 0; i < data.getRwList().size(); i++) {
					WorkTypeGroupView bview = new WorkTypeGroupView(RepairReportCheckoutActivity.this, false);
					
					bview.setOnChangeListener(this);
					bview.setData(data.getRwList().get(i));
					((KingTellerEditText) bview.findViewById(R.id.workType)).setFieldEnabled(false);
					((KingTellerEditText) bview.findViewById(R.id.costMinute)).setFieldEnabled(false);
					((KingTellerEditText) bview.findViewById(R.id.handleSub)).setFieldEnabled(false);
					((KingTellerEditText) bview.findViewById(R.id.troubleReasonCode)).setFieldEnabled(false);
					((KingTellerEditText) bview.findViewById(R.id.remark)).setFieldEnabled(false);
					
					((Button) bview.findViewById(R.id.btn_delete)).setVisibility(View.GONE);
					((Button) bview.findViewById(R.id.btn_add)).setVisibility(View.GONE);
					
					if (!data.getRwList().get(i).getWorkType().equals("NORMAL")) {
						((KingTellerEditText) bview.findViewById(R.id.handleSub))
							.setFieldTextAndValue(data.getRwList().get(i).getHandleSub(), data.getRwList().get(i).getHandleSubId());
					} else {
						
						((KingTellerEditText) bview.findViewById(R.id.handleSub))
							.setFieldTextAndValue("请在维护信息栏填写维护过程");
					}
		
					workType_group_list.addItem(bview);
				}
			}
		}else{
			findViewById(R.id.layout_bgxx).setVisibility(View.GONE);
		}

		
		
		//二维码信息
		KingTellerStaticConfig.QR_DOTMACHINE_REPLACE_NUM = 0;
		if(data.getpList() != null &&  data.getnList() != null){
			if (data.getpList().size() > 0 || data.getnList().size() > 0) {
				setQRDotMachineReplace(data.getnList(), data.getpList());
			}else{
				layout_bj_wlxx.setVisibility(View.GONE);
			}
		}else{
			layout_bj_wlxx.setVisibility(View.GONE);
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
				(LinearLayout) findViewById(R.id.layout_info), false);
		
		HashMap<String, Object> params_audit = ConditionUtils.getHashMapForm(
				this, (LinearLayout) findViewById(R.id.layout_audit_common),false);
		
		HashMap<String, Object> params_info = ConditionUtils.getHashMapForm(
				this, (LinearLayout) findViewById(R.id.layout_hide), false);
		
		HashMap<String, Object> params_bgxx = ConditionUtils.getHashMapForm(
				this, (LinearLayout) findViewById(R.id.layout_bgxx), false);
		
		HashMap<String, Object> params_xmbgxx = ConditionUtils.getHashMapForm(
				this, (LinearLayout) findViewById(R.id.layout_xmbgxx), false);
		
		HashMap<String, Object> params_jqxx = ConditionUtils.getHashMapForm(
				this, (LinearLayout) findViewById(R.id.layout_jqxx), false);
		
		HashMap<String, Object> params_rjbbxx = ConditionUtils.getHashMapForm(
				this, (LinearLayout) findViewById(R.id.layout_rjbbxx), false);
		
		HashMap<String, Object> params_whfyxx = ConditionUtils.getHashMapForm(
				this, (LinearLayout) findViewById(R.id.layout_whfyxx), false);
		
		HashMap<String, Object> params_fjxx = ConditionUtils.getHashMapForm(
				this, (LinearLayout) findViewById(R.id.layout_fjxx), false);
		
		HashMap<String, Object> params_bj = ConditionUtils.getHashMapForm(
				this,(LinearLayout) findViewById(R.id.layout_bj), false);
		
		//若不点击头右边的信息，自动获取右边信息
		/*
		List<WorkTypeBean> lista = workType_group_list.getListData();
		List<String> listtypea = new ArrayList<String>();
		for (int j = 0; j < lista.size(); j++) {
			listtypea.add(lista.get(j).getWorkType());
		}
		if (listtypea.contains("NORMAL")) {
			involvesSpareParts.setFieldTextAndValue("1");
		}else{
			involvesSpareParts.setFieldTextAndValue("0");
		}
		*/
		params.putAll(params_info);
		params.putAll(params_audit);
		params.putAll(params_bgxx);
		params.putAll(params_xmbgxx);
		params.putAll(params_jqxx);
		params.putAll(params_rjbbxx);
		params.putAll(params_whfyxx);
		params.putAll(params_fjxx);
		params.putAll(params_bj);
		
		
		//工作类别
		List<WorkTypeBean> wtbcost = new ArrayList<WorkTypeBean>();
		List<WorkTypeBean> wcost = workType_group_list.getListData();
		for (int i = 0; i < wcost.size(); i++) {
			WorkTypeBean data = new WorkTypeBean();
			data.setWorkType(wcost.get(i).getWorkType());
			data.setWorkTypeLike(wcost.get(i).getWorkTypeLike());
			data.setCostMinute(wcost.get(i).getCostMinute());
			if (wcost.get(i).getHandleSub().equals("请在维护信息栏填写维护过程")) {
				data.setHandleSub("");
			} else {
				data.setHandleSub(wcost.get(i).getHandleSub().trim());
			}
			data.setHandleSubId(wcost.get(i).getHandleSubId());
			data.setReason(wcost.get(i).getReason());
			data.setRemark(((KingTellerEditText) ((WorkTypeGroupView) workType_group_list.getLayoutList().getChildAt(i)).findViewById(R.id.remark)).getFieldText());
			wtbcost.add(data);
		}
		params.put("rwList", wtbcost);
		
		
		//项目工单工作类别
		List<WorkTypeBean> xmwtbcost = new ArrayList<WorkTypeBean>();
		List<WorkTypeBean> xmwcost = xm_workType_group_list.getListData();
		for (int i = 0; i < xmwcost.size(); i++) {
			WorkTypeBean data = new WorkTypeBean();
			data.setWorkType(xmwcost.get(i).getWorkType());
			data.setCostMinute(xmwcost.get(i).getCostMinute());
			data.setHandleSub(xmwcost.get(i).getHandleSub().trim());
			xmwtbcost.add(data);
		}
		params.put("prwList", xmwtbcost.size() == 0? null : xmwtbcost);
		
		
		// 费用类型
		List<RCostInfoBean> rcost = new ArrayList<RCostInfoBean>();
		List<FreeData> fcost = fee_group_list.getListData();
		for (int i = 0; i < fcost.size(); i++) {
			RCostInfoBean data = new RCostInfoBean();
			data.setArriveroute(fcost.get(i).getBusLine());
			data.setTrafficefee(KingTellerJudgeUtils.isEmpty(fcost.get(i).getFeeMoney()) ? "0" : fcost.get(i).getFeeMoney());
			data.setExpand2(fcost.get(i).getFeeModeId());
			data.setExpand5(fcost.get(i).getFeeMode());
			data.setAccessname(fcost.get(i).getFeeMode());
			data.setExpand1(fcost.get(i).getFeeTypeId());
			data.setExpand3(fcost.get(i).getFeeType());
			data.setExpand4(fcost.get(i).getIsGoBack());
			data.setQzdd(fcost.get(i).getQzdd());
			data.setJsdd(fcost.get(i).getJsdd());
			data.setMaintainpersonid(fcost.get(i).getUserId());
			data.setMaintainpersonname(fcost.get(i).getUserName());
			rcost.add(data);
		}
		params.put("epList", rcost);
		
		// 附件类型
		List<AttachmentBean> ss = new ArrayList<AttachmentBean>();
		ss.add(new AttachmentBean());
		ss.add(new AttachmentBean());
		params.put("apList", ss);
		
		
		//备件类型
		if ("1".equals(involvesSpareParts.getFieldValue())){
			params.put("mfList", bj_group_list.getListData());
		}
		
		//二维码新物料的信息
		params.put("nList", reportdata.getnList());
		
		//二维码旧物料的信息
		params.put("pList", reportdata.getpList());
		
		params.put("involvesSpareParts", involvesSpareParts.getFieldValue());
		params.put("wddz", wddz.getFieldText());
		params.put("expand9", wdId.getFieldValue());
		params.put("orderNo", orderId.getFieldText());
		params.put("orgName", workOrgId.getFieldText());
		params.put("workDate", workDate.getFieldText());
		params.put("pplb", pplb.getFieldValue());
		
		params.put("servcomp", reportdata.getServcomp());
		params.put("brandname", reportdata.getBrandname());
		params.put("sxrq", reportdata.getSxrq());
		
		params.put("machineId", reportdata.getMachineId());
		params.put("reportId", reportdata.getReportId());
		params.put("askTim", reportdata.getAskTim());
		params.put("arriveTime", reportdata.getArriveTime());
		params.put("assignOrderTime", reportdata.getAssignOrderTime());
		params.put("maintainBeginTime", reportdata.getMaintainBeginTime());
		params.put("maintainEndTime", reportdata.getMaintainEndTime());
		params.put("troubleHappenTime", reportdata.getTroubleHappenTime());
		params.put("flowStatus", reportdata.getFlowStatus());
		
		if (porderno.getFieldValue() != null) {
			params.put("porderno", porderno.getFieldValue());
		}
		if (xmflag.getFieldValue() != null) {
			params.put("xmflag", xmflag.getFieldValue());
		}
		
		Log.e("维护报告  checkout 输出 ：", ConditionUtils.getJsonFromHashMap(params));
		return ConditionUtils.getJsonFromHashMap(params);
	}

	@Override
	public void onWorkTypeChange(WorkTypeGroupView view, CommonSelectData data) {
		// TODO Auto-generated method stub

		List<WorkTypeBean> list = workType_group_list.getListData();

		List<String> listtype = new ArrayList<String>();

		for (int j = 0; j < list.size(); j++) {
			listtype.add(list.get(j).getWorkType());
		}
		if(data != null){	
			listtype.add(data.getValue());
		}

		/*if (listtype.contains("START") || listtype.contains("ENGINEERING_PROJECT")
				|| listtype.contains("DEBUG")) {
			workTypeNewMove.setVisibility(View.VISIBLE);
			workTypeNewMove_line.setVisibility(View.VISIBLE);
			workTypeNewMove.setLists(CommonSelcetUtils
					.getSelectList(CommonSelcetUtils.workTypeNewMove));
			if (KingTellerJudgeUtils.isEmpty(workTypeNewMove.getFieldValue()))
				workTypeNewMove.setFieldTextAndValueFromValue("NEW");

		} else if (listtype.contains("MOVE_MACHINE")) {
			workTypeNewMove.setVisibility(View.GONE);
			workTypeNewMove_line.setVisibility(View.GONE);
			workTypeNewMove.setLists(CommonSelcetUtils
					.getSelectList(CommonSelcetUtils.workTypeNewMove1));
			if (KingTellerJudgeUtils.isEmpty(workTypeNewMove.getFieldValue()))
				workTypeNewMove.setFieldTextAndValueFromValue("MOVE");

		}

		if (!listtype.contains("START") && !listtype.contains("MOVE_MACHINE")
				&& !listtype.contains("ENGINEERING_PROJECT")
				&& !listtype.contains("DEBUG")) {
			workTypeNewMove.setVisibility(View.GONE);
			workTypeNewMove_line.setVisibility(View.GONE);

		}*/
		if (listtype.contains("START")) {
			workTypeNewMove.setVisibility(View.VISIBLE);
			workTypeNewMove_line.setVisibility(View.VISIBLE);
			workTypeNewMove.setLists(CommonSelcetUtils
					.getSelectList(CommonSelcetUtils.workTypeNewMove));
			if (KingTellerJudgeUtils.isEmpty(workTypeNewMove.getFieldValue()))
				workTypeNewMove.setFieldTextAndValueFromValue("NEW");

		} else if (listtype.contains("MOVE_MACHINE")) {
			workTypeNewMove.setVisibility(View.GONE);
			workTypeNewMove_line.setVisibility(View.GONE);
			workTypeNewMove.setLists(CommonSelcetUtils
					.getSelectList(CommonSelcetUtils.workTypeNewMove1));
			if (KingTellerJudgeUtils.isEmpty(workTypeNewMove.getFieldValue()))
				workTypeNewMove.setFieldTextAndValueFromValue("MOVE");

		}

		if (!listtype.contains("START") ) {
			workTypeNewMove.setVisibility(View.GONE);
			workTypeNewMove_line.setVisibility(View.GONE);
		}
	}

	/**  
     * 初始化部件更换二维码信息,给大组、子列表添加数据  
     * @param mGroup  
     * @param mChildList
     */  
    private void setQRDotMachineReplace(List<AtmWlReplaceMiddleParam> newlist, List<AtmWlReplaceMiddleParam> oldlist){
    	int Jnum = 0;
    	int Xnum = 0;
    	
    	List<CommonSelectData> mDotMachineRelaceGroupList = new ArrayList<CommonSelectData>();
    	List<List<QRCargoScanBean>> mDotMachineRelaceChildList = new ArrayList<List<QRCargoScanBean>>();
    	
    	//add Child  
    	List<QRCargoScanBean> mChildJwlList = new ArrayList<QRCargoScanBean>();
    	List<QRCargoScanBean> mChildXwlList = new ArrayList<QRCargoScanBean>();
    	if(oldlist.size()>0){
    		for(int o = 0; o<oldlist.size(); o++){
    			QRCargoScanBean olddata = new QRCargoScanBean();
    			olddata.setWlName(oldlist.get(o).getWlName());
    			olddata.setNewCode(oldlist.get(o).getNewCode());
    			olddata.setWlBarCode(oldlist.get(o).getWlBarcode());
    			mChildJwlList.add(olddata);
    			Jnum ++;
    		}
    	}
    	
    	if(newlist.size() > 0){
    		for(int n = 0; n<newlist.size(); n++){
    			QRCargoScanBean newdata = new QRCargoScanBean();
    			newdata.setWlName(newlist.get(n).getWlName());
    			newdata.setNewCode(newlist.get(n).getNewCode());
    			newdata.setWlBarCode(newlist.get(n).getWlBarcode());
    			mChildXwlList.add(newdata);
    			Xnum ++;
    		}
    	}
    	
    	mDotMachineRelaceChildList.add(mChildJwlList);  
    	mDotMachineRelaceChildList.add(mChildXwlList);  
    	
    	//add Group
      	CommonSelectData mJwl = new CommonSelectData();
    	CommonSelectData mXwl = new CommonSelectData();
    	
    	mJwl.setText("旧物料"); 
		mJwl.setValue(Integer.toString(Jnum));
		mXwl.setText("新物料"); 
		mXwl.setValue(Integer.toString(Xnum));

    	mDotMachineRelaceGroupList.add(mJwl);  
    	mDotMachineRelaceGroupList.add(mXwl);
        
    	KingTellerStaticConfig.QR_DOTMACHINE_REPLACE_NUM = Jnum + Xnum;
    	mQRDotMachineReplaceAdapter.setLists(mDotMachineRelaceGroupList, mDotMachineRelaceChildList, 1);
    	layout_scroll.setExpandableListViewHeight(mExpandableListView);
    	
    }  
	
	
	public ListViewObj getListviewObj() {
		return listviewObj;
	}

	public void setListviewObj(ListViewObj listviewObj) {
		this.listviewObj = listviewObj;
	}

	/**
	 * 初始化动画，这个就是页卡滑动时，下面的横线也滑动的效果，在这里需要计算一些数据
	 */
	private void InitImageView() {
		imageView = (ImageView) findViewById(R.id.title_twolevel_imageone);
		
		bmpW = BitmapFactory.decodeResource(getResources(),R.drawable.ic_twoleveltitle_bg).getWidth();// 获取图片宽度
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		int screenW = dm.widthPixels;// 获取分辨率宽度
		offset = (screenW / 2 - bmpW) / 2;// 计算偏移量--(屏幕宽度/页卡总数-图片实际宽度)/2
													// = 偏移量
		Matrix matrix = new Matrix();
		matrix.postTranslate(offset, 0);
		imageView.setImageMatrix(matrix);// 设置动画初始位置
	}
	/**
     * 设置选中图标与字体
     */
    private void chosenTwoLevelTVColor(int arg) {
    	resetTwoLevelTVColor(2);
    	mTwoLevelTitlesTVLists.get(arg).setTextColor(getResources().getColor(R.color.twotitle_text_color_pressde));
    }
    
    /**
     * 重设所有底部按钮图片和按钮字体颜色
     */
    private void resetTwoLevelTVColor(int arg) {
        for(int i = 0; i<arg; i++){
        	mTwoLevelTitlesTVLists.get(i).setTextColor(getResources().getColor(R.color.twotitle_text_color_nomal));
        }
    }
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		KingTellerApplication.removeActivity(this);
	}
}
