package com.kingteller.client.activity.map;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.location.LocationManagerProxy;
import com.amap.api.location.LocationProviderProxy;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeAddress;
import com.amap.api.services.geocoder.RegeocodeQuery;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch.OnGeocodeSearchListener;
import com.kingteller.R;
import com.kingteller.client.KingTellerApplication;
import com.kingteller.client.activity.common.SelectAddressActivity;
import com.kingteller.client.activity.common.SelectMapAddressActivity;
import com.kingteller.client.bean.account.User;
import com.kingteller.client.bean.map.AddressBean;
import com.kingteller.client.bean.map.ServiceLocationBean;
import com.kingteller.client.config.KingTellerStaticConfig;
import com.kingteller.client.config.KingTellerUrlConfig;
import com.kingteller.client.netauth.AjaxHttpCallBack;
import com.kingteller.client.netauth.KTHttpClient;
import com.kingteller.client.utils.KingTellerConfigUtils;
import com.kingteller.client.utils.KingTellerJudgeUtils;
import com.kingteller.client.view.dialog.NormalDialog;
import com.kingteller.client.view.dialog.listener.OnBtnClickL;
import com.kingteller.client.view.dialog.use.KingTellerProgressUtils;
import com.kingteller.client.view.dialog.use.KingTellerPromptDialogUtils;
import com.kingteller.client.view.toast.T;
import com.kingteller.framework.http.AjaxParams;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ServiceStationLocationActivity extends Activity implements OnClickListener {
	
	private Context mContext;
	private LinearLayout mNowAddress_layout;
	private TextView title_text, mFwzName_text, mLongitude_text, mLatitude_text, mAddress_text, mNowAddress_text;
	private Button title_left_btn, mUpdate_btn;
	
	private GeocodeSearch geocoderSearch;
	private LocationManagerProxy aMapLocAtmManager;
	private AddressBean address;
	private ServiceLocationBean serviceLocation;
	
	@Override
	public void onCreate(Bundle paramBundle) {
		super.onCreate(paramBundle);
		setContentView(R.layout.layout_servicestation_location);
		KingTellerApplication.addActivity(this);
		
		mContext = ServiceStationLocationActivity.this;
		
		initUI();
		initData();
	}
	
	private void initUI() {
		title_text = (TextView) findViewById(R.id.layout_main_text);
		title_left_btn = (Button) findViewById(R.id.layout_main_left_btn);
		
		title_text.setText("服务站定位");
		title_left_btn.setBackgroundResource(R.drawable.btn_back_arrow);
		title_left_btn.setOnClickListener(this);
		
		mFwzName_text = (TextView) findViewById(R.id.fwz_dw_ssname);
		mLongitude_text = (TextView) findViewById(R.id.fwz_dw_ssjd);
		mLatitude_text = (TextView) findViewById(R.id.fwz_dw_sswd);
		mAddress_text = (TextView) findViewById(R.id.fwz_dw_ssdz);
		
		mNowAddress_text = (TextView) findViewById(R.id.fwz_dw_dqdz);
		mNowAddress_layout = (LinearLayout) findViewById(R.id.layout_fwz_dw_dqdz);
		mNowAddress_layout.setOnClickListener(this);
		
		mUpdate_btn = (Button) findViewById(R.id.btn_fwz_dw);
		mUpdate_btn.setOnClickListener(this);
		
		aMapLocAtmManager = LocationManagerProxy.getInstance(this);
		aMapLocAtmManager.setGpsEnable(true);
		
		geocoderSearch = new GeocodeSearch(this);
		geocoderSearch.setOnGeocodeSearchListener(new OnGeocodeSearchListener() {
					//响应逆地理编码
					@Override
					public void onRegeocodeSearched(RegeocodeResult result, int rCode) {
						if (rCode == 0) {
							if (result != null && result.getRegeocodeAddress() != null) {
								RegeocodeAddress raddress = result.getRegeocodeAddress();
								if(KingTellerJudgeUtils.isEmpty(raddress.getFormatAddress())){
									mAddress_text.setText("");
								}else{
									mAddress_text.setText(raddress.getFormatAddress()); 
								}
								
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
	}
	
	private void initData() {
		mLongitude_text.setText("经度：查询中...");
		mLatitude_text.setText("纬度：查询中...");
		mAddress_text.setText("查询中..."); 
		
		mNowAddress_text.setText("正在定位中..."); 
		
		getServiceStationLocationData();
	}
	
	private void getServiceStationLocationData() {
		if (!KingTellerJudgeUtils.isNetAvaliable(this)) {
			T.showShort(mContext, "没有网络，请检查网络是否可用!");
			return;
		}

		AjaxParams params = new AjaxParams();
		params.put("userId", User.getInfo(mContext).getUserId());
		
		KTHttpClient fh = new KTHttpClient(true);
		fh.post(KingTellerConfigUtils.CreateUrl(mContext, KingTellerUrlConfig.getFwzLocationUrl), params,
				new AjaxHttpCallBack<ServiceLocationBean>(mContext, true) {
			
					@Override
					public void onStart() {
						KingTellerProgressUtils.showProgress(mContext, "正在获取中...");
					}
					
					@Override
					public void onError(int errorNo, String strMsg) {
						KingTellerProgressUtils.closeProgress();
						T.showShort(mContext, "数据访问错误!");
					}
					
					@Override
					public void onDo(ServiceLocationBean data) {
						KingTellerProgressUtils.closeProgress();
						
						if(data.getFlag() == null){
							serviceLocation = data;
							
							mFwzName_text.setText(data.getOrgName());
							
							if("已定位".equals(data.getLocationFlag())){
								
								String[] latlon = data.getOrgLocation().split(",");
								mLongitude_text.setText("经度：" + latlon[1]);
								mLatitude_text.setText("纬度：" + latlon[0]);
								
								if(data.getLocationName() != null){
									mAddress_text.setText(data.getLocationName()); 
								}else{
									getServiceStationLocation(Double.parseDouble(latlon[0]), Double.parseDouble(latlon[1]));
								}
							}else{
								mLongitude_text.setText("经度：无数据");
								mLatitude_text.setText("纬度：无数据");
								mAddress_text.setText("无数据");
							}
							
						}else{
							serviceLocation = null;
							
							mFwzName_text.setText("无数据");
							mLongitude_text.setText("经度：无数据");
							mLatitude_text.setText("纬度：无数据");
							mAddress_text.setText("无数据");
							T.showShort(mContext, "获取当前服务站位置失败!");
						}
						
						getNowLocation();
					}
				});
	}
	
	
	private void getServiceStationLocation(double lat, double lng) {
		LatLonPoint llp = new LatLonPoint(lat, lng);
		RegeocodeQuery query = new RegeocodeQuery(llp, 200, GeocodeSearch.AMAP);// 第一个参数表示一个Latlng，第二参数表示范围多少米，第三个参数表示是火系坐标系还是GPS原生坐标系
		geocoderSearch.getFromLocationAsyn(query);// 设置同步逆地理编码请求
	}
	
	/**
	 * 获取当前定位
	 */
	private void getNowLocation() {
		mNowAddress_text.setText("正在定位中...");
		
		KingTellerProgressUtils.showProgress(this, "正在定位中...");
		
		aMapLocAtmManager.removeUpdates(staffAtmListener);
		aMapLocAtmManager.requestLocationData(LocationProviderProxy.AMapNetwork, -1, 0, staffAtmListener);
	}

	private void setServiceStationLocationData() {
		if (!KingTellerJudgeUtils.isNetAvaliable(this)) {
			T.showShort(mContext, "没有网络，请检查网络是否可用!");
			return;
		}

		AjaxParams params = new AjaxParams();
		params.put("userId", User.getInfo(mContext).getUserId());
		params.put("orgId", serviceLocation.getOrgId());
		params.put("xCode", String.valueOf(address.getLat()));
		params.put("yCode", String.valueOf(address.getLng()));
		params.put("locationName", mNowAddress_text.getText().toString());
		
		KTHttpClient fh = new KTHttpClient(true);
		fh.post(KingTellerConfigUtils.CreateUrl(mContext, KingTellerUrlConfig.setFwzLocationUrl), params,
				new AjaxHttpCallBack<ServiceLocationBean>(mContext, true) {
			
					@Override
					public void onStart() {
						KingTellerProgressUtils.showProgress(mContext, "设置中...");
					}
					
					@Override
					public void onFinish() {
						KingTellerProgressUtils.closeProgress();
					}
					
					@Override
					public void onDo(ServiceLocationBean data) {
						if("上传成功".equals(data.getFlag())){
							T.showShort(mContext, "设置成功");
							finish();
						}else{
							T.showShort(mContext, "设置失败");
						}
					}
				});
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.layout_main_left_btn:
				finish();
				break;
			case R.id.btn_fwz_dw:
				if(serviceLocation == null){
					T.showShort(mContext, "请重新获取所在服务站位置!");
					return;
				}
				
				if(mNowAddress_text.getText().toString().contains("定位失败") || 
						mNowAddress_text.getText().toString().contains("正在定位")){
					T.showShort(mContext, "请重新定位!");
					return;
				}
				
				if(mAddress_text.getText().toString().equals(mNowAddress_text.getText().toString())){
					T.showShort(mContext, "当前位置已为服务站位置!");
					return;
				}
				
				final NormalDialog dialog = new NormalDialog(mContext);
				KingTellerPromptDialogUtils.showTwoPromptDialog(dialog, "你确定要以当前位置：" + mNowAddress_text.getText().toString() 
						+ "  为服务站位置吗？",
						new OnBtnClickL() {
							@Override
							public void onBtnClick() {
								dialog.dismiss();
							}
	                    },new OnBtnClickL() {
		                    @Override
		                    public void onBtnClick() {
		                    	dialog.dismiss();
		                    	setServiceStationLocationData();
		                    }
	                });
				
				break;
			case R.id.layout_fwz_dw_dqdz:
				if(mNowAddress_text.getText().toString().contains("定位失败") || 
						mNowAddress_text.getText().toString().contains("正在定位")){
					getNowLocation();
				}else{
					Intent intent = new Intent(this, SelectMapAddressActivity.class);
					intent.putExtra(SelectAddressActivity.ADDRESS, address);
					startActivityForResult(intent, KingTellerStaticConfig.REQUEST_GETMAPADDRESS);
				}

				break;
			default:
				break;
		}
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
		case KingTellerStaticConfig.REQUEST_GETMAPADDRESS:
			if (RESULT_OK == resultCode) {
				address = (AddressBean) data.getSerializableExtra(SelectAddressActivity.ADDRESS);
				mNowAddress_text.setText(address.getAddress());
			}
			break;
		default:
			break;
		}
	}

	private AMapLocationListener staffAtmListener = new AMapLocationListener() {
		/**
		 * 定位回调函数
		 */
		@Override
		public void onLocationChanged(AMapLocation aMapAtmLocation) {
			
			KingTellerProgressUtils.closeProgress();
			
			if (aMapAtmLocation != null && aMapAtmLocation.getAMapException().getErrorCode() == 0) {
				
				String desc = "";
				Bundle locBundle = aMapAtmLocation.getExtras();
				if (locBundle != null) {
					desc = locBundle.getString("desc");
				}
				
				address = new AddressBean();
				address.setLat(aMapAtmLocation.getLatitude());
				address.setLng(aMapAtmLocation.getLongitude());
				address.setName(desc);
				address.setAddress(desc);
				address.setCity(aMapAtmLocation.getCity());
				
				KingTellerApplication.getApplication().setCurAddress(address);

				mNowAddress_text.setText(address.getAddress());
				T.showShort(mContext, "获取定位信息成功!");
				
			} else {
				mNowAddress_text.setText("定位失败, 请重新定位!");
				
				String erro = aMapAtmLocation.getAMapException().getErrorMessage();
				T.showShort(mContext, "获取定位信息失败！" + erro);
			}

		}

		@Override
		public void onStatusChanged(String arg0, int arg1, Bundle arg2) {

		}

		@Override
		public void onProviderEnabled(String arg0) {

		}

		@Override
		public void onProviderDisabled(String arg0) {

		}

		@Override
		public void onLocationChanged(Location arg0) {
			
		}
	};
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		KingTellerApplication.removeActivity(this);
	}
}
