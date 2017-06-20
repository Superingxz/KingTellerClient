package com.kingteller.client.view.popupwindow;

import java.util.ArrayList;
import java.util.List;

import com.kingteller.client.adapter.WorkOrderAdapter.Callback;
import com.kingteller.client.config.KingTellerStaticConfig;
import com.kingteller.client.view.dialog.utils.CornerUtils;
import com.kingteller.client.view.popupwindow.adapter.WorkAttendanPopupListAdapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnKeyListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;


public class WorkAttendanPopupWindow extends PopupWindow {
	/**
	 * 菜单栏的整体布局LinearLayout
	 */
	private LinearLayout linearLayout;
	
	/**
	 * 菜单栏的子布局SubListView
	 */
	private ListView oneSubListView;
	
	
	/**
	 * 菜单栏分类标题ListView的适配
	 */
	private WorkAttendanPopupListAdapter listAdapter;
	
	private Context mContext;
	
	private List<String> textlists; 
	private List<Integer> imagelists; 
	/**
	 * 屏幕宽度
	 */
	private int screenWidth = 0;
	
	/**
	 * 屏幕高度
	 */
	private int screenHeight = 0;
	
	/**
	 * 监听事件
	 */
	private AttendanOnItemListener mAttendanListener;
	
	public WorkAttendanPopupWindow(final Context context, List<String> textlist, List<Integer> imagelist) {
		super(context);
		this.mContext = context; 
		this.textlists = textlist; 
		this.imagelists = imagelist; 
		
		/**
		 * 菜单栏的整体布局LinearLayout初始化
		 */
		linearLayout = new LinearLayout(context);
		linearLayout.setOrientation(LinearLayout.VERTICAL);//设置线性布局
		linearLayout.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		linearLayout.setBackgroundColor(Color.parseColor("#46aaf8"));
		//linearLayout.setPadding(10, 10, 10, 10);//left,top,right,bottom
		
		/**
		 * 获取屏幕宽度
		 */
		screenWidth = KingTellerStaticConfig.SCREEN.Width;
		screenHeight = KingTellerStaticConfig.SCREEN.Height;
		
		
		/**
		 * 菜单栏的第一个子布局SublinearLayout初始化
		 */
		oneSubListView = new ListView(context);
		oneSubListView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		listAdapter = new WorkAttendanPopupListAdapter(context, textlists, imagelists, 0);
		oneSubListView.setSelector(new ColorDrawable(Color.TRANSPARENT));
		oneSubListView.setAdapter(listAdapter);
		oneSubListView.setDivider(new ColorDrawable(Color.parseColor("#6bbbf9")));
		oneSubListView.setDividerHeight(1);
		/**
		 * 选择分类标题时的响应事件
		 */
		oneSubListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				if(mAttendanListener != null){
					mAttendanListener.onItem(position);
				}
			
			}
		});
		

		/**
		 * 把个子布局加入到整体布局中去
		 */
		linearLayout.addView(oneSubListView);
		
		this.setContentView(linearLayout);
		this.setWidth(screenWidth * 3/8);
		this.setHeight(LayoutParams.WRAP_CONTENT);
		//this.setBackgroundDrawable();//若背景为Null 点击其他地方不会退出popup
		/**
		 * 以下代码是为了解决，菜单栏出现后，不能响应再次按menu按键使菜单栏消失的问题
		 * 在这个网址找到的答案http://blog.csdn.net/admin_/article/details/7278402 可以自己去看
		 */
		this.setFocusable(true);
		linearLayout.setFocusableInTouchMode(true);
		linearLayout.setOnKeyListener(new OnKeyListener() {
			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				if ((keyCode == KeyEvent.KEYCODE_MENU) && (WorkAttendanPopupWindow.this.isShowing())) {
					WorkAttendanPopupWindow.this.dismiss();

					return true;
				}
				return false;
			}
		});
	}
	
	public void setAttendanOnItemListener(AttendanOnItemListener Listener) {
		this.mAttendanListener = Listener;
	}

	public interface AttendanOnItemListener{
		public void onItem(int num);
	}

}
