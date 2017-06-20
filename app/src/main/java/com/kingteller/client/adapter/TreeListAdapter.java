/* 
 * Copyright (C) 2012 Paul Burke
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at 
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0 
 * 
 * Unless required by applicable law or agreed to in writing, software 
 * distributed under the License is distributed on an "AS IS" BASIS, 
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
 * See the License for the specific language governing permissions and 
 * limitations under the License. 
 */

package com.kingteller.client.adapter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.kingteller.R;
import com.kingteller.client.bean.common.TreeBean;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 树形通用适配器
 * @author 王定波
 *
 */
public class TreeListAdapter extends BaseAdapter {

	private final static int ICON_FOLDER = R.drawable.ic_folder;
	private final static int ICON_FILE = R.drawable.ic_file;

	private List<TreeBean> mdatas = new ArrayList<TreeBean>();
	private final LayoutInflater mInflater;

	public TreeListAdapter(Context context) {
		mInflater = LayoutInflater.from(context);
	}

	public ArrayList<TreeBean> getListItems() {
		return (ArrayList<TreeBean>) mdatas;
	}

	public void setListItems(List<TreeBean> lists) {
		this.mdatas = lists;
		notifyDataSetChanged();
	}

	public int getCount() {
		return mdatas.size();
	}

	public void add(TreeBean data) {
		mdatas.add(data);
		notifyDataSetChanged();
	}

	public void clear() {
		mdatas.clear();
		notifyDataSetChanged();
	}

	public Object getItem(int position) {
		return mdatas.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		View row = convertView;
		ViewHolder holder = null;

		if (row == null) {
			row = mInflater.inflate(R.layout.item_file, parent, false);
			holder = new ViewHolder(row);
			row.setTag(holder);
		} else {
			// Reduce, reuse, recycle!
			holder = (ViewHolder) row.getTag();
		}

		// Get the file at the current position
		final TreeBean data = (TreeBean) getItem(position);

		// Set the TextView as the file name
		holder.nameView.setText(data.getTitle());

		// If the item is not a directory, use the file icon
		holder.iconView.setImageResource(data.isLast()? ICON_FILE
				: ICON_FOLDER);

		return row;
	}

	static class ViewHolder {
		TextView nameView;
		ImageView iconView;

		ViewHolder(View row) {
			nameView = (TextView) row.findViewById(R.id.file_name);
			iconView = (ImageView) row.findViewById(R.id.file_icon);
		}
	}
}
