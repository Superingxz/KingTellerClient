package com.kingteller.client.activity.attendance.checkout;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

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
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.kingteller.R;
import com.kingteller.client.KingTellerApplication;
import com.kingteller.client.activity.base.BaseUploadActivity;
import com.kingteller.client.bean.attendance.LeaveBean;
import com.kingteller.client.bean.attendance.OverTimeBean;
import com.kingteller.client.config.KingTellerStaticConfig;
import com.kingteller.client.config.KingTellerUrlConfig;
import com.kingteller.client.netauth.AjaxHttpCallBack;
import com.kingteller.client.netauth.KTHttpClient;
import com.kingteller.client.utils.KingTellerConfigUtils;
import com.kingteller.client.utils.KingTellerJsonUtils;
import com.kingteller.client.utils.KingTellerJudgeUtils;
import com.kingteller.client.utils.KingTellerTimeUtils;
import com.kingteller.client.view.GroupPicGridView;
import com.kingteller.client.view.GroupPicGridView.OnItemClickLister;
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

public class WorkLeaveFromCheckoutActivity extends BaseUploadActivity{
	
	private TextView title_text;
	private Button title_left_btn, audit_ty, audit_th;
	private Context mContext;
	
	private OverTimePeopleGroupListView ldyj_group_list;
	
	private TextView leaveName;
	private TextView leaveType;
	private TextView leaveStartDay;
	private TextView leaveStratTime;
	private TextView leaveEndDay;
	private TextView leaveEndTime;
	private TextView leaveUserTime;
	private EditText leaveWhy;
	
	private TextView oa_attendance_audit_Csy;
	private EditText oa_attendance_audit_Yj;
	
	private TextView oa_attendance_audit_sqdh;
	private TextView oa_attendance_audit_sqry;
	private TextView oa_attendance_audit_sqrq;
	
	private GroupPicGridView mUploadPicture;

	private int isCheckout = 0;// 0：查看报告	1：审批报告
	
	private String mLeaveData_str;			//补登单数据
	private LeaveBean mLeaveData;			//初始数据
	
	private Bitmap mBitmap;
	private int selectPos;
	
	private String[] mSpyj_StringItem;
	private List<String> mSpyj_StringItemList;
	
	private NormalDialog dialog = null; 	//初始化通用dialog
	
	@Override
	public void onCreate(Bundle paramBundle) {
		super.onCreate(paramBundle);
		setContentView(R.layout.layout_work_attendance_general);
		KingTellerApplication.addActivity(this);
		
		mContext = WorkLeaveFromCheckoutActivity.this;
		isCheckout = getIntent().getIntExtra("isCheckout", 0);
		mLeaveData_str = (String) getIntent().getStringExtra("mLeaveData");
		mLeaveData = KingTellerJsonUtils.getPerson(mLeaveData_str, LeaveBean.class);
		
		KingTellerStaticConfig.OA_ADUIT_ATTENDANCE_ISUDATE = false;
		
		initUI();
		initData();
	}
	
