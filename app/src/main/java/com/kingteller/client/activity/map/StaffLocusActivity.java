package com.kingteller.client.activity.map;

import java.util.List;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.amap.api.maps2d.AMap.InfoWindowAdapter;
import com.amap.api.maps2d.AMap.OnInfoWindowClickListener;
import com.amap.api.maps2d.AMap.OnMarkerClickListener;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.Marker;
import com.amap.api.maps2d.model.MarkerOptions;
import com.amap.api.maps2d.model.Polyline;
import com.amap.api.maps2d.model.PolylineOptions;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeQuery;
import com.amap.api.services.geocoder.GeocodeSearch.OnGeocodeSearchListener;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.kingteller.R;
import com.kingteller.client.KingTellerApplication;
import com.kingteller.client.activity.base.BaseMapActivity;
import com.kingteller.client.bean.map.StaffLocationBean;
import com.kingteller.client.bean.map.StaffPointBean;
import com.kingteller.client.utils.KingTellerJudgeUtils;

/**
 * 路线轨迹Activity
 * @author 王定波
 *
 */
public class StaffLocusActivity extends BaseMapActivity implements
		OnMarkerClickListener, OnGeocodeSearchListener,
		OnInfoWindowClickListener, InfoWindowAdapter {
	
	private StaffLocationBean locus;
	private Polyline polyline;
	private GeocodeSearch geocoderSearch;
	private Marker nowMaker;
	private TextView title_text;
	private Button title_left_btn;
	@Override
	public void onCreate(Bundle paramBundle) {
		super.onCreate(paramBundle);
		setContentView(R.layout.layout_stafflocus);
		KingTellerApplication.addActivity(this);
		
		initUI();
	}

	private void initUI() {
		title_text = (TextView) findViewById(R.id.layout_main_text);
		title_left_btn = (Button) findViewById(R.id.layout_main_left_btn);
		title_left_btn.setBackgroundResource(R.drawable.btn_back_arrow);
		
		locus = (StaffLocationBean) getIntent().getSerializableExtra("data");
		title_text.setText(locus.getUserName());
		title_left_btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();

			}
		});

		aMap.setMyLocationEnabled(true);
		aMap.setOnMarkerClickListener(this);// 设置点击marker事件监听器

		aMap.setOnInfoWindowClickListener(this);// 设置点击infoWindow事件监听器
		aMap.setInfoWindowAdapter(this);// 设置自定义InfoWindow样式

		geocoderSearch = new GeocodeSearch(this);
		geocoderSearch.setOnGeocodeSearchListener(this);

		List<StaffPointBean> points = locus.getuMaplist();
		PolylineOptions po = new PolylineOptions();
		int length = points.size();
		for (int i = 0; i < points.size(); i++) {
			LatLng latlng = new LatLng(points.get(i).getLatItude(), points
					.get(i).getLongItude());
			po.add(latlng);

			int res = 0;
			String title = "";
			String desc = locus.getUserName() + "："
					+ points.get(i).getMonitorDate();
			if (i == 0) {
				res = R.drawable.tu_start;
				title = "开始位置";
			} else if (i == length - 1) {
				res = R.drawable.tu_end;
				title = "当前位置";
			} else {
				res = R.drawable.tu_ing;
				title = "中间位置";
			}

			if (!KingTellerJudgeUtils.isEmpty(points.get(i).getOrderNo()) && !points.get(i).getOrderNo().equals("orderNo")) {
				desc += "正在维护机器" + ":" + points.get(i).getJqbh() + "\n维护工单号:"
						+ points.get(i).getOrderNo();
			}
			desc += "\n地址：正在获取中...";

			aMap.addMarker(new MarkerOptions().anchor(0.5f, 0.5f)
					.position(latlng).title(title)
					.icon(BitmapDescriptorFactory.fromResource(res))
					.snippet(desc).draggable(true));
		}

		aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(points
				.get(0).getLatItude(), points.get(0).getLongItude()), 15));
		
		polyline = aMap.addPolyline(po.width(5).setDottedLine(false)
				.geodesic(false).color(Color.parseColor("#6655AA")));

	}

	@Override
	public void onGeocodeSearched(GeocodeResult arg0, int arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onRegeocodeSearched(RegeocodeResult arg0, int arg1) {
		if (arg1 == 0) {
			if (arg0 != null && arg0.getRegeocodeAddress() != null
					&& arg0.getRegeocodeAddress().getFormatAddress() != null) {
				String str = arg0.getRegeocodeAddress().getFormatAddress()
						+ "附近";
				nowMaker.setSnippet(nowMaker.getSnippet().replace("正在获取中...",
						str));
				nowMaker.showInfoWindow();

			} else {
				// ToastUtil.show(GeocoderActivity.this, R.string.no_result);
			}
		} else if (arg1 == 27) {
			// ToastUtil.show(GeocoderActivity.this, R.string.error_network);
		} else if (arg1 == 32) {
			// ToastUtil.show(GeocoderActivity.this, R.string.error_key);
		} else {
			// ToastUtil.show(GeocoderActivity.this, R.string.error_other);
		}
	}

	@Override
	public boolean onMarkerClick(Marker maker) {
		// TODO Auto-generated method stub
		nowMaker = maker;
		if (maker.getSnippet().endsWith("正在获取中..."))
			getAddress(maker.getPosition());
		return false;
	}

	public void getAddress(LatLng latLng) {
		RegeocodeQuery query = new RegeocodeQuery(new LatLonPoint(
				latLng.latitude, latLng.longitude), 200, GeocodeSearch.AMAP);// 第一个参数表示一个Latlng，第二参数表示范围多少米，第三个参数表示是火系坐标系还是GPS原生坐标系
		geocoderSearch.getFromLocationAsyn(query);// 设置同步逆地理编码请求

	}

	@Override
	public View getInfoContents(Marker arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public View getInfoWindow(Marker arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onInfoWindowClick(Marker arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		KingTellerApplication.removeActivity(this);
	}
}
