package com.kingteller.client.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.Thread.UncaughtExceptionHandler;
import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build;
import android.os.Environment;
import android.os.Looper;
import android.util.Log;
import cn.jpush.android.api.JPushInterface;

import com.kingteller.R;
import com.kingteller.client.KingTellerApplication;
import com.kingteller.client.bean.account.User;
import com.kingteller.client.bean.common.BaseBean;
import com.kingteller.client.bean.map.AddressBean;
import com.kingteller.client.config.KingTellerStaticConfig;
import com.kingteller.client.config.KingTellerUrlConfig;
import com.kingteller.client.netauth.AjaxHttpCallBack;
import com.kingteller.client.netauth.KTHttpClient;
import com.kingteller.client.service.KingTellerService;
import com.kingteller.client.view.dialog.GlobalCueDialog;
import com.kingteller.client.view.dialog.GlobalCueDialog.OnBtnClickL;
import com.kingteller.client.view.toast.T;
import com.kingteller.framework.http.AjaxParams;

/**
 * UncaughtException处理类,当程序发生Uncaught异常的时候,有该类来接管程序,并记录发送错误报告.
 * 
 * @author user
 * 
 */
public class CrashHandler implements UncaughtExceptionHandler {

	public static final String TAG = "CrashHandler";

	// 系统默认的UncaughtException处理类
	private Thread.UncaughtExceptionHandler mDefaultHandler;
	// CrashHandler实例
	private static CrashHandler INSTANCE = new CrashHandler();
	// 程序的Context对象
	private Context mContext;
	// 用来存储设备信息和异常信息
	private Map<String, String> infos = new HashMap<String, String>();

	// 用于格式化日期,作为日志文件名的一部分
	private DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
	
	private String ex_str;
	
	/** 保证只有一个CrashHandler实例 */
	private CrashHandler() {
		
	}

	/** 获取CrashHandler实例 ,单例模式 */
	public static CrashHandler getInstance() {
		return INSTANCE;
	}
    
	/**
	 * 初始化
	 * @param context
	 */
	public void init(Context context) {
		mContext = context;
		// 获取系统默认的UncaughtException处理器
		mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
		// 设置该CrashHandler为程序的默认处理器
		Thread.setDefaultUncaughtExceptionHandler(this);
	}

	/**
	 * 所有未处理的异常发生时，都会到这来 UncaughtException发生时会转入该函数来处理
	 */
	@Override
	public void uncaughtException(Thread thread, Throwable ex) {
		if (!handleException(ex) && mDefaultHandler != null) {
			// 如果用户没有处理则让系统默认的异常处理器来处理
			mDefaultHandler.uncaughtException(thread, ex);
		} else {
            new Thread() {
	            @Override  
	            public void run() {
	                Looper.prepare();
	                
//	                T.showLongSetG(KingTellerApplication.currentActivity(), "很抱歉, 程序出现异常, 请联系管理员...");

	        		//弹出提示框
	                final GlobalCueDialog dialog_ex = new GlobalCueDialog(KingTellerApplication.currentActivity(), R.style.Login_dialog,
	                		"程序出现异常", ex_str);
	                dialog_ex.setOnBtnClickL(new OnBtnClickL() {
						@Override
						public void OnBtnClick() {
							dialog_ex.dismiss();
							
							//上传日志 到 反馈意见
							KTHttpClient fh = new KTHttpClient(true);
							AjaxParams params = new AjaxParams();
							params.put("content", ex_str);
							fh.post(KingTellerConfigUtils.CreateUrl(mContext, KingTellerUrlConfig.SubmitFeedBackUrl),
									params, new AjaxHttpCallBack<BaseBean>(mContext, true) {
										@Override
										public void onDo(BaseBean data) {
											if ("1".equals(data.getStatus())) {
												Log.e(TAG, "上传错误日志 成功!");
											}else{
												Log.e(TAG, "上传错误日志 失败!");
											}
										}
									});
							
							// 退出程序
							JPushInterface.stopPush(mContext);
							mContext.stopService(new Intent(mContext, KingTellerService.class));
				            KingTellerApplication.finishActivity();
						}
					});
	                dialog_ex.show();
	                
	                Looper.loop(); 
	            }  
	        }.start();  
		}
	}

	/**
	 * 自定义错误处理,收集错误信息 发送错误报告等操作均在此完成.
	 * 
	 * @param ex
	 * @return true:如果处理了该异常信息;否则返回false.
	 */
	private boolean handleException(Throwable ex) {
		if (ex == null) {
			return false;
		}
//		// 使用Toast来显示异常信息
//		new Thread() {
//			@Override
//			public void run() {
//				Looper.prepare();
//				T.showLong(KingTellerApplication.currentActivity(), "很抱歉, 程序出现异常, 错误信息已复制到粘贴板, 正在退出程序!");
//				Looper.loop();
//			}
//		}.start();
        
		// 收集设备参数信息
		collectDeviceInfo(mContext);
		// 保存日志文件
		saveCrashInfoFile(ex);
		
		return true;
	}

