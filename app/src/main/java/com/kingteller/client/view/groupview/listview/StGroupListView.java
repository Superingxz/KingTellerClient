package com.kingteller.client.view.groupview.listview;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.kingteller.R;
import com.kingteller.client.view.GroupViewBase;

public class StGroupListView extends LinearLayout {

	private LinearLayout layout_list;

	public LinearLayout getLayoutList() {
		return layout_list;
	}

	public StGroupListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView();
	}

	public StGroupListView(Context context) {
		super(context);
		initView();
	}

	private void initView() {

		LayoutInflater.from(getContext()).inflate(R.layout.st_group_list_view, this);

		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.WRAP_CONTENT);
		lp.setMargins(0, 10, 0, 0);
		setLayoutParams(lp);
		layout_list = (LinearLayout) findViewById(R.id.layout_list);

	}


	/**
	 * 得到所有视图的数据，视图必须为GroupViewBase的扩展类
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <T> List<T> getListData() {
		List<T> list = new ArrayList<T>();
		for (int i = 0; i < layout_list.getChildCount(); i++) {
			list.add((T) ((GroupViewBase) layout_list.getChildAt(i)).getData());
		}
		return list;
	}

	public LinearLayout getLinearLayout(int index){
		return (LinearLayout)layout_list.getChildAt(index);
	}

}
