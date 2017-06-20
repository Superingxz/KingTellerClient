package com.kingteller.client.adapter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.kingteller.R;
import com.kingteller.client.bean.common.BasePageBean;
import com.kingteller.client.bean.workorder.ReturnBackStatus;
import com.kingteller.client.bean.workorder.SendOrderBean;
import com.kingteller.client.bean.workorder.WorkorderMachineParamBean;
import com.kingteller.client.bean.workorder.XmBean;
import com.kingteller.client.bean.workorder.XmListBean;
import com.kingteller.client.config.KingTellerStaticConfig;
import com.kingteller.client.config.KingTellerUrlConfig;
import com.kingteller.client.netauth.AjaxHttpCallBack;
import com.kingteller.client.netauth.KTHttpClient;
import com.kingteller.client.utils.ConditionUtils;
import com.kingteller.client.utils.KingTellerConfigUtils;
import com.kingteller.client.utils.KingTellerJudgeUtils;
import com.kingteller.client.view.KingTellerEditText;
import com.kingteller.client.view.dialog.NormalDialog;
import com.kingteller.client.view.dialog.listener.OnBtnClickL;
import com.kingteller.client.view.dialog.use.KingTellerProgressUtils;
import com.kingteller.client.view.dialog.use.KingTellerPromptDialogUtils;
import com.kingteller.client.view.toast.T;
import com.kingteller.framework.http.AjaxParams;

import java.util.ArrayList;
import java.util.List;

public class SendOrderAdapter extends BaseAdapter {
	
	private LayoutInflater inflater;
	private List<SendOrderBean> sendOrderList = new ArrayList<SendOrderBean>();
	private Context mContext;
	private String tabName;
	private SendOrderBean sendOrder;
	private Callback callBack;
	private String servType;

	public SendOrderAdapter(Context context, List<SendOrderBean> sendOrderList) {
		inflater = LayoutInflater.from(context);
		this.mContext = context;
		this.sendOrderList = sendOrderList;
	}

	public SendOrderAdapter(Context context, List<SendOrderBean> sendOrderList, String tabName) {
		inflater = LayoutInflater.from(context);
		this.sendOrderList = sendOrderList;
		this.tabName = tabName;
		this.mContext = context;
	}

	public void setLists(List<SendOrderBean> sendOrderList) {
		this.sendOrderList = sendOrderList;
		notifyDataSetChanged();
	}

	public void addLists(List<SendOrderBean> sendOrderList) {
		this.sendOrderList.addAll(sendOrderList);
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		if (sendOrderList == null) {
			return 0;
		}
		return sendOrderList.size();
	}

