package com.kingteller.client.activity.attendance.frament;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kingteller.R;
import com.kingteller.client.activity.attendance.checkout.WorkAbsentFromCheckoutActivity;
import com.kingteller.client.activity.attendance.checkout.WorkBoardFromCheckoutActivity;
import com.kingteller.client.activity.attendance.checkout.WorkBusinessTripFromCheckoutActivity;
import com.kingteller.client.activity.attendance.checkout.WorkLeaveFromCheckoutActivity;
import com.kingteller.client.activity.attendance.checkout.WorkOvertimeFormCheckoutActivity;
import com.kingteller.client.activity.attendance.checkout.WorkSickLeaveFromCheckoutActivity;
import com.kingteller.client.adapter.WorkAttendanAduitAdapter;
import com.kingteller.client.bean.attendance.AbsentBean;
import com.kingteller.client.bean.attendance.BoardBean;
import com.kingteller.client.bean.attendance.BusinessTripBean;
import com.kingteller.client.bean.attendance.LeaveBean;
import com.kingteller.client.bean.attendance.OverTimeBean;
import com.kingteller.client.bean.attendance.SickLeaveBean;
import com.kingteller.client.bean.attendance.WorkAttendanceBean;
import com.kingteller.client.bean.workorder.WorkOrderBean;
import com.kingteller.client.config.KingTellerUrlConfig;
import com.kingteller.client.netauth.AjaxHttpCallBack;
import com.kingteller.client.netauth.KTHttpClient;
import com.kingteller.client.utils.KingTellerConfigUtils;
import com.kingteller.client.utils.KingTellerJudgeUtils;
import com.kingteller.client.view.ListViewObj;
import com.kingteller.client.view.XListView.IXListViewListener;
import com.kingteller.client.view.datatype.LoadingEnum;
import com.kingteller.client.view.dialog.NormalDialog;
import com.kingteller.client.view.dialog.entity.SearchGeneraItem;
import com.kingteller.client.view.dialog.listener.OnBtnClickL;
import com.kingteller.client.view.dialog.use.KingTellerProgressUtils;
import com.kingteller.client.view.dialog.use.KingTellerPromptDialogUtils;
import com.kingteller.client.view.toast.T;
import com.kingteller.framework.http.AjaxParams;

public class AduitAttendanceFragment extends Fragment implements IXListViewListener, OnItemClickListener{
	
	private View mContentView;
	private Context mContext;
	
	private ListViewObj listviewObj;
	private WorkAttendanAduitAdapter aduitAdapter;
	private String tabName;
	
	private List<WorkAttendanceBean> workattendancelist = new ArrayList<WorkAttendanceBean>();
	
	private SearchGeneraItem searchList;
	
	private int mPage = 1;
	private boolean isFirst = true;
	
	private NormalDialog dialog = null; 	//初始化通用dialog
	
	public AduitAttendanceFragment getInstance(String tabName){
		AduitAttendanceFragment wf = new AduitAttendanceFragment();
		Bundle bun = new Bundle();
		bun.putString("tName", tabName);
		wf.setArguments(bun);
		return wf;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.tabName = getArguments().getString("tName");
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mContentView = inflater.inflate(R.layout.common_list_view, null);
		return mContentView;
	}
	
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		mContext = this.getActivity();
		
		if (getView().findViewById(R.id.loading_view) != null){
			setListviewObj(new ListViewObj(getView()));
		}
		
