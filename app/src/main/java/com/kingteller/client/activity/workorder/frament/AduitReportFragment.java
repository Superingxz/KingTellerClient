package com.kingteller.client.activity.workorder.frament;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import com.google.gson.reflect.TypeToken;
import com.kingteller.R;
import com.kingteller.client.activity.workorder.BaseReportActivity;
import com.kingteller.client.adapter.AduitReportAdapter;
import com.kingteller.client.bean.common.BasePageBean;
import com.kingteller.client.bean.workorder.AduitReportBean;
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

public class AduitReportFragment extends Fragment implements IXListViewListener {

	private View mContentView;
	private Context mContext;
	
	private ListViewObj listviewObj;
	private AduitReportAdapter aduitAdapter;
	private String executionState;
	
	private List<AduitReportBean> aduitlist = new ArrayList<AduitReportBean>();
	
	private boolean isFirst = true;
	
	public AduitReportFragment() {
	}

	public AduitReportFragment getInstance(String executionState) {
		AduitReportFragment af = new AduitReportFragment();
		Bundle bun = new Bundle();
		bun.putString("executionState", executionState);
		af.setArguments(bun);
		return af;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.executionState = getArguments().getString("executionState");
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
		
		if (getView().findViewById(R.id.loading_view) != null){
			setListviewObj(new ListViewObj(getView()));
		}
		
		initUI();
		initData();
	}

	private void initUI() {
		aduitAdapter = new AduitReportAdapter(mContext, aduitlist, executionState);

		getListviewObj().getListview().setAdapter(aduitAdapter);
		getListviewObj().getListview().setRefreshEnabled(true);
		getListviewObj().getListview().setLoadMoreEnabled(false);
		getListviewObj().getListview().setOnRefreshListener(this);

		getListviewObj().setOnClickLister(LoadingEnum.NODATA, res);
		getListviewObj().setOnClickLister(LoadingEnum.NETERROR, res);
	}

	private OnClickListener res = new OnClickListener() {

		@Override
		public void onClick(View view) {
			isFirst = true;
			onRefresh();
		}
	};

	private void initData() {
		getAduits();
	}

	@Override
	public void onRefresh() {
		getAduits();
	}

	@Override
	public void onLoadMore() {

	}
	
	@Override
	public void onResume() {
		super.onResume();
		
		if(KingTellerStaticConfig.AUDIT_REPORT_SUBMISSION_STATE_1 == true){
			if(BaseReportActivity.OPT_UNTREATED.equals(executionState)){
				isFirst = true;
				getAduits();
				KingTellerStaticConfig.AUDIT_REPORT_SUBMISSION_STATE_1 = false;
			}
		}
		
		if(KingTellerStaticConfig.AUDIT_REPORT_SUBMISSION_STATE_2 == true){
			if(BaseReportActivity.OPT_PROCESSED.equals(executionState)){
				isFirst = true;
				getAduits();
				KingTellerStaticConfig.AUDIT_REPORT_SUBMISSION_STATE_2 = false;
			}
		}
	}
	
	public void getAduits() {

		KTHttpClient fh = new KTHttpClient(true);
		AjaxParams params = new AjaxParams();
		
		if(BaseReportActivity.OPT_UNTREATED.equals(executionState)){	
			params.put("flag", "0");
		}else if(BaseReportActivity.OPT_PROCESSED.equals(executionState)){
			params.put("flag", "1");
		}

		fh.post(KingTellerConfigUtils.CreateUrl(mContext, KingTellerUrlConfig.WebSpbgUrl),
				params, new AjaxHttpCallBack<BasePageBean<AduitReportBean>>(mContext,
						new TypeToken<BasePageBean<AduitReportBean>>() {
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
					}

					@Override
					public void onDo(BasePageBean<AduitReportBean> data) {
						
						if("".equals(data.getStatus()) && data.getList().size() > 0){
							aduitlist = data.getList();
							aduitAdapter.setLists(data.getList());
							getListviewObj().setStatus(LoadingEnum.LISTSHOW);
						}else{
							getListviewObj().setStatus(LoadingEnum.NODATA, getString(R.string.no_data_try));
							isFirst = true;
						}
					};
				});
	}
	
	public List<AduitReportBean> getAduitCheckedtList(){
		return aduitAdapter.getAduitCheckedList();
	}

	public ListViewObj getListviewObj() {
		return listviewObj;
	}

	public void setListviewObj(ListViewObj listviewObj) {
		this.listviewObj = listviewObj;
	}
}
