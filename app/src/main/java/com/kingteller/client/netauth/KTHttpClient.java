package com.kingteller.client.netauth;

import com.kingteller.client.KingTellerApplication;
import com.kingteller.client.config.KingTellerStaticConfig;
import com.kingteller.framework.KingTellerHttp;
import com.kingteller.framework.utils.Logger;

/**
 * @Description HttpClient是一个扩展出来的设置是否添加sessionid的类
 * @author 王定波
 * 
 */
public class KTHttpClient extends KingTellerHttp {

	/**
	 * 
	 * @param isAuth 是否检查登陆
	 */
	public KTHttpClient(boolean isAuth) {
		// TODO Auto-generated constructor stub
		super();
		if(isAuth)
		this.addHeader("Cookie", KingTellerStaticConfig.COOKIENAME + "="
				+ KingTellerApplication.getApplication().getAccessToken());
	}
}