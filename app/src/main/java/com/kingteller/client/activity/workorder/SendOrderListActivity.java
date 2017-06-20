package com.kingteller.client.activity.workorder;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.kingteller.R;
import com.kingteller.client.KingTellerApplication;
import com.kingteller.client.activity.workorder.frament.SendOrderFragment;
import com.kingteller.client.adapter.PagerAdapter;
import com.kingteller.client.adapter.TopTabAdapter;
import com.kingteller.client.config.KingTellerStaticConfig;
import com.kingteller.client.view.TabView;
import com.kingteller.client.view.TabView.OnItemTabListener;

/**
 * 我的派单
 */
public class SendOrderListActivity extends FragmentActivity implements
		OnClickListener, OnPageChangeListener {

	private SendOrderFragment noAcceptFragment,ongoingFragment,finishFragment;
	private PagerAdapter pagerAdapter;
	private ViewPager mViewPager;
	private ArrayList<Fragment> fragments;

	private String[] mTwoLevelTitles = new String[]{"未接收","处理中","已结束"};
	
	private List<TextView> mTwoLevelTitlesTVLists;
	
	private TextView title_text, title_twolevel_textone, title_twolevel_texttwo, title_twolevel_textthree;
	
	private Button title_left_btn, title_rightone_btn, title_righttwo_btn;

	private int offset = 0;// 动画图片偏移量
	private int currIndex = 0;// 当前页卡编号
	private int bmpW;// 动画图片宽度
	private ImageView imageView;// 动画图片
	
	@Override
	public void onCreate(Bundle paramBundle) {
		super.onCreate(paramBundle);
		setContentView(R.layout.layout_send_order_list);
		KingTellerApplication.addActivity(this);
		
		initUI();
		initData();
	}

	private void initUI() {
		InitImageView();
		initTitle();
		InitTextView();
		initViewPager();
	}

	private void initData() {
		
	}
	
	/**
	 * 初始化title
	 */
	private void initTitle() {
		title_text = (TextView) findViewById(R.id.layout_main_text);
		title_left_btn = (Button) findViewById(R.id.layout_main_left_btn);
		title_rightone_btn = (Button) findViewById(R.id.layout_main_rightone_btn);
		title_righttwo_btn = (Button) findViewById(R.id.layout_main_righttwo_btn);
		
		title_text.setText("我的派单");
		title_left_btn.setBackgroundResource(R.drawable.btn_back_arrow);
		title_left_btn.setOnClickListener(this);
		title_rightone_btn.setBackgroundResource(R.drawable.btn_serach);
		title_rightone_btn.setOnClickListener(this);
		title_righttwo_btn.setBackgroundResource(R.drawable.btn_add);
		title_righttwo_btn.setOnClickListener(this);
	}
	
	/**
	 * 初始化头标
	 * 
	 */
	private void InitTextView() {
		mTwoLevelTitlesTVLists  = new ArrayList<TextView>();
		
		title_twolevel_textone = (TextView) findViewById(R.id.title_twolevel_textone);
		title_twolevel_texttwo = (TextView) findViewById(R.id.title_twolevel_texttwo);
		title_twolevel_textthree = (TextView) findViewById(R.id.title_twolevel_textthree);
		
		mTwoLevelTitlesTVLists.add(title_twolevel_textone);
		mTwoLevelTitlesTVLists.add(title_twolevel_texttwo);
		mTwoLevelTitlesTVLists.add(title_twolevel_textthree);
		
		title_twolevel_textone.setText(mTwoLevelTitles[0]);
		title_twolevel_texttwo.setText(mTwoLevelTitles[1]);
		title_twolevel_textthree.setText(mTwoLevelTitles[2]);
		
		TwoLevelTitleOnClickListener mTwoLevelTitleOnClickListener = new TwoLevelTitleOnClickListener();// 底部按钮监听事件
		 
		title_twolevel_textone.setOnClickListener(mTwoLevelTitleOnClickListener);
		title_twolevel_texttwo.setOnClickListener(mTwoLevelTitleOnClickListener);
		title_twolevel_textthree.setOnClickListener(mTwoLevelTitleOnClickListener);
		
		chosenTwoLevelTVColor(0);
	}


    /**
     * 设置选中图标与字体
     */
    private void chosenTwoLevelTVColor(int arg) {
    	resetTwoLevelTVColor(3);
    	mTwoLevelTitlesTVLists.get(arg).setTextColor(getResources().getColor(R.color.twotitle_text_color_pressde));
    }
    
    /**
     * 重设所有底部按钮图片和按钮字体颜色
     */
    private void resetTwoLevelTVColor(int arg) {
        for(int i = 0; i<arg; i++){
        	mTwoLevelTitlesTVLists.get(i).setTextColor(getResources().getColor(R.color.twotitle_text_color_nomal));
        }
    }
	
	/**
	 * 初始化Viewpager页
	 */
	private void initViewPager() {
		mViewPager = (ViewPager) findViewById(R.id.viewPaper2);
		
		fragments = new ArrayList<Fragment>();
		noAcceptFragment = new SendOrderFragment().getInstance("tab1");
		ongoingFragment = new SendOrderFragment().getInstance("tab2");
		finishFragment = new SendOrderFragment().getInstance("tab3");
		
		fragments.add(noAcceptFragment);
		fragments.add(ongoingFragment);
		fragments.add(finishFragment);
		
		pagerAdapter = new PagerAdapter(getSupportFragmentManager(), fragments);
		mViewPager.setAdapter(pagerAdapter);
		mViewPager.setOffscreenPageLimit(3);
		mViewPager.setCurrentItem(0,true);
		mViewPager.setOnPageChangeListener(this);
	}
	
	
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
		case R.id.layout_main_left_btn:
			finish();
			break;
		case R.id.layout_main_rightone_btn:				//查询派单
			startActivity(new Intent(this,SendOrderSearchListActivity.class));
			break;
		case R.id.layout_main_righttwo_btn:				//新建工单
			startActivity(new Intent(this, RapairSendOrderActivity.class));
			break;
		default:
			break;
		}
	}
	
	@Override
	public void onPageScrollStateChanged(int arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPageSelected(int arg0) {
		int one = offset * 2 + bmpW;// 页卡1 -> 页卡2 偏移量
		int two = one * 2;// 页卡1 -> 页卡3 偏移量
		
		Animation animation = new TranslateAnimation(one * currIndex, one * arg0, 0, 0);// 显然这个比较简洁，只有一行代码。
		
		animation.setFillAfter(true);// True:图片停在动画结束位置
		animation.setDuration(300);
		imageView.startAnimation(animation);
		
		chosenTwoLevelTVColor(arg0);
		
		currIndex = arg0;
	}

	/**
	 * 头标点击监听
	 */
	private class TwoLevelTitleOnClickListener implements OnClickListener {
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.title_twolevel_textone:
				mViewPager.setCurrentItem(0, true);
				break;
			case R.id.title_twolevel_texttwo:
				mViewPager.setCurrentItem(1, true);
				break;
			case R.id.title_twolevel_textthree:
				mViewPager.setCurrentItem(2, true);
				break;
			}
			
		}

	}
	
	
	/**
	 * 初始化动画，这个就是页卡滑动时，下面的横线也滑动的效果，在这里需要计算一些数据
	 */
	private void InitImageView() {
		imageView = (ImageView) findViewById(R.id.title_twolevel_imageone);
		
		bmpW = BitmapFactory.decodeResource(getResources(),R.drawable.ic_twoleveltitle_bg).getWidth();// 获取图片宽度
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		int screenW = dm.widthPixels;// 获取分辨率宽度
		offset = (screenW / 3 - bmpW) / 2;// 计算偏移量--(屏幕宽度/页卡总数-图片实际宽度)/2
													// = 偏移量
		Matrix matrix = new Matrix();
		matrix.postTranslate(offset, 0);
		imageView.setImageMatrix(matrix);// 设置动画初始位置
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		KingTellerApplication.removeActivity(this);
	}
}
