package com.kingteller.client.adapter;

import java.util.ArrayList;
import java.util.List;

import com.kingteller.R;
import com.kingteller.client.bean.attendance.WorkAttendanceSearchLeaveFromBean;
import com.kingteller.client.bean.attendance.WorkAttendanceSearchPeopleBean;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

public class WorkAttendanceSearchLeaveFromAdapter extends BaseAdapter{
	private LayoutInflater inflater;
	private Context mContext;
	private WorkAttendanceSearchLeaveFromBean assignWorkerLeaveFromBean;
	private List<WorkAttendanceSearchLeaveFromBean> assignWorkerLeaveFromlist = new ArrayList<WorkAttendanceSearchLeaveFromBean>();
	
	public WorkAttendanceSearchLeaveFromAdapter(Context context, List<WorkAttendanceSearchLeaveFromBean> assignWorkerLeaveFromlist) {
		inflater = LayoutInflater.from(context);
		this.mContext = context;
		this.assignWorkerLeaveFromlist = assignWorkerLeaveFromlist;
	}

	public void setLists(List<WorkAttendanceSearchLeaveFromBean> assignWorkerLeaveFromlist) {
		this.assignWorkerLeaveFromlist = assignWorkerLeaveFromlist;
		notifyDataSetChanged();
	}

	public void addLists(List<WorkAttendanceSearchLeaveFromBean> assignWorkerLeaveFromlist) {
		this.assignWorkerLeaveFromlist.addAll(assignWorkerLeaveFromlist);
		notifyDataSetChanged();
	}
	
	
	@Override
	public int getCount() {
		if (assignWorkerLeaveFromlist == null) {
			return 0;
		}
		return assignWorkerLeaveFromlist.size();
	}

	@Override
	public Object getItem(int arg0) {
		return assignWorkerLeaveFromlist.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	@Override
	public View getView(final int position, View v, ViewGroup parent) {
		
		assignWorkerLeaveFromBean = assignWorkerLeaveFromlist.get(position);

		ViewHoler viewHoler = null;
		if (v == null) {
			viewHoler = new ViewHoler();
			v = inflater.inflate(R.layout.item_search_oa_people, null);
			viewHoler.jobNumber = (TextView) v.findViewById(R.id.oa_search_people_gh);
			viewHoler.userName = (TextView) v.findViewById(R.id.oa_search_people_xm);
			viewHoler.userTime = (TextView) v.findViewById(R.id.oa_search_people_time);
			viewHoler.checkBox = (CheckBox) v.findViewById(R.id.oa_search_people_cb);

			v.setTag(viewHoler);
		}else{
			viewHoler = (ViewHoler)v.getTag();
		}
		
		viewHoler.userTime.setVisibility(View.VISIBLE);
		viewHoler.checkBox.setVisibility(View.GONE);
		
		viewHoler.jobNumber.setText("请假单号：" + assignWorkerLeaveFromBean.getLeaveNo());
		viewHoler.userName.setText("请假时长：" + String.valueOf(Float.parseFloat(assignWorkerLeaveFromBean.getLeaveTimes())));
		viewHoler.userTime.setText("请假时间：" + assignWorkerLeaveFromBean.getLeaveStartTimeStr() + 
				" 至 " + assignWorkerLeaveFromBean.getLeaveEndTimeStr());

		return v;
	}
	
	private  class ViewHoler{
		public TextView jobNumber;
		public TextView userName;
		public TextView userTime;
		public CheckBox checkBox;
	}

}
