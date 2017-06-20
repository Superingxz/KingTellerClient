package com.kingteller.client.view;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kingteller.R;
import com.kingteller.client.adapter.FloatWaitDoAdapter;
import com.kingteller.client.bean.account.User;
import com.kingteller.client.bean.account.WaitDoBean;
import com.kingteller.client.config.KingTellerStaticConfig;
import com.kingteller.client.config.KingTellerUrlConfig;
import com.kingteller.client.netauth.AjaxHttpCallBack;
import com.kingteller.client.netauth.KTHttpClient;
import com.kingteller.client.utils.KingTellerFileUtils;
import com.kingteller.client.utils.KingTellerConfigUtils;
import com.kingteller.client.utils.KingTellerJudgeUtils;
import com.kingteller.client.view.datatype.LoadingEnum;
import com.kingteller.framework.http.AjaxParams;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

public class FloatWindowView extends LinearLayout {

	private ListViewObj listviewObj;
	private FloatWaitDoAdapter adpater;
	private Gson gson;

	public FloatWindowView(Context context) {
		super(context);
		LayoutInflater.from(context).inflate(R.layout.layout_float_window, this);

		initUI();
		initData();
	}

	private void initUI() {
		listviewObj = new ListViewObj(this);
		adpater = new FloatWaitDoAdapter(getContext(),new ArrayList<WaitDoBean>());
		listviewObj.getListview().setAdapter(adpater);
		listviewObj.getListview().setRefreshEnabled(false);
		listviewObj.getListview().setLoadMoreEnabled(false);

		findViewById(R.id.refresh).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				// TODO Auto-generated method stub
				setVisibility(GONE);
			}
		});

	}

	private void initData() {
		gson = new Gson();

		if (KingTellerFileUtils.FileExistName("waitdo")) {
			String data = KingTellerFileUtils.readFile("waitdo");
			if (!KingTellerJudgeUtils.isEmpty(data)) {
				try {
					List<WaitDoBean> list = gson.fromJson(data,
							new TypeToken<List<WaitDoBean>>() {
							}.getType());
					adpater.setLists(list);

					if (list.size() > 0)
						listviewObj.setStatus(LoadingEnum.LISTSHOW);
					else
						listviewObj.setStatus(LoadingEnum.NODATA);
				} catch (Exception e) {
					// TODO: handle exception
					listviewObj.setStatus(LoadingEnum.NODATA);
				}

			} else {
				listviewObj.setStatus(LoadingEnum.NODATA);
			}

		} else {

		}
	}

	public void getWaitDos() {
		// TODO Auto-generated method stub
		User user = User.getInfo(getContext());
		KTHttpClient fh = new KTHttpClient(true);
		AjaxParams params = new AjaxParams();
		params.put("userAccount", user.getUserName());
		params.put("orgId", user.getOrgId());
		params.put("roleCode", user.getRoleCode());
		params.put("userId", user.getUserId());

		fh.post(KingTellerConfigUtils.CreateUrl(getContext(), KingTellerUrlConfig.WaitDoUrl),
				params, new AjaxHttpCallBack<List<WaitDoBean>>(
						new TypeToken<List<WaitDoBean>>() {
						}.getType(), true) {

					@Override
					public void onSuccess(String response) {
						// TODO Auto-generated method stub

						super.onSuccess(response);
						try {
							KingTellerFileUtils.writeFile("waitdo", response);
						} catch (Exception e) {
							// TODO: handle exception
						}
					}

					@Override
					public void onDo(List<WaitDoBean> data) {
						adpater.setLists(data);
						if (data.size() > 0) {
							listviewObj.setStatus(LoadingEnum.LISTSHOW);
						} else {
							listviewObj.setStatus(LoadingEnum.NODATA);
						}
					};

				});

	}

}
