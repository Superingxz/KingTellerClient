package com.kingteller.client.activity.base;

import cn.jpush.android.api.JPushInterface;

import com.kingteller.R;
import com.kingteller.client.utils.KingTellerJudgeUtils;
import com.kingteller.client.view.ListViewObj;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

/**
 * 通用基础Activity
 * @author 王定波
 *
 */
public class BaseActivity extends Activity {
	//private static final String TAG = BaseActivity.class.getSimpleName();
	protected Button title_left;
	protected Button title_right;
	protected Button title_title;
	private ListViewObj listviewObj;
	protected boolean isshow_no_net=true;

	@Override
	public void onCreate(Bundle paramBundle) {
		super.onCreate(paramBundle);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}

	@Override
	public boolean onKeyDown(int paramInt, KeyEvent paramKeyEvent) {
		return super.onKeyDown(paramInt, paramKeyEvent);
	}

	@Override
	public void onPause() {
		super.onPause();
		 JPushInterface.onPause(this);

	}

	@Override
	public void onResume() {
		super.onResume();
		JPushInterface.onResume(this);
		//检查网络是否可用
		if(findViewById(R.id.net_error)!=null && isshow_no_net)
		{
			if(!KingTellerJudgeUtils.isNetAvaliable(this))
				findViewById(R.id.net_error).setVisibility(View.VISIBLE);
			else findViewById(R.id.net_error).setVisibility(View.GONE);
		}

	}

	@Override
	protected void onSaveInstanceState(Bundle paramBundle) {
		super.onSaveInstanceState(paramBundle);
	}

	@Override
	public void onStart() {
		super.onStart();
	}

	@Override
	public void onStop() {
		super.onStop();
	}

	@Override
	public void setContentView(int layoutResID) {
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		super.setContentView(layoutResID);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,
				R.layout.common_title_bar);
		title_left = (Button) findViewById(R.id.button_left);
		title_right = (Button) findViewById(R.id.button_right);
		title_title = (Button) findViewById(R.id.button_middle);
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
				R.layout.common_title_bar);
		title_left = (Button) findViewById(R.id.button_left);
		title_right = (Button) findViewById(R.id.button_right);
		title_title = (Button) findViewById(R.id.button_middle);
		title_left.setBackgroundResource(R.drawable.btn_back_arrow);

		if (findViewById(R.id.loading_view) != null) {
			setListviewObj(new ListViewObj(this));
		}

	}

	public void setContentWithOutTitle(int res) {
		super.setContentView(res);
	}

	protected void startActivity(String action, Bundle bundle) {
		Intent intent = new Intent();
		intent.setAction(action);
		if (bundle != null)
			intent.putExtras(bundle);
		startActivity(intent);
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

	public void toastMsg(int resStr) {
		toastMsg(getString(resStr));
	}

	public void toastMsg(String str) {
		Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
	}

	public ListViewObj getListviewObj() {
		return listviewObj;
	}

	public void setListviewObj(ListViewObj listviewObj) {
		this.listviewObj = listviewObj;
	}

}