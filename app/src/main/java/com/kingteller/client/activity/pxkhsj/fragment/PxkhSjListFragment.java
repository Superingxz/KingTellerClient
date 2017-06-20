package com.kingteller.client.activity.pxkhsj.fragment;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.reflect.TypeToken;
import com.kingteller.R;
import com.kingteller.client.activity.base.BaseFragment;
import com.kingteller.client.adapter.AllSjpaperListAdapter;
import com.kingteller.client.bean.account.User;
import com.kingteller.client.bean.common.BasePageBean;
import com.kingteller.client.bean.pxkh.SjPaperBean;
import com.kingteller.client.config.KingTellerUrlConfig;
import com.kingteller.client.netauth.AjaxHttpCallBack;
import com.kingteller.client.netauth.KTHttpClient;
import com.kingteller.client.utils.KingTellerConfigUtils;
import com.kingteller.client.utils.KingTellerJudgeUtils;
import com.kingteller.client.view.XListView.IXListViewListener;
import com.kingteller.client.view.datatype.LoadingEnum;
import com.kingteller.client.view.toast.T;
import com.kingteller.framework.http.AjaxParams;

public class PxkhSjListFragment extends BaseFragment implements
		IXListViewListener {
	private View view;
	private Context mContext;
	private AllSjpaperListAdapter sjListAdapter;
	private List<SjPaperBean> sjPaperList = new ArrayList<SjPaperBean>();

	@Override
	public void onRefresh() {
		getAllSjPaperListDatas();

	}

	@Override
	public void onLoadMore() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.common_list_view, container, false);
		mContext = inflater.getContext();
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		initUI();
		initData();
	}

	private void initUI() {

		sjListAdapter = new AllSjpaperListAdapter(mContext, sjPaperList);
		getListviewObj().getListview().setAdapter(sjListAdapter);
		getListviewObj().getListview().setRefreshEnabled(true);
		getListviewObj().getListview().setLoadMoreEnabled(false);
		getListviewObj().getListview().setOnRefreshListener(this);

		getListviewObj().setOnClickLister(LoadingEnum.NODATA, res);
		getListviewObj().setOnClickLister(LoadingEnum.NETERROR, res);

	}

	private OnClickListener res = new OnClickListener() {

		@Override
		public void onClick(View view) {
			getListviewObj().setStatus(LoadingEnum.LOADING);
			onRefresh();
		}
	};
	
	private void initData() {
		getAllSjPaperListDatas();
	}

	public void getAllSjPaperListDatas() {

		if (!KingTellerJudgeUtils.isNetAvaliable(getActivity())) {
			T.showShort(mContext, "没有网络，请检查网络是否可用!");
			return;
		}

		User user = User.getInfo(mContext);

		KTHttpClient fh = new KTHttpClient(true);
		AjaxParams params = new AjaxParams();
		params.put("userId", user.getUserId());

		fh.post(KingTellerConfigUtils.CreateUrl(mContext, KingTellerUrlConfig.sjlistUrl),
				params, new AjaxHttpCallBack<BasePageBean<SjPaperBean>>(mContext, 
						new TypeToken<BasePageBean<SjPaperBean>>() {
						}.getType(), true) {

					@Override
					public void onFinish() {
						getListviewObj().getListview().stopRefresh();
					}

					@Override
					public void onDo(BasePageBean<SjPaperBean> data) {
						if ("".equals(data.getStatus())) {
							if (data.getList().size() > 0) {
								sjPaperList = data.getList();
								sjListAdapter.setLists(data.getList());
								getListviewObj().setStatus(LoadingEnum.LISTSHOW);
							} else {
								getListviewObj().setStatus(LoadingEnum.NODATA,getString(R.string.no_data_try));
							}
						} else {
							T.showShort(mContext, data.getMsg());
						}
					};
				});
	}
}
