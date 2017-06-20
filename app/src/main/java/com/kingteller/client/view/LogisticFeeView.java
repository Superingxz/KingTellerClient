package com.kingteller.client.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.kingteller.R;
import com.kingteller.client.activity.workorder.RapairSendOrderActivity;
import com.kingteller.client.bean.common.CommonSelectData;
import com.kingteller.client.utils.CommonSelcetUtils;
import com.kingteller.client.view.GroupListView.AddViewCallBack;
import com.kingteller.client.view.KingTellerEditText.OnChangeListener;
import com.kingteller.client.view.KingTellerEditText.OnDialogClickLister;

public class LogisticFeeView extends GroupViewBase {

	private Context context;
	private KingTellerEditText fhqclcs;
	private KingTellerEditText involvesFee;
	private LinearLayout layout_fee_info;
	private GroupListView costType_group_list;
	

	public LogisticFeeView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public LogisticFeeView(Context context, boolean isdel) {
		super(context, isdel);
		this.context = context;
	}

	@Override
	protected void initView() {
		// TODO Auto-generated method stub
		LayoutInflater.from(getContext()).inflate(R.layout.item_layout_fhqc, this);
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.FILL_PARENT,
				LinearLayout.LayoutParams.WRAP_CONTENT);
		lp.setMargins(0, 10, 0, 0);
		setLayoutParams(lp);
		
		fhqclcs = (KingTellerEditText) findViewById(R.id.fhqclcs);
		involvesFee = (KingTellerEditText) findViewById(R.id.involvesFee);
		layout_fee_info = (LinearLayout) findViewById(R.id.layout_fee_info);
		costType_group_list = (GroupListView) findViewById(R.id.costType_group_list);
		
		costType_group_list.setAddViewCallBack(new AddViewCallBack() {

			@Override
			public void addView(GroupListView view) {
				// TODO Auto-generated method stub
				view.addItem(new LogisticTaskFareGroupView(
						context, true));
			}
		});

		costType_group_list.addItem(new LogisticTaskFareGroupView(context,false));
		
		involvesFee.setLists(CommonSelcetUtils.getSelectList(CommonSelcetUtils.radios01));
		involvesFee.setOnChangeListener(new OnChangeListener() {
			
			@Override
			public void onChanged(CommonSelectData data) {
				// TODO Auto-generated method stub
				if(data.getValue().equals("1")){
					layout_fee_info.setVisibility(View.VISIBLE);
				}else{
					layout_fee_info.setVisibility(View.GONE);
				}
			}
		});
	}

	@Override
	public boolean checkData() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Object getData() {
		// TODO Auto-generated method stub
		return null;
	}

}
