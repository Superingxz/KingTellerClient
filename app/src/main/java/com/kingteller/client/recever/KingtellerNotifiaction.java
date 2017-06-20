package com.kingteller.client.recever;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.widget.RemoteViews;

import com.kingteller.R;
import com.kingteller.client.bean.account.Push;
import com.kingteller.client.config.KingTellerStaticConfig;
import com.kingteller.client.utils.FloatWindowManager;
import com.kingteller.client.utils.KingTellerConfigUtils;
import com.kingteller.client.utils.KingTellerJudgeUtils;
import com.kingteller.framework.KingTellerDb;
/**
 * 推送消息弹出通知栏
 * 
 * @author 王定波
 * 
 */
public class KingtellerNotifiaction {

	// notifiaction显示的内容
	public static int notifiactionCount;
	public static NotificationManager manager;
	private static KingTellerDb finalDb;

	@SuppressWarnings("deprecation")
	public static void showNotification(Context context, Push data) {

		finalDb = KingTellerDb.create(context);
		// 查询数据库是否有了
		if (!KingTellerJudgeUtils.isEmpty(data.getId())) {
			// 数据库处理防止异常
			try {
				Push datatmp = finalDb.findById(data.getId(), Push.class);
				if (datatmp == null) {
					finalDb.save(data);
				} else {
					// 重复了
					return;
				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}

		}
		KingtellerNotifiaction.notifiactionCount++;

		// 设置点击事件 发送广播
		Intent intent = new Intent(context, KingTellerBroadCastRecever.class);
		intent.setAction(KingTellerBroadCastRecever.MAINACTIVITYACTION);
		PendingIntent pendIntent = PendingIntent.getBroadcast(context, 0,intent, PendingIntent.FLAG_UPDATE_CURRENT);

		manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

		Notification.Builder builder = new Notification.Builder(context);



		//Notification notification = new Notification();
		builder.setSmallIcon(R.drawable.notice_logo);
		//notification.icon = R.drawable.notice_logo;

		// 设置滚动的信息
		if (KingTellerJudgeUtils.isEmpty(data.getMsg()))
			builder.setTicker(context.getString(R.string.app_name));
			//notification.tickerText = context.getString(R.string.app_name);
		else {
			if (KingTellerJudgeUtils.isEmpty(data.getSender())){
				builder.setTicker(data.getMsg());
				//notification.tickerText = data.getMsg();
			}else{
				builder.setTicker(data.getSender() + ":" + data.getMsg());
				//notification.tickerText = data.getSender() + ":" + data.getMsg();
			}
		}
		/*notification.setLatestEventInfo(context, context
				.getString(R.string.push_title), String.format(
				context.getString(R.string.push_msg), notifiactionCount),
				pendIntent);*/

		builder.setContentTitle(context
				.getString(R.string.push_title));
		builder.setContentText(String.format(
				context.getString(R.string.push_msg), notifiactionCount));

		RemoteViews remoteViews = new RemoteViews(context.getPackageName(),
				R.layout.item_notifacation_push);

		builder.setContent(remoteViews);

		// 设置显示的数据
		if (!KingTellerJudgeUtils.isEmpty(data.getMsg())) {
			remoteViews.setTextViewText(R.id.notifacationtv, data.getMsg());
		} else {
			remoteViews.setTextViewText(R.id.notifacationtv,
					String.format(context.getString(R.string.push_msg), notifiactionCount));
		}

		// 设置标题
		if (KingTellerJudgeUtils.isEmpty(data.getSender()))
			remoteViews.setTextViewText(
					R.id.title,
					context.getString(R.string.push_title)
							+ String.format(
									context.getString(R.string.push_title_f),
									notifiactionCount));
		else
			remoteViews.setTextViewText(
					R.id.title,
					data.getSender()
							+ String.format(
									context.getString(R.string.push_title_f),
									notifiactionCount));

		// 设置时间
		if (!KingTellerJudgeUtils.isEmpty(data.getSend_time()))
			remoteViews.setTextViewText(R.id.time,
					data.getSend_time());

		Notification notification = builder.build();


		// 设置一些属性
		//显示  正在进行中
		notification.flags = Notification.FLAG_ONGOING_EVENT;
		
		if (KingTellerConfigUtils.getConfigMeta(KingTellerConfigUtils.KEY_PUSH_SY, true))
			notification.defaults |= Notification.DEFAULT_SOUND;

		if (KingTellerConfigUtils.getConfigMeta(KingTellerConfigUtils.KEY_PUSH_ZD, true))
			notification.defaults |= Notification.DEFAULT_VIBRATE;

		/**
		 * 增加闪灯
		 */
		notification.defaults |= Notification.DEFAULT_LIGHTS; 
		notification.ledARGB = Color.BLUE;
		notification.ledOnMS = 1000; 
		notification.ledOffMS = 1000; 
		notification.flags |= Notification.FLAG_SHOW_LIGHTS; 

		manager.notify(KingTellerStaticConfig.NotifiactionPushID, notification);

		if (FloatWindowManager.isWindowShowing())
			FloatWindowManager.updateFloat(context);

	}
}
