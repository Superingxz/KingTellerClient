package com.kingteller.client.activity.attendance.search;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kingteller.R;
import com.kingteller.client.KingTellerApplication;
import com.kingteller.client.adapter.WorkAttendanceSearchPersonnelAdapter;
import com.kingteller.client.adapter.WorkAttendanceSearchPersonnelAdapter.OnItemClickL;
import com.kingteller.client.bean.account.User;
import com.kingteller.client.bean.attendance.WorkAttendanceSearchPeopleBean;
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

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class WorkAttendanceSearchPersonnelActivity extends Activity implements 
	OnClickListener, IXListViewListener, OnItemClickListener{
	
	private TextView title_text;
	private Button title_left_btn, title_righttwo_btn, btn_search;
	private Context mContext;
	
	private EditText edit_search;
	private ListViewObj listviewObj;
	
	private List<WorkAttendanceSearchPeopleBean> machineInfolist = new ArrayList<WorkAttendanceSearchPeopleBean>();
	private WorkAttendanceSearchPersonnelAdapter adapter;
	private Gson gson;
	private String mPeopleType;
	
	@Override
	public void onCreate(Bundle paramBundle) {
		super.onCreate(paramBundle);
		setContentView(R.layout.layout_work_attendance_search_personnel);
		KingTellerApplication.addActivity(this);
		
		mContext = WorkAttendanceSearchPersonnelActivity.this;

		mPeopleType = (String) getIntent().getSerializableExtra("peopleType"); 
		
		if (findViewById(R.id.loading_view) != null) {
			setListviewObj(new ListViewObj(this));
		}

		gson = new Gson();
		initUI();
		initData();
	}
	
	private void initUI() {
		title_text = (TextView) findViewById(R.id.layout_main_text);
		title_left_btn = (Button) findViewById(R.id.layout_main_left_btn);
		title_righttwo_btn = (Button) findViewById(R.id.layout_main_righttwo_btn);
		
		title_text.setText("选择人员");
		title_left_btn.setBackgroundResource(R.drawable.btn_back_arrow);
		title_left_btn.setOnClickListener(this);
		
		title_righttwo_btn.setText("选择");
		title_righttwo_btn.setTextSize(16);
		title_righttwo_btn.setTextColor(Color.parseColor("#FFFFFF")); 
		title_righttwo_btn.setPadding(10, 10, 10, 10);
		title_righttwo_btn.setBackgroundResource(R.drawable.btn_green_bg);
		title_righttwo_btn.setOnClickListener(this);
		
		title_righttwo_btn.setClickable(false);
		
		edit_search = (EditText) findViewById(R.id.oa_edit_search_people);
		edit_search.setHint("工号/名字");
		
		btn_search = (Button) findViewById(R.id.oa_btn_search_people);
		
		btn_search.setOnClickListener(this);
		
		adapter = new WorkAttendanceSearchPersonnelAdapter(mContext, machineInfolist);
		adapter.setOnItemClickLister(new OnItemClickL() {
			@Override
			public void OnItemClick(int pos) {
				if(pos == 0){
					title_righttwo_btn.setText("选择");
					title_righttwo_btn.setClickable(false);
				}else{
					title_righttwo_btn.setText("选择" + "(" + pos+ ")");
					title_righttwo_btn.setClickable(true);
				}
			}
		}); 

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
		getWorkAttendancePersonnelData();
	}
	
	
	public void getWorkAttendancePersonnelData() {

		KTHttpClient fh = new KTHttpClient(true);
		AjaxParams params = new AjaxParams();
		params.put("orgId", User.getInfo(this).getOrgId());
		
		if(!KingTellerJudgeUtils.isEmpty(edit_search.getText().toString())){
			params.put("userInfo", edit_search.getText().toString());
		}
		
		fh.post(KingTellerConfigUtils.CreateUrl(this, KingTellerUrlConfig.GetOAPeopleDate),params, 
				new AjaxHttpCallBack<List<WorkAttendanceSearchPeopleBean>>(this,
						new TypeToken<List<WorkAttendanceSearchPeopleBean>>() {
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
					public void onDo(List<WorkAttendanceSearchPeopleBean> data) {
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
			getWorkAttendancePersonnelData();
			break;
		case R.id.layout_main_righttwo_btn:
			if("1".equals(mPeopleType) || "3".equals(mPeopleType)){//单人          或 	多人 是单选人员
				if(adapter.getWorkerNameCheckedList().size() == 1){
					Intent intent_one = new Intent();
					intent_one.putExtra("peopleType", mPeopleType);
					intent_one.putExtra("peopleData", gson.toJson(adapter.getWorkerNameCheckedList()));
					setResult(RESULT_OK, intent_one);
					finish();
				}else{
					T.showShort(mContext, "您只能选择一项!");
				}
			}else if("2".equals(mPeopleType)){//多人
				Intent intent_two = new Intent();
				intent_two.putExtra("peopleType", mPeopleType);
				intent_two.putExtra("peopleData", gson.toJson(adapter.getWorkerNameCheckedList()));
				setResult(RESULT_OK, intent_two);
				finish();
			}
			break;
		default:
			break;
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onRefresh() {
		getWorkAttendancePersonnelData();
		
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
