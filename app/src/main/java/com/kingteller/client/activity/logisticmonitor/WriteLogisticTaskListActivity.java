package com.kingteller.client.activity.logisticmonitor;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.kingteller.R;
import com.kingteller.client.activity.base.BaseFragmentActivity;
import com.kingteller.client.activity.logisticmonitor.fragment.LogisticOrderFragment;



public class WriteLogisticTaskListActivity extends FragmentActivity implements OnClickListener {

	private LogisticOrderFragment loFragment;
	private TextView title_text;
	private Button title_left_btn;
	
	@Override
	public void onCreate(Bundle paramBundle) {
		// TODO Auto-generated method stub
		super.onCreate(paramBundle);
		setContentView(R.layout.layout_logistic_order);
		initUI();
		initData();
	}

	private void initUI() {
		title_text = (TextView) findViewById(R.id.layout_main_text);
		title_left_btn = (Button) findViewById(R.id.layout_main_left_btn);
	}

	private void initData() {
		title_text.setText("查看任务报告");
		title_left_btn.setBackgroundResource(R.drawable.btn_back_arrow);
		title_left_btn.setOnClickListener(this);
		
		loFragment = new LogisticOrderFragment().getInstance("fill");
		getSupportFragmentManager().beginTransaction()
				.add(R.id.relativeLayout, loFragment).commit();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		loFragment.getLogisticOrders();
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

}
