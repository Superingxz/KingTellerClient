package com.kingteller.client.activity.map;

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
import com.kingteller.client.adapter.FunctionAdapter;
import com.kingteller.client.bean.account.User;
import com.kingteller.client.view.toast.T;

/**
 * 地图功能导航菜单
 * @author 王定波
 *
 */
public class MapMainActivity extends Activity implements OnItemClickListener {
	private ListView listview;
	private FunctionAdapter adapter;
	private TextView title_text;
	private Button title_left_btn;
	private Context mContext;
	@Override
	public void onCreate(Bundle paramBundle) {
		super.onCreate(paramBundle);
		
		setContentView(R.layout.layout_map_main);
		KingTellerApplication.addActivity(this);
		
		mContext = MapMainActivity.this;
		initUI();
		initData();
	}

	private void initUI() {
		title_text = (TextView) findViewById(R.id.layout_main_text);
		title_left_btn = (Button) findViewById(R.id.layout_main_left_btn);

		listview = (ListView) findViewById(R.id.function_listview);
		adapter = new FunctionAdapter(this, User.getInfo(this).getRight(),
				FunctionAdapter.MAPMENU);
		listview.setAdapter(adapter);
 
		listview.setOnItemClickListener(this);
	}
	
	private void initData() {
		title_text.setText("地图功能");
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
		String action = (String) adapterView.getAdapter().getItem(postion);
		
		if (action.equals("MOBILE_MAP_USER")) {						//员工定位
			startActivity(new Intent(this, StaffLocationActivity.class));
		} else if (action.equals("MOBILE_MAP_FWZ")) {				//服务站查询
			startActivity(new Intent(this, ServiceStationQueryActivity.class));
		} else if (action.equals("MOBILE_MAP_LOCATION")) {			//机器定位
			startActivity(new Intent(this, ATMUploadActivity.class));
		} else if (action.equals("MOBILE_MAP_MACHINE")) {			//机器查询
			startActivity(new Intent(this, ATMQueryActivity.class));
		} else if (action.equals("MOBILE_MAP_DF")) {				//手机导航
			startActivity(new Intent(this, MobileNavActivity.class));
		} else if (action.equals("MOBILE_MAP_SERVICE")) {			//服务站定位
			startActivity(new Intent(this, ServiceStationLocationActivity.class));
			//startActivity(new Intent(this, SelectImageAllActivity.class));
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
