package com.kingteller.client.adapter;

import java.util.ArrayList;
import java.util.List;

import com.kingteller.R;
import com.kingteller.client.bean.qrcode.QRBarCodeBean;
import com.kingteller.client.bean.qrcode.QRCargoScanBean;
import com.kingteller.client.bean.qrcode.QRServiceCheckBean;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class QRServiceStationExpandableAdapter extends BaseExpandableListAdapter{
	
	private List<QRServiceCheckBean> mList_Group = new ArrayList<QRServiceCheckBean>();
	private List<List<QRCargoScanBean>> mList_Child = new ArrayList<List<QRCargoScanBean>>();
	private Context context;
	private LayoutInflater inflater;
	public QRServiceStationExpandableAdapter(Context context, List<QRServiceCheckBean> list_Group,
			List<List<QRCargoScanBean>> list_Child) {
		inflater = LayoutInflater.from(context);
		this.context = context;
		this.mList_Group = list_Group;
		this.mList_Child = list_Child;
	}

	
	public void setLists(List<QRServiceCheckBean> list_Group ,List<List<QRCargoScanBean>> list_Child) {
		this.mList_Group = list_Group;
		this.mList_Child = list_Child;
		notifyDataSetChanged();
	}
	
	@Override
	public int getGroupCount() {
	 return mList_Group.size();
	}
	
	@Override
	public int getChildrenCount(int groupPosition) {
	 return mList_Child.get(groupPosition).size();
	}
	
	@Override
	public Object getGroup(int groupPosition) {
	 return mList_Group.get(groupPosition);
	}
	
	@Override
	public Object getChild(int groupPosition, int childPosition) {
	 return mList_Child.get(groupPosition).get(childPosition);
	}
	
	@Override
	public long getGroupId(int groupPosition) {
	 return groupPosition;
	}
	
	@Override
	public long getChildId(int groupPosition, int childPosition) {
	 return childPosition;
	}
	
	@Override
	public boolean hasStableIds() {
	 return true;
	}
	
	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,View convertView, ViewGroup parent) {
		 GroupHolder groupHolder = null;
		 QRServiceCheckBean mGroupData = (QRServiceCheckBean) mList_Group.get(groupPosition);
		if (convertView == null) { 
			groupHolder = new GroupHolder();
			convertView =  inflater.inflate(R.layout.item_service_tree_group, null);
			groupHolder.mHandText_top = (TextView) convertView.findViewById(R.id.dj_tree_hand);
			groupHolder.mHandText_name = (TextView) convertView.findViewById(R.id.dj_tree_righttop_name);
			groupHolder.mHandText_rightOne = (TextView) convertView.findViewById(R.id.dj_tree_wlzs);
			groupHolder.mHandText_rightTwo = (TextView) convertView.findViewById(R.id.dj_tree_wltmzs);
			groupHolder.mHandText_rightThree = (TextView) convertView.findViewById(R.id.dj_tree_wlwtms);
			groupHolder.mHandImge = (ImageView) convertView.findViewById(R.id.dj_treenode_icon);
			
			convertView.setTag(groupHolder);
		  
		} else {
			groupHolder = (GroupHolder) convertView.getTag();
		}
		
			groupHolder.mHandText_top.setText(mGroupData.getTypeName());
			if(mGroupData.getTypeName() !=null){
				if(mGroupData.getTypeName().equals("登记物料出库")){
					groupHolder.mHandText_name.setText("出库总数：");
					groupHolder.mHandText_rightOne.setText(mGroupData.getTotalOutQuantity());
					groupHolder.mHandText_rightTwo.setText(mGroupData.getTotalOutBarcodeQuantity());
					groupHolder.mHandText_rightThree.setText(mGroupData.getTotalOutNullBarcodeQuantity());
				}else{
					groupHolder.mHandText_name.setText("入库总数：");
					groupHolder.mHandText_rightOne.setText(mGroupData.getTotalInQuantity());
					groupHolder.mHandText_rightTwo.setText(mGroupData.getTotalInBarcodeQuantity());
					groupHolder.mHandText_rightThree.setText(mGroupData.getTotalInNullBarcodeQuantity());
				}
			}else{
				groupHolder.mHandText_rightOne.setText("");
				groupHolder.mHandText_rightTwo.setText("");
				groupHolder.mHandText_rightThree.setText("");
			}
			
			//判断isExpanded就可以控制是按下还是关闭，同时更换图片
            if(isExpanded){
            	groupHolder.mHandImge.setBackgroundResource(R.drawable.ic_tree_ex);
            }else{
            	groupHolder.mHandImge.setBackgroundResource(R.drawable.ic_tree_ec);
            }
            
		return convertView;
	}
	
	@Override
	public View getChildView(int groupPosition, int childPosition,boolean isLastChild, 
			View convertView, ViewGroup parent) {
		
		QRCargoScanBean mChildData = (QRCargoScanBean) mList_Child.get(groupPosition).get(childPosition);
		
		ChildHolder childHolder = null;
		if (convertView == null) {
			 convertView =  inflater.inflate(R.layout.item_service_tree_child, null);
			 childHolder = new ChildHolder();
			 
			 childHolder.wlmc = (TextView) convertView.findViewById(R.id.djmx_wlmc);
			 childHolder.wlbm = (TextView) convertView.findViewById(R.id.djmx_wlbm);
			 childHolder.wltm = (TextView) convertView.findViewById(R.id.djmx_tmbh);
			 childHolder.wlzt = (TextView) convertView.findViewById(R.id.djmx_xz);
			 
			 
			 convertView.setTag(childHolder);
			 
		 } else {
			 childHolder = (ChildHolder) convertView.getTag();
		 }
			
			childHolder.wlmc.setText(mChildData.getWlName());
			childHolder.wlbm.setText(mChildData.getNewCode());
			childHolder.wltm.setText(mChildData.getWlBarCode());
			
			convertView.findViewById(R.id.djmx_sc).setVisibility(View.GONE);
			
			if(mChildData.getIsNewadd() !=null){
				childHolder.wlzt.setVisibility(View.VISIBLE);
				childHolder.wlzt.setText("新增");
			}else{
				childHolder.wlzt.setVisibility(View.GONE);
			}
			
		 	
		 return convertView;
	}
	
	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
	 return true;
	}

	
	private static class GroupHolder {
		public ImageView mHandImge;
		public TextView mHandText_top;
		public TextView mHandText_name;
		public TextView mHandText_rightOne;
		public TextView mHandText_rightTwo;
		public TextView mHandText_rightThree;
	}
	
	private static class ChildHolder {
		public TextView wlmc;
		public TextView wlbm;
		public TextView wltm;
		public TextView wlzt;
		
	}
}