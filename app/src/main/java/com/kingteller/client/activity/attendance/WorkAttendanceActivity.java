package com.kingteller.client.activity.attendance;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kingteller.R;
import com.kingteller.client.KingTellerApplication;
import com.kingteller.client.activity.attendance.checkout.WorkAbsentFromCheckoutActivity;
import com.kingteller.client.activity.attendance.checkout.WorkBoardFromCheckoutActivity;
import com.kingteller.client.activity.attendance.checkout.WorkBusinessTripFromCheckoutActivity;
import com.kingteller.client.activity.attendance.checkout.WorkLeaveFromCheckoutActivity;
import com.kingteller.client.activity.attendance.checkout.WorkOvertimeFormCheckoutActivity;
import com.kingteller.client.activity.attendance.checkout.WorkSickLeaveFromCheckoutActivity;
import com.kingteller.client.activity.attendance.fillin.WorkAbsentFromActivity;
import com.kingteller.client.activity.attendance.fillin.WorkBoardFromActivity;
import com.kingteller.client.activity.attendance.fillin.WorkBusinessTripFromActivity;
import com.kingteller.client.activity.attendance.fillin.WorkLeaveFormActivity;
import com.kingteller.client.activity.attendance.fillin.WorkOvertimeFormActivity;
import com.kingteller.client.activity.attendance.fillin.WorkSickLeaveFromActivity;
import com.kingteller.client.adapter.WorkAttendanAdapter;
import com.kingteller.client.bean.attendance.AbsentBean;
import com.kingteller.client.bean.attendance.BoardBean;
import com.kingteller.client.bean.attendance.BusinessTripBean;
import com.kingteller.client.bean.attendance.LeaveBean;
import com.kingteller.client.bean.attendance.OverTimeBean;
import com.kingteller.client.bean.attendance.SickLeaveBean;
import com.kingteller.client.bean.attendance.WorkAttendanceBean;
import com.kingteller.client.config.KingTellerStaticConfig;
import com.kingteller.client.config.KingTellerUrlConfig;
import com.kingteller.client.netauth.AjaxHttpCallBack;
import com.kingteller.client.netauth.KTHttpClient;
import com.kingteller.client.utils.KingTellerConfigUtils;
import com.kingteller.client.view.ListViewObj;
import com.kingteller.client.view.XListView.IXListViewListener;
import com.kingteller.client.view.datatype.LoadingEnum;
import com.kingteller.client.view.dialog.NormalDialog;
import com.kingteller.client.view.dialog.listener.OnBtnClickL;
import com.kingteller.client.view.dialog.use.KingTellerProgressUtils;
import com.kingteller.client.view.dialog.use.KingTellerPromptDialogUtils;
import com.kingteller.client.view.popupwindow.WorkAttendanPopupWindow;
import com.kingteller.client.view.popupwindow.WorkAttendanPopupWindow.AttendanOnItemListener;
import com.kingteller.client.view.toast.T;
import com.kingteller.framework.http.AjaxParams;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.TextView;

public class WorkAttendanceActivity extends Activity implements OnClickListener, OnRefreshListener, OnItemClickListener, IXListViewListener{
	
	private Context mContext;
	private TextView title_text;
	private Button title_left_btn, title_rightone_btn, title_righttwo_btn;
	
	private WorkAttendanAdapter workAttendanAdapter;
	private List<WorkAttendanceBean> workattendancelist;
	private List<String> mWorkAttendanTypeTexts;
	private List<Integer> mWorkAttendanTypeimages; // 选项图标
	private WorkAttendanPopupWindow mWorkAttendanPopupWindow;
	private ListViewObj listviewObj;
	
	private int mPage = 1;
	private boolean isFirst = true;
	
	private NormalDialog dialog = null; 	//初始化通用dialog
	
	@Override
	public void onCreate(Bundle paramBundle) {
		super.onCreate(paramBundle);
		setContentView(R.layout.common_alllist_view);
		KingTellerApplication.addActivity(this);
		
		if (findViewById(R.id.loading_view) != null) {
			setListviewObj(new ListViewObj(this));
		}
		
		mContext = WorkAttendanceActivity.this;
		
		initUI();
		initData();
	}
	
