package com.kingteller.client.activity.workorder;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.kingteller.R;
import com.kingteller.client.KingTellerApplication;
import com.kingteller.client.activity.common.CommonSelectDataActivity;
import com.kingteller.client.adapter.SendOrderAdapter;
import com.kingteller.client.adapter.SendOrderAdapter.Callback;
import com.kingteller.client.bean.account.User;
import com.kingteller.client.bean.common.BasePageBean;
import com.kingteller.client.bean.common.CommonSelectData;
import com.kingteller.client.bean.common.KingTellerDateTime;
import com.kingteller.client.bean.workorder.SendOrderBean;
import com.kingteller.client.config.KingTellerStaticConfig;
import com.kingteller.client.config.KingTellerUrlConfig;
import com.kingteller.client.netauth.AjaxHttpCallBack;
import com.kingteller.client.netauth.KTHttpClient;
import com.kingteller.client.utils.ConditionUtils;
import com.kingteller.client.utils.KingTellerConfigUtils;
import com.kingteller.client.view.ConditionView;
import com.kingteller.client.view.KingTellerEditText.OnDialogClickLister;
import com.kingteller.client.view.ListViewObj;
import com.kingteller.client.view.XListView.IXListViewListener;
import com.kingteller.client.view.datatype.LoadingEnum;
import com.kingteller.client.view.toast.T;
import com.kingteller.framework.condition.Condition;
import com.kingteller.framework.http.AjaxParams;

import java.util.ArrayList;
import java.util.List;

