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
import com.kingteller.client.activity.logisticmonitor.LogisticTaskActivity;
import com.kingteller.client.bean.logisticmonitor.LogisticConsignMobileBean;
import com.kingteller.client.utils.KingTellerConfigUtils;
import com.kingteller.client.view.ListViewForScrollView;

public class YtjTxBgAdapter extends BaseAdapter {

	private LayoutInflater inflater;
	private List<LogisticConsignMobileBean> tydlist = new ArrayList<LogisticConsignMobileBean>();
	private Context context;
	private LogisticConsignMobileBean tyd;
	private YtjHwxxAdapter adapter;

	public YtjTxBgAdapter(Context context,
			List<LogisticConsignMobileBean> tydlist) {
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
			v = inflater.inflate(R.layout.item_add_ytjtxbg, null);
			viewHoler.tydh = (TextView) v.findViewById(R.id.tydh);
			viewHoler.tcdz = (TextView) v.findViewById(R.id.tcdz);
			viewHoler.tclxr = (TextView) v.findViewById(R.id.tclxr);
			viewHoler.tclxdh = (Button) v.findViewById(R.id.tclxdh);
			viewHoler.jqbm = (TextView) v.findViewById(R.id.jqbm);
			viewHoler.trdz = (TextView) v.findViewById(R.id.trdz);
			viewHoler.trlxr = (TextView) v.findViewById(R.id.trlxr);
			viewHoler.trlxdh = (Button) v.findViewById(R.id.trlxdh);
			viewHoler.listView = (ListViewForScrollView) v.findViewById(R.id.listViewData);

			v.setTag(viewHoler);
		} else {
			viewHoler = (ViewHoler) v.getTag();
		}
		viewHoler.tydh.setText(tyd.getConsign().getTydh());
		viewHoler.tcdz.setText(tyd.getConsign().getDcdz());
		viewHoler.tclxr.setText(tyd.getConsign().getDcdzlxr());
		viewHoler.tclxdh.setText(tyd.getConsign().getDcdzlxdh());
		viewHoler.jqbm.setText(tyd.getConsign().getJqbm());
		viewHoler.trdz.setText(tyd.getConsign().getDrdz());
		viewHoler.trlxr.setText(tyd.getConsign().getDrdzlxr());
		viewHoler.trlxdh.setText(tyd.getConsign().getDrdzlxdh());
		adapter = new YtjHwxxAdapter(context, tyd.getHwList());
		viewHoler.listView.setAdapter(adapter);
	
		viewHoler.tclxdh.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				KingTellerConfigUtils.dial(context, ((Button) arg0)
						.getText().toString());
			}
		});
		
		viewHoler.trlxdh.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				KingTellerConfigUtils.dial(context, ((Button) arg0)
						.getText().toString());
			}
		});
		return v;
	}

	private static class ViewHoler {
		public TextView tydh;
		public TextView tcdz;
		public TextView tclxr;
		public Button tclxdh;
		public TextView jqbm;
		public TextView trdz;
		public TextView trlxr;
		public Button trlxdh;
		public ListViewForScrollView listView;
	}
	

}

