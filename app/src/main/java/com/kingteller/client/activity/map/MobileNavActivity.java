package com.kingteller.client.activity.map;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.location.LocationManagerProxy;
import com.amap.api.location.LocationProviderProxy;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.LocationSource;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.MarkerOptions;
import com.amap.api.maps2d.model.MyLocationStyle;
import com.amap.api.maps2d.overlay.BusRouteOverlay;
import com.amap.api.maps2d.overlay.DrivingRouteOverlay;
import com.amap.api.maps2d.overlay.WalkRouteOverlay;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.route.BusPath;
import com.amap.api.services.route.BusRouteResult;
import com.amap.api.services.route.DrivePath;
import com.amap.api.services.route.DriveRouteResult;
import com.amap.api.services.route.RouteSearch;
import com.amap.api.services.route.WalkPath;
import com.amap.api.services.route.WalkRouteResult;
import com.amap.api.services.route.RouteSearch.BusRouteQuery;
import com.amap.api.services.route.RouteSearch.DriveRouteQuery;
import com.amap.api.services.route.RouteSearch.OnRouteSearchListener;
import com.amap.api.services.route.RouteSearch.WalkRouteQuery;
import com.kingteller.R;
import com.kingteller.client.KingTellerApplication;
import com.kingteller.client.activity.base.BaseMapActivity;
import com.kingteller.client.activity.common.SelectATMCodeActivity;
import com.kingteller.client.activity.common.SelectAddressActivity;
import com.kingteller.client.activity.more.FeedBackActivity;
import com.kingteller.client.adapter.NavLineAdapter;
import com.kingteller.client.bean.map.AddressBean;
import com.kingteller.client.config.KingTellerStaticConfig;
import com.kingteller.client.view.ChangeColorButton;
import com.kingteller.client.view.ListViewObj;
import com.kingteller.client.view.datatype.LoadingEnum;
import com.kingteller.client.view.dialog.use.KingTellerProgressUtils;
import com.kingteller.client.view.dialog.utils.CornerUtils;
import com.kingteller.client.view.toast.T;

/**
 * 手机导航
 * @author 王定波
 */
