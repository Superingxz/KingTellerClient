package com.kingteller.client.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.kingteller.R;
import com.kingteller.client.bean.onlinelearning.VideoListBean;

public class VideoListAdadpter extends BaseAdapter{

	private LayoutInflater inflater;
	private List<VideoListBean> lists = new ArrayList<VideoListBean>();
	private Context context;
	private VideoListBean vlb ;

	public VideoListAdadpter(Context context, List<VideoListBean> lists) {
		inflater = LayoutInflater.from(context);
		this.lists = lists;
		this.context = context;
	}

	public void setLists(List<VideoListBean> lists) {
		this.lists = lists;
		notifyDataSetChanged();
	}

	public void addLists(List<VideoListBean> lists) {
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
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub

		vlb = (VideoListBean) lists.get(position);
		ViewHoler viewHoler = null;
		if (convertView == null) {
			viewHoler = new ViewHoler();
			convertView = inflater.inflate(R.layout.item_video_list, null);
			
			viewHoler.vtTitle = (TextView) convertView.findViewById(R.id.vtTitle);
			viewHoler.vtDuration = (TextView) convertView.findViewById(R.id.vtDuration);
			viewHoler.vtSwfLink = (TextView) convertView.findViewById(R.id.vtSwfLink);
			viewHoler.vtId = (TextView) convertView.findViewById(R.id.vtId);

			convertView.setTag(viewHoler);
		} else {
			viewHoler = (ViewHoler) convertView.getTag();
		}
		
		viewHoler.vtTitle.setText(vlb.getVtTitle());
		viewHoler.vtDuration.setText(vlb.getVtDuration());
		viewHoler.vtSwfLink.setText(vlb.getVtSwfLink());
		viewHoler.vtId.setText(vlb.getVtId());
		
	/*	convertView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				KingTellerJudgeUtils.openBrowser(context, vlb.getVtSwfLink().trim());
				Intent intent = new Intent(context, CommonWebViewActivity.class);
				intent.putExtra(CommonWebViewActivity.TITLE,vlb.getVtTitle().trim());
				intent.putExtra(CommonWebViewActivity.URL,vlb.getVtSwfLink().trim());
				context.startActivity(intent);
			}
		});*/

		return convertView;
	}

	private static class ViewHoler {

		public TextView vtTitle;
		public TextView vtDuration;
		public TextView vtSwfLink;
		public TextView vtId;

	}

}
