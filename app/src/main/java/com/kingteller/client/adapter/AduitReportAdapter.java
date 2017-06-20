package com.kingteller.client.adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.reflect.TypeToken;
import com.kingteller.R;
import com.kingteller.client.activity.workorder.BaseReportActivity;
import com.kingteller.client.activity.workorder.CleanReportActivity;
import com.kingteller.client.activity.workorder.checkout.LogisticsReportCheckoutActivity;
import com.kingteller.client.activity.workorder.checkout.OtherMatterReportCheckoutActivity;
import com.kingteller.client.activity.workorder.checkout.RepairReportCheckoutActivity;
import com.kingteller.client.bean.offlineupload.OfflineUploadBean;
import com.kingteller.client.bean.workorder.AduitReportBean;
import com.kingteller.client.bean.workorder.CleanReportBean;
import com.kingteller.client.bean.workorder.LogisticsReportBean;
import com.kingteller.client.bean.workorder.OtherMatterReportBean;
import com.kingteller.client.bean.workorder.RepairReportBean;
import com.kingteller.client.bean.workorder.WorkOrderBean;
import com.kingteller.client.config.KingTellerUrlConfig;
import com.kingteller.client.netauth.AjaxHttpCallBack;
import com.kingteller.client.netauth.KTHttpClient;
import com.kingteller.client.utils.KingTellerConfigUtils;
import com.kingteller.client.utils.KingTellerJudgeUtils;
import com.kingteller.client.view.dialog.NormalDialog;
import com.kingteller.client.view.dialog.listener.OnBtnClickL;
import com.kingteller.client.view.dialog.use.KingTellerProgressUtils;
import com.kingteller.client.view.dialog.use.KingTellerPromptDialogUtils;
import com.kingteller.client.view.toast.T;
import com.kingteller.framework.http.AjaxParams;

@SuppressLint("UseSparseArrays")
public class AduitReportAdapter extends BaseAdapter{

	private LayoutInflater inflater;
	private Context mContext;
	private AduitReportBean aduitBean;
	private List<AduitReportBean> mAduitList;
	private List<AduitReportBean> aduitCheckedList = new ArrayList<AduitReportBean>();
	private String executionState;
	private HashMap<Integer, Boolean> isCheckedMap = new HashMap<Integer, Boolean>();
	private String msg;
	
	public AduitReportAdapter(Context context,List<AduitReportBean> aduitList,String executionState){
		inflater = LayoutInflater.from(context);
		this.mContext = context;
		this.mAduitList = aduitList;
		this.executionState = executionState;
		configCheckMap(false);
	}
	
	public void setLists(List<AduitReportBean> aduitList) {
		this.mAduitList = aduitList;
		isCheckedMap.clear();
		configCheckMap(false);
		notifyDataSetChanged();
	}
	
	public void addLists(List<AduitReportBean> aduitList) {
		this.mAduitList.addAll(aduitList);
		notifyDataSetChanged();
	}
	
	/**
	 * 首先,默认情况下,所有项目都是没有选中的.这里进行初始化
	 */
	public void configCheckMap(boolean bool) {
		for (int i = 0; i < mAduitList.size(); i++) {
			isCheckedMap.put(i, bool);
		}
	}
	
	@Override
	public int getCount() {
		if( mAduitList == null){
			return 0;
		}
		return mAduitList.size();
	}

