package com.kingteller.client.adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import com.kingteller.R;
import com.kingteller.client.bean.common.CommonSelectData;
import com.kingteller.client.view.KTAlertDialog;

public class CommonSelectGtgjAdapter extends BaseAdapter {
	private LayoutInflater inflater;
	private List<CommonSelectData> lists = new ArrayList<CommonSelectData>();
	private Context context;
	public List<CommonSelectData> listCheckedData = new ArrayList<CommonSelectData>();
	private HashMap<Integer, Boolean> isCheckedMap = new HashMap<Integer, Boolean>();

	public CommonSelectGtgjAdapter(Context context, List<CommonSelectData> lists) {
		inflater = LayoutInflater.from(context);
		this.lists = lists;
		this.context = context;
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
		//isCheckedMap.clear();
		ViewHoler viewHoler = null;
		if (convertView == null) {
			viewHoler = new ViewHoler();
			convertView = inflater.inflate(R.layout.item_common_select_jtgj,null);
			viewHoler.item_name = (TextView) convertView.findViewById(R.id.item_name);
			viewHoler.checkBox = (CheckBox) convertView.findViewById(R.id.checkBox);

			convertView.setTag(viewHoler);
		} else {
			viewHoler = (ViewHoler) convertView.getTag();
		}
		viewHoler.item_name.setText(data.getText().trim());
		
		final int posit = position;
		viewHoler.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){

			@Override
			public void onCheckedChanged(CompoundButton pos, boolean isChecked) {
				// TODO Auto-generated method stub
				if (isChecked) {
					if (!listCheckedData.contains(lists.get(posit))) {
						listCheckedData.add(lists.get(posit));
					}
					isCheckedMap.put(posit, isChecked);
				} else {
					if (listCheckedData.contains(lists.get(posit))) {
						listCheckedData.remove(lists.get(posit));
					}
					isCheckedMap.remove(posit);
				}
			}
			
		});
		viewHoler.checkBox.setChecked(isCheckedMap.get(position) == null ? false :true);
	
		return convertView;
	}

	public List<CommonSelectData> getCheckedDataList(){
		return listCheckedData;
	}
	public void setCheckedDataList(List<CommonSelectData> commonSelectDataList){
		this.listCheckedData = commonSelectDataList;
	}
	
	private static class ViewHoler {

		public TextView item_name;
		public CheckBox checkBox;
	}

}
