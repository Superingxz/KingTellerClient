package com.kingteller.client.activity.workorder;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;

import com.google.gson.reflect.TypeToken;
import com.kingteller.R;
import com.kingteller.client.KingTellerApplication;
import com.kingteller.client.adapter.WorkOrderAdapter;
import com.kingteller.client.adapter.WorkOrderAdapter.Callback;
import com.kingteller.client.bean.common.BasePageBean;
import com.kingteller.client.bean.common.KingTellerDateTime;
import com.kingteller.client.bean.map.StaffSearchCondition;
import com.kingteller.client.bean.workorder.WorkOrderBean;
import com.kingteller.client.config.KingTellerUrlConfig;
import com.kingteller.client.netauth.AjaxHttpCallBack;
import com.kingteller.client.netauth.KTHttpClient;
import com.kingteller.client.utils.CommonSelcetUtils;
import com.kingteller.client.utils.ConditionUtils;
import com.kingteller.client.utils.KingTellerConfigUtils;
import com.kingteller.client.utils.KingTellerTimeUtils;
import com.kingteller.client.view.ConditionView;
import com.kingteller.client.view.ListViewObj;
import com.kingteller.client.view.XListView.IXListViewListener;
import com.kingteller.client.view.datatype.LoadingEnum;
import com.kingteller.client.view.toast.T;
import com.kingteller.framework.condition.Condition;
import com.kingteller.framework.http.AjaxParams;

/**
 * 查询工单
 * @author jinyh
 * */

public class WorkOrderSearchActivity extends Activity implements
		OnClickListener, IXListViewListener, OnItemClickListener {
	private ConditionView startDate;
	private ConditionView endDate;
	private ConditionView orderNo;
	private ConditionView ordertype;
	private List<WorkOrderBean> workOrderList = new ArrayList<WorkOrderBean>();
	private WorkOrderAdapter adapter;
	private int currentPage = 1;
	private ListViewObj listviewObj;

	private TextView title_text;
	private Button title_left_btn, title_righttwo_btn;
	
	@Override
	public void onCreate(Bundle paramBundle) {
		super.onCreate(paramBundle);
		setContentView(R.layout.layout_workorder_search);
		KingTellerApplication.addActivity(this);
		
		if (findViewById(R.id.loading_view) != null) {
			setListviewObj(new ListViewObj(this));
		}

		initUI();
		initData();
	}

	private void initUI() {
		title_text = (TextView) findViewById(R.id.layout_main_text);
		title_left_btn = (Button) findViewById(R.id.layout_main_left_btn);
		title_righttwo_btn = (Button) findViewById(R.id.layout_main_righttwo_btn);

		orderNo = (ConditionView) findViewById(R.id.orderNo);
		startDate = (ConditionView) findViewById(R.id.start_date);
		endDate = (ConditionView) findViewById(R.id.end_date);
		ordertype = (ConditionView) findViewById(R.id.ordertype);
		adapter = new WorkOrderAdapter(this, workOrderList,"");
		adapter.setCallBack(new Callback() {
			@Override
			public void onSuccess() {
				setResult(RESULT_OK);
				onRefresh();
			}
			
			@Override
			public void onFalse(String msg) {
				T.showShort(WorkOrderSearchActivity.this, msg);
			}
		});

		getListviewObj().getListview().setAdapter(adapter);
		getListviewObj().getListview().setRefreshEnabled(true);
		getListviewObj().getListview().setLoadMoreEnabled(true);
		getListviewObj().getListview().setOnRefreshListener(this);
		getListviewObj().getListview().setOnItemClickListener(this);
		getListviewObj().setStatus(LoadingEnum.NODATA, "请输入条件进行搜索");
	}
	
	private void initData() {
		title_text.setText("查询工单");
		title_left_btn.setBackgroundResource(R.drawable.btn_back_arrow);
		title_left_btn.setOnClickListener(this);
		title_righttwo_btn.setBackgroundResource(R.drawable.btn_serach);
		title_righttwo_btn.setOnClickListener(this);
		
		ordertype.setLists(CommonSelcetUtils.getSelectList(CommonSelcetUtils.ordertypeType));
		
		String nowday = KingTellerTimeUtils.getNowDayAndTime(0);
		startDate.setFieldTextAndValue(KingTellerTimeUtils.getNextDay(nowday, "-30"));
		endDate.setFieldTextAndValue(nowday);
	}

	public void getWorkOrderData() {
		if (currentPage == 1) {
			getListviewObj().setStatus(LoadingEnum.LOADING);
		}
		KTHttpClient fh = new KTHttpClient(true);
		// 获取condition实例
		LinearLayout conditionLayout = (LinearLayout) findViewById(R.id.workorder_condition);
		Condition cond = ConditionUtils.getCondition(this, conditionLayout);
		AjaxParams params = ConditionUtils.getAjaxParams(cond, currentPage);
		params.put("workDateRange1", startDate.getFieldValue());
		params.put("workDateRange2", endDate.getFieldValue());
		params.put("orderNo", orderNo.getFieldValue());
		params.put("statuflag", "5");
		params.put("ordertype", ordertype.getFieldValue());

		fh.post(KingTellerConfigUtils.CreateUrl(this, KingTellerUrlConfig.WebRwdUrl), params,
				new AjaxHttpCallBack<BasePageBean<WorkOrderBean>>(this,
						new TypeToken<BasePageBean<WorkOrderBean>>() {
						}.getType(), true) {

					@Override
					public void onFinish() {
						getListviewObj().getListview().stopRefresh();
						getListviewObj().getListview().stopLoadMore();
					}

					@Override
					public void onDo(BasePageBean<WorkOrderBean> basePageBean) {
						List<WorkOrderBean> data = basePageBean.getList();
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
							getListviewObj().setStatus(LoadingEnum.NODATA,"没有数据");
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
		currentPage=1;
		getWorkOrderData();
		getListviewObj().getListview().setLoadMoreEnabled(true);
	}

	@Override
	public void onLoadMore() {
		// TODO Auto-generated method stub
		currentPage++;
		getWorkOrderData();
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if(requestCode == 1) {
			if(resultCode == RESULT_OK) {
				Log.e("baogao-----", "RESULT_OK");
				onRefresh();
			}else {
				Log.e("baogao-----", "RESULT_CANCELED");
			}
		}
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
