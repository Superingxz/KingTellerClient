package com.kingteller.client.adapter;

import java.util.ArrayList;
import java.util.List;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.kingteller.R;
import com.kingteller.client.activity.logisticmonitor.WriteOtherTaskActivity;
import com.kingteller.client.bean.logisticmonitor.OtherTaskBean;
import com.kingteller.framework.utils.StringUtil;

public class WriteOtherTaskAdapter extends BaseAdapter {
	private LayoutInflater inflater;
	private List<OtherTaskBean> otherTaskList = new ArrayList<OtherTaskBean>();
	private Context context;
	private OtherTaskBean otherTaskBean;

	public WriteOtherTaskAdapter(Context context, List<OtherTaskBean> otherTaskList) {
		inflater = LayoutInflater.from(context);
		this.otherTaskList = otherTaskList;
		this.context = context;
	}

	public void setLists(List<OtherTaskBean> otherTaskList) {
		this.otherTaskList = otherTaskList;
		notifyDataSetChanged();
	}

	public void addLists(List<OtherTaskBean> otherTaskList) {
		this.otherTaskList.addAll(otherTaskList);
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		if (otherTaskList == null) {
			return 0;
		}
		return otherTaskList.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return otherTaskList.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@SuppressLint("InflateParams") @Override
	public View getView(int postion, View v, ViewGroup parent) {
		otherTaskBean = otherTaskList.get(postion);
		final String rwdzt = otherTaskBean.getRwdzt();
		final String swdh = otherTaskBean.getSwdh();
		final String cl = otherTaskBean.getCl();
		final String tel = otherTaskBean.getTel();
		final String id = otherTaskBean.getId();
		final String shjg = otherTaskBean.getShjg();
		ViewHoler viewHoler = null;
		if (v == null) {
			viewHoler = new ViewHoler();
			v = inflater.inflate(R.layout.item_write_othertasklist, null);
			viewHoler.swdh = (TextView) v.findViewById(R.id.swdh);
			viewHoler.swname = (TextView) v.findViewById(R.id.swname);
			viewHoler.pdsj = (TextView) v.findViewById(R.id.pdsj);
			viewHoler.rwdzt = (TextView) v.findViewById(R.id.rwdzt);
			viewHoler.shjg = (TextView) v.findViewById(R.id.shjg);
			viewHoler.gcy1 = (TextView) v.findViewById(R.id.gcy1);
			viewHoler.gcy2 = (TextView ) v.findViewById(R.id.gcy2);
			viewHoler.cl = (TextView) v.findViewById(R.id.cl);
			v.setTag(viewHoler);
		} else {
			viewHoler = (ViewHoler) v.getTag();
		}

		viewHoler.swdh.setText(otherTaskBean.getSwdh());
		viewHoler.swname.setText(otherTaskBean.getSwname());
		viewHoler.pdsj.setText(otherTaskBean.getCjsjStr());
		viewHoler.rwdzt.setText(otherTaskBean.getStatusName());
		viewHoler.rwdzt.setTextColor(context.getResources().getColor(R.color.blue));
		viewHoler.gcy1.setText(otherTaskBean.getGcy1Name());
		viewHoler.gcy2.setText(otherTaskBean.getGcy2Name());
		viewHoler.cl.setText(otherTaskBean.getClName());
		if(StringUtil.equals(shjg,"退回")){
			viewHoler.shjg.setText("退回");
			viewHoler.shjg.setTextColor(context.getResources().getColor(R.color.red));
		}else if(StringUtil.equals(shjg,"未填写")){
			viewHoler.shjg.setText("未填写");
			viewHoler.shjg.setTextColor(context.getResources().getColor(R.color.blue));
		}
		v.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent();
				intent.putExtra("rwdzt", rwdzt);
				intent.putExtra("swdh", swdh);
				intent.putExtra("cl", cl);
				intent.putExtra("tel", tel);
				intent.putExtra("id", id);
				intent.setClass(context, WriteOtherTaskActivity.class);
				context.startActivity(intent);
			}
		});
		return v;
	}

	private static class ViewHoler {
		public TextView swdh;
		public TextView swname;
		public TextView pdsj;
		public TextView rwdzt;
		public TextView shjg;
		public TextView gcy1;
		public TextView gcy2;
		public TextView cl;
	}

}