public class SendOrderSearchListActivity extends Activity implements
		OnClickListener, IXListViewListener, OnItemClickListener {
	
	private Context mContext;
	private TextView title_text;
	private Button title_left_btn, title_righttwo_btn;
	
	
	private ConditionView workDateRange1;
	private ConditionView workDateRange2;
	private ConditionView workerName;
	private ConditionView wdsbjc;
	private ConditionView jqbh;
	private KingTellerDateTime kingTellerDateTime;
	private List<SendOrderBean> workOrderList = new ArrayList<SendOrderBean>();
	private SendOrderAdapter adapter;
	private int currentPage = 1;
	private ListViewObj listviewObj;
	

	@Override
	public void onCreate(Bundle paramBundle) {
		super.onCreate(paramBundle);
		setContentView(R.layout.layout_sendorder_search);
		KingTellerApplication.addActivity(this);
		
		if (findViewById(R.id.loading_view) != null) {
			setListviewObj(new ListViewObj(this));
		}
		
		mContext = SendOrderSearchListActivity.this;
		
		initUI();
		initData();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
		case KingTellerStaticConfig.SELECT_CXFWZRY:
			if (resultCode == RESULT_OK) {
				CommonSelectData cdata = (CommonSelectData) data.getSerializableExtra(CommonSelectDataActivity.DATA);
				workerName.setFieldTextAndValue(cdata.getText(), cdata.getValue());
			}
			break;
		default:
			break;
		}
	}
	
	private void initUI() {
		title_text = (TextView) findViewById(R.id.layout_main_text);
		title_left_btn = (Button) findViewById(R.id.layout_main_left_btn);
		title_righttwo_btn = (Button) findViewById(R.id.layout_main_righttwo_btn);
	

		workerName = (ConditionView) findViewById(R.id.workerName);
		wdsbjc = (ConditionView) findViewById(R.id.wdsbjc);
		jqbh = (ConditionView) findViewById(R.id.jqbh);
		workDateRange1 = (ConditionView) findViewById(R.id.workDateRange1);
		workDateRange2 = (ConditionView) findViewById(R.id.workDateRange2);
		

		kingTellerDateTime = new KingTellerDateTime().initNow();
		
		adapter = new SendOrderAdapter(this, workOrderList, "search");
		
		adapter.setCallBack(new Callback() {
			
			@Override
			public void onSuccess() {
				onRefresh();
			}
			
			@Override
			public void onFalse(String msg) {
				T.showShort(SendOrderSearchListActivity.this, msg);
			}
		});
		
		getListviewObj().getListview().setAdapter(adapter);
		getListviewObj().getListview().setRefreshEnabled(true);
		getListviewObj().getListview().setLoadMoreEnabled(true);
		getListviewObj().getListview().setOnRefreshListener(this);
		getListviewObj().getListview().setOnItemClickListener(this);
		getListviewObj().setStatus(LoadingEnum.NODATA, "请输入条件进行搜索");
		
		workerName.setOnDialogClickLister(new OnDialogClickLister() {
			@Override
			public void OnDialogClick() {
				// TODO Auto-generated method stub
				Intent intent = new Intent(SendOrderSearchListActivity.this, CommonSelectDataActivity.class);
				intent.putExtra(CommonSelectDataActivity.TYPE, R.array.cxfwzry);
				startActivityForResult(intent, KingTellerStaticConfig.SELECT_CXFWZRY);
			}
		});
		
		User user = User.getInfo(this);
		if(user.getRoleCode().contains("FWZFZR_COMMON")){
			workerName.setVisibility(View.GONE);
		}
	}

	private void initData() {
		title_text.setText("查询派单");
		title_left_btn.setBackgroundResource(R.drawable.btn_back_arrow);
		title_left_btn.setOnClickListener(this);
		title_righttwo_btn.setBackgroundResource(R.drawable.btn_serach);
		title_righttwo_btn.setOnClickListener(this);

		workDateRange1.setFieldTextAndValue(kingTellerDateTime.getDateString());
		workDateRange2.setFieldTextAndValue(kingTellerDateTime.getDateString());
	}

	public void getSendOrderDatas() {
		if (currentPage == 1) {
			getListviewObj().setStatus(LoadingEnum.LOADING);
		}
		KTHttpClient fh = new KTHttpClient(true);
		// 获取condition实例
		LinearLayout conditionLayout = (LinearLayout) findViewById(R.id.workorder_condition);
		Condition cond = ConditionUtils.getCondition(this, conditionLayout);
		
		AjaxParams params = ConditionUtils.getAjaxParams(cond, currentPage);
		params.put("workDateRange1", workDateRange1.getFieldValue());
		params.put("workDateRange2", workDateRange2.getFieldValue());
		params.put("workerName", workerName.getFieldValue());
		params.put("wdsbjc",wdsbjc.getFieldValue());
		params.put("jqbh", jqbh.getFieldValue());
		params.put("flag", "2");

		fh.post(KingTellerConfigUtils.CreateUrl(this, KingTellerUrlConfig.CxlspdUrl), params,
				new AjaxHttpCallBack<BasePageBean<SendOrderBean>>(this,
						new TypeToken<BasePageBean<SendOrderBean>>() {
						}.getType(), true) {

					@Override
					public void onFinish() {
						getListviewObj().getListview().stopRefresh();
						getListviewObj().getListview().stopLoadMore();
					}

					@Override
					public void onDo(BasePageBean<SendOrderBean> basePageBean) {
						List<SendOrderBean> data = basePageBean.getList();
						if (data.size() > 0) {
							if (basePageBean.getCurrentPage() == 1)
								adapter.setLists(data);
							else
								adapter.addLists(data);

							getListviewObj().setStatus(LoadingEnum.LISTSHOW);
							// 如果当前页等于总页数，则不需要先是load more按钮
							if (basePageBean.getCurrentPage() == basePageBean
									.getTotalPage())
								getListviewObj().getListview()
										.setLoadMoreEnabled(false);

						} else {
							getListviewObj().setStatus(LoadingEnum.NODATA,
									"没有数据");
						}

					};

				});

	}
	
	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub
		switch (view.getId()) {
		case R.id.layout_main_left_btn:
			finish();
			break;
		case R.id.layout_main_righttwo_btn:
			onRefresh();
			break;
		default:
			break;
		}
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onRefresh() {
		// TODO Auto-generated method stub
		currentPage = 1;
		getSendOrderDatas();
		getListviewObj().getListview().setLoadMoreEnabled(true);
	}

	@Override
	public void onLoadMore() {
		// TODO Auto-generated method stub
		currentPage++;
		getSendOrderDatas();
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

