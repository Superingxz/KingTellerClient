package com.kingteller.client.service;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

public class KingTellerPushService extends Service{
	
	private static final String TAG = "KingTellerPushService";
	private IBinder binder = new KingTellerPushService.LocalBinder();
	
	private Timer timerCheckauth = null;
	
	@Override
	public IBinder onBind(Intent intent) {
		return binder;
	}

	
	// 定义内容类继承Binder
	public class LocalBinder extends Binder {
		// 返回本地服务
		KingTellerPushService getService() {
			return KingTellerPushService.this;
		}
	}
	
	private TimerTask timerTask = new TimerTask() {
		public void run() {
			Log.e(TAG, "------------------！");
		}
	};
	
	@Override
	public void onCreate() {
		Log.e(TAG, "KingTellerPushService--onCreate");
		if (timerCheckauth == null)
			timerCheckauth = new Timer();
		
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		
		timerCheckauth.schedule(timerTask,//监听方法
				3 * 1000,//开始时 延迟时间
				5 * 1000);//执行间隔时间
		
		//START_STICKY:兼容模式service异常关闭后系统自动重启
		return super.onStartCommand(intent, START_STICKY, startId);
	}
	
	@Override
	public void onDestroy() {
		Log.e(TAG, "KingTellerPushService--onDestroy");
		if (timerCheckauth != null) {
			timerCheckauth.cancel();
			timerCheckauth = null;
		}
	}
}
