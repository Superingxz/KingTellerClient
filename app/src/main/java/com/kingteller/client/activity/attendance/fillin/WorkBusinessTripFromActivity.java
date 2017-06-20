package com.kingteller.client.activity.attendance.fillin;

import java.util.Calendar;
import java.util.List;

import com.google.gson.reflect.TypeToken;
import com.kingteller.R;
import com.kingteller.client.KingTellerApplication;
import com.kingteller.client.activity.attendance.search.WorkAttendanceSearchPersonnelActivity;
import com.kingteller.client.bean.account.User;
import com.kingteller.client.bean.attendance.BusinessTripBean;
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
 * 出差填写
 * @author Administrator
 */
public class WorkBusinessTripFromActivity extends Activity implements OnClickListener{
	
	private TextView title_text;
	private Button title_left_btn, title_rightone_btn, title_righttwo_btn;
	private Context mContext;
	
	private OverTimePeopleGroupListView ldyj_group_list;
	
	private TextView oa_attendance_audit_sqdh;
	private TextView oa_attendance_audit_sqry;
	private TextView oa_attendance_audit_sqrq;
	
	private TextView businessTripName;
	private TextView businessTripAddress;
	private TextView businessTripStartDay;
	private TextView businessTripStratTime;
	private TextView businessTripEndDay;
	private TextView businessTripEndTime;
	private TextView businessTripUserTime;
	private EditText businessTripWhy;

	private String mBusinessData_str;				//出差单数据
	private BusinessTripBean mBusinessData;			//初始数据
	
	private NormalDialog dialog = null; 	//初始化通用dialog
	
	private Calendar calendar = null;
	
