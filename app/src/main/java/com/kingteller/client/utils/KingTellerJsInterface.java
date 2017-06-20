package com.kingteller.client.utils;

import java.util.HashMap;
import java.util.Map;

import com.kingteller.client.KingTellerApplication;
import com.kingteller.client.bean.account.User;

import android.content.Context;
import android.webkit.JavascriptInterface;

/**
 * webview javascript接口
 * @author 王定波
 *
 */
public class KingTellerJsInterface {
	private Context context;
	private User user;
	private Map<String, String> param = new HashMap<String, String>();

	public KingTellerJsInterface(Context context)
	{
		this.context=context;
		user=User.getInfo(context);
	}
	@JavascriptInterface
	public void receOrders() {
		// WaitDoSecondLayerActivity.removeState = true;

	}

	@JavascriptInterface
	public String getRemoteIp() {
		return KingTellerConfigUtils.getIpDomain(context);
	}

	@JavascriptInterface
	public String getRemotePort() {
		return KingTellerConfigUtils.getPort(context);
	}

	@JavascriptInterface
	public String getSessionId() {
		return KingTellerApplication.getApplication().getAccessToken();
	}

	@JavascriptInterface
	public String getRole() {
		return user.getRoleCode();
	}

	@JavascriptInterface
	public void setParam(String name, String value) {
		this.param.put(name, value);
	}

	@JavascriptInterface
	public String getParam(String name) {
		return this.param.get(name);
	}
}
