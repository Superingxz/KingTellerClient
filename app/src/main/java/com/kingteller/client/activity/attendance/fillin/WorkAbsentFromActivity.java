package com.kingteller.client.activity.attendance.fillin;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

import com.google.gson.reflect.TypeToken;
import com.kingteller.R;
import com.kingteller.client.KingTellerApplication;
import com.kingteller.client.activity.attendance.search.WorkAttendanceSearchPersonnelActivity;
import com.kingteller.client.bean.account.User;
import com.kingteller.client.bean.attendance.AbsentBean;
import com.kingteller.client.bean.attendance.WorkAttendanceGetUserTimeBean;
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
 * 旷工填写
 * @author Administrator
 */
public class WorkAbsentFromActivity extends Activity implements OnClickListener{

	private TextView title_text;
	private Button title_left_btn, title_rightone_btn, title_righttwo_btn;
	private Context mContext;
	
	private OverTimePeopleGroupListView ldyj_group_list;
	
	private TextView oa_attendance_audit_sqdh;
	private TextView oa_attendance_audit_sqry;
	private TextView oa_attendance_audit_sqrq;
	
	private TextView absentName;
	private TextView absentStartDay;
	private TextView absentStratTime;
	private TextView absentEndDay;
	private TextView absentEndTime;
	private TextView absentUserTime;
	private TextView absentWhy;
	
	private String mAbsenData_str;			//旷工单数据
	private AbsentBean mAbsenData;			//初始数据
	
	private NormalDialog dialog = null; 	//初始化通用dialog
	
	private Calendar calendar = null;
	
