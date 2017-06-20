package com.kingteller.client.view;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.kingteller.R;
import com.kingteller.client.activity.common.CommonSelectClgcActivity;
import com.kingteller.client.activity.common.CommonSelectDataActivity;
import com.kingteller.client.activity.workorder.RepairReportActivity;
import com.kingteller.client.bean.common.CommonSelectData;
import com.kingteller.client.bean.workorder.WorkTypeBean;
import com.kingteller.client.config.KingTellerStaticConfig;
import com.kingteller.client.utils.CommonSelcetUtils;
import com.kingteller.client.utils.KingTellerJudgeUtils;
import com.kingteller.client.view.KingTellerEditText.OnDialogClickLister;
import com.kingteller.client.view.toast.T;

/**
 * 工作类别视图
 * 
 * @author 王定波
 * 
 */
public class WorkTypeGroupView extends GroupViewBase {

	private KingTellerEditText workType;
	private KingTellerEditText costMinute;
	private KingTellerEditText handleSub;
	private KingTellerEditText remark;
	private KingTellerEditText troubleReasonCode;
	private OnChangeListener onChangeListener;
	private Button btn_add, btn_delete;
	private boolean listflag;
//	private boolean wlzcflag = false;
	
	public WorkTypeGroupView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public WorkTypeGroupView(Context context, boolean isdel) {
		super(context, isdel);
	}
	
	@Override
	protected void initView() {
		// TODO Auto-generated method stub
		LayoutInflater.from(getContext()).inflate(R.layout.item_add_worktype,
				this);
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.WRAP_CONTENT);
		lp.setMargins(0, 0, 0, 25);
		setLayoutParams(lp);

		btn_add = (Button) findViewById(R.id.btn_add);
		btn_delete = (Button) findViewById(R.id.btn_delete);

		listflag = false;
		
		if (isdel){
			btn_delete.setVisibility(View.VISIBLE);
		}else{
			btn_delete.setVisibility(View.GONE);
		}

