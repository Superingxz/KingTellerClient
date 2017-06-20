package com.kingteller.client.activity.map;

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
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kingteller.R;
import com.kingteller.client.KingTellerApplication;
import com.kingteller.client.adapter.StaffLocationAdapter;
import com.kingteller.client.bean.map.StaffLocationBean;
import com.kingteller.client.config.KingTellerUrlConfig;
import com.kingteller.client.netauth.AjaxHttpCallBack;
import com.kingteller.client.netauth.KTHttpClient;
import com.kingteller.client.utils.KingTellerConfigUtils;
import com.kingteller.client.utils.KingTellerJsonUtils;
import com.kingteller.client.utils.KingTellerJudgeUtils;
import com.kingteller.client.utils.KingTellerTimeUtils;
import com.kingteller.client.view.ListViewObj;
import com.kingteller.client.view.XListView.IXListViewListener;
import com.kingteller.client.view.datatype.LoadingEnum;
import com.kingteller.client.view.dialog.SearchGeneralDialog;
import com.kingteller.client.view.dialog.entity.SearchGeneraItem;
import com.kingteller.client.view.dialog.listener.OnBtnSearchClickL;
import com.kingteller.client.view.toast.T;
import com.kingteller.framework.http.AjaxParams;

/**
 * 员工定位
 * 
 * @author 王定波
 * 
 */

public class StaffLocationActivity extends Activity implements OnClickListener, 
	OnItemClickListener, IXListViewListener {
	
	private Context mContext;
	private TextView title_text;
	private Button title_left_btn, title_righttwo_btn;
	
	private StaffLocationAdapter adpater;
	
	private int nowPage = 1;
	
	private ListViewObj listviewObj;

	private SearchGeneraItem mapYg_search = new SearchGeneraItem();
	
	@Override
	public void onCreate(Bundle paramBundle) {
		super.onCreate(paramBundle);
		setContentView(R.layout.common_alllist_view);
		KingTellerApplication.addActivity(this);
		
		if (findViewById(R.id.loading_view) != null) {
			setListviewObj(new ListViewObj(this));
		}
		
		mContext = StaffLocationActivity.this;
		
		initUI();
		initData();
	}

	private void initUI() {
		title_text = (TextView) findViewById(R.id.layout_main_text);
		title_left_btn = (Button) findViewById(R.id.layout_main_left_btn);
		title_righttwo_btn = (Button) findViewById(R.id.layout_main_righttwo_btn);
		
		title_text.setText("员工查询");
		title_left_btn.setBackgroundResource(R.drawable.btn_back_arrow);
		title_left_btn.setOnClickListener(this);
		title_righttwo_btn.setBackgroundResource(R.drawable.btn_serach);
		title_righttwo_btn.setOnClickListener(this);


		adpater = new StaffLocationAdapter(this, new ArrayList<StaffLocationBean>());
		getListviewObj().getListview().setAdapter(adpater);
		getListviewObj().getListview().setRefreshEnabled(false);
		getListviewObj().getListview().setLoadMoreEnabled(true);
		getListviewObj().getListview().setOnRefreshListener(this);
		getListviewObj().getListview().setOnItemClickListener(this);
		getListviewObj().setStatus(LoadingEnum.TIP, "请设置查询条件");
	}
	
	private void initData() {
		mapYg_search.setYgCxrq(KingTellerTimeUtils.getNowDayAndTime(0)); 
		mapYg_search.setYgKssj("00:00"); 
		mapYg_search.setYgJssj("23:59"); 
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.layout_main_left_btn:
			finish();
			break;
		case R.id.layout_main_righttwo_btn:
			SearchGeneraItem str = new SearchGeneraItem();
			str = mapYg_search;

			final SearchGeneralDialog searchgeneral_dialog = new SearchGeneralDialog(mContext, R.style.Login_dialog, 3, new Gson().toJson(str));
			searchgeneral_dialog.setOnBtnSearchClickL(
				new OnBtnSearchClickL() {
	                @Override
	                public void onBtnClick(String ss) {
	                	searchgeneral_dialog.dismiss();
	                	mapYg_search = KingTellerJsonUtils.getPerson(ss, SearchGeneraItem.class);
	                	nowPage = 1;
	                	getStaffs();
	                }
	            },
	            new OnBtnSearchClickL() {
	                @Override
	                public void onBtnClick(String qx) {
	                	searchgeneral_dialog.dismiss();
	                }
	            });
			
			searchgeneral_dialog.show();
			
			break;
		default:
			break;
		}
	}

	private void getStaffs() {
		if (!KingTellerJudgeUtils.isNetAvaliable(this)) {
			T.showShort(mContext, "没有网络，请检查网络是否可用!");
			return;
		}

		KTHttpClient fh = new KTHttpClient(true);
		AjaxParams params = new AjaxParams();
		
		params.put("page", String.valueOf(nowPage));
		params.put("phoneFlag", "1");
		params.put("roleName", "");
		
		params.put("userNameLike", mapYg_search.getYgYhxm());//用户姓名
		params.put("userAccountLike", mapYg_search.getYgYhzh());//用户账号
		params.put("userOrgName", mapYg_search.getYgSsbm());//所属部门
		
		params.put("monitorDay", mapYg_search.getYgCxrq());//查询日期
		
		String[] strstart = mapYg_search.getYgKssj().split(":");//开始时间
		params.put("startHour",strstart[0]);
		params.put("startMinute", strstart[1]);
		
		String[] strend = mapYg_search.getYgJssj().split(":");//结束时间
		params.put("endHour", strend[0]);
		params.put("endMinute", strend[1]);

		fh.post(KingTellerConfigUtils.CreateUrl(this, KingTellerUrlConfig.StaffListUrl), params,
				new AjaxHttpCallBack<List<StaffLocationBean>>(this,
						new TypeToken<List<StaffLocationBean>>() {
						}.getType(), true) {

					@Override
					public void onSuccess(String response) {
						if(KingTellerJudgeUtils.isEmpty(response))
							response="[]";
						super.onSuccess(response);
					}

					@Override
					public void onFinish() {
						getListviewObj().getListview().stopLoadMore();
					}

					@Override
					public void onStart() {
						if (nowPage == 1){
							getListviewObj().setStatus(LoadingEnum.LOADING);
						}
					}

					@Override
					public void onDo(List<StaffLocationBean> data) {
						if (data.size() > 0) {
							if (nowPage == 1){
								adpater.setLists(data);
								getListviewObj().setStatus(LoadingEnum.LISTSHOW);
							}else{
								adpater.addLists(data);
							}
						}else{
							if (nowPage == 1){
								getListviewObj().setStatus(LoadingEnum.NODATA);
							}else{
								getListviewObj().getListview().setLoadMoreEnabled(false);
							}
						}
					};
				});
	}

	@Override
	public void onItemClick(AdapterView<?> adapterView, View arg1, int pos, long arg3) {
		StaffLocationBean data = (StaffLocationBean) adapterView.getAdapter().getItem(pos);
		Intent intent = new Intent(this, StaffLocusActivity.class);
		intent.putExtra("data", data);
		startActivity(intent);

	}

	@Override
	public void onRefresh() {
	}

	@Override
	public void onLoadMore() {
		nowPage++;
		getStaffs();

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
