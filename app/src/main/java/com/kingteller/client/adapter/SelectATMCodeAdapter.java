package com.kingteller.client.adapter;

import java.util.ArrayList;
import java.util.List;

import com.kingteller.R;
import com.kingteller.client.bean.map.ATMCodeBean;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class SelectATMCodeAdapter extends BaseAdapter {
	private LayoutInflater inflater;
	private List<ATMCodeBean> lists = new ArrayList<ATMCodeBean>();

	public SelectATMCodeAdapter(Context context, List<ATMCodeBean> lists) {
		inflater = LayoutInflater.from(context);
		this.lists = lists;
	}

	public void setLists(List<ATMCodeBean> lists) {
		this.lists = lists;
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

		ATMCodeBean data = (ATMCodeBean) lists.get(position);
		ViewHoler viewHoler = null;
		if (convertView == null) {
			viewHoler = new ViewHoler();
			convertView = inflater.inflate(R.layout.item_atmcode, null);
			viewHoler.name = (TextView) convertView.findViewById(R.id.item_name);
			convertView.setTag(viewHoler);
		} else {
			viewHoler = (ViewHoler) convertView.getTag();
		}

		viewHoler.name.setText(data.getJqbh());

		return convertView;
	}

	private static class ViewHoler {
		public TextView name;
	}

}
