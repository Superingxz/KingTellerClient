package com.kingteller.client.recever;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;

import com.kingteller.client.KingTellerApplication;
import com.kingteller.client.bean.account.Push;
import com.kingteller.client.config.KingTellerStaticConfig;
import com.kingteller.client.utils.KingTellerConfigUtils;
import com.kingteller.client.view.toast.T;
import com.kingteller.framework.utils.Logger;

/**
 * 短信拦截广播接收器
 * @author 王定波
 *
 */
public class SMSBroadcaster extends BroadcastReceiver {
	public Context mContext;
	public Intent intent;

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		this.mContext = context;
		// 获取intent参数
		Bundle bundle = intent.getExtras();
		// 判断bundle内容
		if (bundle != null) {
			// 取pdus内容,转换为Object[]
			Object[] pdus = (Object[]) bundle.get("pdus");
			// 解析短信
			SmsMessage[] messages = new SmsMessage[pdus.length];
			for (int i = 0; i < messages.length; i++) {
				byte[] pdu = (byte[]) pdus[i];
				messages[i] = SmsMessage.createFromPdu(pdu);
			}

			StringBuffer strmessage = new StringBuffer();

			// 解析完内容后分析具体参数
			for (SmsMessage msg : messages) {
				// 获取短信内容
				String contentstr = msg.getMessageBody();
				String sender = msg.getOriginatingAddress();
				// Date date = new Date(msg.getTimestampMillis());
				// TODO:根据条件判断,然后进一般处理
				if ("10060".equals(sender)) {
					// 屏蔽手机号为10060的短信，这里还可以时行一些处理，如把这个信息发送到第三人的手机等等。
					// TODO:测试
					T.showShort(mContext, "收到10060的短信" + "内容:" + contentstr);
					
					// 对于特定的内容,取消广播
					abortBroadcast();
				} else {
					strmessage.append(contentstr);

				}

				Logger.e("短信信息", strmessage.toString());

				try {
					if (strmessage.toString().indexOf("*kingteller*")!=-1) {
						if (KingTellerApplication.getApplication().IsLogin()) {
							//KingtellerNotifiaction.showNotification(context,null);
							String smsmsg = strmessage.toString()
									.replace("*kingteller*", "").trim();
							Push data = new Push();
							// 设置兼容模式 兼容以前的
							if (smsmsg.indexOf("*") == -1)
								data.setMsg(smsmsg);
							else {
								//手机短信内容格式 ：拦截前缀*发送者姓名*发送日期文本*一致性标识*短信内容
								String[] strdata = smsmsg.split("\\*");
								if (strdata.length >= 4) {
									data.setSender(strdata[0]);
									data.setSend_time(strdata[1]);
									data.setId(strdata[2]);
									data.setMsg(strdata[3]);
								} else
									data.setMsg(smsmsg);

							}

							KingtellerNotifiaction.showNotification(context,
									data);
							KingTellerConfigUtils.MainUpdateStatus(context,
									KingTellerStaticConfig.MAIN_WAITDOT, 
									true,true);
						}
						this.abortBroadcast();
					}
				} catch (Exception ex) {
					ex.printStackTrace();
				}

			}

		}
	}
}
