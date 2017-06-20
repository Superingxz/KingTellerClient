package com.kingteller.client.activity.logisticmonitor.fragment;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

import com.google.gson.reflect.TypeToken;
import com.kingteller.R;
import com.kingteller.client.activity.base.BaseFragment;
import com.kingteller.client.adapter.LogisticOrderAdapter;
import com.kingteller.client.bean.account.User;
import com.kingteller.client.bean.common.BasePageBean;
import com.kingteller.client.bean.logisticmonitor.LogisticOrderBean;
import com.kingteller.client.config.KingTellerUrlConfig;
import com.kingteller.client.netauth.AjaxHttpCallBack;
import com.kingteller.client.netauth.KTHttpClient;
import com.kingteller.client.utils.KingTellerConfigUtils;
import com.kingteller.client.view.ListViewObj;
import com.kingteller.client.view.XListView.IXListViewListener;
import com.kingteller.client.view.datatype.LoadingEnum;
import com.kingteller.client.view.toast.T;
import com.kingteller.framework.http.AjaxParams;

public class LogisticOrderFragment extends Fragment implements IXListViewListener, OnItemClickListener {

	private View view;
	private Context mContext;
	private LogisticOrderAdapter logisticOrderAdapter;
	private String optType;
	private List<LogisticOrderBean> logisticOrderlist = new ArrayList<LogisticOrderBean>();
	
	private ListViewObj listviewObj;
	
	public LogisticOrderFragment(){}
	
	public LogisticOrderFragment getInstance(String optType){
		LogisticOrderFragment wf = new LogisticOrderFragment();
		Bundle bun = new Bundle();
		bun.putString("optype", optType);
		wf.setArguments(bun);
		return wf;
	}

	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.optType = getArguments().getString("optype");
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view = inflater.inflate(R.layout.common_list_view, container, false);
		mContext = inflater.getContext();
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		if (getView().findViewById(R.id.loading_view) != null)
			setListviewObj(new ListViewObj(getView()));
		initUI();
		initData();

	}

	private void initUI() {
		
		logisticOrderAdapter = new LogisticOrderAdapter(mContext, logisticOrderlist,optType);
		getListviewObj().getListview().setAdapter(logisticOrderAdapter);
		getListviewObj().getListview().setRefreshEnabled(true);
		getListviewObj().getListview().setLoadMoreEnabled(false);
		getListviewObj().getListview().setOnRefreshListener(this);
		getListviewObj().getListview().setOnItemClickListener(this);

		getListviewObj().setOnClickLister(LoadingEnum.NODATA, res);
		getListviewObj().setOnClickLister(LoadingEnum.NETERROR, res);

	}

	private OnClickListener res = new OnClickListener() {

		@Override
		public void onClick(View view) {
			// TODO Auto-generated method stub
			getListviewObj().setStatus(LoadingEnum.LOADING);
			onRefresh();
		}
	};

	private void initData() {
		getLogisticOrders();
	}
	
	public void getLogisticOrders() {	
		
		KTHttpClient fh = new KTHttpClient(true);
		AjaxParams params = new AjaxParams();
		if(optType.equals("query")){
			params.put("type", "query");
		}else if(optType.equals("fill")){
			params.put("type", "fill");
		}
		params.put("userId", User.getInfo(mContext).getUserId());
		
		fh.post(KingTellerConfigUtils.CreateUrl(mContext, KingTellerUrlConfig.WljklbUrl),
				params, new AjaxHttpCallBack<BasePageBean<LogisticOrderBean>>(mContext,
						new TypeToken<BasePageBean<LogisticOrderBean>>() {
						}.getType(), true) {
					
					@Override
					public void onFinish() {
						getListviewObj().getListview().stopRefresh();
					}
					
					@Override
					public void onDo(BasePageBean<LogisticOrderBean> data) {
						if("".equals(data.getStatus()) ){
							if (data.getList().size() > 0) {
								logisticOrderlist = data.getList();
								logisticOrderAdapter.setLists(data.getList());
								getListviewObj().setStatus(LoadingEnum.LISTSHOW);
							} else {
								getListviewObj().setStatus(LoadingEnum.NODATA,getString(R.string.no_data_try));
							}
						}else{
							T.showShort(mContext, data.getMsg());
						}
					};
				});
	}
	
	@Override
	public void onRefresh() {
		// TODO Auto-generated method stub
		getLogisticOrders();
	}

	@Override
	public void onLoadMore() {
		// TODO Auto-generated method stub
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
	}
	
	
	public ListViewObj getListviewObj() {
		return listviewObj;
	}

	public void setListviewObj(ListViewObj listviewObj) {
		this.listviewObj = listviewObj;
	}
}
