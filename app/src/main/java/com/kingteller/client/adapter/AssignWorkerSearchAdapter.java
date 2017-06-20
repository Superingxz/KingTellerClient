package com.kingteller.client.adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.CompoundButton.OnCheckedChangeListener;

import com.kingteller.R;
import com.kingteller.client.bean.workorder.AssignWorkerNameBean;
import com.kingteller.client.bean.workorder.MachineinfoSimpleBean;

@SuppressLint("UseSparseArrays")
public class AssignWorkerSearchAdapter extends BaseAdapter {

	private LayoutInflater inflater;
	private Context context;
	private AssignWorkerNameBean assignWorkerNameBean;
	private HashMap<Integer, Boolean> isCheckedMap = new HashMap<Integer, Boolean>();
	private List<AssignWorkerNameBean> assignWorkerNamelist = new ArrayList<AssignWorkerNameBean>();
	private List<AssignWorkerNameBean> workerNameCheckedList = new ArrayList<AssignWorkerNameBean>();

	public AssignWorkerSearchAdapter(Context context, List<AssignWorkerNameBean> assignWorkerNamelist) {
		inflater = LayoutInflater.from(context);
		this.context = context;
		this.assignWorkerNamelist = assignWorkerNamelist;
		configAddCheckMap(false);
	}

	public void setLists(List<AssignWorkerNameBean> assignWorkerNamelist) {
		this.assignWorkerNamelist = assignWorkerNamelist;
		isCheckedMap.clear();
		configAddCheckMap(false);
		notifyDataSetChanged();
	}

	public void addLists(List<AssignWorkerNameBean> assignWorkerNamelist) {
		this.assignWorkerNamelist.addAll(assignWorkerNamelist);
		configAddCheckMap(false);
		notifyDataSetChanged();
	}

	/**
	 * 首先,默认情况下,所有项目都是没有选中的.这里进行初始化
	 */
	public void configCheckMap(boolean bool) {
		for (int i = 0; i < assignWorkerNamelist.size(); i++) {
			isCheckedMap.put(i, bool);
		}
	}
	
	/**
	 * 新增
	 */
	public void configAddCheckMap(boolean bool) {
		for (int i = isCheckedMap.size() - 1; i < assignWorkerNamelist.size(); i++) {
			isCheckedMap.put(i, bool);
		}
	}
	
	@Override
	public int getCount() {
		if (assignWorkerNamelist == null) {
			return 0;
		}
		return assignWorkerNamelist.size();
	}

	@Override
	public Object getItem(int arg0) {
		return assignWorkerNamelist.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	@Override
	public View getView(final int postion, View v, ViewGroup parent) {
		
		assignWorkerNameBean = assignWorkerNamelist.get(postion);

		ViewHoler viewHoler = null;
		if (v == null) {
			viewHoler = new ViewHoler();
			v = inflater.inflate(R.layout.item_assign_worker, null);
			viewHoler.userAccount = (TextView) v.findViewById(R.id.userAccount);
			viewHoler.userName = (TextView) v.findViewById(R.id.userName);
			viewHoler.linkPhone = (TextView) v.findViewById(R.id.linkPhone);
			viewHoler.work_flag = (TextView) v.findViewById(R.id.work_flag);
			viewHoler.checkBox = (CheckBox) v.findViewById(R.id.checkBox);

			v.setTag(viewHoler);
		}else{
			viewHoler = (ViewHoler)v.getTag();
		}
		
		viewHoler.checkBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean isChecked) {
				isCheckedMap.put(postion, isChecked); //将选择项加载到map里面寄存
			}
		});
		
		if (isCheckedMap.get(postion) == null) {
			isCheckedMap.put(postion, false);
		}
		viewHoler.checkBox.setChecked(isCheckedMap.get(postion));
		
		v.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if(((CheckBox) arg0.findViewById(R.id.checkBox)).isChecked()){
					((CheckBox) arg0.findViewById(R.id.checkBox)).setChecked(false);
					isCheckedMap.put(postion, false);
				}else{
					((CheckBox) arg0.findViewById(R.id.checkBox)).setChecked(true);
					isCheckedMap.put(postion, true);
				}
			}
		});
		
		viewHoler.userAccount.setText(assignWorkerNameBean.getUserAccount());
		viewHoler.userName.setText(assignWorkerNameBean.getUserName());
		if(assignWorkerNameBean.getWorkFlag() == 0){
			viewHoler.work_flag.setText("休假");
		}else if(assignWorkerNameBean.getWorkFlag() == 2){
			viewHoler.work_flag.setText("请假");
		}else{
			viewHoler.work_flag.setText("空闲");
		}
		viewHoler.linkPhone.setText(assignWorkerNameBean.getLinkPhone());
		
		return v;
	}
	
	private  class ViewHoler{
		public TextView userAccount;
		public TextView userName;
		public TextView linkPhone;
		public TextView work_flag;
		public CheckBox checkBox;
	}

	public List<AssignWorkerNameBean> getWorkerNameCheckedList(){
		workerNameCheckedList = new ArrayList<AssignWorkerNameBean>();
		// 进行遍历
		for (int i = 0; i < assignWorkerNamelist.size(); i++) {
			if (isCheckedMap.get(i) != null && isCheckedMap.get(i)) {
				workerNameCheckedList.add(assignWorkerNamelist.get(i));
			}
		}
		
		return workerNameCheckedList ;
	}
	
}
