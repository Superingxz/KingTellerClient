package com.kingteller.client.activity.attendance.checkout;

import java.util.Arrays;
import java.util.List;

import com.google.gson.reflect.TypeToken;
import com.kingteller.R;
import com.kingteller.client.KingTellerApplication;
import com.kingteller.client.bean.attendance.BoardBean;
import com.kingteller.client.bean.attendance.OverTimeBean;
import com.kingteller.client.config.KingTellerStaticConfig;
import com.kingteller.client.config.KingTellerUrlConfig;
import com.kingteller.client.netauth.AjaxHttpCallBack;
import com.kingteller.client.netauth.KTHttpClient;
import com.kingteller.client.utils.KingTellerConfigUtils;
import com.kingteller.client.utils.KingTellerJsonUtils;
import com.kingteller.client.utils.KingTellerJudgeUtils;
import com.kingteller.client.utils.KingTellerTimeUtils;
import com.kingteller.client.view.dialog.NormalDialog;
import com.kingteller.client.view.dialog.NormalListDialog;
import com.kingteller.client.view.dialog.listener.OnBtnClickL;
import com.kingteller.client.view.dialog.listener.OnOperItemClickL;
import com.kingteller.client.view.dialog.use.KingTellerProgressUtils;
import com.kingteller.client.view.dialog.use.KingTellerPromptDialogUtils;
import com.kingteller.client.view.groupview.itemview.OverTimePeopleHistoryGroupView;
import com.kingteller.client.view.groupview.listview.OverTimePeopleGroupListView;
import com.kingteller.client.view.toast.T;
import com.kingteller.framework.http.AjaxParams;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class WorkBoardFromCheckoutActivity extends Activity implements OnClickListener{
	
	private TextView title_text;
	private Button title_left_btn, audit_ty, audit_th;
	private Context mContext;
	
	private OverTimePeopleGroupListView ldyj_group_list;
	
	private TextView boardName;
	private TextView boardStartDay;
	private TextView boardStratTime;
	private TextView boardType;
	private EditText boardTypeQt;
	private EditText boardWhy;
	
	private TextView oa_attendance_audit_Csy;
	private EditText oa_attendance_audit_Yj;
	
	private TextView oa_attendance_audit_sqdh;
	private TextView oa_attendance_audit_sqry;
	private TextView oa_attendance_audit_sqrq;

	private int isCheckout = 0;// 0：查看报告	1：审批报告
	
	private String mBoardData_str;				//出差单数据
	private BoardBean mBoardData;			//初始数据
	
	private String[] mSpyj_StringItem;
	private List<String> mSpyj_StringItemList;
	
	private NormalDialog dialog = null; 	//初始化通用dialog
	
	@Override
	public void onCreate(Bundle paramBundle) {
		super.onCreate(paramBundle);
		setContentView(R.layout.layout_work_attendance_general);
		KingTellerApplication.addActivity(this);

		mContext = WorkBoardFromCheckoutActivity.this;
		isCheckout = getIntent().getIntExtra("isCheckout", 0);
		mBoardData_str = (String) getIntent().getStringExtra("mBoardData");
		mBoardData = KingTellerJsonUtils.getPerson(mBoardData_str, BoardBean.class);
		
		KingTellerStaticConfig.OA_ADUIT_ATTENDANCE_ISUDATE = false;
		
		initUI();
		initData();
	}

	private void initUI() {
		title_text = (TextView) findViewById(R.id.layout_main_text);
		title_left_btn = (Button) findViewById(R.id.layout_main_left_btn);
		
		title_left_btn.setBackgroundResource(R.drawable.btn_back_arrow);
		title_left_btn.setOnClickListener(this);
		
		findViewById(R.id.oa_qjlx_layout).setVisibility(View.GONE);//隐藏 请假类型
		findViewById(R.id.oa_qjdh_layout).setVisibility(View.GONE);//隐藏 请假单号
		findViewById(R.id.oa_czmdd_layout).setVisibility(View.GONE);//隐藏 出差目的地
		
		((TextView) findViewById(R.id.oa_work_people_name)).setText("补登人员：");
		boardName = (TextView) findViewById(R.id.oa_work_people);
		
		((TextView) findViewById(R.id.oa_start_time_name)).setText("补登时间：");
		boardStartDay = (TextView) findViewById(R.id.oa_start_day);
		boardStratTime = (TextView) findViewById(R.id.oa_start_time);
		
		findViewById(R.id.oa_end_time_layout).setVisibility(View.GONE);//隐藏 结束时间
		findViewById(R.id.oa_usertime_layout).setVisibility(View.GONE);//隐藏 时长
		
		((TextView) findViewById(R.id.oa_all_why_name)).setText("补登原因：");
		boardWhy = (EditText) findViewById(R.id.oa_all_why);
		boardWhy.setTextColor(Color.parseColor("#FF666666"));
		boardWhy.setFocusable(false);
		boardWhy.setEnabled(false);
		
		findViewById(R.id.oa_bdxx_layout).setVisibility(View.VISIBLE);//显示  补登类型
		boardType = (TextView) findViewById(R.id.oa_all_bdxx_lx);
		boardTypeQt = (EditText) findViewById(R.id.oa_all_bdxx_qtsm);
		boardTypeQt.setTextColor(Color.parseColor("#FF666666"));
		boardTypeQt.setFocusable(false);
		boardTypeQt.setEnabled(false);

		oa_attendance_audit_sqdh = (TextView) findViewById(R.id.oa_attendance_audit_sqdh);
		oa_attendance_audit_sqry = (TextView) findViewById(R.id.oa_attendance_audit_sqry);
		oa_attendance_audit_sqrq = (TextView) findViewById(R.id.oa_attendance_audit_sqrq);
		
		if(isCheckout == 0){
			title_text.setText("补登单查看");
			findViewById(R.id.layout_attendance_audit_djxx_common).setVisibility(View.VISIBLE);//单号信息
			findViewById(R.id.layout_attendance_audit_ldyj_common).setVisibility(View.VISIBLE);//领导意见
			findViewById(R.id.oa_aduit_spyj_layout).setVisibility(View.GONE);//审批意见
			findViewById(R.id.oa_general_aduit_btn_layout).setVisibility(View.GONE);//审批按钮
		}else{
			title_text.setText("补登单审批");
			findViewById(R.id.layout_attendance_audit_djxx_common).setVisibility(View.VISIBLE);
			findViewById(R.id.layout_attendance_audit_ldyj_common).setVisibility(View.VISIBLE);
			findViewById(R.id.oa_aduit_spyj_layout).setVisibility(View.VISIBLE);
			findViewById(R.id.oa_general_aduit_btn_layout).setVisibility(View.VISIBLE);
			
			audit_ty = (Button) findViewById(R.id.oa_general_aduit_ty);
			audit_ty.setOnClickListener(this);
			audit_th = (Button) findViewById(R.id.oa_general_aduit_th);
			audit_th.setOnClickListener(this);
		}
		
		//处理历史
		ldyj_group_list = (OverTimePeopleGroupListView) findViewById(R.id.ldyj_group_list);
		ldyj_group_list.setGroupListTwoViewHidden(true);
		ldyj_group_list.setAddBtnHidden(1);
		
		oa_attendance_audit_Csy = (TextView) findViewById(R.id.attendance_audit_csy);
		oa_attendance_audit_Csy.setOnClickListener(this);
		oa_attendance_audit_Yj = (EditText) findViewById(R.id.attendance_audit_yj);

	}
	
	public void initData() {
		mSpyj_StringItem = new String[] {"同意", "核查无误", "批准", "退回"};
		mSpyj_StringItemList = Arrays.asList(mSpyj_StringItem);
		
		setData();
		
		KingTellerConfigUtils.hideInputMethod(this);
	}
	
	public void setData() {
		//单号信息 
		oa_attendance_audit_sqdh.setText(mBoardData.getFillNo()); 
		oa_attendance_audit_sqry.setText(mBoardData.getCreateUserName());
		oa_attendance_audit_sqrq.setText(mBoardData.getCreateTimeStr());
		
		//设置基本信息
		boardName.setText(mBoardData.getFillUserName() + "(" + mBoardData.getFillUserAccount() + ")");
		
		boardStartDay.setText(KingTellerTimeUtils.getYearMonthDay(mBoardData.getFillTime(), 1, 2));
		boardStratTime.setText(KingTellerTimeUtils.getYearMonthDay(mBoardData.getFillTime(), 1, 3));
		
		boardWhy.setText(mBoardData.getFillReason());
		boardType.setText(mBoardData.getFillTypeName());
		if("其他".equals(mBoardData.getFillTypeName())){
			boardTypeQt.setVisibility(View.VISIBLE);
			boardTypeQt.setText(mBoardData.getFillTypeOth());
		}

		//处理历史
		for(int i = 0; i < mBoardData.getHistoryList().size(); i++){
			OverTimePeopleHistoryGroupView leaderView = new OverTimePeopleHistoryGroupView(mContext);
			leaderView.setData(mBoardData.getHistoryList().get(i).getExetime(), 
					mBoardData.getHistoryList().get(i).getExeuser(), 
					mBoardData.getHistoryList().get(i).getTitle(), 
					mBoardData.getHistoryList().get(i).getTt(),
					mBoardData.getHistoryList().get(i).getSuggestion());
			
			ldyj_group_list.addItem(leaderView);
		}
		ldyj_group_list.setSelectData("处理历史");
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.layout_main_left_btn:
			finish();
			break;
		case R.id.attendance_audit_csy:
			final NormalListDialog dialog_jblx = new NormalListDialog(mContext, mSpyj_StringItem);
			 dialog_jblx.title("请选择审批示语")//
		                .layoutAnimation(null)
		                .titleBgColor(Color.parseColor("#409ED7"))//
		                .itemPressColor(Color.parseColor("#85D3EF"))//
		                .itemTextColor(Color.parseColor("#303030"))//
		                .show();
			 dialog_jblx.setOnOperItemClickL(new OnOperItemClickL() {
		            @Override
		            public void onOperItemClick(AdapterView<?> parent, View view, int position, long id) {
		            	dialog_jblx.dismiss();
		            	oa_attendance_audit_Csy.setText(mSpyj_StringItemList.get(position).toString());
		            	oa_attendance_audit_Yj.setText(mSpyj_StringItemList.get(position).toString());
		            	
		            }
		        });

			break;
		case R.id.oa_general_aduit_ty:
			dialog = new NormalDialog(mContext);
			KingTellerPromptDialogUtils.showTwoPromptDialog(dialog, "你确定要同意吗？",
					new OnBtnClickL() {
						@Override
						public void onBtnClick() {
							dialog.dismiss();
						}
                    },new OnBtnClickL() {
	                    @Override
	                    public void onBtnClick() {
	                    	dialog.dismiss();
	                    	setAduitBoard(2);
	                    }
                });
			break;
		case R.id.oa_general_aduit_th:
			dialog = new NormalDialog(mContext);
			KingTellerPromptDialogUtils.showTwoPromptDialog(dialog, "你确定要退回吗？",
					new OnBtnClickL() {
						@Override
						public void onBtnClick() {
							dialog.dismiss();
						}
                    },new OnBtnClickL() {
	                    @Override
	                    public void onBtnClick() {
	                    	dialog.dismiss();
	                    	setAduitBoard(3);
	                    }
                });
			break;
		}
	}
		
	/**
	 * 2同意 或  3退回
	 */
	public void setAduitBoard(final int num) {
		if (mBoardData == null) {
			return;
		}
		
		//上传数据
		String saveflag = "";
		if(num == 2){
			saveflag = "audit";
		}else{
			saveflag = "back";
		}
		
		String csy = oa_attendance_audit_Csy.getText().toString();
		String yj = oa_attendance_audit_Yj.getText().toString();
		//限制条件
//		if("请选择常用批示语".equals(csy)){
//			T.showShort(mContext, "请选择常用批示语!");
//			return;
//		}
		if(KingTellerJudgeUtils.isEmpty(yj)){
			T.showShort(mContext, "请输入您的审批意见!");
			return;
		}
		
		//批示语限制
		if("同意".equals(csy) || "核查无误".equals(csy) ||	"批准".equals(csy)){
			if(num == 3){
				T.showShort(mContext, "您选择的常用批示语为：" + csy + "! 不能选择退回" );
				return;
			}
		}
		if( "退回".equals(csy) && num == 2){
			T.showShort(mContext, "您选择的常用批示语为：" + csy + "! 不能选择同意" );
			return;
		}
		
		KTHttpClient fh = new KTHttpClient(true);
		AjaxParams params = new AjaxParams();
		
		params.put("saveflag", saveflag);
		params.put("billType", "kqmgrFILLFlow");
		
		params.put("fillId", mBoardData.getFillId());
		params.put("fillNo", mBoardData.getFillNo());
		
		params.put("createUserId", mBoardData.getCreateUserId());
		params.put("createUserName", mBoardData.getCreateUserName());
		params.put("createTimeStr", mBoardData.getCreateTimeStr());
		
		params.put("flowStatus", mBoardData.getFlowStatus());
		params.put("fillTime", mBoardData.getFillTime());
		
		params.put("fillUserId", mBoardData.getFillUserId());
		params.put("fillUserName", mBoardData.getFillUserName());
		params.put("fillUserAccount", mBoardData.getFillUserAccount());
		
		params.put("fillType", mBoardData.getFillType());
		params.put("fillTypeName", mBoardData.getFillTypeName());
		params.put("fillTypeOth", mBoardData.getFillTypeOth());

		params.put("fillReason", mBoardData.getFillReason());
		
		params.put("exeRemark", yj);

		fh.post(KingTellerConfigUtils.CreateUrl(this, KingTellerUrlConfig.OAOAAllAduitWorkFillIn), params, 
				new AjaxHttpCallBack<OverTimeBean>(this,
						new TypeToken<OverTimeBean>() {
						}.getType(), true) {
			
					@Override
					public void onStart() {
						if(num == 2){
							KingTellerProgressUtils.showProgress(mContext, "正在审批中...");
						}else{
							KingTellerProgressUtils.showProgress(mContext, "正在退回中...");
						}	
					}
					
					@Override
					public void onError(int errorNo, String strMsg) {
						super.onError(errorNo, strMsg);
						KingTellerProgressUtils.closeProgress();
						
						T.showShort(mContext, "数据访问超时!");
					}
					
					@Override
					public void onDo(OverTimeBean data) {
						KingTellerProgressUtils.closeProgress();
						if("".equals(data.getCode())){
							
							KingTellerStaticConfig.OA_ADUIT_ATTENDANCE_ISUDATE = true;
							
							if(num == 2){
								T.showShort(mContext, "审批成功!");
							}else{
								T.showShort(mContext, "退回成功!");
							}
							finish();
						}else{
							dialog = new NormalDialog(mContext);
							if(num == 2){
								KingTellerPromptDialogUtils.showOnePromptDialog(dialog, "审批失败!" + data.getCode(),
				    					new OnBtnClickL() {
				    						@Override
				    						public void onBtnClick() {
				    							dialog.dismiss();
				    						}
				                        });
							}else{
								KingTellerPromptDialogUtils.showOnePromptDialog(dialog, "退回失败!" + data.getCode(),
				    					new OnBtnClickL() {
				    						@Override
				    						public void onBtnClick() {
				    							dialog.dismiss();
				    						}
				                        });
							}	 
						}
						
					};
				});
		
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		KingTellerApplication.removeActivity(this);
	}
}
