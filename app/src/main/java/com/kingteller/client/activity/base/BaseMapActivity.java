package com.kingteller.client.activity.base;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.MapView;
import com.kingteller.R;

/**
 * 通用地图基础activity
 * @author 王定波
 *
 */
public class BaseMapActivity extends Activity {
	protected MapView mapView;
	protected AMap aMap;
	private Bundle savedInstanceState;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.savedInstanceState = savedInstanceState;

	}

	@Override
	public void setContentView(int layoutResID) {
		// TODO Auto-generated method stub
		super.setContentView(layoutResID);
		mapView = (MapView) findViewById(R.id.map);
		mapView.onCreate(savedInstanceState);// 此方法必须重写
		aMap = mapView.getMap();
	}

	@Override
	public void setContentView(View view) {
		// TODO Auto-generated method stub
		super.setContentView(view);
		mapView = (MapView) findViewById(R.id.map);
		mapView.onCreate(savedInstanceState);// 此方法必须重写
		aMap = mapView.getMap();
	}

	/**
	 * 方法必须重写
	 */
	@Override
	public void onResume() {
		super.onResume();
		mapView.onResume();
	}

	/**
	 * 方法必须重写
	 */
	@Override
	public void onPause() {
		super.onPause();
		mapView.onPause();
	}

	/**
	 * 方法必须重写
	 */
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		mapView.onSaveInstanceState(outState);
	}

	/**
	 * 方法必须重写
	 */
	@Override
	public void onDestroy() {
		super.onDestroy();
		mapView.onDestroy();
	}
}
