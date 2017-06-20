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
import com.kingteller.client.bean.common.KingTellerDateTime;
import com.kingteller.client.bean.logisticmonitor.LogisticObjectBean;
import com.kingteller.client.utils.CommonSelcetUtils;
import com.kingteller.client.utils.KingTellerJudgeUtils;
import com.kingteller.client.view.ConditionView;
import com.kingteller.client.view.KingTellerEditText;

public class NzyHwxxAdapter extends BaseAdapter{

	private LayoutInflater inflater;
	private List<LogisticObjectBean> hwList = new ArrayList<LogisticObjectBean>();
	private Context context;
	private LogisticObjectBean hw;
	private KingTellerDateTime kingTellerDateTime;

	public NzyHwxxAdapter(Context context,List<LogisticObjectBean> hwList){
		inflater = LayoutInflater.from(context);
		this.context = context;
		this.hwList = hwList;
		kingTellerDateTime = new KingTellerDateTime().initNow();
		
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
			v = inflater.inflate(R.layout.item_nzy_hwxx, null);
			viewHoler.hwmc = (TextView) v.findViewById(R.id.hwmc);
			viewHoler.hwsl = (TextView) v.findViewById(R.id.hwsl);
			viewHoler.sjps = (KingTellerEditText) v.findViewById(R.id.sjps);
			viewHoler.sqxz = (KingTellerEditText) v.findViewById(R.id.sqxz);
			viewHoler.jqbm = (KingTellerEditText) v.findViewById(R.id.jqbm);
			viewHoler.qsdlsh = (KingTellerEditText) v.findViewById(R.id.qsdlsh);
			viewHoler.sjpsrq = (TextView) v.findViewById(R.id.sjpsrq);
			
			viewHoler.sqxz.setLists(CommonSelcetUtils.getSelectList(CommonSelcetUtils.sqxzType));

			viewHoler.sjps.setFouces(false);
			viewHoler.sqxz.setFieldCheckoutEnabled(false);
			viewHoler.jqbm.setFouces(false);
			viewHoler.qsdlsh.setFouces(false);
			
			v.setTag(viewHoler);
		}else{
			viewHoler = (ViewHoler)v.getTag();
		}
		
		viewHoler.hwmc.setText(hw.getName());
		viewHoler.hwsl.setText(hw.getSl());
		
		viewHoler.jqbm.setFieldTextAndValue(KingTellerJudgeUtils.isEmpty(hw.getJqbms())?"":hw.getJqbms());
		//viewHoler.sqxz.setFieldTextAndValue(KingTellerJudgeUtils.isEmpty(hw.getSqxxz())?"":hw.getSqxxz());
		viewHoler.sjps.setFieldTextAndValue(KingTellerJudgeUtils.isEmpty(hw.getSjpsdz())?"":hw.getSjpsdz());
		viewHoler.qsdlsh.setFieldTextAndValue(KingTellerJudgeUtils.isEmpty(hw.getQsdlsh())?"":hw.getQsdlsh());
		viewHoler.sqxz.setFieldTextAndValueFromValue(KingTellerJudgeUtils.isEmpty(hw.getSqxxz())?"":hw.getSqxxz());
		viewHoler.sjpsrq.setText(KingTellerJudgeUtils.isEmpty(hw.getSjpsrq())?"":hw.getSjpsrq());
		
		return v;
	}
	
	private static class ViewHoler{
		public TextView hwmc;
		public TextView hwsl;
		public KingTellerEditText sjps;
		public KingTellerEditText sqxz;
		public KingTellerEditText jqbm;
		public KingTellerEditText qsdlsh;
		public TextView sjpsrq;
	} 

}

