package com.kingteller.client.adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.kingteller.R;
import com.kingteller.client.activity.attendance.checkout.WorkAbsentFromCheckoutActivity;
import com.kingteller.client.activity.attendance.checkout.WorkBoardFromCheckoutActivity;
import com.kingteller.client.activity.attendance.checkout.WorkBusinessTripFromCheckoutActivity;
import com.kingteller.client.activity.attendance.checkout.WorkLeaveFromCheckoutActivity;
import com.kingteller.client.activity.attendance.checkout.WorkOvertimeFormCheckoutActivity;
import com.kingteller.client.activity.attendance.checkout.WorkSickLeaveFromCheckoutActivity;
import com.kingteller.client.activity.attendance.search.WorkAttendanceSearchPersonnelActivity;
import com.kingteller.client.bean.attendance.WorkAttendanceBean;
import com.kingteller.client.view.dialog.NormalDialog;
import com.kingteller.client.view.dialog.listener.OnBtnClickL;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.CompoundButton.OnCheckedChangeListener;

@SuppressLint("UseSparseArrays")
public class WorkAttendanAduitAdapter extends BaseAdapter {
	
	private LayoutInflater inflater;
	private List<WorkAttendanceBean> workAttendancelist = new ArrayList<WorkAttendanceBean>();
	private HashMap<Integer, Boolean> isCheckedMap = new HashMap<Integer, Boolean>();
	private Context mContext;
	private String tabName;
	
	public WorkAttendanAduitAdapter(Context context, List<WorkAttendanceBean> workAttendancelist, String tabName) {
		inflater = LayoutInflater.from(context);
		this.workAttendancelist = workAttendancelist;
		this.tabName = tabName;
		this.mContext = context;
		configCheckMap(false);
	}

	public void setLists(List<WorkAttendanceBean> workAttendancelist) {
		this.workAttendancelist = workAttendancelist;
		configCheckMap(false);
		notifyDataSetChanged();
	}

	public void addLists(List<WorkAttendanceBean> workAttendancelist) {
		this.workAttendancelist.addAll(workAttendancelist);
		configAddCheckMap(false);
		notifyDataSetChanged();
	}
	
	/**
	 * 首先,默认情况下,所有项目都是没有选中的.这里进行初始化
	 */
	public void configCheckMap(boolean bool) {
		for (int i = 0; i < workAttendancelist.size(); i++) {
			isCheckedMap.put(i, bool);
		}
	}
	
	/**
	 * 新增
	 */
	public void configAddCheckMap(boolean bool) {
		for (int i = isCheckedMap.size() - 1; i < workAttendancelist.size(); i++) {
			isCheckedMap.put(i, bool);
		}
	}
	
	@Override
	public int getCount() {
		if (workAttendancelist == null) {
			return 0;
		}
		return workAttendancelist.size();
	}

	@Override
	public Object getItem(int arg0) {
		return workAttendancelist.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}   
	
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		
		WorkAttendanceBean data = workAttendancelist.get(position);
		
		ViewHoler viewHoler = null;
		if (convertView == null) {
			viewHoler = new ViewHoler();
			convertView = inflater.inflate(R.layout.item_workattendan, null);
			viewHoler.oa_type_image = (ImageView) convertView.findViewById(R.id.oa_type_image);
			viewHoler.oa_type_text = (TextView) convertView.findViewById(R.id.oa_type_text);
			viewHoler.oa_time_people = (TextView) convertView.findViewById(R.id.oa_time_people);
			viewHoler.oa_time_number = (TextView) convertView.findViewById(R.id.oa_time_number);
			viewHoler.oa_time_interval = (TextView) convertView.findViewById(R.id.oa_time_interval);
			viewHoler.oa_time_masg = (TextView) convertView.findViewById(R.id.oa_time_masg);
			viewHoler.oa_work_practice = (TextView) convertView.findViewById(R.id.oa_work_practice);
			viewHoler.oa_work_reason = (TextView) convertView.findViewById(R.id.oa_work_reason);
			viewHoler.oa_work_check = (CheckBox) convertView.findViewById(R.id.oa_work_check);
			
			convertView.setTag(viewHoler);
		} else {
			viewHoler = (ViewHoler) convertView.getTag();
		}
		
