package com.kingteller.framework.http;

import java.util.HashMap;
import java.util.Map;

import org.apache.http.Header;

import android.util.Log;

/**
 * 
 * @author 王定波
 * 
 * @param <T>
 *            目前泛型支持 String,File, 以后扩展：JSONObject,Bitmap,byte[],XmlDom
 */
public abstract class AjaxCallBack<T> {

	private boolean progress = true;
	private int rate = 1000 * 1;// 每秒
	private Map<String, String> header = new HashMap<String, String>();

	// private Class<T> type;
	//
	// public AjaxCallBack(Class<T> clazz) {
	// this.type = clazz;
	// }

	public boolean isProgress() {
		return progress;
	}

	public int getRate() {
		return rate;
	}

	/**
	 * 设置进度,而且只有设置了这个了以后，onLoading才能有效。
	 * 
	 * @param progress
	 *            是否启用进度显示
	 * @param rate
	 *            进度更新频率
	 */
	public AjaxCallBack<T> progress(boolean progress, int rate) {
		this.progress = progress;
		this.rate = rate;
		return this;
	}

	public void onStart() {
	};

	/**
	 * onLoading方法有效progress
	 * 
	 * @param count
	 * @param current
	 */
	public void onLoading(long count, long current) {
	};
	
	/**
	 * 上传进度
	 * 
	 * @param count
	 * @param current
	 */
	public void onUplodinging(long count, long current) {
	};

	public void onSuccess(T t) {
	};

	public void onFailure(Throwable t, int errorNo, String strMsg) {
	};

	public void SetHeaders(Header[] headers) {
		if (headers != null)
			for (Header h : headers) {
				//Log.v("skylun", h.getName() + "--------" + h.getValue());
				header.put(h.getName(), h.getValue());
			}
	};

	public String onGetHeader(String key) {
		try {
			return header.get(key);
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}

	};

}
