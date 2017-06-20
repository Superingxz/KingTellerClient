package com.kingteller.client.activity.onlinelearning;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.net.TrafficStats;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

import com.google.gson.reflect.TypeToken;
import com.kingteller.R;
import com.kingteller.client.KingTellerApplication;
import com.kingteller.client.adapter.VideoListAdadpter;
import com.kingteller.client.bean.account.User;
import com.kingteller.client.bean.common.BasePageBean;
import com.kingteller.client.bean.onlinelearning.VideoListBean;
import com.kingteller.client.config.KingTellerUrlConfig;
import com.kingteller.client.netauth.AjaxHttpCallBack;
import com.kingteller.client.netauth.KTHttpClient;
import com.kingteller.client.utils.KingTellerConfigUtils;
import com.kingteller.client.utils.KingTellerTimeUtils;
import com.kingteller.client.view.ListViewObj;
import com.kingteller.client.view.XListView.IXListViewListener;
import com.kingteller.client.view.datatype.LoadingEnum;
import com.kingteller.client.view.toast.T;
import com.kingteller.framework.http.AjaxParams;

public class OnlineVideoListActivity extends Activity implements
		OnClickListener, IXListViewListener, OnItemClickListener {

	private String gernerId;
	private List<VideoListBean> videoList = new ArrayList<VideoListBean>();
	private VideoListAdadpter adapter;
	private String startTime = "";
	private String endTime = "";
	private float startLL = 0;
	private float endLL = 0;
	private String vtId = "";
	
	private ListViewObj listviewObj;
	private TextView title_text;
	private Button title_left_btn;
	
	private Context mContext;
	@Override
	public void onCreate(Bundle paramBundle) {
		super.onCreate(paramBundle);
		setContentView(R.layout.common_alllist_view);
		KingTellerApplication.addActivity(this);
		
		if (findViewById(R.id.loading_view) != null) {
			setListviewObj(new ListViewObj(this));
		}
		mContext = OnlineVideoListActivity.this;
		initUI();
		initData();
	}

	private void initUI() {
		title_text = (TextView) findViewById(R.id.layout_main_text);
		title_left_btn = (Button) findViewById(R.id.layout_main_left_btn);
		
		title_left_btn.setBackgroundResource(R.drawable.btn_back_arrow);
		title_left_btn.setOnClickListener(this);
		
		title_text.setText("视频列表");

		gernerId = getIntent().getStringExtra("gernerId");

		adapter = new VideoListAdadpter(this, videoList);

		getListviewObj().getListview().setAdapter(adapter);
		getListviewObj().getListview().setRefreshEnabled(true);
		getListviewObj().getListview().setLoadMoreEnabled(false);
		getListviewObj().getListview().setOnRefreshListener(this);
		getListviewObj().getListview().setOnItemClickListener(this);

		getListviewObj().setOnClickLister(LoadingEnum.NODATA, res);
		getListviewObj().setOnClickLister(LoadingEnum.NETERROR, res);
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
		case R.id.layout_main_left_btn:
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

	private void initData() {
		getVideoList();
	}

	private void getVideoList() {
		KTHttpClient fh = new KTHttpClient(true);
		AjaxParams params = new AjaxParams();
		params.put("gernerId", gernerId);

		fh.post(KingTellerConfigUtils.CreateUrl(this, KingTellerUrlConfig.SplbURL), params,
				new AjaxHttpCallBack<BasePageBean<VideoListBean>>(this,
						new TypeToken<BasePageBean<VideoListBean>>() {
						}.getType(), true) {

					@Override
					public void onFinish() {
						getListviewObj().getListview().stopRefresh();
						getListviewObj().getListview().stopLoadMore();

					}

					@Override
					public void onDo(BasePageBean<VideoListBean> data) {
						if ("".equals(data.getStatus().trim())) {
							if (data.getList().size() > 0) {
								videoList = data.getList();
								adapter.setLists(data.getList());
								getListviewObj().setStatus(LoadingEnum.LISTSHOW);
							} else {
								getListviewObj().setStatus(LoadingEnum.NODATA, getString(R.string.no_data_try));
							}
						} else {
							T.showShort(mContext, data.getMsg());
						}

					};

				});

	}

	@Override
	public void onItemClick(AdapterView<?> adapterView, View v, int postion,
			long arg3) {
		// TODO Auto-generated method stub

		VideoListBean vlb = (VideoListBean) adapterView.getAdapter().getItem(postion);
		startTime = KingTellerTimeUtils.formatDate(new Date());
		KingTellerConfigUtils.openBrowser(this, vlb.getVtSwfLink().trim());

		vtId = vlb.getVtId().trim();
		startLL = TrafficStats.getTotalRxBytes() / 1024 / 1024;
	}

	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();
		endTime = KingTellerTimeUtils.formatDate(new Date());
		endLL = TrafficStats.getTotalRxBytes() / 1024 / 1024;
		Log.e("产生的数据流量", String.valueOf((endLL - startLL) * 1024 * 1024));

		getVideoParam(startTime, endTime, vtId,
				String.valueOf((endLL - startLL) * 1024 * 1024));
	}

	@Override
	public void onRefresh() {
		// TODO Auto-generated method stub
		getVideoList();
		getListviewObj().getListview().setLoadMoreEnabled(true);
	}

	@Override
	public void onLoadMore() {
		// TODO Auto-generated method stub
		getVideoList();
	}

	private void getVideoParam(String startTime, String endTime, String vtId,
			String ll) {
		KTHttpClient fh = new KTHttpClient(true);
		AjaxParams params = new AjaxParams();
		params.put("userId", User.getInfo(this).getUserId());
		params.put("startTime", startTime);
		params.put("endTime", endTime);
		params.put("vtId", vtId);
		params.put("ll", ll);

		fh.post(KingTellerConfigUtils.CreateUrl(this, KingTellerUrlConfig.SphjURL), params,
				new AjaxHttpCallBack<String>(this, true){});
		

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
