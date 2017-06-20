package com.kingteller.client.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.kingteller.R;
import com.kingteller.client.bean.account.Notice;
import com.kingteller.client.bean.account.WaitDoBean;
import com.kingteller.client.config.KingTellerStaticConfig;
import com.kingteller.framework.KingTellerDb;

public class WaitDoAdapter extends BaseAdapter {
	private LayoutInflater inflater;
	private List<WaitDoBean> lists = new ArrayList<WaitDoBean>();
	private Context context;
	private KingTellerDb finalDb;

	public WaitDoAdapter(Context context, List<WaitDoBean> lists) {
		inflater = LayoutInflater.from(context);
		this.lists = lists;
		this.context = context;
		finalDb = KingTellerDb.create(context);
	}

	public void setLists(List<WaitDoBean> lists) {
		this.lists = lists;
		KingTellerStaticConfig.WAITDO_BOTTOM_CORNER = 0;
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
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub

		WaitDoBean data = (WaitDoBean) lists.get(position);
		ViewHoler viewHoler = null;
		if (convertView == null) {
			viewHoler = new ViewHoler();
			convertView = inflater.inflate(R.layout.item_waitdo, null);
			viewHoler.item_title = (TextView) convertView.findViewById(R.id.item_title);
			viewHoler.item_num = (TextView) convertView.findViewById(R.id.item_num);
			viewHoler.item_time = (TextView) convertView.findViewById(R.id.item_time);
			viewHoler.item_sender = (TextView) convertView.findViewById(R.id.item_sender);
			viewHoler.item_new = (ImageView) convertView.findViewById(R.id.item_new);

			convertView.setTag(viewHoler);
		} else {
			viewHoler = (ViewHoler) convertView.getTag();
		}

		viewHoler.item_num.setText(String.valueOf(position + 1) + ".");
		
//		if(data.getFlag().contains("notice")){
//			viewHoler.item_sender.setText(data.getTitle());
//			viewHoler.item_time.setVisibility(View.GONE);
//			viewHoler.item_title.setVisibility(View.GONE);
//		}else{
//			viewHoler.item_time.setVisibility(View.VISIBLE);
//			viewHoler.item_title.setVisibility(View.VISIBLE);
//			viewHoler.item_sender.setText(data.getSenderName());
//			viewHoler.item_time.setText(data.getSendTime());
//			viewHoler.item_title.setText(data.getTitle());
//		}
		
		if(data.getFlag().contains("notice")){
			viewHoler.item_sender.setText(data.getTitle());
			viewHoler.item_time.setVisibility(View.GONE);
			viewHoler.item_title.setVisibility(View.GONE);
		}else{
			viewHoler.item_sender.setText(data.getTitle());
			viewHoler.item_title.setText(data.getSendTime());
			viewHoler.item_time.setVisibility(View.GONE);
		}
		

		
//		if (!KingTellerJudgeUtils.isEmpty(data.getSendTime())) {
//			viewHoler.item_time.setText(data.getSendTime());
//			viewHoler.item_time.setVisibility(View.VISIBLE);
//		} else{
//			viewHoler.item_time.setVisibility(View.GONE);
//		}
//		
//		if (!KingTellerJudgeUtils.isEmpty(data.getSenderName()) && !data.getSenderName().equals("null")) {
//			viewHoler.item_sender.setText(data.getSenderName());
//			viewHoler.item_sender.setVisibility(View.VISIBLE);
//		} else{
//			viewHoler.item_sender.setVisibility(View.GONE);
//		}
//
//		if (viewHoler.item_sender.getVisibility() == View.GONE && viewHoler.item_time.getVisibility() == View.GONE) {
//			viewHoler.item_title.setTextColor(context.getResources().getColor(R.color.common_right_edittext_textcolor));
//		} else{
//			viewHoler.item_title.setTextColor(context.getResources().getColor(R.color.common_left_edittext_textcolor));
//		}
		
		Notice datatmp = finalDb.findById(data.getOnlyId(), Notice.class);
		if (datatmp == null) {
			viewHoler.item_new.setVisibility(View.VISIBLE);
			KingTellerStaticConfig.WAITDO_BOTTOM_CORNER++;
		} else{
			viewHoler.item_new.setVisibility(View.GONE);
		}

		return convertView;
	}

	private static class ViewHoler {

		public ImageView item_new;
		public TextView item_sender;
		public TextView item_time;
		public TextView item_num;
		public TextView item_title;

	}

}