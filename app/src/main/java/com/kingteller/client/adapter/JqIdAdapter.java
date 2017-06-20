package com.kingteller.client.adapter;

import java.util.ArrayList;
import java.util.List;

import com.kingteller.R;
import com.kingteller.client.bean.workorder.WorkorderMachineParamBean;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class JqIdAdapter extends BaseAdapter{

	private LayoutInflater inflater;
	private List<WorkorderMachineParamBean> womplist = new ArrayList<WorkorderMachineParamBean>();
	private Context context;
	private WorkorderMachineParamBean womp;
	
	public JqIdAdapter(Context context,List<WorkorderMachineParamBean> womplist){
		inflater = LayoutInflater.from(context);
		this.context = context;
		this.womplist = womplist;
	}

	public void setLists(List<WorkorderMachineParamBean> womplist) {
		this.womplist = womplist;
		notifyDataSetChanged();
	}

	public void addLists(List<WorkorderMachineParamBean> womplist) {
		this.womplist.addAll(womplist);
		notifyDataSetChanged();
	}

	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		if (womplist == null) {
			return 0;
		}
		return womplist.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return womplist.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(final int position, View v, ViewGroup arg2) {
		// TODO Auto-generated method stub
		womp = womplist.get(position);
				
		ViewHoler viewHoler = null;
		if (v == null) {
			viewHoler = new ViewHoler();
			v = inflater.inflate(R.layout.item_add_jqid, null);
			viewHoler.type = (TextView) v.findViewById(R.id.type);
			viewHoler.typeName = (TextView) v.findViewById(R.id.typeName);
			viewHoler.content = (TextView) v.findViewById(R.id.content);
			v.setTag(viewHoler);
		}else{
			viewHoler = (ViewHoler)v.getTag();
		}
		
		viewHoler.type.setText(String.valueOf(position+1)+".");
		viewHoler.typeName.setText("["+womp.getTypename()+"]");
		viewHoler.content.setText(womp.getContent());
		
		return v;
	}

	private static class ViewHoler{
		public TextView type;
		public TextView typeName;
		public TextView content;
	} 
	
}