	@Override
	public Object getItem(int arg0) {
		return mAduitList.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	@Override
	public View getView(final int pos, View v, ViewGroup arg2) {
		
		aduitBean = mAduitList.get(pos);

		ViewHoler viewHoler = null;
		if (v == null) {
			viewHoler = new ViewHoler();
			v = inflater.inflate(R.layout.item_aduit_report, null);
			viewHoler.checkBox = (CheckBox) v.findViewById(R.id.checkBox);
			viewHoler.orderNo = (TextView) v.findViewById(R.id.orderNo);
			viewHoler.orderType = (TextView) v.findViewById(R.id.orderType);
			viewHoler.assignMan = (TextView) v.findViewById(R.id.assignMan);
			viewHoler.assignTime = (TextView) v.findViewById(R.id.assignTime);
			viewHoler.atmMsg = (TextView) v.findViewById(R.id.atmMsg);
			viewHoler.taskInfo = (TextView) v.findViewById(R.id.taskInfo);

			v.setTag(viewHoler);
		}else{
			viewHoler = (ViewHoler)v.getTag();
		}
		
		if(executionState.equals(BaseReportActivity.OPT_UNTREATED)){
			viewHoler.checkBox.setVisibility(View.VISIBLE);
			msg = "你确定要查看并审核报告吗?";
		}else if (executionState.equals(BaseReportActivity.OPT_PROCESSED)){
			viewHoler.checkBox.setVisibility(View.GONE);
			msg = "你确定要查看报告吗?";
		}

		viewHoler.orderNo.setText(aduitBean.getOrderNo());
		viewHoler.assignMan.setText(aduitBean.getExeUserName());
		viewHoler.assignTime.setText(aduitBean.getSubmitTime());
		viewHoler.atmMsg.setText(aduitBean.getATMCode());
		viewHoler.taskInfo.setText(aduitBean.getTroubleRemark());
		
		if(aduitBean.getReportProperty().equals("xm")){
			viewHoler.orderType.setText("[项目]");
		}else{
			viewHoler.orderType.setText("["+ getOrderType(aduitBean.getReportProperty()) + "]");
		}
		
		viewHoler.checkBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean isChecked) {
				isCheckedMap.put(pos, isChecked); //将选择项加载到map里面寄存
			}
		});
		
		if (isCheckedMap.get(pos) == null) {
			isCheckedMap.put(pos, false);
		}
		viewHoler.checkBox.setChecked(isCheckedMap.get(pos));
		
			
		v.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				
				final NormalDialog dialog = new NormalDialog(mContext);
	        	KingTellerPromptDialogUtils.showTwoPromptDialog(dialog, msg,
						new OnBtnClickL() {
							@Override
							public void onBtnClick() {
								dialog.dismiss();
							}
	                    }, new OnBtnClickL() {
							@Override
							public void onBtnClick() {
								dialog.dismiss();
								singleAudit(executionState, mAduitList.get(pos));
							}
	                    });
			}
		});

		return v;
	}
	
	public List<AduitReportBean> getAduitCheckedList(){
		aduitCheckedList = new ArrayList<AduitReportBean>();
		// 进行遍历
		for (int i = 0; i < mAduitList.size(); i++) {
			if (isCheckedMap.get(i) != null && isCheckedMap.get(i)) {
				aduitCheckedList.add(mAduitList.get(i));
			}
		}
		
		return aduitCheckedList;
	}
	
	private static class ViewHoler{
		public CheckBox checkBox;
		public TextView orderNo;
		public TextView orderType;
		public TextView assignMan;
		public TextView assignTime;
		public TextView atmMsg;
		public TextView taskInfo;
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
	
	public void singleAudit(final String state,AduitReportBean aduitReportBean){
		KTHttpClient fh = new KTHttpClient(true);
		AjaxParams params = new AjaxParams();
		params.put("reportId", aduitReportBean.getReportId());
		params.put("reportType", aduitReportBean.getReportProperty());
		params.put("orderId", aduitReportBean.getOrderId());
		
		KingTellerProgressUtils.showProgress(mContext, "正在查询中...");
		
		if(aduitReportBean.getReportProperty().equals("otherMatter")){
			fh.post(KingTellerConfigUtils.CreateUrl(mContext, KingTellerUrlConfig.WebDgspUrl),
					params, new AjaxHttpCallBack<OtherMatterReportBean>(mContext,
							new TypeToken<OtherMatterReportBean>() {
							}.getType(), true) {

						@Override
						public void onFinish() {
							KingTellerProgressUtils.closeProgress();
						}

						@Override
						public void onDo(OtherMatterReportBean data) {
							setJump_qtsw(data , data.getOrderId(), "otherMatter", state,BaseReportActivity.OPT_ADUIT,OtherMatterReportCheckoutActivity.class);
						};
					});
		}
//		else if(aduitReportBean.getReportProperty().equals("logistics")){
//			fh.post(KingTellerConfigUtils.CreateUrl(mContext, KingTellerUrlConfig.WebDgspUrl),
//					params, new AjaxHttpCallBack<LogisticsReportBean>(mContext,
//							new TypeToken<LogisticsReportBean>() {
//							}.getType(), true) {
//
//						@Override
//						public void onFinish() {
//							KingTellerProgressUtils.closeProgress();
//						}
//
//						@Override
//						public void onDo(LogisticsReportBean data) {
//							setJump(data.getOrderId(), "logistics", state,BaseReportActivity.OPT_ADUIT,LogisticsReportCheckoutActivity.class);
//						};
//					});
//		}else if(aduitReportBean.getReportProperty().equals("clean")){
//			fh.post(KingTellerConfigUtils.CreateUrl(mContext, KingTellerUrlConfig.WebDgspUrl),
//					params, new AjaxHttpCallBack<CleanReportBean>(mContext,
//							new TypeToken<CleanReportBean>() {
//							}.getType(), true) {
//
//						@Override
//						public void onFinish() {
//							KingTellerProgressUtils.closeProgress();
//						}
//
//						@Override
//						public void onDo(CleanReportBean data) {
//							setJump(data.getOrderId(), "clean", state,BaseReportActivity.OPT_ADUIT, CleanReportActivity.class);
//						};
//					});
//		}
		else if(aduitReportBean.getReportProperty().equals("maintenance") ||
				aduitReportBean.getReportProperty().equals("xm")){
			fh.post(KingTellerConfigUtils.CreateUrl(mContext, KingTellerUrlConfig.WebDgspUrl),
					params, new AjaxHttpCallBack<RepairReportBean>(mContext,
							new TypeToken<RepairReportBean>() {
							}.getType(), true) {

						@Override
						public void onFinish() {
							KingTellerProgressUtils.closeProgress();
						}

						@Override
						public void onDo(RepairReportBean data) {
							setJump_wh(data , data.getOrderId(), "maintenance", state,BaseReportActivity.OPT_ADUIT, RepairReportCheckoutActivity.class);
						};
					});
		}else {
			T.showShort(mContext, "数据错误!");
		}
	}
	
	public void setJump(String orderId,String dataType,String state,int optType,Class<?> clazz){
		WorkOrderBean workOrderBean = new WorkOrderBean();
		workOrderBean.setOrderId(orderId);
		workOrderBean.setOrderType(dataType);
		Intent intent = new Intent();
		Bundle bundle = new Bundle();
		bundle.putSerializable("reportBean", workOrderBean);
		intent.putExtras(bundle);
		intent.putExtra(BaseReportActivity.OPT_TYPE, optType);
		intent.putExtra(BaseReportActivity.OPT_STATE_TYPE, state);
		mContext.startActivity(intent.setClass(this.mContext,clazz));
	}
	
	public void setJump_qtsw(OtherMatterReportBean data,String orderId,String dataType,String state,int optType,Class<?> clazz){
		WorkOrderBean workOrderBean = new WorkOrderBean();
		workOrderBean.setOrderId(orderId);
		workOrderBean.setOrderType(dataType);
		Intent intent = new Intent();
		Bundle bundle = new Bundle();
		bundle.putSerializable("reportBean", workOrderBean);
		bundle.putSerializable("mOtherMatterReportBean", data);
		intent.putExtras(bundle);
		intent.putExtra(BaseReportActivity.OPT_TYPE, optType);
		intent.putExtra(BaseReportActivity.OPT_STATE_TYPE, state);
		mContext.startActivity(intent.setClass(this.mContext,clazz));
	}
	
	public void setJump_wh(RepairReportBean data,String orderId,String dataType,String state,int optType,Class<?> clazz){
		WorkOrderBean workOrderBean = new WorkOrderBean();
		workOrderBean.setOrderId(orderId);
		workOrderBean.setOrderType(dataType);
		Intent intent = new Intent();
		Bundle bundle = new Bundle();
		bundle.putSerializable("reportBean", workOrderBean);
		bundle.putSerializable("mRepairReportBean", data);
		intent.putExtras(bundle);
		intent.putExtra(BaseReportActivity.OPT_TYPE, optType);
		intent.putExtra(BaseReportActivity.OPT_STATE_TYPE, state);
		mContext.startActivity(intent.setClass(this.mContext,clazz));
	}
}
