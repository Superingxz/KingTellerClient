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
import com.kingteller.client.bean.logisticmonitor.OtherTaskConsignBean;

public class OtherTaskConsignAdapter extends BaseAdapter {
	private LayoutInflater inflater;
	@SuppressWarnings("unused")
	private Context context;
	private List<OtherTaskConsignBean> list = new ArrayList<OtherTaskConsignBean>();
	private OtherTaskConsignBean consignBean;

	
	public OtherTaskConsignAdapter(Context context, List<OtherTaskConsignBean> list) {
		inflater = LayoutInflater.from(context);
		this.context = context;
		this.list = list;
	}

	public void setLists(List<OtherTaskConsignBean> list) {
		this.list = list;
		notifyDataSetChanged();
	}

	public void addLists(List<OtherTaskConsignBean> list) {
		this.list = list;
		notifyDataSetChanged();
	}
	@Override
	public int getCount() {
		if(list == null){
			return 0;
		}
		return list.size();
	}

	@Override
	public Object getItem(int arg0) {
		return list.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(int position, View v, ViewGroup arg2) {
		consignBean = list.get(position);
		ViewHoler viewHoler = null;
		if(v == null){
			viewHoler = new ViewHoler();
			v = inflater.inflate(R.layout.item_taskconsign_list, null);
			viewHoler.tydh = (TextView) v.findViewById(R.id.tydh);
			viewHoler.swlb = (TextView) v.findViewById(R.id.swlb);
			viewHoler.sfd = (TextView) v.findViewById(R.id.sfd);
			viewHoler.mdd = (TextView) v.findViewById(R.id.mdd);
			viewHoler.yqddsj = (TextView) v.findViewById(R.id.yqddsj);
			viewHoler.swyq = (TextView) v.findViewById(R.id.swyq);
			v.setTag(viewHoler);
		}else {
			viewHoler = (ViewHoler) v.getTag();
		}
		viewHoler.tydh.setText(consignBean.getTydh());
		viewHoler.swlb.setText(consignBean.getSwlb()); 
		viewHoler.sfd.setText(consignBean.getSfd());
		viewHoler.mdd.setText(consignBean.getMdd());
		viewHoler.yqddsj.setText(consignBean.getYqddsjStr());
		viewHoler.swyq.setText(consignBean.getSwyq());
		
		return v;
		
	}
		
    private static class ViewHoler{
    	public TextView tydh;
		public TextView swlb;
		public TextView sfd;
		public TextView mdd;
		public TextView yqddsj;
		public TextView swyq;
    }

}
