package com.kingteller.client.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.kingteller.R;
import com.kingteller.client.bean.qrcode.QRCargoScanBean;
import com.kingteller.client.config.KingTellerStaticConfig;
import com.kingteller.client.view.toast.T;

public class QRDotDeliveradapter  extends BaseAdapter{
	private LayoutInflater inflater;
	private Context mContext;
	private List<QRCargoScanBean> showlists = new ArrayList<QRCargoScanBean>();
	private List<QRCargoScanBean> keeplists = new ArrayList<QRCargoScanBean>();
	public QRDotDeliveradapter(Context context, List<QRCargoScanBean> showlists, List<QRCargoScanBean> keeplists) {
		inflater = LayoutInflater.from(context);
		this.showlists = showlists;
		this.keeplists = keeplists;
		this.mContext = context;
	}
	public void setLists(List<QRCargoScanBean> showlists, List<QRCargoScanBean> keeplists) {
		this.showlists = showlists;
		this.keeplists = keeplists;
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		if (showlists == null)
			return 0;
		return showlists.size();
	}
	
	@Override
	public Object getItem(int location) {
		// TODO Auto-generated method stub
		return showlists.get(location);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
 
		final QRCargoScanBean data = (QRCargoScanBean) showlists.get(position);
		ViewHoler viewHoler = null;
		if (convertView == null) {
			viewHoler = new ViewHoler();
			convertView = inflater.inflate(R.layout.item_delivery_qrcode_smjl, null);
			viewHoler.mWlbm = (TextView) convertView.findViewById(R.id.qr_smjl_wlbm);
			viewHoler.mWlmc = (TextView) convertView.findViewById(R.id.qr_smjl_wlmc);
			viewHoler.mSmzt = (TextView) convertView.findViewById(R.id.qr_smjl_xz);
			viewHoler.mSl = (TextView) convertView.findViewById(R.id.qr_smjl_sl);
			viewHoler.mYTms = (TextView) convertView.findViewById(R.id.qr_smjl_ytms);

			viewHoler.mSl_name = (TextView) convertView.findViewById(R.id.textView3);
			viewHoler.mYTms_name = (TextView) convertView.findViewById(R.id.textView4);
			
			convertView.setTag(viewHoler);
		} else {
			viewHoler = (ViewHoler) convertView.getTag();
		}
		
			convertView.findViewById(R.id.qr_top_tmbh).setVisibility(View.GONE);
			convertView.findViewById(R.id.qr_top_little_mms).setVisibility(View.GONE);
			
			viewHoler.mSl_name.setText("数量："); 
			viewHoler.mYTms_name.setText("有条码数："); 

			viewHoler.mWlbm.setText(data.getNewCode());
			viewHoler.mWlmc.setText(data.getWlName());
			viewHoler.mSl.setText(data.getQuantity());
			viewHoler.mYTms.setText(data.getBarcodeCount());
			
			
			viewHoler.mSmzt.setText("删除");
			viewHoler.mSmzt.setTextColor(Color.parseColor("#FF0000"));
			viewHoler.mSmzt.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					for(int i = 0; i<showlists.size(); i++){
						if(data.getNewCode().equals(showlists.get(i).getNewCode())){
							showlists.remove(showlists.get(i));
							i = 0;
						}
					}

					for(int n = 0; n<keeplists.size(); n++){
						if(data.getNewCode().equals(keeplists.get(n).getNewCode())){
							keeplists.remove(keeplists.get(n));
							n = 0;
						}	
					}
					notifyDataSetChanged();
					T.showShort(mContext, "删除成功!");
					
					KingTellerStaticConfig.QR_DOTDELIVERY_LISTDATA = true;
				}
			});
			
			
			return convertView;
	}
	
	private static class ViewHoler {
		public TextView mWlbm;
		public TextView mWlmc;
		public TextView mSmzt;
		public TextView mSl;
		public TextView mYTms;
		public TextView mSl_name;
		public TextView mYTms_name;

	}
	
}
