package com.kingteller.client.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.kingteller.R;
import com.kingteller.client.bean.qrcode.QRTraceBackToBean;

/**
 * 通用选择适配器
 */
public class CommonSelectQrCodeAdapter extends BaseAdapter {
	private LayoutInflater inflater;
	private List<QRTraceBackToBean> lists = new ArrayList<QRTraceBackToBean>();
	private Context mContext;

	public CommonSelectQrCodeAdapter(Context context, List<QRTraceBackToBean> lists) {
		inflater = LayoutInflater.from(context);
		this.lists = lists;
		this.mContext = context;
	} 

	public void setLists(List<QRTraceBackToBean> lists) {
		this.lists = lists;
		notifyDataSetChanged();
	}

	public void addLists(List<QRTraceBackToBean> lists) {
		this.lists.addAll(lists);
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		if (lists == null)
			return 0;
		return lists.size();
	}

	@Override
	public Object getItem(int location) {
		// TODO Auto-generated method stub
		return lists.get(location);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		QRTraceBackToBean data = (QRTraceBackToBean) lists.get(position);
		
		ViewHoler viewHoler = null;
		if (convertView == null) {
			viewHoler = new ViewHoler();
			
			convertView = inflater.inflate(R.layout.item_search_qr_masg,null);
			
		
			viewHoler.item_ewm = (TextView) convertView.findViewById(R.id.qrcode_search_ewm);
			viewHoler.item_wlbm = (TextView) convertView.findViewById(R.id.qrcode_search_wlbm);
			viewHoler.item_wlmc = (TextView) convertView.findViewById(R.id.qrcode_search_wlmc);
			viewHoler.item_ggxh = (TextView) convertView.findViewById(R.id.qrcode_search_ggxh);
 
			convertView.setTag(viewHoler);
		} else {
			viewHoler = (ViewHoler) convertView.getTag();
		}

//		二维码：3.03.10.000215070003000000000000000000000000000000
		viewHoler.item_ewm.setText(Html.fromHtml("二维码：" + data.getBarcode()));
		viewHoler.item_wlbm.setText("物料编码：" + data.getNewCode());
		viewHoler.item_wlmc.setText("物料名称：" + data.getWlName());
		viewHoler.item_ggxh.setText("规格型号：" + data.getModelNo());

		return convertView;
	}

	private static class ViewHoler {
		
		public TextView item_ewm;
		public TextView item_wlbm;
		public TextView item_wlmc;
		public TextView item_ggxh;

	}

}
