package com.kingteller.client.activity.attendance;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.Executors;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kingteller.R;
import com.kingteller.client.KingTellerApplication;
import com.kingteller.client.activity.attendance.checkout.WorkAbsentFromCheckoutActivity;
import com.kingteller.client.activity.attendance.checkout.WorkBoardFromCheckoutActivity;
import com.kingteller.client.activity.attendance.checkout.WorkBusinessTripFromCheckoutActivity;
import com.kingteller.client.activity.attendance.checkout.WorkLeaveFromCheckoutActivity;
import com.kingteller.client.activity.attendance.checkout.WorkOvertimeFormCheckoutActivity;
import com.kingteller.client.activity.attendance.checkout.WorkSickLeaveFromCheckoutActivity;
import com.kingteller.client.activity.attendance.decorators.EventDecorator;
import com.kingteller.client.adapter.WorkAttendanAdapter;
import com.kingteller.client.bean.account.User;
import com.kingteller.client.bean.attendance.AbsentBean;
import com.kingteller.client.bean.attendance.BoardBean;
import com.kingteller.client.bean.attendance.BusinessTripBean;
import com.kingteller.client.bean.attendance.LeaveBean;
import com.kingteller.client.bean.attendance.OverTimeBean;
import com.kingteller.client.bean.attendance.SickLeaveBean;
import com.kingteller.client.bean.attendance.WorkAttendanceBean;
import com.kingteller.client.bean.attendance.WorkAttendanceHistoricalBean;
import com.kingteller.client.config.KingTellerUrlConfig;
import com.kingteller.client.netauth.AjaxHttpCallBack;
import com.kingteller.client.netauth.KTHttpClient;
import com.kingteller.client.utils.KingTellerConfigUtils;
import com.kingteller.client.utils.KingTellerTimeUtils;
import com.kingteller.client.view.KingTellerScrollView;
import com.kingteller.client.view.calendar.CalendarDay;
import com.kingteller.client.view.calendar.MaterialCalendarView;
import com.kingteller.client.view.calendar.OnDateSelectedListener;
import com.kingteller.client.view.calendar.OnMonthChangedListener;
import com.kingteller.client.view.dialog.NormalDialog;
import com.kingteller.client.view.dialog.listener.OnBtnClickL;
import com.kingteller.client.view.dialog.use.KingTellerProgressUtils;
import com.kingteller.client.view.dialog.use.KingTellerPromptDialogUtils;
import com.kingteller.client.view.toast.T;
import com.kingteller.framework.http.AjaxParams;