	private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg){
            super.handleMessage(msg);
            String code = msg.getData().getString("code");
            if(msg.what == 1){
            	
				businessTripStartDay.setText(KingTellerTimeUtils.getYearMonthDay(mBusinessData.getStartTime(), 1, 2));
				businessTripStratTime.setText(KingTellerTimeUtils.getYearMonthDay(mBusinessData.getStartTime(), 1, 3));
				businessTripEndDay.setText(KingTellerTimeUtils.getYearMonthDay(mBusinessData.getEndTime(), 1, 2));
				businessTripEndTime.setText(KingTellerTimeUtils.getYearMonthDay(mBusinessData.getEndTime(), 1, 3));
				
				businessTripUserTime.setText(String.valueOf(Float.parseFloat(mBusinessData.getTravelTimes())));

            }else if(msg.what == 2){
            	
            	businessTripStartDay.setText("日期");
				businessTripStratTime.setText("时间");
				businessTripEndDay.setText("日期");
				businessTripEndTime.setText("时间");
				
				businessTripUserTime.setText("0.0");
				
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
		
		mContext = WorkBusinessTripFromActivity.this;
		
		mBusinessData_str = (String) getIntent().getStringExtra("mBusinessData");
		
		KingTellerStaticConfig.OA_WORK_ATTENDANCE_ISUDATE = false;
		
		initUI();
		initData();
	}
	
	private void initUI() {
		title_text = (TextView) findViewById(R.id.layout_main_text);
		title_left_btn = (Button) findViewById(R.id.layout_main_left_btn);
		title_rightone_btn = (Button) findViewById(R.id.layout_main_rightone_btn);
		title_righttwo_btn = (Button) findViewById(R.id.layout_main_righttwo_btn);

		title_text.setText("出差单填写");
		
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
		
		((TextView) findViewById(R.id.oa_work_people_name)).setText("出差人员：");
		businessTripName = (TextView) findViewById(R.id.oa_work_people);
		
		businessTripAddress = (TextView) findViewById(R.id.oa_work_address);
		
		((TextView) findViewById(R.id.oa_start_time_name)).setText("出差开始时间：");
		businessTripStartDay = (TextView) findViewById(R.id.oa_start_day);
		businessTripStratTime = (TextView) findViewById(R.id.oa_start_time);
		
		((TextView) findViewById(R.id.oa_end_time_name)).setText("出差结束时间：");
		businessTripEndDay = (TextView) findViewById(R.id.oa_end_day);
		businessTripEndTime = (TextView) findViewById(R.id.oa_end_time);
		
		((TextView) findViewById(R.id.oa_all_usertime_name)).setText("出差时长：");
		businessTripUserTime = (TextView) findViewById(R.id.oa_all_usertime);
		
		((TextView) findViewById(R.id.oa_all_why_name)).setText("出差原因：");
		businessTripWhy = (EditText) findViewById(R.id.oa_all_why);
		
		businessTripName.setOnClickListener(this);
		businessTripStartDay.setOnClickListener(this);
		businessTripStratTime.setOnClickListener(this);
		businessTripEndDay.setOnClickListener(this);
		businessTripEndTime.setOnClickListener(this);
		
		//处理历史
		ldyj_group_list = (OverTimePeopleGroupListView) findViewById(R.id.ldyj_group_list);
		ldyj_group_list.setGroupListTwoViewHidden(true);
		ldyj_group_list.setAddBtnHidden(1);
	}
	
	public void initData() {
		if("".equals(mBusinessData_str)){//新建
			mBusinessData = new BusinessTripBean();
			mBusinessData.setTravelUserId(User.getInfo(this).getUserId());
			mBusinessData.setTravelUserName(User.getInfo(this).getName());
			mBusinessData.setTravelUserAccount(User.getInfo(this).getUserName());
		}else{//修改
			mBusinessData = KingTellerJsonUtils.getPerson(mBusinessData_str, BusinessTripBean.class);
		}
		
		mBusinessData.setNextUserAccounts("");
		setData(mBusinessData);
	
		KingTellerConfigUtils.hideInputMethod(this);
	}
	
	private void setData(BusinessTripBean data) {
		//初始化单号信息
		if(data.getTravelId() != null){
			findViewById(R.id.layout_attendance_audit_djxx_common).setVisibility(View.VISIBLE);
			oa_attendance_audit_sqdh.setText(data.getTravelNo()); 
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
		businessTripName.setText(data.getTravelUserName() + "(" + data.getTravelUserAccount() + ")");
		businessTripAddress.setText(KingTellerJudgeUtils.isEmpty(data.getTravelDestination()) ? "" :  data.getTravelDestination());
		if(KingTellerJudgeUtils.isEmpty(data.getStartTime())){
			businessTripStartDay.setText("日期");
			businessTripStratTime.setText("时间");
		}else{
			businessTripStartDay.setText(KingTellerTimeUtils.getYearMonthDay(data.getStartTime(), 1, 2));
			businessTripStratTime.setText(KingTellerTimeUtils.getYearMonthDay(data.getStartTime(), 1, 3));
		}
		if(KingTellerJudgeUtils.isEmpty(data.getEndTime())){
			businessTripEndDay.setText("日期");
			businessTripEndTime.setText("时间");
		}else{
			businessTripEndDay.setText(KingTellerTimeUtils.getYearMonthDay(data.getEndTime(), 1, 2));
			businessTripEndTime.setText(KingTellerTimeUtils.getYearMonthDay(data.getEndTime(), 1, 3));
		}
		businessTripUserTime.setText(KingTellerJudgeUtils.isEmpty(data.getTravelTimes()) ? "0.0" :  String.valueOf(Float.parseFloat(data.getTravelTimes())));
		businessTripWhy.setText(KingTellerJudgeUtils.isEmpty(data.getTravelReason()) ? "" :  data.getTravelReason());
		
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
						mBusinessData.setTravelUserId(list.get(0).getUserId());
						mBusinessData.setTravelUserName(list.get(0).getUserName());
						mBusinessData.setTravelUserAccount(list.get(0).getUserAccount());
						
						businessTripName.setText(mBusinessData.getTravelUserName() + "(" + mBusinessData.getTravelUserAccount() + ")");
						
						String startDay_1 = businessTripStartDay.getText().toString();
						String endDay_1 = businessTripEndDay.getText().toString();
						String startTime_1 = businessTripStratTime.getText().toString();
						String endTime_1 = businessTripEndTime.getText().toString();
						
						if(!"日期".equals(startDay_1) && !"日期".equals(endDay_1) && !"时间".equals(startTime_1) && !"时间".equals(endTime_1)){
							getBusinessTripUserTime(mBusinessData.getTravelUserId(), startDay_1 + " " + startTime_1, endDay_1 + " " + endTime_1);
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
	                    	setSaveBusinessTrip(0);
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
	                    	setSaveBusinessTrip(1);
	                    }
                });
			break;
		case R.id.oa_work_people:
			Intent intent = new Intent(mContext, WorkAttendanceSearchPersonnelActivity.class);
			intent.putExtra("peopleType", "1");
			startActivityForResult(intent, KingTellerStaticConfig.OACODE_SELECT_PEOPLE_NUM);
			break;
		case R.id.oa_start_day:
			 String cc_ks_data = businessTripStartDay.getText().toString();
			 
			 if("日期".equals(cc_ks_data)){
				 calendar = Calendar.getInstance();
			 }else{
				 calendar = Calendar.getInstance();
				 calendar.setTime(KingTellerTimeUtils.getConversionFormatDateByString(cc_ks_data, 2));
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
		                    	setBusinessTripUserTime(0, xz_ksday);
	
		                    }
	                });
			break;
		case R.id.oa_start_time:
			String cc_ks_time = businessTripStratTime.getText().toString();
			
			if("时间".equals(cc_ks_time)){
				 calendar = Calendar.getInstance();
			}else{
				 calendar = Calendar.getInstance();
				 calendar.setTime(KingTellerTimeUtils.getConversionFormatDateByString(cc_ks_time, 3));
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
	                        setBusinessTripUserTime(2, xz_kstime);
	                    }
               });
			
			break;
		case R.id.oa_end_day:
			 String cc_js_data = businessTripEndDay.getText().toString();
			 
			 if("日期".equals(cc_js_data)){
				 calendar = Calendar.getInstance();
			 }else{
				 calendar = Calendar.getInstance();
				 calendar.setTime(KingTellerTimeUtils.getConversionFormatDateByString(cc_js_data, 2));
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
		                    	setBusinessTripUserTime(1, xz_jsday);
	
		                    }
	                });
			break;
		case R.id.oa_end_time:
			String cc_js_time = businessTripEndTime.getText().toString();
			
			if("时间".equals(cc_js_time)){
				 calendar = Calendar.getInstance();
			}else{
				 calendar = Calendar.getInstance();
				 calendar.setTime(KingTellerTimeUtils.getConversionFormatDateByString(cc_js_time, 3));
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
                       setBusinessTripUserTime(3, xz_jstime);
                   }
           });

			break;
		default:
			break;
		
		}
	}
	
	
	/**
	 * 设置 出差时长
	 * @param type 0: 	startDay
	 * @param type 1: 	endDay
	 * @param type 2: 	startTime
	 * @param type 3: 	endTime
	 */
	public void setBusinessTripUserTime(int type, String xzTime) {
		String dqTime = "";
		String startDay = businessTripStartDay.getText().toString();
		String endDay = businessTripEndDay.getText().toString();
		String startTime = businessTripStratTime.getText().toString();
		String endTime = businessTripEndTime.getText().toString();
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
					businessTripStartDay.setText(dqTime);
				}else if(type == 1){
					businessTripEndDay.setText(dqTime);
				}else if(type == 2){
					businessTripStratTime.setText(dqTime);
				}else{
					businessTripEndTime.setText(dqTime);
				}
				
				T.showShort(mContext, "所选的时间差不能小于等于0!");
			}else{
				getBusinessTripUserTime(mBusinessData.getTravelUserId(), startDay + " " + startTime, endDay + " " + endTime);
			}
		}else{
			if(type == 0){
				businessTripStartDay.setText(xzTime);
			}else if(type == 1){
				businessTripEndDay.setText(xzTime);
			}else if(type == 2){
				businessTripStratTime.setText(xzTime);
			}else{
				businessTripEndTime.setText(xzTime);
			}
		}
	}

	/**
	 * 获取  出差时长
	 */
	public void getBusinessTripUserTime(String useId, String ksTime, String jsTime) {
		
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
							mBusinessData.setTravelTimes(data.getTotalHour());
							mBusinessData.setTravelDay(data.getDay());
							mBusinessData.setTravelHour(data.getHour());
							mBusinessData.setStartTime(data.getNewBeginDateTime());
							mBusinessData.setEndTime(data.getNewEndDateTime());
							
							mBusinessData.setFirstDayHour(data.getBeginHour());
							mBusinessData.setEndDayHour(data.getEndHour());
							message.what = 1;
						}else{
							mBusinessData.setTravelTimes("");
							mBusinessData.setTravelDay("");
							mBusinessData.setTravelHour("");
							mBusinessData.setStartTime("");
							mBusinessData.setEndTime("");
							
							mBusinessData.setFirstDayHour("");
							mBusinessData.setEndDayHour("");
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
	public void setSaveBusinessTrip(final int num) {
		if (mBusinessData == null) {
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
		if("日期".equals(businessTripStartDay.getText().toString())){
			T.showShort(mContext, "请选择出差开始日期!");
			return;
		}
		if("时间".equals(businessTripStratTime.getText().toString())){
			T.showShort(mContext, "请选择出差开始时间!");
			return;
		}
		
		if("日期".equals(businessTripEndDay.getText().toString())){
			T.showShort(mContext, "请选择出差结束日期!");
			return;
		}
		if("时间".equals(businessTripEndTime.getText().toString())){
			T.showShort(mContext, "请选择出差结束时间!");
			return;
		}
		
		if(Float.parseFloat(businessTripUserTime.getText().toString()) <= 0){
			T.showShort(mContext, "所选的时间差不能小于等于0!");
			return;
		}
		
		if(KingTellerJudgeUtils.isEmpty(businessTripAddress.getText().toString())){
			T.showShort(mContext, "请输入出差地址!");
			return;
		}
		
		if(KingTellerJudgeUtils.isEmpty(businessTripWhy.getText().toString())){
			T.showShort(mContext, "请输入出差原因!");
			return;
		}

		
		KTHttpClient fh = new KTHttpClient(true);
		AjaxParams params = new AjaxParams();
		
		params.put("saveflag", saveflag);
		params.put("billType", "kqmgrTravelFlow");
		
		params.put("travelId", mBusinessData.getTravelId());
		params.put("travelNo", mBusinessData.getTravelNo());
		
		params.put("createUserId", mBusinessData.getCreateUserId());
		params.put("createUserName", mBusinessData.getCreateUserName());
		params.put("createTimeStr", mBusinessData.getCreateTimeStr());
		
		params.put("flowStatus", mBusinessData.getFlowStatus());
		params.put("travelTimes", mBusinessData.getTravelTimes());
		params.put("travelDay", mBusinessData.getTravelDay());
		params.put("travelHour", mBusinessData.getTravelHour());
		
		params.put("firstDayHour", mBusinessData.getFirstDayHour());
		params.put("endDayHour", mBusinessData.getEndDayHour());
		
		params.put("startTime", mBusinessData.getStartTime());
		params.put("endTime", mBusinessData.getEndTime());
		
		params.put("travelUserId", mBusinessData.getTravelUserId());
		params.put("travelUserName", mBusinessData.getTravelUserName());
		params.put("travelUserAccount", mBusinessData.getTravelUserAccount());
		params.put("travelDestination", businessTripAddress.getText().toString());
		params.put("travelReason", businessTripWhy.getText().toString());
		
		params.put("nextUserAccounts", mBusinessData.getNextUserAccounts());

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
								            	mBusinessData.setNextUserAccounts(data.getUlist().get(position).getUserAccount());
								            	setSaveBusinessTrip(1);
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
