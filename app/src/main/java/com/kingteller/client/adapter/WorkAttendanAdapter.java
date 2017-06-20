package com.kingteller.client.adapter;

import java.util.ArrayList;
import java.util.List;

import com.kingteller.R;
import com.kingteller.client.bean.attendance.WorkAttendanceBean;
import com.kingteller.client.bean.offlineupload.OfflineDotMachineBean;

import android.content.Context;
import android.media.Image;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class WorkAttendanAdapter extends BaseAdapter{
	private LayoutInflater inflater;
	private List<WorkAttendanceBean> lists = new ArrayList<WorkAttendanceBean>();
	private Context mContext;

	public WorkAttendanAdapter(Context context, List<WorkAttendanceBean> mlists) {
		inflater = LayoutInflater.from(context);
		this.mContext = context;
		this.lists = mlists;
	}

	public void setLists(List<WorkAttendanceBean> lists) {
		this.lists = lists;
		notifyDataSetChanged();
	}
	
	public void addLists(List<WorkAttendanceBean> lists) {
		this.lists.addAll(lists);
		notifyDataSetChanged();
	}
	
	@Override
	public int getCount() {
		if (lists == null)
			return 0;
		return lists.size();
	}

	@Override
	public Object getItem(int position) {
		return lists.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int num, View convertView, ViewGroup parent) {

		WorkAttendanceBean data = (WorkAttendanceBean) lists.get(num);
		
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

			convertView.setTag(viewHoler);
		} else {
			viewHoler = (ViewHoler) convertView.getTag();
		}
		
		convertView.findViewById(R.id.oa_work_check).setVisibility(View.GONE);
		
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
		
		String position = data.getFlowPosition();
		if("ZC".equals(position)){//暂存
			viewHoler.oa_work_practice.setText("[暂存]"); 
		}else if ("SPZ".equals(position)){//审批中
			viewHoler.oa_work_practice.setText("[审批中]"); 
		}else if ("TH".equals(position)){//退回
			viewHoler.oa_work_practice.setText("[退回]"); 
		}else if ("YWC".equals(position)){//已完成
			viewHoler.oa_work_practice.setText("[已完成]"); 
		}
		
		viewHoler.oa_time_people.setText(data.getBodyCuserName()); 	//人物描述
		viewHoler.oa_time_number.setText(data.getBillNo());			//单号
		viewHoler.oa_time_interval.setText(data.getDateContent()); 	//时间描述
		viewHoler.oa_time_masg.setText(Html.fromHtml("<font color=#32a4da>"+ data.getAllContent() + "</font>")); 		//综合描述
		viewHoler.oa_work_reason.setText(data.getOthContent()); 	//原因描述
		
		return convertView;
	}

	
	private static class ViewHoler {
		public ImageView oa_type_image;
		public TextView oa_type_text;
		public TextView oa_time_people;
		public TextView oa_time_number;
		public TextView oa_time_interval;
		public TextView oa_time_masg;
		public TextView oa_work_practice;
		public TextView oa_work_reason;
		

	}

}