	private void initUI() {
		title_text = (TextView) findViewById(R.id.layout_main_text);
		title_left_btn = (Button) findViewById(R.id.layout_main_left_btn);
		title_rightone_btn = (Button) findViewById(R.id.layout_main_rightone_btn);
		title_righttwo_btn = (Button) findViewById(R.id.layout_main_righttwo_btn);
		
		title_text.setText("我的考勤");
		
		title_left_btn.setBackgroundResource(R.drawable.btn_back_arrow);
		title_left_btn.setOnClickListener(this);
		
		title_rightone_btn.setBackgroundResource(R.drawable.btn_serach);
		title_rightone_btn.setOnClickListener(this);
		
		title_righttwo_btn.setBackgroundResource(R.drawable.btn_add);
		title_righttwo_btn.setOnClickListener(this);
		
		workattendancelist = new ArrayList<WorkAttendanceBean>();
		workAttendanAdapter = new WorkAttendanAdapter(mContext, workattendancelist);
	
		getListviewObj().getListview().setAdapter(workAttendanAdapter);
		getListviewObj().getListview().setRefreshEnabled(true);
		getListviewObj().getListview().setLoadMoreEnabled(true);
		getListviewObj().getListview().setOnRefreshListener(this);
		getListviewObj().getListview().setOnItemClickListener(this);
		
		getListviewObj().setOnClickLister(LoadingEnum.NODATA, res);
		getListviewObj().setOnClickLister(LoadingEnum.NETERROR, res);
	}

	
	private void initData() {
		
		mWorkAttendanTypeTexts = new ArrayList<String>();
		mWorkAttendanTypeimages = new ArrayList<Integer>();
		
		mWorkAttendanTypeTexts = addItems(new String[]{"加班申请", "请假申请", "销假申请", "出差申请", "补登考勤", "旷工报告"});
		mWorkAttendanTypeimages = addItems(new Integer[] { R.drawable.ic_oa_overtime, R.drawable.ic_oa_leave,
				R.drawable.ic_oa_sickleave, R.drawable.ic_oa_business, 
				R.drawable.ic_oa_board, R.drawable.ic_oa_absenteeism});
		
		mWorkAttendanPopupWindow = new WorkAttendanPopupWindow(this, mWorkAttendanTypeTexts, mWorkAttendanTypeimages);
		mWorkAttendanPopupWindow.setBackgroundDrawable(new ColorDrawable(0xb0000000));//设置透明背景
		mWorkAttendanPopupWindow.setAttendanOnItemListener(listener);
		
		onRefresh();
		isFirst = false;
	}
	
	
	
	private OnClickListener res = new OnClickListener() {
		@Override
		public void onClick(View view) {
			onRefresh();
		}
	};
	
	
	/**
	 * PopupWindow 点击事件
	 */
	AttendanOnItemListener listener = new AttendanOnItemListener() {	
		@Override
		public void onItem(int num) {
			mWorkAttendanPopupWindow.dismiss();
			switch (num) {
				case 0://加班
					Intent intent_xjjbd = new Intent(mContext, WorkOvertimeFormActivity.class);
					intent_xjjbd.putExtra("overtimeData", "");
    				mContext.startActivity(intent_xjjbd);
					break;
				case 1://请假
					Intent intent_xjqjd = new Intent(mContext, WorkLeaveFormActivity.class);
					intent_xjqjd.putExtra("mLeaveData", "");
    				mContext.startActivity(intent_xjqjd);
					break;
				case 2://销假
					Intent intent_xjxjd = new Intent(mContext, WorkSickLeaveFromActivity.class);
					intent_xjxjd.putExtra("mSickLeaveData", "");
    				mContext.startActivity(intent_xjxjd);
					break;
				case 3://出差
					Intent intent_xjccd = new Intent(mContext, WorkBusinessTripFromActivity.class);
					intent_xjccd.putExtra("mBusinessData", "");
    				mContext.startActivity(intent_xjccd);
					break;
				case 4://补登
					Intent intent_xjbdd = new Intent(mContext, WorkBoardFromActivity.class);
					intent_xjbdd.putExtra("mBoardData", "");
    				mContext.startActivity(intent_xjbdd);
					break;
				case 5://旷工
					Intent intent_xjkgd = new Intent(mContext, WorkAbsentFromActivity.class);
					intent_xjkgd.putExtra("mAbsenData", "");
    				mContext.startActivity(intent_xjkgd);
					break;
				default:
					break;
			}
			
			
		}
	};

