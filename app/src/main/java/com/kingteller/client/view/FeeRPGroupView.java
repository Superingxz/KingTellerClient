package com.kingteller.client.view;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.kingteller.R;
import com.kingteller.client.activity.common.CommonSelectGtgjActivity;
import com.kingteller.client.bean.common.CommonSelectData;
import com.kingteller.client.bean.workorder.FreeData;
import com.kingteller.client.config.KingTellerStaticConfig;
import com.kingteller.client.utils.CommonSelcetUtils;
import com.kingteller.client.utils.KingTellerJudgeUtils;
import com.kingteller.client.view.KingTellerEditText.OnChangeListener;
import com.kingteller.client.view.KingTellerEditText.OnDialogClickLister;
import com.kingteller.client.view.toast.T;

/**
 * 维护报告费用信息
 * 
 * @author 王定波
 * 
 */
public class FeeRPGroupView extends GroupViewBase implements Cloneable {

	private KingTellerEditText maintainpersonname;
	private KingTellerEditText expand1;
	private KingTellerEditText reportAccess;
	private KingTellerEditText trafficefee;
	private KingTellerEditText arriveroute;
	private KingTellerEditText qzdd;
	private KingTellerEditText jsdd;
	private Button btn_add, btn_delete;
	private String feeid;
	private View btn_reportAccess;
	private KingTellerEditText isGoBack;

	public FeeRPGroupView(Context context) {
		super(context);
	}

	public FeeRPGroupView(Context context, boolean isdel) {
		super(context, isdel);
	}

	public void setData(FreeData data) {
		maintainpersonname.setFieldTextAndValue(data.getUserName(),data.getUserId());
		expand1.setFieldTextAndValueFromValue(data.getFeeTypeId());
		reportAccess.setFieldTextAndValue(data.getFeeMode(), data.getFeeModeId());
		trafficefee.setFieldTextAndValue(data.getFeeMoney());
		arriveroute.setFieldTextAndValue(data.getBusLine());
		qzdd.setFieldTextAndValue(data.getQzdd());
		jsdd.setFieldTextAndValue(data.getJsdd());
		isGoBack.setFieldTextAndValueFromValue(KingTellerJudgeUtils.isEmpty(data.getIsGoBack()) ? "0" : data.getIsGoBack());
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
		data.setIsGoBack(isGoBack.getFieldValue());
		data.setId(feeid);
		return data;
	}

	protected void initView() {
		// TODO Auto-generated method stub
		LayoutInflater.from(getContext()).inflate(R.layout.item_add_rpfee, this);
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.WRAP_CONTENT);
		lp.setMargins(0, 0, 0, 25);
		setLayoutParams(lp);

		btn_add = (Button) findViewById(R.id.btn_add);
		btn_delete = (Button) findViewById(R.id.btn_delete);
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
				((LinearLayout) getParent()).removeView(FeeRPGroupView.this);
			}
		});

		btn_add.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				((LinearLayout) getParent()).addView(FeeRPGroupView.this.clone());

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
		isGoBack = (KingTellerEditText) findViewById(R.id.isGoBack);

		isGoBack.setLists(CommonSelcetUtils.getSelectList(CommonSelcetUtils.radios01));
		isGoBack.setFieldTextAndValueFromValue("0");
		expand1.setLists(CommonSelcetUtils.getSelectList(CommonSelcetUtils.feeType));

		expand1.setOnChangeListener(new OnChangeListener() {

			@Override
			public void onChanged(CommonSelectData data) {
				// TODO Auto-generated method stub
				reportAccess.setLists(new ArrayList<CommonSelectData>());
				reportAccess.setFieldTextAndValue("");
				if(!data.getText().contains("车费") && !"车船费".equals(data.getText())){
					reportAccess.setFieldEnabled(false);
				}else{
					reportAccess.setFieldEnabled(true);
				}
			}
		});
		
		reportAccess.setOnDialogClickLister(new OnDialogClickLister() {

			@Override
			public void OnDialogClick() {

				if(!KingTellerJudgeUtils.isEmpty(expand1.getFieldValue())){
					Intent intent = new Intent(getContext(),CommonSelectGtgjActivity.class);
					intent.putExtra(CommonSelectGtgjActivity.DATA, expand1.getFieldValue());
					intent.putExtra(CommonSelectGtgjActivity.TYPE, R.array.jtgj);
					((Activity) getContext()).startActivityForResult(intent, KingTellerStaticConfig.REQUEST_MODE);
					((LinearLayout) getParent()).setTag(reportAccess);
				}else{
					T.showShort(getContext(), "请先选择费用类型!");
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
	protected FeeRPGroupView clone() {
		// TODO Auto-generated method stub
		FeeRPGroupView ca = new FeeRPGroupView(getContext(), true);
		FreeData data = new FreeData();
		data.setUserId(maintainpersonname.getFieldValue());
		data.setUserName(maintainpersonname.getFieldText());
		ca.setData(data);
		return ca;
	}

}
