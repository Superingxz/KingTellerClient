package com.kingteller.client.activity.attendance;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kingteller.R;
import com.kingteller.client.KingTellerApplication;
import com.kingteller.client.adapter.WorkAttendanAduitAdapter;
import com.kingteller.client.bean.attendance.OverTimeBean;
import com.kingteller.client.bean.attendance.WorkAttendanceBean;
import com.kingteller.client.config.KingTellerStaticConfig;
import com.kingteller.client.config.KingTellerUrlConfig;
import com.kingteller.client.netauth.AjaxHttpCallBack;
import com.kingteller.client.netauth.KTHttpClient;
import com.kingteller.client.utils.KingTellerConfigUtils;
import com.kingteller.client.utils.KingTellerJsonUtils;
import com.kingteller.client.view.ListViewObj;
import com.kingteller.client.view.XListView.IXListViewListener;
import com.kingteller.client.view.datatype.LoadingEnum;
import com.kingteller.client.view.dialog.AduitGeneralDialog;
import com.kingteller.client.view.dialog.NormalDialog;
import com.kingteller.client.view.dialog.listener.OnBtnClickL;
import com.kingteller.client.view.dialog.listener.OnBtnSearchClickL;
import com.kingteller.client.view.dialog.use.KingTellerProgressUtils;
import com.kingteller.client.view.dialog.use.KingTellerPromptDialogUtils;
import com.kingteller.client.view.toast.T;
import com.kingteller.framework.http.AjaxParams;

