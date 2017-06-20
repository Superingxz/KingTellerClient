package com.kingteller.client.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kingteller.R;
import com.kingteller.client.adapter.HwxxAdapter;
import com.kingteller.client.bean.logisticmonitor.LogisticConsignMobileBean;

public class TydmkView extends GroupViewBase{

	private TextView tydh, psdz, lxr, lxdh;
	private ListViewForScrollView listView;
	private HwxxAdapter adapter;

	public TydmkView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void initView() {
		// TODO Auto-generated method stub
		LayoutInflater.from(getContext())
				.inflate(R.layout.item_add_tydmk, this);
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
		lp.setMargins(0, 10, 0, 0);
		setLayoutParams(lp);

		tydh = (TextView) findViewById(R.id.tydh);
		psdz = (TextView) findViewById(R.id.psdz);
		lxr = (TextView) findViewById(R.id.lxr1);
		lxdh = (TextView) findViewById(R.id.lxdh1);
		listView = (ListViewForScrollView) findViewById(R.id.listView);
		
		findViewById(R.id.acceptMalf).setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
			}
		});
	}

	@Override
	public boolean checkData() {
		// TODO Auto-generated method stub
		return false;
	}

	public void setData(LogisticConsignMobileBean consignBean) {

		tydh.setText(consignBean.getConsign().getTydh());
		psdz.setText(consignBean.getConsign().getPsxxdz());
		lxr.setText(consignBean.getConsign().getPsdzlxr());
		lxdh.setText(consignBean.getConsign().getPsdzlxdh());
		adapter = new HwxxAdapter(getContext(), consignBean.getHwList());
		listView.setAdapter(adapter);
		
	}

	@Override
	public Object getData() {
		// TODO Auto-generated method stub
		return null;
	}

}
