package com.kingteller.client.activity.map;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.content.Context;
import android.content.DialogInterface;


import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import com.amap.api.maps2d.AMap.InfoWindowAdapter;
import com.amap.api.maps2d.AMap.OnInfoWindowClickListener;
import com.amap.api.maps2d.AMap.OnMarkerClickListener;
import com.amap.api.maps2d.CameraUpdateFactory;
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
import com.kingteller.client.activity.more.FeedBackActivity;
import com.kingteller.client.adapter.SelectListAdapter;
import com.kingteller.client.bean.common.CommonSelectData;
import com.kingteller.client.bean.map.ATMInfoWindowBean;
import com.kingteller.client.bean.map.ATMLocationBean;
import com.kingteller.client.bean.map.ATMSearchCondition;
import com.kingteller.client.bean.map.AddressBean;
import com.kingteller.client.config.KingTellerUrlConfig;
import com.kingteller.client.netauth.AjaxHttpCallBack;
import com.kingteller.client.netauth.KTHttpClient;
import com.kingteller.client.utils.KingTellerConfigUtils;
import com.kingteller.client.utils.KingTellerJsonUtils;
import com.kingteller.client.utils.KingTellerJudgeUtils;
import com.kingteller.client.view.KTAlertDialog;
import com.kingteller.client.view.dialog.ATMSearchConditionDialog;
import com.kingteller.client.view.dialog.NormalListDialog;
import com.kingteller.client.view.dialog.SearchGeneralDialog;
import com.kingteller.client.view.dialog.entity.DialogMenuItem;
import com.kingteller.client.view.dialog.entity.SearchGeneraItem;
import com.kingteller.client.view.dialog.listener.OnBtnSearchClickL;
import com.kingteller.client.view.dialog.listener.OnOperItemClickL;
import com.kingteller.client.view.dialog.use.KingTellerProgressUtils;
import com.kingteller.client.view.toast.T;
import com.kingteller.framework.http.AjaxParams;

/**
 * 机器查询
 * @author Administrator
 *
 */
