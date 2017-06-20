package com.kingteller.client.adapter;

import java.util.ArrayList;
import java.util.List;

import com.kingteller.R;
import com.kingteller.client.bean.qrcode.QRCargoScanBean;
import com.kingteller.client.bean.qrcode.QRDeliveryBean;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class QRCargoScanAdapter extends BaseAdapter{
	private LayoutInflater inflater;
	private int Type;
	private List<QRCargoScanBean> lists = new ArrayList<QRCargoScanBean>();
	public QRCargoScanAdapter(Context context, List<QRCargoScanBean> lists, int type) {
		inflater = LayoutInflater.from(context);
		this.lists = lists;
		this.Type = type;
	}

	public void setLists(List<QRCargoScanBean> lists) {
		this.lists = lists;
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
		// TODO Auto-generated method stub

		QRCargoScanBean data = (QRCargoScanBean) lists.get(position);
		ViewHoler viewHoler = null;
		if (convertView == null) {
			viewHoler = new ViewHoler();
			convertView = inflater.inflate(R.layout.item_delivery_qrcode_smjl, null);
			viewHoler.mWlbm = (TextView) convertView.findViewById(R.id.qr_smjl_wlbm);
			viewHoler.mWlmc = (TextView) convertView.findViewById(R.id.qr_smjl_wlmc);
			viewHoler.mSl = (TextView) convertView.findViewById(R.id.qr_smjl_sl);
			viewHoler.mYTms = (TextView) convertView.findViewById(R.id.qr_smjl_ytms);
			viewHoler.mMTms = (TextView) convertView.findViewById(R.id.qr_smjl_mtms);
			viewHoler.mSmzt = (TextView) convertView.findViewById(R.id.qr_smjl_xz);
			viewHoler.mTmbh = (TextView) convertView.findViewById(R.id.qr_smjl_tmbh);
			viewHoler.mSl_name = (TextView) convertView.findViewById(R.id.textView3);
			viewHoler.mYTms_name = (TextView) convertView.findViewById(R.id.textView4);
			viewHoler.mMTms_name = (TextView) convertView.findViewById(R.id.TextView01);
			convertView.setTag(viewHoler);
		} else {
			viewHoler = (ViewHoler) convertView.getTag();
		}
		
		if(Type == 1 ){
			convertView.findViewById(R.id.qr_top_sitms).setVisibility(View.VISIBLE);
			convertView.findViewById(R.id.qr_top_tmbh).setVisibility(View.GONE);
			viewHoler.mSl_name.setText("数量："); 
			viewHoler.mYTms_name.setText("有条码数："); 
			viewHoler.mMTms_name.setText("没条码数："); 		
			
			viewHoler.mWlbm.setText(data.getNewCode());
			viewHoler.mWlmc.setText(data.getWlName());
			viewHoler.mSl.setText(data.getQuantity());
			viewHoler.mYTms.setText(data.getBarcodeCount());
			viewHoler.mMTms.setText(data.getNullBarcodeCount());
		}else if (Type == 2){
			convertView.findViewById(R.id.qr_top_sitms).setVisibility(View.VISIBLE);
			convertView.findViewById(R.id.qr_top_tmbh).setVisibility(View.GONE);
			viewHoler.mSl_name.setText("数量："); 
			viewHoler.mYTms_name.setText("有条码数："); 
			viewHoler.mMTms_name.setText("未扫描数："); 
			
			viewHoler.mWlbm.setText(data.getNewCode());
			viewHoler.mWlmc.setText(data.getWlName());
			viewHoler.mSl.setText(data.getQuantity());
			viewHoler.mYTms.setText(data.getBarcodeCount());
			viewHoler.mMTms.setText(data.getUnscanBarcodeCount());
		}else if(Type == 3){
			convertView.findViewById(R.id.qr_top_sitms).setVisibility(View.GONE);
			convertView.findViewById(R.id.qr_top_tmbh).setVisibility(View.VISIBLE);
			
			
			viewHoler.mWlbm.setText(data.getNewCode());
			viewHoler.mWlmc.setText(data.getWlName());
			viewHoler.mTmbh.setText(data.getWlBarCode());
		}

		//EE7600橙色 008B00绿色 FF0000红色 666666灰色  4169E1蓝色 DA70D6紫色
		if(data.getIsNewadd() != null ){
			viewHoler.mSmzt.setVisibility(View.VISIBLE);
			if(data.getIsNewadd() != null && data.getIsNewadd().equals("1")){
				viewHoler.mSmzt.setText("新增");
				viewHoler.mSmzt.setTextColor(Color.parseColor("#008B00"));  
			}else if(data.getIsNewadd() != null && data.getIsNewadd().equals("2")){
				viewHoler.mSmzt.setText("已替换");
				viewHoler.mSmzt.setTextColor(Color.parseColor("#008B00"));  
			}
		}else{
			viewHoler.mSmzt.setVisibility(View.GONE);
		}
		
		//收货  判断是否  验证
		if(data.getIsScaned() != null){
			if(data.getIsScaned().equals("是")){
				viewHoler.mMTms.setTextColor(Color.parseColor("#FF0000"));
			}else {
				viewHoler.mMTms.setTextColor(Color.parseColor("#666666"));
			}
		}
		
		if(data.getIsNewaddSl() != null && !data.getIsNewaddSl().equals("")){
			viewHoler.mSl.setTextColor(Color.parseColor("#FF0000"));  
		}else{
			viewHoler.mSl.setTextColor(Color.parseColor("#666666"));                              
		}
		
		if(data.getIsNewaddTms() != null && !data.getIsNewaddTms().equals("")){
			viewHoler.mYTms.setTextColor(Color.parseColor("#FF0000"));  
		}else{
			viewHoler.mYTms.setTextColor(Color.parseColor("#666666"));                              
		}

		return convertView;
	}

	private static class ViewHoler {
		public TextView mWlbm;
		public TextView mWlmc;
		public TextView mSl;
		public TextView mYTms;
		public TextView mMTms;
		public TextView mSl_name;
		public TextView mYTms_name;
		public TextView mMTms_name;
		public TextView mSmzt;
		public TextView mTmbh;
	}
}
