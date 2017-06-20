package com.kingteller.client.activity.workorder;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnGroupCollapseListener;
import android.widget.ExpandableListView.OnGroupExpandListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.kingteller.R;
import com.kingteller.client.KingTellerApplication;
import com.kingteller.client.activity.common.CommonSelectDataActivity;
import com.kingteller.client.activity.common.CommonSelectDataSearchActivity;
import com.kingteller.client.activity.common.CommonSelectWddzSearchActivity;
import com.kingteller.client.adapter.QRDotMachineReplaceExpandableAdapter;
import com.kingteller.client.bean.common.BasePageBean;
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
import com.kingteller.client.bean.workorder.JqxxDataBean;
import com.kingteller.client.bean.workorder.JqzdDataBean;
import com.kingteller.client.bean.workorder.RCostInfoBean;
import com.kingteller.client.bean.workorder.RepairReportBean;
import com.kingteller.client.bean.workorder.ReportListBean;
import com.kingteller.client.bean.workorder.ReturnBackStatus;
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
import com.kingteller.client.utils.KingTellerDbUtils;
import com.kingteller.client.utils.KingTellerJudgeUtils;
import com.kingteller.client.utils.KingTellerTimeUtils;
import com.kingteller.client.utils.SQLiteHelper;
import com.kingteller.client.view.BjGroupView;
import com.kingteller.client.view.FeeRPGroupView;
import com.kingteller.client.view.GroupListView;
import com.kingteller.client.view.ListViewObj;
import com.kingteller.client.view.GroupListView.AddViewCallBack;
import com.kingteller.client.view.KingTellerEditText;
import com.kingteller.client.view.KingTellerEditText.OnChangeListener;
import com.kingteller.client.view.KingTellerEditText.OnDialogClickLister;
import com.kingteller.client.view.datatype.LoadingEnum;
import com.kingteller.client.view.dialog.NormalDialog;
import com.kingteller.client.view.dialog.ReportEditDialog;
import com.kingteller.client.view.dialog.ReportEditDialog.EditOnClickL;
import com.kingteller.client.view.dialog.listener.OnBtnClickL;
import com.kingteller.client.view.dialog.use.KingTellerProgressUtils;
import com.kingteller.client.view.dialog.use.KingTellerPromptDialogUtils;
import com.kingteller.client.view.toast.T;
import com.kingteller.client.view.WorkTypeGroupView;
import com.kingteller.client.view.XmWorkTypeGroupView;
import com.kingteller.framework.http.AjaxParams;

/**
 * 维护报告
 * 
 * @author 王定波
 * 
 */