public class MobileNavActivity extends BaseMapActivity implements
		LocationSource, OnClickListener, OnItemClickListener {
	private OnLocationChangedListener mListener;
	private LocationManagerProxy mAMapLocationManager;
	private MyLocationStyle myLocationStyle;

	private ChangeColorButton edittext_start;
	private ChangeColorButton edittext_end;
	private Button start_mac_code;
	private Button end_mac_code;
	private AddressBean start;
	private AddressBean end;
	private RouteSearch routeSearch;
	private int routeType = 2;

	private int busMode = RouteSearch.BusDefault;// 公交默认模式
	private int drivingMode = RouteSearch.DrivingDefault;// 驾车默认模式
	private int walkMode = RouteSearch.WalkDefault;// 步行默认模式

	private NavLineAdapter<BusPath> busAdapter;
	private NavLineAdapter<DrivePath> driverAdapter;
	private NavLineAdapter<WalkPath> walkAdapter;

	public final static int BUS = 1;
	public final static int DRIVE = 2;
	public final static int WALK = 3;
	public final static String NAVLINE = "navline";
	private BusRouteResult bresult;
	private DriveRouteResult dresult;
	private WalkRouteResult wresult;
	private Button busButton;
	private Button drivingButton;
	private Button walkButton;
	private LinearLayout layout_map;
	private LinearLayout layout_set_line;
	
	private ListViewObj listviewObj;
	
	private Context mContext;
	private TextView title_text;
	private Button title_left_btn, title_righttwo_btn;
	 
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_setline);
		KingTellerApplication.addActivity(this);
		
		if (findViewById(R.id.loading_view) != null) {
			setListviewObj(new ListViewObj(this));
		}
		mContext = MobileNavActivity.this;
		initUI();
		initData();
	}

	private void initData() {
		drivingRoute();
		busAdapter = new NavLineAdapter<BusPath>(this, null, BUS);
		driverAdapter = new NavLineAdapter<DrivePath>(this, null, DRIVE);
		walkAdapter = new NavLineAdapter<WalkPath>(this, null, WALK);

		routeSearch = new RouteSearch(this);
		routeSearch.setRouteSearchListener(new OnRouteSearchListener() {

			/**
			 * 公交路线查询回调
			 */
			@Override
			public void onBusRouteSearched(BusRouteResult result, int rCode) {
				KingTellerProgressUtils.closeProgress();
				if (rCode == 0) {
					if (result != null && result.getPaths() != null
							&& result.getPaths().size() > 0) {

						getListviewObj().getListview().setAdapter(busAdapter);
						busAdapter.setLists(result.getPaths());
						getListviewObj().setStatus(LoadingEnum.LISTSHOW);
						bresult = result;

					} else {
						getListviewObj().setStatus(LoadingEnum.NODATA);
					}
				} else if (rCode == 27) {
					// error_network
					getListviewObj().setStatus(LoadingEnum.NODATA);

				} else if (rCode == 32) {
					// error_key
					getListviewObj().setStatus(LoadingEnum.NODATA);
				} else {
					// error_other
					getListviewObj().setStatus(LoadingEnum.NODATA);

				}
			}

			/**
			 * 驾车结果回调
			 */
			@Override
			public void onDriveRouteSearched(DriveRouteResult result, int rCode) {
				KingTellerProgressUtils.closeProgress();
				if (rCode == 0) {
					if (result != null && result.getPaths() != null
							&& result.getPaths().size() > 0) {

						getListviewObj().getListview()
								.setAdapter(driverAdapter);
						driverAdapter.setLists(result.getPaths());
						getListviewObj().setStatus(LoadingEnum.LISTSHOW);
						dresult = result;

					} else {
						getListviewObj().setStatus(LoadingEnum.NODATA);
					}
				} else if (rCode == 27) {
					// error_network
					getListviewObj().setStatus(LoadingEnum.NODATA);

				} else if (rCode == 32) {
					// error_key
					getListviewObj().setStatus(LoadingEnum.NODATA);
				} else {
					// error_other
					getListviewObj().setStatus(LoadingEnum.NODATA);

				}
			}

			/**
			 * 步行路线结果回调
			 */
			@Override
			public void onWalkRouteSearched(WalkRouteResult result, int rCode) {
				KingTellerProgressUtils.closeProgress();
				if (rCode == 0) {
					if (result != null && result.getPaths() != null
							&& result.getPaths().size() > 0) {
						getListviewObj().getListview().setAdapter(walkAdapter);
						walkAdapter.setLists(result.getPaths());
						getListviewObj().setStatus(LoadingEnum.LISTSHOW);
						wresult = result;
					} else {
						getListviewObj().setStatus(LoadingEnum.NODATA);
					}
				} else if (rCode == 27) {
					// error_network
					getListviewObj().setStatus(LoadingEnum.NODATA);

				} else if (rCode == 32) {
					// error_key
					getListviewObj().setStatus(LoadingEnum.NODATA);
				} else {
					// error_other
					getListviewObj().setStatus(LoadingEnum.NODATA);

				}
			}
		});

		getListviewObj().setStatus(LoadingEnum.TIP, "请输入查询条件");
		getListviewObj().getListview().setRefreshEnabled(false);
		getListviewObj().getListview().setLoadMoreEnabled(false);
		getListviewObj().getListview().setOnItemClickListener(this);

	}

	/**
	 * 初始化AMap对象
	 */
	private void initUI() {
		title_text = (TextView) findViewById(R.id.layout_main_text);
		title_left_btn = (Button) findViewById(R.id.layout_main_left_btn);
		title_righttwo_btn = (Button) findViewById(R.id.layout_main_righttwo_btn);
		
		title_text.setText("手机导航");
		title_left_btn.setBackgroundResource(R.drawable.btn_back_arrow);
		title_left_btn.setOnClickListener(this);
		title_righttwo_btn.setBackgroundResource(R.drawable.btn_serach);
		title_righttwo_btn.setOnClickListener(this);
		

		// 自定义系统定位小蓝点
		myLocationStyle = new MyLocationStyle();
		myLocationStyle.myLocationIcon(BitmapDescriptorFactory.fromResource(R.drawable.ic_mobilelocation_marker));// 设置小蓝点的图标
		myLocationStyle.strokeWidth(1.0f);// 设置圆形的边框粗细
		aMap.setMyLocationStyle(myLocationStyle);
		aMap.setLocationSource(this);// 设置定位监听
		aMap.getUiSettings().setMyLocationButtonEnabled(true);// 设置默认定位按钮是否显示
		aMap.setMyLocationEnabled(false);// 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false
		// KingTellerProgress.showProgress(this, "正在定位...");

		// KingTellerUtils.GDLocation(MobileNavActivity.this, null);

		AddressBean address = KingTellerApplication.getApplication().getCurAddress();
		// 移动到当前位置
		aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
				new LatLng(address.getLat(), address.getLng()), 15));

		aMap.addMarker(new MarkerOptions()
				.anchor(0.5f, 0.5f)
				.position(new LatLng(address.getLat(), address.getLng()))
				.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_mobilelocation_marker))
				.draggable(false));

		edittext_start = (ChangeColorButton) findViewById(R.id.edittext_start);
		edittext_end = (ChangeColorButton) findViewById(R.id.edittext_end);
		start_mac_code = (Button) findViewById(R.id.start_mac_code);
		end_mac_code = (Button) findViewById(R.id.end_mac_code);

		edittext_start.setOnClickListener(this);
		edittext_end.setOnClickListener(this);
		start_mac_code.setOnClickListener(this);
		end_mac_code.setOnClickListener(this);

		busButton = (Button) findViewById(R.id.imagebtn_roadsearch_tab_transit);
		drivingButton = (Button) findViewById(R.id.imagebtn_roadsearch_tab_driving);
		walkButton = (Button) findViewById(R.id.imagebtn_roadsearch_tab_walk);

		busButton.setOnClickListener(this);
		drivingButton.setOnClickListener(this);
		walkButton.setOnClickListener(this);

		layout_map = (LinearLayout) findViewById(R.id.layout_map);
		layout_set_line = (LinearLayout) findViewById(R.id.layout_set_line);

	}

	
	/**
	 * 方法必须重写
	 */
	@Override
	public void onPause() {
		super.onPause();
		//deactivate();
	}

	/**
	 * 激活定位
	 */
	@Override
	public void activate(OnLocationChangedListener listener) {
//		mListener = listener;
//		if (mAMapLocationManager == null) {
//			mAMapLocationManager = LocationManagerProxy.getInstance(this);
//			/*
//			 * mAMapLocManager.setGpsEnable(false);
//			 * 1.0.2版本新增方法，设置true表示混合定位中包含gps定位，false表示纯网络定位，默认是true Location
//			 * API定位采用GPS和网络混合定位方式
//			 * ，第一个参数是定位provider，第二个参数时间最短是2000毫秒，第三个参数距离间隔单位是米，第四个参数是定位监听者
//			 */
//			mAMapLocationManager.requestLocationUpdates(
//					LocationProviderProxy.AMapNetwork, 2000, 10,
//					locationlistener);
//		}
	}

	/**
	 * 停止定位
	 */
	@Override
	public void deactivate() {
//		mListener = null;
//		if (mAMapLocationManager != null) {
//			mAMapLocationManager.removeUpdates(locationlistener);
//			// mAMapLocationManager.destory();
//		}
//		mAMapLocationManager = null;
	}

	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub
		switch (view.getId()) {
		case R.id.layout_main_left_btn:
			if (layout_map.getVisibility() == View.VISIBLE) {
				finish();
			} else {
				setLayout(1);
			}
			break;
		case R.id.layout_main_righttwo_btn:
			if (layout_map.getVisibility() == View.VISIBLE) {
				setLayout(2);
			} else {
				start = KingTellerApplication.getApplication().getCurAddress();
				if (start != null) {
					edittext_start.setText(KingTellerApplication.getApplication().getCurAddress().getName());
					getNavLine();
				} else{
					T.showShort(mContext, "未获取到您的定位数据!");
				}
			}
			break;
		case R.id.edittext_start:
			Intent onintent = new Intent(this, SelectAddressActivity.class);
			onintent.putExtra("isStart", true);
			startActivityForResult(onintent, KingTellerStaticConfig.REQUEST_ONCAR);
			break;
		case R.id.edittext_end:
			Intent offintent = new Intent(this, SelectAddressActivity.class);
			offintent.putExtra("isStart", false);
			startActivityForResult(offintent, KingTellerStaticConfig.REQUEST_OFFCAR);
			break;
		case R.id.start_mac_code:
			Intent onmacintent = new Intent(this, SelectATMCodeActivity.class);
			onmacintent.putExtra("isStart", true);
			startActivityForResult(onmacintent, KingTellerStaticConfig.REQUEST_ONATMCODE);
			break;
		case R.id.end_mac_code:
			Intent offmacintent = new Intent(this, SelectATMCodeActivity.class);
			offmacintent.putExtra("isStart", false);
			startActivityForResult(offmacintent, KingTellerStaticConfig.REQUEST_OFFATMCODE);
			break;
		case R.id.imagebtn_roadsearch_tab_transit:
			busRoute();
			break;
		case R.id.imagebtn_roadsearch_tab_driving:
			drivingRoute();
			break;
		case R.id.imagebtn_roadsearch_tab_walk:
			walkRoute();
			break;
		default:
			break;
		}

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
		case KingTellerStaticConfig.REQUEST_ONATMCODE:
		case KingTellerStaticConfig.REQUEST_ONCAR:
			if (Activity.RESULT_OK == resultCode) {
				AddressBean address = (AddressBean) data.getSerializableExtra(SelectAddressActivity.ADDRESS);
				edittext_start.setText(address.getName());
				start = address;
				getNavLine();
			}
			break;
		case KingTellerStaticConfig.REQUEST_OFFCAR:
		case KingTellerStaticConfig.REQUEST_OFFATMCODE:
			if (Activity.RESULT_OK == resultCode) {
				AddressBean address = (AddressBean) data.getSerializableExtra(SelectAddressActivity.ADDRESS);
				edittext_end.setText(address.getName());
				end = address;
				getNavLine();
			}
			break;

		default:
			break;
		}
	}

	/**
	 * 选择公交模式
	 */
	private void busRoute() {
		routeType = BUS;// 标识为公交模式
		busMode = RouteSearch.BusDefault;
		busButton.setBackgroundResource(R.drawable.ic_geofence_bus_pressed);
		drivingButton.setBackgroundResource(R.drawable.ic_geofence_taxi_nomal);
		walkButton.setBackgroundResource(R.drawable.ic_geofence_walk_nomal);
		getNavLine();
	}

	/**
	 * 选择驾车模式
	 */
	private void drivingRoute() {
		routeType = DRIVE;// 标识为驾车模式
		drivingMode = RouteSearch.DrivingSaveMoney;
		busButton.setBackgroundResource(R.drawable.ic_geofence_bus_nomal);
		drivingButton.setBackgroundResource(R.drawable.ic_geofence_taxi_pressed);
		walkButton.setBackgroundResource(R.drawable.ic_geofence_walk_nomal);
		getNavLine();
	}

	/**
	 * 选择步行模式
	 */
	private void walkRoute() {
		routeType = WALK;// 标识为步行模式
		walkMode = RouteSearch.WalkMultipath;
		busButton.setBackgroundResource(R.drawable.ic_geofence_bus_nomal);
		drivingButton.setBackgroundResource(R.drawable.ic_geofence_taxi_nomal);
		walkButton.setBackgroundResource(R.drawable.ic_geofence_walk_pressed);
		getNavLine();
	}

	private void getNavLine() {
		if (start == null || end == null)
			return;

		getListviewObj().setStatus(LoadingEnum.LOADING);
		RouteSearch.FromAndTo fromAndTo = new RouteSearch.FromAndTo(
				new LatLonPoint(start.getLat(), start.getLng()),
				new LatLonPoint(end.getLat(), end.getLng()));

		if (routeType == BUS) {// 公交路径规划
			BusRouteQuery query = new BusRouteQuery(fromAndTo, busMode,
					KingTellerApplication.getApplication().getCurAddress()
							.getCity(), 0);// 第一个参数表示路径规划的起点和终点，第二个参数表示公交查询模式，第三个参数表示公交查询城市区号，第四个参数表示是否计算夜班车，0表示不计算
			routeSearch.calculateBusRouteAsyn(query);// 异步路径规划公交模式查询
		} else if (routeType == DRIVE) {// 驾车路径规划
			DriveRouteQuery query = new DriveRouteQuery(fromAndTo, drivingMode,
					null, null, "");// 第一个参数表示路径规划的起点和终点，第二个参数表示驾车模式，第三个参数表示途经点，第四个参数表示避让区域，第五个参数表示避让道路
			routeSearch.calculateDriveRouteAsyn(query);// 异步路径规划驾车模式查询
		} else if (routeType == WALK) {// 步行路径规划
			WalkRouteQuery query = new WalkRouteQuery(fromAndTo, walkMode);
			routeSearch.calculateWalkRouteAsyn(query);// 异步路径规划步行模式查询
		}

	}

	@Override
	public void onItemClick(AdapterView<?> adapterView, View arg1, int postion,
			long arg3) {
		// TODO Auto-generated method stub
		switch (routeType) {
		case BUS:
			aMap.clear();// 清理地图上的所有覆盖物
			BusRouteOverlay routeOverlay = new BusRouteOverlay(this, aMap,
					bresult.getPaths().get(postion - 1), bresult.getStartPos(),
					bresult.getTargetPos());
			routeOverlay.removeFromMap();
			routeOverlay.addToMap();
			routeOverlay.zoomToSpan();
			break;
		case DRIVE:
			aMap.clear();// 清理地图上的所有覆盖物
			DrivingRouteOverlay drivingRouteOverlay = new DrivingRouteOverlay(
					this, aMap, dresult.getPaths().get(postion - 1),
					dresult.getStartPos(), dresult.getTargetPos());
			drivingRouteOverlay.removeFromMap();
			drivingRouteOverlay.addToMap();
			drivingRouteOverlay.zoomToSpan();
			break;
		case WALK:
			aMap.clear();// 清理地图上的所有覆盖物
			WalkRouteOverlay walkRouteOverlay = new WalkRouteOverlay(this,
					aMap, wresult.getPaths().get(postion - 1),
					wresult.getStartPos(), wresult.getTargetPos());
			walkRouteOverlay.removeFromMap();
			walkRouteOverlay.addToMap();
			walkRouteOverlay.zoomToSpan();
			break;
		default:
			break;
		}

		setLayout(1);

	}

	private void setLayout(int pos) {
		switch (pos) {
		case 1:
			layout_map.setVisibility(View.VISIBLE);
			layout_set_line.setVisibility(View.GONE);
			title_text.setText("手机导航");
			title_righttwo_btn.setBackgroundResource(R.drawable.btn_serach);
			break;
		case 2:
			layout_map.setVisibility(View.GONE);
			layout_set_line.setVisibility(View.VISIBLE);
			title_text.setText("设置线路");
			title_righttwo_btn.setBackgroundResource(R.drawable.btn_direction);
			break;
		default:
			break;
		}
	}

	private AMapLocationListener locationlistener = new AMapLocationListener() {
		/**
		 * 此方法已经废弃
		 */
		@Override
		public void onLocationChanged(Location location) {
		}

		@Override
		public void onProviderDisabled(String provider) {
		}

		@Override
		public void onProviderEnabled(String provider) {
		}

		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
		}

		/**
		 * 定位成功后回调函数
		 */
		@Override
		public void onLocationChanged(AMapLocation aLocation) {
			KingTellerProgressUtils.closeProgress();
			if (mListener != null && aLocation != null) {
				mListener.onLocationChanged(aLocation);// 显示系统小蓝点
				String desc = "";
				Bundle locBundle = aLocation.getExtras();
				if (locBundle != null) {
					// cityCode = locBundle.getString("citycode");
					desc = locBundle.getString("desc");
				}
				AddressBean address = new AddressBean();
				address.setLat(aLocation.getLatitude());
				address.setLng(aLocation.getLongitude());
				address.setName(desc);
				address.setAddress(desc);
				address.setCity(aLocation.getCity());

				KingTellerApplication.getApplication().setCurAddress(address);

			}
		}
	};
	

	public ListViewObj getListviewObj() {
		return listviewObj;
	}

	public void setListviewObj(ListViewObj listviewObj) {
		this.listviewObj = listviewObj;
	}
	
	 /** dp to px */
    protected int dp2px(float dp) {
        final float scale = mContext.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    @Override
	public void onDestroy() {
		super.onDestroy();
		KingTellerApplication.removeActivity(this);
	}
}