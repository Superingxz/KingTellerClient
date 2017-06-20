package com.kingteller.client.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.kingteller.R;
import com.kingteller.client.bean.logisticmonitor.LogisticConsignMobileBean;
import com.kingteller.client.utils.KingTellerConfigUtils;
import com.kingteller.client.view.ListViewForScrollView;

public class NzyTxBgAdapter extends BaseAdapter {

	private LayoutInflater inflater;
	private List<LogisticConsignMobileBean> tydlist = new ArrayList<LogisticConsignMobileBean>();
	private Context context;
	private LogisticConsignMobileBean tyd = new LogisticConsignMobileBean();
	private NzyHwxxAdapter adapter;

	public NzyTxBgAdapter(Context context,List<LogisticConsignMobileBean> tydlist) {
		inflater = LayoutInflater.from(context);
		this.context = context;
		this.tydlist = tydlist;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		if (tydlist == null) {
			return 0;
		}
		return tydlist.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return tydlist.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(final int postion, View v, ViewGroup arg2) {
		// TODO Auto-generated method stub

		tyd = tydlist.get(postion);

		ViewHoler viewHoler = null;
		if (v == null) {
			viewHoler = new ViewHoler();
			v = inflater.inflate(R.layout.item_add_nzytxbg, null);

			viewHoler.psdz = (TextView) v.findViewById(R.id.psdz);
			viewHoler.tydh = (TextView) v.findViewById(R.id.tydh);
			viewHoler.lxr = (TextView) v.findViewById(R.id.lxr);
			viewHoler.lxdh = (Button) v.findViewById(R.id.lxdh);
			viewHoler.listView = (ListViewForScrollView) v.findViewById(R.id.listViewData);
			v.setTag(viewHoler);
		} else {
			viewHoler = (ViewHoler) v.getTag();
		}
		viewHoler.psdz.setText(tyd.getConsign().getPsxxdz());
		viewHoler.tydh.setText(tyd.getConsign().getTydh());
		viewHoler.lxr.setText(tyd.getConsign().getPsdzlxr());
		viewHoler.lxdh.setText(tyd.getConsign().getPsdzlxdh());
		adapter = new NzyHwxxAdapter(context, tyd.getHwList());
		viewHoler.listView.setAdapter(adapter);
		
		viewHoler.lxdh.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				KingTellerConfigUtils.dial(context, ((Button) arg0).getText().toString());
			}
		});
		
		return v;
	}

	private static class ViewHoler {
		public TextView psdz;
		public TextView tydh;
		public TextView lxr;
		public Button lxdh;
		public ListViewForScrollView listView;
	}
}