public class RepairReportActivity extends BaseReportActivity implements
		com.kingteller.client.view.WorkTypeGroupView.OnChangeListener {

	private KingTellerEditText orderId;
	private KingTellerEditText reportId;
	private KingTellerEditText jqbhExt;
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
	private KingTellerEditText ssyhName;
	private KingTellerEditText askTim;
	private KingTellerEditText assignOrderTime;
	private KingTellerEditText arriveTime;
	private KingTellerEditText maintainBeginTime;
	private KingTellerEditText maintainEndTime;
	private KingTellerEditText maintainOvertimeMin;
	private KingTellerEditText workDate;
	private KingTellerEditText maintainOverRemark;
	private KingTellerEditText atmcVerson;
	private KingTellerEditText spVerson;
	private KingTellerEditText expand6;
	private KingTellerEditText expand7;
	private KingTellerEditText serveAssessResultCode;
	private KingTellerEditText workTypeNewMove;
	private KingTellerEditText involvesSpareParts;
	private KingTellerEditText skillServeNumber;
	private KingTellerEditText expand1;
	private KingTellerEditText prearrangeDate;
	private KingTellerEditText arriveOverRemark;
	private KingTellerEditText porderno;
	private KingTellerEditText xmflag;

	private KingTellerEditText stopReason;
	private KingTellerEditText yesnotj;
	private KingTellerEditText tjdh;
	private KingTellerEditText pplb;
	
	private RepairReportBean reportdata;
	private GroupListView bj_group_list;
	private GroupListView workType_group_list;
	private GroupListView xm_workType_group_list;
	
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
	
	private int isShowXysj = 0;//是否打开提示框
	
	private WorkOrderBean workBean;
	
	private Long backflag;
	
	private List<JqzdDataBean> jqzdList = new ArrayList<>();
	
	private Context mContext;
	
//	{ "故障维护：NORMAL", "软件升级：SOFT_UPGRAD", "调试机器：DEBUG", "PM：PM", "开通机器：START", 
//	  "账务处理：ACCOUNTANT", "培训：TRAINING","接机：RECEIVE_MACHINE", "移机：MOVE_MACHINE", 
//	  "退机：BACK_MACHINE", "安装机器：SETUP_MACHINE", "检查机器：CHECK_MACHINE", "其他：OTHER", 
//	  "部件加装：ADD_COMPONENT","客服报废：SCRAP_MACHINE", "硬件改造：MODIFY_COMPONENT","停机：STOP_MACHINE"};
	
	private boolean IsNeed = false;//是新机开通工作类别时候,且为移机开通的情况
	private boolean isNotNormalEmpty = false; //状态：当工作类别是开通机器  PM 客户协助  加装升级项目类 或 PM项目工单时   如果维护信息为空  就不去清空维护信息，添加默认
	
	@Override
	public void onCreate(Bundle paramBundle) {
		super.onCreate(paramBundle);
		setContentView(R.layout.layout_repair_report);
		KingTellerApplication.addActivity(this);
		
		mContext = RepairReportActivity.this;
		if (findViewById(R.id.loading_view) != null) {
			setListviewObj(new ListViewObj(RepairReportActivity.this));
		}
		// 加载背景不透明
		getListviewObj().setBackground(false);
		workBean = (WorkOrderBean) this.getIntent().getExtras().getSerializable("reportBean");
		backflag = getIntent().getLongExtra("backflag", 0);
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
			list = workType_group_list.getListData();
			for(int i = 0; i < xm_workType_group_list.getListData().size(); i++) {
				list.add((WorkTypeBean)xm_workType_group_list.getListData().get(i));
			}
			
			List<String> listtype = new ArrayList<String>();
			for (int j = 0; j < list.size(); j++) {
				listtype.add(list.get(j).getWorkType());
			}
			
				if(!listtype.contains("1")  
						&&!listtype.contains("START")
						&&!listtype.contains("PM")
						&&!listtype.contains("NORMAL")
						&&!listtype.contains("CUSTOMER_ASSISTANCE")
						&&!listtype.contains("INSTALL_UPGRAD")) {
					if(v.getId() == R.id.title_twolevel_texttwo){
						T.showShort(mContext, "只有 维护工单 开通机器  PM 客户协助  加装升级项目类 或 PM项目工单 时才能填写 故障信息!");
					}
					involvesSpareParts.setFieldTextAndValue("0");
					bj_group_list.getLayoutList().removeAllViews();
					layout_bj.setVisibility(View.GONE);
				}else {
					twoLevelTitleSelected(v);
					involvesSpareParts.setFieldTextAndValue("1");
				}
			
			
		}
	}
	
	private void twoLevelTitleSelected(View v) {
		int one = offset * 2 + bmpW;// 页卡1 -> 页卡2 偏移量
		Animation animation = null;
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
	}
	
	
	@Override
	public void initUI() {
		dataKey = "assign";
		
		super.initUI();


		/**-维护信息View-**/
		//维护信息-自定义添加删除视图view
		bj_group_list = (GroupListView) findViewById(R.id.bj_group_list);
		bj_group_list.setAddViewCallBack(new AddViewCallBack() {
			@Override
			public void addView(GroupListView view) {
				view.addItem(new BjGroupView(RepairReportActivity.this, true, workBean.getJqId()));
			}
		});
		
		//维护信息视图
		BjGroupView bjView = new BjGroupView(RepairReportActivity.this, false, workBean.getJqId());
		bjView.setOnClickListener(this);
		bj_group_list.addItem(bjView);

		
		
		/**-工作类别View-**/
		//工作类别-自定义添加删除视图view
		workType_group_list = (GroupListView) findViewById(R.id.workType_group_list);
		workType_group_list.setAddViewCallBack(new AddViewCallBack() {
			@Override
			public void addView(GroupListView view) {
				WorkTypeGroupView wview = new WorkTypeGroupView(RepairReportActivity.this, true);
				wview.setOnChangeListener(RepairReportActivity.this);
				wview.setListflag(true);
				view.addItem(wview);
			}
		});
		
		//工作类别视图
		WorkTypeGroupView wview = new WorkTypeGroupView(RepairReportActivity.this, false);//工作类别视图
		wview.setOnChangeListener(this);
		workType_group_list.addItem(wview);
		workType_group_list.findViewById(R.id.add_workType).setVisibility(View.VISIBLE);

		
		/**-项目View-**/
		xm_workType_group_list = (GroupListView) findViewById(R.id.xm_workType_group_list);

		// 初始化UI
		
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
		arrangeType.setFieldEnabled(false);
		prearrangeDate = (KingTellerEditText) findViewById(R.id.prearrangeDate);//预约时间
		machId = (KingTellerEditText) findViewById(R.id.machId);//机器编号
		atmh = (KingTellerEditText) findViewById(R.id.atmh);//ATM号
		yesnotj = (KingTellerEditText) findViewById(R.id.yesnotj);//是否退机
		yesnotj.setFieldEnabled(false);
		tjdh = (KingTellerEditText) findViewById(R.id.tjdh);//退机单号
		tjdh.setFieldEnabled(false);
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


		if (optType == BaseReportActivity.OPT_ADUIT) {
			// 设置信息不可写 只可以看
			int count1 = layout_info.getChildCount();
			for (int i = 0; i < count1; i++) {
				if (layout_info.getChildAt(i).getClass().getName().equals(KingTellerEditText.class.getName())) {
					((KingTellerEditText) layout_info.getChildAt(i)).setFieldEnabled(false);
				}
			}

			int count2 = layout_bj.getChildCount();
			for (int i = 0; i < count2; i++) {
				if (layout_bj.getChildAt(i).getClass().getName().equals(KingTellerEditText.class.getName())) {
					((KingTellerEditText) layout_bj.getChildAt(i)).setFieldEnabled(false);
				}
			}
		}

		if (KingTellerJudgeUtils.isEmpty(isCheckout)) {
			switch (optType) {
			case OPT_ADUIT:
				title_text.setText("维护报告审核");
				break;
			default:
				title_text.setText("维护报告填写");
				break;
			}
		} else {
			title_text.setText("维护报告");
		}

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

		// 从数据库读取数据
		Report data = KingTellerDbUtils.getReportFromDataBase(this, workBean.getOrderId());
		if (data == null || optType != 0 || data.getReportData() == null){
			getReportInfo();
		} else {
			setDataInfo(KingTellerDbUtils.getReportDataFromString(data.getReportData(), RepairReportBean.class));
		}	

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
		case KingTellerStaticConfig.SELECT_GZMS: //故障描述
			if (resultCode == RESULT_OK) {
				bj_group_list.setItemData((CommonSelectGZBJ) data
						.getSerializableExtra(CommonSelectDataActivity.DATA));
				List<BJBean> bjList = bj_group_list.getListData();
				for (int i = 0; i < bjList.size(); i++) {
					/*if (bjList.get(i).getTroubleRemark() != null && bjList.get(i).getTroubleRemark().contains("其他")) {  //其它 其他
						((BjGroupView) bj_group_list.getLayoutList()
								.getChildAt(i)).findViewById(R.id.liearOther)
								.setVisibility(View.VISIBLE);
					} else {
						((BjGroupView) bj_group_list.getLayoutList()
								.getChildAt(i)).findViewById(R.id.liearOther)
								.setVisibility(View.GONE);
					}*/
					if (bjList.get(i).getTroubleRemark() != null && bjList.get(i).getTroubleRemark().contains("其他")) {  //其它 其他
						((BjGroupView) bj_group_list.getLayoutList()
								.getChildAt(i)).findViewById(R.id.liearOther_troubleRemarkId)
								.setVisibility(View.VISIBLE);
					} else {
						((BjGroupView) bj_group_list.getLayoutList()
								.getChildAt(i)).findViewById(R.id.liearOther_troubleRemarkId)
								.setVisibility(View.GONE);
					}
				}
				setChangeUIRwxx();
			}
			break;
		case KingTellerStaticConfig.SELECT_BJMK: //故障类别?
			if (resultCode == RESULT_OK) {
				bj_group_list.setItemData((CommonSelectData) data.getSerializableExtra(CommonSelectDataActivity.DATA));
				setChangeUI();
			}
			break;
		case KingTellerStaticConfig.SELECT_CLGC: //处理过程
			if (resultCode == RESULT_OK) {
				bj_group_list.setItemData((CommonSelectData) data.getSerializableExtra(CommonSelectDataActivity.DATA));

				List<BJBean> bjList = bj_group_list.getListData();
				LinearLayout bj = (LinearLayout) ((KingTellerEditText) bj_group_list
						.getLayoutList().getTag()).getParent();
				/*
				if(((KingTellerEditText)bj.findViewById(R.id.troubleRemarkId)).getFieldText() != null 
						&& !((KingTellerEditText)bj.findViewById(R.id.troubleRemarkId)).getFieldText().contains("其他")) { //其它 其他
					if (((KingTellerEditText)bj.findViewById(R.id.handleSubId)).getFieldText() != null 
							&& ((KingTellerEditText)bj.findViewById(R.id.handleSubId)).getFieldText().contains("其他")) {
						bj.findViewById(R.id.liearOther).setVisibility(View.VISIBLE);
					} else {
						bj.findViewById(R.id.liearOther).setVisibility(View.GONE);
					}
				 }else {
					bj.findViewById(R.id.liearOther).setVisibility(View.VISIBLE);
				 }*/
				
				if (((KingTellerEditText)bj.findViewById(R.id.handleSubId)).getFieldText() != null 
						&& ((KingTellerEditText)bj.findViewById(R.id.handleSubId)).getFieldText().contains("其他")) {
					bj.findViewById(R.id.liearOther).setVisibility(View.VISIBLE);
				} else {
					bj.findViewById(R.id.liearOther).setVisibility(View.GONE);
				}
				
				 if(((KingTellerEditText)bj.findViewById(R.id.handleSubId)).getFieldText() != null 
						 && ((KingTellerEditText)bj.findViewById(R.id.handleSubId)).getFieldText().contains("更换")){
					 KingTellerEditText isChangeModule = (KingTellerEditText) bj.findViewById(R.id.isChangeModule);
					 
					 isChangeModule.setFieldTextAndValueFromValue("0");
					 isChangeModule.setFieldTextAndValueFromValue("1");				 
				}
				/*
				for (int i = 0; i < bjList.size(); i++) {
					 if(bjList.get(i).getTroubleRemark() != null && !bjList.get(i).getTroubleRemark().contains("其他")) { //其它 其他
						if (bjList.get(i).getHandleSub() != null && bjList.get(i).getHandleSub().contains("其他")) {
							((BjGroupView) bj_group_list.getLayoutList().getChildAt(i)).findViewById(R.id.liearOther).setVisibility(View.VISIBLE);
						} else {
							((BjGroupView) bj_group_list.getLayoutList().getChildAt(i)).findViewById(R.id.liearOther).setVisibility(View.GONE);
						}
					 }else {
						((BjGroupView) bj_group_list.getLayoutList().getChildAt(i)).findViewById(R.id.liearOther).setVisibility(View.VISIBLE);
					 }
					 
					 if(bjList.get(i).getHandleSub() != null && bjList.get(i).getHandleSub().contains("更换")){
						 KingTellerEditText isChangeModule = (KingTellerEditText) ((BjGroupView) bj_group_list.getLayoutList().getChildAt(i)).findViewById(R.id.isChangeModule);
						 //KingTellerEditText installBjWlId = (KingTellerEditText)((BjGroupView) bj_group_list.getLayoutList().getChildAt(i)).findViewById(R.id.installBjWlId);
						 //KingTellerEditText downBjWlId = (KingTellerEditText)((BjGroupView) bj_group_list.getLayoutList().getChildAt(i)).findViewById(R.id.downBjWlId);
						 
						 isChangeModule.setFieldTextAndValueFromValue("0");
						 isChangeModule.setFieldTextAndValueFromValue("1");
						 //installBjWlId.setFieldEnabled(true);
						 //installBjWlId.setFieldTextAndValue("");
						 //downBjWlId.setFieldEnabled(true);
						 //downBjWlId.setFieldTextAndValue("");
					}

				}
				*/
				setChangeUIClgc();
			}
			break;
		case KingTellerStaticConfig.REQUEST_BJWL: //部件物料
			if (resultCode == RESULT_OK) {
				bj_group_list.setItemData(((TreeBean) data.getSerializableExtra(CommonSelectDataActivity.DATA)).getCommonSelectData());
			}
			break;
		case KingTellerStaticConfig.SELECT_HANDLESUB: 
			if (resultCode == RESULT_OK) {
				workType_group_list.setItemData((CommonSelectData) data.getSerializableExtra(CommonSelectDataActivity.DATA));
				List<WorkTypeBean> bjList = workType_group_list.getListData();
				for (int i = 0; i < bjList.size(); i++) {
					if (bjList.get(i).getHandleSub().contains("其他")) {  //其它 其他
						((WorkTypeGroupView) workType_group_list.getLayoutList().getChildAt(i)).findViewById(R.id.remark).setVisibility(View.VISIBLE);

					} else {
						((WorkTypeGroupView) workType_group_list.getLayoutList().getChildAt(i)).findViewById(R.id.remark).setVisibility(View.GONE);

					}
				}
			}
			break;
		case KingTellerStaticConfig.SELECT_HANDLESUB_XM: 
			if (resultCode == RESULT_OK) {
				xm_workType_group_list.setItemData((CommonSelectData) data.getSerializableExtra(CommonSelectDataActivity.DATA));
				
				List<WorkTypeBean> bjList = xm_workType_group_list.getListData();
				for (int i = 0; i < bjList.size(); i++) {
					if (bjList.get(i).getHandleSub().contains("其他")) {  //其它 其他
						((XmWorkTypeGroupView) xm_workType_group_list.getLayoutList().getChildAt(i)).findViewById(R.id.remark).setVisibility(View.VISIBLE);

					} else {
						((XmWorkTypeGroupView) xm_workType_group_list.getLayoutList().getChildAt(i)).findViewById(R.id.remark).setVisibility(View.GONE);

					}
				}
				
			}
			break;
		case KingTellerStaticConfig.REQUEST_GZBJ: //故障部件
			if (resultCode == RESULT_OK) {
				CommonSelectGZBJ commonSelectGZBJData = (CommonSelectGZBJ) data.getSerializableExtra(CommonSelectDataActivity.DATA);
				bj_group_list.setItemData(commonSelectGZBJData);
				setChangeUIGzbj();
				//TODO 更新 硬件配置 时，把下面注释
				
				setTypeId(commonSelectGZBJData.getType_id());
				getYJPZ(commonSelectGZBJData.getValue());
				
			}
			break;
		case KingTellerStaticConfig.SELECT_SERVICE:
			if (resultCode == RESULT_OK) {
				workOrgId.setFieldTextAndValue((CommonSelectData) data.getSerializableExtra(CommonSelectDataActivity.DATA));
			}
			break;
		case KingTellerStaticConfig.SELECT_WDDZ:
			if (resultCode == RESULT_OK) {
				CommonSelectData csa = (CommonSelectData) data.getSerializableExtra(CommonSelectDataActivity.DATA);
				if (csa.getObj() != null) {
					wdlxr.setFieldTextAndValue(((WddzBean) csa.getObj()).getName());
					wdlxdh.setFieldTextAndValue(((WddzBean) csa.getObj()).getPhone());
					wdId.setFieldTextAndValue(((WddzBean) csa.getObj()).getId());
				} else if(csa.getObj() == null &&csa.getValue() != null){
					wdlxr.setFieldTextAndValue("");
					wdlxdh.setFieldTextAndValue("");
					wdId.setFieldTextAndValue(csa.getValue());
				}else{
					wdlxr.setFieldTextAndValue("");
					wdlxdh.setFieldTextAndValue("");
					wdId.setFieldTextAndValue("");
				}
				wddz.setFieldTextAndValue(csa);
			}
			break;
		case KingTellerStaticConfig.SELECT_ATMCODE:
			if (resultCode == RESULT_OK) {
				CommonSelectData cdata = (CommonSelectData) data.getSerializableExtra(CommonSelectDataActivity.DATA);
				machId.setFieldTextAndValue(cdata);
				ATMCodeBean adata = (ATMCodeBean) cdata.getObj();

				atmh.setFieldTextAndValue(adata.getAtmh());
				expand1.setFieldTextAndValue(adata.getJqbh());
				atmhExt.setFieldTextAndValue(adata.getAtmh());
				wddz.setFieldTextAndValue(adata.getWddz());
				wdlxr.setFieldTextAndValue(adata.getWdlxr());
				wdlxdh.setFieldTextAndValue(adata.getWdlxdh());
				wdsbmc.setFieldTextAndValue(adata.getWdsbjc());
				atmTypeCode.setFieldTextAndValue(adata.getJxName());
				jqgsrusername.setFieldTextAndValue(adata.getJqgsrusername());

			}
			break;
		case KingTellerStaticConfig.REQUEST_JQZD: //机器字段
			if (resultCode == RESULT_OK) {
				JqzdDataBean jqzdData = (JqzdDataBean) data.getSerializableExtra(CommonSelectDataActivity.DATA);
				jqzdList.add(jqzdData);
				
				LinearLayout bj = (LinearLayout) ((KingTellerEditText) bj_group_list
						.getLayoutList().getTag()).getParent();
				setChangeUIJqzd(jqzdData, bj);
			}
			break;
		default:
			break;
		}
	}
	
	private void setTypeId(String type_id) {
		LinearLayout bj = (LinearLayout) ((KingTellerEditText) bj_group_list
				.getLayoutList().getTag()).getParent();
		((TextView) bj.findViewById(R.id.bj_type_id)).setText(type_id);
	}
	/**
	 * 当选择 故障部件 或更改 是否更换备件 为是时，获取硬件配置相关信息
	 */
	private void getYJPZ(final String wlId) {
		final LinearLayout bj = (LinearLayout) ((KingTellerEditText) bj_group_list
				.getLayoutList().getTag()).getParent();
		final KingTellerEditText troubleYjPz = ((KingTellerEditText) bj.findViewById(R.id.troubleYjPz));
		final KingTellerEditText changeSituation = ((KingTellerEditText) bj.findViewById(R.id.changeSituation));
		
		if("1".equals(((KingTellerEditText) bj.findViewById(R.id.isChangeModule)).getFieldValue())) {
			KTHttpClient fh = new KTHttpClient(true);
			AjaxParams params = new AjaxParams();
			
			Log.e("jqid+wlid", "jqId:"+workBean.getJqId()+",  wlId:"+wlId);
			params.put("jqid", workBean.getJqId());
			params.put("wlid", wlId);
			
			fh.post(KingTellerConfigUtils.CreateUrl(this, KingTellerUrlConfig.WebYjpzUrl), params, new AjaxHttpCallBack<BasePageBean<JqzdDataBean>>(this,
					new TypeToken<BasePageBean<JqzdDataBean>>() {
					}.getType(), true) {
	
	
				@Override
				public void onFinish() {
					
				}
				
				@Override
				public void onDo(BasePageBean<JqzdDataBean> basePageBean) {
					//后台返回数据与Bean类型不一致时跳到onError方法中去
					Log.e("onDo", basePageBean.toString());
					List<JqzdDataBean> listJqzdData = basePageBean.getList();
					
					if(listJqzdData == null) {
						troubleYjPz.setFieldTextAndValue("", "");
						troubleYjPz.setFieldEnabled(false);
						changeSituation.setFieldTextAndValueFromValue("2");
					}else {
						if(listJqzdData.size() == 1){
							setChangeUIJqzd(listJqzdData.get(0), bj);
							troubleYjPz.setFieldEnabled(false);
						}else {
							troubleYjPz.setFieldEnabled(true);
						}
					}
				};
			});
		}
		
	}
	
	private void setYJPZ() {
		
	}

	/**
	 * 设置一些动作
	 */
	private void initChangeUI() {
		//是否涉及故障
		involvesSpareParts.setOnChangeListener(new OnChangeListener() {
			@Override
			public void onChanged(CommonSelectData data) {
				if ("0".equals(data.getValue())) {
					layout_bj_info.setVisibility(View.GONE);
					bj_group_list.getLayoutList().removeAllViews();
					if (isNotNormalEmpty) {//添加空的维护信息表单控件
						layout_bj_info.setVisibility(View.VISIBLE);
						BjGroupView bjView = new BjGroupView(RepairReportActivity.this, false, workBean.getJqId());
						bjView.setOnClickListener(RepairReportActivity.this);
						bj_group_list.addItem(bjView);
					}
				} else if ("1".equals(data.getValue())) {
					layout_bj_info.setVisibility(View.VISIBLE);
					if (bj_group_list.getLayoutList().getChildCount() == 0) {
						bj_group_list.addItem(new BjGroupView(RepairReportActivity.this, false, workBean.getJqId()));
					}
				}
			}
		});
		
		
		//是否预约
		arrangeType.setOnChangeListener(new OnChangeListener() {
			@Override
			public void onChanged(CommonSelectData data) {
				if ("0".equals(data.getValue())) {
					prearrangeDate.setVisibility(View.GONE);
				} else if ("1".equals(data.getValue())) {
					prearrangeDate.setVisibility(View.VISIBLE);
				}
			}
		});
		
		//是否退机
		yesnotj.setOnChangeListener(new OnChangeListener() {
			@Override
			public void onChanged(CommonSelectData data) {
				if ("1".equals(data.getValue())) {
					tjdh.setVisibility(View.VISIBLE);
					getsftj();
				} else {
					tjdh.setVisibility(View.GONE);
					tjdh.setFieldTextAndValue("");
				}
			}
		});

		//所属服务站
		workOrgId.setOnDialogClickLister(new OnDialogClickLister() {
			@Override
			public void OnDialogClick() {
				Intent intent = new Intent(RepairReportActivity.this, CommonSelectDataSearchActivity.class);
				intent.putExtra(CommonSelectDataActivity.TYPE, R.array.serverState);
				startActivityForResult(intent, KingTellerStaticConfig.SELECT_SERVICE);
			}
		});

		//网点地址
		wddz.setOnDialogClickLister(new OnDialogClickLister() {
			@Override
			public void OnDialogClick() {
				Intent intent = new Intent(RepairReportActivity.this, CommonSelectWddzSearchActivity.class);
				intent.putExtra(CommonSelectDataActivity.TYPE, R.array.wddz);
				startActivityForResult(intent, KingTellerStaticConfig.SELECT_WDDZ);
			}
		});

		//机器编号
		machId.setOnDialogClickLister(new OnDialogClickLister() {
			@Override
			public void OnDialogClick() {
				Intent intent = new Intent(RepairReportActivity.this, CommonSelectDataSearchActivity.class);
				intent.putExtra(CommonSelectDataActivity.TYPE, R.array.atmcode);
				startActivityForResult(intent, KingTellerStaticConfig.SELECT_ATMCODE);
			}
		});
		
		//机器类型选项
		workTypeNewMove.setOnChangeListener(new OnChangeListener() {
			@Override
			public void onChanged(CommonSelectData data) {
				
				List<WorkTypeBean> list = workType_group_list.getListData();
				final List<String> listtype = new ArrayList<String>();
				
				for (int j = 0; j < list.size(); j++) {
					listtype.add(list.get(j).getWorkType());
				}
				
				/**
				 * 工作类别为 接机 时， (a) 机器类型选项  选择的是  移机开通，机器是否正常服务默认为  停机，停机原因默认为   移机停机
		         *              (b) 机器类型选项  选择的是  新机开通，机器是否正常服务默认为  未上线，停机原因默认为   未上线停机
				 */
				/*if(listtype.contains("RECEIVE_MACHINE")){
					if("MOVE".equals(data.getValue())){//移机开通
						workTypeNewMove.setVisibility(View.VISIBLE);
						workTypeNewMove.setFieldTextAndValueFromValue("MOVE");
						workFinishFlag.setFieldTextAndValueFromValue("1");
						stopReason.setFieldTextAndValueFromValue("MOVE_REASON");
					}else if("NEW".equals(data.getValue())){//新机开通
						workTypeNewMove.setVisibility(View.VISIBLE);
						workTypeNewMove.setFieldTextAndValueFromValue("NEW");
						workFinishFlag.setFieldTextAndValueFromValue("3");
						stopReason.setFieldTextAndValueFromValue("NOTLINE_REASON");
					}
				}*/
				
				/*if(listtype.contains("START")){
					*//**
					 * 工作类别为 开通机器  且  上线日期的机器  时, 这'机器类型' 不能选择  '新机开通' 
					 *//*
					if("NEW".equals(data.getValue()) && !KingTellerJudgeUtils.isEmpty(reportdata.getSxrq())){//新机开通
						workTypeNewMove.setFieldTextAndValueFromValue("MOVE");
					//	T.showShort(mContext, "请注意:此机器已有上线日期, 机器类型选项必须选择移机开通!");
					}else if("MOVE".equals(data.getValue())&& !KingTellerJudgeUtils.isEmpty(reportdata.getSxrq())){
						workTypeNewMove.setFieldTextAndValueFromValue("NEW");
					//	T.showShort(mContext, "请注意:此机器没有上线日期, 机器类型选项必须是新机开通!");
					}
				}*/
				
				/**
				 * 工作类别为 开通机器 或 调试机器	且 含有 维护故障  时, 当'机器类型' 选项选择了  '新机开通' 时;   (2.2.14: 工作类别含有维护故障时  不能和退机、客服报废、移机、开通机器共存   去除了本次故障原因的  新机原因)
				 */
				/*if(listtype.contains("START") || listtype.contains("DEBUG") && listtype.contains("NORMAL")){
					KingTellerEditText troubleReasonCode_1 = null;
					for(int i = 0; i < list.size(); i++ ){
						if("NORMAL".equals(list.get(i).getWorkType())){
							troubleReasonCode_1 = (KingTellerEditText) ((WorkTypeGroupView) workType_group_list.getLayoutList().getChildAt(i)).findViewById(R.id.troubleReasonCode);
							if("NEW".equals(data.getValue())){//新机开通	
								troubleReasonCode_1.setFieldTextAndValueFromValue("4");
								troubleReasonCode_1.setFieldEnabled(false);
							}else{
								troubleReasonCode_1.setFieldTextAndValue("");
								troubleReasonCode_1.setFieldEnabled(true);
								troubleReasonCode_1.setFieldRequested(true);
							}
						}
					}
				}*/
			}
		});
				
		//机器是否正常服务
		workFinishFlag.setOnChangeListener(new OnChangeListener() {
			private NormalDialog dialog;

			@Override
			public void onChanged(CommonSelectData data) {
				
				List<WorkTypeBean> list = workType_group_list.getListData();
				final List<String> listtype = new ArrayList<String>();
				
				for (int j = 0; j < list.size(); j++) {
					listtype.add(list.get(j).getWorkType());
				}
				
				if ("0".equals(data.getValue())) {//正常对外 
					
					//工作类 别为 安装机器、接机、退机、客服报废  单独存在时 时    机器是否正常对外 设置 不能选择正常对外
					if(listtype.size() == 1){
						if(listtype.contains("ENGINEERING_PROJECT") || listtype.contains("BACK_MACHINE") || listtype.contains("SCRAP_MACHINE") ){
							T.showShort(mContext, "机器是否正常服务不能再选择："+ data.getText());
							workFinishFlag.setFieldTextAndValueFromValue("99");
							return;
						}
					}
					
					//上线日期为空 时    机器是否正常对外 设置 不能选择正常对外   除 开通机器 类别 外      (现在不能选择所以不用判断了)
				/*	if(KingTellerJudgeUtils.isEmpty(reportdata.getSxrq()) && !listtype.contains("START")) {
						T.showShort(mContext, "请注意:机器没有 上线日期, 工作类别不是选 开通机器 时, 机器是否正常对外 只能选择 暂停服务!");
						workFinishFlag.setFieldTextAndValueFromValue("99");
						return;
					}*/
					
					isbjyy.setVisibility(View.GONE);
					isbjyy.setFieldTextAndValue("");
					stopReason.setVisibility(View.GONE);
					stopReason.setFieldTextAndValue("");
				}/*else if("1".equals(data.getValue())){//停机
					isbjyy.setVisibility(View.VISIBLE);
					stopReason.setVisibility(View.VISIBLE);
				}else if("3".equals(data.getValue())){//未上线停机
					if("御银客服".equals(reportdata.getServcomp())){
						if("1".equals(reportdata.getMachineStatus()) || "2".equals(reportdata.getMachineStatus())){
							T.showShort(mContext, "机器状态为:正常使用 或 停机, 机器是否正常服务 不能选择  未上线停机, 请重新选择!");
							workFinishFlag.setFieldTextAndValueFromValue("99");
							return;
						}
					}
					
					isbjyy.setVisibility(View.VISIBLE);
					stopReason.setVisibility(View.VISIBLE);
				}*/
				
				else if ("2".equals(data.getValue())) {
					if (listtype.contains("CUSTOMER_ASSISTANCE") && listtype.size() == 1) {
						dialog = new NormalDialog(mContext);
						KingTellerPromptDialogUtils.showTwoPromptDialog(dialog, "只有客户协助工作类别，不能选择机器带故障运行！",
								new OnBtnClickL() {
									@Override
									public void onBtnClick() {
										dialog.dismiss();
									}
			                    },new OnBtnClickL() {
				                    @Override
				                    public void onBtnClick() {
				                    	dialog.dismiss();
				                    }
			                });
						workFinishFlag.setFieldTextAndValueFromValue("99");
						return;
					}
					isbjyy.setVisibility(View.GONE);
					isbjyy.setFieldTextAndValue("");
					stopReason.setVisibility(View.GONE);
					stopReason.setFieldTextAndValue("");
				}
				
				//如果机器是否正常服务选择   暂停服务      1.如果工作类别包含客户协助  停机原因默认银行原因 2.如果工作类别包含 故障维护    PM  加装升级项目类     停机原因默认 技术原因
				else if("4".equals(data.getValue())){
					//工作报告有退回过，工作类别选择调试机器，机器是否正常服务可以选择除暂停服务的状态
					/*if (backflag > 0 && listtype.contains("DEBUG")) {
						dialog = new NormalDialog(mContext);
						KingTellerPromptDialogUtils.showOnePromptDialog(dialog, "工作报告有退回过，工作类别选择调试机器，机器是否正常服务可以选择除暂停服务的状态！",
								new OnBtnClickL() {
									@Override
									public void onBtnClick() {
										dialog.dismiss();
									}
			                    });
						workFinishFlag.setFieldTextAndValueFromValue("99");
						return;
					}*/
					
					if (listtype.contains("PM") || listtype.contains("NORMAL") || listtype.contains("INSTALL_UPGRAD")) {
				//		isbjyy.setVisibility(View.VISIBLE);   (2.2.14去掉是否备件原因)
						stopReason.setVisibility(View.VISIBLE);
						stopReason.setFieldTextAndValueFromValue("TECHNOLOGY_REASON");
					}else if (listtype.contains("CUSTOMER_ASSISTANCE") ) {
				//		isbjyy.setVisibility(View.VISIBLE);		(2.2.14去掉是否备件原因)
						stopReason.setVisibility(View.VISIBLE);
						stopReason.setFieldTextAndValueFromValue("BANK_REASON");
					}
				//	isbjyy.setVisibility(View.VISIBLE);			(2.2.14去掉是否备件原因)
					stopReason.setVisibility(View.VISIBLE);
				}
				else if("4".equals(data.getValue())){//暂停服务
					if("御银客服".equals(reportdata.getServcomp())){
						if("1".equals(reportdata.getMachineStatus()) || "2".equals(reportdata.getMachineStatus())){
							T.showShort(mContext, "机器状态为:正常使用 或 停机, 机器是否正常服务 不能选择  暂停服务, 请重新选择!");
							workFinishFlag.setFieldTextAndValueFromValue("99");
							return;
						}
					}
					
				//	isbjyy.setVisibility(View.VISIBLE);  (2.2.14去掉是否备件原因)
					stopReason.setVisibility(View.VISIBLE);
				}
			}
		});
		
	}

	

	/**
	 * 设置数据
	 * @param data
	 */
	private void setDataInfo(RepairReportBean data) {
		// TODO 设置数据
		reportdata = data;
		
		List<WorkTypeBean> rwList = data.getRwList();
		
		List<String> listtype = new ArrayList<String>();
		
		for (int j = 0; j < rwList.size(); j++) {
			listtype.add(rwList.get(j).getWorkType());
		}
		
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
			/*if (listtype.contains("DEBUG")||listtype.contains("")) {
				
			}*/
		}else {
			workFinishFlag.setFieldTextAndValueFromValue(data.getWorkFinishFlag());
		}
	//	isbjyy.setFieldTextAndValueFromValue(data.getIsbjyy());    //(2.2.14去掉是否备件原因)
		stopReason.setFieldTextAndValueFromValue(data.getStopReason());
		if (listtype.contains("BACK_MACHINE")
				||listtype.contains("MOVE_MACHINE")
				||listtype.contains("DEBUG")
				||listtype.contains("SCRAP_MACHINE")
				||listtype.contains("ENGINEERING_PROJECT")) {
			workFinishFlag.setFieldEnabled(false);
			stopReason.setFieldEnabled(false);
		}else {
			workFinishFlag.setFieldMachineEnabled(true);
			stopReason.setFieldMachineEnabled(true);
		}
		
		stopReason.setLists(CommonSelcetUtils.getSelectList(CommonSelcetUtils.stopReasonType2));//恢复最新版本暂停服务原因选项
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
		
		auditRemark.setFieldTextAndValue(data.getAuditRemark());
		auditContent.setFieldTextAndValue(data.getAuditContent());
		auditTitle.setText("维护报告意见");
		
		
		List<String> gzlb = new ArrayList<String>();
		for(int i = 0; i< data.getRwList().size(); i++){
			gzlb.add(data.getRwList().get(i).getWorkType());
		}
		
		workTypeNewMove.setFieldTextAndValueFromValue(data.getWorkTypeNewMove());
	/*	if(!KingTellerJudgeUtils.isEmpty(data.getWorkTypeNewMove())){
			workTypeNewMove.setVisibility(View.VISIBLE);
		}else{
			if (gzlb.contains("START") || gzlb.contains("ENGINEERING_PROJECT")
					 || gzlb.contains("DEBUG")){
				workTypeNewMove.setVisibility(View.VISIBLE);
			}else{
				workTypeNewMove.setVisibility(View.GONE);
			}
		}*/
		/**
		 * 改为工作类别为 开通机器 时   显示机器类型选项
		 */
		if (gzlb.contains("START") ){
			workTypeNewMove.setVisibility(View.VISIBLE);
			workTypeNewMove.setFieldEnabled(false);
			workFinishFlag.setFieldEnabled(false);
		}else{
			workTypeNewMove.setVisibility(View.GONE);
		}
		
		
		if (data.getPorderno() != null) {
			porderno.setFieldTextAndValue(data.getPorderno());
		}
		if (data.getXmflag() != null) {
			xmflag.setFieldTextAndValue(data.getXmflag());
		}

		yesnotj.setFieldTextAndValueFromValue(data.getYesnotj());
		if ("1".equals(data.getYesnotj())) {
			tjdh.setVisibility(View.VISIBLE);
			tjdh.setFieldTextAndValue(data.getTjdh());
		} else {
			tjdh.setVisibility(View.GONE);
			tjdh.setFieldTextAndValue(data.getTjdh());
		}
		
		if ("1".equals(arrangeType.getFieldValue())) {
			arrangeType.setFieldTextAndValueFromValue(data.getArrangeType());
			prearrangeDate.setFieldTextAndValue(data.getPrearrangeDate());
			prearrangeDate.setVisibility(View.VISIBLE);
		}else{
			arrangeType.setFieldTextAndValueFromValue("0");
			prearrangeDate.setVisibility(View.GONE);
		}
		
		if (KingTellerJudgeUtils.isEmpty(data.getArriveOvertimeMin())) {
			arriveOvertimeMin.setVisibility(View.GONE);
		} else {
			arriveOvertimeMin.setFieldTextAndValue(data.getArriveOvertimeMin());//响应超时分钟
			if(!"0".equals(data.getArriveOvertimeMin())){
				arriveOverRemark.setVisibility(View.VISIBLE);//响应超时说明
				arriveOverRemark.setFieldTextAndValue(data.getArriveOverRemark());
			}	
		}
		
		if (KingTellerJudgeUtils.isEmpty(data.getMaintainOvertimeMin())) {
			maintainOvertimeMin.setVisibility(View.GONE);
		} else {
			maintainOvertimeMin.setFieldTextAndValue(data.getMaintainOvertimeMin());//维护超时分钟
			if(!"0".equals(data.getMaintainOvertimeMin())){
				maintainOverRemark.setVisibility(View.VISIBLE);//维护超时说明
				maintainOverRemark.setFieldTextAndValue(data.getMaintainOverRemark());
			}
		}
		
		
		//维护费用信息
		fee_group_list.getLayoutList().removeAllViews();
		if (data.getEpList() != null){
			if(data.getEpList().size() > 0){
				for (int i = 0; i < data.getEpList().size(); i++) {
					FeeRPGroupView fview;
					if (i == 0){
						fview = new FeeRPGroupView(RepairReportActivity.this, false);
					}else {
						fview = new FeeRPGroupView(RepairReportActivity.this, true);
					}

					FreeData fdata = new FreeData();
					fdata.setUserName(data.getEpList().get(i).getMaintainpersonname());
					fdata.setUserId(data.getEpList().get(i).getMaintainpersonid());
					fdata.setFeeMoney(String.valueOf(data.getEpList().get(i).getTrafficefee()).equals("0") ? null:String.valueOf(data.getEpList().get(i).getTrafficefee()));
					fdata.setBusLine(data.getEpList().get(i).getArriveroute());
					fdata.setFeeModeId(data.getEpList().get(i).getExpand2());
					fdata.setFeeTypeId(String.valueOf(data.getEpList().get(i).getExpand1()));
					fdata.setFeeType(data.getEpList().get(i).getExpand3());
					fdata.setIsGoBack(data.getEpList().get(i).getExpand4());
					fdata.setFeeMode(data.getEpList().get(i).getExpand5());
					fdata.setQzdd(data.getEpList().get(i).getQzdd());
					fdata.setJsdd(data.getEpList().get(i).getJsdd());
					fview.setData(fdata);
					fee_group_list.addItem(fview);
				}
			}else{
				findViewById(R.id.layout_whfyxx).setVisibility(View.GONE);
			}
		}

		//维护信息
		bj_group_list.getLayoutList().removeAllViews();
		if (data.getMfList() != null) {
			if(data.getMfList().size() > 0){
				for (int i = 0; i < data.getMfList().size(); i++) {
					BjGroupView bview = new BjGroupView(RepairReportActivity.this,data.getPplb(), workBean.getJqId());
					bview.setData(data.getMfList().get(i));
					/*if(data.getMfList().get(i).getTroubleRemark() != null || data.getMfList().get(i).getHandleSub() != null){
						if (data.getMfList().get(i).getTroubleRemark().contains("其他") || data.getMfList().get(i).getHandleSub().contains("其他")) {
							bview.findViewById(R.id.liearOther).setVisibility(View.VISIBLE);
						} else {
							bview.findViewById(R.id.liearOther).setVisibility(View.GONE);
						}
					}else{
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
					bj_group_list.addItem(bview);
				}
			}
		}

		//项目工单类型
		xm_workType_group_list.getLayoutList().removeAllViews();
		if (data.getPrwList() != null) {
			if(data.getPrwList().size() > 0){
				if ("xm".equals(data.getXmflag())) {
					findViewById(R.id.layout_bgxx).setVisibility(View.GONE);
				}
				
				xm_workType_group_list.setVisibility(View.VISIBLE);
				findViewById(R.id.xmworkType_group_list_view).setVisibility(View.VISIBLE);
				xm_workType_group_list.findViewById(R.id.btn_add).setVisibility(View.GONE);
				
				for (int i = 0; i < data.getPrwList().size(); i++) {
					XmWorkTypeGroupView xwtgv = new XmWorkTypeGroupView(RepairReportActivity.this);
					xwtgv.setData(data.getPrwList().get(i));
					
					
					if ("1".equals(data.getPrwList().get(i).getWorkType())) {
						if(KingTellerJudgeUtils.isEmpty(data.getPrwList().get(i).getHandleSubId())) {
							((KingTellerEditText) xwtgv.findViewById(R.id.handleSub)).setFieldTextAndValue("");
						}else {
							((KingTellerEditText) xwtgv.findViewById(R.id.handleSub)).setFieldTextAndValue(
									data.getPrwList().get(i).getHandleSub(), 
									data.getPrwList().get(i).getHandleSubId());
						}
						
					} else {
						((KingTellerEditText) xwtgv.findViewById(R.id.handleSub)).setFieldTextAndValue(
								data.getPrwList().get(i).getHandleSub(), 
								data.getPrwList().get(i).getHandleSubId());
					}
					
					xm_workType_group_list.addItem(xwtgv);
				}
				
			}
		}

		//工作工单类型
		workType_group_list.getLayoutList().removeAllViews();
		if (data.getRwList() != null) {
			
			if ("xm".equals(data.getXmflag())) {
				findViewById(R.id.layout_bgxx).setVisibility(View.GONE);
			} else {
				
				Date endTime = KingTellerTimeUtils.getDateByString(data.getMaintainEndTime());
				Date beginTime = KingTellerTimeUtils.getDateByString(data.getMaintainBeginTime());
				long minutes = (endTime.getTime() - beginTime.getTime())/ (1000 * 60);
				
				if (data.getRwList().size() == 0) {
					final WorkTypeGroupView bview = new WorkTypeGroupView(RepairReportActivity.this, false);
					bview.setListflag(true);
					bview.setOnChangeListener(this);
		

					if (minutes == 0) {
						minutes += 1;
					}
					
					((KingTellerEditText) bview.findViewById(R.id.costMinute)).setFieldTextAndValue(String.valueOf(minutes));
					
					workType_group_list.addItem(bview);
					
				} else if (data.getRwList().size() == 1) {
					
					WorkTypeGroupView bview = new WorkTypeGroupView(RepairReportActivity.this, false);
					bview.setOnChangeListener(this);
					bview.setData(data.getRwList().get(0));
					
					if (!KingTellerJudgeUtils.isEmpty(data.getRwList().get(0).getCostMinute())) {
						minutes = data.getRwList().get(0).getCostMinute();
					}
					
					if (minutes == 0) {
						minutes += 1;
					}
					
					((KingTellerEditText) bview.findViewById(R.id.costMinute)).setFieldTextAndValue(String.valueOf(minutes));
					
					if ("NORMAL".equals(data.getRwList().get(0).getWorkType())) {
						((KingTellerEditText) bview.findViewById(R.id.handleSub)).setFieldTextAndValue("请在维护信息栏填写维护过程");
					} else {
						((KingTellerEditText) bview.findViewById(R.id.handleSub)).setFieldTextAndValue(
								data.getRwList().get(0).getHandleSub(), 
								data.getRwList().get(0).getHandleSubId());
					}

					if (data.getRwList().get(0).getHandleSub().contains("其他")) {  //其他
						bview.findViewById(R.id.remark).setVisibility(View.VISIBLE);
					} else {
						bview.findViewById(R.id.remark).setVisibility(View.GONE);
					}

					workType_group_list.addItem(bview);
					
				} else {
					
					for (int i = 0; i < data.getRwList().size(); i++) {
						WorkTypeGroupView bview;
						
						if (i == 0) {
							bview = new WorkTypeGroupView(RepairReportActivity.this, false);
						} else{
							bview = new WorkTypeGroupView(RepairReportActivity.this, true);
						}
						bview.setOnChangeListener(this);
						bview.setData(data.getRwList().get(i));
						
						if ("NORMAL".equals(data.getRwList().get(i).getWorkType())) {
							((KingTellerEditText) bview.findViewById(R.id.handleSub)).setFieldTextAndValue("请在维护信息栏填写维护过程");
						} else {
							((KingTellerEditText) bview.findViewById(R.id.handleSub)).setFieldTextAndValue(
								data.getRwList().get(i).getHandleSub(), 
								data.getRwList().get(i).getHandleSubId());
						}
						
						if (data.getRwList().get(0).getHandleSub().contains("其他")) {  //其他
							bview.findViewById(R.id.remark).setVisibility(View.VISIBLE);
						} else {
							bview.findViewById(R.id.remark).setVisibility(View.GONE);
						}

						workType_group_list.addItem(bview);
					}
				}
			}
		}
		
		
		List<WorkTypeBean> list = workType_group_list.getListData();
		for (int i = 0; i < list.size(); i++) {
			
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

	

	@Override
	public void submitReport(final int flag) {
		// TODO 提交维护报告 判断

		List<WorkTypeBean> list;
		list = workType_group_list.getListData();
		for(int i = 0; i < xm_workType_group_list.getListData().size(); i++) {
			list.add((WorkTypeBean)xm_workType_group_list.getListData().get(i));
		}
		final List<String> listtype = new ArrayList<String>();
		
		for (int j = 0; j < list.size(); j++) {
			listtype.add(list.get(j).getWorkType());
		}
		
		if (flag == 2) {
			
			if (KingTellerJudgeUtils.isEmpty(wdsbmc.getFieldValue())) {
				T.showShort(mContext, "网点设备简称必须填写!");
				return;
			}
			
			if (KingTellerJudgeUtils.isEmpty(wdlxr.getFieldValue())) {
				T.showShort(mContext, "网点联系人必须填写!");
				return;
			}

			if (KingTellerJudgeUtils.isEmpty(wdlxdh.getFieldValue())) {
				T.showShort(mContext, "网点联系人电话必须填写!");
				return;
			}

			for (int j = 0; j < list.size(); j++) {
				if (KingTellerJudgeUtils.isEmpty(list.get(j).getWorkType())) {
					T.showShort(mContext, "工作类别必须填写!");
					return;
				}

				if (KingTellerJudgeUtils.isEmpty(list.get(j).getCostMinute())
						|| list.get(j).getCostMinute() == 0) {
					T.showShort(mContext, "工作类别信息栏目中 的 耗时时间 必须填写, 且不能少于1分钟!");
					return;
				}

				if (KingTellerJudgeUtils.isEmpty(list.get(j).getHandleSub())) {
					T.showShort(mContext, "工作类别信息栏目中 的 处理过程 必须填写!");
					return;
				}
				
				
				if (list.get(j).getWorkType().equals("NORMAL")) {
					if (KingTellerJudgeUtils.isEmpty(list.get(j).getReason())) {
						T.showShort(mContext, "工作类别信息栏目中 的 本次故障原因 不能为空!");
						return;
					}
					
					/**
					 * 工作类别    （改动:故障维护  不能和退机、客服报废、移机、开通机器共存    去除银行操作不当   其他  新机原因      新选项：全部  设备运行故障 人为原因 自然灾害  外部原因导致）
					 */
					/*if (listtype.contains("START") || listtype.contains("DEBUG")) {
						if(!"4".equals(list.get(j).getReason()) && "新机开通".equals(workTypeNewMove.getFieldText())){
							T.showShort(mContext, "工作类别 含有 故障维护 且有 （开通机器 或 调试机器 ）且 机器类型 为 新机开通 时,本次故障原因 必须为  新机原因!");
							return;
						}
					}
					
					if (!listtype.contains("START") || !listtype.contains("DEBUG")) {
						if("4".equals(list.get(j).getReason()) && !"新机开通".equals(workTypeNewMove.getFieldText())){
							T.showShort(mContext, "工作类别 含有 故障维护 且 （不含有 开通机器 或 调试机器 ）且 机器类型  不为  新机开通 时, 本次故障原因 不能为  新机原因!");
							return;
						}
					}*/
				}
				
				if (list.get(j).getHandleSub().contains("其他")) {  //其他 
					if (KingTellerJudgeUtils.isEmpty(list.get(j).getRemark())) {
						T.showShort(mContext, "工作类别信息栏目中 的 其他说明 不能为空!");
						return;
					}
				}
			}
			
			/**=============判断耗时时间===========如果为项目工单不做限制 start**/
			if(xmflag.getFieldValue().contains("wh") || xmflag.getFieldValue().contains("xmwh")){
				if(!KingTellerJudgeUtils.isEmpty(reportdata.getServType()) && "SITE_SERV".equals(reportdata.getServType().trim())){
					long consumTime = 0;//耗时时间总和
					if (KingTellerJudgeUtils.isEmpty(maintainBeginTime.getFieldValue()) || 
							KingTellerJudgeUtils.isEmpty(maintainEndTime.getFieldValue())) {
						T.showShort(mContext, "维护完成时间或维护开始时间为空!");
						return;
					}
					String startTime = maintainBeginTime.getFieldValue().trim();
					String endTime =  maintainEndTime.getFieldValue().trim(); 
					long time = KingTellerTimeUtils.getBeApartMinute(startTime, endTime, 0);
					if(time == 0 || time <= 1){
						time = 1;
					}
					for (int j = 0; j < list.size(); j++) {
						if (!KingTellerJudgeUtils.isEmpty(list.get(j).getCostMinute())) {
							consumTime += list.get(j).getCostMinute();
						}
					}
					//判断耗时时间
					if(consumTime > time){
						T.showShort(mContext, "所有工作类别的耗费时间和 不能 大于 维护完成时间-维护开始时间!");
						return;
					}
				}
			}
			/**=============判断耗时时间=========== end**/
			
			
			/*if (listtype.contains("START") || listtype.contains("MOVE_MACHINE") ||listtype.contains("ENGINEERING_PROJECT") || listtype.contains("DEBUG")) {
				if (KingTellerJudgeUtils.isEmpty(workTypeNewMove.getFieldText())) {
					T.showShort(mContext, "机器类型选项不能为空!");
					return;
				}
			}*/
			if (listtype.contains("START") ) {
				if (KingTellerJudgeUtils.isEmpty(workTypeNewMove.getFieldText())) {
					T.showShort(mContext, "机器类型选项不能为空!");
					return;
				}else if ("MOVE".equals(workTypeNewMove.getFieldValue())) {
					IsNeed = true;
					 final NormalDialog IsUpdate_Dialog = new NormalDialog(mContext);
		                KingTellerPromptDialogUtils.showTwoPromptDialog(IsUpdate_Dialog, "是否需要更新网点简称地址等信息！", new OnBtnClickL() {
		                    @Override
		                    public void onBtnClick() {
		                    	IsUpdate_Dialog.dismiss();
		                    	submitMoveStartReport(flag);        	
		                    }
		                },new OnBtnClickL() {
							@Override
							public void onBtnClick() {
								IsUpdate_Dialog.dismiss();
								wdsbmc.setFouces(true);
							}
						});
				}else {
					IsNeed = false;
				}
			}
			
			if (IsNeed) {
				return;
			}
			
			if (KingTellerJudgeUtils.isEmpty(workFinishFlag.getFieldValue()) || "99".equals(workFinishFlag.getFieldValue())) {
				T.showShort(mContext, "机器是否正常服务不能为空!");
				return;
			}
			
			
			if (!KingTellerJudgeUtils.isEmpty(arrangeType.getFieldValue()) && "1".equals(arrangeType.getFieldValue())) {
				if (KingTellerJudgeUtils.isEmpty(prearrangeDate.getFieldValue())) {
					T.showShort(mContext, "预约时间不能为空!");
					return;
				} 
//				else if (!prearrangeDate.getFieldValue().startsWith(
//					  workDate.getFieldValue())) { T.showShort(mContext, "预约时间必须和工作时间同一天!");
//					  return; 
//				}
				
			}
			
			if (!KingTellerJudgeUtils.isEmpty(arriveOvertimeMin.getFieldValue()) && !"0".equals(arriveOvertimeMin.getFieldValue())) {
				if (KingTellerJudgeUtils.isEmpty(arriveOverRemark.getFieldValue())) {
					T.showShort(mContext, "响应超时说明不能为空!");
					return;
				}
			}

			if (!KingTellerJudgeUtils.isEmpty(maintainOvertimeMin.getFieldValue()) && !"0".equals(maintainOvertimeMin.getFieldValue())) {
				if (KingTellerJudgeUtils.isEmpty(maintainOverRemark.getFieldValue())) {
					T.showShort(mContext, "维护超时说明不能为空!");
					return;
				}
			}
			
			/*if ("1".equals(workFinishFlag.getFieldValue())) {
				if(KingTellerJudgeUtils.isEmpty(stopReason.getFieldValue())){
					T.showShort(mContext, "停机原因不能为空!");
					return;
				}
				if (KingTellerJudgeUtils.isEmpty(isbjyy.getFieldValue())) {
					T.showShort(mContext, "是否备件原因不能为空!");
					return;
				}
			}*/
			
			/**
			 * 改为 机器类型选项为 暂停服务时    1.停机原因不能为空 2.是否备件原因不能为空
			 */
			if ("4".equals(workFinishFlag.getFieldValue())) {
				if(KingTellerJudgeUtils.isEmpty(stopReason.getFieldValue())){
					T.showShort(mContext, "停机原因不能为空!");
					return;
				}
				/*if (KingTellerJudgeUtils.isEmpty(isbjyy.getFieldValue())) {    //(2.2.14去掉是否备件原因)
					T.showShort(mContext, "是否备件原因不能为空!");
					return;
				}*/
			}
			
			
			
			if (listtype.contains("NORMAL") && flag == 2 && "0".equals(involvesSpareParts.getFieldValue())) {
				T.showShort(mContext, "您的维护信息必须填写!");
				return;
			}
			
			//判断 维护信息数据
			if (listtype.contains("NORMAL") && "1".equals(involvesSpareParts.getFieldValue())) {
				List<BJBean> bjcost = bj_group_list.getListData();
				for (int i = 0; i < bjcost.size(); i++) {
					if (KingTellerJudgeUtils.isEmpty(bjcost.get(i).getTroubleType())) {
						T.showShort(mContext, "维护信息栏目中 的 故障类别 不能为空!");
						return;
					}

					if (KingTellerJudgeUtils.isEmpty(bjcost.get(i).getTroubleModule())) {
						T.showShort(mContext, "维护信息栏目中 的 故障部件 不能为空!");
						return;
					}

					if (KingTellerJudgeUtils.isEmpty(bjcost.get(i).getTroubleRemarkId())) {
						T.showShort(mContext, "维护信息栏目中 的 故障描述 不能为空!");
						return;
					}

					if (bjcost.get(i).getTroubleRemark().contains("其他")) {
						if (KingTellerJudgeUtils.isEmpty(bjcost.get(i).getExpand4())) {
							T.showShort(mContext, "维护信息栏目中的故障描述的其他说明 不能为空!");
							return;
						}
					}
					if (KingTellerJudgeUtils.isEmpty(bjcost.get(i).getHandleSub())) {
						T.showShort(mContext, "维护信息栏目中 的 处理过程 不能为空!");
						return;
					}
					if (bjcost.get(i).getHandleSub().contains("其他")) {//其它 其他 
						if (KingTellerJudgeUtils.isEmpty(bjcost.get(i).getRemark())) {
							T.showShort(mContext, "维护信息栏目中 的处理过程其他说明 不能为空!");
							return;
						}
					}
					
					//处理过程或其他说明是否含有更换
					if (bjcost.get(i).getRemark().contains("更换") || bjcost.get(i).getHandleSub().contains("更换") ) {
						if(bjcost.get(i).getIsChangeModule() == 0){
							T.showShort(mContext, "维护信息栏目中 的 处理过程或其他说明中 包含 更换 字眼 ,请设置是否更换备件为 是!");
							return;
						}
					}

					if (bjcost.get(i).getIsChangeModule() == 1) {
						if (KingTellerJudgeUtils.isEmpty(bjcost.get(i).getInstallBjWlId())) {
							T.showShort(mContext, "维护信息栏目中 的 安装物料 不能为空!");
							return;
						}
						if (KingTellerJudgeUtils.isEmpty(bjcost.get(i).getDownBjWlId())) {
							T.showShort(mContext, "维护信息栏目中 的 原装物料 不能为空!");
							return;
						}
						if (((KingTellerEditText)bj_group_list.getLinearLayout(i).findViewById(R.id.troubleYjPz)).isFieldEnabled() 
								&& KingTellerJudgeUtils.isEmpty(bjcost.get(i).getMachineBjId())) {
							T.showShort(mContext, "维护信息栏目中 的 硬件配置 不能为空!");
							return;
						}
					}
				}
			}
			//判断 项目PM工单 维护信息数据
			else if(listtype.contains("1") 
					||listtype.contains("START")
					||listtype.contains("PM")
					||listtype.contains("CUSTOMER_ASSISTANCE")
					||listtype.contains("INSTALL_UPGRAD")) {		
				List<BJBean> bjcost = bj_group_list.getListData();
				for (int i = 0; i < bjcost.size(); i++) {
					//判断 是否为空
					if(KingTellerJudgeUtils.isEmpty(bjcost.get(i).getTroubleType())
						&& KingTellerJudgeUtils.isEmpty(bjcost.get(i).getTroubleModule())
						&& KingTellerJudgeUtils.isEmpty(bjcost.get(i).getTroubleRemarkId())
						&& KingTellerJudgeUtils.isEmpty(bjcost.get(i).getHandleSubId())
						&& KingTellerJudgeUtils.isEmpty(bjcost.get(i).getInstallBjWlId())
						&& KingTellerJudgeUtils.isEmpty(bjcost.get(i).getDownBjWlId())) {
						isNotNormalEmpty= true;
						involvesSpareParts.setFieldTextAndValue("0");
					}
					else {
						isNotNormalEmpty = false;
						involvesSpareParts.setFieldTextAndValue("1");
						break;
					}
				}
				if("1".equals(involvesSpareParts.getFieldValue())) {
					List<BJBean> bjcost2 = bj_group_list.getListData();
					for (int i = 0; i < bjcost.size(); i++) {
						if (KingTellerJudgeUtils.isEmpty(bjcost.get(i).getTroubleType())) {
							T.showShort(mContext, "维护信息栏目中 的 故障类别 不能为空!");
							return;
						}

						if (KingTellerJudgeUtils.isEmpty(bjcost.get(i).getTroubleModule())) {
							T.showShort(mContext, "维护信息栏目中 的 故障部件 不能为空!");
							return;
						}

						if (KingTellerJudgeUtils.isEmpty(bjcost.get(i).getTroubleRemarkId())) {
							T.showShort(mContext, "维护信息栏目中 的 故障描述 不能为空!");
							return;
						}
						
						if (bjcost.get(i).getTroubleRemark().contains("其他")) {//其它 其他 
							if (KingTellerJudgeUtils.isEmpty(bjcost.get(i).getExpand4())) {
								T.showShort(mContext, "维护信息栏目中 的 故障描述其他说明 不能为空!");
								return;
							}
						}
						
						if (KingTellerJudgeUtils.isEmpty(bjcost.get(i).getHandleSubId())) {
							T.showShort(mContext, "维护信息栏目中 的 处理过程 不能为空!");
							return;
						}
						if (bjcost.get(i).getHandleSub().contains("其他")) {//其它 其他 
							if (KingTellerJudgeUtils.isEmpty(bjcost.get(i).getRemark())) {
								T.showShort(mContext, "维护信息栏目中 的 处理过程其他说明 不能为空!");
								return;
							}
						}
						/*if (bjcost.get(i).getTroubleRemark().contains("其他") || bjcost.get(i).getHandleSub().contains("其他")) {//其它 其他 
							if (KingTellerJudgeUtils.isEmpty(bjcost.get(i).getRemark())) {
								T.showShort(mContext, "维护信息栏目中 的 其他说明 不能为空!");
								return;
							}
						}*/
						
						//处理过程或其他说明是否含有更换
						if (bjcost.get(i).getRemark().contains("更换") || bjcost.get(i).getHandleSub().contains("更换") ) {
							if(bjcost.get(i).getIsChangeModule() == 0){
								T.showShort(mContext, "维护信息栏目中 的 处理过程或其他说明中 包含 更换 字眼 ,请设置是否更换备件为 是!");
								return;
							}
						}

						if (bjcost.get(i).getIsChangeModule() == 1) {
							if (KingTellerJudgeUtils.isEmpty(bjcost.get(i).getInstallBjWlId())) {
								T.showShort(mContext, "维护信息栏目中 的 安装物料 不能为空!");
								return;
							}
							if (KingTellerJudgeUtils.isEmpty(bjcost.get(i).getDownBjWlId())) {
								T.showShort(mContext, "维护信息栏目中 的 原装物料 不能为空!");
								return;
							}
							if (((KingTellerEditText)bj_group_list.getLinearLayout(i).findViewById(R.id.troubleYjPz)).isFieldEnabled() 
									&& KingTellerJudgeUtils.isEmpty(bjcost.get(i).getMachineBjId())) {
								T.showShort(mContext, "维护信息栏目中 的 硬件配置 不能为空!");
								return;
							}
						}
					}
				}
			}else {
				bj_group_list.getLayoutList().removeAllViews();
			}
			
			
//			/**
//			 * 工作类别为 开通机器  时, 当'机器类型' 选项选择了  '移机开通' 出现提示
//			 */
//			if(listtype.contains("START") && "移机开通".equals(workTypeNewMove.getFieldText())){//移机开通
//				if(isShowXysj == 0){
//					isShowXysj ++;
//					final NormalDialog dialog = new NormalDialog(mContext);
//		        	KingTellerPromptDialogUtils.showSaveTwoPromptDialog(dialog, "是否需要修改该机器的标准响应时间?(只提示一次)", "继续提交", "确定修改",
//							new OnBtnClickL() {
//								@Override
//								public void onBtnClick() {
//									dialog.dismiss();
//									submitReport(flag);
//								}
//		                    },new OnBtnClickL() {
//								@Override
//								public void onBtnClick() {
//									dialog.dismiss();
//									getReportXiangYingTime();
//								}
//		                    });
//		        	return;
//				}
//			}

			/*
			 * if(!KingTellerJudgeUtils.isEmpty(tjdh.getFieldValue()) &&
			 * !tjdh.getFieldValue().equals(machId.getFieldValue())){
			 * T.showShort(mContext, "退机单号和机器编号不一致!"); return; }
			 */
			
		}


		
		super.submitReport(flag);
	}
	
	private void submitMoveStartReport(int flag){
		List<WorkTypeBean> list;
		list = workType_group_list.getListData();
		for(int i = 0; i < xm_workType_group_list.getListData().size(); i++) {
			list.add((WorkTypeBean)xm_workType_group_list.getListData().get(i));
		}
		final List<String> listtype = new ArrayList<String>();
		
		for (int j = 0; j < list.size(); j++) {
			listtype.add(list.get(j).getWorkType());
		}
		if (KingTellerJudgeUtils.isEmpty(workFinishFlag.getFieldValue()) || "99".equals(workFinishFlag.getFieldValue())) {
			T.showShort(mContext, "机器是否正常服务不能为空!");
			return;
		}
		
		
		if (!KingTellerJudgeUtils.isEmpty(arrangeType.getFieldValue()) && "1".equals(arrangeType.getFieldValue())) {
			if (KingTellerJudgeUtils.isEmpty(prearrangeDate.getFieldValue())) {
				T.showShort(mContext, "预约时间不能为空!");
				return;
			} 
//			else if (!prearrangeDate.getFieldValue().startsWith(
//				  workDate.getFieldValue())) { T.showShort(mContext, "预约时间必须和工作时间同一天!");
//				  return; 
//			}
			
		}
		
		if (!KingTellerJudgeUtils.isEmpty(arriveOvertimeMin.getFieldValue()) && !"0".equals(arriveOvertimeMin.getFieldValue())) {
			if (KingTellerJudgeUtils.isEmpty(arriveOverRemark.getFieldValue())) {
				T.showShort(mContext, "响应超时说明不能为空!");
				return;
			}
		}

		if (!KingTellerJudgeUtils.isEmpty(maintainOvertimeMin.getFieldValue()) && !"0".equals(maintainOvertimeMin.getFieldValue())) {
			if (KingTellerJudgeUtils.isEmpty(maintainOverRemark.getFieldValue())) {
				T.showShort(mContext, "维护超时说明不能为空!");
				return;
			}
		}
		
		/*if ("1".equals(workFinishFlag.getFieldValue())) {
			if(KingTellerJudgeUtils.isEmpty(stopReason.getFieldValue())){
				T.showShort(mContext, "停机原因不能为空!");
				return;
			}
			if (KingTellerJudgeUtils.isEmpty(isbjyy.getFieldValue())) {
				T.showShort(mContext, "是否备件原因不能为空!");
				return;
			}
		}*/
		
		/**
		 * 改为 机器类型选项为 暂停服务时    1.停机原因不能为空 2.是否备件原因不能为空
		 */
		if ("4".equals(workFinishFlag.getFieldValue())) {
			if(KingTellerJudgeUtils.isEmpty(stopReason.getFieldValue())){
				T.showShort(mContext, "停机原因不能为空!");
				return;
			}
			/*if (KingTellerJudgeUtils.isEmpty(isbjyy.getFieldValue())) {    //(2.2.14去掉是否备件原因)
				T.showShort(mContext, "是否备件原因不能为空!");
				return;
			}*/
		}
		
		
		
		if (listtype.contains("NORMAL") && flag == 2 && "0".equals(involvesSpareParts.getFieldValue())) {
			T.showShort(mContext, "您的维护信息必须填写!");
			return;
		}
		
		//判断 维护信息数据
		if (listtype.contains("NORMAL") && "1".equals(involvesSpareParts.getFieldValue())) {
			List<BJBean> bjcost = bj_group_list.getListData();
			for (int i = 0; i < bjcost.size(); i++) {
				if (KingTellerJudgeUtils.isEmpty(bjcost.get(i).getTroubleType())) {
					T.showShort(mContext, "维护信息栏目中 的 故障类别 不能为空!");
					return;
				}

				if (KingTellerJudgeUtils.isEmpty(bjcost.get(i).getTroubleModule())) {
					T.showShort(mContext, "维护信息栏目中 的 故障部件 不能为空!");
					return;
				}

				if (KingTellerJudgeUtils.isEmpty(bjcost.get(i).getTroubleRemarkId())) {
					T.showShort(mContext, "维护信息栏目中 的 故障描述 不能为空!");
					return;
				}

				if (bjcost.get(i).getTroubleRemark().contains("其他")) {
					if (KingTellerJudgeUtils.isEmpty(bjcost.get(i).getExpand4())) {
						T.showShort(mContext, "维护信息栏目中的故障描述的其他说明 不能为空!");
						return;
					}
				}
				if (KingTellerJudgeUtils.isEmpty(bjcost.get(i).getHandleSub())) {
					T.showShort(mContext, "维护信息栏目中 的 处理过程 不能为空!");
					return;
				}
				if (bjcost.get(i).getHandleSub().contains("其他")) {//其它 其他 
					if (KingTellerJudgeUtils.isEmpty(bjcost.get(i).getRemark())) {
						T.showShort(mContext, "维护信息栏目中 的处理过程其他说明 不能为空!");
						return;
					}
				}
				
				//处理过程或其他说明是否含有更换
				if (bjcost.get(i).getRemark().contains("更换") || bjcost.get(i).getHandleSub().contains("更换") ) {
					if(bjcost.get(i).getIsChangeModule() == 0){
						T.showShort(mContext, "维护信息栏目中 的 处理过程或其他说明中 包含 更换 字眼 ,请设置是否更换备件为 是!");
						return;
					}
				}

				if (bjcost.get(i).getIsChangeModule() == 1) {
					if (KingTellerJudgeUtils.isEmpty(bjcost.get(i).getInstallBjWlId())) {
						T.showShort(mContext, "维护信息栏目中 的 安装物料 不能为空!");
						return;
					}
					if (KingTellerJudgeUtils.isEmpty(bjcost.get(i).getDownBjWlId())) {
						T.showShort(mContext, "维护信息栏目中 的 原装物料 不能为空!");
						return;
					}
					if (((KingTellerEditText)bj_group_list.getLinearLayout(i).findViewById(R.id.troubleYjPz)).isFieldEnabled() 
							&& KingTellerJudgeUtils.isEmpty(bjcost.get(i).getMachineBjId())) {
						T.showShort(mContext, "维护信息栏目中 的 硬件配置 不能为空!");
						return;
					}
				}
			}
		
			
		}
		//判断 项目PM工单 维护信息数据
		else if(listtype.contains("1") 
				||listtype.contains("START")
				||listtype.contains("PM")
				||listtype.contains("CUSTOMER_ASSISTANCE")
				||listtype.contains("INSTALL_UPGRAD")) {		
			List<BJBean> bjcost = bj_group_list.getListData();
			for (int i = 0; i < bjcost.size(); i++) {
				//判断 是否为空
				if(KingTellerJudgeUtils.isEmpty(bjcost.get(i).getTroubleType())
					&& KingTellerJudgeUtils.isEmpty(bjcost.get(i).getTroubleModule())
					&& KingTellerJudgeUtils.isEmpty(bjcost.get(i).getTroubleRemarkId())
					&& KingTellerJudgeUtils.isEmpty(bjcost.get(i).getHandleSubId())
					&& KingTellerJudgeUtils.isEmpty(bjcost.get(i).getInstallBjWlId())
					&& KingTellerJudgeUtils.isEmpty(bjcost.get(i).getDownBjWlId())) {
					isNotNormalEmpty = true;
					involvesSpareParts.setFieldTextAndValue("0");
				}
				else {
					isNotNormalEmpty = false;
					involvesSpareParts.setFieldTextAndValue("1");
					break;
				}
			}
			if("1".equals(involvesSpareParts.getFieldValue())) {
				List<BJBean> bjcost2 = bj_group_list.getListData();
				for (int i = 0; i < bjcost.size(); i++) {
					if (KingTellerJudgeUtils.isEmpty(bjcost.get(i).getTroubleType())) {
						T.showShort(mContext, "维护信息栏目中 的 故障类别 不能为空!");
						return;
					}

					if (KingTellerJudgeUtils.isEmpty(bjcost.get(i).getTroubleModule())) {
						T.showShort(mContext, "维护信息栏目中 的 故障部件 不能为空!");
						return;
					}

					if (KingTellerJudgeUtils.isEmpty(bjcost.get(i).getTroubleRemarkId())) {
						T.showShort(mContext, "维护信息栏目中 的 故障描述 不能为空!");
						return;
					}
					
					if (bjcost.get(i).getTroubleRemark().contains("其他")) {//其它 其他 
						if (KingTellerJudgeUtils.isEmpty(bjcost.get(i).getExpand4())) {
							T.showShort(mContext, "维护信息栏目中 的 故障描述其他说明 不能为空!");
							return;
						}
					}
					
					if (KingTellerJudgeUtils.isEmpty(bjcost.get(i).getHandleSubId())) {
						T.showShort(mContext, "维护信息栏目中 的 处理过程 不能为空!");
						return;
					}
					if (bjcost.get(i).getHandleSub().contains("其他")) {//其它 其他 
						if (KingTellerJudgeUtils.isEmpty(bjcost.get(i).getRemark())) {
							T.showShort(mContext, "维护信息栏目中 的 处理过程其他说明 不能为空!");
							return;
						}
					}
					/*if (bjcost.get(i).getTroubleRemark().contains("其他") || bjcost.get(i).getHandleSub().contains("其他")) {//其它 其他 
						if (KingTellerJudgeUtils.isEmpty(bjcost.get(i).getRemark())) {
							T.showShort(mContext, "维护信息栏目中 的 其他说明 不能为空!");
							return;
						}
					}*/
					
					//处理过程或其他说明是否含有更换
					if (bjcost.get(i).getRemark().contains("更换") || bjcost.get(i).getHandleSub().contains("更换") ) {
						if(bjcost.get(i).getIsChangeModule() == 0){
							T.showShort(mContext, "维护信息栏目中 的 处理过程或其他说明中 包含 更换 字眼 ,请设置是否更换备件为 是!");
							return;
						}
					}

					if (bjcost.get(i).getIsChangeModule() == 1) {
						if (KingTellerJudgeUtils.isEmpty(bjcost.get(i).getInstallBjWlId())) {
							T.showShort(mContext, "维护信息栏目中 的 安装物料 不能为空!");
							return;
						}
						if (KingTellerJudgeUtils.isEmpty(bjcost.get(i).getDownBjWlId())) {
							T.showShort(mContext, "维护信息栏目中 的 原装物料 不能为空!");
							return;
						}
						if (((KingTellerEditText)bj_group_list.getLinearLayout(i).findViewById(R.id.troubleYjPz)).isFieldEnabled() 
								&& KingTellerJudgeUtils.isEmpty(bjcost.get(i).getMachineBjId())) {
							T.showShort(mContext, "维护信息栏目中 的 硬件配置 不能为空!");
							return;
						}
					}
				}
			}
		}else {
			bj_group_list.getLayoutList().removeAllViews();
		}
		
		
//		/**
//		 * 工作类别为 开通机器  时, 当'机器类型' 选项选择了  '移机开通' 出现提示
//		 */
//		if(listtype.contains("START") && "移机开通".equals(workTypeNewMove.getFieldText())){//移机开通
//			if(isShowXysj == 0){
//				isShowXysj ++;
//				final NormalDialog dialog = new NormalDialog(mContext);
//	        	KingTellerPromptDialogUtils.showSaveTwoPromptDialog(dialog, "是否需要修改该机器的标准响应时间?(只提示一次)", "继续提交", "确定修改",
//						new OnBtnClickL() {
//							@Override
//							public void onBtnClick() {
//								dialog.dismiss();
//								submitReport(flag);
//							}
//	                    },new OnBtnClickL() {
//							@Override
//							public void onBtnClick() {
//								dialog.dismiss();
//								getReportXiangYingTime();
//							}
//	                    });
//	        	return;
//			}
//		}

		/*
		 * if(!KingTellerJudgeUtils.isEmpty(tjdh.getFieldValue()) &&
		 * !tjdh.getFieldValue().equals(machId.getFieldValue())){
		 * T.showShort(mContext, "退机单号和机器编号不一致!"); return; }
		 */
		super.submitReport(flag);
	}

	/**
	 * 获取参数生成json
	 * 
	 * @return
	 */
	@Override
	public String getFromData() {
		if(reportdata == null){
			return "";
		}

		//Log.e("11111111111", wddz.getFieldValue() + "  " + wddz.getFieldText());
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
				this, (LinearLayout) findViewById(R.id.layout_bj), false);
		
		//若不点击头右边的信息，自动获取右边信息
		/*
		List<WorkTypeBean> lista;
		if("xm".equals(xmflag.getFieldValue())) {
			lista = xm_workType_group_list.getListData();
		}else {
			lista = workType_group_list.getListData();
		}
		List<String> listtypea = new ArrayList<String>();
		for (int j = 0; j < lista.size(); j++) {
			listtypea.add(lista.get(j).getWorkType());
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
			if ("请在维护信息栏填写维护过程".equals(wcost.get(i).getHandleSub())) {
				data.setHandleSub("");
			} else {
				data.setHandleSub(wcost.get(i).getHandleSub());
			}
			data.setHandleSubId(wcost.get(i).getHandleSubId());
			data.setReason(wcost.get(i).getReason());
			data.setRemark(wcost.get(i).getRemark());
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
			data.setHandleSub(xmwcost.get(i).getHandleSub());
			data.setHandleSubId(xmwcost.get(i).getHandleSubId());
			data.setRemark(xmwcost.get(i).getRemark());
			xmwtbcost.add(data);
		}
		params.put("prwList", xmwtbcost.size() == 0 ? null : xmwtbcost);
		
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
		
		
		//获取维护信息的信息
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
		params.put("troubleHandleResult", workFinishFlag.getFieldText());
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

		Log.e("维护报告 input 输出 ：", ConditionUtils.getJsonFromHashMap(params));
		return ConditionUtils.getJsonFromHashMap(params);
	}

	
	
	@Override
	public void onWorkTypeChange(WorkTypeGroupView view, CommonSelectData data) {
//			  "故障维护：NORMAL", "软件升级：SOFT_UPGRAD", "调试机器：DEBUG", "PM：PM", "开通机器：START", 
//			  "账务处理：ACCOUNTANT", "培训：TRAINING", "接机：RECEIVE_MACHINE", "移机：MOVE_MACHINE", 
//			  "退机：BACK_MACHINE", "安装机器：SETUP_MACHINE", "检查机器：CHECK_MACHINE", "其他：OTHER", 
//			  "部件加装：ADD_COMPONENT", "客服报废：SCRAP_MACHINE", "硬件改造：MODIFY_COMPONENT","停机：STOP_MACHINE"};
			
			//List<WorkTypeBean> list = workType_group_list.getListData();
			final List<String> listtype = new ArrayList<String>();
			List<WorkTypeBean> list;
			list = workType_group_list.getListData();
			for(int i = 0; i < xm_workType_group_list.getListData().size(); i++) {
				list.add((WorkTypeBean)xm_workType_group_list.getListData().get(i));
			}
			
			
			for (int j = 0; j < list.size(); j++) {
				listtype.add(list.get(j).getWorkType());
			}
			
			if(data != null && !listtype.contains(data.getValue())){
				Log.e("当前  View 工作类别   ", data.getValue());
				listtype.add(data.getValue());
			}
			
			/*for(int i = 0; i < list.size(); i++ ){//重新设置 新机原因
				if("NORMAL".equals(list.get(i).getWorkType()) && "4".equals(list.get(i).getReason())){
					KingTellerEditText troubleReasonCode_2 = (KingTellerEditText) ((WorkTypeGroupView) 
							workType_group_list.getLayoutList().getChildAt(i)).findViewById(R.id.troubleReasonCode);
						troubleReasonCode_2.setFieldTextAndValue("");
						troubleReasonCode_2.setFieldEnabled(true);
						troubleReasonCode_2.setFieldRequested(true);
				}
			}*/

			//初始默认
			workTypeNewMove.setFieldTextAndValue("");//机器类型选项  移机开通 MOVE 新机开通NEW
			isbjyy.setFieldTextAndValue("");//是否备件原因  是1  否2
			workFinishFlag.setFieldTextAndValue("");//机器是否正常服务   正常对外0    停机1   未上线停机3    请选择99    (改为:正常服务0 机器带故障运行1  暂停服务3  请选择99)
			stopReason.setFieldTextAndValue("");//停机原因  
			yesnotj.setFieldTextAndValueFromValue("0");//是否退机  是1   否0
			
			workFinishFlag.setFieldEnabled(true);
			workFinishFlag.setFieldRequested(true);
			workFinishFlag.setFieldMachineEnabled(true);
			stopReason.setFieldMachineEnabled(true);
			
			
			if (listtype.contains("INSTALL_UPGRAD")) {
				if (KingTellerJudgeUtils.isEmpty(reportdata.getSxrq())) {
					workFinishFlag.setFieldTextAndValueFromValue("4");
				}else {
					workFinishFlag.setFieldTextAndValueFromValue("2");
				}				
			}
			
			/**
			 * 工作类别为工程项目类时，隐藏  机器是否正常服务   停机原因
			 */
			if (listtype.contains("ENGINEERING_PROJECT")) {
				workFinishFlag.setFieldTextAndValueFromValue("4");
				workFinishFlag.setFieldEnabled(false);
				stopReason.setFieldTextAndValueFromValue("NOTLINE_REASON");
				stopReason.setFieldEnabled(false);
			}			
			
			/**
			 * 工作类别为 客户协助  PM 加装加装升级项目  时 机器是否正常服务 默认 "请选择"   停机原因隐藏
			 */
			if (listtype.contains("NORMAL")||listtype.contains("CUSTOMER_ASSISTANCE") || listtype.contains("INSTALL_UPGRAD") || listtype.contains("PM")) {
				workFinishFlag.setFieldMachineEnabled(true);
				stopReason.setFieldMachineEnabled(true);
				workFinishFlag.setFieldTextAndValueFromValue("99");
				stopReason.setVisibility(View.GONE);
			}
	
			/**
			 * 工作类别为 故障维护  项目工程类 客户协助类  加装升级类时, 设置可以填写 维护信息;
			 */
			if(listtype.contains("NORMAL") 
					||listtype.contains("START")
					|| listtype.contains("1")
					||listtype.contains("PM")
					||listtype.contains("CUSTOMER_ASSISTANCE")
					||listtype.contains("INSTALL_UPGRAD")){
				involvesSpareParts.setFieldTextAndValue("1");
			/*}else if(xmflag.getFieldValue().contains("xmwh")){
				involvesSpareParts.setFieldTextAndValue("1");*/
				
			}else {
				involvesSpareParts.setFieldTextAndValue("0");
			}
			
			/**
			 * 工作类别为   移机  时, 机器是否正常服务 默认  停机, 备件原因  默认  否, 停机原因  默认   移机停机 
			 */
			if(listtype.contains("MOVE_MACHINE")){
				workFinishFlag.setFieldTextAndValueFromValue("4");
				workFinishFlag.setFieldEnabled(false);
			//	isbjyy.setFieldTextAndValueFromValue("2");     //(2.2.14去掉是否备件原因)
				stopReason.setFieldTextAndValueFromValue("MOVESTOP_REASON");
				stopReason.setFieldEnabled(false);
			}
			
			/**
			 * 工作类别为调试机器是 机器是否正常服务   为暂停服务   系统为灰色        停机原因默认   
			 */
			if (listtype.contains("DEBUG")) {
				workFinishFlag.setFieldTextAndValueFromValue("4");
				workFinishFlag.setFieldEnabled(false);
				stopReason.setVisibility(View.VISIBLE);
				if (!KingTellerJudgeUtils.isEmpty(reportdata.getSxrq())) {
					stopReason.setFieldTextAndValueFromValue("MOVESTOP_REASON");
				}else {
					stopReason.setFieldTextAndValueFromValue("NOTLINE_REASON");
				}
				stopReason.setFieldEnabled(false);
			}
			
			/**
			 *工作类别为开通机器时 机器是否正常是正常服务    停机原因隐藏
			 */
			if (listtype.contains("START")) {
				workFinishFlag.setFieldTextAndValueFromValue("0");
				workFinishFlag.setFieldEnabled(false);
				stopReason.setVisibility(View.GONE);
				if (KingTellerJudgeUtils.isEmpty(reportdata.getSxrq())) {
					workTypeNewMove.setFieldTextAndValueFromValue("NEW");
				}else {
					workTypeNewMove.setFieldTextAndValueFromValue("MOVE");
				}
				workTypeNewMove.setFieldEnabled(false);
			}
			
			/**
			 * 工作类别为  开通机器，移机，接机，安装机器，调试机器  时，机器类型选项  可以选择   否则  隐藏(接机和安装机器包含到工程项目类中)    改为：只有开通机器才会显示机器类别选项
			 */
			if (listtype.contains("START") ) {
				workTypeNewMove.setVisibility(View.VISIBLE);
				workTypeNewMove.setFieldTextAndValueFromValue("");
			} else {
				workTypeNewMove.setVisibility(View.GONE);
			}
			
			
			/**
			 * 工作类别为  退机 时, 机器是否正常服务  默认  停机, 备件原因  默认  否, 停机原因  默认  准备撤机, 是否退机  默认  是
			 * 机器是否正常服务  和  停机原因 	不用填写	系统为灰色
			 */
			if(listtype.contains("BACK_MACHINE")){
				workFinishFlag.setFieldTextAndValueFromValue("4");
				workFinishFlag.setFieldEnabled(false);
			//	isbjyy.setFieldTextAndValueFromValue("2");  //(2.2.14去掉是否备件原因)
				stopReason.setFieldTextAndValueFromValue("BACK_REASON");
				stopReason.setFieldEnabled(false);
				yesnotj.setFieldTextAndValueFromValue("1");
			}
			
			/**
			 * 工作类别为   客服报废  时, 机器是否正常服务  默认  停机, 备件原因  默认  否, 停机原因  默认  准备撤机, 是否退机  默认 否
			 * 机器是否正常服务  和  停机原因 	不用填写	系统为灰色
			 */
			if(listtype.contains("SCRAP_MACHINE")){
				workFinishFlag.setFieldTextAndValueFromValue("4");
				workFinishFlag.setFieldEnabled(false);
			//	isbjyy.setFieldTextAndValueFromValue("2");   //(2.2.14去掉是否备件原因)
				stopReason.setFieldTextAndValueFromValue("BACK_REASON");
				stopReason.setFieldEnabled(false);
				yesnotj.setFieldTextAndValueFromValue("0");
			}
			
			
			
			
			/**
			 * 工作类别为  停机时, 机器是否正常服务  默认  停机, 停机原因  必填;
			 */
			if(listtype.contains("STOP_MACHINE")){
				workFinishFlag.setFieldTextAndValueFromValue("4");
			}
					
			
		
