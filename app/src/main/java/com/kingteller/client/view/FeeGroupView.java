package com.kingteller.client.view;

import java.util.ArrayList;

import com.kingteller.R;
import com.kingteller.client.activity.common.CommonSelectDataActivity;
import com.kingteller.client.activity.common.TreeChooserActivity;
import com.kingteller.client.bean.common.CommonSelectData;
import com.kingteller.client.bean.workorder.FreeData;
import com.kingteller.client.config.KingTellerStaticConfig;
import com.kingteller.client.view.KingTellerEditText.OnChangeListener;
import com.kingteller.client.view.KingTellerEditText.OnDialogClickLister;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

/**
 * 费用信息
 * 
 * @author 王定波
 * 
 */
public class FeeGroupView extends GroupViewBase implements Cloneable {

	private KingTellerEditText maintainpersonname;
	private KingTellerEditText expand1;
	private KingTellerEditText reportAccess;
	private KingTellerEditText trafficefee;
	private KingTellerEditText arriveroute;
	private KingTellerEditText qzdd;
	private KingTellerEditText jsdd;
	private Button btn_add;
	private View btn_delete;
	private String feeid;
	private View btn_reportAccess;

	public FeeGroupView(Context context) {
		super(context);
	}

	public FeeGroupView(Context context, boolean isdel) {
		super(context, isdel);
	}

	public void setData(FreeData data) {
		maintainpersonname.setFieldTextAndValue(data.getUserName(), data.getUserId());
		expand1.setFieldTextAndValue(data.getFeeType(), data.getFeeTypeId());
		reportAccess.setFieldTextAndValue(data.getFeeMode(), data.getFeeModeId());
		trafficefee.setFieldTextAndValue(data.getFeeMoney());
		arriveroute.setFieldTextAndValue(data.getBusLine());
		qzdd.setFieldTextAndValue(data.getQzdd());
		jsdd.setFieldTextAndValue(data.getJsdd());
		feeid = data.getId();
	}

	public FreeData getData() {
		FreeData data = new FreeData();
		data.setBusLine(arriveroute.getFieldValue());
		data.setFeeMoney(trafficefee.getFieldValue());
		data.setFeeMode(reportAccess.getFieldText());
		data.setFeeModeId(reportAccess.getFieldValue());
		data.setQzdd(qzdd.getFieldText()); 
		data.setJsdd(jsdd.getFieldText()); 
		data.setUserId(maintainpersonname.getFieldValue());
		data.setUserName(maintainpersonname.getFieldText());
		data.setFeeType(expand1.getFieldText());
		data.setFeeTypeId(expand1.getFieldValue());
		data.setId(feeid);
		return data;
	}

	protected void initView() {
		// TODO Auto-generated method stub
		LayoutInflater.from(getContext()).inflate(R.layout.item_add_fee, this);
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.WRAP_CONTENT);
		lp.setMargins(0, 10, 0, 0);
		setLayoutParams(lp);

		btn_add = (Button) findViewById(R.id.btn_add);
		btn_delete = findViewById(R.id.btn_delete);
		btn_reportAccess = findViewById(R.id.btn_reportAccess);

		if (isdel){
			btn_delete.setVisibility(View.VISIBLE);
		}else{
			btn_delete.setVisibility(View.GONE);
		}
		
		btn_delete.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				((LinearLayout) getParent()).removeView(FeeGroupView.this);
			}
		});

		btn_add.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				((LinearLayout) getParent()).addView(FeeGroupView.this.clone());

			}
		});
		
		btn_reportAccess.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				reportAccess.setFieldTextAndValue("");

			}
		});

		maintainpersonname = (KingTellerEditText) findViewById(R.id.maintainpersonname);
		expand1 = (KingTellerEditText) findViewById(R.id.expand1);
		reportAccess = (KingTellerEditText) findViewById(R.id.reportAccess);
		trafficefee = (KingTellerEditText) findViewById(R.id.trafficefee);
		arriveroute = (KingTellerEditText) findViewById(R.id.arriveroute);
		qzdd = (KingTellerEditText) findViewById(R.id.qzdd);
		jsdd = (KingTellerEditText) findViewById(R.id.jsdd);
		
		expand1.setOnDialogClickLister(new OnDialogClickLister() {

			@Override
			public void OnDialogClick() {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getContext(),
						TreeChooserActivity.class);
				intent.putExtra(CommonSelectDataActivity.TYPE, R.array.feetype);
				((Activity) getContext()).startActivityForResult(intent,
						KingTellerStaticConfig.REQUEST_FEETYPE);

				((LinearLayout) getParent()).setTag(expand1);
			}
		});

		expand1.setOnChangeListener(new OnChangeListener() {

			@Override
			public void onChanged(CommonSelectData data) {
				// TODO Auto-generated method stub
				reportAccess.setLists(new ArrayList<CommonSelectData>());
				reportAccess.setFieldTextAndValue("");
			}
		});

		reportAccess.setOnDialogClickLister(new OnDialogClickLister() {

			@Override
			public void OnDialogClick() {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getContext(),
						CommonSelectDataActivity.class);
				intent.putExtra(CommonSelectDataActivity.EXTRADATA,expand1.getFieldValue());
				intent.putExtra(CommonSelectDataActivity.TYPE, R.array.jtgj);
				((Activity) getContext()).startActivityForResult(intent,
						KingTellerStaticConfig.REQUEST_MODE);

				((LinearLayout) getParent()).setTag(reportAccess);
			}
		});

	}


	@Override
	public boolean checkData() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected FeeGroupView clone() {
		// TODO Auto-generated method stub
		FeeGroupView ca = new FeeGroupView(getContext(), true);
		FreeData data = new FreeData();
		data.setUserId(maintainpersonname.getFieldValue());
		data.setUserName(maintainpersonname.getFieldText());
		ca.setData(data);
		return ca;
	}

}
