package com.kingteller.client.activity.attendance.fillin;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kingteller.R;
import com.kingteller.client.KingTellerApplication;
import com.kingteller.client.activity.base.BaseUploadActivity;
import com.kingteller.client.bean.account.User;
import com.kingteller.client.bean.attendance.BusinessTripBean;
import com.kingteller.client.bean.attendance.LeaveBean;
import com.kingteller.client.bean.attendance.LeavePicListBean;
import com.kingteller.client.bean.attendance.OADictBean;
import com.kingteller.client.bean.attendance.OADictListBean;
import com.kingteller.client.bean.attendance.WorkAttendanceGetUserTimeBean;
import com.kingteller.client.config.KingTellerStaticConfig;
import com.kingteller.client.config.KingTellerUrlConfig;
import com.kingteller.client.netauth.AjaxHttpCallBack;
import com.kingteller.client.netauth.KTHttpClient;
import com.kingteller.client.utils.BitmapUtils;
import com.kingteller.client.utils.KingTellerConfigUtils;
import com.kingteller.client.utils.KingTellerJsonUtils;
import com.kingteller.client.utils.KingTellerJudgeUtils;
import com.kingteller.client.utils.KingTellerTimeUtils;
import com.kingteller.client.view.GroupPicGridView;
import com.kingteller.client.view.GroupPicGridView.OnItemClickLister;
import com.kingteller.client.view.GroupPicGridView.RemoveItemClickLister;
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
import com.kingteller.framework.http.AjaxFilesParams;
import com.kingteller.framework.http.AjaxParams;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

/**
 * 请假填写
 * @author Administrator
 */
public class WorkLeaveFormActivity extends BaseUploadActivity {

	private TextView title_text;
	private Button title_left_btn, title_rightone_btn, title_righttwo_btn;
	private Context mContext;
	
	private OverTimePeopleGroupListView ldyj_group_list;
	
	private TextView oa_attendance_audit_sqdh;
	private TextView oa_attendance_audit_sqry;
	private TextView oa_attendance_audit_sqrq;
	
	private TextView leaveName;
	private TextView leaveType;
	private TextView leaveStartDay;
	private TextView leaveStratTime;
	private TextView leaveEndDay;
	private TextView leaveEndTime;
	private TextView leaveUserTime;
	private EditText leaveWhy;

	private GroupPicGridView mUploadPicture;

	private String mLeaveData_str;			//补登单数据
	private LeaveBean mLeaveData;			//初始数据
	
	private Bitmap mBitmap;
	
	private List<LeavePicListBean> mLeavePicListData = new ArrayList<LeavePicListBean>();
	private List<OADictBean> mDictList = new ArrayList<OADictBean>();
	private String[] mbdlx_StringItem;

	private int selectPos;
	
	private NormalDialog dialog = null; 	//初始化通用dialog
	
	private Calendar calendar = null;
	 
