package com.kingteller.client.activity.common;

import java.util.List;

import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.GeocodeSearch.OnGeocodeSearchListener;
import com.amap.api.services.geocoder.RegeocodeAddress;
import com.amap.api.services.geocoder.RegeocodeQuery;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.google.gson.reflect.TypeToken;
import com.kingteller.R;
import com.kingteller.client.KingTellerApplication;
import com.kingteller.client.adapter.SelectATMCodeAdapter;
import com.kingteller.client.bean.map.ATMCodeBean;
import com.kingteller.client.bean.map.AddressBean;
import com.kingteller.client.config.KingTellerUrlConfig;
import com.kingteller.client.netauth.AjaxHttpCallBack;
import com.kingteller.client.netauth.KTHttpClient;
import com.kingteller.client.utils.KingTellerConfigUtils;
import com.kingteller.client.utils.KingTellerJudgeUtils;
import com.kingteller.client.view.ListViewObj;
import com.kingteller.client.view.datatype.LoadingEnum;
import com.kingteller.client.view.dialog.use.KingTellerProgressUtils;
import com.kingteller.client.view.toast.T;
import com.kingteller.framework.http.AjaxParams;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

/**
 * ATM号选择器
 * @author 王定波
 *
 */
public class SelectATMCodeActivity extends Activity implements
		OnClickListener, OnItemClickListener {

	private boolean isStart;
	private SelectATMCodeAdapter searchAdapter;
	private ProgressBar progress;
	private boolean isFavAddrEdit = false;
	private GeocodeSearch geocoderSearch;
	public static final String ADDRESS = "address";
	private  ATMCodeBean selectatm;
	private ListViewObj listviewObj;
	
	private EditText edit;
	private ImageView address_icon;
	private TextView title_text;
	private Button title_left_btn, btn_search, btn_delete;
	private Context mContext;
	
	@Override
	public void onCreate(Bundle paramBundle) {
		super.onCreate(paramBundle);
		setContentView(R.layout.select_address);
		KingTellerApplication.addActivity(this);
		
		if (findViewById(R.id.loading_view) != null) {
			setListviewObj(new ListViewObj(this));
		}
		mContext = SelectATMCodeActivity.this;
		initUI();
		initData();
		initTitle();
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

		switch (v.getId()) {
		case R.id.layout_main_left_btn:
			finish();
			break;
		case R.id.btn_search:
			searchAddress();
			break;
		default:
			break;
		}
	}

	private void initData() {
		String from = getIntent().getStringExtra("from");
		if (from != null) {
			isFavAddrEdit = true;
		} else
			isFavAddrEdit = false;
		isStart = getIntent().getBooleanExtra("isStart", false);
		if (isStart) {
			String address = getIntent().getStringExtra("address");
			if (!KingTellerJudgeUtils.isEmpty(address)) {
				btn_delete.setVisibility(View.VISIBLE);
				edit.setText(address);
			}
		}

		geocoderSearch = new GeocodeSearch(this);
		geocoderSearch
				.setOnGeocodeSearchListener(new OnGeocodeSearchListener() {

					/**
					 * 响应逆地理编码
					 */
					@Override
					public void onRegeocodeSearched(RegeocodeResult result,
							int rCode) {
						KingTellerProgressUtils.closeProgress();
						
						if (rCode == 0) {
							if (result != null
									&& result.getRegeocodeAddress() != null) {
								RegeocodeAddress address = result.getRegeocodeAddress();
								AddressBean addr=new AddressBean();
								
								addr.setLat(selectatm.getX());
								addr.setLng(selectatm.getY());
								addr.setName("("+selectatm.getJqbh()+")"+address.getFormatAddress());
								addr.setAddress(address.getFormatAddress());
								
								Intent intent = new Intent();
								intent.putExtra(ADDRESS, addr);
								setResult(RESULT_OK, intent);
								finish();
							
								
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

					/**
					 * 地理编码查询回调
					 */
					@Override
					public void onGeocodeSearched(GeocodeResult result,
							int rCode) {
						

					}
				});

	}

	private void initTitle() {

		if (isStart) {
			title_text.setText("起点机器编码");
			edit.setHint("起点机器编码");
			address_icon.setBackgroundResource(R.drawable.ic_geofence_nomal);
		} else {
			title_text.setText("终点机器编码");
			edit.setHint("终点机器编码");
			address_icon.setBackgroundResource(R.drawable.ic_geofence_nomal);
		}

		if (isFavAddrEdit) {
			title_text.setText("设置机器编码");
			edit.setHint("搜索");
			address_icon.setBackgroundResource(R.drawable.ic_geofence_nomal);
		}
		
		title_left_btn.setOnClickListener(this);
	}

	private void initUI() {
		title_text = (TextView) findViewById(R.id.layout_main_text);
		title_left_btn = (Button) findViewById(R.id.layout_main_left_btn);
		
		title_left_btn.setBackgroundResource(R.drawable.btn_back_arrow);
		title_left_btn.setOnClickListener(this);
		
		edit = (EditText) findViewById(R.id.edit);
		
		btn_search = (Button) findViewById(R.id.btn_search);
		btn_search.setOnClickListener(this);
	
		address_icon = (ImageView)findViewById(R.id.img_view);
		
		btn_delete = (Button) findViewById(R.id.btn_delete);
		
		progress = (ProgressBar) findViewById(R.id.progress);
		
		btn_delete.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				edit.setText("");
			}
		});

		edit.addTextChangedListener(new TextWatcher() {

			@Override
			public void afterTextChanged(Editable s) {
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				if (s.length() > 0) {
					btn_search.setVisibility(View.VISIBLE);
					btn_delete.setVisibility(View.VISIBLE);
				} else {
					btn_search.setVisibility(View.GONE);
					btn_delete.setVisibility(View.GONE);
				}
			}

		});

		searchAdapter = new SelectATMCodeAdapter(this, null);

		getListviewObj().getListview().setAdapter(searchAdapter);
		getListviewObj().getListview().setRefreshEnabled(false);
		getListviewObj().getListview().setLoadMoreEnabled(false);
		getListviewObj().getListview().setOnItemClickListener(this);

		getListviewObj().setStatus(LoadingEnum.TIP, "请搜索");

	}

	private void searchAddress() {
		String editstr = edit.getText().toString();
		if (editstr == null || "".equals(editstr.trim())) {
			T.showShort(mContext, "请输入有效ATM编码!");
			return;
		}

		btn_search.setVisibility(View.GONE);
		btn_delete.setVisibility(View.GONE);
		progress.setVisibility(View.VISIBLE);
		edit.setEnabled(false);

		KTHttpClient fh = new KTHttpClient(true);
		AjaxParams params = new AjaxParams();
		params.put("jqbh", editstr);

		fh.post(KingTellerConfigUtils.CreateUrl(this, KingTellerUrlConfig.ATMListUrl), params,
				new AjaxHttpCallBack<List<ATMCodeBean>>(this,
						new TypeToken<List<ATMCodeBean>>() {
						}.getType(), true) {

					@Override
					public void onFinish() {
						getListviewObj().getListview().stopRefresh();
						getListviewObj().getListview().stopLoadMore();

						btn_search.setVisibility(View.VISIBLE);
						btn_delete.setVisibility(View.VISIBLE);
						progress.setVisibility(View.GONE);
						edit.setEnabled(true);

					}

					@Override
					public void onDo(List<ATMCodeBean> data) {
						searchAdapter.setLists(data);
						
						if (data.size() > 0) {
							getListviewObj().setStatus(LoadingEnum.LISTSHOW);

						} else
							getListviewObj().setStatus(LoadingEnum.NODATA);

					};

				});
	}

	@Override
	public void onItemClick(AdapterView<?> adapterView, View view, int pos,
			long arg3) {
		// TODO Auto-generated method stub

		ATMCodeBean address = (ATMCodeBean) adapterView.getAdapter().getItem(
				pos);

		LatLonPoint llp = new LatLonPoint(address.getX(), address.getY());
		RegeocodeQuery query = new RegeocodeQuery(llp, 200, GeocodeSearch.AMAP);// 第一个参数表示一个Latlng，第二参数表示范围多少米，第三个参数表示是火系坐标系还是GPS原生坐标系
		geocoderSearch.getFromLocationAsyn(query);// 设置同步逆地理编码请求
		selectatm=address;
		
		KingTellerProgressUtils.showProgress(this, "正在获取地址信息...");

	}
	

	public ListViewObj getListviewObj() {
		return listviewObj;
	}

	public void setListviewObj(ListViewObj listviewObj) {
		this.listviewObj = listviewObj;
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		KingTellerApplication.removeActivity(this);
	}
}
