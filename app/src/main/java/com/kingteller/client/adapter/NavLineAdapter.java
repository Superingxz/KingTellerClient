package com.kingteller.client.adapter;

import java.util.ArrayList;
import java.util.List;

import com.amap.api.services.route.BusPath;
import com.amap.api.services.route.BusStep;
import com.amap.api.services.route.DrivePath;
import com.amap.api.services.route.DriveStep;
import com.amap.api.services.route.WalkPath;
import com.amap.api.services.route.WalkStep;
import com.google.gson.Gson;
import com.kingteller.R;
import com.kingteller.client.utils.KingTellerJudgeUtils;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class NavLineAdapter<T> extends BaseAdapter {
	private LayoutInflater inflater;
	private List<T> lists = new ArrayList<T>();
	private int type;

	public NavLineAdapter(Context context, List<T> lists, int type) {
		inflater = LayoutInflater.from(context);
		this.lists = lists;
		this.type = type;
	}

	public void setLists(List<T> lists) {
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

		String desc = "";
		ViewHoler viewHoler = null;
		if (convertView == null) {
			viewHoler = new ViewHoler();
			convertView = inflater.inflate(R.layout.item_navline, null);
			viewHoler.address = (TextView) convertView
					.findViewById(R.id.address);
			viewHoler.name = (TextView) convertView.findViewById(R.id.name);
			convertView.setTag(viewHoler);
		} else {
			viewHoler = (ViewHoler) convertView.getTag();
		}

		switch (type) {
		case 1:
			Gson gson=new Gson();
			BusPath bdata = (BusPath) lists.get(position);
			StringBuffer sb = new StringBuffer();
			for (BusStep ds : bdata.getSteps()) {
				if(ds.getBusLine()!=null && !KingTellerJudgeUtils.isEmpty(ds.getBusLine().getBusLineName()))
				{
					sb.append(ds.getBusLine()+ "-");
				}
			}
			desc=sb.toString();
			break;
		case 2:
			DrivePath ddata = (DrivePath) lists.get(position);
			StringBuffer dbw = new StringBuffer();
			for (DriveStep ds : ddata.getSteps()) {
				dbw.append(ds.getRoad()+ "-");
			}
			desc=dbw.toString();
			break;
		case 3:
			WalkPath wdata = (WalkPath) lists.get(position);
			StringBuffer sbw = new StringBuffer();
			for (WalkStep ds : wdata.getSteps()) {
				sbw.append(ds.getRoad()+ "-");
			}
			desc=sbw.toString();
			break;

		default:
			break;
		}
		viewHoler.name.setText("第"+(position+1)+"线路");
		viewHoler.address.setText(desc+"终点");

		return convertView;
	}

	private static class ViewHoler {
		public TextView name;
		public TextView address;
	}

}
