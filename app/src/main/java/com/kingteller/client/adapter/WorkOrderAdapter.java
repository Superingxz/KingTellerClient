package com.kingteller.client.adapter;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kingteller.R;
import com.kingteller.client.activity.account.MainActivity;
import com.kingteller.client.activity.qrcode.QRDotMachineCargoScanActivict;
import com.kingteller.client.activity.qrcode.QRDotMachineReplaceCargoScanActivict;
import com.kingteller.client.activity.qrcode.QRServiceStationCargoScanActivity;
import com.kingteller.client.activity.workorder.CleanReportActivity;
import com.kingteller.client.activity.workorder.LogisticsReportActivity;
import com.kingteller.client.activity.workorder.OtherMatterReportActivity;
import com.kingteller.client.activity.workorder.RepairReportActivity;
import com.kingteller.client.activity.workorder.WorkOrderSearchActivity;
import com.kingteller.client.activity.workorder.checkout.LogisticsReportCheckoutActivity;
import com.kingteller.client.activity.workorder.checkout.OtherMatterReportCheckoutActivity;
import com.kingteller.client.activity.workorder.checkout.RepairReportCheckoutActivity;
import com.kingteller.client.bean.account.User;
import com.kingteller.client.bean.common.BasePageBean;
import com.kingteller.client.bean.common.CommonSelectData;
import com.kingteller.client.bean.qrcode.QRCargoScanBean;
import com.kingteller.client.bean.qrcode.QRServiceCheckBean;
import com.kingteller.client.bean.workorder.JqxxDataBean;
import com.kingteller.client.bean.workorder.ReturnBackStatus;
import com.kingteller.client.bean.workorder.WorkOrderBean;
import com.kingteller.client.bean.workorder.WorkorderMachineParamBean;
import com.kingteller.client.bean.workorder.XmBean;
import com.kingteller.client.bean.workorder.XmListBean;
import com.kingteller.client.config.KingTellerStaticConfig;
import com.kingteller.client.config.KingTellerUrlConfig;
import com.kingteller.client.netauth.AjaxHttpCallBack;
import com.kingteller.client.netauth.KTHttpClient;
import com.kingteller.client.utils.ConditionUtils;
import com.kingteller.client.utils.FloatWindowManager;
import com.kingteller.client.utils.KingTellerConfigUtils;
import com.kingteller.client.utils.KingTellerJudgeUtils;
import com.kingteller.client.utils.SQLiteHelper;
import com.kingteller.client.view.KTAlertDialog;
import com.kingteller.client.view.KingTellerEditText;
import com.kingteller.client.view.KingTellerEditText.OnChangeListener;
import com.kingteller.client.view.dialog.LoginSetUpDialog;
import com.kingteller.client.view.dialog.MachineInformationDialog;
import com.kingteller.client.view.dialog.NormalDialog;
import com.kingteller.client.view.dialog.listener.OnBtnClickL;
import com.kingteller.client.view.dialog.use.KingTellerProgressUtils;
import com.kingteller.client.view.dialog.use.KingTellerPromptDialogUtils;
import com.kingteller.client.view.toast.T;
import com.kingteller.framework.http.AjaxParams;

/**
 * 任务单适配器
 * 
 * @author jinyh
 * */

public class WorkOrderAdapter extends BaseAdapter {

	private LayoutInflater inflater;
	private List<WorkOrderBean> workOrderlist = new ArrayList<WorkOrderBean>();
	private Context mContext;
	private String tabName;
	private WorkOrderBean workOrder = new WorkOrderBean();
	private Callback callBack;
	private String backorderType;
	private String servType;

	
	public WorkOrderAdapter(Context context,List<WorkOrderBean> workOrderlist){
		inflater = LayoutInflater.from(context);
		this.mContext = context;
		this.workOrderlist = workOrderlist;

	}
	
	public WorkOrderAdapter(Context context, List<WorkOrderBean> workOrderlist, String tabName) {
		inflater = LayoutInflater.from(context);
		this.workOrderlist = workOrderlist;
		this.tabName = tabName;
		this.mContext = context;
	}

	public void setLists(List<WorkOrderBean> workOrderlist) {
		this.workOrderlist = workOrderlist;
		notifyDataSetChanged();
	}

	public void addLists(List<WorkOrderBean> workOrderlist) {
		this.workOrderlist.addAll(workOrderlist);
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		if (workOrderlist == null) {
			return 0;
		}
		return workOrderlist.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return workOrderlist.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}   

