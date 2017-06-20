package com.kingteller.client.recever;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.kingteller.client.KingTellerApplication;
import com.kingteller.client.activity.account.MainActivity;
import com.kingteller.client.bean.account.User;
import com.kingteller.client.config.KingTellerStaticConfig;
import com.kingteller.client.netauth.exception.NetErrorCode;
import com.kingteller.client.service.KingTellerService;
import com.kingteller.client.utils.CrashHandler;
import com.kingteller.client.utils.KingTellerConfigUtils;
import com.kingteller.client.view.toast.T;

/**
 * @Title KingTellerBroadCastRecever
 * @Package com.kingteller.client.recever
 * @Description KingTellerBroadCastRecever
 * @author 王定波
 */
public class KingTellerBroadCastRecever extends BroadcastReceiver {

	public static final String NETERRORACTION = "com.kingteller.client.recever.KingTellerBroadCastRecever.netErrorAction";
	public static final String AUTHERRORACTION = "com.kingteller.client.recever.KingTellerBroadCastRecever.authErrorAction";
	public static final String DATAERRORACTION = "com.kingteller.client.recever.KingTellerBroadCastRecever.dataErrorAction";
	public static final String BOOTSTARTACTION = "android.intent.action.BOOT_COMPLETED";// 系统重新启动
	public static final String MAINACTIVITYACTION = "com.kingteller.client.recever.KingTellerBroadCastRecever.OpenMainActivityAction";
	private Context mContext;
	@Override
	public void onReceive(final Context context, Intent intent) {
		mContext = KingTellerApplication.currentActivity();
		
		String action = intent.getAction();
		
		if (mContext != null && action.equals(NETERRORACTION)) {// 网络错误或者服务器无法连接
			Log.e("广播类型：", "网络错误或者服务器无法连接!");
			
			/*网络错误或者服务器无法连接 --- 调试代码*/
			CrashHandler.saveStaffLogInTextFile("网络错误或者服务器无法连接!", User.getInfo(mContext),
					KingTellerConfigUtils.getIpDomain(mContext),
					KingTellerConfigUtils.getPort(mContext), null, 0);
			
			T.showShort(mContext, NetErrorCode.NETERRORMSG);
		} else if (mContext != null && action.equals(AUTHERRORACTION)) {// 登陆失效
			Log.e("广播类型：", "登陆失效!");
			
			/*记录登陆失效信息 --- 调试代码*/
			CrashHandler.saveStaffLogInTextFile("登陆失效, 正在重新登陆!", User.getInfo(mContext),
					KingTellerConfigUtils.getIpDomain(mContext),
					KingTellerConfigUtils.getPort(mContext), null, 0);
			
			KingTellerApplication.getApplication().exit(false);
			KingTellerApplication.getAgainJsession(mContext);

		} else if (mContext != null && action.equals(DATAERRORACTION)) {//数据错误
			Log.e("广播类型：", "数据错误!");
			
		} else if (mContext != null && action.equals(BOOTSTARTACTION)) {// 启动服务
			Log.e("广播类型：", "启动服务!");
			
		} else if (mContext != null && MAINACTIVITYACTION.equals(action)) {	// 打开主界面
			Log.e("广播类型：", "打开主界面!");
			
			try {
				KingtellerNotifiaction.notifiactionCount = 0;
				KingtellerNotifiaction.manager.cancel(KingTellerStaticConfig.NotifiactionPushID);
			} catch (Exception e) {
			}

			
			// 打开自定义的Activity
			Intent i = new Intent(context, MainActivity.class);
			i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			mContext.startActivity(i);
			
			KingTellerConfigUtils.MainUpdateStatus(mContext, 
					KingTellerStaticConfig.MAIN_WAITDOT, 
					false, true);
		}

	}

}
