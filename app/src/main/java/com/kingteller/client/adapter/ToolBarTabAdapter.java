package com.kingteller.client.adapter;

import java.util.ArrayList;
import java.util.List;

import com.kingteller.R;
import com.kingteller.client.bean.common.ToolBar;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 底部菜单适配器
 * @author 王定波
 *
 */
public class ToolBarTabAdapter extends BaseAdapter {
	private LayoutInflater inflater;
	private List<ToolBar> list = new ArrayList<ToolBar>();
	private Context context;
	private int selectpostion = 0;

	private final static String[] tooltitles = { "提醒", "工作", "系统设置" };
	private final static int[] nomalIcons = { R.drawable.icon_waitdo_nomal,
			R.drawable.icon_competence_nomal, R.drawable.icon_setting_nomal };
	private final static int[] pressedIcons = { R.drawable.icon_waitdo_pressed,
			R.drawable.icon_competence_pressed, R.drawable.icon_setting_pressed };

	public ToolBarTabAdapter(Context context) {
		super();
		inflater = (LayoutInflater) context.getSystemService("layout_inflater");
		this.context = context;
		initData();
	}
	
	public void setNew(int pos,boolean isnew)
	{
		list.get(pos).setIsNew(isnew);
		notifyDataSetChanged();
	}

	private void initData() {
		// TODO Auto-generated method stub
		int length = tooltitles.length;
		for (int i = 0; i < length; i++) {
			ToolBar data = new ToolBar();
			data.setTitle(tooltitles[i]);
			data.setNomalImg(nomalIcons[i]);
			data.setPressedImg(pressedIcons[i]);
			data.setIsNew(false);
			list.add(data);
		}
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		if (list == null)
			return 0;
		return list.size();
	}

	@Override
	public Object getItem(int location) {
		// TODO Auto-generated method stub
		return list.get(location);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub

		ToolBar data = (ToolBar) list.get(position);
		final ViewHoler viewHoler;
		if (convertView == null) {
			viewHoler = new ViewHoler();
			convertView = inflater.inflate(R.layout.item_toolbar, null);
			viewHoler.item_icon = (ImageView) convertView
					.findViewById(R.id.item_icon);
			viewHoler.item_title = (TextView) convertView
					.findViewById(R.id.item_title);
			viewHoler.item_new = (ImageView) convertView
					.findViewById(R.id.item_new);

			convertView.setTag(viewHoler);
		} else {
			viewHoler = (ViewHoler) convertView.getTag();
		}

		viewHoler.item_title.setText(data.getTitle());
		if (selectpostion == position) {
			viewHoler.item_icon.setImageResource(data.getPressedImg());
			viewHoler.item_title.setTextColor(context.getResources().getColor(R.color.menu_color));
		} else {
			viewHoler.item_icon.setImageResource(data.getNomalImg());
			viewHoler.item_title.setTextColor(context.getResources().getColor(R.color.black));
		}
		
		if(data.isIsNew())
			viewHoler.item_new.setVisibility(View.VISIBLE);
		else viewHoler.item_new.setVisibility(View.GONE);

		return convertView;
	}

	public void setSelectPostion(int selectpostion) {
		this.selectpostion = selectpostion;
		notifyDataSetChanged();
	}

	private static class ViewHoler {

		public ImageView item_new;
		public TextView item_title;
		public ImageView item_icon;

	}

}
