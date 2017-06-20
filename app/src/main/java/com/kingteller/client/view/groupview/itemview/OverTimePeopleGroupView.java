package com.kingteller.client.view.groupview.itemview;

import java.util.Arrays;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kingteller.R;
import com.kingteller.client.activity.attendance.search.WorkAttendanceSearchPersonnelActivity;
import com.kingteller.client.bean.attendance.OverTimePeopleBean;
import com.kingteller.client.config.KingTellerStaticConfig;
import com.kingteller.client.view.GroupViewBase;
import com.kingteller.client.view.dialog.NormalListDialog;
import com.kingteller.client.view.dialog.listener.OnOperItemClickL;

public class OverTimePeopleGroupView  extends GroupViewBase implements Cloneable {

	private Context mContext;
	private TextView overtimePeopleId, overtimePeopleName, overtimeWard , overtimeCheckout;
	private EditText overtimeUsed;
	
	private String[] mJbbtfs_StringItem = new String[] {"补休", "计薪"};
	private List<String> mJbbtfs_StringItemList = Arrays.asList(new String[] {"补休", "计薪"});

	private DeleteViewCallBack deleteViewCallBack;
	
	private SelectWardViewCallBack selectWardViewCallBack;
	
	private EditTextUseTimeViewCallBack editTextUseTimeViewCallBack;
	
	public OverTimePeopleGroupView(Context context) {
		super(context);
		this.mContext = context;
	}

	public OverTimePeopleGroupView(Context context, boolean isdel, int type) {
		super(context, isdel, type);
		this.mContext = context;
	}
	
	@Override
	protected void initView() {
		LayoutInflater.from(getContext()).inflate(R.layout.item_add_overtimepeople, this);
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.WRAP_CONTENT);
		lp.setMargins(0, 10, 0, 0);
		setLayoutParams(lp);

	

		findViewById(R.id.btn_delete).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				
				((LinearLayout) getParent()).removeView(OverTimePeopleGroupView.this);
				
				if (deleteViewCallBack != null) {
					deleteViewCallBack.deleteView(OverTimePeopleGroupView.this);
				}
			}
		});

		overtimePeopleId = (TextView) findViewById(R.id.overtimePeopleId);
		overtimePeopleName = (TextView) findViewById(R.id.overtimePeopleName);
		overtimeWard = (TextView) findViewById(R.id.overtimereWard);
		overtimeUsed = (EditText) findViewById(R.id.overtimereUsed);
		overtimeCheckout = (TextView) findViewById(R.id.overtimereCheckout);
		
		//选择人员
		findViewById(R.id.add_oa_people_layout).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(mContext, WorkAttendanceSearchPersonnelActivity.class);
				intent.putExtra("peopleType", "3");
				((Activity) mContext).startActivityForResult(intent, KingTellerStaticConfig.OACODE_SELECT_PEOPLE_NUM);
				((LinearLayout) getParent()).setTag(R.id.overtimePeopleId, overtimePeopleId);
				((LinearLayout) getParent()).setTag(R.id.overtimePeopleName, overtimePeopleName);
			}
		});
		
		

		//选择补贴方式
		findViewById(R.id.add_oa_ward_layout).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				final NormalListDialog dialog_btfs = new NormalListDialog(mContext, mJbbtfs_StringItem);
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
			            	overtimeWard.setText(mJbbtfs_StringItemList.get(position).toString());
			            	
			            	if (selectWardViewCallBack != null) {
			            		selectWardViewCallBack.selectWardView(OverTimePeopleGroupView.this, 
			            				mJbbtfs_StringItemList.get(position).toString());
							}
			            }
			        });
			}
		});
		
		//输入加班时间   后
		overtimeUsed.addTextChangedListener(new TextWatcher() {
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
		            	if (editTextUseTimeViewCallBack != null) {
							editTextUseTimeViewCallBack.editTextUseTimeView(OverTimePeopleGroupView.this, s.toString());
						}
		            }
		        }, 5000);
				
				
			}
		});
		
		
		if(isdel){
			findViewById(R.id.add_oa_view_four).setVisibility(View.VISIBLE);
			findViewById(R.id.add_oa_delete_layout).setVisibility(View.VISIBLE);
		}else{
			findViewById(R.id.add_oa_view_four).setVisibility(View.GONE);
			findViewById(R.id.add_oa_delete_layout).setVisibility(View.GONE);
		}
		
		/**
		 * 设置头部样式
		 * @param type : 0:多人加班单填写	1:多人加班单查看与审批		2:领导意见
		 */
		switch (type) {
			case 0:
				findViewById(R.id.add_oa_view_three).setVisibility(View.GONE);
				findViewById(R.id.add_oa_checkout_layout).setVisibility(View.GONE);
				break;
			case 1:
				findViewById(R.id.add_oa_view_four).setVisibility(View.GONE);
				findViewById(R.id.add_oa_delete_layout).setVisibility(View.GONE);
				break;
			case 2:
				findViewById(R.id.add_oa_view_two).setVisibility(View.GONE);
				findViewById(R.id.add_oa_user_layout).setVisibility(View.GONE);
				findViewById(R.id.add_oa_view_four).setVisibility(View.GONE);
				findViewById(R.id.add_oa_delete_layout).setVisibility(View.GONE);
				break;
			default:
				break;
		}
		
	}

	@Override
	public boolean checkData() {
		// TODO Auto-generated method stub
		return false;
	}
	
	public void setDeleteViewCallBack(DeleteViewCallBack deleteViewCallBack) {
		this.deleteViewCallBack = deleteViewCallBack;
	}

	/**
	 * 删除视图回调接口
	 * @author 王定波
	 */
	public interface DeleteViewCallBack {
		public void deleteView(OverTimePeopleGroupView view);
	}

	public void setSelectWardViewCallBack(SelectWardViewCallBack selectWardViewCallBack) {
		this.selectWardViewCallBack = selectWardViewCallBack;
	}
	
	/**
	 * 视图中选择 补贴类型	回调接口
	 * @author 王定波
	 */
	public interface SelectWardViewCallBack {
		public void selectWardView(OverTimePeopleGroupView view, String ward);
	}
	
	public void setEditTextUseTimeViewCallBack(EditTextUseTimeViewCallBack editTextUseTimeViewCallBack) {
		this.editTextUseTimeViewCallBack = editTextUseTimeViewCallBack;
	}
	
	/**
	 * 视图中填写 加班时间	回调接口
	 * @author 王定波
	 */
	public interface EditTextUseTimeViewCallBack {
		public void editTextUseTimeView(OverTimePeopleGroupView view, String text);
	}
	
	@Override
	public OverTimePeopleBean getData() {
		OverTimePeopleBean bean = new OverTimePeopleBean();
		return bean;
	}
	
	public void setData(OverTimePeopleBean data) {
		overtimePeopleId.setText(data.getUserNameId());
		overtimePeopleName.setText(data.getUserName() + "\n(" + data.getUserAccount() + ")");
		overtimeWard.setText(data.getUserWard());
		overtimeUsed.setText(data.getUserTimeHour());
		overtimeCheckout.setText(data.getUserTimeCheckout());
	}

}
