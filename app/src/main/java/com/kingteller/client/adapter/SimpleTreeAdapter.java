package com.kingteller.client.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.kingteller.R;
import com.kingteller.client.bean.onlinelearning.Node;

public class SimpleTreeAdapter<T> extends TreeListViewAdapter<T> {

	private LayoutInflater inflater;

	public SimpleTreeAdapter(ListView mTree, Context context, List<T> datas,
			int defaultExpandLevel) throws IllegalArgumentException,
			IllegalAccessException {
		super(mTree, context, datas, defaultExpandLevel);
		inflater = LayoutInflater.from(context);
	}


	@Override
	public View getConvertView(Node node, int position, View v, ViewGroup parent) {
		ViewHoler viewHoler = null;
		if (v == null) {
			viewHoler = new ViewHoler();
			v = inflater.inflate(R.layout.item_tree_view, null);
			viewHoler.id_treenode_icon = (ImageView) v.findViewById(R.id.id_treenode_icon);
			viewHoler.id_treenode_label = (TextView) v.findViewById(R.id.id_treenode_label);

			v.setTag(viewHoler);
		} else {
			viewHoler = (ViewHoler) v.getTag();
		}

		if (node.getIcon() == -1) {
			viewHoler.id_treenode_icon.setVisibility(View.INVISIBLE);
		} else {
			viewHoler.id_treenode_icon.setVisibility(View.VISIBLE);
			viewHoler.id_treenode_icon.setImageResource(node.getIcon());
		}

		viewHoler.id_treenode_label.setText(node.getName());

		return v;
	}

	private static class ViewHoler {
		public ImageView id_treenode_icon;
		public TextView id_treenode_label;
	}

}