	private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg){
            super.handleMessage(msg);
            String code = msg.getData().getString("code");
            if(msg.what == 1){
            	
            	leaveStartDay.setText(KingTellerTimeUtils.getYearMonthDay(mLeaveData.getStartTime(), 1, 2));
            	leaveStratTime.setText(KingTellerTimeUtils.getYearMonthDay(mLeaveData.getStartTime(), 1, 3));
            	leaveEndDay.setText(KingTellerTimeUtils.getYearMonthDay(mLeaveData.getEndTime(), 1, 2));
            	leaveEndTime.setText(KingTellerTimeUtils.getYearMonthDay(mLeaveData.getEndTime(), 1, 3));
				
            	leaveUserTime.setText(String.valueOf(Float.parseFloat(mLeaveData.getLeaveTimes())));

            }else if(msg.what == 2){
            	
            	leaveStartDay.setText("日期");
            	leaveStratTime.setText("时间");
            	leaveEndDay.setText("日期");
            	leaveEndTime.setText("时间");
				
            	leaveUserTime.setText("0.0");
            	
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
		
		mContext = WorkLeaveFormActivity.this;
		
		mLeaveData_str = (String) getIntent().getStringExtra("mLeaveData");
		
		KingTellerStaticConfig.OA_WORK_ATTENDANCE_ISUDATE = false;
		
		initUI();
		initData();
		
	}
	
	private void initUI() {
		title_text = (TextView) findViewById(R.id.layout_main_text);
		title_left_btn = (Button) findViewById(R.id.layout_main_left_btn);
		title_rightone_btn = (Button) findViewById(R.id.layout_main_rightone_btn);
		title_righttwo_btn = (Button) findViewById(R.id.layout_main_righttwo_btn);

		title_text.setText("请假单填写");
		
		title_left_btn.setBackgroundResource(R.drawable.btn_back_arrow);
		title_left_btn.setOnClickListener(this);
		
		title_rightone_btn.setBackgroundResource(R.drawable.btn_save);
		title_rightone_btn.setOnClickListener(this);
		
		title_righttwo_btn.setBackgroundResource(R.drawable.btn_check);
		title_righttwo_btn.setOnClickListener(this);
		
		oa_attendance_audit_sqdh = (TextView) findViewById(R.id.oa_attendance_audit_sqdh);
		oa_attendance_audit_sqry = (TextView) findViewById(R.id.oa_attendance_audit_sqry);
		oa_attendance_audit_sqrq = (TextView) findViewById(R.id.oa_attendance_audit_sqrq);
		
		findViewById(R.id.oa_qjdh_layout).setVisibility(View.GONE);//隐藏 请假单号
		findViewById(R.id.oa_czmdd_layout).setVisibility(View.GONE);//隐藏 出差目的地
		
		((TextView) findViewById(R.id.oa_work_people_name)).setText("请假人员：");
		leaveName = (TextView) findViewById(R.id.oa_work_people);
		
		leaveType = (TextView) findViewById(R.id.leaveType);
		
		((TextView) findViewById(R.id.oa_start_time_name)).setText("请假开始时间：");
		leaveStartDay = (TextView) findViewById(R.id.oa_start_day);
		leaveStratTime = (TextView) findViewById(R.id.oa_start_time);
		
		((TextView) findViewById(R.id.oa_end_time_name)).setText("请假结束时间：");
		leaveEndDay = (TextView) findViewById(R.id.oa_end_day);
		leaveEndTime = (TextView) findViewById(R.id.oa_end_time);
		
		((TextView) findViewById(R.id.oa_all_usertime_name)).setText("请假时长：");
		leaveUserTime = (TextView) findViewById(R.id.oa_all_usertime);
		
		((TextView) findViewById(R.id.oa_all_why_name)).setText("请假原因：");
		leaveWhy = (EditText) findViewById(R.id.oa_all_why);
		
		((TextView) findViewById(R.id.oa_all_sctp_name)).setText("上传图片：");
		mUploadPicture = (GroupPicGridView) findViewById(R.id.oa_add_pic);
		mUploadPicture.setOnItemClickLister(new OnItemClickLister() {
			@Override
			public void OnItemClick(View view, int pos) {
				selectPos = pos;
				popdialog.show();
			}
		});
		mUploadPicture.setRemoveItemClickLister(new RemoveItemClickLister() {
			@Override
			public void RemoveItemClick(int pos) {
				mLeavePicListData.remove(pos);
			}
		});
		
		leaveName.setOnClickListener(this);
		leaveType.setOnClickListener(this);
		leaveStartDay.setOnClickListener(this);
		leaveStratTime.setOnClickListener(this);
		leaveEndDay.setOnClickListener(this);
		leaveEndTime.setOnClickListener(this);
		
		//处理历史
		ldyj_group_list = (OverTimePeopleGroupListView) findViewById(R.id.ldyj_group_list);
		ldyj_group_list.setGroupListTwoViewHidden(true);
		ldyj_group_list.setAddBtnHidden(1);
		

	}
	
	
	public void initData() {
		if("".equals(mLeaveData_str)){//新建
			mLeaveData = new LeaveBean();
			mLeaveData.setLeaveUserId(User.getInfo(this).getUserId());
			mLeaveData.setLeaveUserName(User.getInfo(this).getName());
			mLeaveData.setLeaveUserAccount(User.getInfo(this).getUserName());
		}else{//修改
			mLeaveData = KingTellerJsonUtils.getPerson(mLeaveData_str, LeaveBean.class);
		}
		
		mLeaveData.setNextUserAccounts("");
		setData(mLeaveData);
		
		KingTellerConfigUtils.hideInputMethod(this);
	}
	
	private void setData(LeaveBean data) {
		//初始化单号信息
		if(data.getLeaveId() != null){
			findViewById(R.id.layout_attendance_audit_djxx_common).setVisibility(View.VISIBLE);
			oa_attendance_audit_sqdh.setText(data.getLeaveNo()); 
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
		leaveName.setText(data.getLeaveUserName() + "(" + data.getLeaveUserAccount() + ")");
		leaveType.setText(KingTellerJudgeUtils.isEmpty(data.getLeaveName()) ? "请选择请假类型" : data.getLeaveName());
		if(KingTellerJudgeUtils.isEmpty(data.getStartTime())){
			leaveStartDay.setText("日期");
			leaveStratTime.setText("时间");
		}else{
			leaveStartDay.setText(KingTellerTimeUtils.getYearMonthDay(data.getStartTime(), 1, 2));
			leaveStratTime.setText(KingTellerTimeUtils.getYearMonthDay(data.getStartTime(), 1, 3));
		}
		if(KingTellerJudgeUtils.isEmpty(data.getEndTime())){
			leaveEndDay.setText("日期");
			leaveEndTime.setText("时间");
		}else{
			leaveEndDay.setText(KingTellerTimeUtils.getYearMonthDay(data.getEndTime(), 1, 2));
			leaveEndTime.setText(KingTellerTimeUtils.getYearMonthDay(data.getEndTime(), 1, 3));
		}
		leaveUserTime.setText(KingTellerJudgeUtils.isEmpty(data.getLeaveTimes()) ? "0.0" :  String.valueOf(Float.parseFloat(data.getLeaveTimes())));
		leaveWhy.setText(KingTellerJudgeUtils.isEmpty(data.getLeaveReason()) ? "" :  data.getLeaveReason());
		 
		if("请选择请假类型".equals(leaveType.getText().toString()) || "事假".equals(leaveType.getText().toString()) 
				|| "调休".equals(leaveType.getText().toString()) || "年假".equals(leaveType.getText().toString())){
    		findViewById(R.id.oa_sctp_layout).setVisibility(View.GONE);//隐藏  上传图片
    	}else{
    		findViewById(R.id.oa_sctp_layout).setVisibility(View.VISIBLE);//显示  上传图片
    	}
		
		//初始化图片
		if(data.getPicList() != null && data.getPicList().size() > 0){
			mLeavePicListData = data.getPicList();
			
			int isHavePic = 0;
			for(int i = 0; i< mLeavePicListData.size(); i++){
				//判断文件是否存在
				String file_str_1 = KingTellerStaticConfig.CACHE_PATH.SD_DOWNLOAD + "/" + mLeavePicListData.get(i).getPicName();
				File picFlie = new File(file_str_1);
				if (picFlie.exists()) { // 判断文件是否存在
					isHavePic ++;
				}
			}
			
			if(isHavePic == mLeavePicListData.size()){
				for(int n = 0; n < mLeavePicListData.size(); n++){
					String file_str_2 = KingTellerStaticConfig.CACHE_PATH.SD_DOWNLOAD + "/" + mLeavePicListData.get(n).getPicName();
					mUploadPicture.setItemImageView(n, file_str_2);
		    		mUploadPicture.addPicItem();
				}
				
			}else{
				KingTellerProgressUtils.showProgress(mContext, "正在下载图片中...");
				new Thread(connectNet).start();
			}
		}
	}
	
    //保存图片   与  设置图片
    private Handler connectHanlder = new Handler() {  
        @Override  
        public void handleMessage(Message msg) {  

        	String downPath = "";
        	try {
	        	String filePath = KingTellerStaticConfig.CACHE_PATH.SD_DOWNLOAD + "/" + mLeavePicListData.get(msg.what).getPicName();
	        	downPath = filePath;
	        	File myCaptureFile = new File(filePath);  
	            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(myCaptureFile));  
	            mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);  
	     
				bos.flush();
				bos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}  
//        	T.showShort(mContext, "下载图片" + msg.what + "成功!" ); 
        	
        	mUploadPicture.setItemImageView(msg.what, downPath);
    		mUploadPicture.addPicItem();
    		
            if(msg.what == mLeavePicListData.size() - 1){
            	KingTellerProgressUtils.closeProgress();
            }

        }  
    }; 
	//下载图片
	private Runnable connectNet = new Runnable(){  
        @Override  
        public void run() {  
            try {  
            	for(int i = 0; i < mLeavePicListData.size(); i++){
            		String fileUrl = mLeavePicListData.get(i).getPicPath();
            		//******** 方法2：取得的是InputStream，直接从InputStream生成bitmap ***********/  
                    mBitmap = BitmapFactory.decodeStream(getImageStream(fileUrl));  
                    //********************************************************************/
                    // 发送消息，通知handler在主线程中更新UI  
                    connectHanlder.sendEmptyMessage(i);
            	}
            } catch (Exception e) {  
            	T.showShort(mContext, "无法链接网络!");
                e.printStackTrace();  
            }  
  
        }  
  
    };  
 
    //网络访问
    public InputStream getImageStream(String path) throws Exception{  
        URL url = new URL(path);  
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();  
        conn.setConnectTimeout(5 * 1000);  
        conn.setRequestMethod("GET");  
        if(conn.getResponseCode() == HttpURLConnection.HTTP_OK){  
            return conn.getInputStream();  
        }  
        return null;  
    } 
    
	@Override
	public void OnCallBackPhoto(String picPath) {
		super.OnCallBackPhoto(picPath);
		
		File file = BitmapUtils.createBitmap(picPath,
				KingTellerStaticConfig.SCREEN.Width,
				KingTellerStaticConfig.SCREEN.Height,
				"");

		mLeavePicListData.add(selectPos, new LeavePicListBean("" , picPath, file, file.getName()));
		
		mUploadPicture.setItemImageView(selectPos, picPath);
		mUploadPicture.addPicItem();

	}
	
	@Override
	public void onClick(View view) {
		super.onClick(view);
		switch (view.getId()) {
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
		                    	setSaveLeave(0);
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
		                    	setSaveLeave(1);
		                    }
	                });
				break;
			case R.id.leaveType:
				getDictListData();
				break;
			case R.id.oa_start_day:
				 String qj_ks_data = leaveStartDay.getText().toString();
				 if("日期".equals(qj_ks_data)){
					 calendar = Calendar.getInstance();
				 }else{
					 calendar = Calendar.getInstance();
					 calendar.setTime(KingTellerTimeUtils.getConversionFormatDateByString(qj_ks_data, 2));
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
			                    	setLeaveUserTime(0, xz_ksday);
			                    }
		                });
				 
				break;
			case R.id.oa_start_time:
				String qj_ks_time = leaveStratTime.getText().toString();
				
				if("时间".equals(qj_ks_time)){
					 calendar = Calendar.getInstance();
				 }else{
					 calendar = Calendar.getInstance();
					 calendar.setTime(KingTellerTimeUtils.getConversionFormatDateByString(qj_ks_time, 3));
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
		                    	setLeaveUserTime(2, xz_kstime);
		                    }
	                });
				
				break;
			case R.id.oa_end_day:
				 String qj_js_data = leaveEndDay.getText().toString();
				 
				 if("日期".equals(qj_js_data)){
					 calendar = Calendar.getInstance();
				 }else{
					 calendar = Calendar.getInstance();
					 calendar.setTime(KingTellerTimeUtils.getConversionFormatDateByString(qj_js_data, 2));
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
			                    	setLeaveUserTime(1, xz_jsday);
			                    }
		                });

				break;
			case R.id.oa_end_time:
				String qj_js_time = leaveEndTime.getText().toString();
				
				if("时间".equals(qj_js_time)){
					 calendar = Calendar.getInstance();
				 }else{
					 calendar = Calendar.getInstance();
					 calendar.setTime(KingTellerTimeUtils.getConversionFormatDateByString(qj_js_time, 3));
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
	                 	   	setLeaveUserTime(3, xz_jstime);
	                    }
                });

				break;
			default:
				break;
		}
	}
	
	/**
	 * 获取  字典配置list		请假类型	KQGL_LEAVE_TYPE
	 */
	public void getDictListData() {
		
		KTHttpClient fh = new KTHttpClient(true);
		AjaxParams params = new AjaxParams();
		
		params.put("dictCode", "KQGL_LEAVE_TYPE");

		fh.post(KingTellerConfigUtils.CreateUrl(this, KingTellerUrlConfig.GetOADictList), params, 
				new AjaxHttpCallBack<OADictListBean>(this,
						new TypeToken<OADictListBean>() {
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
					public void onDo(OADictListBean data) {
						KingTellerProgressUtils.closeProgress();
						mDictList = data.getDictList();
						
						if("".equals(data.getCode())){
							mbdlx_StringItem = new String[data.getDictList().size()];
							for(int i = 0; i < data.getDictList().size(); i++){
								mbdlx_StringItem [i] = data.getDictList().get(i).getDictValue();
							}
							
							final NormalListDialog dialog_bdlx = new NormalListDialog(mContext, mbdlx_StringItem);
							dialog_bdlx.title("请选择请假类型")//
						                .layoutAnimation(null)
						                .titleBgColor(Color.parseColor("#409ED7"))//
						                .itemPressColor(Color.parseColor("#85D3EF"))//
						                .itemTextColor(Color.parseColor("#303030"))//
						                .show();
							dialog_bdlx.setOnOperItemClickL(new OnOperItemClickL() {
						            @Override
						            public void onOperItemClick(AdapterView<?> parent, View view, int position, long id) {
						            	dialog_bdlx.dismiss();
						            	
						            	String qjlx_lx = mDictList.get(position).getDictType();
						            	String qjlx_str = mDictList.get(position).getDictValue();
						            	
						            	leaveType.setText(qjlx_str);
						            	
						            	mLeaveData.setLeaveType(qjlx_lx);
						            	mLeaveData.setLeaveName(qjlx_str);
						            	
						            	if("事假".equals(qjlx_str) || "调休".equals(qjlx_str) || "年假".equals(qjlx_str)){
						            		findViewById(R.id.oa_sctp_layout).setVisibility(View.GONE);//隐藏  上传图片
						            	}else{
						            		findViewById(R.id.oa_sctp_layout).setVisibility(View.VISIBLE);//显示  上传图片
						            	}
						            }
						        });
							 
						}else{
							T.showShort(mContext, data.getCode());
						}
					};
				});
	}
	
	/**
	 * 设置 出差时长
	 * @param type 0: 	startDay
	 * @param type 1: 	endDay
	 * @param type 2: 	startTime
	 * @param type 3: 	endTime
	 */
	public void setLeaveUserTime(int type, String xzTime) {
		String dqTime = "";
		String startDay = leaveStartDay.getText().toString();
		String endDay = leaveEndDay.getText().toString();
		String startTime = leaveStratTime.getText().toString();
		String endTime = leaveEndTime.getText().toString();
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
					leaveStartDay.setText(dqTime);
				}else if(type == 1){
					leaveEndDay.setText(dqTime);
				}else if(type == 2){
					leaveStratTime.setText(dqTime);
				}else{
					leaveEndTime.setText(dqTime);
				}
				
				T.showShort(mContext, "所选的时间差不能小于等于0!");
			}else{
				getLeaveUserTime(mLeaveData.getLeaveUserId(), startDay + " " + startTime, endDay + " " + endTime);
			}
		}else{
			if(type == 0){
				leaveStartDay.setText(xzTime);
			}else if(type == 1){
				leaveEndDay.setText(xzTime);
			}else if(type == 2){
				leaveStratTime.setText(xzTime);
			}else{
				leaveEndTime.setText(xzTime);
			}
		}
	}
	

	/**
	 * 获取  出差时长
	 */
	public void getLeaveUserTime(String useId, String ksTime, String jsTime) {
		
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
							mLeaveData.setLeaveTimes(data.getTotalHour());
							mLeaveData.setLeaveDay(data.getDay());
							mLeaveData.setLeaveHour(data.getHour());
							mLeaveData.setStartTime(data.getNewBeginDateTime());
							mLeaveData.setEndTime(data.getNewEndDateTime());
							
							mLeaveData.setFirstDayHour(data.getBeginHour());
							mLeaveData.setEndDayHour(data.getEndHour());
							message.what = 1;
						}else{
							mLeaveData.setLeaveTimes("");
							mLeaveData.setLeaveDay("");
							mLeaveData.setLeaveHour("");
							mLeaveData.setStartTime("");
							mLeaveData.setEndTime("");
							
							mLeaveData.setFirstDayHour("");
							mLeaveData.setEndDayHour("");
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
	public void setSaveLeave(final int num) {
		if (mLeaveData == null) {
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
		if("请选择请假类型".equals(leaveType.getText().toString())){
			T.showShort(mContext, "请选择请假类型!");
			return;
		}
		if("日期".equals(leaveStartDay.getText().toString())){
			T.showShort(mContext, "请选择请假开始日期!");
			return;
		}
		if("时间".equals(leaveStratTime.getText().toString())){
			T.showShort(mContext, "请选择请假开始时间!");
			return;
		}
		
		if("日期".equals(leaveEndDay.getText().toString())){
			T.showShort(mContext, "请选择请假结束日期!");
			return;
		}
		if("时间".equals(leaveEndTime.getText().toString())){
			T.showShort(mContext, "请选择请假结束时间!");
			return;
		}
		
		if(Float.parseFloat(leaveUserTime.getText().toString()) <= 0){
			T.showShort(mContext, "所选的时间差不能小于等于0!");
			return;
		}
		
		if(KingTellerJudgeUtils.isEmpty(leaveWhy.getText().toString())){
			T.showShort(mContext, "请输入请假原因!");
			return;
		}
    	
    	if( !"事假".equals(leaveType.getText().toString()) && 
    		!"调休".equals(leaveType.getText().toString()) &&
    		!"年假".equals(leaveType.getText().toString())){
    		
    		if(mUploadPicture.getImages().size() == 0){
    			T.showShort(mContext, "请上传图片!");
    			return;
    		}
    	}
		
		KTHttpClient fh = new KTHttpClient(true);
		AjaxFilesParams params = new AjaxFilesParams();
		
		params.put("saveflag", saveflag);
		params.put("billType", "kqmgrLeaveFlow");
		
		params.put("leaveId", mLeaveData.getLeaveId());
		params.put("leaveNo", mLeaveData.getLeaveNo());
		
		params.put("createUserId", mLeaveData.getCreateUserId());
		params.put("createUserName", mLeaveData.getCreateUserName());
		params.put("createTimeStr", mLeaveData.getCreateTimeStr());
		
		params.put("flowStatus", mLeaveData.getFlowStatus());
		params.put("leaveTimes", mLeaveData.getLeaveTimes());
		params.put("leaveDay", mLeaveData.getLeaveDay());
		params.put("leaveHour", mLeaveData.getLeaveHour());
		
		params.put("firstDayHour", mLeaveData.getFirstDayHour());
		params.put("endDayHour", mLeaveData.getEndDayHour());
		
		params.put("leaveType", mLeaveData.getLeaveType());
		params.put("leaveName", mLeaveData.getLeaveName());
		
		params.put("startTime", mLeaveData.getStartTime());
		params.put("endTime", mLeaveData.getEndTime());
		
		params.put("leaveUserId", mLeaveData.getLeaveUserId());
		params.put("leaveUserName", mLeaveData.getLeaveUserName());
		params.put("leaveUserAccount", mLeaveData.getLeaveUserAccount());
		params.put("leaveReason", leaveWhy.getText().toString());
		
		params.put("nextUserAccounts", mLeaveData.getNextUserAccounts());

		if(mLeavePicListData.size() > 0 ){
			for(int i = 0; i< mLeavePicListData.size(); i++){
				if(KingTellerJudgeUtils.isEmpty(mLeavePicListData.get(i).getPicId())){
					params.put("files", mLeavePicListData.get(i).getPicFlie());
				}
			}

			params.put("picList", new Gson().toJson(mLeavePicListData)); 
		}else{
			params.put("picList", "[]");
		}
		
		
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
								            	mLeaveData.setNextUserAccounts(data.getUlist().get(position).getUserAccount());
								            	setSaveLeave(1);
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