	/**
	 * 收集设备参数信息
	 * 
	 * @param ctx
	 */
	public void collectDeviceInfo(Context ctx) {
		try {
			PackageManager pm = ctx.getPackageManager();
			PackageInfo pi = pm.getPackageInfo(ctx.getPackageName(),
					PackageManager.GET_ACTIVITIES);
			if (pi != null) {
				String versionName = pi.versionName == null ? "null"
						: pi.versionName;
				String versionCode = pi.versionCode + "";
				infos.put("versionName", versionName);
				infos.put("versionCode", versionCode);
			}
		} catch (NameNotFoundException e) {
			Log.e(TAG, "an error occured when collect package info", e);
		}
		Field[] fields = Build.class.getDeclaredFields();
		for (Field field : fields) {
			try {
				field.setAccessible(true);
				infos.put(field.getName(), field.get(null).toString());
				Log.d(TAG, field.getName() + " : " + field.get(null));
			} catch (Exception e) {
				Log.e(TAG, "an error occured when collect crash info", e);
			}
		}
	}

	/**
	 * 保存错误信息到文件中
	 * 
	 * @param ex
	 * @return 返回文件名称,便于将文件传送到服务器  crash.log
	 *
	 */
	private void saveCrashInfoFile(Throwable ex) {

		StringBuffer sb = new StringBuffer();
		for (Map.Entry<String, String> entry : infos.entrySet()) {
			String key = entry.getKey();
			String value = entry.getValue();
			sb.append(key + "=" + value + "\n");
		}

		Writer writer = new StringWriter();
		PrintWriter printWriter = new PrintWriter(writer);
		ex.printStackTrace(printWriter);
		Throwable cause = ex.getCause();
		while (cause != null) {
			cause.printStackTrace(printWriter);
			cause = cause.getCause();
		}
		printWriter.close();
		String result = writer.toString();
		sb.append(result);
		sb.append("\n\n");
		
		ex_str = sb.toString();
		
		String time = KingTellerTimeUtils.getConversionFormatStringByDate(new Date(), 2);
		String fileName = "crash-" + time + ".txt";
		
		try {
			if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
				FileOutputStream fos = new FileOutputStream(KingTellerStaticConfig.CACHE_PATH.SD_LOG + "/" + fileName, true);
				fos.write(sb.toString().getBytes());
				fos.close();
			}
		} catch (Exception e) {
			Log.e(TAG, "an error occured while writing file...", e);
		}

	}
	
	/**
	 * 用户登陆信息写入LogInRecord.txt
	 * 
	 */
	public static void saveStaffLogInTextFile(String msg, User user, String ipAddress, String port, AddressBean address, int num){
		String Today = KingTellerTimeUtils.getConversionFormatStringByDate(new Date(), 2);
		String newfileName = "staffLogInRecord-" +Today + ".txt";
		
		StringBuffer sb = new StringBuffer();
		switch (num) {
			case 0:
				//时间 ，登陆状态，登陆账号，用户名，服务器IP，端口，手机IMEI码,版本号
				sb.append(KingTellerTimeUtils.getConversionFormatStringByDate(new Date(), 0) + "："
						+ msg + " , "
						+ user.getUserName() + " , "
						+ user.getName() + " , "
						+ ipAddress + " , "
						+ port + " , "
						+ KingTellerApplication.getDeviceToken() + " , "
						+ user.getVersionName() + "\n");
				break;
			case 1:
				//时间，用户信息
				sb.append(KingTellerTimeUtils.getConversionFormatStringByDate(new Date(), 0) + "："
						+ msg + "\n");
				break;
			case 2:
				//时间，用户 定位上传情况
				sb.append(KingTellerTimeUtils.getConversionFormatStringByDate(new Date(), 0) + "："
						+ msg + " , "
						+ address.getAddress() + "\n");
				break;
			default:
				sb.append("日志错误!");
				break;
		}
		try {
			if (Environment.getExternalStorageState().equals(
					Environment.MEDIA_MOUNTED)) {
				FileOutputStream fos = new FileOutputStream(KingTellerStaticConfig.CACHE_PATH.SD_LOG + "/" + newfileName,true);
				fos.write(sb.toString().getBytes());
				fos.close();
			}
		} catch (Exception e) {
			Log.e(TAG, "an error occured while writing file...", e);
		}
	}
	
	
}