		if("tab4".equals(tabName)){ 
			convertView.findViewById(R.id.oa_work_check).setVisibility(View.VISIBLE);
			viewHoler.oa_work_check.setOnCheckedChangeListener(new OnCheckedChangeListener(){
				@Override
				public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
					isCheckedMap.put(position, isChecked); //将选择项加载到map里面寄存
				}
			});
			
			if (isCheckedMap.get(position) == null) {
				isCheckedMap.put(position, false);
			}
			viewHoler.oa_work_check.setChecked(isCheckedMap.get(position));
			
			convertView.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View arg0) {
					if(((CheckBox) arg0.findViewById(R.id.oa_work_check)).isChecked()){
						((CheckBox) arg0.findViewById(R.id.oa_work_check)).setChecked(false);
						isCheckedMap.put(position, false);
						
					}else{
						((CheckBox) arg0.findViewById(R.id.oa_work_check)).setChecked(true);
						isCheckedMap.put(position, true);
					}
				}
			});
			
		}else{
			convertView.findViewById(R.id.oa_work_check).setVisibility(View.GONE);
		}
		
		
		String type = data.getBillType();
		if("kqmgrOvertimeFlow".equals(type)){//加班
			viewHoler.oa_type_image.setBackgroundResource(R.drawable.ic_oa_overtime);
			viewHoler.oa_type_text.setText("加班"); 
		}else if("kqmgrLeaveFlow".equals(type)){//请假
			viewHoler.oa_type_image.setBackgroundResource(R.drawable.ic_oa_leave);
			viewHoler.oa_type_text.setText("请假"); 
		}else if("kqmgrSickleaveFlow".equals(type)){//销假
			viewHoler.oa_type_image.setBackgroundResource(R.drawable.ic_oa_sickleave);
			viewHoler.oa_type_text.setText("销假"); 
		}else if("kqmgrTravelFlow".equals(type)){//出差
			viewHoler.oa_type_image.setBackgroundResource(R.drawable.ic_oa_business);
			viewHoler.oa_type_text.setText("出差"); 
		}else if("kqmgrFILLFlow".equals(type)){//补登
			viewHoler.oa_type_image.setBackgroundResource(R.drawable.ic_oa_board);
			viewHoler.oa_type_text.setText("补登"); 
		}else if("kqmgrAbsenteeismFlow".equals(type)){//旷工
			viewHoler.oa_type_image.setBackgroundResource(R.drawable.ic_oa_absenteeism);
			viewHoler.oa_type_text.setText("旷工"); 
		}
		
		viewHoler.oa_work_practice.setText(""); 
//		String zt = data.getFlowPosition();
//		if("ZC".equals(zt)){//暂存
//			viewHoler.oa_work_practice.setText("[暂存]"); 
//		}else if ("SPZ".equals(zt)){//审批中
//			viewHoler.oa_work_practice.setText("[审批中]"); 
//		}else if ("TH".equals(zt)){//退回
//			viewHoler.oa_work_practice.setText("[退回]"); 
//		}else if ("YWC".equals(zt)){//已完成
//			viewHoler.oa_work_practice.setText("[已完成]"); 
//		}
		
		viewHoler.oa_time_people.setText(data.getBodyCuserName()); 	//人物描述
		viewHoler.oa_time_number.setText(data.getBillNo());			//单号
		viewHoler.oa_time_interval.setText(data.getDateContent()); 	//时间描述
		viewHoler.oa_time_masg.setText(Html.fromHtml("<font color=#32a4da>"+ data.getAllContent() + "</font>")); 		//综合描述
		viewHoler.oa_work_reason.setText(data.getOthContent()); 	//原因描述
		
		return convertView;
	}
	
	
	public HashMap<Integer, Boolean> getCheckMap() {
		return this.isCheckedMap;
	}
	
	private static class ViewHoler{
		public ImageView oa_type_image;
		public TextView oa_type_text;
		public TextView oa_time_people;
		public TextView oa_time_number;
		public TextView oa_time_interval;
		public TextView oa_time_masg;
		public TextView oa_work_practice;
		public TextView oa_work_reason;
		public CheckBox oa_work_check;
	} 
}
