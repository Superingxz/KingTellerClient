package com.kingteller.client.activity.attendance.fillin;

import java.util.Calendar;
import java.util.List;

import com.google.gson.reflect.TypeToken;
import com.kingteller.R;
import com.kingteller.client.KingTellerApplication;
import com.kingteller.client.activity.attendance.search.WorkAttendanceSearchLeaveFromActivity;
import com.kingteller.client.activity.attendance.search.WorkAttendanceSearchPersonnelActivity;
import com.kingteller.client.bean.account.User;
import com.kingteller.client.bean.attendance.BusinessTripBean;
import com.kingteller.client.bean.attendance.SickLeaveBean;
import com.kingteller.client.bean.attendance.WorkAttendanceGetUserTimeBean;
import com.kingteller.client.bean.attendance.WorkAttendanceSearchLeaveFromBean;
import com.kingteller.client.bean.attendance.WorkAttendanceSearchPeopleBean;
import com.kingteller.client.config.KingTellerStaticConfig;
import com.kingteller.client.config.KingTellerUrlConfig;
import com.kingteller.client.netauth.AjaxHttpCallBack;
import com.kingteller.client.netauth.KTHttpClient;
import com.kingteller.client.utils.KingTellerConfigUtils;
import com.kingteller.client.utils.KingTellerJsonUtils;
import com.kingteller.client.utils.KingTellerJudgeUtils;
import com.kingteller.client.utils.KingTellerTimeUtils;
import com.kingteller.client.view.calendar.CalendarDay;
import com.kingteller.client.view.dialog.NormalDialog;
import com.kingteller.client.view.dialog.NormalListDialog;
import com.kingteller.client.view.dialog.listener.OnBtnClickL;
import com.kingteller.client.view.dialog.listener.OnBtnSelectDateClickL;
import com.kingteller.client.view.dialog.listener.OnBtnSelectTimeClickL;
import com.kingteller.client.view.dialog.listener.OnOperItemClickL;
import com.kingteller.client.view.dialog.use.KingTellerDateTimeDialogUtils;
import com.kingteller.client.view.dialog.use.KingTellerProgressUtils;
import com.kingteller.client.view.dialog.use.KingTellerPromptDialogUtils;
import com.kingteller.client.view.groupview.itemview.OverTimePeopleHistoryGroupView;
import com.kingteller.client.view.groupview.listview.OverTimePeopleGroupListView;
import com.kingteller.client.view.toast.T;
import com.kingteller.framework.http.AjaxParams;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

/**
 * 销假填写
 * @author Administrator
 */
public class WorkSickLeaveFromActivity extends Activity implements OnClickListener{
	
	private TextView title_text;
	private Button title_left_btn, title_rightone_btn, title_righttwo_btn;
	private Context mContext;
	
	private OverTimePeopleGroupListView ldyj_group_list;
	
	private TextView oa_attendance_audit_sqdh;
	private TextView oa_attendance_audit_sqry;
	private TextView oa_attendance_audit_sqrq;
	
	private TextView leaveSickName;
	private TextView leaveSickNumber;
	private TextView leaveSickStartDay;
	private TextView leaveSickStratTime;
	private TextView leaveSickEndDay;
	private TextView leaveSickEndTime;
	private TextView leaveSickUserTime;
	private EditText leaveSickWhy;
	
	private TextView leaveStartTimeStr;
	private TextView leaveEndTimeStr;
	private TextView leaveTimes;
	
	private String mSickLeaveData_str;				//销假单数据
	private SickLeaveBean mSickLeaveData;			//初始数据
	
	private NormalDialog dialog = null; 	//初始化通用dialog
	
	private Calendar calendar = null;
	
