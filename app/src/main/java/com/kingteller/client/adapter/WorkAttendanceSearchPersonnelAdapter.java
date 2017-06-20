package com.kingteller.client.adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.CompoundButton.OnCheckedChangeListener;

import com.kingteller.R;
import com.kingteller.client.bean.attendance.WorkAttendanceSearchPeopleBean;
import com.kingteller.client.bean.workorder.AssignWorkerNameBean;

@SuppressLint("UseSparseArrays")
public class WorkAttendanceSearchPersonnelAdapter extends BaseAdapter {
	private LayoutInflater inflater;
	private Context mContext;
	private WorkAttendanceSearchPeopleBean assignWorkerNameBean;
	private HashMap<Integer, Boolean> isCheckedMap = new HashMap<Integer, Boolean>();
	private List<WorkAttendanceSearchPeopleBean> assignWorkerNamelist = new ArrayList<WorkAttendanceSearchPeopleBean>();
	private List<WorkAttendanceSearchPeopleBean> workerNameCheckedList = new ArrayList<WorkAttendanceSearchPeopleBean>();
	
	private OnItemClickL lister;
	
	public WorkAttendanceSearchPersonnelAdapter(Context context, List<WorkAttendanceSearchPeopleBean> assignWorkerNamelist) {
		inflater = LayoutInflater.from(context);
		this.mContext = context;
		this.assignWorkerNamelist = assignWorkerNamelist;
		configAddCheckMap(false);
	}

	public void setLists(List<WorkAttendanceSearchPeopleBean> assignWorkerNamelist) {
		this.assignWorkerNamelist = assignWorkerNamelist;
		isCheckedMap.clear();
		configAddCheckMap(false);
		notifyDataSetChanged();
	}

	public void addLists(List<WorkAttendanceSearchPeopleBean> assignWorkerNamelist) {
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
	public View getView(final int position, View v, ViewGroup parent) {
		
		assignWorkerNameBean = assignWorkerNamelist.get(position);

		ViewHoler viewHoler = null;
		if (v == null) {
			viewHoler = new ViewHoler();
			v = inflater.inflate(R.layout.item_search_oa_people, null);
			viewHoler.jobNumber = (TextView) v.findViewById(R.id.oa_search_people_gh);
			viewHoler.userName = (TextView) v.findViewById(R.id.oa_search_people_xm);
			viewHoler.checkBox = (CheckBox) v.findViewById(R.id.oa_search_people_cb);

			v.setTag(viewHoler);
		}else{
			viewHoler = (ViewHoler)v.getTag();
		}
		
		viewHoler.checkBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean isChecked) {
				isCheckedMap.put(position, isChecked); //将选择项加载到map里面寄存
			}
		});
		
		if (isCheckedMap.get(position) == null) {
			isCheckedMap.put(position, false);
		}
		viewHoler.checkBox.setChecked(isCheckedMap.get(position));
		
		v.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if(((CheckBox) arg0.findViewById(R.id.oa_search_people_cb)).isChecked()){
					((CheckBox) arg0.findViewById(R.id.oa_search_people_cb)).setChecked(false);
					isCheckedMap.put(position, false);
					
				}else{
					((CheckBox) arg0.findViewById(R.id.oa_search_people_cb)).setChecked(true);
					isCheckedMap.put(position, true);
				}
				if (lister != null) {
					lister.OnItemClick(getWorkerNameCheckedList().size());
                }
			}
		});
		
		viewHoler.jobNumber.setText("工号：" + assignWorkerNameBean.getUserAccount());
		viewHoler.userName.setText("姓名：" + assignWorkerNameBean.getUserName());

		return v;
	}
	
	private  class ViewHoler{
		public TextView jobNumber;
		public TextView userName;
		public CheckBox checkBox;
	}

	public List<WorkAttendanceSearchPeopleBean> getWorkerNameCheckedList(){
		workerNameCheckedList = new ArrayList<WorkAttendanceSearchPeopleBean>();
		// 进行遍历
		for (int i = 0; i < assignWorkerNamelist.size(); i++) {
			if (isCheckedMap.get(i) != null && isCheckedMap.get(i)) {
				workerNameCheckedList.add(assignWorkerNamelist.get(i));
			}
		}
		
		return workerNameCheckedList ;
	}
	
	public void setOnItemClickLister(OnItemClickL lister) {
		this.lister = lister;
	}
	
	public interface OnItemClickL {
		public void OnItemClick(int pos);
	}
}
