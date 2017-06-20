package com.kingteller.client.adapter;

import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.kingteller.R;
import com.kingteller.client.bean.onlinelearning.Node;

public class QRDotMachineAdapter<T> extends TreeListViewAdapter<T>{
	private LayoutInflater inflater;
	public QRDotMachineAdapter(ListView mTree, Context context, List<T> datas,int defaultExpandLevel) throws
		IllegalArgumentException,IllegalAccessException {
		super(mTree, context, datas, defaultExpandLevel);
		inflater = LayoutInflater.from(context);

	}
	
	
	@Override
	public View getConvertView(Node node, int position, View v, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHoler viewHoler = null;
		if (v == null) {
			viewHoler = new ViewHoler();
			v = inflater.inflate(R.layout.item_dotmachine_cargosacan, null);
			viewHoler.image = (ImageView) v.findViewById(R.id.wdjq_item_image);
			viewHoler.wlmc = (TextView) v.findViewById(R.id.wdjq_item_wlmc);
			viewHoler.tmbh = (TextView) v.findViewById(R.id.wdjq_item_tmbh);

			v.setTag(viewHoler);
		} else {
			viewHoler = (ViewHoler) v.getTag();
		}

		if (node.getIcon() == -1) {
			viewHoler.image.setVisibility(View.INVISIBLE);
		} else {
			viewHoler.image.setVisibility(View.VISIBLE);
			viewHoler.image.setImageResource(node.getIcon());
		}
		

		String [] stringArr = node.getName().split(",");

		viewHoler.wlmc.setText(stringArr[0] + "(" + stringArr[1] + ")");
		viewHoler.tmbh.setText(stringArr[2]);

		if("false".equals(stringArr[4]) || "null".equals(stringArr[4])){//新增
			viewHoler.wlmc.setTextColor(Color.parseColor("#666666"));
		}else{
			viewHoler.wlmc.setTextColor(Color.parseColor("#008B00"));
		}
		
		if("true".equals(stringArr[3])){//当前扫 
			viewHoler.tmbh.setTextColor(Color.parseColor("#FF0000"));
		}else{
			viewHoler.tmbh.setTextColor(Color.parseColor("#666666"));
		}
		
		return v;
	}
	
	private static class ViewHoler {
		public ImageView image;
		public TextView tmbh;
		public TextView wlmc;
	}

}
