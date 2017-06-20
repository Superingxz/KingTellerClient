package com.kingteller.client.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.kingteller.R;


/**
 * 顶部菜单适配器
 * @author 王定波
 *
 */
public class TopTabAdapter extends BaseAdapter {
	private LayoutInflater inflater;
	private Context context;
	private int selectpostion = 0;
	private int graypostion = 100;
	private String[] tooltitles;

	public TopTabAdapter(Context context, String[] tooltitles) {
		super();
		inflater = (LayoutInflater) context.getSystemService("layout_inflater");
		this.context = context;
		this.tooltitles = tooltitles;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		if (tooltitles == null)
			return 0;
		return tooltitles.length;
	}

	@Override
	public Object getItem(int location) {
		// TODO Auto-generated method stub
		return tooltitles[location];
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub

		final ViewHoler viewHoler;
		if (convertView == null) {
			viewHoler = new ViewHoler();
			convertView = inflater.inflate(R.layout.item_top_tab, null);

			viewHoler.item_title = (TextView) convertView
					.findViewById(R.id.item_title);

			convertView.setTag(viewHoler);
		} else {
			viewHoler = (ViewHoler) convertView.getTag();
		}

		viewHoler.item_title.setText(tooltitles[position]);
		
		if(graypostion == position){
			viewHoler.item_title.setTextColor(context.getResources().getColor(
					R.color.gray));
		}else{
			if (selectpostion == position) {
				viewHoler.item_title.setTextColor(context.getResources().getColor(
						R.color.menu_color));
			} else {
				viewHoler.item_title.setTextColor(context.getResources().getColor(
						R.color.black));
			}
		}
		return convertView;
	}

	public void setSelectPostion(int selectpostion) {
		this.selectpostion = selectpostion;
		notifyDataSetChanged();
	}

	public void setGrayPostion(int graypostion){
		this.graypostion = graypostion;
		notifyDataSetChanged();
	}
	
	private static class ViewHoler {

		public ImageView item_new;
		public TextView item_title;
		public ImageView item_icon;

	}

}
