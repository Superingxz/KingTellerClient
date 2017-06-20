package com.kingteller.client.activity.workorder;

import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kingteller.R;
import com.kingteller.client.activity.base.BaseUploadActivity;
import com.kingteller.client.activity.common.CommonSelectDataActivity;
import com.kingteller.client.adapter.WorkOrderAdapter;
import com.kingteller.client.bean.common.CommonSelectData;
import com.kingteller.client.bean.common.TreeBean;
import com.kingteller.client.bean.db.QRDotMachineReplace;
import com.kingteller.client.bean.workorder.FreeData;
import com.kingteller.client.bean.workorder.ReturnBackStatus;
import com.kingteller.client.bean.workorder.WorkOrderBean;
import com.kingteller.client.config.KingTellerStaticConfig;
import com.kingteller.client.config.KingTellerUrlConfig;
import com.kingteller.client.netauth.AjaxHttpCallBack;
import com.kingteller.client.netauth.KTHttpClient;
import com.kingteller.client.utils.KingTellerConfigUtils;
import com.kingteller.client.utils.KingTellerDbUtils;
import com.kingteller.client.utils.KingTellerJudgeUtils;
import com.kingteller.client.utils.KingTellerTimeUtils;
import com.kingteller.client.view.FeeGroupView;
import com.kingteller.client.view.FeeRPGroupView;
import com.kingteller.client.view.GroupListView;
import com.kingteller.client.view.GroupPicGridView.OnItemClickLister;
import com.kingteller.client.view.GroupUploadFileView;
import com.kingteller.client.view.KTAlertDialog;
import com.kingteller.client.view.KingTellerEditText;
import com.kingteller.client.view.KingTellerScrollView;
import com.kingteller.client.view.ListViewObj;
import com.kingteller.client.view.KingTellerScrollView.ScrollViewListener;
import com.kingteller.client.view.dialog.NormalDialog;
import com.kingteller.client.view.dialog.listener.OnBtnClickL;
import com.kingteller.client.view.dialog.use.KingTellerProgressUtils;
import com.kingteller.client.view.dialog.use.KingTellerPromptDialogUtils;
import com.kingteller.client.view.toast.T;
import com.kingteller.framework.http.AjaxParams;

