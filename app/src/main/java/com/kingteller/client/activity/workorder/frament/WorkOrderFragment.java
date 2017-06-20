package com.kingteller.client.activity.workorder.frament;

import java.util.ArrayList;
import java.util.Iterator;
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
import com.kingteller.client.adapter.WorkOrderAdapter;
import com.kingteller.client.adapter.WorkOrderAdapter.Callback;
import com.kingteller.client.bean.common.BasePageBean;
import com.kingteller.client.bean.workorder.WorkOrderBean;
import com.kingteller.client.config.KingTellerStaticConfig;
import com.kingteller.client.config.KingTellerUrlConfig;
import com.kingteller.client.netauth.AjaxHttpCallBack;
import com.kingteller.client.netauth.KTHttpClient;
import com.kingteller.client.utils.KingTellerConfigUtils;
import com.kingteller.client.view.ListViewObj;
import com.kingteller.client.view.XListView.IXListViewListener;
import com.kingteller.client.view.datatype.LoadingEnum;
import com.kingteller.client.view.edittext.LoginEditTextView;
import com.kingteller.client.view.toast.T;
import com.kingteller.framework.http.AjaxParams;

public class WorkOrderFragment extends Fragment implements IXListViewListener {

	private View mContentView;
	private Context mContext;
	
	private ListViewObj listviewObj;
	private LoginEditTextView searchEdit;
	private WorkOrderAdapter workOrderAdapter;
	private String tabName;
	
	private int currentPage;
	
	private List<WorkOrderBean> workOrderlist = new ArrayList<WorkOrderBean>();
	
	private boolean isFirst = true;
	private boolean firstLoading = true;
	
