package com.kingteller.client.activity.logisticmonitor;

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
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

import com.kingteller.R;
import com.kingteller.client.activity.base.BaseActivity;
import com.kingteller.client.activity.onlinelearning.OnlineMainActivity;
import com.kingteller.client.adapter.FunctionAdapter;
import com.kingteller.client.bean.account.User;
import com.kingteller.client.view.toast.T;

public class WLJKMainActivity extends Activity implements OnItemClickListener {
	private ListView listview;
	private FunctionAdapter adapter;
	private TextView title_text;
	private Button title_left_btn, title_right_btn;
	private Context mContext;
	@Override
	public void onCreate(Bundle paramBundle) {
		// TODO Auto-generated method stub
		super.onCreate(paramBundle);
		setContentView(R.layout.layout_map_main);
		mContext = WLJKMainActivity.this;
		initUI();
		initData();
	}

	private void initUI() {
		// TODO Auto-generated method stub
		title_text = (TextView) findViewById(R.id.layout_main_text);
		title_left_btn = (Button) findViewById(R.id.layout_main_left_btn);

		listview = (ListView) findViewById(R.id.function_listview);
		adapter = new FunctionAdapter(this, User.getInfo(this).getRight(), FunctionAdapter.WLJKMENU);
		listview.setAdapter(adapter);

		listview.setOnItemClickListener(this);
	}
	
	private void initData() {
		title_text.setText("物流监控");
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
		if (action.equals("WLJK_MOBILE_HDGL")) {						//货单管理
			startActivity(new Intent(this, LogisticOrderListActivity.class));
		} else if (action.equals("WLJK_MOBILE_TXQTSWBG")) {				//填写其他事物报告
            startActivity(new Intent(this, WriteOtherTaskListActivity.class));
		} else if (action.equals("WLJK_MOBILE_QTSWGL")) {				//其他事物管理
			startActivity(new Intent(this, OtherTaskListActivity.class));
		} else if (action.equals("WLJK_MOBILE_TXRWBG")) {				//查看任务报告
			startActivity(new Intent(this, WriteLogisticTaskListActivity.class));
		} else {
			T.showShort(mContext, "该功能正在建设中，敬请关注!");
		}

	}
	
}