	private void initUI() {
		title_text = (TextView) findViewById(R.id.layout_main_text);
		title_left_btn = (Button) findViewById(R.id.layout_main_left_btn);

		title_left_btn.setBackgroundResource(R.drawable.btn_back_arrow);
		title_left_btn.setOnClickListener(this);

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
		leaveWhy.setTextColor(Color.parseColor("#FF666666"));
		leaveWhy.setFocusable(false);
		leaveWhy.setEnabled(false);
		
		mUploadPicture = (GroupPicGridView) findViewById(R.id.oa_add_pic);
		mUploadPicture.setOnItemClickLister(new OnItemClickLister() {
			@Override
			public void OnItemClick(View view, int pos) {
				selectPos = pos;
			}
		});
		mUploadPicture.setOpt(false);

		oa_attendance_audit_sqdh = (TextView) findViewById(R.id.oa_attendance_audit_sqdh);
		oa_attendance_audit_sqry = (TextView) findViewById(R.id.oa_attendance_audit_sqry);
		oa_attendance_audit_sqrq = (TextView) findViewById(R.id.oa_attendance_audit_sqrq);
		
		((TextView) findViewById(R.id.oa_all_sctp_name)).setText("图片预览：");
		if(isCheckout == 0){
			title_text.setText("请假单查看");
			findViewById(R.id.layout_attendance_audit_djxx_common).setVisibility(View.VISIBLE);//单号信息
			findViewById(R.id.layout_attendance_audit_ldyj_common).setVisibility(View.VISIBLE);//领导意见
			findViewById(R.id.oa_aduit_spyj_layout).setVisibility(View.GONE);//审批意见
			findViewById(R.id.oa_general_aduit_btn_layout).setVisibility(View.GONE);//审批按钮
		}else{
			title_text.setText("请假单审批");
			findViewById(R.id.layout_attendance_audit_djxx_common).setVisibility(View.VISIBLE);
			findViewById(R.id.layout_attendance_audit_ldyj_common).setVisibility(View.VISIBLE);
			findViewById(R.id.oa_aduit_spyj_layout).setVisibility(View.VISIBLE);
			findViewById(R.id.oa_general_aduit_btn_layout).setVisibility(View.VISIBLE);
			
			audit_ty = (Button) findViewById(R.id.oa_general_aduit_ty);
			audit_ty.setOnClickListener(this);
			audit_th = (Button) findViewById(R.id.oa_general_aduit_th);
			audit_th.setOnClickListener(this);
		}
		
		//领导信息头
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
		oa_attendance_audit_sqdh.setText(mLeaveData.getLeaveNo()); 
		oa_attendance_audit_sqry.setText(mLeaveData.getCreateUserName());
		oa_attendance_audit_sqrq.setText(mLeaveData.getCreateTimeStr());
		
		//设置基本信息
		leaveName.setText(mLeaveData.getLeaveUserName() + "(" + mLeaveData.getLeaveUserAccount() + ")");
		leaveType.setText(mLeaveData.getLeaveName());

		leaveStartDay.setText(KingTellerTimeUtils.getYearMonthDay(mLeaveData.getStartTime(), 1, 2));
		leaveStratTime.setText(KingTellerTimeUtils.getYearMonthDay(mLeaveData.getStartTime(), 1, 3));
		
		leaveEndDay.setText(KingTellerTimeUtils.getYearMonthDay(mLeaveData.getEndTime(), 1, 2));
		leaveEndTime.setText(KingTellerTimeUtils.getYearMonthDay(mLeaveData.getEndTime(), 1, 3));

		leaveUserTime.setText(String.valueOf(Float.parseFloat(mLeaveData.getLeaveTimes())));
		leaveWhy.setText(mLeaveData.getLeaveReason());
		
		if("事假".equals(leaveType.getText().toString()) || "调休".equals(leaveType.getText().toString()) || "年假".equals(leaveType.getText().toString())){
    		findViewById(R.id.oa_sctp_layout).setVisibility(View.GONE);//隐藏  上传图片
    	}else{
    		findViewById(R.id.oa_sctp_layout).setVisibility(View.VISIBLE);//显示  上传图片
    	}
		//初始化图片
		if(mLeaveData.getPicList().size() > 0){
	
			int isHavePic = 0;
			for(int i = 0; i< mLeaveData.getPicList().size(); i++){
				//判断文件是否存在
				String file_str_1 = KingTellerStaticConfig.CACHE_PATH.SD_DOWNLOAD + "/" + mLeaveData.getPicList().get(i).getPicName();
				File picFlie = new File(file_str_1);
				if (picFlie.exists()) { // 判断文件是否存在
					isHavePic ++;
				}
			}
			
			if(isHavePic == mLeaveData.getPicList().size()){
				for(int n = 0; n < mLeaveData.getPicList().size(); n++){
					String file_str_2 = KingTellerStaticConfig.CACHE_PATH.SD_DOWNLOAD + "/" + mLeaveData.getPicList().get(n).getPicName();
					mUploadPicture.setItemImageView(n, file_str_2);
					if(n != mLeaveData.getPicList().size() - 1){
						mUploadPicture.addPicItem();
					}
				}
				
			}else{
				KingTellerProgressUtils.showProgress(mContext, "正在下载图片中...");
				new Thread(connectNet).start();
			}
		}
		
		
		//处理历史
		for(int i = 0; i < mLeaveData.getHistoryList().size(); i++){
			OverTimePeopleHistoryGroupView leaderView = new OverTimePeopleHistoryGroupView(mContext);
			leaderView.setData(mLeaveData.getHistoryList().get(i).getExetime(), 
					mLeaveData.getHistoryList().get(i).getExeuser(), 
					mLeaveData.getHistoryList().get(i).getTitle(), 
					mLeaveData.getHistoryList().get(i).getTt(),
					mLeaveData.getHistoryList().get(i).getSuggestion());
			
			ldyj_group_list.addItem(leaderView);
		}
		ldyj_group_list.setSelectData("处理历史");
	}
	
	 //保存图片   与  设置图片
    private Handler connectHanlder = new Handler() {  
        @Override  
        public void handleMessage(Message msg) {  

        	String downPath = "";
        	try {
	        	String filePath = KingTellerStaticConfig.CACHE_PATH.SD_DOWNLOAD + "/" + mLeaveData.getPicList().get(msg.what).getPicName();
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
        	 if(msg.what != mLeaveData.getPicList().size() - 1){
        		 mUploadPicture.addPicItem();
             }
    		
            if(msg.what == mLeaveData.getPicList().size() - 1){
            	KingTellerProgressUtils.closeProgress();
            }

        }  
    }; 
	//下载图片
	private Runnable connectNet = new Runnable(){  
        @Override  
        public void run() {  
            try {  
            	for(int i = 0; i < mLeaveData.getPicList().size(); i++){
            		String fileUrl = mLeaveData.getPicList().get(i).getPicPath();
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
	                    	setAduitLeave(2);
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
	                    	setAduitLeave(3);
	                    }
                });
			break;
		}
	}
	
	/**
	 * 2同意 或  3退回
	 */
	public void setAduitLeave(final int num) {
		if (mLeaveData == null) {
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
		
		params.put("leaveReason", mLeaveData.getLeaveReason());
		
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
