package com.kingteller.client.adapter;

import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;

import com.kingteller.R;
import com.kingteller.client.bean.pxkh.AnswerParamBean;

public class MxXzAdapter extends BaseAdapter{

	private LayoutInflater inflater;
	private Context context;
	private List<AnswerParamBean> answerList;
	private AnswerParamBean apb;
	private HashMap<Integer, Boolean> isCheckedMap ;
	
	public MxXzAdapter(Context context,List<AnswerParamBean> answerList){
		inflater = LayoutInflater.from(context);
		this.context = context;
		this.answerList = answerList;
		isCheckedMap = new HashMap<Integer, Boolean>();
		
		for(int i =0; i < answerList.size(); i ++){
			isCheckedMap.put(i, false);
		}
	}
	
	public void setLists(List<AnswerParamBean> answerList){
		this.answerList = answerList;
		notifyDataSetChanged();
	}
	
	public void addLists(List<AnswerParamBean> answerList) {
		this.answerList.addAll(answerList);
		notifyDataSetChanged();
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return answerList.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return answerList.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(final int pos, View v, ViewGroup viewGroup) {
		// TODO Auto-generated method stub
		
		apb = answerList.get(pos);
		ViewHoler viewHoler = null;
		
		if (v == null) {
			viewHoler = new ViewHoler();
			v = inflater.inflate(R.layout.item_mxxz_st, null);
			viewHoler.radioButton = (CheckBox) v.findViewById(R.id.radioButton);
			viewHoler.choiceItem = (TextView) v.findViewById(R.id.choiceItem);
			viewHoler.choiceId = (TextView) v.findViewById(R.id.choiceId);

			v.setTag(viewHoler);
		}else{
			viewHoler = (ViewHoler)v.getTag();
		}
		viewHoler.choiceId.setText(apb.getId());
		viewHoler.choiceItem.setText(apb.getContent());
		v.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if(isCheckedMap.get(pos)){
					isCheckedMap.put(pos, false);
				}else{
					isCheckedMap.put(pos, true);
				}
				notifyDataSetChanged(); 
			}
		});
		
		
		viewHoler.radioButton.setChecked(isCheckedMap.get(pos)?true:false);
		
		return v;
	}

	private static class ViewHoler{
		public CheckBox radioButton;
		public TextView choiceItem;
		public TextView choiceId;
	} 
	
}
