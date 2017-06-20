package com.kingteller.client.activity.map;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.AMap.InfoWindowAdapter;
import com.amap.api.maps2d.AMap.OnInfoWindowClickListener;
import com.amap.api.maps2d.AMap.OnMarkerClickListener;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.LatLngBounds;
import com.amap.api.maps2d.model.Marker;
import com.amap.api.maps2d.model.MarkerOptions;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeQuery;
import com.amap.api.services.geocoder.GeocodeSearch.OnGeocodeSearchListener;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kingteller.R;
import com.kingteller.client.KingTellerApplication;
import com.kingteller.client.activity.base.BaseMapActivity;
import com.kingteller.client.bean.map.AddressBean;
import com.kingteller.client.bean.map.ServiceStationInfoWindowBean;
import com.kingteller.client.bean.map.ServiceStationLocationBean;
import com.kingteller.client.config.KingTellerUrlConfig;
import com.kingteller.client.netauth.AjaxHttpCallBack;
import com.kingteller.client.netauth.KTHttpClient;
import com.kingteller.client.utils.KingTellerConfigUtils;
import com.kingteller.client.utils.KingTellerJsonUtils;
import com.kingteller.client.utils.KingTellerJudgeUtils;
import com.kingteller.client.view.dialog.SearchGeneralDialog;
import com.kingteller.client.view.dialog.entity.SearchGeneraItem;
import com.kingteller.client.view.dialog.listener.OnBtnSearchClickL;
import com.kingteller.client.view.dialog.use.KingTellerProgressUtils;
import com.kingteller.client.view.toast.T;
import com.kingteller.framework.http.AjaxParams;

/**
 * 服务站查询
 * @author Administrator
 */
