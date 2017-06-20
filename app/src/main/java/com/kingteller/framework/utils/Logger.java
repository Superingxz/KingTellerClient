package com.kingteller.framework.utils;

import android.util.Log;

/**
 * @Title Logger
 * @Package com.kingteller.framework.utils
 * @Description TALogger是一个日志打印类
 * @author 王定波
 * @date 2014-6-16 16:30
 * @version V1.0
 */
public class Logger {
	private final static boolean DEBUG = true;

	public static void d(String Tag, String msg) {
		if (Tag == null || msg == null)
			return;
		else if (DEBUG)
			Log.d(Tag, msg);
	}

	public static void e(String Tag, String msg) {
		if (Tag == null || msg == null)
			return;
		else if (DEBUG)
			Log.e(Tag, msg);
	}

	public static void i(String Tag, String msg) {
		if (Tag == null || msg == null)
			return;
		else if (DEBUG)
			Log.i(Tag, msg);
	}

	public static void v(String Tag, String msg) {
		if (Tag == null || msg == null)
			return;
		else if (DEBUG)
			Log.v(Tag, msg);
	}

	public static void w(String Tag, String msg) {
		if (Tag == null || msg == null)
			return;
		else if (DEBUG)
			Log.w(Tag, msg);
	}
}
