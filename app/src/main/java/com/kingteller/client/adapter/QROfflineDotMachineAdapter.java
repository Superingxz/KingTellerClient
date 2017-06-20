package com.kingteller.client.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.kingteller.R;
import com.kingteller.client.bean.qrcode.QRDotMachineBean;
import com.kingteller.client.config.KingTellerStaticConfig;
import com.kingteller.client.view.toast.T;

public class QROfflineDotMachineAdapter extends BaseAdapter{
	private LayoutInflater inflater;
	private List<QRDotMachineBean> lists = new ArrayList<QRDotMachineBean>();
	private Context mContext; 
	
	public QROfflineDotMachineAdapter(Context context, List<QRDotMachineBean> lists) {
		inflater = LayoutInflater.from(context);
		this.lists = lists;
		this.mContext = context;
	}
	
	public void setLists(List<QRDotMachineBean> lists) {
		this.lists = lists;
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		if (lists == null)
			return 0;
		return lists.size();
	}
	
	@Override
	public Object getItem(int location) {
		return lists.get(location);
	}
	
	@Override
	public long getItemId(int position) {
		return position;
	}
	   
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		QRDotMachineBean data = (QRDotMachineBean) lists.get(position);
		ViewHoler viewHoler = null;
		if (convertView == null) {
			viewHoler = new ViewHoler();
			convertView = inflater.inflate(R.layout.item_offline_qrbarcode_cargosacan, null);
			viewHoler.mEwm = (TextView) convertView.findViewById(R.id.qrewm_bm_offline);
			viewHoler.mSmsc = (TextView) convertView.findViewById(R.id.qrewm_sc_offline);
			convertView.setTag(viewHoler);
		} else {
			viewHoler = (ViewHoler) convertView.getTag();
		}
		
		viewHoler.mEwm.setText(data.getWlBarCode());
		
		viewHoler.mSmsc.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				lists.remove(position);
				notifyDataSetChanged();
				T.showShort(mContext, "删除成功!");
				KingTellerStaticConfig.QR_DOTMACHINE_OFFLINE_LISTDATA = true;
			}
		});
		
		return convertView;
	}
	
	
	private static class ViewHoler {
		public TextView mEwm;
		public TextView mSmsc;
	}
	
}
