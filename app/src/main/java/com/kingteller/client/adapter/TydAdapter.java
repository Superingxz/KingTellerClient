package com.kingteller.client.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.kingteller.R;
import com.kingteller.client.bean.logisticmonitor.LogisticConsignBean;

public class TydAdapter extends BaseAdapter{

	private LayoutInflater inflater;
	private List<LogisticConsignBean> logisticConsignlist = new ArrayList<LogisticConsignBean>();
	private Context context;
	private LogisticConsignBean logisticConsign;

	public TydAdapter(Context context,List<LogisticConsignBean> logisticConsignlist){
		inflater = LayoutInflater.from(context);
		this.context = context;
		this.logisticConsignlist = logisticConsignlist;
	}

	public void setLists(List<LogisticConsignBean> logisticConsignlist) {
		this.logisticConsignlist = logisticConsignlist;
		notifyDataSetChanged();
	}

	public void addLists(List<LogisticConsignBean> logisticConsignlist) {
		this.logisticConsignlist.addAll(logisticConsignlist);
		notifyDataSetChanged();
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		if (logisticConsignlist == null) {
			return 0;
		}
		return logisticConsignlist.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return logisticConsignlist.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(final int postion, View v, ViewGroup arg2) {
		// TODO Auto-generated method stub
		
		logisticConsign = logisticConsignlist.get(postion);
		
		ViewHoler viewHoler = null;
		if (v == null) {
			viewHoler = new ViewHoler();
			v = inflater.inflate(R.layout.item_logistic_tyd, null);
			viewHoler.tydh = (TextView) v.findViewById(R.id.tydh);
			viewHoler.tydzt = (TextView) v.findViewById(R.id.tydzt);

			v.setTag(viewHoler);
		}else{
			viewHoler = (ViewHoler)v.getTag();
		}
		
		viewHoler.tydh.setText(logisticConsign.getTydh());
		viewHoler.tydzt.setText(logisticConsign.getTydztLike());
		return v;
	}
	
	private static class ViewHoler{
		public TextView tydh;
		public TextView tydzt;
	} 

}
