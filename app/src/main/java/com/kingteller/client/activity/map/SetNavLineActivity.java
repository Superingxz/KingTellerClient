package com.kingteller.client.activity.map;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;

import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.route.BusPath;
import com.amap.api.services.route.BusRouteResult;
import com.amap.api.services.route.DrivePath;
import com.amap.api.services.route.DriveRouteResult;
import com.amap.api.services.route.RouteSearch;
import com.amap.api.services.route.WalkPath;
import com.amap.api.services.route.RouteSearch.BusRouteQuery;
import com.amap.api.services.route.RouteSearch.DriveRouteQuery;
import com.amap.api.services.route.RouteSearch.OnRouteSearchListener;
import com.amap.api.services.route.RouteSearch.WalkRouteQuery;
import com.amap.api.services.route.WalkRouteResult;
import com.kingteller.R;
import com.kingteller.client.KingTellerApplication;
import com.kingteller.client.activity.base.BaseActivity;
import com.kingteller.client.activity.common.SelectATMCodeActivity;
import com.kingteller.client.activity.common.SelectAddressActivity;
import com.kingteller.client.adapter.NavLineAdapter;
import com.kingteller.client.bean.map.AddressBean;
import com.kingteller.client.config.KingTellerStaticConfig;
import com.kingteller.client.view.ChangeColorButton;
import com.kingteller.client.view.datatype.LoadingEnum;
import com.kingteller.client.view.dialog.use.KingTellerProgressUtils;
import com.kingteller.client.view.toast.T;

/**
 * 设置线路
 * 
 * @author 王定波
 * 
 */
public class SetNavLineActivity extends BaseActivity implements
		OnClickListener, OnItemClickListener {
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

	private Context mContext;
	
	@Override
	public void onCreate(Bundle paramBundle) {
		super.onCreate(paramBundle);
		setContentView(R.layout.layout_setline);
		KingTellerApplication.addActivity(this);
		
		mContext = SetNavLineActivity.this;
		initUI();
		initData();
	}

	private void initData() {
		// TODO Auto-generated method stub
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

	private void initUI() {
		// TODO Auto-generated method stub
		title_title.setText("设置线路");
		title_right.setBackgroundResource(R.drawable.icon_nav);
		title_left.setOnClickListener(this);
		title_right.setOnClickListener(this);

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

	}

	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub
		switch (view.getId()) {
		case R.id.button_left:
			finish();
			break;
		case R.id.button_right:
			start = KingTellerApplication.getApplication().getCurAddress();
			if (start != null) {
				edittext_start.setText(KingTellerApplication.getApplication()
						.getCurAddress().getName());
				getNavLine();
			} else{
				T.showShort(mContext, "未获取到您的定位数据!");
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
				AddressBean address = (AddressBean) data
						.getSerializableExtra(SelectAddressActivity.ADDRESS);
				edittext_start.setText(address.getName());
				start = address;
				getNavLine();
			}
			break;
		case KingTellerStaticConfig.REQUEST_OFFCAR:
		case KingTellerStaticConfig.REQUEST_OFFATMCODE:
			if (Activity.RESULT_OK == resultCode) {
				AddressBean address = (AddressBean) data
						.getSerializableExtra(SelectAddressActivity.ADDRESS);
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
		drivingButton.setBackgroundResource(R.drawable.mode_driving_off);
		busButton.setBackgroundResource(R.drawable.mode_transit_on);
		walkButton.setBackgroundResource(R.drawable.mode_walk_off);
		getNavLine();

	}

	/**
	 * 选择驾车模式
	 */
	private void drivingRoute() {
		routeType = DRIVE;// 标识为驾车模式
		drivingMode = RouteSearch.DrivingSaveMoney;
		drivingButton.setBackgroundResource(R.drawable.mode_driving_on);
		busButton.setBackgroundResource(R.drawable.mode_transit_off);
		walkButton.setBackgroundResource(R.drawable.mode_walk_off);
		getNavLine();
	}

	/**
	 * 选择步行模式
	 */
	private void walkRoute() {
		routeType = WALK;// 标识为步行模式
		walkMode = RouteSearch.WalkMultipath;
		drivingButton.setBackgroundResource(R.drawable.mode_driving_off);
		busButton.setBackgroundResource(R.drawable.mode_transit_off);
		walkButton.setBackgroundResource(R.drawable.mode_walk_on);
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

		Intent intent = new Intent();

		switch (routeType) {
		case BUS:
			intent.putExtra(NAVLINE, bresult);
			break;
		case DRIVE:
			intent.putExtra(NAVLINE, dresult);
			break;
		case WALK:
			intent.putExtra(NAVLINE, wresult);
			break;
		default:
			break;
		}
		intent.putExtra("linecode", postion - 1);
		intent.putExtra("type", routeType);
		setResult(RESULT_OK, intent);
		finish();

	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		KingTellerApplication.removeActivity(this);
	}
}
