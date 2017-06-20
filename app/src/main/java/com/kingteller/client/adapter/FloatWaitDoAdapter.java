package com.kingteller.client.adapter;

import java.util.ArrayList;
import java.util.List;

import com.kingteller.R;
import com.kingteller.client.bean.account.WaitDoBean;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class FloatWaitDoAdapter extends BaseAdapter {
	private LayoutInflater inflater;
	private List<WaitDoBean> lists = new ArrayList<WaitDoBean>();
	private Context context;

	public FloatWaitDoAdapter(Context context, List<WaitDoBean> lists) {
		inflater = LayoutInflater.from(context);
		this.lists = lists;
		this.context=context;
	}

	public void setLists(List<WaitDoBean> lists) {
		this.lists = lists;
		notifyDataSetChanged();
	}

	public void addLists(List<WaitDoBean> lists) {
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
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		WaitDoBean data = (WaitDoBean) lists.get(position);
		ViewHoler viewHoler = null;
		if (convertView == null) {
			viewHoler = new ViewHoler();
			convertView = inflater.inflate(R.layout.item_float_waitdo, null);
			viewHoler.item_title = (TextView) convertView.findViewById(R.id.item_title);
			viewHoler.item_num = (TextView) convertView.findViewById(R.id.item_num);
			viewHoler.item_time= (TextView) convertView.findViewById(R.id.item_time);
			viewHoler.item_sender= (TextView) convertView.findViewById(R.id.item_sender);

			convertView.setTag(viewHoler);
		} else {
			viewHoler = (ViewHoler) convertView.getTag();
		}

		viewHoler.item_num.setText(String.valueOf(position + 1) + ".");
		
		if(data.getFlag().contains("notice")){
			viewHoler.item_sender.setText(data.getTitle());
			viewHoler.item_time.setVisibility(View.GONE);
			viewHoler.item_title.setVisibility(View.GONE);
		}else{
			viewHoler.item_sender.setText(data.getTitle());
			viewHoler.item_title.setText(data.getSendTime());
			viewHoler.item_time.setVisibility(View.GONE);
		}
		
//		viewHoler.item_title.setText(data.getTitle());
//		if(data.getFlag().contains("notice")){
//			viewHoler.item_sender.setText(data.getTitle());
//			viewHoler.item_time.setVisibility(View.GONE);
//			viewHoler.item_title.setVisibility(View.GONE);
//		}else{
//			viewHoler.item_sender.setText(data.getSenderName());
//			viewHoler.item_time.setText(data.getSendTime());
//			viewHoler.item_title.setText(data.getTitle());
//			viewHoler.item_time.setVisibility(View.VISIBLE);
//			viewHoler.item_title.setVisibility(View.VISIBLE);
//		}
//		
//		if(!KingTellerJudgeUtils.isEmpty(data.getSendTime()))
//		{
//			viewHoler.item_time.setText(data.getSendTime());
//			viewHoler.item_time.setVisibility(View.VISIBLE);
//		}
//		else viewHoler.item_time.setVisibility(View.GONE);
//		
//		if(!KingTellerJudgeUtils.isEmpty(data.getSenderName()) && !data.getSenderName().equals("null")){
//			viewHoler.item_sender.setText(data.getSenderName());
//			viewHoler.item_sender.setVisibility(View.VISIBLE);
//		}
//		else viewHoler.item_sender.setVisibility(View.GONE);
//		
//		
//		if(viewHoler.item_sender.getVisibility()==View.GONE && viewHoler.item_time.getVisibility()==View.GONE){
//			viewHoler.item_title.setTextColor(context.getResources().getColor(R.color.white));
//		}
//		else viewHoler.item_title.setTextColor(context.getResources().getColor(R.color.text_999999));

		return convertView;
	}

	private static class ViewHoler {
		public TextView item_sender;
		public TextView item_time;
		public TextView item_num;
		public TextView item_title;
	}

}