		initUI();
		initData();
	}

	private void initUI() {
		searchList = new SearchGeneraItem();
		workattendancelist = new ArrayList<WorkAttendanceBean>();
		aduitAdapter = new WorkAttendanAduitAdapter(mContext, workattendancelist, tabName);
		
		getListviewObj().getListview().setAdapter(aduitAdapter);
		getListviewObj().getListview().setRefreshEnabled(true);
		getListviewObj().getListview().setLoadMoreEnabled(true);
		getListviewObj().getListview().setOnRefreshListener(this);
		getListviewObj().getListview().setOnItemClickListener(this);
		
		getListviewObj().setOnClickLister(LoadingEnum.NODATA, res);
		getListviewObj().setOnClickLister(LoadingEnum.NETERROR, res);
		
	}
	
	private void initData() {
		onRefresh();
	}
	
	private OnClickListener res = new OnClickListener() {
		@Override
		public void onClick(View view) {
			isFirst = true;
			onRefresh();
		}
	};
	
	@Override
	public void onRefresh() {
		mPage = 1;
		getAduitAttendanceData();
	}
	
	@Override
	public void onLoadMore() {
		mPage++;
		getAduitAttendanceData();
	}
	
	public void getAduitAttendanceData() {

		KTHttpClient fh = new KTHttpClient(true);
		AjaxParams params = new AjaxParams();
		
		String NewPage = Integer.toString(mPage);
		params.put("page", NewPage);//页数
		
		if("tab1".equals(tabName)){
			params.put("status", "1");
		}else if("tab2".equals(tabName)){
			params.put("status", "2");
		}else if("tab3".equals(tabName)){
			params.put("status", "3");
		}

		params.put("djlb", KingTellerJudgeUtils.isEmpty(searchList.getBillType()) ? "" : searchList.getBillType());
		params.put("ksrq", KingTellerJudgeUtils.isEmpty(searchList.getStartTime()) ? "" : searchList.getStartTime());
		params.put("jsrq", KingTellerJudgeUtils.isEmpty(searchList.getEndTime()) ? "" : searchList.getEndTime());
		params.put("ssbm", KingTellerJudgeUtils.isEmpty(searchList.getTheDepartment()) ? "" : searchList.getTheDepartment());
	

		fh.post(KingTellerConfigUtils.CreateUrl(mContext, KingTellerUrlConfig.OAAduitAttendanceList),
				params, new AjaxHttpCallBack<List<WorkAttendanceBean>>(mContext,
						new TypeToken<List<WorkAttendanceBean>>() {
						}.getType(), true) {

					@Override
					public void onStart() {
						if (mPage == 1 && isFirst == true){
							getListviewObj().setStatus(LoadingEnum.LOADING);
							isFirst = false;
						}
					}
			
					@Override
					public void onError(int errorNo, String strMsg) {
						super.onError(errorNo, strMsg);
						getListviewObj().setStatus(LoadingEnum.NETERROR);
						T.showShort(mContext, "数据访问超时!");
					}
					
					@Override
					public void onFinish() {
						getListviewObj().getListview().stopRefresh();
						getListviewObj().getListview().stopLoadMore();
					}

					@Override
					public void onDo(List<WorkAttendanceBean> data) {
						if (data.size() > 0 && data.get(0).getCode().equals("")) {
							if(mPage == 1){
								workattendancelist = data;
								aduitAdapter.setLists(workattendancelist);
								getListviewObj().setStatus(LoadingEnum.LISTSHOW);
								getListviewObj().getListview().setLoadMoreEnabled(true);
							}else{
								aduitAdapter.addLists(data);
							}
						}else {
							if(mPage == 1){
								getListviewObj().setStatus(LoadingEnum.NODATA, getString(R.string.no_data_try));
								isFirst = true;
							}else{
								getListviewObj().getListview().setLoadMoreEnabled(false);
								T.showShort(mContext, data.get(0).getCode());
							}
						}
					};
				});
	}
	
	public void setSearchList(SearchGeneraItem search_str) {
		isFirst = true;
		this.searchList = search_str;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		WorkAttendanceBean data = (WorkAttendanceBean) parent.getAdapter().getItem(position);
		if("kqmgrOvertimeFlow".equals(data.getBillType())){				//加班
			getSaveOvertime(data.getBillId(), data.getBillType(), data.getFlowPosition());
		}else if ("kqmgrLeaveFlow".equals(data.getBillType())){			//请假
			getSaveLeave(data.getBillId(), data.getBillType(), data.getFlowPosition());
		}else if ("kqmgrSickleaveFlow".equals(data.getBillType())){		//销假
			getSaveSickLeave(data.getBillId(), data.getBillType(), data.getFlowPosition());
		}else if ("kqmgrAbsenteeismFlow".equals(data.getBillType())){	//旷工
			getSaveAbsent(data.getBillId(), data.getBillType(), data.getFlowPosition());
		}else if ("kqmgrFILLFlow".equals(data.getBillType())){			//补登
			getSaveBoard(data.getBillId(), data.getBillType(), data.getFlowPosition());
		}else if ("kqmgrTravelFlow".equals(data.getBillType())){		//出差
			getSaveBusinessTrip(data.getBillId(), data.getBillType(), data.getFlowPosition());
		}

	}
	
	/**
	 * 获取当前加班单信息 
	 */
	public void getSaveOvertime(String billId, String billType, final String flowPosition) {
		KTHttpClient fh = new KTHttpClient(true);
		AjaxParams params = new AjaxParams();
		params.put("keyId", billId);
		params.put("billType", billType);
		fh.post(KingTellerConfigUtils.CreateUrl(mContext, KingTellerUrlConfig.GetOAAllAduitWorkDate),
				params, new AjaxHttpCallBack<OverTimeBean>(mContext,
						new TypeToken<OverTimeBean>() {
						}.getType(), true) {

					@Override
					public void onStart() {
						KingTellerProgressUtils.showProgress(mContext, "正在获取中...");
					}
					
					@Override
					public void onError(int errorNo, String strMsg) {
						super.onError(errorNo, strMsg);
						KingTellerProgressUtils.closeProgress();
						T.showShort(mContext, "数据访问超时!");
					}

					@Override
					public void onDo(OverTimeBean data) {
						KingTellerProgressUtils.closeProgress();
						if("".equals(data.getCode())){
							if("tab1".equals(tabName)){	
								Intent intent_spjbd = new Intent(mContext, WorkOvertimeFormCheckoutActivity.class);
								intent_spjbd.putExtra("isCheckout", 1);
								intent_spjbd.putExtra("overtimeData", new Gson().toJson(data));
			    				mContext.startActivity(intent_spjbd);
							}else if("tab2".equals(tabName) || "tab3".equals(tabName)){
								Intent intent_ckjbd = new Intent(mContext, WorkOvertimeFormCheckoutActivity.class);
								intent_ckjbd.putExtra("isCheckout", 0);
								intent_ckjbd.putExtra("overtimeData", new Gson().toJson(data));
			    				mContext.startActivity(intent_ckjbd);
							}
							
						}else{
							OpenDialog(data.getCode(), 1);
						}
					};
				});
	}
	
	/**
	 * 获取当前出差单信息 
	 */
	public void getSaveBusinessTrip(String billId, String billType, final String flowPosition) {
		KTHttpClient fh = new KTHttpClient(true);
		AjaxParams params = new AjaxParams();
		params.put("keyId", billId);
		params.put("billType", billType);
		fh.post(KingTellerConfigUtils.CreateUrl(mContext, KingTellerUrlConfig.GetOAAllAduitWorkDate),
				params, new AjaxHttpCallBack<BusinessTripBean>(mContext,
						new TypeToken<BusinessTripBean>() {
						}.getType(), true) {

					@Override
					public void onStart() {
						KingTellerProgressUtils.showProgress(mContext, "正在获取中...");
					}
					
					@Override
					public void onError(int errorNo, String strMsg) {
						super.onError(errorNo, strMsg);
						KingTellerProgressUtils.closeProgress();
						T.showShort(mContext, "数据访问超时!");
					}

					@Override
					public void onDo(BusinessTripBean data) {
						KingTellerProgressUtils.closeProgress();
						if("".equals(data.getCode())){
							if("tab1".equals(tabName)){	
								Intent intent_spccd = new Intent(mContext, WorkBusinessTripFromCheckoutActivity.class);
								intent_spccd.putExtra("isCheckout", 1);
								intent_spccd.putExtra("mBusinessData", new Gson().toJson(data));
			    				mContext.startActivity(intent_spccd);
							}else if("tab2".equals(tabName) || "tab3".equals(tabName)){
								Intent intent_ckccd = new Intent(mContext, WorkBusinessTripFromCheckoutActivity.class);
								intent_ckccd.putExtra("isCheckout", 0);
								intent_ckccd.putExtra("mBusinessData", new Gson().toJson(data));
			    				mContext.startActivity(intent_ckccd);
							}
							
						}else{
							OpenDialog(data.getCode(), 1);
						}
					};
				});
		
	}
	
	/**
	 * 获取当前补登单信息 
	 */
	public void getSaveBoard(String billId, String billType, final String flowPosition) {
		KTHttpClient fh = new KTHttpClient(true);
		AjaxParams params = new AjaxParams();
		params.put("keyId", billId);
		params.put("billType", billType);
		fh.post(KingTellerConfigUtils.CreateUrl(mContext, KingTellerUrlConfig.GetOAAllAduitWorkDate),
				params, new AjaxHttpCallBack<BoardBean>(mContext,
						new TypeToken<BoardBean>() {
						}.getType(), true) {

					@Override
					public void onStart() {
						KingTellerProgressUtils.showProgress(mContext, "正在获取中...");
					}
					
					@Override
					public void onError(int errorNo, String strMsg) {
						super.onError(errorNo, strMsg);
						KingTellerProgressUtils.closeProgress();
						T.showShort(mContext, "数据访问超时!");
					}

					@Override
					public void onDo(BoardBean data) {
						KingTellerProgressUtils.closeProgress();
						if("".equals(data.getCode())){
							if("tab1".equals(tabName)){	
								Intent intent_spccd = new Intent(mContext, WorkBoardFromCheckoutActivity.class);
								intent_spccd.putExtra("isCheckout", 1);
								intent_spccd.putExtra("mBoardData", new Gson().toJson(data));
			    				mContext.startActivity(intent_spccd);
							}else if("tab2".equals(tabName) || "tab3".equals(tabName)){
								Intent intent_ckccd = new Intent(mContext, WorkBoardFromCheckoutActivity.class);
								intent_ckccd.putExtra("isCheckout", 0);
								intent_ckccd.putExtra("mBoardData", new Gson().toJson(data));
			    				mContext.startActivity(intent_ckccd);
							}
							
						}else{
							OpenDialog(data.getCode(), 1);
						}
					};
				});
	}
	
	/**
	 * 获取当前旷工单信息 
	 */
	public void getSaveAbsent(String billId, String billType, final String flowPosition) {
		KTHttpClient fh = new KTHttpClient(true);
		AjaxParams params = new AjaxParams();
		params.put("keyId", billId);
		params.put("billType", billType);
		fh.post(KingTellerConfigUtils.CreateUrl(mContext, KingTellerUrlConfig.GetOAAllAduitWorkDate),
				params, new AjaxHttpCallBack<AbsentBean>(mContext,
						new TypeToken<AbsentBean>() {
						}.getType(), true) {

					@Override
					public void onStart() {
						KingTellerProgressUtils.showProgress(mContext, "正在获取中...");
					}
					
					@Override
					public void onError(int errorNo, String strMsg) {
						super.onError(errorNo, strMsg);
						KingTellerProgressUtils.closeProgress();
						T.showShort(mContext, "数据访问超时!");
					}

					@Override
					public void onDo(AbsentBean data) {
						KingTellerProgressUtils.closeProgress();
						if("".equals(data.getCode())){
							if("tab1".equals(tabName)){	
								Intent intent_spkgd = new Intent(mContext, WorkAbsentFromCheckoutActivity.class);
								intent_spkgd.putExtra("isCheckout", 1);
								intent_spkgd.putExtra("mAbsenData", new Gson().toJson(data));
			    				mContext.startActivity(intent_spkgd);
							}else if("tab2".equals(tabName) || "tab3".equals(tabName)){
								Intent intent_ckkgd = new Intent(mContext, WorkAbsentFromCheckoutActivity.class);
								intent_ckkgd.putExtra("isCheckout", 0);
								intent_ckkgd.putExtra("mAbsenData", new Gson().toJson(data));
			    				mContext.startActivity(intent_ckkgd);
							}
							
						}else{
							OpenDialog(data.getCode(), 1);
						}
					};
				});
	}
	
	/**
	 * 获取当前销假单信息 
	 */
	public void getSaveSickLeave(String billId, String billType, final String flowPosition) {
		KTHttpClient fh = new KTHttpClient(true);
		AjaxParams params = new AjaxParams();
		params.put("keyId", billId);
		params.put("billType", billType);
		fh.post(KingTellerConfigUtils.CreateUrl(mContext, KingTellerUrlConfig.GetOAAllAduitWorkDate),
				params, new AjaxHttpCallBack<SickLeaveBean>(mContext,
						new TypeToken<SickLeaveBean>() {
						}.getType(), true) {

					@Override
					public void onStart() {
						KingTellerProgressUtils.showProgress(mContext, "正在获取中...");
					}
					
					@Override
					public void onError(int errorNo, String strMsg) {
						super.onError(errorNo, strMsg);
						KingTellerProgressUtils.closeProgress();
						T.showShort(mContext, "数据访问超时!");
					}

					@Override
					public void onDo(SickLeaveBean data) {
						KingTellerProgressUtils.closeProgress();
						if("".equals(data.getCode())){
							if("tab1".equals(tabName)){	
								Intent intent_spxjd = new Intent(mContext, WorkSickLeaveFromCheckoutActivity.class);
								intent_spxjd.putExtra("isCheckout", 1);
								intent_spxjd.putExtra("mSickLeaveData", new Gson().toJson(data));
			    				mContext.startActivity(intent_spxjd);
							}else if("tab2".equals(tabName) || "tab3".equals(tabName)){
								Intent intent_ckxjd = new Intent(mContext, WorkSickLeaveFromCheckoutActivity.class);
								intent_ckxjd.putExtra("isCheckout", 0);
								intent_ckxjd.putExtra("mSickLeaveData", new Gson().toJson(data));
			    				mContext.startActivity(intent_ckxjd);
							}
							
						}else{
							OpenDialog(data.getCode(), 1);
						}
					};
				});
	}
	
	/**
	 * 获取当前销假单信息 
	 */
	public void getSaveLeave(String billId, String billType, final String flowPosition) {
		KTHttpClient fh = new KTHttpClient(true);
		AjaxParams params = new AjaxParams();
		params.put("keyId", billId);
		params.put("billType", billType);
		fh.post(KingTellerConfigUtils.CreateUrl(mContext, KingTellerUrlConfig.GetOAAllAduitWorkDate),
				params, new AjaxHttpCallBack<LeaveBean>(mContext,
						new TypeToken<LeaveBean>() {
						}.getType(), true) {

					@Override
					public void onStart() {
						KingTellerProgressUtils.showProgress(mContext, "正在获取中...");
					}
					
					@Override
					public void onError(int errorNo, String strMsg) {
						super.onError(errorNo, strMsg);
						KingTellerProgressUtils.closeProgress();
						T.showShort(mContext, "数据访问超时!");
					}

					@Override
					public void onDo(LeaveBean data) {
						KingTellerProgressUtils.closeProgress();
						if("".equals(data.getCode())){
							if("tab1".equals(tabName)){	
								Intent intent_spqjd = new Intent(mContext, WorkLeaveFromCheckoutActivity.class);
								intent_spqjd.putExtra("isCheckout", 1);
								intent_spqjd.putExtra("mLeaveData", new Gson().toJson(data));
			    				mContext.startActivity(intent_spqjd);
							}else if("tab2".equals(tabName) || "tab3".equals(tabName)){
								Intent intent_ckqjd = new Intent(mContext, WorkLeaveFromCheckoutActivity.class);
								intent_ckqjd.putExtra("isCheckout", 0);
								intent_ckqjd.putExtra("mLeaveData", new Gson().toJson(data));
			    				mContext.startActivity(intent_ckqjd);
							}
							
						}else{
							OpenDialog(data.getCode(), 1);
						}
					};
				});
	}
	
	public ListViewObj getListviewObj() {
		return listviewObj;
	}

	public void setListviewObj(ListViewObj listviewObj) {
		this.listviewObj = listviewObj;
	}
	
	public List<WorkAttendanceBean> getAduitAttendanListData(){
		return workattendancelist;
	}
	
	//打开提示dialog
	public void OpenDialog (String msg, int type){
		if(type == 1){
			
			dialog = new NormalDialog(mContext);
        	KingTellerPromptDialogUtils.showOnePromptDialog(dialog, msg,
					new OnBtnClickL() {
						@Override
						public void onBtnClick() {
							dialog.dismiss();
						}
                    });

		}
	}

}
