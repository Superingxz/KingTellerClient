package com.kingteller.client.activity.common;

import android.app.Activity;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import com.kingteller.R;
import com.kingteller.client.KingTellerApplication;

public class FloatActivity extends Activity {
	@Override
	public void onCreate(Bundle paramBundle) {
		super.onCreate(paramBundle);
		
		final Window win = getWindow();
		win.addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
				| WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
		win.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
				| WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
		
		final WindowManager.LayoutParams params = win.getAttributes();

		int screenWidth = win.getWindowManager().getDefaultDisplay().getWidth();
		int screenHeight = win.getWindowManager().getDefaultDisplay()
				.getHeight();
		int viewWidth = (int) screenWidth * 3 / 4;
		int viewHeight = (int) screenHeight / 2;

		params.x = screenWidth / 2 - viewWidth / 2;
		params.y = screenHeight / 3 - viewHeight / 3;
		params.format = PixelFormat.RGBA_8888;
		params.gravity = Gravity.LEFT | Gravity.TOP;
		params.width = viewWidth;
		params.height = viewHeight;
		
		setContentView(R.layout.layout_float_window);
		KingTellerApplication.addActivity(this);
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
//		final Window win = getWindow();
//		final WindowManager.LayoutParams params = win.getAttributes();
//
//		int screenWidth = win.getWindowManager().getDefaultDisplay().getWidth();
//		int screenHeight = win.getWindowManager().getDefaultDisplay()
//				.getHeight();
//		int viewWidth = (int) screenWidth * 3 / 4;
//		int viewHeight = (int) screenHeight / 2;
//
//		params.x = screenWidth / 2 - viewWidth / 2;
//		params.y = screenHeight / 3 - viewHeight / 3;
//		params.format = PixelFormat.RGBA_8888;
//		params.gravity = Gravity.LEFT | Gravity.TOP;
//		params.width = viewWidth;
//		params.height = viewHeight;
//		params.flags |= LayoutParams.FLAG_SHOW_WHEN_LOCKED;

	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		KingTellerApplication.removeActivity(this);
	}
}
