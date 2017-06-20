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
import com.kingteller.client.bean.knowledge.KnowledgeBean;

/**
 * 知识库列表适配器
 */
public class KnowledgeAdapter extends BaseAdapter {
	private LayoutInflater inflater;
	private List<KnowledgeBean> lists = new ArrayList<KnowledgeBean>();
	private Context context;

	public KnowledgeAdapter(Context context, List<KnowledgeBean> lists) {
		inflater = LayoutInflater.from(context);
		this.lists = lists;
		this.context = context;

	}

	public void setLists(List<KnowledgeBean> lists) {
		this.lists = lists;
		notifyDataSetChanged();
	}

	public void addLists(List<KnowledgeBean> lists) {
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
		// TODO Auto-generated method stub

		KnowledgeBean data = (KnowledgeBean) lists.get(position);
		ViewHoler viewHoler = null;
		if (convertView == null) {
			viewHoler = new ViewHoler();
			convertView = inflater.inflate(R.layout.item_knowledge, null);
			viewHoler.item_error_code = (TextView) convertView
					.findViewById(R.id.item_error_code);
			viewHoler.item_component = (TextView) convertView
					.findViewById(R.id.item_component);
			viewHoler.item_trouble_remark = (TextView) convertView
					.findViewById(R.id.item_trouble_remark);
			viewHoler.item_maintenance_remark = (TextView) convertView
					.findViewById(R.id.item_maintenance_remark);

			convertView.setTag(viewHoler);
		} else {
			viewHoler = (ViewHoler) convertView.getTag();
		}

		viewHoler.item_error_code.setText(data.getErrorCode());
		viewHoler.item_component.setText(data.getComponent());
		viewHoler.item_trouble_remark.setText(data.getTroubleRemark());
		viewHoler.item_maintenance_remark.setText(Html.fromHtml(data.getMaintenanceRemark()));

		return convertView;
	}

	private static class ViewHoler {

		public TextView item_error_code;
		public TextView item_component;
		public TextView item_trouble_remark;
		public TextView item_maintenance_remark;

	}

}