	@Override
	public View getView(final int postion, View v, ViewGroup parent) {
		// TODO Auto-generated method stub
		workOrder = workOrderlist.get(postion);
		
		ViewHoler viewHoler = null;
		if (v == null) {
			viewHoler = new ViewHoler();
			v = inflater.inflate(R.layout.item_workorder, null);
			viewHoler.orderNo = (TextView) v.findViewById(R.id.orderNo);//工单号
			viewHoler.orderType = (TextView) v.findViewById(R.id.orderType);//工单的类型
			viewHoler.wdsbjc = (TextView) v.findViewById(R.id.wdsbjc);//网点设备简称
			viewHoler.troubleRemark = (TextView) v.findViewById(R.id.troubleRemark);//问题描述
			viewHoler.assignName = (TextView) v.findViewById(R.id.assignName);//派单人
			viewHoler.assignTime = (TextView) v.findViewById(R.id.assignTime);//派单时间
			viewHoler.backflag = (TextView) v.findViewById(R.id.backflag);//退回状态
			viewHoler.prearrangeDateStr = (TextView) v.findViewById(R.id.prearrangeDateStr);//预约时间
			viewHoler.status = (TextView) v.findViewById(R.id.status);//工单状态
			
			viewHoler.atmMsg = (Button) v.findViewById(R.id.atmMsg);//机器信息
			viewHoler.acceptMalf = (Button) v.findViewById(R.id.acceptMalf); //接障
			viewHoler.machineScan = (Button) v.findViewById(R.id.machineScan);//二维码录入
			viewHoler.dotmachine = (Button) v.findViewById(R.id.dotmachine);//部件更换扫描
			viewHoler.backMalf = (Button) v.findViewById(R.id.backMalf);//退单
			viewHoler.reportFill = (Button) v.findViewById(R.id.fillReport);//填写报告
			
			v.setTag(viewHoler);
		}else{
			viewHoler = (ViewHoler)v.getTag();
		}
		
		hideBtn(viewHoler);
		
		if(tabName.equals("tab1") || tabName.equals("tab2")){//切换背景
			v.setBackgroundResource(R.drawable.btn_layout_off_bg);
		}else if(tabName.equals("tab3")){
			v.setBackgroundResource(R.drawable.btn_layout_on_bg);
		}
		
		
		final String orderId = workOrder.getOrderId();				//工单ID
		final String orderNo = workOrder.getOrderNo();				//工单号
		final String orderType = workOrder.getOrderType();			//工单的类型
		
		final String assignName = workOrder.getAssignName();		//派单人
		final String assignTime = workOrder.getAssignTime();		//派单时间
		final String troubleRemark = workOrder.getTroubleRemark();	//问题描述
		
		
		final String atmCode = workOrder.getATMCode();				//机器编号(御银为机器编的号)
		final String atmBankcode = workOrder.getATMBankcode();		//ATM号(银行为机器编的号)
		
		String atmMsg = "";//ATM信息		
		if(KingTellerJudgeUtils.isEmpty(atmBankcode)){
			atmMsg = atmCode; 
		}else{
			atmMsg = atmCode + "(" + atmBankcode + ")";
		}
		
		final String status = workOrder.getStatus();				//工单状态
		final String createType = workOrder.getCreateType();		//派单类别
		final String autoorderBanktype = workOrder.getAutoorderBanktype();//自动派单的银行类别
		final String reportFlowStatus = workOrder.getReportFlowStatus();//审核人类型
		
		final String wdId = workOrder.getWdid();					//网点id
		final String wdSbJc = workOrder.getWdsbjc();				//网点设备简称
		final String workOrgName = workOrder.getWorkOrgName();		//服务站名
		final String jqId = workOrder.getJqId();					//机器id
 
		final Long backFlag = workOrder.getBackflag();//退回状态
		final String arrangeType = workOrder.getArrangeType();//是否预约
		
		final String allright = User.getInfo(mContext).getRight();
		
		viewHoler.orderNo.setText(orderNo);
		viewHoler.atmMsg.setText(atmMsg);
		viewHoler.wdsbjc.setText(wdSbJc);
		
		viewHoler.troubleRemark.setText(troubleRemark);
		viewHoler.assignName.setText(assignName);
		viewHoler.assignTime.setText(assignTime);
		
		if(createType.equals("XM")){	//工单的类型
			viewHoler.orderType.setText("[项目]");
		}else{
			viewHoler.orderType.setText("["+getOrderType(orderType)+"]");
		}
		
		if(arrangeType.equals("1")){	//是否预约
			viewHoler.prearrangeDateStr.setText(workOrder.getPrearrangeDateStr());
			((LinearLayout)viewHoler.prearrangeDateStr.getParent()).setVisibility(View.VISIBLE);
		}else{
			((LinearLayout)viewHoler.prearrangeDateStr.getParent()).setVisibility(View.GONE);
		}
		
		if(backFlag > 0){				//是否退回
			viewHoler.backflag.setVisibility(View.VISIBLE);
		}else{
			viewHoler.backflag.setVisibility(View.GONE);
		}
		
		if(!"finish".equals(status)) {

			//机器信息 —— 点击事件
			viewHoler.atmMsg.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View arg0) {
					
					getJqXxData(atmCode);
				}
			});
			
			
