package com.kingteller.client.adapter;

import java.util.ArrayList;
import java.util.List;

import com.kingteller.R;
import com.kingteller.client.bean.common.CommonSelectGZBJ;
import com.kingteller.client.bean.workorder.JqzdDataBean;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * 机器字段选择适配器
 */
public class CommonSelectJQZDDataAdapter extends BaseAdapter {

	private LayoutInflater inflater;
	private List<JqzdDataBean> lists = new ArrayList<JqzdDataBean>();
	private Context context;
	private JqzdDataBean data;
	
	public CommonSelectJQZDDataAdapter(Context context, List<JqzdDataBean> lists) {
		inflater = LayoutInflater.from(context);
		this.lists = lists;
		this.context = context;

	}
	
	public void setLists(List<JqzdDataBean> lists) {
		this.lists = lists;
		notifyDataSetChanged();
	}

	public void addLists(List<JqzdDataBean> lists) {
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
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return lists.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		data = (JqzdDataBean) lists.get(position);
		ViewHoler viewHoler = null;
		if (convertView == null) {
			viewHoler = new ViewHoler();
			convertView = inflater.inflate(R.layout.item_common_select_gzbj_data,null);
			viewHoler.item_name = (TextView) convertView.findViewById(R.id.item_name);
			viewHoler.item_pathname = (TextView) convertView.findViewById(R.id.item_pathname);

			convertView.setTag(viewHoler);
		} else {
			viewHoler = (ViewHoler) convertView.getTag();
		}

		viewHoler.item_name.setText(data.getAttrName());
		viewHoler.item_pathname.setText(data.getWlPathName());

		return convertView;
	}
	
	private static class ViewHoler {

		public TextView item_name;
		public TextView item_pathname;

	}

}