public class AduitBatchAttendanceActivity extends Activity implements 
	OnClickListener, IXListViewListener, OnItemClickListener{
	
	private TextView title_text;
	private Button title_left_btn, btn_selectall, btn_aduit;
	private Context mContext;
	private ListViewObj listviewObj;
	private WorkAttendanAduitAdapter aduitAdapter;
	private List<WorkAttendanceBean> workattendancelist;
	private String listData;
	
	private int mPage = 1;
	private Boolean isFirst = true;
	
	private NormalDialog dialog = null; 	//初始化通用dialog

	public void onCreate(Bundle paramBundle) {
		super.onCreate(paramBundle);
		setContentView(R.layout.layout_work_attendance_aduit_batch);
		KingTellerApplication.addActivity(this);
		
		if (findViewById(R.id.loading_view) != null) {
			setListviewObj(new ListViewObj(this));
		}
		
		mContext = AduitBatchAttendanceActivity.this;
		
		listData = (String) getIntent().getStringExtra("mAduitAttendanListData");
		
		KingTellerStaticConfig.OA_ADUIT_ATTENDANCE_ISUDATE = false;

		initUI();
		initData();
	}
	
	private void initUI() {
		title_text = (TextView) findViewById(R.id.layout_main_text);
		title_left_btn = (Button) findViewById(R.id.layout_main_left_btn);
		
		title_text.setText("审批多选");
		title_left_btn.setBackgroundResource(R.drawable.btn_back_arrow);
		title_left_btn.setOnClickListener(this);
		
		btn_aduit = (Button) findViewById(R.id.oa_aduit_attendance_aduit);

		btn_selectall = (Button) findViewById(R.id.oa_aduit_attendance_selectall);
		
		btn_selectall.setOnClickListener(this);
		btn_aduit.setOnClickListener(this);

		
		workattendancelist = new ArrayList<WorkAttendanceBean>();
		aduitAdapter = new WorkAttendanAduitAdapter(mContext, workattendancelist, "tab4");
		getListviewObj().getListview().setAdapter(aduitAdapter);
		getListviewObj().getListview().setRefreshEnabled(true);
		getListviewObj().getListview().setLoadMoreEnabled(true);
		getListviewObj().getListview().setOnRefreshListener(this);

		getListviewObj().setOnClickLister(LoadingEnum.NODATA, res);
		getListviewObj().setOnClickLister(LoadingEnum.NETERROR, res);
		
	}
	
	private void initData() {
		workattendancelist = KingTellerJsonUtils.getPersons(listData, WorkAttendanceBean.class);
		
		if(workattendancelist.size() > 0){
			aduitAdapter.setLists(workattendancelist);
			getListviewObj().setStatus(LoadingEnum.LISTSHOW);
			isFirst = false;
		}else{
			getListviewObj().setStatus(LoadingEnum.NODATA, getString(R.string.no_data_try));
			isFirst = true;
		}

	}
	
	private OnClickListener res = new OnClickListener() {
		@Override
		public void onClick(View view) {
			getListviewObj().setStatus(LoadingEnum.LOADING);
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
		params.put("status", "1");

		fh.post(KingTellerConfigUtils.CreateUrl(mContext, KingTellerUrlConfig.OAAduitAttendanceList),
				params, new AjaxHttpCallBack<List<WorkAttendanceBean>>(mContext,
						new TypeToken<List<WorkAttendanceBean>>() {
						}.getType(), true) {

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
	
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void onClick(View v) {
		// 获取当前的数据数量
		int count = aduitAdapter.getCount();
		
		switch (v.getId()) {
			case R.id.layout_main_left_btn:
				finish();
				break;
			case R.id.oa_aduit_attendance_aduit:
	
				if(count == 0){
					T.showShort(mContext, "暂无数据!");
					return;
				}
				
				HashMap<Integer, Boolean> map = aduitAdapter.getCheckMap();
				int inSelect = 0;
				for (int i = 0; i < count; i++) {
					if (map.get(i) != null && map.get(i)) {
						inSelect ++;
					}
				}
				if(inSelect == 0){
					T.showShort(mContext, "没有选择数据!");
					return;
				}
				
				final AduitGeneralDialog aduitgeneral_dialog = new AduitGeneralDialog(mContext, R.style.Login_dialog, 0);
				aduitgeneral_dialog.setOnBtnSearchClickL(
					new OnBtnSearchClickL() {
		                @Override
		                public void onBtnClick(String ty) {
		                	aduitgeneral_dialog.dismiss();
		                	setAduitAllAttendanceData(0, ty);
		                }
		            },
		            new OnBtnSearchClickL() {
		                @Override
		                public void onBtnClick(String th) {
		                	aduitgeneral_dialog.dismiss();
		                	setAduitAllAttendanceData(1, th);
		                }
		            },new OnBtnSearchClickL() {
		                @Override
		                public void onBtnClick(String qx) {
		                	aduitgeneral_dialog.dismiss();
		                }
		        });
				
				aduitgeneral_dialog.show();
				break;
			case R.id.oa_aduit_attendance_selectall:
				if(count == 0){
					T.showShort(mContext, "暂无数据!");
					return;
				}
				
				if ("全选".equals(btn_selectall.getText().toString())) {
					aduitAdapter.configCheckMap(true);
					aduitAdapter.notifyDataSetChanged();
					btn_selectall.setText("全不选");
				} else {
					aduitAdapter.configCheckMap(false);
					aduitAdapter.notifyDataSetChanged();
					btn_selectall.setText("全选");
				}
				break;
			default:
				break;
			}
		
	}

	public ListViewObj getListviewObj() {
		return listviewObj;
	}

	public void setListviewObj(ListViewObj listviewObj) {
		this.listviewObj = listviewObj;
	}
	
	/**
	 * 批量审批    0同意   1退回
	 */
	public void setAduitAllAttendanceData(final int num, String remark){
		
		//获取选中list
		List<WorkAttendanceBean> data = new ArrayList<WorkAttendanceBean>();
		for (int i = 0; i < aduitAdapter.getCount(); i++) {
			if (aduitAdapter.getCheckMap().get(i) != null && aduitAdapter.getCheckMap().get(i)) {
				data.add((WorkAttendanceBean) aduitAdapter.getItem(i));
			}
		}
				
		
		KTHttpClient fh = new KTHttpClient(true);
		AjaxParams params = new AjaxParams();
		
		//上传数据
		String batchFlag = "";
		if(num == 0){
			batchFlag = "bacthaudit";
		}else{
			batchFlag = "bacthback";
		}

		params.put("batchFlag", batchFlag);
		params.put("remark", remark);
		params.put("billList", new Gson().toJson(data));
		
		fh.post(KingTellerConfigUtils.CreateUrl(this, KingTellerUrlConfig.OAAduitAttendanceAllApproval), params, 
				new AjaxHttpCallBack<OverTimeBean>(this,
						new TypeToken<OverTimeBean>() {
						}.getType(), true) {
			
					@Override
					public void onStart() {
						if(num == 0){
							KingTellerProgressUtils.showProgress(mContext, "正在审批中...");
						}else{
							KingTellerProgressUtils.showProgress(mContext, "正在退回中...");
						}	
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
							
							KingTellerStaticConfig.OA_ADUIT_ATTENDANCE_ISUDATE = true;
							
							if(num == 0){
								T.showShort(mContext, "审批成功!");
							}else{
								T.showShort(mContext, "退回成功!");
							}
							onRefresh();
						}else{
							dialog = new NormalDialog(mContext);
							if(num == 2){
								KingTellerPromptDialogUtils.showOnePromptDialog(dialog, "审批失败!" + data.getCode(),
				    					new OnBtnClickL() {
				    						@Override
				    						public void onBtnClick() {
				    							dialog.dismiss();
				    						}
				                        });
							}else{
								KingTellerPromptDialogUtils.showOnePromptDialog(dialog, "退回失败!" + data.getCode(),
				    					new OnBtnClickL() {
				    						@Override
				    						public void onBtnClick() {
				    							dialog.dismiss();
				    						}
				                        });
							}	 
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
