package com.kingteller.client.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class SharedPreferencesUtils {

	public static void saveData(Context context,String key,String value) {
		Editor sharedata = context.getSharedPreferences("submitTime", 0).edit();
		sharedata.putString(key, value);
		sharedata.commit();
	}
	
	public static String getData(Context context,String key){
		SharedPreferences sharedata = context.getSharedPreferences("submitTime", 0);    
		String data = sharedata.getString(key, null);   
		return data;
	}
}