//			
//			String str = "START, RECEIVE_MACHINE, SETUP_MACHINE, DEBUG, MOVE_MACHINE, SCRAP_MACHINE, BACK_MACHINE, STOP_MACHINE";
//			String[] strList = str.split(",");
//			boolean strflag = false;
//			for(int i = 0; i < strList.length; i++){
//					if(listtype.contains(strList[i])){
//						strflag = true;
//				}
//			}
//			
//			if(!strflag){
//				if(!KingTellerJudgeUtils.isEmpty(workFinishFlag.getFieldValue())){
//					workFinishFlag.setFieldTextAndValue("");
//				}
//				if(!KingTellerJudgeUtils.isEmpty(stopReason.getFieldValue())){
//					stopReason.setFieldTextAndValue("");
//				}
//				if(!KingTellerJudgeUtils.isEmpty(workTypeNewMove.getFieldValue())){
//					workTypeNewMove.setFieldTextAndValue("");
//				}
//			}
			
	}

	private void setChangeUI() {
		LinearLayout bj = (LinearLayout) ((KingTellerEditText) bj_group_list
				.getLayoutList().getTag()).getParent();
		((KingTellerEditText) bj.findViewById(R.id.installBjWlId))
				.setFieldTextAndValue("");
		((KingTellerEditText) bj.findViewById(R.id.downBjWlId))
				.setFieldTextAndValue("");
		((KingTellerEditText) bj.findViewById(R.id.troubleRemarkId))
				.setFieldTextAndValue("");
		((KingTellerEditText) bj.findViewById(R.id.handleSubId))
				.setFieldTextAndValue("");
		((KingTellerEditText) bj.findViewById(R.id.troubleModule))
				.setFieldTextAndValue("");
		((KingTellerEditText) bj.findViewById(R.id.otherDescription))
				.setFieldTextAndValue("");
		((LinearLayout) bj.findViewById(R.id.liearOther))
				.setVisibility(View.GONE);
		((LinearLayout) bj.findViewById(R.id.liearOther_troubleRemarkId))
		.setVisibility(View.GONE);
		((KingTellerEditText) bj.findViewById(R.id.troubleJqZd))
				.setFieldTextAndValue("");
		((KingTellerEditText) bj.findViewById(R.id.troubleYjPz))
				.setFieldEnabled(true);
		((KingTellerEditText) bj.findViewById(R.id.troubleYjPz))
				.setFieldTextAndValue("");
		((KingTellerEditText) bj.findViewById(R.id.changeSituation))
				.setFieldTextAndValue("");
		((KingTellerEditText) bj.findViewById(R.id.downBjWlId))
				.setFieldTextAndValue("");
	}

	private void setChangeUIGzbj() {
		LinearLayout bj = (LinearLayout) ((KingTellerEditText) bj_group_list
				.getLayoutList().getTag()).getParent();
		((KingTellerEditText) bj.findViewById(R.id.installBjWlId))
				.setFieldTextAndValue("");
		((KingTellerEditText) bj.findViewById(R.id.downBjWlId))
				.setFieldTextAndValue("");
		((KingTellerEditText) bj.findViewById(R.id.troubleRemarkId))
				.setFieldTextAndValue("");
		((KingTellerEditText) bj.findViewById(R.id.handleSubId))
				.setFieldTextAndValue("");
		((KingTellerEditText) bj.findViewById(R.id.otherDescription))
				.setFieldTextAndValue("");
		((KingTellerEditText) bj.findViewById(R.id.otherDescription_troubleRemarkId))
		.setFieldTextAndValue("");
		((LinearLayout) bj.findViewById(R.id.liearOther))
				.setVisibility(View.GONE);
		((LinearLayout) bj.findViewById(R.id.liearOther_troubleRemarkId))
		.setVisibility(View.GONE);
		((KingTellerEditText) bj.findViewById(R.id.troubleJqZd))
				.setFieldTextAndValue("");
		((KingTellerEditText) bj.findViewById(R.id.troubleYjPz))
				.setFieldTextAndValue("");
		((KingTellerEditText) bj.findViewById(R.id.changeSituation))
				.setFieldTextAndValue("");
		((KingTellerEditText) bj.findViewById(R.id.downBjWlId))
		.setFieldTextAndValue("");
	}

	private void setChangeUIRwxx() {
		LinearLayout bj = (LinearLayout) ((KingTellerEditText) bj_group_list
				.getLayoutList().getTag()).getParent();
		//((KingTellerEditText) bj.findViewById(R.id.installBjWlId))
		//		.setFieldTextAndValue("");
		//((KingTellerEditText) bj.findViewById(R.id.downBjWlId))
		//		.setFieldTextAndValue("");
		((KingTellerEditText) bj.findViewById(R.id.handleSubId))
				.setFieldTextAndValue("");
		((KingTellerEditText) bj.findViewById(R.id.otherDescription))
				.setFieldTextAndValue("");
		((KingTellerEditText) bj.findViewById(R.id.otherDescription_troubleRemarkId))
		.setFieldTextAndValue("");
		// ((LinearLayout)
		// bj.findViewById(R.id.liearOther)).setVisibility(View.GONE);
	}

	private void setChangeUIClgc() {
		LinearLayout bj = (LinearLayout) ((KingTellerEditText) bj_group_list
				.getLayoutList().getTag()).getParent();
		//((KingTellerEditText) bj.findViewById(R.id.installBjWlId))
		//		.setFieldTextAndValue("");
		//((KingTellerEditText) bj.findViewById(R.id.downBjWlId))
		//		.setFieldTextAndValue("");
		((KingTellerEditText) bj.findViewById(R.id.otherDescription))
				.setFieldTextAndValue("");
	}
	
	//选择机器字段时刷新
	private void setChangeUIJqzd(JqzdDataBean jqzdData, LinearLayout bj) {
		CommonSelectData csd = new CommonSelectData();
		//LinearLayout bj = (LinearLayout) ((KingTellerEditText) bj_group_list
		//	.getLayoutList().getTag()).getParent();
		
		csd.setText(jqzdData.getAttrName());
		csd.setValue(jqzdData.getAttrCode());
		((KingTellerEditText) bj.findViewById(R.id.troubleJqZd))
			.setFieldTextAndValue(csd);
		
		csd.setText(jqzdData.getAttrValue());
		csd.setValue(jqzdData.getAttrValueId());
		((KingTellerEditText) bj.findViewById(R.id.troubleYjPz))
			.setFieldTextAndValue(csd);
		
		if(KingTellerJudgeUtils.isEmpty(jqzdData.getAttrValueId())) {
			((KingTellerEditText) bj.findViewById(R.id.changeSituation))
			.setFieldTextAndValueFromValue("2");
		}else {
			((KingTellerEditText) bj.findViewById(R.id.changeSituation))
			.setFieldTextAndValueFromValue("0");
		}
		
		csd.setText(jqzdData.getAttrValue());
		csd.setValue(jqzdData.getAttrValueId());
		if(!"无".equals(csd.getText())) {
			((KingTellerEditText) bj.findViewById(R.id.downBjWlId))
			.setFieldTextAndValue(csd);
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
    	
    	KingTellerStaticConfig.QR_DOTMACHINE_REPLACE_NUM  = Jnum + Xnum;
    	
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
    
    
    private void getsftj() {
		if (!KingTellerJudgeUtils.isNetAvaliable(this)) {
			T.showShort(mContext, "没有网络, 请检查网络是否可用!");
			return;
		}
		
		KTHttpClient fh = new KTHttpClient(true);
		AjaxParams params = new AjaxParams();
		params.put("jqbh", machId.getFieldValue());

		fh.post(KingTellerConfigUtils.CreateUrl(this, KingTellerUrlConfig.SftjUrl), params,
				new AjaxHttpCallBack<ReturnBackStatus>(this, true) {

					@Override
					public void onStart() {
					}

					@Override
					public void onFinish() {
					}

					@Override
					public void onDo(ReturnBackStatus data) {
						tjdh.setFieldTextAndValue(data.getMessage());
					};
				});
	}

	private void getReportInfo() {

		if (!KingTellerJudgeUtils.isNetAvaliable(this)) {
			T.showShort(mContext, "没有网络, 请检查网络是否可用!");
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
						KingTellerProgressUtils.showProgress(mContext, "数据加载中...");
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
	
	//获取响应时间
	private void getReportXiangYingTime() {

		if (!KingTellerJudgeUtils.isNetAvaliable(this)) {
			T.showShort(mContext, "没有网络, 请检查网络是否可用!");
			return;
		}
		KTHttpClient fh = new KTHttpClient(true);
		AjaxParams params = new AjaxParams();
		params.put("jqid", reportdata.getMachineId()); //机器id
		params.put("reportId", reportdata.getReportId());//维护报告id
		params.put("workDate", reportdata.getWorkDate());//工作日期
		
		fh.post(KingTellerConfigUtils.CreateUrl(this, KingTellerUrlConfig.toUpdateMachineUrl), params,
				new AjaxHttpCallBack<JqxxDataBean>(this, true) {

					@Override
					public void onStart() {
						KingTellerProgressUtils.showProgress(mContext, "数据获取中...");
					}

					@Override
					public void onError(int errorNo, String strMsg) {
						super.onError(errorNo, strMsg);
						KingTellerProgressUtils.closeProgress();
						T.showShort(mContext, "数据访问超时!");
					}

					@Override
					public void onDo(final JqxxDataBean data) {
						KingTellerProgressUtils.closeProgress();
						
						if(!"error".equals(data.getResult())){
							final ReportEditDialog edit_dialog = new ReportEditDialog(mContext, R.style.Login_dialog);
							edit_dialog.setEditOnClickL(new EditOnClickL() {
								@Override
								public void EditOnClick(String str1, String str2) {
									edit_dialog.dismiss();
									
									if(!"".equals(str1) && !"".equals(str2)){
										setReportXiangYingTime(data.getJqid(), data.getReportId(), str1, str2); 
									}
								}
							});
							edit_dialog.show();
							
							edit_dialog.setEditStr(data.getBzxysj(), data.getBzwhsj());
						}else{
							final NormalDialog dialog_get_error = new NormalDialog(mContext);
				        	KingTellerPromptDialogUtils.showOnePromptDialog(dialog_get_error, data.getMessage(),
									new OnBtnClickL() {
										@Override
										public void onBtnClick() {
											dialog_get_error.dismiss();
										}
				                    });
						}
					};
				});
	}
	
	//设置响应时间
	private void setReportXiangYingTime(String jqid, String rpid, String xysj, String whsj) {

		if (!KingTellerJudgeUtils.isNetAvaliable(this)) {
			T.showShort(mContext, "没有网络, 请检查网络是否可用!");
			return;
		}

		KTHttpClient fh = new KTHttpClient(true);
		AjaxParams params = new AjaxParams();
		params.put("jqid", jqid); //机器id
		params.put("reportId", rpid);//工单id
		params.put("bzxysj", xysj);//响应时间
		params.put("bzwhsj", whsj);//维护时间
		
		fh.post(KingTellerConfigUtils.CreateUrl(this, KingTellerUrlConfig.doUpdateMachineUrl), params,
				new AjaxHttpCallBack<RepairReportBean>(this, true) {

					@Override
					public void onStart() {
						KingTellerProgressUtils.showProgress(mContext, "数据设置中...");
					}

					@Override
					public void onError(int errorNo, String strMsg) {
						super.onError(errorNo, strMsg);
						KingTellerProgressUtils.closeProgress();
						T.showShort(mContext, "数据访问超时!");
					}
					
					@Override
					public void onDo(RepairReportBean data) {
						KingTellerProgressUtils.closeProgress();
						
						if(!"error".equals(data.getResult())){
							T.showShort(mContext, "修改成功!");
							
							if (KingTellerJudgeUtils.isEmpty(data.getArriveOvertimeMin())) {
								arriveOvertimeMin.setVisibility(View.GONE);
							} else {
								arriveOvertimeMin.setFieldTextAndValue(data.getArriveOvertimeMin());//响应超时分钟
								if(!"0".equals(data.getArriveOvertimeMin())){
									arriveOverRemark.setVisibility(View.VISIBLE);//响应超时说明
								}else{
									arriveOverRemark.setVisibility(View.GONE);//响应超时说明
								}	
							}
							
							if (KingTellerJudgeUtils.isEmpty(data.getMaintainOvertimeMin())) {
								maintainOvertimeMin.setVisibility(View.GONE);
							} else {
								maintainOvertimeMin.setFieldTextAndValue(data.getMaintainOvertimeMin());//维护超时分钟
								if(!"0".equals(data.getMaintainOvertimeMin())){
									maintainOverRemark.setVisibility(View.VISIBLE);//维护超时说明
								}else{
									maintainOverRemark.setVisibility(View.GONE);//响应超时说明
								}	
							}
							
						}else{
							final NormalDialog dialog_set_error = new NormalDialog(mContext);
				        	KingTellerPromptDialogUtils.showOnePromptDialog(dialog_set_error, data.getMessage(),
									new OnBtnClickL() {
										@Override
										public void onBtnClick() {
											dialog_set_error.dismiss();
										}
				                    });
						}
					};
				});
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		KingTellerApplication.removeActivity(this);
	}

}
