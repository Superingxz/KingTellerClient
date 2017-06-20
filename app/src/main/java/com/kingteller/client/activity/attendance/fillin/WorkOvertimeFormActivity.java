package com.kingteller.client.activity.attendance.fillin;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kingteller.R;
import com.kingteller.client.KingTellerApplication;
import com.kingteller.client.activity.attendance.search.WorkAttendanceSearchPersonnelActivity;
import com.kingteller.client.bean.account.User;
import com.kingteller.client.bean.attendance.OverTimeBean;
import com.kingteller.client.bean.attendance.OverTimePeopleBean;
import com.kingteller.client.bean.attendance.OverTimeSonPeople;
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
import com.kingteller.client.view.groupview.itemview.OverTimePeopleGroupView;
import com.kingteller.client.view.groupview.itemview.OverTimePeopleHistoryGroupView;
import com.kingteller.client.view.groupview.itemview.OverTimePeopleGroupView.DeleteViewCallBack;
import com.kingteller.client.view.groupview.itemview.OverTimePeopleGroupView.EditTextUseTimeViewCallBack;
import com.kingteller.client.view.groupview.itemview.OverTimePeopleGroupView.SelectWardViewCallBack;
import com.kingteller.client.view.groupview.listview.OverTimePeopleGroupListView;
import com.kingteller.client.view.groupview.listview.OverTimePeopleGroupListView.AddViewCallBack;
import com.kingteller.client.view.toast.T;
import com.kingteller.framework.http.AjaxParams;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

/**
 * 加班填写
 * @author Administrator
 */
public class WorkOvertimeFormActivity extends Activity implements OnClickListener {
	
	private TextView title_text;
	private Button title_left_btn, title_rightone_btn, title_righttwo_btn;
	private Context mContext;

	private OverTimePeopleGroupListView jbyy_group_list;
	private OverTimePeopleGroupListView ldyj_group_list;
	
	private OverTimePeopleGroupView peopleView;
	private TextView overtimeType;
	private TextView overtimeName;
	private TextView overtimeSubsidyWay;
	private TextView overtimeSelectDay;
	private TextView overtimeSelectStartTime;
	private TextView overtimeSelectEndTime;
	private EditText overtimeUseTime;
	private EditText overtimeWhy;

	private TextView oa_attendance_audit_sqdh;
	private TextView oa_attendance_audit_sqry;
	private TextView oa_attendance_audit_sqrq;
	
	private String[] mJblx_StringItem;
	private List<String> mJblx_StringItemList;
	
	private String[] mBtfs_StringItem;
	private List<String> mBtfs_StringItemList;
	
	private String mOvertimeData_str;			//加班单数据
	private OverTimeBean mOverTimeData;			//初始数据
	private List<OverTimeSonPeople> mOverTimeOneSonList;			//初始单人list
	private List<OverTimeSonPeople> mOverTimeMoreSonList;			//初始多人list
	
	private NormalDialog dialog = null; 	//初始化通用dialog
	
	private Calendar calendar = null;
	
	@Override
	public void onCreate(Bundle paramBundle) {
		super.onCreate(paramBundle);
		setContentView(R.layout.layout_work_attendance_overtime_form);
		KingTellerApplication.addActivity(this);
		
		mContext = WorkOvertimeFormActivity.this;
		
		mOvertimeData_str = (String) getIntent().getStringExtra("overtimeData");
		
		KingTellerStaticConfig.OA_WORK_ATTENDANCE_ISUDATE = false;
		
		initUI();
		initData();
	}
	
