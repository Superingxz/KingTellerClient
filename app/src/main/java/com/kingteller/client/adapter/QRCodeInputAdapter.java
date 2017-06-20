package com.kingteller.client.adapter;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.kingteller.R;
import com.kingteller.client.bean.qrcode.QRBarCodeBean;
import com.kingteller.client.bean.qrcode.QRCargoScanBean;
import com.kingteller.client.photo.activity.PhotoMainActivity;
import com.kingteller.client.view.toast.T;

import android.view.View.OnLongClickListener;  
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

public class QRCodeInputAdapter extends BaseAdapter{
	private LayoutInflater inflater;
	private List<QRBarCodeBean> lists = new ArrayList<QRBarCodeBean>();
	private PopupWindow popupWindow;
	private static final int SHOW_TIME = 1000;
	private Context mContext; 
	public QRCodeInputAdapter(Context context, List<QRBarCodeBean> lists) {
		inflater = LayoutInflater.from(context);
		this.lists = lists;
		this.mContext = context;
		//initPopupWindow(inflater);
	}
	
	public void setLists(List<QRBarCodeBean> lists) {
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
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		QRBarCodeBean data = (QRBarCodeBean) lists.get(position);
		ViewHoler viewHoler = null;
		if (convertView == null) {
			viewHoler = new ViewHoler();
			convertView = inflater.inflate(R.layout.item_qrcode_input, null);
			viewHoler.mEwm = (TextView) convertView.findViewById(R.id.qrewm_ewm);
			viewHoler.mWlbm = (TextView) convertView.findViewById(R.id.qrewm_wlbm);
			viewHoler.mWlmc = (TextView) convertView.findViewById(R.id.qrewm_wlmc);
			viewHoler.mGgxh = (TextView) convertView.findViewById(R.id.qrewm_ggxh);
			viewHoler.mSmzt = (TextView) convertView.findViewById(R.id.qrewm_smzt);
			viewHoler.mSmsc = (TextView) convertView.findViewById(R.id.qrewm_sc);
			viewHoler.mKcsl = (TextView) convertView.findViewById(R.id.qrewm_kcsl);
			viewHoler.mYlr = (TextView) convertView.findViewById(R.id.qrewm_ylr);
			viewHoler.mWlr = (TextView) convertView.findViewById(R.id.qrewm_wlr);
			convertView.setTag(viewHoler);
		} else {
			viewHoler = (ViewHoler) convertView.getTag();
		}
		
		if(data.getSmZt() != null ){
			if(data.getSmZt().equals("二维码未被使用且有效")){
				viewHoler.mSmzt.setTextColor(Color.parseColor("#666666"));  
			}else{
				viewHoler.mSmzt.setTextColor(Color.parseColor("#FF0000"));   
			}
		} 
		
		viewHoler.mEwm.setText("二维码："  + data.getBarCode());
		viewHoler.mWlbm.setText("物料编码：" + data.getNewCode());
		viewHoler.mWlmc.setText("物料名称：" + data.getWlName());
		viewHoler.mGgxh.setText("规格型号：" + data.getModelNo());
		
		if(data.getStockQuantity() !=null && data.getBarcodeCount() !=null && data.getNullBarcodeCount() !=null){
			viewHoler.mKcsl.setText("库存量：" + data.getStockQuantity());
			viewHoler.mYlr.setText("已录入：" + data.getBarcodeCount());
			viewHoler.mWlr.setText("未录入：" + data.getNullBarcodeCount());
		}else{
			viewHoler.mKcsl.setText("库存量：0" );
			viewHoler.mYlr.setText("已录入：0");
			viewHoler.mWlr.setText("未录入：0");
		}
		viewHoler.mSmzt.setText(data.getSmZt());
	
		viewHoler.mSmsc.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				lists.remove(position);
				notifyDataSetChanged();
				T.showShort(mContext, "删除成功!");
			}
		});
		
		return convertView;
	}
	
	
	private static class ViewHoler {
		public TextView mEwm;
		public TextView mWlbm;
		public TextView mWlmc;
		public TextView mGgxh;
		public TextView mSmzt;
		public TextView mKcsl;
		public TextView mYlr;
		public TextView mWlr;
		public TextView mSmsc;
	}
	

}
