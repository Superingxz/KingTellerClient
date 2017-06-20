package com.kingteller.client.adapter;

import java.util.ArrayList;
import java.util.List;

import com.kingteller.R;
import com.kingteller.client.activity.qrcode.QRDotMachineReplaceCargoScanActivict;
import com.kingteller.client.bean.common.CommonSelectData;
import com.kingteller.client.bean.qrcode.QRCargoScanBean;
import com.kingteller.client.config.KingTellerStaticConfig;
import com.kingteller.client.view.dialog.ATMSearchConditionDialog.ATMMyDialogListener;
import com.kingteller.client.view.toast.T;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class QRDotMachineReplaceExpandableAdapter extends BaseExpandableListAdapter{
	
	private List<CommonSelectData> mList_Group = new ArrayList<CommonSelectData>();
	private List<List<QRCargoScanBean>> mList_Child = new ArrayList<List<QRCargoScanBean>>();
	private Context mContext;
	private LayoutInflater inflater;
	private int mType;//0:开启删除功能   1：关闭删除功能    2:开启删除功能 并且把 物料名与物料编码 隐藏
	public QRDotMachineReplaceExpandableAdapter(Context context, List<CommonSelectData> list_Group,
			List<List<QRCargoScanBean>> list_Child, int type) {
		inflater = LayoutInflater.from(context);
		this.mContext = context;
		this.mList_Group = list_Group;
		this.mList_Child = list_Child;
		this.mType = type;
		
	}
	
	public void setLists(List<CommonSelectData> list_Group ,List<List<QRCargoScanBean>> list_Child, int type) {
		this.mList_Group = list_Group;
		this.mList_Child = list_Child;
		this.mType = type;
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
	public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
		 GroupHolder groupHolder = null;
		 CommonSelectData mGroupData = (CommonSelectData) mList_Group.get(groupPosition);
		if (convertView == null) { 
			groupHolder = new GroupHolder();
			convertView =  inflater.inflate(R.layout.item_service_tree_group, null);
			groupHolder.mHandText_top = (TextView) convertView.findViewById(R.id.dj_tree_hand);
			groupHolder.mHandText_rightTwo = (TextView) convertView.findViewById(R.id.dj_tree_wltmzs);
			groupHolder.mHandImge = (ImageView) convertView.findViewById(R.id.dj_treenode_icon);
			
			convertView.setTag(groupHolder);
		  
		} else {
			groupHolder = (GroupHolder) convertView.getTag();
		}
			
		  	convertView.findViewById(R.id.RelativeLayout02).setVisibility(View.INVISIBLE);
			convertView.findViewById(R.id.RelativeLayout04).setVisibility(View.INVISIBLE);
			
			groupHolder.mHandText_top.setText(mGroupData.getText());
			groupHolder.mHandText_rightTwo.setText(mGroupData.getValue());

			//判断isExpanded就可以控制是按下还是关闭，同时更换图片
            if(isExpanded){
            	groupHolder.mHandImge.setBackgroundResource(R.drawable.ic_tree_ex);
            }else{
            	groupHolder.mHandImge.setBackgroundResource(R.drawable.ic_tree_ec);
            }
            
		return convertView;
	}

	@Override
	public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
 
		QRCargoScanBean mChildData = (QRCargoScanBean) mList_Child.get(groupPosition).get(childPosition);
		
		ChildHolder childHolder = null;
		if (convertView == null) {
			 convertView =  inflater.inflate(R.layout.item_service_tree_child, null);
			 childHolder = new ChildHolder();
			 
			 childHolder.wlmc = (TextView) convertView.findViewById(R.id.djmx_wlmc);
			 childHolder.wlbm = (TextView) convertView.findViewById(R.id.djmx_wlbm);
			 childHolder.wltm = (TextView) convertView.findViewById(R.id.djmx_tmbh);
			 childHolder.wlzt = (TextView) convertView.findViewById(R.id.djmx_xz);
			 childHolder.wlsc = (TextView) convertView.findViewById(R.id.djmx_sc);
			 
			 convertView.setTag(childHolder);
			 
		 } else {
			 childHolder = (ChildHolder) convertView.getTag();
		 }
		
			childHolder.wlzt.setVisibility(View.GONE);
			
			
			childHolder.wlmc.setText(mChildData.getWlName());
			childHolder.wlbm.setText(mChildData.getNewCode());
			childHolder.wltm.setText(mChildData.getWlBarCode());

			if(mType == 0){
				childHolder.wlsc.setVisibility(View.VISIBLE);
				childHolder.wlsc.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						mList_Child.get(groupPosition).remove(childPosition);
						mList_Group.get(groupPosition).setValue(Integer.toString(
								Integer.valueOf(mList_Group.get(groupPosition).getValue()).intValue() - 1));
						notifyDataSetChanged();
						
						KingTellerStaticConfig.QR_DOTMACHINEREPLACE_LISTDATA = true;
						T.showShort(mContext, "删除成功!");
					}
				});
			}else if (mType == 1){
				childHolder.wlsc.setVisibility(View.GONE);
			}else if (mType == 2){
				convertView.findViewById(R.id.qr_top_tmbm).setVisibility(View.GONE);
				convertView.findViewById(R.id.qr_top_tmmc).setVisibility(View.GONE);
				 
				childHolder.wlsc.setVisibility(View.VISIBLE);
				childHolder.wlsc.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						mList_Child.get(groupPosition).remove(childPosition);
						mList_Group.get(groupPosition).setValue(Integer.toString(
								Integer.valueOf(mList_Group.get(groupPosition).getValue()).intValue() - 1));
						notifyDataSetChanged();
						
						KingTellerStaticConfig.QR_DOTMACHINEREPLACE_OFFLINE_LISTDATA = true;
						T.showShort(mContext, "删除成功!");
					}
				});
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
		public TextView mHandText_rightTwo;
	}
	
	private static class ChildHolder {
		public TextView wlmc;
		public TextView wlbm;
		public TextView wltm;
		public TextView wlzt;
		public TextView wlsc;
		
	}

}
