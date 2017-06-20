package com.kingteller.client.service;


import java.util.Timer;
import java.util.TimerTask;

import android.app.KeyguardManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.Location;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.location.LocationManagerProxy;
import com.amap.api.location.LocationProviderProxy;
import com.google.gson.Gson;
import com.kingteller.client.KingTellerApplication;
import com.kingteller.client.activity.account.MainActivity;
import com.kingteller.client.bean.account.User;
import com.kingteller.client.bean.map.AddressBean;
import com.kingteller.client.bean.map.UploadLocation;
import com.kingteller.client.config.KingTellerStaticConfig;
import com.kingteller.client.config.KingTellerUrlConfig;
import com.kingteller.client.netauth.AjaxHttpCallBack;
import com.kingteller.client.netauth.KTHttpClient;
import com.kingteller.client.recever.ScreenReceiver;
import com.kingteller.client.utils.CrashHandler;
import com.kingteller.client.utils.FloatWindowManager;
import com.kingteller.client.utils.KingTellerConfigUtils;
import com.kingteller.client.utils.KingTellerJudgeUtils;
import com.kingteller.framework.http.AjaxParams;
import com.kingteller.framework.utils.Logger;

/**
 * 全局服务上传位置、检查是否登陆等操作
 * 
 * @author 王定波
 * 
 */
public class KingTellerService extends Service {

	private static final String TAG = "KingTellerService";
	private IBinder binder = new KingTellerService.LocalBinder();
	private KTHttpClient fh;
	private User user;
	private Gson gson;
	private LocationManagerProxy aMapLocManager;
	private WakeLock wakeLock = null;
	private int MumNetwork_ON;
	private int MumNetwork_OFF;
	
	/**
	 * Handler来实现UI线程的更新
	 * 用于创建或移除悬浮窗
	 */
	private Handler handler = new Handler();

	private AMapLocationListener staffListener = new AMapLocationListener() {
		/**
		 * 定位回调函数
		 */
		@Override
		public void onLocationChanged(AMapLocation location) {

			if (location != null && location.getAMapException().getErrorCode() == 0) {
				String desc = "";
				Bundle locBundle = location.getExtras();
				if (locBundle != null) {
					desc = locBundle.getString("desc");
				}
				AddressBean address = new AddressBean();
				address.setLat(location.getLatitude());
				address.setLng(location.getLongitude());
				address.setName(desc);
				address.setAddress(desc);
				address.setCity(location.getCity());

				KingTellerApplication.getApplication().setCurAddress(address);
				
				//Log.e(TAG, "获取定位信息成功！");
				
				
				//上传位置信息
				UploadLocation(address);
				
			} else {
				
				String erro = location.getAMapException().getErrorMessage();
				
				/*写获取定位信息成功---调试代码 */
				CrashHandler.saveStaffLogInTextFile("没有获取定位信息!:" + erro, null,
						null, null, null, 1);
				
				Log.e(TAG, "没有获取定位：" + erro);
			}

		}

		@Override
		public void onStatusChanged(String arg0, int arg1, Bundle arg2) {

		}

		@Override
		public void onProviderEnabled(String arg0) {

		}

		@Override
		public void onProviderDisabled(String arg0) {

		}

		@Override
		public void onLocationChanged(Location arg0) {

		}
	};
	
	/**
	 * 监听电话状态的Listener
	 */
	private PhoneStateListener phonelistener = new PhoneStateListener() {
		public void onCallStateChanged(int state, String incomingNumber) {
			switch (state) {
			case TelephonyManager.CALL_STATE_RINGING:// 来电状态
				if (FloatWindowManager.isWindowShowing())
					handler.post(new Runnable() {
						@Override
						public void run() {
							FloatWindowManager
									.removeFloatWindow(getApplicationContext());
						}
					});
				break;
			case TelephonyManager.CALL_STATE_OFFHOOK:// 接听状态
				if (FloatWindowManager.isWindowShowing())
					handler.post(new Runnable() {
						@Override
						public void run() {
							FloatWindowManager
									.removeFloatWindow(getApplicationContext());
						}
					});
				break;
			case TelephonyManager.CALL_STATE_IDLE:// 挂断后回到空闲状态
				if (!FloatWindowManager.isWindowShowing()
						&& isLockScreen()
						&& KingTellerConfigUtils.getConfigMeta(KingTellerConfigUtils.KEY_PUSH_LOCK, true))
					handler.post(new Runnable() {
						@Override
						public void run() {
							FloatWindowManager
									.createFloatWindow(getApplicationContext());
						}
					});
				break;
			default:
				break;
			}
		};
	};

	/**
	 * 检查是否登陆有效
	 */
	private TimerTask timerTask = new TimerTask() {
		public void run() {
			KingTellerApplication.CheckAuthSession(KingTellerService.this);
		}
	};
	
