package com.kingteller.client.netauth;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;

import com.google.gson.Gson;
import com.kingteller.client.bean.account.InvalidateSessionBean;
import com.kingteller.client.bean.common.BaseBean;
import com.kingteller.client.netauth.exception.NetErrorCode;
import com.kingteller.client.netauth.exception.KingTellerException;
import com.kingteller.client.recever.KingTellerBroadCastRecever;
import com.kingteller.framework.http.AjaxCallBack;
import com.kingteller.framework.utils.Logger;

/**
 * @Title AjaxHttpCallBack
 * @Package com.kingteller.client.netauth
 * @Description AjaxAuthCheckCallBack是一个扩展出来的http返回信息类，这个类主要判断用户是否登录是否失效等
 * @author 王定波
 * @date 2014-6-16 16:30
 * @version V1.0
 */
public class AjaxHttpCallBack<T> extends AjaxCallBack<String> {

	private static final String TAG = "AjaxHttpCallBack";
	private Context context = null;// 此参数传入可发送广播进行相应处理
	private Type type = null;// 数据类型
	private boolean isAuth = false;// 接口请求是否需要验证

	/*
	 * 初始化AjaxAuthCheckCallBack
	 */
	public AjaxHttpCallBack(Context context, Type typeOfT, boolean isAuth) {
		this.context = context;
		this.type = typeOfT;
		this.isAuth = isAuth;
	}

	public AjaxHttpCallBack(Type typeOfT, boolean isAuth) {
		this.context = null;
		this.type = typeOfT;
		this.isAuth = isAuth;
	}

	public AjaxHttpCallBack(Context context, boolean isAuth) {
		this.context = context;
		this.type = null;
		this.isAuth = isAuth;

	}

	public AjaxHttpCallBack(boolean isAuth) {
		this.context = null;
		this.type = null;
		this.isAuth = isAuth;
	}

	public Context getContext() {
		return context;
	}

	
	@Override
	public void onSuccess(String response) {
		onFinish();
		try {
			Logger.e(TAG, response);
			checkAuth(response);
		} catch (KingTellerException e) {
			// TODO Auto-generated catch block
			onFailure(null, e.getStatusCode(), e.getMessage());
		}
	}

	/**
	 * 请求完成---执行的回调函数
	 */
	public void onFinish() {
	}

	private void checkAuth(String response) throws KingTellerException {

		if (response != null && !response.equals("")) {
			if (response.trim().equals("yes") || response.trim().equals("no")) {
				onDoString(response);
			} else {
				response = response.replace("[null]", "[]");
				Gson gson = new Gson();

				if (isAuth) {
					// 需要验证
					try {
						parseJson(response);
					} catch (Exception e) {
						// TODO: handle exception
						try {
							InvalidateSessionBean data = gson.fromJson(
									response, InvalidateSessionBean.class);
							if ("1".equals(data.getInvalidateSession())) {
								onFailure(null, NetErrorCode.AUTHERROR,
										NetErrorCode.AUTHERRORMSG);
							} else{
								onFailure(null, NetErrorCode.DATAERROR,
										NetErrorCode.DATAERRORMSG);
							}
							
						} catch (Exception e2) {
							// TODO: handle exception
							throw new KingTellerException(
									NetErrorCode.DATAERRORMSG,
									NetErrorCode.DATAERROR);
						}

					}
				} else {
					// 不验证
					try {
						T tdata = null;
						if (type == null) {
							Type t = getClass().getGenericSuperclass();
							Type[] params = ((ParameterizedType) t)
									.getActualTypeArguments();
							Class<T> cls = (Class<T>) params[0];
							tdata = gson.fromJson(response, cls);
						} else
							tdata = gson.fromJson(response, type);
						onDo(tdata);
					} catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
						throw new KingTellerException(
								NetErrorCode.DATAERRORMSG,
								NetErrorCode.DATAERROR);
					}
				}
			}

		} else {
			throw new KingTellerException(NetErrorCode.DATAERRORMSG,
					NetErrorCode.DATAERROR);
		}

	}

	private void parseJson(String response) throws KingTellerException {
		Gson gson = new Gson();
		T tdata = null;
		boolean isauth = false;
		
		try {
			InvalidateSessionBean data = gson.fromJson(response, InvalidateSessionBean.class);
		
			if ("1".equals(data.getInvalidateSession())) {
				isauth = true;
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		if (isauth)
			throw new KingTellerException(NetErrorCode.AUTHERRORMSG,
					NetErrorCode.AUTHERROR);

		try {
			if (type == null) {
				Type t = getClass().getGenericSuperclass();
				Type[] params = ((ParameterizedType) t)
						.getActualTypeArguments();
				Class<T> cls = (Class<T>) params[0];
				tdata = gson.fromJson(response, cls);
			} else
				tdata = gson.fromJson(response, type);
			onDo(tdata);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new KingTellerException(NetErrorCode.DATAERRORMSG,
					NetErrorCode.DATAERROR);
		}

	}
	

	/**
	 * 发生错误---执行的回调函数
	 */
	@SuppressLint("ShowToast")
	public void onError(int errorNo, String strMsg) {

	}
	
	/**
	 * 完成成功获取数据---执行的回调函数
	 */
	public void onDo(T data) {

	}
	
	/**
	 * 完成成功获取数据---执行的回调函数
	 */
	public void onDoString(String data) {

	}
	

	@Override
	public void onFailure(Throwable t, int errorNo, String strMsg) {
		onFinish();
		Logger.e("netErrorCode", "code:" + errorNo);
		if (context != null) {
			switch (errorNo) {
			case NetErrorCode.NETERROR:
				context.sendBroadcast(new Intent(
						KingTellerBroadCastRecever.NETERRORACTION));
				break;
			case NetErrorCode.DATAERROR:
				context.sendBroadcast(new Intent(
						KingTellerBroadCastRecever.DATAERRORACTION));
				break;
			case NetErrorCode.AUTHERROR:
				context.sendBroadcast(new Intent(
						KingTellerBroadCastRecever.AUTHERRORACTION));
				break;
			// 设置更多错误可以在此设置
			default:
				context.sendBroadcast(new Intent(
						KingTellerBroadCastRecever.NETERRORACTION));
				break;
			}
		}
		onError(errorNo, strMsg);
	}
}
