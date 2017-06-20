package com.kingteller.client.activity.common;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.amap.api.maps2d.AMap.OnCameraChangeListener;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.model.CameraPosition;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.GeocodeSearch.OnGeocodeSearchListener;
import com.amap.api.services.geocoder.RegeocodeAddress;
import com.amap.api.services.geocoder.RegeocodeQuery;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.kingteller.R;
import com.kingteller.client.KingTellerApplication;
import com.kingteller.client.activity.base.BaseMapActivity;
import com.kingteller.client.bean.map.AddressBean;
import com.kingteller.client.view.dialog.NormalDialog;
import com.kingteller.client.view.dialog.listener.OnBtnClickL;
import com.kingteller.client.view.dialog.use.KingTellerProgressUtils;
import com.kingteller.client.view.dialog.use.KingTellerPromptDialogUtils;
import com.kingteller.client.view.toast.T;

/**
 * 地图地址搜索选择器
 * @author 王定波
 *
 */
public class SelectMapAddressActivity extends BaseMapActivity implements OnClickListener {
	
	private GeocodeSearch geocoderSearch;
	private TextView tv_start_position;
	private ProgressBar progbar_searching;
	private ImageView st;
	private ImageButton imageButton;

	private TextView title_text;
	private Button title_left_btn, title_righttwo_btn;
	private AddressBean address = new AddressBean();
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_select_map_address);
		KingTellerApplication.addActivity(this);
		
		address = (AddressBean) getIntent().getSerializableExtra("address");
		
		initUI();
		initData();
	}

	private void initData() {
		
		// 移动到当前位置
		aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(address.getLat(), address.getLng()), 15));

		aMap.setOnCameraChangeListener(new OnCameraChangeListener() {
			@Override
			public void onCameraChangeFinish(CameraPosition cameraPosition) {
				getAddressDesc(cameraPosition.target.latitude, cameraPosition.target.longitude);
			}
			@Override
			public void onCameraChange(CameraPosition arg0) {
				
			}
		});

		geocoderSearch = new GeocodeSearch(this);
		geocoderSearch.setOnGeocodeSearchListener(new OnGeocodeSearchListener() {
					//响应逆地理编码
					@Override
					public void onRegeocodeSearched(RegeocodeResult result,
							int rCode) {
						KingTellerProgressUtils.closeProgress();
						
						if (rCode == 0) {
							if (result != null && result.getRegeocodeAddress() != null) {
								RegeocodeAddress raddress = result.getRegeocodeAddress();

								address.setName(raddress.getFormatAddress());
								address.setAddress(raddress.getFormatAddress());

								address.setLat(result.getRegeocodeQuery().getPoint().getLatitude());
								address.setLng(result.getRegeocodeQuery().getPoint().getLongitude());

								tv_start_position.setText(raddress.getFormatAddress());
								progbar_searching.setVisibility(View.GONE);

							} else {
								// R.string.error_network
							}

						} else if (rCode == 27) {
							// R.string.error_network
						} else if (rCode == 32) {

							// R.string.error_key
						} else {

							// R.string.error_other
						}

					}
					//地理编码查询回调
					@Override
					public void onGeocodeSearched(GeocodeResult result, int rCode) {

					}
				});

		getAddressDesc(address.getLat(), address.getLng());

	}

	private void initUI() {
		title_text = (TextView) findViewById(R.id.layout_main_text);
		title_left_btn = (Button) findViewById(R.id.layout_main_left_btn);
		title_righttwo_btn = (Button) findViewById(R.id.layout_main_righttwo_btn);
		
		title_text.setText("设置地址");
		title_left_btn.setBackgroundResource(R.drawable.btn_back_arrow);
		title_left_btn.setOnClickListener(this);
		title_righttwo_btn.setBackgroundResource(R.drawable.btn_check);
		title_righttwo_btn.setOnClickListener(this);
		

		tv_start_position = (TextView) findViewById(R.id.tv_start_position);
		progbar_searching = (ProgressBar) findViewById(R.id.progbar_searching);
		imageButton = (ImageButton) findViewById(R.id.imageButton);
		st = (ImageView) findViewById(R.id.st);
		
		imageButton.setOnClickListener(this);
		
	}
	
	private void getAddressDesc(double lat, double lng) {
		// 获取地址
		st.setAnimation(AnimationUtils.loadAnimation(SelectMapAddressActivity.this, R.anim.map_circle_scale));
		
		LatLonPoint llp = new LatLonPoint(lat, lng);
		RegeocodeQuery query = new RegeocodeQuery(llp, 200, GeocodeSearch.AMAP);// 第一个参数表示一个Latlng，第二参数表示范围多少米，第三个参数表示是火系坐标系还是GPS原生坐标系
		geocoderSearch.getFromLocationAsyn(query);// 设置同步逆地理编码请求

		tv_start_position.setText(R.string.label_GPS_positioning);
		progbar_searching.setVisibility(View.VISIBLE);
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.layout_main_left_btn:
			final NormalDialog dialog = new NormalDialog(this);
			KingTellerPromptDialogUtils.showTwoPromptDialog(dialog, "您没有选择指定的定位信息, 确定要退出吗？",
					new OnBtnClickL() {
						@Override
						public void onBtnClick() {
							dialog.dismiss();
						}
	                },new OnBtnClickL() {
	                    @Override
	                    public void onBtnClick() {
	                    	dialog.dismiss();
	                    	finish();
	                    }
	            });
			break;
		case R.id.layout_main_righttwo_btn:
			Intent intent = new Intent();
			intent.putExtra(SelectAddressActivity.ADDRESS, address);
			setResult(RESULT_OK, intent);
			finish();
			break;
		case R.id.imageButton:
			
			address = KingTellerApplication.getApplication().getCurAddress();
			
			if(address != null){
				// 移动到当前位置
				aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(address.getLat(), address.getLng()), 15));
				
				getAddressDesc(address.getLat(), address.getLng());
			}else{
				T.showShort(this, "无法获取当前位置!");
			}
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