	@Override  
	public void onRestart() {  
        super.onRestart();  
        if(KingTellerStaticConfig.OA_WORK_ATTENDANCE_ISUDATE){
        	onRefresh();
        	KingTellerStaticConfig.OA_WORK_ATTENDANCE_ISUDATE = false;
        }
    }  
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.layout_main_left_btn:
				finish();
				break;
			case R.id.layout_main_rightone_btn:
				startActivity(new Intent(mContext, WorkAttendanceHistoricalActivity.class));
				break;
			case R.id.layout_main_righttwo_btn:
				openPopup();
				break;
			default:
				break;
		}
	}
	
	//单据类型 		kqmgrOvertimeFlow：加班	kqmgrLeaveFlow：请假
	//			kqmgrSickleaveFlow：销假	kqmgrAbsenteeismFlow：旷工
	//			kqmgrFILLFlow：补登		kqmgrTravelFlow：出差
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
	 * 打开popup
	 */
	private void openPopup() {
		if (mWorkAttendanPopupWindow.isShowing()) {
			mWorkAttendanPopupWindow.dismiss();
		} else {
		    int xpos= KingTellerStaticConfig.SCREEN.Width - mWorkAttendanPopupWindow.getWidth();
			mWorkAttendanPopupWindow.showAsDropDown(this.findViewById(R.id.layout_main_relativelayout), xpos - 20, 0);
			mWorkAttendanPopupWindow.isShowing();
		}
	}

	
	@Override
	public void onRefresh() {
		mPage = 1;
		getWorkAttendanceData();
	}
	
	@Override
	public void onLoadMore() {
		mPage++;
		getWorkAttendanceData();
	}
	
	public void getWorkAttendanceData() {

		KTHttpClient fh = new KTHttpClient(true);
		AjaxParams params = new AjaxParams();
		String NewPage = Integer.toString(mPage);
		params.put("page", NewPage);//页数

		fh.post(KingTellerConfigUtils.CreateUrl(this, KingTellerUrlConfig.OAAttendanceList),params, 
				new AjaxHttpCallBack<List<WorkAttendanceBean>>(this,
						new TypeToken<List<WorkAttendanceBean>>() {
						}.getType(), true) {

					@Override
					public void onFinish() {
						getListviewObj().getListview().stopRefresh();
						getListviewObj().getListview().stopLoadMore();
					}
					
					@Override
					public void onStart() {
						if (mPage == 1 && isFirst == true)
							getListviewObj().setStatus(LoadingEnum.LOADING);
					}
					
					@Override
					public void onError(int errorNo, String strMsg) {
						super.onError(errorNo, strMsg);
						getListviewObj().setStatus(LoadingEnum.NETERROR);
						T.showShort(mContext, "数据访问超时!");
					}
					
					@Override
					public void onDo(List<WorkAttendanceBean> data) {
						if (data.size() > 0 && data.get(0).getCode().equals("")) {
							if(mPage == 1){
								workattendancelist = data;
								workAttendanAdapter.setLists(workattendancelist);
								getListviewObj().setStatus(LoadingEnum.LISTSHOW);
								getListviewObj().getListview().setLoadMoreEnabled(true);
							}else{
								workAttendanAdapter.addLists(data);
							}
						}else {
							if(mPage == 1){
								getListviewObj().setStatus(LoadingEnum.NODATA, getString(R.string.no_data_try));
								isFirst = true;
							}else{
								getListviewObj().getListview().setLoadMoreEnabled(false);
							}
							T.showShort(mContext, data.get(0).getCode());
						}
					
					};
				});
	}
	
	
	
	
	
	/**
	 * 获取当前加班单信息 
	 */
	public void getSaveOvertime(String billId, String billType, final String flowPosition) {
		KTHttpClient fh = new KTHttpClient(true);
		AjaxParams params = new AjaxParams();
		params.put("keyId", billId);
		params.put("billType", billType);
		fh.post(KingTellerConfigUtils.CreateUrl(this, KingTellerUrlConfig.GetOAAllWorkDate), params, 
				new AjaxHttpCallBack<OverTimeBean>(this,
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
							if("ZC".equals(flowPosition) || "TH".equals(flowPosition)){//修改
								Intent intent_xgjbd = new Intent(mContext, WorkOvertimeFormActivity.class);
								intent_xgjbd.putExtra("overtimeData", new Gson().toJson(data));
			    				mContext.startActivity(intent_xgjbd);
							}else if("SPZ".equals(flowPosition) || "YWC".equals(flowPosition)){//查看
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
		fh.post(KingTellerConfigUtils.CreateUrl(this, KingTellerUrlConfig.GetOAAllWorkDate), params, 
				new AjaxHttpCallBack<BusinessTripBean>(this,
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
							if("ZC".equals(flowPosition) || "TH".equals(flowPosition)){//修改
								Intent intent_xgccd = new Intent(mContext, WorkBusinessTripFromActivity.class);
								intent_xgccd.putExtra("mBusinessData", new Gson().toJson(data));
			    				mContext.startActivity(intent_xgccd);
							}else if("SPZ".equals(flowPosition) || "YWC".equals(flowPosition)){//查看
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
		fh.post(KingTellerConfigUtils.CreateUrl(this, KingTellerUrlConfig.GetOAAllWorkDate), params, 
				new AjaxHttpCallBack<BoardBean>(this,
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
							if("ZC".equals(flowPosition) || "TH".equals(flowPosition)){//修改
								Intent intent_xgbdd = new Intent(mContext, WorkBoardFromActivity.class);
								intent_xgbdd.putExtra("mBoardData", new Gson().toJson(data));
			    				mContext.startActivity(intent_xgbdd);
							}else if("SPZ".equals(flowPosition) || "YWC".equals(flowPosition)){//查看
								Intent intent_ckbdd = new Intent(mContext, WorkBoardFromCheckoutActivity.class);
								intent_ckbdd.putExtra("isCheckout", 0);
								intent_ckbdd.putExtra("mBoardData", new Gson().toJson(data));
			    				mContext.startActivity(intent_ckbdd);
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
		fh.post(KingTellerConfigUtils.CreateUrl(this, KingTellerUrlConfig.GetOAAllWorkDate), params, 
				new AjaxHttpCallBack<AbsentBean>(this,
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
							if("ZC".equals(flowPosition) || "TH".equals(flowPosition)){//修改
								Intent intent_xgkgd = new Intent(mContext, WorkAbsentFromActivity.class);
								intent_xgkgd.putExtra("mAbsenData", new Gson().toJson(data));
			    				mContext.startActivity(intent_xgkgd);
							}else if("SPZ".equals(flowPosition) || "YWC".equals(flowPosition)){//查看
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
		fh.post(KingTellerConfigUtils.CreateUrl(this, KingTellerUrlConfig.GetOAAllWorkDate), params, 
				new AjaxHttpCallBack<SickLeaveBean>(this,
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
							if("ZC".equals(flowPosition) || "TH".equals(flowPosition)){//修改
								Intent intent_xgxjd = new Intent(mContext, WorkSickLeaveFromActivity.class);
								intent_xgxjd.putExtra("mSickLeaveData", new Gson().toJson(data));
			    				mContext.startActivity(intent_xgxjd);
							}else if("SPZ".equals(flowPosition) || "YWC".equals(flowPosition)){//查看
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
	 * 获取当前请假单信息
	 */
	public void getSaveLeave(String billId, String billType, final String flowPosition) {
		KTHttpClient fh = new KTHttpClient(true);
		AjaxParams params = new AjaxParams();
		params.put("keyId", billId);
		params.put("billType", billType);
		fh.post(KingTellerConfigUtils.CreateUrl(this, KingTellerUrlConfig.GetOAAllWorkDate), params, 
				new AjaxHttpCallBack<LeaveBean>(this,
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
							if("ZC".equals(flowPosition) || "TH".equals(flowPosition)){//修改
								Intent intent_xgqjd = new Intent(mContext, WorkLeaveFormActivity.class);
								intent_xgqjd.putExtra("mLeaveData", new Gson().toJson(data));
			    				mContext.startActivity(intent_xgqjd);
							}else if("SPZ".equals(flowPosition) || "YWC".equals(flowPosition)){//查看
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
	
	/**
	 * 转换为List<String>
	 * 用于菜单栏中的菜单项图标赋值
	 * @param values
	 * @return
	 */
	private List<String> addItems(String[] values) {
		List<String> list = new ArrayList<String>();
		for (String var : values) {
			list.add(var);
		}
		return list;
	}
	
	/**
	 * 转换为List<Integer>
	 * 用于菜单栏中的标题赋值
	 * @param values
	 * @return
	 */
	private List<Integer> addItems(Integer[] values) {
		List<Integer> list = new ArrayList<Integer>();
		for (Integer var : values) {
			list.add(var);
		}
		return list;
	}
	
	public ListViewObj getListviewObj() {
		return listviewObj;
	}

	public void setListviewObj(ListViewObj listviewObj) {
		this.listviewObj = listviewObj;
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
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		KingTellerApplication.removeActivity(this);
	}
}
