package com.kingteller.client.view;

import com.kingteller.R;
import com.kingteller.client.view.datatype.LoadingEnum;

import android.app.Activity;
import android.view.View;

/**
 * 通用listView对象
 * @author 王定波
 *
 */
public class ListViewObj extends LoadingObj {
	private XListView listview;

	public ListViewObj(Activity activity) {
		super(activity);
		listview = (XListView) activity.findViewById(R.id.list_view);
	}
	
	public ListViewObj(View view) {
		super(view);
		listview = (XListView) view.findViewById(R.id.list_view);
	}
	
	@Override
	public void setStatus(LoadingEnum status, String message) {
		// TODO Auto-generated method stub
		super.setStatus(status, message);
		if(listview!=null)
		{
			if(status==LoadingEnum.LISTSHOW)
			{
				listview.setVisibility(View.VISIBLE);
				super.setVisibility(View.GONE);
			}
			else {
				listview.setVisibility(View.GONE);
			}
		}
	}
	
	@Override
	public void setStatus(LoadingEnum status) {
		setStatus(status,null);
	}
	
	public XListView getListview() {
		return listview;
	}

	public void setListview(XListView listview) {
		this.listview = listview;
	}
}