	public WorkOrderFragment getInstance(String tabName){
		WorkOrderFragment wf = new WorkOrderFragment();
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
//		getView().findViewById(R.id.work_order_list_search).setVisibility(View.VISIBLE);
//		searchEdit = (LoginEditTextView) getView().findViewById(R.id.work_order_list_search_gddh);
//		
//		searchEdit.addTextChangedListener(new TextWatcher() {
//			@Override
//			public void onTextChanged(CharSequence s, int start, int before, int count) {
//			}
//			@Override
//			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//			}
//			@Override
//			public void afterTextChanged(final Editable s) {
//				new Handler().postDelayed(new Runnable() {
//		            @Override
//		            public void run() {
//						if("".equals(s.toString())){
//							workOrderAdapter.setLists(workOrderlist);
//						}else{
//							List<WorkOrderBean> newdata = new ArrayList<WorkOrderBean>();
//							for(int i = 0; i < workOrderlist.size(); i++ ){
//								if(workOrderlist.get(i).getOrderNo().contains(s.toString())){
//									newdata.add(workOrderlist.get(i));
//								}
//							}
//							workOrderAdapter.setLists(newdata);
//						}
//		            }
//		        }, 1000);
//			}
//		});
		
		
		workOrderlist = new ArrayList<WorkOrderBean>();
		workOrderAdapter = new WorkOrderAdapter(mContext, workOrderlist, tabName);
		workOrderAdapter.setCallBack(new Callback() {
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
		
		getListviewObj().getListview().setAdapter(workOrderAdapter);
		getListviewObj().getListview().setRefreshEnabled(true);
		getListviewObj().getListview().setLoadMoreEnabled(true);
		getListviewObj().getListview().setOnRefreshListener(this);

		getListviewObj().setOnClickLister(LoadingEnum.NODATA, res);
		getListviewObj().setOnClickLister(LoadingEnum.NETERROR, res);
		
	}
	
	private void initData() {
		onRefresh();
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
		getWorkOrders(currentPage);
	}

	@Override
	public void onLoadMore() {
		currentPage++;
		firstLoading = false;
		getWorkOrders(currentPage);
	}
	
	@Override
	public void onResume() {
		super.onResume();
		
//		if(KingTellerStaticConfig.WRITE_REPORT_SUBMISSION_STATE_1 == true){
//			if(tabName.equals("tab1")){
//				getWorkOrders();
//				KingTellerStaticConfig.WRITE_REPORT_SUBMISSION_STATE_1 = false;
//			}
//		}
		
		if(KingTellerStaticConfig.WRITE_REPORT_SUBMISSION_STATE_2 == true){
			if(tabName.equals("tab2")){
				isFirst = true;
				getWorkOrders(currentPage);
				KingTellerStaticConfig.WRITE_REPORT_SUBMISSION_STATE_2 = false;
			}
		}
		
		if(KingTellerStaticConfig.WRITE_REPORT_SUBMISSION_STATE_3 == true){
			if(tabName.equals("tab3")){
				isFirst = true;
				getWorkOrders(currentPage);
				KingTellerStaticConfig.WRITE_REPORT_SUBMISSION_STATE_3 = false;
			}
		}
	}
	
	public void getWorkOrders(final int currentPage) {

		KTHttpClient fh = new KTHttpClient(true);
		AjaxParams params = new AjaxParams();
		if(tabName.equals("tab1")){
			params.put("statuflag", "1");
		}else if(tabName.equals("tab2")){
			params.put("statuflag", "4");
		}else if(tabName.equals("tab3")){
			params.put("statuflag", "6");
		}
		params.put("currentPage", ""+currentPage);

		fh.post(KingTellerConfigUtils.CreateUrl(mContext, KingTellerUrlConfig.WebRwdUrl),
				params, new AjaxHttpCallBack<BasePageBean<WorkOrderBean>>(mContext,
						new TypeToken<BasePageBean<WorkOrderBean>>() {
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
						super.onError(errorNo, strMsg);
						getListviewObj().setStatus(LoadingEnum.NETERROR);
						T.showShort(mContext, "数据访问超时!");
					}
					
					@Override
					public void onDo(BasePageBean<WorkOrderBean> data) {
						if("".equals(data.getStatus())) {
							if(data.getList().size() > 0){
//								if("".equals(searchEdit.getText().toString())){
//									workOrderAdapter.setLists(workOrderlist);
//								}else{
//									List<WorkOrderBean> newdata = new ArrayList<WorkOrderBean>();
//									for(int i = 0; i < workOrderlist.size(); i++ ){
//										if(workOrderlist.get(i).getOrderNo().contains(searchEdit.getText().toString())){
//											newdata.add(workOrderlist.get(i));
//										}
//									}
//									workOrderAdapter.setLists(newdata);
//								}
								if(currentPage == 1) {
									workOrderlist = sortList(data.getList());
									workOrderAdapter.setLists(workOrderlist);
								}else {
									workOrderlist.addAll(sortList(data.getList()));
									//workOrderAdapter.addLists(sortList(data.getList()));
									workOrderAdapter.notifyDataSetChanged();
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
						}else{
							getListviewObj().setStatus(LoadingEnum.NODATA, getString(R.string.no_data_try));
							isFirst = true;
						}
					};
				});
	}

	/**
	 * 把维修工单提前排序
	 * @param list
	 * @return
	 */
	private List<WorkOrderBean> sortList(List<WorkOrderBean> list) {
		List<WorkOrderBean> newList = new ArrayList<>();
		Iterator<WorkOrderBean> iter = list.iterator();
		while(iter.hasNext()) {
			WorkOrderBean workOrder = iter.next();
			if("H".equals(workOrder.getCreateType()) && "maintenance".equals(workOrder.getOrderType())) {
				newList.add(workOrder);
				iter.remove();
			}
		}
		newList.addAll(list);
		Log.e("num----", newList.size()+"");
		return newList;
	}
	
	public ListViewObj getListviewObj() {
		return listviewObj;
	}

	public void setListviewObj(ListViewObj listviewObj) {
		this.listviewObj = listviewObj;
	}
}
