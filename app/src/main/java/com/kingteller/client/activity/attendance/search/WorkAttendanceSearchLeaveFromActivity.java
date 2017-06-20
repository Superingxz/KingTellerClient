package com.kingteller.client.activity.attendance.search;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kingteller.R;
import com.kingteller.client.KingTellerApplication;
import com.kingteller.client.adapter.WorkAttendanceSearchLeaveFromAdapter;
import com.kingteller.client.bean.attendance.WorkAttendanceSearchLeaveFromBean;
import com.kingteller.client.config.KingTellerUrlConfig;
import com.kingteller.client.netauth.AjaxHttpCallBack;
import com.kingteller.client.netauth.KTHttpClient;
import com.kingteller.client.utils.KingTellerConfigUtils;
import com.kingteller.client.utils.KingTellerJudgeUtils;
import com.kingteller.client.view.ListViewObj;
import com.kingteller.client.view.XListView.IXListViewListener;
import com.kingteller.client.view.datatype.LoadingEnum;
import com.kingteller.client.view.toast.T;
import com.kingteller.framework.http.AjaxParams;

public class WorkAttendanceSearchLeaveFromActivity extends Activity implements 
	OnClickListener, IXListViewListener, OnItemClickListener{
	
	private TextView title_text;
	private Button title_left_btn, btn_search;
	private Context mContext;
	
	private EditText edit_search;
	private ListViewObj listviewObj;
	
	private List<WorkAttendanceSearchLeaveFromBean> machineInfolist = new ArrayList<WorkAttendanceSearchLeaveFromBean>();
	private WorkAttendanceSearchLeaveFromAdapter adapter;
	private String mSickUserId;
	
	public void onCreate(Bundle paramBundle) {
		super.onCreate(paramBundle);
		setContentView(R.layout.layout_work_attendance_search_personnel);
		KingTellerApplication.addActivity(this);
		
		mContext = WorkAttendanceSearchLeaveFromActivity.this;

		mSickUserId = (String) getIntent().getSerializableExtra("SickUserId"); 
		
		if (findViewById(R.id.loading_view) != null) {
			setListviewObj(new ListViewObj(this));
		}
		
		initUI();
		initData();
	}
	
	private void initUI() {
		title_text = (TextView) findViewById(R.id.layout_main_text);
		title_left_btn = (Button) findViewById(R.id.layout_main_left_btn);
		
		title_text.setText("选择加班单");
		title_left_btn.setBackgroundResource(R.drawable.btn_back_arrow);
		title_left_btn.setOnClickListener(this);
		
		
		edit_search = (EditText) findViewById(R.id.oa_edit_search_people);
		edit_search.setHint("单号");
		
		btn_search = (Button) findViewById(R.id.oa_btn_search_people);
		
		btn_search.setOnClickListener(this);
		
		adapter = new WorkAttendanceSearchLeaveFromAdapter(mContext, machineInfolist);

		getListviewObj().getListview().setAdapter(adapter);
		getListviewObj().getListview().setRefreshEnabled(true);
		getListviewObj().getListview().setLoadMoreEnabled(false);
		getListviewObj().getListview().setOnRefreshListener(this);
		getListviewObj().getListview().setOnItemClickListener(this);
		getListviewObj().setStatus(LoadingEnum.NODATA, "请输入条件进行搜索");
		
		KingTellerConfigUtils.hideInputMethod(this);
	}
	
	private void initData() {
		getListviewObj().setStatus(LoadingEnum.LOADING);
		getWorkAttendanceLeaveFromData();
	}
	
	public void getWorkAttendanceLeaveFromData() {

		KTHttpClient fh = new KTHttpClient(true);
		AjaxParams params = new AjaxParams();
		params.put("userId", mSickUserId);
		
		if(!KingTellerJudgeUtils.isEmpty(edit_search.getText().toString())){
			params.put("leaveNo", edit_search.getText().toString());
		}
		
		fh.post(KingTellerConfigUtils.CreateUrl(this, KingTellerUrlConfig.GetOALeaveFromDate),params, 
				new AjaxHttpCallBack<List<WorkAttendanceSearchLeaveFromBean>>(this,
						new TypeToken<List<WorkAttendanceSearchLeaveFromBean>>() {
						}.getType(), true) {

					@Override
					public void onFinish() {
						getListviewObj().getListview().stopRefresh();
						getListviewObj().getListview().stopLoadMore();
					}
					
					@Override
					public void onStart() {
						
					}
					
					@Override
					public void onError(int errorNo, String strMsg) {
						super.onError(errorNo, strMsg);
						getListviewObj().setStatus(LoadingEnum.NETERROR);
						T.showShort(mContext, "数据访问超时!");
					}
					
					@Override
					public void onDo(List<WorkAttendanceSearchLeaveFromBean> data) {
						if("".equals(data.get(0).getCode())){
							machineInfolist = data;
							adapter.setLists(machineInfolist);
							getListviewObj().setStatus(LoadingEnum.LISTSHOW);
						}else{
							T.showShort(mContext, data.get(0).getCode());
							getListviewObj().setStatus(LoadingEnum.NODATA);
						}
					
					};
				});
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.layout_main_left_btn:
			finish();
			break;
		case R.id.oa_btn_search_people:
			getListviewObj().setStatus(LoadingEnum.LOADING);
			getWorkAttendanceLeaveFromData();
			break;
		default:
			break;
		}
		
	}
	
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		WorkAttendanceSearchLeaveFromBean data = (WorkAttendanceSearchLeaveFromBean) parent.getAdapter().getItem(position);
		Intent intent_one = new Intent();
		intent_one.putExtra("LeaveFromData", new Gson().toJson(data));
		setResult(RESULT_OK, intent_one);
		finish();
	}

	@Override
	public void onRefresh() {
		getWorkAttendanceLeaveFromData();
	}

	@Override
	public void onLoadMore() {
		// TODO Auto-generated method stub
		
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