public class WorkAttendanceHistoricalActivity extends Activity implements 
	OnDateSelectedListener, OnMonthChangedListener, OnItemClickListener, OnClickListener {
	private Context mContext;
	private KingTellerScrollView layout_scroll;
	private ListView mListView;
	private TextView title_text, mTextView, mTitleTextView;
	private Button title_left_btn;
	private WorkAttendanAdapter workAttendanAdapter;
	private List<WorkAttendanceBean> workattendancelist;
	private List<WorkAttendanceHistoricalBean> mHistoricalDay = new ArrayList<WorkAttendanceHistoricalBean>();
	private List<String> mHistoricalDay_str = new ArrayList<String>();
	private MaterialCalendarView mCalendarView;
	private int mPage = 1;
	private NormalDialog dialog = null; 	//初始化通用dialog
	@Override
	public void onCreate(Bundle paramBundle) {
		super.onCreate(paramBundle);
		setContentView(R.layout.layout_work_attendance_historical);
		KingTellerApplication.addActivity(this);
		
		mContext = WorkAttendanceHistoricalActivity.this;
		
		initUi();
		initData();
		
		
	}
	
	private void initUi() {
		title_text = (TextView) findViewById(R.id.layout_main_text);
		title_left_btn = (Button) findViewById(R.id.layout_main_left_btn);
		
		title_text.setText("我的考勤记录");
		
		title_left_btn.setBackgroundResource(R.drawable.btn_back_arrow);
		title_left_btn.setOnClickListener(this);
		
		layout_scroll = (KingTellerScrollView) findViewById(R.id.oa_layout_scrollview);
		
		mListView = (ListView) findViewById(R.id.oa_attendance_historical_list);
		mTextView = (TextView) findViewById(R.id.oa_attendance_historical_text);
		mTitleTextView = (TextView) findViewById(R.id.oa_attendance_historical_titletext);
		workattendancelist = new ArrayList<WorkAttendanceBean>();
		workAttendanAdapter = new WorkAttendanAdapter(mContext, workattendancelist);
		mListView.setAdapter(workAttendanAdapter);
		mListView.setOnItemClickListener(this);
		
		mCalendarView = (MaterialCalendarView) findViewById(R.id.oa_calendarview);
		mCalendarView.setShowOtherDates(MaterialCalendarView.SHOW_ALL);
	}
	
	
	private void initData() {
		
		getHistoricalDay(User.getInfo(this).getUserId(), "");
		
	}
	
	private void initCalendarView(List<String> day, int page) {

		if(page == 1){
			//设置最大日期结束
			String mMaxDay = day.get(0);
			int year = Integer.valueOf(KingTellerTimeUtils.getYearMonthDay(mMaxDay, 2, 0));
			int mon = Integer.valueOf(KingTellerTimeUtils.getYearMonthDay(mMaxDay, 2, 5));
	        Calendar calendar2 = Calendar.getInstance();
	        calendar2.set(year, mon-1, 31);
	        mCalendarView.setMaximumDate(calendar2.getTime());
			
			
//			Calendar calendar1 = Calendar.getInstance(); 
//			calendar1.set(2014, 0, 1);
//	        mCalendarView.setMinimumDate(calendar1.getTime());//设置最小日期开始
//
//	        Calendar calendar2 = Calendar.getInstance();
//	        calendar2.set(calendar2.get(Calendar.YEAR), Calendar.DECEMBER, 31);
//	        mCalendarView.setMaximumDate(calendar2.getTime());//设置最大日期结束
	        
	        //设置当前日期
	        Calendar calendar3 = Calendar.getInstance();
	        mCalendarView.setSelectedDate(calendar3.getTime());
	        
	    	String startstr = new SimpleDateFormat("yyyy-MM-dd").format(calendar3.getTime());
			mTitleTextView.setText(KingTellerTimeUtils.getConversionFormatDay(startstr, 0)); 
	        
			mCalendarView.setOnDateChangedListener(this);
			mCalendarView.setOnMonthChangedListener(this);
			
			
//			mCalendarView.addDecorators(
//		                new MySelectorDecorator(this),
//		                new HighlightWeekendsDecorator(),
//		                oneDayDecorator
//		        );
			
			if(mHistoricalDay_str.contains(startstr)){
				mTextView.setVisibility(View.GONE);
				workattendancelist = new ArrayList<WorkAttendanceBean>();
				getWorkAttendanceData(startstr);
			}else{
				mTextView.setVisibility(View.VISIBLE);
			}
		}
		
		new ApiSimulator().executeOnExecutor(Executors.newSingleThreadExecutor());
	}

	
	
	@Override
	public void onMonthChanged(MaterialCalendarView widget, CalendarDay date) {
		String end_mon = mHistoricalDay_str.get(mHistoricalDay_str.size() - 1);
		String end_day = KingTellerTimeUtils.getYearMonthDay(end_mon, 2, 0) + "-" 
					 + KingTellerTimeUtils.getYearMonthDay(end_mon, 2, 5) + "-" + "01";
		String select_mon = KingTellerTimeUtils.getConversionFormatStringByDate(date.getDate(), 4);
		String select_day = select_mon + "-01";
		
		long miao = KingTellerTimeUtils.getBeApartMillisecond(select_day, end_day, 2);
		if(mPage != 0 && miao > 0){
			mPage++;
			getHistoricalDay(User.getInfo(this).getUserId(), select_mon);
		}
	}


	@Override
	public void onDateSelected(MaterialCalendarView widget, CalendarDay date, boolean selected) {
		String str = KingTellerTimeUtils.getConversionFormatStringByDate(date.getDate(), 2);
		mTitleTextView.setText(KingTellerTimeUtils.getConversionFormatDay(str, 0));
		
		boolean bo = mHistoricalDay_str.contains(str);
		workattendancelist = new ArrayList<WorkAttendanceBean>();
		if(bo){
			mTextView.setVisibility(View.GONE); 
			getWorkAttendanceData(str);
		}else{
			mTextView.setVisibility(View.VISIBLE);
			workAttendanAdapter.setLists(workattendancelist);
			layout_scroll.setListViewHeight(mListView);
		}
	}
	
	
		//单据类型 		kqmgrOvertimeFlow：加班	kqmgrLeaveFlow：请假
		//			kqmgrSickleaveFlow：销假	kqmgrAbsenteeismFlow：旷工
		//			kqmgrFILLFlow：补登		kqmgrTravelFlow：出差
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			
			WorkAttendanceBean data = (WorkAttendanceBean) parent.getAdapter().getItem(position);
			if("kqmgrOvertimeFlow".equals(data.getBillType())){				//加班
				getSaveOvertime(data.getBillId(), data.getBillType(), data.getFlowPosition());
			}else if ("kqmgrLeaveFlow".equals(data.getBillType())){			//请假
				getSaveLeave(data.getBillId(), data.getBillType(), data.getFlowPosition());
			}else if ("kqmgrSickleaveFlow".equals(data.getBillType())){		//销假
				getSaveSickLeave(data.getBillId(), data.getBillType(), data.getFlowPosition());
			}else if ("kqmgrAbsenteeismFlow".equals(data.getBillType())){	//旷工
				getSaveAbsent(data.getBillId(), data.getBillType(), data.getFlowPosition());
			}else if ("kqmgrFILLFlow".equals(data.getBillType())){			//补登
				getSaveBoard(data.getBillId(), data.getBillType(), data.getFlowPosition());
			}else if ("kqmgrTravelFlow".equals(data.getBillType())){		//出差
				getSaveBusinessTrip(data.getBillId(), data.getBillType(), data.getFlowPosition());
			}
		}


    /**
     * Simulate an API call to show how to add decorators
     */
    private class ApiSimulator extends AsyncTask<Void, Void, List<CalendarDay>> {

        @Override
        protected List<CalendarDay> doInBackground(@NonNull Void... voids) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        	
        	List<CalendarDay> dates = new ArrayList<>();
        	for(int i = 0; i < mHistoricalDay_str.size(); i++){
        		String daystr = mHistoricalDay_str.get(i);
        		
            	Calendar calendar = Calendar.getInstance();
            	try {
					calendar.setTime(new SimpleDateFormat("yyyy-M-d").parse(daystr));
				} catch (ParseException e) {
					e.printStackTrace();
				}
            	
            	CalendarDay day = CalendarDay.from(calendar);
            	dates.add(day);
        	}
        	
            return dates;
        }

        @Override
        protected void onPostExecute(@NonNull List<CalendarDay> calendarDays) {
            super.onPostExecute(calendarDays);

            if (isFinishing()) {
                return;
            }

            mCalendarView.addDecorator(new EventDecorator(Color.RED, calendarDays));
        }
    }


	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.layout_main_left_btn:
			finish();
			break;
		}
	}
	
	
	/**
	 * 获取历史数据日期
	 */
	public void getHistoricalDay(String userId, String monthNo) {
		KTHttpClient fh = new KTHttpClient(true);
		AjaxParams params = new AjaxParams();
		params.put("userId", userId);
		params.put("monthNo", monthNo);
		fh.post(KingTellerConfigUtils.CreateUrl(this, KingTellerUrlConfig.GetOAHistoricalDayList), params,
				new AjaxHttpCallBack<List<WorkAttendanceHistoricalBean>>(this,
						new TypeToken<List<WorkAttendanceHistoricalBean>>() {
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
					public void onDo(List<WorkAttendanceHistoricalBean> data) {
						KingTellerProgressUtils.closeProgress();
						if("".equals(data.get(0).getCode())){
							if(mPage == 1){
								mHistoricalDay = data;
							}else{
								mHistoricalDay.addAll(data);
							}
							
							mHistoricalDay_str = new ArrayList<String>();
							for(int i = 0; i < mHistoricalDay.size(); i++){
								mHistoricalDay_str.add(mHistoricalDay.get(i).getBillDay());
							}

							initCalendarView(mHistoricalDay_str, mPage);
						}else{
							mPage = 0;
							T.showShort(mContext, data.get(0).getCode());
						}
						
					};
				});
	}
	
	public void getWorkAttendanceData(String searchDay) {

		KTHttpClient fh = new KTHttpClient(true);
		AjaxParams params = new AjaxParams();
		params.put("searchDay", searchDay);

		fh.post(KingTellerConfigUtils.CreateUrl(this, KingTellerUrlConfig.OAAttendanceList),params, 
				new AjaxHttpCallBack<List<WorkAttendanceBean>>(this,
						new TypeToken<List<WorkAttendanceBean>>() {
						}.getType(), true) {
					
					@Override
					public void onStart() {
						KingTellerProgressUtils.showProgress(mContext, "正在获取当天数据中...");
					}
					
					@Override
					public void onError(int errorNo, String strMsg) {
						super.onError(errorNo, strMsg);
						KingTellerProgressUtils.closeProgress();
						T.showShort(mContext, "数据访问超时!");
					}
					
					@Override
					public void onDo(List<WorkAttendanceBean> data) {
						KingTellerProgressUtils.closeProgress();
						if (data.size() > 0 && data.get(0).getCode().equals("")) {
								workattendancelist = data;
								workAttendanAdapter.setLists(workattendancelist);
								layout_scroll.setListViewHeight(mListView);
						}else {
							T.showShort(mContext, data.get(0).getCode());
							
							mTextView.setVisibility(View.VISIBLE);
							workAttendanAdapter.setLists(new ArrayList<WorkAttendanceBean>());
							layout_scroll.setListViewHeight(mListView);
							
						}
					
					};
				});
	}
	

	
	/**
	 * 获取当前加班单信息 
	 */
	public void getSaveOvertime(String billId, String billType, final String flowPosition) {
		KTHttpClient fh = new KTHttpClient(true);
		AjaxParams params = new AjaxParams();
		params.put("keyId", billId);
		params.put("billType", billType);
		fh.post(KingTellerConfigUtils.CreateUrl(this, KingTellerUrlConfig.GetOAAllWorkDate), params, 
				new AjaxHttpCallBack<OverTimeBean>(this,
						new TypeToken<OverTimeBean>() {
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
					public void onDo(OverTimeBean data) {
						KingTellerProgressUtils.closeProgress();
						if("".equals(data.getCode())){
							Intent intent_ckjbd = new Intent(mContext, WorkOvertimeFormCheckoutActivity.class);
							intent_ckjbd.putExtra("isCheckout", 0);
							intent_ckjbd.putExtra("overtimeData", new Gson().toJson(data));
		    				mContext.startActivity(intent_ckjbd);
						}else{
							OpenDialog(data.getCode(), 1);
						}
						
					};
				});
	}
	

	/**
	 * 获取当前出差单信息
	 */
	public void getSaveBusinessTrip(String billId, String billType, final String flowPosition) {
		KTHttpClient fh = new KTHttpClient(true);
		AjaxParams params = new AjaxParams();
		params.put("keyId", billId);
		params.put("billType", billType);
		fh.post(KingTellerConfigUtils.CreateUrl(this, KingTellerUrlConfig.GetOAAllWorkDate), params, 
				new AjaxHttpCallBack<BusinessTripBean>(this,
						new TypeToken<BusinessTripBean>() {
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
					public void onDo(BusinessTripBean data) {
						KingTellerProgressUtils.closeProgress();
						if("".equals(data.getCode())){
							Intent intent_ckccd = new Intent(mContext, WorkBusinessTripFromCheckoutActivity.class);
							intent_ckccd.putExtra("isCheckout", 0);
							intent_ckccd.putExtra("mBusinessData", new Gson().toJson(data));
		    				mContext.startActivity(intent_ckccd);
						}else{
							OpenDialog(data.getCode(), 1);
						}
						
					};
				});
	}
	
	
	/**
	 * 获取当前补登单信息
	 */
	public void getSaveBoard(String billId, String billType, final String flowPosition) {
		KTHttpClient fh = new KTHttpClient(true);
		AjaxParams params = new AjaxParams();
		params.put("keyId", billId);
		params.put("billType", billType);
		fh.post(KingTellerConfigUtils.CreateUrl(this, KingTellerUrlConfig.GetOAAllWorkDate), params, 
				new AjaxHttpCallBack<BoardBean>(this,
						new TypeToken<BoardBean>() {
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
					public void onDo(BoardBean data) {
						KingTellerProgressUtils.closeProgress();
						if("".equals(data.getCode())){
							Intent intent_ckbdd = new Intent(mContext, WorkBoardFromCheckoutActivity.class);
							intent_ckbdd.putExtra("isCheckout", 0);
							intent_ckbdd.putExtra("mBoardData", new Gson().toJson(data));
		    				mContext.startActivity(intent_ckbdd);
						}else{
							OpenDialog(data.getCode(), 1);
						}
						
					};
				});
	}

	/**
	 * 获取当前出差单信息
	 */
	public void getSaveAbsent(String billId, String billType, final String flowPosition) {
		KTHttpClient fh = new KTHttpClient(true);
		AjaxParams params = new AjaxParams();
		params.put("keyId", billId);
		params.put("billType", billType);
		fh.post(KingTellerConfigUtils.CreateUrl(this, KingTellerUrlConfig.GetOAAllWorkDate), params, 
				new AjaxHttpCallBack<AbsentBean>(this,
						new TypeToken<AbsentBean>() {
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
					public void onDo(AbsentBean data) {
						KingTellerProgressUtils.closeProgress();
						if("".equals(data.getCode())){
							Intent intent_ckkgd = new Intent(mContext, WorkAbsentFromCheckoutActivity.class);
							intent_ckkgd.putExtra("isCheckout", 0);
							intent_ckkgd.putExtra("mAbsenData", new Gson().toJson(data));
		    				mContext.startActivity(intent_ckkgd);
						}else{
							OpenDialog(data.getCode(), 1);
						}
						
					};
				});
	}
	
	/**
	 * 获取当前销假单信息
	 */
	public void getSaveSickLeave(String billId, String billType, final String flowPosition) {
		KTHttpClient fh = new KTHttpClient(true);
		AjaxParams params = new AjaxParams();
		params.put("keyId", billId);
		params.put("billType", billType);
		fh.post(KingTellerConfigUtils.CreateUrl(this, KingTellerUrlConfig.GetOAAllWorkDate), params, 
				new AjaxHttpCallBack<SickLeaveBean>(this,
						new TypeToken<SickLeaveBean>() {
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
					public void onDo(SickLeaveBean data) {
						KingTellerProgressUtils.closeProgress();
						if("".equals(data.getCode())){
							Intent intent_ckxjd = new Intent(mContext, WorkSickLeaveFromCheckoutActivity.class);
							intent_ckxjd.putExtra("isCheckout", 0);
							intent_ckxjd.putExtra("mSickLeaveData", new Gson().toJson(data));
		    				mContext.startActivity(intent_ckxjd);
						}else{
							OpenDialog(data.getCode(), 1);
						}
						
					};
				});
	}
	
	/**
	 * 获取当前请假单信息
	 */
	public void getSaveLeave(String billId, String billType, final String flowPosition) {
		KTHttpClient fh = new KTHttpClient(true);
		AjaxParams params = new AjaxParams();
		params.put("keyId", billId);
		params.put("billType", billType);
		fh.post(KingTellerConfigUtils.CreateUrl(this, KingTellerUrlConfig.GetOAAllWorkDate), params, 
				new AjaxHttpCallBack<LeaveBean>(this,
						new TypeToken<LeaveBean>() {
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
					public void onDo(LeaveBean data) {
						KingTellerProgressUtils.closeProgress();
						if("".equals(data.getCode())){
							Intent intent_ckqjd = new Intent(mContext, WorkLeaveFromCheckoutActivity.class);
							intent_ckqjd.putExtra("isCheckout", 0);
							intent_ckqjd.putExtra("mLeaveData", new Gson().toJson(data));
		    				mContext.startActivity(intent_ckqjd);
						}else{
							OpenDialog(data.getCode(), 1);
						}
						
					};
				});
	}
	
	//打开提示dialog
	public void OpenDialog (String msg, int type){
		if(type == 1){
			
			dialog = new NormalDialog(mContext);
        	KingTellerPromptDialogUtils.showOnePromptDialog(dialog, msg,
					new OnBtnClickL() {
						@Override
						public void onBtnClick() {
							dialog.dismiss();
						}
                    });

		}
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		KingTellerApplication.removeActivity(this);
	}
}
