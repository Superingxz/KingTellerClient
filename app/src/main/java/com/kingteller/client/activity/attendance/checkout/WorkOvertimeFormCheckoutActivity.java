package com.kingteller.client.activity.attendance.checkout;

import java.util.Arrays;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kingteller.R;
import com.kingteller.client.KingTellerApplication;
import com.kingteller.client.bean.attendance.OverTimeBean;
import com.kingteller.client.bean.attendance.OverTimePeopleBean;
import com.kingteller.client.bean.attendance.OverTimeSonPeople;
import com.kingteller.client.config.KingTellerStaticConfig;
import com.kingteller.client.config.KingTellerUrlConfig;
import com.kingteller.client.netauth.AjaxHttpCallBack;
import com.kingteller.client.netauth.KTHttpClient;
import com.kingteller.client.utils.KingTellerConfigUtils;
import com.kingteller.client.utils.KingTellerJsonUtils;
import com.kingteller.client.utils.KingTellerJudgeUtils;
import com.kingteller.client.view.dialog.NormalDialog;
import com.kingteller.client.view.dialog.NormalListDialog;
import com.kingteller.client.view.dialog.listener.OnBtnClickL;
import com.kingteller.client.view.dialog.listener.OnOperItemClickL;
import com.kingteller.client.view.dialog.use.KingTellerProgressUtils;
import com.kingteller.client.view.dialog.use.KingTellerPromptDialogUtils;
import com.kingteller.client.view.groupview.itemview.OverTimePeopleGroupView;
import com.kingteller.client.view.groupview.itemview.OverTimePeopleHistoryGroupView;
import com.kingteller.client.view.groupview.itemview.OverTimePeopleGroupView.EditTextUseTimeViewCallBack;
import com.kingteller.client.view.groupview.listview.OverTimePeopleGroupListView;
import com.kingteller.client.view.toast.T;
import com.kingteller.framework.http.AjaxParams;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class WorkOvertimeFormCheckoutActivity extends Activity implements OnClickListener{
	
	private TextView title_text;
	private Button title_left_btn, audit_ty, audit_th;
	private Context mContext;
	
	private OverTimePeopleGroupListView jbyy_group_list;
	private OverTimePeopleGroupListView ldyj_group_list;
	
	private TextView overtimeType;
	private TextView overtimeName;
	private TextView overtimeSubsidyWay;
	private TextView overtimeSelectDay;
	private TextView overtimeSelectStartTime;
	private TextView overtimeSelectEndTime;
	private EditText overtimeUseTime;
	private EditText overtimeWhy;
	
	private TextView oa_attendance_audit_Csy;
	private EditText oa_attendance_audit_Yj;
	
	private TextView oa_attendance_audit_sqdh;
	private TextView oa_attendance_audit_sqry;
	private TextView oa_attendance_audit_sqrq;
	
	private int isCheckout = 0;// 0：查看报告	1：审批报告
	
	private String mOvertimeData_str;			//加班单数据
	private OverTimeBean mOverTimeData;			//初始数据
	
	private String[] mSpyj_StringItem;
	private List<String> mSpyj_StringItemList;
	
	private NormalDialog dialog = null; 	//初始化通用dialog
	
	@Override
	public void onCreate(Bundle paramBundle) {
		super.onCreate(paramBundle);
		setContentView(R.layout.layout_work_attendance_overtime_form);
		KingTellerApplication.addActivity(this);
		
		mContext = WorkOvertimeFormCheckoutActivity.this;
		
		isCheckout = getIntent().getIntExtra("isCheckout", 0);
		mOvertimeData_str = (String) getIntent().getStringExtra("overtimeData");
		mOverTimeData = KingTellerJsonUtils.getPerson(mOvertimeData_str, OverTimeBean.class);
		
		KingTellerStaticConfig.OA_ADUIT_ATTENDANCE_ISUDATE = false;
		
		initUI();
		initData();
	}
	
	@SuppressLint("ResourceAsColor")
	private void initUI() {
		title_text = (TextView) findViewById(R.id.layout_main_text);
		title_left_btn = (Button) findViewById(R.id.layout_main_left_btn);
		
		title_left_btn.setBackgroundResource(R.drawable.btn_back_arrow);
		title_left_btn.setOnClickListener(this);
		
		overtimeType = (TextView) findViewById(R.id.overtimeType);
		overtimeName = (TextView) findViewById(R.id.overtimeName);
		overtimeSubsidyWay = (TextView) findViewById(R.id.overtimeSubsidyWay);
		overtimeSelectDay = (TextView) findViewById(R.id.overtimeSelectDay);
		overtimeSelectStartTime = (TextView) findViewById(R.id.overtimeSelectStartTime);
		overtimeSelectEndTime = (TextView) findViewById(R.id.overtimeSelectEndTime);
		overtimeUseTime = (EditText) findViewById(R.id.overtimeUseTime);
		overtimeWhy = (EditText) findViewById(R.id.overtimeWhy);
		overtimeWhy.setTextColor(Color.parseColor("#FF666666"));
		overtimeWhy.setFocusable(false);
		overtimeWhy.setEnabled(false);
		
		oa_attendance_audit_sqdh = (TextView) findViewById(R.id.oa_attendance_audit_sqdh);
		oa_attendance_audit_sqry = (TextView) findViewById(R.id.oa_attendance_audit_sqry);
		oa_attendance_audit_sqrq = (TextView) findViewById(R.id.oa_attendance_audit_sqrq);
		
		if(isCheckout == 0){
			title_text.setText("加班单查看");
			findViewById(R.id.layout_attendance_audit_djxx_common).setVisibility(View.VISIBLE);//单号信息
			findViewById(R.id.layout_attendance_audit_ldyj_common).setVisibility(View.VISIBLE);//领导意见
			findViewById(R.id.oa_aduit_spyj_layout).setVisibility(View.GONE);//审批意见
			findViewById(R.id.oa_ouvertime_aduit_btn_layout).setVisibility(View.GONE);//审批按钮
		}else{
			title_text.setText("加班单审批");
			findViewById(R.id.layout_attendance_audit_djxx_common).setVisibility(View.VISIBLE);
			findViewById(R.id.layout_attendance_audit_ldyj_common).setVisibility(View.VISIBLE);
			findViewById(R.id.oa_aduit_spyj_layout).setVisibility(View.VISIBLE);
			findViewById(R.id.oa_ouvertime_aduit_btn_layout).setVisibility(View.VISIBLE);
			
			audit_ty = (Button) findViewById(R.id.attendance_audit_ty);
			audit_ty.setOnClickListener(this);
			audit_th = (Button) findViewById(R.id.attendance_audit_th);
			audit_th.setOnClickListener(this);
		}
		
		//多人list
		jbyy_group_list = (OverTimePeopleGroupListView) findViewById(R.id.jbyy_group_list);
		
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
		
		if("1".equals(mOverTimeData.getOvertimeType())){ //单人
			jbyy_group_list.setGroupListViewHidden(true);
			
			findViewById(R.id.overtimeName_layout).setVisibility(View.VISIBLE);
			findViewById(R.id.overtimeSubsidyWay_layout).setVisibility(View.VISIBLE);
		}else{
			jbyy_group_list.setGroupListViewHidden(false);
    		jbyy_group_list.setAddBtnHidden(1);
			jbyy_group_list.setTitleStart(1);
			
			findViewById(R.id.overtimeName_layout).setVisibility(View.GONE);
    		findViewById(R.id.overtimeSubsidyWay_layout).setVisibility(View.GONE);
		}

		setData();

		KingTellerConfigUtils.hideInputMethod(this);
	}
	
	public void setData() {
		//单号信息 
		oa_attendance_audit_sqdh.setText(mOverTimeData.getOvertimeNo()); 
		oa_attendance_audit_sqry.setText(mOverTimeData.getCreateUserName());
		oa_attendance_audit_sqrq.setText(mOverTimeData.getCreateTimeStr());

		//设置基本信息
		if("1".equals(mOverTimeData.getOvertimeType())){//设置个人信息
			overtimeType.setText("个人"); 
			overtimeName.setText(mOverTimeData.getSonList().get(0).getSonUserName());

			overtimeSelectDay.setText(mOverTimeData.getOvertimeDayExt());
			overtimeSelectStartTime.setText(mOverTimeData.getStartTime());
			overtimeSelectEndTime.setText(mOverTimeData.getEndTime());
			
			overtimeSubsidyWay.setText("0".equals(mOverTimeData.getSonList().get(0).getRewardType()) ? "补休" : "计薪");
			overtimeUseTime.setText(String.valueOf(Float.parseFloat(mOverTimeData.getSonList().get(0).getSonHour())));

			overtimeWhy.setText(mOverTimeData.getOvertimeReason());
			
			if(isCheckout == 0){
				overtimeUseTime.setTextColor(Color.parseColor("#FF666666"));
				overtimeUseTime.setFocusable(false);
				overtimeUseTime.setEnabled(false);
			}else{
				//输入加班时间   后
				overtimeUseTime.addTextChangedListener(new TextWatcher() {
					@Override
					public void onTextChanged(CharSequence s, int start, int before, int count) {//在输入数据时监听
					}
					@Override
					public void beforeTextChanged(CharSequence s, int start, int count, int after) {//输入数据之前的监听
					}
					@Override
					public void afterTextChanged(final Editable s) {//输入数据之后监听
						
						new Handler().postDelayed(new Runnable() {
				            @Override
				            public void run() {
								String cut = s.toString().substring(s.toString().indexOf(".") + 1);//获取小数点 后的字符
								float mrtime = Float.parseFloat(mOverTimeData.getOvertimeHourOne());
								float dqtime = Float.parseFloat(mOverTimeData.getSonList().get(0).getSonHour());
								
								if("".equals(s.toString())){
									overtimeUseTime.setText(String.valueOf(dqtime));
									T.showShort(mContext, "加班时长不能为空!");
									return;
								}
								
								if(".".equals(s.toString())){
									overtimeUseTime.setText(String.valueOf(dqtime));
									T.showShort(mContext, "加班时长不符合规格!");
									return;
								}
								

								float txtime = Float.parseFloat(s.toString());
								
								if(txtime <= 0){
									overtimeUseTime.setText(String.valueOf(dqtime));
									T.showShort(mContext, "加班时长不能小于等于0!");
									return;
								}
								
								if(txtime > mrtime){
									overtimeUseTime.setText(String.valueOf(dqtime));
									T.showShort(mContext, "加班时长不能大于 " + mrtime + " !");
									return;
								} 
								if(txtime % 0.5 != 0 || cut.length() >= 2 && cut.length() > 0){
									overtimeUseTime.setText(String.valueOf(dqtime));
									T.showShort(mContext, "加班时长不符合规格!");
									return;
								}
								
								if(txtime != dqtime){
									mOverTimeData.getSonList().get(0).setSonHour(String.valueOf(txtime));
									overtimeUseTime.setText(String.valueOf(txtime));
								}
				            }
				        }, 5000);
					}
				});
					
			}
			
		}else{//设置多人信息
			overtimeType.setText("多人"); 
			
			overtimeSelectDay.setText(mOverTimeData.getOvertimeDayExt());
			overtimeSelectStartTime.setText(mOverTimeData.getStartTime());
			overtimeSelectEndTime.setText(mOverTimeData.getEndTime());
			
			overtimeUseTime.setText(String.valueOf(Float.parseFloat(mOverTimeData.getOvertimeHour())));
			overtimeUseTime.setTextColor(Color.parseColor("#FF666666"));
			overtimeUseTime.setFocusable(false);
			overtimeUseTime.setEnabled(false);
			
			overtimeWhy.setText(mOverTimeData.getOvertimeReason());
			overtimeWhy.setTextColor(Color.parseColor("#FF666666"));
			overtimeWhy.setFocusable(false);
			overtimeWhy.setEnabled(false);

			for(int i = 0; i < mOverTimeData.getSonList().size(); i++){
				OverTimePeopleGroupView peopleView = new OverTimePeopleGroupView(mContext, true, 1);
		
				if(isCheckout == 0){
					peopleView.findViewById(R.id.overtimereUsed).setFocusable(false);
				}else{
					//填写 加班时间 回调
					peopleView.setEditTextUseTimeViewCallBack(new EditTextUseTimeViewCallBack() {
						@Override
						public void editTextUseTimeView(OverTimePeopleGroupView view, String text) {
							
							String cut = text.substring(text.indexOf(".") + 1);//获取小数点 后的字符
							int item = getNumPeopleList(view);
							float mrtime = Float.parseFloat(mOverTimeData.getOvertimeHourOne());
							float dqtime = Float.parseFloat(mOverTimeData.getSonList().get(item).getSonHour());
							
							
							if("".equals(text)){
								((TextView) view.findViewById(R.id.overtimereUsed)).setText(String.valueOf(dqtime));
								T.showShort(mContext, "加班时长不能为空!");
								return;
							}
							
							if(".".equals(text)){
								((TextView) view.findViewById(R.id.overtimereUsed)).setText(String.valueOf(dqtime));
								T.showShort(mContext, "加班时长不符合规格!");
								return;
							}
							
	
							float txtime = Float.parseFloat(text);
							
							if(txtime <= 0){
								((TextView) view.findViewById(R.id.overtimereUsed)).setText(String.valueOf(dqtime));
								T.showShort(mContext, "加班时长不能小于等于0!");
								return;
							}
							
							if(txtime > mrtime){
								((TextView) view.findViewById(R.id.overtimereUsed)).setText(String.valueOf(dqtime));
								T.showShort(mContext, "加班时长不能大于 " + mrtime + " !");
								return;
							} 
							if(txtime % 0.5 != 0 || cut.length() >= 2 && cut.length() > 0){
								((TextView) view.findViewById(R.id.overtimereUsed)).setText(String.valueOf(dqtime));
								T.showShort(mContext, "加班时长不符合规格!");
								return;
							}
							
							if(txtime != dqtime){
								mOverTimeData.getSonList().get(item).setSonHour(String.valueOf(txtime));
								getMoreTotalTime(mOverTimeData.getSonList());
							}
							
						}
					});
				}
				peopleView.findViewById(R.id.add_oa_people_layout).setEnabled(false);
				peopleView.findViewById(R.id.add_oa_ward_layout).setEnabled(false);
				

				
				OverTimePeopleBean people_data = new OverTimePeopleBean(
						mOverTimeData.getSonList().get(i).getSonUserId(),
						mOverTimeData.getSonList().get(i).getSonUserName(),
						mOverTimeData.getSonList().get(i).getSonUserAccount(),
						"0".equals(mOverTimeData.getSonList().get(i).getRewardType()) ? "补休" : "计薪",
						String.valueOf(Float.parseFloat(mOverTimeData.getSonList().get(i).getSonHour())),
						mOverTimeData.getSonList().get(i).getCardTime());
				
				peopleView.setData(people_data);
				jbyy_group_list.addItem(peopleView);
			}
			jbyy_group_list.setSelectData("人员信息(" + mOverTimeData.getSonList().size() + ")");
		}
		
		
		//处理历史
		for(int i = 0; i < mOverTimeData.getHistoryList().size(); i++){
			OverTimePeopleHistoryGroupView leaderView = new OverTimePeopleHistoryGroupView(mContext);
			leaderView.setData(mOverTimeData.getHistoryList().get(i).getExetime(), 
					mOverTimeData.getHistoryList().get(i).getExeuser(), 
					mOverTimeData.getHistoryList().get(i).getTitle(), 
					mOverTimeData.getHistoryList().get(i).getTt(),
					mOverTimeData.getHistoryList().get(i).getSuggestion());
			
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
		case R.id.attendance_audit_ty:
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
	                    	setAduitOvertime(2);
	                    }
                });
			break;
		case R.id.attendance_audit_th:
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
	                    	setAduitOvertime(3);
	                    }
                });
			break;
		}
	}
	
	/**
	 * 2同意 或  3退回
	 */
	public void setAduitOvertime(final int num) {
		if (mOverTimeData == null) {
			return;
		}
		
		//上传数据
		String saveflag = "";
		if(num == 2){
			saveflag = "audit";
		}else{
			saveflag = "back";
		}
		
		if(KingTellerJudgeUtils.isEmpty(overtimeUseTime.getText().toString())){
			T.showShort(mContext, "请输入加班时长!");
			return;
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
		params.put("billType", "kqmgrOvertimeFlow");
		
		
		params.put("createUserId", mOverTimeData.getCreateUserId());
		params.put("createUserName", mOverTimeData.getCreateUserName());
		params.put("createTimeStr", mOverTimeData.getCreateTimeStr());
		params.put("flowStatus", mOverTimeData.getFlowStatus());
		params.put("overtimeId", mOverTimeData.getOvertimeId());
		params.put("overtimeNo", mOverTimeData.getOvertimeNo());
		params.put("overtimeType", mOverTimeData.getOvertimeType());
		
		params.put("overtimeDayExt", overtimeSelectDay.getText().toString());
		params.put("startTime", overtimeSelectStartTime.getText().toString());
		params.put("endTime", overtimeSelectEndTime.getText().toString());
		params.put("overtimeReason", overtimeWhy.getText().toString());
		params.put("exeRemark", yj);
		
		if("1".equals(mOverTimeData.getOvertimeType())){
			params.put("overtimeHourOne", overtimeUseTime.getText().toString());
			params.put("overtimeHour", overtimeUseTime.getText().toString());

			params.put("sonList", new Gson().toJson(mOverTimeData.getSonList()));
		}else{
			params.put("overtimeHourOne", mOverTimeData.getOvertimeHourOne());
			params.put("overtimeHour", overtimeUseTime.getText().toString());
			
			params.put("sonList", new Gson().toJson(mOverTimeData.getSonList()));
		}

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
	
	/**
	 * 获取  多人list view num
	 * @param name
	 * @param account
	 * @return
	 */
	public int getNumPeopleList(OverTimePeopleGroupView view) {
		for(int i = 0; i< jbyy_group_list.getListData().size(); i++ ){
			if(view == (OverTimePeopleGroupView) jbyy_group_list.getLayoutList().getChildAt(i)){
				return i;
			}
		}
		return 0;
	}
	
	/**
	 * 获取多人  合计时间
	 * @param num
	 */
	public void getMoreTotalTime(List<OverTimeSonPeople> moreList) {
		float counttime = 0;
		for(int i = 0; i< moreList.size(); i++){
			counttime = counttime + Float.parseFloat(moreList.get(i).getSonHour());
		}
		overtimeUseTime.setText(String.valueOf(counttime));
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		KingTellerApplication.removeActivity(this);
	}
}