	@Override
	public Object getItem(int arg0) {
		return sendOrderList.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	@Override
	public View getView(final int postion, View v, ViewGroup parent) {
		sendOrder = sendOrderList.get(postion);

		ViewHoler viewHoler = null;
		if (v == null) {
			viewHoler = new ViewHoler();
			v = inflater.inflate(R.layout.item_sendorder, null);
			viewHoler.orderNo = (TextView) v.findViewById(R.id.orderNo);		//工单号
			viewHoler.wdsbjc = (TextView) v.findViewById(R.id.wdsbjc);			//网点设备简称
			viewHoler.cmFlag = (ImageView) v.findViewById(R.id.cmFlag);			//是否手机派单
			viewHoler.atmMsg = (TextView) v.findViewById(R.id.atmMsg);			//机器信息
			viewHoler.troubleRemark = (TextView) v.findViewById(R.id.troubleRemark);//问题描述
			viewHoler.assignWorkerName = (TextView) v.findViewById(R.id.assignWorkerName);//维护人员
			viewHoler.assignName = (TextView) v.findViewById(R.id.assignName);//派单人
			viewHoler.assignTime = (TextView) v.findViewById(R.id.assignTime);//派单时间
			viewHoler.prearrangeDateStr = (TextView) v.findViewById(R.id.prearrangeDateStr);//预约时间
			viewHoler.status = (TextView) v.findViewById(R.id.status);//工单状态
			
			viewHoler.btn_button = (Button) v.findViewById(R.id.btn_button);
	
			
			v.setTag(viewHoler);
		} else {
			viewHoler = (ViewHoler) v.getTag();
		}

		viewHoler.btn_button.setVisibility(View.GONE);
		
		final SendOrderBean sob = sendOrder;
		
		final String orderId = sendOrder.getOrderId();				//工单ID
		final String orderNo = sendOrder.getOrderNo();				//工单号
		final String orderType = sendOrder.getOrderType();			//工单的类型
		
		final String assignName = sendOrder.getAssignName();		//派单人
		final String assignTime = sendOrder.getAssignTime();		//派单时间
		final String troubleRemark = sendOrder.getTroubleRemark();	//问题描述
		
		final String assignWorkerName = sendOrder.getAssignWorkerName();	//维护人员
		
		final String atmCode = sendOrder.getATMCode();				//机器编号(御银为机器编的号)
		final String atmBankcode = sendOrder.getATMBankcode();		//ATM号(银行为机器编的号)
		
		String atmMsg = "";//ATM信息		
		if(KingTellerJudgeUtils.isEmpty(atmBankcode)){
			atmMsg = atmCode; 
		}else{
			atmMsg = atmCode + "(" + atmBankcode + ")";
		}
		
		final String cmFlag = sendOrder.getCmFlag();				//是否手机派单
		final String status = sendOrder.getStatus();//工单状态
		final String createType = sendOrder.getCreateType();//派单类别
		final String jqId = sendOrder.getJqId();//机器id
		
		final String wdSbJc = sendOrder.getWdsbjc();				//网点设备简称

		viewHoler.orderNo.setText(orderNo);
		viewHoler.atmMsg.setText(atmMsg);
		
		viewHoler.troubleRemark.setText(troubleRemark);
		viewHoler.assignName.setText(assignName);
		viewHoler.assignTime.setText(assignTime);
		viewHoler.assignWorkerName.setText(assignWorkerName);
		
		viewHoler.wdsbjc.setText(wdSbJc);
		
		if ("1".equals(cmFlag)) {
			viewHoler.cmFlag.setVisibility(View.VISIBLE);
		} else {
			viewHoler.cmFlag.setVisibility(View.GONE);
		}
		
		if ("1".equals(sendOrder.getArrangeType())) {
			viewHoler.prearrangeDateStr.setText(sendOrder.getPrearrangeDateStr());
			((LinearLayout) viewHoler.prearrangeDateStr.getParent()).setVisibility(View.VISIBLE);
		} else {
			((LinearLayout) viewHoler.prearrangeDateStr.getParent()).setVisibility(View.GONE);
		}
		
		
		// 工单状态：new-新单(20)，accept-接障(30)，arrive-到达(99)，begin-开始维护(40),finish-维护结束(1)
		if (!"finish".equals(status)) {
			viewHoler.btn_button.setVisibility(View.VISIBLE);
			if ("tab1".equals(tabName) || "tab2".equals(tabName) || "search".equals(tabName)) {
				if ("new".equals(status)) {
					viewHoler.btn_button.setText("接障");
				} else if ("accept".equals(status)) {
					viewHoler.btn_button.setText("到达现场");
				} else if ("arrive".equals(status)) {
					viewHoler.btn_button.setText("开始维护");
				} else if ("begin".equals(status)) {
					viewHoler.btn_button.setText("维护结束");
				}
			} else {
				viewHoler.btn_button.setVisibility(View.GONE);
			}
		}else {
			viewHoler.btn_button.setVisibility(View.GONE);
		}
		
		//进度： 工单状态
		if (!KingTellerJudgeUtils.isEmpty(status)) {
			if ("new".equals(status)) {
				viewHoler.status.setText("新单");
			} else if ("accept".equals(status)) {
				viewHoler.status.setText("接障");
			} else if ("arrive".equals(status)) {
				viewHoler.status.setText("到达现场");
			} else if ("begin".equals(status)) {
				viewHoler.status.setText("开始维护");
			} else if ("finish".equals(status)) {
				viewHoler.status.setText("维护结束");
			} else {
				viewHoler.status.setText(status);
			}
			((LinearLayout) viewHoler.status.getParent()).setVisibility(View.VISIBLE);
		} else {
			((LinearLayout) viewHoler.status.getParent()).setVisibility(View.GONE);
		}

		//我的工单 —— 接障事件
		viewHoler.btn_button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if (((Button) arg0).getText().toString().equals("到达现场") && sob.getOrderType().equals("maintenance")) {
					if (createType.equals("XM")) {
						getFhJqXxData(0, jqId, createType, orderId, status, orderType);
					} else {
						getFhJqXxData(1, jqId, createType, orderId, status, orderType);						
					}
				} else if (((Button) arg0).getText().toString().equals("接障") && orderType.equals("maintenance")) {
					getFhJqXxData(2, jqId, createType, orderId, status, orderType);
				} else {
					OpenWorkDialog(5, null, ((Button)arg0).getText().toString(), orderId, status, orderType);

				}

			}
		});
		return v;
	}
	

	private static class ViewHoler {
		public TextView orderNo;
		public ImageView cmFlag;
		public TextView atmMsg;
		public TextView troubleRemark;
		public TextView assignWorkerName;
		public TextView assignName;
		public TextView assignTime;
		public TextView status;
		public TextView prearrangeDateStr;
		public TextView wdsbjc;
		public Button btn_button;
	}

	public void submitStatus(String orderId, String status, String orderType,String servType) {

		KTHttpClient fh = new KTHttpClient(true);
		AjaxParams params = new AjaxParams();
		params.put("orderId", orderId);
		params.put("status", status);
		params.put("orderType", orderType);
		params.put("servType", servType);

		fh.post(KingTellerConfigUtils.CreateUrl(mContext, KingTellerUrlConfig.WebRwdztUrl), params,
				new AjaxHttpCallBack<ReturnBackStatus>(mContext, true) {

					@Override
					public void onStart() {
						KingTellerProgressUtils.showProgress(mContext, "正在提交...");
					}
					
					@Override
					public void onFinish() {
						KingTellerProgressUtils.closeProgress();
					}

					@Override
					public void onDo(ReturnBackStatus data) {
						if ("fail".equals(data.getResult())) {
							callBack.onFalse("提示：" + data.getMessage());
						} else if ("success".equals(data.getResult())) {
							T.showShort(mContext, "提交成功!");
							callBack.onSuccess();
						}
					};
				});
	}
	
	public void submitXmStatus(String orderId, String status, String orderType, String servType, XmListBean xmList) {

		KTHttpClient fh = new KTHttpClient(true);
		AjaxParams params = new AjaxParams();
		params.put("orderId", orderId);
		params.put("status", status);
		params.put("orderType", orderType);
		params.put("servType", servType);
		params.put("assign", ConditionUtils.getJsonFromObj(xmList));

		fh.post(KingTellerConfigUtils.CreateUrl(mContext, KingTellerUrlConfig.WebRwdztUrl),
				params, new AjaxHttpCallBack<ReturnBackStatus>(mContext,
						new TypeToken<ReturnBackStatus>() {
						}.getType(), false) {

					@Override
					public void onStart() {
						KingTellerProgressUtils.showProgress(mContext, "正在提交...");
					}
					
					@Override
					public void onFinish() {
						KingTellerProgressUtils.closeProgress();
					}

					@Override
					public void onDo(ReturnBackStatus data) {
						if ("fail".equals(data.getResult())) {
							callBack.onFalse("提示：" + data.getMessage());
						} else if ("success".equals(data.getResult())) {
							callBack.onSuccess();
						}
					};
				});
	}


	public void getFhJqXxData(final int type, String jqId, final String createType, final String orderId, final String status, final String orderType){
		KTHttpClient fh = new KTHttpClient(true);
		AjaxParams params = new AjaxParams();
		params.put("jqId", jqId);

		fh.post(KingTellerConfigUtils.CreateUrl(mContext, KingTellerUrlConfig.Fhjqxx),
				params, new AjaxHttpCallBack<BasePageBean<WorkorderMachineParamBean>>(mContext,
						new TypeToken<BasePageBean<WorkorderMachineParamBean>>() {}.getType(), false) {
					
					@Override
					public void onDo(BasePageBean<WorkorderMachineParamBean> data) {
						switch (type) {
						/**--到达现场--**/
						case 0://XM
							if("".equals(data.getStatus()) ){
								if(data.getList() == null){
									
								}else if(data.getList().size() > 0){
									OpenWorkDialog (1, data,  createType,  orderId, status, orderType);
								}
							}else{
								T.showShort(mContext, "获取数据失败!");
							}
							break;
							
						/**--到达现场--**/
						case 1://GD
							if("".equals(data.getStatus()) ){
								if(data.getList() == null){
									OpenWorkDialog (0, data,  createType,  orderId, status, orderType);
								}else if(data.getList().size() > 0){
									OpenWorkDialog (2, data,  createType,  orderId, status, orderType);
								}
							}else{
								T.showShort(mContext, "获取数据失败!");
							}
							break;
							
						/**--接障--**/
						case 2://JZ
							if("".equals(data.getStatus()) ){
								if(data.getList() == null){
									OpenWorkDialog (3, data,  createType,  orderId, status, orderType);
								}else if(data.getList().size() > 0){
									OpenWorkDialog (4, data,  createType,  orderId, status, orderType);
								}else{
									OpenWorkDialog (3, data,  createType,  orderId, status, orderType);
								}
							}else{
								T.showShort(mContext, "获取数据失败!");
							}
							break;
						case 3:
							
							break;	
						default:
							break;
						}
					}
		});
	}
	
	/**
	 * 打开工单选择dialog
	 */
	public void OpenWorkDialog (int type, BasePageBean<WorkorderMachineParamBean> data, 
			final String createType, final String orderId, final String status, final String orderType){
		switch (type) {
			case 0://服务方式 
				
				View layout_arrivexc = inflater.inflate(R.layout.item_layout_arrivexc, null);
				final Dialog dialog_arrivexc = new AlertDialog.Builder(mContext, R.style.Login_dialog).create();
				dialog_arrivexc.setCanceledOnTouchOutside(false);
				dialog_arrivexc.show();
				dialog_arrivexc.getWindow().setContentView(layout_arrivexc);
				WindowManager.LayoutParams lp_arrivexc = dialog_arrivexc.getWindow().getAttributes();
				lp_arrivexc.width = (int) (KingTellerStaticConfig.SCREEN.Width * 0.85); // 宽度设置为屏幕的0.85
		        dialog_arrivexc.getWindow().setAttributes(lp_arrivexc);


				RadioGroup group_arrivexc = (RadioGroup) layout_arrivexc.findViewById(R.id.radioGroup);
				Button submit_arrivexc = (Button) layout_arrivexc.findViewById(R.id.submit);
				Button cancel_arrivexc = (Button) layout_arrivexc.findViewById(R.id.cancel);
				
				servType = "";
				
				group_arrivexc.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
					@Override
					public void onCheckedChanged(RadioGroup group,int checkedId) {
						if (checkedId == R.id.radioButton1) {
							servType = "SITE_SERV";
							dialog_arrivexc.findViewById(R.id.backorderType).setVisibility(View.GONE);
						}else if(checkedId == R.id.radioButton2){
							servType = "TEL_SERV";
							dialog_arrivexc.findViewById(R.id.backorderType).setVisibility(View.VISIBLE);
						}else if(checkedId == R.id.radioButton3){
							servType = "OTHER_SERV";
							dialog_arrivexc.findViewById(R.id.backorderType).setVisibility(View.VISIBLE);
						}
					}
				});
				
				submit_arrivexc.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View arg0) {
						if(KingTellerJudgeUtils.isEmpty(servType)){
							T.showShort(mContext, "必须选择一项!");
							return;
						}
						submitStatus(orderId, status, orderType, servType);
						dialog_arrivexc.dismiss();
						
					}
				});
				
				cancel_arrivexc.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View arg0) {
						dialog_arrivexc.dismiss();
					}
				});
				
				break;
			case 1://操作选择
				
				View layout_jqidsm = inflater.inflate(R.layout.item_layout_jqidsm, null);
				final Dialog dialog_jqidsm = new AlertDialog.Builder(mContext, R.style.Login_dialog).create();
				dialog_jqidsm.setCanceledOnTouchOutside(false);
				dialog_jqidsm.show();
				dialog_jqidsm.getWindow().setContentView(layout_jqidsm);
				dialog_jqidsm.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
				WindowManager.LayoutParams lp_jqidsm = dialog_jqidsm.getWindow().getAttributes();
				lp_jqidsm.width = (int) (KingTellerStaticConfig.SCREEN.Width * 0.85); // 宽度设置为屏幕的0.85
				dialog_jqidsm.getWindow().setAttributes(lp_jqidsm);

				
				final ListView listView_jqidsm = (ListView) layout_jqidsm.findViewById(R.id.listView);
				JqIdXxAdapter jixAdapter_jqidsm = new JqIdXxAdapter(mContext, data.getList());
				listView_jqidsm.setAdapter(jixAdapter_jqidsm);
				
				Button submit_jqidsm = (Button) layout_jqidsm.findViewById(R.id.submit);
				Button cancel_jqidsm = (Button) layout_jqidsm.findViewById(R.id.cancel);
				
				submit_jqidsm.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View arg0) {

						XmListBean xmList = new XmListBean();
						List<XmBean> xl = new ArrayList<XmBean>();
						XmBean xm = null;
						int count = listView_jqidsm.getChildCount();
						for(int i =0;i < count;i ++){
							xm = new XmBean();
							xm.setXmid(((TextView)listView_jqidsm.getChildAt(i).findViewById(R.id.xmId)).getText().toString());
							KingTellerEditText choose1 = (KingTellerEditText)listView_jqidsm.getChildAt(i).findViewById(R.id.choose1);
							if(KingTellerJudgeUtils.isEmpty(choose1.getFieldValue())){
								T.showShort(mContext, "操作选项必须选择!");
								return;
							}
							xm.setOper(choose1.getFieldValue());
							xm.setUnexe(((EditText)listView_jqidsm.getChildAt(i).findViewById(R.id.editText1)).getText().toString());
							xl.add(xm);
						}
						xmList.setXmlist(xl);
						xmList.setXmct(createType);
						
						submitXmStatus(orderId, status, orderType, "", xmList);
						
						dialog_jqidsm.dismiss();
						
					}
				});
				
				cancel_jqidsm.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View arg0) {
						dialog_jqidsm.dismiss();
					}
				});
				break;
			case 2://服务方式 + 操作选择
				View layout_arrivejqidxx = inflater.inflate(R.layout.item_layout_arrivejqidxx, null);
				final Dialog dialog_arrivejqidxx = new AlertDialog.Builder(mContext, R.style.Login_dialog).create();
				dialog_arrivejqidxx.setCanceledOnTouchOutside(false);
				dialog_arrivejqidxx.show();
				dialog_arrivejqidxx.getWindow().setContentView(layout_arrivejqidxx);
				dialog_arrivejqidxx.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
				WindowManager.LayoutParams lp_arrivejqidxx = dialog_arrivejqidxx.getWindow().getAttributes();
				lp_arrivejqidxx.width = (int) (KingTellerStaticConfig.SCREEN.Width * 0.85); // 宽度设置为屏幕的0.85
				dialog_arrivejqidxx.getWindow().setAttributes(lp_arrivejqidxx);

				
				final ListView listView_arrivejqidxx = (ListView) layout_arrivejqidxx.findViewById(R.id.listView);
				JqIdXxAdapter jixAdapter_arrivejqidxx = new JqIdXxAdapter(mContext, data.getList());
				listView_arrivejqidxx.setAdapter(jixAdapter_arrivejqidxx);
				
				RadioGroup group_arrivejqidxx = (RadioGroup) layout_arrivejqidxx.findViewById(R.id.radioGroup);
				Button submit_arrivejqidxx = (Button) layout_arrivejqidxx.findViewById(R.id.submit);
				Button cancel_arrivejqidxx = (Button) layout_arrivejqidxx.findViewById(R.id.cancel);
				
				servType = "";
				
				group_arrivejqidxx.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
					@Override
					public void onCheckedChanged(RadioGroup group,int checkedId) {
						if (checkedId == R.id.radioButton1) {
							servType = "SITE_SERV";
							dialog_arrivejqidxx.findViewById(R.id.backorderType).setVisibility(View.GONE);
						}else if(checkedId == R.id.radioButton2){
							servType = "TEL_SERV";
							dialog_arrivejqidxx.findViewById(R.id.backorderType).setVisibility(View.VISIBLE);
						}else if(checkedId == R.id.radioButton3){
							servType = "OTHER_SERV";
							dialog_arrivejqidxx.findViewById(R.id.backorderType).setVisibility(View.VISIBLE);
						}
					}
				});
				
				submit_arrivejqidxx.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View arg0) {
						if(KingTellerJudgeUtils.isEmpty(servType)){
							T.showShort(mContext, "必须选择一项!");
							return;
						}
						XmListBean xmList = new XmListBean();
						List<XmBean> xl = new ArrayList<XmBean>();
						XmBean xm = null;
						int count = listView_arrivejqidxx.getChildCount();
						for(int i =0;i < count;i ++){
							xm = new XmBean();
							xm.setXmid(((TextView)listView_arrivejqidxx.getChildAt(i).findViewById(R.id.xmId)).getText().toString());
							KingTellerEditText choose1 = (KingTellerEditText)listView_arrivejqidxx.getChildAt(i).findViewById(R.id.choose1);
							if(KingTellerJudgeUtils.isEmpty(choose1.getFieldValue())){
								T.showShort(mContext, "操作选项必须选择!");
								return;
							}
							xm.setOper(choose1.getFieldValue());
							xm.setUnexe(((EditText)listView_arrivejqidxx.getChildAt(i).findViewById(R.id.editText1)).getText().toString());
							xl.add(xm);
						}
						xmList.setXmlist(xl);
						xmList.setXmct(createType);

						submitXmStatus(orderId, status, orderType, servType, xmList);
						
						dialog_arrivejqidxx.dismiss();
						
					}
				});
				
				cancel_arrivejqidxx.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View arg0) {
						dialog_arrivejqidxx.dismiss();
					}
				});
				break;
			case 3://维护报告普通接障
				
				final NormalDialog dialog_jz = new NormalDialog(mContext);
	        	KingTellerPromptDialogUtils.showTwoPromptDialog(dialog_jz, "您确定要接障吗？",
						new OnBtnClickL() {
							@Override
							public void onBtnClick() {
								dialog_jz.dismiss();
							}
	                    }, new OnBtnClickL() {
							@Override
							public void onBtnClick() {
								dialog_jz.dismiss();
								submitStatus(orderId, status, orderType, "");
							}
	                    });

				break;
			case 4://维护报告有提示信息接障
				
				View layout_jqxxjz = inflater.inflate(R.layout.item_jqxx, null);
				final Dialog dialog_jqxxjz = new AlertDialog.Builder(mContext, R.style.Login_dialog).create();
				dialog_jqxxjz.setCanceledOnTouchOutside(false);
				dialog_jqxxjz.show();
				dialog_jqxxjz.getWindow().setContentView(layout_jqxxjz);
				WindowManager.LayoutParams lp_jqxxjz = dialog_jqxxjz.getWindow().getAttributes();
				lp_jqxxjz.width = (int) (KingTellerStaticConfig.SCREEN.Width * 0.85); // 宽度设置为屏幕的0.85
				dialog_jqxxjz.getWindow().setAttributes(lp_jqxxjz);
				
				ListView listView_jqxxjz = (ListView) layout_jqxxjz.findViewById(R.id.listView);
				JqIdAdapter jqIdAdapter_jqxxjz = new JqIdAdapter(mContext, data.getList());
				listView_jqxxjz.setAdapter(jqIdAdapter_jqxxjz);
				
				((TextView)layout_jqxxjz.findViewById(R.id.textView)).setText("您确定要接障吗?");
				
				((Button)layout_jqxxjz.findViewById(R.id.submit)).setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View arg0) {
						dialog_jqxxjz.dismiss();
						submitStatus(orderId, status, orderType, "");
					}
				});
				
				((Button)layout_jqxxjz.findViewById(R.id.cancel)).setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View arg0) {
						dialog_jqxxjz.dismiss();
					}
				});
				break;
			case 5://其他接障
				
				//如果是接障,查询是否存在对应机器的项目工单]
				final NormalDialog dialog_qtjz = new NormalDialog(mContext);
	        	KingTellerPromptDialogUtils.showTwoPromptDialog(dialog_qtjz, "您确定要" + createType + "吗？",
						new OnBtnClickL() {
							@Override
							public void onBtnClick() {
								dialog_qtjz.dismiss();
							}
	                    }, new OnBtnClickL() {
							@Override
							public void onBtnClick() {
								dialog_qtjz.dismiss();
								submitStatus(orderId, status, orderType, "");
							}
	                    });
				
				break;
			default:
				break;
		}
	}
	

	public void setCallBack(Callback callBack) {
		this.callBack = callBack;
	}

	public interface Callback {
		public void onSuccess();

		public void onFalse(String msg);
	}
}
