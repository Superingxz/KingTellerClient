package com.kingteller.client.activity.pxkhsj;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.kingteller.R;
import com.kingteller.client.KingTellerApplication;
import com.kingteller.client.activity.base.BaseFragmentActivity;
import com.kingteller.client.activity.pxkhsj.fragment.PxkhSjListFragment;

public class DoPxkhsjListActivity extends FragmentActivity implements OnClickListener {

	PxkhSjListFragment pxkhfragment;
	private TextView title_text;
	private Button title_left_btn, title_right_btn;
	@Override
	protected void onCreate(Bundle paramBundle) {
		super.onCreate(paramBundle);
		setContentView(R.layout.layout_logistic_order);
		KingTellerApplication.addActivity(this);
		
		initUI();
		initData();
	}
	
	private void initUI(){
		title_text = (TextView) findViewById(R.id.layout_main_text);
		title_left_btn = (Button) findViewById(R.id.layout_main_left_btn);

	}
	
	private void initData(){
		title_text.setText("考核试卷列表");
		title_left_btn.setBackgroundResource(R.drawable.btn_back_arrow);
		title_left_btn.setOnClickListener(this);
		
		pxkhfragment = new PxkhSjListFragment();
		getSupportFragmentManager().beginTransaction().add(R.id.relativeLayout, pxkhfragment).commit(); 
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
	protected void onResume() {
		super.onResume();
		pxkhfragment.getAllSjPaperListDatas();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		KingTellerApplication.removeActivity(this);
	}
}
