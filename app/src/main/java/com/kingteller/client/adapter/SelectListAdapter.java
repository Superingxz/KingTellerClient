package com.kingteller.client.adapter;

import java.util.List;

import com.kingteller.R;
import com.kingteller.client.bean.common.CommonSelectData;
import com.kingteller.client.utils.KingTellerJudgeUtils;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * select适配器
 * 
 * @author 王定波
 * 
 */
public class SelectListAdapter extends BaseAdapter {
	private LayoutInflater inflater;
	private List<CommonSelectData> lists;
	private Context context;

	public SelectListAdapter(Context context, List<CommonSelectData> lists) {
		inflater = LayoutInflater.from(context);
		this.lists = lists;
		this.context = context;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		if (lists == null)
			return 0;
		else
			return lists.size();
	}

	@Override
	public Object getItem(int pos) {
		// TODO Auto-generated method stub
		if (lists == null)
			return null;
		else
			return lists.get(pos);
	}

	@Override
	public long getItemId(int pos) {
		// TODO Auto-generated method stub
		return pos;
	}

	@Override
	public View getView(int pos, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHoler viewHoler = null;
		CommonSelectData data = (CommonSelectData) lists.get(pos);
		if (convertView == null) {
			viewHoler = new ViewHoler();
			convertView = inflater.inflate(R.layout.item_select, null);
			
			viewHoler.item_text = (TextView) convertView.findViewById(R.id.item_text);
			viewHoler.item_image = (ImageView) convertView.findViewById(R.id.item_image);
			
			convertView.findViewById(R.id.item_image).setVisibility(View.GONE);
			
			convertView.setTag(viewHoler);
		} else {
			viewHoler = (ViewHoler) convertView.getTag();
		}
		
		viewHoler.item_text.setText(data.getText());
		
		if(!KingTellerJudgeUtils.isEmpty(data.getTextcolor())){
			viewHoler.item_text.setTextColor(Color.parseColor(data.getTextcolor()));  
		}
		
		if(!KingTellerJudgeUtils.isEmpty(data.getImage()) && data.getImage() != 0){
			convertView.findViewById(R.id.item_image).setVisibility(View.VISIBLE);
			viewHoler.item_image.setBackgroundResource(data.getImage());
		}

		return convertView;
	}

	private static class ViewHoler {
		public TextView item_text;
		public ImageView item_image;
	}

}
