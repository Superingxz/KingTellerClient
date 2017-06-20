package com.kingteller.client.activity.workorder;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;
import cn.jpush.android.api.JPushInterface;

import com.kingteller.R;
import com.kingteller.client.utils.KingTellerJudgeUtils;
import com.kingteller.client.view.ListViewObj;

public class BaseSendOrderFragmentActivity extends FragmentActivity {

	protected Button title_left;
	protected Button title_right;
	protected Button title_title;
	protected Button title_search;

	private ListViewObj listviewObj;

	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);

		initUI();

	}

	private void initUI() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void onPause() {
		super.onPause();
		JPushInterface.onPause(this);

	}

	@Override
	public void setContentView(int layoutResID) {
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		super.setContentView(layoutResID);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,
				R.layout.common_wdpd_title_bar);
		title_left = (Button) findViewById(R.id.button_left);
		title_right = (Button) findViewById(R.id.button_right);
		title_title = (Button) findViewById(R.id.button_middle);
		title_search = (Button) findViewById(R.id.button_search);
		title_left.setBackgroundResource(R.drawable.btn_back_arrow);
		if (findViewById(R.id.loading_view) != null) {
			setListviewObj(new ListViewObj(this));
		}

	}

	@Override
	public void setContentView(View view) {

		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		super.setContentView(view);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,
				R.layout.common_wdpd_title_bar);
		title_left = (Button) findViewById(R.id.button_left);
		title_right = (Button) findViewById(R.id.button_right);
		title_title = (Button) findViewById(R.id.button_middle);
		title_search = (Button) findViewById(R.id.button_search);
		title_left.setBackgroundResource(R.drawable.btn_back_arrow);
		if (findViewById(R.id.loading_view) != null) {
			setListviewObj(new ListViewObj(this));
		}

	}

	public void setContentWithOutTitle(int res) {
		super.setContentView(res);
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		JPushInterface.onResume(this);
		// 检查网络是否可用
		if (findViewById(R.id.net_error) != null) {
			if (!KingTellerJudgeUtils.isNetAvaliable(this))
				findViewById(R.id.net_error).setVisibility(View.VISIBLE);
			else
				findViewById(R.id.net_error).setVisibility(View.GONE);
		}
	}

//	@Override
//	public void startActivity(Intent intent) {
//		// TODO Auto-generated method stub
//		super.startActivity(intent);
//		overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
//	}

	@Override
	public void finish() {
		// TODO Auto-generated method stub
		super.finish();
		overridePendingTransition(0, R.anim.push_right_out);
	}
	
	public ListViewObj getListviewObj() {
		return listviewObj;
	}

	public void setListviewObj(ListViewObj listviewObj) {
		this.listviewObj = listviewObj;
	}


}
