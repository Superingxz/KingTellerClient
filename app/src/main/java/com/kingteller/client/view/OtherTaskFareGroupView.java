package com.kingteller.client.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.kingteller.R;
import com.kingteller.client.activity.common.CommonSelectDataActivity;
import com.kingteller.client.bean.common.CommonSelectData;
import com.kingteller.client.bean.logisticmonitor.FareDetailParam;
import com.kingteller.client.config.KingTellerStaticConfig;
import com.kingteller.client.utils.CommonSelcetUtils;
import com.kingteller.client.view.KingTellerEditText.OnDialogClickLister;

public class OtherTaskFareGroupView  extends GroupViewBase{
	
	private KingTellerEditText fylx;
	private KingTellerEditText je;
	private OnChangeListener onChangeListener;
	private Button btn_add;
	private View btn_delete;

	public OtherTaskFareGroupView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	
	public OtherTaskFareGroupView(Context context, boolean isdel) {
		super(context, isdel);
	}
	
	@Override
	protected void initView() {
		LayoutInflater.from(getContext()).inflate(R.layout.item_add_othertask_cost, this);
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.FILL_PARENT,
				LinearLayout.LayoutParams.WRAP_CONTENT);
		lp.setMargins(0, 10, 0, 0);
		setLayoutParams(lp);
		
		fylx = (KingTellerEditText) findViewById(R.id.fylx);
		je = (KingTellerEditText) findViewById(R.id.je);
		
		btn_add = (Button) findViewById(R.id.btn_add);
		btn_delete = findViewById(R.id.btn_delete);

		if (isdel){
			findViewById(R.id.btn_delete).setVisibility(View.VISIBLE);
		}
		else{
			findViewById(R.id.btn_delete).setVisibility(View.GONE);
			findViewById(R.id.btn_add).setVisibility(View.GONE);
		}

		btn_delete.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				((LinearLayout) getParent()).removeView(OtherTaskFareGroupView.this);
			}
		});

		btn_add.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				((LinearLayout) getParent()).addView(OtherTaskFareGroupView.this
						.clone());

			}
		});
		
		fylx.setOnDialogClickLister(new OnDialogClickLister() {

			@Override
			public void OnDialogClick() {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getContext(),
						CommonSelectDataActivity.class);
				intent.putExtra(CommonSelectDataActivity.TYPE, R.array.fylx);

				((Activity) getContext()).startActivityForResult(intent,
						KingTellerStaticConfig.SELECT_FYLX);
				((LinearLayout) getParent()).setTag(fylx);
			}
		});
		
		/*fylx.setLists(CommonSelcetUtils
				.getSelectList(CommonSelcetUtils.costType));*/
		
	}

	@Override
	public boolean checkData() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public FareDetailParam getData() {
		// TODO Auto-generated method stub
		FareDetailParam fd = new FareDetailParam();
		fd.setFylx(fylx.getFieldValue());
		fd.setJe(je.getFieldValue());
		
		return fd;
	}
	
	public interface OnChangeListener {
		public void onCostTypeChange(OtherTaskFareGroupView view,
				CommonSelectData data);
	}
	
	@Override
	protected OtherTaskFareGroupView clone() {
		// TODO Auto-generated method stub
		OtherTaskFareGroupView ca = new OtherTaskFareGroupView(getContext(), true);
		ca.setOnChangeListener(onChangeListener);
		return ca;
	}

	public OnChangeListener getOnChangeListener() {
		return onChangeListener;
	}

	public void setOnChangeListener(OnChangeListener onChangeListener) {
		this.onChangeListener = onChangeListener;
	}

}
