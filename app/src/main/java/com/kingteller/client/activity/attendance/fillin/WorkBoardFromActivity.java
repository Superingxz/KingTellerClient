package com.kingteller.client.activity.attendance.fillin;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.google.gson.reflect.TypeToken;
import com.kingteller.R;
import com.kingteller.client.KingTellerApplication;
import com.kingteller.client.activity.attendance.search.WorkAttendanceSearchPersonnelActivity;
import com.kingteller.client.bean.account.User;
import com.kingteller.client.bean.attendance.BoardBean;
import com.kingteller.client.bean.attendance.OADictBean;
import com.kingteller.client.bean.attendance.OADictListBean;
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
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

/**
 * 补登填写
 * @author Administrator
 */
public class WorkBoardFromActivity extends Activity implements OnClickListener{

	private TextView title_text;
	private Button title_left_btn, title_rightone_btn, title_righttwo_btn;
	private Context mContext;
	
	private OverTimePeopleGroupListView ldyj_group_list;
	
	private TextView oa_attendance_audit_sqdh;
	private TextView oa_attendance_audit_sqry;
	private TextView oa_attendance_audit_sqrq;
	
	private TextView boardName;
	private TextView boardStartDay;
	private TextView boardStratTime;
	private TextView boardType;
	private EditText boardTypeQt;
	private EditText boardWhy;

	private String mBoardData_str;			//补登单数据
	private BoardBean mBoardData;			//初始数据
	
	private List<OADictBean> mDictList = new ArrayList<OADictBean>();
	private String[] mbdlx_StringItem;
	
	private NormalDialog dialog = null; 	//初始化通用dialog
	
	private Calendar calendar = null;
	
	@Override
	public void onCreate(Bundle paramBundle) {
		super.onCreate(paramBundle);
		setContentView(R.layout.layout_work_attendance_general);
		KingTellerApplication.addActivity(this);
		
		mContext = WorkBoardFromActivity.this;
	
		mBoardData_str = (String) getIntent().getStringExtra("mBoardData");
		
		KingTellerStaticConfig.OA_WORK_ATTENDANCE_ISUDATE = false;
		
		initUI();
		initData();
	}
	
	private void initUI() {
		title_text = (TextView) findViewById(R.id.layout_main_text);
		title_left_btn = (Button) findViewById(R.id.layout_main_left_btn);
		title_rightone_btn = (Button) findViewById(R.id.layout_main_rightone_btn);
		title_righttwo_btn = (Button) findViewById(R.id.layout_main_righttwo_btn);

		title_text.setText("补登单填写");
		
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
		
		
		((TextView) findViewById(R.id.oa_work_people_name)).setText("补登人员：");
		boardName = (TextView) findViewById(R.id.oa_work_people);
		
		((TextView) findViewById(R.id.oa_start_time_name)).setText("补登时间：");
		boardStartDay = (TextView) findViewById(R.id.oa_start_day);
		boardStratTime = (TextView) findViewById(R.id.oa_start_time);
		
		findViewById(R.id.oa_end_time_layout).setVisibility(View.GONE);//隐藏 结束时间
		findViewById(R.id.oa_usertime_layout).setVisibility(View.GONE);//隐藏 时长
		
		((TextView) findViewById(R.id.oa_all_why_name)).setText("补登原因：");
		boardWhy = (EditText) findViewById(R.id.oa_all_why);
		
		findViewById(R.id.oa_bdxx_layout).setVisibility(View.VISIBLE);//显示   补登类型
		boardType = (TextView) findViewById(R.id.oa_all_bdxx_lx);
		boardTypeQt = (EditText) findViewById(R.id.oa_all_bdxx_qtsm);

		boardName.setOnClickListener(this);
		boardType.setOnClickListener(this);
		boardStartDay.setOnClickListener(this);
		boardStratTime.setOnClickListener(this);
		
		//处理历史
		ldyj_group_list = (OverTimePeopleGroupListView) findViewById(R.id.ldyj_group_list);
		ldyj_group_list.setGroupListTwoViewHidden(true);
		ldyj_group_list.setAddBtnHidden(1);

	}
	

