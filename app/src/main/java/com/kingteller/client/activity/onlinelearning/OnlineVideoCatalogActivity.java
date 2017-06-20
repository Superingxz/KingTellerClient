package com.kingteller.client.activity.onlinelearning;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.kingteller.R;
import com.kingteller.client.KingTellerApplication;
import com.kingteller.client.adapter.SimpleTreeAdapter;
import com.kingteller.client.adapter.TreeListViewAdapter.OnTreeNodeClickListener;
import com.kingteller.client.bean.common.BasePageBean;
import com.kingteller.client.bean.onlinelearning.FileBean;
import com.kingteller.client.bean.onlinelearning.Node;
import com.kingteller.client.bean.onlinelearning.VideoGenreBean;
import com.kingteller.client.config.KingTellerUrlConfig;
import com.kingteller.client.netauth.AjaxHttpCallBack;
import com.kingteller.client.netauth.KTHttpClient;
import com.kingteller.client.utils.KingTellerConfigUtils;
import com.kingteller.client.view.dialog.use.KingTellerProgressUtils;
import com.kingteller.client.view.toast.T;
import com.kingteller.framework.http.AjaxParams;

public class OnlineVideoCatalogActivity extends Activity implements
		OnClickListener {

	@SuppressWarnings("rawtypes")
	private SimpleTreeAdapter adapter;
	private ListView listView;
	private TextView title_text;
	private Button title_left_btn;
	
	private Context mContext;
	
	@Override
	public void onCreate(Bundle paramBundle) {
		super.onCreate(paramBundle);
		setContentView(R.layout.layout_online_video_catalog);
		KingTellerApplication.addActivity(this);
		
		mContext = OnlineVideoCatalogActivity.this;
		initUI();
		initData();
	}

	private void initUI() {
		title_text = (TextView) findViewById(R.id.layout_main_text);
		title_left_btn = (Button) findViewById(R.id.layout_main_left_btn);
		
		title_left_btn.setBackgroundResource(R.drawable.btn_back_arrow);
		title_left_btn.setOnClickListener(this);
		
		title_text.setText("视频目录");

		listView = (ListView) findViewById(R.id.list_view);

	}

	private void initData() {
		KTHttpClient fh = new KTHttpClient(true);
		AjaxParams params = new AjaxParams();

		fh.post(KingTellerConfigUtils.CreateUrl(this, KingTellerUrlConfig.SpcxflURL), params,
				new AjaxHttpCallBack<BasePageBean<VideoGenreBean>>(this,
						new TypeToken<BasePageBean<VideoGenreBean>>() {
						}.getType(), false) {

					@Override
					public void onStart() {
						KingTellerProgressUtils.showProgress(mContext, "加载数据中...");
					}
			
					@Override
					public void onFinish() {
						super.onFinish();
						KingTellerProgressUtils.closeProgress();
					}

					@Override
					public void onDo(BasePageBean<VideoGenreBean> data) {
						if ("".equals(data.getStatus().trim())) {
							if (data.getList().size() > 0) {
								setData(data.getList());
							} else {
								T.showShort(mContext, "获取数据失败!");
							}
						} else {
							T.showShort(mContext, data.getMsg());
						}
					}

				});
	}

	private void setData(List<VideoGenreBean> mDatas) {
		List<FileBean> fbList = new ArrayList<FileBean>();
		FileBean fb;
		for (int i = 0; i < mDatas.size(); i++) {
			fb = new FileBean(mDatas.get(i).getGenreId().trim(), 
								mDatas.get(i).getGenrePid().trim(),
								mDatas.get(i).getGenreName().trim());
			fbList.add(fb);
		}

		try {
			adapter = new SimpleTreeAdapter<FileBean>(listView, this, fbList, 10);

			adapter.setOnTreeNodeClickListener(new OnTreeNodeClickListener() {
				@Override
				public void onClick(Node node, int position) {
					if (node.isLeaf()) {
						Intent intent = new Intent();
						intent.putExtra("gernerId", node.getId());
						intent.setClass(OnlineVideoCatalogActivity.this, OnlineVideoListActivity.class);
						startActivity(intent);
					}
				}

			});
		} catch (Exception e) {
			e.printStackTrace();
		}

		listView.setAdapter(adapter);
	}

	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.layout_main_left_btn:
			finish();
			break;
		default:
			break;
		}
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		KingTellerApplication.removeActivity(this);
	}
}
