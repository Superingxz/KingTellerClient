package com.kingteller.client.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;

import com.kingteller.R;
import com.kingteller.client.bean.common.CommonSelectData;
import com.kingteller.client.bean.workorder.WorkorderMachineParamBean;
import com.kingteller.client.utils.CommonSelcetUtils;
import com.kingteller.client.view.KingTellerEditText;
import com.kingteller.client.view.KingTellerEditText.OnChangeListener;

public class JqIdXxAdapter extends BaseAdapter{

	private LayoutInflater inflater;
	private List<WorkorderMachineParamBean> womplist = new ArrayList<WorkorderMachineParamBean>();
	private Context context;
	private WorkorderMachineParamBean womp;
	
	public JqIdXxAdapter(Context context,List<WorkorderMachineParamBean> womplist){
		inflater = LayoutInflater.from(context);
		this.context = context;
		this.womplist = womplist;
	}

	public void setLists(List<WorkorderMachineParamBean> womplist) {
		this.womplist = womplist;
		notifyDataSetChanged();
	}

	public void addLists(List<WorkorderMachineParamBean> womplist) {
		this.womplist.addAll(womplist);
		notifyDataSetChanged();
	}

	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		if (womplist == null) {
			return 0;
		}
		return womplist.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return womplist.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(final int position, View v, ViewGroup arg2) {
		// TODO Auto-generated method stub
		womp = womplist.get(position);
		
		ViewHoler viewHoler = null;
		if (v == null) {
			viewHoler = new ViewHoler();
			v = inflater.inflate(R.layout.item_add_jqidxx, null);
			viewHoler.xmId = (TextView) v.findViewById(R.id.xmId);
			viewHoler.type = (TextView) v.findViewById(R.id.type1);
			viewHoler.typeName = (TextView) v.findViewById(R.id.typeName1);
			viewHoler.content = (TextView) v.findViewById(R.id.content1);
			viewHoler.choose = (KingTellerEditText) v.findViewById(R.id.choose1);
			viewHoler.editText = (EditText) v.findViewById(R.id.editText1);
			v.setTag(viewHoler);
		}else{
			viewHoler = (ViewHoler)v.getTag();
		}
		
		viewHoler.xmId.setText(womp.getId());
		viewHoler.type.setText(String.valueOf(position+1)+".");
		viewHoler.typeName.setText("["+womp.getTypename()+"]");
		viewHoler.content.setText(womp.getContent());
		viewHoler.choose.setLists(CommonSelcetUtils.getSelectList(CommonSelcetUtils.CzType));
		viewHoler.editText.setVisibility(View.GONE);
		
		final ViewHoler vh = viewHoler;
		viewHoler.choose.setOnChangeListener(new OnChangeListener() {
			
			@Override
			public void onChanged(CommonSelectData data) {
				// TODO Auto-generated method stub
				if(data.getValue().equals("3")){
					vh.editText.setVisibility(View.VISIBLE);
				}else{
					vh.editText.setVisibility(View.GONE);
					vh.editText.setText("");
				}
			}
		});
		
		return v;
	}

	private static class ViewHoler{
		public TextView xmId;
		public TextView type;
		public TextView typeName;
		public TextView content;
		public KingTellerEditText choose;
		public EditText editText;
		
	} 
	/*	
	public interface OnDataChangeListener{
		public void onChange(String data);
	}
	
	public void setOnDataChangeListener(OnDataChangeListener listener) {
		this.mListener = listener;
	}
	*/
}