	public void initData() {
		if("".equals(mBoardData_str)){//新建
			mBoardData = new BoardBean();
			mBoardData.setFillUserId(User.getInfo(this).getUserId());
			mBoardData.setFillUserName(User.getInfo(this).getName());
			mBoardData.setFillUserAccount(User.getInfo(this).getUserName());
		}else{//修改
			mBoardData = KingTellerJsonUtils.getPerson(mBoardData_str, BoardBean.class);
		}
		
		mBoardData.setNextUserAccounts("");
		setData(mBoardData);
		
		KingTellerConfigUtils.hideInputMethod(this);
	}
	
	private void setData(BoardBean data) {
		//初始化单号信息
		if(data.getFillId() != null){
			findViewById(R.id.layout_attendance_audit_djxx_common).setVisibility(View.VISIBLE);
			oa_attendance_audit_sqdh.setText(data.getFillNo()); 
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
		boardName.setText(data.getFillUserName() + "(" + data.getFillUserAccount() + ")");
		if(KingTellerJudgeUtils.isEmpty(data.getFillTime())){
			boardStartDay.setText("日期");
			boardStratTime.setText("时间");
		}else{
			boardStartDay.setText(KingTellerTimeUtils.getYearMonthDay(data.getFillTime(), 1, 2));
			boardStratTime.setText(KingTellerTimeUtils.getYearMonthDay(data.getFillTime(), 1, 3));
		}
		boardWhy.setText(KingTellerJudgeUtils.isEmpty(data.getFillReason()) ? "" :  data.getFillReason());
		boardType.setText(KingTellerJudgeUtils.isEmpty(data.getFillTypeName()) ? "请选择补登类型" :  data.getFillTypeName());
		if("其他".equals(data.getFillTypeName())){
			boardTypeQt.setVisibility(View.VISIBLE);
			boardTypeQt.setText(data.getFillTypeOth());
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
						mBoardData.setFillUserId(list.get(0).getUserId());
						mBoardData.setFillUserName(list.get(0).getUserName());
						mBoardData.setFillUserAccount(list.get(0).getUserAccount());
						
						boardName.setText(mBoardData.getFillUserName() + "(" + mBoardData.getFillUserAccount() + ")");
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
	                    	setSaveBoard(0);
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
	                    	setSaveBoard(1);
	                    }
                });
			break;
		case R.id.oa_work_people:
			Intent intent = new Intent(mContext, WorkAttendanceSearchPersonnelActivity.class);
			intent.putExtra("peopleType", "1");
			startActivityForResult(intent, KingTellerStaticConfig.OACODE_SELECT_PEOPLE_NUM);
			break;
		case R.id.oa_all_bdxx_lx:
			getDictListData();
			break;
		case R.id.oa_start_day:
			 String bd_ks_data = boardStartDay.getText().toString();
			 
