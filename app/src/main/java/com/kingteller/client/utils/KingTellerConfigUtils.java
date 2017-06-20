package com.kingteller.client.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.Uri;
import android.view.WindowManager;

import com.kingteller.R;
import com.kingteller.client.KingTellerApplication;
import com.kingteller.client.config.KingTellerStaticConfig;
import com.kingteller.client.config.KingTellerUrlConfig;


/**
 * 配置工具类
 * Created by Administrator on 16-1-22.
 */

public class KingTellerConfigUtils {

    private final static String IPDOMAIN = "ipdomain";
    private final static String PORT = "port";

    public final static String KEY_PUSH_SY = "push_sy";
    public final static String KEY_PUSH_ZD = "push_zd";
    public final static String KEY_PUSH_LOCK = "push_lock";
    public final static String KEY_APP_NEW = "app_new";

    /**
     * 设置config的值
     */
    public static void setConfigMeta(String key, boolean value) {
        Editor sharedata = KingTellerApplication.getApplication().getSharedPreferences(KingTellerStaticConfig.SHARED_PREFERENCES.CONFIG,
                Context.MODE_APPEND).edit();
        sharedata.putBoolean(key, value);
        sharedata.commit();
    }

    /**
     * 得到config的值
     */
    public static boolean getConfigMeta(String key, boolean defaultdata) {
        return KingTellerApplication.getApplication().getSharedPreferences(KingTellerStaticConfig.SHARED_PREFERENCES.CONFIG,
                Context.MODE_APPEND).getBoolean(key, defaultdata);
    }

    /**
     * 设置域名
     */
    public static void setIpDomain(Context context, String IpDomain) {
        Editor sharedata = context.getSharedPreferences(KingTellerStaticConfig.SHARED_PREFERENCES.CONFIG,
                Context.MODE_APPEND).edit();
        sharedata.putString(IPDOMAIN, IpDomain);
        sharedata.commit();
    }



    /**
     * 得到域名
     */
    public static String getIpDomain(Context context) {
        SharedPreferences sp = context.getSharedPreferences(KingTellerStaticConfig.SHARED_PREFERENCES.CONFIG,
                Context.MODE_APPEND);
        return sp.getString(IPDOMAIN, KingTellerUrlConfig.DefalutIp);
    }


    /**
     * 设置端口
     */
    public static void setPort(Context context, String port) {
        Editor sharedata = context.getSharedPreferences(KingTellerStaticConfig.SHARED_PREFERENCES.CONFIG,
                Context.MODE_APPEND).edit();
        sharedata.putString(PORT, port);
        sharedata.commit();
    }

    /**
     * 得到端口
     */
    public static String getPort(Context context) {
        SharedPreferences sp = context.getSharedPreferences(KingTellerStaticConfig.SHARED_PREFERENCES.CONFIG,
                Context.MODE_APPEND);
        return sp.getString(PORT, KingTellerUrlConfig.DefaultPort);
    }


    /**
     * 生成URL
     * @param context
     * @param url
     * @return
     */
    public static String CreateUrl(Context context, String url) {
        StringBuffer sb = new StringBuffer();
        sb.append("http://");
        sb.append(getIpDomain(context));
        sb.append(":");
        sb.append(getPort(context));
        sb.append(KingTellerUrlConfig.DefaultPath);
        sb.append(url);
        if (url.indexOf("?") == -1){
            sb.append("?version=" + context.getResources().getString(R.string.app_version));
            sb.append("&versionCode=" + context.getResources().getString(R.string.app_versionCode));
        }else{
            sb.append("&version=" + context.getResources().getString(R.string.app_version));
            sb.append("&versionCode=" + context.getResources().getString(R.string.app_versionCode));
        }

        return sb.toString();
    }
    
    /**
	 * 获取本地html文件
	 * @param url
	 * @return
	 */
	public static String CreateLocalUrl(Context context, String url) {
		StringBuffer sb = new StringBuffer();
		sb.append("file:///android_asset");
		sb.append(KingTellerUrlConfig.DefaultPath);
		sb.append(url);
		if (url.indexOf("?") == -1){
			sb.append("?version=" + context.getResources().getString(R.string.app_version));
		}else{
			sb.append("&version=" + context.getResources().getString(R.string.app_version));
		}
			

		return sb.toString();
	}

    /**
     * 获取JsessionID
     */
    public static String getAuthCookie(String cookieValue) {
        String sessionId = "";
        if (cookieValue != null) {
            sessionId = cookieValue.substring(0, cookieValue.indexOf(";"));
            sessionId = sessionId.substring(sessionId.indexOf("=") + 1, sessionId.length());
        }
        return sessionId;
    }

    /**
     * 拨打电话
     */
    public static void dial(Context context,String telephone){
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + telephone));
        context.startActivity(intent);
    }

    /**
     * 更新首页提示与 数据
     * @param mContext
     * @param page	底部按钮
     * @param ispagedata	是否更新数据
     * @param iscornerdata	底部按钮是否有提示
     */
    public static void MainUpdateStatus(Context mContext, int page, boolean ispagedata, boolean iscornerdata) {
        Intent intentupdate = new Intent();
        intentupdate.putExtra(KingTellerStaticConfig.ISUP_MAINPAGE, page);//底部按钮
        intentupdate.putExtra(KingTellerStaticConfig.ISUP_PAGEDATA, ispagedata);//是否更新数据
        intentupdate.putExtra(KingTellerStaticConfig.ISUP_CORNERDATA, iscornerdata);//底部按钮是否有提示
        intentupdate.setAction(KingTellerStaticConfig.ISUP_UPSTATUSACTION);
        mContext.sendBroadcast(intentupdate);
    }
    
	/**
	 * 隐藏软键盘
	 */
	public static void hideInputMethod(Context context) {
		((Activity) context).getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
	}
	
	/**
	 * 打开浏览器
	 */
	public static void openBrowser(Context context, String url) {
		Intent intent = new Intent();
		intent.setAction("android.intent.action.VIEW");
		intent.setData(Uri.parse(url));
		context.startActivity(intent);
	}
	
	/**
	 * 将字符转为2进制数据
	 */
	public static String byte2hex(byte[] b) {
		String hs = "";
		String stmp = "";
		for (int n = 0; n < b.length; n++) {
			stmp = (java.lang.Integer.toHexString(b[n] & 0XFF));
			if (stmp.length() == 1)
				hs = hs + "0" + stmp;
			else
				hs = hs + stmp;
			// if (n<b.length-1) hs=hs+":";
		}
		return hs.toUpperCase();
	}
}
