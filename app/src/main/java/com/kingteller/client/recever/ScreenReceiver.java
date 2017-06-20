package com.kingteller.client.recever;

import com.kingteller.client.utils.FloatWindowManager;
import com.kingteller.client.utils.KingTellerConfigUtils;
import com.kingteller.framework.utils.Logger;

import android.app.KeyguardManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;

/**
 * 锁屏广播接收器
 * @author 王定波
 *
 */
public class ScreenReceiver extends BroadcastReceiver {

	/**
	 * 用于在线程中创建或移除悬浮窗。
	 */
	private Handler handler = new Handler();

	@Override
	public void onReceive(final Context context, Intent intent) {
		// TODO Auto-generated method stub
		String action = intent.getAction();
		if (Intent.ACTION_SCREEN_ON.equals(action) && isLockScreen(context) && 
				KingTellerConfigUtils.getConfigMeta(KingTellerConfigUtils.KEY_PUSH_LOCK, true)) {
			// 开屏

			if (!FloatWindowManager.isWindowShowing())
				handler.post(new Runnable() {
					@Override
					public void run() {
						FloatWindowManager.createFloatWindow(context);
					}
				});
			Log.e("锁屏广播接收器",  "--开屏--");  
		} else if (Intent.ACTION_USER_PRESENT.equals(action)) {
			// 解锁

			if (FloatWindowManager.isWindowShowing())
				handler.post(new Runnable() {
					@Override
					public void run() {
						FloatWindowManager.removeFloatWindow(context);
					}
				});
			Log.e("锁屏广播接收器",  "--解锁--");  
		} else if (Intent.ACTION_SCREEN_OFF.equals(action)) {
			// 锁屏
			if (!FloatWindowManager.isWindowShowing() && isLockScreen(context) && 
					KingTellerConfigUtils.getConfigMeta(KingTellerConfigUtils.KEY_PUSH_LOCK, true))
				handler.post(new Runnable() {
					@Override
					public void run() {
						FloatWindowManager.createFloatWindow(context);
					}
				});
			Log.e("锁屏广播接收器",  "--锁屏--");  
		}
	}

	/**
	 * 判断是否锁屏界面
	 * 
	 * @return
	 */
	public boolean isLockScreen(Context context) {
		KeyguardManager mKeyguardManager = (KeyguardManager) context.getSystemService(Context.KEYGUARD_SERVICE);

		if (mKeyguardManager.inKeyguardRestrictedInputMode()) {
			return true;
		}
		return false;
	}

}
