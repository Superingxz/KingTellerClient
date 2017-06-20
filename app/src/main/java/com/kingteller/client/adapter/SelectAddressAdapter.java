package com.kingteller.client.adapter;

import java.util.ArrayList;
import java.util.List;

import com.kingteller.R;
import com.kingteller.client.bean.map.AddressBean;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class SelectAddressAdapter extends BaseAdapter {
	private LayoutInflater inflater;
	private List<AddressBean> addresses = new ArrayList<AddressBean>();

	public SelectAddressAdapter(Context context, List<AddressBean> lists) {
		inflater = LayoutInflater.from(context);
		addresses = lists;
	}

	public void setAddresses(List<AddressBean> lists) {
		addresses = lists;
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		if (addresses == null)
			return 0;
		return addresses.size();
	}

	@Override
	public Object getItem(int location) {
		// TODO Auto-generated method stub
		return addresses.get(location);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub

		AddressBean data = (AddressBean) addresses.get(position);
		ViewHoler viewHoler = null;
		if (convertView == null) {
			viewHoler = new ViewHoler();
			convertView = inflater.inflate(R.layout.item_select_address, null);
			viewHoler.address = (TextView) convertView
					.findViewById(R.id.address);
			viewHoler.name = (TextView) convertView.findViewById(R.id.name);
			convertView.setTag(viewHoler);
		} else {
			viewHoler = (ViewHoler) convertView.getTag();
		}

		viewHoler.address.setText(data.getAddress());
		viewHoler.name.setText(data.getName());

		return convertView;
	}

	private static class ViewHoler {
		public TextView address;
		public TextView name;
	}

}
