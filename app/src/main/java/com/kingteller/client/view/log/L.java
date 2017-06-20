package com.kingteller.client.view.log;

import android.util.Log;

/**
 * Log 日志管理 统一使用类
 * Created by Administrator on 16-1-31.
 */
public class L {
    /**
     * 是否开启debug
     */
    public static boolean isDebug = true;


    /**
     * 错误
     */
    public static void e(Class<?> clazz,String msg){
        if(isDebug){
            Log.e(clazz.getSimpleName(), msg + "");
        }
    }

    public static void e(String tag,String msg){
        if(isDebug){
            Log.e(tag, msg + "");
        }
    }

    /**
     * 信息
     */
    public static void i(Class<?> clazz,String msg){
        if(isDebug){
            Log.i(clazz.getSimpleName(), msg+"");
        }
    }
    /**
     * 警告
     */
    public static void w(Class<?> clazz,String msg) {
        if (isDebug) {
            Log.w(clazz.getSimpleName(), msg + "");
        }
    }
}
