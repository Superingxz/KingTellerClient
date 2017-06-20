package com.kingteller.client.activity.logisticmonitor;

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
 * 填写其他事物报告
 * @author Administrator
 *
 */
public class WriteOtherTaskListActivity extends FragmentActivity implements OnClickListener{

    private OtherTaskFragment otFragment;
    private String optType="swlb_fh";
	
	private TextView title_text;
	private Button title_left_btn;
	
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
	}
	
	private void initData() {
		title_text.setText("填写其他事物报告");
		title_left_btn.setBackgroundResource(R.drawable.btn_back_arrow);
		title_left_btn.setOnClickListener(this);
		otFragment = new OtherTaskFragment(optType);
		getSupportFragmentManager().beginTransaction().add(R.id.relativeLayout, otFragment).commit();           
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		otFragment.getOtherTaskOrder();
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.layout_main_left_btn:
			finish();
			break;
		default:
			break;
		}
	}

	
}