public class ATMQueryActivity extends BaseMapActivity implements 
	OnClickListener, OnMarkerClickListener, OnInfoWindowClickListener, 
	InfoWindowAdapter, OnGeocodeSearchListener {
	
	private Context mContext;
	private TextView title_text;
	private Button title_left_btn, title_righttwo_btn;
	private TextView mTotalPage, mDTotal, mPage, mUp_page, mDown_page, mStatuSname;
	private RelativeLayout mATM_selectstatus;
	
	private GeocodeSearch geocoderSearch;
	private ATMInfoWindowBean mATMInfoWindowstr = new ATMInfoWindowBean();
	private Marker mMarKer;
	private List<MarkerOptions> MarkerOptionsAtmList;
	private LatLngBounds.Builder AtmBuilder;
	
	private String Totalpage;//总页数
	private String Dtotal;//总数
	private int nowPage = 1;
	
	private SearchGeneraItem mapAtm_search = new SearchGeneraItem();
	
	private String[] StringItem;
	private List<String> StringItemList;

	
	private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg){
            super.handleMessage(msg);
            if(msg.what == 1){
            	findViewById(R.id.include_foot).setVisibility(View.VISIBLE);//显示底部
            	mTotalPage.setText(msg.getData().getString("TotalPage"));
            	mDTotal.setText(msg.getData().getString("DTotal"));
            	mPage.setText(String.valueOf(nowPage));
            	if(KingTellerJudgeUtils.isEmpty(mapAtm_search.getAtmJqzt())){
            		mStatuSname.setText("所有状态");
            	}else{
            		mStatuSname.setText(mapAtm_search.getAtmJqzt());
            	}
            	
            	aMap.clear();
            	
            }else if(msg.what == 2){
            	findViewById(R.id.include_foot).setVisibility(View.VISIBLE);//显示底部
            	mTotalPage.setText(msg.getData().getString("TotalPage"));
            	mDTotal.setText(msg.getData().getString("DTotal"));
            	mPage.setText(String.valueOf(nowPage));
            	if(KingTellerJudgeUtils.isEmpty(mapAtm_search.getAtmJqzt())){
            		mStatuSname.setText("所有状态");
            	}else{
            		mStatuSname.setText(mapAtm_search.getAtmJqzt());
            	}
            	
            	aMap.clear();
            	
            	for(int i = 0;i < MarkerOptionsAtmList.size(); i++){
            		aMap.addMarker(MarkerOptionsAtmList.get(i));
            	}
            	aMap.moveCamera(CameraUpdateFactory.newLatLngBounds(AtmBuilder.build(), 10));
            }
            KingTellerProgressUtils.closeProgress();
        }
    };
    
    
    
	@Override
	public void onCreate(Bundle paramBundle) {
		super.onCreate(paramBundle);
		setContentView(R.layout.layout_atmquery);
		KingTellerApplication.addActivity(this);
		
		mContext = ATMQueryActivity.this;
		initUI();
		initData();
	}
	
	private void initUI() {
		title_text = (TextView) findViewById(R.id.layout_main_text);
		title_left_btn = (Button) findViewById(R.id.layout_main_left_btn);
		title_righttwo_btn = (Button) findViewById(R.id.layout_main_righttwo_btn);
		
		title_text.setText("机器查询");
		title_left_btn.setBackgroundResource(R.drawable.btn_back_arrow);
		title_left_btn.setOnClickListener(this);
		title_righttwo_btn.setBackgroundResource(R.drawable.btn_serach);
		title_righttwo_btn.setOnClickListener(this);
		
		
		mTotalPage = (TextView)findViewById(R.id.text_totalPage);//总页数
		mDTotal = (TextView)findViewById(R.id.text_dTotal);//总数量
		mPage = (TextView)findViewById(R.id.current_page);//当前页数
		mStatuSname = (TextView)findViewById(R.id.atm_statusname);//机器状态
		
		mUp_page = (TextView)findViewById(R.id.up_page);
		mDown_page = (TextView)findViewById(R.id.down_page);
		mATM_selectstatus = (RelativeLayout) findViewById(R.id.atm_selectstatus);
	
		
		mUp_page.setOnClickListener(this);
		mDown_page.setOnClickListener(this);
		mATM_selectstatus.setOnClickListener(this);
		
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
		
		StringItem = new String[] {"所有状态", "正常运行", "停机", "未上线", "撤机", "响应中", "处理中"};
		StringItemList = Arrays.asList(StringItem);
		
		mapAtm_search.setAtmJqlb("ATM");
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.layout_main_left_btn:
			finish();
			break;
		case R.id.layout_main_righttwo_btn:
			SearchGeneraItem str = new SearchGeneraItem();
			str = mapAtm_search;

			final SearchGeneralDialog searchgeneral_dialog = new SearchGeneralDialog(mContext, R.style.Login_dialog, 1, new Gson().toJson(str));
			searchgeneral_dialog.setOnBtnSearchClickL(
				new OnBtnSearchClickL() {
	                @Override
	                public void onBtnClick(String ss) {
	                	searchgeneral_dialog.dismiss();
	                	mapAtm_search = KingTellerJsonUtils.getPerson(ss, SearchGeneraItem.class);
	                	nowPage = 1;
	                	mapAtm_search.setAtmJqzt("");
	                	getATMStaff();
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
		case R.id.atm_selectstatus:
		       
			   final NormalListDialog dialog = new NormalListDialog(ATMQueryActivity.this, StringItem);
		        dialog.title("请选择机器状态")//
		                .layoutAnimation(null)
		                .titleBgColor(Color.parseColor("#409ED7"))//
		                .itemPressColor(Color.parseColor("#85D3EF"))//
		                .itemTextColor(Color.parseColor("#303030"))//
		                .show();
		        dialog.setOnOperItemClickL(new OnOperItemClickL() {
		            @Override
		            public void onOperItemClick(AdapterView<?> parent, View view, int position, long id) {
		                dialog.dismiss();
		                String operName = StringItemList.get(position);
		                if("所有状态".equals(operName)){
		                	operName = "";
		                }
		                nowPage = 1;
	                	mapAtm_search.setAtmJqzt(operName);
						getATMStaff();
		            } 
		        });
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
	public boolean onMarkerClick(Marker marker) {
		mMarKer = marker;
		aMap.moveCamera(CameraUpdateFactory.zoomTo(15));
		if(mMarKer.isInfoWindowShown()){
			mMarKer.hideInfoWindow();
		}
		
		mATMInfoWindowstr = new ATMInfoWindowBean();	
		
		String infowindow_jqid = marker.getTitle();
		getATMWindowDaTaStr(infowindow_jqid);
		
		return false;
	}

	@Override
	public void onInfoWindowClick(Marker arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public View getInfoContents(Marker arg0) {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * 监听自定义infowindow窗口的infowindow事件回调
	 */
	@Override
	public View getInfoWindow(Marker marker) {
		View infoWindow = getLayoutInflater().inflate(
				R.layout.custom_marker_infowindow, null);

		render(marker, infoWindow);
		return infoWindow;
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
				String str = arg0.getRegeocodeAddress().getFormatAddress() + "附近";
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
					getATMStaff();
				}else{
					T.showShort(mContext, "已经是第一页!");
					nowPage = 1;
				}	
				break;
			case 1://下一页
				nowPage = nowPage + 1;
				if(nowPage >= 1  && nowPage <= nowTotalPage){
					getATMStaff();
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
	public void getATMStaff() {
		if (!KingTellerJudgeUtils.isNetAvaliable(this)) {
			T.showShort(mContext, "没有网络，请检查网络是否可用!");
			return;
		}
		
		KTHttpClient fh = new KTHttpClient(true);
		AjaxParams params = new AjaxParams();
		
		params.put("page", String.valueOf(nowPage));//当前页数
		
		params.put("jqbh", KingTellerJudgeUtils.isEmpty(mapAtm_search.getAtmJqbh()) ? "" : mapAtm_search.getAtmJqbh());//机器编号
		params.put("atmhlike", KingTellerJudgeUtils.isEmpty(mapAtm_search.getAtmNum()) ? "" : mapAtm_search.getAtmNum());//ATM号
		params.put("extfield20", KingTellerJudgeUtils.isEmpty(mapAtm_search.getAtmSsdq()) ? "" : mapAtm_search.getAtmSsdq());//所属大区
		params.put("ssqy", KingTellerJudgeUtils.isEmpty(mapAtm_search.getAtmSsqy()) ? "" : mapAtm_search.getAtmSsqy());//所属区域
		params.put("ssyhLike", KingTellerJudgeUtils.isEmpty(mapAtm_search.getAtmSsyh()) ? "" : mapAtm_search.getAtmSsyh());//所属银行
		params.put("ssbsc", KingTellerJudgeUtils.isEmpty(mapAtm_search.getAtmSsfwz()) ? "" : mapAtm_search.getAtmSsfwz());//所属服务站
		params.put("jqgsrusername", KingTellerJudgeUtils.isEmpty(mapAtm_search.getAtmJqgsr()) ? "" : mapAtm_search.getAtmJqgsr());//机器归属人
		params.put("statusName", KingTellerJudgeUtils.isEmpty(mapAtm_search.getAtmJqzt()) ? "" : mapAtm_search.getAtmJqzt());//机器状态

		String jqlb = KingTellerJudgeUtils.isEmpty(mapAtm_search.getAtmJqlb()) ? "" : mapAtm_search.getAtmJqlb(); //机器类别
		if("ATM".equals(jqlb)){ 
			params.put("jqlb", "A");
		}else if("清分机".equals(jqlb)){
			params.put("jqlb", "Q");
		}else if("CRS".equals(jqlb)){
			params.put("jqlb", "C");
		}else if("VTM".equals(jqlb)){
			params.put("jqlb", "V");
		}else if("查询机".equals(jqlb)){
			params.put("jqlb", "S");
		}else if("其他".equals(jqlb)){
			params.put("jqlb", "O");
		}
		
		
		
		fh.post(KingTellerConfigUtils.CreateUrl(this, KingTellerUrlConfig.StaffATMListUrl), params,
				new AjaxHttpCallBack<List<ATMLocationBean>>(this,
						new TypeToken<List<ATMLocationBean>>() {
						}.getType(), true) {
			
					@Override
					public void onError(int errorNo, String strMsg) {
						super.onError(errorNo, strMsg);
						KingTellerProgressUtils.closeProgress();
						T.showShort(mContext, "数据访问超时!");
					}
					
					@Override
					public void onStart() {
						KingTellerProgressUtils.showProgress(ATMQueryActivity.this,"正在查询中...");
					}

					@Override
					public void onDo(List<ATMLocationBean> data) {
						Log.e("ATMsearch","onDo");
						if (data.size() > 0) {
							if(!KingTellerJudgeUtils.isEmpty(data.get(0).getMsg())){
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
								AddMarkerAtmThread threadtwo = new AddMarkerAtmThread(data, Totalpage, Dtotal);
								threadtwo.start();	
							}
						}
					};
				});
	}
	
	/**
	 * 自定义infowinfow窗口
	 */
	public void render(Marker marker, View view) {
		view.findViewById(R.id.atm_show).setVisibility(View.VISIBLE);
		view.findViewById(R.id.fwz_show).setVisibility(View.GONE);
		
		TextView text_jqbh = ((TextView) view.findViewById(R.id.infowindow_jqbh));
		TextView text_ssdq = ((TextView) view.findViewById(R.id.infowindow_ssdq));
		TextView text_ssyh = ((TextView) view.findViewById(R.id.infowindow_ssyh));
		TextView text_jqzt = ((TextView) view.findViewById(R.id.infowindow_jqzt));
		TextView text_ssqy = ((TextView) view.findViewById(R.id.infowindow_ssqy));
		TextView text_sszh = ((TextView) view.findViewById(R.id.infowindow_sszh));
		TextView text_atmh = ((TextView) view.findViewById(R.id.infowindow_atmh));
		TextView text_ssfwz = ((TextView) view.findViewById(R.id.infowindow_ssfwz));
		TextView text_ssgsr = ((TextView) view.findViewById(R.id.infowindow_ssgsr));
		TextView text_wdsbjc = ((TextView) view.findViewById(R.id.infowindow_wdsbjc));
		TextView text_wddz = ((TextView) view.findViewById(R.id.infowindow_wddz));
		TextView text_dwdz = ((TextView) view.findViewById(R.id.infowindow_dwdz));
		
		RelativeLayout mATM_infowindowdelect = (RelativeLayout) view.findViewById(R.id.infowindow_delect);
		mATM_infowindowdelect.setOnClickListener(new View.OnClickListener() {
		        @Override
		        public void onClick(View v) {
		        	mMarKer.hideInfoWindow();
		        }
		});
		
		String dwdz = marker.getSnippet();
		
        text_jqbh.setText(mATMInfoWindowstr.getJqbh());
        text_ssdq.setText(mATMInfoWindowstr.getSsdq());
        text_ssyh.setText(mATMInfoWindowstr.getSsyh());
        text_jqzt.setText(mATMInfoWindowstr.getStatusName());
        text_ssqy.setText(mATMInfoWindowstr.getSsqy());
        text_sszh.setText(mATMInfoWindowstr.getSszh());
        text_atmh.setText(mATMInfoWindowstr.getAtmh());
        text_ssfwz.setText(mATMInfoWindowstr.getFwz());
        text_ssgsr.setText(mATMInfoWindowstr.getJqgsrusername());
        text_wdsbjc.setText(mATMInfoWindowstr.getWdsbjc());
        text_wddz.setText(mATMInfoWindowstr.getWddz());
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
	 * @param jqid
	 */
	private void getATMWindowDaTaStr(String jqid) {
		if (!KingTellerJudgeUtils.isNetAvaliable(this)) {
			T.showShort(mContext, "没有网络，请检查网络是否可用!");
			return;
		}
		KTHttpClient fh = new KTHttpClient(true);
		AjaxParams params = new AjaxParams();
		params.put("jqid", jqid);//机器ID
		//机器编号 ，所属大区，所属银行，机器状态，所属区域，所属支行，ATM号，所属服务站，机器归属人，网点设备简称，网点地址，定位地址

		fh.post(KingTellerConfigUtils.CreateUrl(this, KingTellerUrlConfig.StaffATMListUrl), params,
				new AjaxHttpCallBack<List<ATMInfoWindowBean>>(this,
						new TypeToken<List<ATMInfoWindowBean>>() {
						}.getType(), true) {
					@Override
					public void onError(int errorNo, String strMsg) {
						// TODO Auto-generated method stub
						super.onError(errorNo, strMsg);
						Log.e("ATMsearch","onError" + errorNo + ":" + strMsg);
						T.showShort(mContext, "数据访问超时!");
					}
					@Override
					public void onDo(List<ATMInfoWindowBean> data) {
						Log.e("ATMsearch","onDo");
						if (data.size() > 0) {
							mATMInfoWindowstr = data.get(0);
							if(!KingTellerJudgeUtils.isEmpty(mATMInfoWindowstr.getMsg())){
								T.showShort(mContext, mATMInfoWindowstr.getMsg());
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
	class AddMarkerAtmThread extends Thread {
        private List<ATMLocationBean> mData; //定义需要传值进来的参数
        private String mTotalpage;
        private String mDtotal;
        public AddMarkerAtmThread(List<ATMLocationBean> data ,String Totalpage, String Dtotal){
                this.mData = data;
                this.mTotalpage = Totalpage;
                this.mDtotal = Dtotal;
        }
        @Override
        public void run() {
    		int length = mData.size();

    		//将所有点显示在地图上
    		AtmBuilder = new LatLngBounds.Builder();
    		MarkerOptionsAtmList = new ArrayList<MarkerOptions>();
    		int StatusNum;
    		
    		for(int i = 0; i<length; i++){
    			StatusNum = Integer.parseInt(mData.get(i).getStatus());
    			MarkerOptions markerOption = new MarkerOptions();
    			LatLng latlng = new LatLng(mData.get(i).getY(), mData.get(i).getX());
    			
    			markerOption.anchor(0.5f, 1f);
    			markerOption.position(latlng);
    			markerOption.title(mData.get(i).getJqid());//存放jqid
    			markerOption.snippet("正在获取中...");//存放dwdz
    			
    			switch (StatusNum) {
    				case 1://正常运行
    					markerOption.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_atmlocation_marker6));
    					break;
    				case 2://停机
    					markerOption.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_atmlocation_marker2));
    					break;
    				case 3://撤机
    					markerOption.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_atmlocation_marker3));
    					break;
    				case 4://未上线
    					markerOption.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_atmlocation_marker4));
    					break;
    				case 5://测试机
    					markerOption.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_atmlocation_marker6));
    					break;
    				case 6://撤机
    					markerOption.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_atmlocation_marker3));
    					break;
    				case 10://处理中
    					markerOption.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_atmlocation_marker1));
    					break;
    				case 12://响应中
    					markerOption.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_atmlocation_marker5));
    					break;
    				default:
    					break;
    			}
    			
    			MarkerOptionsAtmList.add(markerOption);
    			AtmBuilder.include(markerOption.getPosition());//构造范围
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