public abstract class BaseReportActivity extends Activity implements
		OnClickListener {
	protected GroupListView fee_group_list;
	protected WorkOrderBean workBean;
	protected Button btn_temp_save;
	protected Button btn_submit;
	protected Button btn_back;
	protected Button btn_sp;
	protected KingTellerScrollView layout_scroll;
	private int sx = 0;// 记录位置
	private int sy = 0;// 记录位置
	protected String dataKey = "assign";
	public final static int OPT_ADUIT = 1;// 审核类型
	public final static String OPT_TYPE = "optType";// 类型字段
	protected int optType = 0;
	private LinearLayout layout_audit_common;
	protected KingTellerEditText auditRemark;
	protected KingTellerEditText auditContent;
	protected TextView auditTitle;

	public final static String OPT_STATE_TYPE = "stateType";// 未处理和已处理
	public final static String OPT_UNTREATED = "untreated";
	public final static String OPT_PROCESSED = "processed";
	private String state_type = "";
	protected String isCheckout = "";// 查看报告

	private LinearLayout layout_hide;
	private ImageButton hide;
	private Long backflag;


	protected TextView title_text;
	protected Button title_left_btn, title_righttwo_btn, add_fnnex_btn;
	
	private NormalDialog dialog = null; 	//初始化通用dialog
	
	public void initData() {
		
		KingTellerStaticConfig.WRITE_REPORT_SUBMISSION_STATE_1 = false;
		KingTellerStaticConfig.WRITE_REPORT_SUBMISSION_STATE_2 = false;
		KingTellerStaticConfig.WRITE_REPORT_SUBMISSION_STATE_3 = false;
		KingTellerStaticConfig.AUDIT_REPORT_SUBMISSION_STATE_1 = false;
		KingTellerStaticConfig.AUDIT_REPORT_SUBMISSION_STATE_2 = false;
		// 获取传递过来的数据
		workBean = (WorkOrderBean) this.getIntent().getExtras().getSerializable("reportBean");
	}

	public void initUI() {
		
		
		title_text = (TextView) findViewById(R.id.layout_main_text);
		title_left_btn = (Button) findViewById(R.id.layout_main_left_btn);
		
		title_left_btn.setOnClickListener(this);
		title_left_btn.setBackgroundResource(R.drawable.btn_back_arrow);
		
		
		
		
		optType = getIntent().getIntExtra(OPT_TYPE, 0);
		state_type = getIntent().getStringExtra(OPT_STATE_TYPE);
		isCheckout = getIntent().getStringExtra("isCheckout");
		backflag = getIntent().getLongExtra("backflag", 0);
		
		fee_group_list = (GroupListView) findViewById(R.id.group_list);
		
		if(!KingTellerJudgeUtils.isEmpty(dataKey)) {
			if("assign".equals(dataKey) || "otherMatter".equals(dataKey)){
				//维护报告费用类型 assign	and  其他报告费用类型 otherMatter
				fee_group_list.addItem(new FeeRPGroupView(BaseReportActivity.this, false));
			}else{
				fee_group_list.addItem(new FeeGroupView(BaseReportActivity.this, false));
			}
			
		}
		
		fee_group_list.setAddBtnHidden(true);
	

		layout_audit_common = (LinearLayout) findViewById(R.id.layout_audit_common);
		auditRemark = (KingTellerEditText) findViewById(R.id.auditRemark);
		auditContent = (KingTellerEditText) findViewById(R.id.auditContent);
		auditTitle = (TextView) findViewById(R.id.auditTitle);

		btn_temp_save = (Button) findViewById(R.id.btn_temp_save);
		btn_submit = (Button) findViewById(R.id.btn_submit);
		btn_back = (Button) findViewById(R.id.btn_back);
		btn_sp = (Button) findViewById(R.id.btn_sp);

		//工单信息layout
		layout_hide = (LinearLayout) findViewById(R.id.layout_hide);
		hide = (ImageButton) findViewById(R.id.hide);

		btn_submit.setOnClickListener(this);
		btn_temp_save.setOnClickListener(this);
		btn_back.setOnClickListener(this);
		btn_sp.setOnClickListener(this);
		hide.setOnClickListener(this);

		layout_scroll = (KingTellerScrollView) findViewById(R.id.layout_scroll);
		if (layout_scroll != null) {
			layout_scroll.setScrollViewListener(new ScrollViewListener() {

				@Override
				public void onScrollChanged(KingTellerScrollView scrollView,
						int x, int y, int oldx, int oldy) {
					// Logger.e("pos", sx+"_"+sy+"_"+x+"_"+y+"_"+oldx+"_"+oldy);
					if (oldx == sx && oldy == sy && y != sy && sy != 0) {
						sx = 0;
						sy = 0;
						layout_scroll.scrollTo(oldx, oldy);

					}
				}
			});
		}

		if (optType == BaseReportActivity.OPT_ADUIT) {
			// 设置信息不可写 只可以看
			LinearLayout layout_form = (LinearLayout) findViewById(R.id.layout_form);
			if (layout_form != null) {
				int length = layout_form.getChildCount();
				for (int i = 0; i < length; i++) {
					if (layout_form.getChildAt(i).getClass().getName().equals(KingTellerEditText.class.getName())) {
						((KingTellerEditText) layout_form.getChildAt(i)).setFieldEnabled(false);
					}
				}
			}
		}

		switch (optType) {
		case OPT_ADUIT:
			if (state_type.equals(OPT_PROCESSED)) {
				layout_audit_common.setVisibility(View.VISIBLE);
				onlyRead();
				btn_submit.setVisibility(View.GONE);
				btn_temp_save.setVisibility(View.GONE);
				btn_back.setVisibility(View.GONE);
				btn_sp.setVisibility(View.GONE);
			} else {
				layout_audit_common.setVisibility(View.VISIBLE);
				btn_submit.setVisibility(View.GONE);
				btn_temp_save.setVisibility(View.GONE);
			}
			break;
		default:
			if (backflag > 0) {
				onlyRead();
				layout_audit_common.setVisibility(View.VISIBLE);
			} else {
				layout_audit_common.setVisibility(View.GONE);
			}
			if (KingTellerJudgeUtils.isEmpty(isCheckout)) {
			} else {
				btn_submit.setVisibility(View.GONE);
				btn_temp_save.setVisibility(View.GONE);
			}
			btn_back.setVisibility(View.GONE);
			btn_sp.setVisibility(View.GONE);
			break;
		}

		
		KingTellerConfigUtils.hideInputMethod(this);

	}

	public void onlyRead() {
		int length = layout_audit_common.getChildCount();
		for (int i = 0; i < length; i++) {
			if (layout_audit_common.getChildAt(i).getClass().getName().equals(KingTellerEditText.class.getName())) {
				((KingTellerEditText) layout_audit_common.getChildAt(i)).setFieldEnabled(false);
			}
		}
	}


	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
		case KingTellerStaticConfig.REQUEST_FEETYPE:
			if (resultCode == RESULT_OK) {
				fee_group_list.setItemData(((TreeBean) data
						.getSerializableExtra(CommonSelectDataActivity.DATA))
						.getCommonSelectData());
			}
			break;
		case KingTellerStaticConfig.REQUEST_MODE:
			if (resultCode == RESULT_OK) {
				if (workBean.getOrderType().equals("maintenance")) {
					fee_group_list.setItemDataMul((CommonSelectData) data
									.getSerializableExtra(CommonSelectDataActivity.DATA));
				} else {
					fee_group_list.setItemData((CommonSelectData) data
									.getSerializableExtra(CommonSelectDataActivity.DATA));
				}
			}
			break;
		default:
			break;
		}

	}

	public boolean checkNet(boolean isSubmit) {
		if (!KingTellerJudgeUtils.isNetAvaliable(this)) {
			
			final NormalDialog dialog_wlcw = new NormalDialog(this);
			dialog_wlcw.content("网络不可用, 是否离线保存？")
	                .style(NormalDialog.STYLE_TWO)
	                .btnTextColor(Color.parseColor("#383838"), Color.parseColor("#383838"))
					.show();
			dialog_wlcw.setOnBtnClickL(
	                new OnBtnClickL() {
	                    @Override
	                    public void onBtnClick() {
	                    	dialog_wlcw.dismiss();
	                    	finish();
	                    }
	                },
	                new OnBtnClickL() {
	                    @Override
	                    public void onBtnClick() {
	                    	dialog_wlcw.dismiss();
	                    	
	                    	KingTellerDbUtils.saveReportToDataBase(
									BaseReportActivity.this, 
									workBean.getOrderId(),
									workBean.getOrderType(),
									getFromData(), 
									"",
									KingTellerTimeUtils.getNowTime(),
									0);
	                    }
	                });

			return false;
		}

		return true;
	}

	public abstract String getFromData();

	@Override
	public void startActivityForResult(Intent intent, int requestCode) {
		super.startActivityForResult(intent, requestCode);
		sx = layout_scroll.getScrollX();
		sy = layout_scroll.getScrollY();
	}

	@SuppressLint("NewApi")
	@Override
	public void onClick(View view) {
		switch (view.getId()) {

		case R.id.btn_sp:
			
			dialog = new NormalDialog(this);
        	KingTellerPromptDialogUtils.showTwoPromptDialog(dialog, "您确定要审批报告吗?",
					new OnBtnClickL() {
						@Override
						public void onBtnClick() {
							dialog.dismiss();
						}
                    }, new OnBtnClickL() {
						@Override
						public void onBtnClick() {
							dialog.dismiss();
							doAuditFlow(0);
						}
                    });

			break;
		case R.id.btn_back:
			if(KingTellerJudgeUtils.isEmpty(auditContent.getFieldValue())){
				T.showShort(this, "需要填写退回意见!");
				return;
			}
			
			dialog = new NormalDialog(this);
        	KingTellerPromptDialogUtils.showTwoPromptDialog(dialog, "您确定要退回该报告吗?",
					new OnBtnClickL() {
						@Override
						public void onBtnClick() {
							dialog.dismiss();
						}
                    }, new OnBtnClickL() {
						@Override
						public void onBtnClick() {
							dialog.dismiss();
							doAuditFlow(1);
						}
                    });
        	
			break;
		case R.id.btn_temp_save:
			if (checkNet(false))
				submitReport(1);
			break;
		case R.id.btn_submit:
			if (checkNet(true))
				submitReport(2);

			break;
		case R.id.layout_main_left_btn:
			returnBack();
			break;
		case R.id.hide:
			if(layout_hide.getVisibility() == View.GONE){
				hide.setBackground(getResources().getDrawable(R.drawable.ic_up_nomal));
				layout_hide.setVisibility(View.VISIBLE);
			}else if(layout_hide.getVisibility() == View.VISIBLE){
				hide.setBackground(getResources().getDrawable(R.drawable.ic_down_nomal));
				layout_hide.setVisibility(View.GONE);
			}
			break;
		default:
			break;
		}
	}

	/**
	 * 提交报告
	 * @param flag 1：暂存 2：提交　
	 */
	public void submitReport(final int flag) {
		
		if (flag == 2) {
			List<FreeData> fcost = fee_group_list.getListData();
			for (int i = 0; i < fcost.size(); i++) {
				if(KingTellerJudgeUtils.isEmpty(fcost.get(i).getFeeType())){
					T.showShort(this, "费用类型不能为空!");
					return;
				}
				if(KingTellerJudgeUtils.isEmpty(fcost.get(i).getFeeMoney())){
					T.showShort(this, "费用金额不能为空!");
					return;
				}
				if(fcost.get(i).getFeeType().contains("车费") || "车船费".equals(fcost.get(i).getFeeType())){
					if(KingTellerJudgeUtils.isEmpty(fcost.get(i).getFeeMode())){
						T.showShort(this, "交通工具不能为空!");
						return;
					}
					if(KingTellerJudgeUtils.isEmpty(fcost.get(i).getBusLine())){
						T.showShort(this, "费用说明不能为空!");
						return;
					}
				}
				if(KingTellerJudgeUtils.isEmpty(fcost.get(i).getQzdd())){
					T.showShort(this, "起止地点不能为空!");
					return;
				}
				if(KingTellerJudgeUtils.isEmpty(fcost.get(i).getJsdd())){
					T.showShort(this, "结束地点不能为空!");
					return;
				}
			}
			
//			T.showShort(this, "测试!");
//			return;
		}

		final String assign = getFromData();
		KTHttpClient fh = new KTHttpClient(true);
		AjaxParams ajaxParams = new AjaxParams();
		ajaxParams.put(dataKey, assign);
		ajaxParams.put("flag", String.valueOf(flag));
				
		fh.post(KingTellerConfigUtils.CreateUrl(this, KingTellerUrlConfig.otherMatterUrl),
				ajaxParams, new AjaxHttpCallBack<ReturnBackStatus>(this, true) {

					@Override
					public void onStart() {
						KingTellerProgressUtils.showProgress(BaseReportActivity.this, "正在保存数据...");
					}


					@Override
					public void onError(int errorNo, String strMsg) {
						KingTellerProgressUtils.closeProgress();
						
						KingTellerDbUtils.saveReportToDataBase(
								BaseReportActivity.this, 
								workBean.getOrderId(),
								workBean.getOrderType(),
								assign,
								"",
								KingTellerTimeUtils.getNowTime(),
								0);
						
						T.showShort(BaseReportActivity.this, "数据错误!");
					}

					@Override
					public void onDo(ReturnBackStatus data) {
						KingTellerProgressUtils.closeProgress();
						
						if ("success".equals(data.getResult())) {
							
							KingTellerDbUtils.deleteReportFromDataBase(
									BaseReportActivity.this,
									workBean.getOrderId());
							
							if (flag == 1) {
								T.showShort(BaseReportActivity.this, "保存成功!");
								
							} else if (flag == 2) {
								KingTellerStaticConfig.WRITE_REPORT_SUBMISSION_STATE_2 = true;
								KingTellerStaticConfig.WRITE_REPORT_SUBMISSION_STATE_3 = true;
								T.showShort(BaseReportActivity.this, "提交成功!");
								setResult(RESULT_OK);
								finish();
							}
						} else {
							dialog = new NormalDialog(BaseReportActivity.this);
							KingTellerPromptDialogUtils.showOnePromptDialog(dialog, data.getMessage(),
			    					new OnBtnClickL() {
			    						@Override
			    						public void onBtnClick() {
			    							dialog.dismiss();
			    							
			    							KingTellerDbUtils.saveReportToDataBase(
													BaseReportActivity.this, 
													workBean.getOrderId(),
													workBean.getOrderType(),
													assign, 
													"",
													KingTellerTimeUtils.getNowTime(),
													1);
			    						}
			                        });
						}

					}
				});

	}

	private void back() {
		if (optType == 0) {
				String data = KingTellerDbUtils.getReportDataFromDataBase(this, workBean.getOrderId());
				
				final String fromdata = getFromData();
				if (!data.equals(fromdata) && !"".equals(fromdata)){
					final NormalDialog dialog_lxbc = new NormalDialog(this);
					dialog_lxbc.content("您的数据尚未提交, 是否保存本地?")
			                .style(NormalDialog.STYLE_TWO)
			                .btnText("不保存", "保存")
			                .btnTextColor(Color.parseColor("#383838"), Color.parseColor("#383838"))
							.show();
					dialog_lxbc.setOnBtnClickL(
			                new OnBtnClickL() {
			                    @Override
			                    public void onBtnClick() {
			                    	dialog_lxbc.dismiss();
			                    	finish();
			                    }
			                },
			                new OnBtnClickL() {
			                    @Override
			                    public void onBtnClick() {
			                    	dialog_lxbc.dismiss();
			                    	
			                    	KingTellerDbUtils.saveReportToDataBase(
											BaseReportActivity.this, 
											workBean.getOrderId(),
											workBean.getOrderType(),
											fromdata, 
											"",
											KingTellerTimeUtils.getNowTime(),
											0);
			                    	
			                    	finish();
			                    }
			                });
					

				}else{
					finish();
				}
		} else{
			finish();
		}
	}

	@Override
	public void onBackPressed() {
		// super.onBackPressed();
		returnBack();	
	}

	private void returnBack(){
		if(!KingTellerJudgeUtils.isEmpty(isCheckout)){
			finish();
		}else{
			back();
		}
	}
	
	/**
	 * 审批
 	 * @param flag 1退回    0审批
	 */
	private void doAuditFlow(final int flag) {

		String assign = getFromData();
		KTHttpClient fh = new KTHttpClient(true);
		AjaxParams ajaxParams = new AjaxParams();
		ajaxParams.put(dataKey, assign);
		ajaxParams.put("flag", String.valueOf(flag));

		fh.post(KingTellerConfigUtils.CreateUrl(this, KingTellerUrlConfig.SfthUrl), ajaxParams,
				new AjaxHttpCallBack<ReturnBackStatus>(this, true) {

					@Override
					public void onStart() {
						KingTellerProgressUtils.showProgress(BaseReportActivity.this, "正在提交...");
					}

					@Override
					public void onError(int errorNo, String strMsg) {
						KingTellerProgressUtils.closeProgress();
						T.showShort(BaseReportActivity.this, "数据错误!");
					}

					@Override
					public void onDo(ReturnBackStatus data) {
						KingTellerProgressUtils.closeProgress();
						if ("success".equals(data.getResult())) {
							if (flag == 1) {
								T.showShort(BaseReportActivity.this, "退回成功!");
							} else if (flag == 0) {
								T.showShort(BaseReportActivity.this, "审批成功!");
							}	
							KingTellerStaticConfig.AUDIT_REPORT_SUBMISSION_STATE_1 = true;
							KingTellerStaticConfig.AUDIT_REPORT_SUBMISSION_STATE_2 = true;
							finish();
						}else{
							dialog = new NormalDialog(BaseReportActivity.this);
							KingTellerPromptDialogUtils.showOnePromptDialog(dialog, data.getMessage(),
			    					new OnBtnClickL() {
			    						@Override
			    						public void onBtnClick() {
			    							dialog.dismiss();
			    						}
			                        });
							
							//T.showShort(BaseReportActivity.this, "提示：" + data.getMessage());
						}

					};
				});
	}

}
