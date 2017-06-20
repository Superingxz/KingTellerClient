package com.kingteller.client.activity.onlinelearning;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

import com.kingteller.R;
import com.kingteller.client.KingTellerApplication;
import com.kingteller.client.activity.logisticmonitor.OtherTaskListActivity;
import com.kingteller.client.adapter.FunctionAdapter;
import com.kingteller.client.bean.account.User;
import com.kingteller.client.view.toast.T;

public class OnlineMainActivity extends Activity implements OnItemClickListener {
	private ListView listview;
	private FunctionAdapter adapter;
	private TextView title_text;
	private Button title_left_btn;
	private Context mContext;
	@Override
	public void onCreate(Bundle paramBundle) {
		super.onCreate(paramBundle);
		setContentView(R.layout.layout_online_main);
		KingTellerApplication.addActivity(this);
		
		mContext = OnlineMainActivity.this;
		initUI();
		initData();
	}

	private void initUI() {
		title_text = (TextView) findViewById(R.id.layout_main_text);
		title_left_btn = (Button) findViewById(R.id.layout_main_left_btn);
		
		listview = (ListView) findViewById(R.id.function_listview);
		
		adapter = new FunctionAdapter(this, User.getInfo(this).getRight(), FunctionAdapter.ONLINEMENU);
		
		listview.setAdapter(adapter);
		listview.setOnItemClickListener(this);
	}
	
	private void initData() {
		title_text.setText("在线学习");
		title_left_btn.setBackgroundResource(R.drawable.btn_back_arrow);
		title_left_btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
	}

	@Override
	public void onItemClick(AdapterView<?> adapterView, View arg1, int postion,
			long arg3) {
		// TODO Auto-generated method stub
		String action = (String) adapterView.getAdapter().getItem(postion);
		if (action.equals("MOBILE_ONLINE_VIDEO")) {//在线视频
			startActivity(new Intent(this, OnlineVideoCatalogActivity.class));
		} else if (action.equals("MOBILE_ONLINE_FILE")) {//在线文档
			T.showShort(mContext, "该功能正在建设中，敬请关注!");
			//startActivity(new Intent(this, OnlineWorkCatalogActivity.class));
		} else if (action.equals("MOBILE_ONLINE_LEARNING_RECORDS")) {//学习记录
			T.showShort(mContext, "该功能正在建设中，敬请关注!");
			//startActivity(new Intent(this, OtherTaskListActivity.class));
		} else {
			T.showShort(mContext, "该功能正在建设中，敬请关注!");
		}

	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		KingTellerApplication.removeActivity(this);
	}
}
