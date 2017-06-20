package com.kingteller.client.adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.CompoundButton.OnCheckedChangeListener;

import com.kingteller.R;
import com.kingteller.client.bean.workorder.AduitReportBean;
import com.kingteller.client.bean.workorder.MachineinfoSimpleBean;

@SuppressLint("UseSparseArrays")
public class MachineSearchAdapter extends BaseAdapter {

	private LayoutInflater inflater;
	private Context mContext;
	private MachineinfoSimpleBean machineInfo;
	private HashMap<Integer, Boolean> isCheckedMap = new HashMap<Integer, Boolean>();
	private List<MachineinfoSimpleBean> machineInfolist = new ArrayList<MachineinfoSimpleBean>();
	public List<MachineinfoSimpleBean> machiceInfoCheckedList = new ArrayList<MachineinfoSimpleBean>();

	public MachineSearchAdapter(Context context, List<MachineinfoSimpleBean> machineInfolist) {
		inflater = LayoutInflater.from(context);
		this.mContext = context;
		this.machineInfolist = machineInfolist;
		configCheckMap(false);
	}

	public void setLists(List<MachineinfoSimpleBean> machineInfolist) {
		this.machineInfolist = machineInfolist;
		isCheckedMap.clear();
		configCheckMap(false);
		notifyDataSetChanged();
	}
	
	public void addLists(List<MachineinfoSimpleBean> machineInfolist) {
		this.machineInfolist.addAll(machineInfolist);
		configAddCheckMap(false);
		notifyDataSetChanged();
	}

	/**
	 * 首先,默认情况下,所有项目都是没有选中的.这里进行初始化
	 */
	public void configCheckMap(boolean bool) {
		for (int i = 0; i < machineInfolist.size(); i++) {
			isCheckedMap.put(i, bool);
		}
	}
	
	/**
	 * 新增
	 */
	public void configAddCheckMap(boolean bool) {
		for (int i = isCheckedMap.size() - 1; i < machineInfolist.size(); i++) {
			isCheckedMap.put(i, bool);
		}
	}
	
	@Override
	public int getCount() {
		if (machineInfolist == null) {
			return 0;
		}
		return machineInfolist.size();
	}

	@Override
	public Object getItem(int arg0) {
		return machineInfolist.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	@Override
	public View getView(final int postion, View v, ViewGroup parent) {
		machineInfo = machineInfolist.get(postion);
	
		ViewHoler viewHoler = null;
		if (v == null) {
			viewHoler = new ViewHoler();
			v = inflater.inflate(R.layout.item_machine_search, null);
			viewHoler.atmh = (TextView) v.findViewById(R.id.atmh);
			viewHoler.jqbh = (TextView) v.findViewById(R.id.jqbh);
			viewHoler.ssyh = (TextView) v.findViewById(R.id.ssyh);
			viewHoler.wdsbjc = (TextView) v.findViewById(R.id.wdsbjc);
			viewHoler.checkBox = (CheckBox) v.findViewById(R.id.checkBox);

			v.setTag(viewHoler);
		}else{
			
			viewHoler = (ViewHoler)v.getTag();
		}
		
		viewHoler.checkBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean isChecked) {
				isCheckedMap.put(postion, isChecked); //将选择项加载到map里面寄存
			}
		});
		
		if (isCheckedMap.get(postion) == null) {
			isCheckedMap.put(postion, false);
		}
		viewHoler.checkBox.setChecked(isCheckedMap.get(postion));
		
		v.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if(((CheckBox) arg0.findViewById(R.id.checkBox)).isChecked()){
					((CheckBox) arg0.findViewById(R.id.checkBox)).setChecked(false);
					isCheckedMap.put(postion, false);
				}else{
					((CheckBox) arg0.findViewById(R.id.checkBox)).setChecked(true);
					isCheckedMap.put(postion, true);
				}
			}
		});
			
		viewHoler.atmh.setText(machineInfo.getAtmh());
		viewHoler.jqbh.setText(machineInfo.getJqbh());
		viewHoler.ssyh.setText(machineInfo.getSsyh());
		viewHoler.wdsbjc.setText(machineInfo.getWdsbjc());
		
		return v;
	}
	
	private  class ViewHoler{
		public TextView atmh;
		public TextView jqbh;
		public TextView ssyh;
		public TextView wdsbjc;
		public CheckBox checkBox;
	}

	public List<MachineinfoSimpleBean> getMachineInfoCheckedList(){
		machiceInfoCheckedList = new ArrayList<MachineinfoSimpleBean>();
		// 进行遍历
		for (int i = 0; i < machineInfolist.size(); i++) {
			if (isCheckedMap.get(i) != null && isCheckedMap.get(i)) {
				machiceInfoCheckedList.add(machineInfolist.get(i));
			}
		}
		return machiceInfoCheckedList ;
	}
}
