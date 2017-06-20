package com.kingteller.client.activity.account;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

import com.kingteller.R;
import com.kingteller.client.KingTellerApplication;
import com.kingteller.client.activity.account.fragment.FunctionFragment;
import com.kingteller.client.activity.account.fragment.MoreFragment;
import com.kingteller.client.activity.account.fragment.WaitDoFragment;
import com.kingteller.client.bean.account.User;
import com.kingteller.client.config.KingTellerStaticConfig;
import com.kingteller.client.service.KingTellerService;
import com.kingteller.client.utils.CrashHandler;
import com.kingteller.client.utils.KingTellerConfigUtils;
import com.kingteller.client.utils.KingTellerFileUtils;
import com.kingteller.client.utils.KingTellerJudgeUtils;
import com.kingteller.client.utils.KingTellerTimeUtils;
import com.kingteller.client.utils.KingTellerUpdateUtils;
import com.kingteller.client.view.toast.T;
import com.kingteller.framework.utils.Logger;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * 主Activity
 * @author 王定波
 */
public class MainActivity extends FragmentActivity implements OnClickListener, OnPageChangeListener {

	private static final String TAG = "MainActivity";
	private ViewPager mViewPager;
	private List<Fragment> mTabs;
	private List<ImageView> mBottomIVLists;
	private List<TextView> mBottomTVLists;
	private List<TextView> mBottomTVCornerLists;
	
	private TextView title_title;

    private LinearLayout mRemindLinearLayout;
    private LinearLayout mBenchLinearLayout;
    private LinearLayout mSettingLinearLayout;

    private RelativeLayout mRemindSmallLinearLayout;
    private RelativeLayout mBenchSmallLinearLayout;
    private RelativeLayout mSettingSmallLinearLayout;

    private TextView mRemindTextView;
    private TextView mBenchTextView;
    private TextView mSettingTextView;
    
    private TextView mRemindTextCorner;
    private TextView mBenchTextCorner;
    private TextView mSettingTextCorner;

    private ImageView mRemindImageView;
    private ImageView mBenchImageView;
    private ImageView mSettingImageView;

    private String[] mTitles = new String[]{"提醒","工作台","设置"};
	private WaitDoFragment wFragment;
	private FunctionFragment fFragment;
	private MoreFragment mFragment;
	
    private FragmentPagerAdapter mAdapter;
	
	private Handler currHandler = new Handler();//设置别名
	private JPushRegRunnable cRunnable = new JPushRegRunnable();

	private Handler initHandler = new Handler();//检查更新
	private InitRunnable initRunnable = new InitRunnable();

	private int RegTryTimes = 10;// 注册失败 总 重启次数
	private int NowRegTryTimes = 0;// 当前注册  重启次数
	
	private int backCount = 1;//返回次数
	