	private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg){
            super.handleMessage(msg);
            String code = msg.getData().getString("code");
            if(msg.what == 1){
            	
            	leaveSickStartDay.setText(KingTellerTimeUtils.getYearMonthDay(mSickLeaveData.getStartTime(), 1, 2));
            	leaveSickStratTime.setText(KingTellerTimeUtils.getYearMonthDay(mSickLeaveData.getStartTime(), 1, 3));
            	leaveSickEndDay.setText(KingTellerTimeUtils.getYearMonthDay(mSickLeaveData.getEndTime(), 1, 2));
            	leaveSickEndTime.setText(KingTellerTimeUtils.getYearMonthDay(mSickLeaveData.getEndTime(), 1, 3));
				
            	leaveSickUserTime.setText(String.valueOf(Float.parseFloat(mSickLeaveData.getSickTimes())));

            }else if(msg.what == 2){
            	
            	leaveSickStartDay.setText("日期");
            	leaveSickStratTime.setText("时间");
            	leaveSickEndDay.setText("日期");
            	leaveSickEndTime.setText("时间");
				
            	leaveSickUserTime.setText("0.0");
            	
            	dialog = new NormalDialog(mContext);
            	KingTellerPromptDialogUtils.showOnePromptDialog(dialog, code,
    					new OnBtnClickL() {
    						@Override
    						public void onBtnClick() {
    							dialog.dismiss();
    						}
                        });
            }
        }
    };
	@Override
	public void onCreate(Bundle paramBundle) {
		super.onCreate(paramBundle);
		setContentView(R.layout.layout_work_attendance_general);
		KingTellerApplication.addActivity(this);
		
		mContext = WorkSickLeaveFromActivity.this;

		mSickLeaveData_str = (String) getIntent().getStringExtra("mSickLeaveData");
		
		KingTellerStaticConfig.OA_WORK_ATTENDANCE_ISUDATE = false;
		
		initUI();
		initData();
	}
	
	private void initUI() {
		title_text = (TextView) findViewById(R.id.layout_main_text);
		title_left_btn = (Button) findViewById(R.id.layout_main_left_btn);
		title_rightone_btn = (Button) findViewById(R.id.layout_main_rightone_btn);
		title_righttwo_btn = (Button) findViewById(R.id.layout_main_righttwo_btn);

		title_text.setText("销假单填写");
		
		title_left_btn.setBackgroundResource(R.drawable.btn_back_arrow);
		title_left_btn.setOnClickListener(this);
		
		title_rightone_btn.setBackgroundResource(R.drawable.btn_save);
		title_rightone_btn.setOnClickListener(this);
		
		title_righttwo_btn.setBackgroundResource(R.drawable.btn_check);
		title_righttwo_btn.setOnClickListener(this);
		
		oa_attendance_audit_sqdh = (TextView) findViewById(R.id.oa_attendance_audit_sqdh);
		oa_attendance_audit_sqry = (TextView) findViewById(R.id.oa_attendance_audit_sqry);
		oa_attendance_audit_sqrq = (TextView) findViewById(R.id.oa_attendance_audit_sqrq);
		
		findViewById(R.id.oa_qjlx_layout).setVisibility(View.GONE);//隐藏 请假类型
		findViewById(R.id.oa_czmdd_layout).setVisibility(View.GONE);//隐藏 出差目的地
		
		((TextView) findViewById(R.id.oa_work_people_name)).setText("销假人员：");
		leaveSickName = (TextView) findViewById(R.id.oa_work_people);
		
		leaveSickNumber = (TextView) findViewById(R.id.leaveNumber);
		
		leaveTimes = (TextView) findViewById(R.id.oa_use_sick_time);
		leaveStartTimeStr = (TextView) findViewById(R.id.oa_start_sick_time);
		leaveEndTimeStr = (TextView) findViewById(R.id.oa_end_sick_time);
		
		((TextView) findViewById(R.id.oa_start_time_name)).setText("销假开始时间：");
		leaveSickStartDay = (TextView) findViewById(R.id.oa_start_day);
		leaveSickStratTime = (TextView) findViewById(R.id.oa_start_time);
		
		((TextView) findViewById(R.id.oa_end_time_name)).setText("销假结束时间：");
		leaveSickEndDay = (TextView) findViewById(R.id.oa_end_day);
		leaveSickEndTime = (TextView) findViewById(R.id.oa_end_time);
		
		((TextView) findViewById(R.id.oa_all_usertime_name)).setText("销假时长：");
		leaveSickUserTime = (TextView) findViewById(R.id.oa_all_usertime);
		
		((TextView) findViewById(R.id.oa_all_why_name)).setText("销假原因：");
		leaveSickWhy = (EditText) findViewById(R.id.oa_all_why);
		
		leaveSickName.setOnClickListener(this);
		leaveSickNumber.setOnClickListener(this);
		leaveSickStartDay.setOnClickListener(this);
		leaveSickStratTime.setOnClickListener(this);
		leaveSickEndDay.setOnClickListener(this);
		leaveSickEndTime.setOnClickListener(this);

		//处理历史
		ldyj_group_list = (OverTimePeopleGroupListView) findViewById(R.id.ldyj_group_list);
		ldyj_group_list.setGroupListTwoViewHidden(true);
		ldyj_group_list.setAddBtnHidden(1);

	}
	
	public void initData() {
		if("".equals(mSickLeaveData_str)){//新建
			mSickLeaveData = new SickLeaveBean();
			mSickLeaveData.setSickUserId(User.getInfo(this).getUserId());
			mSickLeaveData.setSickUserName(User.getInfo(this).getName());
			mSickLeaveData.setSickUserAccount(User.getInfo(this).getUserName());
		}else{//修改
			mSickLeaveData = KingTellerJsonUtils.getPerson(mSickLeaveData_str, SickLeaveBean.class);
		}
		
		mSickLeaveData.setNextUserAccounts("");
		setData(mSickLeaveData);
	
		KingTellerConfigUtils.hideInputMethod(this);
	}
	
	private void setData(SickLeaveBean data) {
		//初始化单号信息
		if(data.getSickId() != null){
			findViewById(R.id.layout_attendance_audit_djxx_common).setVisibility(View.VISIBLE);
			oa_attendance_audit_sqdh.setText(data.getSickNo()); 
			oa_attendance_audit_sqry.setText(data.getCreateUserName());
			oa_attendance_audit_sqrq.setText(data.getCreateTimeStr());
		}else{
			findViewById(R.id.layout_attendance_audit_djxx_common).setVisibility(View.GONE);
		}
		
		//初始化处理历史
		if(data.getHistoryList() != null && data.getHistoryList().size() >0){
			findViewById(R.id.layout_attendance_audit_ldyj_common).setVisibility(View.VISIBLE);
			for(int i = 0; i < data.getHistoryList().size(); i++){
				OverTimePeopleHistoryGroupView leaderView = new OverTimePeopleHistoryGroupView(mContext);
				leaderView.setData(data.getHistoryList().get(i).getExetime(), 
						data.getHistoryList().get(i).getExeuser(), 
						data.getHistoryList().get(i).getTitle(), 
						data.getHistoryList().get(i).getTt(),
						data.getHistoryList().get(i).getSuggestion());
				
				ldyj_group_list.addItem(leaderView);
			}
			ldyj_group_list.setSelectData("处理历史");
		}else{
			findViewById(R.id.layout_attendance_audit_ldyj_common).setVisibility(View.GONE);
		}
		
		//初始化数据
		leaveSickName.setText(data.getSickUserName() + "(" + data.getSickUserAccount() + ")");
		leaveSickNumber.setText(KingTellerJudgeUtils.isEmpty(data.getLeaveNo()) ? "请选择请假单号" : data.getLeaveNo());
		if("请选择请假单号".equals(leaveSickNumber.getText().toString())){
			findViewById(R.id.oa_qjdh_time_layout).setVisibility(View.GONE);
		}else{
			findViewById(R.id.oa_qjdh_time_layout).setVisibility(View.VISIBLE);
			leaveTimes.setText(String.valueOf(Float.parseFloat(data.getLeaveTimes())));
			leaveStartTimeStr.setText(data.getLeaveStartTimeStr());
			leaveEndTimeStr.setText(data.getLeaveEndTimeStr());
		}
		if(KingTellerJudgeUtils.isEmpty(data.getStartTime())){
			leaveSickStartDay.setText("日期");
			leaveSickStratTime.setText("时间");
		}else{
			leaveSickStartDay.setText(KingTellerTimeUtils.getYearMonthDay(data.getStartTime(), 1, 2));
			leaveSickStratTime.setText(KingTellerTimeUtils.getYearMonthDay(data.getStartTime(), 1, 3));
		}
		if(KingTellerJudgeUtils.isEmpty(data.getEndTime())){
			leaveSickEndDay.setText("日期");
			leaveSickEndTime.setText("时间");
		}else{
			leaveSickEndDay.setText(KingTellerTimeUtils.getYearMonthDay(data.getEndTime(), 1, 2));
			leaveSickEndTime.setText(KingTellerTimeUtils.getYearMonthDay(data.getEndTime(), 1, 3));
		}
		leaveSickUserTime.setText(KingTellerJudgeUtils.isEmpty(data.getSickTimes()) ? "0.0" :  data.getSickTimes());
		leaveSickWhy.setText(KingTellerJudgeUtils.isEmpty(data.getSickReason()) ? "" :  data.getSickReason());
	
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
			case KingTellerStaticConfig.OACODE_SELECT_LEAVEFROM_NUM:
				if (resultCode == RESULT_OK) {
					String pData = (String) data.getSerializableExtra("LeaveFromData");
					WorkAttendanceSearchLeaveFromBean leavedata = KingTellerJsonUtils.getPerson(pData, WorkAttendanceSearchLeaveFromBean.class);
					
					findViewById(R.id.oa_qjdh_time_layout).setVisibility(View.VISIBLE);
					mSickLeaveData.setLeaveId(leavedata.getLeaveId());
					mSickLeaveData.setLeaveNo(leavedata.getLeaveNo());
					mSickLeaveData.setLeaveTimes(leavedata.getLeaveTimes()); 
					mSickLeaveData.setLeaveStartTimeStr(leavedata.getLeaveStartTimeStr()); 
					mSickLeaveData.setLeaveEndTimeStr(leavedata.getLeaveEndTimeStr()); 
					
					leaveSickNumber.setText(mSickLeaveData.getLeaveNo());
					leaveTimes.setText(String.valueOf(Float.parseFloat(mSickLeaveData.getLeaveTimes())));
					leaveStartTimeStr.setText(mSickLeaveData.getLeaveStartTimeStr());
					leaveEndTimeStr.setText(mSickLeaveData.getLeaveEndTimeStr());
					
					String startDay_1 = KingTellerTimeUtils.getYearMonthDay(mSickLeaveData.getLeaveStartTimeStr(), 1, 2);
					String startTime_1 = KingTellerTimeUtils.getYearMonthDay(mSickLeaveData.getLeaveStartTimeStr(), 1, 3);
					String endDay_1 = KingTellerTimeUtils.getYearMonthDay(mSickLeaveData.getLeaveEndTimeStr(), 1, 2);
					String endTime_1 = KingTellerTimeUtils.getYearMonthDay(mSickLeaveData.getLeaveEndTimeStr(), 1, 3);
					
					if(!"日期".equals(startDay_1) && !"日期".equals(endDay_1) && !"时间".equals(startTime_1) && !"时间".equals(endTime_1)){
						getSickLeaveUserTime(mSickLeaveData.getSickUserId(), startDay_1 + " " + startTime_1, endDay_1 + " " + endTime_1);
					}

				}
				break;
			case KingTellerStaticConfig.OACODE_SELECT_PEOPLE_NUM:
				if (resultCode == RESULT_OK) {
					String pType = (String) data.getSerializableExtra("peopleType");
					String pData = (String) data.getSerializableExtra("peopleData");
					List<WorkAttendanceSearchPeopleBean> list = KingTellerJsonUtils.getPersons(pData, WorkAttendanceSearchPeopleBean.class);
					
					if("1".equals(pType)){		//单人	没view	添加	人员
						
						mSickLeaveData.setSickUserId(list.get(0).getUserId());
						mSickLeaveData.setSickUserName(list.get(0).getUserName());
						mSickLeaveData.setSickUserAccount(list.get(0).getUserAccount());
						
						leaveSickName.setText(mSickLeaveData.getSickUserName() + "(" + mSickLeaveData.getSickUserAccount() + ")");
						
						findViewById(R.id.oa_qjdh_time_layout).setVisibility(View.GONE);
						leaveSickNumber.setText("请选择请假单号");
						leaveSickStartDay.setText("日期");
						leaveSickStratTime.setText("时间");
						leaveSickEndDay.setText("日期");
						leaveSickEndTime.setText("时间");
						leaveSickUserTime.setText("0.0");
					}

				}
				break;
			default:
				break;
		}
	}
	
	/**
	 * 设置 销假时长
	 * @param type 0: 	startDay
	 * @param type 1: 	endDay
	 * @param type 2: 	startTime
	 * @param type 3: 	endTime
	 */
	public void setSickLeaveUserTime(int type, String xzTime) {
		String dqTime = "";
		String startDay = leaveSickStartDay.getText().toString();
		String endDay = leaveSickEndDay.getText().toString();
		String startTime = leaveSickStratTime.getText().toString();
		String endTime = leaveSickEndTime.getText().toString();
		if(type == 0){
			dqTime = startDay;
			startDay = xzTime;
		}else if(type == 1){
			dqTime = endDay;
			endDay = xzTime;
		}else if(type == 2){
			dqTime = startTime;
			startTime = xzTime;
		}else{
			dqTime = endTime;
			endTime = xzTime;
		}
		
		if(!"日期".equals(startDay) && !"日期".equals(endDay) && !"时间".equals(startTime) && !"时间".equals(endTime)){
			float hour = KingTellerTimeUtils.getBeApartHour(startDay + " " + startTime, endDay + " " + endTime, 1);
			float hour_formLeaveStartTime = KingTellerTimeUtils.getBeApartHour(mSickLeaveData.getLeaveStartTimeStr(), startDay + " " + startTime, 1);
			float hour_formLeaveEndTime = KingTellerTimeUtils.getBeApartHour(mSickLeaveData.getLeaveEndTimeStr(), endDay + " " + endTime, 1);
			
			if(hour <= 0){
				if(type == 0){
					leaveSickStartDay.setText(dqTime);
				}else if(type == 1){
					leaveSickEndDay.setText(dqTime);
				}else if(type == 2){
					leaveSickStratTime.setText(dqTime);
				}else{
					leaveSickEndTime.setText(dqTime);
				}
				
				T.showShort(mContext, "所选的时间差不能小于等于0!");
			}else if(hour_formLeaveStartTime < 0){
				if(type == 0){
					leaveSickStartDay.setText(dqTime);
				}else if(type == 1){
					leaveSickEndDay.setText(dqTime);
				}else if(type == 2){
					leaveSickStratTime.setText(dqTime);
				}else{
					leaveSickEndTime.setText(dqTime);
				}
				
				T.showShort(mContext, "销假开始时间  不能小于  请假开始时间!");
				
			}else if(hour_formLeaveEndTime > 0){
				if(type == 0){
					leaveSickStartDay.setText(dqTime);
				}else if(type == 1){
					leaveSickEndDay.setText(dqTime);
				}else if(type == 2){
					leaveSickStratTime.setText(dqTime);
				}else{
					leaveSickEndTime.setText(dqTime);
				}
				
				T.showShort(mContext, "销假结束时间  不能大于  请假结束时间!");
				
			}else if(!mSickLeaveData.getLeaveStartTimeStr().equals(startDay + " " + startTime) &&  
					!mSickLeaveData.getLeaveEndTimeStr().equals(endDay + " " + endTime)){
				
				if(type == 0){
					leaveSickStartDay.setText(dqTime);
				}else if(type == 1){
					leaveSickEndDay.setText(dqTime);
				}else if(type == 2){
					leaveSickStratTime.setText(dqTime);
				}else{
					leaveSickEndTime.setText(dqTime);
				}
				
				T.showShort(mContext, "销假开始时间、销假结束时间, 至少要有一个等于请假开始时间、请假结束时间!");
			}else{
				getSickLeaveUserTime(mSickLeaveData.getSickUserId(), startDay + " " + startTime, endDay + " " + endTime);
			}
		}else{
			if(type == 0){
				leaveSickStartDay.setText(xzTime);
			}else if(type == 1){
				leaveSickEndDay.setText(xzTime);
			}else if(type == 2){
				leaveSickStratTime.setText(xzTime);
			}else{
				leaveSickEndTime.setText(xzTime);
			}
		}
	}
	
	/**
	 * 获取  销假时长
	 */
	public void getSickLeaveUserTime(String useId, String ksTime, String jsTime) {
		
		KTHttpClient fh = new KTHttpClient(true);
		AjaxParams params = new AjaxParams();
		
		params.put("userId", useId);
		params.put("beginDateTime", ksTime);
		params.put("endDateTime", jsTime);

		fh.post(KingTellerConfigUtils.CreateUrl(this, KingTellerUrlConfig.GetOAUserTimeDate), params, 
				new AjaxHttpCallBack<WorkAttendanceGetUserTimeBean>(this,
						new TypeToken<WorkAttendanceGetUserTimeBean>() {
						}.getType(), true) {
			
					@Override
					public void onStart() {
						KingTellerProgressUtils.showProgress(mContext, "正在获取中...");
					}
					
					@Override
					public void onError(int errorNo, String strMsg) {
						super.onError(errorNo, strMsg);
						KingTellerProgressUtils.closeProgress();
						T.showShort(mContext, "数据访问超时!");
					}
					
					@Override
					public void onDo(WorkAttendanceGetUserTimeBean data) {
						KingTellerProgressUtils.closeProgress();
						Message message = new Message();
			        	Bundle bundleData = new Bundle();  
			        	
						if("".equals(data.getCode())){	
							mSickLeaveData.setSickTimes(data.getTotalHour());
							mSickLeaveData.setSickDay(data.getDay());
							mSickLeaveData.setSickHour(data.getHour());
							mSickLeaveData.setStartTime(data.getNewBeginDateTime());
							mSickLeaveData.setEndTime(data.getNewEndDateTime());
							
							mSickLeaveData.setFirstDayHour(data.getBeginHour());
							mSickLeaveData.setEndDayHour(data.getEndHour());
							
							message.what = 1;
						}else{
							mSickLeaveData.setSickTimes("");
							mSickLeaveData.setSickDay("");
							mSickLeaveData.setSickHour("");
							mSickLeaveData.setStartTime("");
							mSickLeaveData.setEndTime("");
							
							mSickLeaveData.setFirstDayHour("");
							mSickLeaveData.setEndDayHour("");
							message.what = 2;
						}
					
						bundleData.putString("code", data.getCode());
				        message.setData(bundleData);  
				        handler.sendMessage(message);
					};
				});
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.layout_main_left_btn:
				finish();
				break;
			case R.id.layout_main_rightone_btn:
				dialog = new NormalDialog(mContext);
				KingTellerPromptDialogUtils.showTwoPromptDialog(dialog, "你确定要暂存吗？",
						new OnBtnClickL() {
							@Override
							public void onBtnClick() {
								dialog.dismiss();
							}
	                    },new OnBtnClickL() {
		                    @Override
		                    public void onBtnClick() {
		                    	dialog.dismiss();
		                    	setSaveSickLeave(0);
		                    }
	                });
			
				break;
			case R.id.layout_main_righttwo_btn:
				dialog = new NormalDialog(mContext);
				KingTellerPromptDialogUtils.showTwoPromptDialog(dialog, "你确定要提交吗？",
						new OnBtnClickL() {
							@Override
							public void onBtnClick() {
								dialog.dismiss();
							}
	                    },new OnBtnClickL() {
		                    @Override
		                    public void onBtnClick() {
		                    	dialog.dismiss();
		                    	setSaveSickLeave(1);
		                    }
	                });
				
				break;
			case R.id.leaveNumber:
				Intent intent_leave = new Intent(mContext, WorkAttendanceSearchLeaveFromActivity.class);
				intent_leave.putExtra("SickUserId", mSickLeaveData.getSickUserId());
				startActivityForResult(intent_leave, KingTellerStaticConfig.OACODE_SELECT_LEAVEFROM_NUM);
				break;
			case R.id.oa_work_people:
				Intent intent = new Intent(mContext, WorkAttendanceSearchPersonnelActivity.class);
				intent.putExtra("peopleType", "1");
				startActivityForResult(intent, KingTellerStaticConfig.OACODE_SELECT_PEOPLE_NUM);
				break;
			case R.id.oa_start_day:
				if("请选择请假单号".equals(leaveSickNumber.getText().toString())){
					T.showShort(mContext, "请选择请假单号!");
					return;
				}
				
				String xj_ks_data = leaveSickStartDay.getText().toString();
				 
				if("日期".equals(xj_ks_data)){
					calendar = Calendar.getInstance();
				}else{
					calendar = Calendar.getInstance();
					calendar.setTime(KingTellerTimeUtils.getConversionFormatDateByString(xj_ks_data, 2));
				}
				 	 
				KingTellerDateTimeDialogUtils.showDatePickerDialog(mContext, calendar,
					new OnBtnSelectDateClickL() {
	                	@Override
	                    public void OnBtnSelectDateClick(DatePicker view, int year, int month, int day) {
	                    	//取消
	                    }
	                },new OnBtnSelectDateClickL() {
	                    @Override
	                    public void OnBtnSelectDateClick(DatePicker view, int year, int month, int day) {
	                    	//确定
	                    	String xz_ksday = KingTellerTimeUtils.getConversionFormatStringByDate(
	                    			CalendarDay.from(year, month, day).getDate(), 2);
	                    	setSickLeaveUserTime(0, xz_ksday);
	                    
	                    }
		        });
				break;
			case R.id.oa_start_time:
				if("请选择请假单号".equals(leaveSickNumber.getText().toString())){
					T.showShort(mContext, "请选择请假单号!");
					return;
				}
				
				String xj_ks_time = leaveSickStratTime.getText().toString();
				
				if("时间".equals(xj_ks_time)){
					 calendar = Calendar.getInstance();
				 }else{
					 calendar = Calendar.getInstance();
					 calendar.setTime(KingTellerTimeUtils.getConversionFormatDateByString(xj_ks_time, 3));
				 }
				
				KingTellerDateTimeDialogUtils.showTimePickerDialog(mContext, calendar,  new String [] {"00","05","10","15","20","25","30","35","40","45","50","55"},
						new OnBtnSelectTimeClickL() {
	                    	@Override
		                    public void OnBtnSelectTimeClick(TimePicker view, int hour, int minute) {
		                    	//取消
		                    }
	                    },new OnBtnSelectTimeClickL() {
		                    @Override
		                    public void OnBtnSelectTimeClick(TimePicker view, int hour, int minute) {
		                    	//确定
		                        String xz_kstime = KingTellerTimeUtils.getConversionFormatTime(hour + ":" + minute, 1);
		                        setSickLeaveUserTime(2, xz_kstime);
		                    }
	                });
				
				break;
			case R.id.oa_end_day:
				if("请选择请假单号".equals(leaveSickNumber.getText().toString())){
					T.showShort(mContext, "请选择请假单号!");
					return;
				}
				
				String xj_js_data = leaveSickEndDay.getText().toString();
				 
				if("日期".equals(xj_js_data)){
					 calendar = Calendar.getInstance();
				}else{
					 calendar = Calendar.getInstance();
					 calendar.setTime(KingTellerTimeUtils.getConversionFormatDateByString(xj_js_data, 2));
				}
				 
				 
				KingTellerDateTimeDialogUtils.showDatePickerDialog(mContext, calendar,
					new OnBtnSelectDateClickL() {
		            	@Override
		                public void OnBtnSelectDateClick(DatePicker view, int year, int month, int day) {
		                	//取消
		            	}
					},new OnBtnSelectDateClickL() {
					    @Override
					    public void OnBtnSelectDateClick(DatePicker view, int year, int month, int day) {
					    	//确定
					        	String xz_jsday = KingTellerTimeUtils.getConversionFormatStringByDate(
					        			CalendarDay.from(year, month, day).getDate(), 2);
					        	setSickLeaveUserTime(1, xz_jsday);
					        }
				});
				break;
			case R.id.oa_end_time:
				if("请选择请假单号".equals(leaveSickNumber.getText().toString())){
					T.showShort(mContext, "请选择请假单号!");
					return;
				}
				
				String xj_js_time = leaveSickEndTime.getText().toString();
				
				if("时间".equals(xj_js_time)){
					 calendar = Calendar.getInstance();
				 }else{
					 calendar = Calendar.getInstance();
					 calendar.setTime(KingTellerTimeUtils.getConversionFormatDateByString(xj_js_time, 3));
				 }
				
				KingTellerDateTimeDialogUtils.showTimePickerDialog(mContext, calendar,  new String [] {"00","05","10","15","20","25","30","35","40","45","50","55"},
					new OnBtnSelectTimeClickL() {
                    	@Override
	                    public void OnBtnSelectTimeClick(TimePicker view, int hour, int minute) {
	                    	//取消
	                    }
                    },new OnBtnSelectTimeClickL() {
	                    @Override
	                    public void OnBtnSelectTimeClick(TimePicker view, int hour, int minute) {
	                    	//确定
	                 	   String xz_jstime = KingTellerTimeUtils.getConversionFormatTime(hour + ":" + minute, 1);
	                 	  setSickLeaveUserTime(3, xz_jstime);
	                    }
                });

				break;
			default:
				break;
		}
		
	}
	
	/**
	 * 0暂存 或  1保存
	 */
	public void setSaveSickLeave(final int num) {
		if (mSickLeaveData == null) {
			return;
		}
		
		//上传数据
		String saveflag = "";
		if(num == 0){
			saveflag = "zc";
		}else{
			saveflag = "submit";
		}
		
		//限制条件
		if("请选择请假单号".equals(leaveSickNumber.getText().toString())){
			T.showShort(mContext, "请选择请假单号!");
			return;
		}
		
		if("日期".equals(leaveSickStartDay.getText().toString())){
			T.showShort(mContext, "请选择销假开始日期!");
			return;
		}
		
		if("时间".equals(leaveSickStratTime.getText().toString())){
			T.showShort(mContext, "请选择销假开始时间!");
			return;
		}
		
		if("日期".equals(leaveSickEndDay.getText().toString())){
			T.showShort(mContext, "请选择销假结束日期!");
			return;
		}
		
		if("时间".equals(leaveSickEndTime.getText().toString())){
			T.showShort(mContext, "请选择销假束时间!");
			return;
		}
		
		if(Float.parseFloat(leaveSickUserTime.getText().toString()) <= 0){
			T.showShort(mContext, "所选的时间差不能小于等于0!");
			return;
		}
		
		float hour_StartTime = KingTellerTimeUtils.getBeApartHour(mSickLeaveData.getLeaveStartTimeStr(), leaveSickStartDay.getText().toString() + " " + leaveSickStratTime.getText().toString(), 1);
		float hour_EndTime = KingTellerTimeUtils.getBeApartHour(mSickLeaveData.getLeaveEndTimeStr(), leaveSickEndDay.getText().toString() + " " + leaveSickEndTime.getText().toString(), 1);
		if(hour_StartTime < 0){
			T.showShort(mContext, "销假开始时间  不能小于  请假开始时间!");
			return;
		}
		
		if(hour_EndTime > 0){
			T.showShort(mContext, "销假结束时间  不能大于  请假结束时间!");
			return;
		}
		
		if(!mSickLeaveData.getLeaveStartTimeStr().equals(
					leaveSickStartDay.getText().toString() + " " +leaveSickStratTime.getText().toString())
			&& !mSickLeaveData.getLeaveEndTimeStr().equals(
					leaveSickEndDay.getText().toString() + " " +leaveSickEndTime.getText().toString())){
			T.showShort(mContext, "销假开始时间、销假结束时间, 至少要有一个等于请假开始时间、请假结束时间!");
			return;
		}

		if(KingTellerJudgeUtils.isEmpty(leaveSickWhy.getText().toString())){
			T.showShort(mContext, "请输入销假原因!");
			return;
		}

		KTHttpClient fh = new KTHttpClient(true);
		AjaxParams params = new AjaxParams();
		
		params.put("saveflag", saveflag);
		params.put("billType", "kqmgrSickleaveFlow");
		
		params.put("sickId", mSickLeaveData.getSickId());
		params.put("sickNo", mSickLeaveData.getSickNo());
		
		params.put("createUserId", mSickLeaveData.getCreateUserId());
		params.put("createUserName", mSickLeaveData.getCreateUserName());
		params.put("createTimeStr", mSickLeaveData.getCreateTimeStr());
		
		params.put("flowStatus", mSickLeaveData.getFlowStatus());
		params.put("sickTimes", mSickLeaveData.getSickTimes());
		params.put("sickDay", mSickLeaveData.getSickDay());
		params.put("sickHour", mSickLeaveData.getSickHour());
		
		params.put("firstDayHour", mSickLeaveData.getFirstDayHour());
		params.put("endDayHour", mSickLeaveData.getEndDayHour());
		
		params.put("startTime", mSickLeaveData.getStartTime());
		params.put("endTime", mSickLeaveData.getEndTime());
		
		params.put("sickUserId", mSickLeaveData.getSickUserId());
		params.put("sickUserName", mSickLeaveData.getSickUserName());
		params.put("sickUserAccount", mSickLeaveData.getSickUserAccount());
		
		params.put("leaveId", mSickLeaveData.getLeaveId());
		params.put("leaveNo", mSickLeaveData.getLeaveNo());
		params.put("leaveStartTimeStr", mSickLeaveData.getLeaveStartTimeStr());
		params.put("leaveEndTimeStr", mSickLeaveData.getLeaveEndTimeStr());
		params.put("leaveTimes", mSickLeaveData.getLeaveTimes());
		params.put("sickReason", leaveSickWhy.getText().toString());
		
		params.put("nextUserAccounts", mSickLeaveData.getNextUserAccounts());

		fh.post(KingTellerConfigUtils.CreateUrl(this, KingTellerUrlConfig.OAOAAllAduitWorkFillIn), params, 
				new AjaxHttpCallBack<BusinessTripBean>(this,
						new TypeToken<BusinessTripBean>() {
						}.getType(), true) {
			
					@Override
					public void onStart() {
						if(num == 0){
							KingTellerProgressUtils.showProgress(mContext, "正在暂存中...");
						}else{
							KingTellerProgressUtils.showProgress(mContext, "正在保存中...");
						}	
					}
					
					@Override
					public void onError(int errorNo, String strMsg) {
						super.onError(errorNo, strMsg);
						KingTellerProgressUtils.closeProgress();
						
						T.showShort(mContext, "数据访问超时!");
					}
					
					@Override
					public void onDo(final BusinessTripBean data) {
						KingTellerProgressUtils.closeProgress();
						if("".equals(data.getCode())){
							
							KingTellerStaticConfig.OA_WORK_ATTENDANCE_ISUDATE = true;
							
							if(num == 0){
								T.showShort(mContext, "暂存成功!");
							}else{
								T.showShort(mContext, "提交成功!");
							}	
							finish();
						}else{
							if(num == 0){
								dialog = new NormalDialog(mContext);
								KingTellerPromptDialogUtils.showOnePromptDialog(dialog, "暂存失败! " + data.getCode(),
				    					new OnBtnClickL() {
				    						@Override
				    						public void onBtnClick() {
				    							dialog.dismiss();
				    						}
				                        });
							}else{
								if("select_user".equals(data.getCode())){//选择下一环节审批人
									
									String [] mxyhj_StringItem = new String[data.getUlist().size()];
									for(int i = 0; i < data.getUlist().size(); i++){
										mxyhj_StringItem [i] = data.getUlist().get(i).getUserName();
									}
									
									final NormalListDialog dialog_bdlx = new NormalListDialog(mContext, mxyhj_StringItem);
									dialog_bdlx.title("请选择下一环节审批人")//
								                .layoutAnimation(null)
								                .titleBgColor(Color.parseColor("#409ED7"))//
								                .itemPressColor(Color.parseColor("#85D3EF"))//
								                .itemTextColor(Color.parseColor("#303030"))//
								                .show();
									dialog_bdlx.setOnOperItemClickL(new OnOperItemClickL() {
								            @Override
								            public void onOperItemClick(AdapterView<?> parent, View view, int position, long id) {
								            	dialog_bdlx.dismiss();
								            	mSickLeaveData.setNextUserAccounts(data.getUlist().get(position).getUserAccount());
								            	setSaveSickLeave(1);
								            }
								        });
								}else{
									dialog = new NormalDialog(mContext);
									KingTellerPromptDialogUtils.showOnePromptDialog(dialog, "提交失败! " + data.getCode(),
					    					new OnBtnClickL() {
					    						@Override
					    						public void onBtnClick() {
					    							dialog.dismiss();
					    						}
					                        });
								}
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
