package com.kingteller.client.adapter;

import java.util.ArrayList;
import java.util.List;

import com.kingteller.R;
import com.kingteller.client.bean.map.ATMCodeBean;
import com.kingteller.client.bean.map.StaffLocationBean;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class StaffLocationAdapter extends BaseAdapter {
	private LayoutInflater inflater;
	private List<StaffLocationBean> lists = new ArrayList<StaffLocationBean>();
	private Context context;

	public StaffLocationAdapter(Context context, List<StaffLocationBean> lists) {
		inflater = LayoutInflater.from(context);
		this.lists = lists;
		this.context=context;
	}

	public void setLists(List<StaffLocationBean> lists) {
		this.lists = lists;
		notifyDataSetChanged();
	}
	
	public void addLists(List<StaffLocationBean> lists) {
		this.lists.addAll(lists);
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		if (lists == null)
			return 0;
		return lists.size();
	}

	@Override
	public Object getItem(int location) {
		// TODO Auto-generated method stub
		return lists.get(location);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub

		StaffLocationBean data = (StaffLocationBean) lists.get(position);
		ViewHoler viewHoler = null;
		if (convertView == null) {
			viewHoler = new ViewHoler();
			convertView = inflater.inflate(R.layout.item_atmcode, null);
			viewHoler.name = (TextView) convertView.findViewById(R.id.item_name);
			convertView.setTag(viewHoler);
		} else {
			viewHoler = (ViewHoler) convertView.getTag();
		}

		viewHoler.name.setText(data.getUserName());
		convertView.setBackgroundColor(context.getResources().getColor(R.color.white));

		return convertView;
	}

	private static class ViewHoler {
		public TextView name;
	}

}