	/**
	 * 检查是网络是否正常
	 */
	private TimerTask timerTaskNetwork = new TimerTask() {
		public void run() {
			if (!KingTellerJudgeUtils.isNetAvaliable(KingTellerService.this)){
				if(MumNetwork_OFF == 0){
					MumNetwork_OFF++;
					/*写入网络不可用---调试代码 */
					CrashHandler.saveStaffLogInTextFile("当前网络不可用!", null,
							null, null, null, 1);
				}
				MumNetwork_ON = 0;
				Log.e(TAG, "当前网络不可用！");
			}else{	
				if(MumNetwork_ON == 0){
					MumNetwork_ON++;
					/*写入网络不可用---调试代码 */
					CrashHandler.saveStaffLogInTextFile("当前网络正常!", null,
							null, null, null, 1);
				}
				MumNetwork_OFF = 0;
				Log.e(TAG, "当前网络正常！");
			}
		}
	};
	
	/**
	 * 定时上传地址
	 */
	private TimerTask timerTaskUpdatedz = new TimerTask() {
		public void run() {
			/*provider：有三种定位Provider供用户选择，分别是:LocationManagerProxy.GPS_PROVIDER，代表使用手机GPS定位；LocationManagerProxy.NETWORK_PROVIDER，代表使用手机网络定位；LocationProviderProxy.AMapNetwork，代表高德网络定位服务，混合定位。
			 *minTime：位置变化的通知时间，单位为毫秒。如果为-1，定位只定位一次。时间最短是2000毫秒
			 *minDistance:位置变化通知距离，单位为米。
			 *listener:定位监听者
			 */
			aMapLocManager.removeUpdates(staffListener);
			aMapLocManager.requestLocationData(LocationProviderProxy.AMapNetwork, -1, 0, staffListener);
		}
	};
	
	private Timer timerCheckauth = null;
	private Timer timerCheckNetwork = null;
	private Timer timerCheckUpdatedz = null;
	private ScreenReceiver mReceiver = null;
	@Override
	public IBinder onBind(Intent intent) {

		return binder;
	}
	
	// 定义内容类继承Binder
	public class LocalBinder extends Binder {
		// 返回本地服务
		KingTellerService getService() {
			return KingTellerService.this;
		}
	}

