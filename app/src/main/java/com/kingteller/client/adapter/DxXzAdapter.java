package com.kingteller.client.adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.kingteller.R;
import com.kingteller.client.bean.common.CommonSelectData;
import com.kingteller.client.bean.pxkh.AnswerParamBean;

public class DxXzAdapter extends BaseAdapter{

	private LayoutInflater inflater;
	private Context context;
	private List<AnswerParamBean> answerList;
	private AnswerParamBean apb;
	private static HashMap<Integer, Boolean> isCheckedMap ;
	private List<AnswerParamBean> beSelectedData = new ArrayList<AnswerParamBean>(); 
	private List<CommonSelectData> commonSelectList = new ArrayList<CommonSelectData>();
	
	public DxXzAdapter(Context context,List<AnswerParamBean> answerList){
		inflater = LayoutInflater.from(context);
		this.context = context;
		this.answerList = answerList;
		
		if(isCheckedMap != null){
			isCheckedMap = null;
		}
		
		isCheckedMap = new HashMap<Integer, Boolean>();
		
		for(int i =0;i < answerList.size();i ++){
			isCheckedMap.put(i, false);
		}
		
		if(beSelectedData.size() > 0){
			beSelectedData.clear();
		}
	}
	
	public void setLists(List<AnswerParamBean> answerList) {
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
		if( answerList == null){
			return 0;
		}
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

	public View getView(final int pos, View v, ViewGroup arg2) {
		// TODO Auto-generated method stub
		apb = answerList.get(pos);
		
		ViewHoler viewHoler = null;
		if (v == null) {
			viewHoler = new ViewHoler();
			v = inflater.inflate(R.layout.item_dxxz_st, null);
		
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
				boolean cu = !isCheckedMap.get(pos);
				for(Integer p : isCheckedMap.keySet()) {  
					isCheckedMap.put(p, false);  
                }  
				isCheckedMap.put(pos, cu);  
                notifyDataSetChanged();  
                beSelectedData.clear();  
                if(cu) {
                	beSelectedData.add(answerList.get(pos));
                }
			}
		});
		
		viewHoler.radioButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				boolean cu = !isCheckedMap.get(pos);
				for(Integer p : isCheckedMap.keySet()) {  
					isCheckedMap.put(p, false);  
                }  
				isCheckedMap.put(pos, cu);  
                notifyDataSetChanged();  
                beSelectedData.clear();  
                if(cu) {
                	beSelectedData.add(answerList.get(pos));
                }
			}
		});
		
		viewHoler.radioButton.setChecked(isCheckedMap.get(pos));
		return v;
	}
	
	private static class ViewHoler{
		public CheckBox radioButton;
		public TextView choiceItem;
		public TextView choiceId;
	} 
	
	public List<AnswerParamBean> getSelectedDateList(){
		return beSelectedData;
	}
	
	
}
