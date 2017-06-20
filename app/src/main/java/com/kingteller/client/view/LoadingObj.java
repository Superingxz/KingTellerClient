package com.kingteller.client.view;

import com.kingteller.R;
import com.kingteller.client.utils.KingTellerJudgeUtils;
import com.kingteller.client.view.datatype.LoadingEnum;

import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 通用加载对象
 * @author 王定波
 *
 */
public class LoadingObj {
	private LinearLayout layout_data_loading;
	private LinearLayout layout_no_data;
	private LinearLayout layout_net_error;
	private LinearLayout layout_tip;
	
	private TextView text_data_loading;
	private TextView text_no_data;
	private TextView text_net_error;
	private TextView text_tip;
	protected View loading_view;
	private Activity activity;
	
	
	public LoadingObj(Activity activity) {

		loading_view = activity.findViewById(R.id.loading_view);
		
		layout_data_loading = (LinearLayout) activity.findViewById(R.id.layout_data_loading);
		layout_no_data = (LinearLayout) activity.findViewById(R.id.layout_no_data);
		layout_net_error = (LinearLayout) activity.findViewById(R.id.layout_net_error);
		layout_tip = (LinearLayout) activity.findViewById(R.id.layout_tip);
		
		text_data_loading = (TextView) activity.findViewById(R.id.text_data_loading);
		text_no_data = (TextView) activity.findViewById(R.id.text_no_data);
		text_net_error = (TextView) activity.findViewById(R.id.text_net_error);
		text_tip = (TextView) activity.findViewById(R.id.text_tip);
		this.activity = activity;
		setStatus(LoadingEnum.LOADING, null);
	}

	public LoadingObj(View view) {

		loading_view = view.findViewById(R.id.loading_view);
		layout_data_loading = (LinearLayout) view.findViewById(R.id.layout_data_loading);

		layout_no_data = (LinearLayout) view.findViewById(R.id.layout_no_data);
		layout_net_error = (LinearLayout) view.findViewById(R.id.layout_net_error);
		layout_tip = (LinearLayout) view.findViewById(R.id.layout_tip);

		text_data_loading = (TextView) view.findViewById(R.id.text_data_loading);
		text_no_data = (TextView) view.findViewById(R.id.text_no_data);
		text_net_error = (TextView) view.findViewById(R.id.text_net_error);
		text_tip = (TextView) view.findViewById(R.id.text_tip);
		
		setStatus(LoadingEnum.LOADING);
	}

	public void setVisibility(int visibility) {
		loading_view.setVisibility(visibility);
	}

	public void setBackground(boolean istm) {
		if (istm)
			loading_view.setBackgroundColor(activity.getResources().getColor(
					R.color.transparent));
		else
			loading_view.setBackgroundColor(activity.getResources().getColor(
					R.color.bg));
	}

	public void setStatus(LoadingEnum status, String message) {
			setVisibility(View.VISIBLE);

		if (status == LoadingEnum.LOADING) {
			
			layout_data_loading.setVisibility(View.VISIBLE);
			layout_no_data.setVisibility(View.GONE);
			layout_net_error.setVisibility(View.GONE);
			layout_tip.setVisibility(View.GONE);
			if (!KingTellerJudgeUtils.isEmpty(message)) {
				text_data_loading.setText(message);
			}

		} else if (status == LoadingEnum.NETERROR) {
			
			layout_data_loading.setVisibility(View.GONE);
			layout_no_data.setVisibility(View.GONE);
			layout_net_error.setVisibility(View.VISIBLE);
			layout_tip.setVisibility(View.GONE);
			if (!KingTellerJudgeUtils.isEmpty(message)) {
				text_net_error.setText(message);
			}

		} else if (status == LoadingEnum.NODATA) {
			
			layout_data_loading.setVisibility(View.GONE);
			layout_no_data.setVisibility(View.VISIBLE);
			layout_net_error.setVisibility(View.GONE);
			layout_tip.setVisibility(View.GONE);
			if (!KingTellerJudgeUtils.isEmpty(message)) {
				text_no_data.setText(message);
			}
		} else if (status == LoadingEnum.TIP) {
			
			layout_data_loading.setVisibility(View.GONE);
			layout_no_data.setVisibility(View.GONE);
			layout_net_error.setVisibility(View.GONE);
			layout_tip.setVisibility(View.VISIBLE);
			if (!KingTellerJudgeUtils.isEmpty(message)) {
				text_tip.setText(message);
			}
		} else if( status == LoadingEnum.UPDATEPLE){
			
			layout_data_loading.setVisibility(View.GONE);
			layout_no_data.setVisibility(View.VISIBLE);
			layout_net_error.setVisibility(View.GONE);
			layout_tip.setVisibility(View.GONE);
			text_no_data.setText("请下载新版本升级");
		}
	}

	public void setOnClickLister(LoadingEnum status, OnClickListener lister) {
		if (status == LoadingEnum.NODATA) {
			layout_no_data.setOnClickListener(lister);
		} else if (status == LoadingEnum.NETERROR) {
			layout_net_error.setOnClickListener(lister);
		}
	}

	public void setStatus(LoadingEnum status) {
		setStatus(status, null);
	}
}
