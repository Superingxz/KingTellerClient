package com.kingteller.client.activity.workorder;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.MarkerOptions;
import com.amap.api.maps2d.model.MyLocationStyle;
import com.kingteller.R;
import com.kingteller.client.KingTellerApplication;
import com.kingteller.client.activity.base.BaseMapActivity;
import com.kingteller.client.utils.KingTellerJudgeUtils;

public class MobileNavJqbhActivity extends BaseMapActivity implements
		OnClickListener {

	private MyLocationStyle myLocationStyle;
	private String lat="";
	private String lng="";
	private String titleJqxx ="";
	
	private TextView title_text;
	private Button title_left_btn;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_setline);
		KingTellerApplication.addActivity(this);
		
		initUI();
	}

	/**
	 * 初始化AMap对象
	 */
	private void initUI() {
		title_text = (TextView) findViewById(R.id.layout_main_text);
		title_left_btn = (Button) findViewById(R.id.layout_main_left_btn);
		
		title_left_btn.setBackgroundResource(R.drawable.btn_back_arrow);
		title_left_btn.setOnClickListener(this);
		
		titleJqxx = getIntent().getStringExtra("title");
		if(!KingTellerJudgeUtils.isEmpty(titleJqxx)){
			title_text.setText("机器位置");
		}else{	
			title_text.setText("手机导航");
		}
		lat = getIntent().getStringExtra("lat");
		lng = getIntent().getStringExtra("lng");
		
		//Log.e("1111111111111111","lat:"+lat+"lng:"+lng);

		// 自定义系统定位小蓝点
		myLocationStyle = new MyLocationStyle();
		myLocationStyle.myLocationIcon(BitmapDescriptorFactory.fromResource(R.drawable.ic_mobilelocation_marker));// 设置小蓝点的图标
		myLocationStyle.strokeWidth(1.0f);// 设置圆形的边框粗细
		aMap.setMyLocationStyle(myLocationStyle);
		// aMap.setLocationSource(this);// 设置定位监听
		aMap.getUiSettings().setMyLocationButtonEnabled(true);// 设置默认定位按钮是否显示
		aMap.setMyLocationEnabled(false);// 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false

		if(!KingTellerJudgeUtils.isEmpty(lat)  && !KingTellerJudgeUtils.isEmpty(lng)){
			aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
					new LatLng(Float.parseFloat(lat), Float.parseFloat(lng)), 15));

			aMap.addMarker(new MarkerOptions()
					.anchor(0.5f, 0.5f)
					.position(new LatLng(Float.parseFloat(lat), Float.parseFloat(lng)))
					.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_mobilelocation_marker))
					.draggable(false));
		}
	
	}

	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub
		switch (view.getId()) {
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
