package com.kingteller.client.adapter;

import java.util.ArrayList;
import java.util.List;

import com.kingteller.R;
import com.kingteller.client.bean.common.CommonSelectData;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * 机器扫描选择适配器
 */
public class QRDotMachineSelectListAdapter extends BaseAdapter {
	private LayoutInflater inflater;
	private List<CommonSelectData> lists = new ArrayList<CommonSelectData>();
	private Context mContext;

	public QRDotMachineSelectListAdapter(Context context, List<CommonSelectData> lists) {
		inflater = LayoutInflater.from(context);
		this.lists = lists;
		this.mContext = context;

	}

	public void setLists(List<CommonSelectData> lists) {
		this.lists = lists;
		notifyDataSetChanged();
	}

	public void addLists(List<CommonSelectData> lists) {
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
		
		CommonSelectData data = (CommonSelectData) lists.get(position);
		ViewHoler viewHoler = null;
		if (convertView == null) {
			viewHoler = new ViewHoler();
			convertView = inflater.inflate(R.layout.item_common_select_sblr_data,null);
			viewHoler.item_name = (TextView) convertView.findViewById(R.id.item_sblr_name);
			viewHoler.pathname_one = (TextView) convertView.findViewById(R.id.item_sblr_pathname_one);
			viewHoler.pathname_two = (TextView) convertView.findViewById(R.id.item_sblr_pathname_two);

			convertView.setTag(viewHoler);
		} else {
			viewHoler = (ViewHoler) convertView.getTag();
		}
		
		viewHoler.item_name.setText(data.getText());
		viewHoler.pathname_one.setText(data.getValue());
		viewHoler.pathname_two.setText(data.getObj().toString());

		return convertView;
	}

	private static class ViewHoler {

		public TextView item_name;
		public TextView pathname_one;
		public TextView pathname_two;
	}

}
