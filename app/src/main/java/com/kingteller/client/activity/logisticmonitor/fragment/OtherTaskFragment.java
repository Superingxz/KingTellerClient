package com.kingteller.client.activity.logisticmonitor.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import com.google.gson.reflect.TypeToken;
import com.kingteller.R;
import com.kingteller.client.adapter.OtherTaskAdapter;
import com.kingteller.client.adapter.WriteOtherTaskAdapter;
import com.kingteller.client.bean.account.User;
import com.kingteller.client.bean.common.BasePageBean;
import com.kingteller.client.bean.logisticmonitor.OtherTaskBean;
import com.kingteller.client.config.KingTellerUrlConfig;
import com.kingteller.client.netauth.AjaxHttpCallBack;
import com.kingteller.client.netauth.KTHttpClient;
import com.kingteller.client.utils.KingTellerConfigUtils;
import com.kingteller.client.view.ListViewObj;
import com.kingteller.client.view.XListView.IXListViewListener;
import com.kingteller.client.view.datatype.LoadingEnum;
import com.kingteller.client.view.toast.T;
import com.kingteller.framework.http.AjaxParams;
import com.kingteller.framework.utils.StringUtil;

import java.util.ArrayList;
import java.util.List;

public class OtherTaskFragment extends Fragment implements IXListViewListener{

	private View view;
	private Context mContext;
	private OtherTaskAdapter otherTaskAdapter;
	private WriteOtherTaskAdapter writeOtherTaskAdapter;
	private List<OtherTaskBean> otherTaskList = new ArrayList<OtherTaskBean>();
	private String optType;
	
	private ListViewObj listviewObj;
	
	public OtherTaskFragment(){
		
	}
	
	@SuppressLint("ValidFragment")
	public OtherTaskFragment(String optType) {
		this.optType = optType;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
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
		if(StringUtil.equals(optType, "swlb_fh")){
			writeOtherTaskAdapter = new WriteOtherTaskAdapter(mContext, otherTaskList);
			getListviewObj().getListview().setAdapter(writeOtherTaskAdapter);
		}else{
			otherTaskAdapter = new OtherTaskAdapter(mContext, otherTaskList);
			getListviewObj().getListview().setAdapter(otherTaskAdapter);
		}
		
		getListviewObj().getListview().setRefreshEnabled(true);
		getListviewObj().getListview().setLoadMoreEnabled(false);
		getListviewObj().getListview().setOnRefreshListener(this);

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
		getOtherTaskOrder();
	}

	public void getOtherTaskOrder() {	
		
		User user = User.getInfo(mContext);

		KTHttpClient fh = new KTHttpClient(true);
		AjaxParams params = new AjaxParams();
		params.put("userId", user.getUserId());
		params.put("optType", optType);
		
		fh.post(KingTellerConfigUtils.CreateUrl(mContext, KingTellerUrlConfig.WljkotUrl),
				params, new AjaxHttpCallBack<BasePageBean<OtherTaskBean>>(mContext,
						new TypeToken<BasePageBean<OtherTaskBean>>() {
						}.getType(), true) {
					
					@Override
					public void onFinish() {
						getListviewObj().getListview().stopRefresh();
					}
					
					@Override
					public void onDo(BasePageBean<OtherTaskBean> data) {
						if("".equals(data.getStatus()) ){
							if (data.getList().size() > 0) {
								otherTaskList = data.getList();
								if(StringUtil.equals(optType, "swlb_fh")){
									writeOtherTaskAdapter.setLists(data.getList());
								}else{
									otherTaskAdapter.setLists(data.getList());
								}
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
		getOtherTaskOrder();
		
	}

	@Override
	public void onLoadMore() {
		// TODO Auto-generated method stub
		
	}

	
	public ListViewObj getListviewObj() {
		return listviewObj;
	}

	public void setListviewObj(ListViewObj listviewObj) {
		this.listviewObj = listviewObj;
	}
}