		btn_delete.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				((LinearLayout) getParent()).removeView(WorkTypeGroupView.this);
				if (onChangeListener != null) {
					onChangeListener.onWorkTypeChange(WorkTypeGroupView.this, null);
				}
			}
		});

		btn_add.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				((LinearLayout) getParent()).addView(WorkTypeGroupView.this.clone());
			}
		});
		btn_add.setVisibility(GONE);

		workType = (KingTellerEditText) findViewById(R.id.workType);
		costMinute = (KingTellerEditText) findViewById(R.id.costMinute);
		handleSub = (KingTellerEditText) findViewById(R.id.handleSub);
		troubleReasonCode = (KingTellerEditText) findViewById(R.id.troubleReasonCode);
		remark = (KingTellerEditText) findViewById(R.id.remark);

		workType.setLists(CommonSelcetUtils.getSelectList(CommonSelcetUtils.workType2));
		troubleReasonCode.setLists(CommonSelcetUtils.getSelectList(CommonSelcetUtils.troubleReasonCode2));

		workType.setOnChangeListener(new com.kingteller.client.view.KingTellerEditText.OnChangeListener() {

			@Override
			public void onChanged(CommonSelectData data) {
				
//			{ "故障维护：NORMAL", "软件升级：SOFT_UPGRAD", "调试机器：DEBUG", "PM：PM", "开通机器：START", 
//			  "账务处理：ACCOUNTANT", "培训：TRAINING","接机：RECEIVE_MACHINE", "移机：MOVE_MACHINE", 
//			  "退机：BACK_MACHINE", "安装机器：SETUP_MACHINE", "检查机器：CHECK_MACHINE", "其他：OTHER", 
//			  "部件加装：ADD_COMPONENT","客服报废：SCRAP_MACHINE", "硬件改造：MODIFY_COMPONENT","停机：STOP_MACHINE"};

				handleSub.setFieldRequested(true);
				handleSub.setFieldTextAndValue("");//处理过程
				troubleReasonCode.setFieldRequested(true);
				troubleReasonCode.setFieldTextAndValue("");//本次故障原因
				remark.setFieldTextAndValue("");//其他说明
				remark.setVisibility(View.GONE);
				
				
				if(data.getValue().equals("NORMAL")){//维护报告
					handleSub.setFieldEnabled(false);
					handleSub.setFieldTextAndValue("请在维护信息栏填写维护过程");
					troubleReasonCode.setVisibility(View.VISIBLE);
					troubleReasonCode.setFieldTextAndValueFromValue("-1");
				}else{
					handleSub.setFieldEnabled(true);
					handleSub.setFieldRequested(true);
					troubleReasonCode.setVisibility(View.GONE);
				}
				
				try {
					
					List<WorkTypeBean> list = ((GroupListView) ((LinearLayout) ((LinearLayout) getParent()).getParent()).getParent()).getListData();
					List<String> listtype = new ArrayList<String>();
	
					for (int j = 0; j < list.size(); j++) {
						listtype.add(list.get(j).getWorkType());
					}
				
					//有部件更换二维码信息	首先选择	故障维护；
					if(KingTellerStaticConfig.QR_DOTMACHINE_REPLACE_NUM > 0){
						if(!listtype.contains("NORMAL")){
							T.showShort(getContext(), "该工单含有二维码信息, 请首先选择  故障维护  的工作类别!");
							workType.setFieldTextAndValue("");
							listflag=false;
							return;
						}
					}	
					
					//工作类别中包含故障维护时  工作类别不能选退机，客服报废，移机，开通机器共存
					if (listtype.contains("NORMAL")) {
						
						if (listtype.contains("BACK_MACHINE")||listtype.contains("SCRAP_MACHINE")||listtype.contains("MOVE_MACHINE")) {
							T.showShort(getContext(), "不能再选择："+data.getText()+"!");
							workType.setFieldTextAndValue("");
							listflag = false;
							return;
						}
					}
					//工作类别中包含PM时 工作类别不能选择退机，工程项目，开通机器
					if (listtype.contains("PM")) {
						if (listtype.contains("BACK_MACHINE")||listtype.contains("ENGINEERING_PROJECT")||listtype.contains("START")||listtype.contains("DEBUG")) {
							T.showShort(getContext(),"不能再选择："+data.getText()+"!");
							workType.setFieldTextAndValue("");
							listflag = false;
							return;
						}
					}
					//工作类别中包含开通机器时 工作类别不能选择移机，退机，客服报废
					if (listtype.contains("START")) {
						if (listtype.contains("MOVE_MACHINE")||listtype.contains("BACK_MACHINE")||listtype.contains("SCRAP_MACHINE")||listtype.contains("DEBUG")) {
							T.showShort(getContext(), "不能再选择："+data.getText() + "!");
							workType.setFieldTextAndValue("");
							listflag = false;
							return;
						}
					}
					//工作类别中包含调试机器 工作类别不能选择退机，客服报废        2.2.19->开通机器
					if (listtype.contains("DEBUG")) {
						if (listtype.contains("BACK_MACHINE")||listtype.contains("SCRAP_MACHINE")||listtype.contains("START")) {
							T.showShort(getContext(), "不能再选择："+ data.getText() + "!");
							workType.setFieldTextAndValue("");
							listflag = false;
							return;
						}
					}
					//如果工作类别为 退机或者客服报废  工作类别不能选择其他任何一项
					if (listtype.contains("BACK_MACHINE")) {
						if (listtype.contains("NORMAL")||listtype.contains("PM")||listtype.contains("START")
							||listtype.contains("DEBUG")||listtype.contains("MOVE_MACHINE")
							||listtype.contains("CUSTOMER_ASSISTANCE")||listtype.contains("ENGINEERING_PROJECT")
							||listtype.contains("INSTALL_UPGRAD")||listtype.contains("SCARAP_MACHINE")) {
							T.showShort(getContext(), "不能再选择："+data.getText()+"!");
							workType.setFieldTextAndValue("");
							listflag = false;
							return;
						}
						
					}
					//如果工作类别为客服报废 工作类别不能选择其他任何一项
					if (listtype.contains("SCRAP_MACHINE")) {
						if (listtype.contains("NORMAL")||listtype.contains("PM")||listtype.contains("START")
							||listtype.contains("DEBUG")||listtype.contains("MOVE_MACHINE")
							||listtype.contains("CUSTOMER_ASSISTANCE")||listtype.contains("ENGINEERING_PROJECT")
							||listtype.contains("INSTALL_UPGRAD")||listtype.contains("BACK_MACHINE")) {
							T.showShort(getContext(), "不能再选择："+data.getText()+"!");
							workType.setFieldTextAndValue("");
							listflag = false;
							return;
						}
						
					}
				/*	//工作类别中包含 开通机器、调试机器、安装机器、接机中的任何一个时  工作类别都 不能再选择  退机  或  客服报废；
					if(listtype.contains("START") || listtype.contains("DEBUG") || listtype.contains("SETUP_MACHINE")
							|| listtype.contains("RECEIVE_MACHINE")){
						if(listtype.contains("BACK_MACHINE") || listtype.contains("SCRAP_MACHINE")){
							T.showShort(getContext(), "不能再选择：" + data.getText() + "!");
							workType.setFieldTextAndValue("");
							listflag=false;
							return;
						}
					}
					
					//工作类别中包含	退机  时工作类别  都不能再选择	开通机器,调试机器,安装机器,接机,客服报废   中的任何一个；    
					if(listtype.contains("BACK_MACHINE")){
						if(listtype.contains("START") || listtype.contains("DEBUG") || listtype.contains("SETUP_MACHINE") 
							|| listtype.contains("RECEIVE_MACHINE") || listtype.contains("SCRAP_MACHINE")){
							T.showShort(getContext(), "不能再选择：" + data.getText() + "!");
							workType.setFieldTextAndValue("");
							listflag=false;
							return;
						}
					}
					
					//工作类别中包含	客服报废   时工作类别  都不能再选择	开通机器,调试机器,安装机器,接机,硬件改造,退机,停机	中的任何一个
					if(listtype.contains("SCRAP_MACHINE")){
						if(listtype.contains("START") || listtype.contains("DEBUG") || listtype.contains("SETUP_MACHINE") 
							|| listtype.contains("RECEIVE_MACHINE") || listtype.contains("MOVE_MACHINE")
							|| listtype.contains("BACK_MACHINE") || listtype.contains("STOP_MACHINE")){
							T.showShort(getContext(), "不能再选择：" + data.getText() + "!");
							workType.setFieldTextAndValue("");
							listflag=false;
							return;
						}
					}
					*/
					//去重
					for (int j = 0; j < listtype.size(); j++) {
						for(int i = j;i <listtype.size();i++){
							if(i != j && listtype.get(i).equals(listtype.get(j))){
								T.showShort(getContext(), "不能重复选择同一个类别：" + data.getText() + "!");
								workType.setFieldTextAndValue("");
								listflag=false;
								return;
							}
						}
					}
					
				} catch (Exception e) {
									
				}
				
//				//判断打开报告状态？还是填写的状态
//				if(!wlzcflag){
//					listflag = false;
//					wlzcflag = true;
//				}
				
				if (onChangeListener != null && listflag) {
					onChangeListener.onWorkTypeChange(WorkTypeGroupView.this, data);
				}
				
				listflag = true;
			}
		});
		
		handleSub.setOnDialogClickLister(new OnDialogClickLister() {
			@Override
			public void OnDialogClick() {
				
				if(KingTellerJudgeUtils.isEqualEmpty(workType.getFieldValue())){
					T.showShort(getContext(), "工作类别不能为空!");
				}else{
					Intent intent = new Intent(getContext(), CommonSelectClgcActivity.class);
					intent.putExtra(CommonSelectDataActivity.TYPE, R.array.clgcworktype);
					intent.putExtra(CommonSelectDataActivity.EXTRADATA, KingTellerJudgeUtils.isEqualEmpty(workType.getFieldValue())? "" : workType.getFieldValue());
					((Activity) getContext()).startActivityForResult(intent, KingTellerStaticConfig.SELECT_HANDLESUB);
					((LinearLayout) getParent()).setTag(handleSub);
				}	
			}
		});
	}
	@Override
	public boolean checkData() {
		return false;
	}

	@Override
	public WorkTypeBean getData() {
		WorkTypeBean bean = new WorkTypeBean();
		if(KingTellerJudgeUtils.isEmpty(workType.getFieldValue())){
			bean.setWorkType("");
			bean.setWorkTypeLike("");
			bean.setHandleSubId("");
			bean.setHandleSub("");
			bean.setCostMinute(Long.valueOf("0"));
			bean.setReason("");
			bean.setRemark("");
		}else{
			bean.setWorkType(KingTellerJudgeUtils.isEmpty(workType.getFieldValue())? "" : workType.getFieldValue());
			bean.setWorkTypeLike(KingTellerJudgeUtils.isEmpty(workType.getFieldText())? "" : workType.getFieldText());
			bean.setHandleSubId(KingTellerJudgeUtils.isEmpty(handleSub.getFieldValue())? "" : handleSub.getFieldValue());
			bean.setHandleSub(KingTellerJudgeUtils.isEmpty(handleSub.getFieldText())? "" : handleSub.getFieldText().trim());
			bean.setCostMinute(KingTellerJudgeUtils.isEmpty(costMinute.getFieldValue())? Long.valueOf("0") : (long)Float.parseFloat(costMinute.getFieldValue()));
			bean.setReason(KingTellerJudgeUtils.isEmpty(troubleReasonCode.getFieldValue())? "" : troubleReasonCode.getFieldValue());
			bean.setRemark(KingTellerJudgeUtils.isEmpty(remark.getFieldText())? "" : remark.getFieldText());
		}
		return bean;
	}

	public void setData(WorkTypeBean data) {
		
		if(data.getWorkType().equals("NORMAL")){
			troubleReasonCode.setVisibility(View.VISIBLE);
			handleSub.setFieldEnabled(false);
		}else{
			troubleReasonCode.setVisibility(View.GONE);

			handleSub.setFieldEnabled(true);
			handleSub.setFieldRequested(true);
		}
		
		if(data.getHandleSub().contains("其他")){ //其它 其他
			remark.setVisibility(View.VISIBLE);
		}else{
			remark.setVisibility(View.GONE);
		}
		
		if ("STOP_MACHINE".equals(data.getWorkType())
				||"ACCOUNTANT".equals(data.getWorkType())
				||"CHECK_MACHINE".equals(data.getWorkType())
				||"TRAINING".equals(data.getWorkType())
				||"OTHER".equals(data.getWorkType())) {
			workType.setFieldTextAndValueFromValue("CUSTOMER_ASSISTANCE");
		}else if ("RECEIVE_MACHINE".equals(data.getWorkType())
				||"SETUP_MACHINE".equals(data.getWorkType())) {
			workType.setFieldTextAndValueFromValue("ENGINEERING_PROJECT");
		}else if ("SOFT_UPGRAD".equals(data.getWorkType())
				||"ADD_COMPONENT".equals(data.getWorkType())
				||"MODIFY_COMPONENT".equals(data.getWorkType())) {
			workType.setFieldTextAndValueFromValue("INSTALL_UPGRAD");			
		}else {
			workType.setFieldTextAndValueFromValue(data.getWorkType());
		}
		costMinute.setFieldTextAndValue(data.getCostMinute().toString());
		
		if ("1".equals(data.getReason())||"3".equals(data.getReason())) {
			troubleReasonCode.setFieldTextAndValueFromValue("6");
		}else if ("2".equals(data.getReason())||"4".equals(data.getReason())) {
			troubleReasonCode.setFieldTextAndValueFromValue("2");
		}else {
			troubleReasonCode.setFieldTextAndValueFromValue(data.getReason());
		}
		remark.setFieldTextAndValue(data.getRemark());	
	}

	public void setOnChangeListener(OnChangeListener onChangeListener) {
		this.onChangeListener = onChangeListener;
	}
	

	public interface OnChangeListener {
		public void onWorkTypeChange(WorkTypeGroupView view,CommonSelectData data);
	}

	@Override
	protected WorkTypeGroupView clone() {
		WorkTypeGroupView ca = new WorkTypeGroupView(getContext(), true);
		ca.setOnChangeListener(onChangeListener);
		ca.setListflag(true);
		return ca;
	}
	
	public void setListflag(Boolean mListflag) {
		this.listflag = mListflag;
	}
}