	@Override
	public void onCreate() {
		Log.e(TAG, "KingTellerService--onCreate");
		
		/*写service服务状态---调试代码 */
		CrashHandler.saveStaffLogInTextFile("service服务已经启动!", null,
				null, null, null, 1);
		
		MumNetwork_ON = 0;
		MumNetwork_OFF = 0;
		user = User.getInfo(this);
		gson = new Gson();

		//初始化定位
		aMapLocManager = LocationManagerProxy.getInstance(this);
		aMapLocManager.setGpsEnable(true);
		
		acquireWakeLock();

		if (timerCheckauth == null)
			timerCheckauth = new Timer();
		
		if (timerCheckNetwork == null)
			timerCheckNetwork = new Timer();
		
		if (timerCheckUpdatedz == null)
			timerCheckUpdatedz = new Timer();
		
		/**
		 * 注册锁屏广播接收器
		 */
		IntentFilter filter = new IntentFilter(Intent.ACTION_SCREEN_ON);
		filter.addAction(Intent.ACTION_SCREEN_OFF);
		filter.addAction(Intent.ACTION_USER_PRESENT);
		mReceiver = new ScreenReceiver();
		registerReceiver(mReceiver, filter);
		
		/**
		 * 注册电话监听事件
		 */
		TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
		telephonyManager.listen(phonelistener,PhoneStateListener.LISTEN_CALL_STATE);
		
		/**
		 * 设置重复闹钟
		 */
		//setClock();
        
		super.onCreate();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		try {

			timerCheckauth.schedule(timerTask,//监听方法
					KingTellerStaticConfig.SERVICE_LOCATION_TIME * 60 * 1000,//开始时 延迟时间
					KingTellerStaticConfig.SERVICE_LOCATION_TIME * 60 * 1000);//执行间隔时间

			timerCheckNetwork.schedule(timerTaskNetwork,//监听方法
					KingTellerStaticConfig.SERVICE_LOCATION_TIME * 1000,//开始时 延迟时间
					KingTellerStaticConfig.SERVICE_LOCATION_TIME * 60 * 1000);//执行间隔时间
			
			timerCheckUpdatedz.schedule(timerTaskUpdatedz,//监听方法
					KingTellerStaticConfig.SERVICE_LOCATION_TIME * 1000,//开始时 延迟时间
					KingTellerStaticConfig.SERVICE_LOCATION_TIME * 60 * 1000);//执行间隔时间
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		//START_STICKY:兼容模式service异常关闭后系统自动重启
		return super.onStartCommand(intent, START_STICKY, startId);
	}

	/**
	 * 停止定位
	 */
	@Override
	public void onDestroy() {
		Log.e(TAG, "KingTellerService--onDestroy");
		
		/*写service服务状态---调试代码 */
		CrashHandler.saveStaffLogInTextFile("service服务已经停止!", null,
				null, null, null, 1);
		
		super.onDestroy();
		if (aMapLocManager != null) {
			aMapLocManager.removeUpdates(staffListener);
			aMapLocManager.destroy();
		}
		aMapLocManager = null;
		
		if (timerCheckauth != null) {
			timerCheckauth.cancel();
			timerCheckauth = null;
		}
		
		if (timerCheckNetwork != null) {
			timerCheckNetwork.cancel();
			timerCheckNetwork = null;
		}
		
		if (timerCheckUpdatedz != null) {
			timerCheckUpdatedz.cancel();
			timerCheckUpdatedz = null;
		}
		
		if (mReceiver != null) {
			unregisterReceiver(mReceiver);
			mReceiver = null;
		}
		
		releaseWakeLock();
		
		//cancelClock();
	}

	

	/**
	 * 上传当前经纬度
	 */
	private void UploadLocation(final AddressBean address) {
		if (KingTellerApplication.getApplication().IsLogin()) {
			fh = new KTHttpClient(true);
			user = User.getInfo(this);
			UploadLocation location = new UploadLocation();
			location.setImeiCode(KingTellerApplication.getDeviceToken());
			location.setUserAccount(user.getUserName());
			location.setLatidu(String.valueOf(address.getLat()));
			location.setLongtidu(String.valueOf(address.getLng()));
			AjaxParams params = new AjaxParams();
			params.put("longlati", gson.toJson(location));

			Logger.e(TAG, gson.toJson(location));
			//String a = ConfigUtils.CreateUrl(this, URLConfig.UploadLocationUrl);
			
			fh.post(KingTellerConfigUtils.CreateUrl(this, KingTellerUrlConfig.UploadLocationUrl),
					params, new AjaxHttpCallBack<String>(this, false) {
				
						@Override
						public void onDoString(String data) {
							//判断是否含有yes
							if(data.contains("yes")){
								
								/*写获取定位信息成功---调试代码 */
								CrashHandler.saveStaffLogInTextFile("定位信息上传成功!", null,
										null, null, address, 2);

								Log.e(TAG, "定位信息上传成功！");
							}else{
								/*写获取定位信息成功---调试代码 */
								CrashHandler.saveStaffLogInTextFile("定位信息上传失败!", null,
										null, null, address, 2);
								
								Log.e(TAG, "定位信息上传失败！" + data);
							}
						}
						
						public void onError(int errorNo, String strMsg) {
							Log.e(TAG, "获取定位信息成功！发生错误!定位信息上传失败！" + strMsg);
							
						}
						
					});
		}else{
			/*记录登陆信息 --- 调试代码*/
			CrashHandler.saveStaffLogInTextFile("获取定位信息成功! 登陆失效, 定位信息无法上传!", null,
					null, null, address, 2);
		}

	}

	/**
	 * 判断是否锁屏界面
	 * 
	 * @return
	 */
	public boolean isLockScreen() {
		KeyguardManager mKeyguardManager = (KeyguardManager) getSystemService(KEYGUARD_SERVICE);

		if (mKeyguardManager.inKeyguardRestrictedInputMode()) {
			return true;
		}
		return false;
	}
	
	/**
	 * 唤醒设备电源锁
	 */
	private void acquireWakeLock() {
		if (null == wakeLock) {
			PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
			wakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK
					| PowerManager.ON_AFTER_RELEASE, getClass()
					.getCanonicalName());
			if (null != wakeLock) {
				Log.i(TAG, "call acquireWakeLock");
				wakeLock.acquire();
			}
		}
	}
	
	/**
	 * 释放设备电源锁
	 */
	private void releaseWakeLock() {
		if (null != wakeLock && wakeLock.isHeld()) {
			Log.i(TAG, "call releaseWakeLock");
			wakeLock.release();
			wakeLock = null;
		}
	}
	
//	/**
//	 * 设置周期执行clock
//	 */
//	private void setClock() {
//		Intent intent = new Intent(this,ChangTimeReceiver.class);
//		intent.setAction(KingTellerServiceClock);
//        PendingIntent sender = PendingIntent.getBroadcast(this, 0, intent, 0);
//
//        //设定一个10秒后的时间
//        Calendar calendar = Calendar.getInstance();
//        calendar.setTimeInMillis(System.currentTimeMillis());
//        calendar.add(Calendar.SECOND, 10);
//        
//        //开始时间 -从开机到现在的毫秒书（手机睡眠(sleep)的时间也包括在内）  
//        long firstime = SystemClock.elapsedRealtime();
//        AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);
//        am.setRepeating(AlarmManager.RTC_WAKEUP,firstime, 10 * 1000, sender);
//        Log.e("ACTION_TIME_TICK","--开始周期执行clock--");          
//	}
//
//	/**
//	 * 取消周期执行clock
//	 */
//	private void cancelClock() {
//		 Intent intent = new Intent(this, ChangTimeReceiver.class);
//		 intent.setAction(KingTellerServiceClock);
//		 PendingIntent sender = PendingIntent.getBroadcast(this, 0, intent, 0);
//		 AlarmManager alarm = (AlarmManager)getSystemService(ALARM_SERVICE);
//		 alarm.cancel(sender);
//		 Log.e("ACTION_TIME_TICK","--结束周期执行clock--");
//	}
}
