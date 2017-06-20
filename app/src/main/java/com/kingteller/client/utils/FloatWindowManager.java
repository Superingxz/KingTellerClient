package com.kingteller.client.utils;

import com.kingteller.client.view.FloatWindowView;

import android.content.Context;
import android.graphics.PixelFormat;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class FloatWindowManager {

	/**
	 * 大悬浮窗View的实例
	 */
	private static FloatWindowView floatWindow;

	/**
	 * 大悬浮窗View的参数
	 */
	private static LayoutParams floatWindowParams;

	/**
	 * 用于控制在屏幕上添加或移除悬浮窗
	 */
	private static WindowManager mWindowManager;

	/**
	 * 创建一个大悬浮窗。位置为屏幕正中间。
	 * 
	 * @param context
	 *            必须为应用程序的Context.
	 */
	public static void createFloatWindow(Context context) {
		WindowManager windowManager = getWindowManager(context);
		int screenWidth = windowManager.getDefaultDisplay().getWidth();
		int screenHeight = windowManager.getDefaultDisplay().getHeight();
		int viewWidth = (int) screenWidth * 3 / 4;
		int viewHeight = (int) screenHeight / 2;
		if (floatWindow == null) {
			floatWindow = new FloatWindowView(context);
			if (floatWindowParams == null) {
				floatWindowParams = new LayoutParams();
				floatWindowParams.x = screenWidth / 2 - viewWidth / 2;
				floatWindowParams.y = screenHeight / 3 - viewHeight / 3;
				floatWindowParams.type = LayoutParams.TYPE_SYSTEM_ERROR;
				floatWindowParams.format = PixelFormat.RGBA_8888;
				floatWindowParams.gravity = Gravity.LEFT | Gravity.TOP;
				floatWindowParams.width = viewWidth;
				floatWindowParams.height = viewHeight;
				floatWindowParams.flags = LayoutParams.FLAG_NOT_FOCUSABLE
						| LayoutParams.FLAG_SHOW_WHEN_LOCKED;

			}
			windowManager.addView(floatWindow, floatWindowParams);
		}
	}

	/**
	 * 将大悬浮窗从屏幕上移除。
	 * 
	 * @param context
	 *            必须为应用程序的Context.
	 */
	public static void removeFloatWindow(Context context) {
		if (floatWindow != null) {
			WindowManager windowManager = getWindowManager(context);
			windowManager.removeView(floatWindow);
			floatWindow = null;
		}
	}

	/**
	 * 是否有悬浮窗(包括小悬浮窗和大悬浮窗)显示在屏幕上。
	 * 
	 * @return 有悬浮窗显示在桌面上返回true，没有的话返回false。
	 */
	public static boolean isWindowShowing() {
		return floatWindow != null;
	}

	/**
	 * 如果WindowManager还未创建，则创建一个新的WindowManager返回。否则返回当前已创建的WindowManager。
	 * 
	 * @param context
	 *            必须为应用程序的Context.
	 * @return WindowManager的实例，用于控制在屏幕上添加或移除悬浮窗。
	 */
	private static WindowManager getWindowManager(Context context) {
		if (mWindowManager == null) {
			mWindowManager = (WindowManager) context
					.getSystemService(Context.WINDOW_SERVICE);
		}
		return mWindowManager;
	}

	/**
	 * 更新小悬浮窗的TextView上的数据，显示内存使用的百分比。
	 * 
	 * @param context
	 *            可传入应用程序上下文。
	 */
	public static void updateFloat(Context context) {
		if (floatWindow != null) {
			floatWindow.getWaitDos();
		}
	}
	
	/**
	 * 计算和设置ListView的高度
	 * @param listView
	 */
	public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter(); 
        if (listAdapter == null) {
            return;
        }

        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }
}
