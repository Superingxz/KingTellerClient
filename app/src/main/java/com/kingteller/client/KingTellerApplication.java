package com.kingteller.client;

import java.io.File;
import java.util.Stack;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.telephony.TelephonyManager;
import cn.jpush.android.api.JPushInterface;

import com.kingteller.client.activity.account.MainActivity;
import com.kingteller.client.bean.account.InvalidateSessionBean;
import com.kingteller.client.bean.account.LoginBean;
import com.kingteller.client.bean.account.User;
import com.kingteller.client.bean.map.AddressBean;
import com.kingteller.client.config.KingTellerStaticConfig;
import com.kingteller.client.config.KingTellerUrlConfig;
import com.kingteller.client.netauth.AjaxHttpCallBack;
import com.kingteller.client.netauth.KTHttpClient;
import com.kingteller.client.recever.CommonRecerverFunc;
import com.kingteller.client.utils.CrashHandler;
import com.kingteller.client.utils.KingTellerConfigUtils;
import com.kingteller.client.utils.KingTellerJudgeUtils;
import com.kingteller.client.utils.SQLiteHelper;
import com.kingteller.client.utils.KingTellerUpdateUtils;
import com.kingteller.client.view.dialog.use.KingTellerProgressUtils;
import com.kingteller.client.view.toast.T;
import com.kingteller.framework.http.AjaxParams;
import com.kingteller.framework.utils.EncroptionUtils;

/**
 * KingTellerApplication全局
 * @author 王定波
 *
 */
public class KingTellerApplication extends Application {

	private static KingTellerApplication application;
	private AddressBean curAddress;// 当前位置
	private SharedPreferences preferences;
	
    private static Stack<Activity> activityLists = new Stack<>();
    
	@Override
	public void onCreate() {
		super.onCreate();
		
		application = this;
		
		// 初始化缓存目录
		initCacheDir();
		initData();
		
	}

	/** 初始化生成缓存目录 */
	private void initCacheDir() {
		if (KingTellerJudgeUtils.isSDCardAvailable()) {
			File a_dir = new File(KingTellerStaticConfig.CACHE_PATH.SD_DATA);
			File d_dir = new File(KingTellerStaticConfig.CACHE_PATH.SD_DOWNLOAD);
			File l_dir = new File(KingTellerStaticConfig.CACHE_PATH.SD_LOG);
			File s_dir = new File(KingTellerStaticConfig.CACHE_PATH.SD_KTIMAGE);
			File c_dir = new File(KingTellerStaticConfig.CACHE_PATH.SD_IMAGECACHE);
			
			if (!a_dir.isDirectory()) {
				a_dir.mkdirs();
			}

			if (!d_dir.isDirectory()) {
				d_dir.mkdirs();
			}

			if (!l_dir.isDirectory()) {
				l_dir.mkdirs();
			}
			
			if (!s_dir.isDirectory()) {
				s_dir.mkdirs();
			}
			
			if (!c_dir.isDirectory()) {
				c_dir.mkdirs();
			}
		}
	}
	
