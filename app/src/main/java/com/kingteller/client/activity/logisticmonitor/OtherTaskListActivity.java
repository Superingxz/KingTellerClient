package com.kingteller.client.activity.logisticmonitor;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.kingteller.R;
import com.kingteller.client.activity.base.BaseFragmentActivity;
import com.kingteller.client.activity.logisticmonitor.fragment.OtherTaskFragment;
import com.kingteller.client.view.ListViewObj;

/**
 * 其他事物管理
 */
public class OtherTaskListActivity extends FragmentActivity implements OnClickListener {
	
	private OtherTaskFragment otFragment;
	private String opType="all";
	private TextView title_text;
	private Button title_left_btn, title_righttwo_btn;
	@Override
	public void onCreate(Bundle paramBundle) {
		// TODO Auto-generated method stub
		super.onCreate(paramBundle);
		setContentView(R.layout.layout_othertask_order); 
		initUI();
		initData();
	}
	
	private void initUI(){
		title_text = (TextView) findViewById(R.id.layout_main_text);
		title_left_btn = (Button) findViewById(R.id.layout_main_left_btn);
		title_righttwo_btn = (Button) findViewById(R.id.layout_main_righttwo_btn);
		
	}
	
	private void initData() {
		title_text.setText("其他事物管理");
		title_left_btn.setBackgroundResource(R.drawable.btn_back_arrow);
		title_left_btn.setOnClickListener(this);
		title_righttwo_btn.setBackgroundResource(R.drawable.btn_serach);
		title_righttwo_btn.setOnClickListener(this);
		
		otFragment = new OtherTaskFragment(opType);
		getSupportFragmentManager().beginTransaction().add(R.id.relativeLayout, otFragment).commit();           
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		otFragment.getOtherTaskOrder();
	}
	
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
		case R.id.layout_main_left_btn:
			finish();
			break;
		case R.id.layout_main_righttwo_btn:
			startActivity(new Intent(this, OtherTaskSearchActivity.class));
			break;
		default:
			break;
		}
	}


}
