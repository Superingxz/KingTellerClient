package com.kingteller.client.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.kingteller.R;
import com.kingteller.client.activity.logisticmonitor.LogisticTaskActivity;
import com.kingteller.client.activity.logisticmonitor.WriteLogisticTaskActivity;
import com.kingteller.client.bean.logisticmonitor.LogisticOrderBean;
import com.kingteller.client.view.ListViewForScrollView;

public class LogisticOrderAdapter extends BaseAdapter {

	private LayoutInflater inflater;
	private List<LogisticOrderBean> logisticOrderlist = new ArrayList<LogisticOrderBean>();
	private Context context;
	private LogisticOrderBean logisticOrder;
	private TydAdapter adapter;
	private String optType;

	public LogisticOrderAdapter(Context context,List<LogisticOrderBean> logisticOrderlist,String optType) {
		inflater = LayoutInflater.from(context);
		this.context = context;
		this.logisticOrderlist = logisticOrderlist;
		this.optType = optType;
	}

	public void setLists(List<LogisticOrderBean> logisticOrderlist) {
		this.logisticOrderlist = logisticOrderlist;
		notifyDataSetChanged();
	}

	public void addLists(List<LogisticOrderBean> logisticOrderlist) {
		this.logisticOrderlist.addAll(logisticOrderlist);
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		if (logisticOrderlist == null) {
			return 0;
		}
		return logisticOrderlist.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return logisticOrderlist.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(final int postion, View v, ViewGroup parent) {
		// TODO Auto-generated method stub
		logisticOrder = logisticOrderlist.get(postion);

		ViewHoler viewHoler = null;
		if (v == null) {
			viewHoler = new ViewHoler();
			v = inflater.inflate(R.layout.item_tasklist, null);
			viewHoler.rwdh = (TextView) v.findViewById(R.id.rwdh);
			viewHoler.tj = (TextView) v.findViewById(R.id.tj);
			viewHoler.sl = (TextView) v.findViewById(R.id.sl);
			viewHoler.pdsj = (TextView) v.findViewById(R.id.pdsj);
			viewHoler.rwlz = (TextView) v.findViewById(R.id.rwlz);
			viewHoler.btn_rwd_deal = (Button) v.findViewById(R.id.btn_rwd_deal);
			viewHoler.listView = (ListViewForScrollView) v.findViewById(R.id.listViewFor);
			v.setTag(viewHoler);
		} else {
			viewHoler = (ViewHoler) v.getTag();
		}

		viewHoler.rwdh.setText(logisticOrder.getTaskbean().getRwdh());
		viewHoler.tj.setText(logisticOrder.getBasicbean().getTylxLike());
		viewHoler.rwlz.setText(logisticOrder.getTaskbean().getExpand1Like());
		viewHoler.sl.setText(logisticOrder.getHwsl());
		viewHoler.pdsj.setText(logisticOrder.getBasicbean().getCjsjStr());
		adapter = new TydAdapter(context, logisticOrder.getConsignList());
		viewHoler.listView.setAdapter(adapter);
		final String rwdzt = logisticOrder.getTaskbean().getRwdzt();
		final String rwdId = logisticOrder.getTaskbean().getId();
		if(optType.equals("fill")){
			viewHoler.btn_rwd_deal.setText("查看报告");
			
		}else{
			viewHoler.btn_rwd_deal.setText("查看详情");

		}

		viewHoler.btn_rwd_deal.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				if(optType.equals("query")){
					intent.putExtra("rwdId", rwdId);
					intent.putExtra("rwdzt", rwdzt);
					intent.setClass(context, LogisticTaskActivity.class);
					context.startActivity(intent);
				}else if(optType.equals("fill")){
					intent.putExtra("rwdId", rwdId);
					intent.putExtra("rwdzt", rwdzt);
					intent.setClass(context, WriteLogisticTaskActivity.class);
					context.startActivity(intent);
				}
				
			}
		});
		return v;
	}

	private static class ViewHoler {
		public TextView rwdh;
		public TextView tj;
		public TextView sl;
		public TextView pdsj;
		public TextView rwlz;
		public Button btn_rwd_deal;
		public ListViewForScrollView listView;
	}
}
