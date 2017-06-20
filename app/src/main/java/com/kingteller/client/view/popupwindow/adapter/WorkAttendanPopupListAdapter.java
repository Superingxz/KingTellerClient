package com.kingteller.client.view.popupwindow.adapter;

import java.util.List;

import com.kingteller.R;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;


public class WorkAttendanPopupListAdapter extends BaseAdapter{
	private int indexOfExpanded;
	int mViewResourceID = 0;//资源IDd
	private Context mContext;
	private boolean[] mExpanded;
	private List<String> mList_text;
	private List<Integer> mList_image;
	public TextView[] tv_titles;
	public WorkAttendanPopupListAdapter(Context Context, List<String> List_text, List<Integer> List_image, int view)
	{
		super(); 
	    this.mContext = Context;
	    this.mList_text = List_text;
	    this.mList_image = List_image;
	    tv_titles = new TextView[List_text.size()];
	    this.mViewResourceID = view;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		if(mList_text!=null)
			return mList_text.size();
		else
			return 0;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		/**
		 * 设置图标与图标名称的布局
		 */
		LinearLayout layout = new LinearLayout(mContext);
		layout.setOrientation(LinearLayout.HORIZONTAL);
		layout.setPadding(0, 5, 0, 5);
		layout.setGravity(Gravity.CENTER);

		/**
		 * ImageView图标
		 */
		ImageView img_item = new ImageView(mContext);
		img_item.setLayoutParams(new LayoutParams(50, 50));
		img_item.setImageResource(mList_image.get(position));
		
		
		/**
		 * TextView名称
		 */
		TextView tv_item = new TextView(mContext);
		tv_item.setLayoutParams(new GridView.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		tv_item.setGravity(Gravity.CENTER);
		tv_item.setTextColor(Color.WHITE);
		tv_item.setTextSize(16);
		tv_item.setPadding(10, 10, 10, 10);
		tv_item.setText(mList_text.get(position));
		
		layout.addView(img_item);
		layout.addView(tv_item);
		layout.setBackgroundResource(R.drawable.btn_blue_bg);//设置每个item的点击效果
		
		return layout;
	}
}
