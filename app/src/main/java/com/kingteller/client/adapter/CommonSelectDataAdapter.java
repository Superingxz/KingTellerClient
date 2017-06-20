package com.kingteller.client.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.kingteller.R;
import com.kingteller.client.bean.common.CommonSelectData;
import com.kingteller.client.utils.KingTellerJudgeUtils;

/**
 * 通用选择适配器
 */
public class CommonSelectDataAdapter extends BaseAdapter {
	private LayoutInflater inflater;
	private List<CommonSelectData> lists = new ArrayList<CommonSelectData>();
	private Context context;
	private String optType ="";

	public CommonSelectDataAdapter(Context context, List<CommonSelectData> lists) {
		inflater = LayoutInflater.from(context);
		this.lists = lists;
		this.context = context;
	} 
	
	public CommonSelectDataAdapter(Context context, List<CommonSelectData> lists,String optType){
		inflater = LayoutInflater.from(context);
		this.lists = lists;
		this.context = context;
		this.optType = optType;
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
			if(!KingTellerJudgeUtils.isEmpty(optType) && optType.equals("wddz")){
				convertView = inflater.inflate(R.layout.item_common_select_wddz,
						null);
			}else{
				convertView = inflater.inflate(R.layout.item_common_select_data,
						null);
			}
			viewHoler.item_name = (TextView) convertView
					.findViewById(R.id.item_name);

			convertView.setTag(viewHoler);
		} else {
			viewHoler = (ViewHoler) convertView.getTag();
		}

		viewHoler.item_name.setText(data.getText());

		return convertView;
	}

	private static class ViewHoler {

		public TextView item_name;

	}

}