public class ServiceStationQueryActivity extends BaseMapActivity implements OnClickListener, OnMarkerClickListener, 
OnInfoWindowClickListener, InfoWindowAdapter, OnGeocodeSearchListener{
	
	private Context mContext;
	private TextView title_text;
	private Button title_left_btn, title_righttwo_btn;
	private TextView mTotalPage, mDTotal, mPage, mUp_page, mDown_page;
	
	private GeocodeSearch geocoderSearch;
	private Marker mMarKer;
	private ServiceStationInfoWindowBean mFWZInfoWindowStr = new ServiceStationInfoWindowBean();
	private LatLngBounds.Builder FwzBuilder;
	private List<MarkerOptions> MarkerOptionsFwzList;

	private String Totalpage;//总页数
	private String Dtotal;//总数
	private int nowPage = 1;
	
	private SearchGeneraItem mapFwz_search = new SearchGeneraItem();
	
	private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg){
            super.handleMessage(msg);
            if(msg.what == 1){
            	findViewById(R.id.include_foot).setVisibility(View.VISIBLE);//显示底部
            	findViewById(R.id.atm_selectstatus).setVisibility(View.GONE);//隐藏机器类别
            	
            	mTotalPage.setText(msg.getData().getString("TotalPage"));
            	mDTotal.setText(msg.getData().getString("DTotal"));
            	mPage.setText(String.valueOf(nowPage));
            	
            	aMap.clear();
            	
            }else if(msg.what == 2){
            	findViewById(R.id.include_foot).setVisibility(View.VISIBLE);//显示底部
            	findViewById(R.id.atm_selectstatus).setVisibility(View.GONE);//隐藏机器类别
            	
            	mTotalPage.setText(msg.getData().getString("TotalPage"));
            	mDTotal.setText(msg.getData().getString("DTotal"));
            	mPage.setText(String.valueOf(nowPage));
            	
            	aMap.clear();
            	
            	for(int i = 0;i < MarkerOptionsFwzList.size(); i++){
            		aMap.addMarker(MarkerOptionsFwzList.get(i));
            	}
            	 //移动地图，所有marker自适应显示。LatLngBounds与地图边缘10像素的填充区域
            	aMap.moveCamera(CameraUpdateFactory.newLatLngBounds(FwzBuilder.build(), 10));
            }
            KingTellerProgressUtils.closeProgress();
        }
    };

	@Override
	public void onCreate(Bundle paramBundle) {
		super.onCreate(paramBundle);
		setContentView(R.layout.layout_servicestationquery);
		KingTellerApplication.addActivity(this);
		
		mContext = ServiceStationQueryActivity.this;
		initUI();
		initData();
	}

	

	private void initUI() {
		title_text = (TextView) findViewById(R.id.layout_main_text);
		title_left_btn = (Button) findViewById(R.id.layout_main_left_btn);
		title_righttwo_btn = (Button) findViewById(R.id.layout_main_righttwo_btn);
		
		title_text.setText("服务站查询");
		title_left_btn.setBackgroundResource(R.drawable.btn_back_arrow);
		title_left_btn.setOnClickListener(this);
		title_righttwo_btn.setBackgroundResource(R.drawable.btn_serach);
		title_righttwo_btn.setOnClickListener(this);

		
		mTotalPage = (TextView)findViewById(R.id.text_totalPage);//总页数
		mDTotal = (TextView)findViewById(R.id.text_dTotal);//总数量
		mPage = (TextView)findViewById(R.id.current_page);//当前页数
		
		mUp_page = (TextView)findViewById(R.id.up_page);
		mDown_page = (TextView)findViewById(R.id.down_page);
		
		mUp_page.setOnClickListener(this);
		mDown_page.setOnClickListener(this);
		
		//设置默认定位按钮是否显示
        aMap.getUiSettings().setMyLocationButtonEnabled(false);
        
		aMap.setMyLocationEnabled(true);
		aMap.setOnMarkerClickListener(this);// 设置点击marker事件监听器

		aMap.setOnInfoWindowClickListener(this);// 设置点击infoWindow事件监听器
		aMap.setInfoWindowAdapter(this);// 设置自定义InfoWindow样式
		
		geocoderSearch = new GeocodeSearch(this);
		geocoderSearch.setOnGeocodeSearchListener(this);
	}
	
	private void initData() {
		AddressBean address = KingTellerApplication.getApplication().getCurAddress();
		LatLng latlng = new LatLng(address.getLat(), address.getLng());
		aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latlng, 15));
	}



	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.layout_main_left_btn:
			finish();
			break;
		case R.id.layout_main_righttwo_btn:
			SearchGeneraItem str = new SearchGeneraItem();
			str = mapFwz_search;

			final SearchGeneralDialog searchgeneral_dialog = new SearchGeneralDialog(mContext, R.style.Login_dialog, 2, new Gson().toJson(str));
			searchgeneral_dialog.setOnBtnSearchClickL(
				new OnBtnSearchClickL() {
	                @Override
	                public void onBtnClick(String ss) {
	                	searchgeneral_dialog.dismiss();
	                	mapFwz_search = KingTellerJsonUtils.getPerson(ss, SearchGeneraItem.class);
	                	nowPage = 1;
	                	getServiceStationQueryActivityStaff();
	                }
	            },
	            new OnBtnSearchClickL() {
	                @Override
	                public void onBtnClick(String qx) {
	                	searchgeneral_dialog.dismiss();
	                }
	            });
			
			searchgeneral_dialog.show();
			break;
		case R.id.up_page:
			setFlip(0);
			break;
		case R.id.down_page:
			setFlip(1);
			break;
		default:
			break;
		}
	}



	@Override
	public View getInfoContents(Marker arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public View getInfoWindow(Marker marker) {
		View infoWindow = getLayoutInflater().inflate(
				R.layout.custom_marker_infowindow, null);

		render(marker, infoWindow);
		return infoWindow;
	}

	@Override
	public void onInfoWindowClick(Marker arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean onMarkerClick(Marker marker) {
		// TODO Auto-generated method stub
		mMarKer = marker;
		aMap.moveCamera(CameraUpdateFactory.zoomTo(15));
		if(mMarKer.isInfoWindowShown()){
			mMarKer.hideInfoWindow();
		}
		
		mFWZInfoWindowStr = new ServiceStationInfoWindowBean();
		
		String infowindow_jqid = marker.getTitle();
		getFWZWindowDaTaStr(infowindow_jqid);
		
		return false;
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
				mMarKer.setSnippet(mMarKer.getSnippet().replace("正在获取中...",str));
				mMarKer.showInfoWindow();
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
	

	/**
	 * 翻页执行函数
	 * @param filp
	 */
	public void setFlip(int filp) {
		
		String totalpage = Totalpage;
		int nowTotalPage = Integer.parseInt(totalpage);
		
		switch (filp) {
			case 0://上一页
				nowPage = nowPage - 1;
				if(nowPage >= 1  && nowPage <= nowTotalPage){
					getServiceStationQueryActivityStaff();
				}else{
					T.showShort(mContext, "已经是第一页!");
					nowPage = 1;
				}	
				break;
			case 1://下一页
				nowPage = nowPage + 1;
				if(nowPage >= 1  && nowPage <= nowTotalPage){
					getServiceStationQueryActivityStaff();
				}else{
					T.showShort(mContext, "已经是最后一页!");
					nowPage = nowTotalPage;
				}
				break;
		}

	}
	
	/**
	 * 搜索
	 */
	public void getServiceStationQueryActivityStaff() {
		if (!KingTellerJudgeUtils.isNetAvaliable(this)) {
			T.showShort(mContext, "没有网络，请检查网络是否可用!");
			return;
		}
		
		KTHttpClient fh = new KTHttpClient(true);
		AjaxParams params = new AjaxParams();
		params.put("page", String.valueOf(nowPage));//当前页数
		
		params.put("ssdq", KingTellerJudgeUtils.isEmpty(mapFwz_search.getFwzSsdq()) ? "" : mapFwz_search.getFwzSsdq());//所属大区
		params.put("ssqy", KingTellerJudgeUtils.isEmpty(mapFwz_search.getFwzSsqy()) ? "" : mapFwz_search.getFwzSsqy());//所属区域
		params.put("fwz", KingTellerJudgeUtils.isEmpty(mapFwz_search.getFwzName()) ? "" : mapFwz_search.getFwzName());//服务站名
		
		fh.post(KingTellerConfigUtils.CreateUrl(this, KingTellerUrlConfig.StaffFWZListUrl), params,
				new AjaxHttpCallBack<List<ServiceStationLocationBean>>(this,
						new TypeToken<List<ServiceStationLocationBean>>() {
						}.getType(), true) {
			
			
					@Override
					public void onError(int errorNo, String strMsg) {
						super.onError(errorNo, strMsg);
						KingTellerProgressUtils.closeProgress();
						T.showShort(mContext, "数据访问超时!");
					}
					
					@Override
					public void onStart() {
						KingTellerProgressUtils.showProgress(ServiceStationQueryActivity.this,"正在查询中...");
					}
					@Override
					public void onDo(List<ServiceStationLocationBean> data) {
						Log.e("ATMsearch","onDo");
						if (data.size() > 0) {
							if(!KingTellerJudgeUtils.isEmpty(data.get(0).getMsg())){
								Totalpage = "0";
								Dtotal = "0";
								
								Thread threadone = new Thread(new Runnable(){
						            public void run() {
						            	Message message = new Message();
						                message.what = 1;
						                Bundle bundleData = new Bundle();  
						                bundleData.putString("TotalPage", "0");
						                bundleData.putString("DTotal" , "0");
						                message.setData(bundleData);  
						                handler.sendMessage(message);
						            }
						        });
								threadone.start();
								T.showShort(mContext, data.get(0).getMsg());
							}else{
								Totalpage = data.get(0).getTotalPage();
								Dtotal = data.get(0).getdTotal();
								AddMarkerFwzThread threadtwo = new AddMarkerFwzThread(data, Totalpage, Dtotal);
								threadtwo.start();
							}

						}
					}
				});
	}
	
	/**
	 * 自定义infowinfow窗口
	 */
	public void render(Marker marker, View view) {
		view.findViewById(R.id.atm_show).setVisibility(View.GONE);
		view.findViewById(R.id.fwz_show).setVisibility(View.VISIBLE);
		
		TextView text_ssdq = ((TextView) view.findViewById(R.id.fwz_infowindow_ssdq));
		TextView text_ssqy = ((TextView) view.findViewById(R.id.fwz_infowindow_ssqy));
		TextView text_fwz = ((TextView) view.findViewById(R.id.fwz_infowindow_fwzm));
		TextView text_machinecount = ((TextView) view.findViewById(R.id.fwz_infowindow_jqsl));
		TextView text_fwzfzrcount = ((TextView) view.findViewById(R.id.fwz_infowindow_fwzfzrs));
		TextView text_whgcscount  = ((TextView) view.findViewById(R.id.fwz_infowindow_whgcsrs));
		TextView text_dwdz = ((TextView) view.findViewById(R.id.fwz_infowindow_dwdz));
		
		RelativeLayout mATM_infowindowdelect = (RelativeLayout) view.findViewById(R.id.infowindow_delect);
		mATM_infowindowdelect.setOnClickListener(new View.OnClickListener() {
		        @Override
		        public void onClick(View v) {
		        	mMarKer.hideInfoWindow();
		        }
		});
		
		String dwdz = marker.getSnippet();
		
		text_ssdq.setText(mFWZInfoWindowStr.getSsdq());
		text_ssqy.setText(mFWZInfoWindowStr.getSsqy());
		text_fwz.setText(mFWZInfoWindowStr.getFwz());
		text_machinecount.setText(mFWZInfoWindowStr.getMachinecount());
		text_fwzfzrcount.setText(mFWZInfoWindowStr.getFwzfzrcount());
		text_whgcscount.setText(mFWZInfoWindowStr.getWhgcscount());
        text_dwdz.setText(dwdz);

	}
	
	/**
	 * 地理解析
	 * @param latLng
	 */
	public void getAddress(LatLng latLng) {
		RegeocodeQuery query = new RegeocodeQuery(new LatLonPoint(
				latLng.latitude, latLng.longitude), 200, GeocodeSearch.AMAP);// 第一个参数表示一个Latlng，第二参数表示范围多少米，第三个参数表示是火系坐标系还是GPS原生坐标系
		geocoderSearch.getFromLocationAsyn(query);// 设置同步逆地理编码请求

	}
	
	/**
	 * 访问infowindow内容
	 * @param orgid
	 */
	private void getFWZWindowDaTaStr(String orgid) {
		if (!KingTellerJudgeUtils.isNetAvaliable(this)) {
			T.showShort(mContext, "没有网络，请检查网络是否可用!");
			return;
		}
		KTHttpClient fh = new KTHttpClient(true);
		AjaxParams params = new AjaxParams();
		params.put("orgid", orgid);//机器ID
		//机器编号 ，所属大区，所属银行，机器状态，所属区域，所属支行，ATM号，所属服务站，机器归属人，网点设备简称，网点地址，定位地址

		fh.post(KingTellerConfigUtils.CreateUrl(this, KingTellerUrlConfig.StaffFWZListUrl), params,
				new AjaxHttpCallBack<List<ServiceStationInfoWindowBean>>(this,
						new TypeToken<List<ServiceStationInfoWindowBean>>() {
						}.getType(), true) {

					@Override
					public void onError(int errorNo, String strMsg) {
						super.onError(errorNo, strMsg);
						T.showShort(mContext, "数据访问超时!");
					}
					@Override
					public void onDo(List<ServiceStationInfoWindowBean> data) {
						Log.e("ATMsearch","onDo");
						if (data.size() > 0) {
							mFWZInfoWindowStr = data.get(0);
							if(!KingTellerJudgeUtils.isEmpty(mFWZInfoWindowStr.getMsg())){
								T.showShort(mContext, mFWZInfoWindowStr.getMsg());
							}else{
								if (mMarKer.getSnippet().endsWith("正在获取中...")){
										getAddress(mMarKer.getPosition());
									}else{
										mMarKer.showInfoWindow();
									}
								
							}	
						}
					};
				});
 
	}
	
	/**
	 * 开启线程向地图添加点
	 * @author Administrator
	 */
	class AddMarkerFwzThread extends Thread {
        private List<ServiceStationLocationBean> mData; //定义需要传值进来的参数
        private String mTotalpage;
        private String mDtotal;
        public AddMarkerFwzThread(List<ServiceStationLocationBean> data, String Totalpage, String Dtotal){
                this.mData = data;
                this.mTotalpage = Totalpage;
                this.mDtotal = Dtotal;
        }
        @Override
        public void run() {
    		int length = mData.size();
    		//将所有点显示在地图上
    		FwzBuilder = new LatLngBounds.Builder();
    		MarkerOptionsFwzList = new ArrayList<MarkerOptions>();
    		
    		for(int i = 0; i<length; i++){
    			MarkerOptions markerOption = new MarkerOptions();
    			LatLng latlng = new LatLng(mData.get(i).getY(), mData.get(i).getX());
    			markerOption.anchor(0.5f, 1f);
    			markerOption.position(latlng);
    			markerOption.title(mData.get(i).getOrgid());//存放fwzid
    			markerOption.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_atmlocation_marker6));
    			markerOption.snippet("正在获取中...");//存放dwdz
    			
    			MarkerOptionsFwzList.add(markerOption);
    			FwzBuilder.include(markerOption.getPosition());//构造范围
    		}

            Message message = new Message();
            message.what = 2;
            Bundle bundleData = new Bundle();  
            bundleData.putString("TotalPage", mTotalpage);
            bundleData.putString("DTotal" , mDtotal);
            message.setData(bundleData);  
            handler.sendMessage(message);
        
        }
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		KingTellerApplication.removeActivity(this);
	}
}