	private void initUI() {
		title_text = (TextView) findViewById(R.id.layout_main_text);
		title_left_btn = (Button) findViewById(R.id.layout_main_left_btn);
		title_rightone_btn = (Button) findViewById(R.id.layout_main_rightone_btn);
		title_righttwo_btn = (Button) findViewById(R.id.layout_main_righttwo_btn);

		title_text.setText("加班单填写");
		
		title_left_btn.setBackgroundResource(R.drawable.btn_back_arrow);
		title_left_btn.setOnClickListener(this);
		
		title_rightone_btn.setBackgroundResource(R.drawable.btn_save);
		title_rightone_btn.setOnClickListener(this);
		
		title_righttwo_btn.setBackgroundResource(R.drawable.btn_check);
		title_righttwo_btn.setOnClickListener(this);
		
		oa_attendance_audit_sqdh = (TextView) findViewById(R.id.oa_attendance_audit_sqdh);
		oa_attendance_audit_sqry = (TextView) findViewById(R.id.oa_attendance_audit_sqry);
		oa_attendance_audit_sqrq = (TextView) findViewById(R.id.oa_attendance_audit_sqrq);
		
		
		overtimeType = (TextView) findViewById(R.id.overtimeType);
		overtimeName = (TextView) findViewById(R.id.overtimeName);
		overtimeSubsidyWay = (TextView) findViewById(R.id.overtimeSubsidyWay);
		overtimeSelectDay = (TextView) findViewById(R.id.overtimeSelectDay);
		overtimeSelectStartTime = (TextView) findViewById(R.id.overtimeSelectStartTime);
		overtimeSelectEndTime = (TextView) findViewById(R.id.overtimeSelectEndTime);
		overtimeUseTime = (EditText) findViewById(R.id.overtimeUseTime);
		overtimeWhy = (EditText) findViewById(R.id.overtimeWhy);
		
		overtimeType.setOnClickListener(this);
		overtimeName.setOnClickListener(this);
		overtimeSubsidyWay.setOnClickListener(this);
		overtimeSelectDay.setOnClickListener(this);
		overtimeSelectStartTime.setOnClickListener(this);
		overtimeSelectEndTime.setOnClickListener(this);
		
		//人员list
		jbyy_group_list = (OverTimePeopleGroupListView) findViewById(R.id.jbyy_group_list);
		jbyy_group_list.setGroupListViewHidden(true);
		jbyy_group_list.setAddViewCallBack(new AddViewCallBack() {
		@Override
		public void addView(OverTimePeopleGroupListView view) {
				float usetime_two = Float.parseFloat(mOverTimeData.getOvertimeHourOne());
				if(usetime_two > 0){
					Intent intent = new Intent(mContext, WorkAttendanceSearchPersonnelActivity.class);
					intent.putExtra("peopleType", "2");
					startActivityForResult(intent, KingTellerStaticConfig.OACODE_SELECT_PEOPLE_NUM);
				}else{
					T.showShort(mContext, "请选择加班时间!");
				}
			}
		});

		//处理历史
		ldyj_group_list = (OverTimePeopleGroupListView) findViewById(R.id.ldyj_group_list);
		ldyj_group_list.setGroupListTwoViewHidden(true);
		ldyj_group_list.setAddBtnHidden(1);
	}
	