	private void initData() {
		preferences = PreferenceManager.getDefaultSharedPreferences(this);
		
		// 推送初始化
		JPushInterface.setDebugMode(true);
		JPushInterface.init(this);

		SQLiteHelper helper = new SQLiteHelper(application);
		try {
			helper.createDatabase();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//初始化异常捕获
//		CrashHandler crashHandler = CrashHandler.getInstance();
//		crashHandler.init(getApplicationContext());
	}
	
	/** 判断用户是否登陆 */
	public boolean IsLogin() {
		String cookie = getAccessToken();
		if (cookie == null || cookie.equals("")) {
			return false;
		} else{
			return true;
		}
	}
	
	 /** 获取   得到的JSESSION */
    public String getAccessToken() {
        return preferences.getString(KingTellerStaticConfig.SHARED_PREFERENCES.COOKIE, "");
    }

    /** 设置   得到的JSESSION */
    public void setAccessToken(String authcookie) {
        preferences.edit().putString(KingTellerStaticConfig.SHARED_PREFERENCES.COOKIE, authcookie).commit();
    }


	/** 注销，清楚用户信息 */
	public void exit(boolean isLoginout) {
		//清除 JSESSION
		preferences.edit().clear().commit();
		//清除 登陆信息
		if (isLoginout)
			getSharedPreferences(KingTellerStaticConfig.SHARED_PREFERENCES.USER, Context.MODE_APPEND).edit().clear().commit();
	}

	
	/** 获取IMEI */
	public static String getDeviceToken() {
		return ((TelephonyManager) getApplication().getSystemService(TELEPHONY_SERVICE)).getDeviceId();
	}
	
	public static final KingTellerApplication getApplication() {
		return application;
	}
	
	public SharedPreferences getPreferences() {
		return preferences;
	}

	public AddressBean getCurAddress() {
		return curAddress;
	}

	public void setCurAddress(AddressBean curAddress) {
		this.curAddress = curAddress;
	}
	
	/**
	 * 判断 登陆是否失效 JSESSION
	 */
	public static void CheckAuthSession(final Context context) {
		if (KingTellerApplication.getApplication().IsLogin()) {
			KTHttpClient fh = new KTHttpClient(true);
			fh.post(KingTellerConfigUtils.CreateUrl(context, KingTellerUrlConfig.CheckSessionUrl),
					new AjaxHttpCallBack<InvalidateSessionBean>(false) {
						@Override
						public void onDo(InvalidateSessionBean data) {
							// 若返回参数invalidateSession为1代表会话已失效，为2代表会话未失效
							if (data.getInvalidateSession().equals("1")) {
								// 重新登录
								getAgainJsession(context);
							} else if (data.getInvalidateSession().equals("2")) {

							}
						}
					});
		}
	}
	
	/**
	 * 登陆失效 重新获取 JSESSION
	 */
	public static void getAgainJsession(final Context mContext) {
		KingTellerProgressUtils.closeProgress();
		
		User user = User.getInfo(mContext);
		final String user_name =  user.getUserName();
		final String user_password =  user.getPassword();
		
		AjaxParams params = new AjaxParams();
		
		params.put("userAccount", user_name);
		params.put("loginPassword", EncroptionUtils.EncryptSHA(user_password));
		params.put("iemi", KingTellerApplication.getDeviceToken());
		params.put("versionName", KingTellerUpdateUtils.getCurrentVersionName(mContext));
		params.put("appType", "android");
		
		KTHttpClient fh = new KTHttpClient(false);
		fh.post(KingTellerConfigUtils.CreateUrl(mContext, KingTellerUrlConfig.LoginUrl), params,
				new AjaxHttpCallBack<LoginBean>(mContext, false) {

					@Override
					public void onStart() {
						T.showLong(mContext, "重新获取登陆信息中...");
					}

					@Override
					public void onDo(LoginBean data) {
						if (data.getLoginError() == 1) {
							
							// 保存用户信息
							data.setUsername(user_name);
							data.setPassword(user_password);
							data.setVersionName(KingTellerUpdateUtils.getCurrentVersionName(mContext));
							User.SaveInfo(mContext, data);
							
							// 保存回话cookie
							KingTellerApplication.getApplication().setAccessToken(KingTellerConfigUtils.getAuthCookie(onGetHeader("Set-Cookie")));
							
							/*记录登陆信息 --- 调试代码*/
							CrashHandler.saveStaffLogInTextFile("登陆失效, 重新获取成功!", User.getInfo(mContext),
									KingTellerConfigUtils.getIpDomain(mContext),
									KingTellerConfigUtils.getPort(mContext), null, 0);
							
							T.showShort(mContext, "重新获取成功, 请重新刷新!");
							CommonRecerverFunc recerverFunc = new CommonRecerverFunc();
							recerverFunc.wUpdata(mContext);
						} else {
							
							/*记录登陆信息 --- 调试代码*/
							CrashHandler.saveStaffLogInTextFile("登陆失效, 重新获取不成功!", User.getInfo(mContext),
									KingTellerConfigUtils.getIpDomain(mContext),
									KingTellerConfigUtils.getPort(mContext), null, 0);
							
							T.showShort(mContext, "重新获取不成功, 请重新刷新!");
						}
					};

				});
	}
	
	/** 
     * Activity关闭时，删除Activity列表中的Activity对象
     */  
    public static void removeActivity(Activity a){  
    	if(a != null){
    		activityLists.remove(a);
    	}
    }  
      
    /** 
     * 向Activity列表中添加Activity对象
     */  
    public static void addActivity(Activity a){  
    	if(activityLists == null){
    		activityLists = new Stack<>();
    	}
    	activityLists.add(a);  
    }  
    
    /** 
     * 获得当前栈顶Activity
     */ 
     public static Activity currentActivity(){
    	 if(activityLists.size() > 0){
             return activityLists.lastElement();
         }
         return null;
     }
  
    /** 
     * 关闭Activity列表中的所有Activity
     */  
    public static void finishActivity(){  
        for (Activity activity : activityLists) {    
            if (null != activity) {    
                activity.finish();    
            }    
        }
        
       //杀死该应用进程  
       android.os.Process.killProcess(android.os.Process.myPid());    
    }  

}