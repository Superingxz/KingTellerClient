package com.kingteller.client.adapter;

import java.util.ArrayList;
import java.util.List;

import com.kingteller.R;
import com.kingteller.client.bean.onlinelearning.WorkListBean;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class WorkListAdadpater extends BaseAdapter{
	
	private LayoutInflater inflater;
	private List<WorkListBean> lists = new ArrayList<WorkListBean>();
	private Context context;
	private WorkListBean wlb ;

	public WorkListAdadpater(Context context, List<WorkListBean> lists) {
		inflater = LayoutInflater.from(context);
		this.lists = lists;
		this.context = context;
	}
	
	public void setLists(List<WorkListBean> lists) {
		this.lists = lists;
		notifyDataSetChanged();
	}

	public void addLists(List<WorkListBean> lists) {
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
	public View getView(int position, View convertView, ViewGroup parent) {
		
		wlb = (WorkListBean) lists.get(position);
		ViewHoler viewHoler = null;
		if (convertView == null) {
			
			viewHoler = new ViewHoler();
			convertView = inflater.inflate(R.layout.item_work_list, null);
			viewHoler.wfTitle = (TextView) convertView.findViewById(R.id.wfTitle);
			viewHoler.wfSwfLink = (TextView) convertView.findViewById(R.id.wfSwfLink);
			viewHoler.wfId = (TextView) convertView.findViewById(R.id.wfId);
			convertView.setTag(viewHoler);
			
		} else {
			viewHoler = (ViewHoler) convertView.getTag();
		}
		
		viewHoler.wfTitle.setText(wlb.getWfTitle());
		viewHoler.wfSwfLink.setText(wlb.getWfSwfLink());
		viewHoler.wfId.setText(wlb.getWfId());
		return convertView;
	}
	
	private static class ViewHoler {

		public TextView wfTitle;
		public TextView wfSwfLink;
		public TextView wfId;

	}
}
