package com.kingteller.client.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.kingteller.R;
import com.kingteller.client.activity.common.CommonSelectClgcActivity;
import com.kingteller.client.activity.common.CommonSelectDataActivity;
import com.kingteller.client.bean.workorder.WorkTypeBean;
import com.kingteller.client.config.KingTellerStaticConfig;
import com.kingteller.client.utils.CommonSelcetUtils;
import com.kingteller.client.utils.KingTellerJudgeUtils;
import com.kingteller.client.view.KingTellerEditText.OnDialogClickLister;
import com.kingteller.client.view.toast.T;

public class XmWorkTypeGroupView extends GroupViewBase {

	private KingTellerEditText workType;
	private KingTellerEditText costMinute;
	private KingTellerEditText handleSub;
	private KingTellerEditText remark;

	public XmWorkTypeGroupView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void initView() {
		// TODO Auto-generated method stub
		LayoutInflater.from(getContext()).inflate(R.layout.item_add_xmworktype,
				this);
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.WRAP_CONTENT);
		lp.setMargins(0, 0, 0, 25);
		setLayoutParams(lp);

		workType = (KingTellerEditText) findViewById(R.id.workType);
		costMinute = (KingTellerEditText) findViewById(R.id.costMinute);
		handleSub = (KingTellerEditText) findViewById(R.id.handleSub);
		remark = (KingTellerEditText)findViewById(R.id.remark);

		workType.setLists(CommonSelcetUtils.getSelectList(CommonSelcetUtils.projectWorkType));
		workType.setFieldEnabled(false);
		handleSub.setFieldEnabled(false);
		
		handleSub.setOnDialogClickLister(new OnDialogClickLister() {
			@Override
			public void OnDialogClick() {
				Intent intent = new Intent(getContext(), CommonSelectClgcActivity.class);
				intent.putExtra(CommonSelectDataActivity.TYPE, R.array.clgcworktype);
				intent.putExtra(CommonSelectDataActivity.EXTRADATA, KingTellerJudgeUtils.isEqualEmpty(workType.getFieldText())? "" : workType.getFieldText());
				((Activity) getContext()).startActivityForResult(intent, KingTellerStaticConfig.SELECT_HANDLESUB_XM);
				((LinearLayout) getParent()).setTag(handleSub);
			}
		});
	}
	@Override
	public boolean checkData() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public WorkTypeBean getData() {
		WorkTypeBean bean = new WorkTypeBean();
		if(KingTellerJudgeUtils.isEmpty(workType.getFieldValue())){
			bean.setWorkType("");
			bean.setHandleSub("");
			bean.setHandleSubId("");
			bean.setCostMinute(Long.valueOf("0"));
			bean.setRemark("");
		}else{
			bean.setWorkType(KingTellerJudgeUtils.isEmpty(workType.getFieldValue())? "" : workType.getFieldValue());
			bean.setHandleSub(KingTellerJudgeUtils.isEmpty(handleSub.getFieldText())? "" : handleSub.getFieldText().trim());
			bean.setHandleSubId(KingTellerJudgeUtils.isEmpty(handleSub.getFieldValue())? "" : handleSub.getFieldValue());
			bean.setCostMinute(KingTellerJudgeUtils.isEmpty(costMinute.getFieldValue())? Long.valueOf("0") : Long.valueOf(costMinute.getFieldValue()));
			bean.setRemark(KingTellerJudgeUtils.isEmpty(remark.getFieldText())? "" : remark.getFieldText());
		}
		return bean;
	}

	public void setData(WorkTypeBean data) {
		/*
		if("1".equals(data.getWorkType()) && KingTellerJudgeUtils.isEmpty(data.getHandleSubId())) {
			handleSub.setFieldTextAndValue("");
		}else {
			handleSub.setFieldTextAndValue(data.getHandleSub(), data.getHandleSubId());
		}
		*/
		if ("1".equals(data.getWorkType())) {
			if(KingTellerJudgeUtils.isEmpty(data.getHandleSubId())) {
				handleSub.setFieldTextAndValue("");
			}else {
				handleSub.setFieldTextAndValue(
						data.getHandleSub(), 
						data.getHandleSubId());
			}
			
		} else {
			handleSub.setFieldTextAndValue(
					data.getHandleSub(), 
					data.getHandleSubId());
		}
		workType.setFieldTextAndValueFromValue(data.getWorkType());
		costMinute.setFieldTextAndValue(data.getCostMinute().toString());
		if("1".equals(data.getWorkType())) {
			handleSub.setFieldEnabled(true);
			if(data.getHandleSub().contains("其他")){ //其它 其他
				remark.setVisibility(View.VISIBLE);
				remark.setFieldTextAndValue(data.getRemark());
			}else{
				remark.setVisibility(View.GONE);
				remark.setFieldTextAndValue("");
			}
		}
	}

	@Override
	protected XmWorkTypeGroupView clone() {
		// TODO Auto-generated method stub
		return null;
	}
}