			 if("日期".equals(bd_ks_data)){
				 calendar = Calendar.getInstance();
			 }else{
				 calendar = Calendar.getInstance();
				 calendar.setTime(KingTellerTimeUtils.getConversionFormatDateByString(bd_ks_data, 2));
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
		                    	boardStartDay.setText(xz_day);
		                    }
	                });
			break;
		case R.id.oa_start_time:
			String bd_time = boardStratTime.getText().toString();
			
			if("时间".equals(bd_time)){
				 calendar = Calendar.getInstance();
			}else{
				 calendar = Calendar.getInstance();
				 calendar.setTime(KingTellerTimeUtils.getConversionFormatDateByString(bd_time, 3));
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
	                    	String xz_time = KingTellerTimeUtils.getConversionFormatTime(hour + ":" + minute, 1);
	                        boardStratTime.setText(xz_time);
	                    }
              });
			
			break;
		default:
			break;
		}
	}
	
	/**
	 * 获取  字典配置list		补登类型	KQGL_FILL_TYPE
	 */
	public void getDictListData() {
		
		KTHttpClient fh = new KTHttpClient(true);
		AjaxParams params = new AjaxParams();
		
		params.put("dictCode", "KQGL_FILL_TYPE");

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
							dialog_bdlx.title("请选择补登类型")//
						                .layoutAnimation(null)
						                .titleBgColor(Color.parseColor("#409ED7"))//
						                .itemPressColor(Color.parseColor("#85D3EF"))//
						                .itemTextColor(Color.parseColor("#303030"))//
						                .show();
							dialog_bdlx.setOnOperItemClickL(new OnOperItemClickL() {
						            @Override
						            public void onOperItemClick(AdapterView<?> parent, View view, int position, long id) {
						            	dialog_bdlx.dismiss();
						            	boardType.setText(mDictList.get(position).getDictValue());
						            	if("其他".equals(mDictList.get(position).getDictValue())){
						        			boardTypeQt.setVisibility(View.VISIBLE);
						            	}else{
						            		boardTypeQt.setVisibility(View.GONE);
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
	 * 0暂存 或  1保存
	 */
	public void setSaveBoard(final int num) {
		if (mBoardData == null) {
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
		if("日期".equals(boardStartDay.getText().toString())){
			T.showShort(mContext, "请选择补登日期!");
			return;
		}
		if("时间".equals(boardStratTime.getText().toString())){
			T.showShort(mContext, "请选择补登时间!");
			return;
		}
		
		
		if(KingTellerJudgeUtils.isEmpty(boardWhy.getText().toString())){
			T.showShort(mContext, "请输入补登原因!");
			return;
		}
		
		if("请选择补登类型".equals(boardType.getText().toString())){
			T.showShort(mContext, "请选择补登类型!");
			return;
		}
		
		if("其他".equals(boardType.getText().toString()) && KingTellerJudgeUtils.isEmpty(boardTypeQt.getText().toString())){
			T.showShort(mContext, "请输入其他说明!");
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
		params.put("fillTime", boardStartDay.getText().toString() + " " + boardStratTime.getText().toString());
		
		params.put("fillUserId", mBoardData.getFillUserId());
		params.put("fillUserName", mBoardData.getFillUserName());
		params.put("fillUserAccount", mBoardData.getFillUserAccount());
		
		if("请选择补登类型".equals(boardType.getText().toString())){
			params.put("fillType", "");
			params.put("fillTypeName", "");
			params.put("fillTypeOth", "");
		}else{
			if(mDictList.size() > 0){
				for(int i = 0; i < mDictList.size(); i++){
					if(boardType.getText().toString().equals(mDictList.get(i).getDictValue())){
						params.put("fillType", mDictList.get(i).getDictType());
						params.put("fillTypeName", mDictList.get(i).getDictValue());
						if("其他".equals(mDictList.get(i).getDictValue())){
							params.put("fillTypeOth", boardTypeQt.getText().toString());
						}else{
							params.put("fillTypeOth", "");
						}
					}
				}
			}else{
				params.put("fillType", mBoardData.getFillType());
				params.put("fillTypeName", mBoardData.getFillTypeName());
				if("其他".equals(mBoardData.getFillTypeName())){
					params.put("fillTypeOth", boardTypeQt.getText().toString());
				}else{
					params.put("fillTypeOth", "");
				}
			}
			
		}
		
		params.put("fillReason", boardWhy.getText().toString());
		
		params.put("nextUserAccounts", mBoardData.getNextUserAccounts());

		fh.post(KingTellerConfigUtils.CreateUrl(this, KingTellerUrlConfig.OAOAAllAduitWorkFillIn), params, 
				new AjaxHttpCallBack<BoardBean>(this,
						new TypeToken<BoardBean>() {
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
					public void onDo(final BoardBean data) {
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
								            	mBoardData.setNextUserAccounts(data.getUlist().get(position).getUserAccount());
								            	setSaveBoard(1);
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
