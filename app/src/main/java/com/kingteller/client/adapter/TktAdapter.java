package com.kingteller.client.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.kingteller.R;
import com.kingteller.client.bean.pxkh.QuestionParamBean;
import com.kingteller.client.view.LineEditText;

public class TktAdapter extends BaseAdapter {

	private LayoutInflater inflater;
	private Context context;
	private int num;
	private QuestionParamBean qpb ;

	public TktAdapter(Context context,QuestionParamBean data, int num) {
		inflater = LayoutInflater.from(context);
		this.context = context;
		this.num = num;
		this.qpb = data;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return num;
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	public View getView(final int pos, View v, ViewGroup arg2) {
		// TODO Auto-generated method stub

		ViewHoler viewHoler = null;
		if (v == null) {
			viewHoler = new ViewHoler();

			v = inflater.inflate(R.layout.item_edittext_st, null);
			viewHoler.completeId = (TextView) v.findViewById(R.id.completeId);
			viewHoler.numId = (TextView) v.findViewById(R.id.numId);
			viewHoler.myEdit = (LineEditText) v.findViewById(R.id.myEdit);

			v.setTag(viewHoler);
		} else {
			viewHoler = (ViewHoler) v.getTag();
		}
		viewHoler.completeId.setText(qpb.getId()+"_"+(pos+1));
		viewHoler.numId.setText("("+(pos+1)+")");
		return v;
	}

	private static class ViewHoler {
		public TextView completeId;
		public TextView numId;
		
		public LineEditText myEdit;
	}
	
	
}
