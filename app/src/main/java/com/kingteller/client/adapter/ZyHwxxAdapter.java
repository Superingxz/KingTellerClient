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
import com.kingteller.client.bean.logisticmonitor.LogisticObjectBean;
import com.kingteller.client.utils.KingTellerJudgeUtils;
import com.kingteller.client.view.KingTellerEditText;

public class ZyHwxxAdapter extends BaseAdapter{

	private LayoutInflater inflater;
	private List<LogisticObjectBean> hwList = new ArrayList<LogisticObjectBean>();
	private Context context;
	private LogisticObjectBean hw;

	public ZyHwxxAdapter(Context context,List<LogisticObjectBean> hwList){
		inflater = LayoutInflater.from(context);
		this.context = context;
		this.hwList = hwList;
	}

	public void setLists(List<LogisticObjectBean> hwList) {
		this.hwList = hwList;
		notifyDataSetChanged();
	}

	public void addLists(List<LogisticObjectBean> hwList) {
		this.hwList.addAll(hwList);
		notifyDataSetChanged();
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		if (hwList == null) {
			return 0;
		}
		return hwList.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return hwList.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(final int postion, View v, ViewGroup arg2) {
		// TODO Auto-generated method stub
		
		hw = hwList.get(postion);
		
		ViewHoler viewHoler = null;
		if (v == null) {
			viewHoler = new ViewHoler();
			v = inflater.inflate(R.layout.item_zy_hwxx, null);
			viewHoler.hwmc = (TextView) v.findViewById(R.id.hwmc);
			viewHoler.hwsl = (TextView) v.findViewById(R.id.hwsl);
			viewHoler.qsdlsh = (KingTellerEditText) v.findViewById(R.id.qsdlsh);

			viewHoler.qsdlsh.setFouces(false);
			
			v.setTag(viewHoler);
		}else{
			viewHoler = (ViewHoler)v.getTag();
		}
		
		viewHoler.hwmc.setText(hw.getName());
		viewHoler.hwsl.setText(hw.getSl());
		viewHoler.qsdlsh.setFieldTextAndValue(KingTellerJudgeUtils.isEmpty(hw.getQsdlsh())?"":hw.getQsdlsh());
		
		return v;
	}
	
	private static class ViewHoler{
		public TextView hwmc;
		public TextView hwsl;
		public KingTellerEditText qsdlsh;
	} 

}