	private Context mContext;
	/**
	 * 更新小点的广播接收器
	 */
	private BroadcastReceiver MainContentRecever = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent data) {
			String action = data.getAction();
			if (action.equals(KingTellerStaticConfig.ISUP_UPSTATUSACTION)) {
				int IsUp_MainPage = data.getIntExtra(KingTellerStaticConfig.ISUP_MAINPAGE, -1); //页面
				boolean IsUp_Pagedata = data.getBooleanExtra(KingTellerStaticConfig.ISUP_PAGEDATA, false); //是否更新数据
				boolean IsUp_Corner = data.getBooleanExtra(KingTellerStaticConfig.ISUP_CORNERDATA, false); //是否更新角标
				switch (IsUp_MainPage) {
					case KingTellerStaticConfig.MAIN_WAITDOT:
						if (IsUp_Pagedata && mViewPager.getCurrentItem() == 0) {
							wFragment.getWaitDosData();
						} else if (IsUp_Pagedata){
							mViewPager.setCurrentItem(0, true);
							wFragment.getWaitDosData();
						}
						
						if(IsUp_Corner){//设置角标
							chosenTVCorner(KingTellerStaticConfig.MAIN_WAITDOT, KingTellerStaticConfig.WAITDO_BOTTOM_CORNER);
						}else{
							chosenTVCorner(KingTellerStaticConfig.MAIN_WAITDOT, 0);
						}
						
						break;
					case KingTellerStaticConfig.MAIN_FUNCTION:
						
						if(IsUp_Corner){//设置角标
							chosenTVCorner(KingTellerStaticConfig.MAIN_FUNCTION, KingTellerStaticConfig.FUNCTION_BOTTOM_CORNER);
						}else{
							chosenTVCorner(KingTellerStaticConfig.MAIN_FUNCTION, 0);
						}
						
						break;
					case KingTellerStaticConfig.MAIN_MORE:
						if (IsUp_Pagedata && mViewPager.getCurrentItem() == 2){
							
						}
						break;
					default:
						break;
				}

			}else if(action.equals("android.net.conn.CONNECTIVITY_CHANGE")){
				Log.e(TAG,"network state changed.");
				wFragment.isNetAvaliable(KingTellerJudgeUtils.isNetAvaliable(mContext));
			}else if(action.equals("android.intent.action.W_UPDATA")) {
				Log.e(TAG,"wFragment need to updata!");
				wFragment.onClickRefresh();
			}
		}
	};

	/**
	 * 重设返回次数
	 */
	private Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0:
				backCount = 1;
				break;
			}
		}
	};


	
	@Override
	public void onCreate(Bundle arg0) {
		Log.e(TAG, "onCreate");  
		super.onCreate(arg0);
		setContentView(R.layout.layout_main);
		
		KingTellerApplication.addActivity(this);
		mContext = MainActivity.this;
		
		initUI();
		initData();
		
		//删除7天前日志
		setDeleteFile();
	}
	
    @Override  
    public void onStart() {  
        Log.e(TAG, "onStart");  
        super.onStart();  
    }  
    
	@Override  
	public void onRestart() {  
        Log.e(TAG, "onRestart");  
        super.onRestart();  
    }  
	
	@Override
	public void onResume() {
		JPushInterface.onResume(this);
		Log.e(TAG, "onResume");  
		super.onResume();	
		
	}
	
    @Override  
    public void onPause() {  
    	JPushInterface.onPause(this);
        Log.e(TAG, "onPause");  
        super.onPause();  
    }  
    
    @Override  
    public void onStop() {  
        Log.e(TAG, "onStop");  
        super.onStop();  
    }  
    
	@Override
	public void onDestroy() {
		Log.e(TAG, "onDestory");  
		super.onDestroy();
		
		if (MainContentRecever != null) {
			unregisterReceiver(MainContentRecever);
			MainContentRecever = null;
		}
		KingTellerApplication.removeActivity(this);
		
		/*记录注销信息 --- 调试代码*/
		CrashHandler.saveStaffLogInTextFile("登陆已注销!", User.getInfo(MainActivity.this),
				KingTellerConfigUtils.getIpDomain(MainActivity.this),
				KingTellerConfigUtils.getPort(MainActivity.this), null, 0);
	}
 
	
	private void initUI() {
		
		title_title = (TextView) findViewById(R.id.layout_main_text);
		mViewPager = (ViewPager)findViewById(R.id.layout_main_viewpager);
        mTabs = new ArrayList<Fragment>();
        mBottomTVLists = new ArrayList<TextView>();
        mBottomTVCornerLists = new ArrayList<TextView>();
        mBottomIVLists = new ArrayList<ImageView>();
	
        mRemindLinearLayout = (LinearLayout) findViewById(R.id.main_bottom_bar_remind);
        mBenchLinearLayout = (LinearLayout) findViewById(R.id.main_bottom_bar_bench);
        mSettingLinearLayout = (LinearLayout) findViewById(R.id.main_bottom_bar_setting);

        mRemindSmallLinearLayout = (RelativeLayout) findViewById(R.id.main_bottom_bar_remind_small);
        mBenchSmallLinearLayout = (RelativeLayout) findViewById(R.id.main_bottom_bar_bench_small);
        mSettingSmallLinearLayout = (RelativeLayout) findViewById(R.id.main_bottom_bar_setting_small);

        mRemindTextView = (TextView) findViewById(R.id.main_bottom_bar_remind_tv);
        mBenchTextView = (TextView) findViewById(R.id.main_bottom_bar_bench_tv);
        mSettingTextView = (TextView) findViewById(R.id.main_bottom_bar_setting_tv);
        mBottomTVLists.add(mRemindTextView);
        mBottomTVLists.add(mBenchTextView);
        mBottomTVLists.add(mSettingTextView);
        
        mRemindTextCorner = (TextView) findViewById(R.id.main_bottom_bar_remind_corner);
        mBenchTextCorner = (TextView) findViewById(R.id.main_bottom_bar_bench_corner);
        mSettingTextCorner = (TextView) findViewById(R.id.main_bottom_bar_setting_corner);
        mBottomTVCornerLists.add(mRemindTextCorner);
        mBottomTVCornerLists.add(mBenchTextCorner);
        mBottomTVCornerLists.add(mSettingTextCorner);

        mRemindImageView = (ImageView) findViewById(R.id.main_bottom_bar_remind_iv);
        mBenchImageView = (ImageView) findViewById(R.id.main_bottom_bar_bench_iv);
        mSettingImageView = (ImageView) findViewById(R.id.main_bottom_bar_setting_iv);
        mBottomIVLists.add(mRemindImageView);
        mBottomIVLists.add(mBenchImageView);
        mBottomIVLists.add(mSettingImageView);
	}

	private void initData() {

		// 初始化屏幕尺寸
		DisplayMetrics displayMetrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
		KingTellerStaticConfig.SCREEN.Width = displayMetrics.widthPixels;
		KingTellerStaticConfig.SCREEN.Height = displayMetrics.heightPixels;
		
		title_title.setText(mTitles[0]);
		
		wFragment = new WaitDoFragment();
		fFragment = new FunctionFragment();
		mFragment = new MoreFragment();
	    mTabs.add(wFragment);
	    mTabs.add(fFragment);
	    mTabs.add(mFragment);

	    mAdapter = new FragmentPagerAdapter(getSupportFragmentManager()){
            @Override
            public int getCount() {
                return mTabs.size();
            }
            @Override
            public Fragment getItem(int arg0) {
                return mTabs.get(arg0);
            }
        };
        
        mViewPager.setAdapter(mAdapter);
        mViewPager.setOffscreenPageLimit(3);
        mViewPager.setCurrentItem(0, true);
        mViewPager.setOnPageChangeListener(this);
        
        BottomLayoutListener mBottomListener = new BottomLayoutListener();// 底部按钮监听事件

        mRemindLinearLayout.setOnClickListener(mBottomListener);
        mBenchLinearLayout.setOnClickListener(mBottomListener);
        mSettingLinearLayout.setOnClickListener(mBottomListener);
        
        chosenIVSrcAndTVColor(0);

		//注册自定义   更新小点的广播
		IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction(KingTellerStaticConfig.ISUP_UPSTATUSACTION);
		intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");//网络变化action
		intentFilter.addAction("android.intent.action.W_UPDATA");//重新登陆，提醒页自动刷新
		registerReceiver(MainContentRecever, intentFilter);
				
		//启动本地服务
		if (!KingTellerJudgeUtils.isServiceWorked(this, "com.kingteller.client.service.KingTellerService")) {	
			startService(new Intent(this, KingTellerService.class)); 
		}
			
//		//启动本地	进程服务
//		if (!KingTellerUtils.isProessWorked(this, "com.kingteller:push")) {
//			startService(new Intent(this, KingTellerPushService.class)); 
//		}
				
		//启动JPush服务
		if (JPushInterface.isPushStopped(this)){
			JPushInterface.resumePush(getApplicationContext());
		}

		// 设置推送样式
		// KingTellerUtils.SetJPushSetting(this);

		// 延迟10秒设置别名
		currHandler.postDelayed(cRunnable, 10000);
		// 延时5秒检查更新
		initHandler.postDelayed(initRunnable, 5000);

	}
	
	
	@Override
    public void onPageScrolled(int position, float positionOffset, int arg2) {

    }

    @Override
    public void onPageScrollStateChanged(int arg0) {
    
    }

    @Override
    public void onPageSelected(int arg0) {
        //滑动后执行的方法
        resetTextViewColorAndImageViewSrc(3);
        switch (arg0){
            case 0:
                title_title.setText(mTitles[0]);
                chosenIVSrcAndTVColor(0);
                
                break;
            case 1:
                title_title.setText(mTitles[1]);
                chosenIVSrcAndTVColor(1);

                break;
            case 2:
                title_title.setText(mTitles[2]);
                chosenIVSrcAndTVColor(2);
                
                break;
            default:
                break;
        }

    }
    
    /**
     * 点击底部Tab按钮
     */
    class BottomLayoutListener implements OnClickListener {
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.main_bottom_bar_remind:
                    mViewPager.setCurrentItem(0, true);
                    break;
                case R.id.main_bottom_bar_bench:
                    mViewPager.setCurrentItem(1, true);
                    break;
                case R.id.main_bottom_bar_setting:
                    mViewPager.setCurrentItem(2, true);
                    break;
            }
        }
    }
    
    /**
     * 重设所有底部按钮图片和按钮字体颜色
     */
    private void resetTextViewColorAndImageViewSrc(int arg) {
        for(int i = 0; i<arg; i++){
            mBottomTVLists.get(i).setTextColor(getResources().getColor(R.color.main_bottom_color_nomal));
            mBottomIVLists.get(i).setSelected(false);
        }
    }

    /**
     * 设置选中图标与字体
     */
    private void chosenIVSrcAndTVColor(int arg) {
        mBottomTVLists.get(arg).setTextColor(getResources().getColor(R.color.main_bottom_color_pressde));
        mBottomIVLists.get(arg).setSelected(true);
    }
    

    /**
     * 设置角标
     */
    private void chosenTVCorner(int type, int num) {
    	switch (type) {
		case 0:
			if(num == 0){
				mBottomTVCornerLists.get(type).setVisibility(View.GONE); 
			}else{
				mBottomTVCornerLists.get(type).setVisibility(View.VISIBLE); 
				mBottomTVCornerLists.get(type).setText(String.valueOf(num));
			}
			break;
		case 1:
			if(num == 0){
				mBottomTVCornerLists.get(type).setVisibility(View.GONE); 
			}else{
				mBottomTVCornerLists.get(type).setVisibility(View.VISIBLE); 
				mBottomTVCornerLists.get(type).setText(String.valueOf(num));
			}
			break;
		case 2:

			break;
		default:
			break;
		}
    }
	
	@Override
	//相当于HOME键
	public void onBackPressed() {
		if (backCount == 2) {
			moveTaskToBack(true);
		} else {
			T.showShort(mContext, "再按一次退出到桌面");
			backCount++;
			mHandler.sendEmptyMessageDelayed(0, 2000);
		}
	}



	/**
	 * 极光推送设置别名返回
	 */
	private final TagAliasCallback mAliasCallback = new TagAliasCallback() {

		@Override
		public void gotResult(int code, String alias, Set<String> tags) {
			String logs;
			switch (code) {
			case 0:
				logs = "Set tag and alias success";
				Logger.i(TAG, logs + alias);
				setPushAlias(true);
				break;

			case 6002:
				logs = "Failed to set alias and tags due to timeout. Try again after 60s.";
				Logger.i(TAG, logs + alias);
				setPushAlias(false);
				break;

			default:
				logs = "Failed with errorCode = " + code;
				Logger.e(TAG, logs + alias);
				setPushAlias(false);
			}
		}

	};
	
	/**
	 * 极光推送设置别名  
	 */
	private void setPushAlias(boolean issuccess) {
		// 失败重新设置别名3次
		if (NowRegTryTimes < RegTryTimes && !issuccess) {
			NowRegTryTimes++;
			currHandler.postDelayed(cRunnable, 50000);
			Log.e("Jpus_setNameNum", NowRegTryTimes + "次");
		} else {
			Log.e("Jpus_setName", "成功");
		}
		User.savePushReg(MainActivity.this, issuccess);
	}
	
	/**
	 * 设置别名run
	 */
	private class JPushRegRunnable implements Runnable {
		public void run() {
			JPushInterface.setAliasAndTags(MainActivity.this,
					User.getInfo(MainActivity.this).getAlias(), null,
					mAliasCallback);
			currHandler.removeCallbacks(this);
		}
	}

	/**
	 * 检查更新run
	 */
	private class InitRunnable implements Runnable {
		public void run() {
			KingTellerUpdateUtils.CheckUpdate(MainActivity.this, false);
			initHandler.removeCallbacks(this);
		}
	}
	
	/**
	 * 删除日志
	 */
	private void setDeleteFile() {
		//删除log目录下文件
		KingTellerFileUtils.deletePathTextFile(KingTellerStaticConfig.CACHE_PATH.SD_LOG, 10);
		//删除合成图片
		KingTellerFileUtils.deletePathJpgFile(KingTellerStaticConfig.CACHE_PATH.SD_IMAGECACHE);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}
}
