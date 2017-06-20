package com.kingteller.client.activity.onlinelearning;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.google.gson.reflect.TypeToken;
import com.kingteller.R;
import com.kingteller.client.KingTellerApplication;
import com.kingteller.client.activity.base.BaseActivity;
import com.kingteller.client.adapter.WorkListAdadpater;
import com.kingteller.client.bean.common.BasePageBean;
import com.kingteller.client.bean.onlinelearning.VideoListBean;
import com.kingteller.client.bean.onlinelearning.WorkListBean;
import com.kingteller.client.config.KingTellerUrlConfig;
import com.kingteller.client.netauth.AjaxHttpCallBack;
import com.kingteller.client.netauth.KTHttpClient;
import com.kingteller.client.utils.KingTellerConfigUtils;
import com.kingteller.client.view.XListView.IXListViewListener;
import com.kingteller.client.view.datatype.LoadingEnum;
import com.kingteller.client.view.toast.T;
import com.kingteller.framework.http.AjaxParams;

public class OnlineWorkListActivity extends BaseActivity implements
	OnClickListener, IXListViewListener, OnItemClickListener {

	private String libaryId; 
	private List<WorkListBean> workList = new ArrayList<WorkListBean>();
	private WorkListAdadpater adapter;
	
	private Context mContext;
	@Override
	public void onCreate(Bundle paramBundle) {
		super.onCreate(paramBundle);
		setContentView(R.layout.common_list_view);
		KingTellerApplication.addActivity(this);
		
		mContext = OnlineWorkListActivity.this;
		initUI();
		initData();
	}
	


	private void initUI() {
		title_title.setText("文档列表");
		title_left.setOnClickListener(this);
		libaryId = getIntent().getStringExtra("libaryId");
		
		adapter = new WorkListAdadpater(this, workList);

		getListviewObj().getListview().setAdapter(adapter);
		getListviewObj().getListview().setRefreshEnabled(true);
		getListviewObj().getListview().setLoadMoreEnabled(false);
		getListviewObj().getListview().setOnRefreshListener(this);
		getListviewObj().getListview().setOnItemClickListener(this);

		getListviewObj().setOnClickLister(LoadingEnum.NODATA, res);
		getListviewObj().setOnClickLister(LoadingEnum.NETERROR, res);
	}
	
	private void initData() {
		getWorkList();
	}
	
	private void getWorkList() {
		KTHttpClient fh = new KTHttpClient(true);
		AjaxParams params = new AjaxParams();
		params.put("gernerId", libaryId);

		fh.post(KingTellerConfigUtils.CreateUrl(this, KingTellerUrlConfig.SplbURL), params,
				new AjaxHttpCallBack<BasePageBean<WorkListBean>>(this,
						new TypeToken<BasePageBean<WorkListBean>>() {
						}.getType(), true) {
			@Override
			public void onFinish() {
				getListviewObj().getListview().stopRefresh();
				getListviewObj().getListview().stopLoadMore();

			}
			
			@Override
			public void onDo(BasePageBean<WorkListBean> data) {
				if ("".equals(data.getStatus().trim())) {
					if (data.getList().size() > 0) {
						workList = data.getList();
						adapter.setLists(data.getList());
						getListviewObj().setStatus(LoadingEnum.LISTSHOW);
					} else {
						getListviewObj().setStatus(LoadingEnum.NODATA,getString(R.string.no_data_try));
					}
				} else {
					T.showShort(mContext, data.getMsg());
				}

			};
			
		});
	}



	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		VideoListBean wlb = (VideoListBean) parent.getAdapter().getItem(
				position);
	}

	@Override
	public void onRefresh() {
		// TODO Auto-generated method stub
		getWorkList();
		getListviewObj().getListview().setLoadMoreEnabled(true);
	}

	@Override
	public void onLoadMore() {
		// TODO Auto-generated method stub
		getWorkList();
	}
	
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
		case R.id.button_left:
			finish();
			break;

		default:
			break;
		}
	}
	private OnClickListener res = new OnClickListener() {

		@Override
		public void onClick(View view) {
			// TODO Auto-generated method stub
			getListviewObj().setStatus(LoadingEnum.LOADING);
			onRefresh();
		}
	};
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		KingTellerApplication.removeActivity(this);
	}
}