//			acceptMalf //接障
//			machineScan//二维码录入
//			dotmachine//部件更换扫描
//			backMalf//退单
//			reportFill//填写报告

			viewHoler.acceptMalf.setVisibility(View.VISIBLE);
			if(orderType.equals("maintenance")){ //维护工单

				if(allright.contains("MOBILE_EWM_WDJQ_SM")){
					viewHoler.machineScan.setVisibility(View.VISIBLE);
				}
				if(allright.contains("MOBILE_EWM_WDJQGH_SM")){
					viewHoler.dotmachine.setVisibility(View.VISIBLE);
				}

				if("new".equals(status)){
					viewHoler.acceptMalf.setText("接障");
					if(createType.equals("P")){
						viewHoler.backMalf.setVisibility(View.VISIBLE);
					}else{
						viewHoler.backMalf.setVisibility(View.GONE);
					}
				}else if("accept".equals(status)){
					viewHoler.acceptMalf.setText("到达现场");
					if(createType.equals("P")){
						viewHoler.backMalf.setVisibility(View.VISIBLE);
					}else{
						viewHoler.backMalf.setVisibility(View.GONE);
					}
				}else if("arrive".equals(status)){
					viewHoler.acceptMalf.setText("开始维护");
					viewHoler.backMalf.setVisibility(View.GONE);
				}else if("begin".equals(status)){
					viewHoler.acceptMalf.setText("维护结束");
					viewHoler.backMalf.setVisibility(View.GONE);
				}
				
			}else if(workOrder.getOrderType().equals("otherMatter")){//其他事务
				viewHoler.backMalf.setVisibility(View.GONE);
				viewHoler.machineScan.setVisibility(View.GONE);
				viewHoler.dotmachine.setVisibility(View.GONE);
				
				if( "new".equals(status)){
					viewHoler.acceptMalf.setText("确认接收");
				}else if("accept".equals(status)){
					viewHoler.acceptMalf.setText("开始执行");
				}else if("begin".equals(status)){
					viewHoler.acceptMalf.setText("完成事务");
				}
				
			}else if(orderType.equals("logistics")){//物流
				viewHoler.backMalf.setVisibility(View.GONE);
				viewHoler.machineScan.setVisibility(View.GONE);
				viewHoler.dotmachine.setVisibility(View.GONE);
				
				if( "new".equals(status)){
					viewHoler.acceptMalf.setText("确认接收");
				}else if("accept".equals(status)){
					viewHoler.acceptMalf.setText("到达现场");
				}else if("arrive".equals(status)){
					viewHoler.acceptMalf.setText("装成完毕");
				}else if("begin".equals(status)){
					viewHoler.acceptMalf.setText("物流完成");
				}
				
			}else if(orderType.equals("clean")){//清洁
				viewHoler.backMalf.setVisibility(View.GONE);
				viewHoler.machineScan.setVisibility(View.GONE);
				viewHoler.dotmachine.setVisibility(View.GONE);
				
				if( "new".equals(status)){
					viewHoler.acceptMalf.setText("接障");
				}else if("accept".equals(status)){
					viewHoler.acceptMalf.setText("到达现场");
				}else if("arrive".equals(status)){
					viewHoler.acceptMalf.setText("开始维护");
				}else if("begin".equals(status)){
					viewHoler.acceptMalf.setText("维护结束");
				}
			}
	
			//我的工单 —— 接障事件
			viewHoler.acceptMalf.setOnClickListener( new View.OnClickListener() {
				@Override
				public void onClick(View arg0) {
					if(((Button)arg0).getText().toString().equals("到达现场") && orderType.equals("maintenance")){
						if(createType.equals("XM")){
							getFhJqXxData(0, jqId, createType, orderId, status, orderType);
						}else{
							getFhJqXxData(1, jqId, createType, orderId, status, orderType);
						}
					}else if(((Button)arg0).getText().toString().equals("接障") && orderType.equals("maintenance")){
						getFhJqXxData(2, jqId, createType, orderId, status, orderType);
					}else if(((Button)arg0).getText().toString().equals("维护结束") && orderType.equals("maintenance")){ //附加 工单提示
						setEndMaintenanceTips(atmCode, jqId, orderNo, workOrgName, wdId, wdSbJc, assignTime, ((Button)arg0).getText().toString(), orderId, status, orderType);
					}else{
						OpenWorkDialog(5, null, ((Button)arg0).getText().toString(), orderId, status, orderType);
					}
			
				}
			});
			
			//退单事件
			viewHoler.backMalf.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View arg0) {
					if( !KingTellerJudgeUtils.isEmpty(autoorderBanktype) && "SDYZ".equals(autoorderBanktype)){
						OpenWorkDialog(6, null, "", orderId, status, orderType);
					}else{
						OpenWorkDialog(7, null, "", orderId, status, orderType);
					}

				}
			});

			//网点机器扫描 —— 网点机器扫描事件
			viewHoler.machineScan.setOnClickListener( new View.OnClickListener() {
				@Override
				public void onClick(View arg0) {
					startDotMachine(atmCode, jqId, workOrgName, wdId, wdSbJc, "2");
				}
			});
			
			//网点机器部件更换扫描 —— 网点机器部件更换扫描事件
			viewHoler.dotmachine.setOnClickListener( new View.OnClickListener() {
				@Override
				public void onClick(View arg0) {
					startDotMachineReplace(atmCode, jqId, orderNo, orderId, workOrgName, wdId, wdSbJc, assignTime); 
				}
			});
		
		}else {
			if("10".equals(reportFlowStatus) || "50".equals(reportFlowStatus)) {

				
				viewHoler.atmMsg.setOnClickListener(new View.OnClickListener() {//ATM信息
					@Override
					public void onClick(View arg0) {
					
						getJqXxData(atmCode);
						
					}
				});
				
				viewHoler.reportFill.setVisibility(View.VISIBLE);
				viewHoler.reportFill.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View arg0) {
						
						Intent intent = new Intent();
						intent.putExtra("reportBean", workOrderlist.get(postion));
						intent.putExtra("backflag", workOrderlist.get(postion).getBackflag());
						if("maintenance".equals(workOrderlist.get(postion).getOrderType())){
							intent.setClass(mContext, RepairReportActivity.class);
						}else if("otherMatter".equals(workOrderlist.get(postion).getOrderType())){
							intent.setClass(mContext, OtherMatterReportActivity.class);	
						}else if("logistics".equals(workOrderlist.get(postion).getOrderType())){
							intent.setClass(mContext, LogisticsReportActivity.class);
						}else if("clean".equals(workOrderlist.get(postion).getOrderType())){
							intent.setClass(mContext, CleanReportActivity.class);
						}
						if("".equals(tabName)) {
							((WorkOrderSearchActivity)mContext).startActivityForResult(intent, 1);
						}else {
							mContext.startActivity(intent);
						}
					} 
				});
				
				//网点机器部件扫描 —— 网点机器部件扫描事件
				if(allright.contains("MOBILE_EWM_WDJQGH_SM") && orderType.equals("maintenance")){
					viewHoler.dotmachine.setVisibility(View.VISIBLE);
				}else{
					viewHoler.dotmachine.setVisibility(View.GONE);
				}
				
				
				viewHoler.dotmachine.setOnClickListener( new View.OnClickListener() {
					@Override
					public void onClick(View arg0) {
						startDotMachineReplace(atmCode, jqId, orderNo, orderId, workOrgName, wdId, wdSbJc, assignTime); 
					}
				});
			
			}else {

				viewHoler.backflag.setVisibility(View.VISIBLE);
				viewHoler.backflag.setTextSize(10.0f);
				if(reportFlowStatus.equals("20")){
					viewHoler.backflag.setText("服务站负责人审核中");
				}else if(reportFlowStatus.equals("30")){
					viewHoler.backflag.setText("信息管理员审核中");
				}
				
				viewHoler.atmMsg.setOnClickListener(new View.OnClickListener() {//ATM信息
					@Override
					public void onClick(View arg0) {
						getJqXxData(atmCode);
					}
				});
				
				v.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View arg0) {//item事件
						OpenDialog("", "您确定要查看报告吗？", postion,  0);
					}
				});
				
			
			}
			
		}
		return v;
	}
	
	
	/**
	 * 打开dialog
	 * @param msg
	 * @param num
	 * @param str
	 * @param type  0：查看报告   1：机器信息
	 */
	public void OpenDialog (String title, String msg, final int num, int type){
		switch (type) {
			case 0://查看报告 dialog
				
				final NormalDialog dialog_ckbg = new NormalDialog(mContext);
	        	KingTellerPromptDialogUtils.showTwoPromptDialog(dialog_ckbg, msg,
						new OnBtnClickL() {
							@Override
							public void onBtnClick() {
								dialog_ckbg.dismiss();
							}
	                    }, new OnBtnClickL() {
							@Override
							public void onBtnClick() {
								dialog_ckbg.dismiss();
								
								Intent intent = new Intent();
								intent.putExtra("reportBean", workOrderlist.get(num));
								intent.putExtra("isCheckout", "checkout");
								intent.putExtra("backflag", workOrderlist.get(num).getBackflag());
								
								Log.e("workOrderlist---", workOrderlist.get(num).getOrderType());
								
								if("maintenance".equals(workOrderlist.get(num).getOrderType())){
									intent.setClass(mContext, RepairReportCheckoutActivity.class);
								}else if("otherMatter".equals(workOrderlist.get(num).getOrderType())){
									intent.setClass(mContext, OtherMatterReportCheckoutActivity.class);	
								}else if("logistics".equals(workOrderlist.get(num).getOrderType())){
									intent.setClass(mContext, LogisticsReportCheckoutActivity.class);
								}else if("clean".equals(workOrderlist.get(num).getOrderType())){
									intent.setClass(mContext, CleanReportActivity.class);
								}
								mContext.startActivity(intent);
							}
	                    });
	
				break;
			case 1://机器信息 dialog
				
				new MachineInformationDialog(mContext, R.style.Login_dialog, msg).show();// 设置服务器
				
				break;
			default:
				break;
		}
	}
	
	private void hideBtn(ViewHoler viewHoler) {
//		acceptMalf //接障
//		machineScan//二维码录入
//		dotmachine//部件更换扫描
//		backMalf//退单
//		reportFill//填写报告
		viewHoler.reportFill.setVisibility(View.GONE);
		viewHoler.acceptMalf.setVisibility(View.GONE);
		viewHoler.machineScan.setVisibility(View.GONE);
		viewHoler.dotmachine.setVisibility(View.GONE);
	}
	
	/**
	 * 打开工单选择dialog
	 */
	public void OpenWorkDialog (int type, BasePageBean<WorkorderMachineParamBean> data, 
			final String createType, final String orderId, final String status, final String orderType){
		switch (type) {
		case 0://服务方式 
			Log.e("服务方式--", "服务方式");
			View layout_arrivexc = inflater.inflate(R.layout.item_layout_arrivexc, null);
			final Dialog dialog_arrivexc = new AlertDialog.Builder(mContext, R.style.Login_dialog).create();
			dialog_arrivexc.setCanceledOnTouchOutside(false);
			dialog_arrivexc.show();
			dialog_arrivexc.getWindow().setContentView(layout_arrivexc);
			WindowManager.LayoutParams lp_arrivexc = dialog_arrivexc.getWindow().getAttributes();
			lp_arrivexc.width = (int) (KingTellerStaticConfig.SCREEN.Width * 0.85); // 宽度设置为屏幕的0.85
	        dialog_arrivexc.getWindow().setAttributes(lp_arrivexc);


			RadioGroup group_arrivexc = (RadioGroup) layout_arrivexc.findViewById(R.id.radioGroup);
			Button submit_arrivexc = (Button) layout_arrivexc.findViewById(R.id.submit);
			Button cancel_arrivexc = (Button) layout_arrivexc.findViewById(R.id.cancel);
			
			servType = "";
			
			group_arrivexc.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
				@Override
				public void onCheckedChanged(RadioGroup group,int checkedId) {
					if (checkedId == R.id.radioButton1) {
						servType = "SITE_SERV";
						dialog_arrivexc.findViewById(R.id.backorderType).setVisibility(View.GONE);
					}else if(checkedId == R.id.radioButton2){
						servType = "TEL_SERV";
						dialog_arrivexc.findViewById(R.id.backorderType).setVisibility(View.VISIBLE);
					}else if(checkedId == R.id.radioButton3){
						servType = "OTHER_SERV";
						dialog_arrivexc.findViewById(R.id.backorderType).setVisibility(View.VISIBLE);
					}
				}
			});
			
			submit_arrivexc.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View arg0) {
					if(KingTellerJudgeUtils.isEmpty(servType)){
						T.showShort(mContext, "必须选择一项!");
						return;
					}
					submitStatus(orderId, status, orderType, servType);
					dialog_arrivexc.dismiss();
					
				}
			});
			
			cancel_arrivexc.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View arg0) {
					dialog_arrivexc.dismiss();
				}
			});
			break;
		case 1://操作选择
			Log.e("操作选择--", "操作选择");
			View layout_jqidsm = inflater.inflate(R.layout.item_layout_jqidsm, null);
			final Dialog dialog_jqidsm = new AlertDialog.Builder(mContext, R.style.Login_dialog).create();
			dialog_jqidsm.setCanceledOnTouchOutside(false);
			dialog_jqidsm.show();
			dialog_jqidsm.getWindow().setContentView(layout_jqidsm);
			dialog_jqidsm.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
			WindowManager.LayoutParams lp_jqidsm = dialog_jqidsm.getWindow().getAttributes();
			lp_jqidsm.width = (int) (KingTellerStaticConfig.SCREEN.Width * 0.85); // 宽度设置为屏幕的0.85
			dialog_jqidsm.getWindow().setAttributes(lp_jqidsm);

			
			final ListView listView_jqidsm = (ListView) layout_jqidsm.findViewById(R.id.listView);
			JqIdXxAdapter jixAdapter_jqidsm = new JqIdXxAdapter(mContext, data.getList());
			listView_jqidsm.setAdapter(jixAdapter_jqidsm);
			
			final int count_jqidsm = data.getList().size();
			((TextView)layout_jqidsm.findViewById(R.id.xmcount)).setText(""+count_jqidsm);
			
			Button submit_jqidsm = (Button) layout_jqidsm.findViewById(R.id.submit);
			Button cancel_jqidsm = (Button) layout_jqidsm.findViewById(R.id.cancel);
			
			submit_jqidsm.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View arg0) {

					XmListBean xmList = new XmListBean();
					List<XmBean> xl = new ArrayList<XmBean>();
					XmBean xm = null;
					//int count = listView_jqidsm.getChildCount();
					for(int i =0;i < count_jqidsm;i ++){
						xm = new XmBean();
						xm.setXmid(((TextView)listView_jqidsm.getChildAt(i).findViewById(R.id.xmId)).getText().toString());
						KingTellerEditText choose1 = (KingTellerEditText)listView_jqidsm.getChildAt(i).findViewById(R.id.choose1);
						if(KingTellerJudgeUtils.isEmpty(choose1.getFieldValue())){
							T.showShort(mContext, "操作选项必须选择!");
							return;
						}
						xm.setOper(choose1.getFieldValue());
						if(((EditText)listView_jqidsm.getChildAt(i).findViewById(R.id.editText1)).getVisibility() == View.VISIBLE
								&& KingTellerJudgeUtils.isEmpty(((EditText)listView_jqidsm.getChildAt(i).findViewById(R.id.editText1)).getText().toString())) {
							T.showShort(mContext, "无需执行需要填写原因！");
							return;
						}
						xm.setUnexe(((EditText)listView_jqidsm.getChildAt(i).findViewById(R.id.editText1)).getText().toString());
						xl.add(xm);
					}
					xmList.setXmlist(xl);
					xmList.setXmct(createType);
					
					submitXmStatus(orderId, status, orderType, "", xmList);
					
					dialog_jqidsm.dismiss();
					
				}
			});
			
			cancel_jqidsm.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View arg0) {
					dialog_jqidsm.dismiss();
				}
			});
		
			break;
		case 2://服务方式 + 操作选择
			Log.e("服务方式 +操作选择--", "服务方式 +操作选择");
			View layout_arrivejqidxx = inflater.inflate(R.layout.item_layout_arrivejqidxx, null);
			final Dialog dialog_arrivejqidxx = new AlertDialog.Builder(mContext, R.style.Login_dialog).create();
			dialog_arrivejqidxx.setCanceledOnTouchOutside(false);
			dialog_arrivejqidxx.show();
			dialog_arrivejqidxx.getWindow().setContentView(layout_arrivejqidxx);
			dialog_arrivejqidxx.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
			WindowManager.LayoutParams lp_arrivejqidxx = dialog_arrivejqidxx.getWindow().getAttributes();
			lp_arrivejqidxx.width = (int) (KingTellerStaticConfig.SCREEN.Width * 0.85); // 宽度设置为屏幕的0.85
			dialog_arrivejqidxx.getWindow().setAttributes(lp_arrivejqidxx);
			
			final ListView listView_arrivejqidxx = (ListView) layout_arrivejqidxx.findViewById(R.id.listView);
			JqIdXxAdapter jixAdapter_arrivejqidxx = new JqIdXxAdapter(mContext, data.getList());
			
			listView_arrivejqidxx.setAdapter(jixAdapter_arrivejqidxx);
			//FloatWindowManager.setListViewHeightBasedOnChildren(listView_arrivejqidxx);
			
			final int count_arrivejqidxx = data.getList().size();
			((TextView)layout_arrivejqidxx.findViewById(R.id.xmcount)).setText(""+count_arrivejqidxx);
			
			RadioGroup group_arrivejqidxx = (RadioGroup) layout_arrivejqidxx.findViewById(R.id.radioGroup);
			final Button submit_arrivejqidxx = (Button) layout_arrivejqidxx.findViewById(R.id.submit);
			final Button cancel_arrivejqidxx = (Button) layout_arrivejqidxx.findViewById(R.id.cancel);
			
			servType = "";
			group_arrivejqidxx.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
				@Override
				public void onCheckedChanged(RadioGroup group,int checkedId) {
					if (checkedId == R.id.radioButton1) {
						servType = "SITE_SERV";
						dialog_arrivejqidxx.findViewById(R.id.backorderType).setVisibility(View.GONE);
						
					}else if(checkedId == R.id.radioButton2){
						servType = "TEL_SERV";
						dialog_arrivejqidxx.findViewById(R.id.backorderType).setVisibility(View.VISIBLE);
						
						
					}else if(checkedId == R.id.radioButton3){
						servType = "OTHER_SERV";
						dialog_arrivejqidxx.findViewById(R.id.backorderType).setVisibility(View.VISIBLE);
						
					}
				}
			});
			
			submit_arrivejqidxx.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					if(KingTellerJudgeUtils.isEmpty(servType)){
						T.showShort(mContext, "必须选择一项!");
						return;
					}
					XmListBean xmList = new XmListBean();
					List<XmBean> xl = new ArrayList<XmBean>();
					XmBean xm = null;
					//int count = listView_arrivejqidxx.getChildCount();
					for(int i =0;i < count_arrivejqidxx;i ++){
						xm = new XmBean();
						xm.setXmid(((TextView)listView_arrivejqidxx.getChildAt(i).findViewById(R.id.xmId)).getText().toString());
						KingTellerEditText choose1 = (KingTellerEditText)listView_arrivejqidxx.getChildAt(i).findViewById(R.id.choose1);
						if(KingTellerJudgeUtils.isEmpty(choose1.getFieldValue())){
							T.showShort(mContext, "操作选项必须选择!");
							return;
						}
						if("2".equals(choose1.getFieldValue()) && "TEL_SERV".equals(servType)) {
							T.showShort(mContext, "操作有 确认执行 情况下不能选择 电话服务");
							return;
						}
						xm.setOper(choose1.getFieldValue());
						if(((EditText)listView_arrivejqidxx.getChildAt(i).findViewById(R.id.editText1)).getVisibility() == View.VISIBLE
								&& KingTellerJudgeUtils.isEmpty(((EditText)listView_arrivejqidxx.getChildAt(i).findViewById(R.id.editText1)).getText().toString())) {
							T.showShort(mContext, "无需执行需要填写原因！");
							return;
						}
						xm.setUnexe(((EditText)listView_arrivejqidxx.getChildAt(i).findViewById(R.id.editText1)).getText().toString());
						xl.add(xm);
					}
					xmList.setXmlist(xl);
					xmList.setXmct(createType);

					submitXmStatus(orderId, status, orderType, servType, xmList);
					
					dialog_arrivejqidxx.dismiss();
					
				}
			});
			
			cancel_arrivejqidxx.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View arg0) {
					dialog_arrivejqidxx.dismiss();
				}
			});
			
			break;
		case 3://维护报告普通接障
			
			final NormalDialog dialog_jz = new NormalDialog(mContext);
        	KingTellerPromptDialogUtils.showTwoPromptDialog(dialog_jz, "您确定要接障吗？",
					new OnBtnClickL() {
						@Override
						public void onBtnClick() {
							dialog_jz.dismiss();
						}
                    }, new OnBtnClickL() {
						@Override
						public void onBtnClick() {
							dialog_jz.dismiss();
							submitStatus(orderId, status, orderType, "");
						}
                    });

			break;
		case 4://维护报告有提示信息接障
			
			View layout_jqxxjz = inflater.inflate(R.layout.item_jqxx, null);
			final Dialog dialog_jqxxjz = new AlertDialog.Builder(mContext, R.style.Login_dialog).create();
			dialog_jqxxjz.setCanceledOnTouchOutside(false);
			dialog_jqxxjz.show();
			dialog_jqxxjz.getWindow().setContentView(layout_jqxxjz);
			WindowManager.LayoutParams lp_jqxxjz = dialog_jqxxjz.getWindow().getAttributes();
			lp_jqxxjz.width = (int) (KingTellerStaticConfig.SCREEN.Width * 0.85); // 宽度设置为屏幕的0.85
			dialog_jqxxjz.getWindow().setAttributes(lp_jqxxjz);
			
			ListView listView_jqxxjz = (ListView) layout_jqxxjz.findViewById(R.id.listView);
			JqIdAdapter jqIdAdapter_jqxxjz = new JqIdAdapter(mContext, data.getList());
			listView_jqxxjz.setAdapter(jqIdAdapter_jqxxjz);
			
			((TextView)layout_jqxxjz.findViewById(R.id.textView)).setText("您确定要接障吗?");
			
			((Button)layout_jqxxjz.findViewById(R.id.submit)).setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View arg0) {
					dialog_jqxxjz.dismiss();
					submitStatus(orderId, status, orderType, "");
				}
			});
			
			((Button)layout_jqxxjz.findViewById(R.id.cancel)).setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View arg0) {
					dialog_jqxxjz.dismiss();
				}
			});
			break;
		case 5://其他接障
			//如果是接障,查询是否存在对应机器的项目工单]
			final NormalDialog dialog_qtjz = new NormalDialog(mContext);
        	KingTellerPromptDialogUtils.showTwoPromptDialog(dialog_qtjz, "您确定要" + createType + "吗？",
					new OnBtnClickL() {
						@Override
						public void onBtnClick() {
							dialog_qtjz.dismiss();
						}
                    }, new OnBtnClickL() {
						@Override
						public void onBtnClick() {
							dialog_qtjz.dismiss();
							submitStatus(orderId, status, orderType, "");
						}
                    });
        	
			break;
		case 6://退单 1
			
			View layout_sdyz = inflater.inflate(R.layout.item_layout_sdyz, null);
			final Dialog dialog_sdyz = new AlertDialog.Builder(mContext, R.style.Login_dialog).create();
			dialog_sdyz.setCanceledOnTouchOutside(false);
			dialog_sdyz.show();
			dialog_sdyz.getWindow().setContentView(layout_sdyz);
			dialog_sdyz.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
			WindowManager.LayoutParams lp_sdyz = dialog_sdyz.getWindow().getAttributes();
			lp_sdyz.width = (int) (KingTellerStaticConfig.SCREEN.Width * 0.85); // 宽度设置为屏幕的0.85
			dialog_sdyz.getWindow().setAttributes(lp_sdyz);
			
			final KingTellerEditText tdyy_sdyz = (KingTellerEditText)dialog_sdyz.findViewById(R.id.tdyy);
			tdyy_sdyz.setLists(getTdyy("402881f04a606d45014a608ad6210009"));
			
			final EditText editText_sdyz = (EditText)layout_sdyz.findViewById(R.id.backorderEditText);
			Button submit_sdyz = (Button) layout_sdyz.findViewById(R.id.submit);
			Button cancel_sdyz = (Button) layout_sdyz.findViewById(R.id.cancel);
			
			final RelativeLayout relativeLayout_sdyz = (RelativeLayout) layout_sdyz.findViewById(R.id.relativeLayout);
			
			tdyy_sdyz.setOnChangeListener(new OnChangeListener() {
				
				@Override
				public void onChanged(CommonSelectData data) {	
					if(data.getText().trim().equals("其他")){
						relativeLayout_sdyz.setVisibility(View.VISIBLE);
					}else{
						relativeLayout_sdyz.setVisibility(View.GONE);
					}
				}
			});
			
			submit_sdyz.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View arg0) {
					if(KingTellerJudgeUtils.isEmpty(tdyy_sdyz.getFieldValue())){
						T.showShort(mContext, "退单原因必须选择!");
						return;
					}
					if( tdyy_sdyz.getFieldText().trim().equals("其他") && KingTellerJudgeUtils.isEmpty(editText_sdyz.getText().toString().trim())){
						T.showShort(mContext, "退单说明不能为空!");
						return;
					}
					dialog_sdyz.dismiss();
					
					submitTd(orderId, status, orderType, tdyy_sdyz.getFieldValue(),
							KingTellerJudgeUtils.isEmpty(editText_sdyz.getText().toString().trim())? "":editText_sdyz.getText().toString().trim());
					
				}
			});
			
			cancel_sdyz.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View arg0) {
					dialog_sdyz.dismiss();
				}
			});
			
			break;
		case 7://退单 2
			
			View layout_backworkorder = inflater.inflate(R.layout.item_layout_backworkorder, null);
			final Dialog dialog_backworkorder = new AlertDialog.Builder(mContext, R.style.Login_dialog).create();
			dialog_backworkorder.setCanceledOnTouchOutside(false);
			dialog_backworkorder.show();
			dialog_backworkorder.getWindow().setContentView(layout_backworkorder);
			dialog_backworkorder.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
			WindowManager.LayoutParams lp_backworkorder = dialog_backworkorder.getWindow().getAttributes();
			lp_backworkorder.width = (int) (KingTellerStaticConfig.SCREEN.Width * 0.85); // 宽度设置为屏幕的0.85
			dialog_backworkorder.getWindow().setAttributes(lp_backworkorder);
			
			final EditText editText_backworkorder = (EditText)layout_backworkorder.findViewById(R.id.backorderEditText);
			RadioGroup group_backworkorder = (RadioGroup) layout_backworkorder.findViewById(R.id.radioGroup);
			Button submit_backworkorder = (Button) layout_backworkorder.findViewById(R.id.submit);
			Button cancel_backworkorder = (Button) layout_backworkorder.findViewById(R.id.cancel);
			
			backorderType = "";
			
			group_backworkorder.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
				@Override
				public void onCheckedChanged(RadioGroup group,int checkedId) {
					if (checkedId == R.id.radioButton1) {
						backorderType = "noerr";
					}else if(checkedId == R.id.radioButton2){
						backorderType = "assignerr";
					}else if(checkedId == R.id.radioButton3){
						backorderType = "noerr_repeat";
					}
				}
			});
			
			submit_backworkorder.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View arg0) {
					if(KingTellerJudgeUtils.isEmpty(backorderType)){
						T.showShort(mContext, "退单原因必须选择!");
						return;
					}
					if(KingTellerJudgeUtils.isEmpty(editText_backworkorder.getText().toString())){
						T.showShort(mContext, "退单说明不能为空!");
						return;
					}
					
					dialog_backworkorder.dismiss();
					submitTd(orderId, status, orderType, backorderType, editText_backworkorder.getText().toString().trim());
					
				}
			});
			
			cancel_backworkorder.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					dialog_backworkorder.dismiss();
				}
			});
			break;
		default:
			break;
		}
	}
	
	
	public void getFhJqXxData(final int type, String jqId, final String createType, final String orderId, final String status, final String orderType){
		KTHttpClient fh = new KTHttpClient(true);
		AjaxParams params = new AjaxParams();
		params.put("jqId", jqId);

		fh.post(KingTellerConfigUtils.CreateUrl(mContext, KingTellerUrlConfig.Fhjqxx),
				params, new AjaxHttpCallBack<BasePageBean<WorkorderMachineParamBean>>(mContext,
						new TypeToken<BasePageBean<WorkorderMachineParamBean>>() {}.getType(), false) {
					
					@Override
					public void onDo(BasePageBean<WorkorderMachineParamBean> data) {
						switch (type) {
						/**--到达现场--**/
						case 0://XM
							if("".equals(data.getStatus()) ){
								if(data.getList() == null){
									
								}else if(data.getList().size() > 0){
									OpenWorkDialog (1, data,  createType,  orderId, status, orderType);
								}
							}else{
								T.showShort(mContext, "获取数据失败!");
							}
							break;
							
						/**--到达现场--**/
						case 1://GD
							if("".equals(data.getStatus()) ){
								if(data.getList() == null){
									OpenWorkDialog (0, data,  createType,  orderId, status, orderType);
								}else if(data.getList().size() > 0){
									OpenWorkDialog (2, data,  createType,  orderId, status, orderType);
								}
							}else{
								T.showShort(mContext, "获取数据失败!");
							}
							break;
							
						/**--接障--**/
						case 2://JZ
							if("".equals(data.getStatus()) ){
								if(data.getList() == null){
									OpenWorkDialog (3, data,  createType,  orderId, status, orderType);
								}else if(data.getList().size() > 0){
									OpenWorkDialog (4, data,  createType,  orderId, status, orderType);
								}else{
									OpenWorkDialog (3, data,  createType,  orderId, status, orderType);
								}
							}else{
								T.showShort(mContext, "获取数据失败!");
							}
							break;
						case 3:
							
							break;	
						default:
							break;
						}
					}
		});
	}
	
	
	public void setEndMaintenanceTips(final String JqBm, final String JqId, final String Gddh, final String SsFwz, final String WdId, final String WdMc, final String Pdsj,
			final String createType, final String orderId, final String status, final String orderType){
		KTHttpClient fh = new KTHttpClient(true);
		AjaxParams params = new AjaxParams();
		params.put("orderId", orderId);
		
		fh.post(KingTellerConfigUtils.CreateUrl(mContext, KingTellerUrlConfig.WebRwdztJsWhTsUrl),
				params, new AjaxHttpCallBack<ReturnBackStatus>(mContext,
						new TypeToken<ReturnBackStatus>() {}.getType(), false) {
			
					@Override
					public void onStart() {
						KingTellerProgressUtils.showProgress(mContext, "正在加载中...");
					}
					
					@Override
					public void onError(int errorNo, String strMsg) {
						super.onError(errorNo, strMsg);
						T.showShort(mContext, "数据访问超时!");
					}

					@Override
					public void onDo(ReturnBackStatus data) {
						KingTellerProgressUtils.closeProgress();
						
						if("0".equals(data.getMessage())){
							
							final NormalDialog dialog_gdewmts = new NormalDialog(mContext);
				        	KingTellerPromptDialogUtils.showSaveTwoPromptDialog(dialog_gdewmts, 
				        			"如果有更换备件(粘贴有二维码), 请及时扫码!", "前往扫码", "继续提交",
									new OnBtnClickL() {
										@Override
										public void onBtnClick() {
											dialog_gdewmts.dismiss();
											startDotMachineReplace(JqBm, JqId, Gddh, orderId, SsFwz, WdId, WdMc, Pdsj);
										}
				                    }, new OnBtnClickL() {
										@Override
										public void onBtnClick() {
											dialog_gdewmts.dismiss();
											submitStatus(orderId, status, orderType, "");
										}
				                    });

						}else{
							submitStatus(orderId, status, orderType, "");
						}
						
					};
				});
	}
	
	public void startDotMachine(String JqBm, String JqId, String SsFwz, String WdId, String WdMc, String type){
		Intent intent = new Intent();
		intent.putExtra("JqBm", JqBm);
		intent.putExtra("JqId", JqId);
		intent.putExtra("SsFwz", SsFwz);
		intent.putExtra("WdId", WdId);
		intent.putExtra("WdMc", WdMc);
		intent.putExtra("Type", type);
		
		intent.setClass(mContext, QRDotMachineCargoScanActivict.class);
		mContext.startActivity(intent);
	}
	
	
	public void startDotMachineReplace(String JqBm, String JqId, String Gddh, String Gdid, String SsFwz, String WdId, String WdMc, String Pdsj){
		Intent intent = new Intent();
		intent.putExtra("JqBm", JqBm);
		intent.putExtra("JqId", JqId);
		intent.putExtra("Gddh", Gddh);
		intent.putExtra("Gdid", Gdid);
		intent.putExtra("SsFwz", SsFwz);
		intent.putExtra("WdId", WdId);
		intent.putExtra("WdMc", WdMc);
		intent.putExtra("Pdsj", Pdsj);
		intent.setClass(mContext, QRDotMachineReplaceCargoScanActivict.class);
		mContext.startActivity(intent);
	}
	
	
	
	public void submitStatus(String orderId,String status,String orderType,String servType){
		KTHttpClient fh = new KTHttpClient(true);
		AjaxParams params = new AjaxParams();
		params.put("orderId", orderId);
		params.put("status", status);
		params.put("orderType", orderType);
		params.put("servType", servType);
		
		fh.post(KingTellerConfigUtils.CreateUrl(mContext, KingTellerUrlConfig.WebRwdztUrl),
				params, new AjaxHttpCallBack<ReturnBackStatus>(mContext,
						new TypeToken<ReturnBackStatus>() {}.getType(), false) {
			
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
						if("fail".equals(data.getResult())){
							callBack.onFalse("提示：" + data.getMessage());
						}else if("success".equals(data.getResult())){
							T.showShort(mContext, "提交成功!");
							callBack.onSuccess();
						}
					};
				});
	}
	
	public void submitXmStatus(String orderId,String status,String orderType,String servType,XmListBean xmList){
		KTHttpClient fh = new KTHttpClient(true);
		AjaxParams params = new AjaxParams();
		Log.e("canshu", "orderId="+orderId+" ,status="+status+" ,orderType="+orderType+" ,servType="+servType+" ,assign="+ConditionUtils.getJsonFromObj(xmList));
		params.put("orderId", orderId);
		params.put("status", status);
		params.put("orderType", orderType);
		params.put("servType", servType);
		params.put("assign", ConditionUtils.getJsonFromObj(xmList));
		
		fh.post(KingTellerConfigUtils.CreateUrl(mContext, KingTellerUrlConfig.WebRwdztUrl),
				params, new AjaxHttpCallBack<ReturnBackStatus>(mContext,
						new TypeToken<ReturnBackStatus>() {}.getType(), false) {
			
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
						if("fail".equals(data.getResult())){
							callBack.onFalse("提示：" + data.getMessage());
						}else if("success".equals(data.getResult())){
							callBack.onSuccess();
						}
					};
				});
	}
	
	public void submitTd(String orderId,String status,String orderType,String backorderType,String backorderRemark){
		KTHttpClient fh = new KTHttpClient(true);
		AjaxParams params = new AjaxParams();
		params.put("orderId", orderId);
		params.put("status", status);
		params.put("orderType", orderType);
		params.put("backorderRemark", backorderRemark);
		params.put("backorderType", backorderType);
		params.put("direction", "-1");
		
		fh.post(KingTellerConfigUtils.CreateUrl(mContext, KingTellerUrlConfig.WebRwdztUrl),
				params, new AjaxHttpCallBack<ReturnBackStatus>(mContext,
						new TypeToken<ReturnBackStatus>() {}.getType(), false) {
			
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
						if("fail".equals(data.getResult())){
							callBack.onFalse("提示：" + data.getMessage());
						}else if("success".equals(data.getResult())){
							T.showShort(mContext, "提示：" + data.getMessage());
							callBack.onSuccess();
						}
					};
				});
	}
	
	/**
	 * 获取机器信息数据
	 * @param jqbh
	 */
	public void getJqXxData(String jqbh) {
		if (!KingTellerJudgeUtils.isNetAvaliable(mContext)) {
			T.showShort(mContext, "没有网络, 请检查网络是否可用!");
			return;
		}

		KTHttpClient fh = new KTHttpClient(true);
		AjaxParams params = new AjaxParams();
		params.put("jqbh", jqbh);

		fh.post(KingTellerConfigUtils.CreateUrl(mContext, KingTellerUrlConfig.HqjqxxURL),
				params, new AjaxHttpCallBack<JqxxDataBean>(mContext,
						new TypeToken<JqxxDataBean>() {
						}.getType(), false) {
			
					@Override
					public void onStart() {
						KingTellerProgressUtils.showProgress(mContext, "正在获取...");
					}
					
					@Override
					public void onDo(JqxxDataBean data) {
						KingTellerProgressUtils.closeProgress();
						if (data != null) {
							OpenDialog("", new Gson().toJson(data), 0,  1);	
						} else {
							T.showShort(getContext(), "获取数据失败!");
						}

					};
				});
	}
	
	public String getStatus(String status){
		if( workOrder.getOrderType().equals("maintenance")){
			if( "new".equals(status)){
				return "新单";
			}else if("accept".equals(status)){
				return "接障";
			}else if("arrive".equals(status)){
				return "到达现场";
			}else if("begin".equals(status)){
				return "开始维护";
			}else if("finish".equals(status)){
				return "维护结束";
			}
		}else if(workOrder.getOrderType().equals("otherMatter")){
			if( "new".equals(status)){
				return "新单";
			}else if("accept".equals(status)){
				return "确认接收";
			}else if("begin".equals(status)){
				return "开始执行";
			}else if("finish".equals(status)){
				return "完成事务";
			}
		}else if(workOrder.getOrderType().equals("logistics")){
			if( "new".equals(status)){
				return "新单";
			}else if("accept".equals(status)){
				return "确认接收";
			}else if("arrive".equals(status)){
				return "到达现场";
			}else if("begin".equals(status)){
				return "装车完毕";
			}else if("finish".equals(status)){
				return "物流完成";
			}
		}else if(workOrder.getOrderType().equals("clean")){
			if( "new".equals(status)){
				return "新单";
			}else if("accept".equals(status)){
				return "接障";
			}else if("arrive".equals(status)){
				return "到达现场";
			}else if("begin".equals(status)){
				return "开始维护";
			}else if("finish".equals(status)){
				return "维护结束";
			}
		}
		return "";
	}
	
	public String getOrderType(String orderType){
		String type = "";
		if(KingTellerJudgeUtils.isEmpty(orderType)){
			type = "";
		}else if("maintenance".equals(orderType)){
			type = "维护";
		}else if("otherMatter".equals(orderType)){
			type = "其他事务";
		}else if("logistics".equals(orderType)){
			type = "物流";
		}else if("clean".equals(orderType)){
			type = "清洁";
		}
		return type;
	}
	
	private static class ViewHoler{
		public TextView orderNo;
		public TextView orderType;
		
		public TextView assignName;
		public TextView assignTime;
		
		public TextView wdsbjc;
		public TextView troubleRemark;
	
		public TextView backflag;
		public TextView prearrangeDateStr;
		public TextView status;
		
		public Button atmMsg;
		public Button acceptMalf,machineScan,backMalf,reportFill,dotmachine;
	} 

	public void setCallBack(Callback callBack) {
		this.callBack = callBack;
	}

	public interface Callback{
		public void onSuccess();
		public void onFalse(String msg);
	}
	
	//退单原因
	private List<CommonSelectData> getTdyy(String dict_typeId){

		String myPath = SQLiteHelper.DB_PATH+SQLiteHelper.DB_NAME;
		SQLiteDatabase database = SQLiteDatabase.openOrCreateDatabase(myPath,null);
		Cursor c = database.rawQuery("select dict_code,dict_name from tb_pc_dict where dict_typeid=?;",new String[]{dict_typeId});
		
		List<CommonSelectData> lists = new ArrayList<CommonSelectData>();
		c.moveToFirst();
		for (int i = 0; i < c.getCount(); i++) {  
			CommonSelectData data = new CommonSelectData();
			data.setValue(c.getString(c.getColumnIndex("dict_name")));
			data.setValue(c.getString(c.getColumnIndex("dict_code")));
			
//			byte[] bname = c.getBlob(c.getColumnIndex("dict_name"));
//
//			try {
//				data.setText(new String(bname,"GBK"));
//				data.setValue(c.getString(c.getColumnIndex("dict_code")));
//			} catch (UnsupportedEncodingException e) {
//				e.printStackTrace();
//			}
			lists.add(data);
			c.moveToNext();   
		}   
		c.close();
		database.close();
		return lists;
	}
}
