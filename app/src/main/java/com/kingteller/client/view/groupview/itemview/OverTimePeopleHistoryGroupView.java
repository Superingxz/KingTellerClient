package com.kingteller.client.view.groupview.itemview;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kingteller.R;
import com.kingteller.client.bean.attendance.OverTimePeopleBean;
import com.kingteller.client.view.GroupViewBase;

public class OverTimePeopleHistoryGroupView extends GroupViewBase implements Cloneable{
	
	private Context mContext;
	private TextView overtimePeopleHistoryExetime, overtimePeopleHistoryExeuser, 
	overtimePeopleHistorySuggestion , overtimePeopleHistoryTt, overtimePeopleHistoryTitle;
	
	public OverTimePeopleHistoryGroupView(Context context) {
		super(context);
		this.mContext = context;
	}
//
//	public OverTimePeopleHistoryGroupView(Context context, boolean isdel, int type) {
//		super(context, isdel, type);
//		this.mContext = context;
//	}
	
	@Override
	protected void initView() {
		LayoutInflater.from(getContext()).inflate(R.layout.item_add_overtimepeople_history, this);
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.WRAP_CONTENT);
		lp.setMargins(0, 0, 0, 0);
		setLayoutParams(lp);

		overtimePeopleHistoryExetime = (TextView) findViewById(R.id.oa_history_time);
		overtimePeopleHistoryTitle = (TextView) findViewById(R.id.oa_history_clhj);
		overtimePeopleHistoryExeuser = (TextView) findViewById(R.id.oa_history_clr);
		overtimePeopleHistorySuggestion = (TextView) findViewById(R.id.oa_history_clyj);
		
	}

	@Override
	public boolean checkData() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Object getData() {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * 时间   处理人  处理环节  处理情况  处理意见
	 * @param time
	 * @param name
	 * @param clhj
	 * @param clqk
	 * @param clyj
	 */
	public void setData(String time, String name, String clhj, String clqk, String clyj) {
		overtimePeopleHistoryExetime.setText(time);
		overtimePeopleHistoryTitle.setText(clhj);
		overtimePeopleHistoryExeuser.setText(name + "(" + clqk + ")");
		overtimePeopleHistorySuggestion.setText(clyj);
	}

	
}
