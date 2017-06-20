package com.kingteller.client.activity.workorder.frament;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import com.google.gson.reflect.TypeToken;
import com.kingteller.R;
import com.kingteller.client.adapter.SendOrderAdapter;
import com.kingteller.client.adapter.SendOrderAdapter.Callback;
import com.kingteller.client.bean.common.BasePageBean;
import com.kingteller.client.bean.workorder.SendOrderBean;
import com.kingteller.client.config.KingTellerStaticConfig;
import com.kingteller.client.config.KingTellerUrlConfig;
import com.kingteller.client.netauth.AjaxHttpCallBack;
import com.kingteller.client.netauth.KTHttpClient;
import com.kingteller.client.utils.KingTellerConfigUtils;
import com.kingteller.client.view.ListViewObj;
import com.kingteller.client.view.XListView.IXListViewListener;
import com.kingteller.client.view.datatype.LoadingEnum;
import com.kingteller.client.view.toast.T;
import com.kingteller.framework.http.AjaxParams;

public class SendOrderFragment extends Fragment implements IXListViewListener {

	private View mContentView;
	private Context mContext;
	
	private ListViewObj listviewObj;
	private SendOrderAdapter sendOrderAdapter;
	private String tabName;
	
	private int currentPage;
	
	private List<SendOrderBean> sendOrderlist = new ArrayList<SendOrderBean>();
	
	private boolean isFirst = true;
	private boolean firstLoading = true;
	
	public SendOrderFragment(){}
	
	public SendOrderFragment getInstance(String tabName){
		SendOrderFragment wf = new SendOrderFragment();
		Bundle bun = new Bundle();
		bun.putString("tName", tabName);
		wf.setArguments(bun);
		return wf;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.tabName = getArguments().getString("tName");
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mContentView = inflater.inflate(R.layout.common_list_view, null);
		return mContentView;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		mContext = this.getActivity();
		currentPage = 1;
		
		if (getView().findViewById(R.id.loading_view) != null){
			setListviewObj(new ListViewObj(getView()));
		}
		
		initUI();
		initData();
	}

	private void initUI() {
		
		sendOrderAdapter = new SendOrderAdapter(mContext, sendOrderlist, tabName);
		sendOrderAdapter.setCallBack(new Callback() {
			
			@Override
			public void onSuccess() {
				isFirst = true;
				onRefresh();
			}
			
			@Override
			public void onFalse(String msg) {
				T.showShort(getActivity(), msg);
			}
		});

		getListviewObj().getListview().setAdapter(sendOrderAdapter);
		getListviewObj().getListview().setRefreshEnabled(true);
		getListviewObj().getListview().setLoadMoreEnabled(true);
		getListviewObj().getListview().setOnRefreshListener(this);

		getListviewObj().setOnClickLister(LoadingEnum.NODATA, res);
		getListviewObj().setOnClickLister(LoadingEnum.NETERROR, res);
	}

	private void initData() {
		getSendOrders(currentPage);
	}
	
	private OnClickListener res = new OnClickListener() {

		@Override
		public void onClick(View view) {
			isFirst = true;
			onRefresh();
		}
	};

	@Override
	public void onRefresh() {
		currentPage = 1;
		firstLoading = true;
		getSendOrders(currentPage);
	}

	@Override
	public void onLoadMore() {
		currentPage++;
		firstLoading = false;
		getSendOrders(currentPage);
	}
	
	@Override
	public void onResume() {
		super.onResume();
		
		if(KingTellerStaticConfig.SEND_REPORT_SUBMISSION_STATE_1 == true){
			if(tabName.equals("tab1")){
				isFirst = true;
				getSendOrders(currentPage);
				KingTellerStaticConfig.SEND_REPORT_SUBMISSION_STATE_1 = false;
			}
		}
	}
	
	public void getSendOrders(final int currentPage) {
		
		KTHttpClient fh = new KTHttpClient(true);
		AjaxParams params = new AjaxParams();
		if(tabName.equals("tab1")){
			params.put("flag", "0");
		}else if(tabName.equals("tab2")){
			params.put("flag", "1");
		}else if(tabName.equals("tab3")){
			params.put("flag", "3");
		}
		params.put("currentPage", ""+currentPage);

		fh.post(KingTellerConfigUtils.CreateUrl(mContext, KingTellerUrlConfig.PdlbUrl),
				params, new AjaxHttpCallBack<BasePageBean<SendOrderBean>>(mContext,
						new TypeToken<BasePageBean<SendOrderBean>>() {
						}.getType(), true) {
					
					@Override
					public void onStart() {
						if (isFirst == true){
							getListviewObj().setStatus(LoadingEnum.LOADING);
							isFirst = false;
						}
					}
			
					@Override
					public void onFinish() {
						getListviewObj().getListview().stopRefresh();
						getListviewObj().getListview().stopLoadMore();
					}
					
					@Override
					public void onError(int errorNo, String strMsg) {
								// TODO Auto-generated method stub
						super.onError(errorNo, strMsg);
						getListviewObj().setStatus(LoadingEnum.NETERROR, getString(R.string.net_error_try));
						isFirst = true;
					}
					
					@Override
					public void onDo(BasePageBean<SendOrderBean> data) {
						if("".equals(data.getStatus())){
							if(data.getList().size() > 0) {								
								if(currentPage == 1) {
									sendOrderlist = data.getList();
									sendOrderAdapter.setLists(sendOrderlist);
								}else {
									sendOrderlist.addAll(data.getList());
									sendOrderAdapter.notifyDataSetChanged();
									
								}
							
								getListviewObj().setStatus(LoadingEnum.LISTSHOW);
							}else {
								if(!firstLoading) {
									T.showShort(mContext, "没有更多数据");
								}else {
									getListviewObj().setStatus(LoadingEnum.NODATA, getString(R.string.no_data_try));
									isFirst = true;
								}
							}
							
						}else {
							getListviewObj().setStatus(LoadingEnum.NODATA, getString(R.string.no_data_try));
							isFirst = true;
						}
					};
				});
	}
	
	public ListViewObj getListviewObj() {
		return listviewObj;
	}

	public void setListviewObj(ListViewObj listviewObj) {
		this.listviewObj = listviewObj;
	}

}
