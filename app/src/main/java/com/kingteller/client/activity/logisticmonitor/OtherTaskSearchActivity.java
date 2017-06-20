package com.kingteller.client.activity.logisticmonitor;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.AdapterView.OnItemClickListener;

import com.google.gson.reflect.TypeToken;
import com.kingteller.R;
import com.kingteller.client.activity.base.BaseActivity;
import com.kingteller.client.adapter.OtherTaskAdapter;
import com.kingteller.client.bean.account.User;
import com.kingteller.client.bean.common.BasePageBean;
import com.kingteller.client.bean.common.CommonSelectData;
import com.kingteller.client.bean.common.KingTellerDateTime;
import com.kingteller.client.bean.logisticmonitor.OtherTaskBean;
import com.kingteller.client.config.KingTellerUrlConfig;
import com.kingteller.client.netauth.AjaxHttpCallBack;
import com.kingteller.client.netauth.KTHttpClient;
import com.kingteller.client.utils.CommonSelcetUtils;
import com.kingteller.client.utils.ConditionUtils;
import com.kingteller.client.utils.KingTellerConfigUtils;
import com.kingteller.client.view.ConditionView;
import com.kingteller.client.view.KingTellerEditText;
import com.kingteller.client.view.XListView.IXListViewListener;
import com.kingteller.client.view.datatype.LoadingEnum;
import com.kingteller.framework.condition.Condition;
import com.kingteller.framework.http.AjaxParams;

public class OtherTaskSearchActivity extends BaseActivity implements
OnClickListener, IXListViewListener, OnItemClickListener {

	private ConditionView cjsjRange1;
	private ConditionView cjsjRange2;
	private KingTellerDateTime kingTellerDateTime;
	private KingTellerEditText rwdzt;
	private OtherTaskAdapter otAdapter;
	private List<OtherTaskBean> otherTaskList = new ArrayList<OtherTaskBean>();
	private int currentPage = 1;
	private String opType="all";
	
	@Override
	public void onCreate(Bundle paramBundle) {
		// TODO Auto-generated method stub
		super.onCreate(paramBundle);
		setContentView(R.layout.layout_othertask_search);
		initUI();
		initData();
	}
	
	public void initUI(){
		title_title.setText("查询其他事务");
		title_right.setText("搜索");
		title_left.setOnClickListener(this);
		title_right.setOnClickListener(this);

		rwdzt = (KingTellerEditText) findViewById(R.id.rwdzt);
		cjsjRange1 = (ConditionView) findViewById(R.id.cjsjRange1);
		cjsjRange2 = (ConditionView) findViewById(R.id.cjsjRange2);
		otAdapter = new OtherTaskAdapter(this, otherTaskList);
		
		rwdzt.setOnChangeListener(new com.kingteller.client.view.KingTellerEditText.OnChangeListener() {

			@Override
			public void onChanged(CommonSelectData data) {
				
			}
				
		});
		
		rwdzt.setLists(CommonSelcetUtils
				.getSelectList(CommonSelcetUtils.swlbType));

		kingTellerDateTime = new KingTellerDateTime().initNow();
		getListviewObj().getListview().setAdapter(otAdapter);
		getListviewObj().getListview().setRefreshEnabled(true);
		getListviewObj().getListview().setLoadMoreEnabled(true);
		getListviewObj().getListview().setOnRefreshListener(this);
		getListviewObj().getListview().setOnItemClickListener(this);
		getListviewObj().setStatus(LoadingEnum.NODATA, "请输入条件进行搜索");
	}
	
	private void initData() {
		// TODO Auto-generated method stub
		cjsjRange1.setFieldTextAndValue(kingTellerDateTime.getDateString());
		cjsjRange2.setFieldTextAndValue(kingTellerDateTime.getDateString());
	}
	
	public void getOtherTaskOrder() {	
		if (currentPage == 1) {
			getListviewObj().setStatus(LoadingEnum.LOADING);
		}
		
		KTHttpClient fh = new KTHttpClient(true);
		// 获取condition实例
		LinearLayout conditionLayout = (LinearLayout) findViewById(R.id.othertask_condition);
		Condition cond = ConditionUtils.getCondition(this, conditionLayout);
		AjaxParams params = ConditionUtils.getAjaxParams(cond, currentPage);
		User user = User.getInfo(this);
		params.put("opType", opType);
		params.put("rwdzt", rwdzt.getFieldValue());
		params.put("userId", user.getUserId());
		
		fh.post(KingTellerConfigUtils.CreateUrl(this, KingTellerUrlConfig.WljkotUrl),
				params, new AjaxHttpCallBack<BasePageBean<OtherTaskBean>>(this,
						new TypeToken<BasePageBean<OtherTaskBean>>() {
						}.getType(), true) {
					
					@Override
					public void onFinish() {
						getListviewObj().getListview().stopRefresh();
						getListviewObj().getListview().stopLoadMore();
					}
					
					@Override
					public void onDo(BasePageBean<OtherTaskBean> basePageBean) {
						List<OtherTaskBean> data = basePageBean.getList();
						if (data.size() > 0) {
							if (basePageBean.getCurrentPage() == 1)
								otAdapter.setLists(data);
							else
								otAdapter.addLists(data);

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
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onRefresh() {
		// TODO Auto-generated method stub
		currentPage=1;
		getOtherTaskOrder();
		getListviewObj().getListview().setLoadMoreEnabled(true);
	}

	@Override
	public void onLoadMore() {
		// TODO Auto-generated method stub
		currentPage++;
		getOtherTaskOrder();
	}

	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub
		switch (view.getId()) {
		case R.id.button_left:
			finish();
			break;
		case R.id.button_right:
			onRefresh();
			break;
		default:
			break;
		}
	}
	
}
