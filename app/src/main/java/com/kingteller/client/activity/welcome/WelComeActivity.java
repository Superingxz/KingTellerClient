package com.kingteller.client.activity.welcome;

import com.kingteller.R;
import com.kingteller.client.KingTellerApplication;
import com.kingteller.client.activity.account.MainActivity;
import com.kingteller.client.bean.account.User;
import com.kingteller.client.config.KingTellerStaticConfig.SHARED_PREFERENCES;
import com.kingteller.client.utils.KingTellerConfigUtils;
import com.kingteller.client.utils.KingTellerUpdateUtils;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

/**
 * 欢迎界面
 * Created by Administrator on 2015/8/26.
 */
public class WelComeActivity extends Activity{

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_welcome);
        KingTellerApplication.addActivity(this);
        
        firstEnter();
        redirectByTime(); 
    }

    /**
     * 根据时间进行页面跳转
     */
    private void redirectByTime() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(WelComeActivity.this, MainActivity.class));
                finish();
            }
        }, 2000);
    }
    /**
     * 程序第一次打开，可以在这里做一些默认配置初始化工作
     */
    private void firstEnter() {
    	SharedPreferences sp = getSharedPreferences(SHARED_PREFERENCES.CONFIG, MODE_APPEND);
    	String curVersion = "";
    	try {
    		//当前版本号
    		curVersion = KingTellerUpdateUtils.getCurrentVersionName(this);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	if(!"curVersion".equals(sp.getString("version", ""))) {
    		//声音 初始为true
    		KingTellerConfigUtils.setConfigMeta(KingTellerConfigUtils.KEY_PUSH_SY, true);
    		//震动 初始为true
    		KingTellerConfigUtils.setConfigMeta(KingTellerConfigUtils.KEY_PUSH_ZD, true);
    		//锁屏消息 初始为false
    		KingTellerConfigUtils.setConfigMeta(KingTellerConfigUtils.KEY_PUSH_LOCK, false);
    		sp.edit().putString("version", curVersion).commit();
    	}
    }
    
    @Override
	public void onDestroy() {
		super.onDestroy();
		KingTellerApplication.removeActivity(this);
	}
}