	private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg){
            super.handleMessage(msg);
            String code = msg.getData().getString("code");
            if(msg.what == 1){
            	
            	absentStartDay.setText(KingTellerTimeUtils.getYearMonthDay(mAbsenData.getStartTime(), 1, 2));
            	absentStratTime.setText(KingTellerTimeUtils.getYearMonthDay(mAbsenData.getStartTime(), 1, 3));
            	absentEndDay.setText(KingTellerTimeUtils.getYearMonthDay(mAbsenData.getEndTime(), 1, 2));
            	absentEndTime.setText(KingTellerTimeUtils.getYearMonthDay(mAbsenData.getEndTime(), 1, 3));
				
            	absentUserTime.setText(String.valueOf(Float.parseFloat(mAbsenData.getAbsenteeismTimes())));

            }else if(msg.what == 2){
            	
            	absentStartDay.setText("日期");
            	absentStratTime.setText("时间");
            	absentEndDay.setText("日期");
            	absentEndTime.setText("时间");
				
            	absentUserTime.setText("0.0");
            	
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
		
		mContext = WorkAbsentFromActivity.this;
		
		mAbsenData_str = (String) getIntent().getStringExtra("mAbsenData");
		
		KingTellerStaticConfig.OA_WORK_ATTENDANCE_ISUDATE = false;
		
		initUI();
		initData();
	}
	
	private void initUI() {
		title_text = (TextView) findViewById(R.id.layout_main_text);
		title_left_btn = (Button) findViewById(R.id.layout_main_left_btn);
		title_rightone_btn = (Button) findViewById(R.id.layout_main_rightone_btn);
		title_righttwo_btn = (Button) findViewById(R.id.layout_main_righttwo_btn);

		title_text.setText("旷工单填写");
		
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
		findViewById(R.id.oa_qjdh_layout).setVisibility(View.GONE);//隐藏 请假单号
		findViewById(R.id.oa_czmdd_layout).setVisibility(View.GONE);//隐藏 出差目的地
		
		((TextView) findViewById(R.id.oa_work_people_name)).setText("旷工人员：");
		absentName = (TextView) findViewById(R.id.oa_work_people);
		
		((TextView) findViewById(R.id.oa_start_time_name)).setText("旷工开始时间：");
		absentStartDay = (TextView) findViewById(R.id.oa_start_day);
		absentStratTime = (TextView) findViewById(R.id.oa_start_time);
		
		((TextView) findViewById(R.id.oa_end_time_name)).setText("旷工结束时间：");
		absentEndDay = (TextView) findViewById(R.id.oa_end_day);
		absentEndTime = (TextView) findViewById(R.id.oa_end_time);
		
		((TextView) findViewById(R.id.oa_all_usertime_name)).setText("旷工时长：");
		absentUserTime = (TextView) findViewById(R.id.oa_all_usertime);
		
		((TextView) findViewById(R.id.oa_all_why_name)).setText("旷工原因：");
		absentWhy = (EditText) findViewById(R.id.oa_all_why);
		
		absentName.setOnClickListener(this);
		absentStartDay.setOnClickListener(this);
		absentStratTime.setOnClickListener(this);
		absentEndDay.setOnClickListener(this);
		absentEndTime.setOnClickListener(this);

		//处理历史
		ldyj_group_list = (OverTimePeopleGroupListView) findViewById(R.id.ldyj_group_list);
		ldyj_group_list.setGroupListTwoViewHidden(true);
		ldyj_group_list.setAddBtnHidden(1);
	}
	
	public void initData() {
		if("".equals(mAbsenData_str)){//新建
			mAbsenData = new AbsentBean();
			mAbsenData.setAbsenteeismUserId(User.getInfo(this).getUserId());
			mAbsenData.setAbsenteeismUserName(User.getInfo(this).getName());
			mAbsenData.setAbsenteeismUserAccount(User.getInfo(this).getUserName());
		}else{//修改
			mAbsenData = KingTellerJsonUtils.getPerson(mAbsenData_str, AbsentBean.class);
		}
		
		mAbsenData.setNextUserAccounts("");
		setData(mAbsenData);
	
		KingTellerConfigUtils.hideInputMethod(this);
	}
	
	private void setData(AbsentBean data) {
		//初始化单号信息
		if(data.getAbsenteeismId() != null){
			findViewById(R.id.layout_attendance_audit_djxx_common).setVisibility(View.VISIBLE);
			oa_attendance_audit_sqdh.setText(data.getAbsenteeismNo()); 
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
		absentName.setText(data.getAbsenteeismUserName() + "(" + data.getAbsenteeismUserAccount() + ")");
		if(KingTellerJudgeUtils.isEmpty(data.getStartTime())){
			absentStartDay.setText("日期");
			absentStratTime.setText("时间");
		}else{
			absentStartDay.setText(KingTellerTimeUtils.getYearMonthDay(data.getStartTime(), 1, 2));
			absentStratTime.setText(KingTellerTimeUtils.getYearMonthDay(data.getStartTime(), 1, 3));
		}
		if(KingTellerJudgeUtils.isEmpty(data.getEndTime())){
			absentEndDay.setText("日期");
			absentEndTime.setText("时间");
		}else{
			absentEndDay.setText(KingTellerTimeUtils.getYearMonthDay(data.getEndTime(), 1, 2));
			absentEndTime.setText(KingTellerTimeUtils.getYearMonthDay(data.getEndTime(), 1, 3));
		}
		absentUserTime.setText(KingTellerJudgeUtils.isEmpty(data.getAbsenteeismTimes()) ? "0.0" :  String.valueOf(Float.parseFloat(data.getAbsenteeismTimes())));
		absentWhy.setText(KingTellerJudgeUtils.isEmpty(data.getAbsenteeismReason()) ? "" :  data.getAbsenteeismReason());
	
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
			case KingTellerStaticConfig.OACODE_SELECT_PEOPLE_NUM:
				if (resultCode == RESULT_OK) {
					String pType = (String) data.getSerializableExtra("peopleType");
					String pData = (String) data.getSerializableExtra("peopleData");
					List<WorkAttendanceSearchPeopleBean> list = KingTellerJsonUtils.getPersons(pData, WorkAttendanceSearchPeopleBean.class);
					
					if("1".equals(pType)){		//单人	没view	添加	人员
						mAbsenData.setAbsenteeismUserId(list.get(0).getUserId());
						mAbsenData.setAbsenteeismUserName(list.get(0).getUserName());
						mAbsenData.setAbsenteeismUserAccount(list.get(0).getUserAccount());
						
						absentName.setText(mAbsenData.getAbsenteeismUserName() + "(" + mAbsenData.getAbsenteeismUserAccount() + ")");
						
						String startDay_1 = absentStartDay.getText().toString();
						String endDay_1 = absentEndDay.getText().toString();
						String startTime_1 = absentStratTime.getText().toString();
						String endTime_1 = absentEndTime.getText().toString();
						
						if(!"日期".equals(startDay_1) && !"日期".equals(endDay_1) && !"时间".equals(startTime_1) && !"时间".equals(endTime_1)){
							getAbsentUserTime(mAbsenData.getAbsenteeismUserId(), startDay_1 + " " + startTime_1, endDay_1 + " " + endTime_1);
						}

					}

				}
				break;
			default:
				break;
		}
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
	                    	setSaveAbsent(0);
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
	                    	setSaveAbsent(1);
	                    }
                });
			break;
		case R.id.oa_work_people:
			Intent intent = new Intent(mContext, WorkAttendanceSearchPersonnelActivity.class);
			intent.putExtra("peopleType", "1");
			startActivityForResult(intent, KingTellerStaticConfig.OACODE_SELECT_PEOPLE_NUM);
			break;
		case R.id.oa_start_day:
			 String kg_ks_data = absentStartDay.getText().toString();
			 
			 if("日期".equals(kg_ks_data)){
				 calendar = Calendar.getInstance();
			 }else{
				 calendar = Calendar.getInstance();
				 calendar.setTime(KingTellerTimeUtils.getConversionFormatDateByString(kg_ks_data, 2));
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
		                    	setAbsentUserTime(0, xz_ksday);
		                    }
	                });
			break;
		case R.id.oa_start_time:
			String kg_ks_time = absentStratTime.getText().toString();
			
			if("时间".equals(kg_ks_time)){
				 calendar = Calendar.getInstance();
			}else{
				 calendar = Calendar.getInstance();
				 calendar.setTime(KingTellerTimeUtils.getConversionFormatDateByString(kg_ks_time, 3));
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
	                        setAbsentUserTime(2, xz_kstime);
	                    }
               });
			
			break;
		case R.id.oa_end_day:
			 String kg_js_data = absentEndDay.getText().toString();
			 
			 if("日期".equals(kg_js_data)){
				 calendar = Calendar.getInstance();
			 }else{
				 calendar = Calendar.getInstance();
				 calendar.setTime(KingTellerTimeUtils.getConversionFormatDateByString(kg_js_data, 2));
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
		                    	setAbsentUserTime(1, xz_jsday);
		                    }
	                });
			break;
		case R.id.oa_end_time:
			String kg_js_time = absentEndTime.getText().toString();

			if("时间".equals(kg_js_time)){
				 calendar = Calendar.getInstance();
			}else{
				 calendar = Calendar.getInstance();
				 calendar.setTime(KingTellerTimeUtils.getConversionFormatDateByString(kg_js_time, 3));
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
	            	   setAbsentUserTime(3, xz_jstime);
                   }
           });

			break;
		default:
			break;
		
		}
	}
	
	/**
	 * 设置 旷工时长
	 * @param type 0: 	startDay
	 * @param type 1: 	endDay
	 * @param type 2: 	startTime
	 * @param type 3: 	endTime
	 */
	public void setAbsentUserTime(int type, String xzTime) {
		String dqTime = "";
		String startDay = absentStartDay.getText().toString();
		String endDay = absentEndDay.getText().toString();
		String startTime = absentStratTime.getText().toString();
		String endTime = absentEndTime.getText().toString();
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
			
			if(hour <= 0){
				if(type == 0){
					absentStartDay.setText(dqTime);
				}else if(type == 1){
					absentEndDay.setText(dqTime);
				}else if(type == 2){
					absentStratTime.setText(dqTime);
				}else{
					absentEndTime.setText(dqTime);
				}
				
				T.showShort(mContext, "所选的时间差不能小于等于0!");
			}else{
				getAbsentUserTime(mAbsenData.getAbsenteeismUserId(), startDay + " " + startTime, endDay + " " + endTime);
			}
		}else{
			if(type == 0){
				absentStartDay.setText(xzTime);
			}else if(type == 1){
				absentEndDay.setText(xzTime);
			}else if(type == 2){
				absentStratTime.setText(xzTime);
			}else{
				absentEndTime.setText(xzTime);
			}
		}
	}
	
	/**
	 * 获取  旷工时长
	 */
	public void getAbsentUserTime(String useId, String ksTime, String jsTime) {
		
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
							mAbsenData.setAbsenteeismTimes(data.getTotalHour());
							mAbsenData.setAbsenteeismDay(data.getDay());
							mAbsenData.setAbsenteeismHour(data.getHour());
							mAbsenData.setStartTime(data.getNewBeginDateTime());
							mAbsenData.setEndTime(data.getNewEndDateTime());
							
							mAbsenData.setFirstDayHour(data.getBeginHour());
							mAbsenData.setEndDayHour(data.getEndHour());
							message.what = 1;
						}else{
							mAbsenData.setAbsenteeismTimes("");
							mAbsenData.setAbsenteeismDay("");
							mAbsenData.setAbsenteeismHour("");
							mAbsenData.setStartTime("");
							mAbsenData.setEndTime("");
							
							mAbsenData.setFirstDayHour("");
							mAbsenData.setEndDayHour("");
							message.what = 2;
						}
					
						bundleData.putString("code", data.getCode());
				        message.setData(bundleData);  
				        handler.sendMessage(message);
					};
				});
	}
	
	/**
	 * 0暂存 或  1保存
	 */
	public void setSaveAbsent(final int num) {
		if (mAbsenData == null) {
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
		if("日期".equals(absentStartDay.getText().toString())){
			T.showShort(mContext, "请选择旷工开始日期!");
			return;
		}
		if("时间".equals(absentStratTime.getText().toString())){
			T.showShort(mContext, "请选择旷工始时间!");
			return;
		}
		
		if("日期".equals(absentEndDay.getText().toString())){
			T.showShort(mContext, "请选择旷工结束日期!");
			return;
		}
		if("时间".equals(absentEndTime.getText().toString())){
			T.showShort(mContext, "请选择旷工结束时间!");
			return;
		}
		
		if(Float.parseFloat(absentUserTime.getText().toString()) <= 0){
			T.showShort(mContext, "所选的时间差不能小于等于0!");
			return;
		}
		
		if(KingTellerJudgeUtils.isEmpty(absentWhy.getText().toString())){
			T.showShort(mContext, "请输入旷工原因!");
			return;
		}

		
		KTHttpClient fh = new KTHttpClient(true);
		AjaxParams params = new AjaxParams();
		
		params.put("saveflag", saveflag);
		params.put("billType", "kqmgrAbsenteeismFlow");
		
		params.put("absenteeismId", mAbsenData.getAbsenteeismId());
		params.put("absenteeismNo", mAbsenData.getAbsenteeismNo());
		
		params.put("createUserId", mAbsenData.getCreateUserId());
		params.put("createUserName", mAbsenData.getCreateUserName());
		params.put("createTimeStr", mAbsenData.getCreateTimeStr());
		
		params.put("flowStatus", mAbsenData.getFlowStatus());
		params.put("absenteeismTimes", mAbsenData.getAbsenteeismTimes());
		params.put("absenteeismDay", mAbsenData.getAbsenteeismDay());
		params.put("absenteeismHour", mAbsenData.getAbsenteeismHour());
		
		params.put("firstDayHour", mAbsenData.getFirstDayHour());
		params.put("endDayHour", mAbsenData.getEndDayHour());
		
		params.put("startTime", absentStartDay.getText().toString() + " " + absentStratTime.getText().toString());
		params.put("endTime", absentEndDay.getText().toString() + " " + absentEndTime.getText().toString());
		
		params.put("absenteeismUserId", mAbsenData.getAbsenteeismUserId());
		params.put("absenteeismUserName", mAbsenData.getAbsenteeismUserName());
		params.put("absenteeismUserAccount", mAbsenData.getAbsenteeismUserAccount());
		params.put("absenteeismReason", absentWhy.getText().toString());
		
		params.put("nextUserAccounts", mAbsenData.getNextUserAccounts());

		fh.post(KingTellerConfigUtils.CreateUrl(this, KingTellerUrlConfig.OAOAAllAduitWorkFillIn), params, 
				new AjaxHttpCallBack<AbsentBean>(this,
						new TypeToken<AbsentBean>() {
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
					public void onDo(final AbsentBean data) {
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
								            	mAbsenData.setNextUserAccounts(data.getUlist().get(position).getUserAccount());
								            	setSaveAbsent(1);
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
