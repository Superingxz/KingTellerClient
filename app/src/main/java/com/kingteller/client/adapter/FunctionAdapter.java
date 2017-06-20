package com.kingteller.client.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kingteller.R;
import com.kingteller.client.activity.account.MainActivity;
import com.kingteller.client.activity.offlineupload.OfflineUploadActivity;
import com.kingteller.client.config.KingTellerStaticConfig;
import com.kingteller.client.utils.KingTellerConfigUtils;
import com.kingteller.client.utils.KingTellerJudgeUtils;
import com.kingteller.client.view.CornerView;


public class FunctionAdapter extends BaseAdapter {
	private LayoutInflater inflater;
	private List<String> list = new ArrayList<String>();
	private Context mContext;
	private List<String> menulist = new ArrayList<String>();
	private int type;
	public static final int MAINMENU = 1;
	public static final int MAPMENU = 2;
	public static final int WLJKMENU = 3;
	public static final int ONLINEMENU = 4;
	public static final int EWMMENU = 5;

	/**
	 * @param context
	 * @param rightstr
	 * @param type
	 *  1主菜单，2地图菜单，3物流管理菜单，4在线学习菜单，5二维码扫描菜单          
	 */
	public FunctionAdapter(Context context, String rightstr, int type) {
		inflater = LayoutInflater.from(context);
		this.mContext = context;
		this.type = type;
		initData();
		setData(rightstr);
	
	}

	private void initData() {
		
		if (type == MAINMENU) {// 初始化主菜单
			menulist.add("MOBILE_JNKH");
			menulist.add("MOBILE_RWD");
			menulist.add("MOBILE_ZSK");
			menulist.add("MOBILE_SPBG");
			menulist.add("MOBILE_SJK");
			menulist.add("MOBILE_ATMWZ");
			menulist.add("MOBILE_MAP");
			menulist.add("notice");
//			menulist.add("saleshow");销售单查询
//			menulist.add("mysale");我的销售单
//			menulist.add("saleshen");销售单审核
//			menulist.add("MOBILE_PROJECTSTATISTIC");项目管理统计
//			menulist.add("MOBILE_PROJECTORDERAUDIT");项目工单审批
//			menulist.add("MOBILE_PROJECTDEALORDER");项目工单销单
			menulist.add("MOBILE_ASSIGN");
//			menulist.add("WLJK_MOBILE");物流监控
			menulist.add("MOBILE_PXKHSJ");
			menulist.add("MOBILE_ONLINE_LEARNING");
			menulist.add("MOBILE_EWM");
			menulist.add("MOBILE_KQ_FOR_MY");//我的考勤
			menulist.add("MOBILE_KQ_AUDIT");//考勤审批
			
		} else if (type == MAPMENU) {

			// 初始化地图菜单
			menulist.add("MOBILE_MAP_USER");
			menulist.add("MOBILE_MAP_FWZ");
			menulist.add("MOBILE_MAP_LOCATION");
			menulist.add("MOBILE_MAP_MACHINE");
			menulist.add("MOBILE_MAP_DF");
			menulist.add("MOBILE_MAP_SERVICE");
		}else if(type == WLJKMENU){
			
			//初始化物流管理
			menulist.add("WLJK_MOBILE_HDGL");
			menulist.add("WLJK_MOBILE_TXRWBG");
			menulist.add("WLJK_MOBILE_QTSWGL");
			menulist.add("WLJK_MOBILE_TXQTSWBG");
		}else if(type==ONLINEMENU){
			
			//初始化在线学习
			menulist.add("MOBILE_ONLINE_VIDEO");
			menulist.add("MOBILE_ONLINE_FILE");
			menulist.add("MOBILE_ONLINE_LEARNING_RECORDS");
		}else if(type==EWMMENU){
			
			//初始化二维码扫描
			menulist.add("MOBILE_EWM_LR");   
			menulist.add("MOBILE_EWM_FH_SM");
			menulist.add("MOBILE_EWM_SH_SM");
			menulist.add("MOBILE_EWM_BX_SM");
			menulist.add("MOBILE_EWM_REG_SM");
			menulist.add("MOBILE_EWM_WDJQ_SM");
			//menulist.add("MOBILE_EWM_WDJQGH_SM");
			menulist.add("MOBILE_EWM_WD_FH");
			menulist.add("MOBILE_EWM_ZS");
			
			menulist.add("MOBILE_EWM_WDJQGH_SM_LX");
			menulist.add("MOBILE_EWM_WDJQ_SM_LX");
		}
		
	}