	public void initData() {
		mJblx_StringItem = new String[] {"个人", "多人"};// 1个人	2多人
		mJblx_StringItemList = Arrays.asList(mJblx_StringItem);
		
		mBtfs_StringItem = new String[] {"补休", "计薪"};// 0补休	1计薪 
		mBtfs_StringItemList = Arrays.asList(mBtfs_StringItem);
		
		
		mOverTimeOneSonList = new ArrayList<OverTimeSonPeople>();
		mOverTimeMoreSonList = new ArrayList<OverTimeSonPeople>();
		
		if("".equals(mOvertimeData_str)){//新建
			mOverTimeData = new OverTimeBean();
			mOverTimeOneSonList.add(new OverTimeSonPeople("",
					User.getInfo(this).getUserId(),
					User.getInfo(this).getName(), 
					User.getInfo(this).getUserName(), 
					"0.0", 
					"0",
					""));
			mOverTimeData.setSonList(mOverTimeOneSonList);
			mOverTimeData.setOvertimeType("1");
			mOverTimeData.setOvertimeHourOne("0.0");
		}else{//修改
			mOverTimeData = KingTellerJsonUtils.getPerson(mOvertimeData_str, OverTimeBean.class);
		}
		
		mOverTimeData.setNextUserAccounts("");
		setData(mOverTimeData);
		
		KingTellerConfigUtils.hideInputMethod(this);
	}
	
	
	public void setData(OverTimeBean data) {
		//初始化单号信息
		if(mOverTimeData.getOvertimeId() != null){
			findViewById(R.id.layout_attendance_audit_djxx_common).setVisibility(View.VISIBLE);
			oa_attendance_audit_sqdh.setText(mOverTimeData.getOvertimeNo()); 
			oa_attendance_audit_sqry.setText(mOverTimeData.getCreateUserName());
			oa_attendance_audit_sqrq.setText(mOverTimeData.getCreateTimeStr());
		}else{
			findViewById(R.id.layout_attendance_audit_djxx_common).setVisibility(View.GONE);
		}
		
		//初始化处理历史
		if(mOverTimeData.getHistoryList() != null && mOverTimeData.getHistoryList().size() >0){
			findViewById(R.id.layout_attendance_audit_ldyj_common).setVisibility(View.VISIBLE);
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
		}else{
			findViewById(R.id.layout_attendance_audit_ldyj_common).setVisibility(View.GONE);
		}
				
		//初始化数据
		overtimeType.setText("1".equals(data.getOvertimeType()) ? "个人" : "多人");
		
		overtimeSelectDay.setText(KingTellerJudgeUtils.isEmpty(data.getOvertimeDayExt()) ? "请选择加班日期" : data.getOvertimeDayExt());
		overtimeSelectStartTime.setText(KingTellerJudgeUtils.isEmpty(data.getStartTime()) ? "开始时间" : data.getStartTime());
		overtimeSelectEndTime.setText(KingTellerJudgeUtils.isEmpty(data.getEndTime()) ? "结束时间" : data.getEndTime());
		overtimeWhy.setText(KingTellerJudgeUtils.isEmpty(data.getOvertimeReason()) ? "" : data.getOvertimeReason());
		
		if("1".equals(data.getOvertimeType())){
			mOverTimeOneSonList = data.getSonList();
			overtimeUseTime.setText(KingTellerJudgeUtils.isEmpty(data.getOvertimeHour()) ? "0.0" : String.valueOf(Float.parseFloat(data.getOvertimeHourOne())));
			setOvertimeOnePeopleList(mOverTimeOneSonList);
		}else{
			mOverTimeMoreSonList = data.getSonList();
			overtimeUseTime.setText(KingTellerJudgeUtils.isEmpty(data.getOvertimeHour()) ? "0.0" : String.valueOf(Float.parseFloat(data.getOvertimeHour())));
    		jbyy_group_list.setGroupListViewHidden(false);
    		jbyy_group_list.setAddBtnHidden(0);
			jbyy_group_list.setTitleStart(0);
			
			findViewById(R.id.overtimeName_layout).setVisibility(View.GONE);
    		findViewById(R.id.overtimeSubsidyWay_layout).setVisibility(View.GONE);
    		
    		setOvertimeMorePeopleList(mOverTimeMoreSonList);
		}
		
		
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
						if(mOverTimeOneSonList.size() == 1){
							mOverTimeOneSonList.get(0).setSonUserId(list.get(0).getUserId());
							mOverTimeOneSonList.get(0).setSonUserName(list.get(0).getUserName());
							mOverTimeOneSonList.get(0).setSonUserAccount(list.get(0).getUserAccount());
						}
						overtimeName.setText(list.get(0).getUserName() + "(" + list.get(0).getUserAccount() + ")" ); 
						
					}else if("2".equals(pType)){//多人	多view	添加	人员
						String usetime = mOverTimeData.getOvertimeHourOne();
						
						for(int i = 0; i < list.size(); i++){
							//判断当前  多人list是否存在 该人
							if(getIsInMorePeopleList(list.get(i).getUserId())){
								T.showShort(mContext, "已选择人员!");
							}else{
								mOverTimeMoreSonList.add(new OverTimeSonPeople("",
										list.get(i).getUserId(),
										list.get(i).getUserName(), 
										list.get(i).getUserAccount(),
										usetime, 
										"0",
										""));
							}
						}

						//添加人员
						setOvertimeMorePeopleList(mOverTimeMoreSonList);
						
					}else if("3".equals(pType)){//多人	单view	选择	人员
						
						if(getIsInMorePeopleList(list.get(0).getUserId())){
							T.showShort(mContext, "已选择人员!");
						}else{
							jbyy_group_list.setItemTextData(list.get(0).getUserId(),
									list.get(0).getUserName(),
									list.get(0).getUserAccount());
							
							for(int i = 0; i < jbyy_group_list.getListData().size(); i++ ){
								String nameId = ((TextView)((OverTimePeopleGroupView) jbyy_group_list.getLayoutList().getChildAt(i))
										.findViewById(R.id.overtimePeopleId)).getText().toString();
								if(list.get(0).getUserId().equals(nameId)){
									mOverTimeMoreSonList.get(i).setSonUserId(list.get(0).getUserId());
									mOverTimeMoreSonList.get(i).setSonUserName(list.get(0).getUserName());
									mOverTimeMoreSonList.get(i).setSonUserAccount(list.get(0).getUserAccount());
								}
							}
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
	                    	setSaveOvertime(0);
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
	                    	setSaveOvertime(1);
	                    }
                });
			break;
		case R.id.overtimeName:
			if("1".equals(mOverTimeData.getOvertimeType())){
				Intent intent = new Intent(mContext, WorkAttendanceSearchPersonnelActivity.class);
				intent.putExtra("peopleType", "1");
				startActivityForResult(intent, KingTellerStaticConfig.OACODE_SELECT_PEOPLE_NUM);
			}
			break;
		case R.id.overtimeType:
			 final NormalListDialog dialog_jblx = new NormalListDialog(mContext, mJblx_StringItem);
			 dialog_jblx.title("请选择加班类型")//
		                .layoutAnimation(null)
		                .titleBgColor(Color.parseColor("#409ED7"))//
		                .itemPressColor(Color.parseColor("#85D3EF"))//
		                .itemTextColor(Color.parseColor("#303030"))//
		                .show();
			 dialog_jblx.setOnOperItemClickL(new OnOperItemClickL() {
		            @Override
		            public void onOperItemClick(AdapterView<?> parent, View view, int position, long id) {
		            	dialog_jblx.dismiss();
		            	
						jbyy_group_list.getLayoutList().removeAllViews();
						OverTimePeopleGroupView jbView;
		            	if(position == 0){ //个人
		            		mOverTimeData.setOvertimeType("1");
		            		
		            		overtimeType.setText(mJblx_StringItemList.get(position).toString());
		            		
		            		jbyy_group_list.setGroupListViewHidden(true);
		            		
		            		findViewById(R.id.overtimeName_layout).setVisibility(View.VISIBLE);
		            		findViewById(R.id.overtimeSubsidyWay_layout).setVisibility(View.VISIBLE);
		            	
		            		overtimeUseTime.setText(mOverTimeData.getOvertimeHourOne());
		            		setOvertimeOnePeopleList(mOverTimeOneSonList);
		            	}else{//多人
		            		mOverTimeData.setOvertimeType("2");
		            		
		            		overtimeType.setText(mJblx_StringItemList.get(position).toString());
		            		
		            		jbyy_group_list.setGroupListViewHidden(false);
		            		jbyy_group_list.setAddBtnHidden(0);
							jbyy_group_list.setTitleStart(0);
							
							findViewById(R.id.overtimeName_layout).setVisibility(View.GONE);
		            		findViewById(R.id.overtimeSubsidyWay_layout).setVisibility(View.GONE);
		            		
		            		setOvertimeMorePeopleList(mOverTimeMoreSonList);
		            	}
		            }
		        });

			break;
		case R.id.overtimeSubsidyWay:
			 final NormalListDialog dialog_btfs = new NormalListDialog(mContext, mBtfs_StringItem);
			 dialog_btfs.title("请选择补贴方式")//
		                .layoutAnimation(null)
		                .titleBgColor(Color.parseColor("#409ED7"))//
		                .itemPressColor(Color.parseColor("#85D3EF"))//
		                .itemTextColor(Color.parseColor("#303030"))//
		                .show();
			 dialog_btfs.setOnOperItemClickL(new OnOperItemClickL() {
		            @Override
		            public void onOperItemClick(AdapterView<?> parent, View view, int position, long id) {
		            	dialog_btfs.dismiss();
			            overtimeSubsidyWay.setText(mBtfs_StringItemList.get(position).toString());
		            }
		        });
			break;
		case R.id.overtimeSelectDay:
			 String jb_ks_data = overtimeSelectDay.getText().toString();
			 
			 if("请选择加班日期".equals(jb_ks_data)){
				 calendar = Calendar.getInstance();
			 }else{
				 calendar = Calendar.getInstance();
				 calendar.setTime(KingTellerTimeUtils.getConversionFormatDateByString(jb_ks_data, 2));
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
		                    	String xz_day = KingTellerTimeUtils.getConversionFormatStringByDate(
		                    			CalendarDay.from(year, month, day).getDate(), 2);
		                    	overtimeSelectDay.setText(xz_day);
		                    }
	                });
			break;
		case R.id.overtimeSelectStartTime:
			if("请选择加班日期".equals(overtimeSelectDay.getText().toString())){
				T.showShort(mContext, "请选择加班日期!");
				return;
			}
			
			String jb_ks_time = overtimeSelectStartTime.getText().toString();
			
			if("开始时间".equals(jb_ks_time)){
				 calendar = Calendar.getInstance();
			 }else{
				 calendar = Calendar.getInstance();
				 calendar.setTime(KingTellerTimeUtils.getConversionFormatDateByString(jb_ks_time, 3));
			 }
			
			KingTellerDateTimeDialogUtils.showTimePickerDialog(mContext, calendar,  new String [] {"00","30"},
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
	                        setOvertimeUseTime(0, xz_kstime);
	                    }
                });
			
			
			break;
		case R.id.overtimeSelectEndTime:
			if("请选择加班日期".equals(overtimeSelectDay.getText().toString())){
				T.showShort(mContext, "请选择加班日期!");
				return;
			}
			
			String jb_js_time = overtimeSelectEndTime.getText().toString();
			
			if("结束时间".equals(jb_js_time)){
				 calendar = Calendar.getInstance();
			 }else{
				 calendar = Calendar.getInstance();
				 calendar.setTime(KingTellerTimeUtils.getConversionFormatDateByString(jb_js_time, 3));
			 }
			
			KingTellerDateTimeDialogUtils.showTimePickerDialog(mContext, calendar,  new String [] {"00","30"},
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
                        setOvertimeUseTime(1, xz_jstime);
                    }
            });
			
			
			break;
		default:
			break;
		}
	}

	/**
	 * 设置 加班时长
	 * @param type 0: 	startTime
	 * @param type 1: 	endTime
	 */
	public void setOvertimeUseTime(int type, String xzTime) {
		String dqTime = "";
		String Day = overtimeSelectDay.getText().toString();
		String startTime = overtimeSelectStartTime.getText().toString();
		String endTime = overtimeSelectEndTime.getText().toString();
		
		if(type == 0){
			dqTime = startTime;
			startTime = xzTime;
		}else{
			dqTime = endTime;
			endTime = xzTime;
		}
		
		if(!"请选择加班日期".equals(Day) && !"开始时间".equals(startTime) && !"结束时间".equals(endTime)){
			float hour = KingTellerTimeUtils.getBeApartHour(Day + " " + startTime, Day + " " + endTime, 1);
			
			if(hour <= 0){
				if(type == 0){
					overtimeSelectStartTime.setText(dqTime);
				}else{
					overtimeSelectEndTime.setText(dqTime);
				}
				
				T.showShort(mContext, "所选的时间差不能小于等于0!");
			}else{
				if(type == 0){
					overtimeSelectStartTime.setText(xzTime);
				}else{
					overtimeSelectEndTime.setText(xzTime);
				}
				
				mOverTimeData.setOvertimeHourOne(String.valueOf(hour));
				if("1".equals(mOverTimeData.getOvertimeType())){
					mOverTimeOneSonList.get(0).setSonHour(String.valueOf(hour));
					overtimeUseTime.setText(mOverTimeData.getOvertimeHourOne());
				}else{
					for(int i = 0; i < mOverTimeMoreSonList.size(); i++){
						mOverTimeMoreSonList.get(i).setSonHour(String.valueOf(hour));
					}
					setOvertimeMorePeopleList(mOverTimeMoreSonList);
				}
			}

			
		}else{
			if(type == 0){
				overtimeSelectStartTime.setText(xzTime);
			}else{
				overtimeSelectEndTime.setText(xzTime);
			}
		}
	}

	/**
	 * 设置 单人 人员
	 * @param moreList
	 */
	public void setOvertimeOnePeopleList(final List<OverTimeSonPeople> oneList) {
		overtimeUseTime.setTextColor(Color.parseColor("#FF000000"));
		overtimeUseTime.setFocusable(true);
		overtimeUseTime.setEnabled(true);
		
		if(oneList.size() == 0){
			oneList.add(new OverTimeSonPeople("",
					User.getInfo(this).getUserId(),
					User.getInfo(this).getName(), 
					User.getInfo(this).getUserName(), 
					String.valueOf(Float.parseFloat(mOverTimeData.getOvertimeHourOne())), 
					"0",
					""));
			
			overtimeName.setText(oneList.get(0).getSonUserName()
					+ "(" + oneList.get(0).getSonUserAccount() + ")");
			overtimeSubsidyWay.setText("0".equals(oneList.get(0).getRewardType())? "补休" : "计薪");
		}else{
			overtimeName.setText(oneList.get(0).getSonUserName()
					+ "(" + oneList.get(0).getSonUserAccount() + ")");
			overtimeSubsidyWay.setText("0".equals(oneList.get(0).getRewardType())? "补休" : "计薪");
		}
		
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
				if("1".equals(mOverTimeData.getOvertimeType())){
					new Handler().postDelayed(new Runnable() {
			            @Override
			            public void run() {
							String cut = s.toString().substring(s.toString().indexOf(".") + 1);//获取小数点 后的字符
							float mrtime = Float.parseFloat(mOverTimeData.getOvertimeHourOne());
							float dqtime = Float.parseFloat(mOverTimeOneSonList.get(0).getSonHour());
							
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
								mOverTimeOneSonList.get(0).setSonHour(String.valueOf(txtime));
								overtimeUseTime.setText(String.valueOf(txtime));
							}
			            }
			        }, 5000);
				}
			}
			
		});
		
	}
	
	
	/**
	 * 设置 多人 人员
	 * @param moreList
	 */
	public void setOvertimeMorePeopleList(final List<OverTimeSonPeople> moreList) {
		overtimeUseTime.setTextColor(Color.parseColor("#FF666666"));
		overtimeUseTime.setFocusable(false);
		overtimeUseTime.setEnabled(false);
		
		jbyy_group_list.getLayoutList().removeAllViews();
		
		for(int i = 0; i< moreList.size(); i++){
			peopleView = new OverTimePeopleGroupView(mContext, true, 0);
			//删除view 回调
			peopleView.setDeleteViewCallBack( new DeleteViewCallBack() {
				@Override
				public void deleteView(OverTimePeopleGroupView view) {
					String deleteNameId = ((TextView) view.findViewById(R.id.overtimePeopleId)).getText().toString();
					for(int i = 0; i < moreList.size(); i++){
						if(deleteNameId.equals(moreList.get(i).getSonUserId())){
							moreList.remove(i);
							T.showShort(mContext, "删除成功!");
							getMoreTotalTime(moreList);
						}
					}
					jbyy_group_list.setSelectData("已添加(" + jbyy_group_list.getListData().size() + ")");
				}
			});
			//选择 补贴方式 回调
			peopleView.setSelectWardViewCallBack(new SelectWardViewCallBack() {
				@Override
				public void selectWardView(OverTimePeopleGroupView view, String ward) {
					for(int i = 0; i< jbyy_group_list.getListData().size(); i++ ){
						if(view == (OverTimePeopleGroupView) jbyy_group_list.getLayoutList().getChildAt(i)){
							moreList.get(i).setRewardType(ward.equals("补休") ? "0" : "1");
						}
					}
				}
			});
			
			//填写 加班时间 回调
			peopleView.setEditTextUseTimeViewCallBack(new EditTextUseTimeViewCallBack() {
				@Override
				public void editTextUseTimeView(OverTimePeopleGroupView view, String text) {
					//T.showShort(mContext, text);
					
					String cut = text.substring(text.indexOf(".") + 1);//获取小数点 后的字符
					int item = getNumPeopleList(view);
					float mrtime = Float.parseFloat(mOverTimeData.getOvertimeHourOne());
					float dqtime = Float.parseFloat(moreList.get(item).getSonHour());
					
					
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
						moreList.get(item).setSonHour(String.valueOf(txtime));
						getMoreTotalTime(moreList);
					}
					
				}
			});
			
			OverTimePeopleBean people_data = new OverTimePeopleBean(
					moreList.get(i).getSonUserId(),
					moreList.get(i).getSonUserName(),
					moreList.get(i).getSonUserAccount(),
					"0".equals(moreList.get(i).getRewardType()) ? "补休" : "计薪",
					String.valueOf(Float.parseFloat(moreList.get(i).getSonHour())) ,
					"");
			
			peopleView.setData(people_data);
			jbyy_group_list.addItem(peopleView);
			
		}
		jbyy_group_list.setSelectData("已添加(" + jbyy_group_list.getListData().size() + ")");
		
		getMoreTotalTime(moreList);
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
	
	/**
	 * 0暂存 或  1保存
	 */
	public void setSaveOvertime(final int num) {
		if (mOverTimeData == null) {
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
		if(KingTellerJudgeUtils.isEmpty(overtimeUseTime.getText().toString())){
			T.showShort(mContext, "请输入加班时长!");
			return;
		}
		
		if("请选择加班日期".equals(overtimeSelectDay.getText().toString())){
			T.showShort(mContext, "请选择加班日期!");
			return;
		}
		if("请选择开始时间".equals(overtimeSelectStartTime.getText().toString())){
			T.showShort(mContext, "请选择开始时间!");
			return;
		}
		if("请选择结束时间".equals(overtimeSelectEndTime.getText().toString())){
			T.showShort(mContext, "请选择结束时间!");
			return;
		}
		
		if(KingTellerJudgeUtils.isEmpty(overtimeWhy.getText().toString())){
			T.showShort(mContext, "请输入加班原因!");
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
		
		if("1".equals(mOverTimeData.getOvertimeType())){
			params.put("overtimeHourOne", mOverTimeData.getOvertimeHourOne());
			params.put("overtimeHour", mOverTimeOneSonList.get(0).getSonHour());
			
			mOverTimeOneSonList.get(0).setRewardType("补休".equals(overtimeSubsidyWay.getText().toString())? "0" : "1"); 

			params.put("sonList", new Gson().toJson(mOverTimeOneSonList));
		}else{
			params.put("overtimeHourOne", mOverTimeData.getOvertimeHourOne());
			params.put("overtimeHour", overtimeUseTime.getText().toString());
			
			params.put("sonList", new Gson().toJson(mOverTimeMoreSonList));
		}
		
		params.put("nextUserAccounts", mOverTimeData.getNextUserAccounts());
		
		fh.post(KingTellerConfigUtils.CreateUrl(this, KingTellerUrlConfig.OAOAAllAduitWorkFillIn), params, 
				new AjaxHttpCallBack<OverTimeBean>(this,
						new TypeToken<OverTimeBean>() {
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
					public void onDo(final OverTimeBean data) {
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
								            	mOverTimeData.setNextUserAccounts(data.getUlist().get(position).getUserAccount());
								            	setSaveOvertime(1);
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
	 * 判断当前 多人list 是否含有该 人员
	 * @param name
	 * @param account
	 * @return
	 */
	public boolean getIsInMorePeopleList(String nameId) {
		for(int i = 0; i < mOverTimeMoreSonList.size(); i++ ){
			if(nameId.equals(mOverTimeMoreSonList.get(i).getSonUserId())){
				return true;
			}
		}
		return false;
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		KingTellerApplication.removeActivity(this);
	}
}