	public void setData(String rightstr) {
		list = new ArrayList<String>();
		
		if (!KingTellerJudgeUtils.isEmpty(rightstr)) {
			{
				String[] allright = rightstr.split(",");
				for (int i = 0; i < allright.length; i++) {
					if (mContext.getResources().getIdentifier(allright[i],
							"string", mContext.getPackageName()) != 0 && menulist.contains(allright[i]))
						list.add(allright[i]);
				}
			}
		} 
		
		if(type == MAINMENU){//公共功能
			list.add("notice");//公告信息
			list.add("MOBILE_CACHE_DATA");//离线任务
		}
	}

	@Override
	public int getCount() {
		if (list == null)
			return 0;
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if(type == MAINMENU){
			ViewHoler_GridView viewHoler_gridview;
			if (convertView == null) {
				viewHoler_gridview = new ViewHoler_GridView();
				convertView = inflater.inflate(R.layout.item_function_gridview, null);
				viewHoler_gridview.item_icon = (ImageView) convertView.findViewById(R.id.item_icon);
				viewHoler_gridview.item_title = (TextView) convertView.findViewById(R.id.item_title);
				viewHoler_gridview.item_layout = (LinearLayout) convertView.findViewById(R.id.item_linearlayout);
				
				viewHoler_gridview.item_corner_view = new CornerView(mContext, viewHoler_gridview.item_layout);
				viewHoler_gridview.item_corner_view.setTextSize(12);
				viewHoler_gridview.item_corner_view.setBadgePosition(CornerView.POSITION_TOP_RIGHT);
				
				convertView.setTag(viewHoler_gridview);
			} else {
				viewHoler_gridview = (ViewHoler_GridView) convertView.getTag();
			}
			
			viewHoler_gridview.item_title.setText(mContext.getResources().getIdentifier(
					list.get(position), "string", mContext.getPackageName()));
	
			viewHoler_gridview.item_icon.setImageResource(mContext.getResources()
					.getIdentifier("icon_" + list.get(position).toLowerCase(),
							"drawable", mContext.getPackageName()));
			
			
			if("MOBILE_CACHE_DATA".equals(list.get(position))){

				if(KingTellerStaticConfig.FUNCTION_OFFLINE_BOTTOM_CORNER > 0){
					viewHoler_gridview.item_corner_view.setText(String.valueOf(KingTellerStaticConfig.FUNCTION_OFFLINE_BOTTOM_CORNER));
					viewHoler_gridview.item_corner_view.show();
					
					KingTellerConfigUtils.MainUpdateStatus(mContext,
							KingTellerStaticConfig.MAIN_FUNCTION,
							false, true);	
				}else{
					KingTellerConfigUtils.MainUpdateStatus(mContext,
							KingTellerStaticConfig.MAIN_FUNCTION,
							false, false);
					
					viewHoler_gridview.item_corner_view.hide();
				}
			}

		}else{
			
			ViewHoler_ListView viewHoler_listview;
			if (convertView == null) {
				viewHoler_listview = new ViewHoler_ListView();
				convertView = inflater.inflate(R.layout.item_function_listview, null);
				viewHoler_listview.item_icon_one = (ImageView) convertView.findViewById(R.id.item_icon_one);
				viewHoler_listview.item_icon_two = (ImageView) convertView.findViewById(R.id.item_icon_two);
				viewHoler_listview.item_title = (TextView) convertView.findViewById(R.id.item_title);

				convertView.setTag(viewHoler_listview);
			} else {
				viewHoler_listview = (ViewHoler_ListView) convertView.getTag();
			}
			
			viewHoler_listview.item_title.setText(mContext.getResources().getIdentifier(
					list.get(position), "string", mContext.getPackageName()));
	
			viewHoler_listview.item_icon_one.setImageResource(mContext.getResources()
					.getIdentifier("icon_" + list.get(position).toLowerCase(),
							"drawable", mContext.getPackageName()));
			
			viewHoler_listview.item_icon_two.setImageResource(mContext.getResources()
					.getIdentifier("ic_arrow_one_nomal","drawable", mContext.getPackageName()));
		}

		return convertView;
	}

	private static class ViewHoler_GridView {

		public TextView item_title;
		public ImageView item_icon;
		public LinearLayout item_layout;
		public CornerView item_corner_view;
	}
	
	private static class ViewHoler_ListView {

		public TextView item_title;
		public ImageView item_icon_one;
		public ImageView item_icon_two;
		public CornerView item_corner_view;
	}

}
